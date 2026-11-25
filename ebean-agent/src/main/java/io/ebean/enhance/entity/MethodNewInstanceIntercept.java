package io.ebean.enhance.entity;

import io.ebean.enhance.asm.ClassVisitor;
import io.ebean.enhance.asm.Label;
import io.ebean.enhance.asm.MethodVisitor;
import io.ebean.enhance.asm.Opcodes;
import io.ebean.enhance.common.ClassMeta;
import io.ebean.enhance.common.EnhanceConstants;

import static io.ebean.enhance.asm.Opcodes.*;
import static io.ebean.enhance.common.EnhanceConstants.*;

/**
 * Add support for the InterceptReadOnly and ExtendendEntity implementation of EntityBeanIntercept.
 * <p>
 * This is very similar to MethodNewInstanceReadOnly, but adds different constructor (accepting 2 parameters)
 * that will construct either a C_INTERCEPT_RO or C_INTERCEPT_EXT.
 */
class MethodNewInstanceIntercept {

  static void interceptAddIntercept(ClassVisitor cv, ClassMeta meta) {
    if (!meta.entityExtension() && !meta.interceptAddReadOnly()) {
      return;
    }
    if (meta.isSuperClassEntity()) {
      add_initSuper(cv, meta);
    } else {
      add_initLocal(cv, meta);
    }
    if (meta.interceptAddReadOnly()) {
      add_newInstanceReadOnly(cv, meta);
    }
    if (meta.entityExtension()) {
      add_newInstanceExtended(cv, meta);
    }

  }

  /**
   * adds _ebean_newExtendedInstance method:
   * <pre>
   *   public Object _ebean_newInstanceReadOnly() {
   *     return new MyBean(-1, null); // null means ReadOnly EBI
   *   }
   * </pre>
   */
  static void add_newInstanceReadOnly(ClassVisitor cv, ClassMeta meta) {
    MethodVisitor mv = cv.visitMethod(meta.accPublic(), "_ebean_newInstanceReadOnly", "()" + L_OBJECT, null, null);
    mv.visitCode();
    Label label0 = new Label();
    mv.visitLabel(label0);
    mv.visitLineNumber(4, label0);
    mv.visitTypeInsn(NEW, meta.className());
    mv.visitInsn(DUP);
    mv.visitInsn(ICONST_M1);
    mv.visitInsn(ACONST_NULL);
    mv.visitMethodInsn(INVOKESPECIAL, meta.className(), INIT, "(I" + L_ENTITYBEAN + ")V", false);
    mv.visitInsn(ARETURN);
    Label label1 = new Label();
    mv.visitLabel(label1);
    mv.visitLocalVariable("this", "L" + meta.className() + ";", null, label0, label1, 0);
    mv.visitMaxs(4, 3);
    mv.visitEnd();
  }

  /**
   * adds _ebean_newExtendedInstance method:
   * <pre>
   *   public Object _ebean_newExtendedInstance(int offset, EntityBean base) {
   *     return new MyBean(offset, base);
   *   }
   * </pre>
   */
  static void add_newInstanceExtended(ClassVisitor cv, ClassMeta meta) {
    MethodVisitor mv = cv.visitMethod(ACC_PUBLIC, "_ebean_newExtendedInstance", "(I" + L_ENTITYBEAN + ")" + L_OBJECT, null, null);
    mv.visitCode();
    Label label0 = new Label();
    mv.visitLabel(label0);
    mv.visitLineNumber(4, label0);
    mv.visitTypeInsn(NEW, meta.className());
    mv.visitInsn(DUP);
    mv.visitVarInsn(ILOAD, 1);
    mv.visitVarInsn(ALOAD, 2);
    mv.visitMethodInsn(INVOKESPECIAL, meta.className(), INIT, "(I" + L_ENTITYBEAN + ")V", false);
    mv.visitInsn(ARETURN);
    Label label1 = new Label();
    mv.visitLabel(label1);
    mv.visitLocalVariable("this", "L" + meta.className() + ";", null, label0, label1, 0);
    mv.visitLocalVariable("offset", "I", null, label0, label1, 1);
    mv.visitLocalVariable("base", L_ENTITYBEAN, null, label0, label1, 2);
    mv.visitMaxs(4, 3);
    mv.visitEnd();
  }

  /**
   * Adds the super constructor call:
   * <pre>
   *    protected MyBean(int offset, EntityBean base) {
   *     super(offset, base);
   *   }
   * </pre>
   */
  static void add_initSuper(ClassVisitor cv, ClassMeta meta) {
    MethodVisitor mv = cv.visitMethod(meta.accProtected(), INIT, "(I" + L_ENTITYBEAN + ")V", null, null);
    mv.visitCode();
    Label label0 = new Label();
    mv.visitLabel(label0);
    mv.visitLineNumber(12, label0);
    mv.visitVarInsn(ALOAD, 0);
    mv.visitVarInsn(ILOAD, 1);
    mv.visitVarInsn(ALOAD, 2);
    mv.visitMethodInsn(INVOKESPECIAL, meta.superClassName(), INIT, "(I" + L_ENTITYBEAN + ")V", false);
    Label label1 = new Label();
    mv.visitLabel(label1);
    mv.visitLineNumber(13, label1);
    mv.visitInsn(RETURN);
    Label label2 = new Label();
    mv.visitLabel(label2);
    mv.visitLocalVariable("this", "L" + meta.className() + ";", null, label0, label2, 0);
    mv.visitLocalVariable("offset", "I", null, label0, label2, 1);
    mv.visitLocalVariable("base", L_ENTITYBEAN, null, label0, label2, 2);
    mv.visitMaxs(3, 3);
    mv.visitEnd();
  }

  /**
   * Adds constructor:
   * <pre>
   *   protected MyBean (int offset, EntityBean base) {
   *     if (base == null) {
   *       ebi = new InterceptReadOnly(this);
   *     } else {
   *       ebi = new EntityExtensionIntercept(this, offset, base);
   *     }
   *   }
   * </pre>
   */
  static void add_initLocal(ClassVisitor cv, ClassMeta meta) {
    MethodVisitor mv = cv.visitMethod(meta.accProtected(), INIT, "(I" + L_ENTITYBEAN + ")V", null, null);
    mv.visitCode();
    Label label0 = new Label();
    mv.visitLabel(label0);
    mv.visitLineNumber(2, label0);
    mv.visitVarInsn(ALOAD, 0);
    mv.visitMethodInsn(INVOKESPECIAL, meta.className(), INIT, "()V", false);
    Label label1 = new Label();
    mv.visitLabel(label1);
    mv.visitLineNumber(3, label1);
    mv.visitVarInsn(ALOAD, 2);
    Label label2 = new Label();
    mv.visitJumpInsn(IFNONNULL, label2); // base != null -> extension
    Label label3 = new Label();
    mv.visitLabel(label3);
    mv.visitLineNumber(4, label3);
    mv.visitVarInsn(ALOAD, 0);
    mv.visitTypeInsn(NEW, C_INTERCEPT_RO);
    mv.visitInsn(DUP);
    mv.visitVarInsn(ALOAD, 0);
    mv.visitMethodInsn(INVOKESPECIAL, C_INTERCEPT_RO, INIT, "(" + L_OBJECT + ")V", false);
    mv.visitFieldInsn(PUTFIELD, meta.className(), INTERCEPT_FIELD, EnhanceConstants.L_INTERCEPT);
    Label label4 = new Label();
    mv.visitJumpInsn(GOTO, label4);

    mv.visitLabel(label2);
    mv.visitLineNumber(6, label2);
    mv.visitFrame(F_FULL, 3, new Object[]{meta.className(), "I", C_ENTITYBEAN}, 0, new Object[0]);
    mv.visitVarInsn(ALOAD, 0);
    mv.visitTypeInsn(NEW, C_INTERCEPT_EXT);
    mv.visitInsn(DUP);
    mv.visitVarInsn(ALOAD, 0);
    mv.visitVarInsn(ILOAD, 1);
    mv.visitVarInsn(ALOAD, 2);
    mv.visitMethodInsn(INVOKESPECIAL, C_INTERCEPT_EXT, INIT, "(" + L_OBJECT + "I" + L_ENTITYBEAN + ")V", false);
    mv.visitFieldInsn(PUTFIELD, meta.className(), INTERCEPT_FIELD, EnhanceConstants.L_INTERCEPT);

    mv.visitLabel(label4);
    mv.visitLineNumber(8, label4);
    mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
    mv.visitInsn(RETURN);

    Label label5 = new Label();
    mv.visitLabel(label5);
    mv.visitLocalVariable("this", "L" + meta.className() + ";", null, label0, label5, 0);
    mv.visitLocalVariable("offset", "I", null, label0, label5, 1);
    mv.visitLocalVariable("base", L_ENTITYBEAN, null, label0, label5, 2);
    mv.visitMaxs(6, 3);
    mv.visitEnd();
  }
}
