package node.stat;

import node.ASTNode;
import node.Stat;
import node.Type;
import node.expr.Expr;
import visitor.utils.TabellaDeiSimboli;

public class ReturnStat extends ASTNode implements Stat {

    private TabellaDeiSimboli tabella;
    private Expr expr;
    private Type type;;

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public TabellaDeiSimboli getTabella() {
        return tabella;
    }

    public void setTabella(TabellaDeiSimboli tabella) {
        this.tabella = tabella;
    }

    public Expr getExpr() {
        return expr;
    }

    public void setExpr(Expr expr) {
        this.expr = expr;
    }

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
