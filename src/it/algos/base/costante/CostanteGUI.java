/**
 * Title:        CostanteGUI.java
 * Package:      it.algos.base.costante
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:      Algos s.r.l.
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /
 * Creato:       il 29 settembre 2002 alle 22.09
 */
//-----------------------------------------------------------------------------
// Copyright (c) 2002  Algos s.r.l. All Rights Reserved.
//-----------------------------------------------------------------------------
//-----------------------------------------------------------------------------
//  Package di questa interfaccia e classi importate
//-----------------------------------------------------------------------------
package it.algos.base.costante;
//-----------------------------------------------------------------------------
// Classe principale


//-----------------------------------------------------------------------------
/**
 * Interfaccia per le costanti dell'interfaccia.<br>
 *
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /  29 settembre 2002 ore 22.09
 */
public interface CostanteGUI {

    //-------------------------------------------------------------------------
    // Costanti statiche della classe    (private,pubbliche)  (class variables)
    //-------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    //  Dimensioni delle finestre
    //--------------------------------------------------------------------------
    /**
     * larghezza di default
     */
    public static final int LARGHEZZA_FINESTRA_GRANDE = 900;

    public static final int LARGHEZZA_FINESTRA_MEDIA = 580;

    //    static final int LARGHEZZA_FINESTRA_PICCOLA = 400; // mac
//    public static final int LARGHEZZA_FINESTRA_PICCOLA = 432; // metal
    public static final int LARGHEZZA_FINESTRA_PICCOLA = 423; // con 1.4.1

    /**
     * altezza di default
     */
    public static final int ALTEZZA_FINESTRA_GRANDE = 620;

    public static final int ALTEZZA_FINESTRA_MEDIA = 530;

    public static final int ALTEZZA_FINESTRA_PICCOLA = 470;

    /**
     * finestra normale
     */
    public static final int LARGHEZZA_FINESTRA_BASE = LARGHEZZA_FINESTRA_MEDIA;

    public static final int ALTEZZA_FINESTRA_BASE = ALTEZZA_FINESTRA_GRANDE;

    /**
     * finestra normale di tabelle
     */
    public static final int LARGHEZZA_FINESTRA_TABELLA = LARGHEZZA_FINESTRA_PICCOLA;

    public static final int ALTEZZA_FINESTRA_TABELLA = ALTEZZA_FINESTRA_PICCOLA;

    //--------------------------------------------------------------------------
    //  Dimensioni delle schede
    //--------------------------------------------------------------------------
    /**
     * altezza della barra di menu in pixel
     */
    public static final int ALTEZZA_MENU = 33;

    /**
     * altezza della toolbar in pixel
     */
    public static final int ALTEZZA_TOOLBAR = 50;

    /**
     * altezza della striscia bassa in pixel
     */
    public static final int ALTEZZA_STRISCIA_BASSA = 30;

    /**
     * differenza di altezza tra scheda e finestra
     */
    public static final int DELTA_SCHEDA_FINESTRA =
            ALTEZZA_MENU + ALTEZZA_TOOLBAR + ALTEZZA_STRISCIA_BASSA;

    /** scheda normale */
//    public static final int LARGHEZZA_SCHEDA_BASE = LARGHEZZA_FINESTRA_BASE;
//    public static final int ALTEZZA_SCHEDA_BASE = ALTEZZA_FINESTRA_BASE - DELTA_SCHEDA_FINESTRA;

    /** scheda tabella */
//    public static final int LARGHEZZA_SCHEDA_TABELLA = LARGHEZZA_FINESTRA_TABELLA;
//    public static final int ALTEZZA_SCHEDA_TABELLA = ALTEZZA_FINESTRA_TABELLA - DELTA_SCHEDA_FINESTRA;
    //-------------------------------------------------------------------------
}// fine della interfaccia it.algos.base.costante.CostanteGUI.java
//-----------------------------------------------------------------------------

