using System;
using System.Collections.Generic;
using System.Text;

namespace BlockExplorer
{
    class Block
    {
        public int index;
        public List<Transaction> transactions;
        public int difficulty;
        public string minedBy;
        public string blockDataHash;
        public int nonce;
        public string dateCreated;
        public string blockHash;
    }
}
