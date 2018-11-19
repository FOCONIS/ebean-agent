package test.enhancement;

import org.testng.annotations.Test;

import io.ebean.bean.EntityBean;
import test.model.FocTestModel;

import static org.testng.Assert.*;

public class FoconisTest extends BaseTest {

  
  @Test
  public void testBasic() {
    FocTestModel m = new FocTestModel();
    
    m.setName("   Hello   ");
    m.setPercent(105);
    
    
    assertEquals(m.getName(), "Hello");
    assertEquals(m.getPercent(), 100);
    
  }
  
  @Test
  public void testDefault() {
    FocTestModel m = new FocTestModel();
    assertNotNull(m.getTest());
  }
  
  @Test
  public void testDefault2() {
    FocTestModel proto = new FocTestModel();
    FocTestModel m = (FocTestModel) ((EntityBean)proto)._ebean_newInstance();
    
    assertNotNull(m.getTest());
  }
 
}
