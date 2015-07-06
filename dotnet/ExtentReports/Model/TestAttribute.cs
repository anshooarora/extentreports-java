namespace RelevantCodes.ExtentReports.Model
{
    using System;
    using System.Collections.Generic;
    using System.Linq;
    using System.Text;

    internal abstract class TestAttribute
    {
        protected string name;

        public string GetName()
        {
            return name;
        }

        protected TestAttribute(String Name)
        {
            name = Name;
        }
    }
}
