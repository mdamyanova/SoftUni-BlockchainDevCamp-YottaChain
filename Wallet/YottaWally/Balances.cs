using System;
using System.Collections.Concurrent;
using System.Collections.Generic;
using System.Text;

namespace YottaWally
{
    class Balances
    {
        ///A Class Managing Balances
        public Balances(ConcurrentDictionary<string,decimal> AddressBalances) ///Constructor for Balances class
        {
            this.AddressBalances = AddressBalances;
        }
        public ConcurrentDictionary<string, decimal> AddressBalances { get; set; }
    }
}
