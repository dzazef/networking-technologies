package ticker;

public class Ticker extends ThreadChain {
    private final long millis;

    public Ticker(Thread previous, long millis) {
        super(previous);
        this.millis = millis;
    }

    @Override
    protected void task() {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
