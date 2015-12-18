package com.example.asylertls.tls3;

/**
 * Created by asyler on 12/11/15.
 */

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class ChatClient implements Runnable
{  private Socket socket              = null;
    private Thread thread              = null;
    private DataInputStream  console   = null;
    private DataOutputStream streamOut = null;
    public ChatClientThread client    = null;

    private boolean ready2send = false;
    private byte[] msg2send = null;

    public void alert(String msg) {
        System.out.println(msg);
        BaseActivity.alert(0,msg);
    }

    public void prompt() {
        BaseActivity.alert(4,"");
    }

    public void alertPacket(TLSPacket P) {
        BaseActivity.alert(2,P);
    }

    public ChatClient()
    {

    }

    public void __start(String serverName, int serverPort) {
        System.out.println("Establishing connection. Please wait ...");
        try
        {
            socket = new Socket(serverName, serverPort);
            System.out.println("Connected: " + socket);
            BaseActivity.alert(1, "");
            start();
        }
        catch(UnknownHostException uhe)
        {  alert("Host unknown: " + uhe.getMessage()); }
        catch(Exception ioe)
        {
            alert("Unexpected exception: " + ioe.getMessage());
        }
    }

    public void run()
    {
        while (thread != null)
        {  try
        {
            thread.sleep(100);
            if (ready2send) {
                __send();
            }
        }
        catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        }
    }

    public void __send() {
        try
        {
            streamOut.write(msg2send);
            streamOut.flush();
            ready2send = false;
        }
        catch(IOException ioe)
        {  System.out.println("Sending error: " + ioe.getMessage());
            stop();
        }
    }

    public void send(byte[] msg) {
        msg2send = msg;
        ready2send = true;
        //__send();
    }
    public void handle(String msg)
    {  if (msg.equals(".bye"))
    {  System.out.println("Good bye. Press RETURN to exit ...");
        stop();
    }
    else
        System.out.println(msg);
    }
    public void start() throws Exception
    {  console   = new DataInputStream(System.in);
        streamOut = new DataOutputStream(socket.getOutputStream());
        if (thread == null)
        {
            thread = new Thread(this);
            thread.start();
            client = new ChatClientThread(this, socket);
            client.tls.runDemo();
        }
    }
    public void stop()
    {  if (thread != null)
    {  thread.stop();
        thread = null;
    }
        try
        {  if (console   != null)  console.close();
            if (streamOut != null)  streamOut.close();
            if (socket    != null)  socket.close();
        }
        catch(IOException ioe)
        {  System.out.println("Error closing ..."); }
        client.close();
        client.stop();
    }
    public static void main(String args[])
    {
        //ChatClient client = new ChatClient("127.0.0.1", 5061);
    }
}

