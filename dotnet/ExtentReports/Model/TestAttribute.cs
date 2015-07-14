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
