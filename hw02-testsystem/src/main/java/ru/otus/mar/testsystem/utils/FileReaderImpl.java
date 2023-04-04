package ru.otus.mar.testsystem.utils;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Component
public class FileReaderImpl implements FileReader {

    @Override
    public List<String> fileToList(String fileName) {
        List<String> result = new ArrayList<>();
        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(
                        getClass().getClassLoader().getResourceAsStream(fileName), StandardCharsets.UTF_8)
        )) {
            String line;
            while ((line = in.readLine()) != null) {
                    result.add(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }
}
