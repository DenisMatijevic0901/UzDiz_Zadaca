package org.foi.uzdiz.classes;

import org.foi.uzdiz.singletons.GreskeSingleton;
import org.foi.uzdiz.singletons.TvrtkaSingleton;

public class ArgumentValidator {
  public boolean ispravno = true;

  public void validirajVP(String argumentVP) {
    if (argumentVP != null) {
      String regexKomandaVP = "^[a-zA-Z0-9ćčšđžĆČŠĐŽ_-]+\\.csv$";
      if (argumentVP.trim().matches(regexKomandaVP)) {
        TvrtkaSingleton.getInstance().spremiVP(argumentVP.trim());
      } else {
        ispravno = false;
        GreskeSingleton.getInstance()
            .dohvatiGreskuS("Argument VP ne zadovoljava zadani format (regex).");
      }
    } else {
      ispravno = false;
      GreskeSingleton.getInstance()
          .dohvatiGreskuS("Argument VP ne zadovoljava zadani format (NullPointerException).");
    }
  }

  public void validirajPV(String argumentPV) {
    if (argumentPV != null) {
      String regexKomandaPV = "^[a-zA-Z0-9ćčšđžĆČŠĐŽ_-]+\\.csv$";
      if (argumentPV.trim().matches(regexKomandaPV)) {
        TvrtkaSingleton.getInstance().spremiPV(argumentPV.trim());
      } else {
        ispravno = false;
        GreskeSingleton.getInstance()
            .dohvatiGreskuS("Argument PV ne zadovoljava zadani format (regex).");
      }
    } else {
      ispravno = false;
      GreskeSingleton.getInstance()
          .dohvatiGreskuS("Argument PV ne zadovoljava zadani format (NullPointerException).");
    }
  }

  public void validirajPP(String argumentPP) {
    if (argumentPP != null) {
      String regexKomandaPP = "^[a-zA-Z0-9ćčšđžĆČŠĐŽ_-]+\\.csv$";
      if (argumentPP.trim().matches(regexKomandaPP)) {
        TvrtkaSingleton.getInstance().spremiPP(argumentPP.trim());
      } else {
        ispravno = false;
        GreskeSingleton.getInstance()
            .dohvatiGreskuS("Argument PP ne zadovoljava zadani format (regex).");
      }
    } else {
      ispravno = false;
      GreskeSingleton.getInstance()
          .dohvatiGreskuS("Argument PP ne zadovoljava zadani format (NullPointerException).");
    }
  }

  public void validirajPO(String argumentPO) {
    if (argumentPO != null) {
      String regexKomandaPO = "^[a-zA-Z0-9ćčšđžĆČŠĐŽ_-]+\\.csv$";
      if (argumentPO.trim().matches(regexKomandaPO)) {
        TvrtkaSingleton.getInstance().spremiPO(argumentPO.trim());
      } else {
        ispravno = false;
        GreskeSingleton.getInstance()
            .dohvatiGreskuS("Argument PO ne zadovoljava zadani format (regex).");
      }
    } else {
      ispravno = false;
      GreskeSingleton.getInstance()
          .dohvatiGreskuS("Argument PO ne zadovoljava zadani format (NullPointerException).");
    }
  }

  public void validirajPM(String argumentPM) {
    if (argumentPM != null) {
      String regexKomandaPM = "^[a-zA-Z0-9ćčšđžĆČŠĐŽ_-]+\\.csv$";
      if (argumentPM.trim().matches(regexKomandaPM)) {
        TvrtkaSingleton.getInstance().spremiPM(argumentPM.trim());
      } else {
        ispravno = false;
        GreskeSingleton.getInstance()
            .dohvatiGreskuS("Argument PM ne zadovoljava zadani format (regex).");
      }
    } else {
      ispravno = false;
      GreskeSingleton.getInstance()
          .dohvatiGreskuS("Argument PM ne zadovoljava zadani format (NullPointerException).");
    }
  }

  public void validirajPU(String argumentPU) {
    if (argumentPU != null) {
      String regexKomandaPU = "^[a-zA-Z0-9ćčšđžĆČŠĐŽ_-]+\\.csv$";
      if (argumentPU.trim().matches(regexKomandaPU)) {
        TvrtkaSingleton.getInstance().spremiPU(argumentPU.trim());
      } else {
        ispravno = false;
        GreskeSingleton.getInstance()
            .dohvatiGreskuS("Argument PU ne zadovoljava zadani format (regex).");
      }
    } else {
      ispravno = false;
      GreskeSingleton.getInstance()
          .dohvatiGreskuS("Argument PU ne zadovoljava zadani format (NullPointerException).");
    }
  }

  public void validirajPMU(String argumentPMU) {
    if (argumentPMU != null) {
      String regexKomandaPMU = "^[a-zA-Z0-9ćčšđžĆČŠĐŽ_-]+\\.csv$";
      if (argumentPMU.trim().matches(regexKomandaPMU)) {
        TvrtkaSingleton.getInstance().spremiPMU(argumentPMU.trim());
      } else {
        ispravno = false;
        GreskeSingleton.getInstance()
            .dohvatiGreskuS("Argument PMU ne zadovoljava zadani format (regex).");
      }
    } else {
      ispravno = false;
      GreskeSingleton.getInstance()
          .dohvatiGreskuS("Argument PMU ne zadovoljava zadani format (NullPointerException).");
    }
  }

  public void validirajMT(String argumentMT) {
    if (argumentMT != null) {
      String regexKomandaMT = "^[\\d]+$";
      if (argumentMT.trim().matches(regexKomandaMT)) {
        TvrtkaSingleton.getInstance().spremiMT(argumentMT.trim());
      } else {
        ispravno = false;
        GreskeSingleton.getInstance()
            .dohvatiGreskuS("Argument MT ne zadovoljava zadani format (regex).");
      }
    } else {
      ispravno = false;
      GreskeSingleton.getInstance()
          .dohvatiGreskuS("Argument MT ne zadovoljava zadani format (NullPointerException).");
    }
  }

  public void validirajVI(String argumentVI) {
    if (argumentVI != null) {
      String regexKomandaVI = "^[\\d]+$";
      if (argumentVI.trim().matches(regexKomandaVI)) {
        TvrtkaSingleton.getInstance().spremiVI(argumentVI.trim());
      } else {
        ispravno = false;
        GreskeSingleton.getInstance()
            .dohvatiGreskuS("Argument VI ne zadovoljava zadani format (regex).");
      }
    } else {
      ispravno = false;
      GreskeSingleton.getInstance()
          .dohvatiGreskuS("Argument VI ne zadovoljava zadani format (NullPointerException).");
    }
  }

  public void validirajVS(String argumentVS) {
    if (argumentVS != null) {
      String regexKomandaVS =
          "^(0[1-9]|[12]\\d|3[01])\\.(0[1-9]|1[0-2])\\.\\d{4}\\. (0[0-9]|1[0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9])$";
      if (argumentVS.matches(regexKomandaVS)) {
        TvrtkaSingleton.getInstance().spremiVS(argumentVS);
      } else {
        ispravno = false;
        GreskeSingleton.getInstance()
            .dohvatiGreskuS("Argument VS ne zadovoljava zadani format (regex).");
      }
    } else {
      ispravno = false;
      GreskeSingleton.getInstance()
          .dohvatiGreskuS("Argument VS ne zadovoljava zadani format (NullPointerException).");
    }
  }

  public void validirajMS(String argumentMS) {
    if (argumentMS != null) {
      String regexKomandaMS = "^[\\d]+$";
      if (argumentMS.trim().matches(regexKomandaMS)) {
        TvrtkaSingleton.getInstance().spremiMS(argumentMS.trim());
      } else {
        ispravno = false;
        GreskeSingleton.getInstance()
            .dohvatiGreskuS("Argument MS ne zadovoljava zadani format (regex).");
      }
    } else {
      ispravno = false;
      GreskeSingleton.getInstance()
          .dohvatiGreskuS("Argument MS ne zadovoljava zadani format (NullPointerException).");
    }
  }

  public void validirajPR(String argumentPR) {
    if (argumentPR != null) {
      String regexKomandaPR = "^(0[0-9]|1[0-9]|2[0-3]):([0-5][0-9])$";
      if (argumentPR.trim().matches(regexKomandaPR)) {
        TvrtkaSingleton.getInstance().spremiPR(argumentPR.trim());
      } else {
        ispravno = false;
        GreskeSingleton.getInstance()
            .dohvatiGreskuS("Argument PR ne zadovoljava zadani format (regex).");
      }
    } else {
      ispravno = false;
      GreskeSingleton.getInstance()
          .dohvatiGreskuS("Argument PR ne zadovoljava zadani format (NullPointerException).");
    }
  }

  public void validirajKR(String argumentKR) {
    if (argumentKR != null) {
      String regexKomandaKR = "^(0[0-9]|1[0-9]|2[0-3]):([0-5][0-9])$";
      if (argumentKR.trim().matches(regexKomandaKR)) {
        TvrtkaSingleton.getInstance().spremiKR(argumentKR.trim());
      } else {
        ispravno = false;
        GreskeSingleton.getInstance()
            .dohvatiGreskuS("Argument KR ne zadovoljava zadani format (regex).");
      }
    } else {
      ispravno = false;
      GreskeSingleton.getInstance()
          .dohvatiGreskuS("Argument KR ne zadovoljava zadani format (NullPointerException).");
    }
  }

  public void validirajGps(String argumentGps) {
    if (argumentGps != null) {
      String argumentGpsTrim = argumentGps.trim();
      String regexKomandaGps = "^[-+]?[0-9]{1,2}\\.[0-9]+,\\s*[-+]?[0-9]{1,3}\\.[0-9]+$";
      if (argumentGpsTrim.matches(regexKomandaGps)) {
        TvrtkaSingleton.getInstance().spremiGps(argumentGpsTrim);
      } else {
        ispravno = false;
        GreskeSingleton.getInstance()
            .dohvatiGreskuS("Argument GPS ne zadovoljava zadani format (regex).");
      }
    } else {
      ispravno = false;
      GreskeSingleton.getInstance()
          .dohvatiGreskuS("Argument GPS ne zadovoljava zadani format (NullPointerException).");
    }
  }

  public void validirajIsporuka(String argumentIsporuka) {
    if (argumentIsporuka != null) {
      String regexKomandaIsporuka = "^[\\d]+$";
      if (argumentIsporuka.trim().matches(regexKomandaIsporuka)) {
        TvrtkaSingleton.getInstance().spremiIsporuka(argumentIsporuka.trim());
      } else {
        ispravno = false;
        GreskeSingleton.getInstance()
            .dohvatiGreskuS("Argument ISPORUKA ne zadovoljava zadani format (regex).");
      }
    } else {
      ispravno = false;
      GreskeSingleton.getInstance()
          .dohvatiGreskuS("Argument ISPORUKA ne zadovoljava zadani format (NullPointerException).");
    }
  }
}
