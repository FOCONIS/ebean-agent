package test.enhancement;

import io.qameta.allure.Description;
import org.junit.jupiter.api.Test;
import test.model.PostJsonBean;
import test.model.PostJsonBean.JsonValue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class PostJsonGetterTest {

  /**
   * Beim Lesen eines JSON-Objekts und einer JSON-Liste erhält der Hook jeweils den
   * aktuellen Wert und den Property-Namen. Das Lesen eines Strings löst ihn nicht aus.
   */
  @Description
  @Test
  void callsHookForJsonPropertiesOnly() {
    var json = new JsonValue();
    var values = List.of(new JsonValue(), new JsonValue());
    var calls = new ArrayList<Map.Entry<String, Object>>();
    var bean = new PostJsonBean(json, values, "plain", (field, value) -> calls.add(Map.entry(field, value)));

    assertThat(bean.getJson()).isSameAs(json);
    assertThat(bean.getJson()).isSameAs(json);
    assertThat(bean.getValues()).isSameAs(values);
    assertThat(bean.getName()).isEqualTo("plain");
    assertThat(calls).containsExactly(Map.entry("json", json), Map.entry("json", json), Map.entry("values", values));
  }
}
