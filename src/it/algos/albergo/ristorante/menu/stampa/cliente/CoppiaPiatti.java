/**
 * Title:        MenuCoppiaPiatti.java
 * Package:      it.algos.albergo.ristorante.menu
 * Description:
 * Copyright:    Copyright (c) 2002, 2003
 * Company:      Algos s.r.l.
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  alex
 * @version 1.0  /
 * Creato:       il 20 maggio 2003 alle 12.01
 */
//-----------------------------------------------------------------------------
// Copyright (c) 2002, 2003  Algos s.r.l. All Rights Reserved.
//-----------------------------------------------------------------------------
//-----------------------------------------------------------------------------
// Package di questa classe e classi importate
//-----------------------------------------------------------------------------
package it.algos.albergo.ristorante.menu.stampa.cliente;

import it.algos.base.costante.CostanteBase;
import it.algos.base.errore.ErroreInizia;
//-----------------------------------------------------------------------------
// Classe principale


//-----------------------------------------------------------------------------
/**
 * Questa classe concreta e' responsabile di: <br>
 * A - Definire un modello dati per una coppia di piatti (2 lingue) contenuta nel Menu <br>
 * B - ... <br>
 *
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  alex
 * @version 1.0  /  20 maggio 2003 ore 12.01
 */
public final class CoppiaPiatti implements CostanteBase {

    //-------------------------------------------------------------------------
    // Costanti statiche della classe    (private,pubbliche)  (class variables)
    //-------------------------------------------------------------------------
    /**
     * nome di questa classe (per i messaggi di errore)
     */
    private static final String NOME_CLASSE = "MenuCoppiaPiatti";

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
     * Categoria del piatto nella prima lingua
     */
    private String unaCategoriaPiatto1 = "";

    /**
     * Nome del piatto nella prima lingua
     */
    private String unNomePiatto1 = "";

    /**
     * Descrizione del piatto nella prima lingua
     */
    private String unaDescrizionePiatto1 = "";

    /**
     * Nome dell'eventuale contorno nella prima lingua
     */
    private String unNomeContorno1 = "";

    /**
     * Descrizione dell'eventuale contorno nella prima lingua
     */
    private String unaDescrizioneContorno1 = "";

    /**
     * Congiunzione tra secondo e contorno nella prima lingua
     */
    private String unaCongiunzione1 = "";

    /**
     * Categoria del piatto nella seconda lingua
     */
    private String unaCategoriaPiatto2 = "";

    /**
     * Nome del piatto nella seconda lingua
     */
    private String unNomePiatto2 = "";

    /**
     * Descrizione del piatto nella seconda lingua
     */
    private String unaDescrizionePiatto2 = "";

    /**
     * Nome dell'eventuale contorno nella seconda lingua
     */
    private String unNomeContorno2 = "";

    /**
     * Descrizione dell'eventuale contorno nella seconda lingua
     */
    private String unaDescrizioneContorno2 = "";

    /**
     * Congiunzione tra secondo e contorno nella seconda lingua
     */
    private String unaCongiunzione2 = "";

    /**
     * Codice della Categoria del Piatto
     * (serve per riconoscere la rottura di Categoria
     * indipendentemente dalla descrizione della Categoria)
     */
    private int unCodiceCategoria = 0;

    /**
     * flag - piatto  congelato
     */
    private boolean isCongelatoPiatto = false;

    /**
     * flag - contorno congelato
     */
    private boolean isCongelatoContorno = false;


    //-------------------------------------------------------------------------
    // Costruttori della classe                                   constructors)
    //-------------------------------------------------------------------------
    /**
     * Costruttore completo
     */
    public CoppiaPiatti() {
        /** rimanda al costruttore della superclasse */
        super();

        /** regolazioni iniziali di riferimenti e variabili */
        try {                                   // prova ad eseguire il codice
            this.inizia();
        } catch (Exception unErrore) {           // intercetta l'errore
            /** messaggio di errore */
            new ErroreInizia(NOME_CLASSE, unErrore);
        } /* fine del blocco try-catch */
    } /* fine del metodo costruttore completo */


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


    /**
     * Controlla se questa coppia piatti contiene almeno un alimento congelato.
     * <p/>
     *
     * @return true se il piatto o il contorno e' congelato
     */
    public boolean contieneCongelato() {
        /* variabili e costanti locali di lavoro */
        boolean congelato = false;

        if (this.isCongelatoPiatto() || this.isCongelatoContorno()) {
            congelato = true;
        }// fine del blocco if

        /* valore di ritorno */
        return congelato;
    }


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
     * Categoria del piatto nella prima lingua
     *
     * @param unaCategoriaPiatto1
     */
    public void setCategoriaPiatto1(String unaCategoriaPiatto1) {
        this.unaCategoriaPiatto1 = unaCategoriaPiatto1;
    } /* fine del metodo setter */


    /**
     * Nome del piatto nella prima lingua
     *
     * @param unNomePiatto1
     */
    public void setNomePiatto1(String unNomePiatto1) {
        this.unNomePiatto1 = unNomePiatto1;
    } /* fine del metodo setter */


    /**
     * Descrizione del piatto nella prima lingua
     *
     * @param unaDescrizionePiatto1
     */
    public void setDescrizionePiatto1(String unaDescrizionePiatto1) {
        this.unaDescrizionePiatto1 = unaDescrizionePiatto1;
    } /* fine del metodo setter */


    /**
     * Nome dell'eventuale contorno nella prima lingua
     *
     * @param unNomeContorno1
     */
    public void setNomeContorno1(String unNomeContorno1) {
        this.unNomeContorno1 = unNomeContorno1;
    } /* fine del metodo setter */


    /**
     * Descrizione dell'eventuale contorno nella prima lingua
     *
     * @param unaDescrizioneContorno1
     */
    public void setDescrizioneContorno1(String unaDescrizioneContorno1) {
        this.unaDescrizioneContorno1 = unaDescrizioneContorno1;
    } /* fine del metodo setter */


    /**
     * Congiunzione tra secondo e contorno nella prima lingua
     *
     * @param unaCongiunzione1
     */
    public void setCongiunzione1(String unaCongiunzione1) {
        this.unaCongiunzione1 = unaCongiunzione1;
    } /* fine del metodo setter */


    /**
     * Categoria del piatto nella seconda lingua
     *
     * @param unaCategoriaPiatto2
     */
    public void setCategoriaPiatto2(String unaCategoriaPiatto2) {
        this.unaCategoriaPiatto2 = unaCategoriaPiatto2;
    } /* fine del metodo setter */


    /**
     * Nome del piatto nella seconda lingua
     *
     * @param unNomePiatto2
     */
    public void setNomePiatto2(String unNomePiatto2) {
        this.unNomePiatto2 = unNomePiatto2;
    } /* fine del metodo setter */


    /**
     * Descrizione del piatto nella seconda lingua
     *
     * @param unaDescrizionePiatto2
     */
    public void setDescrizionePiatto2(String unaDescrizionePiatto2) {
        this.unaDescrizionePiatto2 = unaDescrizionePiatto2;
    } /* fine del metodo setter */


    /**
     * Nome dell'eventuale contorno nella seconda lingua
     *
     * @param unNomeContorno2
     */
    public void setNomeContorno2(String unNomeContorno2) {
        this.unNomeContorno2 = unNomeContorno2;
    } /* fine del metodo setter */


    /**
     * Descrizione dell'eventuale contorno nella seconda lingua
     *
     * @param unaDescrizioneContorno2
     */
    public void setDescrizioneContorno2(String unaDescrizioneContorno2) {
        this.unaDescrizioneContorno2 = unaDescrizioneContorno2;
    } /* fine del metodo setter */


    /**
     * Congiunzione tra secondo e contorno nella seconda lingua
     *
     * @param unaCongiunzione2
     */
    public void setCongiunzione2(String unaCongiunzione2) {
        this.unaCongiunzione2 = unaCongiunzione2;
    } /* fine del metodo setter */


    /**
     * Codice della Categoria del Piatto
     *
     * @param unCodiceCategoria
     */
    public void setCodiceCategoria(int unCodiceCategoria) {
        this.unCodiceCategoria = unCodiceCategoria;
    } /* fine del metodo setter */


    //-------------------------------------------------------------------------
    // Metodi di restituzione delle variabili locali                   (getter)
    //-------------------------------------------------------------------------
    /**
     * Categoria del piatto nella prima lingua
     *
     * @return
     */
    public String getCategoriaPiatto1() {
        return unaCategoriaPiatto1;
    } /* fine del metodo getter */


    /**
     * Nome del piatto nella prima lingua
     *
     * @return
     */
    public String getNomePiatto1() {
        return unNomePiatto1;
    } /* fine del metodo getter */


    /**
     * Descrizione del piatto nella prima lingua
     *
     * @return
     */
    public String getDescrizionePiatto1() {
        return unaDescrizionePiatto1;
    } /* fine del metodo getter */


    /**
     * Nome dell'eventuale contorno nella prima lingua
     *
     * @return
     */
    public String getNomeContorno1() {
        return unNomeContorno1;
    } /* fine del metodo getter */


    /**
     * Descrizione dell'eventuale contorno nella prima lingua
     *
     * @return
     */
    public String getDescrizioneContorno1() {
        return unaDescrizioneContorno1;
    } /* fine del metodo getter */


    /**
     * Congiunzione tra secondo e contorno nella prima lingua
     *
     * @return
     */
    public String getCongiunzione1() {
        return unaCongiunzione1;
    } /* fine del metodo getter */


    /**
     * Categoria del piatto nella seconda lingua
     *
     * @return
     */
    public String getCategoriaPiatto2() {
        return unaCategoriaPiatto2;
    } /* fine del metodo getter */


    /**
     * Nome del piatto nella seconda lingua
     *
     * @return
     */
    public String getNomePiatto2() {
        return unNomePiatto2;
    } /* fine del metodo getter */


    /**
     * Descrizione del piatto nella seconda lingua
     *
     * @return
     */
    public String getDescrizionePiatto2() {
        return unaDescrizionePiatto2;
    } /* fine del metodo getter */


    /**
     * Nome dell'eventuale contorno nella seconda lingua
     *
     * @return
     */
    public String getNomeContorno2() {
        return unNomeContorno2;
    } /* fine del metodo getter */


    /**
     * Descrizione dell'eventuale contorno nella seconda lingua
     *
     * @return
     */
    public String getDescrizioneContorno2() {
        return unaDescrizioneContorno2;
    } /* fine del metodo getter */


    /**
     * Congiunzione tra secondo e contorno nella seconda lingua
     *
     * @return
     */
    public String getCongiunzione2() {
        return unaCongiunzione2;
    } /* fine del metodo getter */


    /**
     * Codice della Categoria del Piatto
     *
     * @return
     */
    public int getCodiceCategoria() {
        return unCodiceCategoria;
    } /* fine del metodo getter */


    public boolean isCongelatoPiatto() {
        return isCongelatoPiatto;
    }


    public void setCongelatoPiatto(boolean congelatoPiatto) {
        isCongelatoPiatto = congelatoPiatto;
    }


    public boolean isCongelatoContorno() {
        return isCongelatoContorno;
    }


    public void setCongelatoContorno(boolean congelatoContorno) {
        isCongelatoContorno = congelatoContorno;
    }

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
}// fine della classe it.algos.albergo.ristorante.menu.MenuCoppiaPiatti.java
//-----------------------------------------------------------------------------

