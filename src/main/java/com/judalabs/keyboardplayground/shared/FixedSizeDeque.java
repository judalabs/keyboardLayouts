package com.judalabs.keyboardplayground.shared;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;

public class FixedSizeDeque<T> {

    private final int maxSize;
    private Deque<T> deque;

    public FixedSizeDeque(int maxSize) {
        if (maxSize <= 0) {
            throw new IllegalArgumentException();
        }
        this.maxSize = maxSize;
        this.deque = new LinkedList<>();
    }

    public void addFirst(T element) {
        if (deque.size() == maxSize) {
            deque.removeLast(); // Remove o mais antigo
        }
        deque.addFirst(element);
    }

    public T peekFirst() {
        return deque.peekFirst();
    }

    public T peekLast() {
        return deque.peekLast();
    }

    public int size() {
        return deque.size();
    }

    public boolean isEmpty() {
        return deque.isEmpty();
    }

    public int getMaxSize() {
        return maxSize;
    }

    public void reset() {
        deque = new ArrayDeque<>();
    }
}
