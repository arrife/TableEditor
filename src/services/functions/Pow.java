package services.functions;

public class Pow extends BinaryOperation {
    public Pow() {
        super("POW", "Power of two numbers");
    }

    @Override
    public double operation(double left, double right) {
        return Math.pow(left, right);
    }
}
