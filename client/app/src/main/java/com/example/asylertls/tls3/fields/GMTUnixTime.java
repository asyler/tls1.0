package com.example.asylertls.tls3.fields;

import com.example.asylertls.tls3.TLSParameter2;

/**
 * Created by asyler on 17/11/15.
 */
public class GMTUnixTime extends TLSParameter2 {
    public GMTUnixTime() {
        super("GMT Unix Time", 4, "final");
    }
}
