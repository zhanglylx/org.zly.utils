package org.zly.utils.DynamicSplicing;

import org.apache.commons.lang3.StringUtils;


/**
 * @author zly
 * @version 1.0
 * @date 2021/1/26 15:23
 */
@SuppressWarnings("all")
public class StringDynamicSplicingProxy extends DynamicSplicingImpl<String> {
    protected final StringBuilder sb = new StringBuilder();

    @Override
    public String getResult() {
        return sb.toString();
    }

    @Override
    public StringDynamicSplicingProxy join(Object... code) {
        if (code == null) return this;
        if (this.sb.length() != 0) this.sb.append(this.separator());
        this.sb.append(StringUtils.join(code, this.separator()));
        return this;
    }
//
//    @Override
//    public String init() {
//        return this.dynamicSplicing.init();
//    }

    @Override
    public String clear() {
        String code = sb.toString();
        sb.delete(0, sb.length());
        sb.append(this.dynamicSplicing.init());
        return code;
    }

    @Override
    public void insert(int location, String... value) {
        this.sb.insert(location, StringUtils.join(value));
    }

    @Override
    public String remove(int location) {
        String r = this.get(location);
        this.sb.deleteCharAt(location);
        return r;
    }

    @Override
    public String[] remove(int start, int end) {
        String[] r = this.get(start, end);
        this.sb.delete(start, end);
        return r;
    }

    @Override
    public String get(int location) {
        char[] c = new char[1];
        this.sb.getChars(location, location + 1, c, 0);
        return new String(c);
    }

    @Override
    public String[] get(int start, int end) {
        int size = Math.abs(start - end);
        char[] c = new char[size];
        this.sb.getChars(start, end, c, 0);
        String[] strings = new String[size];
        for (int i = 0; i < c.length; i++) {
            strings[i] = String.valueOf(c[i]);
        }
        return strings;
    }

    @Override
    public int indexOf(String t) {
        return this.sb.indexOf(t);
    }

    @Override
    public String separator() {
        return this.dynamicSplicing.separator().toString();
    }

    @Override
    public String toString() {
        return getResult();
    }
}
