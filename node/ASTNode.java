package node;

import java.util.ArrayList;
import java.util.List;

public abstract class ASTNode {

    private String nodeName;  // Nome del nodo (utile per debug e analisi)
    private List<ASTNode> children; // Figli del nodo (se applicabile

    public ASTNode(String nodeName) {
        this.nodeName = nodeName;
        this.children = new ArrayList<>();
    }

    // Aggiunge un figlio al nodo
    public void addChild(ASTNode child) {
        this.children.add(child);
    }

    // Ritorna la lista di figli
    public List<ASTNode> getChildren() {
        return children;
    }

    // Ritorna il nome del nodo
    public String getNodeName() {
        return nodeName;
    }

    @Override
    public String toString() {
        return "ASTNode{name='" + nodeName + "', children=" + children + "}";
    }
}


