package node.expr.constant;

import node.ASTNode;
import node.Type;
import node.Visitor;
import node.expr.Expr;
import visitor.utils.TabellaDeiSimboli;

/**
 * La classe Identifier rappresenta un nodo costante che contiene un identificatore
 * (nome) all'interno dell'albero sintattico astratto (AST).
 * Implementa l'interfaccia Expr, quindi è un'espressione nell'AST.
 */
public class Identifier implements Expr {

    // Nome dell'identificatore rappresentato da questo nodo
    private String name;

    // Tabella dei simboli associata al nodo, utile per la gestione degli scope
    private TabellaDeiSimboli tabellaDeiSimboli;

    // Tipo del nodo, che rappresenta il tipo di dato associato
    private Type type;

    // Indica se l'identificatore è un riferimento
    private boolean ref;

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
     * Costruttore della classe Identifier.
     * Inizializza il nome dell'identificatore.
     * @param name Nome dell'identificatore
     */
    public Identifier(Object name) {
        this.name = (String) name;
    }

    /**
     * Restituisce il nome dell'identificatore.
     * @return Nome dell'identificatore
     */
    public String getName() {
        return name;
    }

    /**
     * Imposta il nome dell'identificatore.
     * @param name Nome da associare
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Verifica se l'identificatore è un riferimento.
     * @return true se è un riferimento, altrimenti false
     */
    public boolean isRef() {
        return ref;
    }

    /**
     * Imposta se l'identificatore è un riferimento.
     * @param ref true se è un riferimento, altrimenti false
     */
    public void setRef(boolean ref) {
        this.ref = ref;
    }

    /**
     * Restituisce una rappresentazione testuale del nodo Identifier.
     * @return Stringa che rappresenta il nodo Identifier
     */
    @Override
    public String toString() {
        return "Identifier{" +
                "name=" + name +
                '}';
    }

    /**
     * Metodo di accettazione per il Visitor.
     * Permette di applicare un'operazione definita da un Visitor alla classe Identifier.
     * @param visitor Visitor da applicare
     * @return Risultato dell'operazione del Visitor
     */
    @Override
    public Object accept(Visitor visitor) {
        return visitor.visit(this);
    }
}