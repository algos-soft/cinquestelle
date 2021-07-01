/**
 * Title:     AddebitoNavConto
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      28-set-2007
 */
package it.algos.albergo.conto.addebito;

import it.algos.albergo.conto.movimento.MovimentoNavConto;
import it.algos.base.errore.Errore;
import it.algos.base.modulo.Modulo;

/**
 * Navigatore dei pagamenti all'interno del conto.
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 5-giu-2007 ore 13.55.52
 */
public final class AddebitoNavConto extends MovimentoNavConto {

    /**
     * Costruttore completo con parametri.
     *
     * @param unModulo modulo di riferimento
     */
    public AddebitoNavConto(Modulo unModulo) {
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
     * Regolazioni statiche di riferimenti e variabili.
     * </p>
     * Metodo invocato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {

        try { // prova ad eseguire il codice

            this.setNomeVista(Addebito.Vis.vistaConto.get());
            this.addScheda(new AddebitoSchedaConto(this.getModulo()));
            this.setUsaRicerca(true);
            this.setUsaStampaLista(true);
            this.setUsaStatusBarLista(true);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }// fine del metodo inizia


}// fine della classe
