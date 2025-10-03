package node.expr.constant;

import node.Type;
import node.Visitor;
import node.expr.Expr;
import visitor.utils.TabellaDeiSimboli;

// La classe RgbNode, come tutte le altre espressioni, estende la classe astratta Expr.
public class RgbNode implements Expr {

    // --- CAMPI SPECIFICI DI RgbNode ---

    /**
     * 1. CAMPO PER MEMORIZZARE IL TIPO
     * Poiché la classe base 'Expr' non gestisce la memorizzazione del tipo,
     * ogni sottoclasse concreta come questa deve avere il proprio campo privato
     * per conservare l'informazione sul tipo, che verrà impostata dal TypeChecker.
     */
    private TabellaDeiSimboli tabellaDeiSimboli;
    private Type type;

    /**
     * 2. CAMPO PER MEMORIZZARE IL VALORE
     * Questo campo conterrà il valore effettivo della costante,
     * ovvero una delle stringhe "red", "green", o "blue".
     * È 'final' perché una volta creato il nodo, il suo valore non deve cambiare.
     */
    private final String value;

    // --- COSTRUTTORE ---

    public RgbNode(String value) {

        // Imposta il valore specifico di questo nodo.
        this.value = value;

        // Inizializza il tipo a null. Sarà compito del TypeChecker
        // calcolare e impostare il tipo corretto (che sarà Type.RGB).
        this.type = null;
    }

    // --- GETTER E SETTER PER IL TIPO ---

    /**
     * 3. GETTER PER IL TIPO (getType)
     * Questo metodo permette alle altre parti del compilatore (come il CodeGenerator)
     * di interrogare il nodo per sapere qual è il suo tipo, una volta che è stato
     * calcolato e impostato dal TypeChecker.
     * L'@Override indica che stiamo fornendo un'implementazione specifica
     * per un metodo dichiarato nella classe base (anche se lì è vuoto).
     */
    @Override
    public Type getType() {
        return this.type;
    }

    /**
     * 4. SETTER PER IL TIPO (setType)
     * Questo è il metodo FONDAMENTALE usato dal TypeChecker. Dopo aver analizzato
     * questo nodo, il TypeChecker chiamerà `rgbNode.setType(Type.RGB)` per "decorare"
     * il nodo con l'informazione semantica sul suo tipo.
     */
    @Override
    public void setType(Type type) {
        this.type = type;
    }

    // --- METODI SPECIFICI DEL NODO ---

    /**
     * Getter per il valore della costante ("red", "green", o "blue").
     * Sarà utilizzato principalmente dal Generatore di Codice C per sapere
     * quale stringa stampare nel file di output.
     */
    public String getValue() {
        return this.value;
    }

    public TabellaDeiSimboli getTabellaDeiSimboli() {
        return tabellaDeiSimboli;
    }

    public void setTabellaDeiSimboli(TabellaDeiSimboli tabellaDeiSimboli) {
        this.tabellaDeiSimboli = tabellaDeiSimboli;
    }

    /**
     * 5. METODO ACCEPT PER IL VISITOR PATTERN
     * Questo è il cuore del Visitor Pattern. Quando un visitor attraversa l'AST
     * e incontra un RgbNode, chiama questo metodo `accept`.
     * Il metodo, a sua volta, richiama il metodo `visit` corretto sul visitor,
     * passandogli se stesso (`this`) come argomento. Questo permette al visitor
     * di eseguire l'azione specifica per un RgbNode.
     */
    @Override
    public Object accept(Visitor visitor) {
        return visitor.visit(this);
    }
}