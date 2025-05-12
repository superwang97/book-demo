package com.example.demo.Service.impl;

import com.example.demo.chain.BookStatusValidator;
import com.example.demo.exception.BookNotFoundException;
import com.example.demo.factory.SearchStrategyFactory;
import com.example.demo.mapper.BookDAO;
import com.example.demo.model.Book;
import com.example.demo.model.BookDTO;
import com.example.demo.model.BookStatus;
import com.example.demo.strategy.SearchStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {

    @Mock
    private BookDAO bookDAO;

    @Mock
    private SearchStrategyFactory searchStrategyFactory;

    @Mock
    private SearchStrategy searchStrategy;

    @Mock
    private BookStatusValidator statusValidator;

    @InjectMocks
    private BookServiceImpl bookService;

    private Book testBook;
    private BookDTO testBookDTO;
    private Pageable pageable;

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

        pageable = PageRequest.of(0, 10);
    }

    @Test
    void findAllBooks_ShouldReturnPagedBooks() {
        // Arrange
        List<Book> books = Arrays.asList(testBook);
        Page<Book> bookPage = new PageImpl<>(books, pageable, books.size());
        when(bookDAO.findAll(any(Pageable.class))).thenReturn(bookPage);

        // Act
        Page<Book> result = bookService.findAllBooks(pageable);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(testBook, result.getContent().get(0));
    }

    @Test
    void findBookById_WhenBookExists_ShouldReturnBook() {
        // Arrange
        when(bookDAO.findById(1L)).thenReturn(Optional.of(testBook));

        // Act
        Book result = bookService.findBookById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(testBook.getId(), result.getId());
        assertEquals(testBook.getTitle(), result.getTitle());
    }

    @Test
    void findBookById_WhenBookNotExists_ShouldThrowException() {
        // Arrange
        when(bookDAO.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(BookNotFoundException.class, () -> bookService.findBookById(1L));
    }

    @Test
    void createBook_WithValidData_ShouldReturnCreatedBook() {
        // Arrange
        when(bookDAO.save(any(Book.class))).thenReturn(testBook);

        // Act
        Book result = bookService.createBook(testBookDTO);

        // Assert
        assertNotNull(result);
        assertEquals(testBook.getId(), result.getId());
        assertEquals(testBook.getTitle(), result.getTitle());
        verify(bookDAO).save(any(Book.class));
    }

    @Test
    void updateBook_WhenBookExists_ShouldReturnUpdatedBook() {
        // Arrange
        when(bookDAO.findById(1L)).thenReturn(Optional.of(testBook));
        when(bookDAO.save(any(Book.class))).thenReturn(testBook);

        // Act
        Book result = bookService.updateBook(1L, testBookDTO);

        // Assert
        assertNotNull(result);
        assertEquals(testBook.getId(), result.getId());
        assertEquals(testBook.getTitle(), result.getTitle());
        verify(bookDAO).save(any(Book.class));
    }

    @Test
    void updateBook_WhenBookNotExists_ShouldThrowException() {
        // Arrange
        when(bookDAO.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(BookNotFoundException.class, () -> bookService.updateBook(1L, testBookDTO));
    }

    @Test
    void deleteBook_WhenBookExists_ShouldDeleteBook() {
        // Arrange
        when(bookDAO.existsById(1L)).thenReturn(true);

        // Act
        bookService.deleteBook(1L);

        // Assert
        verify(bookDAO).deleteById(1L);
    }

    @Test
    void deleteBook_WhenBookNotExists_ShouldThrowException() {
        // Arrange
        when(bookDAO.existsById(1L)).thenReturn(false);

        // Act & Assert
        assertThrows(BookNotFoundException.class, () -> bookService.deleteBook(1L));
    }

    @Test
    void searchBooks_ShouldReturnPagedResults() {
        // Arrange
        List<Book> books = Arrays.asList(testBook);
        Page<Book> bookPage = new PageImpl<>(books, pageable, books.size());
        when(searchStrategyFactory.getStrategy(anyString())).thenReturn(searchStrategy);
        when(searchStrategy.search(any(), anyString(), any(Pageable.class))).thenReturn(bookPage);

        // Act
        Page<Book> result = bookService.searchBooks("test", "title", pageable);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(testBook, result.getContent().get(0));
    }

    @Test
    void updateBookStatus_WhenValid_ShouldUpdateStatus() {
        // Arrange
        when(bookDAO.findById(1L)).thenReturn(Optional.of(testBook));
        when(bookDAO.save(any(Book.class))).thenReturn(testBook);
        doNothing().when(statusValidator).validate(any(Book.class), any(BookStatus.class));

        // Act
        Book result = bookService.updateBookStatus(1L, BookStatus.BORROWED);

        // Assert
        assertNotNull(result);
        assertEquals(testBook.getId(), result.getId());
        verify(statusValidator).validate(any(Book.class), any(BookStatus.class));
        verify(bookDAO).save(any(Book.class));
    }

} 