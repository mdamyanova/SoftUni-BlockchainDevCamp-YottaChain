using System;
using System.Collections.Generic;
using System.Text;

namespace YottaWally
{
    class AddressBalance
    {
        ///A Class Managing Balance for an Address
        public decimal safeBalance { get; set; }
        public decimal confirmedBalance { get; set; }
        public decimal pendingBalance { get; set; }
    }
}
