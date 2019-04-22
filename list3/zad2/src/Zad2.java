import data.User;
import data.UserFactory;
import exceptions.IncorrectUserIndexException;
import parameters.Parameters;
import simulator.EthernetSimulator;

public class Zad2 {
    public static void main(String[] args) {
        EthernetSimulator ethernetSimulator = new EthernetSimulator(Parameters.ETHERNET_SIZE);

        User marzena = UserFactory.getUser();
        User katarzyna = UserFactory.getUser();
        User grazyna = UserFactory.getUser();

        try {
            ethernetSimulator.addUser(10, marzena);
            ethernetSimulator.addUser(40, katarzyna);
            ethernetSimulator.addUser(25, grazyna);
        } catch (IncorrectUserIndexException e) {
            e.printStackTrace();
        }

        marzena.createPackage(2);
        katarzyna.createPackage(2);
        grazyna.createPackage(10);

        ethernetSimulator.createEthernet();
    }
}
