using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RelevantCodes.ExtentReports.Model
{
    public class Author : TestAttribute
    {
        public Author(string Name) : base(Name.Trim()) { }
    }
}