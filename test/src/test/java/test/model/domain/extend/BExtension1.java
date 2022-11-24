package test.model.domain.extend;

import io.ebean.annotation.EbeanComponent;
import io.ebean.bean.NotEnhancedException;
import io.ebean.bean.extend.EntityExtension;

/**
 * @author Roland Praml, FOCONIS AG
 */
@EntityExtension(BEntityBaseAbstract.class)
@EbeanComponent
public class BExtension1 {

  String foo;

  public static BExtension1 get(BEntityBaseAbstract obj) {
    throw new NotEnhancedException();
  }

}
