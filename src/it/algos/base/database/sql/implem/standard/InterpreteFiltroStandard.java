/**
 * Title:     InterpreteFiltroStandard
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      25-ott-2004
 */
package it.algos.base.database.sql.implem.standard;

import it.algos.base.database.sql.DbSql;
import it.algos.base.database.sql.interprete.InterpreteFiltroSql;

/**
 * Interprete di un filtro per un database Sql standard.
 * <p/>
 * Trasforma un Filtro nel corrispondente comando WHERE Sql.
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 25-ott-2004 ore 12.30.47
 */
public final class InterpreteFiltroStandard extends InterpreteFiltroSql {

    /**
     * Costruttore completo.
     * <p/>
     *
     * @param dbSql il database Sql proprietario
     */
    public InterpreteFiltroStandard(DbSql dbSql) {
        /** rimanda al costruttore della superclasse */
        super(dbSql);
    } /* fine del metodo costruttore completo */


}// fine della classe it.algos.base.db.DBSelect.java
