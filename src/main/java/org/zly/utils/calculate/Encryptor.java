package org.zly.utils.calculate;

import java.io.IOException;
import java.nio.charset.Charset;


/**
 * CND压缩和解密
 */
public class Encryptor {
    private final byte[] _key;
    private final Compressor _compressor;

    public Encryptor(byte[] key) {
        _key = key;
        _compressor = new Compressor();
    }

    private void mix(byte[] input) {
        int n = _key.length;
        int len = input.length;
        for (int i = 0, j = 0; i < len; i++, j++) {
            if (j >= n) {
                j = 0;
            }
            input[i] = (byte) (input[i] ^ _key[j]);
        }
    }

    public byte[] encode(String text, Charset encoding) throws IOException {
        return encode(text.getBytes(encoding));
    }

    public byte[] encode(byte[] text) throws IOException {
        byte[] ret = _compressor.encode(text);
        mix(ret);
        return ret;
    }

    public byte[] decode(byte[] bytes) throws IOException {
        mix(bytes);
        return _compressor.decode(bytes);
    }
}