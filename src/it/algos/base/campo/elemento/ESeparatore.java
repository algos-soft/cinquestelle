/**
 * Title:        ESeparatore.java
 * Package:      it.algos.base.campo.elemento
 * Description:
 * Copyright:    Copyright (c) 2003
 * Company:      Algos s.r.l.
 * @author Ceresa, Colombo, Valbonesi
 * @version 1.0  /
 * Creato:       il 14 novembre 2003 alle 10.20
 */
//-----------------------------------------------------------------------------
// Copyright (c) 2003  Algos s.r.l. All Rights Reserved.
//-----------------------------------------------------------------------------
//-----------------------------------------------------------------------------
// Package di questa classe e classi importate
//-----------------------------------------------------------------------------
package it.algos.base.campo.elemento;

import it.algos.base.errore.Errore;

import javax.swing.*;
//-----------------------------------------------------------------------------
// Classe principale


//-----------------------------------------------------------------------------
/**
 * Questa classe concreta e' responsabile di: <br>
 * A - Costruire un elemento separatore da usare nelle lista (JList e JComboBox) <br>
 *
 * @author Guido Ceresa
 * @author Alberto Colombo
 * @author Alessandro Valbonesi
 * @author gac
 * @version 1.0  /  14 novembre 2003 ore 10.20
 */
public final class ESeparatore extends EBase {

    //-------------------------------------------------------------------------
    // Costanti statiche della classe    (private,pubbliche)  (class variables)
    //-------------------------------------------------------------------------
    /**
     * carattere separatore per costruire il testo
     */
    private static final char CARATTERE = '-';

    /**
     * ripetizioni standard del carattere
     */
    private static final int RIPETIZIONI = 30;

    /**
     * valore del codice associato a questo elemento nella matriceDoppia
     */
    private static final int CODICE = -87;

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
     * numero di caratteri uguali che compongono il testo
     */
    private int numeroCaratteri = 0;


    //-------------------------------------------------------------------------
    // Costruttori della classe                                   constructors)
    //-------------------------------------------------------------------------
    /**
     * Costruttore base senza parametri <br>
     * Indispensabile anche se non viene utilizzato
     * (anche solo per compilazione in sviluppo) <br>
     * Rimanda al costruttore completo utilizzando eventuali valori di default
     */
    public ESeparatore() {
        /** rimanda al costruttore di questa classe */
        this(RIPETIZIONI);
    } /* fine del metodo costruttore base */


    /**
     * Costruttore completo
     *
     * @param numeroCaratteri numero di caratteri uguali che compongono il testo
     */
    public ESeparatore(int numeroCaratteri) {
        /** rimanda al costruttore della superclasse */
        super();

        /** regola le variabili di istanza coi parametri */
        this.numeroCaratteri = numeroCaratteri;

        /** regolazioni iniziali di riferimenti e variabili */
        try {                                   // prova ad eseguire il codice
            this.inizia();
        } catch (Exception unErrore) {           // intercetta l'errore
            /** messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */
    } /* fine del metodo costruttore completo */


    //-------------------------------------------------------------------------
    // Metodi statici della classe                              (class methods)
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Metodi privati chiamati dal costruttore prima di tornare alla sottoclasse
    //-------------------------------------------------------------------------
    /**
     * Regolazioni iniziali di riferimenti e variabili <br>
     * Metodo chiamato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        /* variabili e costanti locali di lavoro */
        JComponent separatore = null;

        /** crea il testo in base al numero di ripetizioni del carattere */
        this.regolaTesto();

        /** regola il componente - attributo della superclasse */
        separatore = new JSeparator();
        super.setComponente(separatore);

        /** regola il codice - attributo della superclasse */
        super.setCodice(CODICE);
    } /* fine del metodo inizia */


    //-------------------------------------------------------------------------
    // Metodi protetti chiamati dalla sottoclasse subito dopo il costruttore
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Metodi privati degli oggetti (usati nella classe)     (instance methods)
    //-------------------------------------------------------------------------
    /**
     * crea il testo in base al numero di ripetizioni del carattere
     */
    private void regolaTesto() {
        /** variabili e costanti locali di lavoro */
        char[] caratteri = null;

        try {    // prova ad eseguire il codice
            /** crea un array provvisorio di caratteri */
            caratteri = new char[numeroCaratteri];

            /** riempie tutto l'array col medesimo carattere */
            for (int k = 0; k < numeroCaratteri; k++) {
                caratteri[k] = CARATTERE;
            } /* fine del blocco for */

            /** regola il testo - attributo della superclasse */
            super.setTesto(new String(caratteri));

        } catch (Exception unErrore) {    // intercetta l'errore
            /** mostra il messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */


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
}// fine della classe it.algos.base.campo.elemento.ESeparatore.java
//-----------------------------------------------------------------------------

