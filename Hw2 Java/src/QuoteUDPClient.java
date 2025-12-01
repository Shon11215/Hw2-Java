import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class QuoteUDPClient {
    public static void main(String[] args) {
        try {
            InetAddress serverAddress = InetAddress.getByName("localhost");
            int serverPort = 8080;

            DatagramSocket clientSocket = new DatagramSocket();
            Scanner scanner = new Scanner(System.in);

            byte[] receiveBuffer = new byte[1024];
            byte[] sendBuffer;

            System.out.println("Enter GET to the server to get a random sentence or exit to leave: ");
            while (true) {

                String clientMessage = scanner.nextLine();

                sendBuffer = clientMessage.getBytes();

                DatagramPacket sendpacket = new DatagramPacket(sendBuffer, sendBuffer.length, serverAddress,
                        serverPort);
                clientSocket.send(sendpacket);

                if (clientMessage.toLowerCase().equals("exit")) {
                    System.out.println("Server shutting down");
                    break;
                }
                DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                clientSocket.receive(receivePacket);

                String serverResponse = new String(receivePacket.getData(), 0, receivePacket.getLength());
                System.out.println("Quote received: " + serverResponse);

            }
            clientSocket.close();
            scanner.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
