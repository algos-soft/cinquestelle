/**
 * Title:     TANumero
 * Copyright: Copyright (c) 2006
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      30-mar-2006
 */
package it.algos.base.campo.tipodati.tipoarchivio;

import it.algos.base.errore.Errore;

/**
 * Tipo Archivio del campo di tipo Numero generico.
 * Classe astratta da estendere obbligatoriamente.
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 30-mar-2006 ore 12.41.48
 */
public abstract class TANumero extends TABase {

    /**
     * Costruttore completo senza parametri.
     */
    protected TANumero() {
        /* rimanda al costruttore della superclasse */
        super();

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


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
