/**
 * Title:     TipoDatiSql
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      26-ott-2004
 */

package it.algos.base.database.sql.tipodati;

import it.algos.base.database.sql.util.OperatoreSql;
import it.algos.base.database.tipodati.TipoDati;

/**
 * Tipo di dati Sql generico
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 26-ott-2004 ore 17.11.30
 */
public interface TipoDatiSql extends TipoDati {


    /**
     * Ritorna una stringa Sql che rappresenta
     * un valore per questo tipo di dato.
     * <p/>
     *
     * @param valore un oggetto valore
     *
     * @return una stringa rappresentante il valore Sql
     */
    public abstract String stringaValore(Object valore);


    /**
     * Ritorna una stringa Sql che rappresenta
     * la stringa di confronto per un valore, un
     * operatore e questo tipo di dato.
     * <p/>
     * Il valore ritornato viene costruito considerando anche
     * alcune caratteristiche dell'operatore (es. la presenza e la posizione
     * delle wildcard per il testo)
     *
     * @param operatore l'operatore di confronto
     * @param valore l' oggetto valore
     *
     * @return una stringa rappresentante la stringa Sql di confronto
     */
    public abstract String stringaConfronto(Object valore, OperatoreSql operatore);


    /**
     * Ritorna la keyword Sql del Tipo Dati
     */
    public abstract String getKeyword();


    /**
     * Ritorna la stringa Sql che identifica la sezione tipo
     * nei comandi di creazione delle colonne.
     * <p/>
     *
     * @return la stringa
     */
    public String stringaSqlTipo();


    /**
     * Ritorna il tipo dati JDBC
     */
    public int getTipoJdbc();

    //-------------------------------------------------------------------------
}// fine della interfaccia
//-----------------------------------------------------------------------------

