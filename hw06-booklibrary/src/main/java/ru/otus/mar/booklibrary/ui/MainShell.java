package ru.otus.mar.booklibrary.ui;

import org.springframework.shell.Availability;

public interface MainShell {

    void selectEntity(Object item, String prompt);

    Object getSelectedEntity();

    Availability isEntityClassSelected(Class clazz);

    void reset();
}