
package node;

import node.defdecl.*;
import node.expr.constant.*;
import node.expr.operation.*;
import node.vardecl.*;

import node.program.*;
import node.stat.*;
import node.pardecl.*;

import node.body.*;

public interface Visitor<T> {
    // Programma
    T visit(ProgramOp programOp);

    // Dichiarazioni DefDecl
    T visit(DefDecl defDecl);

    // Dichiarazioni VarDecl
    T visit(VarDecl varDecl);
    T visit(VarInit varInit);

    // Espressioni T visitConstant(Constant constant);
    T visit(BinaryOp binaryOp);
    T visit(UnaryOp unaryOp);
    T visit(FunCall funCall);

    // Espressioni Costanti
    T visit(Identifier identifier);
    T visit(CharNode charNode);
    T visit(DoubleNode doubleNode);
    T visit(IntegerNode integerNode);
    T visit(StringNode stringNode);
    T visit(TrueNode trueNode);
    T visit(FalseNode falseNode);

    // Istruzioni (Statements)
    T visit(AssignOp assignOp);
    T visit(WriteOp writeOp);
    T visit(IfThenNode ifThen);
    T visit(IfThenElse ifThenElse);
    T visit(ReadOp readOp);
    T visit(WhileOp whileOp);
    T visit(ReturnStat returnOp);
    T visit(ParDecl parDecl);
    T visit(ParVar parVar);
    T visit(BodyOp bodyOp);
    T visit(CascadeForOp cascadeForOp);
}
