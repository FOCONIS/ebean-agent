package io.ebean.enhance.common;

import io.ebean.DB;
import io.ebean.bean.EntityBean;
import io.ebean.bean.ExtensionAccessors;
import io.ebean.bean.InterceptReadOnly;
import io.ebean.bean.InterceptReadWrite;
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
    new BEntityBase();

    BExtension1 ext = new BExtension1();
    assertThat(ext).isInstanceOf(EntityBean.class);

    BExtension1 extension = BExtension1.get(new BEntityBase());
    assertThat(extension.getBaz()).isTrue();

    Field field = BEntityBaseAbstract.class.getDeclaredField("_ebean_extension_accessors");
    Object ret = field.get(null);
    System.out.println(ret);
    BEntityBaseAbstract.class.getDeclaredField("_ebean_extension_storage");

    BEntityBase.class.getDeclaredField("_ebean_extension_accessors");
    try {
      BEntityBase.class.getDeclaredField("_ebean_extension_storage");
      fail("exception expected");
    } catch (NoSuchFieldException e) {
    }

    BEntityBase base = new BEntityBase();
    ExtensionAccessors info = ((EntityBean) base)._ebean_getExtensionAccessors();
    //assertThat(info.getPropertyLength()).isEqualTo(3);

    ret = ((EntityBean) base)._ebean_getExtension(info.iterator().next());
    assertThat(ret).isNotNull();
    base = DB.getDefault().createEntityBean(BEntityBase.class);
    assertThat(base._ebean_getExtensions()).hasSize(2);

    DB.save(base);

    base = DB.find(BEntityBase.class).setReadOnly(false).where().idEq(base.getId()).findOne();
    assertThat(((EntityBean)base)._ebean_getIntercept()).isInstanceOf(InterceptReadWrite.class);
    assertThat(base._ebean_getExtensions()).hasSize(2);

    base = DB.find(BEntityBase.class).setReadOnly(true).setDisableLazyLoading(true).where().idEq(base.getId()).findOne();
    assertThat(((EntityBean)base)._ebean_getIntercept()).isInstanceOf(InterceptReadOnly.class);
    assertThat(base._ebean_getExtensions()).hasSize(2);
  }
}
