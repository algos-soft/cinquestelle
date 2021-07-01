/**
 * Title:        IntBoolBool.java
 * Package:      it.algos.base.wrapper
 * Description:  Contenitore per un oggetto formato da un terzetto
 *               di primitive int - boolean - boolean
 * Copyright:    Copyright (c) 2002, 2004
 * Company:      Algos s.r.l.
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  alex
 * @version 1.0  /
 * Creato:       il 5 ott 2003 alle 14.50
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
 * Contenitore per un oggetto formato da un terzetto
 * di primitive int - boolean - boolean
 *
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  alex
 * @version 1.0  /  30 dicembre 2003 ore 13.23
 */
public final class IntBoolBool extends Object {

    public int i = 0;

    public boolean b1 = false;

    public boolean b2 = false;


    /**
     * Costruttore base senza parametri <br>
     * Indispensabile anche se non viene utilizzato
     * (anche solo per compilazione in sviluppo) <br>
     */
    public IntBoolBool() {
        /** rimanda al costruttore completo con dei valori di default */
        this(0, false, false);

    } /* fine del metodo costruttore base */


    /**
     * Costruttore completo
     *
     * @param i il valore intero
     * @param b1 il primo valore booleano
     * @param b2 il secondo valore booleano
     */
    public IntBoolBool(int i, boolean b1, boolean b2) {
        /** rimanda al costruttore della superclasse */
        super();

        /** regola le variabili di istanza coi parametri */
        this.i = i;
        this.b1 = b1;
        this.b2 = b2;

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
     * Getter for property b1.
     *
     * @return Value of property b.
     */
    public boolean getBool1() {
        return this.b1;
    }


    /**
     * Getter for property b1.
     *
     * @return Value of property b.
     */
    public boolean getBool2() {
        return this.b2;
    }


    /**
     * Setter for property i.
     *
     * @param i new Value of property i.
     */
    public void setInt(int i) {
        this.i = i;
    }


    /**
     * Setter for property b1.
     *
     * @param b new Value of property b1.
     */
    public void setBool1(boolean b) {
        this.b1 = b;
    }


    /**
     * Setter for property b1.
     *
     * @param b new Value of property b1.
     */
    public void setBool2(boolean b) {
        this.b2 = b;
    }
    //-------------------------------------------------------------------------
}// fine della classe
//-----------------------------------------------------------------------------

