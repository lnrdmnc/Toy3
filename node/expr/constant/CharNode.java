package node.expr.constant;

import node.expr.Expr;

public class CharNode extends Expr {

    private Object costant;

    public CharNode(Object costant) {
        super("FunCall");
        this.costant = (CharNode) costant;
    }

    public Object getCostant() {
        return costant;
    }
}


