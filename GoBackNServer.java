import java.io.*;
import java.net.*;
import java.util.*;

public class GoBackNServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(6000);
        System.out.println("Server started. Waiting for client...");

        Socket socket = serverSocket.accept();
        System.out.println("Client connected.");

        DataInputStream dis = new DataInputStream(socket.getInputStream());
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

        int totalFrames = dis.readInt();
        int windowSize = dis.readInt();

        Set<Integer> lostFrames = new HashSet<>();
        lostFrames.add(2); // Simulate loss of frame 2

        int expected = 0;

        while (expected < totalFrames) {
            int count = 0;
            System.out.println("\nReceiving window from frame: " + expected);

            boolean errorDetected = false;

            for (int i = expected; i < expected + windowSize && i < totalFrames; i++) {
                String frameData = dis.readUTF();

                if (lostFrames.contains(i)) {
                    System.out.println("Frame " + i + " is lost. Sending NACK " + i);
                    dos.writeUTF("NACK " + i);
                    dos.flush();
                    errorDetected = true;
                    break;
                }

                if (!errorDetected) {
                    System.out.println("Received Frame " + i + ": " + frameData);
                    count++;
                }
            }

            if (!errorDetected) {
                expected += count;
                dos.writeUTF("ACK " + expected);
                dos.flush();
                System.out.println("Sent cumulative ACK " + expected);
            }
        }

        socket.close();
        serverSocket.close();
        System.out.println("Server closed.");
    }
}

