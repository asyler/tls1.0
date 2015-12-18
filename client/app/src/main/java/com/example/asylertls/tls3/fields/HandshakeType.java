package com.example.asylertls.tls3.fields;

import com.example.asylertls.tls3.TLSParameter2;

/**
 * Created by asyler on 17/11/15.
 */
public class HandshakeType extends TLSParameter2 {
    public HandshakeType() {
        super("Handshake Type", 1,"changeable");

        addValue("Hello request", 0);
        addValue("Client hello", 1);
        addValue("Server hello", 2);
        addValue("Certificate", 11);
        addValue("Server key exchange", 12);
        addValue("Certificate request", 13);
        addValue("Server hello done", 14);
        addValue("Certificate verify", 15);
        addValue("Client key exchange", 16);
        addValue("Finished", 20);
    }
}
