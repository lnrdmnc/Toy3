package node.defdecl;

import node.ASTNode;
import node.Visitor;
/**
 * L'interfaccia Decl rappresenta una dichiarazione generica all'interno dell'albero sintattico astratto (AST).
 * Estende ASTNode, quindi ogni classe che implementa Decl è un nodo dell'AST.
 * Fornisce un metodo per accettare un Visitor, che consente di applicare operazioni specifiche ai nodi.
 */
public interface Decl {
    Object accept(Visitor v);
}
