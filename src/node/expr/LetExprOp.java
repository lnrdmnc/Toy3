package node.expr;

import node.Stat;
import node.Type;
import node.vardecl.VarDecl;
import node.Visitor;
import visitor.utils.TabellaDeiSimboli;
import java.util.ArrayList;

public class LetExprOp implements Expr {

    private final ArrayList<VarDecl> declarations;
    private final ArrayList<Stat> givenBody;
    private final Expr isExpr;
    private TabellaDeiSimboli tabellaDeiSimboli;
    private Type type;

    public LetExprOp(ArrayList<VarDecl> declarations, ArrayList<Stat> givenBody, Expr isExpr) {
        this.declarations = declarations;
        this.givenBody = givenBody;
        this.isExpr = isExpr;
    }


    public Type getType() {
        return type;
    }

    public  void setType(Type type) {
        this.type = type;
    }

    public ArrayList<VarDecl> getDeclarations() {
        return declarations;
    }

    public ArrayList<Stat> getGivenBody() {
        return givenBody;
    }

    public Expr getIsExpr() {
        return isExpr;
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

}