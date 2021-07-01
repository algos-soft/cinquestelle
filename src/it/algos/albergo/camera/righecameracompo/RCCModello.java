package it.algos.albergo.camera.righecameracompo;

import it.algos.albergo.camera.Camera;
import it.algos.albergo.camera.composizione.CompoCamera;
import it.algos.base.campo.base.Campo;
import it.algos.base.campo.base.CampoFactory;
import it.algos.base.errore.Errore;
import it.algos.base.modello.ModelloAlgos;
import it.algos.base.vista.Vista;

/**
 * - @todo Manca la descrizione.
 * </p>
 *
 * @author Guido Andrea Ceresa ed Alessandro Valbonesi
 * @author Gac
 * @version 1.0 / 9-apr-2009 ore 07:18
 */
public final class RCCModello extends ModelloAlgos implements RCC {

    /**
     * Costruttore completo senza parametri.
     */
    public RCCModello() {
        /* rimanda al costruttore della superclasse */
        super();

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo costruttore


    /**
     * Regolazioni immediate di riferimenti e variabili.
     * </p>
     * Metodo invocato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        /* regola il nome della tavola dalla costante */
        super.setTavolaArchivio(RCC.NOME_TAVOLA);

    }// fine del metodo inizia


    /**
     * Creazione dei campi.
     * <p/>
     * Creazione dei campi base presenti in tutte le tavole <br>
     * Metodo invocato dal ciclo statico del progetto <br>
     * Creazione dei campi record di questo modello <br>
     * I campi verranno visualizzati nell'ordine di inserimento <br>
     * Ogni campo viene creato con un costruttore semplice con solo le piu'
     * comuni informazioni; le altre vengono regolate con chiamate successive <br>
     * Invoca il metodo sovrascritto della superclasse <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     * (metodo chiamato dalla superclasse) <br>
     *
     * @see it.algos.base.progetto.Progetto#preparaModuli
     * @see it.algos.base.modello.ModelloAlgos#creaCampi
     * @see it.algos.base.campo.base.CampoFactory
     * @see it.algos.base.campo.video.decorator.VideoFactory
     */
    @Override
    protected void creaCampi() {
        /* variabili e costanti locali di lavoro */
        Campo unCampo;

        try { // prova ad eseguire il codice
            /* invoca il metodo sovrascritto della superclasse */
            super.creaCampi();

            /* campo link al record di camera */
            unCampo = CampoFactory.link(Cam.linkcamera);
            unCampo.setNomeModuloLinkato(Camera.NOME_MODULO);
            unCampo.setNomeColonnaListaLinkata(Camera.Cam.camera.get());
            unCampo.setNomeCampoValoriLinkato(Camera.Cam.camera.get());
            this.addCampo(unCampo);

            /* campo link al record di composizione */
            unCampo = CampoFactory.comboLinkSel(Cam.linkcompo);
            unCampo.setNomeModuloLinkato(CompoCamera.NOME_MODULO);
            unCampo.setNomeColonnaListaLinkata(CompoCamera.CAMPO_SIGLA);
            unCampo.setNomeCampoValoriLinkato(CompoCamera.CAMPO_SIGLA);
            unCampo.setUsaNuovo(true);
            this.addCampo(unCampo);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo creaCampi


    /**
     * Creazione delle viste aggiuntive.
     * <p/>
     * Metodo invocato dal ciclo statico del progetto <br>
     * Eventuale creazione di viste aggiuntive, oltre alla vista base di default <br>
     * Costruisce degli ArrayList di riferimenti ordinati (oggetti <code>Vista</code>) per
     * individuare i campi che voglio vedere nelle liste alternative ed
     * aggiuntive a quella standard (costruita in automatico nella superclasse) <br>
     * Gli array vengono creati coi campi di questo modello, oppure con
     * viste di altri moduli, oppure con campi di altri modelli <br>
     * Viene chiamato <strong>dopo</strong> che nella sottoclasse sono stati costruiti tutti i campi <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     * (metodo chiamato dalla superclasse) <br>
     *
     * @see it.algos.base.costante.CostanteModulo#VISTA_BASE_DEFAULT
     * @see it.algos.base.costante.CostanteModello#NOME_CAMPO_SIGLA
     */
    @Override
    protected void creaViste() {
        /* variabili e costanti locali di lavoro */
        Vista vista;

        try { // prova ad eseguire il codice

            /* crea la vista per le righe in camera */
            vista = this.creaVista(Vis.visCamera.toString());
            vista.addCampo(Cam.linkcompo);
            this.addVista(vista);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


}// fine della classe