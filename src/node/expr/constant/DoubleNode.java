package node.expr.constant;

import node.ASTNode;
import node.expr.Expr;
import java.lang.String;

public class DoubleNode extends ASTNode implements  Expr{

    private Object costant;

    public DoubleNode(Object value) {
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

    @Override
    public void accept(ASTNode v) {
        v.accept(this);
    }
}
