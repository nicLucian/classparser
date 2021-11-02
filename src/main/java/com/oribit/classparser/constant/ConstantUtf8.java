package com.oribit.classparser.constant;

import static com.oribit.classparser.util.Reader.readShort;

/**
 * CONSTANT_Utf8_info {
 *     u1 tag;
 *     u2 length;
 *     u1 bytes[length];
 * }
 */
public class ConstantUtf8 implements Constant {
    private byte mTag;
    private short mLength;
    private byte[] mRawBytes;
    private String mContent;

    public ConstantUtf8(byte tag, short length, byte[] rawBytes) {
        mLength = length;
        mRawBytes = rawBytes;
        mContent = new String(mRawBytes);
    }

    @Override
    public int size() {
        return 3 + mLength;
    }

    @Override
    public byte tag() {
        return mTag;
    }

    public short getLength() {
        return mLength;
    }

    public byte[] getRawBytes() {
        return mRawBytes;
    }

    public String getContent() {
        return mContent;
    }

    public static class Parser implements ConstantParser {
        @Override
        public Constant parse(byte[] content, byte tag, int currentOffset) {
            short length = readShort(content, currentOffset);
            byte[] bytes = new byte[length];
            System.arraycopy(content, currentOffset + 2, bytes, 0, length);
            return new ConstantUtf8(tag, length, bytes);
        }
    }
}
