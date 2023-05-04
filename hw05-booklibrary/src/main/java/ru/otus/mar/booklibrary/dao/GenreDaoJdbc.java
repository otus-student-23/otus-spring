package ru.otus.mar.booklibrary.dao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Service;
import ru.otus.mar.booklibrary.model.Genre;
import ru.otus.mar.booklibrary.service.FilterExpression;
import ru.otus.mar.booklibrary.service.FilterExpressionParser;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class GenreDaoJdbc implements GenreDao {

    private final NamedParameterJdbcOperations jdbc;

    private final FilterExpressionParser filterExpressionParser;

    public GenreDaoJdbc(NamedParameterJdbcOperations namedParameterJdbcOperations,
                         FilterExpressionParser filterExpressionParser) {
        this.jdbc = namedParameterJdbcOperations;
        this.filterExpressionParser = filterExpressionParser;
    }

    @Override
    public Genre create(Genre genre) {
        jdbc.update("insert into genre (name) values (:name)", Map.of("name", genre.name()));
        return getByName(genre.name()).get();
    }

    @Override
    public Genre update(Genre genre) {
        jdbc.update("update genre set name = :name where id = :id", Map.of("id", genre.id(), "name", genre.name()));
        return getById(genre.id()).get();
    }

    @Override
    public void deleteById(long id) {
        jdbc.update("delete from genre where id = :id", Map.of("id", id));
    }

    @Override
    public List<Genre> getAll() {
        return jdbc.query("select id, name from genre", new GenreMapper());
    }

    @Override
    public List<Genre> getByFilter(String filter) {
        FilterExpression e = filterExpressionParser.parse(filter);
        return jdbc.getJdbcOperations().query("select id, name from genre where " + e.toString(),
                new GenreDaoJdbc.GenreMapper(),
                e.getParams().toArray());
    }

    @Override
    public Optional<Genre> getById(long id) {
        return jdbc.query("select id, name from genre where id = :id", Map.of("id", id), new GenreMapper())
                .stream().findFirst();
    }

    @Override
    public Optional<Genre> getByName(String name) {
        return jdbc.query("select id, name from genre where name = :name", Map.of("name", name), new GenreMapper())
                .stream().findFirst();
    }

    private static class GenreMapper implements RowMapper<Genre> {

        @Override
        public Genre mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Genre(rs.getLong("id"), rs.getString("name"));
        }
    }
}
