package test.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class SomeExtendsAbstract extends SomeAbstractClass implements SomeInterface {

  @Id
  Long id;

  String name;

  @Override
  public String sayHello() {
    return "hello " + name;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
