/**
 * Copyright: Copyright (c) 2006
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      13-giu-2006
 */
package it.algos.base.navigatore;

import it.algos.base.errore.Errore;
import it.algos.base.lista.Lista;
import it.algos.base.modulo.Modulo;
import it.algos.base.portale.Portale;

/**
 * Business logic per Navigatore.
 * </p>
 * Questa classe concreta: <ul>
 * <li> Gestisce la business-logic della GUI </li>
 * <li> Riceve le invocazioni da una classe di tipo <code>Gestore</code> </li>
 * <ul/>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 13-giu-2006 ore 16.08.53
 */
public class NavigatoreL extends NavigatoreBase {

    /**
     * Costruttore completo con parametri.
     *
     * @param unModulo modulo di riferimento
     */
    public NavigatoreL(Modulo unModulo) {
        /* rimanda al costruttore della superclasse */
        super(unModulo);

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo costruttore completo


    /**
     * Regolazioni immediate di riferimenti e variabili.
     * <p/>
     * Metodo chiamato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        /* variabili e costanti locali di lavoro */
        Portale portaleLista;

        try {    // prova ad eseguire il codice
            this.setUsaPannelloUnico(true);

            /* crea il Portale contenente la lista nella superclasse */
            portaleLista = this.creaPortaleLista();
            portaleLista.getToolBar().setUsaNuovo(false);
            portaleLista.getToolBar().setUsaModifica(false);

            /* aggiunge i due portali al portale Navigatore */
            this.addPortali(portaleLista, null);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

    }// fine del metodo inizia


    /**
     * Azione nuovo record in una <code>Lista</code>.
     * <p/>
     * Metodo invocato dal Gestore Eventi <br>
     * Invoca il metodo delegato <br>
     */
    @Override protected int nuovoRecord() {
        return 0;
    }


    /**
     * Azione modifica record in una <code>Lista</code>.
     * <p/>
     * Nel navigatore Lista non fa nulla
     *
     * @return true se eseguito correttamente
     */
    @Override protected boolean modificaRecord() {
        return false;
    }


    /**
     * Flag - seleziona l'icona piccola, media o grande.
     * <p/>
     *
     * @param unTipoIcona codice tipo icona (Codifica in ToolBar)
     *
     * @see it.algos.base.toolbar.ToolBar
     */
    public void setTipoIcona(int unTipoIcona) {
        try { // prova ad eseguire il codice
            this.getPortaleLista().setTipoIcona(unTipoIcona);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * flag - usa i bottoni di selezione.
     *
     * @param usaSelezione true per usare i tre bottoni di selezione dei records <br>
     */
    public void setUsaSelezione(boolean usaSelezione) {
        try { // prova ad eseguire il codice
            this.getPortaleLista().setUsaSelezione(usaSelezione);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * flag - usa il bottone di stampa lista.
     *
     * @param flag true per usare il bottone di stampa lista <br>
     */
    public void setUsaStampaLista(boolean flag) {
        try { // prova ad eseguire il codice
            this.getPortaleLista().setUsaStampa(flag);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Recupera la lista pilota di questo Navigatore.
     * <p/>
     * Metodo sovrascritto nelle sottoclassi.
     *
     * @return la lista pilota di questo navigatore
     */
    protected Lista getListaPilota() {
        return this.getLista();
    }

}// fine della classe
