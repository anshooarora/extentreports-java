// ***********************************************************************
// Copyright (c) 2015, Anshoo Arora (Relevant Codes). All rights reserved.
//
// Copyrights licensed under the New BSD License.
//
// See the accompanying LICENSE file for terms.
// ***********************************************************************

namespace RelevantCodes.ExtentReports.Support
{
    using System;
    using System.Collections.Generic;
    using System.Linq;
    using System.Text;
    using System.Text.RegularExpressions;

    internal class RegexMatcher
    {
        public static string GetNthMatch(string Text, string Pattern, int MatchNumber)
        {
            var groups = GetMatch(Text, Pattern);

            if (groups != null)
            {
                return groups[MatchNumber].ToString();
            }

            return null;
        }

        public static GroupCollection GetMatch(string Text, string Pattern)
        {
            Match match = Regex.Match(Text, Pattern);

            if (match.Success)
            {
                return match.Groups;
            }

            return null;
        }
    }
}
