package node.expr.constant;
import node.ASTNode;
import node.Type;
import node.Visitor;
import node.expr.Expr;
import node.expr.constant.Identifier;
import visitor.utils.TabellaDeiSimboli;

public class ArrayAccess extends ASTNode implements Expr {

    private Identifier id;
    private Expr indexExpr; // Espressione dell’indice

    private Type type;
    private TabellaDeiSimboli tabellaDeiSimboli;

    public ArrayAccess(Identifier id, Expr indexExpr) {
        this.id = id;
        this.indexExpr = indexExpr;
    }

    public Identifier getId() {
        return id;
    }

    public Expr getIndexExpr() {
        return indexExpr;
    }

    public TabellaDeiSimboli getTabellaDeiSimboli() {
        return tabellaDeiSimboli;
    }

    public void setTabellaDeiSimboli(TabellaDeiSimboli tabellaDeiSimboli) {
        this.tabellaDeiSimboli = tabellaDeiSimboli;
    }

    @Override
    public Type getType() {
        return type;
    }

    @Override
    public void setType(Type type) {
        this.type = type;
    }

    public void setIndexExpr(Expr indexExpr) {
        this.indexExpr = indexExpr;
    }

    public void setId(Identifier id) {
        this.id = id;
    }

    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
    }
}




