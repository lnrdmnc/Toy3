package visitor;

import java_cup.runtime.SyntaxTreeDFS;
import node.ASTNode;
import node.Stat;
import node.Type;
import node.Visitor;
import node.defdecl.Decl;
import node.defdecl.DefDecl;
import node.expr.Expr;
import node.expr.constant.*;
import node.expr.operation.BinaryOp;
import node.expr.operation.FunCall;
import node.expr.operation.UnaryOp;
import node.program.ProgramOp;
import node.stat.*;
import node.vardecl.VarDecl;
import node.vardecl.VarInit;
import visitor.utils.TabellaDeiSimboli;

import java.util.Stack;

public class TypeCheck implements Visitor {

    private Stack<TabellaDeiSimboli> typeenv;
    private TabellaDeiSimboli current_table;


    @Override
    public Object visitProgramOp(ProgramOp programOp) {
        typeenv.add(programOp.getTabellaDeiSimboliProgram());
        if(programOp.getDeclarations() != null){
            for(Decl decl : programOp.getDeclarations())
            {
                ASTNode node = (ASTNode)decl;
                ASTNode.accept(this);

            }

            typeenv.add(programOp.getTabellaBegEnd());
            if(typeenv.add(programOp.getTabellaBegEnd()) != null){
                for(Decl decl : programOp.getVarDeclarations()))
                {
                    ASTNode node = (ASTNode) decl;
                    ASTNode.accept(this);

                }
            }

            if(typeenv.add(programOp.getStatements()!= null)) {
                for(Stat stat : programOp.getStatements())
                {
                    ASTNode node = (ASTNode) stat;
                    Type type = (Type) node.accept(this);
                    if(type==null){
                        throw new RuntimeException("Statements not valid.");
                    }
                    ASTNode.accept(this);
                }
                typeenv.pop();// BeginEndTable pop
                typeenv.pop(); // Program pop
                programOp.setType(Type.NOTYPE);
                return null;
            }
        }
        return null;
    }

    @Override
    public Object visitDefDecl(DefDecl defDecl) {
        return null;
    }

    @Override
    public Object visitVarDecl(VarDecl varDecl) {
        return null;
    }

    @Override
    public Object visitVarInit(VarInit varInit) {
       if(varInit.getInitValue() != null) {
           varInit.getInitValue().accept(this);
           varInit.setReturnType(varInit.getInitValue().getType());
       }
       else
       {
           varInit.setReturnType(null);
       }
       return null;
    }

    @Override
    public Object visitBinaryOp(BinaryOp binaryOp) {
        Expr leftOperand = binaryOp.getLeft();
        Expr rightOperand = binaryOp.getRight();
        leftOperand.accept(this);
        rightOperand.accept(this);
        binaryOp.setType(this.OpTypeChecker(binaryOp));
        return binaryOp.getType();
        return null;
    }

    @Override
    public Object visitUnaryOp(UnaryOp unaryOp) {

       Expr operand = unaryOp.getOperand();
       operand.accept(this);
       unaryOp.setType(this.unaryChecker(unaryOp));
       return unaryOp.getType();

    }

    @Override
    public Object visitFunCall(FunCall funCall) {
        return null;
    }

    @Override
    public Object visitIdentifier(Identifier identifier) {
        return null;
    }

    @Override
    public Object visitIdentifier(CharNode charNode) {
        return null;
    }

    @Override
    public Object visitIdentifier(DoubleNode doubleNode) {
        return null;
    }

    @Override
    public Object visitIdentifier(IntegerNode integerNode) {
        return null;
    }

    @Override
    public Object visitIdentifier(StringNode stringNode) {
        return null;
    }

    @Override
    public Object visitIdentifier(TrueNode trueNode) {
        return null;
    }

    @Override
    public Object visitAssignOp(AssignOp assignOp) {
        return null;
    }

    @Override
    public Object visitWriteOperationNode(WriteOp writeOp) {

       TabellaDeiSimboli table = typeenv.peek();
        writeOp.setTabella(table);

        if (writeOp.getExpressions() != null) {
            for (Expr expressions : writeOp.getExpressions()) {
                expressions.accept(this);
            }
        }

        writeOp.setType(Type.NOTYPE);
        return writeOp.getType();
        return null;
    }

    @Override
    public Object visitIfThen(IfThenNode ifThen) {
        return null;
    }

    @Override
    public Object visitIfThenElse(IfThenElse ifThenElse) {
        return null;
    }

    @Override
    public Object visitReadOp(ReadOp readOp) {
        if (readOp.getList() != null) {
            for (Identifier variables : readOp.getList()) {
                variables.accept(this);
            }
        }
        readOp.setType(Type.NOTYPE);
        return readOp.getType();
    }

    @Override
    public Object visitWhileOp(WhileOp whileOp) {
        return null;
    }

    @Override
    public Object visitReturnOp(ReturnStat returnOp) {

        Expr result = returnOp.getResult();
        Type exprType = (Type) result.accept(this);
        returnOp.setType(Type.NOTYPE);
        return null;
    }
}
