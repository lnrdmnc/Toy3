package node.stat;

import node.ASTNode;
import node.Stat;
import node.Type;
import node.expr.Expr;
import node.expr.constant.Identifier;
import visitor.utils.TabellaDeiSimboli;

import java.util.ArrayList;
import java.util.List;

//costruttore pieno e costruttore vuoto

public class AssignOp extends ASTNode implements Stat {

    private ArrayList<Identifier> variables; // Lista di variabili
    private ArrayList<Expr> expressions; // Lista di espressioni
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

    public AssignOp(ArrayList<Identifier> variables, ArrayList<Expr> expressions) {
        this.variables = variables;
        this.expressions = expressions;

        if (variables.size() != expressions.size()) {
            throw new IllegalArgumentException("Il numero di variabili deve corrispondere al numero di espressioni.");
        }
    }

    public AssignOp() {
        this.variables = new ArrayList<>();
        this.expressions = new ArrayList<>();
    }

    public List<Expr> getExpressions() {
        return expressions;
    }

    public void setExpressions(ArrayList<Expr> expressions) {
        this.expressions = expressions;
    }

    public ArrayList<Identifier> getVariables() {
        return variables;
    }

    public void setVariables(ArrayList<Identifier> variables) {
        this.variables = variables;
    }

    public void addAssignment(Identifier variables, Expr expression) {
        this.variables.add(variables);
        this.expressions.add(expression);
    }

    @Override
    public void accept(ASTNode v) {
        v.accept(this);
    }
}
