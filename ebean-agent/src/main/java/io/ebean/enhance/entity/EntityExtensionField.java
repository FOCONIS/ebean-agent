package io.ebean.enhance.entity;

import io.ebean.enhance.asm.ClassVisitor;
import io.ebean.enhance.asm.FieldVisitor;
import io.ebean.enhance.asm.Label;
import io.ebean.enhance.asm.MethodVisitor;
import io.ebean.enhance.asm.Opcodes;
import io.ebean.enhance.asm.Type;
import io.ebean.enhance.common.ClassMeta;
import io.ebean.enhance.common.EnhanceConstants;

/**
 * Generate the _ebean_getIntercept() method and field.
 */
class EntityExtensionField implements Opcodes, EnhanceConstants {

  /**
   * Add the _ebean_intercept field.
   */
  static void addStorageField(ClassVisitor cv, ClassMeta meta, boolean transientInternalFields) {
    if (meta.entityExtension() && meta.implementsExtendableBeanInterface()) {
      int access = meta.accProtected() + (transientInternalFields ? ACC_TRANSIENT : 0);
      FieldVisitor f1 = cv.visitField(access, EXTENSION_STORAGE_FIELD, "[L" + C_ENTITYBEAN + ";", null, null);
      f1.visitEnd();
    }
  }


  public static void addExtensionInfoField(ClassVisitor cv, ClassMeta meta) {
    if (meta.entityExtension() && meta.implementsExtendableBeanInterface()) {
      FieldVisitor fv = cv.visitField(meta.accPublic() + ACC_STATIC, EXTENSION_INFO_FIELD, "L" + C_EXTENSIONINFO + ";", null, null);
      fv.visitEnd();
    }
  }
//
//  /**
//   * Generate the _ebean_getIntercept() method.
//   * <p>
//   * <pre>
//   * public EntityBeanIntercept _ebean_getIntercept() {
//   *     return _ebean_intercept;
//   * }
//   * </pre>
//   */
//  static void addGetterSetter(ClassVisitor cv, ClassMeta meta) {
//    String className = meta.getClassName();
//    String lClassName = "L" + className + ";";
//
//    MethodVisitor mv;
//    Label l0, l1;
//
//    mv = cv.visitMethod(meta.accPublic(), "_ebean_getIntercept", "()" + L_INTERCEPT, null, null);
//    mv.visitCode();
//    l0 = new Label();
//    mv.visitLabel(l0);
//    mv.visitLineNumber(1, l0);
//    mv.visitVarInsn(ALOAD, 0);
//    mv.visitFieldInsn(GETFIELD, className, INTERCEPT_FIELD, L_INTERCEPT);
//    mv.visitInsn(ARETURN);
//    l1 = new Label();
//    mv.visitLabel(l1);
//    mv.visitLocalVariable("this", lClassName, null, l0, l1, 0);
//    mv.visitMaxs(0, 0);
//    mv.visitEnd();
//
//    addInitInterceptMethod(cv, meta);
//  }

  /**
   * Add _ebean_intercept() method that includes initialisation of the
   * EntityBeanIntercept.
   * <p>
   * This is only required when transientInternalFields=true with enhancement.
   * In that case the EntityBeanIntercept is transient and can be null after
   * deserialization - in which case it needs to be initialised.
   * </p>
   * <p>
   * <pre>
   * public EntityBeanIntercept _ebean_intercept() {
   *     if (_ebean_intercept == null) {
   *         _ebean_intercept = new EntityBeanIntercept(this);
   *     }
   *     return _ebean_intercept;
   * }
   * </pre>
   */
//  private static void addInitInterceptMethod(ClassVisitor cv, ClassMeta meta) {
//    String className = meta.getClassName();
//    MethodVisitor mv = cv.visitMethod(meta.accPublic(), "_ebean_intercept", "()" + L_INTERCEPT, null, null);
//    mv.visitCode();
//    Label l0 = new Label();
//    mv.visitLabel(l0);
//    mv.visitLineNumber(1, l0);
//    mv.visitVarInsn(ALOAD, 0);
//    mv.visitFieldInsn(GETFIELD, className, INTERCEPT_FIELD, L_INTERCEPT);
//    Label l1 = new Label();
//    mv.visitJumpInsn(IFNONNULL, l1);
//    Label l2 = new Label();
//    mv.visitLabel(l2);
//    mv.visitLineNumber(2, l2);
//    mv.visitVarInsn(ALOAD, 0);
//    mv.visitTypeInsn(NEW, meta.interceptNew());
//    mv.visitInsn(DUP);
//    mv.visitVarInsn(ALOAD, 0);
//    mv.visitMethodInsn(INVOKESPECIAL, meta.interceptNew(), INIT, "(Ljava/lang/Object;)V", false);
//    mv.visitFieldInsn(PUTFIELD, className, INTERCEPT_FIELD, L_INTERCEPT);
//    mv.visitLabel(l1);
//    mv.visitLineNumber(3, l1);
//    mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
//    mv.visitVarInsn(ALOAD, 0);
//    mv.visitFieldInsn(GETFIELD, className, INTERCEPT_FIELD, L_INTERCEPT);
//    mv.visitInsn(ARETURN);
//    Label l3 = new Label();
//    mv.visitLabel(l3);
//    mv.visitLocalVariable("this", "L" + className + ";", null, l0, l3, 0);
//    mv.visitMaxs(4, 1);
//    mv.visitEnd();
//  }
  public static void addStorageInit(MethodVisitor mv, ClassMeta classMeta) {
    if (!classMeta.entityExtension() || !classMeta.implementsExtendableBeanInterface()) {
      return;
    }
   /* List<FieldMeta> fields = classMeta.getAllFields();

    Label l0 = new Label();
    mv.visitLabel(l0);
    mv.visitLineNumber(1, l0);
    VisitUtil.visitIntInsn(mv, fields.size());
    mv.visitTypeInsn(ANEWARRAY, "java/lang/String");

    if (fields.isEmpty()) {
      if (classMeta.isLog(4)) {
        classMeta.log("Has no fields?");
      }
    } else {
      for (int i = 0; i < fields.size(); i++) {
        FieldMeta field = fields.get(i);
        mv.visitInsn(DUP);
        VisitUtil.visitIntInsn(mv, i);
        mv.visitLdcInsn(field.getName());
        mv.visitInsn(AASTORE);
      }
    }*/
    //mv.visitTypeInsn(NEW, C_EXTENSIONINFO);
    //mv.visitInsn(DUP);
    //mv.visitLdcInsn(classMeta.getClassName());
    //mv.visitInsn(ACONST_NULL);
    //
    // mv.visitInsn(ACONST_NULL);
    //mv.visitFieldInsn(GETSTATIC, classMeta.getClassName(), "_ebean_props2", "[L"+C_EXTENSIONINFO+";");

//    new io/ebean/bean/extend/ExtensionInfo
//    dup
//    ldc Ltest/model/extend/BEntityBase; (org.objectweb.asm.Type)
//      getstatic test/model/extend/BEntityBaseAbstract.demo:io.ebean.bean.extend.ExtensionInfo
//    invokespecial io/ebean/bean/extend/ExtensionInfo.<init>(Ljava/lang/Class;Lio/ebean/bean/extend/ExtensionInfo;)V
//    putstatic test/model/extend/BEntityBase.demo:io.ebean.bean.extend.ExtensionInfo
//    return

    //mv.visitFieldInsn(PUTSTATIC, classMeta.getClassName(), EXTENSION_STORAGE_FIELD, "[L"+C_EXTENSIONINFO+";");


    Label l0 = new Label();
    mv.visitLabel(l0);
    mv.visitLineNumber(1, l0);

    mv.visitTypeInsn(NEW, C_EXTENSIONINFO);
    mv.visitInsn(DUP);
    mv.visitLdcInsn(Type.getType("L" + classMeta.getClassName() + ";"));
    if (classMeta.isSuperClassEntity()) {
      mv.visitFieldInsn(GETSTATIC, classMeta.getSuperClassName(), EXTENSION_INFO_FIELD, "L" + C_EXTENSIONINFO + ";");
    } else {
      mv.visitInsn(ACONST_NULL);
    }
    mv.visitMethodInsn(INVOKESPECIAL, C_EXTENSIONINFO, INIT, "(Ljava/lang/Class;Lio/ebean/bean/extend/ExtensionInfo;)V", false);
    mv.visitFieldInsn(PUTSTATIC, classMeta.getClassName(), EXTENSION_INFO_FIELD, "L" + C_EXTENSIONINFO + ";");
  }

  public static void addGetExtensionInfo(ClassVisitor cv, ClassMeta classMeta) {
    if (!classMeta.entityExtension() || !classMeta.implementsExtendableBeanInterface()) {
      return;
    }
    MethodVisitor mv = cv.visitMethod(classMeta.accPublic(), "_ebean_getExtensionInfos", "()L" + C_EXTENSIONINFO + ";", null, null);
    mv.visitCode();
    Label l0 = new Label();
    mv.visitLabel(l0);
    mv.visitLineNumber(13, l0);
    mv.visitFieldInsn(GETSTATIC, classMeta.getClassName(), EXTENSION_INFO_FIELD, "L" + C_EXTENSIONINFO + ";");
    mv.visitInsn(ARETURN);
    Label l1 = new Label();
    mv.visitLabel(l1);
    mv.visitLocalVariable("this", "L" + classMeta.getClassName() + ";", null, l0, l1, 0);
    mv.visitMaxs(1, 1);
    mv.visitEnd();
  }
}
