package services.functions;

public class Mul extends BinaryOperation {

    public  Mul() {
        super("MUL");
    }

    public Mul(Term left, Term right) {
        super(left, right, "MUL");
    }


    @Override
    public Object eval() {
        if (!(subTerms.get(0).eval() instanceof Double) || !(subTerms.get(1).eval() instanceof Double)) {
            throw new IllegalArgumentException("Product operator working only with numbers");
        } else {
            return (Double) subTerms.get(0).eval() * (Double) subTerms.get(1).eval();
        }
    }
}
