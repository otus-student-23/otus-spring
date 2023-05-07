package ru.otus.mar.booklibrary.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.mar.booklibrary.model.Book;
import ru.otus.mar.booklibrary.model.BookComment;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class BookCommentDaoJpa implements BookCommentDao {

    @PersistenceContext
    private final EntityManager em;

    @Override
    public BookComment insert(BookComment comment) {
        em.persist(comment);
        return comment;
    }

    @Override
    public BookComment update(BookComment comment) {
        return em.merge(comment);
    }

    @Override
    public void delete(BookComment comment) {
        em.remove(em.contains(comment) ? comment : em.find(BookComment.class, comment.getId()));
    }

    @Override
    public void deleteByBook(Book book) {
        Query query = em.createQuery("delete from BookComment c where c.book = :book");
        query.setParameter("book", book);
        query.executeUpdate();
    }

    @Override
    public BookComment getById(UUID id) {
        return em.find(BookComment.class, id);
    }

    @Override
    public List<BookComment> getByBook(Book book) {
        TypedQuery<BookComment> query =
                em.createQuery("select c from BookComment c where c.book = :book", BookComment.class);
        query.setParameter("book", book);
        return query.getResultList();
    }
}
