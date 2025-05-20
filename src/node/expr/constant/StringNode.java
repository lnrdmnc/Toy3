package node.expr.constant;

import node.ASTNode;
import node.Type;
import node.Visitor;
import node.expr.Expr;
import visitor.utils.TabellaDeiSimboli;

public class StringNode implements Expr {

    private String constant;
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

    public StringNode (Object constant) {
        this.constant = (String) constant;
    }

    public String getConstant() {
        return constant;
    }

    @Override
    public Object accept(Visitor visitor) {
        return visitor.visit(this);
    }

}
