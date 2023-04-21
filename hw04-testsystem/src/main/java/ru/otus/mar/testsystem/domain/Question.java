package ru.otus.mar.testsystem.domain;

import java.util.List;

public record Question(String question, List<String> options, String answer) {

}