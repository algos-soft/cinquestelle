/**
 * Title:     OperatoreBase
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      26-ott-2004
 */
package it.algos.base.database.util;

import it.algos.base.errore.Errore;

/**
 * Operatore generico
 * </p>
 * Descrive il tipo di dati per un operatore.
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 26-ott-2004 ore 8.48.05
 */
public abstract class OperatoreBase implements Operatore {


    /**
     * Simbolo dell'operatore
     */
    private String simbolo = null;


    /**
     * Costruttore completo con parametri. <br>
     */
    public OperatoreBase() {
        /* rimanda al costruttore della superclasse */
        super();

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo costruttore completo


    /**
     * Regolazioni immediate di riferimenti e variabili. <br>
     * Metodo chiamato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
    }// fine del metodo inizia


    public String getSimbolo() {
        return simbolo;
    }


    public void setSimbolo(String simbolo) {
        this.simbolo = simbolo;
    }


}// fine della classe
