package com.oribit.classparser.constant;

import static com.oribit.classparser.util.Reader.readShort;

/**
 * CONSTANT_InvokeDynamic_info {
 *     u1 tag;
 *     u2 bootstrap_method_attr_index;
 *     u2 name_and_type_index;
 * }
 */
public class ConstantInvokeDynamic implements Constant {
    private byte mTag;
    private short mBootstrapMethodAttrIndex;
    private short mNameAndTypeIndex;

    public ConstantInvokeDynamic(byte tag, short bootstrapMethodAttrIndex, short nameAndTypeIndex) {
        mTag = tag;
        mBootstrapMethodAttrIndex = bootstrapMethodAttrIndex;
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

    public static class Parser implements ConstantParser {
        @Override
        public Constant parse(byte[] content, byte tag, int currentOffset) {
            short bootstrapMethodAttrIndex = readShort(content, currentOffset);
            short nameAndTypeIndex = readShort(content, currentOffset + 2);
            return new ConstantInvokeDynamic(tag, bootstrapMethodAttrIndex, nameAndTypeIndex);
        }
    }
}
