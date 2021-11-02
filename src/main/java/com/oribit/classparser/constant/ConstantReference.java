package com.oribit.classparser.constant;

import static com.oribit.classparser.util.Reader.readShort;

/**
 * CONSTANT_Fieldref_info {
 *     u1 tag;
 *     u2 class_index;
 *     u2 name_and_type_index;
 * }
 *
 * CONSTANT_Methodref_info {
 *     u1 tag;
 *     u2 class_index;
 *     u2 name_and_type_index;
 * }
 *
 * CONSTANT_InterfaceMethodref_info {
 *     u1 tag;
 *     u2 class_index;
 *     u2 name_and_type_index;
 * }
 */
public class ConstantReference implements Constant {
    private final byte mTag;
    private final short mClassIndex;
    private final short mNameAndTypeIndex;

    public ConstantReference(byte tag, short classIndex, short nameAndTypeIndex) {
        mTag = tag;
        mClassIndex = classIndex;
        mNameAndTypeIndex = nameAndTypeIndex;
    }

    @Override
    public int size() {
        return 5;
    }

    @Override
    public byte tag() {
        return mTag;
    }

    public int getClassIndex() {
        return mClassIndex;
    }

    public int getNameAndTypeIndex() {
        return mNameAndTypeIndex;
    }

    public static class Parser implements ConstantParser {
        @Override
        public Constant parse(byte[] content, byte tag, int currentOffset) {
            short classIndex = readShort(content, currentOffset);
            short nameAndTypeIndex = readShort(content, currentOffset + 2);
            return new ConstantReference(tag, classIndex, nameAndTypeIndex);
        }
    }
}
