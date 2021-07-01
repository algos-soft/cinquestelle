/**
 * Title:        InitTimestampAttuale.java
 * Package:      it.algos.base.campo.inizializzatore
 * Description:  Inizializzatore con timestamp attuale
 * Copyright:    Copyright (c) 2003
 * Company:      Algos s.r.l.
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  alex
 * @version 1.0  /
 * Creato:       il 22 settembre 2003 alle 16.44
 */
//-----------------------------------------------------------------------------
// Copyright (c) 2002  Algos s.r.l. All Rights Reserved.
//-----------------------------------------------------------------------------

package it.algos.base.campo.inizializzatore;

import it.algos.base.errore.Errore;
import it.algos.base.progetto.Progetto;

import java.sql.Timestamp;

/**
 * Inizializzatore Timestamp.
 * <p/>
 * Ritorna il timestamp di sistema
 *
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  alex
 * @version 1.0  /  22 settembre 2003 ore 16.44
 */
class InitTimestampAttuale extends InitBase {

    /**
     * Costruttore completo.
     * <p/>
     */
    public InitTimestampAttuale() {

        /* rimanda al costruttore della superclasse */
        super();

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    } /* fine del metodo costruttore base */


    /**
     * Regolazioni statiche di riferimenti e variabili.
     * </p>
     * Metodo chiamato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
    }// fine del metodo inizia


    /**
     * Ritorna il valore di inizializzazione per il campo.
     * <p/>
     * Il valore ritornato e' a livello di Memoria
     */
    public Object getValore() {
        /** variabili e costanti locali di lavoro */
        Timestamp unValore = null;

        try { // prova ad eseguire il codice
            unValore = Progetto.getTimestampCorrente();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return unValore;
    } /* fine del metodo */

}