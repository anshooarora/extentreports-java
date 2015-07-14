// ***********************************************************************
// Copyright (c) 2015, Anshoo Arora (Relevant Codes). All rights reserved.
//
// Copyrights licensed under the New BSD License.
//
// See the accompanying LICENSE file for terms.
// ***********************************************************************

namespace RelevantCodes.ExtentReports.Model
{
    using System;
    using System.Collections.Generic;
    using System.Linq;
    using System.Text;

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
