package org.zly.utils.random.type;

import org.zly.utils.random.ZlyRandom;

import java.util.UUID;

public class ZlyRandomUUID implements ZlyRandom<String> {
    @Override
    public String nextRandom() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
