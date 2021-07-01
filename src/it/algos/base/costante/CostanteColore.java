/**
 * Title:        CostanteColore.java
 * Package:      it.algos.base.costante
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:      Algos s.r.l.
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /
 * Creato:       il 29 settembre 2002 alle 22.03
 */
//-----------------------------------------------------------------------------
// Copyright (c) 2002  Algos s.r.l. All Rights Reserved.
//-----------------------------------------------------------------------------
//-----------------------------------------------------------------------------
//  Package di questa interfaccia e classi importate
//-----------------------------------------------------------------------------
package it.algos.base.costante;

import java.awt.*;
//-----------------------------------------------------------------------------
// Classe principale


//-----------------------------------------------------------------------------
/**
 * Interfaccia per le costanti di colore usate da ogni programma.<br>
 *
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /  29 settembre 2002 ore 22.03
 */
public interface CostanteColore {

    //-------------------------------------------------------------------------
    // Costanti statiche della classe    (private,pubbliche)  (class variables)
    //-------------------------------------------------------------------------
    //----------------------------------------------------------------
    //  Costanti per il bianco e nero ed i grigi
    //----------------------------------------------------------------
    /**
     * Colore nero completo
     */
    public static final Color NERO = Color.black;

    /**
     * Colore nero scurissimo
     */
    public static final Color NERO_MOLTO_SCURO = new Color(30, 30, 30);

    /**
     * Colore nero scuro
     */
    public static final Color NERO_SCURO = new Color(60, 60, 60);

    /**
     * Colore grigio scuro
     */
    public static final Color GRIGIO_SCURO = Color.darkGray;

    /**
     * Colore grigio normale
     */
    public static final Color GRIGIO = Color.gray;

    /**
     * Colore grigio chiaro
     */
    public static final Color GRIGIO_CHIARO = Color.lightGray;

    /**
     * Colore grigio chiarissimo
     */
    public static final Color GRIGIO_MOLTO_CHIARO = new Color(160, 160, 160);

    /**
     * Colore grigio bianco
     */
    public static final Color BIANCO_GRIGIO = new Color(200, 200, 200);

    /**
     * Colore bianco sporco
     */
    public static final Color BIANCO_SPORCO = new Color(234, 234, 234);

    /**
     * Colore bianco completo
     */
    public static final Color BIANCO = Color.white;

    //----------------------------------------------------------------
    //  Costanti di colori base
    //----------------------------------------------------------------
    /**
     * Colore rosso base
     */
    public static final Color ROSSO = new Color(200, 20, 20);

    /**
     * Colore blu base
     */
    public static final Color BLU = Color.blue;

    /**
     * Colore verde base
     */
    public static final Color VERDE = Color.green;

    /**
     * Colore giallo (rosso e verde)
     */
    public static final Color GIALLO = Color.yellow;

    /**
     * Ciano (verde e blu)
     */
    public static final Color CYAN = Color.cyan;

    /**
     * Colore magenta (rosso e blu)
     */
    public static final Color MAGENTA = Color.magenta;

    /**
     * Colore arancio
     */
    public static final Color ARANCIO = Color.orange;

    /**
     * Colore rosa
     */
    public static final Color ROSA = Color.pink;

    //----------------------------------------------------------------
    //  Costanti di colori costruiti
    //----------------------------------------------------------------
    /**
     * Colore verde mio
     */
    public static final Color VERDE_SCURO = new Color(80, 165, 80);

    /**
     * Colore verde mio
     */
    public static final Color VERDE_CHIARO_UNO = new Color(156, 190, 156);

    public static final Color VERDE_CHIARO_DUE = new Color(140, 170, 140);

    public static final Color VERDE_CHIARO_TRE = new Color(140, 150, 140);

    public static final Color AZZURRO_UNO = new Color(156, 178, 190);

    public static final Color AZZURRO_DUE = new Color(140, 160, 170);

    public static final Color MARRONE_UNO = new Color(190, 190, 156);

    public static final Color MARRONE_DUE = new Color(170, 170, 140);

    /**
     * Colore viola mio
     */
    public static final Color VIOLA = new Color(180, 180, 200);

    public static final Color BLU_CHIARO = new Color(0, 110, 236);


    //----------------------------------------------------------------
    //  Costanti di colori specifici per oggetti dell'interfaccia
    //----------------------------------------------------------------
    /**
     * Colore sfondo avvisi nelle finestre
     */
    public static final Color SFONDO_AVVISO_BASSO = VERDE_SCURO;

    /* Colore sfondo nelle schede */
    public static final Color SFONDO_SCHEDA_ALTO = VERDE_CHIARO_UNO;

    public static final Color SFONDO_SCHEDA_CENTRO = VERDE_CHIARO_DUE;

    public static final Color SFONDO_SCHEDA_BASSO = VERDE_CHIARO_UNO;

    /* Colore sfondo nelle liste */
    public static final Color SFONDO_FINESTRA_LISTA_CENTRO = VERDE_CHIARO_DUE;

    public static final Color SFONDO_FINESTRA_LISTA_STATUSBAR = VERDE_CHIARO_UNO;

    /**
     * Colore sfondo delle finestre
     */
    public static final Color SFONDO_FINESTRA = GRIGIO_SCURO;

    /**
     * Colore sfondo delle finestre
     */
    public static final Color SFONDO_SCHEDA = VERDE_CHIARO_TRE;

    /**
     * Colore sfondo dei campi edit
     */
    public static final Color SFONDO_CAMPO_EDIT = BIANCO_GRIGIO;

    /**
     * Colore sfondo dei campi popup
     */
    public static final Color SFONDO_CAMPO_POPUP = BIANCO_GRIGIO;

    /**
     * Colore sfondo dei campi area testo
     */
    public static final Color SFONDO_CAMPO_TESTO_AREA = BIANCO_GRIGIO;

    /**
     * Colore sfondo dei campi radio bottoni
     */
    public static final Color SFONDO_CAMPO_RADIO = SFONDO_SCHEDA_CENTRO;

    /**
     * Colore sfondo delle liste interne scorrevoli
     */
    public static final Color SFONDO_CAMPO_LISTA = VERDE_CHIARO_UNO;

    /**
     * Colore testo avvisi nelle schede
     */
    public static final Color TESTO_AVVISO = new Color(100, 0, 100);

    /**
     * Colore testo avvisi nelle schede
     */
    public static final Color TESTO_AVVISO_SCHEDA = new Color(150, 50, 50);

    /**
     * Colore testo normale nelle finestre
     */
    public static final Color TESTO_NORMALE = NERO;

    /**
     * Colore bottoni di comando
     */
    public static final Color TESTO_BOTTONE = NERO;

    /**
     * Colore etichette statiche delle schede
     */
    public static final Color TESTO_ETICHETTA = BLU;

    /**
     * Colore statusbar delle finestre
     */
    public static final Color TESTO_STATUS_BAR = TESTO_ETICHETTA;

    /**
     * Colore etichette statiche delle schede
     */
    public static final Color TESTO_AGGIUNTIVO_CAMPO = ROSSO;

    /**
     * Colore titlebar finestra palette
     */
    public static final Color PALETTE_TITLEBAR = BLU_CHIARO;

    /**
     * Colore toolbar finestra palette
     */
    public static final Color PALETTE_TOOLBAR = new Color(150, 210, 255);


    /**
     * Colore campi testo disabilitati non modificabili
     */
    public static final Color SFONDO_CAMPO_DISABILITATO = new Color(120, 120, 120);
    //-------------------------------------------------------------------------
}// fine della interfaccia it.algos.base.costante.CostanteColore.java
//-----------------------------------------------------------------------------

