package io.ebean.enhance.entity;

import io.ebean.enhance.asm.ClassVisitor;
import io.ebean.enhance.asm.Label;
import io.ebean.enhance.asm.MethodVisitor;
import io.ebean.enhance.common.ClassMeta;
import io.ebean.enhance.common.EnhanceConstants;

import static io.ebean.enhance.asm.Opcodes.ACC_PROTECTED;
import static io.ebean.enhance.asm.Opcodes.ACC_PUBLIC;
import static io.ebean.enhance.asm.Opcodes.ACONST_NULL;
import static io.ebean.enhance.asm.Opcodes.ALOAD;
import static io.ebean.enhance.asm.Opcodes.ARETURN;
import static io.ebean.enhance.asm.Opcodes.DUP;
import static io.ebean.enhance.asm.Opcodes.INVOKESPECIAL;
import static io.ebean.enhance.asm.Opcodes.NEW;
import static io.ebean.enhance.asm.Opcodes.PUTFIELD;
import static io.ebean.enhance.asm.Opcodes.RETURN;
import static io.ebean.enhance.common.EnhanceConstants.C_ENTITYBEAN;
import static io.ebean.enhance.common.EnhanceConstants.C_INTERCEPT_I;
import static io.ebean.enhance.common.EnhanceConstants.C_INTERCEPT_RO;
import static io.ebean.enhance.common.EnhanceConstants.INIT;
import static io.ebean.enhance.common.EnhanceConstants.INTERCEPT_FIELD;

/**
 * Add support for the InterceptReadOnly implementation of EntityBeanIntercept.
 */
class MethodNewInstanceIntercept {

  static void interceptAddIntercept(ClassVisitor cv, ClassMeta meta) {
    if (!meta.entityExtension()) {
      return;
    }
    if (meta.isSuperClassEntity()) {
      add_initSuper(cv, meta);
    } else {
      add_initLocal(cv, meta);
    }
    add_newInstanceReadOnly(cv, meta);
  }

  static void add_newInstanceReadOnly(ClassVisitor cv, ClassMeta meta) {
    MethodVisitor mv = cv.visitMethod(ACC_PUBLIC, "_ebean_newInstanceIntercept", "(L" + C_INTERCEPT_I + ";)Ljava/lang/Object;", null, null);
    mv.visitCode();
    Label label0 = new Label();
    mv.visitLabel(label0);
    mv.visitLineNumber(4, label0);
    mv.visitTypeInsn(NEW, meta.className());
    mv.visitInsn(DUP);
    mv.visitVarInsn(ALOAD, 1);
    mv.visitMethodInsn(INVOKESPECIAL, meta.className(), INIT, "(L" + C_INTERCEPT_I + ";)V", false);
    mv.visitInsn(ARETURN);
    Label label1 = new Label();
    mv.visitLabel(label1);
    mv.visitLocalVariable("this", "L" + meta.className() + ";", null, label0, label1, 0);
    mv.visitLocalVariable("intercept", "L" + C_INTERCEPT_I + ";", null, label0, label1, 1);
    mv.visitMaxs(3, 2);
    mv.visitEnd();
  }

  static void add_initSuper(ClassVisitor cv, ClassMeta meta) {
    MethodVisitor mv = cv.visitMethod(ACC_PROTECTED, "<init>", "(L" + C_INTERCEPT_I + ";)V", null, null);
    mv.visitCode();
    Label label0 = new Label();
    mv.visitLabel(label0);
    mv.visitLineNumber(12, label0);
    mv.visitVarInsn(ALOAD, 0);
    mv.visitVarInsn(ALOAD, 1);
    mv.visitMethodInsn(INVOKESPECIAL, meta.superClassName(), "<init>", "(L" + C_INTERCEPT_I + ";)V", false);
    Label label1 = new Label();
    mv.visitLabel(label1);
    mv.visitLineNumber(13, label1);
    mv.visitInsn(RETURN);
    Label label2 = new Label();
    mv.visitLabel(label2);
    mv.visitLocalVariable("this", "L" + meta.className() + ";", null, label0, label2, 0);
    mv.visitLocalVariable("intercept", "L" + C_INTERCEPT_I + ";", null, label0, label2, 1);
    mv.visitMaxs(2, 2);
    mv.visitEnd();
  }

  static void add_initLocal(ClassVisitor cv, ClassMeta meta) {
    MethodVisitor mv = cv.visitMethod(ACC_PROTECTED, "<init>", "(L" + C_INTERCEPT_I + ";)V", null, null);
    mv.visitCode();
    Label label0 = new Label();
    mv.visitLabel(label0);
    mv.visitLineNumber(2, label0);
    mv.visitVarInsn(ALOAD, 0);
    mv.visitMethodInsn(INVOKESPECIAL, meta.superClassName(), "<init>", "()V", false);
    Label label1 = new Label();
    mv.visitLabel(label1);
    mv.visitLineNumber(3, label1);
    mv.visitVarInsn(ALOAD, 0);
    mv.visitVarInsn(ALOAD, 1);
    mv.visitFieldInsn(PUTFIELD, meta.className(), INTERCEPT_FIELD, EnhanceConstants.L_INTERCEPT);
    Label label2 = new Label();
    mv.visitLabel(label2);
    mv.visitLineNumber(25, label2);
    mv.visitInsn(RETURN);
    Label label3 = new Label();
    mv.visitLabel(label3);
    mv.visitLocalVariable("this", "L" + meta.className() + ";", null, label0, label3, 0);
    mv.visitLocalVariable("intercept", "L" + C_INTERCEPT_I + ";", null, label0, label3, 1);
    mv.visitMaxs(4, 2);
    mv.visitEnd();
  }
}
