
import java.io.IOException;
import java.net.*;
import java.util.HashMap;

public class Server {

    final static String INET_ADDR = "224.0.0.3";
    final static int PORT = 8888;
    
    private static HashMap<String,String> database;
    
    private static byte[] buf;

    public static void main(String[] args) throws IOException, InterruptedException {
    	
        //Data Structure
    	database = new HashMap<String, String>();
        
        InetAddress b_addr = InetAddress.getByName(args[1]);
        int b_port = Integer.parseInt(args[2]);
        InetAddress s_addr = InetAddress.getByName(args[3]);
        int s_port = Integer.parseInt(args[0]);
        
        broadcast(b_addr, b_port, s_addr, s_port);
       
        DatagramSocket socket = new DatagramSocket(s_port);
        
        while (true){
        	
        	DatagramPacket packet = receive(socket);
        	
        	String[] info = fetch(packet);
        	String msg, oper = info[2].trim();

            InetAddress saddress = packet.getAddress();
            int sport = packet.getPort();

            if (oper.equalsIgnoreCase("register")) {
            	
            	String plate = info[3].trim(), owner = info[4].trim();
            	msg = "register " + plate + " " + owner + "::" + register(plate, owner);
            	
            	byte[] buffer = msg.getBytes();
           	 
                reply (buffer, socket, saddress, sport);

            } else if (oper.equalsIgnoreCase("lookup")) {
            	
            	String plate = info[3].trim();
            	
            	msg = "lookup " + plate + "::" + lookup(plate);
            	
            	byte[] buffer = msg.getBytes();
            	 
                reply (buffer, socket, saddress, sport);
            }

        }
    }
    
    private static void broadcast(InetAddress b_addr, int b_port, InetAddress s_addr, int s_port) {
    	
        Thread broadcast = new Thread(new ServerBroadcast(b_addr, b_port, s_port, s_addr));
        broadcast.start();
        
    }
    
    private static String register(String plate, String owner){
    	
    	if(!lookup(plate).equals("ERROR"))
    		return "Plate already exists";
    	
    	database.put(plate, owner);
    	
    	return plate;
    }
    
    private static String lookup (String plate){
    	String owner = database.get(plate);
    	
    	if(owner == null)
    		return "ERROR";
    	
    	return owner;
    	
    }
    
    private static void reply (byte[] reply, DatagramSocket socket, InetAddress addr, int port) throws IOException {
    	
    	  DatagramPacket packet = new DatagramPacket(reply, reply.length, addr, port);
          socket.send(packet);
          
    }
    
    private static DatagramPacket receive (DatagramSocket socket) throws IOException{
    	
    	byte[] rbuf = new byte[1024];
        DatagramPacket packet = new DatagramPacket(rbuf, rbuf.length);
        socket.receive(packet);
        
		return packet;
    }
    
    private static String[] fetch(DatagramPacket packet) throws IOException{
    	
    	buf = packet.getData();
    	
    	String received = new String (buf, 0, buf.length);

        System.out.println("Received Request " + received);

        String[] receivedArray = received.split("\\|");  
        
        return receivedArray;
    	
    }
    	
}
