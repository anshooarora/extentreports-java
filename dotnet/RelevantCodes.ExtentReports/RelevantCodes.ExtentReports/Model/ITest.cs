using System;
using System.Collections.Generic;

namespace RelevantCodes.ExtentReports.Model
{
    public interface ITest 
    {
        void setStartedTime(DateTime startedTime);

        DateTime getStartedTime();

        String getRunDuration();

        void setEndedTime(DateTime endedTime);

        DateTime getEndedTime();

        LogStatus getStatus();

        void setStatus(LogStatus logStatus);

        void setDescription(String description);

        String getDescription();

        void setName(String name);

        String getName();

        Guid getId();

        void setCategory(TestAttribute category);

        List<TestAttribute> getCategoryList();

        void setAuthor(TestAttribute author);

        List<TestAttribute> getAuthorsList();

        List<Log> getLogList();

        void setLog(List<Log> logList);

        void hasChildNodes(bool val);

        List<Test> getNodeList();

        void setNodeList(List<Test> nodeList);

        void setException(ExceptionInfo exceptionInfo);

        void setUUID(Guid id);
    }
}
