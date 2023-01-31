package test.model.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.util.UUID;

@Entity
public class DbPerson {

  @Id
  UUID id;

  String name;

  @ManyToOne
  DbGroup group;

  public UUID getId() {
    return id;
  }

  public DbPerson id(UUID id) {
    this.id = id;
    return this;
  }

  public String getName() {
    return name;
  }

  public DbPerson name(String name) {
    this.name = name;
    return this;
  }
}
