import simulator.User;
import exceptions.IncorrectUserIndexException;
import parameters.Parameters;
import simulator.Ethernet;
import ticker.ThreadChain;
import ticker.Ticker;

public class Zad2 {
    public static void main(String[] args) throws IncorrectUserIndexException {
        ThreadChain ticker = new Ticker(null, Parameters.DELAY_MILLISECONDS);
        Ethernet ethernet = new Ethernet(ticker, Parameters.ETHERNET_SIZE);
        ThreadChain user = new User(ethernet, "A", Parameters.ETHERNET_SIZE/5);
        ThreadChain user2 = new User(ethernet, "B", 2*Parameters.ETHERNET_SIZE/5);
        ThreadChain user3 = new User(ethernet, "C", 3*Parameters.ETHERNET_SIZE/5);
        ThreadChain user4 = new User(ethernet, "D", 4*Parameters.ETHERNET_SIZE/5);
        ticker.start();
        ethernet.start();
        user.start();
        user2.start();
        user3.start();
        user4.start();
    }
}
