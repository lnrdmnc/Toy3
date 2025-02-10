package node.expr.operation;

import node.expr.Expr;

import java.util.List;

public class FunCall extends Expr {
    private String functionName;      // Nome della funzione
    private List<Expr> arguments;    // Argomenti della funzione

    public FunCall(String functionName, List<Expr> arguments) {
        super(); // Nome per il debug
        this.functionName = functionName;
        this.arguments = arguments;
    }

    // Getter per il nome della funzione
    public String getFunctionName() {
        return functionName;
    }

    // Getter per gli argomenti
    public List<Expr> getArguments() {
        return arguments;
    }
}
