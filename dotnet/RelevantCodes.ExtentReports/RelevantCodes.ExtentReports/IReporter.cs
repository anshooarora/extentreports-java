using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RelevantCodes.ExtentReports
{
    public interface IReporter
    {
        void Start(Report report);
        void Stop();
        void Flush();
        void AddTest();
    }
}
