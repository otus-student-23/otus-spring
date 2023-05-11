package ru.otus.mar.booklibrary.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.mar.booklibrary.model.BookComment;

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
    public BookComment getById(UUID id) {
        return em.find(BookComment.class, id);
    }
}
