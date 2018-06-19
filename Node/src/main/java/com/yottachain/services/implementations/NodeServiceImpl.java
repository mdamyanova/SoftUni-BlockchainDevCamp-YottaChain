package com.yottachain.services.implementations;

import com.yottachain.entities.Address;
import com.yottachain.entities.Block;
import com.yottachain.entities.Transaction;
import com.yottachain.models.viewModels.*;
import com.yottachain.services.interfaces.NodeService;
import com.yottachain.services.interfaces.TransactionService;
import org.bouncycastle.util.encoders.Hex;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import java.lang.reflect.Type;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class NodeServiceImpl implements NodeService {

    private ModelMapper mapper;
    private TransactionService transactionService;
    private ConcurrentHashMap<String, Transaction> pendingTransactionsById;
    private final List<Block> blockchain;

    public NodeServiceImpl() {
        this.mapper = new ModelMapper();
        this.transactionService = new TransactionServiceImpl();
        this.pendingTransactionsById = new ConcurrentHashMap<>();
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

        Block block = blockchain.get(blockIndex);

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
        Address minedBy = new Address("00");
        genesis.setIndex(0);
        genesis.setTransactions(transactions);
        genesis.setDifficulty(1);
        genesis.setPreviousBlockHash("NONE");
        genesis.setMinedBy(minedBy);
        genesis.setBlockDataHash(Hex.toHexString("TODO".getBytes()));
        genesis.setNonce(123456789L);
        genesis.setCreatedOn(0L);
        genesis.setBlockDataHash(Hex.toHexString("TODO".getBytes()));
        return genesis;
    }

    @Override
    public List<TransactionViewModel> getPendingTransactions() {
        // TODO
        List<TransactionViewModel> model = new ArrayList<>();
        for (Map.Entry<String, Transaction> transaction : pendingTransactionsById.entrySet()) {
            if (!transaction.getValue().isConfirmed()) {
                model.add(mapper.map(transaction.getValue(), TransactionViewModel.class));
            }
        }
        return model;
    }

    @Override
    public TransactionViewModel getTransactionInfo(String transactionHash) {
        return null;
    }

    @Override
    public TransactionCreatedViewModel addTransaction(TransactionViewModel transaction) {
        // TODO - Create transaction from the service
        // TODO - Validate transaction from the service
        // TODO - Throw exception or check - if non exist, add to blockchain
        // TODO - Return TransactionCreatedViewModel :))

        return null;
    }
}