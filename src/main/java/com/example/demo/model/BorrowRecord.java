package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 借阅记录实体类
 * 用于记录图书的借阅信息，包括借阅人、借阅时间、应还时间等信息
 */
@Entity
@Table(name = "borrow_records")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BorrowRecord {
    /**
     * 借阅记录ID，主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 借阅的图书
     * 多对一关系
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    /**
     * 借阅人
     * 多对一关系
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /**
     * 借阅日期
     * 不能为空
     */
    @Column(name = "borrow_date", nullable = false)
    private LocalDateTime borrowDate;

    /**
     * 应还日期
     * 不能为空
     */
    @Column(name = "due_date", nullable = false)
    private LocalDateTime dueDate;

    /**
     * 实际归还日期
     * 可以为空（未归还时）
     */
    @Column(name = "return_date")
    private LocalDateTime returnDate;

    /**
     * 借阅状态
     * 包括：已借出、已归还、已逾期、丢失
     * 默认为已借出
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BorrowStatus status = BorrowStatus.BORROWED;

    /**
     * 罚款金额
     * 默认为0.0
     */
    @Column(name = "fine_amount")
    private Double fineAmount = 0.0;

    /**
     * 创建时间
     */
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    /**
     * 更新时间
     */
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    /**
     * 创建时自动设置创建时间和更新时间
     */
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    /**
     * 更新时自动更新更新时间
     */
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
} 