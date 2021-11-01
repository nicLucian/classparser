package com.oribit.classparser.constant;

import static com.oribit.classparser.util.Reader.readShort;

public class ConstantClass implements Constant {
    private final int mTag;
    private final int mNameIndex;

    public ConstantClass(int tag, int nameIndex) {
        mTag = tag;
        mNameIndex = nameIndex;
    }

    @Override
    public int size() {
        return 3;
    }

    @Override
    public int tag() {
        return mTag;
    }

    public int getNameIndex() {
        return mNameIndex;
    }

    public static class ClassParser implements ConstantParser {
        @Override
        public Constant parse(byte[] content, int tag, int currentOffset) {
            int nameIndex = readShort(content, currentOffset);
            System.out.println("name index = " + nameIndex);
            return new ConstantClass(tag, nameIndex);
        }
    }
}
