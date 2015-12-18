package com.example.asylertls.tls3;

/**
 * Created by asyler on 17/11/15.
 */
public abstract class TLSParameter2old {
    public String name;
    public Object value;
    public String type;
    public boolean available;
    public int length;
    public byte[] raw_value;

    public TLSParameter2old(String type, int length) {
        this.type = type;
        this.length = length;
    }

    public TLSParameter2old(String type, String name, Object value, boolean available) {
        this.name = name;
        this.value = value;
        this.type = type;
        this.available = available;
    }

    public abstract Object getValue();
}
