/**
 * Title:     InterpreteQmStandard
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      21-ott-2004
 */
package it.algos.base.database.sql.implem.standard;

import it.algos.base.database.sql.DbSql;
import it.algos.base.database.sql.interprete.InterpreteQmSql;

/**
 * Interprete di una Query di tipo INSERT/UPDATE/DELETE per un database Sql standard.
 * </p>
 * Trasforma una Query nel corrispondente comando INSERT/UPDATE/DELETE Sql.
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 21-ott-2004 ore 13.34.12
 */
public final class InterpreteQmStandard extends InterpreteQmSql {

    /**
     * Costruttore completo.
     * <p/>
     *
     * @param dbSql il database Sql proprietario
     */
    public InterpreteQmStandard(DbSql dbSql) {
        /* rimanda al costruttore della superclasse */
        super(dbSql);
    }// fine del metodo costruttore completo

}// fine della classe
