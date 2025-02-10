package node.expr.constant;

import node.ASTNode;
import node.expr.Expr;

public class IntegerNode extends ASTNode implements Expr {

    private Object costant;

    public IntegerNode(Object value) {
        this.costant =  (int) value;
    }

    public Object getValue() {
        return costant;
    }


    @Override
    public void accept(ASTNode v) {
        v.accept(this);
    }
}
