package test.model.normalize;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;


@Entity
public class InheritClassNormalizeModel extends BaseNormalizeModel {

  @Id
  int id;

  String name;


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

}
