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
    using System.Text;

    using Source;
    using Support;

    /// <summary>
    /// Report configuration
    /// </summary>
    public class ReportConfig
    {
        private ReportInstance report;

        /// <summary>
        /// Inject javascript into the report
        /// </summary>
        /// <param name="Script">JavaScript</param>
        /// <returns>ReportConfig object</returns>
        public ReportConfig InsertJs(string Script)
        {
            Script = "<script type='text/javascript'>" + Script + "</script>";
            report.UpdateSource(report.Source.Replace(ExtentFlag.GetPlaceHolder("customscript"), Script + ExtentFlag.GetPlaceHolder("customscript")));

            return this;
        }

        /// <summary>
        /// Inject custom CSS into the report
        /// </summary>
        /// <param name="Styles">CSS styles</param>
        /// <returns>ReportConfig object</returns>
        public ReportConfig InsertStyles(string Styles)
        {
            Styles = "<style type='text/css'>" + Styles + "</style>";
            report.UpdateSource(report.Source.Replace(ExtentFlag.GetPlaceHolder("customcss"), Styles + ExtentFlag.GetPlaceHolder("customcss")));

            return this;
        }

        /// <summary>
        /// Add a CSS stylesheet
        /// </summary>
        /// <param name="StylesheetPath">Path of .css file</param>
        /// <returns>ReportConfig object</returns>
        public ReportConfig AddStylesheet(string StylesheetPath)
        {
            string link = "<link href='file:///" + StylesheetPath + "' rel='stylesheet' type='text/css' />";

            if (StylesheetPath.StartsWith(".") || StylesheetPath.StartsWith("/"))
            {
                link = "<link href='" + StylesheetPath + "' rel='stylesheet' type='text/css' />";
            }

            report.UpdateSource(report.Source.Replace(ExtentFlag.GetPlaceHolder("customcss"), link + ExtentFlag.GetPlaceHolder("customcss")));

            return this;
        }

        /// <summary>
        /// Report headline
        /// </summary>
        /// <param name="Headline">A short headline</param>
        /// <returns>ReportConfig object</returns>
        public ReportConfig ReportHeadline(string Headline)
        {
            int maxlength = 70;

            if (Headline.Length >= maxlength)
                Headline = Headline.Substring(0, maxlength - 1);

            string pattern = ExtentFlag.GetPlaceHolder("headline") + ".*" + ExtentFlag.GetPlaceHolder("headline");
            Headline = pattern.Replace(".*", Headline);
            report.UpdateSource(report.Source.Replace(RegexMatcher.GetNthMatch(report.Source, pattern, 0), Headline));

            return this;
        }

        /// <summary>
        /// Report Header
        /// </summary>
        /// <param name="Name">Header of the report</param>
        /// <returns>ReportConfig object</returns>
        public ReportConfig ReportName(string Name)
        {
            int maxlength = 20;

            if (Name.Length >= maxlength)
                Name = Name.Substring(0, maxlength - 1);

            string pattern = ExtentFlag.GetPlaceHolder("logo") + ".*" + ExtentFlag.GetPlaceHolder("logo");
            Name = pattern.Replace(".*", Name);
            report.UpdateSource(report.Source.Replace(RegexMatcher.GetNthMatch(report.Source, pattern, 0), Name));

            return this;
        }

        /// <summary>
        /// Document Title
        /// </summary>
        /// <param name="Title">Title</param>
        /// <returns>ReportConfig object</returns>
        public ReportConfig DocumentTitle(string Title)
        {
            string docTitle = "<title>.*</title>";
            report.UpdateSource(report.Source.Replace(RegexMatcher.GetNthMatch(report.Source, docTitle, 0), docTitle.Replace(".*", Title)));

            return this;
        }

        internal ReportConfig(ReportInstance report)
        {
            this.report = report;
        }
    }
}
