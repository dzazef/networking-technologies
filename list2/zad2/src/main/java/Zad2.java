public class Zad2 {
    public static void main(String[] args) {
        NetworkBuilder nb = new NetworkBuilder();
        Network nm = nb.buildNetwork();
        nm.sendPackages();
        nm.printCapacity();
        nm.printFlow();
        nm.printCompleteFlow();
        nm.printAverageDelay();
        GraphPrinter.print(nm.getGraph());
    }
}