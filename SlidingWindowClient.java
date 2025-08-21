import java.io.*;
import java.net.*;
import java.util.Scanner;

public class SlidingWindowClient {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 6000);
        System.out.println("Connected to server.");

        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
        DataInputStream dis = new DataInputStream(socket.getInputStream());
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter total number of frames to send: ");
        int totalFrames = sc.nextInt();

        System.out.print("Enter sliding window size: ");
        int windowSize = sc.nextInt();
        sc.nextLine(); // consume newline

        String[] frameData = new String[totalFrames];
        for (int i = 0; i < totalFrames; i++) {
            System.out.print("Enter data for frame " + i + ": ");
            frameData[i] = sc.nextLine();
        }

        dos.writeInt(totalFrames);  // send total frame size
        dos.writeInt(windowSize);   // send window size
        dos.flush();

        int sentUpto = 0;

        while (sentUpto < totalFrames) {
            int count = 0;
            System.out.println("\nSending window starting from frame: " + sentUpto);

            while (count < windowSize && sentUpto + count < totalFrames) {
                dos.writeUTF(frameData[sentUpto + count]);
                System.out.println("Sent frame " + (sentUpto + count) + ": " + frameData[sentUpto + count]);
                count++;
            }

            dos.flush();

            int ack = dis.readInt();
            System.out.println("Received ACK for frame: " + ack);
            sentUpto = ack;
        }

        socket.close();
        sc.close();
        System.out.println("All frames sent. Client closed.");
    }
}
