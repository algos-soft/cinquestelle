/**
 * Title:        JTextFieldIntero.java
 * Package:      it.algos.base.jtext.field
 * Description:  Campo numerico
 * Copyright:    Copyright (c) 2002, 2003
 * Company:      Algos s.r.l.
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /
 * Creato:       il 24 gennaio 2003 alle 10.37
 */
//-----------------------------------------------------------------------------
// Copyright (c) 2002, 2003  Algos s.r.l. All Rights Reserved.
//-----------------------------------------------------------------------------
//-----------------------------------------------------------------------------
// Package di questa classe e classi importate
//-----------------------------------------------------------------------------
package it.algos.base.jtext.field;

import it.algos.base.campo.base.Campo;
import it.algos.base.filtro.FiltroIntero;

import javax.swing.text.Document;
//-----------------------------------------------------------------------------
// Classe principale


//-----------------------------------------------------------------------------
/**
 * Questa classe concreta e' responsabile di: <br>
 * A - Gestire un campo di numeri interi <br>
 * B - Crea un riferimento alla classe filtroOld <code>FiltroIntero</code> <br>
 * C - Sovrascrive il metodo <code>creaDefaulModel</code> <br>
 *
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /  24 gennaio 2003 ore 10.37
 */
public final class JTextFieldIntero extends JTextFieldAlgos {

    //-------------------------------------------------------------------------
    // Costanti statiche della classe    (private,pubbliche)  (class variables)
    //-------------------------------------------------------------------------
    /**
     * nome di questa classe (per i messaggi di errore)
     */
    private static final String NOME_CLASSE = "JTextFieldIntero";

    /**
     * numero massimo di cifre di default
     */
    private static final int MAX_CIFRE_DEFAULT = MAX;


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
    public JTextFieldIntero() {
        /** rimanda al costruttore di questa classe */
        this(null);
    } /* fine del metodo costruttore base */


    /**
     * Costruttore <br>
     * Rimanda al costruttore completo utilizzando eventuali valori di default
     *
     * @param unCampo riferimento al campo che contiene questo componente
     */
    public JTextFieldIntero(Campo unCampo) {
        /** rimanda al costruttore di questa classe */
        this(unCampo, MAX_CIFRE_DEFAULT);
    } /* fine del metodo costruttore */


    /**
     * Costruttore completo
     *
     * @param unCampo riferimento al campo che contiene questo componente
     * @param caratteriMassimi numero massimo di cifre
     */
    public JTextFieldIntero(Campo unCampo, int caratteriMassimi) {
        /** rimanda al costruttore della superclasse */
        super(unCampo);

        /** regola le variabili di istanza coi parametri */
        super.caratteriMassimi = caratteriMassimi;
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
     * ...
     */
    protected Document createDefaultModel() {
        return new FiltroIntero(caratteriMassimi);
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
}// fine della classe it.algos.base.jtext.field.JTextFieldIntero.java
//-----------------------------------------------------------------------------

