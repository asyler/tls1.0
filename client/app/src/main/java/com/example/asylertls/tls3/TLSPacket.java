package com.example.asylertls.tls3;

import com.example.asylertls.tls3.fields.ContentType;
import com.example.asylertls.tls3.fields.Length;
import com.example.asylertls.tls3.fields.ProtocolVersion;

/**
 * Created by asyler on 12/11/15.
 */

public class TLSPacket extends TLSContainer {
    TLSPacket() {
        super("Packet");

        addParam(new ContentType());
        addParam(new ProtocolVersion());
        addParam(new Length(2));
        fragments = true;
    }
}

