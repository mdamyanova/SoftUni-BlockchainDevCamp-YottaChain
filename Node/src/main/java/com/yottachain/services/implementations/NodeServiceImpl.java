package com.yottachain.services.implementations;

import com.yottachain.entities.Block;
import com.yottachain.models.viewModels.BlockViewModel;
import com.yottachain.models.viewModels.NodeInfoViewModel;
import com.yottachain.models.viewModels.TransactionCreatedViewModel;
import com.yottachain.models.viewModels.TransactionViewModel;
import com.yottachain.services.interfaces.NodeService;

import java.util.List;

public class NodeServiceImpl implements NodeService {

    //private ModelMapper mapper;
    private List<Block> blockchain;

    public NodeServiceImpl(List<Block> blockchain) {
       // this.mapper = mapper;
        this.blockchain = blockchain;
    }

    @Override
    public NodeInfoViewModel GetInfo() {
        // TODO - Create new object from type NodeInfoViewModel and return it
        return null;
    }

    @Override
    public List<BlockViewModel> GetAllBlocks() {

        //List<Block> blocks = this.nodeRepository.findAll();
        // TODO - Map blocks to BlockViewModel
        // TODO - Return them as list

        return null;
    }

    @Override
    public BlockViewModel GetBlock(int blockIndex) throws Exception {
        if (blockIndex < 0 || blockIndex > blockchain.size()) {
            throw new Exception(); // TODO - Make custom exception for Block not found
        }

        // TODO - Find block from blockchain
        // TODO - Get block view model and return it
        return null;
    }

    @Override
    public TransactionViewModel GetTransactionInfo(String transactionHash) {
        return null;
    }

    @Override
    public TransactionCreatedViewModel AddTransaction(TransactionViewModel transaction) {
        // TODO - Create transaction from the service
        // TODO - Validate transaction from the service
        // TODO - Throw exception or check - if non exist, add to blockchain
        // TODO - Return TransactionCreatedViewModel :))

        return null;
    }
}