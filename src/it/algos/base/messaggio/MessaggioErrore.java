/**
 * Title:        MessaggioErrore.java
 * Package:      it.algos.base.messaggio
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:      Algos s.r.l.
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /
 * Creato:       il 30 settembre 2002 alle 13.53
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
 * errore.<br>
 *
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /  30 settembre 2002 ore 13.53
 */
public class MessaggioErrore extends MessaggioBase {

    //-------------------------------------------------------------------------
    // Costanti statiche della classe    (private,pubbliche)  (class variables)
    //-------------------------------------------------------------------------
    /**
     * nome di questa classe (per i messaggi di errore)
     */
    private static final String NOMECLASSE = "MessaggioErrore";

    /**
     * voce della finestra di dialogo
     */
    private static final String TITOLO = "Messaggio di errore";


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
     * Rimanda al costruttore completo utilizzando i valori di default
     */
    public MessaggioErrore() {
        /** rimanda al costruttore di questa classe */
        this(null);
    } /* fine del metodo costruttore base */


    /**
     * Costruttore
     *
     * @param unaChiave chiave per il testo del messaggio
     */
    public MessaggioErrore(String unaChiave) {
        /** rimanda al costruttore di questa classe */
        this(unaChiave, "");
    } /* fine del metodo costruttore */


    /**
     * Costruttore
     *
     * @param unaChiave chiave per il testo del messaggio
     * @param unOggetto chi ha invocato l'errore
     */
    public MessaggioErrore(String unaChiave, Object unOggetto) {
        /** rimanda al costruttore di questa classe */
        this(unaChiave, unOggetto.getClass().getName());
    } /* fine del metodo costruttore */


    /**
     * Costruttore completo
     *
     * @param unaChiave chiave per il testo del messaggio
     * @param unAggiunta testo aggiuntivo
     */
    public MessaggioErrore(String unaChiave, String unAggiunta) {
        /** rimanda al costruttore della superclasse */
        super(unaChiave, unAggiunta);

        /** Operazioni alla partenza ed eventuale interfaccia utente */
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

        /** mostra il messaggio */
        JOptionPane.showOptionDialog(null,
                unTesto,
                TITOLO,
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.ERROR_MESSAGE,
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
}// fine della classe it.algos.base.messaggio.MessaggioErrore.java
//-----------------------------------------------------------------------------

