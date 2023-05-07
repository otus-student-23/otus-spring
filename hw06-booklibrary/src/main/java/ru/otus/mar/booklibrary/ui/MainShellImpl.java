package ru.otus.mar.booklibrary.ui;

import org.h2.tools.Console;
import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;
import org.springframework.shell.Availability;
import org.springframework.shell.jline.PromptProvider;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.io.IOException;
import java.io.InputStream;

@ShellComponent
public class MainShellImpl implements MainShell, PromptProvider {

    private Object entity;

    private String promt;

    @ShellMethod(value = "console", key = {"c", "console"})
    public void console() throws Exception {
        Console.main();
    }

    @ShellMethod(value = "reset", key = {"r", "reset"})
    public void reset() {
        entity = null;
    }

    @ShellMethod(value = "howto", key = {"h", "howto"})
    public void howto() throws IOException {
        try (InputStream in = getClass().getClassLoader().getResourceAsStream("howto.txt")) {
            byte[] buffer = new byte[1024];
            for (int n; (n = in.read(buffer, 0, buffer.length)) > 0; ) {
                System.out.write(buffer, 0, n);
            }
        }
    }

    @Override
    public AttributedString getPrompt() {
        return (entity == null)
                ? new AttributedString("shell:> ", AttributedStyle.DEFAULT.foreground(AttributedStyle.YELLOW))
                : new AttributedString(promt, AttributedStyle.DEFAULT.foreground(AttributedStyle.BLUE));
    }

    @Override
    public void selectEntity(Object entity, String promt) {
        this.entity = entity;
        this.promt = promt == null ? null : String.format(
                "%s[%s]:> ",
                entity.getClass().getSimpleName().substring(0, entity.getClass().getSimpleName().length() - 3),
                promt.length() > 32 ? promt.substring(0, 32) + ".." : promt);
    }

    @Override
    public Object getSelectedEntity() {
        return entity;
    }

    @Override
    public Availability isEntityClassSelected(Class clazz) {
        return (entity != null && entity.getClass().equals(clazz))
                ? Availability.available()
                : Availability.unavailable("Please, select item with 'get' command");
    }
}