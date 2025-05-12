package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO {
    @NotBlank(message = "书名不能为空")
    @Size(min = 1, max = 200, message = "书名长度必须在1-200个字符之间")
    private String title;

    @NotBlank(message = "作者不能为空")
    @Size(min = 1, max = 100, message = "作者名长度必须在1-100个字符之间")
    private String author;

    @Pattern(regexp = "^(?:ISBN(?:-1[03])?:? )?(?=[0-9X]{10}$|(?=(?:[0-9]+[- ]){3})[- 0-9X]{13}$|97[89][0-9]{10}$|(?=(?:[0-9]+[- ]){4})[- 0-9]{17}$)(?:97[89][- ])?[0-9]{1,5}[- ]?[0-9]+[- ]?[0-9]+[- ]?[0-9X]$", 
             message = "ISBN格式不正确")
    private String isbn;

    private LocalDate publishDate;
    private BookStatus status;
    private String category;
    private String description;
    private Double price;
    private String location;
    
    /**
     * 图书总副本数
     */
    private Integer totalCopies = 1;

    /**
     * 可借阅副本数
     */
    private Integer availableCopies = 1;
}
