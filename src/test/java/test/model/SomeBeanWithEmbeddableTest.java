package test.model;

import org.testng.annotations.Test;
import test.enhancement.BaseTest;

public class SomeBeanWithEmbeddableTest extends BaseTest {


  @Test
  public void create() {

    SomeBeanWithEmbeddable bean = new SomeBeanWithEmbeddable();
    bean.setOne(new MyEmbeddedBean());
    bean.setTwo(new MyEmbeddedBean());
    bean.getOne().setName("foo");

  }
}
