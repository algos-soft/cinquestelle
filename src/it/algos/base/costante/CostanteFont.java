/**
 * Title:        CostanteFont.java
 * Package:      it.algos.base.costante
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:      Algos s.r.l.
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /
 * Creato:       il 29 settembre 2002 alle 22.06
 */
//-----------------------------------------------------------------------------
// Copyright (c) 2002  Algos s.r.l. All Rights Reserved.
//-----------------------------------------------------------------------------
//-----------------------------------------------------------------------------
//  Package di questa interfaccia e classi importate
//-----------------------------------------------------------------------------
package it.algos.base.costante;

import it.algos.base.font.FontFactory;

import java.awt.*;

//-----------------------------------------------------------------------------
// Classe principale
//-----------------------------------------------------------------------------


/**
 * Interfaccia per le costanti dei font usati da ogni programma.<br>
 *
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /  29 settembre 2002 ore 22.06
 */
public interface CostanteFont {

    //-------------------------------------------------------------------------
    // Costanti statiche della classe    (private,pubbliche)  (class variables)
    //-------------------------------------------------------------------------

    /*
     * Percorso fino alla cartella di libreria che contiene
     * le cartelle delle famiglie di font (cartelle contenenti
     * i file .TTF)
     */

    public static final String PERCORSO_CARTELLA_FONTS = "fonts";

    /* ---- Nomi delle famiglie interne di font di uso comune ---- */

    /* Famiglia di default per i font sullo schermo */

    public static final String NOME_FAMIGLIA_SCREEN = "arial";

    /* Famiglia di default per i font Sans Serif */
    public static final String NOME_FAMIGLIA_SANS = "arial";

    /* Famiglia di default per i font Serif */
    public static final String NOME_FAMIGLIA_SERIF = "times";

    /* Famiglia di default per i font Monospace */
    public static final String NOME_FAMIGLIA_MONO = "courier";

    /* Famiglia di default per la stampa Sans */
    public static final String NOME_FAMIGLIA_STAMPA_SANS = NOME_FAMIGLIA_SANS;

    /* Famiglia di default per la stampa Serif */
    public static final String NOME_FAMIGLIA_STAMPA_SERIF = NOME_FAMIGLIA_SERIF;

    /* Famiglia di default per la stampa Mono */
    public static final String NOME_FAMIGLIA_STAMPA_MONO = NOME_FAMIGLIA_MONO;

    /* Famiglia di default per le stampe */
    public static final String NOME_FAMIGLIA_STAMPA_DEFAULT = NOME_FAMIGLIA_STAMPA_SANS;

    /* ---- Dimensioni font di uso comune per gli elementi della GUI ---- */

    /**
     * Dimensione di default dei font piccolo per lo schermo
     */
    public static final float DIM_SCREEN_PICCOLO = 10;

    /**
     * Dimensione di default dei font normale per lo schermo
     */
    public static final float DIM_SCREEN_NORMALE = 12;

    /* ---- Font di uso comune per le stampe ---- */
    /*	Dimensione di default del testo normale per la stampa */
    public static final float DIM_STAMPA_NORMALE = 10;

    /*	Dimensione di default dei titoli di primo livello per la stampa */
    public static final float DIM_STAMPA_TITOLO_1 = 14;

    /*	Dimensione di default dei titoli di secondo livello per la stampa */
    public static final float DIM_STAMPA_TITOLO_2 = 12;

    /*	Dimensione di default dei titoli di terzo livello per la stampa */
    public static final float DIM_STAMPA_TITOLO_3 = 10;

    /*	Dimensione di default del testo piccolo per la stampa */
    public static final float DIM_STAMPA_PICCOLO = 8;

    /* ---- Fonts per gli specifici elementi della GUI ---- */

    /**
     * Font per i titoli della lista
     */
    public static final Font FONT_TITOLI_LISTA = FontFactory.creaScreenFont(Font.BOLD);

    /**
     * Font per i campi della lista
     */
    public static final Font FONT_CAMPI_LISTA = FontFactory.creaScreenFont();

    /**
     * Font per le etichette della scheda
     */
    public static final Font FONT_LABEL_SCHEDA = FontFactory.creaScreenFont(Font.PLAIN);

    /**
     * Font per i titoli della scheda
     */
    public static final Font FONT_TITOLI_SCHEDA = FontFactory.creaScreenFont();

    /**
     * Font standard
     */
    public static final Font FONT_STANDARD = FontFactory.creaScreenFont();

    /**
     * Font standard in grasetto
     */
    public static final Font FONT_STANDARD_BOLD = FontFactory.creaScreenFont(Font.BOLD);

    /**
     * Font standard in corsivo
     */
    public static final Font FONT_STANDARD_ITALIC = FontFactory.creaScreenFont(Font.ITALIC);

    /**
     * Font per i campi nella scheda
     */
    public static final Font FONT_CAMPI_EDIT_SCHEDA = FontFactory.creaScreenFont();

    /**
     * Font per i campi modificabili della scheda
     */
    public static final Font FONT_CAMPI_CHECK_SCHEDA = FontFactory.creaScreenFont(Font.BOLD);

    /**
     * Font per i campi modificabili della scheda
     */
    public static final Font FONT_CAMPI_RADIO_SCHEDA = FontFactory.creaScreenFont(Font.BOLD);

    /**
     * Font per i campi modificabili della scheda
     */
    public static final Font FONT_CAMPI_POPUP_SCHEDA = FontFactory.creaScreenFont();

    /**
     * Font per i testi aggiuntivi dei campi
     */
    public static final Font FONT_CAMPI_AGGIUNTIVI = FontFactory.creaScreenFont();

    /**
     * Font per i campi fissi della scheda
     */
    public static final Font FONT_CAMPI_FISSI_SCHEDA = FontFactory.creaScreenFont();

    /**
     * Font per le scritte fisse della scheda
     */
    public static final Font FONT_SCRITTE_SCHEDA = FontFactory.creaScreenFont();

    /**
     * Font per gli avvisi in basso alla lista ed alla scheda
     */
    public static final Font FONT_AVVISO_BASSO = FontFactory.creaScreenFont();

    /**
     * Font per i dialoghi di selezione ed avviso
     */
    public static final Font FONT_DIALOGHI = FontFactory.creaScreenFont();

    /**
     * Font per le liste a tendina
     */
    public static final Font FONT_LISTE = FontFactory.creaScreenFont();

    /**
     * Font per le scritte delle legenda
     */
    public static final Font FONT_LEGENDA_PICCOLO = FontFactory.creaScreenFont(Font.PLAIN,
            DIM_SCREEN_PICCOLO);

    /**
     * Font per le scritte delle legenda
     */
    public static final Font FONT_LEGENDA_NORMALE = FontFactory.creaScreenFont(Font.PLAIN,
            DIM_SCREEN_NORMALE);

    /**
     * Font per i campi edit evidenziati
     */
    public static final Font FONT_CAMPI_EDIT_BOLD_SCHEDA = FontFactory.creaScreenFont(Font.BOLD);

    /**
     * Font per le liste combobox
     */
    public static final Font FONT_COMBO = FontFactory.creaScreenFont();

    /* ---- Fonts per le stampe ---- */

    /*	Font per la stampa dei titoli di primo livello */

    public static final Font FONT_STAMPA_TITOLO_1 = FontFactory.creaPrinterFont(Font.BOLD,
            DIM_STAMPA_TITOLO_1);

    /*	Font per la stampa dei titoli di secondo livello */
    public static final Font FONT_STAMPA_TITOLO_2 = FontFactory.creaPrinterFont(Font.BOLD,
            DIM_STAMPA_TITOLO_2);

    /*	Font per la stampa dei titoli di terzo livello */
    public static final Font FONT_STAMPA_TITOLO_3 = FontFactory.creaPrinterFont(Font.BOLD,
            DIM_STAMPA_TITOLO_3);

    /*	Font per la stampa delle parti di dettaglio (testo normale) */
    public static final Font FONT_STAMPA_TESTO = FontFactory.creaPrinterFont();

    /*	Font per la stampa delle parti di dettaglio (testo piccolo) */
    public static final Font FONT_STAMPA_TESTO_PICCOLO = FontFactory.creaPrinterFont(Font.PLAIN,
            DIM_STAMPA_PICCOLO);

    //-------------------------------------------------------------------------
}// fine della interfaccia it.algos.base.costante.CostanteFont.java

//-----------------------------------------------------------------------------

