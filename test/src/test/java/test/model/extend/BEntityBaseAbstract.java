package test.model.extend;

import io.ebean.bean.EntityBean;
import io.ebean.bean.EntityBeanIntercept;
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


  private EntityBean[] _ebean__extensionStorage= new EntityBean[_ebean_getExtensionInfo().size()];


  @Id
  Long id;

  public EntityBean _ebean_getExtension(int index, EntityBeanIntercept ebi) {


    if (_ebean__extensionStorage[index] == null) {
      _ebean__extensionStorage[index] =  _ebean_getExtensionInfo().get(index).createInstance(_ebean_getExtensionInfo().getOffset(index), ebi);
    }
    return _ebean__extensionStorage[index];
  }
}
