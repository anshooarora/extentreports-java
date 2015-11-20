using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace RelevantCodes.ExtentReports
{
    internal class ExtentTestInterruptedException : Exception
    {
        public ExtentTestInterruptedException() { }

        public ExtentTestInterruptedException(string message) : base(message) { }

        public ExtentTestInterruptedException(string message, Exception inner): base(message, inner) { } 
    }
}
