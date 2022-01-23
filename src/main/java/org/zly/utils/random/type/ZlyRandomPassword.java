package org.zly.utils.random.type;

import org.zly.utils.random.ZlyRandom;
import org.zly.utils.random.ZlyRandomCharacterUtils;
import org.zly.utils.random.character.CharRandomType;

public class ZlyRandomPassword implements ZlyRandom<String> {
    @Override
    public String nextRandom() {
        return nextRandom(6);
    }

    @Override
    public String nextRandom(int number) {
        return ZlyRandomCharacterUtils.nextMixture(number, CharRandomType.NUMBER, CharRandomType.ENGLISH, CharRandomType.SPECIAL);
    }
}
