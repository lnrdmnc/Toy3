package visitor.utils;

/**
 * La classe RigaTabellaDeiSimboli rappresenta una riga della tabella dei simboli.
 * Contiene informazioni sul tipo, l'identificatore, la firma e se la variabile è un riferimento.
 */
public class RigaTabellaDeiSimboli {

    // Tipo della variabile
    private String tipo;

    // Identificatore della variabile
    private String id;

    // Firma associata alla variabile
    private Firma firma;

    // Indica se la variabile è un riferimento
    private boolean isRef;

    /**
     * Costruttore di default della classe RigaTabellaDeiSimboli.
     */
    public RigaTabellaDeiSimboli() {
    }

    /**
     * Costruttore che inizializza il tipo e l'identificatore della variabile.
     * @param id Identificatore della variabile
     * @param tipo Tipo della variabile
     */
    public RigaTabellaDeiSimboli(String id, String tipo) {
        this.tipo = tipo;
        this.id = id;
    }

    /**
     * Costruttore che inizializza il tipo, l'identificatore e la firma della variabile.
     * @param id Identificatore della variabile
     * @param tipo Tipo della variabile
     * @param firma Firma associata alla variabile
     */
    public RigaTabellaDeiSimboli(String id, String tipo, Firma firma) {
        this.tipo = tipo;
        this.id = id;
        this.firma = firma;
    }

    /**
     * Costruttore che inizializza tutti i campi della classe.
     * @param id Identificatore della variabile
     * @param tipo Tipo della variabile
     * @param firma Firma associata alla variabile
     * @param isRef Indica se la variabile è un riferimento
     */
    public RigaTabellaDeiSimboli(String id, String tipo, Firma firma, boolean isRef) {
        this.tipo = tipo;
        this.id = id;
        this.firma = firma;
        this.isRef = isRef;
    }

    /**
     * Restituisce il tipo della variabile.
     * @return Tipo della variabile
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * Imposta il tipo della variabile.
     * @param tipo Tipo da associare
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * Restituisce l'identificatore della variabile.
     * @return Identificatore della variabile
     */
    public String getId() {
        return id;
    }

    /**
     * Imposta l'identificatore della variabile.
     * @param id Identificatore da associare
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Restituisce la firma associata alla variabile.
     * @return Firma associata
     */
    public Firma getFirma() {
        return firma;
    }

    /**
     * Imposta la firma associata alla variabile.
     * @param firma Firma da associare
     */
    public void setFirma(Firma firma) {
        this.firma = firma;
    }

    /**
     * Restituisce true se la variabile è un riferimento, altrimenti false.
     * @return True se è un riferimento, altrimenti false
     */
    public boolean isRef() {
        return isRef;
    }

    /**
     * Imposta se la variabile è un riferimento.
     * @param ref True se è un riferimento, altrimenti false
     */
    public void setRef(boolean ref) {
        isRef = ref;
    }

    /**
     * Restituisce una rappresentazione in stringa della riga della tabella dei simboli.
     * @return Rappresentazione in stringa della riga
     */
    @Override
    public String toString() {
        return "RigaTabellaDeiSimboli{" +
                "tipo='" + tipo + '\'' +
                ", id='" + id + '\'' +
                ", firma=" + firma +
                ", isRef=" + isRef +
                '}';
    }

    /**
     * Crea e restituisce una copia della riga della tabella dei simboli.
     * @return Clonazione della riga
     */
    public RigaTabellaDeiSimboli clone() {
        RigaTabellaDeiSimboli clone = new RigaTabellaDeiSimboli();
        clone.id = this.id;
        clone.firma = this.firma.clone();
        clone.isRef = this.isRef;
        return clone;
    }

    /**
     * Confronta questa riga con un altro oggetto per verificare l'uguaglianza.
     * @param obj Oggetto da confrontare
     * @return True se gli oggetti sono uguali, altrimenti false
     */
    @Override
    public boolean equals(Object obj) {
        RigaTabellaDeiSimboli riga = (RigaTabellaDeiSimboli) obj;
        return this.id.equals(riga.getId()) && this.tipo.equals(riga.getTipo());
    }
}