package org.zly.utils.random.character;

import org.apache.commons.lang3.ArrayUtils;
import org.zly.utils.random.*;

public enum CharRandomType implements RandomHandler<String> {
    CHINESE(new ChineseHandler()),
    ENGLISH(new EnglishHandler()),
    NUMBER(new NumberHandler()),
    SPECIAL(new SpecialHandler()),
    EMOJI(new EmojiHandler());
    private static volatile RandomHandler<String>[] RAND_HANDLERS;
    private static final Object lock = new Object();
    private final RandomHandler<String> randomHandler;

    CharRandomType(RandomHandler<String> randomHandler) {
        this.randomHandler = randomHandler;
    }

    public String nextRandom() {
        return this.randomHandler.nextRandom();
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
