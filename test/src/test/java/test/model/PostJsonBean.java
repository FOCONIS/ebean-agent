package test.model;

import io.ebean.annotation.DbJson;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;
import java.util.function.BiConsumer;

@Entity
public class PostJsonBean {

  public static class JsonValue {
    public String value;
  }

  @Id
  Long id;
  @DbJson
  JsonValue json;
  @DbJson
  @Column(name = "json_values")
  List<JsonValue> values;
  String name;
  transient BiConsumer<String, Object> callback;

  public PostJsonBean(JsonValue json, List<JsonValue> values, String name, BiConsumer<String, Object> callback) {
    this.json = json;
    this.values = values;
    this.name = name;
    this.callback = callback;
  }

  public JsonValue getJson() {
    return json;
  }

  public List<JsonValue> getValues() {
    return values;
  }

  public String getName() {
    return name;
  }

  public static void postJsonGet(Object bean, Object value, String fieldName) {
    if (bean instanceof PostJsonBean) {
      BiConsumer<String, Object> callback = ((PostJsonBean) bean).callback;
      if (callback != null) {
        callback.accept(fieldName, value);
      }
    }
  }
}
