package ru.otus.mar.booklibrary.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.mar.booklibrary.model.Genre;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class GenreDaoJpa implements GenreDao {

    @PersistenceContext
    private final EntityManager em;

    @Override
    public Genre insert(Genre genre) {
        em.persist(genre);
        return genre;
    }

    @Override
    public Genre update(Genre genre) {
        return em.merge(genre);
    }

    @Override
    public void delete(Genre genre) {
        em.remove(em.contains(genre) ? genre : em.find(Genre.class, genre.getId()));
    }

    @Override
    public List<Genre> getAll() {
        return em.createQuery("select a from Genre a", Genre.class).getResultList();
    }

    @Override
    public Genre getById(UUID id) {
        return em.find(Genre.class, id);
    }

    @Override
    public Optional<Genre> getByName(String name) {
        TypedQuery<Genre> query = em.createQuery("select a from Genre a where a.name = :name", Genre.class);
        query.setParameter("name", name);
        return query.getResultList().stream().findFirst();
    }
}
