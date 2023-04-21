package ru.otus.mar.testsystem.service;

import ru.otus.mar.testsystem.domain.Test;
import ru.otus.mar.testsystem.domain.TestType;
import ru.otus.mar.testsystem.domain.User;

public interface TestService {

    Test createTest(User user);

    Test createTest(User user, TestType typeCode);

    String printTestResult(Test test);
}
