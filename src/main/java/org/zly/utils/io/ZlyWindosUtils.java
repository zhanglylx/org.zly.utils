package org.zly.utils.io;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;

import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class ZlyWindosUtils {

    public static List<String> dosExecute(String code) throws IOException, InterruptedException {
        return dosExecute(code, false, true, true);
    }

    public static List<String> dosExecute(String code, boolean isWait, boolean isInputStream, boolean isErrorStream) throws IOException, InterruptedException {
        List<String> list = new ArrayList<>();
        Process pro = null;
        BufferedReader br = null;
        try {
            pro = Runtime.getRuntime().exec(code);
            if (isWait) pro.waitFor();
            if (isInputStream) {
                br = new BufferedReader(new InputStreamReader(pro.getInputStream(), StandardCharsets.UTF_8));
                list.addAll(getBufferedReader(br));
            }
            if (isErrorStream) {
                //获取错误流
                br = new BufferedReader(new InputStreamReader(pro.getErrorStream(), StandardCharsets.UTF_8));
                list.addAll(getBufferedReader(br));
            }
        } finally {
            close(br, pro);
        }
        return list;
    }

    /**
     * 这个方法暂时应该是不可用，使用这个方法获取下的流数组想转化成对应的文件，但是不能转化成功
     *
     * @param code
     * @return
     */
    @SneakyThrows
    @Deprecated
    public static byte[] dosExecuteByte(String code) {
        Process pro = null;
        InputStream inputStream = null;
        byte[] bytes = new byte[0];
        try {
            pro = Runtime.getRuntime().exec(code);
            inputStream = pro.getInputStream();
            while (true) {
                byte b = (byte) inputStream.read();
                if (b == -1) break;
                bytes = ArrayUtils.add(bytes, b);
            }
            return bytes;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) inputStream.close();
            if (pro != null) pro.destroy();
        }
        return null;
    }


    /**
     * 读取adb缓冲流
     *
     * @return
     */
    public static List<String> getBufferedReader(BufferedReader br) throws IOException {
        String msg;
        List<String> list = new ArrayList<>();
        while ((msg = br.readLine()) != null) {
            list.add(msg);
        }
        return list;
    }

    public static void close(BufferedReader bufferedReader, Process process) {
        if (bufferedReader != null) {
            try {
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (process != null) process.destroy();
    }

    /**
     * 复制文件
     */
    public static boolean copyFile(File src, File tar) {
        try (FileChannel inFileChannel = FileChannel.open(Paths.get(src.getPath()), StandardOpenOption.READ);
             FileChannel outFileChannel = FileChannel.open(tar.toPath(), StandardOpenOption.WRITE, StandardOpenOption.CREATE, StandardOpenOption.READ)) {
            zeroCopy(inFileChannel, outFileChannel);
            return true;
//            MappedByteBuffer 只有在GC回收时才会被释放资源,
//            MappedByteBuffer inMappedByteBuffer = inFileChannel.map(FileChannel.MapMode.READ_ONLY, 0, inFileChannel.size());
//            MappedByteBuffer outMappedByteBuffer = outFileChannel.map(FileChannel.MapMode.READ_WRITE, 0, inFileChannel.size());
//            outMappedByteBuffer.put(inMappedByteBuffer);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void zeroCopy(FileChannel fileChannel, WritableByteChannel target) throws IOException {
        long size = fileChannel.size();
        long position = 0;
        while (size > 0) {
            long tf = fileChannel.transferTo(position, size, target);
            if (tf > 0) {
                position += tf;
                size -= tf;
            }
        }
    }


    /**
     * 获取指定进程的pid
     *
     * @param process
     * @return -1为未获取
     */
    public static int[] getProcessPID(String process) {
        if (process == null) throw new IllegalArgumentException("process为空");
        List<String> processArrays = new ArrayList<>();
        try {
            processArrays = dosExecute("tasklist");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        int [] getPID=new int[0];
        for (String s : processArrays) {
            if (s.startsWith(process)) {
                String str;
                try {
                    if(!s.contains("Console"))continue;
                    str = s.substring(0, s.indexOf("Console")).trim();
                    str = str.substring(str.lastIndexOf(" "), str.length()).trim();
                    if (str.matches("\\d+")){
                        getPID = Arrays.copyOf(getPID,getPID.length+1);
                        getPID[getPID.length-1] =Integer.parseInt(str);
                    }else{
                        throw new RuntimeException("获取进行pid发生错误，请务必联系管理员，谢谢");

                    }
                } catch (StringIndexOutOfBoundsException e) {
                    e.printStackTrace();
                    throw new RuntimeException("截取pid错误:"+s+"   "+e.toString());
                }

            }
        }
        return getPID;
    }

    /**
     * 关闭指定的进程
     *
     * @param process
     * @return
     */
    public static boolean closeProcess(String process) {
        if (process == null) throw new IllegalArgumentException("process为空");
        int[] closePID = getProcessPID(process);
        if (closePID.length == 0) return true;
        try {
            for (int pid : closePID) {
                //taskkill /im 通过名称关闭  /f
                for (String s : dosExecute("taskkill /pid " + pid + "  /f")) {
                    if (s.contains("错误:")) return false;

                }


            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return false;
        }
        if (getProcessPID(process).length != 0) {
            return false;
        } else {
            return true;
        }


    }

}
