import simulator.User2;
import exceptions.ChangedChainWhileRunningException;
import exceptions.IncorrectUserIndexException;
import parameters.Parameters;
import simulator.Ethernet;
import ticker.ThreadChain;
import ticker.Ticker;

public class Zad2 {
    public static void main(String[] args) throws ChangedChainWhileRunningException, IncorrectUserIndexException {
        ThreadChain ticker = new Ticker(null, Parameters.DELAY_MILLISECONDS);
        Ethernet ethernet = new Ethernet(ticker, Parameters.ETHERNET_SIZE);
        ThreadChain user = new User2(ethernet, "A", Parameters.ETHERNET_SIZE/4);
        ThreadChain user2 = new User2(ethernet, "B", 3*Parameters.ETHERNET_SIZE/4);
//        ThreadChain user3 = new User2(ethernet, "C", Parameters.ETHERNET_SIZE/2);
        ticker.start();
        ethernet.start();
        user.start();
        user2.start();
//        user3.start();
    }
}
