package main;// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.

import genInfo.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.io.IOException;
import java.util.ArrayList;

public class Stats {
    public void getStats(){
        try{
            PythonLexer lexer=new PythonLexer(CharStreams.fromFileName("input/input.txt"));
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            PythonParser parser = new PythonParser(tokens);
            ParseTree newTree = parser.file_input();
            CodeStatisticsVisitor loader1 = new CodeStatisticsVisitor();
            loader1.visit(newTree);
            System.out.println(loader1.getFunctionsLocal());
            System.out.println(loader1.getFunctionsDependency());
            System.out.println(loader1.getExternalDependency());
            System.out.println("----------------------------");
            PythonLexer lexer2=new PythonLexer(CharStreams.fromFileName("input/input.txt"));
            CommonTokenStream tokens2 = new CommonTokenStream(lexer2);
            PythonParser parser2 = new PythonParser(tokens2);
            ExtendedVisitors visitor1 = new ExtendedVisitors();
            visitor1.setFunctions(loader1.getFunctions());
            visitor1.visit(parser2.file_input());


            Java8Lexer javaLexer=new Java8Lexer(CharStreams.fromFileName("input/input2.txt"));
            // Identificar al analizador léxico como fuente de tokens para el sintactico
            CommonTokenStream javaTokens = new CommonTokenStream(javaLexer);
            // Crear el objeto del analizador sintáctico a partir del buffer de tokens
            Java8Parser javaParser = new Java8Parser(javaTokens);
            ParseTree tree = javaParser.compilationUnit();
            CodeSatisticsVisitorJava loader = new CodeSatisticsVisitorJava();
            loader.visit(tree);
            System.out.println(loader.getFunctionsLocal());
            System.out.println(loader.getFunctionsDependency());
            System.out.println(loader.getExternalDependency());
            System.out.println("----------------------------");
            Java8Lexer lexer2Java=new Java8Lexer(CharStreams.fromFileName("input/input2.txt"));
            CommonTokenStream tokens2Java = new CommonTokenStream(lexer2Java);
            Java8Parser parser2Java = new Java8Parser(tokens2Java);
            ExtendedVisitorsJava visitor1Java = new ExtendedVisitorsJava();
            visitor1Java.setFunctions(loader.getFunctions());
            visitor1Java.visit(parser2Java.compilationUnit());
            System.out.println("----------------------------");
            Java8Lexer lexer3Java=new Java8Lexer(CharStreams.fromFileName("input/input2.txt"));
            CommonTokenStream tokens3Java = new CommonTokenStream(lexer3Java);
            Java8Parser parser3Java = new Java8Parser(tokens3Java);
            ParseTree tree3 = parser3Java.compilationUnit();
            ParseTreeWalker walker = new ParseTreeWalker();
            FlowChartJava graphListener = new FlowChartJava();
            walker.walk(graphListener, tree);
            graphListener.myFunctionsPrint();
            System.out.println(graphListener.myFunctions.toString());
        } catch (Exception e){
            System.err.println("Error (Test): " + e);
        }
    }
}