/**
 * Title:        MessaggioBase.java
 * Package:      it.algos.base.messaggio
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:      Algos s.r.l.
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /
 * Creato:       il 30 settembre 2002 alle 12.21
 */
//-----------------------------------------------------------------------------
// Copyright (c) 2002  Algos s.r.l. All Rights Reserved.
//-----------------------------------------------------------------------------
//-----------------------------------------------------------------------------
//  Package di questa classe e classi importate
//-----------------------------------------------------------------------------
package it.algos.base.messaggio;

import it.algos.base.costante.CostanteModulo;
import it.algos.base.errore.Errore;
import it.algos.base.errore.ErroreInizia;
import it.algos.base.libreria.Lib;
import it.algos.base.testo.Testo;

import javax.swing.*;
import java.util.ArrayList;
import java.util.ResourceBundle;
//-----------------------------------------------------------------------------
// Classe principale
//-----------------------------------------------------------------------------


/**
 * Classe astratta per raccogliere alcune funzionalità delle varie classi del
 * pacchetto messaggio.<br>
 *
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /  30 settembre 2002 ore 12.21
 */
public abstract class MessaggioBase extends JOptionPane implements CostanteModulo {

    //-------------------------------------------------------------------------
    // Costanti statiche della classe    (private,pubbliche)  (class variables)
    //-------------------------------------------------------------------------
    /**
     * nome di questa classe (per i messaggi di errore)
     */
    private static final String NOME_CLASSE = "MessaggioBase";

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
     * voce della finestra di dialogo
     */
    protected String titoloDialogo = "";

    /**
     * testo completo del messaggio
     */
    protected String unTesto = "";

    /**
     * risultato numerico della selezione fatta sui bottoni del dialogo
     */
    protected int risposta = 0;

    /**
     * chiave per il testo del messaggio iniziale (o unico)
     */
    private String unaChiave = "";

    /**
     * testo aggiuntivo (in mezzo od alla fine)
     */
    private String unTestoAggiunto = "";

    /**
     * carattere aggiuntivo di controllo per una seconda parte
     * (eventuale) del messaggio
     */
    private char unCodice = ' ';


    //-------------------------------------------------------------------------
    // Costruttori della classe                                   constructors)
    //-------------------------------------------------------------------------
    /**
     * Costruttore base senza parametri <br>
     * Rimanda al costruttore completo utilizzando i valori di default
     */
    public MessaggioBase() {
        /** rimanda al costruttore di questa classe */
        this("", "", ' ');
    } /* fine del metodo costruttore base */


    /**
     * Costruttore
     *
     * @param unaChiave chiave per il testo completo del messaggio
     */
    public MessaggioBase(String unaChiave) {
        /** rimanda al costruttore di questa classe */
        this(unaChiave, "", ' ');
    } /* fine del metodo costruttore */


    /**
     * Costruttore
     *
     * @param unaChiave chiave per il testo iniziale del messaggio
     * @param unTestoAggiunto testo aggiuntivo scritto al volo
     */
    public MessaggioBase(String unaChiave, String unTestoAggiunto) {
        /** rimanda al costruttore di questa classe */
        this(unaChiave, unTestoAggiunto, ' ');
    } /* fine del metodo costruttore */


    /**
     * Costruttore
     *
     * @param unaChiave chiave per la prima parte del testo del messaggio
     * @param unCodice carattere aggiuntivo per la seconda parte del
     * messaggio, variabile secondo le condizioni
     */
    public MessaggioBase(String unaChiave, char unCodice) {
        /** rimanda al costruttore di questa classe */
        this(unaChiave, "", unCodice);
    } /* fine del metodo costruttore */


    /**
     * Costruttore
     *
     * @param unaChiave chiave per la prima parte del testo del messaggio
     * @param unNumero valore da inserire tra le due parti del testo
     * @param unCodice carattere aggiuntivo per la seconda parte del
     * messaggio, variabile secondo le condizioni
     */
    public MessaggioBase(String unaChiave, int unNumero, char unCodice) {
        /** rimanda al costruttore di questa classe */
        this(unaChiave, Integer.toString(unNumero), unCodice);
    } /* fine del metodo costruttore */


    /**
     * Costruttore completo
     *
     * @param unaChiave chiave per la prima parte del testo del messaggio
     * @param unTestoAggiunto testo aggiunto al volo nel mezzo
     * @param unCodice carattere aggiuntivo per la seconda parte del
     * messaggio, variabile secondo le condizioni
     */
    public MessaggioBase(String unaChiave, String unTestoAggiunto, char unCodice) {
        /** rimanda al costruttore della superclasse */
        super();

        /** regola le variabili di istanza coi parametri */
        this.unaChiave = unaChiave;
        this.unTestoAggiunto = unTestoAggiunto;
        this.unCodice = unCodice;

        /** regolazioni iniziali di riferimenti e variabili */
        try {                                   // prova ad eseguire il codice
            this.inizia();
        } catch (Exception unErrore) {           // intercetta l'errore
            /** messaggio di errore */
            new ErroreInizia(NOME_CLASSE, unErrore);
        } /* fine del blocco try-catch */

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
     * Regolazioni iniziali di riferimenti e variabili
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        if (Testo.isChiaveValida(unaChiave)) {
            unTesto = Testo.get(unaChiave);
            /** controllo di congruita' */
            if (unTestoAggiunto != "") {
                unTesto += " " + unTestoAggiunto;
            } /* fine del blocco if */
        } else {
            unTesto = unaChiave;
        } /* fine di if-else */
        //unTesto = Libreria.formattaTesto(unTesto);
    } /* fine del metodo */


    /**
     * Operazioni alla partenza ed eventuale interfaccia utente
     */
    private void partenza() {
        this.recuperaSecondaParte();
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
    /**
     * ...
     */
    protected void recuperaTitolo(String titoloDefault) {
        /** variabili e costanti locali di lavoro */
        ResourceBundle unaRisorsa = null;
        String unFile = PATH_BASE + "testo.TestoDati";

        /** recupera il file di preferenze */
        try {
            unaRisorsa = ResourceBundle.getBundle(unFile);
            titoloDialogo = unaRisorsa.getString(unaChiave + "0");
        } catch (Exception unErrore) {
            titoloDialogo = titoloDefault;
        } /* fine del blocco try-catch */
    } /* fine del metodo */


    /**
     * ...
     */
    protected void recuperaSecondaParte() {
        /** variabili e costanti locali di lavoro */
        ResourceBundle unaRisorsa = null;
        String unFile = PATH_BASE + "testo.TestoDati";
        String testoSecondaParte = "";

        /** recupera il file di preferenze */
        try {
            unaRisorsa = ResourceBundle.getBundle(unFile);
            String alfa = unaChiave + String.valueOf(unCodice);
            testoSecondaParte = unaRisorsa.getString(alfa);
            unTesto += testoSecondaParte;
        } catch (Exception unErrore) {
        } /* fine del blocco try-catch */
    } /* fine del metodo */


    /**
     * ...
     */
    protected String[] recuperaBottoni() {
        /** variabili e costanti locali di lavoro */
        ResourceBundle unaRisorsa = null;
        String unTestoBottone = "";
        ArrayList listaBottoni = null;
        String unFile = PATH_BASE + "testo.TestoDati";
        int maxBottoni = 0;

        listaBottoni = new ArrayList();

        /** massimo numero teorico dei bottoni (volutamente grande) */
        maxBottoni = 99;

        /** valori di default dei bottoni */
        String[] bottoni = {"Si", "No"};

        /** recupera il file di preferenze */
        try {
            unaRisorsa = ResourceBundle.getBundle(unFile);
        } catch (Exception unErrore) {
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /** ciclo for sicuramente superiore al numero reale dei bottoni */
        for (int k = 1; k < maxBottoni; k++) {
            /** prova ad eseguire il codice */
            try {
                unTestoBottone = unaRisorsa.getString(unaChiave + k);
                if (Lib.Testo.isValida(unTestoBottone)) {
                    listaBottoni.add(unTestoBottone);
                } else {
                } /* fine del blocco if/else */
            } catch (Exception unErrore) {
                /** mostra il messaggio di errore */
                break;
            } /* fine del blocco try-catch */
        } /* fine del blocco for */

        /** valore di ritorno */
        if (listaBottoni.size() > 0) {
            return (String[])listaBottoni.toArray(bottoni);
        } else {
            return bottoni;
        } /* fine del blocco if/else */
    } /* fine del metodo */


    /**
     * risultato numerico della selezione fatta sui bottoni del dialogo
     */
    public int getRisposta() {
        return this.risposta;
    } /* fine del metodo getter */
}// fine della classe