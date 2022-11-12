package services.functions;

public class Div extends BinaryOperation {

    public Div() {
        super("DIV");
    }

    public Div(Term left, Term right) {
        super(left, right, "DIV");
    }


    @Override
    public Double eval() {
        if (!(subTerms.get(0).eval() instanceof Double) || !(subTerms.get(1).eval() instanceof Double)) {
            throw new IllegalArgumentException("Division operator working only with numbers");
        } else {
            return (Double) subTerms.get(0).eval() / (Double) subTerms.get(1).eval();
        }
    }
}
