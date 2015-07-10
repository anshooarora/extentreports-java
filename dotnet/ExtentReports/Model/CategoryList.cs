using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace RelevantCodes.ExtentReports.Model
{
    internal class AttributeList
    {
        public List<TestAttribute> Categories;

        public string GetItem(int Index)
        {
            if (Categories.Count > Index)
                return Categories[Index].GetName();

            return null;
        }

        public int Count
        {
            get
            { 
                return Categories.Count; 
            }
        }

        public bool Contains(Category c)
        {
            if (Categories.Contains(c))
                return true;

            return false;
        }

        public AttributeList()
        {
            Categories = new List<TestAttribute>();
        }
    }
}
