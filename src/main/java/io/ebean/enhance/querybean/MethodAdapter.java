package io.ebean.enhance.querybean;

import io.ebean.enhance.common.EnhanceContext;
import io.ebean.enhance.asm.MethodVisitor;
import io.ebean.enhance.asm.Opcodes;

/**
 * Adapter that changes GETFIELD calls to type query beans to instead use the generated
 * 'property access' methods.
 */
public class MethodAdapter extends MethodVisitor implements Opcodes {

  private final EnhanceContext enhanceContext;

  private final ClassInfo classInfo;

  public MethodAdapter(MethodVisitor mv, EnhanceContext enhanceContext, ClassInfo classInfo) {
    super(ASM5, mv);
    this.enhanceContext = enhanceContext;
    this.classInfo = classInfo;
  }

  @Override
  public void visitFieldInsn(int opcode, String owner, String name, String desc) {
    if (opcode == GETFIELD && isDescriptorBean(owner) && !isDescriptorBean(classInfo.getClassName())) {
      enhanceContext.log(1, "desc enhancing: " + owner + " " + classInfo.getClassName() + " ", desc);

      classInfo.addGetFieldIntercept(owner, name);
      mv.visitMethodInsn(INVOKEVIRTUAL, owner, "_" + name, "()" + desc, false);

    } else if (opcode == GETFIELD && enhanceContext.isQueryBean(owner)) {
      classInfo.addGetFieldIntercept(owner, name);
      mv.visitMethodInsn(INVOKEVIRTUAL, owner, "_" + name, "()" + desc, false);
    } else {
      super.visitFieldInsn(opcode, owner, name, desc);
    }
  }
  
  /**
   * Return true if the owner class is a descriptor bean.
   * <p>
   * If true typically means the caller needs to change GETFIELD calls to instead invoke the generated
   * 'property access' methods.
   * </p>
   */
  public boolean isDescriptorBean(String owner) {
    // quick & dirty - all classes that mathces the pattern
    // de/foconis/.../descriptor/D... or
    // de/foconis/.../descriptor/assoc/D... are descriptors
    if (owner.startsWith("de/foconis/")) {
      int subPackagePos = owner.lastIndexOf("/descriptor/");
      if (subPackagePos > -1) {
        String suffix = owner.substring(subPackagePos);
        return (suffix.startsWith("/descriptor/D") || suffix.startsWith("/descriptor/assoc/D"));
      }
    }
    return false;
  }

}
