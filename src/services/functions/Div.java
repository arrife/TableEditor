package services.functions;

import java.io.IOException;

public class Div extends BinaryOperation {

    @SuppressWarnings("unused")
    public Div() {
        super("DIV", "Division of two numbers");
    }

    public Div(Term left, Term right) throws IOException {
        super(left, right, "DIV", "Division of two numbers");
    }


    @Override
    public double operation(double left, double right) {
        return left / right;
    }
}
