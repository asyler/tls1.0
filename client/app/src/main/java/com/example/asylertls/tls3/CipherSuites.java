package com.example.asylertls.tls3;

/**
 * Created by asyler on 12/11/15.
 */

public class CipherSuites extends TLSContainerDyn {
    CipherSuites() {
        super("Cipher Suites",2);

        addParam(new CipherSuitesArray());
    }
}

