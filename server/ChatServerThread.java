
import java.net.*;
import java.io.*;

public class ChatServerThread extends Thread
{  private ChatServer       server    = null;
   private Socket           socket    = null;
   private int              ID        = -1;
   private DataInputStream  streamIn  =  null;
   private DataOutputStream streamOut = null;
   
   private TLS2 tls = null;
   
   public boolean ready2read;

   public ChatServerThread(ChatServer _server, Socket _socket) throws Exception
   {  super();
      server = _server;
      socket = _socket;
      ID     = socket.getPort();
      tls = new TLS2(this);
      tls.start();
   }
   public void send(byte[] msg)
   {   try
       {  streamOut.write(msg);
          streamOut.flush();
       }
       catch(IOException ioe)
       {  System.out.println(ID + " ERROR sending: " + ioe.getMessage());
          server.remove(ID);
          stop();
       }
   }
   public int getID()
   {  return ID;
   }
   public void run()
   {  
	   tls.ready2read = true;
	   System.out.println("Server Thread " + ID + " running.");
      while (true)
      {  try
         {  
    	  if (streamIn.available()>0 && tls.ready2read) {
    		  byte[] msg = new byte[streamIn.available()];
    		  streamIn.read(msg);
    		  tls.msg = msg;
    		  tls.ready2read = false;
    		  //server.handle(ID, msg);
    		  
    	  }
    	  
         }
         catch(IOException ioe)
         {  System.out.println(ID + " ERROR reading: " + ioe.getMessage());
            server.remove(ID);
            stop();
         }
      }
   }
   public void open() throws IOException
   {  streamIn = new DataInputStream(new 
                        BufferedInputStream(socket.getInputStream()));
      streamOut = new DataOutputStream(new
                        BufferedOutputStream(socket.getOutputStream()));
   }
   public void close() throws IOException
   {  if (socket != null)    socket.close();
      if (streamIn != null)  streamIn.close();
      if (streamOut != null) streamOut.close();
   }
}