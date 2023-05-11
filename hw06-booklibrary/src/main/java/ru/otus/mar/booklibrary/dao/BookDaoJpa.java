package ru.otus.mar.booklibrary.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.mar.booklibrary.model.Author;
import ru.otus.mar.booklibrary.model.Book;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class BookDaoJpa implements BookDao {

    @PersistenceContext
    private final EntityManager em;

    @Override
    public Book insert(Book book) {
        em.persist(book);
        return book;
    }

    @Override
    public Book update(Book book) {
        return em.merge(book);
    }

    @Override
    public void delete(Book book) {
        em.remove(em.contains(book) ? book : em.find(Book.class, book.getId()));
    }

    @Override
    public List<Book> getAll() {
        return em.createQuery("select b from Book b join fetch b.author join fetch b.genre",
                Book.class).getResultList();
    }

    @Override
    public Book getById(UUID id) {
        return em.find(Book.class, id);
    }

    @Override
    public Optional<Book> getByNameAndAuthor(String name, Author author) {
        TypedQuery<Book> query = em.createQuery("""
                select b from Book b
                    join fetch b.author join fetch b.genre
                where b.name = :name and b.author = :author""",
                Book.class);
        query.setParameter("name", name);
        query.setParameter("author", author);
        return query.getResultList().stream().findFirst();
    }

    @Override
    public List<Book> getByName(String name) {
        TypedQuery<Book> query = em.createQuery("""
                select b from Book b
                    join fetch b.author join fetch b.genre
                where b.name = :name""",
                Book.class);
        query.setParameter("name", name);
        return query.getResultList();
    }
}
