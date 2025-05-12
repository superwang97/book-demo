package com.example.demo.strategy;

import com.example.demo.mapper.BookDAO;
import com.example.demo.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

/**
 * 标题搜索策略
 * 实现按图书标题进行搜索的策略
 */
@Component
public class TitleSearchStrategy implements SearchStrategy {
    
    @Override
    public Page<Book> search(BookDAO bookDAO, String keyword, Pageable pageable) {
        return bookDAO.findByTitleContaining(keyword, pageable);
    }
}


