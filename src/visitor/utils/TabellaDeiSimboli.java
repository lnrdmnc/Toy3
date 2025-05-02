package visitor.utils;

import node.expr.constant.Identifier;

import java.util.ArrayList;

public class TabellaDeiSimboli {

    private ArrayList<RigaTabellaDeiSimboli> rigaLista;
    private String nome;
    private TabellaDeiSimboli padre;

    public TabellaDeiSimboli(String nome) {
        this.rigaLista = new ArrayList<>();
        this.nome = nome;
    }
    
    //forse serve fare un eccezzione?

    public void aggiungiRiga(RigaTabellaDeiSimboli riga) throws RuntimeException{
        if(rigaLista.contains(riga)) {

            throw new RuntimeException("Riga già presente nella tabella dei simboli");
            return;
        }
        this.rigaLista.add(riga);
    }

    public TabellaDeiSimboli(ArrayList<RigaTabellaDeiSimboli> rigaLista, String nome) {
        this.rigaLista = rigaLista;
        this.nome = nome;
    }

    public ArrayList<RigaTabellaDeiSimboli> getRigaLista() {
        return rigaLista;
    }

    public String getNome() {
        return nome;
    }

    public TabellaDeiSimboli getPadre() {
        return padre;
    }

    public void setRigaLista(ArrayList<RigaTabellaDeiSimboli> rigaLista) {
        this.rigaLista = rigaLista;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setPadre(TabellaDeiSimboli padre) {
        this.padre = padre;
    }

    @Override
    public String toString() {
        return "TabellaDeiSimboli{" +
                "rigaLista=" + rigaLista +
                ", nome='" + nome + '\'' +
                ", padre=" + padre +
                '}';
    }

    // non mi piace questo da cercare devo capire meglio il funzionamento
    public boolean contains(Identifier nodo, String tipo) {
        RigaTabellaDeiSimboli daCercare = new RigaTabellaDeiSimboli(nodo.getName(), tipo);
        if (rigaLista != null) {
            for (RigaTabellaDeiSimboli corrente : rigaLista) {
                if (corrente.equals(daCercare)) return true;
            }
        }
        return false;
    }


}
