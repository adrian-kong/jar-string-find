package dev.dump.visitors;

import dev.threat.ThreatHandler;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class DumpMethodVisitor extends MethodVisitor {

    private String className;

    private final ThreatHandler threatHandler = ThreatHandler.getInstance();

    public DumpMethodVisitor(MethodVisitor methodVisitor, String className) {
        super(Opcodes.ASM5, methodVisitor);
        this.className = className;
    }

    /**
     * int, string, double within class
     */
    @Override
    public void visitLdcInsn(Object o) {
        if (o instanceof String str) {
            if (threatHandler.isSuspicious(str)) {
                System.out.printf("[%s] %s\n", className, str);
            }
        }
        super.visitLdcInsn(o);
    }

    /**
     * parameters in methods
     */
    @Override
    public void visitLocalVariable(String s, String s1, String s2, Label label, Label label1, int i) {
//        System.out.printf("%s %s %s %s %s\n", s, s1, s2, label.toString(), label1.toString());
        super.visitLocalVariable(s, s1, s2, label, label1, i);
    }

    @Override
    public void visitMethodInsn(int i, String s, String s1, String s2, boolean b) {
        if (threatHandler.isSuspicious(s)) {
            System.out.printf("[%s] %s %s %s\n", className, s, s1, s2);
        }
        super.visitMethodInsn(i, s, s1, s2, b);
    }
}
