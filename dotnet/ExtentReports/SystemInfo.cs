// ***********************************************************************
// Copyright (c) 2015, Anshoo Arora (Relevant Codes). All rights reserved.
//
// Copyrights licensed under the New BSD License.
//
// See the accompanying LICENSE file for terms.
// ***********************************************************************

namespace RelevantCodes.ExtentReports
{
    using System;
    using System.Collections.Generic;
    using System.Linq;
    using System.Text;

    using Model;

    internal class SystemInfo
    {
        private SystemProperties properties;

        public void Clear()
        {
            properties.Info.Clear();
        }

        public Dictionary<string, string> GetInfo()
        {
            if (properties == null)
            {
                return null;
            }

            return properties.Info;
        }

        public void SetInfo(Dictionary<string, string> SystemInfo)
        {
            foreach (KeyValuePair<string, string> pair in SystemInfo)
            {
                properties.Info.Add(pair.Key, pair.Value);
            }
        }

        public void SetInfo(string Param, string Value)
        {
            properties.Info.Add(Param, Value);
        }

        private void SetInfo()
        {
            if (properties == null)
            {
                properties = new SystemProperties();
            }

            properties.Info.Add("User Name", Environment.UserName);
            properties.Info.Add("Machine Name", Environment.MachineName);
            properties.Info.Add("Domain", Environment.UserDomainName);
            properties.Info.Add("OS", Environment.OSVersion.Platform + " " + Environment.OSVersion.Version);
        }

        public SystemInfo()
        {
            SetInfo();
        }
    }
}
