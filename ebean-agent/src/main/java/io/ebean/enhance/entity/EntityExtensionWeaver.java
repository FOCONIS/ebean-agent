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
class EntityExtensionWeaver implements Opcodes, EnhanceConstants {

  public static void addExtensionInfoField(ClassVisitor cv, ClassMeta meta) {
    if (meta.entityExtension() && meta.implementsExtendableBeanInterface()) {
      FieldVisitor fv = cv.visitField(meta.accPublic() + ACC_STATIC, EXTENSION_INFO_FIELD, "L" + C_EXTENSIONINFO + ";", null, null);
      fv.visitEnd();
    }
  }

  public static void addExtensionInfoInit(MethodVisitor mv, ClassMeta classMeta) {
    if (!classMeta.entityExtension() || !classMeta.implementsExtendableBeanInterface()) {
      return;
    }

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
    MethodVisitor mv = cv.visitMethod(classMeta.accPublic(), "_ebean_getExtensionInfo", "()L" + C_EXTENSIONINFO + ";", null, null);
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




  /**
   * Add the _ebean_intercept field.
   */
  static void addStorageField(ClassVisitor cv, ClassMeta meta, boolean transientInternalFields) {
    if (true) return;
    if (meta.entityExtension() && meta.implementsExtendableBeanInterface()) {
      int access = meta.accPublic() + (transientInternalFields ? ACC_TRANSIENT : 0);
      FieldVisitor f1 = cv.visitField(access, EXTENSION_STORAGE_FIELD, "[L" + C_ENTITYBEAN + ";", null, null);
      f1.visitEnd();
    }
  }

  static boolean addStorageInit(MethodVisitor mv,  ClassMeta meta) {
    if (true) return false;
    if (meta.entityExtension() && meta.implementsExtendableBeanInterface()) {
      // initialize the EXTENSION_STORAGE field
      mv.visitVarInsn(ALOAD, 0);
      mv.visitVarInsn(ALOAD, 0);
      mv.visitMethodInsn(INVOKEVIRTUAL, meta.getClassName(), "_ebean_getExtensionInfo","()Lio/ebean/bean/extend/ExtensionInfo;", true);
      mv.visitTypeInsn(ANEWARRAY, "io/ebean/bean/EntityBean");
      mv.visitFieldInsn(PUTFIELD,meta.getClassName(), EXTENSION_STORAGE_FIELD,"[Lio/ebean/bean/EntityBean;");
      return true;
    } else {
      return false;
    }
  }


//  public _ebean_getExtension(int arg0, io.ebean.bean.EntityBeanIntercept arg1) { //(ILio/ebean/bean/EntityBeanIntercept;)Lio/ebean/bean/EntityBean;
//         <localVar:index=4 , name=info , desc=Lio/ebean/bean/extend/ExtensionInfo;, sig=null, start=L1, end=L2>
//         <localVar:index=5 , name=offset , desc=I, sig=null, start=L3, end=L2>
//         <localVar:index=0 , name=this , desc=Ltest/model/extend/BEntityBaseAbstract;, sig=null, start=L4, end=L5>
//         <localVar:index=1 , name=index , desc=I, sig=null, start=L4, end=L5>
//         <localVar:index=2 , name=ebi , desc=Lio/ebean/bean/EntityBeanIntercept;, sig=null, start=L4, end=L5>
//         <localVar:index=3 , name=ret , desc=Lio/ebean/bean/EntityBean;, sig=null, start=L6, end=L5>
//
//      L4 {
//      aload0 // reference to self
//      getfield test/model/extend/BEntityBaseAbstract._ebean__extensionStorage:io.ebean.bean.EntityBean[]
//        iload1 // reference to arg0
//      aaload
//        astore3
//    }
//    L6 {
//      aload3
//      ifnonnull L2
//    }
//    L7 {
//      aload0 // reference to self
//      invokevirtual test/model/extend/BEntityBaseAbstract._ebean_getExtensionInfo()Lio/ebean/bean/extend/ExtensionInfo;
//      astore4
//    }
//    L1 {
//      aload4
//        iload1 // reference to arg0
//      invokevirtual io/ebean/bean/extend/ExtensionInfo.getOffset(I)I
//        istore5
//    }
//    L3 {
//      aload0 // reference to self
//      getfield test/model/extend/BEntityBaseAbstract._ebean__extensionStorage:io.ebean.bean.EntityBean[]
//        iload1 // reference to arg0
//      aload4
//        iload1 // reference to arg0
//      invokevirtual io/ebean/bean/extend/ExtensionInfo.get(I)Lio/ebean/bean/extend/ExtensionInfo$Entry;
//      iload5
//        aload2
//      invokevirtual io/ebean/bean/extend/ExtensionInfo$Entry.createInstance(ILio/ebean/bean/EntityBeanIntercept;)Lio/ebean/bean/EntityBean;
//      dup
//        astore3
//      aastore
//    }
//    L2 {
//      f_new (Locals[4]: test/model/extend/BEntityBaseAbstract, 1, io/ebean/bean/EntityBeanIntercept, io/ebean/bean/EntityBean) (Stack[0]: null)
//      aload3
//        areturn
//    }
//    L5 {
//    }
//  }
public static void addGetExtension(ClassVisitor cv, ClassMeta classMeta) {
  if (!classMeta.entityExtension() || !classMeta.implementsExtendableBeanInterface()) {
    return;
  }
  /*
  MethodVisitor mv = cv.visitMethod(classMeta.accPublic(), "_ebean_getExtension", "(ILio/ebean/bean/EntityBeanIntercept;)Lio/ebean/bean/EntityBean;", null, null);
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
  */
}
}
