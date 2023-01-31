package test.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class BExtends extends BaseWithEqualsAndNoIdEntity {

  @Id
  Long id;

  String name;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

}
