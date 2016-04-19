using RelevantCodes.ExtentReports.Model;

namespace RelevantCodes.ExtentReports
{
    public interface IReporter
    {
        void Start(Report report);
        void Stop();
        void Flush();
        void AddTest(Test test);
    }
}
