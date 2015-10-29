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
            private set;
        }

        public string Source
        {
            get;
            private set;
        }

        public Media(string TestName, string Source)
        {
            this.TestName = TestName;
            this.Source = Source;
        }
    }
}
