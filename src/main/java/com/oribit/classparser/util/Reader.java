package com.oribit.classparser.util;

public class Reader {
    private Reader() {

    }

    public static int readInt(byte[] content, int offset) {
        return (short) ((content[offset] & 0xFF << 24) |
                (content[offset + 1] & 0xFF << 16) |
                (content[offset + 2] & 0xFF << 8) |
                (content[offset + 3] & 0xFF));
    }

    public static short readShort(byte[] content, int offset) {
        return (short) ((content[offset] & 0xFF << 8) | (content[offset + 1] & 0xFF));
    }
}
