import java.io.*;
import java.net.*;

public class SlidingWindowServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(6000);
        System.out.println("Server started. Waiting for client...");

        Socket socket = serverSocket.accept();
        System.out.println("Client connected.");

        DataInputStream dis = new DataInputStream(socket.getInputStream());
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

        int frameSize = dis.readInt();  // total frames
        int windowSize = dis.readInt(); // window size

        int expectedFrame = 0;

        while (expectedFrame < frameSize) {
            int count = 0;

            System.out.println("\nReceiving frames in window starting from: " + expectedFrame);
            while (count < windowSize && expectedFrame + count < frameSize) {
                String frameData = dis.readUTF();
                System.out.println("Received frame " + (expectedFrame + count) + ": " + frameData);
                count++;
            }

            expectedFrame += count;
            dos.writeInt(expectedFrame); // cumulative ACK
            System.out.println("Sent cumulative ACK for frame: " + expectedFrame);
        }

        socket.close();
        serverSocket.close();
        System.out.println("All frames received. Server closed.");
    }
}
