/**
 * Copyright(c): 2005
 * Company: Algos s.r.l.
 * Author: gac
 * Date: 3-4-05
 */

package it.algos.base.costante;

/**
 * Repository di costanti per i caratteri.
 * </p>
 * Questa classe: <ul>
 * <li> </li>
 * <li> </li>
 * </ul>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0 / 3-4-05
 */
public abstract class CostCaratteri {

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
     * Le cifre decimali (0-9)
     */
    public static final String CIFRE = "\\p{Digit}";

    /**
     * I numeri interi positivi, ovvero le cifre decimali (0-9),
     * il segno "." (separatore delle migliaia)
     */
    public static final String INTERI_POSITIVI = "\\p{Digit}\\.";

    /**
     * Tutti i numeri interi, ovvero le cifre decimali (0-9),
     * il segno "-"(meno) ed il segno "+"(più)
     */
    public static final String INTERI = "\\p{Digit}.\\-+";

    /**
     * I numeri reali, ovvero le cifre decimali (0-9),
     * I segni "+" e "-"
     * il segno ","(separatore decimale)
     */
    public static final String NUMERI = "\\p{Digit}.\\-+,";

    /**
     * I numeri esadecimali, ovvero le cifre da 0 a 9 più le lettere
     * dalla A alla F (case insensitive)
     */
    public static final String NUMERI_ESA = "\\p{XDigit}";

    /**
     * I caratteri che formano un Codice Fiscale valido, ovvero le
     * lettere maiuscole e le cifre.
     */
    public static final String CODICE_FISCALE = "A-Z0-9";

    /**
     * I caratteri che formano un identificatore Java valido, ovvero le
     * lettere, i numeri e l'underscore "_".
     */
    public static final String PAROLA = "a-zA-Z_0-9";

    /**
     * I segni di punteggiatura
     */
    public static final String PUNTEGGIATURA = "\\p{Punct}";

    /**
     * Gli spazi bianchi, includono il tab e altre spaziature speciali Unicode.
     */
    public static final String SPAZI_BIANCHI = "\\p{Space}";


} // fine della classe
