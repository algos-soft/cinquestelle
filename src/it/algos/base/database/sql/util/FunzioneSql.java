/**
 * Title:     FunzioneSql
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      15-gen-2005
 */
package it.algos.base.database.sql.util;

import it.algos.base.database.util.FunzioneBase;
import it.algos.base.errore.Errore;

/**
 * Funzione Sql generica
 * </p>
 * Descrive il tipo di dati per una funzione Sql.
 * <p/>
 * Una funzione Sql puo' essere Aggregante o Scalare.<br>
 * E' aggregante quando il numero di righe del risultato e' inferiore al
 * numero di righe che si avrebbe senza la funzione (es. COUNT, SUM, DISTINCT...)
 * E' scalare quando la query produce lo stesso numero di righe di risultato
 * con o senza la funzione (es. LENGTH, UPPER, LOWER...)<br>
 * <p/>
 * Se una colonna di una query contiene una funzione aggregante,
 * anche tutte le altre colonne devono avere una funzione aggregante
 * se no il database da' un errore.<br>
 * <p/>
 * Funzioni aggreganti (un solo risultato per tutte le righe)
 * - COUNT -> tipo output numerico
 * - MIN   -> tipo output = tipo input
 * - MAX   -> tipo output = tipo input
 * - SUM   -> tipo output numerico
 * - AVG   -> tipo output numerico
 * <p/>
 * Funzioni scalari (un risultato per riga)
 * - LENGTH -> tipo output numerico
 * - UPPER  -> tipo output = tipo input
 * - LOWER  -> tipo output = tipo input
 * <p/>
 * Funzioni "speciali" (variano il numero righe risultato)
 * vedere come gestirle...
 * - DISTINCT -> tipo output = tipo input,
 * puo' esserci una sola colonna con DISTINCT in una query
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 15-gen-2005 ore 12.48.05
 */
public final class FunzioneSql extends FunzioneBase {

    /**
     * Simbolo Sql della funzione
     */
    private String simbolo = null;

    /**
     * Flag - se la funzione e' scalare (se no e' aggregante)
     */
    private boolean isScalare = false;


    /**
     * Costruttore completo con parametri.
     * <p/>
     */
    public FunzioneSql() {
        /* rimanda al costruttore della superclasse */
        super();

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo costruttore completo


    /**
     * Regolazioni immediate di riferimenti e variabili. <br>
     * Metodo chiamato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
    }// fine del metodo inizia


    public String getSimbolo() {
        return simbolo;
    }


    public void setSimbolo(String simbolo) {
        this.simbolo = simbolo;
    }


    public boolean isScalare() {
        return isScalare;
    }


    public void setScalare(boolean scalare) {
        isScalare = scalare;
    }

}// fine della classe
