package com.example.asylertls.tls3;

/**
 * Created by asyler on 15/11/15.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;

public class ListItemAdapter extends ArrayAdapter<ListItem> {

    private final Context context;
    private final ArrayList<ListItem> itemsArrayList;
    private int layout;

    private ArrayList<Integer> changed_positions = new ArrayList<Integer>();
    private TLSPacket packet;

    public ListItemAdapter(Context context, ArrayList<ListItem> itemsArrayList) {

        super(context, R.layout.row, itemsArrayList);
        layout = R.layout.row;

        this.context = context;
        this.itemsArrayList = itemsArrayList;
    }

    public ListItemAdapter(Context context, ArrayList<ListItem> itemsArrayList, int allow_delete) {

        super(context, R.layout.row2, itemsArrayList);
        layout = R.layout.row2;

        this.context = context;
        this.itemsArrayList = itemsArrayList;
    }

    public ArrayList<ListItem> getItems() {
        return itemsArrayList;
    }

    public ArrayList<ListItem> getCheckedItems() {
        ArrayList<ListItem> checked = new ArrayList<ListItem>();
        for (ListItem item: itemsArrayList) {
            if (item.isChecked())
                checked.add(item);
        }
        return checked;
    }

    public void notifyDataSetChanged(int position) {
        changed_positions.add(position);
        this.notifyDataSetChanged();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        // 1. Create inflater
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // 2. Get rowView from inflater
        View rowView = inflater.inflate(this.layout, parent, false);

        // 3. Get the two text view from the rowView
        TextView labelView = (TextView) rowView.findViewById(R.id.label);
        TextView valueView = (TextView) rowView.findViewById(R.id.value);
        TextView rawValueView = (TextView) rowView.findViewById(R.id.raw_value);
        CheckBox checkBox = (CheckBox) rowView.findViewById(R.id.checkBox);
        if (checkBox!=null) {
            checkBox.setChecked(itemsArrayList.get(position).isChecked());
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    itemsArrayList.get(position).setChecked(isChecked);
                }
            });
        }

        // 4. Set the text for textView
        labelView.setText(itemsArrayList.get(position).getTitle());
        valueView.setText(itemsArrayList.get(position).getDescription());
        rawValueView.setText(itemsArrayList.get(position).getValue());

        if (changed_positions.contains(position))
            rowView.setBackgroundColor(context.getResources().getColor(R.color.changed));

        // 5. retrn rowView
        return rowView;
    }

    public void setPacket(TLSPacket packet) {
        this.packet = packet;
    }

    public TLSPacket getPacket() {
        return packet;
    }

    public void updateLengthItem(String value, String description) {
        ListItem LengthItem = null;
        for (ListItem item: itemsArrayList) {
            if (item.getTitle()=="Length") {
                LengthItem = item;
                break;
            }
        }
        if (LengthItem!=null) {
            LengthItem.setValue(value);
            LengthItem.setDescription(description);
            notifyDataSetChanged(getPosition(LengthItem));
        }
    }
}