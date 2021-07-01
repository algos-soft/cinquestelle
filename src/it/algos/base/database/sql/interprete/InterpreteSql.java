/**
 * Title:     InterpreteSql
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      3-nov-2004
 */
package it.algos.base.database.sql.interprete;

import it.algos.base.database.interprete.Interprete;
import it.algos.base.database.sql.DbSql;

/**
 * Interprete di comandi per un database Sql.
 * </p>
 * Un Interprete Sql e' delegato a trasformare gli oggetti astratti
 * (query, filtri, ecc...) in comandi specifici per il database Sql.
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 3-nov-2004 ore 8.22.03
 */
public interface InterpreteSql extends Interprete {

    /**
     * Ritorna il database Sql proprietario dell'Interprete.
     * <p/>
     *
     * @return il database Sql proprietario
     */
    public DbSql getDatabaseSql();

}// fine della interfaccia
