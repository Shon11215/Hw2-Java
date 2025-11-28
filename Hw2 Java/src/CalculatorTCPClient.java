import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class CalculatorTCPClient {
    public static void main(String[] args) {
        try (Socket serverSocket = new Socket("localhost", 9090)) {
            System.out.println("Client is connected to 9090 port");
            PrintWriter out = new PrintWriter(serverSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));

            String input;
            while (true) {
                System.out.print("Enter expression (num op num) or exit to exit: ");
                input = stdIn.readLine();

                if(input.toLowerCase().equals("exit")) break;

                out.println(input);

                System.out.println(in.readLine());

            }
        } catch (IOException e) {
            // TODO: handle exception
        }
    }
}