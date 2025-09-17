package node.stat;

import node.ASTNode;
import node.Stat;
import node.Type;
import node.Visitor;
import node.expr.Expr;
import node.expr.constant.Identifier;
import visitor.utils.TabellaDeiSimboli;

import java.util.ArrayList;
import java.util.List;

/**
 * La classe AssignOp rappresenta un'operazione di assegnazione nell'AST.
 * Gestisce una lista di variabili e una lista di espressioni corrispondenti.
 * Ogni variabile nella lista viene associata a un'espressione nella lista.
 */
public class AssignOp extends ASTNode implements Stat {

    // Lista di variabili coinvolte nell'assegnazione
    private ArrayList<Identifier> variables;

    // Lista di espressioni da assegnare alle variabili
    private ArrayList<Expr> expressions;

    // Tabella dei simboli associata all'operazione di assegnazione
    private TabellaDeiSimboli tabellaDeiSimboli;

    // Tipo del nodo
    private Type type;

    /**
     * Restituisce la tabella dei simboli associata all'operazione di assegnazione.
     * @return Tabella dei simboli
     */
    public TabellaDeiSimboli getTabellaDeiSimboli() {
        return tabellaDeiSimboli;
    }

    /**
     * Imposta la tabella dei simboli per l'operazione di assegnazione.
     * @param tabellaDeiSimboli Tabella dei simboli da associare
     */
    public void setTabellaDeiSimboli(TabellaDeiSimboli tabellaDeiSimboli) {
        this.tabellaDeiSimboli = tabellaDeiSimboli;
    }

    /**
     * Restituisce il tipo del nodo.
     * @return Tipo del nodo
     */
    public Type getType() {
        return type;
    }

    /**
     * Imposta il tipo del nodo.
     * @param type Tipo da associare
     */
    public void setType(Type type) {
        this.type = type;
    }

    /**
     * Costruttore principale della classe AssignOp.
     * Inizializza le liste di variabili ed espressioni e verifica che abbiano la stessa dimensione.
     *
     * @param variables Lista di variabili
     * @param expressions Lista di espressioni
     * @throws IllegalArgumentException Se il numero di variabili non corrisponde al numero di espressioni
     */
    public AssignOp(ArrayList<Identifier> variables, ArrayList<Expr> expressions) {
        this.variables = variables;
        this.expressions = expressions;

        if (variables.size() != expressions.size()) {
            throw new IllegalArgumentException("Il numero di variabili deve corrispondere al numero di espressioni.");
        }
    }

    /**
     * Costruttore di default della classe AssignOp.
     * Inizializza liste vuote di variabili ed espressioni.
     */
    public AssignOp() {
        this.variables = new ArrayList<>();
        this.expressions = new ArrayList<>();
    }

    /**
     * Restituisce la lista di espressioni da assegnare.
     * @return Lista di espressioni
     */
    public List<Expr> getExpressions() {
        return expressions;
    }

    /**
     * Imposta la lista di espressioni da assegnare.
     * @param expressions Lista di espressioni
     */
    public void setExpressions(ArrayList<Expr> expressions) {
        this.expressions = expressions;
    }

    /**
     * Restituisce la lista di variabili coinvolte nell'assegnazione.
     * @return Lista di variabili
     */
    public ArrayList<Identifier> getVariables() {
        return variables;
    }

    /**
     * Imposta la lista di variabili coinvolte nell'assegnazione.
     * @param variables Lista di variabili
     */
    public void setVariables(ArrayList<Identifier> variables) {
        this.variables = variables;
    }

    /**
     * Aggiunge un'assegnazione alla lista di variabili ed espressioni.
     * @param variables Variabile da aggiungere
     * @param expression Espressione da aggiungere
     */
    public void addAssignment(Identifier variables, Expr expression) {
        this.variables.add(variables);
        this.expressions.add(expression);
    }

    /**
     * Metodo di accettazione per il Visitor.
     * Permette di applicare un'operazione definita da un Visitor alla classe AssignOp.
     * @param v Visitor da applicare
     * @return Risultato dell'operazione del Visitor
     */
    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
    }
}