package org.zly.utils.random.character;

import org.zly.utils.random.*;

public enum CharType {
    CHINESE(new ChineseHandler()),
    ENGLISH(new EnglishHandler()),
    NUMBER(new NumberHandler()),
    SPECIAL(new SpecialHandler());
    private final RandomHandler<String> randomHandler;

    CharType(RandomHandler<String> randomHandler) {
        this.randomHandler = randomHandler;
    }

    public RandomHandler<String> getRandomHandler() {
        return randomHandler;
    }

    public String nextRandom() {
        return this.randomHandler.nextRandom();
    }
}
