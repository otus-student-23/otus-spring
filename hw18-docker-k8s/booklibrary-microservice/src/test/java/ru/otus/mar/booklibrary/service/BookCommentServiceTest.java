package ru.otus.mar.booklibrary.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.mar.booklibrary.BasePersistenceTest;
import ru.otus.mar.booklibrary.dto.BookCommentDto;
import ru.otus.mar.booklibrary.dto.BookDto;
import ru.otus.mar.booklibrary.model.Book;
import ru.otus.mar.booklibrary.model.BookComment;
import ru.otus.mar.booklibrary.repository.BookCommentRepository;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class BookCommentServiceTest extends BasePersistenceTest {

    private static final BookCommentDto COMMENT_DTO = new BookCommentDto(null,
            new BookDto("Book_1", null, null), new Date(0), "Comment_1");

    private static final BookComment COMMENT = new BookComment(null,
            new Book("Book_1", null, null), new Date(0), "Comment_1");

    @Autowired
    private BookCommentService service;

    @MockBean
    private BookCommentRepository repo;

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
