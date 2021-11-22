package org.zly.utils.random.character;

import org.zly.utils.random.RandomHandler;

import java.io.UnsupportedEncodingException;
import java.util.Random;

public class ChineseHandler implements RandomHandler<String> {

    @Override
    public String nextRandom(long size,long  sizeOfRemainingSpace) {
        int hightPos, lowPos; // 定义高低位
        Random random = new Random();
        hightPos = (176 + Math.abs(random.nextInt(39)));// 获取高位值
        lowPos = (161 + Math.abs(random.nextInt(93)));// 获取低位值
        byte[] b = new byte[2];
        b[0] = (new Integer(hightPos).byteValue());
        b[1] = (new Integer(lowPos).byteValue());
        try {
            return new String(b, "GBK");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}
