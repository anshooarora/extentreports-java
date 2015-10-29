using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace RelevantCodes.ExtentReports.Model
{
    public abstract class TestAttribute
    {
        protected string _name;

        public string Name
        {
            get 
            {
                return _name;
            }
        }

        protected TestAttribute(string Name)
        {
            _name = Name;
        }
    }
}
