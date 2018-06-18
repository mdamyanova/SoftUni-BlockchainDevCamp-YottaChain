package com.yottachain.services.implementations;

import com.yottachain.entities.Block;
import com.yottachain.models.viewModels.BlockViewModel;
import com.yottachain.models.viewModels.NodeInfoViewModel;
import com.yottachain.models.viewModels.TransactionCreatedViewModel;
import com.yottachain.models.viewModels.TransactionViewModel;
import com.yottachain.services.interfaces.NodeService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;

public class NodeServiceImpl implements NodeService {

    private ModelMapper mapper;
    private List<Block> blockchain;

    public NodeServiceImpl() {
        this.mapper = new ModelMapper();
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
        // TODO
        NodeInfoViewModel model = new NodeInfoViewModel(
                0, "TEST", 4444,
                "http://test.test", null, null, 3);
        return model;
    }

    @Override
    public List<BlockViewModel> getAllBlocks() {
        Type targetListType = new TypeToken<List<BlockViewModel>>() {}.getType();
        List<BlockViewModel> allBlocks = blockchain.size() > 0 ?
                mapper.map(blockchain, targetListType) : Collections.emptyList();
        return allBlocks;
    }

    @Override
    public BlockViewModel getBlock(int blockIndex) throws Exception {
        if (blockIndex < 0 || blockIndex > blockchain.size()) {
            throw new Exception("No such block"); // TODO - Make custom exception for Block not found
        }

        Block block = blockchain.stream().filter(x -> x.getIndex() == blockIndex).findFirst().get();

        return mapper.map(block, BlockViewModel.class);
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