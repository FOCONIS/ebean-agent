package test.model;

import jakarta.persistence.Entity;

@Entity
public class PBean extends PBase {

  String name;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

}
