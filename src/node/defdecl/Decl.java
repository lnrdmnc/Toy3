package node.defdecl;

import node.ASTNode;
import node.Visitor;

public interface Decl {
    Object accept(Visitor v);
}
