package ru.otus.mar.booklibrary.repository.listener.constraint;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeDeleteEvent;
import org.springframework.stereotype.Component;
import ru.otus.mar.booklibrary.exception.ConstraintViolationException;
import ru.otus.mar.booklibrary.model.Author;
import ru.otus.mar.booklibrary.repository.BookRepository;

@Component
@RequiredArgsConstructor
public class AuthorConstraint extends AbstractMongoEventListener<Author> {

    private final BookRepository bookRepo;

    @Override
    public void onBeforeDelete(BeforeDeleteEvent<Author> event) {
        super.onBeforeDelete(event);
        bookRepo.findFirstByAuthor(new Author(event.getSource().get("_id").toString(), null))
                .ifPresent(b -> {
                    throw new ConstraintViolationException(String.format(
                            "Referential integrity constraint violation: " +
                                    "book DBRef(author) references author(_id) (_id '%s')",
                            b.getAuthor().getId()
                    ));
                });
    }
}