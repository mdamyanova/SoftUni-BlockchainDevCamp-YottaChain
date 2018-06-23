using Newtonsoft.Json;
using System;
using System.Collections.Concurrent;
using System.Collections.Generic;
using System.Linq;
using System.Net.Http;
using System.Threading.Tasks;
using static System.Console;

namespace BlockExplorer
{
    class Program
    {
        private static readonly HttpClient client = new HttpClient(); ///Http Client

        static int startCounter = 0; //Shows how many times was the Main Method Called

        static void Main() => MainAsync();

        static async void MainAsync()
        {
            startCounter++;

            string input = string.Empty;

            string[] availableOperations = new string[]
            {
                "blocks", "confirmedtrans", "pendingtrans", "balances", "peers", "info" //Allowed functionality
            };

            if (startCounter == 1) //Prevent Showing unnecessary things
            {
                Initialize();
                About();
            }

            while (input.ToLower() != "exit")
            {
                Print("");
                input = ReadLine().TrimEnd(' ');
                if (input!=""&&input.ToLower()!="exit" && !availableOperations.Contains(input.ToLower())) PrintLine($"\"{input}\" is not recognized as a command");

                switch (input.ToLower()) //Main Functionality
                {
                    case "info":
                        Info();
                        break;
                    case "confirmedtrans":
                        var confTask = ConfirmedTrans();
                        confTask.Wait(); //Waits for The Task to Complete
                        break;
                    case "pendingtrans":
                        var pendTask = PendingTrans();
                        pendTask.Wait(); //Waits for The Task to Complete
                        break;
                    case "balances":
                        var balancesTask = Balances();
                        balancesTask.Wait(); //Waits for The Task to Complete
                        break;
                    case "blocks":
                        var blocksTask = Blocks();
                        blocksTask.Wait(); //Waits for The Task to Complete
                        break;
                    case "peers":
                        var peersTask = Peers();
                        peersTask.Wait(); //Waits for The Task to Complete
                        break;
                }
            }
            Exit();
        }

        static void Info() ///Shows Info about the Program Functionality
        {
            WriteLine("#####################################################");
            WriteLine("#####################################################");
            WriteLine("###                     INFO                      ###");
            WriteLine("###       Blocks -> Gets Blockchain Blocks        ###");
            WriteLine("### --------------------------------------------- ###");
            WriteLine("### ConfirmedTrans -> Gets Confirmed Transactions ###");
            WriteLine("### --------------------------------------------- ###");
            WriteLine("###   PendingTrans -> Gets Pending Transactions   ###");
            WriteLine("### --------------------------------------------- ###");
            WriteLine("###         Balances -> Gets All Balances         ###");
            WriteLine("### --------------------------------------------- ###");
            WriteLine("###            Peers -> Gets Peer Map             ###");
            WriteLine("###                                               ###");
            WriteLine("#####################################################");
            WriteLine("#####################################################");
        }

        static void Initialize() ///Initialization of the program
        {
            Title = "YottaExplorer v.1";
            BackgroundColor = ConsoleColor.White;
            Clear();
            ForegroundColor = ConsoleColor.Black;
        }

        static void About() ///Describes what the app does
        {
            /*  __   __   ___    _____   _____      _      _____  __  __  ____    _        ___    ____    _____   ____  
                \ \ / /  / _ \  |_   _| |_   _|    / \    | ____| \ \/ / |  _ \  | |      / _ \  |  _ \  | ____| |  _ \ 
                 \ V /  | | | |   | |     | |     / _ \   |  _|    \  /  | |_) | | |     | | | | | |_) | |  _|   | |_) |
                  | |   | |_| |   | |     | |    / ___ \  | |___   /  \  |  __/  | |___  | |_| | |  _ <  | |___  |  _ < 
                  |_|    \___/    |_|     |_|   /_/   \_\ |_____| /_/\_\ |_|     |_____|  \___/  |_| \_\ |_____| |_| \_\ */
            
            WriteLine("__   __   ___    _____   _____      _      _____  __  __  ____    _        ___    ____    _____   ____  ");
            WriteLine("\\ \\ / /  / _ \\  |_   _| |_   _|    / \\    | ____| \\ \\/ / |  _ \\  | |      / _ \\  |  _ \\  | ____| |  _ \\");
            WriteLine(" \\ V /  | | | |   | |     | |     / _ \\   |  _|    \\  /  | |_) | | |     | | | | | |_) | |  _|   | |_) |");
            WriteLine("  | |   | |_| |   | |     | |    / ___ \\  | |___   /  \\  |  __/  | |___  | |_| | |  _ <  | |___  |  _ <");
            WriteLine("  |_|    \\___/    |_|     |_|   /_/   \\_\\ |_____| /_/\\_\\ |_|     |_____|  \\___/  |_| \\_\\ |_____| |_| \\_\\");
            WriteLine("YOTTAEXPLORER v.1");
            WriteLine("Part of the YottaChain Blockchain Project");
            WriteLine($"({DateTime.Now.Year}) All rights reserved.");
            WriteLine();
            WriteLine("Available Operations:");
            WriteLine("[ \"Blocks\", \"ConfirmedTrans\", \"PendingTrans\", \"Balances\", \"Peers\", \"Info\", \"Exit\" ]");
            WriteLine();
        }

        static void Print(string str) ///Prints the string with ">>>" infront
        {
            Write($">>> {str}");
        }

        static void PrintLine(string str) ///Prints a line with the string and ">>>" infront
        {
            WriteLine($">>> {str}");
        }

        static async Task ConfirmedTrans() ///Gets the Confirmed Transactions
        {
            try
            {
                string responseString = await client.GetStringAsync($"http://localhost:8080/transactions/confirmed");
                List<Transaction> transactions = JsonConvert.DeserializeObject<List<Transaction>>(responseString);
                if (transactions.Count > 0)
                {
                    foreach (var transaction in transactions)
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
                    PrintLine($"No Confirmed Transactions found!");
                }
            }
            catch
            {
                PrintLine("ERROR! COULD NOT GET TRANSACTIONS!");
            }
        }

        static async Task PendingTrans() ///Gets the Pending Transactions
        {
            try
            {
                string responseString = await client.GetStringAsync($"http://localhost:8080/transactions/pending");
                List<PendingTransaction> transactions = JsonConvert.DeserializeObject<List<PendingTransaction>>(responseString);
                if (transactions.Count > 0)
                {
                    foreach (var transaction in transactions)
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
                        WriteLine("-------------------------------------------");
                        WriteLine();
                    }
                }
                else
                {
                    PrintLine($"No Pending Transactions found!");
                }
            }
            catch
            {
                PrintLine("ERROR! COULD NOT GET TRANSACTIONS!");
            }

        }

        static async Task Balances() ///Gets the Balances
        {
            string responseString = await client.GetStringAsync("http://localhost:8080/balances");
            List<Balance> addressBalances = JsonConvert.DeserializeObject<List<Balance>>(responseString);
            GetBalances(addressBalances);
        }

        static void GetBalances(List<Balance> balances)
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
                            PrintLine($"\"{bal.Key}\" : \"{bal.Value}\"");
                        };
                    }
                    WriteLine("    --------------------------------------------");
                }
                else PrintLine("No Balances Found!");
            }
            catch
            {
                PrintLine("ERROR! COULD NOT GET BALANCES");
            }
        }

        static async Task Peers() ///Gets the Peers Map
        {
            string responseString = await client.GetStringAsync("http://localhost:8080/peers");
            ConcurrentDictionary<string, string> peersMap = JsonConvert.DeserializeObject<ConcurrentDictionary<string, string>>(responseString);
            GetPeers(new Peers(peersMap));
        }

        static void GetPeers(Peers peersMap)
        {
            try
            {
                WriteLine("    ------------------BALANCES------------------");
                foreach (var peer in peersMap.PeersMap.OrderBy(x => x.Value))
                {
                    PrintLine($"\"{peer.Key}\" : \"{peer.Value}\"");
                }
                WriteLine("    --------------------------------------------");
            }
            catch
            {
                PrintLine("ERROR! COULD NOT GET PEERS");
            }
        }

        static async Task Blocks() ///Gets the Blocks
        {
            try
            {
                string responseString = await client.GetStringAsync($"http://localhost:8080/blocks");
                List<Block> blocks = JsonConvert.DeserializeObject<List<Block>>(responseString);
                if (blocks.Count > 0)
                {
                    foreach (var block in blocks)
                    {
                        WriteLine("------------------ Block ------------------");
                        WriteLine($"\"Index\":{block.index},");
                        WriteLine("\"Transactions\": [");
                        WriteLine($"{string.Join(",\n", block.transactions)}");
                        WriteLine("],");
                        WriteLine($"\"Difficulty\":{block.difficulty},");
                        WriteLine($"\"MinedBy\":{block.minedBy},");
                        WriteLine($"\"BlockDataHash\":{block.blockDataHash},");
                        WriteLine($"\"Nonce\":{block.nonce},");
                        WriteLine($"\"DateCreated\":{block.dateCreated},");
                        WriteLine($"\"BlockHash\":{block.blockHash},");
                        WriteLine("-------------------------------------------");
                        WriteLine();
                    }
                }
                else
                {
                    PrintLine($"No Blocks found!");
                }
            }
            catch
            {
                PrintLine("ERROR! COULD NOT GET BLOCKS!");
            }
        }

        static void Exit() ///Exit Check
        {
            Print("Do you really want to exit YottaExplorer? [Y/N]: ");
            string ans = ReadLine();

            if (ans.ToLower() != "y")
            {
                if (ans.ToLower() == "n")
                {
                    MainAsync();
                }

                else Exit();
            }
        }
    }
}
