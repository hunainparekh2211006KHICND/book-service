package com.glc.bookservice;

public interface IBookRepository<T> {
    public void save(T t);
}
