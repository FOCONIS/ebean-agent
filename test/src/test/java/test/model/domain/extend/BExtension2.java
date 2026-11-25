package test.model.domain.extend;

import io.ebean.annotation.EbeanComponent;
import io.ebean.bean.extend.EntityExtension;

/**
 * @author Roland Praml, FOCONIS AG
 */
@EntityExtension(BEntityBaseAbstract.class)
@EbeanComponent
public class BExtension2 extends AbstractExtension {


  String bar;


}
