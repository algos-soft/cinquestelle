/**
 * Title:        Pagina.java
 * Package:      it.algos.base.stampa.impostazione
 * Description:
 * Copyright:    Copyright (c) 2002, 2003
 * Company:      Algos s.r.l.
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  alex
 * @version 1.0  /
 * Creato:       il 6 novembre 2003 alle 11.00
 */
//-----------------------------------------------------------------------------
// Copyright (c) 2002, 2003  Algos s.r.l. All Rights Reserved.
//-----------------------------------------------------------------------------
//-----------------------------------------------------------------------------
// Package di questa classe e classi importate
//-----------------------------------------------------------------------------
package it.algos.base.stampa.impostazione;

import it.algos.base.errore.Errore;
import it.algos.base.stampa.carta.Carta;
import it.algos.base.stampa.carta.Carte;
import it.algos.base.wrapper.DueDouble;
import it.algos.base.wrapper.QuattroDouble;

import java.awt.print.PageFormat;
import java.awt.print.Paper;

//-----------------------------------------------------------------------------
// Classe principale


//-----------------------------------------------------------------------------
/**
 * Questa classe concreta e' responsabile di: <br>
 * A - Definire le caratteristiche della pagina per la stampa <br>
 * B - ... <br>
 *
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  alex
 * @version 1.0  /  6 novembre 2003 ore 11.00
 */
public class PaginaStampa {

    //-------------------------------------------------------------------------
    // Costanti statiche della classe    (private,pubbliche)  (class variables)
    //-------------------------------------------------------------------------
    /**
     * orientamento della Pagina
     */
    public static final int VERTICALE = PageFormat.PORTRAIT;

    public static final int ORIZZONTALE = PageFormat.LANDSCAPE;

    public static final int ORIZZONTALE_INVERSO = PageFormat.REVERSE_LANDSCAPE;

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
     * codice identificativo della Carta
     */
    private Integer codiceCarta = null;

    /**
     * dimensione della Pagina (larghezza x altezza)
     */
    private DueDouble dimensionePagina = null;

    /**
     * margini della Pagina (sinistro, destro, superiore, inferiore)
     */
    private QuattroDouble marginePagina = null;

    /**
     * orientamento della Pagina (verticale o orizzontale o orizzontale inverso)
     */
    private int orientamentoPagina = 0;

    /**
     * PageFormat associato alla Pagina
     */
    private PageFormat formatoPagina = null;


    //-------------------------------------------------------------------------
    // Costruttori della classe                                  (constructors)
    //-------------------------------------------------------------------------
    /**
     * Costruttore base senza parametri <br>
     * Indispensabile anche se non viene utilizzato
     * (anche solo per compilazione in sviluppo) <br>
     * Rimanda al costruttore completo utilizzando eventuali valori di default
     */
    public PaginaStampa() {
        /** rimanda al costruttore di questa classe */
        this(Carte.A4, VERTICALE);
    } /* fine del metodo costruttore base */


    /**
     * Costruttore completo
     *
     * @param codiceCarta il codice della Carta
     * @param unOrientamento l'orientamento della Pagina
     */
    public PaginaStampa(Integer codiceCarta, int unOrientamento) {
        /** rimanda al costruttore della superclasse */
        super();

        /** regola le variabili di istanza coi parametri */
        this.codiceCarta = codiceCarta;
        this.orientamentoPagina = unOrientamento;

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
     * Regolazioni iniziali di riferimenti e variabili
     * Metodo chiamato direttamente dal costruttore
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        /** crea un nuovo PageFormat e lo assegna alla Pagina */
        this.setPageFormat(new PageFormat());
        /** inizializza la Pagina */
        this.inizializzaPagina();

    } /* fine del metodo inizia */


    //-------------------------------------------------------------------------
    // Metodi protetti chiamati dalla sottoclasse subito dopo il costruttore
    //-------------------------------------------------------------------------
    /**
     * Regolazioni iniziali, dopo che sono stati regolati dalla sottoclasse
     * i parametri indispensabili (tra cui il riferimento al modulo)
     * Metodo chiamato dalla classe che crea questo oggetto
     * Viene eseguito una sola volta
     *
     * @param unModulo Abstract Data Types per le informazioni di un modulo
     */
    public void inizializzaPagina() {
        /** regola i parametri in base al codice della Carta */
        this.regolaCodiceCarta();
        /** lancia la Pagina */
        this.avviaPagina();
    } /* fine del metodo */


    //-------------------------------------------------------------------------
    // Metodi privati degli oggetti (usati nella classe)     (instance methods)
    //-------------------------------------------------------------------------
    /**
     * Regolazioni di avvio, ogni volta che questo oggetto deve 'ripartire', per
     * essere sicuri che sia 'pulito'
     * Viene eseguito tutte le volte che necessita
     */
    private void avviaPagina() {
        /** regola le dimensioni */
        this.regolaDimensioni();
        /** regola i margini */
        this.regolaMargini();
        /** regola l'orientamento */
        this.regolaOrientamento();
    } /* fine del metodo */


    /**
     * Regola i parametri della Pagina in base al codice della Carta
     */
    private void regolaCodiceCarta() {
        /** variabili e costanti locali di lavoro */
        Carta unaCarta = null;

        /** recupera la Carta dal codice */
        unaCarta = Carte.getCarta(this.getCodiceCarta());

        /** regola le dimensioni e i margini della Pagina
         *  NON usa i metodi Set e Get perche' questi
         *  eseguono le regolazioni (lancia) mentre per adesso
         *  devo solo caricare i valori. */
        this.dimensionePagina = unaCarta.getDimensione();
        this.marginePagina = unaCarta.getMargineDefault();

    } /* fine del metodo */


    /**
     * Regola le dimensioni della Pagina
     */
    private void regolaDimensioni() {
        /** variabili e costanti locali di lavoro */
        Paper unaPaper = null;
        DueDouble unaDimensione = null;
        double unaLarghezza = 0;
        double unaAltezza = 0;

        /** Recupera l'oggetto Paper del PageFormat */
        unaPaper = this.getPageFormat().getPaper();

        /** Recupera le dimensioni della Pagina */
        unaDimensione = this.getDimensionePagina();
        unaLarghezza = unaDimensione.x;
        unaAltezza = unaDimensione.y;

        /** Regola la dimensione della Paper */
        unaPaper.setSize(unaLarghezza, unaAltezza);

        /** Riassegna la Paper modificata al PageFormat*/
        this.getPageFormat().setPaper(unaPaper);

    } /* fine del metodo */


    /**
     * Regola i margini della Pagina
     */
    private void regolaMargini() {
        /** variabili e costanti locali di lavoro */
        Paper unaPaper = null;
        DueDouble unaDimensione = null;
        QuattroDouble unMargine = null;
        double unaLarghezza = 0;
        double unaAltezza = 0;
        double sx = 0;
        double dx = 0;
        double sup = 0;
        double inf = 0;
        double x = 0;
        double y = 0;
        double w = 0;
        double h = 0;

        /** Recupera l'oggetto Paper del PageFormat */
        unaPaper = this.getPageFormat().getPaper();

        /** Recupera le dimensioni della Pagina */
        unaDimensione = this.getDimensionePagina();
        unaLarghezza = unaDimensione.x;
        unaAltezza = unaDimensione.y;

        /** Recupera i margini della Pagina */
        unMargine = this.getMarginePagina();
        sx = unMargine.a;
        dx = unMargine.b;
        sup = unMargine.c;
        inf = unMargine.d;

        /** Calcola la Imageable Area */
        x = sx;
        y = sup;
        w = unaLarghezza - sx - dx;
        h = unaAltezza - sup - inf;

        /** Regola la ImageableArea della Paper */
        unaPaper.setImageableArea(x, y, w, h);

        /** Riassegna la Paper modificata al PageFormat*/
        this.getPageFormat().setPaper(unaPaper);

    } /* fine del metodo */


    /**
     * Regola l'orientamento della Pagina
     */
    private void regolaOrientamento() {
        /** variabili e costanti locali di lavoro */
        PageFormat unPageFormat = null;

        /** regupera il PageFormat */
        unPageFormat = this.getPageFormat();

        /** regola l'orientamento */
        unPageFormat.setOrientation(this.getOrientamentoPagina());

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
    //-------------------------------------------------------------------------
    // Metodi di regolazione delle variabili locali                    (setter)
    //-------------------------------------------------------------------------
    /**
     * Regola il codice identificativo della Carta
     * param codiceCarta il codice identificativo della Carta
     */
    public void setCodiceCarta(Integer codiceCarta) {
        this.codiceCarta = codiceCarta;
        this.inizializzaPagina();   // in questo caso deve reinizializzare
    } /* fine del metodo setter */


    /**
     * Regola la dimensione della Pagina
     *
     * @param dimensionePagina la dimensione della Pagina (larghezza, altezza)
     */
    public void setDimensionePagina(DueDouble dimensionePagina) {
        this.dimensionePagina = dimensionePagina;
        this.avviaPagina();
    } /* fine del metodo setter */


    /**
     * Regola la dimensione della Pagina
     *
     * @param larghezza la larghezza della Pagina
     * @param altezza l'altezza della Pagina
     */
    public void setDimensionePagina(double larghezza, double altezza) {
        DueDouble dimensionePagina = new DueDouble(larghezza, altezza);
        /** rimanda al metodo setter delegato */
        this.setDimensionePagina(dimensionePagina);
    } /* fine del metodo setter */


    /**
     * Regola i margini della Pagina
     *
     * @param marginePagina il margine della Pagina (sx, dx, sopra, sotto)
     */
    public void setMarginePagina(QuattroDouble marginePagina) {
        this.marginePagina = marginePagina;
        this.avviaPagina();
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
        QuattroDouble marginePagina = new QuattroDouble(sx, dx, sopra, sotto);
        /** rimanda al metodo setter delegato */
        this.setMarginePagina(marginePagina);
    } /* fine del metodo setter */


    /**
     * Regola l'orientamento della Pagina
     *
     * @param orientamentoPagina l'orientamento della Pagina
     */
    public void setOrientamentoPagina(int orientamentoPagina) {
        this.orientamentoPagina = orientamentoPagina;
        this.avviaPagina();
    } /* fine del metodo setter */


    /**
     * Assegna un PageFormat alla Pagina
     *
     * @param formatoPagina il PageFormat da assegnare
     * (pubblico perche' deve poter essere modificato
     * se l'utente interviene nel dialogo di impostazione pagina)
     */
    public void setPageFormat(PageFormat formatoPagina) {
        this.formatoPagina = formatoPagina;
    } /* fine del metodo setter */


    //-------------------------------------------------------------------------
    // Metodi di restituzione delle variabili locali                   (getter)
    //-------------------------------------------------------------------------
    /**
     * Ritorna il codice identificativo dela Carta
     *
     * @return codiceCarta il codice della Carta
     */
    public Integer getCodiceCarta() {
        return this.codiceCarta;
    } /* fine del metodo getter */


    /**
     * Ritorna la dimensione della Pagina
     *
     * @return dimensionePagina (larghezza, altezza)
     */
    public DueDouble getDimensionePagina() {
        return this.dimensionePagina;
    } /* fine del metodo getter */


    /**
     * Ritorna i margini della Pagina
     *
     * @return marginePagina (sx, dx, sopra, sotto)
     */
    public QuattroDouble getMarginePagina() {
        return this.marginePagina;
    } /* fine del metodo getter */


    /**
     * Ritorna l'orientamento della Pagina
     *
     * @return orientamentoPagina l'orientamento della Pagina
     */
    public int getOrientamentoPagina() {
        return this.orientamentoPagina;
    } /* fine del metodo getter */


    /**
     * Ritorna il PageFormat della Pagina
     *
     * @return il PageFormat della Pagina
     */
    public PageFormat getPageFormat() {
        return this.formatoPagina;
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
}// fine della classe
//-----------------------------------------------------------------------------

