package com.example.asylertls.tls3;

import java.util.ArrayList;

import static com.example.asylertls.tls3.TLSContainer2.toByteString;

/**
 * Created by asyler on 17/11/15.
 */
public class TLSParameter {
    public static final ArrayList<TLSParameter2> values;
    static
    {
        values = new ArrayList<TLSParameter2>();

        /*values.add(new TLSParameter2("Handshake Type","Hello request",0,false));
        values.add(new TLSParameter2("Handshake Type", "Client hello", 1,true));
        values.add(new TLSParameter2("Handshake Type", "Server hello", 2,true));
        values.add(new TLSParameter2("Handshake Type", "Certificate", 11,false));
        values.add(new TLSParameter2("Handshake Type", "Server key exchange", 12,false));
        values.add(new TLSParameter2("Handshake Type", "Certificate request", 13,false));
        values.add(new TLSParameter2("Handshake Type", "Server hello done", 14,false));
        values.add(new TLSParameter2("Handshake Type", "Certificate verify", 15,false));
        values.add(new TLSParameter2("Handshake Type", "Client key exchange", 16,false));
        values.add(new TLSParameter2("Handshake Type","Finished", 20,false));

        values.add(new TLSParameter2("Content Type","Change cipher spec", 20,true));
        values.add(new TLSParameter2("Content Type", "Alert", 21,true));
        values.add(new TLSParameter2("Content Type", "Handshake", 22,true));
        values.add(new TLSParameter2("Content Type", "Application data", 23,true));

        values.add(new TLSParameter2("Protocol version","TLS 1.0", new int[]{3,1},true));
        values.add(new TLSParameter2("Protocol version","TLS 1.1", new int[]{3,2},true));
        values.add(new TLSParameter2("Protocol version","TLS 1.2", new int[]{3,3},true));*/
    }

    public static Object getValue(String type, String name) {
        for(TLSParameter2 param: values){
            if (param.type.equals(type) && param.name.equals(name))
                return param.value;
        }
        return null;
    }

    public static String getName(String type, int value) {
        for(TLSParameter2 param: values){
            if (param.type.equals(type) && param.value==value)
                return param.name;
        }
        return "Unknown";
    }

    public static String getRawValue(String type, String name) {
        for(TLSParameter2 param: values){
            if (param.type.equals(type) && param.name.equals(name))
                return toByteString(param.value);
        }
        return null;
    }

    public static String[] getAllForType(String type) {
        ArrayList<String> r = new ArrayList<>();
        for(TLSParameter2 param: values){
            if (param.type.equals(type))
                r.add(param.name);
        }
        return r.toArray(new String[r.size()]);
    }

    public static boolean available(String type, String name) {
        for(TLSParameter2 param: values){
            if (param.type.equals(type) && param.name.equals(name))
                return false;
                //return (param.available);
        }
        return false;
    }
}
