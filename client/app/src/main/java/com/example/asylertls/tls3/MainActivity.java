package com.example.asylertls.tls3;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BaseActivity.context = this;

        final Button button = (Button) findViewById(R.id.connect_button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final EditText editIP = (EditText) findViewById(R.id.editIP);
                final EditText editPort = (EditText) findViewById(R.id.editPort);
                //button.setText("Connecting...");

                new Thread(new Runnable() {
                    public void run() {
                        ((MyApp) getApplication()).tlsclient = new ChatClient();
                        ((MyApp) getApplication()).tlsclient.__start(editIP.getText().toString(),
                                Integer.parseInt(editPort.getText().toString()));
                    }
                }).start();
            }
        });
    }
}
