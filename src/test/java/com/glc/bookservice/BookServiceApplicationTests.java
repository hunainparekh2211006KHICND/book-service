package com.glc.bookservice;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

@AutoConfigureJsonTesters
@SpringBootTest
@AutoConfigureMockMvc
class BookServiceApplicationTests {

	private MockMvc mvc;

	@Mock
	private IBookRepository bookRepository;

	@InjectMocks
	private BookController bookController;

	private JacksonTester<Book> jsonBook;
	private JacksonTester<Collection<Book>> jsonBooks;

	@BeforeEach
	public void setUp(){
		JacksonTester.initFields(this, new ObjectMapper());
		mvc = MockMvcBuilders.standaloneSetup(bookController).build();
	}


	@Test
	void contextLoads() {
	}

	//AC1:  When I enter the title, author, year of publication, and length of the book into the UI and hit submit, my book will saved to the list.
	@Test
	public void canCreateANewBook() throws Exception {
		Book book = new Book(1, "The Hobbit", "J.R.R. Tolkein", 1937, 320);
		mvc.perform(post("/books")
			.contentType(MediaType.APPLICATION_JSON)
			.content(jsonBook.write(book).getJson()))
			.andExpect(status().isOk());
	}

	//AC2:  When I click “View All Books” the application will display a list of all the books in my list.
	@Test
	public void canGetAllBooks() throws Exception {
		Book book1 = new Book(1, "The Hobbit", "J.R.R. Tolkein", 1937, 320);
		Book book2 = new Book(2, "It", "Stephen King", 1986, 1138);
		List<Book> books = new ArrayList<Book>();
		books.add(book1);
		books.add(book2);
		when(bookRepository.findAll()).thenReturn(books);
		mvc.perform(get("/books/all")
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().json(jsonBooks.write(books).getJson()));
	}

	//AC:3 When I Click on Book Name the application will display all data of this book
	@Test
	public void canGetSpecificBook() throws Exception{
		Book book1 = new Book(1, "The Hobbit", "J.R.R. Tolkein", 1937, 320);
		when(bookRepository.findById(1).orElse(book1)).thenReturn(book1);
		mvc.perform(get("/books/1")
		.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(content().json(jsonBook.write(book1).getJson()));
	}

	//AC:4 When I click the checkbox next to a book, and then the press the “Delete Book” button, the application will remove the book from my list.
	@Test
	public void canDeleteBook() throws Exception{
		mvc.perform(delete("/books/1"))
		.andExpect(status().isOk());
	}

	//AC:5  When I click the checkbox next to a book, and then press the “Update Book” button, the application will allow me to update any of the information about the book.
	@Test void canUpdateBook() throws Exception{
		Book book = new Book(1, "The Hobbit", "J.R.R. Tolkein", 1937, 320);
		when(bookRepository.save(any())).thenReturn(book);
		mvc.perform(put("/books")
			.contentType(MediaType.APPLICATION_JSON)
			.content(jsonBook.write(book).getJson()))
			.andExpect(status().isOk())
			.andExpect(content().json(jsonBook.write(book).getJson()));
	}
}
