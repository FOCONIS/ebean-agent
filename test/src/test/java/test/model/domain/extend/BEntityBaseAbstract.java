package test.model.domain.extend;

import io.ebean.bean.extend.ExtendableBean;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * @author Roland Praml, FOCONIS AG
 */
@MappedSuperclass
public abstract class BEntityBaseAbstract implements ExtendableBean {

  @Id
  Long id;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }
}
