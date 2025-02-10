package node.stat;

import node.ASTNode;
import node.Stat;
import node.expr.Expr;

public class ReturnStat extends ASTNode implements Stat {

    private Expr expr;

    public ReturnStat(Object object) {
        this.expr = (Expr) object;

    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "ReturnStat{" +
                "node.expr='" + expr + '\'' +
                '}';
    }

    @Override
    public void accept(ASTNode v) {
       v.accept(this);
    }
}
