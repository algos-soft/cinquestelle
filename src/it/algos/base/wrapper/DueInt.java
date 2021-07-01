/**
 * Title:        DueInt.java
 * Package:      it.algos.base.wrapper
 * Description:
 * Copyright:    Copyright (c) 2002, 2003
 * Company:      Algos s.r.l.
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  alex
 * @version 1.0  /
 * Creato:       il 27 maggio 2003 alle 10.13
 */

package it.algos.base.wrapper;


/**
 * Questa classe concreta e' responsabile di: <br>
 * A - Implementare un oggetto che contiene una coppia di valori int <br>
 *
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  alex
 * @version 1.0  /  27 maggio 2003 ore 10.13
 */
public class DueInt extends Object {

    public int x = 0;

    public int y = 0;


    /**
     * Costruttore base senza parametri
     * <p/>
     * Indispensabile anche se non viene utilizzato
     * (anche solo per compilazione in sviluppo) <br>
     * Rimanda al costruttore completo utilizzando eventuali valori di default
     */
    public DueInt() {
        /** rimanda al costruttore di questa classe */
        this(0, 0);
    } /* fine del metodo costruttore base */


    /**
     * Costruttore completo.
     * <p/>
     */
    public DueInt(int x, int y) {
        /* rimanda al costruttore della superclasse */
        super();

        /* regola le variabili di istanza coi parametri */
        this.x = x;
        this.y = y;

    } /* fine del metodo costruttore completo */


    /**
     * Implementa la comparazione di due oggetti di questa
     * classe (this e un'altro).
     * <p/>
     * I due oggetti sono uguali quando hanno gli stessi valori
     * per i campi x e y.
     *
     * @param altroOggetto l'oggetto da verificare rispetto a questo (this)
     *
     * @return true se i due oggetti sono uguali
     */
    public boolean equals(Object altroOggetto) {
        /* variabili e costanti locali di lavoro */
        boolean uguali = false;
        DueInt altro = null;

        /* Controlli preliminari per tutti gli operatori equals() */
        if (altroOggetto == this) {
            return true; // e' lo stesso oggetto
        }
        if (altroOggetto == null) {
            return false; // l'altro oggetto e' null
        }
        if (!(altroOggetto instanceof DueInt)) {
            return false; // tipi non comparabili
        }

        /* procedo alla comparazione */
        altro = (DueInt)altroOggetto;

        /* comparo l'elemento x dei due oggetti */
        if (this.x == altro.x) {
            /* comparo l'elemento y dei due oggetti */
            if (this.y == altro.y) {
                uguali = true;
            } /* fine del blocco if-else */
        } /* fine del blocco if-else */

        /* valore di ritorno */
        return uguali;
    } /* fine del metodo */


}// fine della classe