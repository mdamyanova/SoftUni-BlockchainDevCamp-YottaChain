using System;
using System.Collections.Concurrent;
using System.Collections.Generic;
using System.Text;

namespace BlockExplorer
{
    class Balance
    {
        ///A Class Managing Balances
        public Balance(ConcurrentDictionary<string, decimal> AddressBalances) ///Constructor for Balances class
        {
            this.AddressBalances = AddressBalances;
        }
        public ConcurrentDictionary<string, decimal> AddressBalances { get; set; }
    }
}
