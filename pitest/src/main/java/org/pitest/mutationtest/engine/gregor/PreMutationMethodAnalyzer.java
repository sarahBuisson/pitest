package org.pitest.mutationtest.engine.gregor;

import java.util.Set;

import org.objectweb.asm.*;
import org.pitest.functional.F;
import org.pitest.functional.FCollection;

public class PreMutationMethodAnalyzer extends MethodVisitor {

  private final Set<String>          loggingClasses;

  private int                        currentLineNumber;
  private final PremutationClassInfo classInfo;
  private final ClassContext      context;

  public PreMutationMethodAnalyzer(final Set<String> loggingClasses,
      final PremutationClassInfo classInfo, ClassContext context) {
    super(Opcodes.ASM5, new TryWithResourcesMethodVisitor(classInfo));
    this.classInfo = classInfo;
    this.loggingClasses = loggingClasses;
    this.context = context;
  }

  @Override
  public void visitMethodInsn(final int opcode, final String owner,
      final String name, final String desc, boolean itf) {

    if (FCollection.contains(this.loggingClasses, matches(owner))) {
      this.classInfo.registerLineToAvoid(this.currentLineNumber);
    }
    for(Integer lineNumber:context.getExcludedLineNumbers()) {
      this.classInfo.registerLineToAvoid(lineNumber);
    }
    super.visitMethodInsn(opcode, owner, name, desc, itf);
  }

  private static F<String, Boolean> matches(final String owner) {
    return new F<String, Boolean>() {
      @Override
      public Boolean apply(final String a) {
        return owner.startsWith(a);
      }

    };
  }

  @Override
  public void visitLineNumber(final int line, final Label start) {
    this.currentLineNumber = line;
    super.visitLineNumber(line, start);
  }

}
