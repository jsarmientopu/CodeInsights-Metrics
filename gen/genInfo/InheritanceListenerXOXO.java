package genInfo;

import genInfo.PythonLexer;
import genInfo.PythonParser;
import genInfo.PythonParserBaseListener;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.util.*;

public class InheritanceListenerXOXO extends PythonParserBaseListener {
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

    private Node father = new Node("object");

    private Set<Node> clases = new HashSet<>();

    private Set<Node> visited = new HashSet<>();

    private Map<String,Node> dictionary = new HashMap<>();

    public InheritanceListenerXOXO() {
        dictionary.put("object",father);
    }

    @Override
    public void enterClass_def_raw (PythonParser.Class_def_rawContext ctx) {
        Node nNode = new Node(ctx.NAME().getText());
        clases.add(nNode);
        dictionary.put(ctx.NAME().getText(),nNode);
        if (ctx.arguments() == null || ctx.arguments().isEmpty()) {
            father.getNext().add(nNode);
        }
        else {
            for (int i = 0; i < ctx.arguments().args().expression().size(); i++) {
                if (dictionary.containsKey(ctx.arguments().args().expression(i).getText())) {
                    dictionary.get(ctx.arguments().args().expression(i).getText()).getNext().add(nNode);
                } else {
                    Node fNode = new Node(ctx.arguments().args().expression(i).getText());
                    dictionary.put(ctx.arguments().args().expression(i).getText(),fNode);
                    fNode.getNext().add(nNode);
                }
            }
        }
    }

    public void printTree () {
        for (String key : dictionary.keySet()) {
            System.out.println(key + " : " + dictionary.get(key).getNext());
        }
    }

    public Map<String,ArrayList<String>> treeToString() {
        Map<String,ArrayList<String>> tree = new HashMap<>();
        for (String key : dictionary.keySet()) {
            tree.put(key,new ArrayList<>());
            for (Node node : dictionary.get(key).getNext()) {
                tree.get(key).add(node.getContent());
            }
        }
        return tree;
    }
}
