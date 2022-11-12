package services.functions;

public class Add extends BinaryOperation {

    public Add() {
        super("ADD");
    }

    public Add(Term left, Term right) {
        super(left, right, "ADD");
    }

    @Override
    public String toString() {
        return String.format("%s + %s", subTerms.get(0), subTerms.get(1));
    }

    @Override
    public Double eval() {
        if (!(subTerms.get(0).eval() instanceof Double) || !(subTerms.get(1).eval() instanceof Double)) {
            throw new IllegalArgumentException("Plus operator working only with numbers");
        } else {
            return (Double) subTerms.get(0).eval() + (Double) subTerms.get(1).eval();
        }
    }
}
