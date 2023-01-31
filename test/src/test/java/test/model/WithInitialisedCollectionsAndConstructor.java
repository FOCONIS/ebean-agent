package test.model;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class WithInitialisedCollectionsAndConstructor extends BaseEntity {

  String name;

  Date whenStart;

  @OneToMany(cascade = CascadeType.PERSIST)
  List<Contact> contacts = new ArrayList<>();

  public WithInitialisedCollectionsAndConstructor(List<Contact> add) {
    contacts.addAll(add);
  }

  @Override
  public String toString() {
    return "id:"+id+" name:"+name;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Date getWhenStart() {
    return whenStart;
  }

  public void setWhenStart(Date whenStart) {
    this.whenStart = whenStart;
  }

  public List<Contact> getContacts() {
    return contacts;
  }

  public void setContacts(List<Contact> contacts) {
    this.contacts = contacts;
  }
}
