package com.example.demo;

import genInfo.CodeStatisticsVisitor;
import genInfo.FunctionSats;
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
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.ui.fx_viewer.FxViewPanel;
import org.graphstream.ui.fx_viewer.FxViewer;
import org.graphstream.ui.javafx.FxGraphRenderer;

import java.io.IOException;


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
    private Text duplicidadInt;
    @FXML
    private Text duplicidadExt;
    @FXML
    private Text variablesLocal;
    @FXML
    private Text indiceEstabilidad;
    @FXML
    private Button generarDiagrama;
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
            for(FunctionSats fun: x.getFunctions()){
                func.append(fun.getName()+", ");
            }
            if(!x.getFunctions().isEmpty()){
                func.delete(func.length()-2, func.length());
            }
            func.delete(func.length()-2, func.length());
            funcionesCodigo.setText(func.toString());
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
                localVar.append(var+"->"+function.getLocalVar().get(var)+"  ");
            }
            variablesLocal.setText(localVar.toString());
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
        Stage stage = new Stage();
        MultiGraph g = new MultiGraph("mg");
        FxViewer v = new FxViewer(g, FxViewer.ThreadingModel.GRAPH_IN_GUI_THREAD);
        DorogovtsevMendesGenerator gen = new DorogovtsevMendesGenerator();

        g.setAttribute("ui.antialias");
        g.setAttribute("ui.quality");

        v.enableAutoLayout();
        FxViewPanel panel = (FxViewPanel)v.addDefaultView(false, new FxGraphRenderer());

        gen.addSink(g);
        gen.begin();
        for(int i = 0 ; i < 100 ; i++)
            gen.nextEvents();
        gen.end();
        gen.removeSink(g);

        Scene scene = new Scene(panel, 800, 600);
        stage.setScene(scene);
        stage.show();
    }
}