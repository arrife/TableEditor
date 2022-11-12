package services.functions;

import java.util.ArrayList;

public class Value extends Term {
    private Object value;

    public Value(String value) {
        super(0, value.getClass().getName());
        try {
            this.value = Double.parseDouble(value);
        } catch (NumberFormatException e) {
            this.value = value;
        }
        subTerms = new ArrayList<>();
        length = 1;
    }

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public Object eval() {
        return value;
    }
}
