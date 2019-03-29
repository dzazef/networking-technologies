import org.jgraph.graph.DefaultEdge;
import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultDirectedWeightedGraph;
import org.jgrapht.graph.DefaultUndirectedWeightedGraph;
import org.jgrapht.io.DOTExporter;
import org.jgrapht.io.GmlExporter;
import org.jgrapht.io.GraphExporter;

import java.io.*;
import java.rmi.server.ExportException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Set;

class GraphPrinter {
    public static void print(Graph<Integer, DefaultEdge> g) {
        GraphExporter<Integer, DefaultEdge> exporter = new DOTExporter<>();
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter("graph.dot"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            exporter.exportGraph(g, writer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class NetworkManager {
    private DefaultUndirectedWeightedGraph<Integer, DefaultEdge> g = new DefaultUndirectedWeightedGraph<>(DefaultEdge.class);
    private int packageSize;
    Integer[][] flow = new Integer[10][10];
    private Integer[][] completeFlow = new Integer[10][10];

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
                    System.out.print(g.getEdgeWeight(defaultEdge)+" ");
                }
            }
            System.out.println();
        }
    }

    void countCompleteFlow() {
        DijkstraShortestPath<Integer, DefaultEdge> dijkstra = new DijkstraShortestPath<>(g);
        for (int v1=1; v1<=10; v1++) {
            for (int v2=1; v2<=10; v2++) {

            }
        }
    }
}

class NetworkBuilder {

    private NetworkManager n;
    private Scanner s;
    private int edges;

    NetworkBuilder() {
        this.n = new NetworkManager();
        this.s = new Scanner(System.in);
    }

    private void setNumberOfEdges() {
        System.out.print("How many edges you want to set (<20): ");
        this.edges = s.nextInt();
        if (edges>=20) {
            edges=19;
            System.out.println("Number of edges set to 19.");
        }
    }

    private void buildEdges() {
        //Setting edges
        System.out.println("Set edges: ");
        int v1, v2;
        for (int i=0; i<edges; i++) {
            System.out.println("Edge no "+(i+1));
            do {
                System.out.print("Vertex 1: ");
                v1 = s.nextInt();
                if (v1>10 || v1<=0) System.out.println("Incorrect value. Try again.");
            } while(v1>10 || v1<=0);
            do {
                System.out.print("Vertex 2: ");
                v2 = s.nextInt();
                if (v2>10 || v2<=0 || v1==v2) System.out.println("Incorrect value. Try again.");
            } while(v2>10 || v2<=0 || v1==v2);
            n.addEdge(v1, v2);
        }

    }

    private void setPackageSize() {
        int packageSize;
        System.out.print("Set package size(Bytes): ");
        do {
            packageSize = s.nextInt();
            if (packageSize<0) System.out.println("Incorrect value. Try again.");
        } while(packageSize<0);
        n.setPackageSize(packageSize);
    }

    private void setFlow() {
        int v1, v2, packages;
        System.out.println("Setting numbers of packages sent between users: ");
        for (DefaultEdge d : n.getEdgeSet()) {
            v1 = n.getGraph().getEdgeSource(d);
            v2 = n.getGraph().getEdgeTarget(d);
            System.out.print("From client "+v1+" to client "+v2+": ");
            do {
                packages = s.nextInt();
                if (packages<0) System.out.println("Incorrect value. Try again.");
            } while (packages<0);
            n.setFlow(v1, v2, packages);

            v1 = n.getGraph().getEdgeTarget(d);
            v2 = n.getGraph().getEdgeSource(d);
            System.out.print("From client "+v1+" to client "+v2+": ");
            do {
                packages = s.nextInt();
                if (packages<0) System.out.println("Incorrect value. Try again.");
            } while (packages<0);
            n.setFlow(v1, v2, packages);
        }
    }

    private void setCapacity() {
        int v1, v2, capacity;
        System.out.println("Setting max numbers of bytes sent between users: ");
        for (DefaultEdge d : n.getEdgeSet()) {
            v1 = n.getGraph().getEdgeSource(d);
            v2 = n.getGraph().getEdgeTarget(d);
            System.out.print("From client "+v1+" to client "+v2+": ");
            do {
                capacity = s.nextInt();
                if (capacity<0) System.out.println("Incorrect value. Try again.");
            } while (capacity<0);
            n.setCapacity(v1, v2, capacity);

            v1 = n.getGraph().getEdgeTarget(d);
            v2 = n.getGraph().getEdgeSource(d);
            System.out.print("From client "+v1+" to client "+v2+": ");
            do {
                capacity = s.nextInt();
                if (capacity<0) System.out.println("Incorrect value. Try again.");
            } while (capacity<0);
            n.setCapacity(v1, v2, capacity);
        }

    }

    NetworkManager buildNetwork() {
        setNumberOfEdges();
        boolean connected; String answer;
        while(true) {
            buildEdges();
            connected = n.checkIfConnected();
            if (!connected) {
                System.out.print("Your graph is not connected. Do you want to rearrange the edges?(yes/no): ");
                answer = s.next();
                if (answer.equals("no") || answer.equals("n"))
                    break;
            } else {
                break;
            }
            n.clearConnections();
        }
        setPackageSize();
        setFlow();
        setCapacity();
        return this.n;
    }

    NetworkManager buildDefaultNetwork() {
        NetworkManager nm = new NetworkManager();
        nm.addEdge(1, 10);
        nm.addEdge(5, 10);
        nm.addEdge(2, 7);
        for (int i=1; i<10; i++) {
            nm.addEdge(i, i+1);
            nm.setFlow(i, i+1, 10);
            nm.setCapacity(i, i+1, 10);
        }
        nm.setPackageSize(255);
        return nm;
    }
}

public class Zad2 {
    public static void main(String[] args) {
        NetworkBuilder nb = new NetworkBuilder();
        NetworkManager nm = nb.buildDefaultNetwork();
        nm.printCapacity();
        GraphPrinter.print(nm.getGraph());
        DijkstraShortestPath<Integer, DefaultEdge> dijkstra = new DijkstraShortestPath<>(nm.getGraph());
        for (Integer i : dijkstra.getPath(1, 7).getVertexList()) {
            System.out.println(i);
        }
        for (Integer i : dijkstra.getPath(4, 7).getVertexList()) {
            System.out.println(i);
        }
    }
}