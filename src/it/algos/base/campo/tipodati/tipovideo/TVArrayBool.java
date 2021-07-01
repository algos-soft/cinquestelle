/**
 * Title:        TVBooleano.java
 * Package:      it.algos.base.campo.tipovideo
 * Description:
 * Copyright:    Copyright (c) 2003
 * Company:      Algos s.r.l.
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  alex
 * @version 1.0  /
 * Creato:       il 22 ottobre 2003 alle 13.05
 */
//-----------------------------------------------------------------------------
// Copyright (c) 2003  Algos s.r.l. All Rights Reserved.
//-----------------------------------------------------------------------------
//-----------------------------------------------------------------------------
// Package di questa classe e classi importate
//-----------------------------------------------------------------------------
package it.algos.base.campo.tipodati.tipovideo;

import it.algos.base.errore.Errore;

import java.util.ArrayList;

/**
 * Questa classe concreta e' responsabile di: <br>
 * A - Implementare il <i>design pattern</i> <b>Singleton</b>  <br>
 * B - Definire un modello dati per il tipo Video Array <br>
 *
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  alex
 * @version 1.0  /  22 ottobre 2003 alle 13.05
 */
public final class TVArrayBool extends TVBase {

    /**
     * istanza unica di questa classe, creata ad avvio del programma
     */
    private final static TVArrayBool ISTANZA = new TVArrayBool();


    /**
     * Costruttore base senza parametri <br>
     * Indispensabile anche se non viene utilizzato
     * (anche solo per compilazione in sviluppo) <br>
     */
    private TVArrayBool() {
        /** rimanda al costruttore della superclasse */
        super();

        /** regolazioni iniziali di riferimenti e variabili */
        try {                                   // prova ad eseguire il codice
            this.inizia();
        } catch (Exception unErrore) {           // intercetta l'errore
            /** messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */
    } /* fine del metodo costruttore completo */


    /**
     * Regolazioni iniziali di riferimenti e variabili
     * Metodo chiamato direttamente dal costruttore
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        this.setClasse((new ArrayList<Boolean>()).getClass());
        this.setValoreVuoto(null);
    } /* fine del metodo inizia */


    /**
     * metodo getter per ottenere il valore della variabile statica privata
     */
    public static TVArrayBool getIstanza() {
        return ISTANZA;
    } /* fine del metodo getter */

}