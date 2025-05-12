package com.example.demo.controller;

import com.example.demo.Service.BookService;
import com.example.demo.model.Book;
import com.example.demo.model.BookDTO;
import com.example.demo.model.BookStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookController.class)
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BookService bookService;

    private Book testBook;
    private BookDTO testBookDTO;

    @BeforeEach
    void setUp() {
        // 初始化测试数据
        testBook = new Book();
        testBook.setId(1L);
        testBook.setTitle("Test Book");
        testBook.setAuthor("Test Author");
        testBook.setIsbn("1234567890");
        testBook.setPublishDate(LocalDate.now());
        testBook.setStatus(BookStatus.AVAILABLE);
        testBook.setCategory("Test Category");
        testBook.setDescription("Test Description");
        testBook.setPrice(99.99);
        testBook.setLocation("Test Location");
        testBook.setTotalCopies(10);
        testBook.setAvailableCopies(5);

        testBookDTO = new BookDTO();
        testBookDTO.setTitle("Test Book");
        testBookDTO.setAuthor("Test Author");
        testBookDTO.setIsbn("1234567890");
        testBookDTO.setPublishDate(LocalDate.now());
        testBookDTO.setStatus(BookStatus.AVAILABLE);
        testBookDTO.setCategory("Test Category");
        testBookDTO.setDescription("Test Description");
        testBookDTO.setPrice(99.99);
        testBookDTO.setLocation("Test Location");
        testBookDTO.setTotalCopies(10);
        testBookDTO.setAvailableCopies(5);
    }

    @Test
    void getAllBooks_ShouldReturnPagedBooks() throws Exception {
        // Arrange
        Pageable pageable = PageRequest.of(0, 10);
        List<Book> books = Arrays.asList(testBook);
        Page<Book> bookPage = new PageImpl<>(books, pageable, books.size());
        when(bookService.findAllBooks(any(Pageable.class))).thenReturn(bookPage);

        // Act & Assert
        mockMvc.perform(get("/api/books")
                .param("page", "0")
                .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(testBook.getId()))
                .andExpect(jsonPath("$.totalElements").value(1));
    }

    @Test
    void getBookById_WhenBookExists_ShouldReturnBook() throws Exception {
        // Arrange
        when(bookService.findBookById(1L)).thenReturn(testBook);

        // Act & Assert
        mockMvc.perform(get("/api/books/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(testBook.getId()))
                .andExpect(jsonPath("$.title").value(testBook.getTitle()));
    }

    @Test
    void createBook_WithValidData_ShouldReturnCreatedBook() throws Exception {
        // Arrange
        when(bookService.createBook(any(BookDTO.class))).thenReturn(testBook);

        // Act & Assert
        mockMvc.perform(post("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testBookDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(testBook.getId()))
                .andExpect(jsonPath("$.title").value(testBook.getTitle()));
    }

    @Test
    void updateBook_WhenBookExists_ShouldReturnUpdatedBook() throws Exception {
        // Arrange
        when(bookService.updateBook(eq(1L), any(BookDTO.class))).thenReturn(testBook);

        // Act & Assert
        mockMvc.perform(put("/api/books/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testBookDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(testBook.getId()))
                .andExpect(jsonPath("$.title").value(testBook.getTitle()));
    }

    @Test
    void deleteBook_WhenBookExists_ShouldReturnNoContent() throws Exception {
        // Arrange
        doNothing().when(bookService).deleteBook(1L);

        // Act & Assert
        mockMvc.perform(delete("/api/books/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void searchBooks_ShouldReturnPagedResults() throws Exception {
        // Arrange
        Pageable pageable = PageRequest.of(0, 10);
        List<Book> books = Arrays.asList(testBook);
        Page<Book> bookPage = new PageImpl<>(books, pageable, books.size());
        when(bookService.searchBooks(anyString(), anyString(), any(Pageable.class))).thenReturn(bookPage);

        // Act & Assert
        mockMvc.perform(get("/api/books/search")
                .param("keyword", "test")
                .param("searchType", "title")
                .param("page", "0")
                .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(testBook.getId()))
                .andExpect(jsonPath("$.totalElements").value(1));
    }

    @Test
    void updateBookStatus_WhenBookExists_ShouldReturnUpdatedBook() throws Exception {
        // Arrange
        when(bookService.updateBookStatus(eq(1L), any(BookStatus.class))).thenReturn(testBook);

        // Act & Assert
        mockMvc.perform(patch("/api/books/1/status")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(BookStatus.BORROWED)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(testBook.getId()))
                .andExpect(jsonPath("$.status").value(testBook.getStatus().toString()));
    }

    @Test
    void findBooksByCriteria_ShouldReturnPagedResults() throws Exception {
        // Arrange
        Pageable pageable = PageRequest.of(0, 10);
        List<Book> books = Arrays.asList(testBook);
        Page<Book> bookPage = new PageImpl<>(books, pageable, books.size());
        when(bookService.findBooksByCriteria(
                anyString(), any(BookStatus.class), anyDouble(), anyDouble(),
                any(LocalDate.class), any(LocalDate.class), any(Pageable.class)
        )).thenReturn(bookPage);

        // Act & Assert
        mockMvc.perform(get("/api/books/search/criteria")
                .param("category", "Test Category")
                .param("status", "AVAILABLE")
                .param("minPrice", "0")
                .param("maxPrice", "100")
                .param("startDate", LocalDate.now().minusYears(1).toString())
                .param("endDate", LocalDate.now().toString())
                .param("page", "0")
                .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(testBook.getId()))
                .andExpect(jsonPath("$.totalElements").value(1));
    }
} 