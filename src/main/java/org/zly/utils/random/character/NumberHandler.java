package org.zly.utils.random.character;

import lombok.Data;
import org.zly.utils.random.RandomHandler;
import org.zly.utils.random.ZlyRandomNumberUtils;
@Data
public class NumberHandler implements RandomHandler<String> {

    @Override
    public String nextRandom(long size,long sizeOfRemainingSpace) {
        return String.valueOf(ZlyRandomNumberUtils.nextInt(10));
    }
}
