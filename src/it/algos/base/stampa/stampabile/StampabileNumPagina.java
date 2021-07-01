/**
 * Title:     StampabileNumPagina
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      22-mag-2006
 */
package it.algos.base.stampa.stampabile;

import it.algos.base.errore.Errore;
import it.algos.base.font.FontFactory;
import it.algos.base.libreria.Lib;

import java.awt.*;

/**
 * Stampa con footer contenente il numero di pagina.
 *
 * @author Guido Andrea Ceresa
 * @author Alessandro Valbonesi
 * @author alex
 * @version 1.0  /  22-mag-2006 ore 11.12
 */
public class StampabileNumPagina extends StampabileDefault {

    /* default - spessore della linea al piede */
    private static final float SPESSORE = 1;

    /* default - spaziatura verticale tra il filo e il testo sotto */
    private static final float DIST_FILO_TESTO = 4;

    /* default - margine non utilizzabile prima del filo */
    private static final float MARG_SOPRA_FILO = 4;

    /* default - testo della scritta "Pag." */
    private static final String TESTO_PAGINA = "Pag.";

    /* default - font per il testo */
    private static final Font FONT_PAGINA = FontFactory.creaPrinterFont();

    /* spessore della linea al piede */
    private float spessoreFilo;

    /* spaziatura verticale tra il filo e il testo sotto */
    private float distFiloTesto;

    /* margine non utilizzabile prima del filo */
    private float margSopraFilo;

    /* testo della scritta "Pag." */
    private String testoFissoNumPagima;

    /* font per il testo */
    private Font fontNumPagina;


    /**
     * Costruttore base senza parametri <br>
     * Indispensabile anche se non viene utilizzato
     * (anche solo per compilazione in sviluppo) <br>
     */
    public StampabileNumPagina() {
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


    /**
     * Regolazioni iniziali di riferimenti e variabili <br>
     * Metodo chiamato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        this.setSpessoreFilo(SPESSORE);
        this.setDistFiloTesto(DIST_FILO_TESTO);
        this.setMargSopraFilo(MARG_SOPRA_FILO);
        this.setTestoFissoNumPagima(TESTO_PAGINA);
        this.setFontNumPagina(FONT_PAGINA);
    } /* fine del metodo inizia */


    /**
     * Aggiunge i contenuti fissi a ogni pagina.
     * <p/>
     * Metodo da implementare nelle sottoclassi
     * per stampare un contenuto fisso su ogni pagina.
     *
     * @param pagina alla quale aggiungere il contenuto fisso
     */
    protected void aggiungeContenutoFisso(Graphics2D pagina) {
        /* variabili e costanti locali di lavoro */
        double xMaxPag;
        double yMaxPag;
        Font oldFont;
        Stroke oldStroke;
        String testo;
        int baseTesto;
        int lunTesto;
        double hTesto;
        double yFilo;

        try { // prova ad eseguire il codice

            /* memorizza le impostazioni */
            oldFont = pagina.getFont();
            oldStroke = pagina.getStroke();

            /* recupera alcune misure della pagina */
            xMaxPag = this.getxMaxPagina();
            yMaxPag = this.getyMaxPagina();

            /* disegna il testo con il numero di pagina */
            testo = this.getTestoFissoNumPagima() + " " + this.getNumeroPagina();
            baseTesto = Lib.Fonte.discendenteStandard(this.getFontNumPagina());
            float yTesto = (float)this.getyMaxPagina() - baseTesto;
            pagina.setFont(this.getFontNumPagina());
            lunTesto = this.getLunghezzaTesto(testo);
            pagina.drawString(testo, (float)(xMaxPag - lunTesto), yTesto);

            /* traccia la linea al piede */
            hTesto = Lib.Fonte.altezzaTotaleStandard(this.getFontNumPagina());
            yFilo = yMaxPag - hTesto - this.getDistFiloTesto();
            Stroke stroke = new BasicStroke(this.getSpessoreFilo());
            pagina.setStroke(stroke);
            pagina.drawLine(0, (int)yFilo, (int)xMaxPag, (int)yFilo);

            /* ripristina le impostazioni */
            pagina.setFont(oldFont);
            pagina.setStroke(oldStroke);

            /* riduce la dimensione massima utilizzabile
             * per escludere il piede */
            this.setyMax(yFilo - this.getMargSopraFilo());

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    private float getSpessoreFilo() {
        return spessoreFilo;
    }


    protected void setSpessoreFilo(float spessoreFilo) {
        this.spessoreFilo = spessoreFilo;
    }


    private float getDistFiloTesto() {
        return distFiloTesto;
    }


    protected void setDistFiloTesto(float distFiloTesto) {
        this.distFiloTesto = distFiloTesto;
    }


    private float getMargSopraFilo() {
        return margSopraFilo;
    }


    protected void setMargSopraFilo(float margSopraFilo) {
        this.margSopraFilo = margSopraFilo;
    }


    private String getTestoFissoNumPagima() {
        return testoFissoNumPagima;
    }


    protected void setTestoFissoNumPagima(String testoFissoNumPagima) {
        this.testoFissoNumPagima = testoFissoNumPagima;
    }


    private Font getFontNumPagina() {
        return fontNumPagina;
    }


    protected void setFontNumPagina(Font fontNumPagina) {
        this.fontNumPagina = fontNumPagina;
    }

}
