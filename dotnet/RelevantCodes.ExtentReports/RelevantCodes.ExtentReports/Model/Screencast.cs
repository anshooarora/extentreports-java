using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RelevantCodes.ExtentReports.Model
{
    internal class Screencast : Media
    {
        public Screencast(string TestName, string Source) : base(TestName, Source) { }
    }
}