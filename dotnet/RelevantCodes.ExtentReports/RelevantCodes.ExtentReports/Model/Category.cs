using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RelevantCodes.ExtentReports.Model
{
    public class Category : TestAttribute
    {
        public Category(string Name) : base(Name.Trim()) { }
    }
}