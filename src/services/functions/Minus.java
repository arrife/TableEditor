package services.functions;

public class Minus extends UnaryOperation {

    public Minus() {
        super("MINUS");
    }

    public Minus(Term term) {
        super(term, "MINUS");
    }

    @Override
    public Double eval() {
        if (!(subTerms.get(0).eval() instanceof Double)) {
            throw new IllegalArgumentException("Minus operator working only with numbers");
        } else {
            return -(Double) subTerms.get(0).eval() ;
        }
    }
}
