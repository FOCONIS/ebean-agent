package de.foconis.core.domain.base;

import de.foconis.core.domain.BaseModel;

public abstract class PropertyImpl<T> {

  public abstract T normalize(final BaseModel model, final T value);
}
