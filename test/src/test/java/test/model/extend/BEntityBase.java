package test.model.extend;

import io.ebean.bean.EntityBean;
import io.ebean.bean.extend.ExtensionInfo;

import javax.persistence.Entity;
import javax.persistence.Transient;

/**
 * @author Roland Praml, FOCONIS AG
 */
@Entity
public class BEntityBase extends BEntityBaseAbstract {

  String name;

}
