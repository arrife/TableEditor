package services.functions;

import java.util.List;

public abstract class UnaryOperation extends Term {

    public UnaryOperation(String name) {
        super(1, name);
    }

    public UnaryOperation(Term term, String name) {
        super(1, name);
        subTerms = List.of(term);
        length = term.length + 1;
    }
}
