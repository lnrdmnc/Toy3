package visitor.utils;

import node.expr.constant.Identifier;

import java.util.ArrayList;

/**
 * La classe TabellaDeiSimboli rappresenta una tabella dei simboli utilizzata per gestire
 * le informazioni sulle variabili e i loro contesti in un programma.
 * Ogni tabella può contenere una lista di righe, un nome e un riferimento alla tabella padre.
 */
public class TabellaDeiSimboli {

    // Lista delle righe della tabella dei simboli
    private ArrayList<RigaTabellaDeiSimboli> rigaLista;

    // Nome della tabella dei simboli
    private String nome;

    // Riferimento alla tabella padre (per la gestione dei contesti)
    private TabellaDeiSimboli padre;

    /**
     * Costruttore che inizializza la tabella con un nome specifico.
     * @param nome Nome della tabella dei simboli
     */
    public TabellaDeiSimboli(String nome) {
        this.rigaLista = new ArrayList<>();
        this.nome = nome;
    }

    /**
     * Costruttore che inizializza la tabella con una lista di righe e un nome.
     * @param rigaLista Lista di righe della tabella
     * @param nome Nome della tabella
     */
    public TabellaDeiSimboli(ArrayList<RigaTabellaDeiSimboli> rigaLista, String nome) {
        this.rigaLista = rigaLista;
        this.nome = nome;
    }

    /**
     * Aggiunge una riga alla tabella dei simboli.
     * Se la riga è già presente, viene lanciata un'eccezione.
     * @param riga Riga da aggiungere
     * @throws Duplicato Se la riga è già presente nella tabella
     */
    public void aggiungiRiga(RigaTabellaDeiSimboli riga) throws Duplicato {
        if (rigaLista.contains(riga)) {
            throw new Duplicato("Riga già presente nella tabella dei simboli");
        }
        this.rigaLista.add(riga);
    }

    /**
     * Restituisce la lista delle righe della tabella.
     * @return Lista delle righe
     */
    public ArrayList<RigaTabellaDeiSimboli> getRigaLista() {
        return rigaLista;
    }

    /**
     * Imposta la lista delle righe della tabella.
     * @param rigaLista Lista di righe da associare
     */
    public void setRigaLista(ArrayList<RigaTabellaDeiSimboli> rigaLista) {
        this.rigaLista = rigaLista;
    }

    /**
     * Restituisce il nome della tabella.
     * @return Nome della tabella
     */
    public String getNome() {
        return nome;
    }

    /**
     * Imposta il nome della tabella.
     * @param nome Nome da associare
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Restituisce la tabella padre.
     * @return Tabella padre
     */
    public TabellaDeiSimboli getPadre() {
        return padre;
    }

    /**
     * Imposta la tabella padre.
     * @param padre Tabella padre da associare
     */
    public void setPadre(TabellaDeiSimboli padre) {
        this.padre = padre;
    }

    /**
     * Restituisce una riga della tabella corrispondente a un identificatore e un tipo.
     * @param nodo Identificatore da cercare
     * @param tipo Tipo associato
     * @return Riga corrispondente o null se non trovata
     */
    public RigaTabellaDeiSimboli getRiga(Identifier nodo, String tipo) {
        RigaTabellaDeiSimboli daCercare = new RigaTabellaDeiSimboli(nodo.getName(), tipo);
        if (rigaLista != null) {
            for (RigaTabellaDeiSimboli corrente : rigaLista) {
                if (corrente.equals(daCercare)) {
                    return corrente;
                }
            }
        }
        return null;
    }

    /**
     * Verifica se la tabella contiene una riga con un identificatore e un tipo specifici.
     * @param node Identificatore da cercare
     * @param tipo Tipo associato
     * @return True se la riga è presente, altrimenti false
     */
    public boolean contains(Identifier node, String tipo) {
        RigaTabellaDeiSimboli daCercare = new RigaTabellaDeiSimboli(node.getName(), tipo);
        if (rigaLista != null) {
            for (RigaTabellaDeiSimboli corrente : rigaLista) {
                if (corrente.equals(daCercare)) return true;
            }
        }
        return false;
    }

    /**
     * Crea e restituisce una copia della tabella dei simboli.
     * @return Clonazione della tabella
     */
    public TabellaDeiSimboli clone() {
        TabellaDeiSimboli clone = new TabellaDeiSimboli(nome);
        if (this.rigaLista != null) {
            clone.rigaLista = new ArrayList<>();
            for (RigaTabellaDeiSimboli riga : this.rigaLista) {
                clone.rigaLista.add(riga.clone());
            }
        }
        if (this.padre != null) {
            clone.padre = this.padre.clone();
        }
        return clone;
    }

    /**
     * Restituisce una rappresentazione in stringa della tabella dei simboli.
     * @return Rappresentazione in stringa della tabella
     */
    @Override
    public String toString() {
        return "TabellaDeiSimboli{" +
                "rigaLista=" + rigaLista +
                ", nome='" + nome + '\'' +
                ", padre=" + padre +
                '}';
    }
}