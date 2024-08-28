package org.foi.uzdiz.decorators;

public class DecoratorIP implements ComponentIP {

  protected ComponentIP componentIP;

  public DecoratorIP(ComponentIP componentIP) {
    this.componentIP = componentIP;
  }

  @Override
  public void ispisi() {
    componentIP.ispisi();
  }
}
