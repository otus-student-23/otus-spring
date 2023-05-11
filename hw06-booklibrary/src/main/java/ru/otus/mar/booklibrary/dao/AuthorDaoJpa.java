package ru.otus.mar.booklibrary.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.mar.booklibrary.model.Author;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class AuthorDaoJpa implements AuthorDao {

    @PersistenceContext
    private final EntityManager em;

    @Override
    public Author insert(Author author) {
        em.persist(author);
        return author;
    }

    @Override
    public Author update(Author author) {
        return em.merge(author);
    }

    @Override
    public void delete(Author author) {
        em.remove(em.contains(author) ? author : em.find(Author.class, author.getId()));
    }

    @Override
    public List<Author> getAll() {
        return em.createQuery("select a from Author a", Author.class).getResultList();
    }

    @Override
    public Author getById(UUID id) {
        return em.find(Author.class, id);
    }

    @Override
    public Optional<Author> getByName(String name) {
        TypedQuery<Author> query = em.createQuery("select a from Author a where a.name = :name", Author.class);
        query.setParameter("name", name);
        return query.getResultList().stream().findFirst();
    }
}
