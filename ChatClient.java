import java.io.*;
 import java.net.*;
 class ChatClient{
 public static void main(String[] args) throws IOException{
  Socket client = null;
  BufferedReader toClient = null;
  DataOutputStream frmClient = null;
  BufferedReader kybd = new BufferedReader(new InputStreamReader(System.in));
  try{
   client = new Socket(InetAddress.getLocalHost(),90); //send request to server;
   toClient = new BufferedReader(new InputStreamReader(client.getInputStream()));
   frmClient = new DataOutputStream(client.getOutputStream());
  }
  catch(UnknownHostException unknwnEx){
   System.out.println("Server not Found");
   System.exit(1);
  }
  System.out.println("Start Conversation!");
  boolean end = false;
  String clientMsg, serverMsg;
  do{
   //read message from the keyboard;
   clientMsg = kybd.readLine();
   //send to server;
   frmClient.writeBytes(clientMsg);
   frmClient.write(13);
   frmClient.write(10);
   frmClient.flush();
   
   //receive message from server
   System.out.println("You: ");
   serverMsg = toClient.readLine();
   System.out.println("From Server: " + serverMsg);
   if(clientMsg.equals("exit"))
    end = true;
  }while(!end);
  
  toClient.close();
  frmClient.close();
  client.close();
 }
 }