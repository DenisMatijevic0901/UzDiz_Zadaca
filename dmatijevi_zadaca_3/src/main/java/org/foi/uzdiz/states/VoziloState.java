package org.foi.uzdiz.states;

import java.time.format.DateTimeFormatter;
import org.foi.uzdiz.classes.Vozilo;

public interface VoziloState {
  void promijeniStatus(Vozilo vozilo, String status, DateTimeFormatter format);
}
