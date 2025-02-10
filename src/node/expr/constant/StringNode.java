package node.expr.constant;

import node.ASTNode;
import node.expr.Expr;

public class StringNode extends ASTNode implements Expr {

    private String constant;

    public StringNode (Object constant) {
        this.constant = (String) constant;
    }

    public String getConstant() {
        return constant;
    }

    @Override
    public void accept(ASTNode v) {
        v.accept(this);
    }
}
