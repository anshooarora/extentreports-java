namespace RelevantCodes.ExtentReports
{
    using System;
    using System.Collections.Generic;
    using System.Linq;
    using System.Text;

    using Source;
    using Support;

    public class ReportConfig : ReportInstance
    {
        public ReportConfig InsertJs(string Script)
        {
            Script = "<script type='text/javascript'>" + Script + "</script>";
            UpdateSource(Source.Replace(ExtentFlag.GetPlaceHolder("customscript"), Script + ExtentFlag.GetPlaceHolder("customscript")));

            return this;
        }

        public ReportConfig InsertStyles(string Styles)
        {
            Styles = "<style type='text/css'>" + Styles + "</style>";
            UpdateSource(Source.Replace(ExtentFlag.GetPlaceHolder("customcss"), Styles + ExtentFlag.GetPlaceHolder("customcss")));

            return this;
        }

        public ReportConfig AddStylesheet(string StylesheetPath)
        {
            string link = "<link href='file:///" + StylesheetPath + "' rel='stylesheet' type='text/css' />";

            if (StylesheetPath.Substring(0, 1).Equals(".") || StylesheetPath.Substring(0, 1).Equals("/"))
            {
                link = "<link href='" + StylesheetPath + "' rel='stylesheet' type='text/css' />";
            }

            UpdateSource(Source.Replace(ExtentFlag.GetPlaceHolder("customcss"), link + ExtentFlag.GetPlaceHolder("customcss")));

            return this;
        }

        public ReportConfig ReportHeadline(string Headline)
        {
            int maxlength = 70;

            Headline = Headline.Substring(0, maxlength - 1);

            string pattern = ExtentFlag.GetPlaceHolder("headline") + ".*" + ExtentFlag.GetPlaceHolder("headline");
            Headline = pattern.Replace(".*", Headline);
            UpdateSource(Source.Replace(RegexMatcher.GetNthMatch(Source, pattern, 0), Headline));

            return this;
        }

        public ReportConfig ReportName(string Name)
        {
            int maxlength = 20;

            Name = Name.Substring(0, maxlength - 1);

            string pattern = ExtentFlag.GetPlaceHolder("logo") + ".*" + ExtentFlag.GetPlaceHolder("logo");
            Name = pattern.Replace(".*", Name);
            UpdateSource(Source.Replace(RegexMatcher.GetNthMatch(Source, pattern, 0), Name));

            return this;
        }

        public ReportConfig DocumentTitle(string Title)
        {
            string docTitle = "<title>.*</title>";
            UpdateSource(Source.Replace(RegexMatcher.GetNthMatch(Source, docTitle, 0), docTitle.Replace(".*", Title)));

            return this;
        }

        public ReportConfig() { }
    }
}
