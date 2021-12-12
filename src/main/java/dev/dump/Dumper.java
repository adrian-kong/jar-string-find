package dev.dump;

import dev.dump.visitors.DumpClassVisitor;
import org.objectweb.asm.ClassReader;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

public class Dumper {

    public void read(File file) throws IOException {
        try (JarInputStream jis = new JarInputStream(new FileInputStream(file))) {
            JarEntry entry;
            while ((entry = jis.getNextJarEntry()) != null) {
                try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
                    bos.writeBytes(jis.readAllBytes());
                    if (entry.getName().endsWith(".class")) {

                        ClassReader reader = new ClassReader(bos.toByteArray());
                        reader.accept(new DumpClassVisitor(entry.getName()), ClassReader.EXPAND_FRAMES);
                    }
                }
            }
        }
    }
}
