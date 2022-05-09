package org.zly.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.SneakyThrows;

import java.util.*;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.BiConsumer;

/**
 * 用于统计key的出现次数
 *
 * @author zhanglianyu
 * @date 2022-04-21 13:36
 */
public class ZlyDataStatistics<T> {
    private final Map<T, Node<T>> map;
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private final ReentrantReadWriteLock.ReadLock readLock = this.lock.readLock();
    private final ReentrantReadWriteLock.WriteLock writeLock = this.lock.writeLock();

    public ZlyDataStatistics() {
        this.map = new HashMap<>();
    }

    public int addAndGet(T key) {
        try {
            this.writeLock.lock();
            Node<T> node = this.map.get(key);
            if (node != null) {
                node.setNumber(node.getNumber() + 1);
                return node.getNumber();
            }
            node = new Node<>(key, 1);
            this.map.put(key, node);
            return node.getNumber();
        } finally {
            this.writeLock.unlock();
        }
    }

    public void adds(T... key) {
        for (T t : key) {
            addAndGet(t);
        }
    }

    public int getData(T key) {
        try {
            this.readLock.lock();
            final Node<T> node = this.map.get(key);
            return node == null ? 0 : node.getNumber();
        } finally {
            this.readLock.unlock();
        }
    }

    public List<Node<T>> getDataList() {
        try {
            this.readLock.lock();
            List<Node<T>> list = new ArrayList<>(this.map.size());
            list.addAll(this.map.values());
            return list;
        } finally {
            this.readLock.unlock();
        }
    }

    public List<Node<T>> getDataListDesc() {
        final List<Node<T>> dataList = getDataList();
        dataList.sort(new Comparator<Node<T>>() {
            @Override
            public int compare(Node<T> o1, Node<T> o2) {
                return Integer.compare(o2.getNumber(), o1.getNumber());
            }
        });
        return dataList;
    }

    public List<Node<T>> getDataListAsc() {
        final List<Node<T>> dataList = getDataList();
        dataList.sort(new Comparator<Node<T>>() {
            @Override
            public int compare(Node<T> o1, Node<T> o2) {
                return Integer.compare(o1.getNumber(), o2.getNumber());
            }
        });
        return dataList;
    }

    public Map<T, Integer> getDataAll() {
        try {
            this.readLock.lock();
            Map<T, Integer> m = new HashMap<>();
            this.map.forEach(new BiConsumer<T, Node<T>>() {
                @Override
                public void accept(T t, Node<T> tNode) {
                    m.put(t, tNode.getNumber());
                }
            });
            return m;
        } finally {
            this.readLock.unlock();
        }
    }

    @Data
    @AllArgsConstructor
    public static class Node<T> {
        private T key;
        private int number;


    }


}
