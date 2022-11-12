package services.functions;

import java.util.List;

public abstract class BinaryOperation extends Term {

    public BinaryOperation(String name) {
        super(2, name);
    }

    public BinaryOperation(Term left, Term right, String name) {
        super(2, name);
        subTerms = List.of(left, right);
        length = left.length + right.length + 1;
    }
}
