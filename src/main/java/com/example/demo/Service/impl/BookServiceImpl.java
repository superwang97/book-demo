package com.example.demo.Service.impl;

import com.example.demo.Service.BookService;
import com.example.demo.chain.BookStatusValidator;
import com.example.demo.exception.BookNotFoundException;
import com.example.demo.factory.SearchStrategyFactory;
import com.example.demo.mapper.BookDAO;
import com.example.demo.model.Book;
import com.example.demo.model.BookDTO;
import com.example.demo.model.BookStatus;
import com.example.demo.strategy.SearchStrategy;
import com.example.demo.utils.RedisUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.persistence.criteria.Predicate;
import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 图书服务实现类
 * 实现图书管理相关的业务逻辑
 */
@Service
@Transactional
@Validated
public class BookServiceImpl implements BookService {

    private final BookDAO bookDAO;
    private final SearchStrategyFactory searchStrategyFactory;
    private final BookStatusValidator statusValidator;
    private final RedisUtils redisUtils;
    
    // Redis缓存相关常量
    private static final String BOOK_CACHE_PREFIX = "book:";
    private static final String BOOK_LIST_CACHE_KEY = "book:list";
    private static final long CACHE_EXPIRE_TIME = 30; // 缓存过期时间（分钟）

    /**
     * 构造函数
     * @param bookDAO 图书数据访问对象
     * @param searchStrategyFactory 搜索策略工厂
     * @param statusValidator 状态验证器
     * @param redisUtils Redis工具类
     */
    public BookServiceImpl(
            BookDAO bookDAO,
            SearchStrategyFactory searchStrategyFactory,
            BookStatusValidator statusValidator,
            RedisUtils redisUtils
    ) {
        this.bookDAO = bookDAO;
        this.searchStrategyFactory = searchStrategyFactory;
        this.statusValidator = statusValidator;
        this.redisUtils = redisUtils;
    }

    @Override
    public Page<Book> findAllBooks(Pageable pageable) {
        return bookDAO.findAll(pageable);
    }

    @Override
    public Book findBookById(Long id) {
        return bookDAO.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book not found with id: " + id));
    }

    @Override
    @Transactional
    public Book createBook(@Valid BookDTO bookDTO) {
        Book book = new Book();
        updateBookFromDTO(book, bookDTO);
        return bookDAO.save(book);
    }

    @Override
    @Transactional
    public Book updateBook(Long id, @Valid BookDTO bookDTO) {
        Book existingBook = findBookById(id);
        updateBookFromDTO(existingBook, bookDTO);
        return bookDAO.save(existingBook);
    }

    @Override
    @Transactional
    public void deleteBook(Long id) {
        if (!bookDAO.existsById(id)) {
            throw new BookNotFoundException("Book not found with id: " + id);
        }
        bookDAO.deleteById(id);
    }

    @Override
    public Page<Book> searchBooks(String keyword, String searchType, Pageable pageable) {
        SearchStrategy strategy = searchStrategyFactory.getStrategy(searchType);
        return strategy.search(bookDAO, keyword, pageable);
    }

    @Override
    @Transactional
    public Book updateBookStatus(Long id, BookStatus newStatus) {
        Book book = findBookById(id);
        statusValidator.validate(book, newStatus);
        book.setStatus(newStatus);
        return bookDAO.save(book);
    }

    @Override
    public Page<Book> findBooksByCriteria(
            String category,
            BookStatus status,
            Double minPrice,
            Double maxPrice,
            LocalDate startDate,
            LocalDate endDate,
            Pageable pageable
    ) {
        return bookDAO.findAll((root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (category != null) {
                predicates.add(cb.equal(root.get("category"), category));
            }
            if (status != null) {
                predicates.add(cb.equal(root.get("status"), status));
            }
            if (minPrice != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("price"), minPrice));
            }
            if (maxPrice != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("price"), maxPrice));
            }
            if (startDate != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("publishDate"), startDate));
            }
            if (endDate != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("publishDate"), endDate));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        }, pageable);
    }

    /**
     * 从DTO更新图书信息
     * @param book 图书实体
     * @param bookDTO 图书DTO
     */
    private void updateBookFromDTO(Book book, BookDTO bookDTO) {
        book.setTitle(bookDTO.getTitle());
        book.setAuthor(bookDTO.getAuthor());
        book.setIsbn(bookDTO.getIsbn());
        book.setPublishDate(bookDTO.getPublishDate());
        book.setStatus(bookDTO.getStatus());
        book.setCategory(bookDTO.getCategory());
        book.setDescription(bookDTO.getDescription());
        book.setPrice(bookDTO.getPrice());
        book.setLocation(bookDTO.getLocation());
        book.setTotalCopies(bookDTO.getTotalCopies());
        book.setAvailableCopies(bookDTO.getAvailableCopies());
    }

    @Override
    @Transactional
    public Book addBook(Book book) {
        Book savedBook = bookDAO.save(book);
        // 清除列表缓存
        redisUtils.delete(BOOK_LIST_CACHE_KEY);
        return savedBook;
    }

    @Override
    @Transactional
    public Book updateBook(Long id, Book book) {
        if (!bookDAO.existsById(id)) {
            throw new RuntimeException("Book not found with id: " + id);
        }
        book.setId(id);
        Book updatedBook = bookDAO.save(book);
        // 清除相关缓存
        redisUtils.delete(BOOK_CACHE_PREFIX + id);
        redisUtils.delete(BOOK_LIST_CACHE_KEY);
        return updatedBook;
    }

    @Override
    @Transactional
    public void deleteBook(Long id) {
        if (!bookDAO.existsById(id)) {
            throw new RuntimeException("Book not found with id: " + id);
        }
        bookDAO.deleteById(id);
        // 清除相关缓存
        redisUtils.delete(BOOK_CACHE_PREFIX + id);
        redisUtils.delete(BOOK_LIST_CACHE_KEY);
    }

    @Override
    public Book getBookById(Long id) {
        // 先从缓存中获取
        String cacheKey = BOOK_CACHE_PREFIX + id;
        Object cachedBook = redisUtils.get(cacheKey);
        if (cachedBook != null) {
            return (Book) cachedBook;
        }

        // 缓存中没有，从数据库获取
        Book book = bookDAO.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + id));
        
        // 放入缓存
        redisUtils.set(cacheKey, book, CACHE_EXPIRE_TIME, TimeUnit.MINUTES);
        return book;
    }

    @Override
    public List<Book> getAllBooks() {
        // 先从缓存中获取
        Object cachedBooks = redisUtils.get(BOOK_LIST_CACHE_KEY);
        if (cachedBooks != null) {
            return (List<Book>) cachedBooks;
        }

        // 缓存中没有，从数据库获取
        List<Book> books = bookDAO.findAll();
        
        // 放入缓存
        redisUtils.set(BOOK_LIST_CACHE_KEY, books, CACHE_EXPIRE_TIME, TimeUnit.MINUTES);
        return books;
    }

    @Override
    public Page<Book> searchBooks(String keyword, Pageable pageable) {
        // 搜索功能不使用缓存，因为搜索结果经常变化
        return bookDAO.findByTitleContainingOrAuthorContaining(keyword, keyword, pageable);
    }

    @Override
    public List<Book> getBooksByCategory(String category) {
        String cacheKey = BOOK_CACHE_PREFIX + "category:" + category;
        // 先从缓存中获取
        Object cachedBooks = redisUtils.get(cacheKey);
        if (cachedBooks != null) {
            return (List<Book>) cachedBooks;
        }

        // 缓存中没有，从数据库获取
        List<Book> books = bookDAO.findByCategory(category);
        
        // 放入缓存
        redisUtils.set(cacheKey, books, CACHE_EXPIRE_TIME, TimeUnit.MINUTES);
        return books;
    }

    @Override
    public List<Book> getBooksByStatus(String status) {
        String cacheKey = BOOK_CACHE_PREFIX + "status:" + status;
        // 先从缓存中获取
        Object cachedBooks = redisUtils.get(cacheKey);
        if (cachedBooks != null) {
            return (List<Book>) cachedBooks;
        }

        // 缓存中没有，从数据库获取
        List<Book> books = bookDAO.findByStatus(status);
        
        // 放入缓存
        redisUtils.set(cacheKey, books, CACHE_EXPIRE_TIME, TimeUnit.MINUTES);
        return books;
    }
}
