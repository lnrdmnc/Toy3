package node.expr.constant;

import node.expr.Expr;

public class TrueNode extends Expr {

    private boolean costant;

    public TrueNode() {
        super("FunCall");
        this.costant = true;
    }

}
