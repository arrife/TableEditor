package services.functions;

import services.TermParser;

import javax.swing.table.TableModel;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;

public class Value extends Term {
    private final Object value;

    public Value(Object value) {
        super(0, "Value");
        if (value instanceof String && TermParser.isAddress((String) value)) {
            links = new HashSet<>(List.of((String) value));
        }
        this.value = Value.of(value);
        length = 1;
    }

    public static Object of(Object value) {
        if (value != null && TermParser.isNumeric(value.toString())) {
            return Double.parseDouble(value.toString());
        } else if (value instanceof Number) {
            return ((Number) value).doubleValue();
        } else {
            return value;
        }
    }

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public double eval(TableModel dm) throws IOException {
        if (value instanceof Double) {
            return (double) value;
        } else if (value instanceof String && TermParser.isAddress((String) value)) {
            int linkRow = TermParser.getAddressRow((String) value);
            int linkColumn = TermParser.getAddressColumn((String) value);
            try {
                Object linkValue = dm.getValueAt(linkRow, linkColumn);
                if (linkValue instanceof Number) {
                    return ((Number) linkValue).doubleValue();
                }
            } catch (IndexOutOfBoundsException e) {
                throw new IOException("Link index is out of bound");
            }
        }
        throw new IOException("The value should be a number");
    }
}
