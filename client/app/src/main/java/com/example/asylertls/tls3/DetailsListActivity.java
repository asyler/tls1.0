package com.example.asylertls.tls3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;

import java.util.ArrayList;

import static com.example.asylertls.tls3.TLSContainer.toClassName;
import static com.example.asylertls.tls3.TLSContainer2.toByteString;


public class DetailsListActivity extends BaseActivity {
    private int changing_now_position = -1;
    TLSContainer C = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_list_activity);

        BaseActivity.context = this;

        Intent intent = getIntent();
        byte[] body = intent.getByteArrayExtra("data");
        String className = intent.getStringExtra("class_name");


        try {
            C = ((TLSContainer) Class.forName(BaseActivity.PATH+'.'+toClassName(className)).newInstance());
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            alert(0,"Not implemented!");
            return;
        }

        ListView m_listview = null;
        while (m_listview==null) {
            m_listview = (ListView) findViewById(R.id.details_list);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        C.parse(body);
        ArrayList<ListItem> items = new ArrayList<ListItem>();
        ArrayList<String[]> dict = C.getDict();
        for (int i=0; i<dict.size(); i++) {
            items.add(new ListItem(
                    dict.get(i)[0], dict.get(i)[1], dict.get(i)[2], dict.get(i)[3]
            ));
        }

        ListItemAdapter adapter;
        if (C.allowDelete())
            adapter = new ListItemAdapter(DetailsListActivity.this, items, 1);
        else {
            adapter = new ListItemAdapter(DetailsListActivity.this, items);
        }

        m_listview.setAdapter(adapter);

        m_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                ListItem item = (ListItem) parent.getItemAtPosition(position);
                final ListItemAdapter adapter = ((ListItemAdapter) parent.getAdapter());

                changing_now_position = position;

                if (item.getType().equals("changeable")) {
                    Intent myIntent = new Intent(DetailsListActivity.this, ChangeListActivity.class);
                    myIntent.putExtra("type", item.getTitle());
                    myIntent.putExtra("description", item.getDescription());
                    DetailsListActivity.this.startActivityForResult(myIntent, 1);
                } else if (item.getType().equals("final")) {
                    Intent myIntent = new Intent(DetailsListActivity.this, EditHex.class);
                    myIntent.putExtra("value", item.getValue());
                    DetailsListActivity.this.startActivityForResult(myIntent, 3);
                } else if (item.getType().equals("tree")) {
                    Intent myIntent = new Intent(DetailsListActivity.this, DetailsListActivity.class);
                    myIntent.putExtra("data", C.getFragment(item.getTitle()));
                    String className = item.getDescription();
                    if (className==null)
                        className = item.getTitle();
                    myIntent.putExtra("class_name", className);
                    DetailsListActivity.this.startActivityForResult(myIntent, 2);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (C!=null && C.allowDelete()) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu, menu);

            return true;
        }
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        ListView m_listview = (ListView) findViewById(R.id.details_list);
        ListItemAdapter adapter = (ListItemAdapter)m_listview.getAdapter();

        switch (item.getItemId()) {
            case R.id.action_delete: {
                ArrayList<ListItem> checked = adapter.getCheckedItems();
                for (ListItem it: checked) {
                    ((TLSContainerArray)C).removeItem(adapter.getPosition(it));
                    adapter.remove(it);
                }
                Intent returnIntent = new Intent();
                returnIntent.putExtra("content", C.getData());
                returnIntent.putExtra("changed",true);
                setResult(Activity.RESULT_OK,returnIntent);
                return true;
            }
            case R.id.action_select_off: {
                int l = m_listview.getCount();
                for (int i = 0; i < l; i++) {
                   adapter.getItem(i).setChecked(false);
                    View view = m_listview.getChildAt(i);
                    if (view!=null) {
                        CheckBox checkBox = ((CheckBox)view.findViewById(R.id.checkBox));
                        if (checkBox!=null)
                            checkBox.setChecked(false);

                    }
                }
                return true;

            }
            case R.id.action_select_on: {
                int l = m_listview.getCount();
                for (int i = 0; i < l; i++) {
                    adapter.getItem(i).setChecked(true);
                    View view = m_listview.getChildAt(i);
                    if (view!=null) {
                        CheckBox checkBox = ((CheckBox)view.findViewById(R.id.checkBox));
                        if (checkBox!=null)
                            checkBox.setChecked(true);

                    }
                }
                return true;
            }
        }

        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                String result=data.getStringExtra("result");
                ListView m_listview = (ListView) findViewById(R.id.details_list);
                ListItem item = (ListItem) m_listview.getItemAtPosition(changing_now_position);
                if (item.getDescription()!=result) {
                    final ListItemAdapter adapter = ((ListItemAdapter)m_listview.getAdapter());
                    String res = null;
                    String title = item.getTitle();
                    res = C.set(title,result);
                    if (res!=null) {
                        item.setValue(res);
                        item.setDescription(result);
                        adapter.notifyDataSetChanged(changing_now_position);
                    }

                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("content", C.getData());
                    returnIntent.putExtra("changed",true);
                    setResult(Activity.RESULT_OK,returnIntent);
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
                ListView m_listview = (ListView) findViewById(R.id.details_list);
                ListItem item = (ListItem) m_listview.getItemAtPosition(changing_now_position);
                if (item.getDescription()!=result) {
                    final ListItemAdapter adapter = ((ListItemAdapter)m_listview.getAdapter());
                    String res = null;
                    String title = item.getTitle();
                    res = C.set_raw(title, result);
                    if (res!=null) {
                        item.setValue(result);
                        item.setDescription(res);
                        adapter.notifyDataSetChanged(changing_now_position);
                    }

                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("content", C.getData());
                    returnIntent.putExtra("changed",true);
                    setResult(Activity.RESULT_OK,returnIntent);
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
                ListView m_listview = (ListView) findViewById(R.id.details_list);
                ListItem item = (ListItem) m_listview.getItemAtPosition(changing_now_position);
                if (result) {
                    C.set_raw(item.getTitle(), content);
                    final ListItemAdapter adapter = ((ListItemAdapter)m_listview.getAdapter());
                    adapter.updateLengthItem(toByteString(C.getLengthParam().raw_value),
                            String.valueOf(C.getLengthParam().value));


                    adapter.notifyDataSetChanged(changing_now_position);

                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("content", C.getData());
                    returnIntent.putExtra("changed",true);
                    setResult(Activity.RESULT_OK,returnIntent);
                }

                changing_now_position = -1;
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }
}