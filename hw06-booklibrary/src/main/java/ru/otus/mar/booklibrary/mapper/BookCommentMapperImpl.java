package ru.otus.mar.booklibrary.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.mar.booklibrary.dto.BookCommentDto;
import ru.otus.mar.booklibrary.model.BookComment;

@RequiredArgsConstructor
@Service
public class BookCommentMapperImpl implements BookCommentMapper {

    private final BookMapper bookMapper;

    @Override
    public BookCommentDto toDto(BookComment comment) {
        return new BookCommentDto(comment.getId(), bookMapper.toDto(comment.getBook()), comment.getComment());
    }

    @Override
    public BookComment fromDto(BookCommentDto comment) {
        return new BookComment(comment.id(), bookMapper.fromDto(comment.book()), comment.comment());
    }
}
