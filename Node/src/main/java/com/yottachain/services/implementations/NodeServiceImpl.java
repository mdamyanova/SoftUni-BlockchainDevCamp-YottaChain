package com.yottachain.services.implementations;

import com.yottachain.entities.Address;
import com.yottachain.entities.Block;
import com.yottachain.entities.Transaction;
import com.yottachain.models.bindingModels.TransactionBindingModel;
import com.yottachain.models.viewModels.*;
import com.yottachain.services.interfaces.NodeService;
import com.yottachain.services.interfaces.TransactionService;
import com.yottachain.utils.Helpers;
import org.bouncycastle.util.encoders.Hex;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import java.lang.reflect.Type;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class NodeServiceImpl implements NodeService {

    private ModelMapper mapper;
    private TransactionService transactionService;
    private ConcurrentHashMap<String, Transaction> pendingTransactionsById;
    private ConcurrentHashMap<String, Transaction> confirmedTransactionsById;
    private List<Address> addresses; // TODO
    private final List<Block> blockchain;

    public NodeServiceImpl() {
        this.mapper = new ModelMapper();
        this.transactionService = new TransactionServiceImpl();
        this.pendingTransactionsById = new ConcurrentHashMap<>();
        this.confirmedTransactionsById = new ConcurrentHashMap<>();
        this.addresses = new ArrayList<>();
        this.blockchain = new ArrayList<>();
        this.blockchain.add(generateGenesisBlock());
    }

    @Override
    public NodeInfoViewModel getInfo() {
        // TODO
        NodeInfoViewModel model = new NodeInfoViewModel();
        model.setNodeId(0);
        model.setHost("localhost");
        model.setPort(8080);
        model.setUrl("http://test.test");
        model.setPeers(new ArrayList<>());
        model.setChain(new ArrayList<>());
        model.setChainId(0);
        return model;
    }

    @Override
    public ResetChainViewModel resetChain() {
        this.blockchain.clear();
        this.blockchain.add(generateGenesisBlock());
        ResetChainViewModel model = new ResetChainViewModel();
        model.setMessage("The chain was reset to its genesis block");
        return model;
    }

    @Override
    public List<BlockViewModel> getAllBlocks() {
        Type targetListType = new TypeToken<List<BlockViewModel>>() {}.getType();
        return blockchain.size() > 0 ?
                mapper.map(blockchain, targetListType) : Collections.emptyList();
    }

    @Override
    public BlockViewModel getBlock(int blockIndex) throws Exception {
        if (blockIndex < 0 || blockIndex > blockchain.size()) {
            throw new Exception("No such block"); // TODO - Make custom exception for Block not found
        }
        Block block = blockchain.stream().filter(x -> x.getIndex() == blockIndex).findFirst().orElse(null);
        if (block == null) {
          throw new Exception("");
        }
        return mapper.map(block, BlockViewModel.class);
    }

    @Override
    public Block generateGenesisBlock() {
       // if (blockchain.size() != 0) - careful here, TODO

        Block genesis = new Block();
        List<Transaction> transactions = new ArrayList<>();
        Address address = new Address();
        address.setAddressId("00");

        genesis.setIndex(0);
        genesis.setTransactions(transactions);
        genesis.setDifficulty(0);
        genesis.setPreviousBlockHash("NONE");
        genesis.setMinedBy(address);
        genesis.setBlockDataHash(Hex.toHexString("TODO".getBytes()));
        genesis.setNonce(0);
        genesis.setCreatedOn(0L);
        genesis.setBlockDataHash(Hex.toHexString("TODO".getBytes()));
        return genesis;
    }

    @Override
    public List<TransactionViewModel> getTransactions(boolean isConfirmed) {
        List<TransactionViewModel> model = new ArrayList<>();
        for (Map.Entry<String, Transaction> transaction : pendingTransactionsById.entrySet()) {
            if (isConfirmed == transaction.getValue().isConfirmed()) {
                model.add(mapper.map(transaction.getValue(), TransactionViewModel.class));
            }
        }
        return model;
    }

    @Override
    public TransactionViewModel getTransactionByHash(String transactionHash) {
        // TODO - this get() ?
        Transaction transaction = pendingTransactionsById.entrySet().stream()
                .filter(x -> x.getValue().getTransactionHash().equals(transactionHash))
                .findFirst().get().getValue();

        if (transaction == null) {
            transaction = confirmedTransactionsById.entrySet().stream()
                    .filter(x -> x.getValue().getTransactionHash().equals(transactionHash))
                    .findFirst().get().getValue();
        }
        return mapper.map(transaction, TransactionViewModel.class);
    }

    @Override
    public List<BalanceViewModel> getBalances() {
        Type targetListType = new TypeToken<List<BalanceViewModel>>() {}.getType();
        return mapper.map(addresses, targetListType);
    }

    @Override
    public BalanceForAddressViewModel getBalanceForAddress(String address) {
        // for address with no transactions -> return zeros
        // for invalid address -> 404 Invalid address
        return null; // TODO
    }

    @Override
    public List<TransactionViewModel> getTransactionsByAddress(String address) {
        List<TransactionViewModel> model = new ArrayList<>();
        return null; //TODO
    }

    @Override
    public TransactionCreatedViewModel addTransaction(TransactionBindingModel model) throws Exception {
        Transaction transaction = transactionService.create(model);

        // TODO add address to addresses !!! - hashmap?

        String isValidMessage = transactionService.validate(transaction);

        if (!isValidMessage.equals("Valid transaction")) {
            throw new Exception(isValidMessage); // TODO - Custom exception
        }

        if (confirmedTransactionsById.containsKey(transaction.getTransactionHash())) {
            throw new Exception("Transaction is already confirmed"); // TODO - Custom exception
        }

        if (!pendingTransactionsById.containsKey(transaction.getTransactionHash())) {
            pendingTransactionsById.put(transaction.getTransactionHash(), transaction);
        }

        TransactionCreatedViewModel createdTrModel = new TransactionCreatedViewModel();
        createdTrModel.setCreatedOn(Helpers.toISO8601UTC(ZonedDateTime.now()));
        createdTrModel.setTransactionHash(transaction.getTransactionHash());
        return createdTrModel;
    }
}