package ru.otus.mar.booklibrary.ui;

import org.springframework.shell.Availability;

public interface EntityProvider {

    void selectEntity(Object entity, String prompt);

    Object getSelectedEntity();

    Availability isEntityClassSelected(Class clazz);

    void reset();
}