package com.example.demo.strategy;

import com.example.demo.mapper.BookDAO;
import com.example.demo.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * 搜索策略接口
 * 定义了图书搜索的通用方法，使用策略模式实现不同的搜索策略
 * 每个具体的搜索策略类都需要实现这个接口
 */
public interface SearchStrategy {
    /**
     * 分页搜索图书
     * @param bookDAO 图书数据访问对象
     * @param keyword 搜索关键词
     * @param pageable 分页参数
     * @return 分页后的搜索结果
     */
    Page<Book> search(BookDAO bookDAO, String keyword, Pageable pageable);
}
