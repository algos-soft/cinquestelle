/**
 * Title:        InteroBooleano.java
 * Package:      it.algos.base.wrapper
 * Description:  Contenitore per un oggetto formato da una coppia
 *               di primitive int - boolean
 * Copyright:    Copyright (c) 2002, 2004
 * Company:      Algos s.r.l.
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  alex
 * @version 1.0  /
 * Creato:       il 30 dicembre 2003 alle 13.23
 */
//-----------------------------------------------------------------------------
// Copyright (c) 2002, 2004  Algos s.r.l. All Rights Reserved.
//-----------------------------------------------------------------------------
//-----------------------------------------------------------------------------
// Package di questa classe e classi importate
//-----------------------------------------------------------------------------
package it.algos.base.wrapper;


/**
 * Questa classe concreta e' responsabile di: <br>
 * A - Implementare un oggetto che contiene una coppia di primitive int - boolean<br>
 *
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  alex
 * @version 1.0  /  30 dicembre 2003 ore 13.23
 */
public final class IntBool extends Object {

    public int i = 0;

    public boolean b = false;


    /**
     * Costruttore base senza parametri <br>
     * Indispensabile anche se non viene utilizzato
     * (anche solo per compilazione in sviluppo) <br>
     */
    public IntBool() {
        /** rimanda al costruttore completo con dei valori di default */
        this(0, false);

    } /* fine del metodo costruttore base */


    /**
     * Costruttore completo
     *
     * @param i il valore intero
     * @param b il valore booleano
     */
    public IntBool(int i, boolean b) {
        /** rimanda al costruttore della superclasse */
        super();

        /** regola le variabili di istanza coi parametri */
        this.i = i;
        this.b = b;

    } /* fine del metodo costruttore completo */


    /**
     * Getter for property i.
     *
     * @return Value of property i.
     */
    public int getInt() {
        return this.i;
    }


    /**
     * Getter for property b.
     *
     * @return Value of property b.
     */
    public boolean getBool() {
        return this.b;
    }


    /**
     * Setter for property i.
     *
     * @param new Value of property i.
     */
    public void setInt(int i) {
        this.i = i;
    }


    /**
     * Setter for property b.
     *
     * @param new Value of property b.
     */
    public void setBool(boolean b) {
        this.b = b;
    }

    //-------------------------------------------------------------------------
}// fine della classe
//-----------------------------------------------------------------------------

