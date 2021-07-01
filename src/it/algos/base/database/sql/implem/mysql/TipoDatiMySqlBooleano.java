/**
 * Title:     TipoDatiMySqlBooleano
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      26-ott-2004
 */

package it.algos.base.database.sql.implem.mysql;

import it.algos.base.database.sql.DbSql;
import it.algos.base.database.sql.tipodati.TipoDatiSqlBooleano;
import it.algos.base.errore.Errore;

import java.sql.Types;

/**
 * Implementazione concreta di un tipo di dati booleano
 * per un Db MySql
 * </p>
 * <p/>
 * MySql gestisce il tipo booleano come TINYINT/BIT.<br>
 * Se creo un campo con la keyword BOOLEAN, viene
 * automaticamente creato come TINYINT sul DB e il driver JDBC
 * restituisce nel resultset il tipo BIT.<br>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 26-ott-2004 ore 13.51.14
 */
public class TipoDatiMySqlBooleano extends TipoDatiSqlBooleano {


    /**
     * Costruttore completo
     * <p/>
     *
     * @param dbSql il database Sql proprietario di questo oggetto
     */
    public TipoDatiMySqlBooleano(DbSql dbSql) {
        /** rimanda al costruttore della superclasse */
        super(dbSql);

        try {                                   // prova ad eseguire il codice
            this.inizia();
        } catch (Exception unErrore) {           // intercetta l'errore
            /** messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */
    } /* fine del metodo costruttore base */


    /**
     * Regolazioni iniziali di riferimenti e variabili
     * <p/>
     * Metodo chiamato direttamente dal costruttore
     */
    private void inizia() throws Exception {
        this.setTipoJdbc(Types.BIT);
    } /* fine del metodo inizia */


}