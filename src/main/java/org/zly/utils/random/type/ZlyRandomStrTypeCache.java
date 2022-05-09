package org.zly.utils.random.type;

import org.zly.utils.random.ZlyRandom;

import java.util.List;

/**
 * 享元设计模式，缓存字符类型的随机类
 * 委派模式，此枚举类不做任何操作，由具体的类执行操作
 * 策略模式，由用户选择要使用哪一个随机类型并通过此类中介到执行类
 */
public enum ZlyRandomStrTypeCache implements ZlyRandom<String> {
    PASSWORD(new ZlyRandomPassword()),
    IP_ADDRESS(new ZlyRandomIpAddress()),
    MOBILE_PHONE(new ZlyRandomMobilePhone()),
    ONLY_STRING(new ZlyRandomOnlyString()),
    USER_ID(new ZlyRandomUserId()),
    UUID(new ZlyRandomUUID());

    private final ZlyRandom<String> random;

    ZlyRandomStrTypeCache(ZlyRandom<String> random) {
        this.random = random;
    }

    @Override
    public String nextRandom() {
        return this.random.nextRandom();
    }


    @Override
    public List<String> nextRandoms(int number) {
        return this.random.nextRandoms(number);
    }

    public ZlyRandom<String> getRandom() {
        return this.random;
    }

}
