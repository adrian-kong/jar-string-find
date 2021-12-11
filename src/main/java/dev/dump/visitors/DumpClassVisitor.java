package dev.dump.visitors;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class DumpClassVisitor extends ClassVisitor {

    private String className;

    public DumpClassVisitor(String className) {
        super(Opcodes.ASM5);
        this.className = className;
    }

    @Override
    public MethodVisitor visitMethod(int i, String s, String s1, String s2, String[] strings) {
        return new DumpMethodVisitor(super.visitMethod(i, s, s1, s2, strings), className);
    }
}
