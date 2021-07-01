/**
 * Title:     LibreriaSql
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      2-nov-2004
 */
package it.algos.base.database.sql.util;

import it.algos.base.database.sql.DbSql;
import it.algos.base.errore.Errore;

/**
 * Libreria di utilita' generali per un dtabase Sql.
 * </p>
 * Classe astratta con soli metodi statici <br>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 2-nov-2004 ore 11.12.24
 */
public abstract class LibreriaSql extends Object {

    /**
     * Introduce la funzione UPPER su una stringa.
     * <p/>
     *
     * @param stringaIn la stringa da racchiudere nella funzione UPPER
     * @param dbSql il database Sql di riferimento
     *
     * @return la stringa risultante
     */
    public static String upper(String stringaIn, DbSql dbSql) {
        /* variabili e costanti locali di lavoro */
        String stringaOut = null;

        try {    // prova ad eseguire il codice
            stringaOut = dbSql.getUpper();
            stringaOut += dbSql.getParentesiAperta();
            stringaOut += stringaIn;
            stringaOut += dbSql.getParentesiChiusa();
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return stringaOut;
    }


    /**
     * Introduce la funzione NOT su una stringa.
     * <p/>
     *
     * @param stringaIn la stringa da racchiudere nella funzione NOT
     * @param dbSql il database Sql di riferimento
     *
     * @return la stringa risultante
     */
    public static String not(String stringaIn, DbSql dbSql) {
        /* variabili e costanti locali di lavoro */
        String stringaOut = null;

        try {    // prova ad eseguire il codice
            stringaOut = dbSql.getNot();
            stringaOut += dbSql.getParentesiAperta();
            stringaOut += stringaIn;
            stringaOut += dbSql.getParentesiChiusa();
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return stringaOut;
    }


}// fine della classe
