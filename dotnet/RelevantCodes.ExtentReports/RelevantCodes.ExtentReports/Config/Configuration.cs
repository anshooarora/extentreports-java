using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Xml.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Diagnostics;

namespace RelevantCodes.ExtentReports.Config
{
    public class Configuration
    {
        private XDocument _xdoc;

        public Dictionary<string, string> Read()
        {
            if (_xdoc == null)
            {
                Debug.WriteLine("Unable to configure report with the supplied configuration. Please check the input File or XDocument and try again.");
                return null;
            }

            var configMap = new Dictionary<string, string>();

            foreach (var xe in _xdoc.Descendants("configuration").First().Elements())
            {
                configMap.Add(xe.Name.ToString(), xe.Value);
            }

            return configMap;
        }

        public Configuration(XDocument XDoc)
        {
            _xdoc = XDoc;
        }

        public Configuration(string FilePath)
        {
            if (!File.Exists(FilePath))
            {
                Debug.WriteLine("The file " + FilePath + " was not found.");
                return;
            }

            _xdoc = XDocument.Load(FilePath, LoadOptions.None);
        }
    }
}
