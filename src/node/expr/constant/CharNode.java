package node.expr.constant;

import node.ASTNode;
import node.expr.Expr;

public class CharNode  extends ASTNode implements  Expr {

    private Object costant;

    public CharNode(Object costant) {
        this.costant = (char) costant;
    }

    public Object getCostant() {
        return costant;
    }


    @Override
    public void accept(ASTNode v) {
        v.accept(this);
    }
}


