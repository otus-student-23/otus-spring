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
import ru.otus.mar.booklibrary.model.document.AuthorDoc;
import ru.otus.mar.booklibrary.model.entity.Author;

@Configuration
@RequiredArgsConstructor
public class AuthorMigrateConfig {

    private final EntityManagerFactory entityManagerFactory;

    private final MongoTemplate mongoTemplate;

    private final JobRepository jobRepository;

    private final PlatformTransactionManager platformTransactionManager;

    @Bean
    public JpaPagingItemReader<Author> authorReader() {
        return new JpaPagingItemReaderBuilder<Author>()
                .name("authorItemReader")
                .entityManagerFactory(entityManagerFactory)
                .queryString("from Author")
                .build();
    }

    @Bean
    public ItemProcessor<Author, AuthorDoc> authorItemProcessor() {
        return (item) -> new AuthorDoc(ObjectId.get(), item.getName());
    }

    @Bean
    public MongoItemWriter<AuthorDoc> authorWriter() {
        return new MongoItemWriterBuilder<AuthorDoc>()
                .collection("author")
                .template(mongoTemplate)
                .build();
    }

    @Bean
    public Step authorMigrateStep(ItemReader<Author> authorReader, MongoItemWriter<AuthorDoc> authorWriter,
                                  ItemProcessor<Author, AuthorDoc> authorItemProcessor) {
        return new StepBuilder("authorMigrateStep", jobRepository)
                .<Author, AuthorDoc>chunk(20, platformTransactionManager)
                .reader(authorReader)
                .processor(authorItemProcessor)
                .writer(authorWriter)
                .build();
    }
}
