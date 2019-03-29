import org.jgraph.graph.DefaultEdge;
import org.jgrapht.Graph;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultUndirectedWeightedGraph;
import org.jgrapht.io.DOTExporter;
import org.jgrapht.io.GraphExporter;

import java.io.*;
import java.util.Scanner;
import java.util.Set;

class NetworkManager {
    private DefaultUndirectedWeightedGraph<Integer, DefaultEdge> g = new DefaultUndirectedWeightedGraph<>(DefaultEdge.class);
    private int packageSize;
    Integer[][] flow = new Integer[10][10];
    private int[][] completeFlow = new int[10][10];

    NetworkManager() {
        for(int i=1; i<=10; i++) {
            g.addVertex(i);
        }
    }

    void addEdge(int v1, int v2) {
        g.addEdge(v1, v2);
    }

    void setPackageSize(int p) {
        this.packageSize = p;
    }

    public int getPackageSize() {
        return packageSize;
    }

    void setCapacity(int v1, int v2, double value) {
        g.setEdgeWeight(v1, v2, value);
    }

    double getCapacity(int v1, int v2) {
        return g.getEdgeWeight(g.getEdge(v1, v2));
    }

    void setFlow(int v1, int v2, int f) {
        flow[v1-1][v2-1] = f;
    }

    Integer getFlow(int v1, int v2) {
        return flow[v1-1][v2-1];
    }

    DefaultUndirectedWeightedGraph<Integer, DefaultEdge> getGraph() {
        return this.g;
    }

    Set<DefaultEdge> getEdgeSet() {
        return this.g.edgeSet();
    }

    boolean checkIfConnected() {
        ConnectivityInspector<Integer, DefaultEdge> c = new ConnectivityInspector<>(g);
        return c.isConnected();
    }

    void clearConnections() {
        g.removeAllEdges(g.edgeSet());
    }

    void printCapacity() {
        System.out.println("----CAPACITY----");
        System.out.println("--------------------");
        DefaultEdge defaultEdge;
        for (int v1=1; v1<=10; v1++) {
            for (int v2=1; v2<=10; v2++) {
                defaultEdge = g.getEdge(v1, v2);
                if (defaultEdge == null) {
                    System.out.print("X"+" ");
                } else {
                    System.out.print((int)g.getEdgeWeight(defaultEdge)+" ");
                }
            }
            System.out.println();
        }
    }

    void printFlow() {
        System.out.println("----FLOW----");
        System.out.println("--------------------");
        for (int v1=1; v1<=10; v1++) {
            for (int v2=1; v2<=10; v2++) {
                if (flow[v1-1][v2-1] == null) {
                    System.out.print("X"+" ");
                } else {
                    System.out.print(flow[v1-1][v2-1]+" ");
                }
            }
            System.out.println();
        }
    }

    void printCompleteFlow() {
        System.out.println("----COMPLETE FLOW----");
        System.out.println("--------------------");
        for (int v1=1; v1<=10; v1++) {
            for (int v2=1; v2<=10; v2++) {
                System.out.print(completeFlow[v1-1][v2-1]+" ");
            }
            System.out.println();
        }
    }

    void countCompleteFlow() {
        DijkstraShortestPath<Integer, DefaultEdge> dijkstra = new DijkstraShortestPath<>(g);
        Integer i;
        for (int v1=1; v1<=10; v1++) {
            for (int v2=1; v2<=10; v2++) {
                i = flow[v1-1][v2-1];
                if (i!=null) {
                    System.out.println("Found flow between "+v1+" and "+v2+" with value"+i);
                    for (DefaultEdge edge : dijkstra.getPath(v1, v2).getEdgeList()) {
                        completeFlow[g.getEdgeSource(edge)-1][g.getEdgeTarget(edge)-1] += i;
                    }
                }
            }
        }
    }
}

public class Zad2 {
    public static void main(String[] args) {
        NetworkBuilder nb = new NetworkBuilder();
        NetworkManager nm = nb.buildDefaultNetwork();
        nm.printCapacity();
        nm.printFlow();
        nm.countCompleteFlow();
        nm.printCompleteFlow();
        GraphPrinter.print(nm.getGraph());
//        DijkstraShortestPath<Integer, DefaultEdge> dijkstra = new DijkstraShortestPath<>(nm.getGraph());
//        for (Integer i : dijkstra.getPath(1, 7).getVertexList()) {
//            System.out.println(i);
//        }
//        for (Integer i : dijkstra.getPath(4, 7).getVertexList()) {
//            System.out.println(i);
//        }
    }
}