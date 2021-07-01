/**
 * Title:     RigaFatturaLogica
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      28-set-2006
 */
package it.algos.gestione.fattura.riga;

import it.algos.base.errore.Errore;
import it.algos.base.modulo.logica.LogicaBase;

/**
 * Business logic del modulo Riga Fattura.
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 28-set-2006 ore 15.00.06
 */
public final class RigaFatturaLogica extends LogicaBase {

    /**
     * Costruttore completo con parametri.
     * <p/>
     *
     * @param modulo
     */
    public RigaFatturaLogica(RigaFatturaModulo modulo) {
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


    /**
     * Sincronizza tutti i campi calcolati della riga fattura.
     * <p/>
     * Chiamato prima della registrazione
     * Chiamato eventualmente durante l'inserimento
     */
    public void sincronizza() {
        /* variabili e costanti locali di lavoro */

        try {    // prova ad eseguire il codice
            ;
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Sincronizza tutti i campi calcolati della riga fattura.
     * <p/>
     * Chiamato prima della registrazione
     * Chiamato eventualmente durante l'inserimento
     */
    private void sincroImponibile() {
        /* variabili e costanti locali di lavoro */

        try {    // prova ad eseguire il codice
            ;
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


}// fine della classe
