/**
 * Title:        StampabileBase.java
 * Package:      it.algos.base.stampa.stampable
 * Description:
 * Copyright:    Copyright (c) 2002, 2003
 * Company:      Algos s.r.l.
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  alex
 * @version 1.0  /
 * Creato:       il 4 novembre 2003 alle 10.20
 */

package it.algos.base.stampa.stampabile;

import it.algos.base.errore.Errore;
import it.algos.base.stampa.impostazione.Impostazione;
import it.algos.base.stampa.impostazione.ImpostazioneDefault;

import java.awt.*;
import java.awt.print.PageFormat;
import java.awt.print.Pageable;
import java.awt.print.Printable;

/**
 * Superclasse astratta che descrive una pagina di stampa.
 * <p/>
 *
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  alex
 * @version 1.0  /  4 novembre 2003 ore 10.20
 */
public abstract class StampabileBase implements Stampabile, Printable {

    /**
     * Numero della pagina correntemente in stampa (0 per la prima)
     */
    private int numeroPagina = 0;

    /**
     * Flag - indica se la stampa e' terminata
     */
    private boolean stampaTerminata = false;

    /**
     * Impostazione della stampa
     */
    private Impostazione impostazione = null;

    /**
     * dimensione massima dell'area stampabile della pagina corrente
     * comprende tutta l'area stampabile della pagina
     */
    private double xMaxPagina, yMaxPagina;

    /**
     * dimensione massima dell'area utilizzabile della pagina corrente
     * le sottoclassi che aggiungono contenuti fissi che non devono
     * essere sovrastampati la possono modificare.
     * (tipicamente per escludere testa e piede)
     */
    private double xMax, yMax;

    /**
     * flag - indica se si sta stampando la pagina corrente
     * per la prima volta
     */
    private boolean paginaNuova;

    /**
     * oggetto grafico sul quale disegnare, fornito
     * dal sottosistema di stampa ad ogni richiesta di pagina
     * e registrato dopo la trasformazione delle coordinate.
     * Le sottoclassi devono disegnare in questo oggetto
     */
    private Graphics2D pagina;


    /**
     * Costruttore base.
     * <p/>
     */
    public StampabileBase() {

        /* rimanda al costruttore della superclasse */
        super();

        /* regolazioni iniziali di riferimenti e variabili */
        try {
            this.inizia();
        } catch (Exception unErrore) {
            /** messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

    } /* fine del metodo costruttore completo */


    /**
     * Regolazioni iniziali di riferimenti e variabili.
     * <p/>
     * Metodo chiamato direttamente dal costruttore
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {

        /* crea una Impostazione di default e la assegna a questo Stampabile */
        this.setImpostazione(new ImpostazioneDefault());

    } /* fine del metodo inizia */


    /**
     * Trasforma le coordinate della pagina.
     * <p/>
     *
     * @param graf oggetto grafico di classe Graphics
     *
     * @return un oggetto Graphics2D rappresentante la pagina da stampare,
     *         con le coordinate trasformate
     *         todo il metodo e' protected per compatibilita' con alcune
     *         vecchie sottoclassi di Ristorante, se possibile renderlo Private.
     */
    protected Graphics2D trasformaCoordinate(Graphics graf, PageFormat pf) {

        /** variabili e costanti locali di lavoro */
        Graphics2D unGrafico2D = null;

        /** effettua il casting a Graphics2D */
        unGrafico2D = (Graphics2D)graf;

        /** trasforma le coordinate */
        unGrafico2D.translate(pf.getImageableX(), pf.getImageableY());

        /** valore di ritorno */
        return unGrafico2D;

    } /* fine del metodo */


    /**
     * Stampa di una pagina.
     * <p/>
     * (metodo invocato direttamente dal sottosistema di stampa)
     *
     * @param g l'oggetto grafico nel quale disegnare
     * @param pf il formato della pagina che verra' stampata
     * @param pageIndex il numero di pagina da stampare (0 per la prima)
     *
     * @return PAGE_EXISTS se la pagina e' stata costruita correttamente,
     *         o NO_SUCH_PAGE se la pagina richiesta non esiste o non
     *         puo' essere costruita
     */
    public int print(Graphics g, PageFormat pf, int pageIndex) {
        /* variabili e costanti locali di lavoro */
        int codUscita = 0;
        boolean terminato;
        Graphics2D pagina = null;
        int numPagina;

        try { // prova ad eseguire il codice

            /* valori iniziali */
            codUscita = NO_SUCH_PAGE;

            /* regola il flag di stampa pagina nuova
             * e l'indicatore di numero pagina corrente */
            numPagina = pageIndex + 1;
            this.setPaginaNuova(false);
            if (numPagina > this.getNumeroPagina()) {
                this.setPaginaNuova(true);
            }// fine del blocco if

            /*
            * Trasforma le coordinate dell'oggetto da stampare e aggiunge
            * le parti fisse.
            * Registra la pagina nella variabile di istanza
            */
            pagina = this.trasformaCoordinate(g, pf);
            this.setPagina(pagina);

            /* regola il paint in nero - devo farlo se poi
            voglio stampare con J2PrinterWorks */
            Paint p =  new Color(0,0,0);
            this.getPagina().setPaint(p);

            /* registra le dimensioni massime della pagina corrente */
            this.setxMaxPagina(pf.getImageableWidth());
            this.setyMaxPagina(pf.getImageableHeight());

            /* registra le dimensioni utilizzabili della pagina corrente */
            this.setxMax(pf.getImageableWidth());
            this.setyMax(pf.getImageableHeight());

            /* aggiunge gli eventuali contenuti fissi */
            this.aggiungeContenutoFisso(pagina);

            /*
             * - se il flag stampaTerminata non a' ancora stato
             * acceso, regola il numero di pagina corrente e
             * procede alla costruzione della pagina nella sottoclasse.
             * richiesta e ritorna PAGE_EXISTS.
             * - se il flag e' gia' stato acceso, risponde solo alle
             * richieste per una pagina non superiore a quella corrente.
             * (se viene richiesta una pagina superiore alla corrente,
             * ritorna NO_SUCH_PAGE)
             */
            if (!this.isStampaTerminata()) {

                this.setNumeroPagina(numPagina);
                terminato = printPagina(pagina, pf, numPagina);
                this.setStampaTerminata(terminato);
                codUscita = PAGE_EXISTS;

            } else {

                if (numPagina <= this.getNumeroPagina()) {
                    printPagina(pagina, pf, numPagina);
                    codUscita = PAGE_EXISTS;
                }// fine del blocco if-else

            }// fine del blocco if-else

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return codUscita;
    } /* fine del metodo */


    /**
     * Disegna una pagina.
     * <p/>
     * Metodo da implementare obbligatoriamente nelle sottoclassi.
     * E' responsabilita' del metodo:
     * - disegnare la pagina richiesta nell'oggetto grafico g che viene passato.
     * - ritornare true se la stampa e' terminata o false se non e' terminata.
     *
     * @param pagina l'oggetto grafico nel quale disegnare
     * @param formato il formato della pagina che verra' stampata
     * @param numPagina il numero di pagina da stampare (1 per la prima)
     *
     * @return true se la stampa e' terminata
     */
    public boolean printPagina(Graphics2D pagina, PageFormat formato, int numPagina) {
        return true;
    }


    /**
     * Aggiunge i contenuti fissi a ogni pagina.
     * <p/>
     * Metodo da implementare nelle sottoclassi
     * per stampare un contenuto fisso su ogni pagina.
     *
     * @param pagina alla quale aggiungere il contenuto fisso
     */
    protected void aggiungeContenutoFisso(Graphics2D pagina) {
    }


    /**
     * Ritorna la lunghezza in pixel di una stringa data
     * nel font di stampa corrente.
     * <p/>
     *
     * @param stringa della quale calcolare la lunghezza
     */
    protected int getLunghezzaTesto(String stringa) {
        /* variabili e costanti locali di lavoro */
        int lunghezza = 0;
        FontMetrics metrica;

        try { // prova ad eseguire il codice
            metrica = this.getPagina().getFontMetrics();
            lunghezza = metrica.stringWidth(stringa);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return lunghezza;
    }


    /**
     * Ritorna la lunghezza in pixel di una stringa data
     * in un dato font.
     * <p/>
     *
     * @param stringa della quale calcolare la lunghezza
     */
    protected int getLunghezzaTesto(String stringa, Font font) {
        /* variabili e costanti locali di lavoro */
        int lunghezza = 0;
        Graphics2D pagina;
        Font oldFont;

        try { // prova ad eseguire il codice
            pagina = this.getPagina();
            oldFont = pagina.getFont();
            pagina.setFont(font);
            lunghezza = this.getLunghezzaTesto(stringa);
            pagina.setFont(oldFont);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return lunghezza;
    }


    /**
     * Ritorna l'altezza in pixel di una stringa data
     * nel contesto di stampa corrente.
     * <p/>
     * Nota: per ora non usa la stringa ma calcola
     * solo l'altezza del font
     *
     * @param stringa della quale calcolare l'altezza
     */
    protected int getAltezzaTesto(String stringa) {
        /* variabili e costanti locali di lavoro */
        int lunghezza = 0;
        FontMetrics metrica;

        try { // prova ad eseguire il codice
            metrica = this.getPagina().getFontMetrics();
            lunghezza = metrica.getHeight();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return lunghezza;
    }


    /**
     * Stampa un filetto orizzontale a partire dal margine sinistro
     * per tutta la larghezza disponibile.
     * <p/>
     *
     * @param y la coordinata verticale
     */
    protected void drawLine(int y) {
        /* variabili e costanti locali di lavoro */
        Graphics2D pagina;
        int xMax;

        try {    // prova ad eseguire il codice
            pagina = this.getPagina();
            xMax = (int)this.getxMax();
            pagina.drawLine(0, y, xMax, y);
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Stampa un filetto orizzontale a partire dal margine sinistro
     * della lunghezza specificata.
     * <p/>
     *
     * @param y la coordinata verticale
     * @param len la lunghezza del filetto
     */
    protected void drawLine(int y, int len) {
        /* variabili e costanti locali di lavoro */
        Graphics2D pagina;

        try {    // prova ad eseguire il codice
            pagina = this.getPagina();
            pagina.drawLine(0, y, len, y);
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Assegna la API di stampa da utilizzare.
     * <p/>
     */
    public void setAPI(int unaAPIStampa) {
        this.getImpostazione().setAPI(unaAPIStampa);
    } /* fine del metodo */


    /**
     * Regola il numero di copie da stampare.
     * <p/>
     */
    public void setNumeroCopie(int unNumeroCopie) {
        this.getImpostazione().setNumeroCopie(unNumeroCopie);
    } /* fine del metodo setter */


    /**
     * Assegna il nome del print job per la stampante.
     * <p/>
     */
    public void setNomePrintJob(String unNomePrintJob) {
        this.getImpostazione().setNomePrintJob(unNomePrintJob);
    } /* fine del metodo setter */


    /**
     * Flag - presenta il dialogo di impostazione pagina all'utente (solo AWT).
     * <p/>
     */
    public void setPresentaDialogoPagina(boolean isPresentaDialogoPagina) {
        this.getImpostazione().setPresentaDialogoPagina(isPresentaDialogoPagina);
    } /* fine del metodo setter */


    /**
     * Flag - presenta il dialogo di impostazione stampa all'utente.
     * <p/>
     */
    public void setPresentaDialogoStampa(boolean isPresentaDialogoStampa) {
        this.getImpostazione().setPresentaDialogoStampa(isPresentaDialogoStampa);
    } /* fine del metodo setter */


    /**
     * -- setter per regolare la Pagina --
     * Regola il codice identificativo della Carta.
     * <p/>
     *
     * @param codiceCarta il codice identificativo della Carta
     */

    /**
     * Regola il codice identificativo della Carta.
     * <p>
     * @param codiceCarta il codice identificativo della Carta
     */
    public void setCodiceCarta(Integer codiceCarta) {
        this.getImpostazione().setCodiceCarta(codiceCarta);
    } /* fine del metodo setter */


    /**
     * Regola la dimensione della Pagina.
     * <p/>
     *
     * @param larghezza la larghezza della Pagina
     * @param altezza l'altezza della Pagina
     */
    public void setDimensionePagina(double larghezza, double altezza) {
        this.getImpostazione().setDimensionePagina(larghezza, altezza);
    } /* fine del metodo setter */


    /**
     * Regola i margini della Pagina.
     * <p/>
     *
     * @param sx il margine sinistro
     * @param dx il margine destro
     * @param sopra il margine superiore
     * @param sotto il margine inferiore
     */
    public void setMarginePagina(double sx, double dx, double sopra, double sotto) {
        this.getImpostazione().setMarginePagina(sx, dx, sopra, sotto);
    } /* fine del metodo setter */


    /**
     * Regola l'orientamento della Pagina.
     * <p/>
     *
     * @param orientamentoPagina l'orientamento della Pagina
     */
    public void setOrientamentoPagina(int orientamentoPagina) {
        this.getImpostazione().setOrientamentoPagina(orientamentoPagina);
    } /* fine del metodo setter */


    protected int getNumeroPagina() {
        return numeroPagina;
    }


    private void setNumeroPagina(int numeroPagina) {
        this.numeroPagina = numeroPagina;
    }


    protected boolean isStampaTerminata() {
        return stampaTerminata;
    }


    private void setStampaTerminata(boolean stampaTerminata) {
        this.stampaTerminata = stampaTerminata;
    }


    /**
     * Ritorna l'impostazione della stampa.
     * <p/>
     *
     * @return l'impostazione della stampa
     */
    public Impostazione getImpostazione() {
        return impostazione;
    }


    /**
     * Assegna l'impostazione della stampa.
     * <p/>
     *
     * @param impostazione l'impostazione della stampa
     */
    public void setImpostazione(Impostazione impostazione) {
        this.impostazione = impostazione;
    }


    protected double getxMax() {
        return xMax;
    }


    protected void setxMax(double xMax) {
        this.xMax = xMax;
    }


    protected double getyMax() {
        return yMax;
    }


    protected void setyMax(double yMax) {
        this.yMax = yMax;
    }


    protected double getxMaxPagina() {
        return xMaxPagina;
    }


    private void setxMaxPagina(double xMaxPagina) {
        this.xMaxPagina = xMaxPagina;
    }


    protected double getyMaxPagina() {
        return yMaxPagina;
    }


    private void setyMaxPagina(double yMaxPagina) {
        this.yMaxPagina = yMaxPagina;
    }


    protected boolean isPaginaNuova() {
        return paginaNuova;
    }


    private void setPaginaNuova(boolean paginaNuova) {
        this.paginaNuova = paginaNuova;
    }


    /**
     * Pagina sulla quale disegnare, con coordinate gia' trasformate.
     * Le sottoclassi devono disegnare su questa pagina.
     */
    protected Graphics2D getPagina() {
        return pagina;
    }


    private void setPagina(Graphics2D pagina) {
        this.pagina = pagina;
    }


    /**
     * Pageable interface implementation
     */
     public int getNumberOfPages(){
         return UNKNOWN_NUMBER_OF_PAGES;
     }

    /**
     * Pageable interface implementation
     * Here PageFormat is always the same
     */
    public  PageFormat getPageFormat(int pageIndex){
        return this.getImpostazione().getPagina().getPageFormat();
    }

    /**
     * Pageable interface implementation
     */
    public  Printable getPrintable(int pageIndex){
        return this;
    }
    


}// fine della classe
