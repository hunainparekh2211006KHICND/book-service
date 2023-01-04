package com.glc.bookservice;

// import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IBookRepository extends JpaRepository<Book,Integer>{
    // public void save(T t);

    // public Collection<T> getAllBooks();

    // public Book getSpecificBook(int id);

    // public Book deleteBook(int id);

    // public Book updateBook(Book book);
}
