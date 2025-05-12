package com.example.demo.controller;

import com.example.demo.Service.BookService;
import com.example.demo.model.Book;
import com.example.demo.model.BookDTO;
import com.example.demo.model.BookStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;

/**
 * 图书管理控制器
 * 提供图书相关的所有REST API接口
 */
@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    /**
     * 构造函数注入BookService
     * @param bookService 图书服务接口
     */
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    /**
     * 分页获取图书列表
     * @param pageable 分页参数
     * @return 分页后的图书列表
     */
    @GetMapping
    public ResponseEntity<Page<Book>> getAllBooks(Pageable pageable) {
        return ResponseEntity.ok(bookService.findAllBooks(pageable));
    }

    /**
     * 根据ID获取图书详情
     * @param id 图书ID
     * @return 图书详情
     */
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.findBookById(id));
    }

    /**
     * 创建新图书
     * @param bookDTO 图书信息DTO
     * @return 创建后的图书信息
     */
    @PostMapping
    public ResponseEntity<Book> createBook(@Valid @RequestBody BookDTO bookDTO) {
        return new ResponseEntity<>(bookService.createBook(bookDTO), HttpStatus.CREATED);
    }

    /**
     * 更新图书信息
     * @param id 图书ID
     * @param bookDTO 更新的图书信息
     * @return 更新后的图书信息
     */
    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @Valid @RequestBody BookDTO bookDTO) {
        return ResponseEntity.ok(bookService.updateBook(id, bookDTO));
    }

    /**
     * 删除图书
     * @param id 图书ID
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
    }

    /**
     * 搜索图书
     * @param keyword 搜索关键词
     * @param searchType 搜索类型（title/author）
     * @param pageable 分页参数
     * @return 分页后的搜索结果
     */
    @GetMapping("/search")
    public ResponseEntity<Page<Book>> searchBooks(
            @RequestParam String keyword,
            @RequestParam String searchType,
            Pageable pageable) {
        return ResponseEntity.ok(bookService.searchBooks(keyword, searchType, pageable));
    }

    /**
     * 更新图书状态
     * @param id 图书ID
     * @param status 新状态
     * @return 更新后的图书信息
     */
    @PatchMapping("/{id}/status")
    public ResponseEntity<Book> updateBookStatus(
            @PathVariable Long id,
            @RequestBody BookStatus status) {
        return ResponseEntity.ok(bookService.updateBookStatus(id, status));
    }

    /**
     * 根据条件查询图书
     * @param category 分类（可选）
     * @param status 状态（可选）
     * @param minPrice 最低价格（可选）
     * @param maxPrice 最高价格（可选）
     * @param startDate 开始日期（可选）
     * @param endDate 结束日期（可选）
     * @param pageable 分页参数
     * @return 分页后的图书列表
     */
    @GetMapping("/search/criteria")
    public ResponseEntity<Page<Book>> findBooksByCriteria(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) BookStatus status,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            Pageable pageable) {
        return ResponseEntity.ok(bookService.findBooksByCriteria(
                category, status, minPrice, maxPrice, startDate, endDate, pageable));
    }
}