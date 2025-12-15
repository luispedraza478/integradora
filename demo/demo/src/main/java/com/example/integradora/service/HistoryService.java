package com.example.integradora.service;

import com.example.integradora.model.HistoryAction;
import com.example.integradora.structures.ArrayStack;

public class HistoryService {

    private ArrayStack<HistoryAction> stack;

    public HistoryService() {
        this.stack = new ArrayStack<>(100);
    }

    public void push(HistoryAction action) {
        stack.push(action);
    }

    public HistoryAction pop() {
        return stack.pop();
    }

    public boolean isEmpty() {
        return stack.isEmpty();
    }

    public void print() {
        stack.print();
    }
}
