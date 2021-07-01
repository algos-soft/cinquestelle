/**
 * Title:        StampabileIntestato.java
 * Package:      it.algos.base.stampa.stampabile
 * Description:
 * Copyright:    Copyright (c) 2002, 2003
 * Company:      Algos s.r.l.
 * @author Guido Andrea Ceresa & Alessandro Valbonesi
 * @version 1.0  /
 * Creato:       il 4 novembre 2003 alle 11.12
 */
package it.algos.base.stampa.stampabile;

import it.algos.base.errore.Errore;

import java.awt.*;
import java.awt.print.PageFormat;

/**
 * Questa classe concreta e' responsabile di: <br>
 * A - Fornire l'implementazione concreta di un oggetto stampabile intestato <br>
 *
 * @author Guido Andrea Ceresa
 * @author Alessandro Valbonesi
 * @author alex
 * @version 1.0  /  4 novembre 2003 ore 11.12
 */
public class StampabileIntestato extends StampabileDefault {

    /**
     * Costruttore base senza parametri <br>
     * Indispensabile anche se non viene utilizzato
     * (anche solo per compilazione in sviluppo) <br>
     */
    public StampabileIntestato() {
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
     * Regolazioni iniziali di riferimenti e variabili <br>
     * Metodo chiamato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
    } /* fine del metodo inizia */


    /**
     * Il metodo print() viene invocato direttamente dal sottosistema
     * di stampa e va obbligatoriamente implementato nelle sottoclassi.
     */
    public int print(Graphics unGrafico, PageFormat unFormatoPagina, int unIndicePagina) {
        return 0;
    }


}