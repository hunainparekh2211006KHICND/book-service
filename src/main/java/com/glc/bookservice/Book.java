package com.glc.bookservice;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;
    private String author;
    private int year;
    private int pages;

    public Book() {}

    public Book(int id, String title, String author, int year, int pages){
        this.id = id;
        this.title = title;
        this.author = author;
        this.year = year;
        this.pages = pages;
    }
    
}
