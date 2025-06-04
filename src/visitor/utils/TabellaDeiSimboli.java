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

    public void aggiungiRiga(RigaTabellaDeiSimboli riga) throws Duplicato{
        if(rigaLista.contains(riga)) {
            throw new Duplicato("Riga già presente nella tabella dei simboli");
        }
        this.rigaLista.add(riga);
        return;
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


    public RigaTabellaDeiSimboli getRiga(Identifier nodo, String tipo) {
        RigaTabellaDeiSimboli daCercare = new RigaTabellaDeiSimboli(nodo.getName(), tipo);
        if (rigaLista != null) {
            for (RigaTabellaDeiSimboli corrente : rigaLista) {
                if (corrente.equals(daCercare)){
                    return corrente;
                }
            }
        }
        return null;
    }

    public TabellaDeiSimboli clone(){
        TabellaDeiSimboli clone = new TabellaDeiSimboli(nome);
        if(this.rigaLista!= null){
            clone.rigaLista = new ArrayList<>();
            for (RigaTabellaDeiSimboli riga : this.rigaLista) {
            clone.rigaLista.add(riga.clone());
            }
        }

        if(this.padre != null){
            clone.padre = (TabellaDeiSimboli) this.padre.clone();
        }
        clone.nome= this.nome;
        return clone;
    }

    public boolean contains(Identifier node, String tipo){
        System.out.println(rigaLista);
        RigaTabellaDeiSimboli daCercare = new RigaTabellaDeiSimboli(node.getName(), tipo);
        if (rigaLista != null) {
            for (RigaTabellaDeiSimboli corrente : rigaLista) {
                if (corrente.equals(daCercare)) return true;
            }
        }
        return false;
    }


}
