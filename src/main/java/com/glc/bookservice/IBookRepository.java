package com.glc.bookservice;

import java.util.Collection;

public interface IBookRepository<T> {
    public void save(T t);

    public Collection<T> getAllBooks();

    public Book getSpecificBook(int id);
}
