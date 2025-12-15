package com.example.integradora.structures;

public class ArrayStack<T> implements IStack<T> {

    private T[] data;
    private int top;

    @SuppressWarnings("unchecked")
    public ArrayStack(int initialCapacity) {
        this.data = (T[]) new Object[initialCapacity];
        this.top = 0;
    }

    @Override
    public void push(T element) {
        if (top == data.length) {
            System.out.println("La pila está llena");
            return;
        }
        data[top] = element;
        top++;
    }

    @Override
    public T pop() {
        if (isEmpty()) {
            System.out.println("La pila está vacía");
            return null;
        }
        top--;
        T value = data[top];
        data[top] = null;
        return value;
    }

    @Override
    public T peek() {
        if (isEmpty()) {
            System.out.println("La pila está vacía");
            return null;
        }
        return data[top - 1];
    }

    @Override
    public void clear() {
        for (int i = 0; i < top; i++) {
            data[i] = null;
        }
        top = 0;
    }

    @Override
    public int size() {
        return top;
    }

    @Override
    public boolean isEmpty() {
        return top == 0;
    }

    @Override
    public void print() {
        System.out.print("[");
        for (int i = top - 1; i >= 0; i--) {
            System.out.print(data[i]);
            if (i != 0) System.out.print(" -> ");
        }
        System.out.println("]");
    }
}
