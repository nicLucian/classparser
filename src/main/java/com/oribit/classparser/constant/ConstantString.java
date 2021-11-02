package com.oribit.classparser.constant;

import static com.oribit.classparser.util.Reader.readShort;

/**
 * CONSTANT_String_info {
 *     u1 tag;
 *     u2 string_index;
 * }
 */
public class ConstantString implements Constant {
    private final byte mTag;
    private final short mStringIndex;

    public ConstantString(byte tag, short stringIndex) {
        mTag = tag;
        mStringIndex = stringIndex;
    }

    @Override
    public int size() {
        return 3;
    }

    @Override
    public byte tag() {
        return mTag;
    }

    public int getStringIndex() {
        return mStringIndex;
    }

    public static class Parser implements ConstantParser {
        @Override
        public Constant parse(byte[] content, byte tag, int currentOffset) {
            short stringIndex = readShort(content, currentOffset);
            System.out.println("stringIndex = " + stringIndex);
            return new ConstantString(tag, stringIndex);
        }
    }
}
