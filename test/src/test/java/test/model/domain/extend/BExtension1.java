package test.model.domain.extend;

import io.ebean.annotation.EbeanComponent;
import io.ebean.bean.NotEnhancedException;
import io.ebean.bean.extend.EntityExtension;
import io.ebean.bean.extend.ExtensionAccessor;

/**
 * @author Roland Praml, FOCONIS AG
 */
@EntityExtension(BEntityBaseAbstract.class)
@EbeanComponent
public class BExtension1 {

  String foo;
  public static final ExtensionAccessor _test_acc_foo = null;

  public static BExtension1 get(BEntityBaseAbstract obj) {
    throw new NotEnhancedException();
  }

  public static BExtension1 get2(BEntityBaseAbstract obj) {
    return _test_acc_foo.getExtension(obj);
  }
}
