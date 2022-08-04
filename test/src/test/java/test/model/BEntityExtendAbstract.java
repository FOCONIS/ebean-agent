package test.model;

import io.ebean.bean.extend.ExtendableBean;
import io.ebean.bean.extend.ExtensionInfo;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * @author Roland Praml, FOCONIS AG
 */
@MappedSuperclass
public abstract class BEntityExtendAbstract implements ExtendableBean {
  public static ExtensionInfo _ebean_extensions = new ExtensionInfo(BEntityExtendAbstract.class, null);

  public ExtensionInfo _ebean_getExtensionInfos() {
    return _ebean_extensions;
  }
  @Id
  Long id;
}
