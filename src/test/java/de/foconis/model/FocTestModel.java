package de.foconis.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class FocTestModel {
  
  @Id
  int id;
  
  String name;
  
  int percent;
  
  private Boolean test = Boolean.FALSE; 
  
  public String getName() {
    return name;
  }
  
  public void setName(String name) {
    this.name = name;
  }
  
  public int getId() {
    return id;
  }
  
  public void setId(int id) {
    this.id = id;
  }
  
  public int getPercent() {
    return percent;
  }

  public void setPercent(int percent) {
    this.percent = percent;
  }
  
  public Boolean getTest() {
    return test;
  }
  public void setTest(Boolean test) {
    this.test = test;
  }
}
