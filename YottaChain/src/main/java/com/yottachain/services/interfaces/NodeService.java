package com.yottachain.services.interfaces;

import com.yottachain.models.viewModels.BlockViewModel;
import com.yottachain.models.viewModels.NodeInfoViewModel;
import com.yottachain.models.viewModels.TransactionCreatedViewModel;
import com.yottachain.models.viewModels.TransactionViewModel;

import java.util.List;

public interface NodeService {

    NodeInfoViewModel GetInfo();
    List<BlockViewModel> GetAllBlocks();
    BlockViewModel GetBlock(int blockIndex);
    TransactionViewModel GetTransactionInfo(String transactionHash);
    TransactionCreatedViewModel AddTransaction(TransactionViewModel transaction);
}