package ru.otus.mar.booklibrary.service;

import java.util.ArrayList;
import java.util.List;

public class FilterExpression {

    private String expression;

    private final List<Object> params = new ArrayList<>();

    private final String operator;

    public FilterExpression(String operator) {
        this.operator = operator;
    }

    public void addParam(Object param) {
        this.params.add(param);
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public List<Object> getParams() {
        return params;
    }

    public String getOperator() {
        return operator;
    }

    @Override
    public String toString() {
        return expression;
    }
}
