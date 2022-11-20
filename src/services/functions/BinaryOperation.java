package services.functions;

import javax.swing.table.TableModel;
import java.io.IOException;
import java.util.List;

public abstract class BinaryOperation extends Term {

    public BinaryOperation(String name, String description) {
        super(2, name, description);
    }

    public BinaryOperation(Term left, Term right, String name, String description) throws IOException {
        this(name, description);
        applyTo(List.of(left, right));
        length = left.length + right.length + 1;
    }

    @Override
    public double eval(TableModel dm) throws IOException {
        return operation(subTerms.get(0).eval(dm), subTerms.get(1).eval(dm));
    }

    public abstract double operation(double left, double right);
}
