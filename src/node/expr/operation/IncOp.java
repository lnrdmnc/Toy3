package node.expr.operation;

import node.Type;
import node.Visitor;
import node.expr.Expr;
import visitor.utils.TabellaDeiSimboli;



public class IncOp implements Expr {

    private FunCall funCall;
    private TabellaDeiSimboli tabellaDeiSimboli;
    private Type type;


    public IncOp(FunCall funCall) {
        this.funCall = funCall;
    }

    public FunCall getFunCall() {
        return funCall;
    }

    public void setFunCall(FunCall funCall) {
        this.funCall = funCall;
    }

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

    @Override
    public Type getType() {
        return type;
    }

    @Override
    public void setType(Type type) {
        this.type = type;
    }


}
