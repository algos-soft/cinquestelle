/**
 * Title:        ComboData.java
 * Package:      it.algos.base.combo
 * Description:  Abstract Data Types per il record dati
 * Copyright:    Copyright (c) 2003
 * Company:      Algos s.r.l.
 * @author Ceresa, Colombo, Valbonesi
 * @version 1.0  /
 * Creato:       il 17 novembre 2003 alle 16.07
 */
//-----------------------------------------------------------------------------
// Copyright (c) 2003  Algos s.r.l. All Rights Reserved.
//-----------------------------------------------------------------------------
//-----------------------------------------------------------------------------
//  Package di questa classe e classi importate
//-----------------------------------------------------------------------------
package it.algos.base.combo;

import it.algos.base.azione.Azione;
import it.algos.base.errore.Errore;

import javax.swing.*;
//-----------------------------------------------------------------------------
// Classe principale


//-----------------------------------------------------------------------------
/**
 * Questa classe concreta e' responsabile di: <br>
 * A - Costruire un nuovo tipo di dato (record in pascal) per la singola
 * riga del popup di un combo box <br>
 *
 * @author Guido Andrea Ceresa, Alberto Colombo e Alessandro Valbonesi
 * @author gac
 * @version 1.0  /  17 novembre 2003 ore 16.07
 */
public final class ComboData extends Object {

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
     * icona eventuale a sinistra
     */
    private Icon icona = null;

    /**
     * azione associato alla riga
     */
    private Azione azione = null;

    //-------------------------------------------------------------------------
    // Variabili degli oggetti (private,protette,pubbliche)(instance variables)
    //-------------------------------------------------------------------------
    /**
     * livello di indentazione del testo, rispetto al bordo sinistro
     */
    private int livello = 0;

    /**
     * flag - se vero la riga e' selezionabile
     * se falso la riga non e' selezionabile e puo' avere un colore diverso
     */
    private boolean isSelezionabile = false;

    /**
     * contenuto della riga (quasi sempre una stringa)
     */
    private Object valore = null;

    /**
     * flag - se vero il contenuto della riga non e' una stringa
     */
    private boolean isElementoSpeciale = false;

    /**
     * flag - se vero la riga e' un oggetto JSeparator()
     */
    private boolean isSeparatore = false;

    /**
     * flag - se vero la riga e' un oggetto non specificato
     */
    private boolean isNonSpecificato = false;


    //-------------------------------------------------------------------------
    // Costruttori della classe                                   constructors)
    //-------------------------------------------------------------------------
    /**
     * Costruttore completo senza parametri <br>
     */
    public ComboData() {
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

        /* regolazione iniziali di default */
        this.setIcona(null);
        this.setAzione(null);
        this.setLivello(0);
        this.setSelezionabile(true);
        this.setValore(null);
        this.setElementoSpeciale(false);
        this.setSeparatore(false);
        this.setNonSpecificato(false);
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
    /**
     * Sovrascrive il metodo standard della classe Object <br>
     *
     * @return testo che viene mostrato nel popup
     */
    public String toString() {
        /** variabili e costanti locali di lavoro */
        String testo = "";

        try {    // prova ad eseguire il codice

            if (this.getValore() != null) {
                testo = this.getValore().toString();
            } /* fine del blocco if */

        } catch (Exception unErrore) {    // intercetta l'errore
            /** mostra il messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /** valore di ritorno */
        return testo;
    } /* fine del metodo */


    //-------------------------------------------------------------------------
    // Metodi di regolazione delle variabili locali                    (setter)
    //-------------------------------------------------------------------------
    /**
     * icona eventuale a sinistra
     */
    public void setIcona(Icon unaIcona) {
        this.icona = unaIcona;
    } /* fine del metodo setter */


    /**
     * azione associato alla riga
     */
    public void setAzione(Azione unaAzione) {
        this.azione = unaAzione;
    } /* fine del metodo setter */


    /**
     * livello di indentazione del testo, rispetto al bordo sinistro
     */
    public void setLivello(int unLivello) {
        this.livello = unLivello;
    } /* fine del metodo setter */


    /**
     * flag - se vero la riga e' selezionabile
     * se falso la riga non e' selezionabile e puo' avere un colore diverso
     */
    public void setSelezionabile(boolean isSelezionabile) {
        this.isSelezionabile = isSelezionabile;
    } /* fine del metodo setter */


    /**
     * contenuto della riga (quasi sempre una stringa)
     */
    public void setValore(Object unValore) {
        this.valore = unValore;
    } /* fine del metodo setter */


    /**
     * flag - se vero il contenuto della riga non e' una stringa
     */
    public void setElementoSpeciale(boolean isElementoSpeciale) {
        this.isElementoSpeciale = isElementoSpeciale;
    } /* fine del metodo setter */


    /**
     * flag - se vero la riga e' un oggetto JSeparator()
     */
    public void setSeparatore(boolean isSeparatore) {
        this.isSeparatore = isSeparatore;
    } /* fine del metodo setter */


    //-------------------------------------------------------------------------
    // Metodi di restituzione delle variabili locali                   (getter)
    //-------------------------------------------------------------------------
    /**
     * icona eventuale a sinistra
     */
    public Icon getIcona() {
        return this.icona;
    } /* fine del metodo getter */


    /**
     * azione associato alla riga
     */
    public Azione getAzione() {
        return this.azione;
    } /* fine del metodo getter */


    /**
     * livello di indentazione del testo, rispetto al bordo sinistro
     */
    public int getLivello() {
        return this.livello;
    } /* fine del metodo getter */


    /**
     * flag - se vero la riga e' selezionabile
     * se falso la riga non e' selezionabile e puo' avere un colore diverso
     */
    public boolean isSelezionabile() {
        return this.isSelezionabile;
    } /* fine del metodo getter */


    /**
     * contenuto della riga (quasi sempre una stringa)
     */
    public Object getValore() {
        return this.valore;
    } /* fine del metodo getter */


    /**
     * flag - se vero il contenuto della riga non e' una stringa
     */
    public boolean isElementoSpeciale() {
        return this.isElementoSpeciale;
    } /* fine del metodo getter */


    /**
     * flag - se vero la riga e' un oggetto JSeparator()
     */
    public boolean isSeparatore() {
        return this.isSeparatore;
    } /* fine del metodo getter */


    /**
     * flag - se vero la riga e' un oggetto speciale di tipo ''non specificato''
     */
    public boolean isNonSpecificato() {
        return this.isNonSpecificato;
    } /* fine del metodo getter */


    public void setNonSpecificato(boolean nonSpecificato) {
        isNonSpecificato = nonSpecificato;
    }
}// fine della classe it.algos.base.combo.ComboData.java

