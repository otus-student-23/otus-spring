package ru.otus.mar.booklibrary.config.step;

import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.batch.item.data.builder.MongoItemWriterBuilder;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.transaction.PlatformTransactionManager;
import ru.otus.mar.booklibrary.cache.BookCache;
import ru.otus.mar.booklibrary.model.document.BookCommentDoc;
import ru.otus.mar.booklibrary.model.document.BookDoc;
import ru.otus.mar.booklibrary.model.entity.BookComment;

@Configuration
@RequiredArgsConstructor
public class CommentMigrateConfig {

    private final EntityManagerFactory entityManagerFactory;

    private final MongoTemplate mongoTemplate;

    private final JobRepository jobRepository;

    private final PlatformTransactionManager platformTransactionManager;

    private final BookCache bookCache;

    @Bean
    public JpaPagingItemReader<BookComment> commentReader() {
        return new JpaPagingItemReaderBuilder<BookComment>()
                .name("commentItemReader")
                .entityManagerFactory(entityManagerFactory)
                .queryString("from BookComment")
                .build();
    }

    @Bean
    public ItemProcessor<BookComment, BookCommentDoc> commentItemProcessor() {
        return (item) -> {
            BookDoc book = new BookDoc();
            book.setId(bookCache.get(item.getBook().getId()));
            return new BookCommentDoc(ObjectId.get(), book, item.getComment());
        };
    }

    @Bean
    public MongoItemWriter<BookCommentDoc> commentWriter() {
        return new MongoItemWriterBuilder<BookCommentDoc>()
                .collection("book_comment")
                .template(mongoTemplate)
                .build();
    }

    @Bean
    public Step commentMigrateStep(ItemReader<BookComment> commentReader, MongoItemWriter<BookCommentDoc> commentWriter,
                                  ItemProcessor<BookComment, BookCommentDoc> commentItemProcessor) {
        return new StepBuilder("commentMigrateStep", jobRepository)
                .<BookComment, BookCommentDoc>chunk(20, platformTransactionManager)
                .reader(commentReader)
                .processor(commentItemProcessor)
                .writer(commentWriter)
                .build();
    }
}
