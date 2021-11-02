package com.oribit.classparser.constant;

import static com.oribit.classparser.util.Reader.readShort;

/**
 * CONSTANT_MethodType_info {
 *     u1 tag;
 *     u2 descriptor_index;
 * }
 */
public class ConstantMethodType implements Constant {
    private byte mTag;
    private short mDescriptorIndex;

    public ConstantMethodType(byte tag, short descriptorIndex) {
        mTag = tag;
        mDescriptorIndex = descriptorIndex;
    }

    @Override
    public int size() {
        return 3;
    }

    @Override
    public byte tag() {
        return mTag;
    }

    public static class Parser implements ConstantParser {
        @Override
        public Constant parse(byte[] content, byte tag, int currentOffset) {
            short descriptorIndex = readShort(content, currentOffset);
            return new ConstantMethodType(tag, descriptorIndex);
        }
    }
}
