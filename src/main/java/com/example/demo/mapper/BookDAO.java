package com.example.demo.mapper;

import com.example.demo.model.Book;
import com.example.demo.model.BookStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * 图书数据访问接口
 * 提供图书相关的数据库操作
 */
@Repository
public interface BookDAO extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {
    /**
     * 获取所有图书
     * @return 图书列表
     */
    List<Book> findAll();

    /**
     * 分页获取所有图书
     * @param pageable 分页参数
     * @return 分页后的图书列表
     */
    Page<Book> findAll(Pageable pageable);

    /**
     * 根据ID查找图书
     * @param id 图书ID
     * @return 图书信息
     */
    Optional<Book> findById(Long id);

    /**
     * 保存或更新图书
     * @param book 图书信息
     * @return 保存后的图书信息
     */
    Book save(Book book);

    /**
     * 根据ID删除图书
     * @param id 图书ID
     */
    void deleteById(Long id);

    /**
     * 检查图书是否存在
     * @param id 图书ID
     * @return 是否存在
     */
    boolean existsById(Long id);

    /**
     * 根据标题模糊查询图书
     * @param keyword 关键词
     * @return 图书列表
     */
    List<Book> findByTitleContaining(String keyword);

    /**
     * 根据作者模糊查询图书
     * @param keyword 关键词
     * @return 图书列表
     */
    List<Book> findByAuthorContaining(String keyword);

    /**
     * 根据ISBN查询图书
     * @param isbn ISBN号
     * @return 图书信息
     */
    Optional<Book> findByIsbn(String isbn);

    /**
     * 根据分类查询图书
     * @param category 分类
     * @return 图书列表
     */
    List<Book> findByCategory(String category);

    /**
     * 根据状态查询图书
     * @param status 状态
     * @return 图书列表
     */
    List<Book> findByStatus(BookStatus status);

    /**
     * 根据价格范围查询图书
     * @param minPrice 最低价格
     * @param maxPrice 最高价格
     * @return 图书列表
     */
    List<Book> findByPriceBetween(Double minPrice, Double maxPrice);

    /**
     * 根据出版日期范围查询图书
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 图书列表
     */
    List<Book> findByPublishDateBetween(LocalDate startDate, LocalDate endDate);

    /**
     * 根据标题模糊查询图书（分页）
     * @param keyword 关键词
     * @param pageable 分页参数
     * @return 分页后的图书列表
     */
    Page<Book> findByTitleContaining(String keyword, Pageable pageable);

    /**
     * 根据作者模糊查询图书（分页）
     * @param keyword 关键词
     * @param pageable 分页参数
     * @return 分页后的图书列表
     */
    Page<Book> findByAuthorContaining(String keyword, Pageable pageable);

    /**
     * 根据分类查询图书（分页）
     * @param category 分类
     * @param pageable 分页参数
     * @return 分页后的图书列表
     */
    Page<Book> findByCategory(String category, Pageable pageable);

    /**
     * 根据状态查询图书（分页）
     * @param status 状态
     * @param pageable 分页参数
     * @return 分页后的图书列表
     */
    Page<Book> findByStatus(BookStatus status, Pageable pageable);
}