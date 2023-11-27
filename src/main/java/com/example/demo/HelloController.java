package com.example.demo;

import genInfo.CodeStatisticsVisitor;
import genInfo.FlowChartJava;
import genInfo.FunctionSats;
import genInfo.GraphListenerXOXO;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import javafx.event.ActionEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import main.Stats;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.graphstream.algorithm.generator.DorogovtsevMendesGenerator;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.fx_viewer.FxViewPanel;
import org.graphstream.ui.fx_viewer.FxViewer;
import org.graphstream.ui.javafx.FxGraphRenderer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class HelloController {
    @FXML
    private Label welcomeText;
    @FXML
    private ChoiceBox seleccionLenguajes;
    @FXML
    private Button analizarCodigo;
    @FXML
    private TextArea insercionCodigo;

    @FXML
    private TextArea codigoInsertar;
    @FXML
    private Text lineasCodigo;
    @FXML
    private Text funcionesCodigo;
    @FXML
    private Text variablesGlobales;
    @FXML
    private Text numeroIfs;
    @FXML
    private Text numeroFors;
    @FXML
    private Text numeroWhiles;
    @FXML
    private Text promedioPrograma;
    @FXML
    private Text promedioVariables;
    @FXML
    private Text promedioFunciones;
    @FXML
    private Text volumen;
    @FXML
    private Text dificultad;
    @FXML
    private Text tiempo;
    @FXML
    private Button analizarFuncion;
    @FXML
    private ChoiceBox funciones;
    @FXML
    private Pane vista1;
    @FXML
    private Pane vista2;
    @FXML
    private Pane vista3;
    @FXML
    private ImageView primerAtras;
    @FXML
    private ImageView segundoAtras;
    @FXML
    private Button Atras5;
    @FXML
    private Button Atras6;
    @FXML
    private TextArea dependencias;
    @FXML
    private Text complejidadCiclo;
    @FXML
    private TextArea duplicidadInt;
    @FXML
    private TextArea duplicidadExt;
    @FXML
    private TextArea variablesLocal;
    @FXML
    private Text indiceEstabilidad;
    @FXML
    private Button generarDiagrama;
    @FXML
    private TextArea noUsed;
    @FXML
    private Button graficoHerencia;
    private Stats stats;
    private FunctionSats function;

    @FXML
    public void initialize() {

        analizarCodigo.setOnAction(this::analizarCodigo);
        analizarFuncion.setOnAction(this::analizarFuncion);
        seleccionLenguajes.getItems().addAll("Python", "Java");
        seleccionLenguajes.setValue("Seleccione el lenguaje");
        Atras5.setOnAction(this::primerAtras);
        Atras6.setOnAction(this::segundoAtras);
        generarDiagrama.setOnAction(this::generateGraph);
        graficoHerencia.setOnAction(this::generateClassGraph);
    }
    @FXML
    private void analizarCodigo(ActionEvent event) {
        Stats x = new Stats(insercionCodigo.getText(), ((String) seleccionLenguajes.getValue()));
        stats = x;
        boolean correct = x.getStats();
        if(correct){
            codigoInsertar.setText(insercionCodigo.getText());
            vista1.setVisible(false);
            vista2.setVisible(true);
            vista3.setVisible(false);
            lineasCodigo.setText(String.valueOf(x.getTotalLines()));
            StringBuffer func = new StringBuffer();
            //for(FunctionSats fun: x.getFunctions()){
            //    func.append(fun.getName()+", ");
            //}
            //if(!x.getFunctions().isEmpty()){
            //    func.delete(func.length()-2, func.length());
            //}
            //func.delete(func.length()-2, func.length());
            funcionesCodigo.setText(String.valueOf(stats.getFunctions().size()));
            StringBuffer globals = new StringBuffer();
            for(String glob : x.getGlobalVariables()){
                globals.append(glob+", ");
            }
            if(!x.getGlobalVariables().isEmpty()){
                globals.delete(globals.length()-2, globals.length());
            }
            variablesGlobales.setText(globals.toString());
            numeroIfs.setText(String.valueOf(x.getTotalIfStatements()));
            numeroFors.setText(String.valueOf(x.getTotalForStatements()));
            numeroWhiles.setText(String.valueOf(x.getTotalWhileStatements()));
            promedioPrograma.setText(String.valueOf(x.getLength()));
            promedioVariables.setText(String.valueOf(x.getTotalVariableNameLength()));
            promedioFunciones.setText(String.valueOf(x.getTotalFunctionNameLength()));
            volumen.setText(String.valueOf(x.getVolumen()));
            dificultad.setText(String.valueOf(x.getDifficulty()));
            tiempo.setText(String.valueOf(x.getTime()));
            funciones.getItems().clear();
            funciones.getItems().addAll(x.getFunctions().stream().map(e -> e.getName()).toArray());
            noUsed.setText(stats.getUnusedVar());
        }else{
            insercionCodigo.setText("Intenta de nuevo, el analisis falló");
        }
    }
    @FXML
    private void analizarFuncion(ActionEvent event) {
        String currentFunction = ((String) funciones.getValue());
        for(FunctionSats func :stats.getFunctions()){
            if(func.getName().equals(currentFunction)){
                function=func;
            }
        }
        if(function!=null){
            StringBuffer dependencies = new StringBuffer();
            for(FunctionSats dep :function.getDependencies()){
                dependencies.append(dep.getName()+"\n");
            }
            dependencias.setText(dependencies.toString());
            complejidadCiclo.setText(String.valueOf(function.getCiclomatic()));
            StringBuffer localVar = new StringBuffer();
            for(String var : function.getLocalVar().keySet()){
                localVar.append(var+"->"+function.getLocalVar().get(var)+" \n");
            }
            variablesLocal.setText(localVar.toString());
            StringBuffer duplicity = new StringBuffer();
            for(FunctionSats func : function.getDuplicityOtherFunc().keySet()){
                duplicity.append(func.getName()+"->"+String.valueOf(function.getDuplicityOtherFunc().get(func))+" \n");
            }
            duplicidadExt.setText(duplicity.toString());
            indiceEstabilidad.setText(String.valueOf(0));
            vista1.setVisible(false);
            vista2.setVisible(false);
            vista3.setVisible(true);
        }
    }
    @FXML
    private void primerAtras(ActionEvent event) {
        vista1.setVisible(true);
        vista2.setVisible(false);
        vista3.setVisible(false);
    }
    @FXML
    private void segundoAtras(ActionEvent event) {
        vista1.setVisible(false);
        vista2.setVisible(true);
        vista3.setVisible(false);
    }

    @FXML
    private void generateGraph(ActionEvent event) {
        if(stats.getType().equals("Python")) {
            GraphListenerXOXO x = stats.getGraph();

            boolean flag = false;
            for (String fun : stats.getFunctionsGraph()) {
                if (fun.equals(function.getName())) {
                    flag = true;
                    break;
                }
            }
            if (flag) {
                Stage stage = new Stage();
                Graph g = new SingleGraph("Tutorial 1");
                Map<String, ArrayList<String>> gString = stats.getGraph().graphToString(function.getName());
                System.out.println(gString.toString());
                g.setAttribute("ui.stylesheet", "node{\n" +
                        "    size: 30px, 30px;\n" +
                        "    fill-color: #f7f7f0;\n" +
                        "    text-mode: normal; \n" +
                        "}");
                for (String key : gString.keySet()) {
                    g.addNode(key).setAttribute("ui.label", key);
                }
                for (String key : gString.keySet()) {
                    for (String son : gString.get(key)) {
                        g.addEdge(key + " " + son, key, son);
                    }
                }
                FxViewer v = new FxViewer(g, FxViewer.ThreadingModel.GRAPH_IN_GUI_THREAD);

                v.enableAutoLayout();
                FxViewPanel panel = (FxViewPanel) v.addDefaultView(false, new FxGraphRenderer());

                Scene scene = new Scene(panel, 800, 600);
                stage.setScene(scene);
                stage.show();
            }
        }
    }

    @FXML
    private void generateClassGraph(ActionEvent event) {
        if(stats.getType().equals("Python")) {
            GraphListenerXOXO x = stats.getGraph();

            boolean flag = false;
            for (String fun : stats.getFunctionsGraph()) {
                if (fun.equals(function.getName())) {
                    flag = true;
                    break;
                }
            }
            if (flag) {
                Stage stage = new Stage();
                Graph g = new SingleGraph("Tutorial 1");
                Map<String, ArrayList<String>> gString = stats.getGraph().graphToString(function.getName());
                System.out.println(gString.toString());
                g.setAttribute("ui.stylesheet", "node{\n" +
                        "    size: 30px, 30px;\n" +
                        "    fill-color: #f7f7f0;\n" +
                        "    text-mode: normal; \n" +
                        "}");
                for (String key : gString.keySet()) {
                    g.addNode(key).setAttribute("ui.label", key);
                }
                for (String key : gString.keySet()) {
                    for (String son : gString.get(key)) {
                        g.addEdge(key + " " + son, key, son);
                    }
                }
                FxViewer v = new FxViewer(g, FxViewer.ThreadingModel.GRAPH_IN_GUI_THREAD);

                v.enableAutoLayout();
                FxViewPanel panel = (FxViewPanel) v.addDefaultView(false, new FxGraphRenderer());

                Scene scene = new Scene(panel, 800, 600);
                stage.setScene(scene);
                stage.show();
            }
        }
    }
}