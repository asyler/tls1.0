package com.example.asylertls.tls3;

import java.util.ArrayList;

/**
 * Created by asyler on 17/11/15.
 */
public abstract class TLSContainer2 {
    private byte[] body;

    public abstract ArrayList<String[]> getDict();
    public static String toByteString(Object n) {
        if (n instanceof Integer) {
            return toByteString((int)n);
        } else if (n instanceof int[]) {
            return toByteString((int[])n);
        }
        return null;
    }
    public static String toByteString(int n) {
        return Integer.toString(n);
    }
    public static String toByteString(int[] n) {
        String s = "";
        String c;
        for(int i=0; i<n.length; i++) {
            c = Integer.toString(n[i]);
            if (c.length()==1)
                c = "0"+c;
            c += " ";
            s += c;
        }
        return s;
    }
    public void parse(byte[] data) {
        return;
    };
    public void parse() {

    };

    public String set(String title, String result) {
        return null;
    }

    public byte[] getBody() {
        return null;
    }

    public String getBodyClassName() {
        return null;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }

    public abstract byte[] toStr();

    public byte[] getData() {
        return new byte[0];
    }
}
