package ru.otus.mar.booklibrary.repository.listener;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.AfterDeleteEvent;
import org.springframework.stereotype.Component;
import ru.otus.mar.booklibrary.model.Book;
import ru.otus.mar.booklibrary.repository.BookCommentRepository;

@Component
@RequiredArgsConstructor
public class BookEventListener extends AbstractMongoEventListener<Book> {

    private final BookCommentRepository bookCommentRepo;

    @Override
    public void onAfterDelete(AfterDeleteEvent<Book> event) {
        super.onAfterDelete(event);
        bookCommentRepo.deleteByBookId(event.getSource().get("_id").toString());
    }
}
