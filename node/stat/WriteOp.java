package node.stat;
import node.ASTNode;
import node.expr.Expr;

import java.util.ArrayList;
import java.util.List;

public class WriteOp extends ASTNode {
    private List<Expr> expressions; // Lista di espressioni da stampare
    private  boolean newLine; // Indica se si usa write o writeln

    // Costruttore principale
    public WriteOp(List<Expr> expressions, boolean newLine) {
        super("WriteOp");
        this.expressions = expressions;
        this.newLine = newLine;
    }

    public WriteOp() {
        super("WriteOp");
        this.expressions = new ArrayList<>();
        this.newLine = false;
    }

    public void setNewLine(boolean newLine) {
        this.newLine = newLine;
    }

    public void setExpressions(ArrayList<Expr> expressions) {
        this.expressions = expressions;
    }





    public void addExpression(Expr expression) {
        this.expressions.add(expression);
    }

    // Getter per le espressioni
    public List<Expr> getExpressions() {
        return expressions;
    }

    // Controlla se la scrittura include il newline
    public boolean isNewLine() {
        return newLine;
    }


    // Metodo toString per debugging
    @Override
    public String toString() {
        return "WriteOp{" +
                "expressions=" + expressions +
                ", newLine=" + newLine +
                '}';
    }
}

