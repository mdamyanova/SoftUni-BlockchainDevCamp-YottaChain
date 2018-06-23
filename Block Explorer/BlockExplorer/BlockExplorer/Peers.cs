using System;
using System.Collections.Concurrent;
using System.Collections.Generic;
using System.Text;

namespace BlockExplorer
{
    class Peers
    {
        ///A Class Managing Balances
        public Peers(ConcurrentDictionary<string, string> PeersMap) ///Constructor for Balances class
        {
            this.PeersMap = PeersMap;
        }
        public ConcurrentDictionary<string, string> PeersMap { get; set; }
    }
}
