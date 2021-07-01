/**
 * Title:     PagamentoNavConto
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      5-giu-2007
 */
package it.algos.albergo.conto.pagamento;

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
public final class PagamentoNavConto extends MovimentoNavConto {

    /**
     * Costruttore completo con parametri.
     *
     * @param unModulo modulo di riferimento
     */
    public PagamentoNavConto(Modulo unModulo) {
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
        this.setNomeVista(Pagamento.Vis.vistaConto.get());
        this.addScheda(new PagamentoSchedaConto(this.getModulo()));
    }// fine del metodo inizia


}// fine della classe
