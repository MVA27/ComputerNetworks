import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;


public class ChatApp {
	
    static void dispaly(byte[] b){
        System.out.println("Elements in the data array");
        for(Byte element: b){
            System.out.print(element+"\t");
        }
    }
    
    static final int PORT = 6969;
    static final String IP = "localhost";
    static final int MESSAGE_SIZE = 255;
    
    public static void main(String[] args) throws Exception {
        
        System.out.println("Client Stated");
        
        int input;
        do{
            System.out.print("SEND->1 | RECEIVE->2 | EXIT->3 : ");
            input = new Scanner(System.in).nextInt();
            System.out.println();
            
            switch(input)
            {
                case 1: 
                    send();
                    break;
                    
                case 2:
                    receive();
                    break;
                    
                case 3:
                    break;
                    
                default:
                    System.out.println("*WRONG INPUT*");
            }
        }while(input!=3);
    }
    
    public static void send() throws Exception
    {
        //Create a socket object
        DatagramSocket socket = new DatagramSocket();
        InetAddress iNet = InetAddress.getByName(IP);
        
        //Read Input and store it in a buffer
        String userMessage;
        System.out.print("SEND : ");
        userMessage = new Scanner(System.in).nextLine();
        byte[] buffer = userMessage.getBytes();
        
        //Create a packet and send the data
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, iNet, PORT);
        socket.send(packet);
		
		//Release all the allocated resources (eg port,file etc)
		socket.close();
    }
    
    public static void receive() throws Exception
    {
        System.out.print("RECEIVED : ");
        //Create socket object
        DatagramSocket socket = new DatagramSocket(PORT);
        
        //Buffer object where data received will be stored
        byte[] buffer = new byte[MESSAGE_SIZE];
        
        //Packet object to hold the data of packet that is received
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
        socket.receive(packet);
        
        //Fill the buffer with data present in packet
        packet.setData(buffer);
        
        for(byte b : buffer)
        {
            if(b!=0)
                System.out.print((char)b);
        }
        
        System.out.println();
		
		//Release all the allocated resources (eg port,file etc)
		socket.close();
    }
}

