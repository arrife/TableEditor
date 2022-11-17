package services.functions;

import java.io.IOException;

public class Minus extends UnaryOperation {

    @SuppressWarnings("unused")
    public Minus() {
        super("MINUS");
    }

    public Minus(Term term) throws IOException {
        super(term, "MINUS");
    }

    @Override
    public double operation(double var) {
        return -var;
    }

    @Override
    public String toString() {
        return String.format("-%s", subTerms.get(0).toString());
    }
}
