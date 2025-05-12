package com.example.demo.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.List;

/**
 * 图书实体类
 * 用于存储图书的基本信息、状态和库存信息
 */
@Entity
@Table(name = "books")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {

    /**
     * 图书ID，主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 图书标题
     * 不能为空，长度1-200字符
     */
    @NotBlank(message = "书名不能为空")
    @Size(min = 1, max = 200, message = "书名长度必须在1-200个字符之间")
    @Column(name = "title", nullable = false)
    private String title;

    /**
     * 作者姓名
     * 不能为空，长度1-100字符
     */
    @NotBlank(message = "作者不能为空")
    @Size(min = 1, max = 100, message = "作者名长度必须在1-100个字符之间")
    @Column(nullable = false)
    private String author;

    /**
     * 国际标准书号
     * 必须符合ISBN格式，且唯一
     */
    @Pattern(regexp = "^(?:ISBN(?:-1[03])?:? )?(?=[0-9X]{10}$|(?=(?:[0-9]+[- ]){3})[- 0-9X]{13}$|97[89][0-9]{10}$|(?=(?:[0-9]+[- ]){4})[- 0-9]{17}$)(?:97[89][- ])?[0-9]{1,5}[- ]?[0-9]+[- ]?[0-9]+[- ]?[0-9X]$", 
             message = "ISBN格式不正确")
    @Column(unique = true)
    private String isbn;

    /**
     * 出版日期
     */
    @Column(name = "publish_date")
    private LocalDate publishDate;

    /**
     * 图书状态
     * 包括：可借阅、已借出、已预约、维修中、丢失
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private BookStatus status = BookStatus.AVAILABLE;

    /**
     * 图书分类
     * 例如：文学、科技、历史等
     */
    @Column(name = "category")
    private String category;

    /**
     * 图书描述
     * 最大长度1000字符
     */
    @Column(name = "description", length = 1000)
    private String description;

    /**
     * 图书价格
     */
    @Column(name = "price")
    private Double price;

    /**
     * 图书存放位置
     * 例如：A区-1层-01架-01号
     */
    @Column(name = "location")
    private String location;

    /**
     * 图书总副本数
     * 默认为1
     */
    @Column(name = "total_copies")
    private Integer totalCopies = 1;

    /**
     * 可借阅副本数
     * 默认为1
     */
    @Column(name = "available_copies")
    private Integer availableCopies = 1;

    /**
     * 借阅记录列表
     * 一对多关系
     */
    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private List<BorrowRecord> borrowRecords;

    /**
     * 预约记录列表
     * 一对多关系
     */
    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private List<Reservation> reservations;

    /**
     * 创建时间
     */
    @Column(name = "created_at")
    private LocalDate createdAt;

    /**
     * 更新时间
     */
    @Column(name = "updated_at")
    private LocalDate updatedAt;

    /**
     * 创建时自动设置创建时间和更新时间
     */
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDate.now();
        updatedAt = LocalDate.now();
    }

    /**
     * 更新时自动更新更新时间
     */
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDate.now();
    }
}
