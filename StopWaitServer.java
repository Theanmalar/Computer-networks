import java.io.*;
import java.net.*;
import java.util.Random;

public class StopWaitServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(5000);
        System.out.println("Server started, waiting for connection...");

        Socket socket = serverSocket.accept();
        System.out.println("Client connected.");

        DataInputStream dis = new DataInputStream(socket.getInputStream());
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

        Random rand = new Random();

        while (true) {
            String frame = dis.readUTF();
            if (frame.equalsIgnoreCase("exit")) {
                System.out.println("Connection closed by client.");
                break;
            }
            System.out.println("Received frame: " + frame);

            // Randomly send ACK or NACK
            if (rand.nextInt(10) < 7) { // 70% chance ACK
                dos.writeUTF("ACK");
                System.out.println("ACK sent.");
            } else { // 30% chance NACK
                dos.writeUTF("NACK");
                System.out.println("NACK sent (asking for retransmission).");
            }
            dos.flush();
        }

        socket.close();
        serverSocket.close();
    }
}
