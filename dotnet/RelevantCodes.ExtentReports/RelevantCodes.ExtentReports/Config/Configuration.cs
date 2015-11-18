using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Xml.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RelevantCodes.ExtentReports.Config
{
    public class Configuration
    {
        private string _filePath;

        public Dictionary<string, string> Read()
        {
            if (!File.Exists(_filePath))
            {
                return null;
            }
            
            var configMap = new Dictionary<string, string>();

            var doc = XDocument.Load(_filePath, LoadOptions.None);

            foreach (var xe in doc.Root.Descendants("configuration").First().Elements())
            {
                configMap.Add(xe.Name.ToString(), xe.Value);
            }

            return configMap;
        }

        public Configuration(string FilePath)
        {
            _filePath = FilePath;
        }
    }
}
