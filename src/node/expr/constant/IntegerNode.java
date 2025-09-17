package node.expr.constant;

import node.ASTNode;
import node.Type;
import node.Visitor;
import node.expr.Expr;
import visitor.utils.TabellaDeiSimboli;

/**
 * La classe IntegerNode rappresenta un nodo costante di tipo intero (int)
 * all'interno dell'albero sintattico astratto (AST).
 * Implementa l'interfaccia Expr, quindi è un'espressione nell'AST.
 */
public class IntegerNode implements Expr {

    // Valore costante di tipo intero rappresentato da questo nodo
    private Object costant;

    // Tabella dei simboli associata al nodo, utile per la gestione degli scope
    private TabellaDeiSimboli tabellaDeiSimboli;

    // Tipo del nodo, che rappresenta il tipo di dato associato (in questo caso, int)
    private Type type;

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
     * Permette di applicare un'operazione definita da un Visitor alla classe IntegerNode.
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
     * Costruttore della classe IntegerNode.
     * Inizializza il valore costante del nodo come intero.
     * @param value Valore costante di tipo intero
     */
    public IntegerNode(Object value) {
        this.costant = (int) value;
    }

    /**
     * Restituisce il valore costante rappresentato da questo nodo.
     * @return Valore costante
     */
    public Object getValue() {
        return costant;
    }

    /**
     * Restituisce una rappresentazione testuale del nodo IntegerNode.
     * @return Stringa che rappresenta il nodo IntegerNode
     */
    @Override
    public String toString() {
        return "Integer{" +
                "costant=" + costant +
                '}';
    }
}