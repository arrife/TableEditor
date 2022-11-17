package services.functions;

import javax.swing.table.TableModel;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public abstract class Term {
    public int length;
    public final int argumentsNumber;
    public final String name;
    List<Term> subTerms;
    public HashSet<String> links;


    public Term(int argumentsNumber, String name) {
        this.argumentsNumber = argumentsNumber;
        this.name = name;
        links = new HashSet<>();
    }

    public void applyTo(List<Term> arguments) throws IOException {
        if (argumentsNumber != arguments.size()) {
            throw new IOException("Wrong number of arguments");
        }
        subTerms = arguments;
        length = argumentsNumber + 2;
        for (Term term : arguments) {
            length += term.length;
            links.addAll(term.links);
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

    abstract public double eval(TableModel dm) throws IOException;
}
