namespace RelevantCodes.ExtentReports
{
    using System;
    using System.Collections.Generic;

    using RelevantCodes.ExtentReports.Source;
    using RelevantCodes.ExtentReports.Support;

    internal class SourceBuilder
    {
        public static string build(string Source, string[] Flags, string[] Values)
        {
            for (int ix = 0; ix < Flags.Length; ix++)
            {
                string matcher = Flags[ix] + ".*" + Flags[ix];
                string match = RegexMatcher.GetNthMatch(Source, matcher, 0);

                if (match == null)
                {
                    Source = Source.Replace(Flags[ix], Values[ix]);
                }
                else
                {
                    Source = Source.Replace(match, matcher.Replace(".*", Values[ix]));
                }
            }

            return Source;
        }

        public static string getSource(Dictionary<string, string> Info) {
            string src = "";
        
            foreach (KeyValuePair<string, string> entry in Info) {
                src += SystemInfoHtml.GetColumn();
            
                src = src.Replace(ExtentFlag.GetPlaceHolder("systemInfoParam"), entry.Key)
                            .Replace(ExtentFlag.GetPlaceHolder("systemInfoValue"), entry.Value);
            }
        
            return src;
        }
    }
}
