using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RelevantCodes.ExtentReports.Model
{
    public class StandardSystemInfo
    {
        private Dictionary<string, string> _properties;

        public StandardSystemInfo()
        {
            _properties = new Dictionary<string, string>();

            _properties.Add("User Name", Environment.UserName);
            _properties.Add("Machine Name", Environment.MachineName);
            _properties.Add("Domain", Environment.UserDomainName);
            _properties.Add("OS", Environment.OSVersion.Platform + " " + Environment.OSVersion.Version);
        }

        public void Clear()
        {
            _properties.Clear();
        }

        public Dictionary<string, string> GetInfo()
        {
            return _properties;
        }
    }
}
