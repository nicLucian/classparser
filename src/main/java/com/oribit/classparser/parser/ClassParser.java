package com.oribit.classparser.parser;

import com.oribit.classparser.constant.*;

import static com.oribit.classparser.util.Reader.readShort;

public class ClassParser {
    private byte[] mContent;
    private int mCurrentOffset = 0;
    private Constant[] mConstants;

    public ClassParser(byte[] content) {
        mContent = content;
    }

    public void parse() {
        parseMagic();
        parseVersion();
        parseConstantPool();
    }

    private void parseConstantPool() {
        int poolOffset = mCurrentOffset;
        byte[] copiedContent = new byte[mContent.length];
        //Defence code in case of that Parsers will modify this data accidentally
        System.arraycopy(mContent, 0, copiedContent, 0, mContent.length);

        int constantPoolSize = readShort(copiedContent, poolOffset);
        poolOffset += 2;
        mConstants = new Constant[constantPoolSize - 1];
        for (int i = 0; i < constantPoolSize - 1; i++) {
            int tag = mContent[poolOffset] & 0xFF;
            Constant.ConstantParser parser = getConstantParser(tag);
            Constant constant = parser.parse(copiedContent, tag, poolOffset + 1);
            poolOffset += constant.size();
            mConstants[i] = constant;
        }
        mCurrentOffset += poolOffset;
    }

    private Constant.ConstantParser getConstantParser(int tag) {
        switch (tag) {
            case Constant.CONSTANT_Class:
                return new ConstantClass.ClassParser();

            case Constant.CONSTANT_Methodref:
            case Constant.CONSTANT_Fieldref:
            case Constant.CONSTANT_InterfaceMethodref:
                return new ConstantReference.ReferenceParser();

            case Constant.CONSTANT_String:
                return new ConstantString.StringParser();

            case Constant.CONSTANT_Integer:
                return new ConstantInteger.IntegerParser();

            case Constant.CONSTANT_Float:
                return new ConstantFloat.FloatParser();

            case Constant.CONSTANT_Long:
                return new ConstantLong.LongParser();

            default:
                return (content, tag1, currentOffset) -> new Constant() {
                    @Override
                    public int size() {
                        return 0;
                    }

                    @Override
                    public int tag() {
                        return 0;
                    }
                };
        }
    }

    private void parseVersion() {
        int minorVersion = readShort(mContent, mCurrentOffset);
        mCurrentOffset += 2;
        int majorVersion = readShort(mContent, mCurrentOffset);
        mCurrentOffset += 2;
    }

    private void parseMagic() {
        mCurrentOffset += 4;
    }


}
