package org.foi.uzdiz.chains;

import java.util.Properties;
import org.foi.uzdiz.classes.ArgumentValidator;

public interface HandlerParametara {
  void obradiParametar(ArgumentValidator argumentValidator, Properties parametri);

  void postaviSljedbenika(HandlerParametara sljedbenik);
}
