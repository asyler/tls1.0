package com.example.asylertls.tls3.fields;

import com.example.asylertls.tls3.TLSParameter2;

/**
 * Created by asyler on 17/11/15.
 */
public class Length extends TLSParameter2 {
    public Length(int length) {
        super("Length", length,"final");
    }
}
