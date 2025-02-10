package node.expr.constant;

import node.ASTNode;
import node.expr.Expr;

public class TrueNode  extends ASTNode implements Expr {

    private boolean costant;

    public TrueNode() {
        this.costant = true;
    }

    @Override
    public void accept(ASTNode v) {
        v.accept(this);
    }
}
