package com.jku.sso.demo.persistence.model;

import jakarta.persistence.*;

@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String isbn;

    @Column
    private double price;

    public Book() { }

    public Book(long id, String title, String isbn, double price) {
        this.id = id;
        this.title = title;
        this.isbn = isbn;
        this.price = price;
    }

    public Book(String title, String isbn, double price) {
        this.title = title;
        this.isbn = isbn;
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
