-- 用户表
CREATE TABLE users (
                       id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '用户ID',
                       username VARCHAR(50) NOT NULL UNIQUE COMMENT '用户名，3-50个字符',
                       password VARCHAR(100) NOT NULL COMMENT '密码，最少6个字符',
                       name VARCHAR(50) NOT NULL COMMENT '真实姓名',
                       email VARCHAR(100) UNIQUE COMMENT '电子邮箱',
                       phone VARCHAR(20) UNIQUE COMMENT '手机号码',
                       role VARCHAR(20) NOT NULL COMMENT '用户角色：ADMIN-管理员，LIBRARIAN-图书管理员，READER-读者',
                       created_at DATETIME NOT NULL COMMENT '创建时间',
                       updated_at DATETIME NOT NULL COMMENT '更新时间'
) COMMENT '用户信息表';

-- 图书表
CREATE TABLE books (
                       id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '图书ID',
                       title VARCHAR(200) NOT NULL COMMENT '图书标题',
                       author VARCHAR(100) NOT NULL COMMENT '作者姓名',
                       isbn VARCHAR(20) UNIQUE COMMENT '国际标准书号',
                       publish_date DATE COMMENT '出版日期',
                       status VARCHAR(20) NOT NULL COMMENT '图书状态：AVAILABLE-可借阅，BORROWED-已借出，RESERVED-已预约，MAINTENANCE-维修中，LOST-丢失',
                       category VARCHAR(50) COMMENT '图书分类',
                       description VARCHAR(1000) COMMENT '图书描述',
                       price DECIMAL(10,2) COMMENT '图书价格',
                       location VARCHAR(100) COMMENT '存放位置',
                       total_copies INT NOT NULL DEFAULT 1 COMMENT '总副本数',
                       available_copies INT NOT NULL DEFAULT 1 COMMENT '可借阅副本数',
                       created_at DATE NOT NULL COMMENT '创建时间',
                       updated_at DATE NOT NULL COMMENT '更新时间'
) COMMENT '图书信息表';

-- 借阅记录表
CREATE TABLE borrow_records (
                                id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '借阅记录ID',
                                book_id BIGINT NOT NULL COMMENT '图书ID',
                                user_id BIGINT NOT NULL COMMENT '借阅人ID',
                                borrow_date DATETIME NOT NULL COMMENT '借阅日期',
                                due_date DATETIME NOT NULL COMMENT '应还日期',
                                return_date DATETIME COMMENT '实际归还日期',
                                status VARCHAR(20) NOT NULL COMMENT '借阅状态：BORROWED-已借出，RETURNED-已归还，OVERDUE-已逾期，LOST-丢失',
                                fine_amount DECIMAL(10,2) DEFAULT 0.0 COMMENT '罚款金额',
                                created_at DATETIME NOT NULL COMMENT '创建时间',
                                updated_at DATETIME NOT NULL COMMENT '更新时间',
                                FOREIGN KEY (book_id) REFERENCES books(id) COMMENT '关联图书表',
                                FOREIGN KEY (user_id) REFERENCES users(id) COMMENT '关联用户表'
) COMMENT '借阅记录表';

-- 预约记录表
CREATE TABLE reservations (
                              id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '预约记录ID',
                              book_id BIGINT NOT NULL COMMENT '图书ID',
                              user_id BIGINT NOT NULL COMMENT '预约人ID',
                              reservation_date DATETIME NOT NULL COMMENT '预约日期',
                              expiry_date DATETIME NOT NULL COMMENT '预约过期日期',
                              status VARCHAR(20) NOT NULL COMMENT '预约状态：PENDING-待处理，APPROVED-已批准，REJECTED-已拒绝，CANCELLED-已取消，COMPLETED-已完成',
                              created_at DATETIME NOT NULL COMMENT '创建时间',
                              updated_at DATETIME NOT NULL COMMENT '更新时间',
                              FOREIGN KEY (book_id) REFERENCES books(id) COMMENT '关联图书表',
                              FOREIGN KEY (user_id) REFERENCES users(id) COMMENT '关联用户表'
) COMMENT '预约记录表';

-- 添加索引
CREATE INDEX idx_books_title ON books(title);
CREATE INDEX idx_books_author ON books(author);
CREATE INDEX idx_books_category ON books(category);
CREATE INDEX idx_books_status ON books(status);
CREATE INDEX idx_borrow_records_book_id ON borrow_records(book_id);
CREATE INDEX idx_borrow_records_user_id ON borrow_records(user_id);
CREATE INDEX idx_borrow_records_status ON borrow_records(status);
CREATE INDEX idx_reservations_book_id ON reservations(book_id);
CREATE INDEX idx_reservations_user_id ON reservations(user_id);
CREATE INDEX idx_reservations_status ON reservations(status);