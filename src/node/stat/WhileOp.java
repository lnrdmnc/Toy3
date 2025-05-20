package node.stat;

import node.ASTNode;
import node.Stat;
import node.Type;
import node.Visitor;
import node.body.BodyOp;
import node.expr.Expr;
import visitor.ScopeVisitor;
import visitor.utils.TabellaDeiSimboli;

public class WhileOp implements  Stat {

    private Type type;
    private TabellaDeiSimboli tabellaDeiSimboli;
    private Expr espr;
    private BodyOp body;

    public WhileOp(Object espr, Object body) {
        this.espr = (Expr) espr;
        this.body = (BodyOp) body;
    }

    public Expr getEspr() {
        return espr;
    }

    public BodyOp getBody() {
        return body;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public TabellaDeiSimboli getTabellaDeiSimboli() {
        return tabellaDeiSimboli;
    }

    public void setTabellaDeiSimboli(TabellaDeiSimboli tabellaDeiSimboli) {
        this.tabellaDeiSimboli = tabellaDeiSimboli;
    }


    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
    }
}
