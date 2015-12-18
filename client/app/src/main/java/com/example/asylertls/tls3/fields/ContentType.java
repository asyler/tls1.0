package com.example.asylertls.tls3.fields;

import com.example.asylertls.tls3.TLSParameter2;

/**
 * Created by asyler on 17/11/15.
 */
public class ContentType extends TLSParameter2 {
    public ContentType() {
        super("Content Type", 1,"changeable");

        addValue("Change cipher spec", 20);
        addValue("Alert", 21);
        addValue("Handshake", 22);
        addValue("Application data", 23);

        /*addValue("Hello request", 0);
        addValue("Client hello", 1);
        addValue("Server hello", 2);
        addValue("Certificate", 11);
        addValue("Server key exchange", 12);
        addValue("Certificate request", 13);
        addValue("Server hello done", 14);
        addValue("Certificate verify", 15);
        addValue("Client key exchange", 16);
        addValue("Finished", 20);*/
    }
}
