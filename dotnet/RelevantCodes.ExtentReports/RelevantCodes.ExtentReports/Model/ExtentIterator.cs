using System;
using System.Collections;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RelevantCodes.ExtentReports.Model
{
    internal class ExtentIterator<T> : IEnumerable<T>
    {
        private List<T> _list;

        public IEnumerator<T> GetEnumerator()
        {
            for (int ix = 0; ix < _list.Count(); ix++)
            {
                yield return _list[ix];
            }
        }

        IEnumerator IEnumerable.GetEnumerator()
        {
            return GetEnumerator();
        }

        public ExtentIterator(List<T> ExtentList)
        {
            _list = ExtentList;
        }
    }
}
