using System;

namespace RelevantCodes.ExtentReports
{
    interface IExtentTestClass 
    {
	    void Log(LogStatus Status, string StepName, string Details);

	    void Log(LogStatus Status, string Details);

	    void Log(LogStatus Status, string StepName, Exception Ex);

	    void Log(LogStatus Status, Exception Ex);

	    string AddScreenCapture(string ImgPath);

	    string AddScreencast(string ScreencastPath);

	    ExtentTest AssignCategory(params string[] Categorie);

	    ExtentTest AssignAuthor(params string[] Author);

	    ExtentTest AppendChild(ExtentTest Node);

	    LogStatus GetCurrentStatus();

        string Description { get; set; }

        DateTime StartTime { get; set; }

        DateTime EndTime { get; set; }
    }
}
