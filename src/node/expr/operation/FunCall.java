package node.expr.operation;

import node.ASTNode;
import node.Stat;
import node.Type;
import node.expr.Expr;
import node.expr.constant.Identifier;
import visitor.utils.TabellaDeiSimboli;

import java.util.List;

public class FunCall extends ASTNode implements Expr, Stat {
    private String functionName;      // Nome della funzione
    private List<Expr> arguments;    // Argomenti della funzione
    private Identifier id;
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

    public FunCall( Object id, Object arguments) {
        this.id=(Identifier) id;
        this.arguments = (List<Expr>) arguments;
    }

    public FunCall(Object id) {
        this.id=(Identifier) id;
    }

    public FunCall(Identifier id) {
        this.id = id;
    }

    // Getter per il nome della funzione
    public String getFunctionName() {
        return functionName;
    }

    // Getter per gli argomenti
    public List<Expr> getArguments() {
        return arguments;
    }

    // Getter per l'identificatore
    public Identifier getId() {
        return id;
    }

    @Override
    public void accept(ASTNode v) {
        v.accept(this);
    }
}
