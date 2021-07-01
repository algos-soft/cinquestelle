/**
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      15-gen-2005
 */
package it.algos.base.database.util;


/**
 * Interfaccia Funzione.
 * </p>
 * Questa interfaccia: <ul>
 * <li>Implementa una funzione del database.</li>
 * <li> Implementa il <i>design pattern</i> <b>Facade</b> </li>
 * <li> Fornisce un interfaccia unificata per un insieme di interfacce presenti
 * nel package</li>
 * </ul>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 15-gen-2005 ore 18.13.27
 */
public interface Funzione {

    /* codifica funzione COUNT */
    public static final String COUNT = "COUNT";

    /* codifica funzione MIN */
    public static final String MIN = "MIN";

    /* codifica funzione MAX */
    public static final String MAX = "MAX";

    /* codifica funzione SUM */
    public static final String SUM = "SUM";

    /* codifica funzione AVG */
    public static final String AVG = "AVG";

    /* codifica funzione LENGTH */
    public static final String LENGTH = "LENGTH";

    /* codifica funzione UPPER */
    public static final String UPPER = "UPPER";

    /* codifica funzione LOWER */
    public static final String LOWER = "LOWER";

    /* codifica funzione DISTINCT */
    public static final String DISTINCT = "DISTINCT";

}// fine della interfaccia
