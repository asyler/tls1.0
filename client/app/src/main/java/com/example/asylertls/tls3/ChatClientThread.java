package com.example.asylertls.tls3;

/**
 * Created by asyler on 12/11/15.
 */

import java.net.*;
import java.io.*;

public class ChatClientThread extends Thread
{  private Socket           socket   = null;
    public ChatClient       client   = null;
    private DataInputStream  streamIn = null;

    public TLS tls;

    public ChatClientThread(ChatClient _client, Socket _socket) throws Exception
    {  client   = _client;
        socket   = _socket;

        open();
        start();

        tls = new TLS(this);
        //tls.runDemo();
    }

    public void send(byte[] msg) {
        client.send(msg);
    }

    public void open()
    {  try
    {  streamIn  = new DataInputStream(socket.getInputStream());
    }
    catch(IOException ioe)
    {  System.out.println("Error getting input stream: " + ioe);
        client.stop();
    }
    }
    public void close()
    {  try
    {  if (streamIn != null) streamIn.close();
    }
    catch(IOException ioe)
    {  System.out.println("Error closing input stream: " + ioe);
    }
    }
    public void run()
    {
        while (true)
        {  try
        {
            sleep(100);
            //client.handle(streamIn.readUTF());
            if (streamIn.available()>0) {
                byte[] msg = new byte[streamIn.available()];
                streamIn.read(msg);
                tls.msg = msg;
                tls.ready2read = false;
                //server.handle(ID, msg);

            }

        }
        catch(IOException ioe)
        {  System.out.println("Listening error: " + ioe.getMessage());
            client.stop();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        }
    }
}
