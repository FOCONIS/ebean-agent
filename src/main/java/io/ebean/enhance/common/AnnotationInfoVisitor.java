package io.ebean.enhance.common;

import io.ebean.enhance.asm.AnnotationVisitor;
import io.ebean.enhance.asm.Opcodes;

/**
 * Reads the annotation information storing it in a AnnotationInfo.
 */
public class AnnotationInfoVisitor extends AnnotationVisitor {

	private final AnnotationInfo info;
	
	private final String prefix;
	
	public AnnotationInfoVisitor(String prefix, AnnotationInfo info, AnnotationVisitor av) {
    super(Opcodes.ASM5, av);
		this.info = info;
		this.prefix = prefix;
	}
	
	public void visit(String name, Object value) {
		info.add(prefix, name, value);
		super.visit(name, value);
	}

	public AnnotationVisitor visitAnnotation(String name, String desc) {
		AnnotationVisitor av = super.visitAnnotation(name, desc);
		return create(name, av);
	}

	public AnnotationVisitor visitArray(String name) {
		AnnotationVisitor av = super.visitArray(name);
		return create(name, av);
	}

	private AnnotationInfoVisitor create(String name, AnnotationVisitor av){
		String newPrefix = prefix == null ? name: prefix+"."+name;
		return new AnnotationInfoVisitor(newPrefix, info, av);
	}
	
	public void visitEnd() {
		if (av != null) {
			av.visitEnd();
		}
	}

	public void visitEnum(String name, String desc, String value) {
		
		info.addEnum(prefix, name, desc, value);
		if (av != null) {
			av.visitEnum(name, desc, value);
		}
	}
	
}
