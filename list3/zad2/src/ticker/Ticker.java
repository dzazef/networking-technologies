package ticker;

public class Ticker extends Thread {
    private final long milliSeconds;
    private boolean running = true;

    public Ticker(long milliSeconds) {
        super();
        this.milliSeconds = milliSeconds;
    }

    @Override
    public void run() {
        while(running) {
            try {
                Thread.sleep(milliSeconds);
                synchronized (this) {
                    notifyAll();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void switchState() {
        this.running = !this.running;
    }
}
