package node.stat;

import node.Stat;
import node.expr.Expr;

public class ReturnStat extends Stat {

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
}
