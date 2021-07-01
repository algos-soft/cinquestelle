/**
 * Title:        GestoreStampa.java
 * Package:      it.algos.base.stampa
 * Description:
 * Copyright:    Copyright (c) 2002, 2003
 * Company:      Algos s.r.l.
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  alex
 * @version 1.0  /
 * Creato:       il 13 novembre 2003 alle 09.00
 */
//-----------------------------------------------------------------------------
// Copyright (c) 2002, 2003  Algos s.r.l. All Rights Reserved.
//-----------------------------------------------------------------------------
//-----------------------------------------------------------------------------
// Package di questa classe e classi importate
//-----------------------------------------------------------------------------
package it.algos.base.stampa.gestore;

import it.algos.base.costante.CostanteCarattere;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.messaggio.MessaggioAvviso;
import it.algos.base.stampa.Printer;
import it.algos.base.stampa.impostazione.Impostazione;
import it.algos.base.stampa.impostazione.ImpostazioneDefault;
import it.algos.base.stampa.impostazione.PaginaStampa;
import it.algos.base.stampa.stampabile.Stampabile;

import javax.print.*;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.JobName;
import javax.print.attribute.standard.OrientationRequested;
import java.awt.print.Book;
import java.awt.print.PageFormat;
import java.awt.print.Pageable;
import java.awt.print.Printable;
import java.awt.print.PrinterJob;

/**
 * Questa classe concreta e' responsabile di: <br>
 * A - Gestire la funzionalita' di stampa <br>
 *
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  alex
 * @version 1.0  /  413 novembre 2003 ore 09.00
 */
public final class GestoreStampa {

    /**
     * il set di attributi per la richiesta di stampa (Swing)
     */
    private static PrintRequestAttributeSet unSetAttributi = null;

    /**
     * il formato nel quale i dati da stampare sono forniti
     */
    private DocFlavor unFormatoDati = null;

    /**
     * l'oggetto che fornisce i dati da stampare
     */
    private Object sorgenteDati = null;

    /**
     * l'impostazione della stampa
     */
    private Impostazione impostazione = null;


    /**
     * Costruttore completo <br>
     *
     * @param sorgenteDati l'oggetto che fornisce i dati da stampare
     */
    public GestoreStampa(Object sorgenteDati) {
        /** rimanda al costruttore della superclasse */
        super();

        /** variabili e costanti locali di lavoro */
        Impostazione unaImpostazione = null;

        /** se la sorgente dati e' un oggetto di tipo Stampabile,
         *  recupera l'impostazione dallo Stampabile, altrimenti
         *  crea una impostazione di default */
        if (sorgenteDati instanceof Stampabile) {
            unaImpostazione = ((Stampabile)sorgenteDati).getImpostazione();
        } else {
            unaImpostazione = new ImpostazioneDefault();
        } /* fine del blocco if-else */

        /** regola le variabili di istanza */
        this.setSorgenteDati(sorgenteDati);
        this.setImpostazione(unaImpostazione);

        /** regolazioni iniziali di riferimenti e variabili */
        try {                                   // prova ad eseguire il codice
            this.inizia();
        } catch (Exception unErrore) {           // intercetta l'errore
            /** messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

    } /* fine del metodo costruttore base */


    /**
     * Costruttore completo
     *
     * @param sorgenteDati l'oggetto che fornisce i dati da stampare
     * @param impostazione l'impostazione della stampa
     */
    public GestoreStampa(Object sorgenteDati, Impostazione impostazione) {
        /** rimanda al costruttore della superclasse */
        super();

        /** regola le variabili di istanza coi parametri */
        this.setSorgenteDati(sorgenteDati);
        this.setImpostazione(impostazione);

        /** regolazioni iniziali di riferimenti e variabili */
        try {                                   // prova ad eseguire il codice
            this.inizia();
        } catch (Exception unErrore) {           // intercetta l'errore
            /** messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */
    } /* fine del metodo costruttore completo */


    /**
     * Regolazioni iniziali di riferimenti e variabili
     * Metodo chiamato direttamente dal costruttore
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {

    }
    /* fine del metodo inizia */


    /**
     * Costruisce un set di attributi per la richiesta di stampa (Swing)
     *
     * @return un PrintRequestAttributeSet contenente le richieste
     *         di attributi per la stampa
     */
    private PrintRequestAttributeSet costruisciRichiestaStampa() {

        /** variabili e costanti locali di lavoro */
        PrintRequestAttributeSet unSet = null;
        int unCodiceOrientamento = 0;
        OrientationRequested unOrientamento = null;
        int unNumeroCopie = 0;
        String unNomePrintJob = null;

        /** Costruisce le specifiche della richiesta di stampa. */
        unSet = new HashPrintRequestAttributeSet();

        // orientamento
        unCodiceOrientamento = this.getImpostazione().getPagina().getOrientamentoPagina();
        switch (unCodiceOrientamento) {
            case PaginaStampa.VERTICALE:
                unOrientamento = OrientationRequested.PORTRAIT;
                break;
            case PaginaStampa.ORIZZONTALE:
                unOrientamento = OrientationRequested.LANDSCAPE;
                break;
            case PaginaStampa.ORIZZONTALE_INVERSO:
                unOrientamento = OrientationRequested.REVERSE_LANDSCAPE;
                break;
            default:
                break;
        } // fine del blocco switch
        if (unOrientamento != null) {
            unSet.add(unOrientamento);
        } /* fine del blocco if */

        /** numero di copie */
        unNumeroCopie = this.getImpostazione().getNumeroCopie();
        if (unNumeroCopie > 0) {
            unSet.add(new Copies(unNumeroCopie));
        } /* fine del blocco if */

        /** nome del job */
        unNomePrintJob = this.getNomePrintJob();
        if (unNomePrintJob != null) {
            unSet.add(new JobName(unNomePrintJob, null));
        } /* fine del blocco if */

        return unSet;
    } /* fine del metodo */


    /**
     * Seleziona un PrintService che possa soddisfare questa
     * richiesta di stampa.
     * Se il sistema dispone di piu' PrintServices in grado di soddisfare
     * la richiesta:
     * - se uno dei PrintServices e' quello di default, lo seleziona
     * - altrimenti, seleziona il primo tra quelli trovati
     * Se nessuno dei PrintServices del sistema puo' soddisfare la
     * richiesta di stampa, ritorna null
     */
    private PrintService selezionaPrintService() {
        /** variabili e costanti locali di lavoro */
        PrintService unServizio = null;
        PrintService unServizioDefault = null;
        PrintService unServizioCorrente = null;
        PrintRequestAttributeSet unSetAttributi = this.getSetAttributi();
        PrintService[] unElencoServizi = new PrintService[0];
        int unNumeroServizi = 0;

        /** effettua la printer discovery
         *  (seleziona tutti i servizi che supportano questo formato dati) */
        unElencoServizi = this.getServiziDisponibili();

        /** recupera il numero di servizi adeguati trovati */
        unNumeroServizi = unElencoServizi.length;

        /** se non ha trovato servizi, ritorna null
         *  se ha trovato un solo PrintService, lo ritorna
         *  se ne ha trovati piu' di uno, prosegue l'analisi */
        switch (unNumeroServizi) {
            case 0:     // nessuno trovato
                break;
            case 1:     // uno solo trovato
                unServizio = unElencoServizi[0];
                break;
            default:   // piu' di uno trovato

                /** recupera il PrintService di default (potrebbe anche essere null) */
                unServizioDefault = PrintServiceLookup.lookupDefaultPrintService();

                /** spazzola i servizi trovati e li confronta con quello di default */
                for (int k = 0; k < unNumeroServizi; k++) {
                    /** estrae il PrintService corrente */
                    unServizioCorrente = unElencoServizi[k];
                    if (unServizioCorrente.equals(unServizioDefault)) {
                        unServizio = unServizioCorrente;
                        break;
                    } /* fine del blocco if */
                } /* fine del blocco for */

                /** se nessuno e' quello di default, seleziona il primo */
                if (unServizio == null) {
                    unServizio = unElencoServizi[0];
                } /* fine del blocco if */

                break;

        } // fine del blocco switch

        return unServizio;
    } /* fine del metodo */


    /**
     * Effettua la printer discovery
     * (seleziona tutti i servizi che supportano questo formato dati)
     * Ritorna un array dei servizi di stampa disponibili per questa stampa
     *
     * @return un array di oggetti PrintService, vuoto se nessun servizio
     *         e' disponibile
     */
    private PrintService[] getServiziDisponibili() {
        return PrintServiceLookup.lookupPrintServices(this.getFormatoDati(), null);
    } /* fine del metodo */


    /**
     * Esegue questa stampa utilizzando la Printing API AWT
     */
    private void eseguiAWT() {
        /** variabili e costanti locali di lavoro */
        PrinterJob unPrinterJob = null;
        Object unSorgenteDati = null;
        Printable unPrintable = null;
        Pageable unPageable = null;
        PageFormat unPageFormat = null;
        PageFormat unFormatoPaginaOriginale = null;
        PageFormat unFormatoPaginaModificato = null;
        boolean continua = true;

        /** crea un PrinterJob per la stampante di default */
        try {                                   // prova ad eseguire il codice
            unPrinterJob = PrinterJob.getPrinterJob();
        } catch (Exception unErrore) {           // intercetta l'errore
            /** messaggio di errore */
            new Errore(unErrore, "creazione printer job");
            continua = false;
        } /* fine del blocco try-catch */

        /** controlla se il printer job e' valido */
        if (continua) {
            try {                                   // prova ad eseguire il codice
                if (unPrinterJob.getPrintService() == null) {
                    new MessaggioAvviso("Non e' stata trovata una stampante.");
//                    continua=false;
                } /* fine del blocco if */
            } catch (Exception unErrore) {           // intercetta l'errore
                /** messaggio di errore */
                new Errore(unErrore, "controllo printer job");
                continua = false;
            } /* fine del blocco try-catch */
        } /* fine del blocco if */

        /** assegna al PrinterJob il nome */
        if (continua) {
            try {                                   // prova ad eseguire il codice
                unPrinterJob.setJobName(this.getNomePrintJob());
            } catch (Exception unErrore) {           // intercetta l'errore
                /** messaggio di errore */
                new Errore(unErrore, "assegnazione del nome al printer job");
                continua = false;
            } /* fine del blocco try-catch */
        } /* fine del blocco if */

        /** assegna al PrinterJob l'oggetto Printable */
        if (continua) {
            try {                                   // prova ad eseguire il codice

                /** recupera l'oggetto da stampare dalla sorgente dati */
                unSorgenteDati = this.getSorgenteDati();

                /** caso Printable */
                if (unSorgenteDati instanceof Printable) {
                    unPrintable = (Printable)unSorgenteDati;
                    unPageFormat = this.getFormatoPagina();
                    unPrinterJob.setPrintable(unPrintable, unPageFormat);
                } /* fine del blocco if */

                /** caso Pageable */
                if (unSorgenteDati instanceof Pageable) {
                    unPageable = (Pageable)unSorgenteDati;
                    unPrinterJob.setPageable(unPageable);
                } /* fine del blocco if */

            } catch (Exception unErrore) {           // intercetta l'errore
                /** messaggio di errore */
                new Errore(unErrore, "assegnazione della sorgente dati al printer job");
                continua = false;
            } /* fine del blocco try-catch */
        } /* fine del blocco if */

        /** se richiesto, mostra il dialogo di impostazione pagina all'utente */
        if (continua) {
            if (this.getImpostazione().isPresentaDialogoPagina()) {
                unFormatoPaginaOriginale = this.getFormatoPagina();
                unFormatoPaginaModificato = unPrinterJob.pageDialog(unFormatoPaginaOriginale);

                /** se il formato pagina modificato e' diverso dall'originale,
                 *  l'utente ha eventualmente modificato e poi confermato.
                 *  se il formato modificato e' identico all'originale,
                 *  l'utente non ha confermato*/
                if (unFormatoPaginaModificato.equals(unFormatoPaginaOriginale) == false) {

                    /** sostituisce il formato pagina con quello modificato */
                    this.setFormatoPagina(unFormatoPaginaModificato);

                    /** riassegna il formato pagina al print job */
                    /** caso Printable */
                    if (unSorgenteDati instanceof Printable) {
                        unPrintable = (Printable)unSorgenteDati;
                        unPageFormat = this.getFormatoPagina();
                        unPrinterJob.setPrintable(unPrintable, unPageFormat);
                    } /* fine del blocco if */

                    /** caso Pageable */
                    if (unSorgenteDati instanceof Pageable) {
                        /** per ora non fa nulla */
//                        unPageable = (Pageable)unSorgenteDati;
//                        unPrinterJob.setPageable(unPageable);
                    } /* fine del blocco if */

                } else {
                    continua = false;
                } /* fine del blocco if-else */

            } /* fine del blocco if */

        } /* fine del blocco if */



        /** se richiesto mostra il dialogo di impostazione stampa all'utente */
        if (continua) {
            if (this.getImpostazione().isPresentaDialogoStampa()) {
                if (!unPrinterJob.printDialog()) {
                    continua = false;
                } /* fine del blocco if-else */
            } /* fine del blocco if */
        } /* fine del blocco if */

        /** invia il PrintJob alla stampante*/
        if (continua) {
            try { // prova ad eseguire il codice
                unPrinterJob.print();
            } catch (Exception unErrore) {           // intercetta l'errore
                Errore.crea(unErrore);
            } /* fine del blocco try-catch */
        } /* fine del blocco if */

    } /* fine del metodo */


    /**
     * Esegue questa stampa utilizzando la Printing API Swing
     */
    private void eseguiSwing() {
        /** variabili e costanti locali di lavoro */
        PrintService unPrintService = null;
        PrintService psUtente = null;
        PrintRequestAttributeSet unPRSet = null;
        DocPrintJob unDocPrintJob = null;
        Doc unDoc = null;
        Object unSorgenteDati = null;
        boolean continua = true;

        /** costruisce il set di attributi della stampa*/
        this.setSetAttributi(this.costruisciRichiestaStampa());

        /** identifica un print service in grado di gestire la richiesta di stampa */
        unPrintService = this.selezionaPrintService();

        /** se non ha trovato un PrintService adeguato, interrompe */
        if (unPrintService == null) {
            new MessaggioAvviso("Non e' stata trovata una stampante.");
            continua = false;
        } /* fine del blocco if */

        /** se richiesto, presenta il dialogo di stampa all'utente */
        if (continua) {
            if (this.getImpostazione().isPresentaDialogoStampa()) {

//                DocFlavor[] dfs;
//                dfs = unPrintService.getSupportedDocFlavors();
                DocFlavor flavor = DocFlavor.INPUT_STREAM.PNG;
//                        new DocFlavor("text/plain; charset=utf-8", "java.io.InputStream");

//                psUtente = ServiceUI.printDialog(null, 50, 50,
//                                                             this.getServiziDisponibili(),
//                                                             unPrintService,
//                                                             this.getFormatoDati(),
//                                                             this.getSetAttributi());
                psUtente = ServiceUI.printDialog(null,
                        50,
                        50,
                        this.getServiziDisponibili(),
                        unPrintService,
                        flavor,
                        this.getSetAttributi());
            } /* fine del blocco if */

            /** se l'utente ha annullato, interrompe
             *  se ha confermato, utilizza il PrintService e i parametri
             *  inseriti dall'utente */
            if (psUtente != null) {
                unPrintService = psUtente;
            } else {
                continua = false;
            } /* fine del blocco if-else */

        } /* fine del blocco if */

        /** Crea un DocPrintJob per il PrintService */
        if (continua) {
            try {                                   // prova ad eseguire il codice
                unDocPrintJob = unPrintService.createPrintJob();
            } catch (Exception unErrore) {           // intercetta l'errore
                /** messaggio di errore */
                new Errore(unErrore, "creazione DocPrintJob");
                continua = false;
            } /* fine del blocco try-catch */
        } /* fine del blocco if */

        /** crea un Doc contenente il riferimento all'oggetto che
         *  fornisce i dati e al tipo di dati forniti */
        if (continua) {
            try {                                   // prova ad eseguire il codice
                unSorgenteDati = this.getSorgenteDati();
                DocFlavor unFlavor = this.getFormatoDati();
                unDoc = new SimpleDoc(unSorgenteDati, unFlavor, null);
            } catch (Exception unErrore) {           // intercetta l'errore
                /** messaggio di errore */
                new Errore(unErrore, "creazione Doc");
                continua = false;
            } /* fine del blocco try-catch */
        } /* fine del blocco if */

        /** invia il DocPrintJob al PrintService
         *  con gli attributi di stampa */
        if (continua) {
            try {                                   // prova ad eseguire il codice
                unDocPrintJob.print(unDoc, this.getSetAttributi());
            } catch (Exception unErrore) {           // intercetta l'errore
                /** messaggio di errore */
                Errore.crea(unErrore);
            } /* fine del blocco try-catch */
        } /* fine del blocco if */
    } /* fine del metodo */


    /**
     * Determina il nome del Print Job
     * Se il nome del PrintJob nella Impostazione
     * non e' stato specificato, usa il nome della
     * classe dell'oggetto sorgente dati
     */
    private String getNomePrintJob() {
        /** variabili e costanti locali di lavoro */
        String unNomeJob = null;
        int unaPosizioneUltimoPunto = 0;

        /** recupera il nome del PrintJob dalla impostazione */
        unNomeJob = this.getImpostazione().getNomePrintJob();

        // Se non e' stato specificato assegna il nome della classe
        if (Lib.Testo.isValida(unNomeJob) == false) {
            unNomeJob = this.getSorgenteDati().getClass().getName();
            unaPosizioneUltimoPunto = unNomeJob.lastIndexOf(CostanteCarattere.PUNTO);
            // Controllo di congruita'
            if ((unaPosizioneUltimoPunto >= 0) && (unaPosizioneUltimoPunto < unNomeJob.length())) {
                unNomeJob = unNomeJob.substring(unaPosizioneUltimoPunto + 1);
            } else {
                unNomeJob = "job di stampa";    // generico
            } /* fine del blocco if-else */

        } /* fine del blocco if */

        /** valore di ritorno */
        return unNomeJob;
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
    /**
     * Esegue questa stampa
     */
    public void esegui() {

        /** seleziona la API da utilizzare
         *  ed esegue la stampa */
        switch (this.getImpostazione().getAPI()) {
            case Impostazione.API_AWT:
                this.eseguiAWT();
                break;
            case Impostazione.API_SWING:
                this.eseguiSwing();
                break;
            default:
                break;
        } // fine del blocco switch

    } /* fine del metodo */


    //-------------------------------------------------------------------------
    // Metodi privati di regolazione delle variabili locali           (setter)
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Metodi di regolazione delle variabili locali           (setter)
    //-------------------------------------------------------------------------
    /**
     * regola l'oggetto sorgeteDati
     *
     * @param sorgenteDati l'oggetto che fornisce i dati da stampare
     */
    public void setSorgenteDati(Object sorgenteDati) {
        this.sorgenteDati = sorgenteDati;
    }


    /**
     * regola l'impostazione della stampa
     *
     * @param impostazione l'impostazione
     */
    public void setImpostazione(Impostazione impostazione) {
        this.impostazione = impostazione;
    }

    /** -- setter privati -- */

    /**
     * il set di attributi per la richiesta di stampa (Swing)
     */
    private void setSetAttributi(PrintRequestAttributeSet unSetAttributi) {
        this.unSetAttributi = unSetAttributi;
    } /* fine del metodo setter */


    /**
     * il formato pagina per la stampa (AWT)
     */
    private void setFormatoPagina(PageFormat unFormatoPagina) {
        this.getImpostazione().getPagina().setPageFormat(unFormatoPagina);
    } /* fine del metodo setter */


    /**
     * restituisce l'oggetto sorgenteDati
     *
     * @return l'oggetto che fornisce i dati da stampare
     */
    public Object getSorgenteDati() {
        return this.sorgenteDati;
    }


    /**
     * restituisce l'impostazione della stampa
     *
     * @return l'impostazione della stampa
     */
    public Impostazione getImpostazione() {
        return this.impostazione;
    }


    /**
     * il set di attributi per la richiesta di stampa
     */
    private PrintRequestAttributeSet getSetAttributi() {
        return this.unSetAttributi;
    } /* fine del metodo getter */


    /**
     * il formato nel quale i dati da stampare sono forniti
     */
    private DocFlavor getFormatoDati() {
        return this.unFormatoDati;
    } /* fine del metodo getter */


    /**
     * il formato pagina per la stampa (AWT)
     */
    private PageFormat getFormatoPagina() {
        return this.getImpostazione().getPagina().getPageFormat();
    } /* fine del metodo getter */


}