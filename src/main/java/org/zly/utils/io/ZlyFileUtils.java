package org.zly.utils.io;

import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nullable;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;
import java.util.function.Consumer;

import static java.nio.file.FileVisitOption.FOLLOW_LINKS;

public class ZlyFileUtils {

    public static char[] ILLEGAL_NAME = {'/', '\\', ':', '?', '*', '\"', '<', '>', '|'};

    public static String readerFileAll(File file) throws IOException {
        return readerFileAll(file, StandardCharsets.UTF_8);
    }

    public static String readerFileAll(File file, Charset charset) throws IOException {
        Path path = file.toPath();
        //一个文本文件如果已经大于int最大值，这种文件一般来说很少见有可能是log文件
        if (file.length() <= Integer.MAX_VALUE - 8) {
            //使用nio提高读取速度
            try (FileChannel in = FileChannel.open(path, StandardOpenOption.READ)) {
                ByteBuffer byteBuffer = ByteBuffer.allocate((int) in.size());
                in.read(byteBuffer);
                return new String(byteBuffer.array(), charset);
            }
        }
        StringBuilder msg = new StringBuilder();
        readAllLines(path, charset, msg::append);
        return msg.toString();
    }


    public static List<String> readAllLines(Path path, Charset cs) {
        final List<String> lines = new ArrayList<>();
        readAllLines(path, cs, lines::add);
        return lines;
    }

    public static List<String> readAllLines(Path path) {
        return readAllLines(path, StandardCharsets.UTF_8);
    }

    public static List<String> readAllLines(File path) {
        return readAllLines(path.toPath(), StandardCharsets.UTF_8);
    }


    public static void readAllLines(Path path, Charset cs, Consumer<String> consumer) {
        try (BufferedReader reader = Files.newBufferedReader(path, cs)) {
            for (; ; ) {
                String line = reader.readLine();
                if (line == null)
                    break;
                consumer.accept(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Delete the supplied {@link File} - for directories,
     * recursively delete any nested directories or files as well.
     * <p>Note: Like {@link File#delete()}, this method does not throw any
     * exception but rather silently returns {@code false} in case of I/O
     * errors. Consider using {@link #deleteRecursively(Path)} for NIO-style
     * handling of I/O errors, clearly differentiating between non-existence
     * and failure to delete an existing file.
     *
     * @param root the root {@code File} to delete
     * @return {@code true} if the {@code File} was successfully deleted,
     * otherwise {@code false}
     */
    public static boolean deleteRecursively(@Nullable File root) {
        if (root == null) {
            return false;
        }

        try {
            return deleteRecursively(root.toPath());
        } catch (IOException ex) {
            return false;
        }
    }

    /**
     * Delete the supplied {@link File} &mdash; for directories,
     * recursively delete any nested directories or files as well.
     *
     * @param root the root {@code File} to delete
     * @return {@code true} if the {@code File} existed and was deleted,
     * or {@code false} if it did not exist
     * @throws IOException in the case of I/O errors
     * @since 5.0
     */
    public static boolean deleteRecursively(@Nullable Path root) throws IOException {
        if (root == null) {
            return false;
        }
        if (!Files.exists(root)) {
            return false;
        }

        Files.walkFileTree(root, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Files.delete(file);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                Files.delete(dir);
                return FileVisitResult.CONTINUE;
            }
        });
        return true;
    }

    public static void traversalFolderFile(final File file, final Consumer<File> fileConsumer) {
        traversalFolder(file, f -> {
            if (f.isFile()) fileConsumer.accept(f);
        });
    }

    public static void traversalFolderDirectory(final File file, final Consumer<File> fileConsumer) {
        traversalFolder(file, f -> {
            if (f.isDirectory()) fileConsumer.accept(f);
        });
    }

    /**
     * 遍历目录下的文件
     *
     * @param file
     * @param fileConsumer
     */
    public static void traversalFolder(final File file, final Consumer<File> fileConsumer) {
        Objects.requireNonNull(file);
        Objects.requireNonNull(fileConsumer);
        if (!file.exists()) return;
        if (file.isDirectory()) {
            File[] listFiles = file.listFiles();
            if (listFiles != null) {
                for (File f : listFiles) {
                    traversalFolder(f, fileConsumer);
                }
            }
        }
        fileConsumer.accept(file);
    }

//    运行期间会存在系统目录权限问题
    public static void traversalFolderFile(@Nullable Path root, final SimpleFileVisitor<Path> simpleFileVisitor) throws IOException {
        Objects.requireNonNull(root);
        if (!Files.exists(root)) {
            throw new IllegalArgumentException("文件路径不存在:" + root);
        }
        Files.walkFileTree(root, simpleFileVisitor);
    }


    public static abstract class SimpleFileVisitorDefault<T> extends SimpleFileVisitor<T> {
        @Override
        public FileVisitResult visitFileFailed(T file, IOException exc) throws IOException {
            if (exc instanceof AccessDeniedException) return FileVisitResult.CONTINUE;
            return super.visitFileFailed(file, exc);
        }
        @Override
        public abstract FileVisitResult visitFile(T file, BasicFileAttributes attrs);
    }

    public static void main(String[] args) throws IOException {
        traversalFolderFile(new File("").toPath(), new SimpleFileVisitorDefault<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                return FileVisitResult.CONTINUE;
            }
        });
    }

    /**
     * Recursively copy the contents of the {@code src} file/directory
     * to the {@code dest} file/directory.
     *
     * @param src  the source directory
     * @param dest the destination directory
     * @throws IOException in the case of I/O errors
     */
    public static void copyRecursively(File src, File dest) throws IOException {

        copyRecursively(src.toPath(), dest.toPath());
    }

    /**
     * Recursively copy the contents of the {@code src} file/directory
     * to the {@code dest} file/directory.
     *
     * @param src  the source directory
     * @param dest the destination directory
     * @throws IOException in the case of I/O errors
     * @since 5.0
     */
    public static void copyRecursively(Path src, Path dest) throws IOException {
        BasicFileAttributes srcAttr = Files.readAttributes(src, BasicFileAttributes.class);

        if (srcAttr.isDirectory()) {
            Files.walkFileTree(src, EnumSet.of(FOLLOW_LINKS), Integer.MAX_VALUE, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                    Files.createDirectories(dest.resolve(src.relativize(dir)));
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    Files.copy(file, dest.resolve(src.relativize(file)), StandardCopyOption.REPLACE_EXISTING);
                    return FileVisitResult.CONTINUE;
                }
            });
        } else if (srcAttr.isRegularFile()) {
            Files.copy(src, dest);
        } else {
            throw new IllegalArgumentException("Source File must denote a directory or file");
        }
    }


    /**
     * 将字符串路径替换成系统路径
     *
     * @param path
     * @return
     */
    public static String replaceFilePathSymbol(String path) {
        if (path == null) throw new NullPointerException();
        path = path.replace("\\", File.separator);
        path = path.replace("/", File.separator);
        return path;
    }


    /**
     * 检查字符串是否有违法的文件名称
     *
     * @param fileName
     * @param replacement
     * @return
     */
    public static String replaceFileIllegalName(String fileName, String replacement) {
        if (StringUtils.containsAny(fileName, ILLEGAL_NAME)) {
            if (StringUtils.containsAny(replacement, ILLEGAL_NAME))
                throw new IllegalArgumentException("replacement不能为限定字符:" + Arrays.toString(ILLEGAL_NAME));
            for (char c : ILLEGAL_NAME) {
                fileName = fileName.replace(String.valueOf(c), replacement);
            }
        }
        return fileName;
    }


}
