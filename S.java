import java.io.*;
import java.net.*;

public class S{
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 6000);
        System.out.println("Connected to Sliding Window Server.");

        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        System.out.print("Enter total number of frames: ");
        int totalFrames = Integer.parseInt(br.readLine());

        System.out.print("Enter window size: ");
        int windowSize = Integer.parseInt(br.readLine());

        // Send initial info to server
        dos.writeInt(windowSize);
        dos.writeInt(totalFrames);
        dos.flush();

        int count = 0;
        while (count < totalFrames) {
            for (int i = 0; i < windowSize && count < totalFrames; i++) {
                System.out.print("Enter data for frame " + (count + 1) + ": ");
                String data = br.readLine();
                dos.writeUTF(data);
                dos.flush();
                count++;
            }
            System.out.println("Sliding window...");
        }

        socket.close();
    }
}

