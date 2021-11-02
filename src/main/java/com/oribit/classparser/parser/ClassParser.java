package com.oribit.classparser.parser;

import com.oribit.classparser.constant_pool.*;

import static com.oribit.classparser.util.Reader.readInt;
import static com.oribit.classparser.util.Reader.readShort;

public class ClassParser {
    private byte[] mContent;
    private int mCurrentOffset = 0;
    private Constant[] mConstants;

    public ClassParser(byte[] content) {
        mContent = content;
    }

    /**
     * ClassFile {
     * u4             magic;
     * u2             minor_version;
     * u2             major_version;
     * u2             constant_pool_count;
     * cp_info        constant_pool[constant_pool_count-1];
     * u2             access_flags;
     * u2             this_class;
     * u2             super_class;
     * u2             interfaces_count;
     * u2             interfaces[interfaces_count];
     * u2             fields_count;
     * field_info     fields[fields_count];
     * u2             methods_count;
     * method_info    methods[methods_count];
     * u2             attributes_count;
     * attribute_info attributes[attributes_count];
     * }
     */
    public void parse() {
        parseMagic();
        parseVersion();
        parseConstantPool();
        parseAccessFlag();
        parseThisClass();
        parseSuperClass();
        parseInterfaces();
        parseFields();
        //todo
        //parseMethods();
        //parseAttributes();
    }

    private void parseFields() {
        int fieldsOffset = mCurrentOffset;
        short fieldsCount = readShort(mContent, fieldsOffset);
        fieldsOffset += 2;
        for (short i = 0; i < fieldsCount; i++) {
            short accessFlag = readShort(mContent, fieldsOffset);
            short nameIndex = readShort(mContent, fieldsOffset + 2);
            short descriptorIndex = readShort(mContent, fieldsOffset + 4);
            System.out.println(accessFlag + " " + readUtf8(nameIndex) + " " + readUtf8(descriptorIndex));
            short attributeCount = readShort(mContent, fieldsOffset + 6);
            fieldsOffset += 8;
            for (short j = 0; j < attributeCount; j++) {
                short attributeNameIndex = readShort(mContent, fieldsOffset);
                int attributeLength = readInt(mContent, fieldsOffset + 2);
                fieldsOffset += (6 + attributeLength);
            }
        }
        mCurrentOffset = fieldsOffset;
    }

    private String readUtf8(short index) {
        ConstantUtf8 constantUtf8 = (ConstantUtf8) mConstants[index - 1];
        return constantUtf8.getContent();
    }

    private void parseInterfaces() {
        int interfaceOffset = mCurrentOffset;
        short count = readShort(mContent, interfaceOffset);
        interfaceOffset += 2;
        for (short i = 0; i < count; i++) {
            short interfaceClassIndex = readShort(mContent, interfaceOffset);
            ConstantClass constantClass = (ConstantClass) mConstants[interfaceClassIndex - 1];
            ConstantUtf8 interfaceName = (ConstantUtf8) mConstants[constantClass.getNameIndex() - 1];
            interfaceOffset += 2;
        }
        mCurrentOffset = interfaceOffset;
    }

    private void parseSuperClass() {
        int classIndex = readShort(mContent, mCurrentOffset);
        Constant constant = mConstants[classIndex - 1];
        ConstantClass superClass = (ConstantClass) constant;
        ConstantUtf8 superName = (ConstantUtf8) mConstants[superClass.getNameIndex() - 1];
        System.out.println("super = " + superName.getContent());
        mCurrentOffset += 2;
    }

    private void parseAccessFlag() {
        short accessFlag = readShort(mContent, mCurrentOffset);
        mCurrentOffset += 2;
    }

    private void parseThisClass() {
        int classIndex = readShort(mContent, mCurrentOffset);
        Constant constant = mConstants[classIndex - 1];
        ConstantClass thisClass = (ConstantClass) constant;
        ConstantUtf8 thisName = (ConstantUtf8) mConstants[thisClass.getNameIndex() - 1];
        mCurrentOffset += 2;
    }

    private void parseConstantPool() {
        int poolOffset = mCurrentOffset;
        byte[] copiedContent = new byte[mContent.length];
        //Defence code in case of that Parsers will modify this data accidentally
        System.arraycopy(mContent, 0, copiedContent, 0, mContent.length);

        short constantPoolSize = readShort(copiedContent, poolOffset);
        poolOffset += 2;
        mConstants = new Constant[constantPoolSize - 1];
        for (int i = 0; i < constantPoolSize - 1; i++) {
            byte tag = mContent[poolOffset];
            Constant.ConstantParser parser = Constant.ParserFactory.getParser(tag);
            Constant constant = parser.parse(copiedContent, tag, poolOffset + 1);
            poolOffset += constant.size();
            mConstants[i] = constant;
        }
        mCurrentOffset = poolOffset;
    }

    private void parseVersion() {
        short minorVersion = readShort(mContent, mCurrentOffset);
        mCurrentOffset += 2;
        short majorVersion = readShort(mContent, mCurrentOffset);
        mCurrentOffset += 2;
    }

    private void parseMagic() {
        mCurrentOffset += 4;
    }
}
