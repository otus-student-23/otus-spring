package ru.otus.mar.booklibrary.repository.listener.constraint;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeDeleteEvent;
import org.springframework.stereotype.Component;
import ru.otus.mar.booklibrary.exception.ConstraintViolationException;
import ru.otus.mar.booklibrary.model.Genre;
import ru.otus.mar.booklibrary.repository.BookRepository;

@Component
@RequiredArgsConstructor
public class GenreConstraint extends AbstractMongoEventListener<Genre> {

    private final BookRepository bookRepo;

    @Override
    public void onBeforeDelete(BeforeDeleteEvent<Genre> event) {
        super.onBeforeDelete(event);
        bookRepo.findFirstByGenre(new Genre(event.getSource().get("_id").toString(), null))
                .ifPresent(b -> {
                    throw new ConstraintViolationException(String.format(
                            "Referential integrity constraint violation: " +
                                    "book DBRef(genre) references genre(_id) (_id '%s')",
                            b.getGenre().getId()
                    ));
                });
    }
}