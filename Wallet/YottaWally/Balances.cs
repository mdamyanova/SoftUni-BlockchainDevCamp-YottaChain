using System;
using System.Collections.Concurrent;
using System.Collections.Generic;
using System.Text;

namespace YottaWally
{
    class Balances
    {
        public ConcurrentDictionary<string, decimal> AddressBalances { get; set; }
    }
}
