package services;

import services.functions.*;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class TermParser {
    public static final Hashtable<String, Class<?>> FUNCTIONS = new Hashtable<>() {{
        put("ADD", Add.class);
        put("MUL", Mul.class);
        put("DIV", Div.class);
        put("MINUS", Minus.class);
        put("POW", Pow.class);
    }};

    private TermParser() {
    }

    public static Term parse(final String expression) throws IOException {
        if (!isExpression(expression)) {
            throw new IllegalArgumentException("String is not an expression");
        }
        String[] tokens = expression.replaceFirst("=", "")
                .toUpperCase().replaceAll("\\s+", "")
                .split("(?<=[+\\-*/(),])|(?=[+\\-*/(),])");
        return parseTermTree(tokens, 0);
    }

    public static boolean isExpression(final Object value) {
        return (value instanceof String && ((String) value).length() > 0 && ((String) value).charAt(0) == '=');
    }

    public static boolean isNumeric(String s) {
        return s.matches("[-+]?[0-9]*\\.?[0-9]+([eE][-+]?[0-9]+)?");
    }

    public static boolean isAddress(String s) {
        return s.matches("[A-Z]+\\d+");
    }

    public static int getAddressColumn(String address) {
        int column = 0;
        int position = 0;
        while (Character.isUpperCase(address.charAt(position))) {
            column *= 26;
            column += address.charAt(position) - 'A';
            position += 1;
        }
        return column + 1;
    }

    public static int getAddressRow(String address) {
        Matcher m = Pattern.compile("\\d+").matcher(address);
        if (!m.find()) {
            throw new IllegalArgumentException("The string is not an address");
        }
        return Integer.parseInt(m.group());
    }

    private static Term parseTermTree(String[] tokens, int start) throws IOException {
        Term term = parseAdditiveTerm(tokens, start);
        int position = start + term.length;
        while (position < tokens.length) {
            String token = tokens[position];
            if (!token.equals("+") && !token.equals("-")) {
                break;
            }
            Term rightTerm = parseAdditiveTerm(tokens, position);
            position += rightTerm.length;
            term = new Add(term, rightTerm);
            term.addLength(-1);
        }
        return term;
    }

    private static Term parseAdditiveTerm(String[] tokens, int start) throws IOException {
        Term term = parseMultiplicativeTerm(tokens, start);
        int position = start + term.length;
        while (position < tokens.length) {
            String token = tokens[position];
            if (!"*/".contains(token)) {
                break;
            }
            Term rightTerm = parseMultiplicativeTerm(tokens, position + 1);
            position += rightTerm.length;
            if (token.equals("*")) {
                term = new Mul(term, rightTerm);
            } else {
                term = new Div(term, rightTerm);
            }
        }
        return term;
    }

    private static Term parseMultiplicativeTerm(String[] tokens, int start) throws IOException {
        if (start >= tokens.length) {
            throw new IOException("End of the expression found while parsing");
        }
        String token = tokens[start];
        if ("+-".contains(token)) {
            Term term = parseMultiplicativeTerm(tokens, start + 1);
            if (token.equals("+")) {
                term.addLength(1);
            } else {
                term = new Minus(term);
            }
            return term;
        }
        return getAtomTerm(tokens, start);
    }

    private static ArrayList<Term> parseArgumentsList(String[] tokens, int start) throws IOException {
        ArrayList<Term> arguments = new ArrayList<>();
        int position = start;
        do {
            position++;
            arguments.add(parseTermTree(tokens, position));
            position += arguments.get(arguments.size() - 1).length;
        } while (position < tokens.length && tokens[position].equals(","));
        if (position >= tokens.length || !tokens[position].equals(")")) {
            throw new IOException("Incorrect sequence of brackets");
        }
        return arguments;
    }

    private static Term parseFunction(String[] tokens, int start) throws IOException {
        if (!FUNCTIONS.containsKey(tokens[start])) {
            System.out.println(tokens[start]);
            throw new IOException(String.format("Function %s is not found", tokens[start]));
        }
        ArrayList<Term> arguments = parseArgumentsList(tokens, start + 1);
        try {
            Class<?> functionName = FUNCTIONS.get(tokens[start]);
            Term term = (Term) functionName.getConstructor().newInstance();
            term.applyTo(arguments);
            return term;
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException |
                 InvocationTargetException e) {
            throw new IOException(String.format("Function %s is not found", tokens[start]));
        }
    }


    private static Term getAtomTerm(String[] tokens, int start) throws IOException {
        if (tokens[start].equals("(")) {
            ArrayList<Term> terms = parseArgumentsList(tokens, start);
            if (terms.size() != 1) {
                throw new IOException("There should be only one expression");
            }
            terms.get(0).addLength(2);
            return terms.get(0);
        } else if (start + 1 < tokens.length && tokens[start + 1].equals("(")) {
            return parseFunction(tokens, start);
        } else {
            return new Value(tokens[start]);
        }
    }

    public static List<List<String>> getEmptyData(int rowCount, int columnCount) {
        List<List<String>> data = new ArrayList<>();
        for (int i = 0; i < rowCount; i++) {
            ArrayList<String> row = new ArrayList<>();
            data.add(row);
            for (int j = 0; j < columnCount; j++) {
                row.add("");
            }
        }
        return data;
    }
}
