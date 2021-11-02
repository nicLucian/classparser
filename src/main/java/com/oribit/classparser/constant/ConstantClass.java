package com.oribit.classparser.constant;

import static com.oribit.classparser.util.Reader.readShort;

/**
 * CONSTANT_Class_info {
 *     u1 tag;
 *     u2 name_index;
 * }
 */
public class ConstantClass implements Constant {
    private final byte mTag;
    private final short mNameIndex;

    public ConstantClass(byte tag, short nameIndex) {
        mTag = tag;
        mNameIndex = nameIndex;
    }

    @Override
    public int size() {
        return 3;
    }

    @Override
    public byte tag() {
        return mTag;
    }

    public int getNameIndex() {
        return mNameIndex;
    }

    public static class ClassParser implements ConstantParser {
        @Override
        public Constant parse(byte[] content, byte tag, int currentOffset) {
            short nameIndex = readShort(content, currentOffset);
            System.out.println("name index = " + nameIndex);
            return new ConstantClass(tag, nameIndex);
        }
    }
}
