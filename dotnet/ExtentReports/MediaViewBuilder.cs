namespace RelevantCodes.ExtentReports
{
    using System;
    using System.Collections.Generic;
    using System.Linq;

    using Model;
    using Source;

    internal class MediaViewBuilder
    {
        public static string GetSource<T>(List<T> MediaList) {
            string source = "";
        
            if (MediaList == null || MediaList.Count == 0) {
                source = ObjectEmbedHtml.GetFullWidth().Replace(ExtentFlag.GetPlaceHolder("objectViewValue"), "No media was embed for the tests in this report.");
            
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
