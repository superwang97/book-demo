package com.example.demo.chain;

import com.example.demo.model.Book;
import com.example.demo.model.BookStatus;
import org.springframework.stereotype.Component;

/**
 * 图书可用性验证器
 * 验证图书是否可以借阅
 * 检查图书当前状态是否允许状态变更
 */
@Component
public class AvailabilityValidator extends AbstractBookStatusValidator {
    /**
     * 验证图书状态变更的可用性
     * 如果图书已经被借出，则不允许再次借出
     * @param book 待验证的图书
     * @param newStatus 新的状态
     * @throws IllegalStateException 当图书已经被借出时抛出此异常
     */
    @Override
    public void validate(Book book, BookStatus newStatus) {
        if (newStatus == BookStatus.BORROWED && book.getStatus() == BookStatus.BORROWED) {
            throw new IllegalStateException("Book is already borrowed");
        }
        validateNext(book, newStatus);
    }
} 