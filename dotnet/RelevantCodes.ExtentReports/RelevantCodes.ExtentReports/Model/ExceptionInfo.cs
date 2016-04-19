using System;
using RelevantCodes.ExtentReports.Model;

namespace RelevantCodes.ExtentReports.Model
{
    public class ExceptionInfo
    {
        public Exception Exception { get; private set; }

        public string ExceptionName { get; private set; }

        public string StackTrace { get; private set; }

        public Test Test { get; private set; }

        public ExceptionInfo(Test test, Exception exception)
        {
            this.Test = test;
            this.Exception = exception;

            ExceptionName = exception.Message;
            StackTrace = exception.StackTrace;
        }
    }
}
