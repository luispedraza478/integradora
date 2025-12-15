package com.example.integradora.model;

import com.example.integradora.structures.ArrayQueue;

public class Book {
    private Integer id;
    private String title;
    private String author;
    private String category;
    private Integer totalCopies;
    private Integer availableCopies;
    private boolean active;
    private ArrayQueue<Integer> waitlist;

    public Book() {
        this.waitlist = new ArrayQueue<>(10);
        this.active = true;
    }

    public Book(Integer id, String title, String author, String category, Integer totalCopies) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.category = category;
        this.totalCopies = totalCopies;
        this.availableCopies = totalCopies;
        this.active = true;
        this.waitlist = new ArrayQueue<>(10);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getTotalCopies() {
        return totalCopies;
    }

    public void setTotalCopies(Integer totalCopies) {
        this.totalCopies = totalCopies;
    }

    public Integer getAvailableCopies() {
        return availableCopies;
    }

    public void setAvailableCopies(Integer availableCopies) {
        this.availableCopies = availableCopies;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public ArrayQueue<Integer> getWaitlist() {
        return waitlist;
    }

    public void setWaitlist(ArrayQueue<Integer> waitlist) {
        this.waitlist = waitlist;
    }
}
