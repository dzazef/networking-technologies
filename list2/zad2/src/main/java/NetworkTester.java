import org.jgraph.graph.DefaultEdge;

import java.util.*;

class Pair {
    private int arg2;
    private int arg1;

    int getArg1() {
        return arg1;
    }

    int getArg2() {
        return arg2;
    }

    Pair(int arg1, int arg2) {
        this.arg1 = arg1;
        this.arg2 = arg2;
    }
}

public class NetworkTester {
    private Network n;

    NetworkTester() {
        NetworkBuilder nb = new NetworkBuilder();
        this.n = nb.buildTestNetwork();
    }

    double testNetwork(int num) {
        int counter = 0;
        n.sendPackages();
        n.sendPackages();
        Map<Pair, Double> weightMap = new HashMap<>();
        Set<Pair> pairSet = new HashSet<>();
        Set<DefaultEdge> defaultEdgeSet = new HashSet<>();
        for (int i=0; i<num; i++) {
            Random random = new Random();
            for (DefaultEdge defaultEdge : n.getEdgeSet()) {
                double t = random.nextDouble();
                if (t > n.getP()) {
                    int arg1 = n.getGraph().getEdgeSource(defaultEdge);
                    int arg2 = n.getGraph().getEdgeTarget(defaultEdge);
                    pairSet.add(new Pair(arg1, arg2));
                    defaultEdgeSet.add(defaultEdge);
                    weightMap.put(new Pair(arg1, arg2), n.getGraph().getEdgeWeight(defaultEdge));
                }
            }
            n.getGraph().removeAllEdges(defaultEdgeSet);
            if (n.checkIfConnected()) {
                n.sendPackages();
//                System.out.println(n.getT());
//                System.out.println(n.getGraph());
//                System.out.println(n.A);
                if( n.getT() < n.getTmax() && n.getT()>0) {
                    counter++;
                }
            }
            //recover broken edges
            for (Pair p : pairSet) {
                n.addEdge(p.getArg1(), p.getArg2());
            }
            for (Map.Entry<Pair, Double> m : weightMap.entrySet()) {
                n.getGraph().setEdgeWeight(n.getGraph().getEdge(m.getKey().getArg1(), m.getKey().getArg2()), m.getValue());
            }
            weightMap.clear();
            defaultEdgeSet.clear();
            pairSet.clear();
        }
        return (double)counter/num;
    }

}
