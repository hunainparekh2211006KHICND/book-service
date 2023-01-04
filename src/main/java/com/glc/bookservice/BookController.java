package com.glc.bookservice;

import java.util.Collection;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

@RestController
@RabbitListener(queues = "booklist")
@RequestMapping("/books")  // Any address like https://localhost:8080/books
public class BookController {

    @Autowired
    private RabbitTemplate template;

    @Autowired
    private Queue queue;
    
    @Autowired
    private IBookRepository repository;

    // public BookController(BookRepository repository){
    //     this.repository = repository;
    // }

    @PostMapping("")  // (POST) https://localhost:8080/books
    public void createBook(@RequestBody Book book) {
        this.repository.save(book);
    }

    @GetMapping("/all") // (GET) https://localhost:8080/books/all
    public Collection<Book> getAllBooks(){
        return this.repository.findAll();
    }

    @GetMapping("/{id}")
    public Book getspecificBook(@PathVariable int id){
        return this.repository.findById(id).orElse(null);
    }
    
    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable int id){
        this.repository.deleteById(id);
        // ResponseEntity<String> response =  book != null ? ResponseEntity.ok().body("Book Deleted Successfully") : ResponseEntity.ok().body("Book Not Found");
        // return response;
    }
    
    @PutMapping("")
    public ResponseEntity<Book> updateBook(@RequestBody Book book){
        Book updatedBook = this.repository.save(book);
        ResponseEntity<Book> response =  updatedBook != null ? ResponseEntity.ok().body(updatedBook) : ResponseEntity.notFound().build();
        return response;
    }

    @RabbitHandler
    public void receive(String in) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
         Collection<Book> books= mapper.readValue(in,new TypeReference<Collection<Book>>() {});
        books.forEach(book -> {System.out.println(book.getTitle());});
    }

    @PostMapping("/addbook")
    public void saveBook(@RequestBody Book book) throws Exception {
        this.repository.save(book);
        Collection<Book> books = this.repository.findAll();
        ObjectMapper mapper = new ObjectMapper();
        this.template.convertAndSend(queue.getName(),mapper.writeValueAsString(books));
    }


}
