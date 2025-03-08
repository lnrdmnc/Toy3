package node.expr.constant;

import node.ASTNode;
import node.Type;
import node.expr.Expr;
import visitor.utils.TabellaDeiSimboli;

public class TrueNode  extends ASTNode implements Expr {

    private boolean costant;
    private TabellaDeiSimboli tabellaDeiSimboli;
    private Type type;

    public TabellaDeiSimboli getTabellaDeiSimboli() {
        return tabellaDeiSimboli;
    }

    public void setTabellaDeiSimboli(TabellaDeiSimboli tabellaDeiSimboli) {
        this.tabellaDeiSimboli = tabellaDeiSimboli;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public TrueNode() {
        this.costant = true;
    }

    @Override
    public void accept(ASTNode v) {
        v.accept(this);
    }
}
