/**
 * Title:     InterpreteStructSql
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      8-nov-2004
 */
package it.algos.base.database.sql.implem.standard;

import it.algos.base.campo.base.Campo;
import it.algos.base.database.sql.DbSql;
import it.algos.base.database.sql.interprete.InterpreteStructSqlBase;

/**
 * Interprete per la manipolazione della struttura di un
 * database Sql standard.
 * <p/>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 8-nov-2004 ore 8.45.03
 */
public class InterpreteStructStandard extends InterpreteStructSqlBase {

    /**
     * Costruttore completo.<br>
     *
     * @param dbSql il database Sql proprietario
     */
    public InterpreteStructStandard(DbSql dbSql) {
        /** rimanda al costruttore della superclasse */
        super(dbSql);
    } /* fine del metodo costruttore completo */


    /**
     * Modifica il tipo di una colonna.
     * <p/>
     * Non implementato in Sql standard.
     *
     * @param tavola il nome della tavola
     * @param colonna il nome della colonna
     * @param keyword la keyword Sql per il tipo della colonna
     *
     * @return true se la colonna e' stata modificata con successo
     */
    public boolean modificaTipoColonna(String tavola, String colonna, String keyword) {
        return true;
    }


    /**
     * Assegna o rimuove la caratteristica NOT NULL da una colonna.
     * <p/>
     * Non implementato in Sql standard.
     *
     * @param campo il campo del quale regolare la colonna
     * @param flag true per assegnare, false per rimuovere
     * la caratteristica NOT NULL
     *
     * @return true se riuscito
     */
    public boolean regolaNotNull(Campo campo, boolean flag) {
        return true;
    }


    /**
     * Elimina la eventuale caratteristica Primary Key dalla tavola.
     * <p/>
     * Non implementato in Sql standard.
     *
     * @param tavola la tavola dalla quale eliminare la caratteristica
     *
     * @return true se riuscito, o se la tavola non aveva Primary Key
     */
    public boolean eliminaPrimaryKey(String tavola) {
        return true;
    }


    /**
     * Elimina un indice.
     * <p/>
     * Non implementato in Sql standard.
     *
     * @param tavola la tavola dalla quale eliminare l'indice
     * @param nomeIndice il nome dell'indice da eliminare
     *
     * @return true se l'indice e' stato eliminato correttamente
     */
    public boolean eliminaIndice(String tavola, String nomeIndice) {
        return true;
    } /* fine del metodo */


    /**
     * Elimina una Foreign Key.
     * <p/>
     * Non implementato in Sql standard.
     *
     * @param tavola il nome della tavola proprietaria della fkey
     * @param nomeFkey il nome della fKey da eliminare
     *
     * @return true se la fKey e' stata eliminata correttamente
     */
    public boolean eliminaForeignKey(String tavola, String nomeFkey) {
        return true;
    } /* fine del metodo */

    //-------------------------------------------------------------------------
}// fine della classe

//-----------------------------------------------------------------------------

