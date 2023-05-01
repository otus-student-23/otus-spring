package ru.otus.mar.booklibrary.dao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Service;
import ru.otus.mar.booklibrary.model.Author;
import ru.otus.mar.booklibrary.service.FilterExpression;
import ru.otus.mar.booklibrary.service.FilterExpressionParser;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class AuthorDaoJdbc implements AuthorDao {

    private final NamedParameterJdbcOperations jdbc;

    private final FilterExpressionParser filterExpressionParser;

    public AuthorDaoJdbc(NamedParameterJdbcOperations namedParameterJdbcOperations,
                         FilterExpressionParser filterExpressionParser) {
        this.jdbc = namedParameterJdbcOperations;
        this.filterExpressionParser = filterExpressionParser;
    }

    @Override
    public Author create(Author author) {
        jdbc.update("insert into author (name) values (:name)", Map.of("name", author.name()));
        return getByName(author.name()).get();
    }

    @Override
    public Author update(Author author) {
        jdbc.update("update author set name = :name where id = :id", Map.of("id", author.id(), "name", author.name()));
        return getById(author.id()).get();
    }

    @Override
    public void deleteById(long id) {
        jdbc.update("delete from author where id = :id", Map.of("id", id));
    }

    @Override
    public List<Author> getAll() {
        return jdbc.query("select id, name from author", new AuthorMapper());
    }

    @Override
    public List<Author> getByFilter(String filter) {
        FilterExpression e = filterExpressionParser.parse(filter);
        return jdbc.getJdbcOperations().query("select id, name from author where " + e.toString(),
                new AuthorMapper(),
                e.getParams().toArray());
    }

    @Override
    public Optional<Author> getById(long id) {
        return jdbc.query("select id, name from author where id = :id", Map.of("id", id), new AuthorMapper())
                .stream().findFirst();
    }

    @Override
    public Optional<Author> getByName(String name) {
        return jdbc.query("select id, name from author where name = :name", Map.of("name", name), new AuthorMapper())
                .stream().findFirst();
    }

    private static class AuthorMapper implements RowMapper<Author> {

        @Override
        public Author mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Author(rs.getLong("id"), rs.getString("name"));
        }
    }
}
