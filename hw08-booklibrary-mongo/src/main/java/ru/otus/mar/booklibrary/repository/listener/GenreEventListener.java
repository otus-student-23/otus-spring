package ru.otus.mar.booklibrary.repository.listener;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.AfterSaveEvent;
import org.springframework.data.mongodb.core.mapping.event.BeforeDeleteEvent;
import org.springframework.stereotype.Component;
import ru.otus.mar.booklibrary.exception.ConstraintViolationException;
import ru.otus.mar.booklibrary.model.Genre;
import ru.otus.mar.booklibrary.repository.BookRepository;

@Component
@RequiredArgsConstructor
public class GenreEventListener extends AbstractMongoEventListener<Genre> {

    private final BookRepository bookRepo;

    @Override
    public void onAfterSave(AfterSaveEvent<Genre> event) {
        super.onAfterSave(event);
        bookRepo.findByGenreId(event.getSource().getId()).forEach(b -> {
            b.setGenre(event.getSource());
            bookRepo.save(b);
        });
    }

    @Override
    public void onBeforeDelete(BeforeDeleteEvent<Genre> event) {
        super.onBeforeDelete(event);
        if (bookRepo.existsByGenreId(event.getSource().get("_id").toString())) {
            throw new ConstraintViolationException(String.format(
                    "Referential integrity constraint violation: book DBRef(genre) references genre(_id) (_id '%s')",
                    event.getSource().get("_id").toString()
            ));
        }
    }
}