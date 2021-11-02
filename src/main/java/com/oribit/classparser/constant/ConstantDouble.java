package com.oribit.classparser.constant;

import static com.oribit.classparser.util.Reader.readInt;

/**
 * CONSTANT_Double_info {
 *     u1 tag;
 *     u4 high_bytes;
 *     u4 low_bytes;
 * }
 */
public class ConstantDouble implements Constant {
    private byte mTag;
    private double mValue;

    public ConstantDouble(byte tag, double value) {
        mTag = tag;
        mValue = value;
    }

    @Override
    public int size() {
        return 9;
    }

    @Override
    public byte tag() {
        return 0;
    }

    public double getValue() {
        return mValue;
    }

    public static class Parser implements ConstantParser {
        @Override
        public Constant parse(byte[] content, byte tag, int currentOffset) {
            long highBytes = readInt(content, currentOffset);
            long lowBytes = readInt(content, currentOffset + 4);
            long bits = (highBytes << 32) + lowBytes;
            int s = ((bits >> 63) == 0) ? 1 : -1;
            int e = (int) ((bits >> 52) & 0x7ffL);
            long m = (e == 0) ?
                    (bits & 0xfffffffffffffL) << 1 :
                    (bits & 0xfffffffffffffL) | 0x10000000000000L;
            double value = s * m * Math.pow(2, Math.E - 1075);
            return new ConstantDouble(tag, value);
        }
    }
}
