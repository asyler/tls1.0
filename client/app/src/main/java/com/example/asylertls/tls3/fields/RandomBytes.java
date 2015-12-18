package com.example.asylertls.tls3.fields;

import com.example.asylertls.tls3.TLSParameter2;

/**
 * Created by asyler on 17/11/15.
 */
public class RandomBytes extends TLSParameter2 {
    public RandomBytes() {
        super("Random Bytes", 28, "final");
    }
}
