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
using System.Collections.Concurrent;
using System.Threading.Tasks;
using Nethereum.Hex.HexConvertors.Extensions;
using Nethereum.Signer;
using System.Net;
using Org.BouncyCastle.Math;
using Org.BouncyCastle.Crypto.Signers;
using Org.BouncyCastle.Crypto.Parameters;
using Org.BouncyCastle.Crypto.Digests;
using Org.BouncyCastle.Asn1.Sec;
using Org.BouncyCastle.Asn1.X9;

namespace YottaWally
{
    class Program
    {
        static readonly X9ECParameters curve = SecNamedCurves.GetByName("secp256k1");
        private static RNGCryptoServiceProvider rngCsp = new RNGCryptoServiceProvider(); ///Crypto Service Provider
        private static Secp256K1Manager secp256 = new Secp256K1Manager(); ///Secp256k1 Manager
        private static readonly HttpClient client = new HttpClient(); ///Http Client
        static WalletData walletData = new WalletData(); /// Wallet Data

        static string openedWalletName = string.Empty; //The Name of the Wallet you have Opened
        static int startCounter = 0; //Shows how many times was the Main Method Called
        static bool? loadWallet = null; //Indicates Whether a Wallet is Loaded
        static void Main()
        {
            MainAsync();
        }
        static async void MainAsync() ///The Async Main Method
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
                About();
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
                        else
                        {
                            loadWallet = true;
                            goto outOfWhile;
                        }
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
                    Write(">>> [\"Balance\", \"Send\", \"History\", \"Exit\"]: ");
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
                            Write(">>> [\"Address\", \"All\"]: ");

                            string cmd = ReadLine();
                            if (cmd.ToLower() == "all")
                            {
                                var task = FillBalancesClass();
                                task.Wait(); //Waits for the task to Complete
                            }

                            else if (cmd.ToLower() == "address")
                            {
                                var task = GetBalanceByAddress();
                                task.Wait(); //Waits for the task to Complete
                            }

                            break;
                        case "history":
                            var historyTask = GetTransactionHistory();
                            historyTask.Wait(); //Waits for the task to Complete
                            break;
                        case "send":
                            var sendTask = Send();
                            sendTask.Wait(); //Waits for the task to Complete
                            break;
                    }
                }
            }
        }

        static void Initialize() ///Initialization of the program
        {
            Title = "YottaWally v.1";
            BackgroundColor = ConsoleColor.White;
            Clear();
            ForegroundColor = ConsoleColor.Black;
        }

        static void About() ///Describes what the app does
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
            if (File.Exists($@"Wallets/{walletName}.zip") || File.Exists($@"Wallets/{walletName}.json"))
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
                    else walletData = new WalletData(walletName, password, privateKeysString, publicKeysString, pubKeysCompressed, addresses);
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
            try
            {
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
            catch
            {
                Print("ERROR! COULD NOT LOAD WALLET!");
                return false;
            }
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
                        if (!SaveToJSON(walletName, password, privateKeysString, publicKeysString, pubKeysCompressed, addresses))
                        {
                            Print("ERROR! COULD NOT SAVE TO JSON FILE!");
                        }
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

        static bool SaveToJSON(string walletName, string password, List<string> privateKeys, List<string> publicKeys, List<string> pubKeysCompressed, List<string> addresses) ///Saves Wallet to an Encrypted JSON file in a ZIP Folder
        {
            if (!Directory.Exists($@"Wallets/{walletName}"))
            {
                Directory.CreateDirectory($@"Wallets/{walletName}");
                walletData = new WalletData(walletName, password, privateKeys, publicKeys, pubKeysCompressed, addresses);
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

        static async Task FillBalancesClass() ///Gets the Balances
        {
            string responseString = await client.GetStringAsync("http://localhost:8080/balances");
            List<Balances> addressBalances = JsonConvert.DeserializeObject<List<Balances>>(responseString);
            GetBalances(addressBalances);
        }

        static async Task GetBalanceByAddress() ///Gets the Balance from the Node By the Address 
        {
            Write(">>> Write your address: ");
            string address = ReadLine();
            try
            {
                string responseString = await client.GetStringAsync($"http://localhost:8080/address/{address}/balance");
                AddressBalance addressBalance = JsonConvert.DeserializeObject<AddressBalance>(responseString);

                Print($"Address: {address}");
                WriteLine("----------------------------------------");
                Print($"Safe Balance: {addressBalance.safeBalance}");
                Print($"Confirmed Balance: {addressBalance.confirmedBalance}");
                Print($"Pending Balance: {addressBalance.confirmedBalance}");
            }
            catch
            {
                Print("ERROR! COULD NOT GET BALANCE!");
            }
        }

        static async Task GetTransactionHistory() ///Getting All Transactions an Address did or is doing
        {
            Write(">>> Write down your address: ");
            string address = ReadLine();
            try
            {
                string responseString = await client.GetStringAsync($"http://localhost:8080/address/{address}/transactions");
                List<GetTransaction> transactionHistory = JsonConvert.DeserializeObject<List<GetTransaction>>(responseString);
                if (transactionHistory.Count > 0) {
                    foreach (var transaction in transactionHistory)
                    {
                        WriteLine("--------------- Transaction ---------------");
                        WriteLine($"\"From\":{transaction.from},");
                        WriteLine($"\"To\":{transaction.to},");
                        WriteLine($"\"Value\":{transaction.value},");
                        WriteLine($"\"Fee\":{transaction.fee},");
                        WriteLine($"\"Date Created\":{transaction.dateCreated},");
                        WriteLine($"\"Data\":{transaction.data},");
                        WriteLine($"\"Sender Public Key\":{transaction.senderPubKey},");
                        WriteLine($"\"Transaction Data Hash\":{transaction.transactionDataHash},");
                        WriteLine("\"SenderSignature\": [");
                        WriteLine($"{string.Join(",\n", transaction.senderSignature)}");
                        WriteLine("],");
                        WriteLine($"\"Mined In Block Index\":{transaction.minedInBlockIndex},");
                        WriteLine($"\"transferSuccessful\":{Convert.ToString(transaction.transferSuccessful)}");
                        WriteLine("-------------------------------------------");
                        WriteLine();
                    }
                }
                else
                {
                    Print($"No Transactions found for this address! ({address})");
                }
            }
            catch
            {
                Print("ERROR! COULD NOT GET HISTORY OF TRANSACTIONS!");
            }
        }

        static async Task Send()
        {
            try
            {
                if (walletData.Addresses.Count > 0)
                {
                    Print("From: ");
                    for (int i = 0; i < 5; i++)
                    {
                        Print($"[{i + 1}] {walletData.Addresses[i]}");
                    }

                    Write(">>> ");
                    int checker = int.Parse(ReadLine()) - 1; //Determines which Address is going to be used

                    string fromAddress = walletData.Addresses[checker];
                    string senderPubKey = walletData.PubKeysCompressed[checker];

                    Write(">>> To: ");
                    string toAddress = ReadLine();
                    Write(">>> Value: ");
                    long value = long.Parse(ReadLine());
                    Write(">>> Fee: ");
                    int fee = int.Parse(ReadLine());
                    Write(">>> Data: ");
                    string data = ReadLine();


                    string transactionJson = CreateAndSignTransaction(
                        recipientAddress: toAddress,
                        value: value, fee: fee, data: data, iso8601datetime: DateTime.UtcNow.ToString("yyyy-MM-ddTHH:mm:ss.fffZ"),
                        senderPrivKeyHex: walletData.PrivateKeys[checker]);

                    var httpWebRequest = (HttpWebRequest)WebRequest.Create("https://localhost:8080/transactions/send");
                    httpWebRequest.ContentType = "application/json";
                    httpWebRequest.Method = "POST";

                    using (var streamWriter = new StreamWriter(httpWebRequest.GetRequestStream()))
                    {
                        streamWriter.Write(transactionJson);
                        streamWriter.Flush();
                        streamWriter.Close();
                    }

                    var httpResponse = (HttpWebResponse)httpWebRequest.GetResponse();
                }

                else
                {
                    Print("ERROR! WALLET NOT LOADED!");
                }
            }
            catch
            {
                Print("ERROR! COULD NOT MAKE A TRANSACTION! - Maybe You do not have enough YottaCoins?");
            }
        }

        private static void ExistingPrivateKeyToAddress(string privKeyHex) ///Gets Private Key and Makes it into an Address
        {
            BigInteger privateKey = new BigInteger(privKeyHex, 16);
            Org.BouncyCastle.Math.EC.ECPoint pubKey = GetPublicKeyFromPrivateKey(privateKey);
            string pubKeyCompressed = EncodeECPointHexCompressed(pubKey);
            string addr = RipeMDHash(pubKeyCompressed);
        }

        private static BigInteger[] SignData(BigInteger privateKey, byte[] data) ///Signs Data with Private Key
        {
            ECDomainParameters ecSpec = new ECDomainParameters(curve.Curve, curve.G, curve.N, curve.H);
            ECPrivateKeyParameters keyParameters = new ECPrivateKeyParameters(privateKey, ecSpec);
            IDsaKCalculator kCalculator = new HMacDsaKCalculator(new Sha256Digest());
            ECDsaSigner signer = new ECDsaSigner(kCalculator);
            signer.Init(true, keyParameters);
            BigInteger[] signature = signer.GenerateSignature(data);
            return signature;
        }

        private static string CreateAndSignTransaction(string recipientAddress, long value,
            int fee, string data, string iso8601datetime, string senderPrivKeyHex) ///Creates and Signs Transaction
        {
            BigInteger privateKey = new BigInteger(senderPrivKeyHex, 16);
            Org.BouncyCastle.Math.EC.ECPoint pubKey = GetPublicKeyFromPrivateKey(privateKey);
            string senderPubKeyCompressed = EncodeECPointHexCompressed(pubKey);

            string senderAddress = RipeMDHash(senderPubKeyCompressed);

            var tran = new //Creates unsigned Transaction
            {
                from = senderAddress,
                to = recipientAddress,
                value,
                fee,
                dateCreated = iso8601datetime,
                data,
                senderPubKey = senderPubKeyCompressed,
            };
            string tranJson = JsonConvert.SerializeObject(tran);

            byte[] tranHash = CalcSHA256(tranJson);

            BigInteger[] tranSignature = SignData(privateKey, tranHash);

            var tranSigned = new //Signed Transaction
            {
                from = senderAddress,
                to = recipientAddress,
                senderPubKey = senderPubKeyCompressed,
                value,
                fee,
                data,
                dateCreated = iso8601datetime,
                senderSignature = new string[]
                {
                tranSignature[0].ToString(16),
                tranSignature[1].ToString(16)
                }
            };

            string signedTranJson = JsonConvert.SerializeObject(tranSigned, Formatting.Indented);
            Print("Transaction Hash: " + BytesToHex(tranHash));
            return signedTranJson;
        }

        private static byte[] CalcSHA256(string str) ///Calculates SHA256 of a given string
        {
            byte[] bytes = Encoding.UTF8.GetBytes(str);
            Sha256Digest digest = new Sha256Digest();
            digest.BlockUpdate(bytes, 0, bytes.Length);
            byte[] result = new byte[digest.GetDigestSize()];
            digest.DoFinal(result, 0);
            return result;
        }

        private static string BytesToHex(byte[] bytes) ///Gets a Byte Array and returns a HEX string
        {
            return string.Concat(bytes.Select(b => b.ToString("x2")));
        }

        private static Org.BouncyCastle.Math.EC.ECPoint GetPublicKeyFromPrivateKey(BigInteger privKey) ///Recovers Public Key from Private Key
        {
            Org.BouncyCastle.Math.EC.ECPoint pubKey = curve.G.Multiply(privKey).Normalize();
            return pubKey;
        }

        private static string EncodeECPointHexCompressed(Org.BouncyCastle.Math.EC.ECPoint point)
        {
            BigInteger x = point.XCoord.ToBigInteger();
            BigInteger y = point.YCoord.ToBigInteger();
            return x.ToString(16) + Convert.ToInt32(y.TestBit(0));
        }

        static byte[] StringToByteArray(string hex) ///Gets HEX string and returns it as a Byte Array
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

        private static void CreateSample(string outPathname, string password, string folderName) ///Creates a Encrypted ZIP form an existing Folder
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
            string[] zipWallets = Directory.GetFiles(@"Wallets/", "*.zip");
            string[] allWallets = zipWallets.Concat(Directory.GetFiles(@"Wallets/", "*.json")).ToArray();
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

        static void GetBalances(List<Balances> balances) ///Gets All Balances
        {
            try
            {
                if (balances.Count > 0)
                {
                    WriteLine("    ------------------BALANCES------------------");
                    foreach (var balance in balances)
                    {
                        foreach (var bal in balance.AddressBalances)
                        {
                            Print($"\"{bal.Key}\" : \"{bal.Value}\"");
                        };
                    }
                    WriteLine("    --------------------------------------------");
                }
                else Print("No Balances Found!");
            }
            catch
            {
                Print("ERROR! COULD NOT GET BALANCES");
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
                    MainAsync();
                }

                else Exit();
            }
            else if (ans.ToLower() == "y") loadWallet = false;
        }
    }
}
