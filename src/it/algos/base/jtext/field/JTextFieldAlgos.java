/**
 * Title:        JTextFieldAlgos.java
 * Package:      it.algos.base.jtext.field
 * Description:
 * Copyright:    Copyright (c) 2002, 2003
 * Company:      Algos s.r.l.
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /
 * Creato:       il 21 gennaio 2003 alle 15.35
 */
//-----------------------------------------------------------------------------
// Copyright (c) 2002, 2003  Algos s.r.l. All Rights Reserved.
//-----------------------------------------------------------------------------
//-----------------------------------------------------------------------------
// Package di questa classe e classi importate
//-----------------------------------------------------------------------------
package it.algos.base.jtext.field;

import it.algos.base.campo.base.Campo;
import it.algos.base.costante.CostanteCarattere;
import it.algos.base.filtro.FiltroAlgos;

import javax.swing.*;
//-----------------------------------------------------------------------------
// Classe principale


//-----------------------------------------------------------------------------
/**
 * Questa classe astratta e' responsabile di: <br>
 * A - Estende la classe <code>JTextField</code>  <br>
 * B - Mantiene un riferimento al campo che contiene questo componente <br>
 * C - Superclasse delle classi di testo specializzate <br>
 *
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /  21 gennaio 2003 ore 15.35
 */
public abstract class JTextFieldAlgos extends JTextField implements CostanteCarattere {

    //-------------------------------------------------------------------------
    // Costanti statiche della classe    (private,pubbliche)  (class variables)
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Variabili statiche della classe         (private)      (class variables)
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Riferimenti ad altri oggetti    (private,protette)  (instance variables)
    //-------------------------------------------------------------------------
    /**
     * riferimento al campo che contiene questo componente
     */
    protected Campo unCampo = null;

    //-------------------------------------------------------------------------
    // Variabili degli oggetti (private,protette,pubbliche)(instance variables)
    //-------------------------------------------------------------------------
    /**
     * numero minimo di caratteri accettabili nel campo
     */
    protected int caratteriMinimi = 0;

    /**
     * numero massimo di caratteri accettabili nel campo
     */
    protected int caratteriMassimi = MAX;


    //-------------------------------------------------------------------------
    // Costruttori della classe                                   constructors)
    //-------------------------------------------------------------------------
    /**
     * Costruttore base senza parametri <br>
     * Indispensabile anche se non viene utilizzato
     * (anche solo per compilazione in sviluppo) <br>
     * Rimanda al costruttore completo utilizzando eventuali valori di default
     */
    public JTextFieldAlgos() {
        /** rimanda al costruttore di questa classe */
        this(null);
    } /* fine del metodo costruttore base */


    /**
     * Costruttore completo
     *
     * @param unCampo riferimento al campo che contiene questo componente
     */
    public JTextFieldAlgos(Campo unCampo) {
        /** rimanda al costruttore della superclasse */
        super();

        /** regola le variabili di istanza coi parametri */
        this.unCampo = unCampo;
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
    //-------------------------------------------------------------------------
    // Metodi pubblici degli oggetti (usati in altre classi)      (instance m.)
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Metodi di regolazione delle variabili locali                    (setter)
    //-------------------------------------------------------------------------
    /**
     * metodo setter per modificare il valore della variabile privata
     */
    public void setCaratteriMinimi(int caratteriMinimi) {
        this.caratteriMinimi = caratteriMinimi;

        FiltroAlgos unFiltro = (FiltroAlgos)getDocument();

        /** controllo di congruita' */
        if (unFiltro != null) {
            unFiltro.setCaratteriMinimi(caratteriMinimi);
        } /* fine del blocco if */
    } /* fine del metodo setter */


    /**
     * metodo setter per modificare il valore della variabile privata
     */
    public void setCaratteriMassimi(int caratteriMassimi) {
        this.caratteriMassimi = caratteriMassimi;

        FiltroAlgos unFiltro = (FiltroAlgos)getDocument();

        /** controllo di congruita' */
        if (unFiltro != null) {
            unFiltro.setCaratteriMassimi(caratteriMassimi);
        } /* fine del blocco if */
    } /* fine del metodo setter */


    //-------------------------------------------------------------------------
    // Metodi di restituzione delle variabili locali                   (getter)
    //-------------------------------------------------------------------------
    /**
     * metodo getter per ottenere il valore della variabile privata
     */
    public Campo getCampo() {
        return this.unCampo;
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
}// fine della classe it.algos.base.jtext.field.JTextFieldAlgos.java
//-----------------------------------------------------------------------------

