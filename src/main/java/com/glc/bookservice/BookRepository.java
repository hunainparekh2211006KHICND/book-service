package com.glc.bookservice;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public class BookRepository implements IBookRepository<Book>{
    private Map<Integer, Book> repository;

    public BookRepository() {
        this.repository = new HashMap<>();
    }

    @Override
    public void save(Book book){
        repository.put(book.getId(), book);
    }

    @Override
    public Collection<Book> getAllBooks(){
        return repository.values();
    }

    @Override
    public Book getSpecificBook(int id){
        return repository.get(id);
    }

    @Override
    public Book deleteBook(int id){
        return repository.remove(id);
    }

    @Override
    public Book updateBook(Book book){
        Book returnBook = repository.get(book.getId());
        if(returnBook != null){
            repository.put(book.getId(),book);
        }
        return repository.get(book.getId());
    }
    
}
