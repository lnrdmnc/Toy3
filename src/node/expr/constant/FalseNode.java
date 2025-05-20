package node.expr.constant;

import node.ASTNode;
import node.Type;
import node.Visitor;
import node.expr.Expr;
import visitor.utils.TabellaDeiSimboli;

import java.lang.String;

public class FalseNode implements Expr {

    private boolean costant;
    private TabellaDeiSimboli tabellaDeiSimboli;
    private Type type;

    public TabellaDeiSimboli getTabellaDeiSimboli() {
        return tabellaDeiSimboli;
    }

    public void setTabellaDeiSimboli(TabellaDeiSimboli tabellaDeiSimboli) {
        this.tabellaDeiSimboli = tabellaDeiSimboli;
    }


    @Override
    public Object accept(Visitor visitor) {
        return visitor.visit(this);
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

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

}
