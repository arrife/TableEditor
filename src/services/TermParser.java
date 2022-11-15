package services;

import services.functions.*;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Hashtable;

public final class TermParser {
    public static final Hashtable<String, Class<?>> FUNCTIONS = new Hashtable<>() {{
        put("ADD", Add.class);
        put("MUL", Mul.class);
        put("DIV", Div.class);
        put("MINUS", Minus.class);
    }};

    private TermParser() {
    }

    public static Term parse(final String expression) {
        if (!isExpression(expression)) {
            throw new IllegalArgumentException("String is not an expression");
        }
        String[] tokens = expression.replaceFirst("=", "")
                .toUpperCase().replaceAll("\\s+", "")
                .split("(?<=[+\\-*/(),])|(?=[+\\-*/(),])");
        return getTermTree(tokens, 0);
    }

    public static boolean isExpression(final Object value) {
        return (value instanceof String && ((String) value).length() > 0 && ((String) value).charAt(0) == '=');
    }

    private static Term getTermTree(String[] tokens, int start) {
        Term term = getAdditiveTerm(tokens, start);
        int position = start + term.length;
        while (position < tokens.length) {
            String token = tokens[position];
            if (!token.equals("+") && !token.equals("-")) {
                break;
            }
            Term rightTerm = getAdditiveTerm(tokens, position);
            position += rightTerm.length;
            term = new Add(term, rightTerm);
            term.addLength(-1);
        }
        return term;
    }

    private static Term getAdditiveTerm(String[] tokens, int start) {
        Term term  = getMultiplicativeTerm(tokens, start);
        int position = start + term.length;
        while (position < tokens.length) {
            String token = tokens[position];
            if (!"*/".contains(token)) {
                break;
            }
            Term rightTerm = getMultiplicativeTerm(tokens, position + 1);
            position += rightTerm.length;
            if (token.equals("*")) {
                term = new Mul(term, rightTerm);
            } else {
                term = new Div(term, rightTerm);
            }
        }
        return term;
    }

    private static Term getMultiplicativeTerm (String[] tokens, int start) {
        if (start >= tokens.length) {
            throw new IllegalArgumentException("End of the expression found while parsing");
        }
        String token = tokens[start];
        if ("+-".contains(token)) {
            Term term = getMultiplicativeTerm(tokens, start + 1);
            if (token.equals("+")) {
                term.addLength(1);
            } else {
                term = new Minus(term);
            }
            return term;
        }
        return getAtomTerm(tokens, start);
    }

    private static ArrayList<Term> parseArguments(String[] tokens, int start) {
        ArrayList<Term> arguments = new ArrayList<>();
        int position = start;
        do {
            position++;
            arguments.add(getTermTree(tokens, position));
            position += arguments.get(arguments.size() - 1).length;
        } while (tokens[position].equals(","));
        if (!tokens[position].equals(")")) {
            throw new IllegalArgumentException("Incorrect sequence of brackets");
        }
        return arguments;
    }

    private static Term parseFunction(String[] tokens, int start) {
        if (!FUNCTIONS.containsKey(tokens[start])) {
            throw new IllegalArgumentException("Function is not found");
        }
        ArrayList<Term> arguments = parseArguments(tokens, start + 1);
        try {
            Class<?> functionName = FUNCTIONS.get(tokens[start]);
            Term term = (Term) functionName.getConstructor().newInstance();
            term.applyTo(arguments);
            return term;
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException |
                 InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    private static Term getAtomTerm(String[] tokens, int start) {
        if (tokens[start].equals("(")) {
            ArrayList<Term> terms = parseArguments(tokens, start);
            if (terms.size() != 1) {
                throw new IllegalArgumentException("Invalid syntax");
            }
            terms.get(0).addLength(2);
            return terms.get(0);
        } else if (start + 1 < tokens.length && tokens[start + 1].equals("(")) {
            return parseFunction(tokens, start);
        } else {
            return new Value(tokens[start]);
        }
    }
}
