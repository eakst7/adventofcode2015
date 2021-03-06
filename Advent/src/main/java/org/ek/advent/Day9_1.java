package org.ek.advent;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day9_1 {

    static class Node {
        public String name;

        public Node(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
        
        @Override
        public boolean equals(Object that) {
            boolean equals = ((that instanceof Node) && (((Node) that).name.equals(name)));
            //System.out.println(this.name + " == " + ((Node) that).name + " = " + equals);
            return equals;
        }
        
        @Override
        public int hashCode() {
            return name.hashCode();
        }
    }
    
    static class Edge {
        public Node n1;
        public Node n2;
        public int weight;
        
        public Edge(Node n1, Node n2, int weight) {
            this.n1 = n1;
            this.n2 = n2;
            this.weight = weight;
        }
    }

    public static <T> List<List<T>> insertAtAll(T node, List<T> list) {
        List<List<T>> lists = new ArrayList<List<T>>();

        for (int i = 0; i <= list.size(); i++) {
            List<T> newl = new ArrayList<T>(list);
            newl.add(i, node);
            lists.add(newl);
        }

        return lists;
    }

    public static List<List<Node>> generateOrderings(List<Node> nodes) {
        List<List<Node>> orderings = new ArrayList<List<Node>>();

        if (nodes.size() == 1) {
            orderings.add(nodes);
            return orderings;
        }

        Node n = nodes.remove(0);
        List<List<Node>> suborderings = generateOrderings(nodes);
        for (List<Node> ln: suborderings) {
            orderings.addAll(insertAtAll(n, ln));
        }
        
        return orderings;
    }

    public static List<Edge> edgeList = new ArrayList<Edge>();
    
    public static int edgeWeight(Node n1, Node n2) {
        for (Edge e : edgeList) {
            if (((e.n1.equals(n1)) && (e.n2.equals(n2))) ||
                    ((e.n1.equals(n2)) && (e.n2.equals(n1)))) {
                return e.weight;
            }
        }
        throw new RuntimeException();
    }
    
    public static int findShortest(List<List<Node>> nodeLists) {
        int bestdistance = Integer.MAX_VALUE;
        for (List<Node> nodes : nodeLists) {
            System.out.println(nodes);
            int distance = 0;
            for (int i = 0; i < nodes.size()-1; i++) {
                int edge = edgeWeight(nodes.get(i), nodes.get(i + 1));
                System.out.print(edge + " ");
                distance += edge;
            }
            System.out.println("");
            if (distance < bestdistance) {
                bestdistance = distance;
            }
        }
        
        return bestdistance;
    }
    
    public static void main(String[] args) throws Exception {
        Node n1 = new Node("n1");
        Node n2 = new Node("n2");
        Node n3 = new Node("n3");
        Node n4 = new Node("n4");

        Edge e1 = new Edge(n1, n2, 518);
        Edge e2 = new Edge(n1, n3, 464);
        Edge e3 = new Edge(n2, n3, 141);
        
        //System.out.println(insertAtAll(n1, Arrays.asList(n2, n3, n4)));
        List<Node> list = new ArrayList<Node>();
//        list.add(n1);
//        list.add(n2);
//        list.add(n3);
//        
        TextFile file = new TextFile("/tmp/eaking/day9.txt");

        for (String s : file) {
            Pattern p = Pattern.compile("(.*) to (.*) = (.*)");
            Matcher m = p.matcher(s);
            if (m.matches()) {
                Node node1 = new Node(m.group(1));
                if (!list.contains(node1)) {
                    list.add(node1);
                }
                Node node2 = new Node(m.group(2));
                if (!list.contains(node2)) {
                    list.add(node2);
                }
                Edge e = new Edge(node1, node2, Integer.parseInt(m.group(3)));
                edgeList.add(e);
            }
        }

        System.out.println(list);

        List<List<Node>> orderings = generateOrderings(list);
        for (List<Node> l : orderings) {
            System.out.println(l);
        }

        System.out.println(findShortest(orderings));
    }

}
