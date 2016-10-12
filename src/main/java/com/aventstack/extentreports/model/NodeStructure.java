package com.aventstack.extentreports.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class NodeStructure implements Serializable {

    private static final long serialVersionUID = -8874294911453542325L;

    private List<Test> nodeList;

    NodeStructure() {
        nodeList = new ArrayList<>();
    }
    
    public void add(Test node) {
        nodeList.add(node);
    }

    public Test get(int x) {
        return nodeList.get(x);
    }

    public List<Test> getAll() {
        return nodeList;
    }

    public NodeIterator getIterator() { return new NodeIterator(); }

    // node iterator
    class NodeIterator implements Iterator<Test> {
        private int nodeIterIndex;

        NodeIterator() {
            nodeIterIndex = 0;
        }

        @Override
        public boolean hasNext() {
            return nodeList != null && nodeList.size() >= nodeIterIndex + 1;
        }

        @Override
        public Test next() {
            if (hasNext()) {
                return nodeList.get(nodeIterIndex++);
            }

            throw new NoSuchElementException();
        }

        @Override
        public void remove() {
            nodeList.remove(nodeIterIndex);
        }
    }
}