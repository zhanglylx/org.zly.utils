package org.zly.utils.io;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StreamUtils;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Enumeration;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;


/**
 * ZipUtils
 *
 * @author ZENG.XIAO.YAN
 * @version v1.0
 * @date 2017年11月19日 下午7:16:08
 */


public class ZipUtils {

    private static final int BUFFER_SIZE = 2 * 1024;

    /**
     * 压缩成ZIP 方法1
     *
     * @param srcDir           需要压缩文件路径
     * @param outSrc           输入压缩文件路径
     * @param KeepDirStructure 是否保留原来的目录结构,true:保留目录结构;
     *                         <p>
     *                         <p>
     *                         <p>
     *                         <p>
     *                         false:所有文件跑到压缩包根目录下(注意：不保留目录结构可能会出现同名文件,会压缩失败)
     * @throws RuntimeException 压缩失败会抛出运行时异常
     */
    public static boolean toZip(File srcDir, File outSrc, boolean KeepDirStructure)
            throws RuntimeException {
        if (!outSrc.getName().toLowerCase().endsWith(".zip")) outSrc = new File(outSrc.getPath() + ".zip");
        if (!srcDir.exists()) {
            throw new RuntimeException("文件路径不存在:" + srcDir);
        }
        if (outSrc.getParentFile() != null && !outSrc.getParentFile().exists()) {
            if (!outSrc.getParentFile().mkdirs()) {
                throw new RuntimeException("创建文件目录失败:" + outSrc.getParentFile());
            }
        }
        long start = System.currentTimeMillis();
        try (FileOutputStream outputStream = new FileOutputStream(outSrc);
             ZipOutputStream zos = new ZipOutputStream(outputStream)) {
            zipFileOutput(srcDir, zos, srcDir.getName(), KeepDirStructure);
            long end = System.currentTimeMillis();
            System.out.println("压缩完成，耗时：" + (end - start) + " ms");
            return true;
        } catch (Exception e) {
            throw new RuntimeException("zip error from ZipUtils", e);
        }
    }


    public static void zipOutWrite(ZipOutputStream zos, File srcFile, String name) throws IOException {
        try (InputStream inputStream = new FileInputStream(srcFile)) {
            zipOutput(zos, inputStream, name);
        }
    }

    /**
     * 像zip流中输出一个压缩实体，在zip流没有关闭的情况下可以添加多次
     *
     * @param zos         zip的流，这里不负责关闭流
     * @param inputStream 需要添加的流，这里不负责关闭输入流
     * @param name        文件的名称
     * @throws IOException
     */
    public static void zipOutput(ZipOutputStream zos, InputStream inputStream, String name) throws IOException {
        // 向zip输出流中添加一个zip实体，构造器中name为zip实体的文件的名字
        zos.putNextEntry(new ZipEntry(name));
        byte[] buf = new byte[BUFFER_SIZE];
        // copy文件到zip输出流中
        int len;
        while ((len = inputStream.read(buf)) != -1) {
            zos.write(buf, 0, len);
        }
        zos.closeEntry();
    }

    public static void zipFileOutput(File sourceFile, ZipOutputStream zos,
                                     boolean KeepDirStructure) throws Exception {
        zipFileOutput(sourceFile, zos, sourceFile.getName(), KeepDirStructure);
    }

    /**
     * 递归压缩方法
     *
     * @param sourceFile       源文件
     * @param zos              zip输出流
     * @param name             压缩后的名称
     * @param KeepDirStructure 是否保留原来的目录结构,true:保留目录结构;
     *                         false:所有文件跑到压缩包根目录下(注意：不保留目录结构可能会出现同名文件,会压缩失败)
     * @throws Exception
     */
    public static void zipFileOutput(File sourceFile, ZipOutputStream zos, String name,
                                     boolean KeepDirStructure) throws IOException {
        if (sourceFile.isFile()) {
            zipOutWrite(zos, sourceFile, name);
        } else {
            File[] listFiles = sourceFile.listFiles();
            if (listFiles == null || listFiles.length == 0) {
                // 需要保留原来的文件结构时,需要对空文件夹进行处理
                if (KeepDirStructure) {
                    // 空文件夹的处理
                    zos.putNextEntry(new ZipEntry(name + "/"));
                    // 没有文件，不需要文件的copy
                    zos.closeEntry();
                }
            } else {
                for (File file : listFiles) {
                    // 判断是否需要保留原来的文件结构
                    if (KeepDirStructure) {
                        // 注意：file.getName()前面需要带上父文件夹的名字加一斜杠,
                        // 不然最后压缩包中就不能保留原来的文件结构,即：所有文件都跑到压缩包根目录下了
                        zipFileOutput(file, zos, name + "/" + file.getName(), KeepDirStructure);
                    } else {
                        zipFileOutput(file, zos, file.getName(), KeepDirStructure);
                    }
                }
            }
        }
    }


    public static void unzipFile(File src, File target, int mode, Charset charset) throws IOException {
        try (ZipFile zipFile = new ZipFile(src, mode, charset)) {
            Enumeration<? extends ZipEntry> enumeration = zipFile.entries();
            while (enumeration.hasMoreElements()) {
                ZipEntry zipEntry = enumeration.nextElement();
                File file = new File(target + "/" + zipEntry.getName());
                if (!file.getParentFile().exists()) {
                    if (!file.getParentFile().mkdirs())
                        throw new RuntimeException("createDirectory fail:" + file.getParent());
                }
                if (!zipEntry.isDirectory()) {
                    try (InputStream in = zipFile.getInputStream(zipEntry)) {
                        Files.copy(in, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
                    }
                }

            }
        }
    }

    public static void unzip(InputStream src, BiConsumer<ZipEntry, byte[]> consumer) throws IOException {
        try (ZipInputStream zipInputStream = new ZipInputStream(src)) {
            ZipEntry zipEntry;
            while ((zipEntry = zipInputStream.getNextEntry()) != null) {
                if (zipEntry.isDirectory()) {
                    consumer.accept(zipEntry, null);
                } else {
                    consumer.accept(zipEntry, StreamUtils.copyToByteArray(zipInputStream));
                }
            }
        }

    }

}
