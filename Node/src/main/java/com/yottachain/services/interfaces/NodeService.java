package com.yottachain.services.interfaces;

import com.yottachain.entities.Block;
import com.yottachain.models.bindingModels.TransactionBindingModel;
import com.yottachain.models.viewModels.*;

import java.util.List;

public interface NodeService {

    NodeInfoViewModel getInfo();
    ResetChainViewModel resetChain();
    List<BlockViewModel> getAllBlocks();
    BlockViewModel getBlock(int blockIndex) throws Exception;
    Block generateGenesisBlock();
    List<TransactionViewModel> getTransactions(boolean isConfirmed);
    TransactionViewModel getTransactionByHash(String transactionHash);
    List<BalanceViewModel> getBalances();
    List<TransactionViewModel> getTransactionsByAddress(String address);
    TransactionCreatedViewModel addTransaction(TransactionBindingModel model) throws Exception;
}