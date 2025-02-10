package node.expr.constant;

import node.ASTNode;
import node.expr.Expr;

public class Identifier extends ASTNode implements  Expr {

    private String name;

    public Identifier(Object name) {
        this.name = (String) name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Identifier{" +
                "name=" + name +
                '}';
    }

    @Override
    public void accept(ASTNode v) {
        v.accept(this);
    }
}
