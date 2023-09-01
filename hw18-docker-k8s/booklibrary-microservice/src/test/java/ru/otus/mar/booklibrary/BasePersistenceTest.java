package ru.otus.mar.booklibrary;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles({"test"})
public abstract class BasePersistenceTest extends BaseContainerTest {

}
