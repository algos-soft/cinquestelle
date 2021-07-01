/**
 * Title:     InterpreteQsPostgres
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      21-ott-2004
 */
package it.algos.base.database.sql.implem.postgres;

import it.algos.base.database.sql.DbSql;
import it.algos.base.database.sql.interprete.InterpreteQsSql;

/**
 * Interprete di una Query di tipo SELECT per un database Postgresql.
 * </p>
 * Trasforma una Query nel corrispondente comando SELECT Sql.
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 21-ott-2004 ore 13.34.12
 */
public final class InterpreteQsPostgres extends InterpreteQsSql {


    /**
     * Costruttore completo.
     * <p/>
     *
     * @param dbSql il database Sql proprietario
     */
    public InterpreteQsPostgres(DbSql dbSql) {
        /* rimanda al costruttore della superclasse */
        super(dbSql);
    }// fine del metodo costruttore completo


}// fine della classe
