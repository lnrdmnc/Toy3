
package node;

import node.defdecl.*;
import node.expr.constant.*;
import node.expr.operation.*;
import node.vardecl.*;
import node.vardecl.*;
import node.expr.*;
import node.program.*;
import node.stat.*;
import node.pardecl.*;
import node.pardecl.*;
import node.vardecl.*;
import node.body.*;

public interface Visitor<T> {
    // Programma
    T visitProgramOp(ProgramOp programOp);

    // Dichiarazioni DefDecl
    T visitDefDecl(DefDecl defDecl);

    // Dichiarazioni VarDecl
    T visitVarDecl(VarDecl varDecl);
    T visitVarInit(VarInit varInit);

    // Espressioni T visitConstant(Constant constant);
    T visitBinaryOp(BinaryOp binaryOp);
    T visitUnaryOp(UnaryOp unaryOp);
    T visitFunCall(FunCall funCall);

    // Espressioni Costanti
    T visitIdentifier(Identifier identifier);
    T visitIdentifier(CharNode charNode);
    T visitIdentifier(DoubleNode doubleNode);
    T visitIdentifier(IntegerNode integerNode);
    T visitIdentifier(StringNode stringNode);
    T visitIdentifier(TrueNode trueNode);

    // Istruzioni (Statements)
    T visitAssignOp(AssignOp assignOp);
    T visitWriteOperationNode(WriteOp writeOp);
    T visitIfThen(IfThenNode ifThen);
    T visitIfThenElse(IfThenElse ifThenElse);
    T visitReadOp(ReadOp readOp);
    T visitWhileOp(WhileOp whileOp);
    T visitReturnOp(ReturnStat returnOp);

}
  7