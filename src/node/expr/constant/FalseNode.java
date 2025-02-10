package node.expr.constant;

import node.ASTNode;
import node.expr.Expr;

import java.lang.String;

public class FalseNode extends ASTNode implements Expr {

    private boolean costant;

    public FalseNode() {
        this.costant = false;
    }

    public boolean getCostant() {
        return costant;
    }

    @Override
    public String toString() {
        return "False{" +
                "costant=" + costant +
                '}';
    }

    @Override
    public void accept(ASTNode v) {
        v.accept(this);
    }
}
