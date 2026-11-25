package test.model.domain.extend;

import io.ebean.annotation.Normalize;
import io.ebean.bean.extend.EntityExtensionSuperclass;
import test.normalize.Trimmer;

/**
 * @author Noemi Praml, FOCONIS AG
 */
@Normalize(Trimmer.class)
@EntityExtensionSuperclass
public abstract class AbstractExtension {

  protected void foo() {};

}
