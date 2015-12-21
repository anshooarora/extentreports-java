using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace RelevantCodes.ExtentReports.Converters
{
    interface IConvertible<T>
    {
        List<T> Convert();
    }
}
