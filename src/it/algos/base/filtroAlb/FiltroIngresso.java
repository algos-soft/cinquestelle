/**
 * Title:        Filtro.java
 * Package:      it.algos.base.filtroAlb
 * Description:
 * Copyright:    __COPY__
 * Company:      __COMPANY__
 * @author __AUTORI__  /  albi
 * @version 1.0  /
 * Creato:       il 5 dicembre 2003 alle 12.20
 */
//-----------------------------------------------------------------------------
// __COPY__  __COMPANY__ All Rights Reserved.
//-----------------------------------------------------------------------------
//-----------------------------------------------------------------------------
//  Package di questa interfaccia e classi importate
//-----------------------------------------------------------------------------
package it.algos.base.filtroAlb;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.regex.PatternSyntaxException;

//-----------------------------------------------------------------------------
// Classe principale


//-----------------------------------------------------------------------------
/**
 * Interfaccia che definisce i filtri di ingresso. Estende KeyListener, in modo
 * che i filtri possano essere applicati ai componenti swing/awt direttamente
 * con addKeyListener. Definisce inoltre numerose costanti stringa che
 * rappresentano alcune classi di caratteri molto utilizzate.
 *
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  albi
 * @version 1.0  /  5 dicembre 2003 ore 12.20
 */
public interface FiltroIngresso extends KeyListener {
    //-------------------------------------------------------------------------
    // Costanti statiche della classe    (private,pubbliche)  (class variables)
    //-------------------------------------------------------------------------

    /**
     * Vocali accentate maiuscole, con accento grave e acuto, e "Y" accentata.
     * La codifica Unicode garantisce la portabilit&agrave;.
     */
    public static final String ACCENTATE_MAIUSCOLE =
            "\u00c0\u00c1\u00c8\u00c9\u00cc\u00cd\u00d2\u00d3\u00d9\u00da\u00dd";

    /**
     * Vocali accentate minuscole, con accento grave e acuto, e "y" accentata.
     * La codifica Unicode garantisce la portabilit&agrave;.
     */
    public static final String ACCENTATE_MINUSCOLE =
            "\u00e0\u00e1\u00e8\u00e9\u00ec\u00ed\u00f2\u00f3\u00f9\u00fa\u00fd";

    /**
     * Vocali accentate maiuscole e minuscole.
     */
    public static final String ACCENTATE = ACCENTATE_MAIUSCOLE + ACCENTATE_MINUSCOLE;

    /**
     * Tutte le lettere
     */
    public static final String LETTERE = "\\p{L}";

    /**
     * Tutte le lettere maiuscole
     */
    public static final String MAIUSCOLE = "\\p{Lu}";

    /**
     * Tutte le lettere minuscole
     */
    public static final String MINUSCOLE = "\\p{L}&&[^\\p{Lu}]";

    /**
     * Numeri esadecimali, ovvero le cifre da 0 a 9 pi&ugrave; le lettere
     * dalla A alla F (case insensitive)
     */
    public static final String NUMERI_ESA = "\\p{XDigit}";

    /**
     * Cifre decimali (0-9)
     */
    public static final String NUMERI = "\\p{Digit}";

    /**
     * I caratteri che formano un identificatore Java valido, ovvero le
     * lettere, i numeri e l'underscore "_".
     */
    public static final String PAROLA = "a-zA-Z_0-9";

    /**
     * Segni di punteggiatura
     */
    public static final String PUNTEGGIATURA = "\\p{Punct}";

    /**
     * Spazi bianchi, includono il tab e altre spaziature speciali Unicode.
     */
    public static final String SPAZI_BIANCHI = "\\p{Space}";

    /**
     * Tutti i caratteri stampabili.
     *
     * @deprecated Il filtroOld ignora i caratteri non stampabili.
     */
    public static final String STAMPABILI = "\\p{Print}";

    /**
     * Il trattino "-". Messo come costante a s&egrave; perch&egrave;
     * necessita di una speciale sequenza escape, altrimenti viene
     * interpretato come metacarattere dal filtroOld e non funziona pi&ugrave;
     * niente.
     */
    public static final String TRATTINO = "\\-";

    /**
     * Segni di valuta.
     *
     * @todo il carattere euro (€, \u20ac), non viene MAI accettato
     * se c'&egrave; almeno un carattere rifiutato.
     */
    public static final String VALUTE = "\\p{Sc}";


    //-------------------------------------------------------------------------
    /**
     * consuma l'evento <code>e</code> se consiste in un carattere non valido.
     * Altrimenti, non fa nulla
     */
    public void filtra(KeyEvent e) throws IllegalStateException;


    /**
     * imposta i caratteri che il filtroOld accetta
     */
    public void addCaratteriAccettati(String s);


    /**
     * imposta i caratteri che il filtroOld rifiuta
     */
    public void addCaratteriRifiutati(String s);


    /**
     * prima di utilizzare il filtroOld, e dopo aver impostato i caratteri accettati
     * e/o rifiutati, invocare sempre questo metodo
     */
    public void inizializza() throws IllegalArgumentException, PatternSyntaxException;


    /**
     * crea una copia non inizializzata di questo filtroOld
     */
    public FiltroIngresso clona();
}// fine della interfaccia it.algos.base.filtroAlb.FiltroIngresso.java
//-----------------------------------------------------------------------------

