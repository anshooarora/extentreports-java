using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

using RelevantCodes.ExtentReports;

namespace RelevantCodes.ExtentReports.Tests.Common
{
    internal class ExtentManager
    {
        private static readonly ExtentReports _instance = new ExtentReports("Extent.Net.html", DisplayOrder.OldestFirst);

        static ExtentManager() { }

        private ExtentManager() { }

        public static ExtentReports Instance
        {
            get
            {
                return _instance;
            }
        }
    }
}
