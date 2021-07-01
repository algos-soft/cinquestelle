/**
 * Title:     InterpreteOrdineStandard
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      25-ott-2004
 */
package it.algos.base.database.sql.implem.standard;

import it.algos.base.database.sql.DbSql;
import it.algos.base.database.sql.interprete.InterpreteOrdineSql;

/**
 * Interprete di un Ordine per un database Sql standard.
 * <p/>
 * Trasforma un Ordine nel corrispondente comando ORDER BY Sql.
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 25-ott-2004 ore 12.30.47
 */
public final class InterpreteOrdineStandard extends InterpreteOrdineSql {

    /**
     * Costruttore completo.
     * <p/>
     *
     * @param dbSql il database Sql proprietario
     */
    public InterpreteOrdineStandard(DbSql dbSql) {
        /** rimanda al costruttore della superclasse */
        super(dbSql);
    } /* fine del metodo costruttore completo */


}// fine della classe it.algos.base.db.DBSelect.java
