package ru.otus.mar.booklibrary.repository.listener;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.AfterSaveEvent;
import org.springframework.data.mongodb.core.mapping.event.BeforeDeleteEvent;
import org.springframework.stereotype.Component;
import ru.otus.mar.booklibrary.exception.ConstraintViolationException;
import ru.otus.mar.booklibrary.model.Author;
import ru.otus.mar.booklibrary.repository.BookRepository;

@Component
@RequiredArgsConstructor
public class AuthorEventListener extends AbstractMongoEventListener<Author> {

    private final BookRepository bookRepo;

    @Override
    public void onAfterSave(AfterSaveEvent<Author> event) {
        super.onAfterSave(event);
        bookRepo.findByAuthorId(event.getSource().getId()).forEach(b -> {
            b.setAuthor(event.getSource());
            bookRepo.save(b);
        });
    }

    @Override
    public void onBeforeDelete(BeforeDeleteEvent<Author> event) {
        super.onBeforeDelete(event);
        if (bookRepo.existsByAuthorId(event.getSource().get("_id").toString())) {
            throw new ConstraintViolationException(String.format(
                    "Referential integrity constraint violation: book DBRef(author) references author(_id) (_id '%s')",
                    event.getSource().get("_id").toString()
            ));
        }
    }
}