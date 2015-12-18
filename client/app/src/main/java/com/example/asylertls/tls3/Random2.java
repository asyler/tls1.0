package com.example.asylertls.tls3;

/**
 * Created by asyler on 12/11/15.
 */
import java.nio.ByteBuffer;


public class Random2 {
    public byte[] gmt_unix_time;
    public byte[] random_bytes;
    public byte[] _data;

    public byte[] setTime() {
        long unixTime = System.currentTimeMillis() / 1000L;
        byte[] bytes1 = ByteBuffer.allocate(8).putLong(unixTime).array();
        byte[] bytes = new byte[4];
        System.arraycopy(bytes1,4,bytes,0,4);
        return bytes;
    }

    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len/2];

        for(int i = 0; i < len; i+=2){
            data[i/2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i+1), 16));
        }

        return data;
    }

    public byte[] setRandomBytes() {
        String rand = "f10c7ed2fe6d9aa4546403л3d5304f64c2ca801e553c6bff2dbfb021c";
        //return DatatypeConverter.parseHexBinary(rand);
        return hexStringToByteArray(rand);
    }
    public byte[] toStr() {
        byte[] bytes = new byte[gmt_unix_time.length + random_bytes.length];
        System.arraycopy(gmt_unix_time, 0, bytes, 0, gmt_unix_time.length);
        System.arraycopy(random_bytes, 0, bytes, gmt_unix_time.length, random_bytes.length);
        return bytes;
    }

    public void parse() {
        gmt_unix_time = new byte[4];
        random_bytes = new byte[28];
        System.arraycopy(_data, 0, gmt_unix_time, 0, 4);
        System.arraycopy(_data, 4, random_bytes, 0, 28);
    }
}




