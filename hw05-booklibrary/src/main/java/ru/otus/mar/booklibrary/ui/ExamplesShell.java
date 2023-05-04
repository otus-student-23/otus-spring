package ru.otus.mar.booklibrary.ui;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class ExamplesShell {

    @ShellMethod(value = "examples", key = {"e", "example"})
    public void print() {
        System.out.println("-------------------------");
        System.out.println("добавить автора: a a -n \"author name\"");
        System.out.println("полный список авторов: l a");
        System.out.println("odata-фильтр: l a -f \"substringof(toupper(name), 'PUSHKIN')\"");

        System.out.println("-------------------------");
        System.out.println("добавить жанр: a g -n \"genre name\"");
        System.out.println("полный список жанров: l g");
        System.out.println("odata-фильтр: l g -f \"(name eq 'study') or (name eq 'novel')\"");

        System.out.println("-------------------------");
        System.out.println("добавить книгу: a b -n \"book name\" -a \"book name\" -g \"book name\"");
        System.out.println("полный список книг: l b");
        System.out.println("odata-фильтр: l b -f \"substringof(toupper(author), 'PUSHKIN')\"");
        System.out.println("удалить книгу: d b -n \"book name\" -a \"book name\"");
    }
}
