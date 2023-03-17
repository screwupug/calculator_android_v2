package calculations;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.EmptyStackException;
import java.util.Locale;
import java.util.Stack;

public class Calculator {

    private final String PI_NUMBER = "3.14159265 ";

    public String start(String input) {
        return calculate(reversedPolishNotation(normalizeExpression(input)));
    }

    public String normalizeExpression(String input) {
        StringBuilder result = new StringBuilder();
        char[] expression = input.toCharArray();
        int pos = 0;
        while (pos < expression.length) {
            if (Character.isDigit(expression[pos]) && pos != expression.length - 1) {
                if (Character.isDigit(expression[pos + 1]) || expression[pos + 1] == '.'
                        || expression[pos + 1] == 'E') {
                    result.append(expression[pos]);
                } else {
                    result.append(expression[pos]).append(" ");
                }
            } else if (expression[pos] == '÷') {
                result.append('/').append(" ");
            } else if (expression[pos] == '×') {
                result.append('*').append(" ");
            } else if (expression[pos] == '.') {
                result.append(expression[pos]);
            } else if (expression[pos] == 'π') {
                result.append(PI_NUMBER);
            } else if (expression[pos] == 'E') {
                result.append(expression[pos]);
            } else {
                result.append(expression[pos]).append(" ");
            }
            pos++;
        }
        System.out.println(result);
        return result.toString();
    }

    public String reversedPolishNotation(String input) {
        Stack<String> lexemes = new Stack<>();
        StringBuilder result = new StringBuilder();
        String[] expression = input.split(" ");
        for (String c : expression) {
            if (isDigit(c) || c.equals("!") || c.equals("E")) {
                result.append(c).append(" ");
            } else {
                if (lexemes.size() > 0 && !c.equals("(")) {
                    if (c.equals(")")) {
                        String s = lexemes.pop();
                        try {

                            while (!s.equals("(")) {
                                result.append(s).append(" ");
                                s = lexemes.pop();
                            }
                        } catch (EmptyStackException e) {
                            return "Ошибка";
                        }
                    } else if (getPriority(c) > getPriority(lexemes.peek())) {
                        lexemes.add(c);
                    } else {
                        while (lexemes.size() > 0 && getPriority(c) <= getPriority(lexemes.peek())) {
                            result.append(lexemes.pop()).append(" ");
                        }
                        lexemes.add(c);
                    }
                } else {
                    lexemes.add(c);
                }
            }
        }
        while (!lexemes.isEmpty()) {
            result.append(lexemes.pop()).append(" ");
        }
        return result.toString();
    }

    private boolean isDigit(String expression) {
        try {
            Double.parseDouble(expression);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private int getPriority(String lexeme) {
        switch (lexeme) {
            case "(":
            case ")":
                return 0;
            case "+":
            case "-":
                return 1;
            case "*":
            case "/":
                return 2;
            case "^":
                return 3;
            case "√":
                return 4;
            default:
                return -1;
        }
    }

    public String calculate(String reversedPolishNotation) {
        Stack<Double> numbers = new Stack<>();
        boolean isNoOperators = true;
        System.out.println(reversedPolishNotation);
        double result = 0;
        for (String item : reversedPolishNotation.split(" ")) {
            if (isDigit(item)) {
                numbers.add(Double.parseDouble(String.valueOf(item)));
            } else {
                isNoOperators = false;
                try {
                    switch (item) {
                        case "+": {
                            double a = numbers.pop();
                            double b = numbers.pop();
                            result = a + b;
                            numbers.add(result);
                            break;
                        }
                        case "-": {
                            double a = numbers.pop();
                            double b = numbers.pop();
                            result = b - a;
                            numbers.add(result);
                            break;
                        }
                        case "*": {
                            double a = numbers.pop();
                            double b = numbers.pop();
                            result = a * b;
                            numbers.add(result);
                            break;
                        }
                        case "/": {
                            double a = numbers.pop();
                            double b = numbers.pop();
                            if (a == 0 || b == 0) {
                                return "На ноль делить нельзя!";
                            }
                            result = b / a;
                            numbers.add(result);
                            break;
                        }
                        case "!": {
                            double a = numbers.pop();
                            result = getFactorial(a);
                            numbers.add(result);
                            break;
                        }
                        case "^": {
                            double a = numbers.pop();
                            double b = numbers.pop();
                            result = Math.pow(b, a);
                            numbers.add(result);
                            break;
                        }
                        case "√":
                            double a = numbers.pop();
                            result = Math.sqrt(a);
                            numbers.add(result);
                            break;
                        default:
                            return "Ошибка";
                    }
                } catch (EmptyStackException | ArithmeticException | StackOverflowError e) {
                    e.printStackTrace();
                    return "Ошибка";
                }
            }
        }
        if (isNoOperators && !numbers.isEmpty()) {
            result = numbers.pop();
        }
        return formatInput(result);
    }

    private double getFactorial(double digit) {
        if (digit <= 1) {
            return 1;
        } else {
            return digit * getFactorial(digit - 1);
        }
    }

    private String formatInput(double result) {
        String res = Double.toString(result);
        System.out.println(res);
        int size = Double.toString(result).length();
        DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(Locale.getDefault());
        otherSymbols.setDecimalSeparator('.');
        if (size > 20 || res.contains("E")) {
            return new DecimalFormat("0.000E00", otherSymbols).format(result);
        } else {

            NumberFormat nf = new DecimalFormat("#.####", otherSymbols);
            return nf.format(result);
        }

    }

    public String calculatePercent(String input) {
        double result = 0;
        try {
            double digit = Double.parseDouble(input);
            result = digit / 100;
        } catch (Exception e) {
            return "Ошибка";
        }
        return formatInput(result);
    }
}
