package ru.otus.mar.booklibrary.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.mar.booklibrary.dto.BookCommentDto;
import ru.otus.mar.booklibrary.dto.BookDto;
import ru.otus.mar.booklibrary.mapper.BookCommentMapper;
import ru.otus.mar.booklibrary.repository.BookCommentRepository;
import ru.otus.mar.booklibrary.repository.BookRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BookCommentServiceImpl implements BookCommentService {

    private final BookCommentRepository repo;

    private final BookCommentMapper mapper;

    private final BookRepository bookRepo;

    @Override
    @Transactional
    public BookCommentDto create(BookCommentDto comment) {
        return mapper.toDto(repo.save(mapper.fromDto(comment)));
    }

    @Override
    @Transactional
    public BookCommentDto update(BookCommentDto comment) {
        return mapper.toDto(repo.save(mapper.fromDto(comment)));
    }

    @Override
    @Transactional
    public void delete(BookCommentDto comment) {
        repo.deleteById(comment.id());
    }

    @Override
    public List<BookCommentDto> getAll() {
        throw new UnsupportedOperationException();
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookCommentDto> getByBook(BookDto book) {
        return bookRepo.findById(book.getId()).get().getComments().stream().map(mapper::toDto).toList();
    }
}
