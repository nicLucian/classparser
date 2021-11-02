package com.oribit.classparser.constant_pool;

import static com.oribit.classparser.util.Reader.readShort;

/**
 * CONSTANT_NameAndType_info {
 *     u1 tag;
 *     u2 name_index;
 *     u2 descriptor_index;
 * }
 */
public class ConstantNameAndType implements Constant {
    private byte mTag;
    private short mNameIndex;
    private short mDescriptorIndex;

    public ConstantNameAndType(byte tag, short nameIndex, short descriptorIndex) {
        mTag = tag;
        mNameIndex = nameIndex;
        mDescriptorIndex = descriptorIndex;
    }

    @Override
    public int size() {
        return 5;
    }

    @Override
    public byte tag() {
        return mTag;
    }

    public int getNameIndex() {
        return mNameIndex;
    }

    public int getDescriptorIndex() {
        return mDescriptorIndex;
    }

    public static class Parser implements ConstantParser {
        @Override
        public Constant parse(byte[] content, byte tag, int currentOffset) {
            short nameIndex = readShort(content, currentOffset);
            short descriptorIndex = readShort(content, currentOffset + 2);
            return new ConstantNameAndType(tag, nameIndex, descriptorIndex);
        }
    }
}
