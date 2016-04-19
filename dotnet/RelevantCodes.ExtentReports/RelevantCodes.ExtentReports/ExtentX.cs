using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;

using RelevantCodes.ExtentReports.Model;

using MongoDB.Driver;
using MongoDB.Bson;

namespace RelevantCodes.ExtentReports
{
    internal class ExtentX : Report, IReporter
    {
        Report _report;
        ObjectId _reportId;

        MongoClient _mongoClient;

        IMongoDatabase _db;

        IMongoCollection<BsonDocument> _projectCollection;
        IMongoCollection<BsonDocument> _reportCollection;
        IMongoCollection<BsonDocument> _testCollection;
        IMongoCollection<BsonDocument> _nodeCollection;
        IMongoCollection<BsonDocument> _logCollection;
        IMongoCollection<BsonDocument> _categoryCollection;
        IMongoCollection<BsonDocument> _authorCollection;
        IMongoCollection<BsonDocument> _categoriesTests;
        IMongoCollection<BsonDocument> _authorsTests;

        public void Start(Report report)
        {
            _report = report;

            // ExtentX database name: extent
            _db = _mongoClient.GetDatabase("extent");

            // collections
            _projectCollection = _db.GetCollection<BsonDocument>("project");
            _reportCollection = _db.GetCollection<BsonDocument>("report");
            _testCollection = _db.GetCollection<BsonDocument>("test");
            _nodeCollection = _db.GetCollection<BsonDocument>("node");
            _logCollection = _db.GetCollection<BsonDocument>("log");
            _categoryCollection = _db.GetCollection<BsonDocument>("category");
            _authorCollection = _db.GetCollection<BsonDocument>("author");

            // many to many collections
            _categoriesTests = _db.GetCollection<BsonDocument>("category_tests__test_categories");
            _authorsTests = _db.GetCollection<BsonDocument>("author_tests__test_authors");
        }

        public void Stop() { }

        void IReporter.Flush()
        {
            if (_reportId.Pid.Equals(0))
                InsertReport();

            var filter = Builders<BsonDocument>.Filter.Eq("_id", _reportId);
            var update = Builders<BsonDocument>.Update
                .Set("endTime", _report.EndTime)
                .Set("status", _report.ReportStatus.ToString().ToLower());

            _reportCollection.UpdateOne(filter, update);
        }

        private void InsertReport()
        {
            if (!_reportId.Pid.Equals(0))
                return;

            BsonDocument document;

            var projectId = GetProjectId();
            var id = _report.MongoDBObjectId;

            // if extent is started with [replaceExisting = false] and ExtentX is used,
            // use the same report ID for the 1st report run and update the database for
            // the corresponding report-ID
            if (id != null && !string.IsNullOrEmpty(id))
            {
                document = new BsonDocument
                {
                    { "_id", new ObjectId(id) }
                };

                var bsonReport = _reportCollection.Find(document).FirstOrDefault();

                if (bsonReport != null)
                {
                    _reportId = bsonReport["_id"].AsObjectId;
                    return;
                }
            }

            // if [replaceExisting = true] or the file does not exist, create a new
            // report-ID and assign all components to it
            document = new BsonDocument
            {
                { "project", projectId },
                { "fileName", Path.GetFileName(_report.FilePath) },
                { "startTime", _report.StartTime }
            };

            _reportCollection.InsertOne(document);

            _reportId = document["_id"].AsObjectId;
            _report.MongoDBObjectId = _reportId.ToString();
        }

        private ObjectId GetProjectId()
        {
            String projectName = _report.ProjectName;

            var document = new BsonDocument
            {
                { "name", projectName }
            };

            var bsonProject = _projectCollection.Find(document).FirstOrDefault();

            if (bsonProject != null)
            {
                return bsonProject["_id"].AsObjectId;
            }
            else
            {
                _projectCollection.InsertOne(document);
                return document["_id"].AsObjectId;
            }
        }

        void IReporter.AddTest(Test test)
        {
            if (_reportId.Pid.Equals(0))
                InsertReport();
            
            var document = new BsonDocument
            {
                { "report", _reportId },
                { "name", test.Name },
                { "status", test.Status.ToString().ToLower() },
                { "description", test.Description },
                { "startTime", test.StartTime },
                { "endTime", test.EndTime },
                { "childNodesCount", test.NodeList.Count },
                { "categorized", test.CategoryList.Count > 0 ? true : false }
            };

            _testCollection.InsertOne(document);

            var testId = document["_id"].AsObjectId;

            AddLogs(test, testId);

            AddNodes(test, testId);

            AddCategories(test, testId);
            AddAuthors(test, testId);
        }

        private void AddLogs(Test test, ObjectId testId)
        {
            BsonDocument document;
            int ix = 0;

            test.LogList.ForEach(x =>
            {
                document = new BsonDocument
                {
                    { "test", testId },
                    { "report", _reportId },
                    { "testName", test.Name },
                    { "logSequence", ix },
                    { "status", x.LogStatus.ToString().ToLower() },
                    { "timestamp", x.Timestamp },
                    { "stepName", x.StepName == null ? "" : x.StepName },
                    { "details", x.Details }
                };

                _logCollection.InsertOne(document);

                ix++;
            });
        }

        private void AddNodes(Test test, ObjectId testId)
        {
            if (!test.ContainsChildNodes)
                return;

            BsonDocument document;

            test.NodeList.ForEach(x =>
            {
                document = new BsonDocument
                {
                    { "test", testId },
                    { "parentTestName", test.Name },
                    { "report", _reportId },
                    { "name", x.Name },
                    { "level", 1 },
                    { "status", x.Status.ToString().ToLower() },
                    { "description", x.Description },
                    { "startTime", x.StartTime },
                    { "endTime", x.EndTime },
                    { "childNodesCount", 0 }
                };

                _nodeCollection.InsertOne(document);

                var nodeId = document["_id"].AsObjectId;
                AddLogs(x, nodeId);
            });
        }

        private void AddCategories(Test test, ObjectId testId)
        {
            BsonDocument document;

            test.CategoryList.ForEach(x =>
            {
                document = new BsonDocument
                {
                    { "tests", testId },
                    { "report", _reportId },
                    { "name", x.Name },
                    { "status", test.Status.ToString().ToLower() },
                    { "testName", test.Name }
                };

                _categoryCollection.InsertOne(document);

                var categoryId = document["_id"].AsObjectId;

                /* create association with category
                 * tests (many) <-> categories (many)
                 * tests and categories have a many to many relationship
                 *   - a test can be assigned with one or more categories
                 *   - a category can have one or more tests
                 */

                document = new BsonDocument
                {
                    { "test_categories", testId },
                    { "category_tests", categoryId },
                    { "category", x.Name },
                    { "test", test.Name }
                };

                _categoriesTests.InsertOne(document);
            });
        }

        private void AddAuthors(Test test, ObjectId testId)
        {
            BsonDocument document;

            test.AuthorList.ForEach(x =>
            {
                document = new BsonDocument
                {
                    { "tests", testId },
                    { "report", _reportId },
                    { "name", x.Name },
                    { "status", test.Status.ToString().ToLower() },
                    { "testName", test.Name }
                };

                _authorCollection.InsertOne(document);

                var authorId = document["_id"].AsObjectId;

                /* create association with author
                 * tests (many) <-> authors (many)
                 * tests and authors have a many to many relationship
                 *   - a test can be assigned with one or more authors
                 *   - an author can have one or more tests
                 */
                document = new BsonDocument
                {
                    { "test_authors", testId },
                    { "author_tests", authorId },
                    { "author", x.Name },
                    { "test", test.Name }
                };

                _authorsTests.InsertOne(document);
            });
        }

        /// <summary>
        /// Connects to MongoDB using a connection string.
        /// Example: mongodb://host:27017,host2:27017/?replicaSet=rs0
        /// </summary>
        /// <param name="connectionString"></param>
        public ExtentX(string connectionString)
        {
            _mongoClient = new MongoClient(connectionString);
        }

        public ExtentX(MongoUrl url)
        {
            _mongoClient = new MongoClient(url);
        }

        public ExtentX(MongoClientSettings settings)
        {
            _mongoClient = new MongoClient(settings);
        }

        /// <summary>
        /// Connects to MongoDB default settings, localhost:27017
        /// </summary>
        public ExtentX()
        {
            _mongoClient = new MongoClient();
        }
    }
}
