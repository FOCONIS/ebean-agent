package io.ebean.enhance.common;

import org.junit.jupiter.api.Test;
import test.model.extend.BEntityBase;
import test.model.extend.BEntityBaseAbstract;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.fail;

/**
 * @author Roland Praml, FOCONIS AG
 */
public class EntityExtensionTest {


  @Test
  void test() throws Exception {

    Field field = BEntityBaseAbstract.class.getDeclaredField("_ebean_extensions");
    field.get(null);
    BEntityBaseAbstract.class.getDeclaredField("_ebean_extension_storage");

    BEntityBase.class.getDeclaredField("_ebean_extensions");
    try {
      BEntityBase.class.getDeclaredField("_ebean_extension_storage");
      fail("exception expected");
    } catch (NoSuchFieldException e) {}
  }
}
