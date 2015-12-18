package com.example.asylertls.tls3;

import com.example.asylertls.tls3.fields.HandshakeType;
import com.example.asylertls.tls3.fields.OtherData;

import java.nio.ByteBuffer;
import java.util.ArrayList;

import static com.example.asylertls.tls3.TLSParameter2.toInt;

/**
 * Created by asyler on 17/11/15.
 */
public abstract class TLSContainer {
    public byte[] data;
    public ArrayList<TLSParameter2> structure = new ArrayList<TLSParameter2>();;
    public String typename;
    public boolean fragments = false;
    private byte[] fragments_data;
    private int pointer = 0;
    private TLSParameter2 parameter;
    private int dyn_length;
    public boolean allow_delete = false;

    TLSContainer(String typename) {
        this.typename = typename;
        parameter = new TLSParameter2(typename);
    }

    TLSContainer(String typename, int length) {
        this.typename = typename;
        parameter = new TLSParameter2(typename,length,"tree");
    }

    TLSContainer(String typename, int length, int is_dyn) {
        this.typename = typename;
        parameter = new TLSParameter2(typename,length,"tree",1);
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public void parse(byte[] data) {
        this.setData(data);
        this.parse();
    }

    public byte[] getData() {
        build();
        return this.data;
    }

    public TLSParameter2 getLengthParam() {
        for (TLSParameter2 param: structure) {
            if (param.type.equals("Length"))
                return param;
        }
        return null;
    }

    public void updateLength(int old_length, int new_length) {
        TLSParameter2 Length = getLengthParam();
        if (Length!=null) {
            int len = Integer.parseInt(Length.name) + (new_length-old_length);
            Length.setRawValueFromValue(len);
        }
    }

    public void build() {
        int length = 0;
        for (TLSParameter2 param: structure) {
            length += param.data.length;
        }
        data = new byte[length];
        pointer = 0;
        for (TLSParameter2 param: structure) {
            System.arraycopy(param.data, 0, data, pointer, param.data.length);
            pointer += param.data.length;
        }
    }

    public void parseParam(TLSParameter2 param) {
        if (param.length==-1) {
            param.length = dyn_length;
        }
        else if (param.length==-2) {
            param.length = data.length-pointer;
        }
        param.raw_value = new byte[param.length];
        System.arraycopy(data, pointer, param.raw_value, 0, param.length);
        param.data = param.raw_value;
        param.setNameFromRawValue();
        if (param.is_dyn) {
            param.length += toInt(param.raw_value);
            param.raw_value = new byte[param.length];
            System.arraycopy(data, pointer, param.raw_value, 0, param.length);
            param.data = param.raw_value;
        }
        if (param.getClass().getSimpleName().equals("DynLength")) {
            dyn_length = Integer.parseInt(param.name);
        }
        pointer += param.length;
    }

    public void parse() {
        for (TLSParameter2 param: structure) {
            parseParam(param);
        }
        parseFragments();
    }

    public void parseFragments() {
        if (fragments) {
            int length = data.length - pointer;
            //fragments_data = new byte[length];
            //System.arraycopy(data, pointer, fragments_data, 0, length);
            while(pointer<data.length)
                addFragment();
        }
    }

    public void addFragment() {
        if ((data.length-pointer<3) ||
                ((structure.get(0).value==22) && ((data.length-pointer)==48))) {
            TLSParameter2 other = new OtherData();
            structure.add(other);
            parseParam(other);
            return;
        }

        byte[] length_ar = new byte[3];
        System.arraycopy(data, pointer+1, length_ar, 0, 3);
        int fragment_length = toInt(length_ar)+4;
        //fragment_length = data.length-pointer; // test one
        byte fragment_type = data[pointer];
        HandshakeType param = new HandshakeType();
        param.raw_value = new byte[] {fragment_type};
        param.setNameFromRawValue();
        param.change_type = "tree";
        param.data = new byte[fragment_length];
        System.arraycopy(data, pointer, param.data, 0, fragment_length);
        structure.add(param);
        pointer += fragment_length;
    }

    public byte[] getFragment(String name) {
        for (TLSParameter2 param: structure) {
            if (param.change_type.equals("tree") && param.type.equals(name)) {
                return param.data;
            }
        }
        return new byte[0];
    }

    public boolean allowDelete() {
        return allow_delete;
    }

    public ArrayList<String[]> getDict() {
        ArrayList<String[]> dict = new ArrayList<String[]>();
        for (TLSParameter2 param: structure) {
            String[] rec = new String[] {
                    param.type,
                    param.name,
                    param.getRawValueStr(),
                    param.change_type
            };
            dict.add(rec);
        }
        return dict;
    }

    public void addParam(TLSParameter2 param) {
        structure.add(param);
    }

    public void addParam(TLSContainer param) {
        structure.add(param.parameter);
    }

    public TLSParameter2 getParam(String name) {
        for (TLSParameter2 param: structure) {
            if (param.type.equals(name)) {
                return param;
            }
        }
        return null;
    }

    public String set(String param_name, String value) { // Content Type, Handshake
        TLSParameter2 param = getParam(param_name);
        param.setValueFromName(value);
        return toByteString(param.value);
    }

    public String set_raw(String param_name, String value) { // Content Type, Handshake
        TLSParameter2 param = getParam(param_name);
        param.setRawValueFromString(value);
        param.setNameFromRawValue();
        return param.name;
    }

    public String set_raw(String param_name, byte[] value) { // Content Type, Handshake
        TLSParameter2 param = getParam(param_name);
        int old_len = param.data.length;
        param.data = value;
        int new_len = param.data.length;
        updateLength(old_len, new_len);
        return param.name;
    }

   /* Static methods */

    public byte[] toByteArray() {
        byte[] result = new byte[1];
        for (TLSParameter2 param: structure) {

        }
        return result;
    }

    public static String toString(int n) {
        return Character.toString((char)n);
    }

    public static String toString(long n) {
        byte[] bytes = ByteBuffer.allocate(4).putLong(n).array();
        String str = new String(bytes);
        return str;
    }

    public static String toString(int[] n) {
        String s = "";
        for(int i=0; i<n.length; i++) {
            s += Character.toString((char)n[i]);
        }
        return s;
    }

    public static String toByteString(Object n) {
        if (n instanceof Integer) {
            return toByteString((int)n);
        } else if (n instanceof int[]) {
            return toByteString((int[])n);
        } else if (n instanceof byte[]) {
            return toByteString((byte [])n);
        }
        return null;
    }

    public static String byte2str(int n) {
        if (n<0)
            n+=128;
        return String.format("%02X", n);
    }

    public static String toByteString(int n) {
        return byte2str(n);
    }
    public static String toByteString(int[] n) {
        String s = "";
        String c;
        for(int i=0; i<n.length; i++) {
            c = byte2str(n[i]);
            c += " ";
            s += c;
        }
        return s;
    }
    public static String toByteString(byte[] n) {
        String s = "";
        String c;
        for(int i=0; i<n.length; i++) {
            c = byte2str(n[i]);
            c += " ";
            s += c;
        }
        return s;
    }

    public static String toClassName(String type) {
        String[] tokens = type.split(" ");
        StringBuilder result = new StringBuilder();
        for(String token : tokens) {
            if(!token.isEmpty()) {
                result.append(token.substring(0, 1).toUpperCase()).append(token.substring(1).toLowerCase());
            }
        }
        return result.toString();
    }
}
