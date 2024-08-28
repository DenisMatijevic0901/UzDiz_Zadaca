package org.foi.uzdiz.chains;

import java.util.Properties;
import org.foi.uzdiz.classes.ArgumentValidator;

public class HandlerParametraPMU implements HandlerParametara {
  private HandlerParametara sljedbenik;

  @Override
  public void obradiParametar(ArgumentValidator argumentValidator, Properties parametri) {

    argumentValidator.validirajPMU(parametri.getProperty("pmu"));

    if (sljedbenik != null) {
      sljedbenik.obradiParametar(argumentValidator, parametri);
    }
  }

  @Override
  public void postaviSljedbenika(HandlerParametara sljedbenik) {
    this.sljedbenik = sljedbenik;
  }
}
