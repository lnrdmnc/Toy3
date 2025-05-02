package node.stat;

import node.ASTNode;
import node.Stat;
import node.Type;
import node.body.BodyOp;
import node.expr.Expr;
import visitor.utils.TabellaDeiSimboli;

public class IfThenNode extends ASTNode implements Stat {

    private Expr espressione;;
    private TabellaDeiSimboli tabellaDeiSimboli;
    private BodyOp body;
    private Type type;

    public void setEspressione(Expr espressione,BodyOp body) {
        this.espressione = espressione;
        this.body=body;
    }

    public BodyOp getBody() {
        return body;
    }

    public void setEspressione(Expr espressione) {
        this.espressione = espressione;
    }

    public void setBody(BodyOp body) {
        this.body = body;
    }

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

    public IfThenNode(Expr espressione, BodyOp body) {
        this.body = body;
        this.espressione = espressione;
    }

    public Expr getEspressione() {
        return espressione;
    }


    @Override
    public String toString() {
        return "IfThenNode{" +
                "espressione=" + espressione +
                ", body=" + body +
                '}';
    }

    @Override
    public void accept(ASTNode v) {
        v.accept(this);
    }
}
