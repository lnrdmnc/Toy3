package node.stat;

import node.ASTNode;
import node.Stat;
import node.Type;
import node.Visitor;
import node.expr.constant.Identifier;
import visitor.ScopeVisitor;
import visitor.utils.TabellaDeiSimboli;

import java.util.ArrayList;

/**
 * La classe ReadOp rappresenta un'operazione di lettura nell'AST.
 * Gestisce una lista di identificatori (variabili) da leggere.
 */
public class ReadOp extends ASTNode implements Stat {

    // Lista di identificatori da leggere
    private ArrayList<Identifier> list;

    // Tipo del nodo
    private Type type;

    // Tabella dei simboli associata all'operazione
    private TabellaDeiSimboli tabellaDeiSimboli;

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
     * Restituisce la tabella dei simboli associata all'operazione.
     * @return Tabella dei simboli
     */
    public TabellaDeiSimboli getTabellaDeiSimboli() {
        return tabellaDeiSimboli;
    }

    /**
     * Imposta la tabella dei simboli per l'operazione.
     * @param tabellaDeiSimboli Tabella dei simboli da associare
     */
    public void setTabellaDeiSimboli(TabellaDeiSimboli tabellaDeiSimboli) {
        this.tabellaDeiSimboli = tabellaDeiSimboli;
    }

    /**
     * Costruttore della classe ReadOp.
     * Inizializza la lista di identificatori da leggere.
     *
     * @param list Lista di identificatori
     */
    public ReadOp(ArrayList<Identifier> list) {
        this.list = list;
    }

    /**
     * Costruttore di default della classe ReadOp.
     * Inizializza una lista vuota di identificatori.
     */
    public ReadOp() {
        this.list = new ArrayList<>();
    }

    /**
     * Aggiunge un identificatore alla lista.
     * @param identifier Identificatore da aggiungere
     */
    public void addList(Identifier identifier) {
        this.list.add(identifier);
    }

    /**
     * Restituisce la lista di identificatori.
     * @return Lista di identificatori
     */
    public ArrayList<Identifier> getList() {
        return list;
    }

    /**
     * Imposta la lista di identificatori.
     * @param list Lista di identificatori da associare
     */
    public void setList(ArrayList<Identifier> list) {
        this.list = list;
    }

    /**
     * Restituisce una rappresentazione in stringa dell'oggetto ReadOp.
     * @return Rappresentazione in stringa di ReadOp
     */
    @Override
    public String toString() {
        return "ReadOp{" +
                "list=" + list +
                '}';
    }

    /**
     * Metodo di accettazione per il Visitor.
     * Permette di applicare un'operazione definita da un Visitor alla classe ReadOp.
     * @param v Visitor da applicare
     * @return Risultato dell'operazione del Visitor
     */
    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
    }
}