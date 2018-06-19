package com.yottachain.services.interfaces;

import com.yottachain.entities.Block;
import com.yottachain.models.viewModels.*;

import java.util.List;

public interface NodeService {

    NodeInfoViewModel getInfo();
    ResetChainViewModel resetChain();
    List<BlockViewModel> getAllBlocks();
    BlockViewModel getBlock(int blockIndex) throws Exception;
    Block generateGenesisBlock();
    List<TransactionViewModel> getPendingTransactions();
    TransactionViewModel getTransactionInfo(String transactionHash);
    TransactionCreatedViewModel addTransaction(TransactionViewModel transaction);
}