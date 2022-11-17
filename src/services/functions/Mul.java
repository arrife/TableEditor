package services.functions;

import java.io.IOException;

public class Mul extends BinaryOperation {

    @SuppressWarnings("unused")
    public Mul() {
        super("MUL");
    }

    public Mul(Term left, Term right) throws IOException {
        super(left, right, "MUL");
    }


    @Override
    public double operation(double left, double right) {
        return left * right;
    }
}
