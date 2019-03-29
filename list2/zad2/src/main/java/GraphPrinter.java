import org.jgraph.graph.DefaultEdge;
import org.jgrapht.Graph;
import org.jgrapht.io.DOTExporter;
import org.jgrapht.io.GraphExporter;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

class GraphPrinter {
    static void print(Graph<Integer, DefaultEdge> g) {
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
