package ticker;

import exceptions.ChangedChainWhileRunningException;
import parameters.Parameters;

public abstract class ThreadChain extends Thread {
    protected Thread previous;
    protected boolean running = false;

    public ThreadChain(Thread previous) {
        this.previous = previous;
    }

    protected void waitForPrevious() {
        if (previous != null) {
            try {
                //noinspection SynchronizeOnNonFinalField
                synchronized (previous) {
                    previous.wait();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    protected void notifyNext() {
        synchronized (this) {
            notifyAll();
        }
    }

    protected abstract void doStuff();

    @Override
    public void run() {
        try {
            Thread.sleep(Parameters.DELAY_MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        running = true;
        while(running) {
            notifyNext();
            waitForPrevious();
            doStuff();
        }
    }

    public void switchRunning() {
        this.running = !this.running;
    }

    public void setPrevious(Thread previous) throws ChangedChainWhileRunningException {
        if (running) throw new ChangedChainWhileRunningException();
        this.previous = previous;
    }
}
