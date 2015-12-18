package com.example.asylertls.tls3;

import com.example.asylertls.tls3.fields.SessionIDParam;

/**
 * Created by asyler on 12/11/15.
 */

public class SessionId extends TLSContainerDyn {
    SessionId() {
        super("Session ID",1);

        addParam(new SessionIDParam());
    }
}

