package com.oribit.classparser.constant;

import static com.oribit.classparser.util.Reader.readInt;

public class ConstantInteger implements Constant {
    private final int mTag;
    private final int mValue;

    public ConstantInteger(int tag, int value) {
        mTag = tag;
        mValue = value;
    }

    @Override
    public int size() {
        return 5;
    }

    @Override
    public int tag() {
        return mTag;
    }

    public int getValue() {
        return mValue;
    }

    public static class IntegerParser implements ConstantParser {
        @Override
        public Constant parse(byte[] content, int tag, int currentOffset) {
            int value = readInt(content, currentOffset);
            return new ConstantInteger(tag, value);
        }
    }
}
