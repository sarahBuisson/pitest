package org.pitest.mutationtest.filter;

import org.pitest.classpath.CodeSource;

import java.util.Properties;

public class GeneratedByAnnotationLineFilterFactory implements
        MutationFilterFactory {
  @Override
  public String description() {
    return "disable the mutation coverage for the line generated by any annotations";
  }

  @Override
  public MutationFilter createFilter(Properties props, CodeSource source, int maxMutationsPerClass) {

    return new GeneratedByAnnotationLineFilter(props, source);
  }
}
