package com.yottachain.services.implementations;

import com.yottachain.entities.Block;
import com.yottachain.models.viewModels.BlockViewModel;
import com.yottachain.models.viewModels.NodeInfoViewModel;
import com.yottachain.models.viewModels.TransactionCreatedViewModel;
import com.yottachain.models.viewModels.TransactionViewModel;
import com.yottachain.services.interfaces.NodeService;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class NodeServiceImpl implements NodeService {

    //private ModelMapper mapper;
    private List<Block> blockchain;

    public NodeServiceImpl() {
       // this.mapper = mapper;
        this.blockchain = new List<Block>() {
            @Override
            public int size() {
                return 0;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public boolean contains(Object o) {
                return false;
            }

            @Override
            public Iterator<Block> iterator() {
                return null;
            }

            @Override
            public Object[] toArray() {
                return new Object[0];
            }

            @Override
            public <T> T[] toArray(T[] a) {
                return null;
            }

            @Override
            public boolean add(Block block) {
                return false;
            }

            @Override
            public boolean remove(Object o) {
                return false;
            }

            @Override
            public boolean containsAll(Collection<?> c) {
                return false;
            }

            @Override
            public boolean addAll(Collection<? extends Block> c) {
                return false;
            }

            @Override
            public boolean addAll(int index, Collection<? extends Block> c) {
                return false;
            }

            @Override
            public boolean removeAll(Collection<?> c) {
                return false;
            }

            @Override
            public boolean retainAll(Collection<?> c) {
                return false;
            }

            @Override
            public void clear() {

            }

            @Override
            public Block get(int index) {
                return null;
            }

            @Override
            public Block set(int index, Block element) {
                return null;
            }

            @Override
            public void add(int index, Block element) {

            }

            @Override
            public Block remove(int index) {
                return null;
            }

            @Override
            public int indexOf(Object o) {
                return 0;
            }

            @Override
            public int lastIndexOf(Object o) {
                return 0;
            }

            @Override
            public ListIterator<Block> listIterator() {
                return null;
            }

            @Override
            public ListIterator<Block> listIterator(int index) {
                return null;
            }

            @Override
            public List<Block> subList(int fromIndex, int toIndex) {
                return null;
            }
        };
    }

    @Override
    public NodeInfoViewModel getInfo() {
        NodeInfoViewModel model = new NodeInfoViewModel(
                0, "TEST", 4444,
                "http://test.test", null, null, 3);
        // TODO - Create new object from type NodeInfoViewModel and return it
        return model;
    }

    @Override
    public List<BlockViewModel> getAllBlocks() {

        //List<Block> blocks = this.nodeRepository.findAll();
        // TODO - Map blocks to BlockViewModel
        // TODO - Return them as list

        return null;
    }

    @Override
    public BlockViewModel getBlock(int blockIndex) throws Exception {
        if (blockIndex < 0 || blockIndex > blockchain.size()) {
            throw new Exception(); // TODO - Make custom exception for Block not found
        }

        // TODO - Find block from blockchain
        // TODO - Get block view model and return it
        return null;
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