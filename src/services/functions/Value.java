package services.functions;

public class Value extends Term {
    private Object value;

    public Value(Object value) {
        super(0, value.getClass().getName());
        try {
            this.value = Double.parseDouble(value.toString());
        } catch (NumberFormatException ignored) {
            this.value = value;
        }
        length = 1;
    }

    @Override
    public String toString() {
        return this.value.toString();
    }

    @Override
    public Object eval() {
        return value;
    }
}
