package ru.otus.mar.booklibrary.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.mar.booklibrary.dto.BookCommentDto;
import ru.otus.mar.booklibrary.dto.BookDto;
import ru.otus.mar.booklibrary.mapper.BookCommentMapper;
import ru.otus.mar.booklibrary.model.Book;
import ru.otus.mar.booklibrary.repository.BookCommentRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BookCommentServiceImpl implements BookCommentService {

    private final BookCommentRepository repo;

    private final BookCommentMapper mapper;

    @Override
    public BookCommentDto create(BookCommentDto comment) {
        return mapper.toDto(repo.save(mapper.fromDto(comment)));
    }

    @Override
    public BookCommentDto update(BookCommentDto comment) {
        return mapper.toDto(repo.save(mapper.fromDto(comment)));
    }

    @Override
    public void delete(BookCommentDto comment) {
        repo.deleteById(comment.id());
    }

    @Override
    public List<BookCommentDto> getAll() {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<BookCommentDto> getByBook(BookDto book) {
        return repo.findByBook(new Book(book.getId(), null, null, null)).stream().map(mapper::toDto).toList();
    }
}
