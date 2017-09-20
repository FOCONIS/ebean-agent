package de.foconis.model.descriptor;

import de.foconis.core.domain.BaseModel;
import de.foconis.core.domain.base.PropertyImpl;

public class DFocTestModel {
  public static final DFocTestModel INSTANCE = new DFocTestModel();
  
  public PropertyImpl<String> _name() {
    return new PropertyImpl<String>() {

      @Override
      public String normalize(BaseModel model, String value) {
        return value.trim();
      }
      
    };
  }
  
  public PropertyImpl<Integer> _percent() {
    return new PropertyImpl<Integer>() {

      @Override
      public Integer normalize(BaseModel model, Integer value) {
        System.out.println("Normalizing " + value);
        if (value > 100) {
          return 100;
        } else {
          return value;
        }
      }
      
    };
  }
}
