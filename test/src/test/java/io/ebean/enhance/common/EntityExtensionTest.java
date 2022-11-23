package io.ebean.enhance.common;

import io.ebean.DB;
import io.ebean.annotation.DocStore;
import io.ebean.bean.EntityBean;
import io.ebean.bean.extend.EnhancedEntityExtension;
import io.ebean.bean.extend.ExtensionInfo;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import test.model.domain.extend.BEntityBase;
import test.model.domain.extend.BEntityBaseAbstract;
import test.model.domain.extend.BExtension1;

import java.lang.reflect.Field;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * @author Roland Praml, FOCONIS AG
 */
public class EntityExtensionTest {


  @Test
  void test() throws Exception {
    DB.getDefault();

    BExtension1 ext = new BExtension1();
    assertThat(ext).isInstanceOf(EntityBean.class);

    Field field = BEntityBaseAbstract.class.getDeclaredField("_ebean_extensions");
    Object ret = field.get(null);
    System.out.println(ret);
    BEntityBaseAbstract.class.getDeclaredField("_ebean_extension_storage");

    BEntityBase.class.getDeclaredField("_ebean_extensions");
    try {
      BEntityBase.class.getDeclaredField("_ebean_extension_storage");
      fail("exception expected");
    } catch (NoSuchFieldException e) {}

    BEntityBase base = new BEntityBase();
    ExtensionInfo info = base._ebean_getExtensionInfo();
    //assertThat(info.getPropertyLength()).isEqualTo(3);

     ret = base._ebean_getExtension(0, null);
    System.out.println(ret);
  }
}
