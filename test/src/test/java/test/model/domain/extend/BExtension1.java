package test.model.domain.extend;

import io.ebean.bean.NotEnhancedException;
import io.ebean.bean.extend.EntityExtension;
import io.ebean.bean.extend.ExtensionAccessor;
import io.ebean.bean.extend.ExtensionManager;

/**
 * @author Roland Praml, FOCONIS AG
 */
@EntityExtension(BEntityBaseAbstract.class)
public class BExtension1 {

  public static final ExtensionAccessor _extension_id = ExtensionManager.extend(BEntityBaseAbstract.class, BExtension1.class);

  String foo;

  public static BExtension1 get(BEntityBaseAbstract obj) {
    throw new NotEnhancedException();
  }
}
