 import java.io.*;
 import java.net.*;
 class ChatServer{
 public static void main(String[] args) throws IOException{
  ServerSocket server = null; //server socket for handling server operation
 
 try{
   server = new ServerSocket(90); //establish connection port
  }
  catch(IOException ioEx){
   System.out.println("Error in port 90");
   System.exit(1);
  }
  
  Socket serverSocket = null; //handle client request
  try{
   System.out.println("Waiting for client!");
   serverSocket = server.accept(); //listen to the client request
   System.out.println("connection accepted at " + serverSocket);
  }
  catch(IOException ioEx){
   System.out.println("Failed to Connect");
   System.exit(1);
  }
  //output to the client
  DataOutputStream frmServer = new DataOutputStream(serverSocket.getOutputStream());
  //get input from client
  BufferedReader toServer = new BufferedReader(new 
InputStreamReader(serverSocket.getInputStream()));
  //get input from keyboard;
  BufferedReader kybd = new BufferedReader(new InputStreamReader(System.in));
  String clientMsg, serverMsg;
  System.out.println("Start Chatting! Type exit to terminate!");
  boolean end = false;
  do{
   clientMsg = toServer.readLine(); //accept client's message;
   System.out.println("From Client : " + clientMsg);
   //int len = msgClient.length();
   //accept msg and send to client
   serverMsg = kybd.readLine();
   frmServer.writeBytes(serverMsg);
   frmServer.write(13);
   frmServer.write(10);
   frmServer.flush();
   if(serverMsg.equals("exit"))
    end = true;
  }while(!end);
  frmServer.close();
  server.close();
 }
 }
