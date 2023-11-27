package genInfo;

import com.github.difflib.text.DiffRow;
import com.github.difflib.text.DiffRowGenerator;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.*;

public class CodeSatisticsVisitorJava<T> extends Java8ParserBaseVisitor<T> {
    private int totalLines = 0;
    private int totalFunctions = 0;
    private int totalGlobalVariables = 0;
    private int totalIfStatements = 0;
    private int totalForStatements = 0;
    private int totalWhileStatements = 0;
    private int totalVariableNameLength = 0;
    private int totalFunctionNameLength = 0;
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
    private double length;
    private double volumen;
    private double difficulty;
    private double time;
    @Override
    public T visitStatementExpression(Java8Parser.StatementExpressionContext ctx) {
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
    public T visitUnannPrimitiveType(Java8Parser.UnannPrimitiveTypeContext ctx) {
        if(ctx.numericType()!=null){
            return (T) "boolean";
        }
        return visitChildren(ctx);
    }

    @Override
    public T visitNumericType(Java8Parser.NumericTypeContext ctx) {
        if(ctx.integralType()!=null){
            return (T) ctx.integralType().getText();
        }
        return (T) ctx.floatingPointType().getText();
    }

    @Override
    public T visitVariableDeclaratorList(Java8Parser.VariableDeclaratorListContext ctx) {
        ArrayList<String> var = new ArrayList<>();
        for(int i = 0; i< ctx.variableDeclarator().size(); i++) {
            var.add((String) visitVariableDeclarator(ctx.variableDeclarator(i)));
        }
        return (T) var;
    }

    @Override
    public T visitVariableDeclarator(Java8Parser.VariableDeclaratorContext ctx) {
        String varName = ctx.variableDeclaratorId().Identifier().getText();
        return (T) varName;
    }

    @Override
    public T visitLocalVariableDeclaration(Java8Parser.LocalVariableDeclarationContext ctx) {
        String type = (String) visitUnannType(ctx.unannType());
        if(scope.isEmpty() || simbolTable.get(scope.peek())!=null){
            for(String var : (ArrayList<String>) visitVariableDeclaratorList(ctx.variableDeclaratorList())){
                simbolTable.put(var,type);
            }
        }else{
            for(String var : (ArrayList<String>) visitVariableDeclaratorList(ctx.variableDeclaratorList())){
                localTable.get(scope.peek()).putIfAbsent(var,type);
            }
        }
        operants++;
        ocurOperants++;
        return null;
    }

    @Override
    public T visitCompilationUnit(Java8Parser.CompilationUnitContext ctx) {
        totalLines = ctx.getStop().getLine(); // Total number of lines in the file

        visitChildren(ctx);

        double averageVariableNameLength = calculateAverage(totalVariableNameLength, totalVariables);
        double averageFunctionNameLength = calculateAverage(totalFunctionNameLength, totalFunctionsVisited);
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

        return null;
    }

    @Override
    public T visitExpressionName(Java8Parser.ExpressionNameContext ctx) {
        String name = "";
        if(ctx.ambiguousName()==null){
            name = ctx.Identifier().getText();
        }else{
            name = visitAmbiguousName(ctx.ambiguousName())+"."+ctx.Identifier().getText();
        }
        totalVariableNameLength+=name.length();
        totalVariables++;
        ocurOperants++;
        return visitChildren(ctx);
    }
    @Override
    public T visitLiteral(Java8Parser.LiteralContext ctx) {
        // Check if the atom is a function call
        ocurOperants++;
        return visitChildren(ctx);
    }

    @Override
    public T visitMethodDeclaration (Java8Parser.MethodDeclarationContext ctx){
        String currentFunction = (String) visitMethodHeader(ctx.methodHeader());
        System.out.println("Visited Function Definition: " + currentFunction);
        FunctionSats newFunc = new FunctionSats(currentFunction);
        newFunc.setLength(ctx.getStart().getLine()-ctx.getStop().getLine()+1);
        //System.out.println(ctx.getText().replaceAll(";",";\n").replaceAll("\\{","{\n").replaceAll("}","}\n"));
        newFunc.setCode(ctx.getText().replaceAll(";",";\n").replaceAll("\\{","{\n").replaceAll("}","}\n").split("\n"));
        functions.add(newFunc);
        scope.push(currentFunction);
        functionDependencies.put(currentFunction, new HashSet<>());

        // You can add more logic here for function-specific statistics
        visitMethodBody(ctx.methodBody());

        scope.pop();
        return null;
    }

    @Override
    public T visitMethodHeader (Java8Parser.MethodHeaderContext ctx){
        String currentFunction = (String) visitMethodDeclarator(ctx.methodDeclarator());
        return (T) currentFunction;
    }

    @Override
    public T visitMethodDeclarator (Java8Parser.MethodDeclaratorContext ctx){
        String currentFunction = ctx.Identifier().getText();

        totalFunctions++;
        totalFunctionsVisited++;

        // Calculate the length of the function name
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
            localTable.put(currentFunction,new HashMap<>());
        }
        if(ctx.formalParameterList()!=null){
            HashMap<String,String> parameters = (HashMap<String, String>) visitFormalParameterList(ctx.formalParameterList());
            localTable.get(currentFunction).putAll(parameters);
        }

        return (T) currentFunction;
    }

    public T visitFormalParameterList (Java8Parser.FormalParameterListContext ctx){
        HashMap<String,String> parameters = new HashMap<>();
        if(ctx.formalParameters()!=null){
            parameters.putAll((HashMap<String,String>) visitFormalParameters(ctx.formalParameters()));
            parameters.putAll((HashMap<String,String>) visitLastFormalParameter(ctx.lastFormalParameter()));
        }else if(ctx.receiverParameter()!=null){
            parameters.putAll((HashMap<String,String>) visitReceiverParameter((ctx.receiverParameter())));
        }else{
            parameters.putAll((HashMap<String,String>) visitLastFormalParameter((ctx.lastFormalParameter())));
        }
        return (T) parameters;
    }

    public T visitReceiverParameter (Java8Parser.ReceiverParameterContext ctx){
        HashMap<String ,String> parameters = new HashMap<>();
        String type = (String) visitUnannType(ctx.unannType());
        parameters.put(ctx.Identifier().getText()+".this",type);
        totalVariableNameLength+=ctx.Identifier().getText().length();
        totalVariables++;
        return (T) parameters;
    }

    public T visitLastFormalParameter (Java8Parser.LastFormalParameterContext ctx){
        HashMap<String ,String> parameters = new HashMap<>();
        if(ctx.unannType()!=null){
            String type = (String) visitUnannType(ctx.unannType());
            parameters.put(ctx.variableDeclaratorId().Identifier().getText(),type);
            totalVariableNameLength+=ctx.variableDeclaratorId().Identifier().getText().length();
            totalVariables++;
            return (T) parameters;
        }else{
            return visitFormalParameter(ctx.formalParameter());
        }
    }

    @Override
    public T visitFormalParameters (Java8Parser.FormalParametersContext ctx){
        HashMap<String ,String> parameters = new HashMap<>();
        if(ctx.receiverParameter()!=null){
            parameters.putAll((HashMap<String,String>) visitReceiverParameter(ctx.receiverParameter()));
        }
        for(Java8Parser.FormalParameterContext x :ctx.formalParameter()){
            parameters.putAll((HashMap<String,String>) visitFormalParameter(x));
        }
        return (T) parameters;
    }

    @Override
    public T visitFormalParameter (Java8Parser.FormalParameterContext ctx){
        HashMap<String ,String> parameters = new HashMap<>();
        String type = (String) visitUnannType(ctx.unannType());
        parameters.put(ctx.variableDeclaratorId().Identifier().getText(),type);
        totalVariableNameLength+=ctx.variableDeclaratorId().Identifier().getText().length();
        totalVariables++;
        return (T) parameters;
    }

    @Override
    public T visitMethodInvocation(Java8Parser.MethodInvocationContext ctx){
        if(ctx.methodName()!=null){
            String functionName = ctx.methodName().getText();
            if (!scope.isEmpty() && localTable.get(scope.peek())!=null && simbolTable.get(functionName)!=null && simbolTable.get(functionName).equals("function") && !functionName.equals(scope.peek())) {
                // Add the called function to the dependencies
                functionDependencies.get(scope.peek()).add(functionName);
            }else if(!scope.isEmpty() && localTable.get(scope.peek())!=null){
                for(String dependency: externalDependencies){
                    String val = dependency.split("\\.")[dependency.split("\\.").length-1];
                    String val2 = dependency.split("=")[dependency.split("=").length-1];
                    if(val.equals(functionName) || val2.equals(functionName)){
                        functionDependencies.get(scope.peek()).add(dependency);
                    }
                }
            }
        }
        return visitChildren(ctx);
    }

    @Override
    public T visitImportDeclaration(Java8Parser.ImportDeclarationContext ctx){
        if(ctx.singleTypeImportDeclaration()!=null){
            externalDependencies.add((String) visitTypeName(ctx.singleTypeImportDeclaration().typeName()));
        }else if(ctx.typeImportOnDemandDeclaration()!=null){
            externalDependencies.add((String) visitPackageOrTypeName(ctx.typeImportOnDemandDeclaration().packageOrTypeName()));
        }else if(ctx.singleStaticImportDeclaration()!=null){
            String dep =((String) visitTypeName(ctx.singleStaticImportDeclaration().typeName()))+"."+ctx.singleStaticImportDeclaration().Identifier().getText();
            externalDependencies.add(dep);
        }else{
            externalDependencies.add((String) visitTypeName(ctx.singleStaticImportDeclaration().typeName()));
        }
        return null;
    }

    @Override
    public T visitTypeName(Java8Parser.TypeNameContext ctx){
        String importName ="";
        if(ctx.packageOrTypeName()==null){
            return (T) ctx.Identifier().getText();
        }
        importName+= visitPackageOrTypeName(ctx.packageOrTypeName())+"."+ctx.Identifier().getText();
        return (T) importName;
    }

    @Override
    public T visitPackageOrTypeName(Java8Parser.PackageOrTypeNameContext ctx) {
        String importName = "";
        if (ctx.packageOrTypeName() == null) {
            return (T) ctx.Identifier().getText();
        }
        importName += visitPackageOrTypeName(ctx.packageOrTypeName()) + "." + ctx.Identifier().getText();
        return (T) importName;
    }

    @Override
    public T visitIfThenStatement(Java8Parser.IfThenStatementContext ctx) {
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
    public T visitIfThenElseStatement(Java8Parser.IfThenElseStatementContext ctx) {
        totalIfStatements++;
        int prev=0;
        FunctionSats x = new FunctionSats("");
        if(!scope.isEmpty() && localTable.get(scope.peek())!=null){
            for(FunctionSats func: functions){
                if(func.getName().equals(scope.peek())){
                    func.setEdges(func.getEdges()+2);
                    break;
                }
            }
        }
        visitChildren(ctx);
        // You can add more logic here for if statement-specific statistics

        return null;
    }

    @Override
    public T visitForStatement(Java8Parser.ForStatementContext ctx) {
        totalForStatements++;
        visitChildren(ctx);

        // You can add more logic here for statement-specific statistics

        return null;
    }

    @Override
    public T visitWhileStatement(Java8Parser.WhileStatementContext ctx) {
        totalWhileStatements++;
        visitChildren(ctx);

        // You can add more logic here for while statement-specific statistics

        return null;
    }

    @Override
    public T visitConditionalExpression(Java8Parser.ConditionalExpressionContext ctx) {if(ctx.expression()!=null){operators.add("?"); ocurOperators++;} return visitChildren(ctx); }
    @Override public T visitConditionalOrExpression(Java8Parser.ConditionalOrExpressionContext ctx) { if(ctx.conditionalOrExpression()!=null){operators.add("||"); ocurOperators++;} return visitChildren(ctx); }
    @Override public T visitConditionalAndExpression(Java8Parser.ConditionalAndExpressionContext ctx) { if(ctx.conditionalAndExpression()!=null){operators.add("&&"); ocurOperators++;} return visitChildren(ctx); }
    @Override public T visitInclusiveOrExpression(Java8Parser.InclusiveOrExpressionContext ctx) { if(ctx.inclusiveOrExpression()!=null){operators.add("|"); ocurOperators++;} return visitChildren(ctx); }
    @Override public T visitExclusiveOrExpression(Java8Parser.ExclusiveOrExpressionContext ctx) { if(ctx.exclusiveOrExpression()!=null){operators.add("^"); ocurOperators++;} return visitChildren(ctx); }
    @Override public T visitAndExpression(Java8Parser.AndExpressionContext ctx) { if(ctx.andExpression()!=null){operators.add("||"); ocurOperators++;} return visitChildren(ctx); }
    @Override public T visitEqualityExpression(Java8Parser.EqualityExpressionContext ctx) {
        if(ctx.equalityExpression()!=null){
            if(ctx.getText().contains("==")){
                operators.add("==");
            }else{
                operators.add("!=");
            }
            ocurOperators++;
        }
        return visitChildren(ctx);
    }
    @Override public T visitRelationalExpression(Java8Parser.RelationalExpressionContext ctx) {
        if(ctx.relationalExpression()!=null){
            if(ctx.getText().contains("<")){
                operators.add("<");
                ocurOperators++;
            }else if(ctx.getText().contains(">")){
                operators.add(">");
                ocurOperators++;
            }else if(ctx.getText().contains("<=")){
                operators.add("<=");
                ocurOperators++;
            }else if(ctx.getText().contains(">=")){
                operators.add(">=");
                ocurOperators++;
            }
        }
        return visitChildren(ctx);
    }
    @Override public T visitShiftExpression(Java8Parser.ShiftExpressionContext ctx) {
        if(ctx.shiftExpression()!=null){
            if(ctx.getText().contains("<<")){
                operators.add("<<");
            }else if(ctx.getText().contains(">>")){
                operators.add(">>");
            }else{
                operators.add(">>>");
            }
            ocurOperators++;
        }
        return visitChildren(ctx);
    }
    @Override public T visitAdditiveExpression(Java8Parser.AdditiveExpressionContext ctx) {
        if(ctx.additiveExpression()!=null){
            if(ctx.getText().contains("+")){
                operators.add("+");
            }else{
                operators.add("-");
            }
            ocurOperators++;
        }
        return visitChildren(ctx);
    }
    @Override public T visitMultiplicativeExpression(Java8Parser.MultiplicativeExpressionContext ctx) {
        if(ctx.multiplicativeExpression()!=null){
            if(ctx.getText().contains("*")){
                operators.add("*");
            }else if(ctx.getText().contains("/")){
                operators.add("/");
            }else{
                operators.add("%");
            }
            ocurOperators++;
        }
        return visitChildren(ctx);
    }

    @Override public T visitUnaryExpression(Java8Parser.UnaryExpressionContext ctx) {
        if(ctx.unaryExpression()!=null){
            if(ctx.getText().contains("+")){
                operators.add("+");
            }else{
                operators.add("-");
            }
            ocurOperators++;
        }
        return visitChildren(ctx);
    }
    @Override public T visitPreIncrementExpression(Java8Parser.PreIncrementExpressionContext ctx) {
        operators.add("++");
        ocurOperators++;
        return visitChildren(ctx);
    }
    @Override public T visitPreDecrementExpression(Java8Parser.PreDecrementExpressionContext ctx) {
        operators.add("--");
        ocurOperators++;
        return visitChildren(ctx);
    }
    @Override public T visitUnaryExpressionNotPlusMinus(Java8Parser.UnaryExpressionNotPlusMinusContext ctx) {
        if(ctx.unaryExpression()!=null){
            if(ctx.getText().contains("~")){
                operators.add("~");
            }else{
                operators.add("!");
            }
            ocurOperators++;
        }
        return visitChildren(ctx);
    }
    @Override public T visitPostIncrementExpression(Java8Parser.PostIncrementExpressionContext ctx) {
        operators.add("++");
        ocurOperators++;
        return visitChildren(ctx);
    }
    @Override public T visitPostDecrementExpression(Java8Parser.PostDecrementExpressionContext ctx) {
        operators.add("--");
        ocurOperators++;
        return visitChildren(ctx);
    }
    @Override public T visitPostIncrementExpression_lf_postfixExpression(Java8Parser.PostIncrementExpression_lf_postfixExpressionContext ctx) {
        operators.add("++");
        ocurOperators++;
        return visitChildren(ctx);
    }
    @Override public T visitPostDecrementExpression_lf_postfixExpression(Java8Parser.PostDecrementExpression_lf_postfixExpressionContext ctx) {
        operators.add("--");
        ocurOperators++;
        return visitChildren(ctx);
    }
    @Override public T visitAssignment(Java8Parser.AssignmentContext ctx) {
        if(ctx.assignmentOperator()!=null){
            String val = ctx.assignmentOperator().getText();
            if(val.contains("*")){
                operators.add("*");
            }else if(val.contains("/")){
                operators.add("/");
            }else if(val.contains("%")){
                operators.add("%");
            }else if(val.contains("+")){
                operators.add("+");
            }else if(val.contains("-")){
                operators.add("-");
            }else if(val.contains("<<")){
                operators.add("<<");
            }else if(val.contains(">>")){
                operators.add(">>");
            }else if(val.contains(">>>")){
                operators.add(">>>");
            }else if(val.contains("&")){
                operators.add("&");
            }else if(val.contains("^")){
                operators.add("^");
            }else{
                operators.add("!");
            }
            ocurOperators++;
        }
        return visitChildren(ctx);
    }

    @Override
    public T visitClassDeclaration(Java8Parser.ClassDeclarationContext ctx){
        if(ctx.normalClassDeclaration()!=null){
            String className = ctx.normalClassDeclaration().Identifier().getText();
            ClassStats newClass = new ClassStats(className);
            scope.push(className);
            System.out.println("Class Definition: " + className);
            if(ctx.normalClassDeclaration().superclass()!=null){
                newClass.getInheritance().add(ctx.normalClassDeclaration().superclass().classType().Identifier().getText());
            }
            visitChildren(ctx);
            scope.pop();
        }else{
            String className = ctx.enumDeclaration().Identifier().getText();
            ClassStats newClass = new ClassStats(className);
            scope.push(className);
            System.out.println("Interface Definition: " + className);
            visitChildren(ctx);
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

    // Helper method to calculate the average
    private double calculateAverage(int totalLength, int totalCount) {
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
        for(FunctionSats func: functions){
            System.out.println(func.getName());
            for(FunctionSats func1 :func.getDependencies()){
                System.out.println("\t"+func1.getName());
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

    public ArrayList<FunctionSats> getFunctions() {
        return functions;
    }
}
