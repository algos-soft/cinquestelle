/**
 * Title:     TipoDatiMemBooleano
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      3-nov-2004
 */

package it.algos.base.database.memoria.tipodati;

import it.algos.base.errore.Errore;

/**
 * Implementazione concreta di un tipo dati Booleano
 * per un Db di tipo Memoria.
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 3-nov-2004 ore 15.40.14
 */
public class TipoDatiMemBooleano extends TipoDatiMemBase {


    /**
     * Costruttore completo
     * <p/>
     */
    protected TipoDatiMemBooleano() {
        /* rimanda al costruttore della superclasse */
        super();

        /* regolazioni iniziali di riferimenti e variabili */
        try {                                   // prova ad eseguire il codice
            this.inizia();
        } catch (Exception unErrore) {           // intercetta l'errore
            /** messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */
    } /* fine del metodo costruttore base */


    /**
     * Regolazioni iniziali di riferimenti e variabili
     * <p/>
     * Metodo chiamato direttamente dal costruttore
     */
    private void inizia() throws Exception {
    } /* fine del metodo inizia */

    //-------------------------------------------------------------------------
}// fine della classe
//-----------------------------------------------------------------------------

