using System;
using System.Linq;
using static System.Console;
using System.Security.Cryptography;
using System.Globalization;
using System.Text;
using Cryptography.ECDSA;
using System.Collections.Generic;
using Newtonsoft.Json;
using ICSharpCode.SharpZipLib.Core;
using ICSharpCode.SharpZipLib.Zip;
using System.IO;
using System.Net.Http;

namespace YottaWally
{
    class Program
    {
        private static RNGCryptoServiceProvider rngCsp = new RNGCryptoServiceProvider(); ///Crypto Service Provider
        private static Secp256K1Manager secp256 = new Secp256K1Manager(); ///Secp256k1 Manager
        private static readonly HttpClient client = new HttpClient(); ///Http Client

        static string openedWalletName = string.Empty; //The Name of the Wallet you have Opened
        static int startCounter = 0; //Shows how many times was the Main Method Called
        static bool? loadWallet = null; //Indicates Whether a Wallet is Loaded

        static void Main()
        {
            Encoding.RegisterProvider(CodePagesEncodingProvider.Instance); //Encoding

            startCounter++;
            string input = string.Empty;

            string[] availableOperations = new string[]
            {
                "create", "mywallets", "load", "recover", "exit" //Allowed functionality
            };

            string[] availableWalletOperations = new string[]
            {
                "balance", "history", "send", "exit" //Allowed functionality in Wallet
            };

            if (loadWallet == true) goto outOfWhile; //If a wallet is loaded go to Wallet Options

            if (startCounter == 1) //Prevent Showing unnecessary things
            {
                Initialize();
                About(availableOperations);
            }

            while (input.ToLower() != "exit")
            {
                Write(">>> ");
                input = ReadLine();

                if (!availableOperations.Contains(input.ToLower()) && input != "")
                {
                    WriteLine($"\"{input}\" is not recognized as a command");
                }

                switch (input.ToLower()) //Main Functionlaity Logic
                {
                    case "create":
                        if (!CreateWallet()) Print("Try to create a Wallet again!"); //Prompts to create new wallet if the wallet is not Created
                        break;

                    case "mywallets":
                        GetAllWallets();
                        break;

                    case "load":
                        loadWallet = LoadWallet();
                        if (loadWallet == false) Print("Try to load a Wallet again!");
                        else goto outOfWhile;
                        break;

                    case "recover":
                        if (!RecoverWallet()) Print("Try to recover the Wallet again!"); //Prompts to recover wallet if the wallet was not Recovered
                        break;
                }
            }
            outOfWhile:

            if (input.ToLower() == "exit")
            {
                Exit();
            }
            if (loadWallet == true)
            {
                Print($"You are in wallet {openedWalletName}.");
                
                while (true)
                {
                    Write(">>> [\"Balance\", \"Send\", \"Exit\"]: ");
                    input = ReadLine();

                    if (input.ToLower() == "exit")
                    {
                        Exit();
                        if (loadWallet == false) return;
                    }

                    if (!availableWalletOperations.Contains(input.ToLower()) && input != "")
                    {
                        WriteLine($"\"{input}\" is not recognized as a command");
                    }

                    switch (input) //In-wallet Main Logic
                    {
                        case "balance":
                            GetBalance();
                            break;
                        case "history":
                            break;
                        case "send": 
                            break;
                    }
                }
            }
        }

        static void Initialize() ///Initialization of the program
        {
            Title = "YottaWally v.1";
        }

        static void About(string[] availableOperations) ///Describes what the app does
        {
            WriteLine("YOTTAWALLY v.1");
            WriteLine("Part of the YottaChain Blockchain Project");
            WriteLine($"({DateTime.Now.Year}) All rights reserved.");
            WriteLine();
            WriteLine("Available Operations:");
            WriteLine("[ \"Create\", \"MyWallets\", \"Load\", \"Recover\", \"Exit\" ]");
            WriteLine();
        }

        static void Print(string str) ///Prints the string with ">>>" infront
        {
            WriteLine($">>> {str}");
        }

        private static string GetHiddenConsoleInput() ///Hide input while typing
        {
            StringBuilder input = new StringBuilder();
            while (true)
            {
                var key = Console.ReadKey(true);
                if (key.Key == ConsoleKey.Enter) break;
                if (key.Key == ConsoleKey.Backspace && input.Length > 0) input.Remove(input.Length - 1, 1);
                else if (key.Key != ConsoleKey.Backspace) input.Append(key.KeyChar);
            }
            return input.ToString();
        }

        static Byte[] GenerateRandomBytes() ///Generates 32 Random Bytes
        {
            Byte[] randomBytes = new Byte[32];
            rngCsp.GetBytes(randomBytes);
            return randomBytes;
        }

        static string GeneratePrivateKey(Byte[] randomBytes) ///Generates a Private Key String from 32 Random Bytes
        {
            return BitConverter.ToString(randomBytes).Replace("-", "");
        }

        static string GetPublicKeyCompressed(string publicKey) ///Compresses the Public key to 65 Hex Digits
        {
            string y = string.Join("", publicKey.Skip(64));
            char lastChar = DetermineLastChar(y);

            return string.Join("", publicKey.Take(64)) + lastChar;
        }

        static char DetermineLastChar(string y) ///Determines the Last Char of the Compressed PB Key
        {
            string lastDig = string.Join("", y.Skip(63));

            if (int.Parse(lastDig, NumberStyles.HexNumber) % 2 == 0)
            {
                return '0';
            }
            return '1';
        }

        static bool CreateWallet() ///Creates Account with its Private Keys, Public Keys and Addresses
        {
            Write(">>> Type Wallet Name: ");
            string walletName = ReadLine();
            if (Directory.Exists($@"Wallets/{walletName}"))
            {
                Print($"ERROR! WALLET \"{walletName.ToUpper()}\" ALREADY EXISTS!");
                return false;
            }
            Write(">>> Type Wallet Password ");
            string password = GetHiddenConsoleInput();
            WriteLine();
            Write(">>> Password Confirmation ");
            string pwConfirm = GetHiddenConsoleInput();
            WriteLine();
            if (password == pwConfirm)
            {
                try
                {
                    List<byte[]> privateKeys = new List<byte[]>(); //List with all Private Keys in Byte Type
                    List<byte[]> publicKeys = new List<byte[]>(); //List with all Public Keys in Byte Type

                    List<string> privateKeysString = new List<string>(); //List with all Private Keys
                    List<string> publicKeysString = new List<string>(); //List with all Public Keys
                    List<string> pubKeysCompressed = new List<string>(); //List of Compressed Public Keys
                    List<string> addresses = new List<string>(); //List of Addresses extracted from Compressed Public Keys

                    try
                    {
                        for (int i = 0; i < 5; i++)
                        {
                            Byte[] privKey = GenerateRandomBytes();
                            privateKeys.Add(privKey);
                        }
                    }
                    catch
                    {
                        throw new ArgumentException("COULD NOT GENERATE PRIVATE KEYS!");
                    }

                    try
                    {
                        for (int i = 0; i < 5; i++)
                        {
                            Byte[] pubKey = Secp256K1Manager.GetPublicKey(privateKeys[i], false);
                            publicKeys.Add(pubKey);
                        }
                    }
                    catch
                    {
                        throw new ArgumentException("COULD NOT RECOVER PUBLIC KEYS!");
                    }

                    for (int i = 0; i < 5; i++)
                    {
                        string privKeyString = GeneratePrivateKey(privateKeys[i]).ToLower();
                        string pubKeyString = string.Join("", BitConverter.ToString(publicKeys[i]).Replace("-", "").Skip(2)).ToLower();
                        privateKeysString.Add(privKeyString);
                        publicKeysString.Add(pubKeyString);
                    }

                    try
                    {
                        for (int i = 0; i < 5; i++)
                        {
                            string pubKeyCompressed = GetPublicKeyCompressed(publicKeysString[i]).ToLower();
                            string address = RipeMDHash(pubKeyCompressed);
                            pubKeysCompressed.Add(pubKeyCompressed);
                            addresses.Add(address);
                        }
                    }
                    catch
                    {
                        throw new ArgumentException("COULD NOT COMPRESS PUBLIC KEYS!");
                    }
                    WriteLine("--------------- WALLET INFO ---------------");
                    WriteLine();
                    WriteLine("--------------- Private Keys --------------\n" + string.Join("\n", privateKeysString));
                    WriteLine("--------------- Public Keys ---------------\n" + string.Join("\n", publicKeysString));
                    WriteLine("---------- PublicKeys Compressed ----------\n" + string.Join("\n", pubKeysCompressed));
                    WriteLine("---------------- Addresses ----------------\n" + string.Join("\n", addresses));
                    WriteLine();
                    WriteLine("Write down and keep in SECURE place your private keys. Only through them you can access your coins!");
                    WriteLine();
                    Write(">>> Do you want to Save your Wallet to a JSON file? [Y/N]: ");
                    bool saveWallet = ReadLine().ToLower() == "y";
                    if (saveWallet)
                    {
                        SaveToJSON(walletName, password, privateKeysString, publicKeysString, pubKeysCompressed, addresses);
                    }
                }
                catch (Exception e)
                {
                    Print($"ERROR! {e.Message}");
                    return false;
                }
                openedWalletName = walletName;
                return true;
            }
            else
            {
                Print($"ERROR! PASSWORDS DO NOT MATCH!");
                return false;
            }
        }

        static bool LoadWallet() ///Loads an existing wallet
        {
            WalletData walletData = new WalletData();
            Write(">>> Place .json file in \"Wallets/\" with the name of the Wallet on it!");
            ReadKey();
            WriteLine();
            Write(">>> Enter Wallet Name: ");
            string walletName = ReadLine();
            if (!File.Exists($@"Wallets/{walletName}.json"))
            {
                Print($"ERROR! THERE IS NO WALLET NAMED \"{walletName.ToUpper()}\"!");
                return false;
            }
            Write($">>> Enter Wallet \"{walletName}\"'s Password: ");
            string password = GetHiddenConsoleInput();
            WriteLine();
            using (StreamReader r = new StreamReader($@"Wallets/{walletName}.json"))
            {
                string json = r.ReadToEnd();
                walletData = JsonConvert.DeserializeObject<WalletData>(json);
            }
            if (!(walletData.PasswordHash == GetPasswordHash(password)))
            {
                Print("ERROR! PASSWORD IS NOT CORRECT!");
                return false;
            }
            openedWalletName = walletName;
            return true;
        }

        static bool RecoverWallet() ///Recovers Wallet from 5 Private Keys
        {
            Write(">>> Type New Wallet Name: ");
            string walletName = ReadLine();
            if (Directory.Exists($@"Wallets/{walletName}"))
            {
                Print($"ERROR! WALLET \"{walletName.ToUpper()}\" ALREADY EXISTS!");
                return false;
            }
            Write(">>> Type New Wallet Password ");
            string password = GetHiddenConsoleInput();
            WriteLine();
            Write(">>> Password Confirmation ");
            string pwConfirm = GetHiddenConsoleInput();
            WriteLine();
            if (password == pwConfirm)
            {
                try
                {
                    List<byte[]> privateKeys = new List<byte[]>(); //List with all Private Keys in Byte Type
                    List<byte[]> publicKeys = new List<byte[]>(); //List with all Public Keys in Byte Type

                    List<string> privateKeysString = new List<string>(); //List with all Private Keys
                    List<string> publicKeysString = new List<string>(); //List with all Public Keys
                    List<string> pubKeysCompressed = new List<string>(); //List of Compressed Public Keys
                    List<string> addresses = new List<string>(); //List of Addresses extracted from Compressed Public Keys

                    try
                    {
                        Print("Write down your Private Keys: ");
                        for (int i = 0; i < 5; i++)
                        {
                            Write(">>> ");
                            privateKeys.Add(StringToByteArray(ReadLine()));
                        }
                    }
                    catch
                    {
                        throw new ArgumentException("COULD NOT GET PRIVATE KEYS!");
                    }

                    try
                    {
                        for (int i = 0; i < 5; i++)
                        {
                            Byte[] pubKey = Secp256K1Manager.GetPublicKey(privateKeys[i], false);
                            publicKeys.Add(pubKey);
                        }
                    }
                    catch
                    {
                        throw new ArgumentException("COULD NOT RECOVER PUBLIC KEYS!");
                    }

                    for (int i = 0; i < 5; i++)
                    {
                        string privKeyString = GeneratePrivateKey(privateKeys[i]).ToLower();
                        string pubKeyString = string.Join("", BitConverter.ToString(publicKeys[i]).Replace("-", "").Skip(2)).ToLower();
                        privateKeysString.Add(privKeyString);
                        publicKeysString.Add(pubKeyString);
                    }

                    try
                    {
                        for (int i = 0; i < 5; i++)
                        {
                            string pubKeyCompressed = GetPublicKeyCompressed(publicKeysString[i]).ToLower();
                            string address = RipeMDHash(pubKeyCompressed);
                            pubKeysCompressed.Add(pubKeyCompressed);
                            addresses.Add(address);
                        }
                    }
                    catch
                    {
                        throw new ArgumentException("COULD NOT COMPRESS PUBLIC KEYS!");
                    }
                    WriteLine("--------------- WALLET INFO ---------------");
                    WriteLine();
                    WriteLine("--------------- Private Keys --------------\n" + string.Join("\n", privateKeysString));
                    WriteLine("--------------- Public Keys ---------------\n" + string.Join("\n", publicKeysString));
                    WriteLine("---------- PublicKeys Compressed ----------\n" + string.Join("\n", pubKeysCompressed));
                    WriteLine("---------------- Addresses ----------------\n" + string.Join("\n", addresses));
                    WriteLine();
                    WriteLine("Write down and keep in SECURE place your private keys. Only through them you can access your coins!");
                    WriteLine();
                    Write(">>> Do you want to Save your Wallet to a JSON file? [Y/N]: ");
                    bool saveWallet = ReadLine().ToLower() == "y";
                    if (saveWallet)
                    {
                        SaveToJSON(walletName, password, privateKeysString, publicKeysString, pubKeysCompressed, addresses);
                    }
                }
                catch (Exception e)
                {
                    Print($"ERROR! {e.Message}");
                    return false;
                }
                openedWalletName = walletName;
                return true;
            }
            else
            {
                Print($"ERROR! PASSWORDS DO NOT MATCH!");
                return false;
            }
        }

        static bool SaveToJSON(string walletName, string password, List<string> privateKeys,List<string>publicKeys,List<string> pubKeysCompressed,List<string> addresses)
        {
            if (!Directory.Exists($@"Wallets/{walletName}"))
            {
                Directory.CreateDirectory($@"Wallets/{walletName}");
                WalletData walletData = new WalletData(walletName, password, privateKeys, publicKeys, pubKeysCompressed, addresses);
                string json = JsonConvert.SerializeObject(walletData, Formatting.Indented);
                File.WriteAllText($@"Wallets/{walletName}/walletData.json", json);
                CreateSample($@"Wallets/{walletName}.zip", password, $@"Wallets/{walletName}");
                Directory.Delete($@"Wallets/{walletName}", true);
                Print($"Wallet saved to Wallets/{walletName}.zip/walletData.json");
                Print("Extract with your Password.");
                return true;
            }
            Print($"ERROR! WALLET \"{walletName}\" ALREADY EXISTS!");
            return false;
        }
        
        static string RipeMDHash(string str) ///Gets the RIPEMD-160 Hash of given Byte Array
        {
            RIPEMD160 r160 = RIPEMD160.Create();

            byte[] bytes = Encoding.UTF8.GetBytes(str);
            byte[] encrypted = r160.ComputeHash(bytes);

            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < encrypted.Length; i++)
            {
                sb.Append(encrypted[i].ToString("X2"));
            }

            return sb.ToString().ToLower();
        }

        static byte[] StringToByteArray(string hex)
        {
            return Enumerable.Range(0, hex.Length)
                             .Where(x => x % 2 == 0)
                             .Select(x => Convert.ToByte(hex.Substring(x, 2), 16))
                             .ToArray();
        }

        private static string GetPasswordHash(string password) ///Gets SHA-256 Hash of the given Password
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

        public static void CreateSample(string outPathname, string password, string folderName) ///Creates a Encrypted ZIP form an existing Folder
        {

            FileStream fsOut = File.Create(outPathname);
            ZipOutputStream zipStream = new ZipOutputStream(fsOut);

            zipStream.SetLevel(3);

            zipStream.Password = password;
            int folderOffset = folderName.Length + (folderName.EndsWith("\\") ? 0 : 1);

            CompressFolder(folderName, zipStream, folderOffset);

            zipStream.IsStreamOwner = true;
            zipStream.Close();
        }

        private static void CompressFolder(string path, ZipOutputStream zipStream, int folderOffset) ///Compresses Folder
        {

            string[] files = Directory.GetFiles(path);

            foreach (string filename in files)
            {

                FileInfo fi = new FileInfo(filename);

                string entryName = filename.Substring(folderOffset);
                entryName = ZipEntry.CleanName(entryName);
                ZipEntry newEntry = new ZipEntry(entryName);
                newEntry.DateTime = fi.LastWriteTime;
                newEntry.Size = fi.Length;

                zipStream.PutNextEntry(newEntry);

                byte[] buffer = new byte[4096];
                using (FileStream streamReader = File.OpenRead(filename))
                {
                    StreamUtils.Copy(streamReader, zipStream, buffer);
                }
                zipStream.CloseEntry();
            }
            string[] folders = Directory.GetDirectories(path);
            foreach (string folder in folders)
            {
                CompressFolder(folder, zipStream, folderOffset);
            }
        }

        static void GetAllWallets() ///Prints All Wallets on this Computer
        {
            Directory.CreateDirectory($@"Wallets/");
            string[] allWallets = Directory.GetFiles(@"Wallets/", "*.zip");
            if (allWallets.Length > 0)
            {
                Print("Wallets on this computer:");
                WriteLine(">>> " + String.Join("\n>>> ", allWallets));
            }
            else
            {
                Print("No wallets found on this computer!");
            }
        }

        static async void GetBalance() ///Gets the Balance for 5 YottaWallet Addresses
        {
            try
            {
                List<string> addresses = new List<string>(); //List with all addresses in the Wallet
                string responseString = await client.GetStringAsync("localhost:8080/balances");
                Balances balances = JsonConvert.DeserializeObject<Balances>(responseString);
                WalletData walletData = new WalletData();

                if (!File.Exists($@"Wallets/{openedWalletName}.json"))
                {
                    Print("Write your 5 Addresses:");
                    for (int i = 0; i < 5; i++)
                    {
                        Write(">>> ");
                        addresses.Add(ReadLine());
                    }
                }

                else
                {
                    using (StreamReader r = new StreamReader($@"Wallets/{openedWalletName}.json"))
                    {
                        string json = r.ReadToEnd();
                        walletData = JsonConvert.DeserializeObject<WalletData>(json);
                        for (int i = 0; i < 5; i++)
                        {
                            addresses.Add(walletData.Addresses[i]);
                        }
                    }
                }
                for (int i = 0; i < 5; i++)
                {
                    try
                    {
                        Print($"\"{addresses[i]}\" : {balances.AddressBalances[addresses[i]]}");
                    }
                    catch
                    {
                        Print($"\"{addresses[i]}\" : 0");
                    }
                }
            }
            catch
            {
                Print("ERROR! COULD NOT GET BALANCES!");
            }
        }

        static void Exit() ///Exit Check
        {
            Write(">>> Do you really want to exit YottaWally? [Y/N]: ");
            string ans = ReadLine();

            if (ans.ToLower() != "y")
            {
                if (ans.ToLower() == "n")
                {
                    Main();
                }

                else Exit();
            }
            else if (ans.ToLower() == "y") loadWallet = false;
        }
    }
}
