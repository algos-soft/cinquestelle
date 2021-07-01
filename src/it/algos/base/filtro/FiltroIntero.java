/**
 * Title:        FiltroIntero.java
 * Package:      it.algos.base.filtroOld
 * Description:  Filtro per i numeri interi
 * Copyright:    Copyright (c) 2002, 2003
 * Company:      Algos s.r.l.
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /
 * Creato:       il 24 gennaio 2003 alle 7.04
 */
//-----------------------------------------------------------------------------
// Copyright (c) 2002, 2003  Algos s.r.l. All Rights Reserved.
//-----------------------------------------------------------------------------
//-----------------------------------------------------------------------------
// Package di questa classe e classi importate
//-----------------------------------------------------------------------------
package it.algos.base.filtro;

import it.algos.base.costante.CostanteCarattere;
import it.algos.base.libreria.Lib;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
//-----------------------------------------------------------------------------
// Classe principale


//-----------------------------------------------------------------------------
/**
 * Questa classe concreta e' responsabile di: <br>
 * A - Filtrare solo i numeri interi <br>
 * B - Intero = cifre e segno meno <br>
 * C - Il numero massimo di caratteri non tiene conto dell'eventuale segno
 * meno, che e' in piu' <br>
 *
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /  24 gennaio 2003 ore 7.04
 */
public final class FiltroIntero extends FiltroAlgos implements CostanteCarattere {

    //-------------------------------------------------------------------------
    // Costanti statiche della classe    (private,pubbliche)  (class variables)
    //-------------------------------------------------------------------------
    /**
     * numero massimo di cifre di default
     */
    private static final int MAX_CIFRE_DEFAULT = MAX;

    /**
     * carattere meno (solo per la prima posizione)
     */
    private static char MENO = '-';


    //-------------------------------------------------------------------------
    // Variabili statiche della classe         (private)      (class variables)
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Riferimenti ad altri oggetti    (private,protette)  (instance variables)
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Variabili degli oggetti (private,protette,pubbliche)(instance variables)
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Costruttori della classe                                   constructors)
    //-------------------------------------------------------------------------
    /**
     * Costruttore base senza parametri <br>
     * Indispensabile anche se non viene utilizzato
     * (anche solo per compilazione in sviluppo) <br>
     * Rimanda al costruttore completo utilizzando eventuali valori di default
     */
    public FiltroIntero() {
        /** rimanda al costruttore di questa classe */
        this(MAX_CIFRE_DEFAULT);
    } /* fine del metodo costruttore base */


    /**
     * Costruttore completo
     *
     * @param maxCifre numero massimo di cifre
     */
    public FiltroIntero(int maxCifre) {
        /** rimanda al costruttore della superclasse */
        super();

        /** regola le variabili di istanza coi parametri */
        super.caratteriMassimi = maxCifre;
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
    //-------------------------------------------------------------------------
    // Metodi protetti degli oggetti (usati nelle sottoclassi)    (instance m.)
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Metodi che verranno sovrascritti nelle sottoclassi          (overriding)
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Metodi che sovrascrivono metodi della superclasse           (overriding)
    //-------------------------------------------------------------------------
    /**
     * Questo metodo viene invocato in automatico dal sistema:
     * a - quando apro una scheda (arriva il testo tutto insieme nel parametro
     * testo)
     * b - quando inserisco un carattere (arriva un singolo carattere nel
     * parametro testo)
     * Qui decido che se arriva nel campo un valore con un numero di cifre
     * superiore al massimo previsto, lo tengo lo stesso perche' era gia'
     * stato inserito e blocco la lunghezza del campo solo per i nuovi
     * inserimenti
     */
    public void insertString(int offSet, String testo, AttributeSet attributi) throws
            BadLocationException {
        /** variabili e costanti locali di lavoro */
        String unaStringa = "";
        char car = ' ';
        int numeroCifre = 0;

        /** recupera il valore del campo esistente prima dell'inserimento */
        unaStringa = (String)getText(0, getLength());

        /** cifre totali (campo pre-esistente + nuovo inserimento) */
        numeroCifre = unaStringa.length() + testo.length();

        /** controllo di congruita' */
        if (Lib.Testo.isValida(unaStringa)) {
            /** calcola le cifre con esclusione dell'eventuale segno meno iniziale */
            if ((unaStringa.charAt(0) == MENO) | (testo.charAt(0) == MENO)) {
                numeroCifre--;
            } /* fine del blocco if/else */

            /** se supera la lunghezza prevista non inserisce ulteriori caratteri */
            if (numeroCifre > caratteriMassimi) {
                Lib.Sist.beep();
                return;
            } /* fine del blocco if/else */

            /** ciclo for */
            for (int k = 0; k < testo.length(); k++) {
                car = testo.charAt(k);

                /** tutti i caratteri devono essere cifre
                 *  escluso solo il primo carattere che puo' essere il segno meno */
                if ((Character.isDigit(car)) | ((k == 0) & (offSet == 0) & (car == MENO))) {
                } else {
                    Lib.Sist.beep();
                    return;
                } /* fine del blocco if/else */
            } /* fine del blocco for */
        } else {
            /** primo carattere */
            car = testo.charAt(0);

            /** tutti i caratteri devono essere cifre
             *  escluso solo il primo carattere che puo' essere il segno meno */
            if ((Character.isDigit(car)) | (car == MENO)) {
            } else {
                Lib.Sist.beep();
                return;
            } /* fine del blocco if/else */
        } /* fine del blocco if/else */

        /** se non ci sono controindicazioni, 'passo' la chiamata al metodo
         *  sovrascritto */
        super.insertString(offSet, testo, attributi);
    } /* fine del metodo */
    //-------------------------------------------------------------------------
    // Metodi pubblici degli oggetti (usati in altre classi)      (instance m.)
    //-------------------------------------------------------------------------
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
}// fine della classe it.algos.base.filtroOld.FiltroIntero.java
//-----------------------------------------------------------------------------

