/**
 * Title:        MessaggioAvviso.java
 * Package:      it.algos.base.messaggio
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:      Algos s.r.l.
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /
 * Creato:       il 30 settembre 2002 alle 12.28
 */
//-----------------------------------------------------------------------------
// Copyright (c) 2002  Algos s.r.l. All Rights Reserved.
//-----------------------------------------------------------------------------
//-----------------------------------------------------------------------------
//  Package di questa classe e classi importate
//-----------------------------------------------------------------------------
package it.algos.base.messaggio;

import it.algos.base.libreria.Lib;

import javax.swing.*;

//-----------------------------------------------------------------------------
// Classe principale
//-----------------------------------------------------------------------------


/**
 * Estende le funzionalita della classe JOptionPane mostrando un messaggio di
 * avviso.<br>
 *
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /  30 settembre 2002 ore 12.28
 */
public class MessaggioAvviso extends MessaggioBase {


    /**
     * voce della finestra di dialogo
     */
    public static final String TITOLO = "Messaggio di avviso";


    /**
     * Costruttore base senza parametri <br>
     * Rimanda al costruttore completo utilizzando i valori di default
     */
    public MessaggioAvviso() {
        /** rimanda al costruttore di questa classe */
        this(null);
    } /* fine del metodo costruttore base */


    /**
     * Costruttore
     *
     * @param unaChiave chiave per il testo del messaggio
     */
    public MessaggioAvviso(String unaChiave) {
        /** rimanda al costruttore di questa classe */
        this(unaChiave, "");
    } /* fine del metodo costruttore */


    /**
     * Costruttore completo
     *
     * @param unaChiave chiave per il testo del messaggio
     * @param unAggiunta testo aggiuntivo
     */
    public MessaggioAvviso(String unaChiave, String unAggiunta) {
        /* rimanda al costruttore della superclasse */
        super(unaChiave, unAggiunta);

        /* Operazioni alla partenza ed eventuale interfaccia utente */
        this.partenza();
    } /* fine del metodo costruttore completo */


    /**
     * Costruttore completo
     *
     * @param unaChiaveIniziale chiave per la prima parte del testo
     * @param unNumero valore da inserire nel testo in mezzo
     * @param unaChiaveFinale chiave per la parte finale del testo
     */
    public MessaggioAvviso(String unaChiaveIniziale, int unNumero, String unaChiaveFinale) {
        /** rimanda al costruttore della superclasse */
//	super(unaChiaveIniziale, unNumero, unaChiaveFinale) ;

        /** Operazioni alla partenza e eventuale interfaccia utente */
        this.partenza();
    } /* fine del metodo costruttore completo */


    //-------------------------------------------------------------------------
    // Metodi statici della classe                              (class methods)
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Metodi privati chiamati dal costruttore prima di tornare alla sottoclasse
    //-------------------------------------------------------------------------
    /**
     * Operazioni alla partenza ed eventuale interfaccia utente
     * Cambia il testo del bottone standard
     */
    private void partenza() {
        /**
         *  crea un array di oggetti (di tipo testo), di lunghezza uno e prende
         *  il valore del testo dalla classe specifica
         */
        String[] valori = {"continua"};
//	String[] valori = {Testo.get(BOTTONE_CONTINUA)} ;

        /* emette un beep */
        Lib.Sist.beep();

        /* mostra il messaggio */
        JOptionPane.showOptionDialog(null,
                unTesto,
                TITOLO,
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                valori,
                valori[0]);
    } /* fine del metodo */
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
}// fine della classe it.algos.base.messaggio.MessaggioAvviso.java

//-----------------------------------------------------------------------------

