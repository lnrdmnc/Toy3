package node.defdecl;

import node.ASTNode;
import node.Visitor;

public interface Decl extends ASTNode {

    Object accept(Visitor v);
}
