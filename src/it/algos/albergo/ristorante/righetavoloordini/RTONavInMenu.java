/**
 * Copyright: Copyright (c) 2005
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      10-mar-2005
 */
package it.algos.albergo.ristorante.righetavoloordini;

import it.algos.base.errore.Errore;
import it.algos.base.modulo.Modulo;
import it.algos.base.navigatore.NavigatoreLS;
import it.algos.base.toolbar.ToolBar;

/**
 * Navigatore specifico usato nel menu.
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 10-mar-2005 ore 9.44.32
 */
public final class RTONavInMenu extends NavigatoreLS {


    /**
     * Costruttore completo con parametri.
     * <p/>
     *
     * @param modulo modulo di riferimento
     */
    public RTONavInMenu(Modulo modulo) {

        /* rimanda al costruttore della superclasse */
        super(modulo);

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo costruttore completo


    /**
     * Regolazioni statiche di riferimenti e variabili.
     * </p>
     * Metodo invocato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        /* variabili e costanti locali di lavoro */
        this.setNomeChiave(RTO.NAV_IN_MENU);
        this.setOrizzontale(true);
        this.setUsaPannelloUnico(true);
        this.setNomeVista(RTO.VISTA_IN_MENU);
        this.getPortaleLista().setPosToolbar(ToolBar.Pos.nessuna);
        this.setLarghezzaLista(180);
        this.getLista().setOrdinabile(false);
    }// fine del metodo inizia


}// fine della classe
