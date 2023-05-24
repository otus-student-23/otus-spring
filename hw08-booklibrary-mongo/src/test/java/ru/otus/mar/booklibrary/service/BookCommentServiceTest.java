package ru.otus.mar.booklibrary.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.mar.booklibrary.dto.BookCommentDto;
import ru.otus.mar.booklibrary.dto.BookDto;
import ru.otus.mar.booklibrary.mapper.BookCommentMapper;
import ru.otus.mar.booklibrary.model.Book;
import ru.otus.mar.booklibrary.model.BookComment;
import ru.otus.mar.booklibrary.repository.BookCommentRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookCommentServiceTest {

    private static final BookCommentDto COMMENT_DTO = new BookCommentDto(null,
            new BookDto("Book_1", null, null), "Comment_1");

    private static final BookComment COMMENT = new BookComment(null,
            new Book("Book_1", null, null), "Comment_1");

    @InjectMocks
    private BookCommentServiceImpl service;

    @Mock
    private BookCommentRepository repo;

    @Mock
    private BookCommentMapper mapper;

    @Test
    void create() {
        when(repo.save(COMMENT)).thenReturn(COMMENT);
        when(mapper.toDto(COMMENT)).thenReturn(COMMENT_DTO);
        when(mapper.fromDto(COMMENT_DTO)).thenReturn(COMMENT);
        assertEquals(COMMENT_DTO, service.create(COMMENT_DTO));
    }

    @Test
    void update() {
        when(repo.save(any())).thenReturn(COMMENT);
        when(mapper.toDto(COMMENT)).thenReturn(COMMENT_DTO);
        when(mapper.fromDto(COMMENT_DTO)).thenReturn(COMMENT);
        assertEquals(COMMENT_DTO, service.update(COMMENT_DTO));
    }

    @Test
    void delete() {
        service.delete(COMMENT_DTO);
        verify(repo, times(1)).deleteById(COMMENT.getId());
    }
}
