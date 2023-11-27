package com.example.demo;

import genInfo.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
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
    private Text linesLabel;
    @FXML
    private Text globals;
    @FXML
    private Text countIf;
    @FXML
    private Text countFor;
    @FXML
    private Text countWhile;
    @FXML
    private Text lengthProgram;
    @FXML
    private Text volumenProgramm;
    @FXML
    private Text DifficultPorgram;
    @FXML
    private Text timeEffort;
    @FXML
    private Text averageVar;
    @FXML
    private Text averageFunc;
    @FXML
    private Text varNoUsed;
    @FXML
    private Text graphClass;
    @FXML
    private Text selectFunctions;
    @FXML
    private Text duplicityFunc;
    @FXML
    private Text dependencies;
    @FXML
    private Text duplicityInter;
    @FXML
    private Text localVar;
    @FXML
    private Text complexCiclco;
    @FXML
    private Text estabilityIndex;
    @FXML
    private Button pieGraph;

    @FXML
    public void initialize() {
        Tooltip t2 = new Tooltip("La cantidad de líneas de código no es un indicador " +
                "\ndirecto de la calidad o eficiencia del software, pero puede influir en" +
                "\n la complejidad y mantenibilidad del proyecto.");
        Tooltip.install(linesLabel, t2);
        Tooltip t3 = new Tooltip("La cantidad de funciones en el código impacta \n" +
                "en la modularidad y estructura del software, \n" +
                "afectando su claridad y mantenibilidad.");
        Tooltip.install(funcionesCodigo, t3);
        Tooltip t4 = new Tooltip("Exceso de variables globales: aumenta \n" +
                "complejidad y dificulta mantenimiento.");
        Tooltip.install(globals, t4);
        Tooltip t5 = new Tooltip("Exceso de 'if' puede implicar complejidad \n" +
                "y afectar la legibilidad y mantenibilidad \n" +
                "del código.");
        Tooltip.install(countIf, t5);
        Tooltip t6 = new Tooltip("Exceso de 'for' sugiere complejidad y \n" +
                "puede impactar negativamente en la estructura \n" +
                "y mantenimiento del código.");
        Tooltip.install(countFor, t6);
        Tooltip t7 = new Tooltip("Exceso de 'while' puede generar \n" +
                "complejidad en entendimiento y causar\n" +
                "posibles bucles infinitos");
        Tooltip.install(countWhile, t7);
        Tooltip t8 = new Tooltip("Longitud del programa (logaritmo \n" +
                "operadores y operandos): indica complejidad, \n" +
                "buscando equilibrio entre concisión y \n" +
                "claridad; rangos dependen del contexto \n" +
                "del proyecto.");
        Tooltip.install(lengthProgram, t8);
        Tooltip t9 = new Tooltip("Volumen del programa: refleja magnitud \n" +
                "del proyecto, su importancia radica en complejidad \n" +
                "y mantenimiento; rangos dependen del tipo \n" +
                "de aplicación.");
        Tooltip.install(volumenProgramm, t9);
        Tooltip t10 = new Tooltip(
                "Dificultad del programa (ecuación de Halstead): evalúa\n" +
                "simplicidad con operadores, operandos y sus\n" +
                "ocurrencias; valor más bajo indica mayor\n" +
                "simplicidad, pero la interpretación varía\n" +
                "según el contexto.");
        Tooltip.install(DifficultPorgram, t10);
        Tooltip t11 = new Tooltip(
                "Esfuerzo en tiempo del programa: calculado con\n" +
                "fórmula de Boehm usando dificultad y volumen;\n" +
                "valores más altos indican mayor esfuerzo,\n" +
                "pero la interpretación varía según la\n" +
                "escala del proyecto.");
        Tooltip.install(timeEffort, t11);
        Tooltip t12= new Tooltip(
                "Promedio longitud nombres variables:busca\n" +
                " equilibrio entre concisión y descriptividad;\n" +
                "impacta en legibilidad y mantenibilidad.\n" +
                "(Ni muy largas ni muy cortas");
        Tooltip.install(averageVar, t12);
        Tooltip t13 = new Tooltip(
                "Promedio longitud nombres funciones:\n" +
                "equilibrio entre brevedad y descriptividad\n" +
                "para optimizar legibilidad y mantenibilidad.");
        Tooltip.install(averageFunc, t13);
        Tooltip t14 = new Tooltip(
                "Variables no usadas: Indican posible\n" +
                "código redundante o errores, afectando\n" +
                "la eficiencia y claridad; deben ser\n" +
                "eliminadas para mantener un código limpio\n" +
                "y eficiente, procurando minimizar su presencia.");
        Tooltip.install(varNoUsed, t14);
        Tooltip t15 = new Tooltip(
                "Observar un grafo que ejemplifique\n" +
                "las relaciones de herencia entre\n" +
                "las clases implementadas en el códgigo");
        Tooltip.install(graphClass, t15);
        Tooltip t16 = new Tooltip(
                "Selecciona una de las funciones\n" +
                "implementadas para evr estadisticas\n" +
                "a detalle");
        Tooltip.install(selectFunctions, t16);
        Tooltip t17 = new Tooltip(
                "Mustra relacion de similitud\n" +
                "entre funciones. Un número mayor \n" +
                "al 0.85 sugiere que hay codigo duplicado\n" +
                "y deberian crearse nuevas funciones");
        Tooltip.install(duplicityFunc, t17);

        Tooltip t18 = new Tooltip(
                "Mustra relacion de similitud\n" +
                "entre componentes del interior\n" +
                "de las funciones(for-whiles-if)\n" +
                ". Un número mayor \n" +
                "al 0.85 sugiere que hay codigo duplicado\n" +
                "y deberian crearse nuevas funciones");
        Tooltip.install(duplicityInter, t18);

        Tooltip t19 = new Tooltip(
                "Muestra a detalle uso y llamado de\n" +
                "funciones o herramientas de \n" +
                "libreras externas o implementadas\n" +
                "fuera del alacance de esta \n" +
                "funcion");
        Tooltip.install(dependencies, t19);

        Tooltip t20 = new Tooltip(
                "Muestra a detalle las varibles\n" +
                "inicializadas en el scope de\n" +
                "la funcion. Se recomienda que\n" +
                "no sean muchas, aunque depende\n" +
                "del contexto.");
        Tooltip.install(localVar, t20);

        Tooltip t21 = new Tooltip(
                "La complejidad ciclomática mide la\n" +
                "cantidad de caminos linealmente\n" +
                "independientes a través del código,\n" +
                "proporcionando insights sobre la\n" +
                "complejidad y la testabilidad. Valores\n" +
                "más altos sugieren mayor complejidad\n" +
                "y posiblemente mayores desafíos en\n" +
                "el mantenimiento y la prueba\n" +
                "del código.");
        Tooltip.install(complexCiclco, t21);

        Tooltip t22 = new Tooltip(
                "Índice de estabilidad: mide\n" +
                "estabilidad de módulos, cerca\n" +
                "de 0 es estable, cerca de 1 es\n" +
                "inestable; equilibrio deseable\n" +
                "para mantenibilidad y flexibilidad.");
        Tooltip.install(estabilityIndex, t22);

        analizarCodigo.setOnAction(this::analizarCodigo);
        analizarFuncion.setOnAction(this::analizarFuncion);
        seleccionLenguajes.getItems().addAll("Python", "Java");
        seleccionLenguajes.setValue("Seleccione el lenguaje");
        Atras5.setOnAction(this::primerAtras);
        Atras6.setOnAction(this::segundoAtras);
        generarDiagrama.setOnAction(this::generateGraph);
        graficoHerencia.setOnAction(this::generateClassGraph);
        pieGraph.setOnAction(this::generatePieGraph);
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
            StringBuffer duplicityInt = new StringBuffer();
            for(String type : function.getDuplicity().keySet()){
                duplicityInt.append(type+": \n");
                for(Double var : function.getDuplicity().get(type)){
                    duplicityInt.append(String.valueOf(roundAvoid(var,4))+", ");
                }
                duplicityInt.append("\n");
            }
            duplicidadInt.setText(duplicityInt.toString());
            StringBuffer duplicity = new StringBuffer();
            for(FunctionSats func : function.getDuplicityOtherFunc().keySet()){
                duplicity.append(func.getName()+"->"+String.valueOf(roundAvoid(function.getDuplicityOtherFunc().get(func),4))+" \n");
            }
            duplicidadExt.setText(duplicity.toString());
            Double val = (double) (Double.valueOf(function.getDependencies().size())/Double.valueOf(stats.getFunctions().size()));
            indiceEstabilidad.setText(String.valueOf(roundAvoid(val,4)));
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
            if (flag && stats.getFunctionsGraph()!=null ) {
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
            InheritanceListenerXOXO x = stats.getClassGraph();
            Stage stage = new Stage();
            Graph g = new SingleGraph("Tutorial 1");
            Map<String, ArrayList<String>> gString = stats.getClassGraph().treeToString();
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

    @FXML
    private void generatePieGraph(ActionEvent event) {
        Stage stage = new Stage();
        Scene scene = new Scene(new Group());
        stage.setTitle("Imported Fruits");
        stage.setWidth(500);
        stage.setHeight(500);
        ObservableList<PieChart.Data> pieChartData =FXCollections.observableArrayList();
        double count = 0;
        for(FunctionSats x : stats.getFunctions()){
            Double value = ((double) (x.getLength()/stats.getLength()))*100;
            PieChart.Data newData = new PieChart.Data(x.getName(),value);
            count+=value;
            pieChartData.add(newData);
        }

        pieChartData.add(new PieChart.Data("Others",count-stats.getLength()));
        final PieChart chart = new PieChart(pieChartData);
        chart.setTitle("Usage of code");
        chart.setLabelLineLength(10);
        chart.setLegendSide(Side.LEFT);
        ((Group) scene.getRoot()).getChildren().add(chart);
        stage.setScene(scene);
        stage.show();

        applyCustomColorSequence(
                pieChartData,
                "aqua",
                "bisque",
                "chocolate",
                "coral",
                "crimson"
        );
    }

    public static double roundAvoid(double value, int places) {
        double scale = Math.pow(10, places);
        return Math.round(value * scale) / scale;
    }

    private void applyCustomColorSequence(ObservableList<PieChart.Data> pieChartData, String... pieColors) {
        int i = 0;
        for (PieChart.Data data : pieChartData) {
            data.getNode().setStyle("-fx-pie-color: " + pieColors[i % pieColors.length] + ";");
            i++;
        }
    }

}