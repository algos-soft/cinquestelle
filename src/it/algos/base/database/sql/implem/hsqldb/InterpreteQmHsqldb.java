/**
 * Title:     InterpreteQmHsqldb
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      16-lug-2007
 */
package it.algos.base.database.sql.implem.hsqldb;

import it.algos.base.database.sql.DbSql;
import it.algos.base.database.sql.interprete.InterpreteQmSql;
import it.algos.base.errore.Errore;

/**
 * Interprete di una Query di tipo INSERT/UPDATE/DELETE per un database HSQLDB.
 * </p>
 * Trasforma una Query nel corrispondente comando INSERT/UPDATE/DELETE Sql.
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 16-lug-2007 ore 21.51.12
 */
public final class InterpreteQmHsqldb extends InterpreteQmSql {

    /**
     * Costruttore completo.
     * <p/>
     *
     * @param dbSql il database Sql proprietario
     */
    public InterpreteQmHsqldb(DbSql dbSql) {
        /* rimanda al costruttore della superclasse */
        super(dbSql);
    }// fine del metodo costruttore completo


    /**
     * Ritorna la stringa corrispondente alla sezione Tavola.
     * <p/>
     * HSQLDB non supporta le DELETE in relazione
     *
     * @return la stringa con il comando e la tavola
     */
    protected String sezioneTavola() {
        /* variabili e costanti locali di lavoro */
        String stringa = "";

        try { // prova ad eseguire il codice

            stringa += this.getTavolaBase();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return stringa;
    }


}// fine della classe
