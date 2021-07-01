/**
 * Title:     TipoDati
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      26-ott-2004
 */

package it.algos.base.database.tipodati;

import it.algos.base.campo.base.Campo;

/**
 * Tipo di dati generico
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 26-ott-2004 ore 17.11.30
 */
public interface TipoDati {

    /**
     * codifica dei tipi di dati disponibili per il database  booleano (vero/falso)
     */

    /** booleano (vero/falso) */
    public static final int TIPO_BOOLEANO = 100;

    /**
     * data (calendario)
     */
    public static final int TIPO_DATA = 200;

    /**
     * intero 32 bit con segno
     */
    public static final int TIPO_INTERO = 300;

    /**
     * numerico a virgola fissa con segno
     */
    public static final int TIPO_DECIMAL = 400;

    /**
     * numerico a virgola mobile con segno con 15 cifre di mantissa
     */
    public static final int TIPO_DOUBLE = 500;

    /**
     * intero a 32 bit con valore nullo al posto dello zero - usato per i link
     */
    public static final int TIPO_LINK = 600;

    /**
     * intero 64 bit con segno
     */
    public static final int TIPO_LONG = 700;

    /**
     * testo a lunghezza variabile
     */
    public static final int TIPO_TESTO = 800;

    /**
     * ora, minuti e secondi
     */
    public static final int TIPO_TIME = 900;

    /**
     * data + ora/minuti/secondi + nanosecondi
     */
    public static final int TIPO_TIMESTAMP = 1000;


    /**
     * Converte un valore da livello Business Logic
     * a livello Database per questo tipo di dati e per un dato Campo.
     * <p/>
     *
     * @param valoreIn il valore a livello di Business Logic da convertire
     * @param campo il campo a fronte del quale effettuare la conversione
     *
     * @return il valore convertito al livello di Database
     */
    public abstract Object bl2db(Object valoreIn, Campo campo);


    /**
     * Converte un valore da livello Database
     * a livello Business Logic per questo tipo di dati e per un dato Campo.
     * <p/>
     *
     * @param valoreIn il valore a livello di Database da convertire
     * @param campo il campo a fronte del quale effettuare la conversione
     *
     * @return il valore convertito al livello di Business Logic
     */
    public abstract Object db2bl(Object valoreIn, Campo campo);


    /**
     * Verifica se un oggetto e' compatibile con la classe
     * che rappresenta la business logic di questo tipo.<p>
     * Oggetti nulli non sono compatibili.
     *
     * @param oggetto l'oggetto da testare
     *
     * @return true se l'oggetto e' una istanza della classe
     */
    public abstract boolean isIstanzaClasseBl(Object oggetto);


    /**
     * Ritorna il valore 'vuoto' per questo tipo di dato
     * <p/>
     *
     * @return una stringa rappresentante il valore
     */
    public abstract Object getValoreVuotoDb();


    /**
     * Assegna il valore 'vuoto' per questo tipo di dato
     * <p/>
     *
     * @param valoreVuoto il valore 'vuoto'
     */
    public abstract void setValoreVuotoDb(Object valoreVuoto);


    /**
     * Ritorna una stringa che rappresenta
     * un Valore per questo tipo di dato
     *
     * @param valore un oggetto valore
     *
     * @return una stringa rappresentante il valore
     */
    public abstract String stringaValore(Object valore);


    /**
     * Ritorna true se questo tipo dati e' di tipo testo.
     * <p/>
     * E' di tipo Testo se la classe di business logic e' String.
     *
     * @return true se e' di tipo testo
     */
    public abstract boolean isTipoTesto();


    /**
     * Ritorna la classe di business logic per questo tipo dati
     * <p/>
     *
     * @return la classe di business logic
     */
    public abstract Class getClasseBl();


    /**
     * Assegna la classe di business logic per questo tipo dati
     * <p/>
     *
     * @param classe la classe di business logic
     */
    public void setClasseBl(Class classe);


}// fine della interfaccia