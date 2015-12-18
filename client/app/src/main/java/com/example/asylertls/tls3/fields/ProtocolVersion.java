package com.example.asylertls.tls3.fields;

import com.example.asylertls.tls3.TLSParameter2;

/**
 * Created by asyler on 17/11/15.
 */
public class ProtocolVersion extends TLSParameter2 {
    public ProtocolVersion() {
        super("Protocol Version", 2,"changeable");

        addValue("TLS 1.0", new byte[]{3,1});
        addValue("TLS 1.1", new byte[]{3,2});
        addValue("TLS 1.2", new byte[]{3,3});
    }
}
