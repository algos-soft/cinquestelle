/**
 * Copyright(c): 2006
 * Company: Algos s.r.l.
 * Author: gac
 * Date: 16-5-2007
 */

package it.algos.albergo.prenotazione.periodo.serviziospecifico;

import it.algos.albergo.prenotazione.periodo.Periodo;
import it.algos.albergo.tabelle.settore.Settore;
import it.algos.base.campo.base.Campo;
import it.algos.base.campo.base.CampoFactory;
import it.algos.base.database.Db;
import it.algos.base.errore.Errore;
import it.algos.base.modello.ModelloAlgos;

/**
 * Tracciato record della tavola Periodi.
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0 / 16-5-2007
 */
public final class ServizioSpecificoModello extends ModelloAlgos implements ServizioSpecifico {

    /**
     * Costruttore completo senza parametri.
     */
    public ServizioSpecificoModello() {
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
        super.setTavolaArchivio(ServizioSpecifico.NOME_TAVOLA);
    }


    /**
     * Creazione dei campi.
     * <p/>
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
     */
    protected void creaCampi() {
        /* variabili e costanti locali di lavoro */
        Campo unCampo;

        try { // prova ad eseguire il codice
            /* invoca il metodo sovrascritto della superclasse */
            super.creaCampi();

            /* campo link periodo  */
            unCampo = CampoFactory.link(Cam.periodo);
            unCampo.setNomeModuloLinkato(Periodo.NOME_MODULO);
            unCampo.setAzioneDelete(Db.Azione.cascade);
            this.addCampo(unCampo);

            /* campo sigla  */
            unCampo = CampoFactory.testo(Cam.descrizione);
            this.addCampo(unCampo);

            /* campo flag arrivo  */
            unCampo = CampoFactory.checkBox(Cam.arrivo);
            unCampo.setTestoComponente("arrivo");
            this.addCampo(unCampo);

            /* campo link settore  */
            unCampo = CampoFactory.comboLinkPop(Cam.settore);
            unCampo.setNomeModuloLinkato(Settore.NOME_MODULO);
            unCampo.setNomeColonnaListaLinkata(Settore.Cam.sigla.get());
            unCampo.setNomeCampoValoriLinkato(Settore.Cam.sigla.get());
            unCampo.setUsaNuovo(true);
            this.addCampo(unCampo);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


} // fine della classe
