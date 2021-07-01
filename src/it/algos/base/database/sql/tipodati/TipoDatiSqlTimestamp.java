/**
 * Title:     TipoDatiSqlData
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      26-ott-2004
 */

package it.algos.base.database.sql.tipodati;

import it.algos.base.database.sql.DbSql;
import it.algos.base.errore.Errore;

import java.sql.Timestamp;
import java.sql.Types;

/**
 * Implementazione concreta di un tipo di dati Timestamp
 * per un Db Sql
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 26-ott-2004 ore 13.51.14
 */
public class TipoDatiSqlTimestamp extends TipoDatiSqlBase {


    /**
     * Costruttore completo
     * <p/>
     *
     * @param dbSql il database Sql proprietario di questo oggetto
     */
    public TipoDatiSqlTimestamp(DbSql dbSql) {
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
        this.setClasseBl(new Timestamp(0).getClass());
        this.setTipoJdbc(Types.TIMESTAMP);
        this.setKeyword(this.getDatabaseSql().getKeyTimestamp());
        this.setWrapperValore(this.getDatabaseSql().getDelimTesto());
        this.setValoreVuotoDb(null);
    } /* fine del metodo inizia */

}// fine della classe