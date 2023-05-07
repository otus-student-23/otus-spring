package ru.otus.mar.booklibrary.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.mar.booklibrary.dao.BookCommentDao;
import ru.otus.mar.booklibrary.dto.AuthorDto;
import ru.otus.mar.booklibrary.dto.BookCommentDto;
import ru.otus.mar.booklibrary.dto.BookDto;
import ru.otus.mar.booklibrary.dto.GenreDto;
import ru.otus.mar.booklibrary.model.Author;
import ru.otus.mar.booklibrary.model.Book;
import ru.otus.mar.booklibrary.model.BookComment;
import ru.otus.mar.booklibrary.model.Genre;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class BookCommentServiceTest {

    private static final BookCommentDto COMMENT_DTO = new BookCommentDto(null,
            new BookDto("Book_1", new AuthorDto("Author_1"), new GenreDto("Genre_1")), "Comment_1");

    private static final BookComment COMMENT = new BookComment(null,
            new Book("Book_1", new Author("Author_1"), new Genre("Genre_1")), "Comment_1");

    @Autowired
    private BookCommentService service;

    @MockBean
    private BookCommentDao dao;

    @Test
    void create() {
        when(dao.insert(COMMENT)).thenReturn(COMMENT);
        assertEquals(COMMENT_DTO, service.create(COMMENT_DTO));
    }

    @Test
    void update() {
        when(dao.update(any())).thenReturn(COMMENT);
        assertEquals(COMMENT_DTO, service.update(COMMENT_DTO));
    }

    @Test
    void delete() {
        service.delete(COMMENT_DTO);
        verify(dao, times(1)).delete(COMMENT);
    }
}
