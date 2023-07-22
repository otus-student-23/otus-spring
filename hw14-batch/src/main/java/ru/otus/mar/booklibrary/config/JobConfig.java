package ru.otus.mar.booklibrary.config;

import com.mongodb.client.model.IndexOptions;
import lombok.RequiredArgsConstructor;
import org.bson.Document;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.lang.NonNull;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@RequiredArgsConstructor
public class JobConfig {

    private final MongoTemplate mongoTemplate;

    private final JobRepository jobRepository;

    private final PlatformTransactionManager platformTransactionManager;

    @Bean
    public Job bookLibraryMigrateJob(Step authorMigrateStep, Step genreMigrateStep,
                                     Step bookMigrateStep, Step commentMigrateStep) {
        return new JobBuilder("bookLibraryMigrateJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(initMongoDbStep())
                .next(authorMigrateStep)
                .next(genreMigrateStep)
                .next(bookMigrateStep)
                .next(commentMigrateStep)
                .listener(new JobExecutionListener() {
                    @Override
                    public void beforeJob(@NonNull JobExecution jobExecution) {
                        System.out.println("JOB START");
                    }

                    @Override
                    public void afterJob(@NonNull JobExecution jobExecution) {
                        System.out.println("JOB DONE");
                    }
                })
                .build();
    }

    @Bean
    public Step initMongoDbStep() {
        return new StepBuilder("initMongoDbStep", jobRepository)
                .tasklet((contribution, chunkContext) -> {
                    mongoTemplate.getDb().drop();
                    mongoTemplate.getCollection("author")
                            .createIndex(new Document("name", 1), new IndexOptions().name("name_idx").unique(true));
                    mongoTemplate.getCollection("genre")
                            .createIndex(new Document("name", 1), new IndexOptions().name("name_idx").unique(true));
                    mongoTemplate.getCollection("book")
                            .createIndex(
                                    new Document("name", 1).append("author", 1),
                                    new IndexOptions().name("name_author_idx").unique(true)
                            );
                    mongoTemplate.getCollection("book_comment")
                            .createIndex(new Document("book", 1), new IndexOptions().name("book_idx").unique(false));
                    return RepeatStatus.FINISHED;
                    }, platformTransactionManager)
                .build();
    }
}
