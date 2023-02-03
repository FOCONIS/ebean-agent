package test.model;

import jakarta.persistence.Entity;

@Entity
public class AExtends extends BaseWithEqualsEntity {

  String name;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

}
