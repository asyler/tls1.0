package com.example.asylertls.tls3;

/**
 * Created by asyler on 12/11/15.
 */

public class CipherSuitesArray extends TLSContainerArray {
    CipherSuitesArray() {
        super("Cipher Suites Array",-1,2);
        allow_delete = true;

        setArrayItem("Cipher Suite");
    }
}

