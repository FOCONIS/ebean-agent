package test.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class NoEnhanceMappedSuperEntity extends NoEnhanceMappedSuper {

  @Id
  long id;

  String name;
}
