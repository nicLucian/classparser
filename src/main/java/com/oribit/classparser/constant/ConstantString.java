package com.oribit.classparser.constant;

import static com.oribit.classparser.util.Reader.readShort;

public class ConstantString implements Constant {
    private final int mTag;
    private final int mStringIndex;

    public ConstantString(int tag, int stringIndex) {
        mTag = tag;
        mStringIndex = stringIndex;
    }

    @Override
    public int size() {
        return 3;
    }

    @Override
    public int tag() {
        return mTag;
    }

    public int getStringIndex() {
        return mStringIndex;
    }

    public static class StringParser implements ConstantParser {
        @Override
        public Constant parse(byte[] content, int tag, int currentOffset) {
            int stringIndex = readShort(content, currentOffset);
            System.out.println("stringIndex = " + stringIndex);
            return new ConstantString(tag, stringIndex);
        }
    }
}
