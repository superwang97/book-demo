package com.example.demo.repository;

import com.example.demo.model.Book;
import com.example.demo.model.BookStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * 图书数据访问接口
 * 提供图书相关的数据库操作
 */
@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    
    /**
     * 根据分类查找图书
     * @param category 图书分类
     * @return 图书列表
     */
    List<Book> findByCategory(String category);
    
    /**
     * 根据状态查找图书
     * @param status 图书状态
     * @return 图书列表
     */
    List<Book> findByStatus(BookStatus status);
    
    /**
     * 根据价格范围查找图书
     * @param minPrice 最低价格
     * @param maxPrice 最高价格
     * @return 图书列表
     */
    List<Book> findByPriceBetween(Double minPrice, Double maxPrice);
    
    /**
     * 根据出版日期范围查找图书
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 图书列表
     */
    List<Book> findByPublishDateBetween(LocalDate startDate, LocalDate endDate);
    
    /**
     * 根据标题模糊查询图书
     * @param title 图书标题
     * @return 图书列表
     */
    List<Book> findByTitleContaining(String title);
    
    /**
     * 根据作者模糊查询图书
     * @param author 作者名称
     * @return 图书列表
     */
    List<Book> findByAuthorContaining(String author);
    
    /**
     * 根据ISBN精确查询图书
     * @param isbn ISBN号
     * @return 图书信息
     */
    Book findByIsbn(String isbn);
    
    /**
     * 根据标题模糊查询图书（分页）
     * @param title 图书标题
     * @param pageable 分页参数
     * @return 分页后的图书列表
     */
    Page<Book> findByTitleContaining(String title, Pageable pageable);
    
    /**
     * 根据作者模糊查询图书（分页）
     * @param author 作者名称
     * @param pageable 分页参数
     * @return 分页后的图书列表
     */
    Page<Book> findByAuthorContaining(String author, Pageable pageable);
    
    /**
     * 根据分类查找图书（分页）
     * @param category 图书分类
     * @param pageable 分页参数
     * @return 分页后的图书列表
     */
    Page<Book> findByCategory(String category, Pageable pageable);
    
    /**
     * 根据状态查找图书（分页）
     * @param status 图书状态
     * @param pageable 分页参数
     * @return 分页后的图书列表
     */
    Page<Book> findByStatus(BookStatus status, Pageable pageable);
} 