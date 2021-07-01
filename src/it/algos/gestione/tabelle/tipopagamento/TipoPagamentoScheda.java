/**
 * Title:     TipoPagamentoScheda
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      26-feb-2007
 */
package it.algos.gestione.tabelle.tipopagamento;

import it.algos.base.errore.Errore;
import it.algos.base.modulo.Modulo;
import it.algos.base.scheda.SchedaDefault;

/**
 * Scheda di un Tipo Pagamento.
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0 / 26-feb-2007
 */
public final class TipoPagamentoScheda extends SchedaDefault implements TipoPagamento {

    /**
     * Costruttore completo.
     *
     * @param modulo di riferimento
     */
    public TipoPagamentoScheda(Modulo modulo) {
        /* rimanda al costruttore della superclasse */
        super(modulo);
    }// fine del metodo costruttore


    /**
     * Sincronizzazione della scheda.
     * <p/>
     */
    public void sincronizza() {
        try { // prova ad eseguire il codice
            super.sincronizza();

            /* abilita/disabilita il campo Quale Banca in funzione del flag Usa Banca */
            super.sincroVisibilita(Cam.usaBanca, Cam.qualeBanca);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }
} // fine della classe
