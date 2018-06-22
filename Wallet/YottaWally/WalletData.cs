using System;
using System.Collections.Generic;
using System.Text;
using System.Security.Cryptography;

namespace YottaWally
{
    class WalletData
    {
        public WalletData() { } ///Blank Constructor

        public WalletData(string Name, string Password, List<string> PrivKeys, List<string> PubKeys, List<string> PubKeysCompressed, List<string> Addresses) ///Constructor for WalletData
        {
            this.Name = Name;
            PasswordHash = GetPasswordHash(Password);
            PrivateKeys = PrivKeys;
            PublicKeys = PubKeys;
            this.PubKeysCompressed = PubKeysCompressed;
            this.Addresses = Addresses;
            CreationTime = $"{DateTime.UtcNow.Year}/{DateTime.UtcNow.Month}/{DateTime.UtcNow.Day} {DateTime.UtcNow.Hour.ToString("00")}:{DateTime.UtcNow.Minute.ToString("00")}";
        }

        public string Name { get; set; }
        public string PasswordHash { get; set; }
        public List<string> PrivateKeys { get; set; }
        public List<string> PublicKeys { get; set; }
        public List<string> PubKeysCompressed { get; set; }
        public List<string> Addresses { get; set; }
        public string CreationTime { get; set; }

        private string GetPasswordHash(string password) ///Gets SHA-256 Hash of the given Password
        {
            SHA256 SHA256Hash = SHA256Managed.Create();

            byte[] bytes = Encoding.UTF8.GetBytes(password);
            byte[] encrypted = SHA256Hash.ComputeHash(bytes);

            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < encrypted.Length; i++)
            {
                sb.Append(encrypted[i].ToString("X2"));
            }

            return sb.ToString().ToLower();
        }
    }
}
