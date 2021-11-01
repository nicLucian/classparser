package com.oribit.classparser.constant;

import static com.oribit.classparser.util.Reader.readShort;

public class ConstantReference implements Constant {
    private final int mTag;
    private final int mClassIndex;
    private final int mNameAndTypeIndex;

    public ConstantReference(int tag, int classIndex, int nameAndTypeIndex) {
        mTag = tag;
        mClassIndex = classIndex;
        mNameAndTypeIndex = nameAndTypeIndex;
    }

    @Override
    public int size() {
        return 5;
    }

    @Override
    public int tag() {
        return mTag;
    }

    public int getClassIndex() {
        return mClassIndex;
    }

    public int getNameAndTypeIndex() {
        return mNameAndTypeIndex;
    }

    public static class ReferenceParser implements ConstantParser {
        @Override
        public Constant parse(byte[] content, int tag, int currentOffset) {
            int classIndex = readShort(content, currentOffset);
            int nameAndTypeIndex = readShort(content, currentOffset + 2);
            return new ConstantReference(tag, classIndex, nameAndTypeIndex);
        }
    }
}
