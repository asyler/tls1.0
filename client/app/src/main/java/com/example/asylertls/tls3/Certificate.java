package com.example.asylertls.tls3;

import com.example.asylertls.tls3.fields.OtherData;

/**
 * Created by asyler on 12/11/15.
 */

public class Certificate extends TLSContainer {
    Certificate() {
        super("Certificate");

        addParam(new OtherData());
    }
}

