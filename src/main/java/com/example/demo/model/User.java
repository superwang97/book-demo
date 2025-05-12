package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * 用户实体类
 * 用于存储系统用户的基本信息、认证信息和角色信息
 */
@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    /**
     * 用户ID，主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 用户名
     * 不能为空，长度3-50字符，且唯一
     */
    @NotBlank(message = "用户名不能为空")
    @Size(min = 3, max = 50, message = "用户名长度必须在3-50个字符之间")
    @Column(nullable = false, unique = true)
    private String username;

    /**
     * 密码
     * 不能为空，最小长度6字符
     */
    @NotBlank(message = "密码不能为空")
    @Size(min = 6, message = "密码长度不能少于6个字符")
    @Column(nullable = false)
    private String password;

    /**
     * 真实姓名
     * 不能为空，最大长度50字符
     */
    @NotBlank(message = "姓名不能为空")
    @Size(max = 50, message = "姓名长度不能超过50个字符")
    @Column(nullable = false)
    private String name;

    /**
     * 电子邮箱
     * 必须符合邮箱格式，且唯一
     */
    @Email(message = "邮箱格式不正确")
    @Column(unique = true)
    private String email;

    /**
     * 手机号码
     * 必须符合中国大陆手机号格式，且唯一
     */
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式不正确")
    @Column(unique = true)
    private String phone;

    /**
     * 用户角色
     * 包括：管理员、图书管理员、读者
     * 默认为读者角色
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role = UserRole.READER;

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