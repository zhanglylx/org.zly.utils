package org.zly.utils.random.character;

import org.apache.commons.lang3.ArrayUtils;
import org.zly.utils.random.*;

public enum CharRandomType implements RandomHandler<String> {
//    中文
    CHINESE(new ChineseHandler()),
//    英文
    ENGLISH(new EnglishHandler()),
//    数字
    NUMBER(new NumberHandler()),
//    特殊字符
    SPECIAL(new SpecialHandler()),
//    表情
    EMOJI(new EmojiHandler());
    private static volatile RandomHandler<String>[] RAND_HANDLERS;
    private static final Object lock = new Object();
    private final RandomHandler<String> randomHandler;

    CharRandomType(RandomHandler<String> randomHandler) {
        this.randomHandler = randomHandler;
    }

    public String nextRandom(long size, long sizeOfRemainingSpace) {
        if (sizeOfRemainingSpace < 1) throw new IllegalArgumentException("剩余空间不足");
        if(sizeOfRemainingSpace>size)throw new IllegalArgumentException("剩余空间不能大于总空间大小");
        return this.randomHandler.nextRandom(size, sizeOfRemainingSpace);
    }


    @Override
    public int size() {
        return this.randomHandler.size();
    }


    public static RandomHandler<String>[] transition() {
        if (RAND_HANDLERS == null) {
            synchronized (lock) {
                if (RAND_HANDLERS == null) {
                    RAND_HANDLERS = new RandomHandler[0];
                    for (CharRandomType value : CharRandomType.values()) {
                        RAND_HANDLERS = ArrayUtils.add(RAND_HANDLERS, value.randomHandler);
                    }
                }
            }
        }
        return ArrayUtils.clone(RAND_HANDLERS.clone());
    }

}
