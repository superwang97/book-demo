package com.example.demo.mapper;

import com.example.demo.model.Book;
import com.example.demo.model.BookStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * 图书数据访问实现类
 * 实现图书相关的数据库操作
 */
@Repository
public class BookDAOImpl implements BookDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Book> findAll() {
        return entityManager.createQuery("SELECT b FROM Book b", Book.class).getResultList();
    }

    @Override
    public Page<Book> findAll(Pageable pageable) {
        TypedQuery<Book> query = entityManager.createQuery("SELECT b FROM Book b", Book.class);
        
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());
        
        List<Book> books = query.getResultList();
        Long total = entityManager.createQuery("SELECT COUNT(b) FROM Book b", Long.class)
                .getSingleResult();
        
        return new PageImpl<>(books, pageable, total);
    }

    @Override
    public Optional<Book> findById(Long id) {
        return Optional.ofNullable(entityManager.find(Book.class, id));
    }

    @Override
    public Book save(Book book) {
        if (book.getId() == null) {
            entityManager.persist(book);
            return book;
        } else {
            return entityManager.merge(book);
        }
    }

    @Override
    public void deleteById(Long id) {
        findById(id).ifPresent(entityManager::remove);
    }

    @Override
    public boolean existsById(Long id) {
        return findById(id).isPresent();
    }

    @Override
    public List<Book> findByTitleContaining(String keyword) {
        return entityManager.createQuery(
                "SELECT b FROM Book b WHERE LOWER(b.title) LIKE LOWER(:keyword)", Book.class)
                .setParameter("keyword", "%" + keyword + "%")
                .getResultList();
    }

    @Override
    public List<Book> findByAuthorContaining(String keyword) {
        return entityManager.createQuery(
                "SELECT b FROM Book b WHERE LOWER(b.author) LIKE LOWER(:keyword)", Book.class)
                .setParameter("keyword", "%" + keyword + "%")
                .getResultList();
    }

    @Override
    public Optional<Book> findByIsbn(String isbn) {
        return entityManager.createQuery(
                "SELECT b FROM Book b WHERE b.isbn = :isbn", Book.class)
                .setParameter("isbn", isbn)
                .getResultStream()
                .findFirst();
    }

    @Override
    public List<Book> findByCategory(String category) {
        return entityManager.createQuery(
                "SELECT b FROM Book b WHERE b.category = :category", Book.class)
                .setParameter("category", category)
                .getResultList();
    }

    @Override
    public List<Book> findByStatus(BookStatus status) {
        return entityManager.createQuery(
                "SELECT b FROM Book b WHERE b.status = :status", Book.class)
                .setParameter("status", status)
                .getResultList();
    }

    @Override
    public List<Book> findByPriceBetween(Double minPrice, Double maxPrice) {
        return entityManager.createQuery(
                "SELECT b FROM Book b WHERE b.price BETWEEN :minPrice AND :maxPrice", Book.class)
                .setParameter("minPrice", minPrice)
                .setParameter("maxPrice", maxPrice)
                .getResultList();
    }

    @Override
    public List<Book> findByPublishDateBetween(LocalDate startDate, LocalDate endDate) {
        return entityManager.createQuery(
                "SELECT b FROM Book b WHERE b.publishDate BETWEEN :startDate AND :endDate", Book.class)
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .getResultList();
    }

    @Override
    public Page<Book> findByTitleContaining(String keyword, Pageable pageable) {
        TypedQuery<Book> query = entityManager.createQuery(
                "SELECT b FROM Book b WHERE LOWER(b.title) LIKE LOWER(:keyword)", Book.class)
                .setParameter("keyword", "%" + keyword + "%");
        
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());
        
        List<Book> books = query.getResultList();
        Long total = entityManager.createQuery(
                "SELECT COUNT(b) FROM Book b WHERE LOWER(b.title) LIKE LOWER(:keyword)", Long.class)
                .setParameter("keyword", "%" + keyword + "%")
                .getSingleResult();
        
        return new PageImpl<>(books, pageable, total);
    }

    @Override
    public Page<Book> findByAuthorContaining(String keyword, Pageable pageable) {
        TypedQuery<Book> query = entityManager.createQuery(
                "SELECT b FROM Book b WHERE LOWER(b.author) LIKE LOWER(:keyword)", Book.class)
                .setParameter("keyword", "%" + keyword + "%");
        
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());
        
        List<Book> books = query.getResultList();
        Long total = entityManager.createQuery(
                "SELECT COUNT(b) FROM Book b WHERE LOWER(b.author) LIKE LOWER(:keyword)", Long.class)
                .setParameter("keyword", "%" + keyword + "%")
                .getSingleResult();
        
        return new PageImpl<>(books, pageable, total);
    }

    @Override
    public Page<Book> findByCategory(String category, Pageable pageable) {
        TypedQuery<Book> query = entityManager.createQuery(
                "SELECT b FROM Book b WHERE b.category = :category", Book.class)
                .setParameter("category", category);
        
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());
        
        List<Book> books = query.getResultList();
        Long total = entityManager.createQuery(
                "SELECT COUNT(b) FROM Book b WHERE b.category = :category", Long.class)
                .setParameter("category", category)
                .getSingleResult();
        
        return new PageImpl<>(books, pageable, total);
    }

    @Override
    public Page<Book> findByStatus(BookStatus status, Pageable pageable) {
        TypedQuery<Book> query = entityManager.createQuery(
                "SELECT b FROM Book b WHERE b.status = :status", Book.class)
                .setParameter("status", status);
        
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());
        
        List<Book> books = query.getResultList();
        Long total = entityManager.createQuery(
                "SELECT COUNT(b) FROM Book b WHERE b.status = :status", Long.class)
                .setParameter("status", status)
                .getSingleResult();
        
        return new PageImpl<>(books, pageable, total);
    }
}