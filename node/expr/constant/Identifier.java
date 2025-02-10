package node.expr.constant;

import node.expr.Expr;

public class Identifier extends Expr {

    private String name;

    public Identifier(String name) {
        super("FunCall");
        this.name = name;
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
}
