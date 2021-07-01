/**
 * Title:     Operatore
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      25-ott-2004
 */
package it.algos.base.database.util;

/**
 * Costanti di tipo Operatore (usate nei filtri)
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 25-ott-2004 ore 8.39.38
 */
public interface Operatore {

    /* codifica clausole di unione */

    /**
     * codifica per la clausola di unione tra filtri di tipo AND
     */
    public static final String AND = "e";

    /**
     * codifica per la clausola di unione tra filtri di tipo OR
     */
    public static final String OR = "o";

    /**
     * codifica per la clausola di unione tra filtri di tipo AND NOT
     */
    public static final String AND_NOT = "e non";

    /* codifica versi di ordinamento */

    /**
     * codifica per il verso di ordinamento ascendente
     */
    public static final String ASCENDENTE = "ASC";

    /**
     * codifica per il verso di ordinamento discendente
     */
    public static final String DISCENDENTE = "DESC";

    /* codifica operatori */

    /**
     * codifica per l'operatore UGUALE (=)
     */
    public static final String UGUALE = "=";

    /**
     * codifica per l'operatore DIVERSO (!=)
     */
    public static final String DIVERSO = "Diverso";

    /**
     * codifica per l'operatore MAGGIORE (>)
     */
    public static final String MAGGIORE = ">";

    /**
     * codifica per l'operatore MINORE (<)
     */
    public static final String MINORE = "<";

    /**
     * codifica per l'operatore MAGGIORE UGUALE (>=)
     */
    public static final String MAGGIORE_UGUALE = ">=";

    /**
     * codifica per l'operatore MINORE UGUALE (<=)
     */
    public static final String MINORE_UGUALE = "<=";

    /**
     * codifica per l'operatore COMINCIA CON
     */
    public static final String COMINCIA = "Comincia";

    /**
     * codifica per l'operatore FINISCE CON
     */
    public static final String FINISCE = "Finisce";

    /**
     * codifica per l'operatore CONTIENE
     */
    public static final String CONTIENE = "Contiene";

    /**
     * codifica per l'operatore MASCHERA
     */
    public static final String MASCHERA = "Maschera";

    /**
     * codifica per l'operatore IN
     */
    public static final String IN = "In";



    public abstract String getSimbolo();


    public abstract void setSimbolo(String simbolo);


    public String getWildcard();


    public void setWildcard(String wildcard);


    public int getPosizioneWildcard();


    public void setPosizioneWildcard(int posizioneWildcard);

}// fine della interfaccia
