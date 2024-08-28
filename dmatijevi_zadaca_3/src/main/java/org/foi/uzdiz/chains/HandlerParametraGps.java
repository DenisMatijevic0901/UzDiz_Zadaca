package org.foi.uzdiz.chains;

import java.util.Properties;
import org.foi.uzdiz.classes.ArgumentValidator;

public class HandlerParametraGps implements HandlerParametara {
  private HandlerParametara sljedbenik;

  @Override
  public void obradiParametar(ArgumentValidator argumentValidator, Properties parametri) {

    argumentValidator.validirajGps(parametri.getProperty("gps"));

    if (sljedbenik != null) {
      sljedbenik.obradiParametar(argumentValidator, parametri);
    }
  }

  @Override
  public void postaviSljedbenika(HandlerParametara sljedbenik) {
    this.sljedbenik = sljedbenik;
  }
}
