package com.example.demo.strategy;

import com.example.demo.mapper.BookDAO;
import com.example.demo.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

/**
 * 作者搜索策略
 * 实现按图书作者进行搜索的策略
 */
@Component
public class AuthorSearchStrategy implements SearchStrategy {
    
    @Override
    public Page<Book> search(BookDAO bookDAO, String keyword, Pageable pageable) {
        return bookDAO.findByAuthorContaining(keyword, pageable);
    }
}