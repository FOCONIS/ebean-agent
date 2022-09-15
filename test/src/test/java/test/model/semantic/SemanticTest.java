package test.model.semantic;

import io.ebean.DB;
import org.junit.jupiter.api.Test;
import test.enhancement.BaseTest;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class SemanticTest extends BaseTest {

  @Test
  public void testFinal() {

    UUID id = UUID.randomUUID();
    BeanWithFinalProp bean = new BeanWithFinalProp(id, "Roland");
    DB.save(bean);

    // fetch bean from DB
    bean = DB.find(BeanWithFinalProp.class, id);

    assertThat(bean.getId()).isEqualTo(id);
    assertThat(bean.getFinalName()).isEqualTo("Roland");

    DB.createUpdate(BeanWithFinalProp.class,
        "update BeanWithFinalProp set finalName ='Rob' where id = :id")
      .setParameter("id", id)
      .execute();


    DB.refresh(bean);
    // This is not expected. "name" is final and a DB.refresh would change it!
    assertThat(bean.getFinalName()).isEqualTo("Rob");
    // Possible solutuion:
    // - Throw exception in DB.refresh, when final prop will be modified
    // - Throw an error (or print as warning in the first step) in enhancement, when a final field is "enhanced"

  }

  @Test
  public void testWithInitializer() {
    UUID id1 = UUID.randomUUID();
    UUID id2 = UUID.randomUUID();

    BeanWithInitializer bean1 = new BeanWithInitializer(id1);
    bean1.setName("Roland");

    BeanWithInitializer bean2 = new BeanWithInitializer("Rob");
    bean2.setId(id2);

    assertThat(bean1.getColl1()).isInstanceOf(HashSet.class);
    assertThat(bean1.getColl2()).isInstanceOf(HashSet.class);
    assertThat(bean1.getColl3()).isInstanceOf(HashSet.class);

    assertThat(bean2.getColl1()).isInstanceOf(HashSet.class);
    assertThat(bean2.getColl2()).isInstanceOf(HashSet.class);
    assertThat(bean2.getColl3()).isInstanceOf(ArrayList.class);

    DB.save(bean1);
    DB.save(bean2);

    bean1 = DB.find(BeanWithInitializer.class, id1);
    bean2 = DB.find(BeanWithInitializer.class, id2);

    // Fetching the bean again, does not trigger the initializers
    // I also don't think, that this can be handeld by the enhancer
    // Possible solution: Print warning (or fail) when enhancing.

    assertThat(bean1.getName()).isEqualTo("Roland");
    assertThat(bean1.getColl1()).isNull();
    assertThat(bean1.getColl2()).isNull();
    assertThat(bean1.getColl3()).isNull();

    assertThat(bean2.getName()).isEqualTo("Rob");
    assertThat(bean2.getColl1()).isNull();
    assertThat(bean2.getColl2()).isNull();
    assertThat(bean2.getColl3()).isNull();
  }


  }
