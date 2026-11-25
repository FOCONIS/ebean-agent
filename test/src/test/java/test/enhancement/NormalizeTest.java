package test.enhancement;

import io.qameta.allure.Description;
import org.junit.jupiter.api.Test;
import test.model.normalize.ClassNormalizeModel;
import test.model.normalize.FieldNormalizeModel;
import test.model.normalize.InheritClassNormalizeModel;

import static org.assertj.core.api.Assertions.assertThat;

class NormalizeTest {

  /**
   * Der Klassenstandard trimmt Strings, lässt null unverändert und kann am Feld
   * deaktiviert oder durch eine geordnete Kette von Normalisierern ersetzt werden.
   */
  @Description
  @Test
  void appliesClassDefaultAndFieldOverrides() {
    var model = new ClassNormalizeModel();
    model.setName(null);
    assertThat(model.getName()).isNull();
    model.setName("   Hello   ");
    model.setNoNormalize("   Hello   ");
    model.setZerosNormalize("   00001234   ");
    model.setBothNormalize("   00001234   ");

    assertThat(model.getName()).isEqualTo("Hello");
    assertThat(model.getNoNormalize()).isEqualTo("   Hello   ");
    assertThat(model.getZerosNormalize()).isEqualTo("   00001234   ");
    assertThat(model.getBothNormalize()).isEqualTo("1234");
  }

  /**
   * Ein am primitiven Feld deklarierter Normalisierer rundet den Wert vor dem Speichern ab.
   */
  @Description
  @Test
  void appliesFieldNormalizerToPrimitive() {
    var model = new FieldNormalizeModel();
    model.setEvenNumberOnly(3);
    assertThat(model.getEvenNumberOnly()).isEqualTo(2);
  }

  /**
   * Eine Entity ohne eigene Klassenannotation übernimmt den Normalisierer ihrer MappedSuperclass.
   */
  @Description
  @Test
  void inheritsClassNormalizer() {
    var model = new InheritClassNormalizeModel();
    model.setName("   Hello   ");
    assertThat(model.getName()).isEqualTo("Hello");
  }
}
