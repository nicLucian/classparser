package com.oribit.classparser.constant;

import static com.oribit.classparser.util.Reader.readShort;

/**
 * CONSTANT_MethodHandle_info {
 *     u1 tag;
 *     u1 reference_kind;
 *     u2 reference_index;
 * }
 */
public class ConstantMethodHandle implements Constant {
    private byte mTag;
    private byte mReferenceKind;
    private short mReferenceIndex;

    public ConstantMethodHandle(byte tag, byte referenceKind, short referenceIndex) {
        mTag = tag;
        mReferenceKind = referenceKind;
        mReferenceIndex = referenceIndex;
    }

    @Override
    public int size() {
        return 4;
    }

    @Override
    public byte tag() {
        return mTag;
    }

    public byte getReferenceKind() {
        return mReferenceKind;
    }

    public short getReferenceIndex() {
        return mReferenceIndex;
    }

    public static class Parser implements ConstantParser {
        @Override
        public Constant parse(byte[] content, byte tag, int currentOffset) {
            byte referenceKind = content[currentOffset];
            short referenceIndex = readShort(content, currentOffset + 1);
            return new ConstantMethodHandle(tag, referenceKind, referenceIndex);
        }
    }
}
