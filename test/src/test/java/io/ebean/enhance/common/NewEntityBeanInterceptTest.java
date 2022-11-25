package io.ebean.enhance.common;

import io.ebean.ValuePair;
import io.ebean.bean.BeanDiffVisitor;
import io.ebean.bean.BeanLoader;
import io.ebean.bean.EntityBean;
import io.ebean.bean.EntityBeanIntercept;
import io.ebean.bean.InterceptReadOnly;
import io.ebean.bean.MutableValueInfo;
import io.ebean.bean.MutableValueNext;
import io.ebean.bean.NodeUsageCollector;
import io.ebean.bean.PersistenceContext;
import io.ebean.bean.PreGetterCallback;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import test.model.BExtends;

import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Roland Praml, FOCONIS AG
 */
public class NewEntityBeanInterceptTest {

  @Test
  void test() {
    BExtends bean = new BExtends();
    EntityBean bean1 = (EntityBean) bean;

    EntityBean bean2 = (EntityBean) bean1._ebean_newExtendedInstance(1, bean1);

    assertEquals(bean2._ebean_getIntercept(), bean1._ebean_getIntercept());


  }
}
