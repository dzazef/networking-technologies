import org.jgrapht.Graph;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultUndirectedWeightedGraph;
import org.jgrapht.io.*;


import javax.swing.*;
import java.awt.*;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

class GraphPrinter {
    public static void print(Graph<Integer, DefaultEdge> g) {
        GraphExporter<Integer, DefaultEdge> exporter = new GmlExporter<>();
        Writer writer = new StringWriter();
        try {
            exporter.exportGraph(g, writer);
        } catch (ExportException e) {
            e.printStackTrace();
        }
        System.out.println(writer.toString());
    }
}

@SuppressWarnings("Duplicates")
class GraphTest {
    private boolean testGraph(DefaultUndirectedWeightedGraph<Integer, DefaultEdge> g) {
        ConnectivityInspector<Integer, DefaultEdge> c = new ConnectivityInspector<>(g);
        Random random = new Random();
        double t;
        Set<DefaultEdge> defaultEdgeSet = new HashSet<>();
        for (DefaultEdge defaultEdge : g.edgeSet()) {
            t = random.nextDouble();
            if (t > g.getEdgeWeight(defaultEdge)) {
                defaultEdgeSet.add(defaultEdge);
            }
        }
        g.removeAllEdges(defaultEdgeSet);
        return c.isConnected();
    }

    public int test1(int num) {
        int counter = 0;
        DefaultUndirectedWeightedGraph<Integer, DefaultEdge> g;
        for (int k=0; k<num; k++) {
            g = new DefaultUndirectedWeightedGraph<>(DefaultEdge.class);

            for (int i=1; i<=20; i++) {
                g.addVertex(i);
            }

            for (int i=1; i<20; i++) {
                g.addEdge(i, i+1);
                g.setEdgeWeight(i, i+1, 0.95);
            }
            if (testGraph(g)) counter++;
        }
        return counter;
    }

    public int test2(int num) {
        int counter = 0;
        DefaultUndirectedWeightedGraph<Integer, DefaultEdge> g;
        for (int k=0; k<num; k++) {
            g = new DefaultUndirectedWeightedGraph<>(DefaultEdge.class);

            for (int i=1; i<=20; i++) {
                g.addVertex(i);
            }

            g.addEdge(1, 20);
            g.setEdgeWeight(1, 20, 0.95);
            for (int i=1; i<20; i++) {
                g.addEdge(i, i+1);
                g.setEdgeWeight(i, i+1, 0.95);
            }
            if (testGraph(g)) counter++;
        }
        return counter;
    }

    public int test3(int num) {
        int counter = 0;
        DefaultUndirectedWeightedGraph<Integer, DefaultEdge> g;
        for (int k=0; k<num; k++) {
            g = new DefaultUndirectedWeightedGraph<>(DefaultEdge.class);

            for (int i=1; i<=20; i++) {
                g.addVertex(i);
            }

            g.addEdge(1, 20);
            g.setEdgeWeight(1, 20, 0.95);
            g.addEdge(1, 10);
            g.setEdgeWeight(1, 10, 0.8);
            g.addEdge(5, 15);
            g.setEdgeWeight(5, 15, 0.7);

            for (int i=1; i<20; i++) {
                g.addEdge(i, i+1);
                g.setEdgeWeight(i, i+1, 0.95);
            }
            if (testGraph(g)) counter++;
        }
        return counter;
    }

    public int test4(int num) {
        int counter = 0;
        DefaultUndirectedWeightedGraph<Integer, DefaultEdge> g;
        for (int k=0; k<num; k++) {
            g = new DefaultUndirectedWeightedGraph<>(DefaultEdge.class);

            for (int i=1; i<=20; i++) {
                g.addVertex(i);
            }

            g.addEdge(1, 20);
            g.setEdgeWeight(1, 20, 0.95);
            g.addEdge(1, 10);
            g.setEdgeWeight(1, 10, 0.8);
            g.addEdge(5, 15);
            g.setEdgeWeight(5, 15, 0.7);

            Random r = new Random();
            for (int i=0; i<4; i++) {
                int v1 = r.nextInt(20)+1;
                int v2 = r.nextInt(20)+1;
                g.addEdge(v1, v2);
                g.setEdgeWeight(v1, v2, 0.4);
            }

            for (int i=1; i<20; i++) {
                g.addEdge(i, i+1);
                g.setEdgeWeight(i, i+1, 0.95);
            }

            if (testGraph(g)) counter++;
        }
        return counter;
    }
}

public class Task1 {
    public static void main(String[] args) {
        System.out.println("--- Task1 ---");
        GraphTest graphTest = new GraphTest();
        final int all = 1000;
        //TEST1
        new Thread(() -> {
            int c = graphTest.test1(all);
            System.out.println("Test1 results:");
            System.out.println("all: "+all+", connected: "+c+", reliability: "+(double)c/all);
        }).start();
        //TEST2
        new Thread(() -> {
            int c = graphTest.test2(all);
            System.out.println("Test2 results:");
            System.out.println("all: "+all+", connected: "+c+", reliability: "+(double)c/all);
        }).start();
        //TEST3
        new Thread(() -> {
            int c = graphTest.test3(all);
            System.out.println("Test3 results:");
            System.out.println("all: "+all+", connected: "+c+", reliability: "+(double)c/all);
        }).start();
        //TEST4
        new Thread(() -> {
            int c = graphTest.test4(all);
            System.out.println("Test4 results:");
            System.out.println("all: "+all+", connected: "+c+", reliability: "+(double)c/all);
        }).start();
    }
}
