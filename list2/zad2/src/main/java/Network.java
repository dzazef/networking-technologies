import org.jgraph.graph.DefaultEdge;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultUndirectedWeightedGraph;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

class Network {
    private int vertexes;
    private DefaultUndirectedWeightedGraph<Integer, DefaultEdge> g = new DefaultUndirectedWeightedGraph<>(DefaultEdge.class);    //edge weight is capacity - C
    private int packageSize; //package size - M
    private Integer[][] N;//number of packages sent between users - N
    private Map<DefaultEdge, Integer> A = new HashMap<>(); // edge - actual flow
    private double T = 0;  //average delay - T

    Network(int size) {
        this.vertexes = size;
        for(int i=1; i<=vertexes; i++) {
            g.addVertex(i);
        }
        N = new Integer[vertexes][vertexes];
    }

    void addEdge(int v1, int v2) {
        g.addEdge(v1, v2);
        A.put(g.getEdge(v1, v2), 0);
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

    void setCapacity(DefaultEdge edge, double value) {
        g.setEdgeWeight(edge, value);
    }

    double getCapacity(int v1, int v2) {
        return g.getEdgeWeight(g.getEdge(v1, v2));
    }

    void setFlow(int v1, int v2, int f) {
        N[v1-1][v2-1] = f;
    }

    Integer getFlow(int v1, int v2) {
        return N[v1-1][v2-1];
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

    void countCompleteFlow() {
        DijkstraShortestPath<Integer, DefaultEdge> dijkstra = new DijkstraShortestPath<>(g);
        Integer i;
        for (int v1=1; v1<=vertexes; v1++) {
            for (int v2=1; v2<=vertexes; v2++) {
                i = N[v1-1][v2-1];
                if (i!=null) {
                    for (DefaultEdge edge : dijkstra.getPath(v1, v2).getEdgeList()) {
                        A.put(edge, A.get(edge) + i);
                    }
                }
            }
        }
    }

    void countAverageDelay() {
        double G = 0;
        for (int v1=0; v1<vertexes; v1++) {
            for (int v2=0; v2<vertexes; v2++) {
                if (N[v1][v2]!=null) G+= N[v1][v2];
            }
        }
        double sum = 0;
        double c, a, m;
        m = packageSize;
        for (DefaultEdge edge : g.edgeSet()) {
            c = g.getEdgeWeight(edge);
            a = A.get(edge);
            sum += a/(c/m-a);
        }
        sum = sum * (1/G);
        T = sum;
    }


    void printCompleteFlow() {
        System.out.println("----COMPLETE FLOW(PACKAGES)----");
        System.out.println("--------------------");
        for (Map.Entry<DefaultEdge, Integer> m : A.entrySet()) {
            System.out.println(m.getValue()+" packages on edge "+g.getEdgeSource(m.getKey())+" - "+g.getEdgeTarget(m.getKey()));
        }
    }

    void printCapacity() {
        System.out.println("----CAPACITY(BYTES)----");
        System.out.println("--------------------");
        for (DefaultEdge edge : g.edgeSet()) {
            System.out.println(g.getEdgeWeight(edge)+" capacity on edge "+g.getEdgeSource(edge)+" - "+g.getEdgeTarget(edge));
        }
    }

    void printFlow() {
        System.out.println("----FLOW(PACKAGES)----");
        System.out.println("--------------------");
        for (int v1=1; v1<=vertexes; v1++) {
            for (int v2=1; v2<=vertexes; v2++) {
                if (N[v1-1][v2-1] == null) {
                    System.out.print("X"+" ");
                } else {
                    System.out.print(N[v1-1][v2-1]+" ");
                }
            }
            System.out.println();
        }
    }

    void printAverageDelay() {
        System.out.println("----AVERAGE DELAY(SECONDS)----");
        System.out.println("--------------------");
        System.out.println(T);
    }
}
