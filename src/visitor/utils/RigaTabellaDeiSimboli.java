package visitor.utils;

public class RigaTabellaDeiSimboli {

    private String tipo;
    private String id;
    private Firma firma;
    private boolean isRef;

    public RigaTabellaDeiSimboli() {
    }

    public RigaTabellaDeiSimboli(String tipo, String id) {
        this.tipo = tipo;
        this.id = id;
    }

    public RigaTabellaDeiSimboli(String tipo, String id, Firma firma) {
        this.tipo = tipo;
        this.id = id;
        this.firma = firma;
    }

    public RigaTabellaDeiSimboli(String tipo, String id, Firma firma, boolean isRef) {
        this.tipo = tipo;
        this.id = id;
        this.firma = firma;
        this.isRef = isRef;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Firma getFirma() {
        return firma;
    }

    public void setFirma(Firma firma) {
        this.firma = firma;
    }

    public boolean isRef() {
        return isRef;
    }

    public void setRef(boolean ref) {
        isRef = ref;
    }

    @Override
    public String toString() {
        return "RigaTabellaDeiSimboli{" +
                "tipo='" + tipo + '\'' +
                ", id='" + id + '\'' +
                ", firma=" + firma +
                ", isRef=" + isRef +
                '}';
    }

    public RigaTabellaDeiSimboli clone (){
        RigaTabellaDeiSimboli clone= new RigaTabellaDeiSimboli();
        clone.id=this.id;
        clone.firma=this.firma.clone();
        clone.isRef=this.isRef;
        return clone;
    }

    @Override
    public boolean equals(Object obj) {
       RigaTabellaDeiSimboli riga= (RigaTabellaDeiSimboli) obj;
        return this.id.equals(riga.getId()) && riga.tipo.equals(riga.getTipo());
    }
}
