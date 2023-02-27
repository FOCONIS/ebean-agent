package test.model.domain.extend;

import io.ebean.annotation.EbeanComponent;
import io.ebean.annotation.Normalize;
import test.normalize.Trimmer;

import javax.persistence.MappedSuperclass;

/**
 * @author Noemi Praml, FOCONIS AG
 */
@Normalize(Trimmer.class)
@EbeanComponent
public abstract class AbstractExtension {

  protected void foo() {};

}
