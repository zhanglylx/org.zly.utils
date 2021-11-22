package org.zly.utils.random.character;

import org.zly.utils.random.RandomHandler;
import org.zly.utils.random.ZlyRandomNumberUtils;

public class SpecialHandler implements RandomHandler<String> {

    private final static char[] CHARS = ("`~!@#$%^&*()_-+=/*,.<>][{}\\|/?;:'\"").toCharArray();
    //    防止出现随机字符最后一位为换行，使用者粘贴时忽略换行
    private final static char[] CHARS_MORE = ("`~!@#$%^&*()_-+=/*,.<>][{}\\|/?;:'\"\n").toCharArray();

    @Override
    public String nextRandom(long size,long sizeOfRemainingSpace) {
        if (sizeOfRemainingSpace == 1 || size == sizeOfRemainingSpace) {
            return String.valueOf(CHARS[ZlyRandomNumberUtils.nextInt(0, CHARS.length)]);
        }
        return String.valueOf(CHARS_MORE[ZlyRandomNumberUtils.nextInt(0, CHARS_MORE.length)]);
    }
}
