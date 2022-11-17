package services.functions;

import java.io.IOException;

public class Div extends BinaryOperation {

    @SuppressWarnings("unused")
    public Div() {
        super("DIV");
    }

    public Div(Term left, Term right) throws IOException {
        super(left, right, "DIV");
    }


    @Override
    public double operation(double left, double right) {
        return left / right;
    }
}
