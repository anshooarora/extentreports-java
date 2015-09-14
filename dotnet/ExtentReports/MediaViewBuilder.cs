// ***********************************************************************
// Copyright (c) 2015, Anshoo Arora (Relevant Codes). All rights reserved.
//
// Copyrights licensed under the New BSD License.
//
// See the accompanying LICENSE file for terms.
// ***********************************************************************

namespace RelevantCodes.ExtentReports
{
    using System;
    using System.Collections.Generic;
    using System.Linq;

    using Model;
    using Source;

    internal class MediaViewBuilder
    {
        public static string GetSource<T>(List<T> MediaList, string type) {
            string source = "";
        
            if (MediaList == null || MediaList.Count == 0) {
                source = SourceBuilder.Build(
                    ObjectEmbedHtml.GetFullWidth(),
                    new string[] { ExtentFlag.GetPlaceHolder("objectViewValue"), ExtentFlag.GetPlaceHolder("objectViewNull") },
                    new string[] { "No media was embed for the tests in this report.", ExtentFlag.GetPlaceHolder("objectViewNull" + type) }
                );
            
                return source;
            }
        
            foreach (Object sc in MediaList) {
                source += ObjectEmbedHtml.GetColumn();
            
                if (sc is ScreenCapture) {
                    source = source.Replace(ExtentFlag.GetPlaceHolder("objectViewParam"), ((ScreenCapture) sc).TestName)
                        .Replace(ExtentFlag.GetPlaceHolder("objectViewValue"), ((ScreenCapture) sc).Source);
                }
            
                if (sc is Screencast) {
                    source = source.Replace(ExtentFlag.GetPlaceHolder("objectViewParam"), ((Screencast) sc).TestName)
                        .Replace(ExtentFlag.GetPlaceHolder("objectViewValue"), ((Screencast) sc).Source);
                }
            }
        
            return source;
        }
    }
}
