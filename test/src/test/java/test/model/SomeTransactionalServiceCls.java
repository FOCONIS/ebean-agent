package test.model;

import io.ebean.DB;
import io.ebean.annotation.Transactional;
import io.ebeaninternal.api.SpiTransaction;

@Transactional(getGeneratedKeys = false, batchSize = 50)
public class SomeTransactionalServiceCls {

  Boolean getGeneratedKeys;

  public void someMethod(String param) {

    System.out.println("--- in someMethod");

    SpiTransaction tdTransaction = (SpiTransaction)DB.currentTransaction();
    getGeneratedKeys = tdTransaction.getBatchGetGeneratedKeys();
  }

}
