package org.zly.utils.io;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class StreamFactory {
    public static PrintWriter createPrintWriter(String name, boolean append, boolean autoFlush, Charset cs) {
        try {
            return new PrintWriter(
                    new OutputStreamWriter(
                            new FileOutputStream(name, append), cs)
                    , autoFlush);
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static PrintWriter createPrintWriter(String name, boolean append) {
        return createPrintWriter(name, append, true, StandardCharsets.UTF_8);
    }
}
