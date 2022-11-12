package services;

import java.util.HashSet;

public class Formula {
    private Expression expression = null;
    private Object value;
    private HashSet<String> args = new HashSet<>();

//    public void setValue(Object value) {
//        if (value.getClass().equals(String.class) && ((String) value).charAt(0) == '=') {
//            expression = new Expression((String) value, 1);
//            this.eval();
//        } else {
//            this.value = value;
//        }
//    }

    public Object getValue() {
        return value;
    }

    public String getExpression() {
        return expression.toString();
    }

    private void eval() {

    }
}
