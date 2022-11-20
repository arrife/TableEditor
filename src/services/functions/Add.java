package services.functions;

import java.io.IOException;

public class Add extends BinaryOperation {

    @SuppressWarnings("unused")
    public Add() {
        super("ADD", "Sum of two numbers");
    }

    public Add(Term left, Term right) throws IOException {
        super(left, right, "ADD", "Sum of two numbers");
    }

    @Override
    public String toString() {
        return String.format("%s + %s", subTerms.get(0), subTerms.get(1));
    }

    @Override
    public double operation(double left, double right) {
        return left + right;
    }
}
