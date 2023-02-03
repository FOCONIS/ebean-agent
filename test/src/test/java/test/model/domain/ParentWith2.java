package test.model.domain;

import jakarta.persistence.Entity;

@Entity
public class ParentWith2 extends ParentWithDbParam {

  String name;

  public ParentWith2() {
    super("db");
  }
}
