package genInfo;

import com.github.difflib.text.DiffRow;
import com.github.difflib.text.DiffRowGenerator;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;
import java.util.regex.Pattern;
import static java.util.Map.entry;
import java.util.stream.Collectors;

import java.util.*;

public class CodeStatisticsVisitor<T> extends PythonParserBaseVisitor<T> {


    String camelCaseRegex = "^[a-z][a-zA-Z0-9]*$";
    String snakeCaseRegex = "^[a-z][a-z0-9_]*$";
    String builtinFunction=  "^__[a-zA-Z_]\\w*__$";

    private Map<Integer, String> caseStyle = Map.ofEntries(
            entry(1,"Camel Case"),
            entry(2,"Snake Case"),
            entry(3,"Other Style")
    );
    private ArrayList<ArrayList<Integer>> varPattern = new ArrayList<>(4) {{
        add(new ArrayList<>());
        add(new ArrayList<>());
        add(new ArrayList<>());
        add(new ArrayList<>());
    }};
    private Map<String, List<Integer>> variableCounter = new HashMap<>();
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
    @Override
    public T visitSimple_stmt(PythonParser.Simple_stmtContext ctx) {
        // Increment the node count for each simple statement
        if(!scope.isEmpty() && localTable.get(scope.peek())!=null){
            for(FunctionSats func : functions){
                if(func.getName().equals(scope.peek())){
                    func.getNodes().add(ctx.getText());
                    //func.getEdges().add(String.valueOf(func.getWay()));
                    break;
                }
            }
        }
        return visitChildren(ctx);
    }
    @Override
    public T visitAssignment(PythonParser.AssignmentContext ctx) {
        if(ctx.NAME()!=null){
            if(scope.isEmpty() || simbolTable.get(scope.peek())!=null){
                simbolTable.put(ctx.NAME().getText(),"Simple variable");
            }else{
                localTable.get(scope.peek()).putIfAbsent(ctx.NAME().getText(), "Simple variable");
            }
            operants++;
        }
        return visitChildren(ctx);
    }
    @Override
    public T visitFile_input(PythonParser.File_inputContext ctx) {
        totalLines = ctx.getStop().getLine(); // Total number of lines in the file

        visitChildren(ctx);

        double averageVariableNameLength = calculateAverage(totalVariableNameLength, totalVariables);
        double averageFunctionNameLength = calculateAverage(totalFunctionNameLength, totalFunctionsVisited);
        totalVariableNameLength=averageVariableNameLength;
        totalFunctionNameLength=averageFunctionNameLength;
        System.out.println(averageFunctionNameLength);
        System.out.println(averageVariableNameLength);
        // Print the collected statistics
        System.out.println("Total Lines of Code: " + totalLines);
        System.out.println("Total Functions: " + totalFunctions);
        System.out.println("Total Global Variables: " + totalGlobalVariables);
        System.out.println("Global Variables: " + globalVariables);
        System.out.println("Total If Statements: " + totalIfStatements);
        System.out.println("Total For Statements: " + totalForStatements);
        System.out.println("Total While Statements: " + totalWhileStatements);
        length=operators.size()+operants;
        System.out.println("Program length: "+ length);
        volumen=((operators.size()+operants)) * (Math.log(operators.size()+operants) / Math.log(2));
        difficulty=((1.0/2.0)*((operators.size()/ocurOperators)+(operants/ocurOperants)));
        time=(((1.0/2.0)*((operators.size()/ocurOperators)+(operants/ocurOperants)))*(Math.log(operators.size()+operants) / Math.log(2)));
        System.out.println("Program volume: "+ volumen);
        System.out.println("Program difficult: "+ difficulty);
        System.out.println("Program effort time: "+ time);

        fixFunctions();

        getSimilar();

        //for(FunctionSats func : functions){
        //    System.out.println(func.getName());
        //    func.printGraph();
        //}

        return null;
    }

    @Override
    public T visitStar_atom(PythonParser.Star_atomContext ctx) {
        if(ctx.NAME()!=null){
            boolean exists = false;
            if(scope.isEmpty() || simbolTable.get(scope.peek())==null){
                if(simbolTable.get(ctx.NAME().getText()) == null){
                    //System.out.println("En symbol table");
                    simbolTable.put(ctx.NAME().getText(),"Simple variable");
                }else{
                    exists = true;
                }
            }else if(localTable.get(scope.peek())!=null){
                if(localTable.get(ctx.NAME().getText()) == null){
                    //System.out.println("En local table");
                    localTable.get(scope.peek()).putIfAbsent(ctx.NAME().getText(),"Simple variable");
                }else{
                    exists = true;
                }
            }
            if(!exists){
                List<Integer> aux = new ArrayList<>();
                aux.add(1);
                aux.add(ctx.getStop().getLine());
                variableCounter.put(ctx.NAME().getText(), aux);
                totalVariableNameLength += ctx.NAME().getText().length();
                totalVariables++;

                checkStyle(ctx.NAME().getText(), false, ctx.getStart().getLine());
            }else{
                List<Integer> modified = variableCounter.get(ctx.NAME().getText());
                modified.set(0,modified.get(0)+1);
                variableCounter.put(ctx.NAME().getText(), modified);
            }
        }
        operants++;
        return visitChildren(ctx);
    }
    @Override
    public T visitAtom(PythonParser.AtomContext ctx) {
        // Check if the atom is a function call
        if (ctx.NAME()!=null) {
            String functionName = ctx.NAME().getText();
            if (!scope.isEmpty() && localTable.get(scope.peek())!=null && simbolTable.get(functionName)!=null && simbolTable.get(functionName).equals("function") && !functionName.equals(scope.peek())) {
                // Add the called function to the dependencies
                functionDependencies.get(scope.peek()).add(functionName);
            }else if(variableCounter.containsKey(functionName)){
                // check if used as argument
                // helps in checking unused of variables
                List<Integer> modified = variableCounter.get(ctx.NAME().getText());
                modified.set(0,modified.get(0)+1);
                variableCounter.put(ctx.NAME().getText(), modified);
            }else if(!scope.isEmpty() && localTable.get(scope.peek())!=null){
                for(String dependency: externalDependencies){
                    String val = dependency.split("\\.")[dependency.split("\\.").length-1];
                    String val2 = dependency.split("=")[dependency.split("=").length-1];
                    if(val.equals(functionName) || val2.equals(functionName)){
                        functionDependencies.get(scope.peek()).add(dependency);
                    }
                }
            }else if(!scope.isEmpty() && scope.peek().split("-").length>1){
                String var = scope.peek().split("-")[1];
                for(ClassStats clas: classes){
                    if(var.equals(clas.getName())){
                        clas.getInheritance().add(ctx.NAME().getText());
                    }
                }
            }
        }
        ocurOperants++;
        return visitChildren(ctx);
    }

    @Override
    public T visitFunction_def(PythonParser.Function_defContext ctx) {
        totalFunctions++;
        totalFunctionsVisited++;

        // Calculate the length of the function name
        String currentFunction = ctx.function_def_raw().NAME().getText();
        totalFunctionNameLength += currentFunction.length();
        if(scope.isEmpty() || localTable.get(scope.peek())==null){
            simbolTable.put(currentFunction,"function");
            localTable.put(currentFunction,new HashMap<>());
        }else{
            boolean flag = true;
            for(ClassStats clas : classes){
                if(clas.getName().equals(scope.peek())){
                    simbolTable.put(currentFunction,"function");
                    clas.getMethods().add(currentFunction);
                    flag = false;
                    break;
                }
            }
            if(flag){
                localTable.get(scope.peek()).putIfAbsent(currentFunction,"function");
            }
        }
        FunctionSats newFunc = new FunctionSats(currentFunction);
        newFunc.setLength(ctx.getStart().getLine()-ctx.getStop().getLine()+1);
        newFunc.setCode(ctx.getText().replaceAll("<DEDENT>","").replaceAll("<INDENT>","").split("\n"));
        functions.add(newFunc);
        scope.push(currentFunction);
        functionDependencies.put(currentFunction, new HashSet<>());
        System.out.println("Visited Function Definition: " + currentFunction);
        checkStyle(currentFunction, true, ctx.function_def_raw().getStart().getLine());
        // You can add more logic here for function-specific statistics
        visitChildren(ctx);

        scope.pop();

        return null;
    }

    @Override
    public T visitGlobal_stmt(PythonParser.Global_stmtContext ctx) {
        totalGlobalVariables += ctx.NAME().size();
        globalVariables.addAll(getNames(ctx));

        //for (TerminalNode nameNode : ctx.NAME()) {
        //    totalVariableNameLength += nameNode.getText().length();
        //    totalVariables++;
        //}

        return null;
    }

    @Override
    public T visitDotted_as_name(PythonParser.Dotted_as_nameContext ctx) {
        String importName =((String) visitDotted_name(ctx.dotted_name()));
        if(ctx.NAME()!=null){
            importName+="="+ctx.NAME().getText();
        }
        externalDependencies.add(importName);
        return null;
    }

    @Override
    public T visitImport_from(PythonParser.Import_fromContext ctx){
        String importName ="";
        if(ctx.dotted_name()!=null){
            importName+=visitDotted_name(ctx.dotted_name())+".";
        }
        ArrayList<String> val = (ArrayList<String>) visitImport_from_targets(ctx.import_from_targets());
        for(String key : val) {
            externalDependencies.add(importName + key);
        }
        return null;
    }

    @Override
    public T visitDotted_name(PythonParser.Dotted_nameContext ctx){
        String val = "";
        if(ctx.dotted_name()!=null) {
            val+=visitDotted_name(ctx.dotted_name());
            val+="."+ctx.NAME().getText();
        }else{
            val+=ctx.NAME().getText();
        }
        return (T) val;
    }

    @Override
    public T visitImport_from_targets(PythonParser.Import_from_targetsContext ctx){
        return visitImport_from_as_names(ctx.import_from_as_names());
    }

    @Override
    public T visitImport_from_as_names(PythonParser.Import_from_as_namesContext ctx){
        ArrayList<String> val = new ArrayList<>();
        for(PythonParser.Import_from_as_nameContext var :ctx.import_from_as_name()){
            StringBuilder x = new StringBuilder(var.NAME(0).getText());
            try {
                if(var.NAME(1)!=null) {
                    x.append("=").append(var.NAME(1));
                }
            }catch (IndexOutOfBoundsException ignored){
            }
            val.add(x.toString());
        }
        return (T) val;
    }

    @Override
    public T visitIf_stmt(PythonParser.If_stmtContext ctx) {
        totalIfStatements++;
        int prev=0;
        FunctionSats x = new FunctionSats("");
        if(!scope.isEmpty() && localTable.get(scope.peek())!=null){
            for(FunctionSats func: functions){
                if(func.getName().equals(scope.peek())){
                    func.setEdges(func.getEdges()+1);
                    break;
                }
            }
        }
        visitChildren(ctx);
        // You can add more logic here for if statement-specific statistics

        return null;
    }

    @Override
    public T visitElif_stmt(PythonParser.Elif_stmtContext ctx) {
        int prev=0;
        FunctionSats x = new FunctionSats("");
        if(!scope.isEmpty() && localTable.get(scope.peek())!=null){
            for(FunctionSats func: functions){
                if(func.getName().equals(scope.peek())){
                    func.setEdges(func.getEdges()+1);
                    break;
                }
            }
        }
        visitChildren(ctx);
        // You can add more logic here for if statement-specific statistics

        return null;
    }
    @Override
    public T visitElse_block(PythonParser.Else_blockContext ctx) {
        int prev=0;
        FunctionSats x = new FunctionSats("");
        if(!scope.isEmpty() && localTable.get(scope.peek())!=null){
            for(FunctionSats func: functions){
                if(func.getName().equals(scope.peek())){
                    func.setEdges(func.getEdges()+1);
                    break;
                }
            }
        }
        visitChildren(ctx);
        // You can add more logic here for if statement-specific statistics

        return null;
    }

    @Override
    public T visitFor_stmt(PythonParser.For_stmtContext ctx) {
        totalForStatements++;
        String startIndex = "0";
        String finalIndex = "";
        String complexity ="";
        visitStar_targets(ctx.star_targets());
        if(ctx.star_expressions()!=null){
            String index = (String) visitStar_expressions(ctx.star_expressions());
            if(index.split(",").length>1){
                startIndex=index.split(",")[0];
                finalIndex=index.split(",")[1];
            }else{
                finalIndex=index+".length";
            }

        }
        if(!startIndex.isEmpty() && !finalIndex.isEmpty()){
            if(Integer.valueOf(startIndex)==0){
                complexity = finalIndex;
            }else{
                complexity = finalIndex+"-"+startIndex;
            }
        }
        System.out.println("For complecity: "+complexity);
        if(!scope.isEmpty()) {
            for (FunctionSats func : functions) {
                if(scope.peek().equals(func.getName())){
                    func.setTime(func.getTime()+"+"+complexity);
                }
            }
        }
        visitBlock(ctx.block());
        if(ctx.else_block()!=null){
            visitElse_block(ctx.else_block());
        }
        // You can add more logic here for statement-specific statistics

        return null;
    }

    @Override
    public T visitStar_expressions(PythonParser.Star_expressionsContext ctx) {
        return visitChildren(ctx);
    }

    @Override
    public T visitStar_expression(PythonParser.Star_expressionContext ctx) {
        if(ctx.expression()==null){
            return null; //* bitwise_or
        }
        return visitChildren(ctx);
    }

    @Override
    public T visitExpression(PythonParser.ExpressionContext ctx) {
        if(ctx.lambdef()!=null){
            return null; //* bitwise_or
        }
        return visitChildren(ctx);
    }

    @Override
    public T visitDisjunction(PythonParser.DisjunctionContext ctx) {
        if(ctx.conjunction().size()>1){
            return null; //* bitwise_or
        }
        return visitChildren(ctx);
    }

    @Override
    public T visitConjunction(PythonParser.ConjunctionContext ctx) {
        if(ctx.inversion().size()>1){
            return null; //* bitwise_or
        }
        return visitChildren(ctx);
    }

    @Override
    public T visitInversion(PythonParser.InversionContext ctx) {
        if(ctx.inversion()!=null){
            return null; //* bitwise_or
        }
        return visitChildren(ctx);
    }

    @Override
    public T visitComparison(PythonParser.ComparisonContext ctx) {
        if(!ctx.compare_op_bitwise_or_pair().isEmpty()){
            return null; //* bitwise_or
        }
        return visitChildren(ctx);
    }

    @Override
    public T visitWhile_stmt(PythonParser.While_stmtContext ctx) {
        totalWhileStatements++;
        visitChildren(ctx);

        // You can add more logic here for while statement-specific statistics

        return null;
    }

    @Override
    public T visitEq_bitwise_or(PythonParser.Eq_bitwise_orContext ctx) {operators.add("=="); ocurOperators++; return visitChildren(ctx); }
    @Override public T visitNoteq_bitwise_or(PythonParser.Noteq_bitwise_orContext ctx) { operators.add("!="); ocurOperators++; return visitChildren(ctx); }
    @Override public T visitLte_bitwise_or(PythonParser.Lte_bitwise_orContext ctx) { operators.add("<="); ocurOperators++; return visitChildren(ctx); }
    @Override public T visitLt_bitwise_or(PythonParser.Lt_bitwise_orContext ctx) { operators.add("<"); ocurOperators++; return visitChildren(ctx); }
    @Override public T visitGte_bitwise_or(PythonParser.Gte_bitwise_orContext ctx) { operators.add(">="); ocurOperators++; return visitChildren(ctx); }
    @Override public T visitGt_bitwise_or(PythonParser.Gt_bitwise_orContext ctx) { operators.add(">"); ocurOperators++; return visitChildren(ctx); }
    @Override public T visitNotin_bitwise_or(PythonParser.Notin_bitwise_orContext ctx) { operators.add("not in"); ocurOperators++; return visitChildren(ctx); }
    @Override public T visitIn_bitwise_or(PythonParser.In_bitwise_orContext ctx) { operators.add("in"); ocurOperators++; return visitChildren(ctx); }
    @Override public T visitIsnot_bitwise_or(PythonParser.Isnot_bitwise_orContext ctx) { operators.add("is not"); ocurOperators++; return visitChildren(ctx); }
    @Override public T visitIs_bitwise_or(PythonParser.Is_bitwise_orContext ctx) { operators.add("is"); ocurOperators++; return visitChildren(ctx); }
    @Override public T visitSum(PythonParser.SumContext ctx) {
        if(ctx.sum()!=null){
            if(ctx.getText().contains("+")){
                operators.add("+");
            }else{
                operators.add("-");
            }
            ocurOperators++;
        }
        return visitChildren(ctx);
    }
    @Override public T visitTerm(PythonParser.TermContext ctx) {
        if(ctx.term()!=null){
            if(ctx.getText().contains("*")){
                operators.add("*");
            }else if(ctx.getText().contains("/")){
                operators.add("/");
            }else if(ctx.getText().contains("//")){
                operators.add("//");
            }else if(ctx.getText().contains("%")){
                operators.add("%");
            }else{
                operators.add("@");
            }
            ocurOperators++;
        }
        return visitChildren(ctx);
    }
    @Override public T visitFactor(PythonParser.FactorContext ctx) {
        if(ctx.factor()!=null){
            if(ctx.getText().contains("+")){
                operators.add("+");
            }else if(ctx.getText().contains("-")){
                operators.add("-");
            }else{
                operators.add("~");
            }
            ocurOperators++;
        }
        return visitChildren(ctx);
    }
    @Override public T visitPower(PythonParser.PowerContext ctx) {
        if(ctx.factor()!=null){
            operators.add("**");
            ocurOperators++;
        }
        return visitChildren(ctx);
    }

    @Override public T visitPrimary(PythonParser.PrimaryContext ctx) {
        visitChildren(ctx);
        String val = ctx.getText().toLowerCase();
        if(val.contains("range") && !val.equals("range")){
            val = val.split("\\(")[1].replaceFirst(".$", "");
            if(!val.contains(",")){
                val = "0,"+val;
            }
        }
        return (T) val;
    }

    @Override
    public T visitClass_def_raw(PythonParser.Class_def_rawContext ctx) {
        ClassStats newClass = new ClassStats(ctx.NAME().getText());
        scope.push(ctx.NAME().getText());
        System.out.println("Class Definition: " + ctx.NAME().getText());
        visitChildren(ctx);

        scope.pop();
        return null;
    }

    @Override
    public T visitArguments(PythonParser.ArgumentsContext ctx) {
        boolean flag = false;
        if(!scope.isEmpty()){
            for(ClassStats clas: classes){
                if(clas.getName().equals(scope.peek())){
                    scope.push(scope.peek()+"-arguments");
                    flag = true;
                    break;
                }
            }
        }
        visitChildren(ctx);
        if(flag) {
            scope.pop();
        }
        return null;
    }

    public int getTotalLines() {
        return totalLines;
    }

    public int getTotalFunctions() {
        return totalFunctions;
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

    // Helper method to calculate the average
    private double calculateAverage(double totalLength, int totalCount) {
        return totalCount > 0 ? ((double) totalLength) / totalCount : 0;
    }

    // Helper method to get the names from a context
    private Set<String> getNames(ParseTree ctx) {
        Set<String> names = new HashSet<>();
        for (int i = 0; i < ctx.getChildCount(); i++) {
            ParseTree child = ctx.getChild(i);
            if (child instanceof TerminalNode) {
                TerminalNode terminalNode = (TerminalNode) child;
                if (terminalNode.getSymbol().getType() == PythonParser.NAME) {
                    names.add(terminalNode.getText());
                }
            } else {
                names.addAll(getNames(child));
            }
        }
        return names;
    }

    public String getFunctionsLocal() {
        System.out.println(functions.size());
        StringBuilder func= new StringBuilder("Variables locales\n");
        for(final String key : localTable.keySet()){
            func.append(key+"\n");
            for(final String key1: localTable.get(key).keySet()){
                func.append("\t"+key1+" -> "+localTable.get(key).get(key1)+"\n");
            }
        }
        return func.toString();
    }

    public String getFunctionsDependency() {
        StringBuilder func= new StringBuilder("Llamados externos\n");
        for(final String key : functionDependencies.keySet()){
            func.append(key+"\n");
            for(final String key1: functionDependencies.get(key)){
                func.append("\t"+key1+"\n");
            }
        }
        return func.toString();
    }

    public String getExternalDependency() {
        StringBuilder func= new StringBuilder("Modulos externos\n");
        for(final String key : externalDependencies){
            func.append(key+"\n");
        }
        return func.toString();
    }

    public void fixFunctions(){
        for(String depend : functionDependencies.keySet()){
            ArrayList<FunctionSats> dependencies = new ArrayList<>();
            FunctionSats function = new FunctionSats("");
            for(String func: functionDependencies.get(depend)){
                boolean flag = true;
                for(FunctionSats funct: functions){
                    if(funct.getName().equals(func)){
                        dependencies.add(funct);
                        flag = false;
                        break;
                    }
                }
                if (flag && externalDependencies.contains(func)){
                    FunctionSats ext = new FunctionSats(func);
                    dependencies.add(ext);
                }
            }
            for(FunctionSats func : functions){
                if(func.getName().equals(depend)){
                    function = func;;
                    break;
                }
            }
            function.getDependencies().addAll(dependencies);
        }
        for(String depend : localTable.keySet()){
            FunctionSats function = new FunctionSats("");
            for(FunctionSats func : functions){
                if(func.getName().equals(depend)){
                    function = func;;
                    break;
                }
            }
            function.getLocalVar().putAll(localTable.get(depend));
        }
        for(FunctionSats func: functions) {
            System.out.println(func.getName());
            for (FunctionSats func1 : func.getDependencies()) {
                System.out.println("\t" + func1.getName());
            }
        }
    }

    public void getSimilar(){
        for(int i =0 ; i < functions.size();i++){
            for(int j = i+1; j < functions.size(); j++){
                if(!functions.get(i).equals(functions.get(j))) {
                    int maxLines =i;
                    int maxLines2 =j;
                    if(maxLines>functions.get(j).getCode().length){
                        maxLines=j;
                        maxLines2=i;
                    }
                    double media = 0;
                    for(int k = 0; k<functions.get(maxLines2).getCode().length;k++)
                    {
                        String aux[] = new String[functions.get(maxLines2).getCode().length];
                        for(int l=0;l<functions.get(maxLines2).getCode().length;l++){
                            aux[(l+k)%functions.get(maxLines2).getCode().length]=functions.get(maxLines2).getCode()[l];
                        }
                        DiffRowGenerator generator = DiffRowGenerator.create()
                                .showInlineDiffs(true)
                                .inlineDiffByWord(true)
                                .oldTag(f -> "~")
                                .newTag(f -> "**")
                                .build();
                        List<DiffRow> rows = generator.generateDiffRows(
                                Arrays.asList(functions.get(maxLines).getCode()),
                                Arrays.asList(aux));


                        //System.out.println("|original|new|");
                        //System.out.println("|--------|---|");
                        double counter = 0;
                        double similar = 0;
                        for (DiffRow row : rows) {
                            if (counter < functions.get(maxLines).getCode().length) {
                                if (row.getOldLine().contains("~") && row.getNewLine().contains("**")) {
                                    similar++;
                                }
                            }
                            counter++;
                        }
                        media += (double) (similar / counter);
                        //System.out.println("Porp similarity: " + (similar / counter));
                    }
                    System.out.println("PROP FINAL: "+(media/functions.get(maxLines2).getCode().length));
                }
            }
        }
    }

    public Map<String, Integer> getUnusedVariables(){
        /*
         * Function that builds an ordered map (by line number) with the variables that
         * where used only one time (when declared), so its value is never used after in the
         * program's logic.
         *
         * */
        Map<String, Integer> unused = new HashMap<>();

        //get map from variableCounter
        for(String key: variableCounter.keySet()){
            if(variableCounter.get(key).get(0) == 1){
                unused.put(key, variableCounter.get(key).get(1) );
            }
        }

        //order map
        Map<String, Integer> ordered = unused.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1, // Si hay duplicados, mantener el valor existente
                        LinkedHashMap::new // Mantener el orden de inserción
                ));


        if(!ordered.isEmpty()) printUnused(ordered);
        return ordered;
    }

    private void printUnused(Map<String, Integer> unused){
        System.out.println("The following variables are not being used after declaration:");
        for(String key: unused.keySet()){
            System.out.println("Variable "+key+" declared in line "+ unused.get(key));
        }
    }

    private void checkStyle(String s, boolean isFunc, int line){
        if(isFunc && s.matches(builtinFunction)){
            return;
        }
        if(s.matches(camelCaseRegex) && s.matches(snakeCaseRegex)){
            varPattern.get(0).add(line);
        }else if(s.matches(camelCaseRegex)){
            varPattern.get(1).add(line);
        }else if (s.matches(snakeCaseRegex)) {
            varPattern.get(2).add(line);
        }else {
            varPattern.get(3).add(line);
        }
    }

    public ArrayList<ArrayList<Integer>> styleStats(){
        int camel = varPattern.get(1).size();
        int snake = varPattern.get(2).size();
        int other = varPattern.get(3).size();

        boolean valid = false;

        if(camel > 0 && snake > 0) valid = true;
        if(camel > 0 && other > 0) valid = true;
        if(other > 0 && snake > 0) valid = true;

        if(valid){
            System.out.println("You are using different conventions for naming your variables," +
                    " try refactoring them.");
            for(int i = 1 ; i < varPattern.size() ; i++){
                if(varPattern.get(i).size() > 0 ){
                    System.out.println("Variables and functions named in " + caseStyle.get(i) +
                            ": "+ varPattern.get(i).size() + " "+varPattern.get(i).toString());
                }
            }
        }

        return varPattern;
    }

    public ArrayList<FunctionSats> getFunctions() {
        return functions;
    }
}