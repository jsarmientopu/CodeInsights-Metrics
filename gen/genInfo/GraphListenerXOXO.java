import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.util.*;

public class GraphListenerXOXO extends PythonParserBaseListener {

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

    private Map<String,Node> myFunctions = new HashMap<>();
    //Grafo de funciones, key = NombreFuncion , value = grafo de la función

    private Stack<Node> currNode = new Stack<>();
    //Nodo sobre el que se está trabajando.

    private Map<String,ArrayList<String>> functionGraph = new HashMap<>();

    private Stack<ArrayList<Node>> sons = new Stack<>();
    //Hijos que van a retornar al flujo principal


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
    public void enterFunction_def_raw (PythonParser.Function_def_rawContext ctx) {
        depth ++;
        Node nNode = new Node(ctx.NAME().getText() + "(" + ctx.params().getText() + ")");
        myFunctions.put(ctx.NAME().getText(),nNode);
        currNode.push(myFunctions.get(ctx.NAME().getText()));
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
    public void exitFunction_def_raw (PythonParser.Function_def_rawContext ctx) {
        depth --;
        Node nNode = new Node("End");
        currNode.pop();
        ConnectSons(nNode);
    }


    @Override
    public void enterSimple_stmt (PythonParser.Simple_stmtContext ctx) {
        if (depth == 0) { }
        else if (ctx.assignment() != null) {
            Node nNode = new Node(ctx.assignment().getText());
            currNode.peek().getNext().add(nNode);
            sons.peek().set(sons.peek().size() - 1,nNode);
            if (currNode.peek().getContent().charAt(currNode.peek().getContent().length() - 1) != '?'){
                currNode.pop();
            }
            currNode.push(nNode);
        }
        else if (ctx.return_stmt() != null) {
            Node nNode = new Node("return " + ctx.return_stmt().getText().substring(6));
            currNode.peek().getNext().add(nNode);
            sons.peek().set(sons.peek().size() - 1,nNode);
            if (currNode.peek().getContent().charAt(currNode.peek().getContent().length() - 1) != '?'){
                currNode.pop();
            }
            currNode.push(nNode);
        }
        else if (ctx.raise_stmt() != null) {
            Node nNode = new Node("raise " + ctx.raise_stmt().getText().substring(5));
            currNode.peek().getNext().add(nNode);
            sons.peek().set(sons.peek().size() - 1,nNode);
            if (currNode.peek().getContent().charAt(currNode.peek().getContent().length() - 1) != '?'){
                currNode.pop();
            }
            currNode.push(nNode);
        }
        else if (ctx.PASS() != null) {
            Node nNode = new Node(ctx.PASS().getText());
            currNode.peek().getNext().add(nNode);
            sons.peek().set(sons.peek().size() - 1,nNode);
            if (currNode.peek().getContent().charAt(currNode.peek().getContent().length() - 1) != '?'){
                currNode.pop();
            }
            currNode.push(nNode);
        }
        else if (ctx.BREAK() != null) {
            Node nNode = new Node(ctx.BREAK().getText());
            currNode.peek().getNext().add(nNode);
            sons.peek().set(sons.peek().size() - 1,nNode);
            if (currNode.peek().getContent().charAt(currNode.peek().getContent().length() - 1) != '?'){
                currNode.pop();
            }
            currNode.push(nNode);
        }
        else if (ctx.CONTINUE() != null) {
            Node nNode = new Node(ctx.CONTINUE().getText());
            currNode.peek().getNext().add(nNode);
            sons.peek().set(sons.peek().size() - 1,nNode);
            if (currNode.peek().getContent().charAt(currNode.peek().getContent().length() - 1) != '?'){
                currNode.pop();
            }
            currNode.push(nNode);

        }
        else if (ctx.del_stmt() != null) {
            Node nNode = new Node("del " + ctx.del_stmt().getText().substring(3));
            currNode.peek().getNext().add(nNode);
            sons.peek().set(sons.peek().size() - 1,nNode);
            if (currNode.peek().getContent().charAt(currNode.peek().getContent().length() - 1) != '?'){
                currNode.pop();
            }
            currNode.push(nNode);
        }
        else if (ctx.global_stmt() != null) {
            Node nNode = new Node("global " + ctx.global_stmt().getText().substring(6));
            currNode.peek().getNext().add(nNode);
            sons.peek().set(sons.peek().size() - 1,nNode);
            if (currNode.peek().getContent().charAt(currNode.peek().getContent().length() - 1) != '?'){
                currNode.pop();
            }
            currNode.push(nNode);
        }
        else if (ctx.nonlocal_stmt() != null) {
            Node nNode = new Node("nonlocal " + ctx.nonlocal_stmt().getText().substring(8));
            currNode.peek().getNext().add(nNode);
            sons.peek().set(sons.peek().size() - 1,nNode);
            if (currNode.peek().getContent().charAt(currNode.peek().getContent().length() - 1) != '?'){
                currNode.pop();
            }
            currNode.push(nNode);
        }
        else if (ctx.star_expressions() != null) {
            Node nNode = new Node(ctx.star_expressions().getText());
            currNode.peek().getNext().add(nNode);
            sons.peek().set(sons.peek().size() - 1,nNode);
            if (currNode.peek().getContent().charAt(currNode.peek().getContent().length() - 1) != '?'){
                currNode.pop();
            }
            currNode.push(nNode);
        }
    }

    @Override
    public void enterCompound_stmt(PythonParser.Compound_stmtContext ctx) {
        if (ctx.if_stmt() != null) {
            Node nNode = new Node(ctx.if_stmt().named_expression().getText() + " ?");
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
    public void enterElif_stmt(PythonParser.Elif_stmtContext ctx) {
        Node nNode = new Node(ctx.named_expression().getText() + " ?");
        currNode.pop();
        currNode.peek().getNext().add(nNode);
        sons.peek().add(nNode);
        currNode.push(nNode);
    }

    @Override
    public void enterElse_block (PythonParser.Else_blockContext ctx) {
        sons.peek().add(new Node("ELSE"));
        currNode.pop();
    }


    @Override
    public void exitIf_stmt (PythonParser.If_stmtContext ctx) {
        Node nNode = new Node("EndIf");
        currNode.pop();
        ConnectSons(nNode);
    }


    @Override
    public void exitElif_stmt (PythonParser.Elif_stmtContext ctx) {
        currNode.pop();
    }

    private void fixGraph(Node node) {
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
        functionGraph.clear();
        return ret;
    }

    public Set<String> functionList () {
        return myFunctions.keySet();
    }



    public static void main(String[] args) throws Exception {
        PythonLexer lexer;
        if (args.length > 0 ) {
            lexer = new PythonLexer(CharStreams.fromFileName(args[0]));
        }
        else {
            lexer = new PythonLexer(CharStreams.fromStream(System.in));
        }
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        PythonParser parser = new PythonParser(tokens);
        ParseTree tree = parser.file_input();
        ParseTreeWalker walker = new ParseTreeWalker();
        GraphListenerXOXO graphListener = new GraphListenerXOXO();
        walker.walk(graphListener, tree);
        for (String key : graphListener.myFunctions.keySet()) {
            graphListener.fixGraph(graphListener.myFunctions.get(key));
        }
        graphListener.myFunctionsPrint();
        System.out.println(graphListener.myFunctions.toString());
    }
}