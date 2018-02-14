package test.enhancement;


import io.ebean.typequery.PLong;
import org.testng.annotations.Test;

import test.model.domain.Order;
import test.model.domain.query.QAddress;
import test.model.domain.query.QOrder;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertSame;

public class QueryBeanEnhanceTest extends BaseTest {

  @Test
  public void test() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

    QAddress qAddress = new QAddress();
    PLong<QAddress> version1 = qAddress.version;
    assertNull(version1);

    Class<?>[] cls= {};
    Method method = qAddress.getClass().getMethod("_version", cls);
    Object result = method.invoke(qAddress, (Object[])null);
    assertNotNull(result);

    PLong<QAddress> version2 = qAddress.version;
    assertNotNull(version2);
    assertSame(result, version2);

    System.out.println("done");

  }
  
  @Test
  public void testQueryBeanInEntityBean() {
	  assertNotNull(new Order().finder());
	  assertNotNull(Order.staticFinder());
  }
}
