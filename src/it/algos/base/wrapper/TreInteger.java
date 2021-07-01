/**
 * Title:        DueInteger.java
 * Package:      it.algos.base.wrapper
 * Description:
 * Copyright:    Copyright (c) 2002, 2003
 * Company:      Algos s.r.l.
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  alex
 * @version 1.0  /
 * Creato:       il 27 maggio 2003 alle 10.13
 */
//-----------------------------------------------------------------------------
// Copyright (c) 2002, 2003  Algos s.r.l. All Rights Reserved.
//-----------------------------------------------------------------------------
//-----------------------------------------------------------------------------
//  Package di questa classe e classi importate
//-----------------------------------------------------------------------------
package it.algos.base.wrapper;

//-----------------------------------------------------------------------------
// Classe principale


//-----------------------------------------------------------------------------
/**
 * Questa classe concreta e' responsabile di: <br>
 * A - Implementare un oggetto che contiene una coppia di valori Integer <br>
 *
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  alex
 * @version 1.0  /  27 maggio 2003 ore 10.13
 */
public final class TreInteger extends Object {

    //-------------------------------------------------------------------------
    // Costanti statiche della classe    (private,pubbliche)  (class variables)
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Variabili statiche della classe         (private)      (class variables)
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Riferimenti ad altri oggetti    (private,protette)  (instance variables)
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Variabili degli oggetti (private,protette,pubbliche)(instance variables)
    //-------------------------------------------------------------------------
    public Integer x = null;

    public Integer y = null;

    public Integer z = null;


    //-------------------------------------------------------------------------
    // Costruttori della classe                                   constructors)
    //-------------------------------------------------------------------------
    /**
     * Costruttore base senza parametri <br>
     * Indispensabile anche se non viene utilizzato
     * (anche solo per compilazione in sviluppo) <br>
     * Rimanda al costruttore completo utilizzando eventuali valori di default
     */
    public TreInteger() {
        /** rimanda al costruttore di questa classe */
        this(null, null, null);
    } /* fine del metodo costruttore base */


    /**
     * Costruttore completo
     */
    public TreInteger(Integer x, Integer y, Integer z) {
        /** rimanda al costruttore della superclasse */
        super();

        /** regola le variabili di istanza coi parametri */
        this.x = x;
        this.y = y;
        this.z = z;

    } /* fine del metodo costruttore completo */


    //-------------------------------------------------------------------------
    // Metodi statici della classe                              (class methods)
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Metodi privati chiamati dal costruttore prima di tornare alla sottoclasse
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Metodi protetti chiamati dalla sottoclasse subito dopo il costruttore
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Metodi privati degli oggetti (usati nella classe)     (instance methods)
    //-------------------------------------------------------------------------
    /**
     * Implementa la comparazione di due Integer
     * I due oggetti sono uguali nei seguenti casi:
     * - sono entrambi nulli
     * - sono entrambi non nulli e hanno lo stesso valore
     *
     * @param x il primo Integer
     * @param y il secondo Integer
     *
     * @return true se i due Integer sono uguali
     */
    private boolean compara(Integer x, Integer y) {
        /** variabili e costanti locali di lavoro */
        boolean uguali = false;

        /* comparo i due oggetti
         * (l'operatore & valuta sempre entrambi) */
        if ((x == null) & (y == null)) {   //entrambi nulli, quindi sono uguali
            uguali = true;

        } else {    //almeno uno non e' nullo
            if ((x != null) & (y != null)) {   //entrambi non nulli, li comparo
                if ((x).equals(y)) {
                    uguali = true;
                } /* fine del blocco if */
            } /* fine del blocco if-else */

        } /* fine del blocco if-else */

        /** valore di ritorno */
        return uguali;
    } /* fine del metodo */


    //-------------------------------------------------------------------------
    // Metodi protetti degli oggetti (usati nelle sottoclassi)    (instance m.)
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Metodi che verranno sovrascritti nelle sottoclassi          (overriding)
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Metodi che sovrascrivono metodi della superclasse           (overriding)
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Metodi pubblici degli oggetti (usati in altre classi)      (instance m.)
    //-------------------------------------------------------------------------
    /**
     * Implementa la comparazione di due oggetti di questa
     * classe (this e un'altro)
     * I due oggetti sono uguali quando hanno gli stessi valori
     * per i campi x, y e z (anche valori nulli)
     *
     * @param altroOggetto l'oggetto da verificare rispetto a questo (this)
     *
     * @return true se i due oggetti sono uguali
     */
    public boolean equals(Object altroOggetto) {

        //* Controlli preliminari per tutti gli operatori equals() */
        if (altroOggetto == this) {
            return true; // e' lo stesso oggetto
        }
        if (altroOggetto == null) {
            return false; // l'altro oggetto e' null
        }
        if (!(altroOggetto instanceof TreInteger)) {
            return false; // tipi non comparabili
        }

        //* procedo alla comparazione */
        boolean uguali = false;
        TreInteger questo = this;
        TreInteger altro = (TreInteger)altroOggetto;

        /* comparo l'elemento x dei due oggetti */
        if (this.compara(questo.x, altro.x)) {
            /* comparo l'elemento y dei due oggetti */
            if (this.compara(questo.y, altro.y)) {
                /* comparo l'elemento z dei due oggetti */
                if (this.compara(questo.z, altro.z)) {
                    uguali = true;
                } /* fine del blocco if-else */
            } /* fine del blocco if-else */
        } /* fine del blocco if-else */

        /** valore di ritorno */
        return uguali;
    } /* fine del metodo */
    //-------------------------------------------------------------------------
    // Metodi di regolazione delle variabili locali                    (setter)
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Metodi di restituzione delle variabili locali                   (getter)
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Metodi di gestione interni, invocati dai metodi azione     (instance m.)
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Metodi azione                                         (instance methods)
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Classi azione interne degli eventi                         (inner class)
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
}// fine della classe it.algos.base.wrapper.DueDouble.java
//-----------------------------------------------------------------------------

