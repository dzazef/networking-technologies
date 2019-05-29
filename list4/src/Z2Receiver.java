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
public class Z2Receiver {
    static final int datagramSize = 50;
    static final int millis = 5000;
    InetAddress localHost;
    int destinationPort;
    DatagramSocket socket;
    ReceiverThread receiver;
    SenderThread sender;
    List<Character> decodedPacketList;
    volatile int lastRead = -1;

    public Z2Receiver(int myPort, int destPort)
            throws Exception {
        localHost = InetAddress.getByName("127.0.0.1");
        destinationPort = destPort;
        decodedPacketList = new ArrayList<>();
        socket = new DatagramSocket(myPort);
        receiver = new ReceiverThread();
        sender = new SenderThread();
    }

    public static void main(String[] args) throws Exception {
        Z2Receiver receiver = new Z2Receiver(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        receiver.receiver.start();
        receiver.sender.start();
    }

    class SenderThread extends Thread {
        @Override
        public void run() {
            while (true) {
                try {
                    sleep(millis);
                    if (lastRead > 0) {
                        var z2p = new Z2Packet(4);
                        z2p.setIntAt(lastRead + 1, 0);
                        socket.send(new DatagramPacket(z2p.data, z2p.data.length, localHost, destinationPort));
                        System.out.print("RECEIVER: Sent " + z2p.getIntAt(0) + "\n");
                    }
                } catch (IOException | InterruptedException e) {
                    System.out.println("Z2Receiver.SenderThread.run: " + e);
                }
            }
        }
    }

    class ReceiverThread extends Thread {
        @Override
        public void run() {
            try {
                while (true) {
                    var data = new byte[datagramSize];
                    var packet = new DatagramPacket(data, datagramSize);
                    socket.receive(packet);
                    var z2p = new Z2Packet(packet.getData());
                    var seq = z2p.getIntAt(0);
                    var message = (char) z2p.data[4];

                    updateList(seq);
                    if (decodedPacketList.get(seq) == null) {
                        decodedPacketList.set(seq, message);
                    }
                    if (seq > lastRead && checkList(lastRead, seq)) {
                        for (int i = lastRead + 1; i <= seq; i++) {
                            System.out.print("RECEIVER: Received " + i + ": " + decodedPacketList.get(i) + "\n");
                        }
                        lastRead = seq;
                    }
                }
            } catch (IOException e) {
                System.out.println("Z2Receiver.ReceiverThread.run: " + e);
            }
        }
    }

    private void updateList(int idx) {
        while (decodedPacketList.size() < idx + 1) {
            decodedPacketList.add(null);
        }
    }

    private boolean checkList(int beginIdx, int endIdx) {
        for (int i = beginIdx + 1; i <= endIdx; i++) {
            if (decodedPacketList.get(i) == null)
                return false;
        }
        return true;
    }

//    class ReceiverThread extends Thread {
//
//        public void run() {
//            try {
//                while (true) {
//                    byte[] data = new byte[datagramSize];
//                    DatagramPacket packet =
//                            new DatagramPacket(data, datagramSize);
//                    socket.receive(packet);
//                    Z2Packet p = new Z2Packet(packet.getData());
//                    System.out.println("R:" + p.getIntAt(0)
//                            + ": " + (char) p.data[4]);
//                    // WYSLANIE POTWIERDZENIA
//                    packet.setPort(destinationPort);
//                    socket.send(packet);
//                }
//            } catch (Exception e) {
//                System.out.println("Z2Receiver.ReceiverThread.run: " + e);
//            }
//        }


}
