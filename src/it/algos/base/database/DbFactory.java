/**
 * Title:     SqlFactory
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      21-ott-2004
 */
package it.algos.base.database;

import it.algos.base.errore.Errore;
import it.algos.base.libreria.Libreria;
import it.algos.base.pref.Pref;


/**
 * Factory per la creazione di un darabase.
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 21-ott-2004 ore 11.25.33
 */
public abstract class DbFactory {

    /**
     * Costruttore semplice senza parametri. <br>
     * Indispensabile anche se non viene utilizzato
     * (anche solo per compilazione in sviluppo) <br>
     */
    public DbFactory() {
        /* rimanda al costruttore della superclasse */
        super();
    }// fine del metodo costruttore semplice


    /**
     * Crea un database di default.
     * <p/>
     * Recupera dalle preferenze il tipo di database da utilizzare<br>
     *
     * @return un database
     */
    public static Db crea() {
        /* variabili e costanti locali di lavoro */
        int ord;
        Object ogg;
        int codice = 0;

        try { // prova ad eseguire il codice

            ogg = Pref.DB.tipo.getWrap().getValore();
            if (ogg == null) {
                ogg = Pref.DB.tipo.getWrap().getStandard();
            }// fine del blocco if

            if (ogg instanceof Integer) {
                ord = (Integer)ogg;

                /* traverso tutta la collezione */
                for (Pref.TipoDb pref : Pref.TipoDb.values()) {
                    if (pref.ordinal() == (ord - 1)) {
                        ogg = pref.getValore();
                        codice = Libreria.getInt(ogg);
                        break;
                    }// fine del blocco if
                } // fine del ciclo for-each
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return crea(codice);
    }// fine del metodo


    /**
     * Crea un database.
     * <p/>
     *
     * @param codice codifica del tipo di database da creare (codificato in Db)
     *
     * @return un database
     */
    public static Db crea(int codice) {
        /* variabili e costanti locali di lavoro */
        Db db = null;

        try { // prova ad eseguire il codice
            /*  */
            switch (codice) {
                case Db.MEMORIA:
                    break;
                case Db.SQL_MYSQL:
                    db = it.algos.base.database.sql.implem.mysql.CreaIstanza.crea();
                    break;
                case Db.SQL_ORACLE:
                    break;
                case Db.SQL_POSTGRES:
                    db = it.algos.base.database.sql.implem.postgres.CreaIstanza.crea();
                    break;
                case Db.SQL_STANDARD:
                    db = it.algos.base.database.sql.implem.standard.CreaIstanza.crea();
                    break;
                case Db.SQL_HSQLDB:
                    db = it.algos.base.database.sql.implem.hsqldb.CreaIstanza.crea();
                    break;
                case Db.SQL_SYBASE:
                    break;
                case Db.XML:
                    break;
                default: // caso non definito
                    break;
            } // fine del blocco switch

            /* regola il tipo di database */
            db.setTipoDb(codice);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return db;
    }// fine del metodo

}// fine della classe
