
import java.net.*;
import java.io.*;

public class ChatServer implements Runnable
{  public ChatServerThread client = null;
   private ServerSocket server = null;
   private Thread       thread = null;
   
   public byte[] msg = null;

   public ChatServer(int port)
   {  try
      {  System.out.println("Binding to port " + port + ", please wait  ...");
         server = new ServerSocket(port);  
         System.out.println("Server started: " + server);
         start(); }
      catch(IOException ioe)
      {  System.out.println("Can not bind to port " + port + ": " + ioe.getMessage()); }
   }
   public void run()
   {  while (thread != null)
      {  try
         {  System.out.println("Waiting for a client ..."); 
            addThread(server.accept()); }
         catch(IOException ioe)
         {  System.out.println("Server accept error: " + ioe); stop(); }
      }
   }
   public void start()
   {  if (thread == null)
      {  thread = new Thread(this); 
         thread.start();
      }
   }
   public void stop()
   {  if (thread != null)
      {  thread.stop(); 
         thread = null;
      }
   }
   public synchronized void handle(int ID, byte[] msg)
   {  if (msg.equals(".bye"))
      {  //client.send(".bye");
         remove(ID); }
      else {
    	  System.out.println(msg);
    	  this.msg = msg;
         //client.send(ID + ": " + msg);
      }
   }
   public synchronized void remove(int ID)
   {     ChatServerThread toTerminate = client;
         System.out.println("Removing client thread " + ID);
         try
         {  toTerminate.close(); }
         catch(IOException ioe)
         {  System.out.println("Error closing thread: " + ioe); }
         toTerminate.stop();
   }
   private void addThread(Socket socket)
   {  	
	   System.out.println("Client accepted: " + socket);
       try {
    	   client = new ChatServerThread(this, socket);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	   }
       try
       {  client.open(); 
          client.start();  
       }
       catch(IOException ioe)
       {  System.out.println("Error opening thread: " + ioe); }
   }
   public static void main(String args[]) {
	   ChatServer server = new ChatServer(5061);
   }
}
 