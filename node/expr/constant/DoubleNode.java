package node.expr.constant;

import node.expr.Expr;

import java.lang.String;

public class DoubleNode extends Expr {

    private Object costant;

    public DoubleNode(Object value) {
        super();
        this.costant = (double) value;
    }

    public Object getCostant() {
        return costant;

    }

    @Override
    public String toString() {
        return "Double{" +
                "costant=" + costant +
                '}';
    }
}
