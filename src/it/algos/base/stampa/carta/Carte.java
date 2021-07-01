/**
 * Title:        Carte.java
 * Package:      it.algos.base.stampa.carta
 * Description:
 * Copyright:    Copyright (c) 2003
 * Company:      Algos s.r.l.
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  alex
 * @version 1.0  /
 * Creato:       il 14 novembre 2003 alle 14.50
 */
//-----------------------------------------------------------------------------
// Copyright (c) 2003  Algos s.r.l. All Rights Reserved.
//-----------------------------------------------------------------------------
//-----------------------------------------------------------------------------
// Package di questa classe e classi importate
//-----------------------------------------------------------------------------
package it.algos.base.stampa.carta;

import it.algos.base.errore.Errore;

import java.util.HashMap;

//-----------------------------------------------------------------------------
// Classe principale


//-----------------------------------------------------------------------------
/**
 * Questa classe concreta e' responsabile di: <br>
 * A - Implementare il <i>design pattern</i> <b>Singleton</b>  <br>
 * B - Mantenere una collezione di oggetti Carta <br>
 *
 * @author Guido Andrea Ceresa
 * @author Alessandro Valbonesi
 * @author alex
 * @version 1.0  /  14 novembre 2003 ore 14.50
 */
public final class Carte extends Object {

    /**
     * codici identificativi degli oggetti Carta
     * (servono come chiavi della collezioneCarte)
     */
    public static final Integer A5 = new Integer(1); // Carta A5

    public static final Integer A4 = new Integer(2); // Carta A4

    public static final Integer A3 = new Integer(3); // Carta A3

    public static final Integer US_LETTER = new Integer(4); // Carta US Letter

    /**
     * margini di default (se non vengono specificati quando si crea la Carta)
     */
    public static final double SX = 42;

    public static final double DX = 42;

    public static final double SUP = 50;

    public static final double INF = 50;

    /**
     * istanza unica di questa classe, creata ad avvio del programma
     * Attenzione! la classe va istanziata dopo la creazione delle
     * altre variabili statiche, perche' il costruttore le usa!
     */
    private final static Carte ISTANZA = new Carte();

    /**
     * collezione di oggetti Carta
     */
    private HashMap collezioneCarte = null;


    /**
     * Costruttore completo
     */
    private Carte() {
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
     * Recupera una Carta dalla collezione
     *
     * @param chiave la chiave corrispondente all'oggetto Carta da recuperare
     */
    public static Carta getCarta(Integer chiave) {
        return getIstanza().recuperaCarta(chiave);
    } /* fine del metodo getter */


    /**
     * Regolazioni iniziali di riferimenti e variabili <br>
     * Metodo chiamato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {

        /** crea la collezione di oggetti Carta */
        collezioneCarte = new HashMap();

        /** crea gli oggetti Carta e li aggiunge alla collezione */
        this.addCarta(A5, 420, 595);
        this.addCarta(A4, 595, 842);
        this.addCarta(A3, 842, 1191);
        this.addCarta(US_LETTER, 612, 792);

    } /* fine del metodo inizia */


    /**
     * aggiunge una nuova Carta alla collezione
     *
     * @param chiave la chiave per la collezione
     * @param w la larghezza
     * @param h l'altezza
     * @param sx il margine sinistro di default
     * @param dx il margine destro di default
     * @param sup il margine superiore di default
     * @param inf il margine inferiore di default
     */
    private void addCarta(Integer chiave,
                          double w,
                          double h,
                          double sx,
                          double dx,
                          double sup,
                          double inf) {

        /** variabili e costanti locali di lavoro */
        Carta unaCarta = null;

        /** crea una nuova Carta e la regola */
        unaCarta = new Carta();
        unaCarta.setDimensione(w, h);
        unaCarta.setMargineDefault(sx, dx, sup, inf);

        /** aggiunge la Carta alla collezione */
        collezioneCarte.put(chiave, unaCarta);

    } /* fine del metodo */


    /**
     * aggiunge una nuova Carta alla collezione
     * usa i margini di default
     *
     * @param chiave la chiave per la collezione
     * @param w la larghezza
     * @param h l'altezza
     */
    private void addCarta(Integer chiave, double w, double h) {

        /** rimanda al metodo delegato
         *  utilizzando i margini di default */
        this.addCarta(chiave, w, h, SX, DX, SUP, INF);

    } /* fine del metodo */


    /**
     * Recupera una Carta dalla collezione
     *
     * @param chiave la chiave corrispondente all'oggetto Carta da recuperare
     */
    private Carta recuperaCarta(Integer chiave) {
        /** variabili e costanti locali di lavoro */
        Carta unaCarta = null;

        unaCarta = (Carta)collezioneCarte.get(chiave);

        return unaCarta;
    } /* fine del metodo getter */


    /**
     * recupera l'istanza di questo Singleton
     */
    private static Carte getIstanza() {
        return ISTANZA;
    } /* fine del metodo getter */

    //-------------------------------------------------------------------------
}// fine della classe
//-----------------------------------------------------------------------------

