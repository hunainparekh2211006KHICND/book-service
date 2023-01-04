package com.glc.bookservice.book;


import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile({"book","hello-books"})
@Configuration
public class BookConfig {

@Bean
public Queue books() {
    return new Queue("books");
}

@Profile("receiver")
@Bean
public BookReceiver receiver() {
    return new BookReceiver();
}

@Profile("sender")
@Bean
public BookSender sender() {
    return new BookSender();
}
}
