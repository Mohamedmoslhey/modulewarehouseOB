package com.ibox.paper.invevtorybriefbygram.model;

import java.util.AbstractList;
import java.util.List;

public class CompositeUnmodifiableList<T> extends AbstractList<T> {

  private final List<T> list1;
  private final List<T> list2;

  public CompositeUnmodifiableList(List<T> list1, List<T> list2) {
    this.list1 = list1;
    this.list2 = list2;
  }

  @Override
  public T get(int index) {
    if (index < list1.size()) {
      return list1.get(index);
    }
    return list2.get(index - list1.size());
  }

  @Override
  public int size() {
    return list1.size() + list2.size();
  }
}
