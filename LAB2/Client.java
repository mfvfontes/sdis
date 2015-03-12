

import java.io.IOException;
import java.net.*;

public class Client {

    final static String INET_ADDR = "224.0.0.3";
    final static int PORT = 8888;

    public static void main(String[] args) throws IOException {
        // Get the address that we are going to connect to.
        InetAddress address = InetAddress.getByName(args[0]);
        int port = Integer.parseInt(args[1]);

        MulticastSocket msocket = join(address, port);

        String[] info = fetch(msocket);

        InetAddress saddress = InetAddress.getByName(info[0]);
        int sport = Integer.parseInt(info[1]);
        
        String request = brequest(args);
        DatagramSocket socket = send(request, saddress, sport);
        
        System.out.println("multicast " + 
        					address.toString().substring(1, address.toString().length()) + ":" + 
        					Integer.toString(port) + "::" + 
        					saddress.toString().substring(1, saddress.toString().length()) + ":" + 
        					Integer.toString(sport));

        DatagramPacket packet = receive(socket);

        byte[] buf = packet.getData();
        
        String s = new String(buf, 0, buf.length);
        
        System.out.println(s);

    }
   
    private static String brequest(String args[]){
    	String request = "";
    	
    	for (int i = 0; i < args.length; i++) {
            request = request + args[i];
            if(i < args.length - 1)
            	request = request + "|";
        }
    	
    	return request;
    }
    
    private static String[] fetch(MulticastSocket socket) throws IOException{
    	
    	byte[] buf = new byte[256];
    	
    	DatagramPacket msgPacket = new DatagramPacket(buf, buf.length);
        socket.receive(msgPacket);
        
        //Read from buffer IP:port
        String msg = new String(buf, 0, buf.length);
        String[] info = msg.split(":");
        
        info[0] = info[0].substring(1, info[0].length());
        info[1] = info[1].trim();
        
        return info;
    }
    
    private static MulticastSocket join(InetAddress address, int port) throws IOException{
    	MulticastSocket socket = new MulticastSocket(port);
    	
    	socket.joinGroup(address);

        return socket;
    }
    
    private static DatagramSocket send(String request, InetAddress saddress, int sport) throws IOException {
    	
        DatagramSocket socket = new DatagramSocket();
        DatagramPacket packet = new DatagramPacket(request.getBytes(), request.getBytes().length, saddress, sport);

        socket.send(packet);
        
    	return socket;
    }
    
    private static DatagramPacket receive(DatagramSocket socket) throws IOException{
    	
    	byte[] rbuf = new byte[1024];
        DatagramPacket packet = new DatagramPacket(rbuf, rbuf.length);
        socket.receive(packet);
        
		return packet;
    }
}