package test.model.domain.extend;

import io.ebean.bean.extend.EntityExtension;
import io.ebean.bean.extend.ExtensionAccessor;
import io.ebean.bean.extend.ExtensionManager;

/**
 * @author Roland Praml, FOCONIS AG
 */
@EntityExtension()
public class BExtension2 {

  public static final ExtensionAccessor _extension_id = ExtensionManager.extend(BEntityBaseAbstract.class, BExtension2.class);

  String bar;


}
