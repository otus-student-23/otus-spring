package ru.otus.mar.booklibrary.config.step;

import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
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
import ru.otus.mar.booklibrary.service.BookService;
import ru.otus.mar.booklibrary.model.document.BookDoc;
import ru.otus.mar.booklibrary.model.entity.Book;

@Configuration
@RequiredArgsConstructor
public class BookMigrateConfig {

    private final EntityManagerFactory entityManagerFactory;

    private final MongoTemplate mongoTemplate;

    private final JobRepository jobRepository;

    private final PlatformTransactionManager platformTransactionManager;

    private final BookService service;

    @Bean
    public JpaPagingItemReader<Book> bookReader() {
        return new JpaPagingItemReaderBuilder<Book>()
                .name("bookItemReader")
                .entityManagerFactory(entityManagerFactory)
                .queryString("select b from Book b join fetch b.author")
                .build();
    }

    @Bean
    public ItemProcessor<Book, BookDoc> bookItemProcessor() {
        return service::buildDocument;
    }

    @Bean
    public MongoItemWriter<BookDoc> bookWriter() {
        return new MongoItemWriterBuilder<BookDoc>()
                .collection("book")
                .template(mongoTemplate)
                .build();
    }

    @Bean
    public Step bookMigrateStep(ItemReader<Book> bookReader, MongoItemWriter<BookDoc> bookWriter,
                                  ItemProcessor<Book, BookDoc> bookItemProcessor) {
        return new StepBuilder("bookMigrateStep", jobRepository)
                .<Book, BookDoc>chunk(20, platformTransactionManager)
                .reader(bookReader)
                .processor(bookItemProcessor)
                .writer(bookWriter)
                .build();
    }
}
