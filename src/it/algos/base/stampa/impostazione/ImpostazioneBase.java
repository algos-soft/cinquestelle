/**
 * Title:        ImpostazioneBase.java
 * Package:      it.algos.base.stampa.impostazione
 * Description:
 * Copyright:    Copyright (c) 2002, 2003
 * Company:      Algos s.r.l.
 * @author Guido Andrea Ceresa & Alessandro Valbonesi
 * @version 1.0  /
 * Creato:       il 13 novembre 2003 alle 9.51
 */
//-----------------------------------------------------------------------------
// Copyright (c) 2002, 2003  Algos s.r.l. All Rights Reserved.
//-----------------------------------------------------------------------------
//-----------------------------------------------------------------------------
// Package di questa classe e classi importate
//-----------------------------------------------------------------------------
package it.algos.base.stampa.impostazione;

//-----------------------------------------------------------------------------
// Classe principale


//-----------------------------------------------------------------------------
/**
 * Questa classe astratta e' responsabile di: <br>
 * A - Definire un modello di dati per le impostazioni di una stampa <br>
 * B - ... <br>
 *
 * @author Guido Andrea Ceresa
 * @author Alessandro Valbonesi
 * @author alex
 * @version 1.0  /  13 novembre 2003 ore 9.51
 */
public abstract class ImpostazioneBase extends Object implements Impostazione {

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
     * la Pagina da utilizzare per la stampa
     * (comprende dimensione, orientamento, margini)
     */
    private PaginaStampa unaPagina = null;

    //-------------------------------------------------------------------------
    // Variabili degli oggetti (private,protette,pubbliche)(instance variables)
    //-------------------------------------------------------------------------
    /**
     * il numero di copie da stampare
     */
    private int unNumeroCopie = 0;

    /**
     * il nome del print job per la stampante
     */
    private String unNomePrintJob = null;

    /**
     * flag - presenta il dialogo di impostazione della pagina (AWT)
     */
    private boolean isPresentaDialogoPagina = false;

    /**
     * flag - presenta il dialogo di impostazione della stampa
     */
    private boolean isPresentaDialogoStampa = false;

    /**
     * flag - la printing API da utilizzare
     */
    private int unaAPIStampa = 0;


    //-------------------------------------------------------------------------
    // Costruttori della classe                                   constructors)
    //-------------------------------------------------------------------------
    /**
     * Costruttore base senza parametri <br>
     * Indispensabile anche se non viene utilizzato
     * (anche solo per compilazione in sviluppo) <br>
     * Rimanda al costruttore completo utilizzando eventuali valori di default
     */
    public ImpostazioneBase() {
        /** rimanda al costruttore della superclasse */
        super();
    } /* fine del metodo costruttore base */


    //-------------------------------------------------------------------------
    // Metodi statici della classe                              (class methods)
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Metodi privati chiamati dal costruttore prima di tornare alla sottoclasse
    //-------------------------------------------------------------------------
    /**
     * Regolazioni iniziali di riferimenti e variabili
     * Metodo chiamato direttamente dal costruttore
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
    } /* fine del metodo inizia */


    //-------------------------------------------------------------------------
    // Metodi protetti chiamati dalla sottoclasse subito dopo il costruttore
    //-------------------------------------------------------------------------
    /**
     * Regolazioni iniziali, dopo che sono stati regolati dalla sottoclasse
     * i parametri indispensabili (tra cui il riferimento al modulo)
     * Metodo chiamato dalla classe che crea questo oggetto
     * Viene eseguito una sola volta
     */
    public void inizializza() {
        /** invoca il metodo sovrascritto della superclasse */
        //super.inizializza();
    } /* fine del metodo */


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
     * la API di stampa da utilizzare
     */
    public void setAPI(int unaAPIStampa) {
        this.unaAPIStampa = unaAPIStampa;
    } /* fine del metodo */


    /**
     * la Pagina da utilizzare
     */
    public void setPagina(PaginaStampa unaPagina) {
        this.unaPagina = unaPagina;
    } /* fine del metodo */


    /**
     * il numero di copie da stampare
     */
    public void setNumeroCopie(int unNumeroCopie) {
        this.unNumeroCopie = unNumeroCopie;
    } /* fine del metodo setter */


    /**
     * il nome del print job per la stampante
     */
    public void setNomePrintJob(String unNomePrintJob) {
        this.unNomePrintJob = unNomePrintJob;
    } /* fine del metodo setter */


    /**
     * flag - presenta il dialogo di impostazione pagina all'utente (solo AWT)
     */
    public void setPresentaDialogoPagina(boolean isPresentaDialogoPagina) {
        this.isPresentaDialogoPagina = isPresentaDialogoPagina;
    } /* fine del metodo setter */


    /**
     * flag - presenta il dialogo di impostazione stampa all'utente
     */
    public void setPresentaDialogoStampa(boolean isPresentaDialogoStampa) {
        this.isPresentaDialogoStampa = isPresentaDialogoStampa;
    } /* fine del metodo setter */


    /**
     * -- setter per regolare la Pagina --  Regola il codice identificativo della Carta
     * param codiceCarta il codice identificativo della Carta
     */

    /** Regola il codice identificativo della Carta
     *  param codiceCarta il codice identificativo della Carta */
    public void setCodiceCarta(Integer codiceCarta) {
        this.getPagina().setCodiceCarta(codiceCarta);
    } /* fine del metodo setter */


    /**
     * Regola la dimensione della Pagina
     *
     * @param larghezza la larghezza della Pagina
     * @param altezza l'altezza della Pagina
     */
    public void setDimensionePagina(double larghezza, double altezza) {
        this.getPagina().setDimensionePagina(larghezza, altezza);
    } /* fine del metodo setter */


    /**
     * Regola i margini della Pagina
     *
     * @param sx il margine sinistro
     * @param dx il margine destro
     * @param sopra il margine superiore
     * @param sotto il margine inferiore
     */
    public void setMarginePagina(double sx, double dx, double sopra, double sotto) {
        this.getPagina().setMarginePagina(sx, dx, sopra, sotto);
    } /* fine del metodo setter */


    /**
     * Regola l'orientamento della Pagina
     *
     * @param orientamentoPagina l'orientamento della Pagina
     */
    public void setOrientamentoPagina(int orientamentoPagina) {
        this.getPagina().setOrientamentoPagina(orientamentoPagina);
    } /* fine del metodo setter */


    //-------------------------------------------------------------------------
    // Metodi di restituzione delle variabili locali                   (getter)
    //-------------------------------------------------------------------------
    /**
     * la API di stampa da utilizzare
     */
    public int getAPI() {
        return this.unaAPIStampa;
    } /* fine del metodo getter */


    /**
     * la Pagina da utilizzare
     */
    public PaginaStampa getPagina() {
        return this.unaPagina;
    } /* fine del metodo getter */


    /**
     * il numero di copie da stampare
     */
    public int getNumeroCopie() {
        return this.unNumeroCopie;
    } /* fine del metodo getter */


    /**
     * il nome del print job per la stampante
     */
    public String getNomePrintJob() {
        return this.unNomePrintJob;
    } /* fine del metodo getter */


    /**
     * flag - presenta il dialogo di impostazione pagina all'utente (solo AWT)
     */
    public boolean isPresentaDialogoPagina() {
        return this.isPresentaDialogoPagina;
    } /* fine del metodo getter */


    /**
     * flag - presenta il dialogo di impostazione stampa all'utente
     */
    public boolean isPresentaDialogoStampa() {
        return this.isPresentaDialogoStampa;
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
}// fine della classe it.algos.base.stampa.impostazione.impostazioneBase.java
//-----------------------------------------------------------------------------

