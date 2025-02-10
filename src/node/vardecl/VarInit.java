package node.vardecl;

import node.ASTNode;
import node.expr.Expr;
import node.expr.constant.Identifier;

public class VarInit extends ASTNode {

    private Identifier id;       // Nome della variabile
    private Expr initValue;  // Valore iniziale (opzionale)

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
                '}';
    }



    @Override
    public void accept(ASTNode v) {
        v.accept(this);
    }
}

