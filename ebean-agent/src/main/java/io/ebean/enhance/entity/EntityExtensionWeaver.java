package io.ebean.enhance.entity;

import io.ebean.enhance.asm.*;
import io.ebean.enhance.common.ClassMeta;
import io.ebean.enhance.common.EnhanceConstants;

import java.util.List;

/**
 * Generate the bytecode for EntityExtension.
 */
class EntityExtensionWeaver implements Opcodes, EnhanceConstants {

  /**
   * if class extends ExtendableBean, this will add the field
   * <pre>
   *   public static ExtensionAccessors _ebean_extension_accessors;
   * </pre>
   * <p>
   * and if class is annotated with &#64;EntityExtension(my.pkg.Foo.class, my.pkg.Bar.class) it will add the fields
   * <pre>
   *   private static ExtensionAccessor _ebean_acc_my_pkg_Foo;
   *   private static ExtensionAccessor _ebean_acc_my_pkg_Bar;
   * </pre>
   */
  public static void addExtensionAccessorsField(ClassVisitor cv, ClassMeta meta) {
    if (!meta.entityExtension()) {
      return; // agent does not support EntityExtension enhancement
    }
    if (meta.implementsExtendableBeanInterface()) {
      FieldVisitor fv = cv.visitField(meta.accPublic() + ACC_STATIC + ACC_FINAL, EXTENSION_ACCESSORS_FIELD, L_EXTENSIONACCESSORS, null, null);
      fv.visitEnd();
    }
    List<Type> extensions = meta.entityExtensions();
    if (extensions != null) {
      for (Type extension : extensions) {
        FieldVisitor fv = cv.visitField(meta.accPrivate() + ACC_STATIC + ACC_FINAL, getFieldName(extension), L_EXTENSIONACCESSOR, null, null);
        fv.visitEnd();
      }
    }
  }

  /**
   * Initializes the fields:
   * <pre>
   *   // if instance of ExtendableBean
   *   protected static ExtensionAccessors _ebean_extension_accessors
   *     = new ExtensionAccessors(Clazz._ebean_props, SuperClazz._ebean_extension_accessors); // or null
   *
   *   // if annotated with &#64;EntityExtension(my.pkg.Foo.class, my.pkg.Bar.class)
   *   private static ExtensionAccessor _ebean_acc_my_pkg_Foo = Foo._ebean_extension_accessors.add(new MyClass()); // myClass is a prototype
   *   private static ExtensionAccessor _ebean_acc_my_pkg_Bar = Bar._ebean_extension_accessors.add(new MyClass());
   * </pre>
   */
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
      mv.visitFieldInsn(GETSTATIC, classMeta.className(), PROPS_FIELD, "[" + L_STRING);
      if (classMeta.isSuperClassEntity()) {
        mv.visitFieldInsn(GETSTATIC, classMeta.superClassName(), EXTENSION_ACCESSORS_FIELD, L_EXTENSIONACCESSORS);
      } else {
        mv.visitInsn(ACONST_NULL);
      }
      mv.visitMethodInsn(INVOKESPECIAL, C_EXTENSIONACCESSORS, INIT, "([" + L_STRING + L_EXTENSIONACCESSORS + ")V", false);
      mv.visitFieldInsn(PUTSTATIC, classMeta.className(), EXTENSION_ACCESSORS_FIELD, L_EXTENSIONACCESSORS);
    }

    List<Type> extensions = classMeta.entityExtensions();
    if (extensions != null) {
      for (Type extension : extensions) {
        Label l0 = new Label();
        mv.visitLabel(l0);
        mv.visitLineNumber(1, l0);
        mv.visitFieldInsn(GETSTATIC, extension.getInternalName(), EXTENSION_ACCESSORS_FIELD, L_EXTENSIONACCESSORS);
        mv.visitTypeInsn(NEW, classMeta.className());
        mv.visitInsn(DUP);
        mv.visitMethodInsn(INVOKESPECIAL, classMeta.className(), INIT, "()V", false);
        mv.visitMethodInsn(INVOKEVIRTUAL, C_EXTENSIONACCESSORS, "add", "(" + L_ENTITYBEAN + ")" + L_EXTENSIONACCESSOR, false);
        mv.visitFieldInsn(PUTSTATIC, classMeta.className(), getFieldName(extension), L_EXTENSIONACCESSOR);
      }
    }
  }

  /**
   * Adds the _ebean_getExtensionAccessors method:
   * <pre>
   *   public ExtensionAccessors _ebean_getExtensionAccessors() {
   *     return MyBean._ebean_extension_accessors;
   *   }
   * </pre>
   * It is important to use this method and do not read the field itself. Especially if inheritance is involved.
   *
   * @param cv
   * @param classMeta
   */
  public static void addGetExtensionAccessors(ClassVisitor cv, ClassMeta classMeta) {
    if (!classMeta.entityExtension()) {
      return;
    }
    if (classMeta.implementsExtendableBeanInterface()) {
      MethodVisitor mv = cv.visitMethod(classMeta.accPublic(), "_ebean_getExtensionAccessors", "()" + L_EXTENSIONACCESSORS, null, null);
      mv.visitCode();
      Label l0 = new Label();
      mv.visitLabel(l0);
      mv.visitLineNumber(1, l0);
      mv.visitFieldInsn(GETSTATIC, classMeta.className(), EXTENSION_ACCESSORS_FIELD, L_EXTENSIONACCESSORS);
      mv.visitInsn(ARETURN);
      Label l1 = new Label();
      mv.visitLabel(l1);
      mv.visitLocalVariable("this", "L" + classMeta.className() + ";", null, l0, l1, 0);
      mv.visitMaxs(1, 1);
      mv.visitEnd();
    }
  }


  /**
   * Add the _ebean_extension_storage field.
   * <pre>
   *   private EntityBean[] _ebean_extension_storage;
   * </pre>
   * Each extendableBean holds space for all extensions that extend this bean.
   */
  static void addStorageField(ClassVisitor cv, ClassMeta meta) {
    if (meta.entityExtension() && meta.implementsExtendableBeanInterface()) {
      FieldVisitor f1 = cv.visitField(meta.accPrivate(), EXTENSION_STORAGE_FIELD, "[" + L_ENTITYBEAN, null, null);
      f1.visitEnd();
    }
  }

  /**
   * Initializes the _ebean_extension_storage field.
   * <pre>
   *   private EntityBean[] _ebean_extension_storage = new EntityBean[MyBean._ebean_getExtensionAccessors().size()];
   * </pre>
   */
  static void addStorageInit(MethodVisitor mv, ClassMeta meta) {
    if (meta.entityExtension() && meta.implementsExtendableBeanInterface()) {
      // initialize the EXTENSION_STORAGE field
      mv.visitVarInsn(ALOAD, 0);
      mv.visitVarInsn(ALOAD, 0);
      mv.visitMethodInsn(INVOKEVIRTUAL, meta.className(), "_ebean_getExtensionAccessors", "()" + L_EXTENSIONACCESSORS, false);
      mv.visitMethodInsn(INVOKEVIRTUAL, C_EXTENSIONACCESSORS, "size", "()I", false);
      mv.visitTypeInsn(ANEWARRAY, C_ENTITYBEAN);
      mv.visitFieldInsn(PUTFIELD, meta.className(), EXTENSION_STORAGE_FIELD, "[" + L_ENTITYBEAN);
    }
  }

  /**
   * Adds the _ebean_getExtension method.
   * <pre>
   *   public EntityBean _ebean_getExtension2(ExtensionAccessor accessor) {
   *     EntityBean ret = this._ebean_extension_storage[accessor.getIndex()];
   *     if (ret == null) {
   *       ret = this._ebean_getExtensionAccessors().createInstance(accessor, this);
   *       this._ebean_extension_storage[accessor.getIndex()] = ret;
   *     }
   *     return ret;
   *   }
   * </pre>
   */
  public static void addGetExtension(ClassVisitor cv, ClassMeta classMeta) {
    if (!classMeta.entityExtension() || !classMeta.implementsExtendableBeanInterface()) {
      return;
    }

    MethodVisitor mv = cv.visitMethod(classMeta.accPublic(), "_ebean_getExtension", "(" + L_EXTENSIONACCESSOR + ")" + L_ENTITYBEAN, null, null);
    mv.visitCode();

    Label l0 = new Label();

    mv.visitLabel(l0);
    mv.visitLineNumber(1, l0);

    mv.visitVarInsn(ALOAD, 0);
    mv.visitFieldInsn(GETFIELD, classMeta.className(), EXTENSION_STORAGE_FIELD, "[" + L_ENTITYBEAN);

    mv.visitVarInsn(ALOAD, 1); // C_EXTENSIONACCESSOR
    mv.visitMethodInsn(INVOKEINTERFACE, C_EXTENSIONACCESSOR, "getIndex", "()I", true);
    mv.visitInsn(AALOAD); // value storage[i] on stack
    mv.visitVarInsn(ASTORE, 2);

    Label l1 = new Label();
    mv.visitLabel(l1);
    mv.visitLineNumber(2, l1);
    mv.visitVarInsn(ALOAD, 2);

    Label l2 = new Label();
    mv.visitJumpInsn(IFNONNULL, l2);

    Label l3 = new Label();
    mv.visitLabel(l3);
    mv.visitLineNumber(3, l3);
    mv.visitVarInsn(ALOAD, 0);
    mv.visitMethodInsn(INVOKEVIRTUAL, classMeta.className(), "_ebean_getExtensionAccessors", "()" + L_EXTENSIONACCESSORS, false);
    mv.visitVarInsn(ALOAD, 1); // C_EXTENSIONACCESSOR
    mv.visitVarInsn(ALOAD, 0); // base
    mv.visitMethodInsn(INVOKEVIRTUAL, C_EXTENSIONACCESSORS, "createInstance", "(L" + C_EXTENSIONACCESSOR + ";L" + C_ENTITYBEAN + ";)L" + C_ENTITYBEAN + ";", false);
    mv.visitVarInsn(ASTORE, 2);

    Label l4 = new Label();
    mv.visitLabel(l4);
    mv.visitLineNumber(4, l4);
    mv.visitVarInsn(ALOAD, 0);
    mv.visitFieldInsn(GETFIELD, classMeta.className(), EXTENSION_STORAGE_FIELD, "[Lio/ebean/bean/EntityBean;");
    mv.visitVarInsn(ALOAD, 1); // C_EXTENSIONACCESSOR
    mv.visitMethodInsn(INVOKEINTERFACE, C_EXTENSIONACCESSOR, "getIndex", "()I", true);
    mv.visitVarInsn(ALOAD, 2);
    mv.visitInsn(AASTORE);

    mv.visitLabel(l2);
    mv.visitLineNumber(5, l2);
    mv.visitFrame(Opcodes.F_APPEND, 1, new Object[]{C_ENTITYBEAN}, 0, null);
    mv.visitVarInsn(ALOAD, 2);
    mv.visitInsn(ARETURN);

    Label l5 = new Label();
    mv.visitLabel(l5);
    mv.visitLocalVariable("this", "L" + classMeta.className() + ";", null, l0, l5, 0);
    mv.visitLocalVariable("accessor", L_EXTENSIONACCESSOR, null, l0, l5, 1);
    mv.visitLocalVariable("ret", L_ENTITYBEAN, null, l1, l5, 2);
    mv.visitMaxs(3, 3);
    mv.visitEnd();

  }

  /**
   * Overrrides the getter body for extension accessor.
   * Normally you would provide a method stumb like this
   * <pre>
   *   public static BExtension1 get(BEntityBaseAbstract obj) {
   *     throw new NotEnhancedException();
   *   }
   * </pre>
   * This code will replace the method body by
   * <pre>
   *   public static BExtension1 get(BEntityBaseAbstract obj) {
   *     return _ebean_acc_my_pkg_BEntityBaseAbstract.getExtension(obj);
   *   }
   * </pre>
   */
  public static MethodVisitor replaceGetterBody(MethodVisitor mv, String className, Type extension) {
    // mv.visitCode();
    Label l0 = new Label();
    mv.visitLabel(l0);
    mv.visitLineNumber(13, l0);
    mv.visitFieldInsn(GETSTATIC, className, getFieldName(extension), L_EXTENSIONACCESSOR);
    mv.visitVarInsn(ALOAD, 0);
    mv.visitMethodInsn(INVOKEINTERFACE, C_EXTENSIONACCESSOR, "getExtension", "(L" + C_EXTENDABLE_BEAN + ")L" + C_ENTITYBEAN + ";", true);
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
