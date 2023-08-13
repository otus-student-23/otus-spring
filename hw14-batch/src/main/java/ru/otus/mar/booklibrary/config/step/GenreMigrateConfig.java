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
import ru.otus.mar.booklibrary.model.document.GenreDoc;
import ru.otus.mar.booklibrary.model.entity.Genre;
import ru.otus.mar.booklibrary.service.GenreService;

@Configuration
@RequiredArgsConstructor
public class GenreMigrateConfig {

    private final EntityManagerFactory entityManagerFactory;

    private final MongoTemplate mongoTemplate;

    private final JobRepository jobRepository;

    private final PlatformTransactionManager platformTransactionManager;

    private final GenreService service;

    @Bean
    public JpaPagingItemReader<Genre> genreReader() {
        return new JpaPagingItemReaderBuilder<Genre>()
                .name("genreItemReader")
                .entityManagerFactory(entityManagerFactory)
                .queryString("from Genre")
                .build();
    }

    @Bean
    public ItemProcessor<Genre, GenreDoc> genreItemProcessor() {
        return service::buildDocument;
    }

    @Bean
    public MongoItemWriter<GenreDoc> genreWriter() {
        return new MongoItemWriterBuilder<GenreDoc>()
                .collection("genre")
                .template(mongoTemplate)
                .build();
    }

    @Bean
    public Step genreMigrateStep(ItemReader<Genre> genreReader, MongoItemWriter<GenreDoc> genreWriter,
                                  ItemProcessor<Genre, GenreDoc> genreItemProcessor) {
        return new StepBuilder("genreMigrateStep", jobRepository)
                .<Genre, GenreDoc>chunk(20, platformTransactionManager)
                .reader(genreReader)
                .processor(genreItemProcessor)
                .writer(genreWriter)
                .build();
    }
}
