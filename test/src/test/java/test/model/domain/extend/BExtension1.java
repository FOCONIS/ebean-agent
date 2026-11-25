package test.model.domain.extend;

import io.ebean.annotation.EbeanComponent;
import io.ebean.bean.NotEnhancedException;
import io.ebean.bean.extend.EntityExtension;

/**
 * @author Roland Praml, FOCONIS AG
 */
@EntityExtension({BEntityBaseAbstract.class, BEntityExtendable.class})
@EbeanComponent
public class BExtension1 extends AbstractExtension {

  String foo;

  Boolean baz = Boolean.TRUE;

  public static BExtension1 get(BEntityBaseAbstract obj) {
    throw new NotEnhancedException();
  }
  public static BExtension1 get(BEntityExtendable obj) {
    throw new NotEnhancedException();
  }

  public Boolean getBaz() {
    return baz;
  }

  public String getFoo() {
    return foo;
  }

  public void setFoo(String foo) {
    this.foo = foo;
  }
}
