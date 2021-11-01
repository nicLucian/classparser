package com.oribit.classparser.constant;

import static com.oribit.classparser.util.Reader.readInt;

public class ConstantFloat implements Constant {
    private int mTag;
    private float mValue;

    public ConstantFloat(int tag, float value) {
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

    public float getValue() {
        return mValue;
    }

    public static class FloatParser implements ConstantParser {
        @Override
        public Constant parse(byte[] content, int tag, int currentOffset) {
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
