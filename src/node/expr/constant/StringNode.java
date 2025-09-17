package node.expr.constant;

import node.ASTNode;
import node.Type;
import node.Visitor;
import node.expr.Expr;
import visitor.utils.TabellaDeiSimboli;

/**
 * La classe StringNode rappresenta un nodo costante di tipo stringa (String)
 * all'interno dell'albero sintattico astratto (AST).
 * Implementa l'interfaccia Expr, quindi è un'espressione nell'AST.
 */
public class StringNode implements Expr {

    // Valore costante di tipo stringa rappresentato da questo nodo
    private String constant;

    // Tabella dei simboli associata al nodo, utile per la gestione degli scope
    private TabellaDeiSimboli tabellaDeiSimboli;

    // Tipo del nodo, che rappresenta il tipo di dato associato (in questo caso, String)
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
     * Costruttore della classe StringNode.
     * Inizializza il valore costante del nodo come stringa.
     * @param constant Valore costante di tipo stringa
     */
    public StringNode(Object constant) {
        this.constant = (String) constant;
    }

    /**
     * Restituisce il valore costante rappresentato da questo nodo.
     * @return Valore costante
     */
    public String getConstant() {
        return constant;
    }

    /**
     * Metodo di accettazione per il Visitor.
     * Permette di applicare un'operazione definita da un Visitor alla classe StringNode.
     * @param visitor Visitor da applicare
     * @return Risultato dell'operazione del Visitor
     */
    @Override
    public Object accept(Visitor visitor) {
        return visitor.visit(this);
    }

    /**
     * Restituisce una rappresentazione testuale del nodo StringNode.
     * @return Stringa che rappresenta il nodo StringNode
     */
    @Override
    public String toString() {
        return "String{" +
                "constant='" + constant + '\'' +
                '}';
    }
}