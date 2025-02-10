package node.stat;

import node.Stat;
import node.expr.Expr;

import java.util.List;

//costruttore pieno e costruttore vuoto

public class AssignOp extends Stat {

    private List<String> variables; // Lista di variabili
    private List<Expr> expressions; // Lista di espressioni

    public AssignOp(List<String> variables, List<Expr> expressions) {
        super("AssignOp");
        this.variables = variables;
        this.expressions = expressions;

        if (variables.size() != expressions.size()) {
            throw new IllegalArgumentException("Il numero di variabili deve corrispondere al numero di espressioni.");
        }
    }

    public AssignOp() {
        super("AssignOp");
    }

    public List<Expr> getExpressions() {
        return expressions;
    }

    public void setExpressions(List<Expr> expressions) {
        this.expressions = expressions;
    }

    public List<String> getVariables() {
        return variables;
    }

    public void setVariables(List<String> variables) {
        this.variables = variables;
    }

    public void addAssignment(String variables, Expr expression) {
        this.variables.add(variables);
        this.expressions.add(expression);
    }
}
