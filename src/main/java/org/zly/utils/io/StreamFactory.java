package org.zly.utils.io;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.function.Consumer;

public class StreamFactory {
    public static PrintWriter createPrintWriter(String target, boolean append, boolean autoFlush, Charset cs) {
        try {
            return new PrintWriter(
                    new OutputStreamWriter(
                            new FileOutputStream(target, append), cs)
                    , autoFlush);
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static PrintWriter createPrintWriter(String target, boolean append) {
        return createPrintWriter(target, append, true, StandardCharsets.UTF_8);
    }

    public static void createPrintWriter(String target, boolean append, boolean autoFlush, Charset cs, Consumer<PrintWriter> consumer) {
        try (PrintWriter p = createPrintWriter(target, append, autoFlush, cs)) {
            consumer.accept(p);
        }
    }

    public static void createPrintWriter(String target, boolean append, Consumer<PrintWriter> consumer) {
        try (PrintWriter p = createPrintWriter(target, append)) {
            consumer.accept(p);
        }
    }
}
