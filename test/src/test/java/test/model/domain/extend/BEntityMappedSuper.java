package test.model.domain.extend;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * @author Roland Praml, FOCONIS AG
 */
@MappedSuperclass
public abstract class BEntityMappedSuper {

  @Id
  Long id;

}
