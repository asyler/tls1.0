package com.example.asylertls.tls3;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class BaseActivity extends AppCompatActivity {

    private static Handler mHandler;
    public static Context context;
    public static final String PATH = "com.example.asylertls.tls3";
    private String m_Text = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        mHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message message) {
                switch(message.what) {
                    case 0: {
                        __alert(message.obj.toString());
                        break;
                    }
                    case 1: {
                        Intent myIntent = new Intent(BaseActivity.this, PacketActivity.class);
                        //myIntent.putExtra("msg", (byte[]) message.obj); //Optional parameters
                        BaseActivity.this.startActivityForResult(myIntent,0);
                        break;
                    }
                    case 2: {
                        TLSPacket P = (TLSPacket) message.obj;

                        ListView m_listview = null;
                        while (m_listview==null) {
                            m_listview = (ListView) findViewById(R.id.packet_list);
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }

                        ArrayList<ListItem> items = new ArrayList<ListItem>();
                        ArrayList<String[]> dict = P.getDict();
                        for (int i=0; i<dict.size(); i++) {
                            items.add(new ListItem(
                                    dict.get(i)[0], dict.get(i)[1], dict.get(i)[2], dict.get(i)[3]
                            ));
                        }

                        ListItemAdapter adapter =
                                new ListItemAdapter(BaseActivity.this, items);

                        adapter.setPacket(P);

                        m_listview.setAdapter(adapter);

                        break;
                    }
                    case 4: {
                        prompt();
                    }
                }


            }
        };

    }

    public static void alert(int i, Object msg) {
        Message message = mHandler.obtainMessage(i,msg);
        message.sendToTarget();
    }

    public void __alert(CharSequence message) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    public void prompt() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Print message");

// Set up the input
        final EditText input = new EditText(this);
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        //input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        builder.setView(input);

// Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                m_Text = input.getText().toString();
                MyApp app = (MyApp)getApplication();
                app.tlsclient.client.tls.m_text  = m_Text;
                app.tlsclient.client.tls.waiting4ui = false;
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }
}
