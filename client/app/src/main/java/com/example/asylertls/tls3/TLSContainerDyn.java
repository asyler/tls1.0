package com.example.asylertls.tls3;

import com.example.asylertls.tls3.fields.DynLength;

/**
 * Created by asyler on 12/11/15.
 */

public class TLSContainerDyn extends TLSContainer {
    TLSContainerDyn(String name, int length) {
        super(name, length, 1);

        addParam(new DynLength(length));
    }
}

