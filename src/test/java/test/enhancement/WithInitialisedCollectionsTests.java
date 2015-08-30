package test.enhancement;

import com.avaje.ebean.bean.EntityBean;
import org.junit.Test;
import test.model.Contact;
import test.model.WithInitialisedCollectionAndAtTransient;
import test.model.WithInitialisedCollectionAndTransient;
import test.model.WithInitialisedCollections;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class WithInitialisedCollectionsTests extends BaseTest {


  @Test
  public void test() {

    WithInitialisedCollections bean = new WithInitialisedCollections();
    assertNotNull(bean);

    assertTrue(bean instanceof EntityBean);

    EntityBean eb = (EntityBean)bean;
    String[] props = eb._ebean_getPropertyNames();

    assertEquals(9, props.length);
    assertEquals("contacts", props[5]);
    assertEquals("myset", props[6]);
    assertEquals("myLinkedSet", props[7]);
    assertEquals("strings", props[8]);

    Object val5 = eb._ebean_getField(5);
    Object val6 = eb._ebean_getField(6);
    Object val7 = eb._ebean_getField(7);
    Object val8 = eb._ebean_getField(8);
    assertNotNull(val8);
    assertNull(val5);
    assertNull(val6);
    assertNull(val7);

    List<Contact> contacts = bean.getContacts();
    assertNotNull(contacts);

    assertNotNull(bean.getMyset());
    assertNotNull(bean.getMyLinkedSet());

  }


  @Test
  public void test_withTransient() {

    WithInitialisedCollectionAndTransient bean = new WithInitialisedCollectionAndTransient();
    assertNotNull(bean);

    assertTrue(bean instanceof EntityBean);

    assertNotNull(bean.getBuffer());

    EntityBean eb = (EntityBean) bean;
    String[] props = eb._ebean_getPropertyNames();

    assertEquals(6, props.length);
  }

  @Test
  public void test_withAtTransient() {

    WithInitialisedCollectionAndAtTransient bean = new WithInitialisedCollectionAndAtTransient();
    assertNotNull(bean);

    assertTrue(bean instanceof EntityBean);

    assertNotNull(bean.getBuffer());

    EntityBean eb = (EntityBean) bean;
    String[] props = eb._ebean_getPropertyNames();

    assertEquals(7, props.length);
  }
}
