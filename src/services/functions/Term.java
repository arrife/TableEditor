package services.functions;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class Term {
    public int length;
    private final int argumentsNumber;
    private final String name;
    List<Term> subTerms;

    public Term(int argumentsNumber, String name) {
        this.argumentsNumber = argumentsNumber;
        this.name = name;
    }

    public void applyTo(ArrayList<Term> arguments) {
        if (argumentsNumber != arguments.size()) {
            throw new IllegalArgumentException("Wrong number of arguments");
        }
        subTerms = arguments;
        length = argumentsNumber + 2;
        for (Term term: arguments) {
            length += term.length;
        }
    }

    public void addLength(int additionalLength) {
        length += additionalLength;
    }

    @Override
    public String toString() {
        return String.format("%s(%s)", name, subTerms.stream()
                .map(Term::toString).collect(Collectors.joining(",")));
    }

    abstract public Object eval();
}
