using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;

namespace RelevantCodes.ExtentReports.Utils
{
    internal class ResourceUtils
    {
        public string GetResource(string Folder, string FileName)
        {
            string result = string.Empty;

            using (Stream stream = this.GetType().Assembly.GetManifestResourceStream(Folder + "." + FileName))
            {
                using (StreamReader sr = new StreamReader(stream))
                {
                    result = sr.ReadToEnd();
                }
            }

            return result;
        }
    }
}
