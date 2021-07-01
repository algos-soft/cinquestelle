/**
 * Copyright: Copyright (c) 2006
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      29-mar-2006
 */
package it.algos.albergo.prenotazione.periodo.periodoaddebito;

import it.algos.base.errore.Errore;
import it.algos.base.modulo.logica.LogicaBase;

/**
 * Business logic del package AddebitoPeriodo.
 * </p>
 * Questa classe: <ul>
 * <li> </li>
 * <li> </li>
 * </ul>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 22-mar-2006 ore 15.00.06
 */
public class AddebitoPeriodoLogica extends LogicaBase {


    /**
     * Costruttore completo con parametri.
     * <p/>
     *
     * @param modulo
     */
    public AddebitoPeriodoLogica(AddebitoPeriodoModulo modulo) {
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
    }// fine del metodo inizia


}// fine della classe
