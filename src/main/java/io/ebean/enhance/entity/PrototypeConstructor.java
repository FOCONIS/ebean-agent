package io.ebean.enhance.entity;

import io.ebean.enhance.asm.ClassVisitor;
import io.ebean.enhance.asm.Label;
import io.ebean.enhance.asm.MethodVisitor;
import static io.ebean.enhance.asm.Opcodes.*;
import io.ebean.enhance.common.ClassMeta;
import static io.ebean.enhance.common.EnhanceConstants.*;

/**
 * Adds the Constructor(io/ebean/bean/ConstructorMarker) method.
 */
public class PrototypeConstructor {

  /**
   * Add the _ebean_newInstance() method.
   */
  public static void addConstructor(ClassVisitor cv, ClassMeta classMeta) {

    MethodVisitor mv = cv.visitMethod(ACC_PUBLIC, "<init>", "(" + C_CONSTRUCTOR_MARKER + ")V", null, null);
    mv.visitCode();
    Label l0 = new Label();
    mv.visitLabel(l0);
    mv.visitLineNumber(10, l0);
    if (classMeta.isSuperClassEntity()) {
      // call: super(ConstructorMarker)
      mv.visitVarInsn(ALOAD,0);
      mv.visitVarInsn(ALOAD,1);
      mv.visitMethodInsn(INVOKESPECIAL, classMeta.getSuperClassName(), "<init>", "(" + C_CONSTRUCTOR_MARKER + ")V", false);
    } else {
      // call: super()
      mv.visitVarInsn(ALOAD,0);
      mv.visitMethodInsn(INVOKESPECIAL, classMeta.getSuperClassName(), "<init>", "()V", false);

      // init intercept
      mv.visitVarInsn(ALOAD, 0);
      mv.visitTypeInsn(NEW, C_INTERCEPT);
      mv.visitInsn(DUP);
      mv.visitVarInsn(ALOAD, 0);
      
      mv.visitMethodInsn(INVOKESPECIAL, C_INTERCEPT, "<init>", "(Ljava/lang/Object;)V", false);
      mv.visitFieldInsn(PUTFIELD, classMeta.getClassName(), INTERCEPT_FIELD, L_INTERCEPT);
    }
    mv.visitMaxs(2, 2);


    
    mv.visitInsn(RETURN);
    Label l1 = new Label();
    mv.visitLabel(l1);
    mv.visitLocalVariable("this", "L" + classMeta.getClassName() + ";", null, l0, l1, 0);
    mv.visitLocalVariable("marker", C_CONSTRUCTOR_MARKER, null, l0, l1, 1);
    mv.visitEnd();
  }
}
