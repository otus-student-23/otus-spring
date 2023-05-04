package ru.otus.mar.booklibrary.service.odata;

import org.apache.olingo.odata2.api.edm.EdmLiteral;
import org.apache.olingo.odata2.api.edm.EdmMultiplicity;
import org.apache.olingo.odata2.api.edm.EdmType;
import org.apache.olingo.odata2.api.edm.EdmTyped;
import org.apache.olingo.odata2.api.uri.expression.BinaryExpression;
import org.apache.olingo.odata2.api.uri.expression.BinaryOperator;
import org.apache.olingo.odata2.api.uri.expression.ExpressionVisitor;
import org.apache.olingo.odata2.api.uri.expression.LiteralExpression;
import org.apache.olingo.odata2.api.uri.expression.MemberExpression;
import org.apache.olingo.odata2.api.uri.expression.MethodExpression;
import org.apache.olingo.odata2.api.uri.expression.MethodOperator;
import org.apache.olingo.odata2.api.uri.expression.OrderByExpression;
import org.apache.olingo.odata2.api.uri.expression.OrderExpression;
import org.apache.olingo.odata2.api.uri.expression.PropertyExpression;
import org.apache.olingo.odata2.api.uri.expression.SortOrder;
import org.apache.olingo.odata2.api.uri.expression.UnaryExpression;
import org.apache.olingo.odata2.api.uri.expression.UnaryOperator;
import ru.otus.mar.booklibrary.service.FilterExpression;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExpressionVisitorSql92 implements ExpressionVisitor {

    private static final Map<BinaryOperator, String> SQL_BINARY_MAP = new HashMap<>() {{
        put(BinaryOperator.ADD, " + ");
        put(BinaryOperator.AND, " and ");
        put(BinaryOperator.DIV, " / ");
        put(BinaryOperator.EQ, " = ");
        put(BinaryOperator.GE, " >= ");
        put(BinaryOperator.GT, " > ");
        put(BinaryOperator.LE, " <= ");
        put(BinaryOperator.LT, " < ");
        put(BinaryOperator.MUL, " * ");
        put(BinaryOperator.NE, " <> ");
        put(BinaryOperator.OR, " or ");
        put(BinaryOperator.SUB, " - ");
    }};

    private static final Map<MethodOperator, String> SQL_METHOD_MAP = new HashMap<>() {{
        put(MethodOperator.TOLOWER, "lower");
        put(MethodOperator.TOUPPER, "upper");
        put(MethodOperator.TRIM, "trim");
        put(MethodOperator.LENGTH, "length");
    }};

    @Override
    public Object visitFilterExpression(org.apache.olingo.odata2.api.uri.expression.FilterExpression filterExpression,
                                        String s, Object o) {
        return o;
    }

    @Override
    public Object visitBinary(BinaryExpression binaryExpression, BinaryOperator binaryOperator, Object o, Object o1) {
        FilterExpression result = new FilterExpression(binaryOperator.name());
        String leftSide = doSide(result, o);
        String rightSide = doSide(result, o1);

        String sqlOperator = SQL_BINARY_MAP.get(binaryOperator);
        if (sqlOperator == null) {
            throw new UnsupportedOperationException(binaryOperator.toUriLiteral());
        }
        if (rightSide.equalsIgnoreCase("null")) {
            if (binaryOperator == BinaryOperator.EQ) {
                sqlOperator = " is ";
            } else if (binaryOperator == BinaryOperator.NE) {
                sqlOperator = " is not ";
            }
        }

        result.setExpression(leftSide + sqlOperator + rightSide);
        return result;
    }

    @Override
    public Object visitOrderByExpression(OrderByExpression orderByExpression, String s, List<Object> list) {
        throw new UnsupportedOperationException("visitOrderByExpression");
    }

    @Override
    public Object visitOrder(OrderExpression orderExpression, Object o, SortOrder sortOrder) {
        throw new UnsupportedOperationException("visitOrder");
    }

    @Override
    public Object visitLiteral(LiteralExpression literalExpression, EdmLiteral edmLiteral) {
        //return literalExpression.getUriLiteral();
        return edmLiteral.getLiteral();
    }

    @Override
    public Object visitMethod(MethodExpression methodExpression, MethodOperator methodOperator, List<Object> list) {
        FilterExpression result = new FilterExpression(null);

        if (methodOperator == MethodOperator.STARTSWITH || methodOperator == MethodOperator.ENDSWITH
                || methodOperator == MethodOperator.SUBSTRINGOF) {
            result.setExpression(
                    String.format("%s like %s",
                            doSide(result, list.get(0)),
                            doSide(result,
                                    String.format("%s%s%s",
                                            methodOperator == MethodOperator.STARTSWITH ? "" : "%",
                                            list.get(1).toString(),
                                            methodOperator == MethodOperator.ENDSWITH ? "" : "%"
                                    ))
                    ));
            return result;
        }

        String method = SQL_METHOD_MAP.get(methodOperator);
        if (method == null) {
            throw new UnsupportedOperationException(methodOperator.toUriLiteral());
        }
        result.setExpression(method + "(" + doSide(result, list.get(0)) + ")");
        return result;
    }

    @Override
    public Object visitMember(MemberExpression memberExpression, Object o, Object o1) {
        throw new UnsupportedOperationException("visitMember");
    }

    @Override
    public Object visitProperty(PropertyExpression propertyExpression, String s, EdmTyped edmTyped) {
        if (edmTyped == null) {
            //TODO проверить на sql-injection
            if (!s.matches("^[a-zA-Z0-9_]{1,32}$")) {
                throw new IllegalArgumentException(s);
            }
            return castToEdmTyped(s);
        } else {
            return edmTyped;
        }
    }

    @Override
    public Object visitUnary(UnaryExpression unaryExpression, UnaryOperator unaryOperator, Object o) {
        throw new UnsupportedOperationException(unaryOperator.toUriLiteral());
    }

    private String doSide(FilterExpression expression, Object o) {
        String side = o.toString();
        if (o instanceof String && !side.equalsIgnoreCase("null")) {
            expression.addParam(side);
            side = "?";
        } else if (o instanceof FilterExpression) {
            FilterExpression e = (FilterExpression) o;
            if (BinaryOperator.AND.name().equals(e.getOperator()) || BinaryOperator.OR.name().equals(e.getOperator())) {
                side = "(" + side + ")";
            }
            for (Object p: e.getParams()) {
                expression.addParam(p);
            }
        }
        return side;
    }

    private EdmTyped castToEdmTyped(String s) {
        return new EdmTyped() {
            @Override
            public EdmType getType() {
                return null;
            }

            @Override
            public EdmMultiplicity getMultiplicity() {
                return null;
            }

            @Override
            public String getName() {
                return s;
            }

            @Override
            public String toString() {
                return s;
            }
        };
    }
}