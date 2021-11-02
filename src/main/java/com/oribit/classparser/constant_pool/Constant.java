package com.oribit.classparser.constant_pool;

public interface Constant {
    /**
     * Constant pool tags
     * Constant Type	Value
     * CONSTANT_Class	7
     * CONSTANT_Fieldref	9
     * CONSTANT_Methodref	10
     * CONSTANT_InterfaceMethodref	11
     * CONSTANT_String	8
     * CONSTANT_Integer	3
     * CONSTANT_Float	4
     * CONSTANT_Long	5
     * CONSTANT_Double	6
     * CONSTANT_NameAndType	12
     * CONSTANT_Utf8	1
     * CONSTANT_MethodHandle	15
     * CONSTANT_MethodType	16
     * CONSTANT_InvokeDynamic	18
     */
    int CONSTANT_Class = 7;
    int CONSTANT_Fieldref = 9;
    int CONSTANT_Methodref = 10;
    int CONSTANT_InterfaceMethodref = 11;
    int CONSTANT_String = 8;
    int CONSTANT_Integer = 3;
    int CONSTANT_Float = 4;
    int CONSTANT_Long = 5;
    int CONSTANT_Double = 6;
    int CONSTANT_NameAndType = 12;
    int CONSTANT_Utf8 = 1;
    int CONSTANT_MethodHandle = 15;
    int CONSTANT_MethodType = 16;
    int CONSTANT_InvokeDynamic = 18;

    int size();

    byte tag();

    interface ConstantParser {
        Constant parse(byte[] content, byte tag, int currentOffset);
    }

    class ParserFactory {
        private static final ConstantParser DEFAULT_PARSER =
                (content, tag1, currentOffset) -> new Constant() {
                    @Override
                    public int size() {
                        return 0;
                    }

                    @Override
                    public byte tag() {
                        return 0;
                    }
                };

        public static ConstantParser getParser(byte tag) {
            switch (tag) {
                case Constant.CONSTANT_Class:
                    return new ConstantClass.ClassParser();

                case Constant.CONSTANT_Methodref:
                case Constant.CONSTANT_Fieldref:
                case Constant.CONSTANT_InterfaceMethodref:
                    return new ConstantReference.Parser();

                case Constant.CONSTANT_String:
                    return new ConstantString.Parser();

                case Constant.CONSTANT_Integer:
                    return new ConstantInteger.Parser();

                case Constant.CONSTANT_Float:
                    return new ConstantFloat.Parser();

                case Constant.CONSTANT_Long:
                    return new ConstantLong.Parser();

                case Constant.CONSTANT_Double:
                    return new ConstantDouble.Parser();

                case Constant.CONSTANT_NameAndType:
                    return new ConstantNameAndType.Parser();

                case Constant.CONSTANT_Utf8:
                    return new ConstantUtf8.Parser();

                case Constant.CONSTANT_MethodHandle:
                    return new ConstantMethodHandle.Parser();

                case Constant.CONSTANT_MethodType:
                    return new ConstantMethodType.Parser();

                case Constant.CONSTANT_InvokeDynamic:
                    return new ConstantInvokeDynamic.Parser();

                default:
                    return DEFAULT_PARSER;
            }
        }
    }
}
