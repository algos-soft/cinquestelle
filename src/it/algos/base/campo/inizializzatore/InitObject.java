/**
 * Title:     InitObject
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      18-mag-2006
 */
package it.algos.base.campo.inizializzatore;

import it.algos.base.errore.Errore;

/**
 * Inizializzatore con valore fisso oggetto.
 * <p/>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 28-gen-2005 ore 10.09.49
 */
final class InitObject extends InitBase {


    /**
     * Costruttore completo.
     * <p/>
     *
     * @param valore il valore fisso da assegnare
     */
    public InitObject(Object valore) {
        /* rimanda al costruttore della superclasse */
        super();

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.setValoreFisso(valore);
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo costruttore completo


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
        return this.getValoreFisso();
    } /* fine del metodo */


}// fine della classe
