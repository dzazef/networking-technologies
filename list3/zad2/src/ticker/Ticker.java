package ticker;

public class Ticker extends ThreadChain {
    private final long millis;

    public Ticker(Thread previous, long millis) {
        super(previous);
        this.millis = millis;
    }

    @Override
    protected void doStuff() {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
//    @Override
//    public void run() {
//        running = true;
//        while(running) {
//            System.out.println("Ticker1");
//            notifyNext();
//            System.out.println("Ticker2");
//            waitForPrevious();
//            System.out.println("Ticker3");
//            doStuff();
//        }
//    }
}
