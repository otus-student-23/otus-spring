package ru.otus.mar.booklibrary.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.mar.booklibrary.dao.BookCommentDao;
import ru.otus.mar.booklibrary.dto.BookCommentDto;
import ru.otus.mar.booklibrary.dto.BookDto;
import ru.otus.mar.booklibrary.mapper.BookCommentMapper;
import ru.otus.mar.booklibrary.mapper.BookMapper;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BookCommentServiceImpl implements BookCommentService {

    private final BookCommentDao dao;

    private final BookCommentMapper mapper;

    private final BookMapper bookMapper;

    @Override
    @Transactional
    public BookCommentDto create(BookCommentDto comment) {
        return mapper.toDto(dao.insert(mapper.fromDto(comment)));
    }

    @Override
    @Transactional
    public BookCommentDto update(BookCommentDto comment) {
        return mapper.toDto(dao.update(mapper.fromDto(comment)));
    }

    @Override
    @Transactional
    public void delete(BookCommentDto comment) {
        dao.delete(mapper.fromDto(comment));
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookCommentDto> getByBook(BookDto book) {
        return dao.getByBook(bookMapper.fromDto(book)).stream().map(mapper::toDto).toList();
    }
}