package com.yottachain.services.implementations;

import com.yottachain.models.viewModels.BlockViewModel;
import com.yottachain.models.viewModels.NodeInfoViewModel;
import com.yottachain.models.viewModels.TransactionCreatedViewModel;
import com.yottachain.models.viewModels.TransactionViewModel;
import com.yottachain.services.interfaces.NodeService;

import java.util.List;

public class NodeServiceImpl implements NodeService {

    @Override
    public NodeInfoViewModel GetInfo() {
        return null;
    }

    @Override
    public List<BlockViewModel> GetAllBlocks() {
        return null;
    }

    @Override
    public BlockViewModel GetBlock(int blockIndex) {
        return null;
    }

    @Override
    public TransactionViewModel GetTransactionInfo(String transactionHash) {
        return null;
    }

    @Override
    public TransactionCreatedViewModel AddTransaction(TransactionViewModel transaction) {
        return null;
    }
}