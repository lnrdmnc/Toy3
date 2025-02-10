import java_cup.runtime.Symbol;
import main.java.compiler.Lexer;
import main.java.compiler.Parser;
import node.ASTNode;
import node.Stat;
import node.Type;
import node.body.BodyOp;
import node.defdecl.Decl;
import node.defdecl.DefDecl;
import node.expr.constant.Identifier;
import node.expr.constant.IntegerNode;
import node.expr.operation.BinaryOp;
import node.expr.operation.FunCall;
import node.pardecl.ParDecl;
import node.program.ProgramOp;
import node.stat.AssignOp;
import node.stat.ReturnStat;
import node.vardecl.VarDecl;
import node.vardecl.VarInit;
import org.apache.commons.io.FilenameUtils;

import javax.swing.*;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Test {


    public static void main(String[] args) throws Exception {
        String outputFolder = "test_files/c_out";
        Path inputPath = Paths.get(args[0]);
        String inputFileName = inputPath.getFileName().toString();
        String CinputFileName = FilenameUtils.removeExtension(inputFileName) + ".c";

        Lexer lexer = new Lexer(new FileReader(inputPath.toString()));
        Parser p = new Parser(lexer);
        ProgramOp pr = (ProgramOp) p.debug_parse().value;
    }
}
