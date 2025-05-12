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

    /**
     * 构造函数
     * @param bookDAO 图书数据访问对象
     * @param searchStrategyFactory 搜索策略工厂
     * @param statusValidator 状态验证器
     */
    public BookServiceImpl(
            BookDAO bookDAO,
            SearchStrategyFactory searchStrategyFactory,
            BookStatusValidator statusValidator
    ) {
        this.bookDAO = bookDAO;
        this.searchStrategyFactory = searchStrategyFactory;
        this.statusValidator = statusValidator;
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
}
