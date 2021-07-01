package it.algos.albergo.nazionealbergo;

import it.algos.base.campo.base.Campo;
import it.algos.base.campo.base.CampoFactory;
import it.algos.base.validatore.ValidatoreFactory;
import it.algos.base.vista.Vista;
import it.algos.gestione.indirizzo.tabelle.nazione.Nazione;
import it.algos.gestione.indirizzo.tabelle.nazione.NazioneModello;

public class NazioneAlbergoModello extends NazioneModello {
	
    public NazioneAlbergoModello() {
        super();
    }
    
    protected Vista creaVistaDefault() {
        Vista vista = null;
        
        /* crea una vista vuota */
        vista = new Vista(this.getModulo());

        /* aggiunge i campi desiderati */
        vista.addCampo(Nazione.Cam.nazione.get());
        vista.addCampo(Nazione.Cam.sigla2.get());
        vista.addCampo(NazioneAlbergo.Cam.codicePS.get());

        /* valore di ritorno */
        return vista;

    } /* fine del metodo */

    
    protected void creaCampi() {

        super.creaCampi();

        /* campo codicePS */
        Campo unCampo = CampoFactory.testo(NazioneAlbergo.Cam.codicePS);
        unCampo.setLarLista(80);
        unCampo.getCampoDati().setValidatore(ValidatoreFactory.testoLunMax(9));

        this.addCampo(unCampo);
        
    }

}
