package test.model.domain.extend;

import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

/**
 * @author Roland Praml, FOCONIS AG
 */
@MappedSuperclass
public abstract class BEntityMappedSuper {

  @Id
  Long id;

}
