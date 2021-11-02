package com.oribit.classparser.constant;

import static com.oribit.classparser.util.Reader.readInt;

/**
 * CONSTANT_Integer_info {
 *     u1 tag;
 *     u4 bytes;
 * }
 */
public class ConstantInteger implements Constant {
    private final byte mTag;
    private final int mValue;

    public ConstantInteger(byte tag, int value) {
        mTag = tag;
        mValue = value;
    }

    @Override
    public int size() {
        return 5;
    }

    @Override
    public byte tag() {
        return mTag;
    }

    public int getValue() {
        return mValue;
    }

    public static class Parser implements ConstantParser {
        @Override
        public Constant parse(byte[] content, byte tag, int currentOffset) {
            int value = readInt(content, currentOffset);
            return new ConstantInteger(tag, value);
        }
    }
}
