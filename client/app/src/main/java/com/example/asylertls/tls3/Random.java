package com.example.asylertls.tls3;

import com.example.asylertls.tls3.fields.GMTUnixTime;
import com.example.asylertls.tls3.fields.RandomBytes;

/**
 * Created by asyler on 12/11/15.
 */

public class Random extends TLSContainer {
    Random() {
        super("Random",32);

        addParam(new GMTUnixTime());
        addParam(new RandomBytes());
    }
}

