package test.model;

import jakarta.persistence.*;

@Entity
public class PersistentFileContent {

  @Id
  private Long id;

  @OneToOne(cascade=CascadeType.ALL)
  private PersistentFile persistentFile;

  @Lob
  private byte[] content;

  public PersistentFileContent(){
  }

  public PersistentFileContent(byte[] content) {
    super();
    this.content = content;
  }

  public Long getId() {
    return id;
  }

  public PersistentFile getPersistentFile() {
    return persistentFile;
  }

  public void setPersistentFile(PersistentFile persistentFile) {
    this.persistentFile = persistentFile;
  }

  public byte[] getContent() {
    return content;
  }

  public void setContent(byte[] content) {
    this.content = content;
  }
}
