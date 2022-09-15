package test.model.semantic;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

/**
 * Bean with final properties (similar to record)
 *
 * @author Roland Praml, FOCONIS AG
 */
@Entity
public class BeanWithFinalProp {
  @Id
  private final UUID id;
  private final String finalName;

  public BeanWithFinalProp(UUID id, String finalName) {
    this.id = id;
    this.finalName = finalName;
  }

  public UUID getId() {
    return id;
  }

  public String getFinalName() {
    return finalName;
  }
}
