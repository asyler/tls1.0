package com.example.asylertls.tls3;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import static com.example.asylertls.tls3.TLSContainer2.toByteString;

/**
 * A {@link PreferenceActivity} that presents a set of application settings. On
 * handset devices, settings are presented as a single list. On tablets,
 * settings are split by category, with category headers shown to the left of
 * the list of settings.
 * <p/>
 * See <a href="http://developer.android.com/design/patterns/settings.html">
 * Android Design: Settings</a> for design guidelines and the <a
 * href="http://developer.android.com/guide/topics/ui/settings.html">Settings
 * API Guide</a> for more information on developing a Settings UI.
 */
public class PacketActivity extends BaseActivity {
    int changing_now_position = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.packet);

        BaseActivity.context = this;

        Intent intent = getIntent();
        String value = intent.getStringExtra("msg");

        final Button button = (Button) findViewById(R.id.send_packet_button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                MyApp app = (MyApp)getApplication();
                ListView m_listview = (ListView) findViewById(R.id.packet_list);
                final ListItemAdapter adapter = ((ListItemAdapter)m_listview.getAdapter());
                app.tlsclient.client.tls.setCurrentPacket(adapter.getPacket());
                        app.tlsclient.client.tls.waiting4ui = false;
            }
        });

        ListView m_listview = (ListView) findViewById(R.id.packet_list);
        m_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                ListItem item = (ListItem) parent.getItemAtPosition(position);
                final ListItemAdapter adapter = ((ListItemAdapter)parent.getAdapter());

                changing_now_position = position;

                if (item.getType().equals("changeable")) {
                    Intent myIntent = new Intent(PacketActivity.this, ChangeListActivity.class);
                    myIntent.putExtra("type", item.getTitle());
                    myIntent.putExtra("description", item.getDescription());
                    PacketActivity.this.startActivityForResult(myIntent, 1);
                } else if (item.getType().equals("final")) {
                    Intent myIntent = new Intent(PacketActivity.this, EditHex.class);
                    myIntent.putExtra("value", item.getValue());
                    PacketActivity.this.startActivityForResult(myIntent, 3);
                } else if (item.getType().equals("tree")) {
                    Intent myIntent = new Intent(PacketActivity.this, DetailsListActivity.class);
                    myIntent.putExtra("data", adapter.getPacket().getFragment(item.getTitle()));
                    myIntent.putExtra("class_name", item.getDescription());
                    PacketActivity.this.startActivityForResult(myIntent, 2);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        BaseActivity.context = this;
        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                String result=data.getStringExtra("result");
                ListView m_listview = (ListView) findViewById(R.id.packet_list);
                ListItem item = (ListItem) m_listview.getItemAtPosition(changing_now_position);
                if (item.getDescription()!=result) {
                    final ListItemAdapter adapter = ((ListItemAdapter)m_listview.getAdapter());
                    TLSPacket P = adapter.getPacket();
                    String res = null;
                    String title = item.getTitle();
                    res = P.set(title,result);
                    if (res!=null) {
                        item.setValue(res);
                        item.setDescription(result);
                        adapter.notifyDataSetChanged(changing_now_position);
                    }
                }

                changing_now_position = -1;
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
        else if (requestCode == 3) {
            if(resultCode == Activity.RESULT_OK){
                String result=data.getExtras().get("result").toString();
                ListView m_listview = (ListView) findViewById(R.id.packet_list);
                ListItem item = (ListItem) m_listview.getItemAtPosition(changing_now_position);
                if (item.getDescription()!=result) {
                    final ListItemAdapter adapter = ((ListItemAdapter)m_listview.getAdapter());
                    TLSPacket P = adapter.getPacket();
                    String res = null;
                    String title = item.getTitle();
                    res = P.set_raw(title, result);
                    if (res!=null) {
                        item.setValue(result);
                        item.setDescription(res);
                        adapter.notifyDataSetChanged(changing_now_position);
                    }
                }

                changing_now_position = -1;
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
        else if (requestCode == 2) {
            if(resultCode == Activity.RESULT_OK){
                byte [] content = data.getByteArrayExtra("content");
                Boolean result=data.getBooleanExtra("changed",false);
                ListView m_listview = (ListView) findViewById(R.id.packet_list);
                ListItem item = (ListItem) m_listview.getItemAtPosition(changing_now_position);
                if (result) {
                    final ListItemAdapter adapter = ((ListItemAdapter)m_listview.getAdapter());
                    adapter.notifyDataSetChanged(changing_now_position);

                    adapter.getPacket().set_raw(item.getTitle(), content);
                    adapter.updateLengthItem(toByteString(adapter.getPacket().getLengthParam().raw_value),
                            String.valueOf(adapter.getPacket().getLengthParam().value));
                }

                changing_now_position = -1;
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }
}
