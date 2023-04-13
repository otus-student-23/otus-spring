package ru.otus.mar.testsystem.service;

import ru.otus.mar.testsystem.domain.Test;
import ru.otus.mar.testsystem.domain.TestType;

public interface TestService {

    Test createTest();

    Test createTest(TestType typeCode);

    String printTestResult(Test test);
}
