package test.model;

import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public class NoEnhanceMappedSuper {

  static String oneStatic;

  transient String oneInstance;

}
