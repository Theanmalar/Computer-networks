import java.io.*;
import java.net.*;

public class StopWaitClient {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 5000);
        System.out.println("Connected to server.");

        DataInputStream dis = new DataInputStream(socket.getInputStream());
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            System.out.print("Enter frame data (or 'exit' to quit): ");
            String data = br.readLine();
            dos.writeUTF(data);
            dos.flush();

            if (data.equalsIgnoreCase("exit"))
                break;

            String ack = dis.readUTF();
            System.out.println("Server response: " + ack);

            if (ack.equals("NACK")) {
                System.out.println("Retransmitting frame: " + data);
                dos.writeUTF(data);
                dos.flush();
                String ack2 = dis.readUTF();
                System.out.println("Server response: " + ack2);
            }
        }

        socket.close();
    }
}
