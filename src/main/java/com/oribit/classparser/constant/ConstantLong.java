package com.oribit.classparser.constant;

import static com.oribit.classparser.util.Reader.readInt;

public class ConstantLong implements Constant {
    private int mTag;
    private long mValue;

    public ConstantLong(int tag, long value) {
        mTag = tag;
        mValue = value;
    }

    @Override
    public int size() {
        return 9;
    }

    @Override
    public int tag() {
        return mTag;
    }

    public long getValue() {
        return mValue;
    }

    public static class LongParser implements ConstantParser {
        @Override
        public Constant parse(byte[] content, int tag, int currentOffset) {
            long highBytes = readInt(content, currentOffset);
            long lowBytes = readInt(content, currentOffset + 4);
            long value = (highBytes << 32) + lowBytes;
            return new ConstantLong(tag, value);
        }
    }
}
