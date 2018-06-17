package com.yottachain.controllers;

import com.yottachain.entities.Block;
import com.yottachain.models.viewModels.BlockViewModel;
import com.yottachain.services.interfaces.NodeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

@CrossOrigin
@RestController
public class BlocksController {

    //private final NodeService nodeService;

    public BlocksController() {

    }

    public BlocksController(NodeService nodeService) {
        //this.nodeService = nodeService;
    }

    // GET Blocks
    @GetMapping("/blocks")
    public ResponseEntity<List<BlockViewModel>> blocks() {
        //List<BlockViewModel> allBlocks = nodeService.GetAllBlocks();
        return new ResponseEntity<List<BlockViewModel>>(new List<BlockViewModel>() {
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
            public Iterator<BlockViewModel> iterator() {
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
            public boolean add(BlockViewModel blockViewModel) {
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
            public boolean addAll(Collection<? extends BlockViewModel> c) {
                return false;
            }

            @Override
            public boolean addAll(int index, Collection<? extends BlockViewModel> c) {
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
            public BlockViewModel get(int index) {
                return null;
            }

            @Override
            public BlockViewModel set(int index, BlockViewModel element) {
                return null;
            }

            @Override
            public void add(int index, BlockViewModel element) {

            }

            @Override
            public BlockViewModel remove(int index) {
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
            public ListIterator<BlockViewModel> listIterator() {
                return null;
            }

            @Override
            public ListIterator<BlockViewModel> listIterator(int index) {
                return null;
            }

            @Override
            public List<BlockViewModel> subList(int fromIndex, int toIndex) {
                return null;
            }
        }, HttpStatus.OK);
    }

    // GET Block by Id
    @GetMapping("/blocks/{id}")
    public ResponseEntity<String> debug() {
        // TODO

        return new ResponseEntity<String>("TODO", HttpStatus.OK); // or 404
    }
}