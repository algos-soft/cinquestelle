package it.algos.albergo.prenotazione.periodo.periodorisorsa;

import it.algos.albergo.prenotazione.periodo.Periodo;
import it.algos.albergo.risorse.Risorsa;
import it.algos.albergo.tabelle.tipirisorsa.TipoRisorsa;
import it.algos.albergo.tabelle.tipirisorsa.TipoRisorsaModulo;
import it.algos.base.campo.base.Campo;
import it.algos.base.campo.base.CampoFactory;
import it.algos.base.database.Db;
import it.algos.base.errore.Errore;
import it.algos.base.modello.ModelloDefault;
import it.algos.base.modulo.Modulo;
import it.algos.base.vista.Vista;
import it.algos.base.vista.VistaElemento;

public class RisorsaPeriodoModello extends ModelloDefault implements RisorsaPeriodo {

    public RisorsaPeriodoModello() {
        super();

        try {
            this.inizia();
        } catch (Exception unErrore) {
            Errore.crea(unErrore);
        }
    }


    private void inizia() throws Exception {
        super.setTavolaArchivio(RisorsaPeriodo.NOME_TAVOLA);
    }


    /**
     * Creazione dei campi.
     */
    protected void creaCampi() {
        Campo unCampo;
        
        super.creaCampi();

        /* campo link periodo */
        unCampo = CampoFactory.link(RisorsaPeriodo.Cam.periodo);
        unCampo.setNomeModuloLinkato(Periodo.NOME_MODULO);
        unCampo.setAzioneDelete(Db.Azione.cascade);
        this.addCampo(unCampo);
        
        /* campo tipo risorsa */
        unCampo = CampoFactory.comboLinkPop(RisorsaPeriodo.Cam.tipoRisorsa);
        unCampo.setNomeModuloLinkato(TipoRisorsa.NOME_MODULO);
        unCampo.setAzioneDelete(Db.Azione.noAction);
        unCampo.setNomeColonnaListaLinkata(TipoRisorsa.Cam.sigla.get());
        unCampo.setLarScheda(150);
        unCampo.decora().obbligatorio();
        unCampo.setVisibileVistaDefault();
        this.addCampo(unCampo);

        /* campo link risorsa */
        unCampo = CampoFactory.comboLinkPop(RisorsaPeriodo.Cam.risorsa);
        unCampo.setNomeModuloLinkato(Risorsa.NOME_MODULO);
        unCampo.setAzioneDelete(Db.Azione.noAction);
        unCampo.setNomeColonnaListaLinkata(Risorsa.Cam.numero.get());
        unCampo.setNomeCampoValoriLinkato(Risorsa.Cam.numero.get());
        unCampo.setLarScheda(150);
        unCampo.decora().obbligatorio();
        unCampo.setVisibileVistaDefault();
        this.addCampo(unCampo);
        
        /* campo data inizio impegno risorsa */
        unCampo = CampoFactory.data(RisorsaPeriodo.Cam.dataInizio);
        unCampo.decora().obbligatorio();
        this.addCampo(unCampo);

        /* campo data fine impegno risorsa */
        unCampo = CampoFactory.data(RisorsaPeriodo.Cam.dataFine);
        unCampo.decora().obbligatorio();
        this.addCampo(unCampo);
        
        /* campo note */
        unCampo = CampoFactory.testo(RisorsaPeriodo.Cam.note);
        this.addCampo(unCampo);

        
    }


    /**
     * Creazione delle viste aggiuntive.
     */
    protected void creaViste() {
        Vista vista;
        VistaElemento elemento;
        
        super.creaViste();

        /* crea la vista per il navigatore nel periodo */
        vista = this.creaVista(RisorsaPeriodo.Vis.inPeriodi.toString());

        vista.addCampo(RisorsaPeriodo.Cam.dataInizio.get());
        vista.addCampo(RisorsaPeriodo.Cam.dataFine.get());
        Campo campoTipo = TipoRisorsaModulo.get().getCampo(TipoRisorsa.Cam.sigla);
        elemento = vista.addCampo(campoTipo);
        elemento.setTitoloColonna("tipo");
        elemento = vista.addCampo(RisorsaPeriodo.Cam.risorsa.get());
        elemento.setTitoloColonna("numero");
        vista.addCampo(RisorsaPeriodo.Cam.note.get());
        
        this.addVista(vista);
    }


    /**
     * Crea la Vista di default
     *
     * @return la Vista creata
     */
    protected Vista creaVistaDefault() {
        /** variabili e costanti locali di lavoro */
        Vista vista = null;

        try {    // prova ad eseguire il codice

            /* crea una vista vuota */
            vista = new Vista(this.getModulo());

            /* aggiunge i campi desiderati */
            vista.addCampo(RisorsaPeriodo.Cam.dataInizio.get());
            vista.addCampo(RisorsaPeriodo.Cam.dataFine.get());
            vista.addCampo(RisorsaPeriodo.Cam.risorsa.get());

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /* valore di ritorno */
        return vista;

    } /* fine del metodo */


	@Override
	public void prepara(Modulo unModulo) {
		super.prepara(unModulo);
	}



}
