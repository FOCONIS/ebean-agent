package test.model.domain.extend;

import io.ebean.bean.extend.ExtendableBean;

import jakarta.persistence.Entity;

/**
 * @author Roland Praml, FOCONIS AG
 */
@Entity
public class BEntityExtendable extends BEntityMappedSuper implements ExtendableBean {

  String name;

}
