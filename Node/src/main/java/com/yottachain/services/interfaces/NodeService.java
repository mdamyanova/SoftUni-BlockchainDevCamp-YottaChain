package com.yottachain.services.interfaces;

import com.yottachain.entities.Block;
import com.yottachain.models.bindingModels.MiningJobBindingModel;
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
    TransactionViewModel getTransactionByHash(String transactionHash) throws Exception;
    List<BalanceViewModel> getBalances();
    BalanceByAddressViewModel getBalanceByAddress(String address) throws Exception;
    List<TransactionViewModel> getTransactionsByAddress(String address) throws Exception;
    TransactionCreatedViewModel addTransaction(TransactionBindingModel model) throws Exception;
    MiningJobViewModel getMiningJob(String minerAddress) throws Exception;
    MiningJobViewModel submitMinedBlock(MiningJobBindingModel minedBlock) throws Exception;
}