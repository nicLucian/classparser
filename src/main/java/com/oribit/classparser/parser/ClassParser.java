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
        mCurrentOffset += poolOffset;
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
