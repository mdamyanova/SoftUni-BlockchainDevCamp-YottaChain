using System;
using System.Collections.Generic;
using System.Text;

namespace BlockExplorer
{
    class Transaction
    {
        ///A Class Managing [GET]ConfirmedTransactions
        public string from { get; set; }
        public string to { get; set; }
        public long value { get; set; }
        public int fee { get; set; }
        public string dateCreated { get; set; }
        public string data { get; set; }
        public string senderPubKey { get; set; }
        public string transactionDataHash { get; set; }
        public List<string> senderSignature { get; set; }
        public decimal minedInBlockIndex { get; set; }
        public bool transferSuccessful { get; set; }
    }
}
