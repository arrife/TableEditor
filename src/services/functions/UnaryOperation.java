package services.functions;

import javax.swing.table.TableModel;
import java.io.IOException;
import java.util.List;

public abstract class UnaryOperation extends Term {

    public UnaryOperation(String name) {
        super(1, name);
    }

    public UnaryOperation(Term term, String name) throws IOException {
        super(1, name);
        applyTo(List.of(term));
        length = term.length + 1;
    }

    @Override
    public double eval(TableModel dm) throws IOException {
        return operation(subTerms.get(0).eval(dm));
    }

    public abstract double operation(double var);
}
