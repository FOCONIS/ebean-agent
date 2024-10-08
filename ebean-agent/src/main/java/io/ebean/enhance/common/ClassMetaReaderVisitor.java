package io.ebean.enhance.common;

import io.ebean.enhance.asm.AnnotationVisitor;
import io.ebean.enhance.asm.ClassVisitor;
import io.ebean.enhance.asm.FieldVisitor;
import io.ebean.enhance.asm.MethodVisitor;
import io.ebean.enhance.asm.Opcodes;

import static io.ebean.enhance.Transformer.EBEAN_ASM_VERSION;

/**
 * Used by ClassMetaReader to read information about a class.
 * <p>
 * Reads the information by visiting the byte codes rather than using
 * ClassLoader. This gets around issues where the annotations are not seen
 * (silently ignored) if they are not in the class path.
 * </p>
 */
class ClassMetaReaderVisitor extends ClassVisitor implements EnhanceConstants {

  private final ClassMeta classMeta;
  private final boolean readMethodMeta;

  ClassMetaReaderVisitor(boolean readMethodMeta, EnhanceContext context) {
    super(EBEAN_ASM_VERSION);
    this.readMethodMeta = readMethodMeta;
    this.classMeta = context.createClassMeta();
  }

  public ClassMeta getClassMeta() {
    return classMeta;
  }

  public boolean isLog(int level) {
    return classMeta.isLog(level);
  }

  public void log(String msg) {
    classMeta.log(msg);
  }

  /**
  * Create the class definition replacing the className and super class.
  */
  @Override
  public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
    classMeta.setClassName(name, superName);
    for (int i = 0; i < interfaces.length; i++) {
      if (interfaces[i].equals(C_EXTENDABLE_BEAN)) {
        classMeta.setExtendableBeanInterface(true);
      }
    }
    super.visit(version, access, name, signature, superName, interfaces);
  }

  @Override
  public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
    classMeta.addClassAnnotation(desc);
    AnnotationVisitor av = super.visitAnnotation(desc, visible);
    if (desc.equals(TRANSACTIONAL_ANNOTATION)) {
      // we have class level Transactional annotation
      // which will act as default for all methods in this class
      return new AnnotationInfoVisitor(null, classMeta.transactionalAnnotationInfo(), av);
    } else if (desc.equals(NORMALIZE_ANNOTATION)) {

      // we have class level Normalize annotation
      // which will act as default for all methods in this class
      return new AnnotationInfoVisitor(null, classMeta.normalizeAnnotationInfo(), av);
    } else if (desc.equals(ENTITY_EXTENSION_ANNOTATION)) {

      // we have class level Normalize annotation
      // which will act as default for all methods in this class
      return new AnnotationInfoVisitor(null, classMeta.extensionAnnotationInfo(), av);
    } else {
      return av;
    }
  }

  /**
  * The ebeanIntercept field is added once but thats all. Note the other
  * fields are defined in the superclass.
  */
  @Override
  public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
    if ((access & Opcodes.ACC_STATIC) != 0) {
      // no interception of static fields
      if (isLog(4)) {
        log("Skip static field " + name);
      }
      return super.visitField(access, name, desc, signature, value);
    }
    if ((access & Opcodes.ACC_TRANSIENT) != 0) {
      if (isLog(4)) {
        log("Skip transient field " + name);
      }
      // no interception of transient fields
      return super.visitField(access, name, desc, signature, value);
    }
    return classMeta.createLocalFieldVisitor(name, desc);
  }

  /**
  * Look for equals/hashCode implementations.
  */
  @Override
  public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
    boolean staticAccess = ((access & Opcodes.ACC_STATIC) != 0);
    if (name.equals("hashCode") && desc.equals("()I")) {
      classMeta.setHasEqualsOrHashcode(true);
    }
    if (name.equals("equals") && desc.equals("(Ljava/lang/Object;)Z")) {
      classMeta.setHasEqualsOrHashcode(true);
    }
    if (name.equals("toString") && desc.equals("()Ljava/lang/String;")) {
      classMeta.setHasToString();
    }
    MethodVisitor mv = super.visitMethod(access, name, desc, signature, exceptions);
    if (!staticAccess && readMethodMeta){
      return classMeta.createMethodVisitor(mv, name, desc);

    } else {
      // not interested in the methods...
      return mv;
    }
  }

}
