package com.example.asylertls.tls3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditHex extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_hex);

        Intent intent = getIntent();
        String value = intent.getStringExtra("value");

        final EditText edit_text = (EditText) findViewById(R.id.editText);
        edit_text.setText(value);

        final Button button = (Button) findViewById(R.id.edit_button_save);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent returnIntent = new Intent();
                returnIntent.putExtra("result", edit_text.getText());
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            }
        });
    }
}
