package com.group76pos;

public interface IMemento {
  public Memento save();
  public void restore(Memento m);
}
