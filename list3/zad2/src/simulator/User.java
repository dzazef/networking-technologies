package simulator;

import exceptions.IncorrectUserIndexException;
import parameters.Parameters;
import ticker.ThreadChain;

import java.util.Random;

import static parameters.Parameters.DELAY_MILLISECONDS;
import static parameters.Parameters.PACKAGE_SIZE;

public class User extends ThreadChain {
    private final String name;
    private final Ethernet ethernet;
    private final int ethernetIndex;
    private int frame;
    private boolean collision;
    private int multiplier = 1;
    private int fullMultiplier = 0;

    public User(Ethernet ethernet, String name, int ethernetIndex) throws IncorrectUserIndexException {
        super(ethernet);
        this.ethernet = ethernet;
        this.name = name;
        this.ethernetIndex = ethernetIndex;
        if (ethernetIndex < 0 || ethernetIndex >= ethernet.getSize()) throw new IncorrectUserIndexException();
    }

    private boolean maybeStartSending() {
        return (new Random().nextDouble() < Parameters.PROBABILITY_OF_NEW_PACKAGE);
    }

    private void startSending() {
        this.frame = PACKAGE_SIZE;
    }

    private boolean isSending() {
        return (frame>0);
    }

    private void sendFrame() {
        this.frame--;
        ethernet.insertNewFrames(ethernetIndex, name);
    }

    private boolean checkCollision() {
        return (isSending() && (ethernet.getFieldSize(ethernetIndex) > 0) && !ethernet.getField(ethernetIndex).getFirst().getUser().equals(name));
    }

    private boolean readyToSend() {
        return ethernet.getFieldSize(ethernetIndex) == 0;
    }

    private void stopCollisionManager() {
        multiplier = 1;
        frame = 0;
        fullMultiplier = 0;
    }

    private void collisionManager() throws InterruptedException {
        if (fullMultiplier==6) {
            stopCollisionManager();
        } else {
            if (multiplier == 1) {
                Thread.sleep((PACKAGE_SIZE + new Random().nextInt((int) Math.pow(2, multiplier++)) * PACKAGE_SIZE) * DELAY_MILLISECONDS);
            } else if (multiplier < 10) {
                Thread.sleep((new Random().nextInt((int) Math.pow(2, multiplier++)) * PACKAGE_SIZE) * DELAY_MILLISECONDS);
            } else {
                Thread.sleep((new Random().nextInt((int) Math.pow(2, 10)) * PACKAGE_SIZE) * DELAY_MILLISECONDS);
                fullMultiplier++;
            }
        }
    }

    @Override
    protected void task() {
//        System.err.println("name "+name+" frame "+frame+" collision "+collision+" multiplier "+multiplier+" multiplierF "+fullMultiplier);

        if (collision && frame==0) stopCollisionManager();

        if (checkCollision()) {
            this.frame = PACKAGE_SIZE;
            this.collision = true;
            try {
                collisionManager();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if (isSending()) {
            if (readyToSend()) {
                sendFrame();
            }
        } else {
            if (readyToSend()) {
                if (maybeStartSending()) {
                    startSending();
                }
            }
        }
    }















































//    @Override
//    protected void task() {
////        System.err.println("name "+name+" frames "+frames+" jam "+jam+" skip "+skip+" multiplier "+multiplier+" cde enabled "+collisionDetectionEnabled);
//        if (skip > 0) {
//            skip--;
//        } else {
//            if(collisionDetectionEnabled) {
//                skip = PACKAGE_SIZE + PACKAGE_SIZE*new Random().nextInt((int)Math.pow(2, multiplier));
//                multiplier *= 2;
//                collisionDetectionEnabled = false;
//            } else {
//                if (jam > 0) {
//                    sendJamFrame();
//                } else if (jam == 0) { //skończono nadawać jam, losowanie szczelin
//                    skip = PACKAGE_SIZE;
//                    collisionDetectionEnabled = true;
//                    jam--;
//                } else {
//                    if (ethernet.getField(ethernetIndex).isJammed()) {
//                        skip = PACKAGE_SIZE;
//                    } else {
//                        if (frames == 0) { //nie nadaję wiadomości - czy chcę zacząć?
//                            if (ethernet.getFieldSize(ethernetIndex) == 0) { //linia czysta
//                                multiplier = 1;
//                                if (new Random().nextDouble() < PROBABILITY_OF_NEW_PACKAGE) {
//                                    frames = PACKAGE_SIZE;
//                                    sendFrame();
//                                }
//                            } else { //ktoś nadaje -> grozi kolizją -> rezygnuję z nadawania
//                                //SKIPPING
//                            }
//                        } else { //nadaję wiadomość
//                            if (ethernet.getFieldSize(ethernetIndex) == 0) { //linia czysta - mogę dalej nadawać
//                                sendFrame();
//                            } else { //KOLIZJA
//                                frames = PACKAGE_SIZE;
//                                jam = PACKAGE_SIZE;
//                            }
//                        }
//                    }
//                }
//            }
//        }
//    }
//    @Override
//    public void run() {
//        running = true;
//        while(running) {
//            System.out.println("User1");
//            notifyNext();
//            System.out.println("User");
//            waitForPrevious();
//            System.out.println("User3");
//            task();
//        }
//    }
}
