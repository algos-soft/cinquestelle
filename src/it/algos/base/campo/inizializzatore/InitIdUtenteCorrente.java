/**
 * Title:        InitIdUtenteCorrente.java
 * Package:      it.algos.base.campo.inizializzatore
 * Description:  Inizializzatore con id dell'utente corrente
 * Copyright:    Copyright (c) 2005
 * Company:      Algos s.r.l.
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  alex
 * @version 1.0  /
 * Creato:       il 22 settembre 2003 alle 16.44
 */

package it.algos.base.campo.inizializzatore;

import it.algos.base.errore.Errore;
import it.algos.base.progetto.Progetto;

/**
 * Inizializzatore Id Utente Corrente.
 * <p/>
 * Ritorna l'id dell'utente corrente.
 *
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  alex
 * @version 1.0  /  22 settembre 2003 ore 16.44
 */
class InitIdUtenteCorrente extends InitBase {

    /**
     * Costruttore completo.
     * <p/>
     */
    public InitIdUtenteCorrente() {

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
        /* variabili e costanti locali di lavoro */
        Integer idInteger = null;
        int id = 0;

        try { // prova ad eseguire il codice
            id = Progetto.getIdUtenteCorrente();
            idInteger = new Integer(id);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return idInteger;
    } /* fine del metodo */

}