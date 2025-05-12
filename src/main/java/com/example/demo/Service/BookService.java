package com.example.demo.Service;

import com.example.demo.model.Book;
import com.example.demo.model.BookDTO;
import com.example.demo.model.BookStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

/**
 * 图书服务接口
 * 提供图书管理相关的核心业务功能
 */
public interface BookService {
    /**
     * 分页获取图书列表
     * @param pageable 分页参数
     * @return 分页后的图书列表
     */
    Page<Book> findAllBooks(Pageable pageable);

    /**
     * 根据ID查找图书
     * @param id 图书ID
     * @return 图书信息
     */
    Book findBookById(Long id);

    /**
     * 创建新图书
     * @param bookDTO 图书信息DTO
     * @return 创建后的图书信息
     */
    Book createBook(@Valid BookDTO bookDTO);

    /**
     * 更新图书信息
     * @param id 图书ID
     * @param bookDTO 更新的图书信息
     * @return 更新后的图书信息
     */
    Book updateBook(Long id, @Valid BookDTO bookDTO);

    /**
     * 删除图书
     * @param id 图书ID
     */
    void deleteBook(Long id);

    /**
     * 搜索图书
     * @param keyword 搜索关键词
     * @param searchType 搜索类型（title/author）
     * @param pageable 分页参数
     * @return 分页后的搜索结果
     */
    Page<Book> searchBooks(String keyword, String searchType, Pageable pageable);

    /**
     * 更新图书状态
     * @param id 图书ID
     * @param newStatus 新状态
     * @return 更新后的图书信息
     */
    Book updateBookStatus(Long id, BookStatus newStatus);

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
    Page<Book> findBooksByCriteria(
            String category,
            BookStatus status,
            Double minPrice,
            Double maxPrice,
            LocalDate startDate,
            LocalDate endDate,
            Pageable pageable
    );
}