package ru.otus.mar.booklibrary.dao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Service;
import ru.otus.mar.booklibrary.model.Author;
import ru.otus.mar.booklibrary.model.Book;
import ru.otus.mar.booklibrary.model.Genre;
import ru.otus.mar.booklibrary.service.FilterExpression;
import ru.otus.mar.booklibrary.service.FilterExpressionParser;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class BookDaoJdbc implements BookDao {

    private final NamedParameterJdbcOperations jdbc;

    private final FilterExpressionParser filterExpressionParser;

    public BookDaoJdbc(NamedParameterJdbcOperations namedParameterJdbcOperations,
                         FilterExpressionParser filterExpressionParser) {
        this.jdbc = namedParameterJdbcOperations;
        this.filterExpressionParser = filterExpressionParser;
    }

    @Override
    public Book create(Book book) {
        jdbc.update("insert into book (name, author_id, genre_id) values (:name, :author_id, :genre_id)",
                Map.of("name", book.getName(), "author_id", book.getAuthor().id(), "genre_id", book.getGenre().id()));
        return getByNameAndAuthor(book.getName(), book.getAuthor()).get();
    }

    @Override
    public Book update(Book book) {
        jdbc.update("update book set name = :name where id = :id", Map.of("id", book.getId(), "name", book.getName()));
        return getById(book.getId()).get();
    }

    @Override
    public void deleteById(long id) {
        jdbc.update("delete from book where id = :id", Map.of("id", id));
    }

    @Override
    public List<Book> getAll() {
        return jdbc.query("""
                select b.id, b.name, b.author_id, a.name as author, b.genre_id, g.name as genre
                from book b
                    inner join author a on b.author_id = a.id
                    inner join genre g on b.genre_id = g.id""", new BookMapper());
    }

    @Override
    public List<Book> getByFilter(String filter) {
        FilterExpression e = filterExpressionParser.parse(filter);
        return jdbc.getJdbcOperations().query("""
                        select * from (
                        select b.id, b.name, b.author_id, a.name as author, b.genre_id, g.name as genre
                        from book b
                            inner join author a on b.author_id = a.id
                            inner join genre g on b.genre_id = g.id
                        ) where
                        """ + e.toString(),
                new BookDaoJdbc.BookMapper(),
                e.getParams().toArray());
    }

    @Override
    public Optional<Book> getById(long id) {
        return jdbc.query("""
                        select b.id, b.name, b.author_id, a.name as author, b.genre_id, g.name as genre
                        from book b
                            inner join author a on b.author_id = a.id
                            inner join genre g on b.genre_id = g.id
                        where b.id = :id""",
                        Map.of("id", id), new BookMapper())
                .stream().findFirst();
    }

    @Override
    public Optional<Book> getByNameAndAuthor(String name, Author author) {
        return jdbc.query("""
                        select b.id, b.name, b.author_id, a.name as author, b.genre_id, g.name as genre
                        from book b
                            inner join author a on b.author_id = a.id
                            inner join genre g on b.genre_id = g.id
                        where b.name = :name and b.author_id = :author_id""",
                        Map.of("name", name, "author_id", author.id()), new BookMapper())
                .stream().findFirst();
    }

    private static class BookMapper implements RowMapper<Book> {

        @Override
        public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Book(rs.getLong("id"), rs.getString("name"),
                    new Author(rs.getLong("author_id"), rs.getString("author")),
                    new Genre(rs.getLong("genre_id"), rs.getString("genre")));
        }
    }
}
