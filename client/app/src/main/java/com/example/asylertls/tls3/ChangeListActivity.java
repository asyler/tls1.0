package com.example.asylertls.tls3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;


public class ChangeListActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_list_activity);

        BaseActivity.context = this;

        Intent intent = getIntent();
        String _type = intent.getStringExtra("type");
        String description = intent.getStringExtra("description");
        TLSParameter2 paramClass = null;
        ArrayList<String> items = new ArrayList<>();
        if ( new String("Client version").equals(_type))
            _type = "Protocol version";
        final String type = _type;
        //items = TLSParameter.getAllForType(type);
        try {
            paramClass = ((TLSParameter2) Class.forName(BaseActivity.PATH+".fields."+TLSContainer.toClassName(type)).newInstance());
            items = paramClass.getOptions();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        ListView m_listview = (ListView) findViewById(R.id.change_list);

        ArrayAdapter adapter =
                new ArrayAdapter(ChangeListActivity.this, android.R.layout.simple_list_item_single_choice, items);

        m_listview.setAdapter(adapter);

        m_listview.setItemChecked(items.indexOf(description),true);


        final TLSParameter2 finalParamClass = paramClass;
        m_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                String item = (String) parent.getItemAtPosition(position);

                if (finalParamClass.available(item)) {
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("result", item);
                    setResult(Activity.RESULT_OK, returnIntent);
                    finish();
                } else {
                    alert(0, "Not implemented, choose another!");
                }
            }
        });

    }
}