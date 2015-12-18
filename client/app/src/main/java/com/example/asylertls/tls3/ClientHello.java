package com.example.asylertls.tls3;

import com.example.asylertls.tls3.fields.HandshakeType;
import com.example.asylertls.tls3.fields.Length;
import com.example.asylertls.tls3.fields.OtherData;
import com.example.asylertls.tls3.fields.ProtocolVersion;

/**
 * Created by asyler on 12/11/15.
 */

public class ClientHello extends TLSContainer {
    ClientHello() {
        super("Client Hello");

        addParam(new HandshakeType());
        addParam(new Length(3));
        addParam(new ProtocolVersion());
        addParam(new Random());
        addParam(new SessionId());
        addParam(new CipherSuites());
        addParam(new OtherData());
    }
}

