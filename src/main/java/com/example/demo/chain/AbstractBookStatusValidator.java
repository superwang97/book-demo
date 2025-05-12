package com.example.demo.chain;

import com.example.demo.model.Book;
import com.example.demo.model.BookStatus;

/**
 * 图书状态验证器抽象基类
 * 实现了责任链模式中的基本功能
 * 提供了设置下一个验证器和传递验证请求的通用实现
 */
public abstract class AbstractBookStatusValidator implements BookStatusValidator {
    /**
     * 责任链中的下一个验证器
     */
    protected BookStatusValidator next;

    /**
     * 设置责任链中的下一个验证器
     * @param next 下一个验证器
     */
    @Override
    public void setNext(BookStatusValidator next) {
        this.next = next;
    }

    /**
     * 将验证请求传递给责任链中的下一个验证器
     * @param book 待验证的图书
     * @param newStatus 新的状态
     */
    protected void validateNext(Book book, BookStatus newStatus) {
        if (next != null) {
            next.validate(book, newStatus);
        }
    }
} 