package node.expr.constant;

import node.ASTNode;
import node.Type;
import node.Visitor;
import node.expr.Expr;
import visitor.utils.TabellaDeiSimboli;

/**
 * La classe TrueNode rappresenta un nodo costante di tipo booleano con valore true
 * all'interno dell'albero sintattico astratto (AST).
 * Implementa l'interfaccia Expr, quindi è un'espressione nell'AST.
 */
public class TrueNode implements Expr {

    // Valore costante booleano rappresentato da questo nodo (sempre true)
    private boolean costant;

    // Tabella dei simboli associata al nodo, utile per la gestione degli scope
    private TabellaDeiSimboli tabellaDeiSimboli;

    // Tipo del nodo, che rappresenta il tipo di dato associato (in questo caso, boolean)
    private Type type;

    /**
     * Restituisce il valore costante rappresentato da questo nodo.
     * @return Valore costante (sempre true)
     */
    public boolean isCostant() {
        return costant;
    }

    /**
     * Imposta il valore costante per il nodo.
     * @param costant Valore costante da associare
     */
    public void setCostant(boolean costant) {
        this.costant = costant;
    }

    /**
     * Restituisce la tabella dei simboli associata al nodo.
     * @return TabellaDeiSimboli
     */
    public TabellaDeiSimboli getTabellaDeiSimboli() {
        return tabellaDeiSimboli;
    }

    /**
     * Imposta la tabella dei simboli per il nodo.
     * @param tabellaDeiSimboli Tabella dei simboli da associare
     */
    public void setTabellaDeiSimboli(TabellaDeiSimboli tabellaDeiSimboli) {
        this.tabellaDeiSimboli = tabellaDeiSimboli;
    }

    /**
     * Metodo di accettazione per il Visitor.
     * Permette di applicare un'operazione definita da un Visitor alla classe TrueNode.
     * @param visitor Visitor da applicare
     * @return Risultato dell'operazione del Visitor
     */
    @Override
    public Object accept(Visitor visitor) {
        return visitor.visit(this);
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
     * Costruttore della classe TrueNode.
     * Inizializza il valore costante del nodo come true.
     */
    public TrueNode() {
        this.costant = true;
    }

    /**
     * Restituisce una rappresentazione testuale del nodo TrueNode.
     * @return Stringa che rappresenta il nodo TrueNode
     */
    @Override
    public String toString() {
        return "True{" +
                "costant=" + costant +
                '}';
    }
}