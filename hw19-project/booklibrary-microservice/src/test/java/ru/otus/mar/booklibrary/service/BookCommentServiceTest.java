package ru.otus.mar.booklibrary.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import ru.otus.mar.booklibrary.dto.BookCommentDto;
import ru.otus.mar.booklibrary.dto.BookDto;
import ru.otus.mar.booklibrary.mapper.AuthorMapperImpl;
import ru.otus.mar.booklibrary.mapper.BookCommentMapperImpl;
import ru.otus.mar.booklibrary.mapper.BookMapperImpl;
import ru.otus.mar.booklibrary.mapper.GenreMapperImpl;
import ru.otus.mar.booklibrary.model.Book;
import ru.otus.mar.booklibrary.model.BookComment;
import ru.otus.mar.booklibrary.repository.BookCommentRepository;
import ru.otus.mar.booklibrary.repository.BookRepository;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = {BookCommentServiceImpl.class,
        BookCommentMapperImpl.class, BookMapperImpl.class, AuthorMapperImpl.class, GenreMapperImpl.class})
@ActiveProfiles({"test"})
public class BookCommentServiceTest {

    private static final BookCommentDto COMMENT_DTO = new BookCommentDto(null,
            new BookDto("Book_1", null, null), LocalDateTime.MIN, null, "Comment_1");

    private static final BookComment COMMENT = new BookComment(null,
            new Book("Book_1", null, null), LocalDateTime.MIN, null, "Comment_1");

    @Autowired
    private BookCommentService service;

    @MockBean
    private BookCommentRepository repo;

    @MockBean
    private BookRepository bookRepo;

    @Test
    void create() {
        when(repo.save(any(BookComment.class))).thenReturn(COMMENT);
        assertEquals(COMMENT_DTO.getComment(), service.create(COMMENT_DTO).getComment());
    }

    @Test
    void update() {
        when(repo.save(any())).thenReturn(COMMENT);
        assertEquals(COMMENT_DTO.getComment(), service.update(COMMENT_DTO).getComment());
    }

    @Test
    void delete() {
        service.delete(COMMENT_DTO.getId());
        verify(repo, times(1)).deleteById(COMMENT.getId());
    }
}
