
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

/**
 * Created by João on 04/03/2015.
 */
public class ServerBroadcast implements Runnable {

    InetAddress address;
    InetAddress saddress;
    int port;
    int sport;

    ServerBroadcast(InetAddress address, int port, int sport, InetAddress saddress){
        this.address = address;
        this.port = port;
        this.saddress = saddress;
        this.sport = sport;
    }

    @Override
    public void run() {
        // Open a new DatagramSocket, which will be used to send the data.
        try {
            DatagramSocket socket = new DatagramSocket();
            while (true) {
            	
                String msg = saddress.toString() + ":" + Integer.toString(sport);
                DatagramPacket msgPacket = new DatagramPacket(msg.getBytes(), msg.getBytes().length, address, port);
                socket.send(msgPacket);

                print();

                Thread.sleep(1000);
            }
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    private void print(){
    	System.out.println("multicast " + 
							address.toString().substring(1, address.toString().length())  + 
							":" + Integer.toString(port) +
							"::" + saddress.toString().substring(1, saddress.toString().length()) +
							":" + Integer.toString(sport));
    }
}
