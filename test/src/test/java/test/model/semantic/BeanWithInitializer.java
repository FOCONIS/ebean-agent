package test.model.semantic;

import io.ebean.annotation.DbJson;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

/**
 * Bean with final properties (similar to record)
 *
 * @author Roland Praml, FOCONIS AG
 */
@Entity
public class BeanWithInitializer {
  @Id
  private UUID id;
  private String name;

  @Transient
  private final Collection<String> coll1 = new HashSet<>();

  @Transient
  private final Collection<String> coll2;

  {
    coll2 = new HashSet<>();
  }

  @Transient
  private final Collection<String> coll3;


  public BeanWithInitializer(UUID id) {
    this.coll3 = new HashSet<>(); // this would be really tricky
    this.id = id;
  }

  public BeanWithInitializer(String name) {
    this.coll3 = new ArrayList<>();
    this.name = name;
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Collection<String> getColl1() {
    return coll1;
  }

  public Collection<String> getColl2() {
    return coll2;
  }

  public Collection<String> getColl3() {
    return coll3;
  }

}
