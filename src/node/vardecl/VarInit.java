package node.vardecl;

import node.ASTNode;
import node.Type;
import node.Visitor;
import node.expr.Expr;
import node.expr.constant.Identifier;
import visitor.utils.TabellaDeiSimboli;

public class VarInit extends ASTNode {

    private Identifier id;       // Nome della variabile
    private Expr initValue;  // Valore iniziale (opzionale)
    private TabellaDeiSimboli tabellaDeiSimboli;
    private Type returnType;
    public VarInit(Object id, Object initValue) {
        this.id = (Identifier) id;
        this.initValue = (Expr) initValue;
    }

    public VarInit(Object id) {
        this.id = (Identifier) id;
    }

    // Getter
    public Identifier getId() { return id; }
    public Expr getInitValue() { return initValue; }

    @Override
    public String toString() {
        return "VarInit{" +
                "id='" + id + '\'' +
                ", initValue=" + initValue +
                ", table=" + tabellaDeiSimboli +
                ", returnType=" + returnType +
                '}';
    }

    public void setReturnType(Type returnType) {
        this.returnType = returnType;
    }

    @Override
    public void accept(Visitor v) {
        v.visitVarInit(this);
    }
}

