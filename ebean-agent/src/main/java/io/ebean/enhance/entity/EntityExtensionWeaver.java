package io.ebean.enhance.entity;

import io.ebean.enhance.asm.*;
import io.ebean.enhance.common.ClassMeta;
import io.ebean.enhance.common.EnhanceConstants;

import java.util.List;

/**
 * Generate the _ebean_getIntercept() method and field.
 */
class EntityExtensionWeaver implements Opcodes, EnhanceConstants {

  public static void addExtensionAccessorsField(ClassVisitor cv, ClassMeta meta) {
    if (!meta.entityExtension()) {
      return; // agent does not support EntityExtension enhancement
    }
    if (meta.implementsExtendableBeanInterface()) {
      FieldVisitor fv = cv.visitField(meta.accPublic() + ACC_STATIC + ACC_FINAL, EXTENSION_ACCESSORS_FIELD, "L" + C_EXTENSIONACCESSORS + ";", null, null);
      fv.visitEnd();
    }
    List<Type> extensions = meta.entityExtensions();
    if (extensions != null) {
      for (Type extension : extensions) {
        FieldVisitor fv = cv.visitField(meta.accPublic() + ACC_STATIC + ACC_FINAL, getFieldName(extension), "L" + C_EXTENSIONACCESSOR + ";", null, null);
        fv.visitEnd();
      }
    }
  }

  public static void addExtensionAccessorsInit(MethodVisitor mv, ClassMeta classMeta) {
    if (!classMeta.entityExtension()) {
      return;
    }

    if (classMeta.implementsExtendableBeanInterface()) {

      Label l0 = new Label();
      mv.visitLabel(l0);
      mv.visitLineNumber(1, l0);

      mv.visitTypeInsn(NEW, C_EXTENSIONACCESSORS);
      mv.visitInsn(DUP);
      //mv.visitLdcInsn(Type.getType("L" + classMeta.className() + ";"));
      mv.visitFieldInsn(GETSTATIC, classMeta.className(), "_ebean_props", "[Ljava/lang/String;");
      if (classMeta.isSuperClassEntity()) {
        mv.visitFieldInsn(GETSTATIC, classMeta.superClassName(), EXTENSION_ACCESSORS_FIELD, "L" + C_EXTENSIONACCESSORS + ";");
      } else {
        mv.visitInsn(ACONST_NULL);
      }
      mv.visitMethodInsn(INVOKESPECIAL, C_EXTENSIONACCESSORS, INIT, "([Ljava/lang/String;L" + C_EXTENSIONACCESSORS + ";)V", false);
      mv.visitFieldInsn(PUTSTATIC, classMeta.className(), EXTENSION_ACCESSORS_FIELD, "L" + C_EXTENSIONACCESSORS + ";");
    }

    List<Type> extensions = classMeta.entityExtensions();
    if (extensions != null) {
      for (Type extension : extensions) {
        Label l0 = new Label();
        mv.visitLabel(l0);
        mv.visitLineNumber(1, l0);
        mv.visitFieldInsn(GETSTATIC, extension.getInternalName(), EXTENSION_ACCESSORS_FIELD, "L" + C_EXTENSIONACCESSORS + ";");
        //mv.visitLdcInsn(Type.getType("L" + classMeta.className() + ";")); // sourceClass
        mv.visitTypeInsn(NEW, classMeta.className());
        mv.visitInsn(DUP);
        mv.visitMethodInsn(INVOKESPECIAL, classMeta.className(), "<init>", "()V", false);
        mv.visitMethodInsn(INVOKEVIRTUAL, C_EXTENSIONACCESSORS, "add", "(L" + C_ENTITYBEAN + ";)L" + C_EXTENSIONACCESSOR + ";", false);
        mv.visitFieldInsn(PUTSTATIC, classMeta.className(), getFieldName(extension), "L" + C_EXTENSIONACCESSOR + ";");
      }
    }
  }

  public static void addGetExtensionAccessors(ClassVisitor cv, ClassMeta classMeta) {
    if (!classMeta.entityExtension()) {
      return;
    }
    if (classMeta.implementsExtendableBeanInterface()) {
      MethodVisitor mv = cv.visitMethod(classMeta.accPublic(), "_ebean_getExtensionAccessors", "()L" + C_EXTENSIONACCESSORS + ";", null, null);
      mv.visitCode();
      Label l0 = new Label();
      mv.visitLabel(l0);
      mv.visitLineNumber(1, l0);
      mv.visitFieldInsn(GETSTATIC, classMeta.className(), EXTENSION_ACCESSORS_FIELD, "L" + C_EXTENSIONACCESSORS + ";");
      mv.visitInsn(ARETURN);
      Label l1 = new Label();
      mv.visitLabel(l1);
      mv.visitLocalVariable("this", "L" + classMeta.className() + ";", null, l0, l1, 0);
      mv.visitMaxs(1, 1);
      mv.visitEnd();
    }

  }


  /**
   * Add the _ebean_intercept field.
   */
  static void addStorageField(ClassVisitor cv, ClassMeta meta, boolean transientInternalFields) {
    if (meta.entityExtension() && meta.implementsExtendableBeanInterface()) {
      int access = meta.accPrivate() + (transientInternalFields ? ACC_TRANSIENT : 0);
      FieldVisitor f1 = cv.visitField(access, EXTENSION_STORAGE_FIELD, "[L" + C_ENTITYBEAN + ";", null, null);
      f1.visitEnd();
    }
  }

  static boolean addStorageInit(MethodVisitor mv, ClassMeta meta) {
    if (meta.entityExtension() && meta.implementsExtendableBeanInterface()) {
      // initialize the EXTENSION_STORAGE field
      mv.visitVarInsn(ALOAD, 0);
      mv.visitFieldInsn(GETSTATIC, meta.className(), EXTENSION_ACCESSORS_FIELD, "L" + C_EXTENSIONACCESSORS + ";");
      mv.visitMethodInsn(INVOKEVIRTUAL, C_EXTENSIONACCESSORS, "size", "()I", false);
      mv.visitTypeInsn(ANEWARRAY, C_ENTITYBEAN);
      mv.visitFieldInsn(PUTFIELD, meta.className(), EXTENSION_STORAGE_FIELD, "[Lio/ebean/bean/EntityBean;");
      return true;
    } else {
      return false;
    }
  }

  public static void addGetExtension(ClassVisitor cv, ClassMeta classMeta) {
    if (!classMeta.entityExtension() || !classMeta.implementsExtendableBeanInterface()) {
      return;
    }

    MethodVisitor mv = cv.visitMethod(classMeta.accPublic(), "_ebean_getExtension", "(L" + C_EXTENSIONACCESSOR + ";Lio/ebean/bean/EntityBeanIntercept;)Lio/ebean/bean/EntityBean;", null, null);
    mv.visitCode();

    Label l0 = new Label();
    Label l1 = new Label();

    mv.visitLabel(l0);
    mv.visitLineNumber(1, l0);

    mv.visitVarInsn(ALOAD, 0);
    mv.visitFieldInsn(GETFIELD, classMeta.className(), EXTENSION_STORAGE_FIELD, "[Lio/ebean/bean/EntityBean;");

    mv.visitVarInsn(ALOAD, 1); // C_EXTENSIONACCESSOR
    mv.visitMethodInsn(INVOKEINTERFACE, C_EXTENSIONACCESSOR, "getIndex", "()I", true);
    mv.visitInsn(AALOAD); // value storage[i] on stack
    mv.visitInsn(DUP);
    mv.visitJumpInsn(IFNONNULL, l1);
    mv.visitInsn(POP); // take "null" from stack

    mv.visitFieldInsn(GETSTATIC, classMeta.className(), EXTENSION_ACCESSORS_FIELD, "L" + C_EXTENSIONACCESSORS + ";");
    mv.visitVarInsn(ALOAD, 1); // C_EXTENSIONACCESSOR
    //mv.visitMethodInsn(INVOKEVIRTUAL, C_EXTENSIONACCESSORS, "getOffset", "(L" + C_EXTENSIONACCESSOR + ";)I", false);
    mv.visitVarInsn(ALOAD, 2); // ebi
    mv.visitMethodInsn(INVOKEVIRTUAL, C_EXTENSIONACCESSORS, "createInstance", "(L" + C_EXTENSIONACCESSOR + ";Lio/ebean/bean/EntityBeanIntercept;)Lio/ebean/bean/EntityBean;", false);
    mv.visitInsn(DUP);

    mv.visitVarInsn(ASTORE, 3);
    mv.visitVarInsn(ALOAD, 0);
    mv.visitFieldInsn(GETFIELD, classMeta.className(), EXTENSION_STORAGE_FIELD, "[Lio/ebean/bean/EntityBean;");
    mv.visitVarInsn(ALOAD, 1); // C_EXTENSIONACCESSOR
    mv.visitMethodInsn(INVOKEINTERFACE, C_EXTENSIONACCESSOR, "getIndex", "()I", true);
    mv.visitVarInsn(ALOAD, 3);
    mv.visitInsn(AASTORE);

    mv.visitInsn(ARETURN);

    mv.visitLabel(l1);
    mv.visitLineNumber(13, l0);

    mv.visitInsn(ARETURN);
    mv.visitLocalVariable("this", "L" + classMeta.className() + ";", null, l0, l1, 0);
    mv.visitMaxs(3, 1);
    mv.visitEnd();

  }

  public static MethodVisitor replaceGetterBody(MethodVisitor mv, String className, Type extension) {
    // mv.visitCode();
    Label l0 = new Label();
    mv.visitLabel(l0);
    mv.visitLineNumber(13, l0);
    mv.visitFieldInsn(GETSTATIC, className, getFieldName(extension), "L" + C_EXTENSIONACCESSOR + ";");
    mv.visitVarInsn(ALOAD, 0);
    mv.visitMethodInsn(INVOKEINTERFACE, C_EXTENSIONACCESSOR, "getExtension", "(Lio/ebean/bean/extend/ExtendableBean;)L" + C_ENTITYBEAN + ";", true);
    mv.visitTypeInsn(Opcodes.CHECKCAST, className);
    mv.visitInsn(ARETURN);

    Label l1 = new Label();
    mv.visitLabel(l1);
    mv.visitLocalVariable("obj", "L" + extension.getInternalName() + ";", null, l0, l1, 0);
    mv.visitMaxs(2, 1);
    mv.visitEnd();

    return null;
  }

  private static String getFieldName(Type extension) {
    return "_ebean_acc_" + extension.getInternalName().replace('/', '_');
  }
}
