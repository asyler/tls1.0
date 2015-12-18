package com.example.asylertls.tls3;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;

import static com.example.asylertls.tls3.TLSContainer.toByteString;

/**
 * Created by asyler on 17/11/15.
 */
public class TLSParameter2 {
    public String name;
    public Object value;
    public String type;
    public boolean is_dyn = false;
    public byte[] data; // using for fragments
    //public boolean available;
    public int length;
    public byte[] raw_value;
    public ArrayList<Object> possible_values = new ArrayList<>();
    public ArrayList<String> possible_strings = new ArrayList<>();
    public String change_type;

    public TLSParameter2(String type,int length, String change_type) {
        this.type = type;
        this.length = length;
        this.change_type = change_type;
    }

    public TLSParameter2(String type,int length, String change_type, int is_dyn) {
        this.type = type;
        this.length = length;
        this.change_type = change_type;
        this.is_dyn = true;
    }

    public TLSParameter2(String type) {
        // bod one. tree
        this.type = type;
        this.length = -1;
        this.change_type = "tree";
    }

    public void addValue(String string, Object value) {
        possible_strings.add(string);
        possible_values.add(value);
    }

    public Object getValue(String string) {
        for(String possible_string: possible_strings){
            if (string.equals(possible_string))
                return value;
        }
        return null;
    }

    public static int toInt(byte[] arr) {
        int res = 0;
        for (int i=0; i<arr.length; i++) {
            res += arr[i]*(int)Math.pow(256,arr.length-i-1);
        }
        return res;
    }

    public boolean checkValueEqaulity(Object other) {
        if (this.length==1)
            return this.value.equals(other);
        else
            return Arrays.equals((byte[])this.value, (byte[])other);
    }

    public void setNameFromRawValue() {
        if (this.length==1)
            this.value = (int)raw_value[0];
        else
            this.value = raw_value;

        if (change_type.equals("final"))
            this.name = String.valueOf(toInt(raw_value));
        else if (change_type.equals("tree")) {

        }
        else {
            int i = 0;
            for (Object possible_value : possible_values) {
                if (checkValueEqaulity(possible_value)) {
                    this.name = possible_strings.get(i);
                    break;
                }
                i++;
            }
        }
    }

    public void setValueFromName(String name) {
        this.name = name;
        int i = 0;
        for(Object possible_string: possible_strings){
            if (possible_string.equals(name)) {
                this.value = possible_values.get(i);
                setRawValueFromValue();
                break;
            }
            i++;
        }
    }

    public void setRawValueFromValue() {
        if (this.length==1) {
            this.raw_value = new byte[]{((Integer) this.value).byteValue()};
        }
        else if (this.value.getClass().getSimpleName().equals("Integer")) {
            byte[] buf4 = ByteBuffer.allocate(4).putInt((Integer)this.value).array();
            this.raw_value = new byte[2];
            System.arraycopy(buf4, 2, this.raw_value, 0, 2);
        }
        else
            this.raw_value = (byte[]) value;
        data = raw_value;
    }

    public void setRawValueFromValue(int value) {
        this.value = value;
        int len = length;
        //this.length = 1;
        setRawValueFromValue();
        //this.length = len;
        data = new byte[length];
        for (int i = 0; i < length-raw_value.length; i++) {
            data[i] = 0;
        }
        System.arraycopy(raw_value, 0, data, length-raw_value.length, raw_value.length);
        raw_value = data;
    }

    public void setRawValueFromString(String value) {
        String[] tokens = value.split(" ");
        StringBuilder result = new StringBuilder();
        int i = 0;
        for(String token : tokens) {
            if(!token.isEmpty()) {
                i++;
            }
        }
        raw_value = new byte[i];
        i = 0;
        for(String token : tokens) {
            if(!token.isEmpty()) {
                raw_value[i] = (byte)Integer.parseInt(token, 16);
                i++;
            }
        }
        data = raw_value;
    }



    public ArrayList<String> getOptions() {
        return possible_strings;
    }

    public boolean available(String item) {
        return true;
    }

    public String getRawValueStr() {
        if (change_type.equals("tree"))
            return "";
        else
            return toByteString(raw_value);
    }
}
