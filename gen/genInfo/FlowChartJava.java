package genInfo;

import java.util.*;

public class FlowChartJava extends Java8ParserBaseListener {

    public class Node {
        private String content;

        private ArrayList<Node> next;

        public Node() {
            content = "";
            next = new ArrayList<>();
        }

        public Node(String content) {
            this.content = content;
            next = new ArrayList<>();
        }

        public ArrayList<Node> getNext() {
            return next;
        }

        public void setNext(ArrayList<Node> next) {
            this.next = next;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }


        @Override
        public String toString() {
            return this.content;
        }
    }

    private int depth = 0;
    private Set<Node> visited = new HashSet<>();
    //Conjunto para la implementacion del DFS

    public Map<String, Node> myFunctions = new HashMap<>();
    //Grafo de funciones, key = NombreFuncion , value = grafo de la función

    private Stack<Node> currNode = new Stack<>();
    //Nodo sobre el que se está trabajando.

    private Map<String,ArrayList<String>> functionGraph = new HashMap<>();

    private Stack<ArrayList<Node>> sons = new Stack<>();
    //Hijos que van a retornar al flujo principal

    private Stack<String> scope = new Stack<>();

    private void DFS (Node node, String indent) {
        if (visited.contains(node)) {
            return;
        }
        System.out.println(node.toString() + " : " + node.getNext().toString());
        visited.add(node);
        for (Node n : node.getNext()) {
            if (node.getNext().size() > 1) {
                DFS(n,indent + "\t");
            }
            else {
                DFS(n,indent);
            }
        }
    }

    private void DFSCreateGraph (Node node) {
        if (visited.contains(node)) {
            return;
        }
        functionGraph.put(node.getContent(),new ArrayList<>());
        visited.add(node);
        for (Node n : node.getNext()) {
            functionGraph.get(node.getContent()).add(n.getContent());
            DFSCreateGraph(n);
        }
    }

    public void myFunctionsPrint() {
        for (String key : myFunctions.keySet()) {
            DFS(myFunctions.get(key), "");
            visited.clear();
        }
    }

    public void myFunctionsPrint(String key) {
        DFS(myFunctions.get(key),"");
        visited.clear();
    }
    @Override
    public void enterMethodDeclaration (Java8Parser.MethodDeclarationContext ctx) {
        depth ++;
        Node nNode;
        if(ctx.methodHeader().methodDeclarator().formalParameterList()!=null){
            nNode = new Node(ctx.methodHeader().methodDeclarator().Identifier().getText() + "(" + ctx.methodHeader().methodDeclarator().formalParameterList().getText() + ")");
        }else{
            nNode = new Node(ctx.methodHeader().methodDeclarator().Identifier().getText() + "( )");
        }
        myFunctions.put(ctx.methodHeader().methodDeclarator().Identifier().getText(),nNode);
        currNode.push(myFunctions.get(ctx.methodHeader().methodDeclarator().Identifier().getText()));
        sons.push(new ArrayList<>());
        sons.peek().add(nNode);
    }

    public void ConnectSons(Node nNode) {
        for (int i = 0; i < sons.peek().size(); i++) {
            sons.peek().get(i).getNext().add(nNode);
        }
        System.out.println(sons.peek());
        sons.pop();
        if (!nNode.getContent().equals("End")) {
            currNode.pop();
            currNode.push(nNode);
            sons.peek().set(sons.peek().size() - 1,nNode);
        }
    }

    @Override
    public void exitMethodDeclaration (Java8Parser.MethodDeclarationContext ctx) {
        depth --;
        Node nNode = new Node("End");
        currNode.pop();
        ConnectSons(nNode);
    }


    @Override
    public void enterStatementWithoutTrailingSubstatement (Java8Parser.StatementWithoutTrailingSubstatementContext ctx) {
        if (depth == 0) { }
        else if (ctx.expressionStatement() != null) {
            Node nNode = new Node(ctx.expressionStatement().statementExpression().getText());
            currNode.peek().getNext().add(nNode);
            sons.peek().set(sons.peek().size() - 1,nNode);
            if (currNode.peek().getContent().charAt(currNode.peek().getContent().length() - 1) != '?'){
                currNode.pop();
            }
            currNode.push(nNode);
        }
        else if (ctx.returnStatement() != null) {
            System.out.println("LLEGUE RETURN");
            Node nNode;
            if(ctx.returnStatement().expression()!=null){
                nNode = new Node("return " + ctx.returnStatement().expression().getText());
            }else{
                nNode = new Node("return ");
            }
            currNode.peek().getNext().add(nNode);
            sons.peek().set(sons.peek().size() - 1,nNode);
            if (currNode.peek().getContent().charAt(currNode.peek().getContent().length() - 1) != '?'){
                currNode.pop();
            }
            currNode.push(nNode);
        }
        else if (ctx.breakStatement() != null) {
            Node nNode = new Node(ctx.breakStatement().getText());
            currNode.peek().getNext().add(nNode);
            sons.peek().set(sons.peek().size() - 1,nNode);
            if (currNode.peek().getContent().charAt(currNode.peek().getContent().length() - 1) != '?'){
                currNode.pop();
            }
            currNode.push(nNode);
        }
        else if (ctx.continueStatement() != null) {
            Node nNode = new Node(ctx.continueStatement().getText());
            currNode.peek().getNext().add(nNode);
            sons.peek().set(sons.peek().size() - 1,nNode);
            if (currNode.peek().getContent().charAt(currNode.peek().getContent().length() - 1) != '?'){
                currNode.pop();
            }
            currNode.push(nNode);

        }
        else if (ctx.throwStatement() != null) {
            Node nNode = new Node("throw " + ctx.throwStatement().expression().getText().substring(3));
            currNode.peek().getNext().add(nNode);
            sons.peek().set(sons.peek().size() - 1,nNode);
            if (currNode.peek().getContent().charAt(currNode.peek().getContent().length() - 1) != '?'){
                currNode.pop();
            }
            currNode.push(nNode);
        }
        else if (ctx.assertStatement() != null) {
            Node nNode;
            if(ctx.assertStatement().expression().size()>1){
                nNode = new Node("assert " + ctx.assertStatement().expression(0).getText());
            }else{
                nNode = new Node("assert " + ctx.assertStatement().expression(0).getText() + ":" +ctx.assertStatement().expression(0).getText());
            }
            currNode.peek().getNext().add(nNode);
            sons.peek().set(sons.peek().size() - 1,nNode);
            if (currNode.peek().getContent().charAt(currNode.peek().getContent().length() - 1) != '?'){
                currNode.pop();
            }
            currNode.push(nNode);
        }else if(ctx.block()!=null){
            scope.push("block");
        }
    }

    @Override
    public void exitStatementWithoutTrailingSubstatement (Java8Parser.StatementWithoutTrailingSubstatementContext ctx) {
        if(ctx.block()!=null){
            scope.pop();
        }
    }


        @Override
    public void enterLocalVariableDeclarationStatement(Java8Parser.LocalVariableDeclarationStatementContext ctx){
        if (depth == 0) { }
        else{
            Node nNode = new Node(ctx.localVariableDeclaration().getText());
            currNode.peek().getNext().add(nNode);
            sons.peek().set(sons.peek().size() - 1,nNode);
            if (currNode.peek().getContent().charAt(currNode.peek().getContent().length() - 1) != '?'){
                currNode.pop();
            }
            currNode.push(nNode);
        }
    }

    @Override
    public void enterStatement(Java8Parser.StatementContext ctx){
        if(!scope.isEmpty() && scope.peek().equals("else")){
            if(ctx.ifThenStatement()!=null){
                Node nNode = new Node(ctx.ifThenStatement().expression().getText() + " ?");
                currNode.pop();
                currNode.peek().getNext().add(nNode);
                sons.peek().add(nNode);
                currNode.push(nNode);
                scope.pop();
                scope.push("elif");
            }else if(ctx.ifThenElseStatement()!=null){
                Node nNode = new Node(ctx.ifThenElseStatement().expression().getText() + " ?");
                currNode.pop();
                currNode.peek().getNext().add(nNode);
                sons.peek().add(nNode);
                currNode.push(nNode);
                scope.pop();
                scope.push("elif");
            }else{
                sons.peek().add(new Node("ELSE"));
                currNode.pop();
            }
        }else if(!scope.isEmpty() &&( scope.peek().equals("if") ||  scope.peek().equals("elif"))){
            scope.pop();
            scope.push("else");
        }
    }

    @Override
    public void enterLabeledStatement(Java8Parser.LabeledStatementContext ctx){
        scope.push("labeled");
    }

    @Override
    public void exitLabeledStatement(Java8Parser.LabeledStatementContext ctx){
        scope.pop();
    }

    @Override
    public void enterWhileStatement(Java8Parser.WhileStatementContext ctx){
        scope.push("while");
    }

    @Override
    public void exitWhileStatement(Java8Parser.WhileStatementContext ctx){
        scope.pop();
    }
    @Override
    public void enterForStatement(Java8Parser.ForStatementContext ctx){
        scope.push("for");
        System.out.println("Entre for ");
    }

    @Override
    public void exitForStatement(Java8Parser.ForStatementContext ctx){
        System.out.println("Sali for");
        scope.pop();
    }

    @Override
    public void enterStatementNoShortIf(Java8Parser.StatementNoShortIfContext ctx){
        if(!scope.isEmpty() && scope.peek().equals("ifElse")){
            if(ctx.ifThenElseStatementNoShortIf()!=null){
                Node nNode = new Node(ctx.ifThenElseStatementNoShortIf().expression().getText() + " ?");
                currNode.pop();
                currNode.peek().getNext().add(nNode);
                sons.peek().add(nNode);
                currNode.push(nNode);
                scope.pop();
                scope.push("elif");
            }else{
                sons.peek().add(new Node("ELSE"));
                currNode.pop();
                scope.pop();
            }
        }else if(!scope.isEmpty() && (scope.peek().equals("if")||scope.peek().equals("elif"))){
            scope.pop();
            scope.push("else");
        }
    }

    @Override
    public void enterLabeledStatementNoShortIf(Java8Parser.LabeledStatementNoShortIfContext ctx){
        scope.push("labeled");
    }

    @Override
    public void exitLabeledStatementNoShortIf(Java8Parser.LabeledStatementNoShortIfContext ctx){
        scope.pop();
    }

    @Override
    public void enterDoStatement(Java8Parser.DoStatementContext ctx){
        scope.push("do-while");
    }

    @Override
    public void exitDoStatement(Java8Parser.DoStatementContext ctx){
        scope.pop();
    }

    @Override
    public void enterWhileStatementNoShortIf(Java8Parser.WhileStatementNoShortIfContext ctx){
        scope.push("while");
    }

    @Override
    public void exitWhileStatementNoShortIf(Java8Parser.WhileStatementNoShortIfContext ctx){
        scope.pop();
    }
    @Override
    public void enterForStatementNoShortIf(Java8Parser.ForStatementNoShortIfContext ctx){
        scope.push("for");
    }

    @Override
    public void exitForStatementNoShortIf(Java8Parser.ForStatementNoShortIfContext ctx){
        scope.pop();
    }

    @Override
    public void enterIfThenStatement(Java8Parser.IfThenStatementContext ctx) {
         if(scope.isEmpty()){
             Node nNode = new Node(ctx.expression().getText() + " ?");
             currNode.peek().getNext().add(nNode);
             if (currNode.peek().getContent().charAt(currNode.peek().getContent().length() - 1) != '?'){
                 currNode.pop();
             }
             sons.peek().set(sons.peek().size() - 1,nNode);
             sons.push(new ArrayList<>());
             sons.peek().add(nNode);
             currNode.push(nNode);
         }

    }

    @Override
    public void exitIfThenStatement(Java8Parser.IfThenStatementContext ctx) {
        if(scope.isEmpty()){
            Node nNode = new Node("EndIf");
            currNode.pop();
            ConnectSons(nNode);
        }else if(scope.peek().equals("elif")){
            currNode.pop();
        }else if(scope.peek().length()>=2 && scope.peek().equals("else")){
            scope.pop();
            if(scope.peek().equals("elif")){
                currNode.pop();
            }
        }
    }

    @Override
    public void enterIfThenElseStatement(Java8Parser.IfThenElseStatementContext ctx) {
        if(scope.isEmpty()){
            Node nNode = new Node(ctx.expression().getText() + " ?");
            currNode.peek().getNext().add(nNode);
            if (currNode.peek().getContent().charAt(currNode.peek().getContent().length() - 1) != '?'){
                currNode.pop();
            }
            sons.peek().set(sons.peek().size() - 1,nNode);
            sons.push(new ArrayList<>());
            sons.peek().add(nNode);
            currNode.push(nNode);
            scope.push("if");
        }
    }

    @Override
    public void exitIfThenElseStatement(Java8Parser.IfThenElseStatementContext ctx) {
        if(scope.isEmpty()){
            Node nNode = new Node("EndIf");
            currNode.pop();
            ConnectSons(nNode);
        }else if(scope.peek().equals("elif")){
            currNode.pop();
        }else if(scope.peek().length()>=2 && scope.peek().equals("else")){
            scope.pop();
            if(scope.peek().equals("elif")){
                currNode.pop();
            }
        }
    }

    @Override
    public void enterIfThenElseStatementNoShortIf(Java8Parser.IfThenElseStatementNoShortIfContext ctx) {
        if(scope.isEmpty()){
            Node nNode = new Node(ctx.expression().getText() + " ?");
            currNode.peek().getNext().add(nNode);
            if (currNode.peek().getContent().charAt(currNode.peek().getContent().length() - 1) != '?'){
                currNode.pop();
            }
            sons.peek().set(sons.peek().size() - 1,nNode);
            sons.push(new ArrayList<>());
            sons.peek().add(nNode);
            currNode.push(nNode);
            scope.push("if");
        }
    }
    @Override
    public void exitIfThenElseStatementNoShortIf(Java8Parser.IfThenElseStatementNoShortIfContext ctx) {
        if(scope.isEmpty()){
            Node nNode = new Node("EndIf");
            currNode.pop();
            ConnectSons(nNode);
        }else if(scope.peek().equals("elif")){
            currNode.pop();
        }else if(scope.peek().length()>=2 && scope.peek().equals("else")){
            scope.pop();
            if(scope.peek().equals("elif")){
                currNode.pop();
            }
        }
    }

    @Override
    public void enterSwitchStatement(Java8Parser.SwitchStatementContext ctx){
        Node nNode = new Node(ctx.expression().getText() + " ?");
        currNode.peek().getNext().add(nNode);
        if (currNode.peek().getContent().charAt(currNode.peek().getContent().length() - 1) != '?'){
            currNode.pop();
        }
        sons.peek().set(sons.peek().size() - 1,nNode);
        sons.push(new ArrayList<>());
        sons.peek().add(nNode);
        currNode.push(nNode);;
    }

    @Override
    public void exitSwitchStatement(Java8Parser.SwitchStatementContext ctx){
        Node nNode = new Node("EndIf");
        currNode.pop();
        ConnectSons(nNode);
    }

    public void enterSwitchLabel(Java8Parser.SwitchLabelContext ctx) {
        if(ctx.getText().contains("default")){
            sons.peek().add(new Node("ELSE"));
            currNode.pop();
        }else{
            Node nNode;
            if(ctx.constantExpression()!=null){
                nNode = new Node(ctx.constantExpression().getText() + " ?");
            }else{
                nNode = new Node(ctx.enumConstantName().getText() + " ?");
            }
            currNode.pop();
            currNode.peek().getNext().add(nNode);
            sons.peek().add(nNode);
            currNode.push(nNode);
        }
    }

    @Override
    public void exitSwitchLabel(Java8Parser.SwitchLabelContext ctx) {
        if(ctx.getText().contains("default")){}
        else{
            currNode.pop();
        }
    }


    public void fixGraph(Node node) {
        boolean ok;
        do {
            ok = false;
            for (int i = 0; i < node.getNext().size(); i++) {
                if (node.getNext().get(i).getContent().equals("EndIf")) {
                    ok = true;
                    System.out.println("I am here\n");
                    Node endif = node.getNext().get(i);
                    node.getNext().remove(i--);
                    for (int j = 0; j < endif.getNext().size(); j++) {
                        node.getNext().add(endif.getNext().get(j));
                    }
                }
            }
        } while (ok);
        for (int i = 0;i < node.getNext().size();i++) {
            fixGraph(node.getNext().get(i));
        }
    }

    public Map<String,ArrayList<String>> graphToString (String name) {
        DFSCreateGraph(myFunctions.get(name));
        Map<String,ArrayList<String>> ret = functionGraph;
        visited.clear();
        //functionGraph.clear();
        return ret;
    }

    public Set<String> functionList () {
        return myFunctions.keySet();
    }
}