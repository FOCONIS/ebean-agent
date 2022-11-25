package io.ebean.enhance.common;

/**
 * Constant values used in byte code generation.
 */
public interface EnhanceConstants {

  String INIT = "<init>";
  String CLINIT = "<clinit>";
  String NOARG_VOID = "()V";
  String MOCKITO_MOCK = "$MockitoMock$";

  String TRANSACTIONAL_ANNOTATION = "Lio/ebean/annotation/Transactional;";
  String TYPEQUERYBEAN_ANNOTATION = "Lio/ebean/typequery/TypeQueryBean;";

  String NORMALIZE_ANNOTATION = "Lio/ebean/annotation/Normalize;";

  String JAVAX_PERSISTENCE = "Ljavax/persistence/";
  String ENTITY_ANNOTATION = "Ljavax/persistence/Entity;";
  String DOCSTORE_ANNOTATION = "Lio/ebean/annotation/DocStore;";
  String EMBEDDABLE_ANNOTATION = "Ljavax/persistence/Embeddable;";
  String MAPPEDSUPERCLASS_ANNOTATION = "Ljavax/persistence/MappedSuperclass;";
  String ENTITY_EXTENSION_ANNOTATION = "Lio/ebean/bean/extend/EntityExtension;";

  String IDENTITY_FIELD = "_ebean_identity";
  String INTERCEPT_FIELD = "_ebean_intercept";
  String PROPS_FIELD = "_ebean_props";
  String EXTENSION_ACCESSORS_FIELD = "_ebean_extension_accessors";
  String EXTENSION_STORAGE_FIELD = "_ebean_extension_storage";
  String C_OBJECT = "java/lang/Object";
  String C_MODEL = "io/ebean/Model";
  String C_QUERYBEAN = "io/ebean/typequery/TQRootBean";
  String C_ENHANCEDTRANSACTIONAL = "io/ebean/bean/EnhancedTransactional";
  String C_ENTITYBEAN = "io/ebean/bean/EntityBean";
  String C_SCALAOBJECT = "scala/ScalaObject";
  String C_GROOVYOBJECT = "groovy/lang/GroovyObject";
  String C_RECORDTYPE = "java/lang/Record";
  String C_INTERCEPT_I = "io/ebean/bean/EntityBeanIntercept";
  String C_INTERCEPT_RW = "io/ebean/bean/InterceptReadWrite";
  String C_INTERCEPT_RO = "io/ebean/bean/InterceptReadOnly";
  String C_INTERCEPT_EXT = "io/ebean/bean/EntityExtensionIntercept";
  String C_BEANCOLLECTION = "io/ebean/bean/BeanCollection";
  String C_TOSTRINGBUILDER = "io/ebean/bean/ToStringBuilder";

  String C_EXTENDABLE_BEAN = "io/ebean/bean/extend/ExtendableBean";
  String C_EXTENSIONACCESSORS = "io/ebean/bean/ExtensionAccessors";
  String C_EXTENSIONACCESSOR = "io/ebean/bean/ExtensionAccessor";
  String L_STRING = "Ljava/lang/String;";
  String L_OBJECT = "Ljava/lang/Object;";
  String L_INTERCEPT = "Lio/ebean/bean/EntityBeanIntercept;";
  String L_ENTITYBEAN = "Lio/ebean/bean/EntityBean;";
  String L_EXTENSIONACCESSORS = "Lio/ebean/bean/ExtensionAccessors;";
  String L_EXTENSIONACCESSOR = "Lio/ebean/bean/ExtensionAccessor;";
  String L_HELPSCOPETRANS = "Lio/ebeaninternal/api/HelpScopeTrans;";
  String L_DRAFT = "Lio/ebean/annotation/Draft;";
  String C_TXSCOPE = "io/ebean/TxScope";
  String C_TXTYPE = "io/ebean/annotation/TxType";
  String C_TXISOLATION = "io/ebean/annotation/TxIsolation";
  String C_PERSISTBATCH = "io/ebean/annotation/PersistBatch";
  String C_TXOPTION = "io/ebean/annotation/TxOption";
  String BEANLIST = "io/ebean/common/BeanList";
  String BEANSET = "io/ebean/common/BeanSet";
  String BEANMAP = "io/ebean/common/BeanMap";
  String ARRAYLIST = "java/util/ArrayList";
  String LINKEDHASHSET = "java/util/LinkedHashSet";
  String LINKEDHASHMAP = "java/util/LinkedHashMap";
  String L_JETBRAINS_NOTNULL = "Lorg/jetbrains/annotations/NotNull;";
  String L_EBEAN_NOTNULL = "Lio/ebean/annotation/NotNull;";
  String L_COLUMN_ANNOTATION = "Ljavax/persistence/Column;";
  String L_DBARRAY = "Lio/ebean/annotation/DbArray;";
}
