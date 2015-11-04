using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RelevantCodes.ExtentReports.View
{
    internal class Table
    {
        public static string Source
        {
            get
            {
                return @"
                        <table></table>
                        ";
            }
        }

        public static string RowSource
        {
            get
            {
                return @"
                        <tr></tr>
                        ";
            }
        }

        public static string CellSource(int Cols)
        {
            string td = "";

            for (int ix = 1; ix <= Cols; ix++)
            {
                td += "<td></td>";
            }

            return td;
        }
    }
}
