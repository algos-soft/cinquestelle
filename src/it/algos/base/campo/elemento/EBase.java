/**
 * Title:        EBase.java
 * Package:      it.algos.base.campo.elemento
 * Description:
 * Copyright:    Copyright (c) 2003
 * Company:      Algos s.r.l.
 * @author Ceresa, Colombo, Valbonesi
 * @version 1.0  /
 * Creato:       il 14 novembre 2003 alle 10.16
 */
//-----------------------------------------------------------------------------
// Copyright (c) 2003  Algos s.r.l. All Rights Reserved.
//-----------------------------------------------------------------------------
//-----------------------------------------------------------------------------
// Package di questa classe e classi importate
//-----------------------------------------------------------------------------
package it.algos.base.campo.elemento;

import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;

import javax.swing.*;
import java.awt.*;

//-----------------------------------------------------------------------------
// Classe principale
//-----------------------------------------------------------------------------


/**
 * Questa classe astratta e' responsabile di: <br>
 * A - Costruire un tipo di dati da usare nelle liste
 * (<CODE>JList</CODE> e <CODE>JComboBox</CODE>) <br>
 *
 * @author Guido Ceresa
 * @author Alberto Colombo
 * @author Alessandro Valbonesi
 * @author gac
 * @version 1.0  /  14 novembre 2003 ore 10.16
 */
public abstract class EBase extends Object implements Elemento {

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
     * componente che viene restituito per essere disegnato
     */
    private Component componente = null;

    //-------------------------------------------------------------------------
    // Variabili degli oggetti (private,protette,pubbliche)(instance variables)
    //-------------------------------------------------------------------------
    /**
     * eventuale icona mostrata a sinistra del testo
     */
    private Icon icona = null;

    /**
     * eventuale icona quando disabilitata
     */
    private Icon iconaDisabilitata = null;

    /**
     * testo che viene mostrato nel popup da questo elemento (riga)
     */
    private String testo = "";

    /**
     * colore del testo di questo elemento (riga)
     */
    private Color colore = null;

    /**
     * valore del codice associato a questo elemento nella matriceDoppia
     */
    private int codice = 0;


    //-------------------------------------------------------------------------
    // Costruttori della classe                                   constructors)
    //-------------------------------------------------------------------------
    /**
     * Costruttore base senza parametri <br>
     * Indispensabile anche se non viene utilizzato
     * (anche solo per compilazione in sviluppo) <br>
     */
    public EBase() {
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
    } /* fine del metodo inizia */


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
     * Sovrascrive il metodo standard della classe Object <br>
     *
     * @return testo che viene mostrato nel popup
     */
    public String toString() {
        return this.getTesto();
    } /* fine del metodo */


    //-------------------------------------------------------------------------
    // Metodi pubblici degli oggetti (usati in altre classi)      (instance m.)
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Metodi di regolazione delle variabili locali                    (setter)
    //-------------------------------------------------------------------------
    /**
     * componente che viene restituito per essere disegnato
     */
    protected void setComponente(Component unComponente) {
        this.componente = unComponente;
    } /* fine del metodo setter */


    /**
     * icona eventuale a sinistra
     */
    private void setIcona(Icon unaIcona) {
        this.icona = unaIcona;
    } /* fine del metodo setter */


    /**
     * icona eventuale a sinistra
     */
    protected void setIcona(String nomeIcona) {
        /** variabili e costanti locali di lavoro */
        Icon unaIcona = null;

        try {    // prova ad eseguire il codice
            unaIcona = Lib.Risorse.getIconaBase(nomeIcona);
            this.setIcona(unaIcona);

        } catch (Exception unErrore) {    // intercetta l'errore
            /** mostra il messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

    } /* fine del metodo setter */


    /**
     * testo che viene mostrato nel popup
     */
    protected void setTesto(String unTesto) {
        this.testo = unTesto;
    } /* fine del metodo setter */


    /**
     * colore del testo di questo elemento (riga)
     */
    protected void setColore(Color unColore) {
        this.colore = unColore;
    } /* fine del metodo setter */


    /**
     * valore del codice associato a questo elemento nella matriceDoppia
     */
    protected void setCodice(int unCodice) {
        this.codice = unCodice;
    } /* fine del metodo setter */


    //-------------------------------------------------------------------------
    // Metodi di restituzione delle variabili locali                   (getter)
    //-------------------------------------------------------------------------
    /**
     * componente che viene restituito per essere disegnato
     */
    public Component getComponente() {
        return this.componente;
    } /* fine del metodo getter */


    /**
     * icona eventuale a sinistra
     */
    public Icon getIcona() {
        return this.icona;
    } /* fine del metodo getter */


    /**
     * icona eventuale a sinistra quando disabilitata.
     */
    public Icon getIconaDisabilitata() {
        return iconaDisabilitata;
    }


    /**
     * icona eventuale a sinistra quando disabilitata.
     * <p/>
     *
     * @param icona l'icona
     */
    public void setIconaDisabilitata(Icon icona) {
        this.iconaDisabilitata = icona;
    }


    /**
     * icona eventuale a sinistra quando disabilitata.
     * <p/>
     *
     * @param nomeIcona il nome dell'icona nella libreria
     */
    protected void setIconaDisabilitata(String nomeIcona) {
        /** variabili e costanti locali di lavoro */
        Icon unaIcona = null;

        try {    // prova ad eseguire il codice
            unaIcona = Lib.Risorse.getIconaBase(nomeIcona);
            this.setIconaDisabilitata(unaIcona);

        } catch (Exception unErrore) {    // intercetta l'errore
            /** mostra il messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

    } /* fine del metodo setter */


    /**
     * testo che viene mostrato nel popup
     */
    public String getTesto() {
        return this.testo;
    } /* fine del metodo getter */


    /**
     * colore del testo di questo elemento (riga)
     */
    public Color getColore() {
        return this.colore;
    } /* fine del metodo getter */


    /**
     * valore del codice associato a questo elemento nella matriceDoppia
     */
    public int getCodice() {
        return this.codice;
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
}// fine della classe it.algos.base.campo.elemento.EBase.java

//-----------------------------------------------------------------------------

