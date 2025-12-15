package com.example.integradora.structures;
public interface Iqueue<T> {
    void offer (T element);
    T poll();
    T peek();
    void clear();
    void print();
    boolean isEmpty();
    int getSize();
}