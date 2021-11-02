package com.oribit.classparser.constant;

import static com.oribit.classparser.util.Reader.readInt;

/**
 * CONSTANT_Float_info {
 *     u1 tag;
 *     u4 bytes;
 * }
 */
public class ConstantFloat implements Constant {
    private byte mTag;
    private float mValue;

    public ConstantFloat(byte tag, float value) {
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

    public float getValue() {
        return mValue;
    }

    public static class Parser implements ConstantParser {
        @Override
        public Constant parse(byte[] content, byte tag, int currentOffset) {
            int bits = readInt(content, currentOffset);
            int s = ((bits >> 31) == 0) ? 1 : -1;
            int e = ((bits >> 23) & 0xff);
            int m = (e == 0) ?
                    (bits & 0x7fffff) << 1 :
                    (bits & 0x7fffff) | 0x800000;
            float result = (float) (s * m * Math.pow(2, Math.E - 150));
            return new ConstantFloat(tag, result);
        }
    }
}
