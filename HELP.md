# 图书管理系统帮助文档

## 系统概述
这是一个基于 Spring Boot 的图书管理系统，提供了图书的基本管理功能，包括图书的增删改查、搜索等功能。系统采用了分层架构设计，使用了多种设计模式来提高代码的可维护性和扩展性。

## 主要功能
1. 图书管理
   - 添加新图书
   - 修改图书信息
   - 删除图书
   - 查询图书详情
   - 图书列表展示

2. 图书搜索
   - 按标题搜索
   - 按作者搜索
   - 支持分页查询
   - 支持多条件组合查询

3. 图书状态管理
   - 在库
   - 借出
   - 维护中

## 技术架构
- 后端框架：Spring Boot
- 数据访问：Spring Data JPA
- 数据库：MySQL
- 设计模式：
  - 策略模式（搜索策略）
  - 工厂模式（搜索策略工厂）
  - 单例模式（工具类）

## 工具类说明

### 1. DateUtils（日期工具类）
提供常用的日期处理方法：
- 日期格式化与解析
- 日期计算
- 日期范围判断
- 获取特定时间点（如月初、月末等）

### 2. ListUtils（列表工具类）
提供常用的List处理方法：
- 列表判空
- 列表转换（List转Set、List转Map等）
- 列表过滤
- 列表排序
- 列表分页
- 列表去重

### 3. MapUtils（Map工具类）
提供常用的Map处理方法：
- Map判空
- Map转换（Map转List、Map转Properties等）
- Map过滤
- Map排序
- Map键值转换

## 使用示例

### 1. 日期处理
```java
// 获取当前日期
LocalDate today = DateUtils.getCurrentDate();

// 格式化日期
String dateStr = DateUtils.formatDate(today);

// 计算两个日期之间的天数
long days = DateUtils.daysBetween(startDate, endDate);
```

### 2. 列表处理
```java
// 判断列表是否为空
boolean isEmpty = ListUtils.isEmpty(bookList);

// 列表过滤
List<Book> availableBooks = ListUtils.filter(bookList, book -> book.getStatus() == BookStatus.AVAILABLE);

// 列表转换
List<String> bookTitles = ListUtils.map(bookList, Book::getTitle);
```

### 3. Map处理
```java
// 判断Map是否为空
boolean isEmpty = MapUtils.isEmpty(bookMap);

// Map转换
List<Book> books = MapUtils.getValues(bookMap);

// Map排序
Map<String, Book> sortedMap = MapUtils.sortByKey(bookMap, String::compareTo);
```

## 注意事项
1. 所有工具类方法都是静态方法，可以直接通过类名调用
2. 工具类方法都做了空值处理，使用时不需要额外判断
3. 日期处理使用Java 8的新日期API，避免使用旧的Date类
4. 列表和Map处理大量使用了Java 8的Stream API，提供了函数式编程支持

## 扩展建议
1. 可以添加更多的搜索策略，如按ISBN搜索、按分类搜索等
2. 可以增加缓存机制，提高查询性能
3. 可以添加日志记录功能，方便问题追踪
4. 可以增加更多的数据验证和异常处理
