/**
 * Title:        FontFactory.java
 * Package:      it.algos.base.font
 * Description:
 * Copyright:    Copyright (c) 2002-2004
 * Company:      Algos s.r.l.
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  alex
 * @version 1.0  /
 * Creato:       il 7 gennaio 2004 alle 14.11
 */
//-----------------------------------------------------------------------------
// Copyright (c) 2002-2004  Algos s.r.l. All Rights Reserved.
//-----------------------------------------------------------------------------
//-----------------------------------------------------------------------------
//  Package di questa classe e classi importate
//-----------------------------------------------------------------------------
package it.algos.base.font;

import it.algos.base.costante.CostanteFont;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.LibFont;
import it.algos.base.libreria.Libreria;
import it.algos.base.messaggio.MessaggioAvviso;
import it.algos.base.progetto.Progetto;

import java.awt.*;
import java.util.ArrayList;

//-----------------------------------------------------------------------------
// Classe principale
//-----------------------------------------------------------------------------


/**
 * Questa classe astratta factory e' responsabile di: <br>
 * A - Implementare i metodi Factory per la creazione di oggetti Font<br>
 * comunemente usati nel programma<br>
 *
 * @author Guido Andrea Ceresa
 * @author Alessandro Valbonesi
 * @version 1.0  /  7 gennaio 2004 ore 14.11
 */
public abstract class FontFactory extends Object {

    //-------------------------------------------------------------------------
    // Costanti statiche della classe    (private,pubbliche)  (class variables)
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Costruttori della classe                                   constructors)
    //-------------------------------------------------------------------------
    /**
     * Costruttore base senza parametri <br>
     * Indispensabile anche se non viene utilizzato
     * (anche solo per compilazione in sviluppo) <br>
     */
    public FontFactory() {
        /** rimanda al costruttore della superclasse */
        super();
    } /* fine del metodo costruttore completo */
    //-------------------------------------------------------------------------
    // Metodi statici della classe                              (class methods)
    //-------------------------------------------------------------------------


    /**
     * Crea un oggetto Font
     *
     * @param nomeFamiglia il nome della famiglia interna da utilizzare
     * @param stile lo stile del font
     * (Font.PLAIN, Font.BOLD, Font.ITALIC, Font.BOLD + Font.ITALIC)
     * @param dimensione la dimensione del font
     *
     * @return un oggetto Font con le caratteristiche specificate
     */
    public static Font creaFont(String nomeFamiglia, int stile, float dimensione) {
        /** variabili e costanti locali di lavoro */
        Font unFont = null;
        Font fontBase = null;

        try {    // prova ad eseguire il codice

            /* recupera il font di base richiesto */
            fontBase = recuperaFont(nomeFamiglia, stile);

            /* deriva il font nella dimensione desiderata */
            unFont = fontBase.deriveFont(dimensione);

        } catch (Exception unErrore) {    // intercetta l'errore
            /** mostra il messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /** valore di ritorno */
        return unFont;
    } /* fine del metodo */


    /**
     * Crea un oggetto Font usando il font di default per lo schermo
     *
     * @param stile lo stile del font
     * (Font.PLAIN, Font.BOLD, Font.ITALIC, Font.BOLD|ITALIC)
     * @param dimensione la dimensione del font
     *
     * @return un oggetto Font con le caratteristiche specificate
     */
    public static Font creaScreenFont(int stile, float dimensione) {
        /** valore di ritorno */
        return creaFont(CostanteFont.NOME_FAMIGLIA_SCREEN, stile, dimensione);
    } /* fine del metodo */


    /**
     * Crea un oggetto Font usando il font di default per lo schermo
     * nella dimensione normale e nello stile specificato
     *
     * @param stile lo stile del font
     *
     * @return un oggetto Font con le caratteristiche specificate
     */
    public static Font creaScreenFont(int stile) {
        /** valore di ritorno */
        return creaScreenFont(stile, CostanteFont.DIM_SCREEN_NORMALE);
    } /* fine del metodo */


    /**
     * Crea un oggetto Font usando il font di default per lo schermo
     * nello stile Plain e nella dimensione specificata
     *
     * @param dimensione la dimensione del font
     *
     * @return un oggetto Font con le caratteristiche specificate
     */
    public static Font creaScreenFont(float dimensione) {
        /** valore di ritorno */
        return creaScreenFont(Font.PLAIN, dimensione);
    } /* fine del metodo */


    /**
     * Crea un oggetto Font usando il font di default per lo schermo
     * nella dimensione normale e nello stile Plain
     *
     * @return un oggetto Font con le caratteristiche specificate
     */
    public static Font creaScreenFont() {
        /** valore di ritorno */
        return creaScreenFont(Font.PLAIN);
    } /* fine del metodo */


    /**
     * Crea un oggetto Font usando il font Sans di default per la stampante
     *
     * @param stile lo stile del font
     * (Font.PLAIN, Font.BOLD, Font.ITALIC, Font.BOLD + Font.ITALIC)
     * @param dimensione la dimensione del font
     *
     * @return un oggetto Font con le caratteristiche specificate
     */
    public static Font creaPrinterFontSans(int stile, float dimensione) {
        /** valore di ritorno */
        return creaFont(CostanteFont.NOME_FAMIGLIA_STAMPA_SANS, stile, dimensione);
    } /* fine del metodo */


    /**
     * Crea un oggetto Font usando il font Sans di default per la stampante
     * nella dimensione normale e nello stile specificato
     *
     * @param stile lo stile del font
     *
     * @return un oggetto Font con le caratteristiche specificate
     */
    public static Font creaPrinterFontSans(int stile) {
        /** valore di ritorno */
        return creaPrinterFontSans(stile, CostanteFont.DIM_STAMPA_NORMALE);
    } /* fine del metodo */


    /**
     * Crea un oggetto Font usando il font Sans di default per la stampante
     * nello stile Plain e nella dimensione specificata
     *
     * @param dimensione la dimensione del font
     *
     * @return un oggetto Font con le caratteristiche specificate
     */
    public static Font creaPrinterFontSans(float dimensione) {
        /** valore di ritorno */
        return creaPrinterFontSans(Font.PLAIN, dimensione);
    } /* fine del metodo */


    /**
     * Crea un oggetto Font usando il font Sans di default per la stampante
     * nella dimensione normale e nello stile Plain
     *
     * @return un oggetto Font con le caratteristiche specificate
     */
    public static Font creaPrinterFontSans() {
        /** valore di ritorno */
        return creaPrinterFontSans(Font.PLAIN);
    } /* fine del metodo */


    /**
     * Crea un oggetto Font usando il font Serif di default per la stampante
     *
     * @param stile lo stile del font
     * (Font.PLAIN, Font.BOLD, Font.ITALIC, Font.BOLD|ITALIC)
     * @param dimensione la dimensione del font
     *
     * @return un oggetto Font con le caratteristiche specificate
     */
    public static Font creaPrinterFontSerif(int stile, float dimensione) {
        /** valore di ritorno */
        return creaFont(CostanteFont.NOME_FAMIGLIA_STAMPA_SERIF, stile, dimensione);
    } /* fine del metodo */


    /**
     * Crea un oggetto Font usando il font Serif di default per la stampante
     * nella dimensione normale e nello stile specificato
     *
     * @param stile lo stile del font
     *
     * @return un oggetto Font con le caratteristiche specificate
     */
    public static Font creaPrinterFontSerif(int stile) {
        /** valore di ritorno */
        return creaPrinterFontSerif(stile, CostanteFont.DIM_STAMPA_NORMALE);
    } /* fine del metodo */


    /**
     * Crea un oggetto Font usando il font Serif di default per la stampante
     * nello stile Plain e nella dimensione specificata
     *
     * @param dimensione la dimensione del font
     *
     * @return un oggetto Font con le caratteristiche specificate
     */
    public static Font creaPrinterFontSerif(float dimensione) {
        /** valore di ritorno */
        return creaPrinterFontSerif(Font.PLAIN, dimensione);
    } /* fine del metodo */


    /**
     * Crea un oggetto Font usando il font Serif di default per la stampante
     * nella dimensione normale e nello stile Plain
     *
     * @return un oggetto Font con le caratteristiche specificate
     */
    public static Font creaPrinterFontSerif() {
        /** valore di ritorno */
        return creaPrinterFontSerif(Font.PLAIN);
    } /* fine del metodo */


    /**
     * Crea un oggetto Font usando il font Mono di default per la stampante
     *
     * @param stile lo stile del font
     * (Font.PLAIN, Font.BOLD, Font.ITALIC, Font.BOLD|ITALIC)
     * @param dimensione la dimensione del font
     *
     * @return un oggetto Font con le caratteristiche specificate
     */
    public static Font creaPrinterFontMono(int stile, float dimensione) {
        /** valore di ritorno */
        return creaFont(CostanteFont.NOME_FAMIGLIA_STAMPA_MONO, stile, dimensione);
    } /* fine del metodo */


    /**
     * Crea un oggetto Font usando il font Mono di default per la stampante
     * nella dimensione normale e nello stile specificato
     *
     * @param stile lo stile del font
     *
     * @return un oggetto Font con le caratteristiche specificate
     */
    public static Font creaPrinterFontMono(int stile) {
        /** valore di ritorno */
        return creaPrinterFontMono(stile, CostanteFont.DIM_STAMPA_NORMALE);
    } /* fine del metodo */


    /**
     * Crea un oggetto Font usando il font Mono di default per la stampante
     * nello stile Plain e nella dimensione specificata
     *
     * @param dimensione la dimensione del font
     *
     * @return un oggetto Font con le caratteristiche specificate
     */
    public static Font creaPrinterFontMono(float dimensione) {
        /** valore di ritorno */
        return creaPrinterFontMono(Font.PLAIN, dimensione);
    } /* fine del metodo */


    /**
     * Crea un oggetto Font usando il font Mono di default per la stampante
     * nella dimensione normale e nello stile Plain
     *
     * @return un oggetto Font con le caratteristiche specificate
     */
    public static Font creaPrinterFontMono() {
        /** valore di ritorno */
        return creaPrinterFontMono(Font.PLAIN);
    } /* fine del metodo */


    /**
     * Crea un oggetto Font di default per la stampante
     *
     * @param stile lo stile del font
     * (Font.PLAIN, Font.BOLD, Font.ITALIC, Font.BOLD + Font.ITALIC)
     * @param dimensione la dimensione del font
     *
     * @return un oggetto Font con le caratteristiche specificate
     */
    public static Font creaPrinterFont(int stile, float dimensione) {
        /** valore di ritorno */
        return creaFont(CostanteFont.NOME_FAMIGLIA_STAMPA_DEFAULT, stile, dimensione);
    } /* fine del metodo */


    /**
     * Crea un oggetto Font di default per la stampante
     * nella dimensione normale e nello stile specificato
     *
     * @param stile lo stile del font
     *
     * @return un oggetto Font con le caratteristiche specificate
     */
    public static Font creaPrinterFont(int stile) {
        /** valore di ritorno */
        return creaPrinterFont(stile, CostanteFont.DIM_STAMPA_NORMALE);
    } /* fine del metodo */


    /**
     * Crea un oggetto Font di default per la stampante
     * nello stile Plain e nella dimensione specificata
     *
     * @param dimensione la dimensione del font
     *
     * @return un oggetto Font con le caratteristiche specificate
     */
    public static Font creaPrinterFont(float dimensione) {
        /** valore di ritorno */
        return creaPrinterFont(Font.PLAIN, dimensione);
    } /* fine del metodo */


    /**
     * Crea un oggetto Font di default per la stampante
     * nella dimensione normale e nello stile Plain
     *
     * @return un oggetto Font con le caratteristiche specificate
     */
    public static Font creaPrinterFont() {
        /** valore di ritorno */
        return creaPrinterFont(Font.PLAIN);
    } /* fine del metodo */


    /**
     * Crea un oggetto Font in base a un nome famiglia
     * esistente tra le famiglie disponibili nel sistema
     *
     * @param nomeFamiglia il nome della famiglia di font
     *
     * @return un oggetto Font della famiglia richiesta
     *         con stile Plain e dimensione 1 punto
     *         null se la famiglia non esiste nel sistema
     */
    private static Font creaFontDaNomeFamiglia(String nomeFamiglia) {
        /** variabili e costanti locali di lavoro */
        Font unFont = null;
        String[] unElencoNomiFamiglie = null;
        int posizione = 0;

        try {                                   // prova ad eseguire il codice
            // recupera l'elenco delle famiglie disponibili sul sistema
            unElencoNomiFamiglie = LibFont.getElencoFamiglieFont();
            // verifica se la famiglia richiesta esiste sul sistema
            posizione = Libreria.posizioneStringaInArray(unElencoNomiFamiglie, nomeFamiglia, false);
            // se esiste, crea il font, altrimenti da' un messaggio
            if (posizione != -1) {
                unFont = new Font(nomeFamiglia, Font.PLAIN, 1);
            } else {
                new MessaggioAvviso("La famiglia di font " +
                        nomeFamiglia +
                        " non e' stata trovata nel sistema");
            } /* fine del blocco if-else */

        } catch (Exception unErrore) {           // intercetta l'errore
            /** messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /** valore di ritorno */
        return unFont;
    } /* fine del metodo */


    /**
     * Recupera una Font nello stile richiesto da una Famiglia interna. <br>
     *
     * @param nomeFamiglia il nome della famiglia
     * @param stile lo stile (rif. costanti di Font)
     *
     * @return il font richiesto, null se non trovato.
     */
    private static Font recuperaFont(String nomeFamiglia, int stile) {
        /* variabili e costanti locali di lavoro */
        Font font = null;
        FamigliaFont famiglia = null;

        try {    // prova ad eseguire il codice
            /* recupera la famiglia */
            famiglia = recuperaFamiglia(nomeFamiglia);
            if (famiglia != null) {
                font = recuperaFont(famiglia, stile);
            }// fine del blocco if-else

        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return font;
    } // fine del metodo


    /**
     * Recupera una famiglia di font dalla collezione di Progetto.<br>
     * Se la famiglia richiesta non viene trovata, recupera la famiglia<br>
     * di default, che dovrebbe essere sempre presente.<br>
     * Se nemmeno la famiglia di default viene trovata, visualizza un<br>
     * messaggio e ritorna null.
     *
     * @param nomeFamiglia il nome della famiglia da recuperare
     *
     * @return la famiglia di font richiesta (null se non trovata)
     */
    private static FamigliaFont recuperaFamiglia(String nomeFamiglia) {
        /* variabili e costanti locali di lavoro */
        FamigliaFont famiglia = null;
        String messaggio = null;

        try {    // prova ad eseguire il codice

            /* tenta di recuperare la famiglia richiesta dal Progetto */
            famiglia = recuperaFamigliaProgetto(nomeFamiglia);

            /* se non e' stata trovata, tenta di recuperare la famiglia di default */
            if (famiglia == null) {
                famiglia = recuperaFamigliaProgetto("_default");
                /* se nemmeno questa e' stata trovata, visualizza messaggio */
                if (famiglia == null) {
                    messaggio = "Famiglia font " + nomeFamiglia + " non trovata.\n";
                    messaggio += "Ho tentato di sostituirla con la famiglia \n";
                    messaggio += "di default, ma nemmeno questa e' stata trovata.";
                    new MessaggioAvviso(messaggio);
                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return famiglia;
    } // fine del metodo


    /**
     * Recupera una famiglia di font dalla collezione di Progetto.<br>
     *
     * @param nomeFamiglia il nome della famiglia da recuperare
     *
     * @return la famiglia di font richiesta (null se non trovata)
     */
    private static FamigliaFont recuperaFamigliaProgetto(String nomeFamiglia) {
        /* variabili e costanti locali di lavoro */
        FamigliaFont famiglia = null;
        Progetto progetto = null;
        ArrayList collezioneFamiglie = null;
        FamigliaFont unaFamiglia = null;
        String unNomeFamiglia = null;

        try {    // prova ad eseguire il codice

            /* recupera la collezione delle famiglie dal progetto */
            progetto = Progetto.getIstanza();
            collezioneFamiglie = progetto.getFamiglieFonts();

            /* cerca la famiglia richiesta nella collezione */
            for (int k = 0; k < collezioneFamiglie.size(); k++) {
                unaFamiglia = (FamigliaFont)collezioneFamiglie.get(k);
                unNomeFamiglia = unaFamiglia.getNomeFamiglia();
                if (unNomeFamiglia.equals(nomeFamiglia)) {
                    famiglia = unaFamiglia;
                    break;
                }// fine del blocco if
            } // fine del ciclo for

        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return famiglia;
    } // fine del metodo


    /**
     * Recupera una Font nello stile richiesto da una Famiglia. <br>
     *
     * @param famiglia l'oggetto FamigliaFont dal quale recuperare il font
     * @param stile lo stile (rif. costanti di Font)
     *
     * @return il font richiesto, null se non trovato.
     */
    private static Font recuperaFont(FamigliaFont famiglia, int stile) {
        /* variabili e costanti locali di lavoro */
        Font font = null;
        ArrayList elencoFontSingoli = null;
        FontSingolo unFontSingolo = null;
        int unoStile = 0;
        boolean trovato = false;

        try {    // prova ad eseguire il codice
            /* recupera l'elenco di oggetti FontSingolo dalla Famiglia */
            elencoFontSingoli = famiglia.getElencoFont();

            /* spazzola l'elenco alla ricerca dello stile richiesto */
            for (int k = 0; k < elencoFontSingoli.size(); k++) {
                unFontSingolo = (FontSingolo)elencoFontSingoli.get(k);
                unoStile = unFontSingolo.getStile();
                if (unoStile == stile) {
                    font = unFontSingolo.getFont();
                    trovato = true;
                    break;
                }// fine del blocco if
            } // fine del ciclo for

            /* se non e' stato trovato manda un messaggio */
            if (trovato == false) {
                new MessaggioAvviso("Famiglia font " +
                        famiglia.getNomeFamiglia() +
                        ": stile non trovato.");
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return font;
    } // fine del metodo

    //-------------------------------------------------------------------------
}// fine della classe

//-----------------------------------------------------------------------------
