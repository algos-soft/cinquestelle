/**
 * Title:        CostanteCarattere.java
 * Package:      it.algos.base.costante
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:      Algos s.r.l.
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /
 * Creato:       il 29 settembre 2002 alle 22.02
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
 * Interfaccia per le costanti dei caratteri usati da ogni programma.<br>
 *
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /  29 settembre 2002 ore 22.02
 */
public interface CostanteCarattere {

    //-------------------------------------------------------------------------
    // Costanti statiche della classe    (private,pubbliche)  (class variables)
    //-------------------------------------------------------------------------
    /**
     * un numero molto grande
     */
    public static final int MAX = 99999;

    /**
     * Una stringa vuota
     */
    public static final String STRINGA_VUOTA = "";

    /**
     * Un punto
     */
    public static final String PUNTO = ".";

    /**
     * Il carattere separatore per i nomi interni di directory
     */
    public static final char SEP_DIR = '/';

    /**
     * Una sottolineatura
     */
    public static final String UNDERSCORE = "_";

    /**
     * Uno spazio vuoto
     */
    public static final String SPAZIO = " ";

    /**
     * Due spazi vuoti
     */
    public static final String SPAZIO_DOPPIO = "  ";

    /**
     * Tabulatore - 5 spazi vuoti
     */
    public static final String TAB = "     ";

    /**
     * Separatore nelle stringhe
     */
    public static final String SEPARATORE = ",";

    /**
     * Numeri cardinali
     */
    public static final String[] CARDINALI =
            {"Uno",
                    "Due",
                    "Tre",
                    "Quattro",
                    "Cinque",
                    "Sei",
                    "Sette",
                    "Otto",
                    "Nove",
                    "Dieci",
                    "Undici",
                    "Dodici",
                    "Tredici",
                    "Quattordi",
                    "Quindici",
                    "Sedici",
                    "Diciasse",
                    "Diciotto",
                    "Diciannove",
                    "Venti",
                    "Ventuno",
                    "Ventidue",
                    "Ventitre",
                    "Ventiquattro",
                    "Venticinque",
                    "Ventisei",
                    "Ventisette",
                    "Ventotto",
                    "Ventinove",
                    "Trenta"};

    /**
     * Numeri ordinali
     */
    public static final String[] ORDINALI =
            {"Primo",
                    "Secondo",
                    "Terzo",
                    "Quarto",
                    "Quinto",
                    "Sesto",
                    "Settimo",
                    "Ottavo",
                    "Nono",
                    "Decimo",
                    "Undicesimo",
                    "Dodicesimo",
                    "Tredicesimo",
                    "Quattordicesimo",
                    "Quindicesimo",
                    "Sedicesimo",
                    "Diciassettesimo",
                    "Diciottesimo",
                    "Diciannovesimo",
                    "Ventesimo",
                    "Ventunesimo",
                    "Ventiduesimo",
                    "Ventitreesimo",
                    "Ventiquattresimo",
                    "Venticinquesimo",
                    "Ventiseiesimo",
                    "Ventisettesimo",
                    "Ventottesimo",
                    "Ventinovesimo",
                    "Trentesimo"};
    //-------------------------------------------------------------------------
}// fine della interfaccia it.algos.base.costante.CostanteCarattere.java
//-----------------------------------------------------------------------------

