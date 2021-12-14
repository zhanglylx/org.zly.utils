package org.zly.utils.random.character;

import lombok.Data;
import org.apache.commons.lang3.ArrayUtils;
import org.zly.utils.random.RandomHandler;
import org.zly.utils.random.ZlyRandomSetUtils;

import java.util.ArrayList;
import java.util.List;
@Data
public class EmojiHandler implements RandomHandler<String> {
    private static final String[] EMOJI_LISTS = new String[6];

    static {
        EMOJI_LISTS[0] = ("\uD83D\uDE01");
        EMOJI_LISTS[1] = ("\uD83D\uDE04");
        EMOJI_LISTS[2] = ("\uD83D\uDE02");
        EMOJI_LISTS[3] = ("\uD83D\uDE01");
        EMOJI_LISTS[4] = ("\uD83D\uDE04");
        EMOJI_LISTS[5] = ("\uD83D\uDE02");
    }

    public static String[] getEmojiLists() {
        return EMOJI_LISTS.clone();
    }

    /**
     * 使用者注意，这里返回的是2个字符长度
     *
     * @return
     */
    @Override
    public String nextRandom(long size,long  sizeOfRemainingSpace) {
        return ZlyRandomSetUtils.nextValue(EMOJI_LISTS);
    }

    @Override
    public int size() {
        return 2;
    }
}
