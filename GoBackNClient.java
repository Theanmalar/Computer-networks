import java.io.*;
import java.net.*;
import java.util.*;

public class GoBackNClient {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 6000);
        System.out.println("Connected to server.");

        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
        DataInputStream dis = new DataInputStream(socket.getInputStream());
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter total number of frames: ");
        int totalFrames = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter window size: ");
        int windowSize = sc.nextInt();
        sc.nextLine();

        String[] frameData = new String[totalFrames];
        for (int i = 0; i < totalFrames; i++) {
            System.out.print("Enter data for frame " + i + ": ");
            frameData[i] = sc.nextLine();
        }

        dos.writeInt(totalFrames);
        dos.writeInt(windowSize);

        int base = 0;

        while (base < totalFrames) {
            System.out.println("\nSending window from frame: " + base);
            for (int i = base; i < base + windowSize && i < totalFrames; i++) {
                System.out.println("Sent frame " + i + ": " + frameData[i]);
                dos.writeUTF(frameData[i]);
            }

            dos.flush();

            String response = dis.readUTF();
            if (response.startsWith("ACK")) {
                int ackNo = Integer.parseInt(response.split(" ")[1]);
                System.out.println("Received ACK: " + ackNo);
                base = ackNo;
            } else if (response.startsWith("NACK")) {
                int nackNo = Integer.parseInt(response.split(" ")[1]);
                System.out.println("Received NACK: " + nackNo);
                base = nackNo;
            }
        }

        socket.close();
        sc.close();
        System.out.println("All frames sent. Client closed.");
    }
}
