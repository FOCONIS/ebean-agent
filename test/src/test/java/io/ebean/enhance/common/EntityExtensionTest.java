package io.ebean.enhance.common;

import io.ebean.DB;
import io.ebean.bean.EntityBean;
import io.ebean.bean.extend.ExtensionInfo;
import org.junit.jupiter.api.Test;
import test.model.extend.BEntityBase;
import test.model.extend.BEntityBaseAbstract;

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

    Field field = BEntityBaseAbstract.class.getDeclaredField("_ebean_extensions");
    field.get(null);
    BEntityBaseAbstract.class.getDeclaredField("_ebean_extension_storage");

    BEntityBase.class.getDeclaredField("_ebean_extensions");
    try {
      BEntityBase.class.getDeclaredField("_ebean_extension_storage");
      fail("exception expected");
    } catch (NoSuchFieldException e) {}

    BEntityBase base = new BEntityBase();
    ExtensionInfo info = base._ebean_getExtensionInfo();
    //assertThat(info.getPropertyLength()).isEqualTo(3);

    EntityBean ret = base._ebean_getExtension(0, null);
    System.out.println(ret);
  }
}
