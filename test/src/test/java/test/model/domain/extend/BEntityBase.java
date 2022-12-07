package test.model.domain.extend;

import javax.persistence.Entity;

/**
 * @author Roland Praml, FOCONIS AG
 */
@Entity
public class BEntityBase extends BEntityBaseAbstract {

  String name;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
