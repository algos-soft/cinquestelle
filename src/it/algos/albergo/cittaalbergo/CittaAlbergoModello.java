package it.algos.albergo.cittaalbergo;

import it.algos.base.campo.base.Campo;
import it.algos.base.campo.base.CampoFactory;
import it.algos.base.campo.dati.CampoDati;
import it.algos.base.validatore.ValidatoreFactory;
import it.algos.gestione.indirizzo.tabelle.citta.CittaModello;

public class CittaAlbergoModello extends CittaModello  {

    public CittaAlbergoModello() {
        super();
    }
    
    protected void creaCampi() {

        super.creaCampi();

        /* campo codicePS */
        Campo unCampo = CampoFactory.testo(CittaAlbergo.Cam.codicePS);
        unCampo.getCampoDati().setValidatore(ValidatoreFactory.testoLunMax(9));

        this.addCampo(unCampo);
        
    }
}
