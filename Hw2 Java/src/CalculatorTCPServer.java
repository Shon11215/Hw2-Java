import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class CalculatorTCPServer {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(9090)) {
            System.out.println("Server is listening on port 9090");
            Socket clientSocket = serverSocket.accept();
            System.out.println("Client has connected!!");
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            String userMsg;
            while ((userMsg = in.readLine()) != null) {
                System.out.println("User sent: " + userMsg);

                if (userMsg.toLowerCase().equals("exit"))
                    break;
                String[] arrToCheck = userMsg.split(" ");

                if (!HandleExceptions(arrToCheck, out, userMsg)) {
                    continue;
                }
                out.println(userMsg + " = " +Calculate(arrToCheck));

            }

        } catch (IOException e) {
            // TODO: handle exception

        }

    }

    public static String Calculate(String[] arrToCheck) {
        double num1 = Double.parseDouble(arrToCheck[0]);
        double num2 = Double.parseDouble(arrToCheck[2]);
        double result = 0;
        switch (arrToCheck[1]) {
            case "+":
                result = num1 + num2;
                break;
            case "/":
                result = num1 / num2;
                break;
            case "*":
                result = num1 * num2;
                break;
            case "-":
                result = num1 - num2;
                break;

        }
        return String.valueOf(result);
    }

    public static boolean isNumeric(String str) {
        if (str == null) {
            return false;
        }
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean HandleExceptions(String[] arrToCheck, PrintWriter out, String userMsg) {
        if (arrToCheck.length != 3) {
            out.println(userMsg + " = Error: Invalid expression");
            return false;
        } else if (!"+-/*".contains(arrToCheck[1]) || !isNumeric(arrToCheck[0]) || !isNumeric(arrToCheck[2])) {
            out.println(userMsg + " = Error: Invalid expression");
            return false;

        }

        else if (arrToCheck[1].equals("/") && arrToCheck[2].equals("0")) {
            out.println(userMsg + " = Error: Division by zero");
            return false;

        }

        return true;

    }

}
