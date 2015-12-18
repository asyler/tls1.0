package com.example.asylertls.tls3.fields;

import com.example.asylertls.tls3.TLSParameter2;

/**
 * Created by asyler on 17/11/15.
 */
public class CipherSuite extends TLSParameter2 {
    public CipherSuite() {
        super("Cipher Suite", 2, "changeable");

        addValue("TLS_NULL_WITH_NULL_NULL", new byte[]{0, 0});
        addValue("TLS_RSA_WITH_NULL_MD5", new byte[] {0,1});
        addValue("TLS_RSA_WITH_NULL_SHA", new byte[] {0,2});
        addValue("TLS_RSA_EXPORT_WITH_RC4_40_MD5", new byte[] {0,3});
        addValue("TLS_RSA_WITH_RC4_128_MD5", new byte[] {0,4});
        addValue("TLS_RSA_WITH_RC4_128_SHA", new byte[] {0,5});
        addValue("TLS_RSA_EXPORT_WITH_RC2_CBC_40_MD5", new byte[] {0,6});
        addValue("TLS_RSA_WITH_IDEA_CBC_SHA", new byte[] {0,7});
        addValue("TLS_RSA_EXPORT_WITH_DES40_CBC_SHA", new byte[] {0,8});
        addValue("TLS_RSA_WITH_DES_CBC_SHA", new byte[] {0,9});
        addValue("TLS_RSA_WITH_3DES_EDE_CBC_SHA", new byte[] {0,10});
        addValue("TLS_DH_DSS_EXPORT_WITH_DES40_CBC_SHA", new byte[] {0,11});
        addValue("TLS_DH_DSS_WITH_DES_CBC_SHA", new byte[] {0,12});
        addValue("TLS_DH_DSS_WITH_3DES_EDE_CBC_SHA", new byte[] {0,13});
        addValue("TLS_DH_RSA_EXPORT_WITH_DES40_CBC_SHA", new byte[] {0,14});
        addValue("TLS_DH_RSA_WITH_DES_CBC_SHA", new byte[] {0,15});
        addValue("TLS_DH_RSA_WITH_3DES_EDE_CBC_SHA", new byte[] {0,16});
        addValue("TLS_DHE_DSS_EXPORT_WITH_DES40_CBC_SHA", new byte[] {0,17});
        addValue("TLS_DHE_DSS_WITH_DES_CBC_SHA", new byte[] {0,18});
        addValue("TLS_DHE_DSS_WITH_3DES_EDE_CBC_SHA", new byte[] {0,19});
        addValue("TLS_DHE_RSA_EXPORT_WITH_DES40_CBC_SHA", new byte[] {0,20});
        addValue("TLS_DHE_RSA_WITH_DES_CBC_SHA", new byte[] {0,21});
        addValue("TLS_DHE_RSA_WITH_3DES_EDE_CBC_SHA", new byte[] {0,22});
        addValue("TLS_DH_anon_EXPORT_WITH_RC4_40_MD5", new byte[] {0,23});
        addValue("TLS_DH_anon_WITH_RC4_128_MD5", new byte[] {0,24});
        addValue("TLS_DH_anon_EXPORT_WITH_DES40_CBC_SHA", new byte[] {0,25});
        addValue("TLS_DH_anon_WITH_DES_CBC_SHA", new byte[] {0,26});
        addValue("TLS_DH_anon_WITH_3DES_EDE_CBC_SHA", new byte[] {0,27});

        addValue("TLS_RSA_WITH_AES_128_CBC_SHA", new byte[] {0,47});
        addValue("TLS_DH_DSS_WITH_AES_128_CBC_SHA", new byte[] {0,48});
        addValue("TLS_DH_RSA_WITH_AES_128_CBC_SHA", new byte[] {0,49});
        addValue("TLS_DHE_DSS_WITH_AES_128_CBC_SHA", new byte[] {0,50});
        addValue("TLS_DHE_RSA_WITH_AES_128_CBC_SHA", new byte[] {0,51});
        addValue("TLS_DH_anon_WITH_AES_128_CBC_SHA", new byte[] {0,52});
        addValue("TLS_RSA_WITH_AES_256_CBC_SHA", new byte[] {0,53});
        addValue("TLS_DH_DSS_WITH_AES_256_CBC_SHA", new byte[] {0,54});
        addValue("TLS_DH_RSA_WITH_AES_256_CBC_SHA", new byte[] {0,55});
        addValue("TLS_DHE_DSS_WITH_AES_256_CBC_SHA", new byte[] {0,56});
        addValue("TLS_DHE_RSA_WITH_AES_256_CBC_SHA", new byte[] {0,57});
        addValue("TLS_DH_anon_WITH_AES_256_CBC_SHA", new byte[] {0,58});
    }
}
