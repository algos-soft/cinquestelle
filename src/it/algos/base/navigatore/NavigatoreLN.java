/**
 * Title:     NavigatoreLN
 * Copyright: Copyright (c) 2005
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      07-feb-2005
 */
package it.algos.base.navigatore;

import it.algos.base.errore.Errore;
import it.algos.base.lista.Lista;
import it.algos.base.modulo.Modulo;
import it.algos.base.portale.Portale;
import it.algos.base.toolbar.ToolBarLista;

/**
 * Navigatore con Lista e Navigatore, la lista pilota il navigatore.
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 07-feb-2005 ore 18.05
 */
public class NavigatoreLN extends NavigatoreXN {

    /**
     * Costruttore completo con parametri.
     * <p/>
     *
     * @param moduloMaster modulo di riferimento
     * @param moduloSlave modulo per il navigatore slave
     * @param chiaveNavSlave nome chiave del navigatore Slave (nel modulo Slave)
     */
    public NavigatoreLN(Modulo moduloMaster, Modulo moduloSlave, String chiaveNavSlave) {

        /* rimanda al costruttore della superclasse */
        super(moduloMaster, moduloSlave, chiaveNavSlave);

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
        ToolBarLista tbLista;

        try {    // prova ad eseguire il codice

            /* crea il Portale contenente la lista nella superclasse */
            portaleLista = this.creaPortaleLista();

            /* aggiunge il portale Lista Interno al componente A
             * del Portale navigatore */
            this.getPortaleNavigatore().setPortaleA(portaleLista);

            /* regola la toolbar della lista - rimuove le funzioni non applicabili */
            tbLista = (ToolBarLista)this.getPortaleLista().getToolBar();
            tbLista.setUsaNuovo(false);
            tbLista.setUsaModifica(false);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

    }// fine del metodo inizia


    /**
     * Recupera la lista pilota di questo Navigatore.
     * <p/>
     *
     * @return la lista pilota di questo navigatore
     */
    protected Lista getListaPilota() {
        return this.getLista();
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
            super.setTipoIcona(unTipoIcona);
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
            super.setUsaSelezione(usaSelezione);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


}// fine della classe
