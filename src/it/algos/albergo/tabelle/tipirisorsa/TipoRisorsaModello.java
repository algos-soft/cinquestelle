package it.algos.albergo.tabelle.tipirisorsa;

import it.algos.base.campo.base.Campo;
import it.algos.base.campo.base.CampoFactory;
import it.algos.base.modello.ModelloAlgos;

public class TipoRisorsaModello extends ModelloAlgos implements TipoRisorsa {


    /**
     * Costruttore completo senza parametri.
     */
    public TipoRisorsaModello() {
        super();
        super.setTavolaArchivio(TipoRisorsa.NOME_TAVOLA);
    }// fine del metodo costruttore


    /**
     * Creazione dei campi.
     */
    protected void creaCampi() {
    	
        super.creaCampi();

        /* campo sigla  */
        Campo unCampo = CampoFactory.testo(TipoRisorsa.Cam.sigla);
        unCampo.decora().obbligatorio();
        unCampo.setLarghezza(150);
        unCampo.setTestoEtichetta("Tipo");
        unCampo.setTitoloColonna("Tipo");
        this.addCampo(unCampo);
        
        /* campo settore (Ristorante, Spiaggia etc...)  */
        unCampo = CampoFactory.testo(TipoRisorsa.Cam.settore);
        unCampo.decora().obbligatorio();
        this.addCampo(unCampo);
        
        /* rende visibile il campo ordine */
        super.setCampoOrdineVisibileLista();

    }


    
    

}
