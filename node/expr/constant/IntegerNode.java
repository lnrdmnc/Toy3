package node.expr.constant;

import node.expr.Expr;

public class IntegerNode extends Expr {

    private Object costant;

    public IntegerNode(Object value) {
        super("FunCall");
        this.costant =  (int) value;
    }

    public Object getValue() {
        return costant;
    }


}
