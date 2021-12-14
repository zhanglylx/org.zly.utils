package org.zly.utils.random.character;

import lombok.Data;
import org.zly.utils.random.RandomHandler;
import org.zly.utils.random.ZlyRandomNumberUtils;
@Data
public class EnglishHandler implements RandomHandler<String> {

    @Override
    public String nextRandom(long size,long sizeOfRemainingSpace) {
        String str;
        str = (char) (Math.random() * 26 + 'A') + "";
        if (ZlyRandomNumberUtils.nextInt(0, 2) == 0) str = str.toLowerCase();
        return str;
    }
}
