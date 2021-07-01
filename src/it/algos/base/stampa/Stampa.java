/**
 * Title:        Stampa.java
 * Package:      it.algos.base.stampa
 * Description:
 * Copyright:    Copyright (c) 2002, 2003
 * Company:      Algos s.r.l.
 * @author Guido Andrea Ceresa & Alessandro Valbonesi
 * @version 1.0  /
 * Creato:       il 6 novembre 2003 alle 16.09
 */
package it.algos.base.stampa;

import it.algos.base.errore.Errore;
import it.algos.base.stampa.gestore.GestoreStampa;
import it.algos.base.stampa.stampabile.Stampabile;


/**
 * Questa classe astratta factory e' responsabile di: <br>
 * A - Implementare i metodi Factory per la gestione delle stampe <br>
 * B - ... <br>
 *
 * @author Guido Andrea Ceresa
 * @author Alessandro Valbonesi
 * @author alex
 * @version 1.0  /  6 novembre 2003 ore 16.09
 */
public abstract class Stampa extends Object {

    /**
     * Costruttore base senza parametri <br>
     * Indispensabile anche se non viene utilizzato
     * (anche solo per compilazione in sviluppo) <br>
     */
    public Stampa() {
        /** rimanda al costruttore della superclasse */
        super();
    } /* fine del metodo costruttore completo */


    /**
     * Crea una stampa di uno Stampabile
     *
     * @param unoStampabile l'oggetto Stampabile da stampare
     *
     * @return l'oggetto Stampa da inviare alla stampante
     */
    public static GestoreStampa creaStampa(Stampabile unoStampabile) {
        /** variabili e costanti locali di lavoro */
        GestoreStampa unGestoreStampa = null;

        try {    // prova ad eseguire il codice
            /** crea un'istanza della classe da ritornare */
            unGestoreStampa = new GestoreStampa(unoStampabile);
        } catch (Exception unErrore) {    // intercetta l'errore
            /** mostra il messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /** valore di ritorno */
        return unGestoreStampa;
    } /* fine del metodo */


    /**
     * Crea una stampa di uno Stampabile e la invia alla stampante
     *
     * @param unoStampabile l'oggetto Stampabile da stampare
     */
    public static void eseguiStampa(Stampabile unoStampabile) {
        /** variabili e costanti locali di lavoro */
        GestoreStampa unGestoreStampa = null;

        try {    // prova ad eseguire il codice

            unGestoreStampa = creaStampa(unoStampabile);
            unGestoreStampa.esegui();

        } catch (Exception unErrore) {    // intercetta l'errore
            /** mostra il messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

    } /* fine del metodo */

}
