
import java.io.IOException;
import java.net.*;
import java.util.HashMap;

public class Server {

    final static String INET_ADDR = "224.0.0.3";
    final static int PORT = 8888;

    public static void main(String[] args) throws IOException, InterruptedException {
        // Get the address that we are going to connect to.
        InetAddress addr = InetAddress.getByName(args[1]);
        InetAddress serviceAddress = InetAddress.getByName(args[3]);
        int port = Integer.parseInt(args[2]);
        int servicePort = Integer.parseInt(args[0]);



        Thread broadCast = new Thread(new ServerBroadcast(addr,port,servicePort,serviceAddress));
        broadCast.start();

        //Data Structure
        HashMap<String, String> dataBase = new HashMap<String, String>();

        // Open a new DatagramSocket, which will be used to receive the requests.
        DatagramSocket serverSocket = new DatagramSocket(servicePort);
        byte[] rbuf = new byte[1024];
        DatagramPacket packet = new DatagramPacket(rbuf,rbuf.length);
        String received;

        while (true){

            serverSocket.receive(packet);

            received = new String (packet.getData());

            System.out.println("Received Request " + received);

            String[] receivedArray = received.split("|");

            InetAddress replyAddress = packet.getAddress();
            int replyPort = packet.getPort();

            if (receivedArray[0].equalsIgnoreCase("register")) {

                dataBase.put(receivedArray[1],receivedArray[2]);

                String size = Integer.toString(dataBase.size());

                byte[] buffer = size.getBytes();

                DatagramPacket replyPacket = new DatagramPacket(buffer,buffer.length,replyAddress,replyPort);
                serverSocket.send(replyPacket);

            } else if (receivedArray[0].equalsIgnoreCase("lookup")) {

                String name = dataBase.get(receivedArray[1]);
                byte[] buffer = name.getBytes();

                DatagramPacket replyPacket = new DatagramPacket(buffer,buffer.length,replyAddress,replyPort);
                serverSocket.send(replyPacket);

            }

        }
    }
}
