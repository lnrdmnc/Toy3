package node.vardecl;

import node.ASTNode;
import node.expr.Expr;

public class VarInit extends ASTNode {

    private final String id;       // Nome della variabile
    private final Expr initValue;  // Valore iniziale (opzionale)

    public VarInit(String id, Expr initValue) {
        super("VarInit");
        this.id = id;
        this.initValue = initValue;
    }

    // Getter
    public String getId() { return id; }
    public Expr getInitValue() { return initValue; }

    @Override
    public String toString() {
        return "VarInit{" +
                "id='" + id + '\'' +
                ", initValue=" + initValue +
                '}';
    }
}

