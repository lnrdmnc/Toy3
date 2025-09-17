package node.vardecl;

import node.ASTNode;
import node.Type;
import node.Visitor;
import node.expr.Expr;
import node.expr.constant.Identifier;
import visitor.utils.TabellaDeiSimboli;

/**
 * La classe VarInit rappresenta l'inizializzazione di una variabile nell'AST.
 * Gestisce il nome della variabile, un valore iniziale opzionale, il tipo di ritorno e la tabella dei simboli associata.
 */
public class VarInit extends ASTNode {

    // Identificatore della variabile
    private Identifier id;

    // Valore iniziale della variabile (opzionale)
    private Expr initValue;

    // Tabella dei simboli associata alla variabile
    private TabellaDeiSimboli tabellaDeiSimboli;

    // Tipo di ritorno della variabile
    private Type returnType;

    /**
     * Restituisce la tabella dei simboli associata alla variabile.
     * @return Tabella dei simboli
     */
    public TabellaDeiSimboli getTabellaDeiSimboli() {
        return tabellaDeiSimboli;
    }

    /**
     * Imposta la tabella dei simboli per la variabile.
     * @param tabellaDeiSimboli Tabella dei simboli da associare
     */
    public void setTabellaDeiSimboli(TabellaDeiSimboli tabellaDeiSimboli) {
        this.tabellaDeiSimboli = tabellaDeiSimboli;
    }

    /**
     * Restituisce il tipo di ritorno della variabile.
     * @return Tipo di ritorno
     */
    public Type getReturnType() {
        return returnType;
    }

    /**
     * Imposta il tipo di ritorno per la variabile.
     * @param returnType Tipo di ritorno da associare
     */
    public void setReturnType(Type returnType) {
        this.returnType = returnType;
    }

    /**
     * Costruttore della classe VarInit.
     * Inizializza l'identificatore e il valore iniziale della variabile.
     *
     * @param id Identificatore della variabile
     * @param initValue Valore iniziale della variabile
     */
    public VarInit(Object id, Object initValue) {
        this.id = (Identifier) id;
        this.initValue = (Expr) initValue;
    }

    /**
     * Costruttore della classe VarInit.
     * Inizializza solo l'identificatore della variabile.
     *
     * @param id Identificatore della variabile
     */
    public VarInit(Object id) {
        this.id = (Identifier) id;
    }

    /**
     * Restituisce l'identificatore della variabile.
     * @return Identificatore della variabile
     */
    public Identifier getId() {
        return id;
    }

    /**
     * Restituisce il valore iniziale della variabile.
     * @return Valore iniziale
     */
    public Expr getInitValue() {
        return initValue;
    }

    /**
     * Restituisce una rappresentazione in stringa dell'oggetto VarInit.
     * @return Rappresentazione in stringa di VarInit
     */
    @Override
    public String toString() {
        return "VarInit{" +
                "id='" + id + '\'' +
                ", initValue=" + initValue +
                ", table=" + tabellaDeiSimboli +
                ", returnType=" + returnType +
                '}';
    }

    /**
     * Metodo di accettazione per il Visitor.
     * Permette di applicare un'operazione definita da un Visitor alla classe VarInit.
     * @param v Visitor da applicare
     * @return Risultato dell'operazione del Visitor
     */
    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
    }
}