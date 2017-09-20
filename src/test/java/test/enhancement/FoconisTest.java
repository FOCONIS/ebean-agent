package test.enhancement;

import org.testng.annotations.Test;

import de.foconis.model.FocTestModel;

import static org.testng.Assert.*;

public class FoconisTest extends BaseTest {

  
  @Test
  public void testBasic() {
    FocTestModel m = new FocTestModel();
    
    m.setName("   Hello   ");
    m.setPercent(105);
    
    
    assertEquals("Hello", m.getName());
    assertEquals(100, m.getPercent());
    
  }

}
