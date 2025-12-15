package com.example.integradora.structures;

public class ArrayQueue<T> implements Iqueue<T> {

    private T[] data;
    private int front;     
    private int rear;      
    private int size;
    private int capacity;

    @SuppressWarnings("unchecked")
    public ArrayQueue(int capacity) {
        this.capacity = capacity;
        this.data = (T[]) new Object[capacity];
        this.front = 0;
        this.rear = 0;   
        this.size = 0;
    }

    public void offer(T element) {
        if (element == null) return;

        expandCapacityIfNeeded();

        data[rear] = element;
        rear = (rear + 1) % data.length;
        size++;
    }
    public T poll() {
        if (isEmpty()) {
            System.out.println("La Queue está vacía");
            return null;
        }
        T result = data[front];
        data[front] = null;  

        front = (front + 1) % data.length;
        size--;
        return result;
    }

    public T peek() {
        if (isEmpty()) {
            System.out.println("La Queue está vacía");
            return null;
        }
        return data[front];
    }
    @SuppressWarnings("unchecked")
    public void clear() {
        this.data = (T[]) new Object[this.capacity];
        this.front = 0;
        this.rear = 0;
        this.size = 0;
    }
    @SuppressWarnings("unchecked")
    private void expandCapacityIfNeeded() {
        if (size < data.length) return; 

        T[] newArr = (T[]) new Object[data.length * 2];
        for (int i = 0; i < size; i++) {
            newArr[i] = data[(front + i) % data.length];
        }
        data = newArr;
        front = 0;
        rear = size;
    }
    public void print() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < size; i++) {
            sb.append(data[(front + i) % data.length]);
            if (i < size - 1) sb.append(" => ");
        }
        sb.append("]");
        System.out.println(sb.toString());
    }
    public boolean isEmpty() {
        return size == 0;
    }
    public int getSize() {
        return size;
    }
}
