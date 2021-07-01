/**
 * Title:        Carta.java
 * Package:      it.algos.base.stampa.carta
 * Description:  Carta Astratta
 * Copyright:    Copyright (c) 2003
 * Company:      Algos s.r.l.
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  alex
 * @version 1.0  /
 * Creato:       il 14 novembre 2003 alle 11.55
 */
//-----------------------------------------------------------------------------
// Copyright (c) 2003  Algos s.r.l. All Rights Reserved.
//-----------------------------------------------------------------------------
//-----------------------------------------------------------------------------
// Package di questa classe e classi importate
//-----------------------------------------------------------------------------
package it.algos.base.stampa.carta;

import it.algos.base.errore.Errore;
import it.algos.base.wrapper.DueDouble;
import it.algos.base.wrapper.QuattroDouble;

//-----------------------------------------------------------------------------
// Classe principale


//-----------------------------------------------------------------------------
/**
 * Questa classe astratta e' responsabile di: <br>
 * A - Implementare un oggetto astratto che descrive la dimensione e <br>
 * i margini di una carta
 *
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  alex
 * @version 1.0  /  14 novembre 2003 ore 11.55
 */
public final class Carta {

    //-------------------------------------------------------------------------
    // Costanti statiche della classe    (private,pubbliche)  (class variables)
    //-------------------------------------------------------------------------
    /**
     * margini di default del foglio (sinistro, destro, superiore, inferiore)
     */
    static final double MARGINE_DEFAULT_SX = 42;

    static final double MARGINE_DEFAULT_DX = 42;

    static final double MARGINE_DEFAULT_SUP = 50;

    static final double MARGINE_DEFAULT_INF = 50;

    //-------------------------------------------------------------------------
    // Variabili statiche della classe         (private)      (class variables)
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Riferimenti ad altri oggetti    (private,protette)  (instance variables)
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Variabili degli oggetti (private,protette,pubbliche)(instance variables)
    //-------------------------------------------------------------------------
    /**
     * dimensione della carta (larghezza, altezza)
     */
    protected DueDouble dimensione = null;

    /**
     * margini di default (sinistro, destro, superiore, inferiore)
     */
    protected QuattroDouble margineDefault = null;


    //-------------------------------------------------------------------------
    // Costruttori della classe                                   constructors)
    //-------------------------------------------------------------------------
    /**
     * Costruttore base senza parametri <br>
     * Indispensabile anche se non viene utilizzato
     * (anche solo per compilazione in sviluppo) <br>
     */
    protected Carta() {
        /** rimanda al costruttore della superclasse */
        super();

        /** regolazioni iniziali di riferimenti e variabili */
        try {                                   // prova ad eseguire il codice
            this.inizia();
        } catch (Exception unErrore) {           // intercetta l'errore
            /** messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */
    } /* fine del metodo costruttore base */


    //-------------------------------------------------------------------------
    // Metodi statici della classe                              (class methods)
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Metodi privati chiamati dal costruttore prima di tornare alla sottoclasse
    //-------------------------------------------------------------------------
    /**
     * Regolazioni iniziali di riferimenti e variabili
     * Metodo chiamato direttamente dal costruttore
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {

        /** crea le istanze delle variabili */
        this.dimensione = new DueDouble();
        this.margineDefault = new QuattroDouble();

        /** regola i margini di default a un valore iniziale 
         *  regolazione modificabile nella carta specifica */
        this.setMargineDefault(MARGINE_DEFAULT_SX,
                MARGINE_DEFAULT_DX,
                MARGINE_DEFAULT_SUP,
                MARGINE_DEFAULT_INF);

    } /* fine del metodo inizia */


    //-------------------------------------------------------------------------
    // Metodi protetti chiamati dalla sottoclasse 
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Metodi privati degli oggetti (usati nella classe)     (instance methods)
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Metodi pubblici degli oggetti (usati in altre classi)      (instance m.)
    //-------------------------------------------------------------------------    
    //-------------------------------------------------------------------------
    // Metodi di regolazione delle variabili locali                    (setter)
    //-------------------------------------------------------------------------
    /**
     * dimensione della carta (larghezza, altezza)
     */
    protected void setDimensione(double larghezza, double altezza) {
        this.dimensione.x = larghezza;
        this.dimensione.y = altezza;
    } /* fine del metodo setter */


    /**
     * margini di default (sinistro, destro, superiore, inferiore)
     */
    protected void setMargineDefault(double sx, double dx, double sup, double inf) {
        this.margineDefault.a = sx;
        this.margineDefault.b = dx;
        this.margineDefault.c = sup;
        this.margineDefault.d = inf;
    } /* fine del metodo setter */


    //-------------------------------------------------------------------------
    // Metodi di restituzione delle variabili locali                   (getter)
    //-------------------------------------------------------------------------
    /**
     * dimensione della carta (larghezza, altezza)
     */
    public DueDouble getDimensione() {
        return dimensione;
    } /* fine del metodo getter */


    /**
     * margini di default (sinistro, destro, superiore, inferiore)
     */
    public QuattroDouble getMargineDefault() {
        return margineDefault;
    } /* fine del metodo getter */
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
}// fine della classe
//-----------------------------------------------------------------------------

