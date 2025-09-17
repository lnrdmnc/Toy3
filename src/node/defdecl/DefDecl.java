package node.defdecl;

import node.ASTNode;
import node.Type;
import node.Visitor;
import node.body.BodyOp;
import node.expr.constant.Identifier;
import node.pardecl.ParDecl;
import visitor.utils.TabellaDeiSimboli;

import java.util.ArrayList;

/**
 * La classe DefDecl rappresenta una dichiarazione di funzione o metodo.
 * Contiene informazioni sull'identificatore, il tipo, il corpo e i parametri della funzione.
 * Estende ASTNode e implementa l'interfaccia Decl, quindi fa parte dell'albero sintattico astratto (AST).
 */
public class DefDecl extends ASTNode implements Decl {

    // Identificatore della funzione (nome della funzione)
    private Identifier id;

    // Tipo di ritorno della funzione
    private Type type;

    // Corpo della funzione, rappresentato da un oggetto BodyOp
    private BodyOp body;

    // Lista dei parametri della funzione
    private ArrayList<ParDecl> list;

    // Tabella dei simboli associata alla funzione, utile per la gestione degli scope
    private TabellaDeiSimboli tabellaDeiSimboli;

    /**
     * Restituisce la tabella dei simboli associata alla funzione.
     * @return TabellaDeiSimboli
     */
    public TabellaDeiSimboli getTabellaDeiSimboli() {
        return tabellaDeiSimboli;
    }

    /**
     * Imposta la tabella dei simboli per la funzione.
     * @param tabellaDeiSimboli Tabella dei simboli da associare
     */
    public void setTabellaDeiSimboli(TabellaDeiSimboli tabellaDeiSimboli) {
        this.tabellaDeiSimboli = tabellaDeiSimboli;
    }

    /**
     * Costruttore della classe DefDecl per funzioni con parametri.
     * Inizializza l'identificatore, il tipo, il corpo e la lista dei parametri.
     * @param list Lista dei parametri della funzione
     * @param id Identificatore della funzione
     * @param type Tipo di ritorno della funzione
     * @param body Corpo della funzione
     */
    public DefDecl(Object list, Object id, Object type, Object body) {
        this.list = (ArrayList<ParDecl>) list;
        this.id = (Identifier) id;
        this.type = (Type) type;
        this.body = (BodyOp) body;
    }

    /**
     * Costruttore della classe DefDecl per funzioni senza parametri.
     * Inizializza l'identificatore, il tipo e il corpo.
     * @param id Identificatore della funzione
     * @param type Tipo di ritorno della funzione
     * @param body Corpo della funzione
     */
    public DefDecl(Object id, Object type, Object body) {
        this.id = (Identifier) id;
        this.type = (Type) type;
        this.body = (BodyOp) body;
    }

    /**
     * Restituisce l'identificatore della funzione.
     * @return Identificatore della funzione
     */
    public Identifier getId() {
        return id;
    }

    /**
     * Imposta l'identificatore della funzione.
     * @param id Identificatore da associare
     */
    public void setId(Identifier id) {
        this.id = id;
    }

    /**
     * Restituisce il tipo di ritorno della funzione.
     * @return Tipo di ritorno
     */
    public Type getType() {
        return type;
    }

    /**
     * Imposta il tipo di ritorno della funzione.
     * @param type Tipo di ritorno da associare
     */
    public void setType(Type type) {
        this.type = type;
    }

    /**
     * Restituisce il corpo della funzione.
     * @return Corpo della funzione
     */
    public BodyOp getBody() {
        return body;
    }

    /**
     * Imposta il corpo della funzione.
     * @param body Corpo da associare
     */
    public void setBody(BodyOp body) {
        this.body = body;
    }

    /**
     * Restituisce la lista dei parametri della funzione.
     * @return Lista dei parametri
     */
    public ArrayList<ParDecl> getList() {
        return list;
    }

    /**
     * Imposta la lista dei parametri della funzione.
     * @param list Lista dei parametri da associare
     */
    public void setList(ArrayList<ParDecl> list) {
        this.list = list;
    }

    /**
     * Restituisce una rappresentazione testuale della dichiarazione di funzione.
     * @return Stringa che rappresenta la dichiarazione di funzione
     */
    @Override
    public String toString() {
        return "DefDecl{" +
                "id=" + id +
                ", type=" + type +
                ", body=" + body +
                ", list=" + list +
                '}';
    }

    /**
     * Metodo di accettazione per il Visitor.
     * Permette di applicare un'operazione definita da un Visitor alla classe DefDecl.
     * @param v Visitor da applicare
     * @return Risultato dell'operazione del Visitor
     */
    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
    }
}