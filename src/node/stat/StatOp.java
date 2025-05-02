package node.stat;

import node.ASTNode;
import node.Visitor;


public interface StatOp extends ASTNode
{
    Object accept(Visitor v);
}
