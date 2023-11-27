package main;// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.

import genInfo.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CodePointCharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Stats {

    private CharStream text;
    private String type;
    private int totalLines = 0;
    private int totalFunctions = 0;
    private int totalGlobalVariables = 0;
    private int totalIfStatements = 0;
    private int totalForStatements = 0;
    private int totalWhileStatements = 0;
    private double totalVariableNameLength = 0;
    private double totalFunctionNameLength = 0;
    private int totalVariables = 0;
    private int totalFunctionsVisited = 0;
    private Set<String> globalVariables = new HashSet<>();
    private Set<String> externalDependencies = new HashSet<>();
    private Stack<String> scope = new Stack<>();
    private Map<String, Set<String>> functionDependencies = new HashMap<>();
    HashMap<String, String> simbolTable = new HashMap<>();
    private HashMap<String, HashMap<String, String>> localTable = new HashMap<>();
    private ArrayList<FunctionSats> functions=new ArrayList<>();
    private ArrayList<ClassStats> classes=new ArrayList<>();
    private double operants=0, ocurOperants=0, ocurOperators =0;
    private Set<String> operators= new HashSet<>();
    private StringBuffer code = new StringBuffer();
    private double length;
    private double volumen;
    private double difficulty;
    private double time;

    public Stats(String code, String type){
        try{
            FileWriter myObj = new FileWriter("input.txt");
            myObj.write(code);
            myObj.close();
            this.text= CharStreams.fromFileName("input.txt");
        } catch (IOException e) {
            System.out.println("An error occurred.");
        }
        System.out.println(type);
        this.type=type;
    }

    public boolean getStats(){
        try{
            if(this.type.equals("Python")){
                System.out.println(this.text);
                PythonLexer lexer=new PythonLexer(this.text);
                CommonTokenStream tokens = new CommonTokenStream(lexer);
                PythonParser parser = new PythonParser(tokens);
                ParseTree newTree = parser.file_input();
                CodeStatisticsVisitor loader1 = new CodeStatisticsVisitor();
                loader1.visit(newTree);
                totalLines= loader1.getTotalLines();
                functions= loader1.getFunctions();
                globalVariables= loader1.getGlobalVariables();
                totalIfStatements= loader1.getTotalIfStatements();
                totalForStatements= loader1.getTotalForStatements();
                totalWhileStatements= loader1.getTotalWhileStatements();
                length= loader1.getLength();
                totalVariableNameLength= roundAvoid(loader1.getTotalVariableNameLength(), 4);
                totalFunctionNameLength= roundAvoid(loader1.getTotalFunctionNameLength(), 4);
                volumen=roundAvoid(loader1.getVolumen(), 4);
                difficulty= roundAvoid(loader1.getDifficulty(), 4);
                time=roundAvoid(loader1.getTime(),4);
                System.out.println(loader1.getFunctionsLocal());
                System.out.println(loader1.getFunctionsDependency());
                System.out.println(loader1.getExternalDependency());
                System.out.println("----------------------------");
                PythonLexer lexer2=new PythonLexer(this.text);
                CommonTokenStream tokens2 = new CommonTokenStream(lexer2);
                PythonParser parser2 = new PythonParser(tokens2);
                ExtendedVisitors visitor1 = new ExtendedVisitors();
                visitor1.setFunctions(loader1.getFunctions());
                visitor1.visit(parser2.file_input());
            }else{
                Java8Lexer javaLexer=new Java8Lexer(this.text);
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
                totalLines= loader.getTotalLines();
                functions= loader.getFunctions();
                globalVariables= loader.getGlobalVariables();
                totalIfStatements= loader.getTotalIfStatements();
                totalForStatements= loader.getTotalForStatements();
                totalWhileStatements= loader.getTotalWhileStatements();
                length= loader.getLength();
                totalVariableNameLength= roundAvoid(loader.getTotalVariableNameLength(), 4);
                totalFunctionNameLength= roundAvoid(loader.getTotalFunctionNameLength(), 4);
                volumen=roundAvoid(loader.getVolumen(), 4);
                difficulty= roundAvoid(loader.getDifficulty(), 4);
                time=roundAvoid(loader.getTime(),4);
                Java8Lexer lexer2Java=new Java8Lexer(this.text);
                CommonTokenStream tokens2Java = new CommonTokenStream(lexer2Java);
                Java8Parser parser2Java = new Java8Parser(tokens2Java);
                ExtendedVisitorsJava visitor1Java = new ExtendedVisitorsJava();
                visitor1Java.setFunctions(loader.getFunctions());
                visitor1Java.visit(parser2Java.compilationUnit());
                System.out.println("----------------------------");
                Java8Lexer lexer3Java=new Java8Lexer(this.text);
                CommonTokenStream tokens3Java = new CommonTokenStream(lexer3Java);
                Java8Parser parser3Java = new Java8Parser(tokens3Java);
                ParseTree tree3 = parser3Java.compilationUnit();
                ParseTreeWalker walker = new ParseTreeWalker();
                FlowChartJava graphListener = new FlowChartJava();
                walker.walk(graphListener, tree3);
                graphListener.myFunctionsPrint();
                System.out.println(graphListener.myFunctions.toString());
            }
            return true;
        } catch (Exception e){
            System.err.println("Error (Test): " + e);
            return false;
        }
    }

    public int getTotalLines() {
        return totalLines;
    }

    public ArrayList<FunctionSats> getFunctions() {
        return functions;
    }

    public int getTotalGlobalVariables() {
        return totalGlobalVariables;
    }

    public int getTotalIfStatements() {
        return totalIfStatements;
    }

    public int getTotalForStatements() {
        return totalForStatements;
    }

    public int getTotalWhileStatements() {
        return totalWhileStatements;
    }

    public double getTotalVariableNameLength() {
        return totalVariableNameLength;
    }

    public double getTotalFunctionNameLength() {
        return totalFunctionNameLength;
    }

    public Set<String> getGlobalVariables() {
        return globalVariables;
    }

    public Set<String> getExternalDependencies() {
        return externalDependencies;
    }

    public double getLength() {
        return length;
    }

    public double getVolumen() {
        return volumen;
    }

    public double getDifficulty() {
        return difficulty;
    }

    public double getTime() {
        return time;
    }

    public static double roundAvoid(double value, int places) {
        double scale = Math.pow(10, places);
        return Math.round(value * scale) / scale;
    }

}