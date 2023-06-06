package ru.otus.mar.booklibrary.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.otus.mar.booklibrary.exception.NotFoundException;

@ControllerAdvice
@Slf4j
public class ExceptionControllerAdvice {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String notFoundException(NotFoundException exception, Model model) {
        model.addAttribute("message", "Объект не найден");
        return "error";
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String dataIntegrityViolationException(DataIntegrityViolationException exception, Model model) {
        model.addAttribute("message", exception.getMessage());//TODO заглушить сообщение
        return "error";
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String exception(Exception exception, Model model) {
        log.error(null, exception);
        model.addAttribute("message", "Что-то пошло не так, но мы уже чиним!");
        return "error";
    }
}
