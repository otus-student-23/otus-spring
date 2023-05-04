package ru.otus.mar.booklibrary.service;

import org.apache.olingo.odata2.api.uri.UriParser;
import org.apache.olingo.odata2.api.uri.expression.ExpressionVisitor;
import org.springframework.stereotype.Service;
import ru.otus.mar.booklibrary.exception.FilterExpressionParserException;

@Service
public class FilterExpressionParserOdata implements FilterExpressionParser {

    private final ExpressionVisitor expressionVisitor;

    public FilterExpressionParserOdata(ExpressionVisitor expressionVisitor) {
        this.expressionVisitor = expressionVisitor;
    }

    @Override
    public FilterExpression parse(String filter) {
        try {
            var filterExpression = UriParser.parseFilter(null, null, filter);
            return (FilterExpression) filterExpression.accept(expressionVisitor);
        } catch (Exception e) {
            throw new FilterExpressionParserException(String.format("Error parse filter %s", filter), e);
        }
    }
}
