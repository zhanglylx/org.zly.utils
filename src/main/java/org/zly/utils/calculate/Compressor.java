package org.zly.utils.calculate;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.InflaterInputStream;

/**
 * CND压缩机
 */
public class Compressor {
    private Charset _encoding = Charset.forName("GB18030");

    public byte[] encode(byte[] bytes) throws IOException {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            try (DeflaterOutputStream zip = new DeflaterOutputStream(out)) {
                zip.write(bytes);
            }
            byte[] ret = out.toByteArray();
            return ret;
        }
    }

    public byte[] decode(byte[] bytes) throws IOException {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            try (ByteArrayInputStream in = new ByteArrayInputStream(bytes)) {
                try (InflaterInputStream unzip = new InflaterInputStream(in)) {
                    byte[] buffer = new byte[256];
                    int n;
                    while ((n = unzip.read(buffer)) >= 0) {
                        out.write(buffer, 0, n);
                    }
                    return out.toByteArray();
                }
            }
        }
    }

}

