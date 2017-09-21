package test.enhancement;

import org.testng.annotations.Test;

import de.foconis.model.FocTestModel;
import io.ebean.bean.ConstructorMarker;
import io.ebean.bean.EntityBean;

import static org.testng.Assert.*;

import java.lang.reflect.Constructor;

public class FoconisTest extends BaseTest {

  
  @Test
  public void testBasic() {
    FocTestModel m = new FocTestModel();
    
    m.setName("   Hello   ");
    m.setPercent(105);
    
    
    assertEquals("Hello", m.getName());
    assertEquals(100, m.getPercent());
    
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
  
  
  @Test
  public void testDefault3() throws Exception {
    Constructor<FocTestModel> ctor = FocTestModel.class.getConstructor(ConstructorMarker.class);
    FocTestModel m = ctor.newInstance(new Object[1]);
    
    assertNull(m.getTest());
  }

}
