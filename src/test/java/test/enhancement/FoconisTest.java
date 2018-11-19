package test.enhancement;

import org.testng.annotations.Test;

import test.model.normalize.ClassNormalizeModel;

import static org.testng.Assert.*;

public class FoconisTest extends BaseTest {


  @Test
  public void testClassNormalize() {
    ClassNormalizeModel m = new ClassNormalizeModel();

    m.setName(null);
    assertEquals(m.getName(), null);

    m.setName("   Hello   ");
    assertEquals(m.getName(), "Hello");


    m.setNoNormalize("   Hello   ");
    assertEquals(m.getNoNormalize(), "   Hello   ");

    m.setZerosNormalize("00001234");
    assertEquals(m.getZerosNormalize(), "1234");

    m.setZerosNormalize("   00001234   ");
    assertEquals(m.getZerosNormalize(), "   00001234   ");

    m.setBothNormalize("   00001234   ");
    assertEquals(m.getBothNormalize(), "1234");
  }


}
