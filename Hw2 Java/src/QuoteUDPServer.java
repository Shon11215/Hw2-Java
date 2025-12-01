import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class QuoteUDPServer {
    public static void main(String[] args) {
        Random random = new Random();

        String[] quotes = {
                "Talk is cheap, show me the code.",
                "Experience is the name everyone gives to their mistakes.",
                "I love deadlines; I love the whooshing noise they make as they go by.",
                "We suffer more often in imagination than in reality.",
                "If you tell the truth, you don't have to remember anything.",
                "In the beginning the Universe was created; this has made a lot of people very angry.",
                "The best way to predict the future is to invent it."
        };

        try {
            int port = 8080;
            DatagramSocket serverSocket = new DatagramSocket(port);
            System.out.println("Server is running on port " + port);

            byte[] receiveBuffer = new byte[1024];
            byte[] sendBuffer;

            while (true) {
                DatagramPacket receivedPacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                serverSocket.receive(receivedPacket);

                String clientMessage = new String(receivedPacket.getData(), 0, receivedPacket.getLength());
                System.out.println(clientMessage);

                if (clientMessage.toLowerCase().equals("get")) {
                    sendBuffer = quotes[random.nextInt(quotes.length)].getBytes();
                    SendPacket(serverSocket, sendBuffer, receivedPacket);
                } else if (isInvalidExpression(clientMessage)) {
                    String error = "Invalid command. Use 'get' or 'exit'.";
                    sendBuffer = error.getBytes();
                    SendPacket(serverSocket, sendBuffer, receivedPacket);
                } else if (clientMessage.toLowerCase().equals("exit")) {
                    System.out.println("Clinet finished");
                    break;
                }
            }
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    private static void SendPacket(DatagramSocket serverSocket, byte[] sendBuffer, DatagramPacket receivedPacket)
            throws IOException {
        InetAddress clientAddress = receivedPacket.getAddress();
        int clientPort = receivedPacket.getPort();

        DatagramPacket packet = new DatagramPacket(sendBuffer, sendBuffer.length, clientAddress, clientPort);
        serverSocket.send(packet);
    }

    private static boolean isInvalidExpression(String clientMessage) {
        if (!clientMessage.toLowerCase().equals("get") && !clientMessage.toLowerCase().equals("exit")) {
            return true;
        }
        return false;
    }
}