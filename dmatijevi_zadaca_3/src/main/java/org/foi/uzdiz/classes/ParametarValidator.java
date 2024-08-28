package org.foi.uzdiz.classes;

import java.util.Properties;
import org.foi.uzdiz.chains.HandlerParametara;
import org.foi.uzdiz.chains.HandlerParametraGps;
import org.foi.uzdiz.chains.HandlerParametraIsporuka;
import org.foi.uzdiz.chains.HandlerParametraKR;
import org.foi.uzdiz.chains.HandlerParametraMS;
import org.foi.uzdiz.chains.HandlerParametraMT;
import org.foi.uzdiz.chains.HandlerParametraPM;
import org.foi.uzdiz.chains.HandlerParametraPMU;
import org.foi.uzdiz.chains.HandlerParametraPO;
import org.foi.uzdiz.chains.HandlerParametraPP;
import org.foi.uzdiz.chains.HandlerParametraPR;
import org.foi.uzdiz.chains.HandlerParametraPU;
import org.foi.uzdiz.chains.HandlerParametraPV;
import org.foi.uzdiz.chains.HandlerParametraVI;
import org.foi.uzdiz.chains.HandlerParametraVP;
import org.foi.uzdiz.chains.HandlerParametraVS;

public class ParametarValidator {
  private ArgumentValidator argumentValidator;

  public ParametarValidator(ArgumentValidator argumentValidator) {
    this.argumentValidator = argumentValidator;
  }

  public void validiraj(Properties parametri) {

    HandlerParametara handlerParametraVP = new HandlerParametraVP();
    HandlerParametara handlerParametraPV = new HandlerParametraPV();
    HandlerParametara handlerParametraPP = new HandlerParametraPP();
    HandlerParametara handlerParametraPO = new HandlerParametraPO();
    HandlerParametara handlerParametraPM = new HandlerParametraPM();
    HandlerParametara handlerParametraPU = new HandlerParametraPU();
    HandlerParametara handlerParametraPMU = new HandlerParametraPMU();
    HandlerParametara handlerParametraMT = new HandlerParametraMT();
    HandlerParametara handlerParametraVI = new HandlerParametraVI();
    HandlerParametara handlerParametraVS = new HandlerParametraVS();
    HandlerParametara handlerParametraMS = new HandlerParametraMS();
    HandlerParametara handlerParametraPR = new HandlerParametraPR();
    HandlerParametara handlerParametraKR = new HandlerParametraKR();
    HandlerParametara handlerParametraGps = new HandlerParametraGps();
    HandlerParametara handlerParametraIsporuka = new HandlerParametraIsporuka();

    handlerParametraVP.postaviSljedbenika(handlerParametraPV);
    handlerParametraPV.postaviSljedbenika(handlerParametraPP);
    handlerParametraPP.postaviSljedbenika(handlerParametraPO);
    handlerParametraPO.postaviSljedbenika(handlerParametraPM);
    handlerParametraPM.postaviSljedbenika(handlerParametraPU);
    handlerParametraPU.postaviSljedbenika(handlerParametraPMU);
    handlerParametraPMU.postaviSljedbenika(handlerParametraMT);
    handlerParametraMT.postaviSljedbenika(handlerParametraVI);
    handlerParametraVI.postaviSljedbenika(handlerParametraVS);
    handlerParametraVS.postaviSljedbenika(handlerParametraMS);
    handlerParametraMS.postaviSljedbenika(handlerParametraPR);
    handlerParametraPR.postaviSljedbenika(handlerParametraKR);
    handlerParametraKR.postaviSljedbenika(handlerParametraGps);
    handlerParametraGps.postaviSljedbenika(handlerParametraIsporuka);

    handlerParametraVP.obradiParametar(argumentValidator, parametri);
  }
}
