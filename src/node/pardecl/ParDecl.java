package node.pardecl;

import node.Type;
import node.ASTNode;
import node.Visitor;
import visitor.utils.TabellaDeiSimboli;

import java.util.List;

/**
 * La classe ParDecl rappresenta una dichiarazione di parametro nell'AST.
 * Include il tipo del parametro, una lista di variabili parametro e una tabella dei simboli associata.
 */
public class ParDecl extends ASTNode {

    // Tipo del parametro (es: int, bool, string, ecc.)
    private Type type;

    // Lista di variabili parametro
    private List<ParVar> variables;

    // Tabella dei simboli associata alla dichiarazione di parametro
    private TabellaDeiSimboli tabellaDeiSimboli;

    /**
     * Costruttore della classe ParDecl.
     * Inizializza il tipo del parametro e la lista di variabili parametro.
     *
     * @param type Tipo del parametro
     * @param variables Lista di variabili parametro
     */
    public ParDecl(Type type, List<ParVar> variables) {
        this.type = type;
        this.variables = variables;
    }

    /**
     * Restituisce il tipo del parametro.
     * @return Tipo del parametro
     */
    public Type getType() {
        return type;
    }

    /**
     * Imposta il tipo del parametro.
     * @param type Tipo da associare
     */
    public void setType(Type type) {
        this.type = type;
    }

    /**
     * Restituisce la lista di variabili parametro.
     * @return Lista di variabili parametro
     */
    public List<ParVar> getVariables() {
        return variables;
    }

    /**
     * Imposta la lista di variabili parametro.
     * @param variables Lista di variabili parametro da associare
     */
    public void setVariables(List<ParVar> variables) {
        this.variables = variables;
    }

    /**
     * Restituisce la tabella dei simboli associata alla dichiarazione di parametro.
     * @return Tabella dei simboli
     */
    public TabellaDeiSimboli getTabellaDeiSimboli() {
        return tabellaDeiSimboli;
    }

    /**
     * Imposta la tabella dei simboli per la dichiarazione di parametro.
     * @param tabellaDeiSimboli Tabella dei simboli da associare
     */
    public void setTabellaDeiSimboli(TabellaDeiSimboli tabellaDeiSimboli) {
        this.tabellaDeiSimboli = tabellaDeiSimboli;
    }

    /**
     * Metodo di accettazione per il Visitor.
     * Permette di applicare un'operazione definita da un Visitor alla classe ParDecl.
     * @param visitor Visitor da applicare
     * @return Risultato dell'operazione del Visitor
     */
    @Override
    public Object accept(Visitor visitor) {
        return visitor.visit(this);
    }

    /**
     * Restituisce una rappresentazione in stringa dell'oggetto ParDecl.
     * @return Rappresentazione in stringa di ParDecl
     */
    @Override
    public String toString() {
        return "ParDecl{" +
                "type=" + type +
                ", variables=" + variables +
                '}';
    }
}