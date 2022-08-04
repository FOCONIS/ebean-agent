package test.model;

import io.ebean.bean.extend.ExtendableBean;
import io.ebean.bean.extend.ExtensionInfo;

import javax.persistence.Entity;
import javax.persistence.MappedSuperclass;

/**
 * @author Roland Praml, FOCONIS AG
 */
@Entity
public class BEntityExtend extends BEntityExtendAbstract {

  public static ExtensionInfo _ebean_extensions = new ExtensionInfo(BEntityExtend.class, BEntityExtendAbstract._ebean_extensions);

  @Override
  public ExtensionInfo _ebean_getExtensionInfos() {
    return _ebean_extensions;
  }

  String name;


}
