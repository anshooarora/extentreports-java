using System;
using System.Collections.Generic;
using System.Linq;

namespace RelevantCodes.ExtentReports.Model
{
    public class SystemProperties
    {
        public void setSystemInfo(Dictionary<String, String> info)
        {
            //if (this.info.Count() > 0)
            //info.Concat(this.info);

            //this.info = info;
            this.info.Concat(info);
        }

        public void setSystemInfo(String k, String v)
        {
            info.Add(k, v);
        }

        public Dictionary<String, String> getSystemInfo()
        {
            return info;
        }

        public SystemProperties()
        {
            info = new Dictionary<String, String>();
        }

        private Dictionary<String, String> info;
    }
}
