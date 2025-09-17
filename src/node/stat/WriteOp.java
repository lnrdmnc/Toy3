package node.stat;

import node.ASTNode;
import node.Stat;
import node.Type;
import node.Visitor;
import node.expr.Expr;
import visitor.utils.TabellaDeiSimboli;

import java.util.ArrayList;
import java.util.List;

/**
 * La classe WriteOp rappresenta un'operazione di scrittura nell'AST.
 * Gestisce una lista di espressioni da stampare e un flag per indicare se aggiungere una nuova riga.
 */
public class WriteOp extends ASTNode implements Stat {

    // Lista di espressioni da stampare
    private ArrayList<Expr> expressions;

    // Indica se l'operazione di scrittura include una nuova riga (write o writeln)
    private boolean newLine;

    // Tabella dei simboli associata all'operazione
    private TabellaDeiSimboli tabella;

    // Tipo del nodo
    private Type type;

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
     * Costruttore principale della classe WriteOp.
     * Inizializza la lista di espressioni e il flag per la nuova riga.
     *
     * @param expressions Lista di espressioni da stampare
     * @param newLine Indica se aggiungere una nuova riga
     */
    public WriteOp(ArrayList<Expr> expressions, boolean newLine) {
        this.expressions = expressions;
        this.newLine = newLine;
    }

    /**
     * Costruttore di default della classe WriteOp.
     * Inizializza una lista vuota di espressioni e imposta il flag per la nuova riga a false.
     */
    public WriteOp() {
        this.expressions = new ArrayList<>();
        this.newLine = false;
    }

    /**
     * Imposta il flag per la nuova riga.
     * @param newLine True se deve aggiungere una nuova riga, altrimenti false
     */
    public void setNewLine(boolean newLine) {
        this.newLine = newLine;
    }

    /**
     * Imposta la lista di espressioni da stampare.
     * @param expressions Lista di espressioni da associare
     */
    public void setExpressions(ArrayList<Expr> expressions) {
        this.expressions = expressions;
    }

    /**
     * Restituisce la tabella dei simboli associata all'operazione.
     * @return Tabella dei simboli
     */
    public TabellaDeiSimboli getTabella() {
        return tabella;
    }

    /**
     * Imposta la tabella dei simboli per l'operazione.
     * @param tabella Tabella dei simboli da associare
     */
    public void setTabella(TabellaDeiSimboli tabella) {
        this.tabella = tabella;
    }

    /**
     * Aggiunge un'espressione alla lista di espressioni da stampare.
     * @param expression Espressione da aggiungere
     */
    public void addExpression(Expr expression) {
        this.expressions.add(expression);
    }

    /**
     * Restituisce la lista di espressioni da stampare.
     * @return Lista di espressioni
     */
    public List<Expr> getExpressions() {
        return expressions;
    }

    /**
     * Controlla se l'operazione di scrittura include una nuova riga.
     * @return True se include una nuova riga, altrimenti false
     */
    public boolean isNewLine() {
        return newLine;
    }

    /**
     * Restituisce una rappresentazione in stringa dell'oggetto WriteOp.
     * @return Rappresentazione in stringa di WriteOp
     */
    @Override
    public String toString() {
        return "WriteOp{" +
                "expressions=" + expressions +
                ", newLine=" + newLine +
                '}';
    }

    /**
     * Metodo di accettazione per il Visitor.
     * Permette di applicare un'operazione definita da un Visitor alla classe WriteOp.
     * @param v Visitor da applicare
     * @return Risultato dell'operazione del Visitor
     */
    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
    }
}