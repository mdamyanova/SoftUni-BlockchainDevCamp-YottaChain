package com.yottachain.services.interfaces;

import com.yottachain.entities.Block;
import com.yottachain.models.viewModels.BlockViewModel;
import com.yottachain.models.viewModels.NodeInfoViewModel;
import com.yottachain.models.viewModels.TransactionCreatedViewModel;
import com.yottachain.models.viewModels.TransactionViewModel;

import java.util.List;

public interface NodeService {

    NodeInfoViewModel getInfo();
    List<BlockViewModel> getAllBlocks();
    BlockViewModel getBlock(int blockIndex) throws Exception;
    Block generateGenesisBlock();
    TransactionViewModel getTransactionInfo(String transactionHash);
    TransactionCreatedViewModel addTransaction(TransactionViewModel transaction);
}