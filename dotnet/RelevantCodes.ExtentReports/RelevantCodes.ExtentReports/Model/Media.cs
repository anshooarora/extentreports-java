using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RelevantCodes.ExtentReports.Model
{
    internal class Media
    {
        public string TestName
        {
            get;
            internal set;
        }

        public string Source
        {
            get;
            internal set;
        }

        public Guid TestID
        {
            get;
            internal set;
        }

        public Media() { }

        public Media(string TestName, string Source, Guid TestID)
        {
            this.TestName = TestName;
            this.Source = Source;
            this.TestID = TestID;
        }
    }
}
