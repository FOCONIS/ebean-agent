package test.model;

import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class SomeAbstractClass {

  public abstract String sayHello();
}
