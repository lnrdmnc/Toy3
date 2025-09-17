package node.pardecl;

import node.ASTNode;
import node.Type;
import node.Visitor;
import node.expr.constant.Identifier;
import visitor.utils.TabellaDeiSimboli;

/**
 * La classe ParVar rappresenta una variabile parametro nell'AST.
 * Include proprietà come il passaggio per riferimento, l'identificatore,
 * il tipo e una tabella dei simboli associata.
 */
public class ParVar extends ASTNode {

    // Indica se il parametro è passato per riferimento
    private boolean isReference;

    // Identificatore del parametro
    private Identifier id;

    // Tabella dei simboli associata alla variabile parametro
    private TabellaDeiSimboli tabellaDeiSimboli;

    // Tipo della variabile parametro
    private Type type;

    /**
     * Restituisce la tabella dei simboli associata alla variabile parametro.
     * @return Tabella dei simboli
     */
    public TabellaDeiSimboli getTabellaDeiSimboli() {
        return tabellaDeiSimboli;
    }

    /**
     * Imposta la tabella dei simboli per la variabile parametro.
     * @param tabellaDeiSimboli Tabella dei simboli da associare
     */
    public void setTabellaDeiSimboli(TabellaDeiSimboli tabellaDeiSimboli) {
        this.tabellaDeiSimboli = tabellaDeiSimboli;
    }

    /**
     * Restituisce il tipo della variabile parametro.
     * @return Tipo della variabile parametro
     */
    public Type getType() {
        return type;
    }

    /**
     * Imposta il tipo della variabile parametro.
     * @param type Tipo da associare
     */
    public void setType(Type type) {
        this.type = type;
    }

    /**
     * Costruttore della classe ParVar.
     * Inizializza il flag di riferimento e l'identificatore.
     *
     * @param isReference Indica se il parametro è passato per riferimento
     * @param id Identificatore del parametro
     */
    public ParVar(boolean isReference, Identifier id) {
        this.isReference = isReference;
        this.id = id;
    }

    /**
     * Restituisce se il parametro è passato per riferimento.
     * @return True se passato per riferimento, false altrimenti
     */
    public boolean isReference() {
        return isReference;
    }

    /**
     * Imposta se il parametro è passato per riferimento.
     * @param reference True se passato per riferimento, false altrimenti
     */
    public void setReference(boolean reference) {
        isReference = reference;
    }

    /**
     * Restituisce l'identificatore del parametro.
     * @return Identificatore del parametro
     */
    public Identifier getId() {
        return id;
    }

    /**
     * Imposta l'identificatore del parametro.
     * @param id Identificatore da associare
     */
    public void setId(Identifier id) {
        this.id = id;
    }

    /**
     * Restituisce una rappresentazione in stringa dell'oggetto ParVar.
     * @return Rappresentazione in stringa di ParVar
     */
    @Override
    public String toString() {
        return "ParVar{" +
                "isReference=" + isReference +
                ", id=" + id +
                '}';
    }

    /**
     * Metodo di accettazione per il Visitor.
     * Permette di applicare un'operazione definita da un Visitor alla classe ParVar.
     * @param visitor Visitor da applicare
     * @return Risultato dell'operazione del Visitor
     */
    @Override
    public Object accept(Visitor visitor) {
        return visitor.visit(this);
    }
}