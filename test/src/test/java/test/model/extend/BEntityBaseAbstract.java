package test.model.extend;

import io.ebean.bean.EntityBean;
import io.ebean.bean.extend.ExtendableBean;
import io.ebean.bean.extend.ExtensionInfo;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

/**
 * @author Roland Praml, FOCONIS AG
 */
@MappedSuperclass
public abstract class BEntityBaseAbstract implements ExtendableBean {

   @Id
  Long id;
}
