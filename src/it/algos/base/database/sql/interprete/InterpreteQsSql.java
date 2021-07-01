/**
 * Title:     Select
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      19-ott-2004
 */
package it.algos.base.database.sql.interprete;

import it.algos.base.database.sql.DbSql;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.query.Query;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.query.ordine.Ordine;
import it.algos.base.query.selezione.QuerySelezione;

/**
 * Interprete di una Query di tipo SELECT per un database Sql generico.
 * <p/>
 * Trasforma una QuerySelezione nel corrispondente comando SELECT Sql.
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 19-ott-2004 ore 12.30.47
 */
public abstract class InterpreteQsSql extends InterpreteSqlBase {

    /**
     * Costruttore completo.
     * <p/>
     *
     * @param dbSql il database Sql proprietario
     */
    public InterpreteQsSql(DbSql dbSql) {
        /** rimanda al costruttore della superclasse */
        super(dbSql);
    } /* fine del metodo costruttore completo */


    /**
     * Ritorna la stringa Sql per effettuare una data query.
     * <p/>
     *
     * @param q la Query di tipo QuerySelezione
     * per la quale costruire la Select
     *
     * @return la stringa Sql
     */
    public String stringaSql(Query q) {
        /* variabili e costanti locali di lavoro */
        String stringa = "";
        boolean continua = false;
        QuerySelezione qs = null;

        try {    // prova ad eseguire il codice

            /*
             * controlla che la Query non sia nulla e
             * sia di tipo QuerySelezione
             */
            if (q != null) {
                if (q instanceof QuerySelezione) {
                    qs = (QuerySelezione)q;
                    continua = true;
                } else {
                    throw new Exception("La Query non e' di tipo QuerySelezione");
                }// fine del blocco if-else
            }// fine del blocco if

            /* procede alla costruzione della stringa */
            if (continua) {

                /* regola il riferimento alla query per la classe */
                this.setQuery(qs);

                /* costruzione delle varie sezioni del comando */
                stringa += sezioneComando();
                stringa += sezioneCampi();
                stringa += "\n";
                stringa += this.getDatabaseSql().getFrom();
                stringa += sezioneTavola();
                stringa += sezioneFiltro();
                stringa += sezioneOrdine();

                /* rimuove il riferimento alla query per la classe */
                this.setQuery(null);

            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return stringa;
    }


    /**
     * Ritorna la stringa corrispondente alla sezione del comando.
     * <p/>
     *
     * @return la stringa del comando SQL
     */
    private String sezioneComando() {
        /* variabili e costanti locali di lavoro */
        String stringa = "";
        QuerySelezione query;

        try { // prova ad eseguire il codice
            stringa += getDatabaseSql().getSelect();
            query = this.getQuerySelezione();
            if (query.isValoriDistinti()) {
                stringa += " ";
                stringa += getDatabaseSql().getDistinct();
                stringa += " ";
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return stringa;
    }


    /**
     * Ritorna la stringa corrispondente alla sezione SELECT.
     * <p/>
     *
     * @return la stringa SELECT con i campi
     */
    private String sezioneCampi() {
        /* variabili e costanti locali di lavoro */
        String stringa = "";

        try { // prova ad eseguire il codice
            stringa += stringaCampi();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return stringa;
    }


    /**
     * Ritorna la stringa corrispondente alla sezione FROM.
     * <p/>
     *
     * @return la stringa FROM con la tavola
     */
    private String sezioneTavola() {
        /* variabili e costanti locali di lavoro */
        String stringa = "";

        try { // prova ad eseguire il codice
            stringa = getTavolaBase();
            stringa = this.getDatabaseSql().fixCase(stringa);
            stringa += this.stringaJoin();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return stringa;
    }


    /**
     * Ritorna la stringa corrispondente alla eventuale sezione WHERE.
     * <p/>
     *
     * @return la eventuale stringa WHERE
     */
    private String sezioneFiltro() {
        /* variabili e costanti locali di lavoro */
        String stringa = "";
        Filtro filtro;

        try { // prova ad eseguire il codice

            /* recupera il filtro */
            filtro = this.getQuerySelezione().getFiltro();
            /* delega alla superclasse la costruzione della stringa */
            stringa += this.stringaFiltro(filtro);
            /* aggiunge eventualmente un acapo di separazione */
            if (Lib.Testo.isValida(stringa)) {
                stringa = "\n" + stringa;
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return stringa;
    }


    /**
     * Ritorna la stringa corrispondente alla eventuale sezione ORDER BY.
     * <p/>
     *
     * @return la eventuale stringa ORDER BY
     */
    private String sezioneOrdine() {
        /* variabili e costanti locali di lavoro */
        String stringa = "";
        Ordine ordine;
        InterpreteOrdineSql intOrdine;

        try { // prova ad eseguire il codice

            /* recupera l'interprete filtro di questo Db */
            intOrdine = this.getDatabaseSql().getInterpreteOrdineSql();
            /* recupera l'ordine */
            ordine = this.getQuerySelezione().getOrdine();
            /* delega all'interprete la costruzione della stringa Sql */
            if (ordine != null) {
                stringa += intOrdine.stringaSql(ordine);
                /* aggiunge eventualmente un acapo di separazione */
                if (Lib.Testo.isValida(stringa)) {
                    stringa = "\n" + stringa;
                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return stringa;
    }


    /**
     * Ritorna la stringa Sql corrispondente alla sezione Campi.
     * <p/>
     * Se la query non contiene campi, ritorna *
     * per selezionare tutti i campi
     *
     * @return la stringa per la sezione Campi
     */
    private String stringaCampi() {
        /* variabili e costanti locali di lavoro */
        String stringa = null;

        try {    // prova ad eseguire il codice

            /*
             * Costruisce la stringa relativa ai campi della query
             * Opera nella superclasse (firma del metodo diversa)
             * I nomi dei campi sono qualificati
             */
            stringa = this.stringaCampi(this.getQuery().getListaElementi(), true);

            /*
             * se la stringa e' vuota (non ci sono campi)
             * ritorna ASTERISCO per selezionare tutti i campi
             */
            if (Lib.Testo.isVuota(stringa)) {
                stringa += this.getDatabaseSql().getAsterisco();
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return stringa;
    }


    /**
     * Ritorna la QuerySelezione di riferimento.
     * <p/>
     *
     * @return la QuerySelezione
     */
    private QuerySelezione getQuerySelezione() {
        /* variabili e costanti locali di lavoro */
        QuerySelezione qs = null;

        try {    // prova ad eseguire il codice
            qs = (QuerySelezione)this.getQuery();
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return qs;
    }

    //-------------------------------------------------------------------------
}// fine della classe it.algos.base.db.DBSelect.java

//-----------------------------------------------------------------------------

