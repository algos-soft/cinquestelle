/**
 * Title:        StampabileDefault.java
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

/**
 * Questa classe concreta e' responsabile di: <br>
 * A - Fornire l'implementazione concreta di un oggetto stampabile di default <br>
 *
 * @author Guido Andrea Ceresa
 * @author Alberto Colombo
 * @author Alessandro Valbonesi
 * @author alex
 * @version 1.0  /  4 novembre 2003 ore 11.12
 */
public class StampabileDefault extends StampabileBase {


    /**
     * Costruttore base senza parametri <br>
     * Indispensabile anche se non viene utilizzato
     * (anche solo per compilazione in sviluppo) <br>
     */
    public StampabileDefault() {
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
     * Default number of pages for Pageable
     */
     public int getNumberOfPages(){
         return 1;
     }


}// fine della classe