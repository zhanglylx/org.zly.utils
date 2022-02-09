package org.zly.utils.random.character;

import lombok.Data;
import org.apache.commons.lang3.ArrayUtils;
import org.zly.utils.random.RandomHandler;
import org.zly.utils.random.ZlyRandomCharacterUtils;
import org.zly.utils.random.ZlyRandomNumberUtils;
import org.zly.utils.random.ZlyRandomSetUtils;

@Data
public class SpecialHandler implements RandomHandler<String> {
    public static String CHAR = "`~!@#$%^&*()_-+=/*,.<>][{}|/?;:'\"";
    //    使用换行增加出现几率
    public static String CHAR_TAB = "\n\n ";
    private static char[] CHARS;
    private static char[] CHARS_TAB;
    //    防止出现随机字符最后一位为换行，使用者粘贴时忽略换行
    private static char[] CHARS_MORE;

    static {
        init();
    }

    /**
     * 反斜线很特殊，比如在数据库模糊搜索时会当成转义符，导致搜索不到，所以这里临时通过手工的方案加入反斜线
     */
    public static void addBackSlant() {
        CHAR += "\\";
        init();
    }

    private static void init() {
        CHARS = CHAR.toCharArray();
        CHARS_TAB = CHAR_TAB.toCharArray();
        CHARS_MORE = ArrayUtils.addAll(CHARS, CHARS_TAB);
    }


    @Override
    public String nextRandom(long size, long sizeOfRemainingSpace) {
        if (sizeOfRemainingSpace == 1 || size == sizeOfRemainingSpace) {
            return String.valueOf(CHARS[ZlyRandomNumberUtils.nextInt(0, CHARS.length)]);
        }
//        if (size > 500 && size - sizeOfRemainingSpace > 300) {
//            char[] c = new char[]{CHARS_TAB[ZlyRandomNumberUtils.nextInt(0, CHARS_TAB.length)], CHARS[ZlyRandomNumberUtils.nextInt(0, CHARS.length)]};
//            return String.valueOf(c[ZlyRandomNumberUtils.nextInt(0, c.length)]);
//        }
        return String.valueOf(CHARS_MORE[ZlyRandomNumberUtils.nextInt(0, CHARS_MORE.length)]);
    }

    public static void main(String[] args) {
        System.out.println(ZlyRandomCharacterUtils.nextMixture(100, CharRandomType.SPECIAL));
    }

}
