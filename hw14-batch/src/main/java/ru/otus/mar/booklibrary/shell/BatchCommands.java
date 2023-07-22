package ru.otus.mar.booklibrary.shell;

import lombok.RequiredArgsConstructor;
import org.h2.tools.Console;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.Date;
import java.util.Properties;

@RequiredArgsConstructor
@ShellComponent
public class BatchCommands {

    private final JobOperator jobOperator;

    private final JobExplorer jobExplorer;

    @ShellMethod(value = "console", key = {"c", "console"})
    public void console() throws Exception {
        Console.main();
    }

    @ShellMethod(value = "run", key = {"r", "run"})
    public void run() throws Exception {
        Properties props = new Properties();
        props.put("startTime", (new Date()).toString());
        Long executionId = jobOperator.start("bookLibraryMigrateJob", props);
        System.out.println(jobOperator.getSummary(executionId));
    }

    @ShellMethod(value = "info", key = {"i", "info"})
    public void info() {
        System.out.println(jobExplorer.getJobNames());
        System.out.println(jobExplorer.getLastJobInstance("bookLibraryMigrateJob"));
    }
}
