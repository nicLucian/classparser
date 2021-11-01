package com.oribit.classparser;

import com.oribit.classparser.parser.ClassParser;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.file.Files;

public class Main {
    public static void main(String[] args) {
        File classFile = new File(Main.class.getClassLoader().getResource("classes/Main.class").getFile());
        ClassParser parser = new ClassParser(parseFile(classFile));
        parser.parse();
    }

    private static byte[] parseFile(File targetFile) {
        try {
            return Files.readAllBytes(targetFile.toPath());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new byte[]{};
    }
}
