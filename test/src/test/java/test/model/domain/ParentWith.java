package test.model.domain;

import jakarta.persistence.Entity;

@Entity
public class ParentWith extends ParentWithDbParam {

  String name;

  public ParentWith(String name) {
    super("db");
    this.name = name;
  }
}
