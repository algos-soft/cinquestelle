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
package it.algos.albergo.ristorante.menu.stampa.cucina;

/**
 * Questa classe concreta e' responsabile di: <br>
 * A - Implementare un oggetto che contiene una tripla di valori Integer <br>
 * rappresentanti i codici di piatto, contorno, modifica
 * (considerati per il confronto nel metodo equals())
 * un Integer per codice di riga Ordine (non considerato in equals())
 * un booleano per il flag Extra (non considerato in equals())
 * un Integer per codice di riga Menu (non considerato in equals())
 *
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  alex
 * @version 1.0  /  27 maggio 2003 ore 10.13
 */
public final class WrapperModifica extends Object {

    public Integer c = null;    // codice riga ordine

    public boolean e = false;   // flag extra

    public Integer m = null;    // codice riga menu

    public Integer x = null;    // codice piatto

    public Integer y = null;    // codice contorno

    public Integer z = null;    // codice modifica


    /**
     * Costruttore base senza parametri <br>
     * Indispensabile anche se non viene utilizzato
     * (anche solo per compilazione in sviluppo) <br>
     * Rimanda al costruttore completo utilizzando eventuali valori di default
     */
    public WrapperModifica() {
        /** rimanda al costruttore di questa classe */
        this(null, false, null, null, null, null);
    } /* fine del metodo costruttore base */


    /**
     * Costruttore completo
     */
    public WrapperModifica(Integer c, boolean e, Integer m, Integer x, Integer y, Integer z) {
        /** rimanda al costruttore della superclasse */
        super();

        /** regola le variabili di istanza coi parametri */
        this.c = c;
        this.e = e;
        this.m = m;
        this.x = x;
        this.y = y;
        this.z = z;

    } /* fine del metodo costruttore completo */


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
        if (!(altroOggetto instanceof WrapperModifica)) {
            return false; // tipi non comparabili
        }

        //* procedo alla comparazione */
        boolean uguali = false;
        WrapperModifica questo = this;
        WrapperModifica altro = (WrapperModifica)altroOggetto;

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


}// fine della classe
