import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;


/**
 * Na podstawie: http://cs.pwr.edu.pl/krzywiecki/teaching.html
 */
@SuppressWarnings("WeakerAccess")
public class Z2Sender {
    static final int buffer = 10;
    static final int millis = 5000;
    static final int datagramSize = 50;
    static final int sleepTime = 500;
    static final int maxPacket = 50;
    InetAddress localHost;
    int destinationPort;
    DatagramSocket socket;
    SenderThread sender;
    ReceiverThread receiver;
    List<DatagramPacket> packetList;
    volatile int lastReceived = 0;  //Zmienna jest volatile, ponieważ jeden wątek ją zwieksza inny odczytuje

    public Z2Sender(int myPort, int destPort)
            throws Exception {
        localHost = InetAddress.getByName("127.0.0.1");
        destinationPort = destPort;
        socket = new DatagramSocket(myPort);
        sender = new SenderThread();
        receiver = new ReceiverThread();
        packetList = new ArrayList<>();
    }

    public static void main(String[] args) throws Exception {
        Z2Sender sender = new Z2Sender(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        sender.sender.start();
        sender.receiver.start();
    }

    /**
     * Zmiany
     * 1. SenderThread przed rozpoczęciem wysyłania wczytuje wszystkie pakiety ze standardowego wejścia i umieszcza je w
     *    liście packetList
     * 2. SenderThread wysyła 10 kolejnych pakietów, począwszy od ostatnio potwierdzonego pakietu
     * 3. SenderThread po wysłaniu 10 pakietów czeka określoną ilość czasu.
     */
    class SenderThread extends Thread {

        public void readAllPackages(List<DatagramPacket> indexPacketList) {
            int x;
            try {
                for (int i = 0; (x = System.in.read()) >= 0; i++) {
                    indexPacketList.add(createPacket(i, x));
                }
                System.out.print("SENDER: Read " + indexPacketList.size() + " packages." + "\n");
            } catch (Exception e) {
                System.out.println("Z2Sender.readAllPackages: " + e);
            }
        }

        public void run() {
            readAllPackages(packetList);
            try {
                while(true) {
                    int first;
                    first = lastReceived;
                    for (int i = 0; i < buffer && (first + i) < packetList.size(); i++) {
                        var p = packetList.get(first + i);
                        socket.send(p);
                        System.out.print("SENDER: Sent " + (first + i) + ": " + ((char) new Z2Packet(p.getData()).data[4]) + "\n");
                    }
                    sleep(millis);
                }
            } catch (InterruptedException | IOException e){
                System.out.println("Z2Sender.SenderThread.run: " + e);
            }
        }

    }

    /**
     * Zmiany
     * 1. ReceiverThread otrzymuje informację, od którego pakietu powienien zacząć wysyłanie(zamiast informacji o
     *    odebraniu każdego z pakietów)
     * 2. ReceiverThread przekazuje powyższą informację do ServerThread
     */
    class ReceiverThread extends Thread {

        public void run() {
            try {
                while (true) {
                    byte[] data = new byte[datagramSize];
                    DatagramPacket packet =
                            new DatagramPacket(data, datagramSize);
                    socket.receive(packet);
                    Z2Packet p = new Z2Packet(packet.getData());
                    var newLastReceived = p.getIntAt(0);
                    if (newLastReceived > lastReceived) {
                        lastReceived = newLastReceived;
                        System.out.print("SENDER: Received " + newLastReceived + "\n");
                    } else {
                        System.out.print("SENDER(IGNORED): Received " + newLastReceived + "\n");

                    }
                }
            } catch (IOException e) {
                System.out.println("Z2Sender.ReceiverThread.run: " + e);
            }
        }
    }

    public DatagramPacket createPacket(int idx, int x) {
        Z2Packet p = new Z2Packet(4 + 1);
        p.setIntAt(idx, 0);
        p.data[4] = (byte) x;
        return new DatagramPacket(p.data, p.data.length, localHost, destinationPort);
    }
}
