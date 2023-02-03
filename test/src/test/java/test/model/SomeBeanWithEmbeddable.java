package test.model;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class SomeBeanWithEmbeddable {

  @Id
  Long id;

  String name;

  @Embedded
  MyEmbeddedBean one;

  @Embedded
  MyEmbeddedBean two;

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

  public MyEmbeddedBean getOne() {
    return one;
  }

  public void setOne(MyEmbeddedBean one) {
    this.one = one;
  }

  public MyEmbeddedBean getTwo() {
    return two;
  }

  public void setTwo(MyEmbeddedBean two) {
    this.two = two;
  }
}
