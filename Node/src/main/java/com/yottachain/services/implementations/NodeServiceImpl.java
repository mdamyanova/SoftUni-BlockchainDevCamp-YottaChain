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
    private ConcurrentHashMap<String, Address> addresses; // Address Id -> AddressId and Amount
    private final List<Block> blockchain;

    public NodeServiceImpl() {
        this.mapper = new ModelMapper();
        this.transactionService = new TransactionServiceImpl();
        this.pendingTransactionsById = new ConcurrentHashMap<>();
        this.confirmedTransactionsById = new ConcurrentHashMap<>();
        this.addresses = new ConcurrentHashMap<>();
        this.blockchain = new ArrayList<>();
        this.blockchain.add(generateGenesisBlock());
    }

    @Override
    public NodeInfoViewModel getInfo() {
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
        return this.blockchain.size() > 0 ?
                this.mapper.map(this.blockchain, targetListType) : Collections.emptyList();
    }

    @Override
    public BlockViewModel getBlock(int blockIndex) throws Exception {
        if (blockIndex < 0 || blockIndex > this.blockchain.size()) {
            throw new Exception("No such block");
        }
        Block block = this.blockchain.stream().filter(x -> x.getIndex() == blockIndex)
                .findFirst().orElse(null);
        if (block == null) {
          throw new Exception("Some");
        }
        return this. mapper.map(block, BlockViewModel.class);
    }

    @Override
    public Block generateGenesisBlock() {
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
        for (Map.Entry<String, Transaction> transaction : this.pendingTransactionsById.entrySet()) {
            if (isConfirmed == transaction.getValue().isConfirmed()) {
                model.add(this.mapper.map(transaction.getValue(), TransactionViewModel.class));
            }
        }
        return model;
    }

    @Override
    public TransactionViewModel getTransactionByHash(String transactionHash) throws Exception {
        Transaction transaction = this.pendingTransactionsById.values().stream()
                .filter(x -> x.getTransactionHash().equals(transactionHash))
                .findFirst().orElse(null);
        if (transaction == null) {
            transaction = this.confirmedTransactionsById.values().stream()
                    .filter(x -> x.getTransactionHash().equals(transactionHash))
                    .findFirst().orElse(null);
        }
        if (transaction == null) {
            throw new Exception("Invalid hash");
        }
        return this.mapper.map(transaction, TransactionViewModel.class);
    }

    @Override
    public List<BalanceViewModel> getBalances() {
        Type targetListType = new TypeToken<List<BalanceViewModel>>() {}.getType();
        return this.mapper.map(this.addresses.values(), targetListType);
    }

    @Override
    public BalanceForAddressViewModel getBalanceForAddress(String address) {
        // for address with no transactions -> return zeros
        // for invalid address -> 404 Invalid address
        return null; // TODO
    }

    @Override
    public List<TransactionViewModel> getTransactionsByAddress(String address) throws Exception {
        List<TransactionViewModel> model = new ArrayList<>();
        if (!this.addresses.containsKey(address)) {
            throw new Exception("Address not found");
        }
        for (Map.Entry<String, Transaction> entry : this.confirmedTransactionsById.entrySet()) {
            if (entry.getValue().getFrom().getAddressId().equals(address) ||
                    entry.getValue().getTo().getAddressId().equals(address)) {
                model.add(this.mapper.map(entry.getValue(), TransactionViewModel.class));
            }
        }
        for (Map.Entry<String, Transaction> entry : this.pendingTransactionsById.entrySet()) {
            if (entry.getValue().getFrom().getAddressId().equals(address) ||
                    entry.getValue().getTo().getAddressId().equals(address)) {
                model.add(this.mapper.map(entry.getValue(), TransactionViewModel.class));
            }
        }
        return model;
    }

    @Override
    public TransactionCreatedViewModel addTransaction(TransactionBindingModel model) throws Exception {
        Transaction transaction = this.transactionService.create(model);

        // TODO add address to addresses !!! - hashmap?

        String isValidMessage = this.transactionService.validate(transaction);

        if (!isValidMessage.equals("Valid transaction")) {
            throw new Exception(isValidMessage);
        }
        if (this.confirmedTransactionsById.containsKey(transaction.getTransactionHash())) {
            throw new Exception("Transaction is already confirmed");
        }
        if (!this.pendingTransactionsById.containsKey(transaction.getTransactionHash())) {
            this.pendingTransactionsById.put(transaction.getTransactionHash(), transaction);
        }
        TransactionCreatedViewModel createdTrModel = new TransactionCreatedViewModel();
        createdTrModel.setCreatedOn(Helpers.toISO8601UTC(ZonedDateTime.now()));
        createdTrModel.setTransactionHash(transaction.getTransactionHash());
        return createdTrModel;
    }
}