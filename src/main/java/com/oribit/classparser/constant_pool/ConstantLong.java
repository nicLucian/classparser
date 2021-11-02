package com.oribit.classparser.constant_pool;

import static com.oribit.classparser.util.Reader.readInt;

/**
 * CONSTANT_Long_info {
 *     u1 tag;
 *     u4 high_bytes;
 *     u4 low_bytes;
 * }
 */
public class ConstantLong implements Constant {
    private byte mTag;
    private long mValue;

    public ConstantLong(byte tag, long value) {
        mTag = tag;
        mValue = value;
    }

    @Override
    public int size() {
        return 9;
    }

    @Override
    public byte tag() {
        return mTag;
    }

    public long getValue() {
        return mValue;
    }

    public static class Parser implements ConstantParser {
        @Override
        public Constant parse(byte[] content, byte tag, int currentOffset) {
            long highBytes = readInt(content, currentOffset);
            long lowBytes = readInt(content, currentOffset + 4);
            long value = (highBytes << 32) + lowBytes;
            return new ConstantLong(tag, value);
        }
    }
}
