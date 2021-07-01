/**
 * Title:     InterpreteQmSql
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      19-ott-2004
 */
package it.algos.base.database.sql.interprete;

import it.algos.base.campo.base.Campo;
import it.algos.base.database.sql.DbSql;
import it.algos.base.database.sql.tipodati.TipoDatiSql;
import it.algos.base.database.tipodati.TipoDati;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.query.Query;
import it.algos.base.query.campi.CampoQuery;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.query.modifica.QueryModifica;

import java.util.ArrayList;

/**
 * Interprete di una Query di tipo INSERT/UPDATE/DELETE per un database Sql generico.
 * </p>
 * Trasforma una Query nel corrispondente comando INSERT/UPDATE/DELETE Sql.
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 19-ott-2004 ore 12.30.47
 */
public abstract class InterpreteQmSql extends InterpreteSqlBase {


    /**
     * Costruttore completo.
     * <p/>
     *
     * @param dbSql il database Sql proprietario
     */
    public InterpreteQmSql(DbSql dbSql) {
        /** rimanda al costruttore della superclasse */
        super(dbSql);
    } /* fine del metodo costruttore completo */


    /**
     * Ritorna la stringa Sql per effettuare una data query.
     * <p/>
     *
     * @param q la Query di tipo QueryModifica
     * per la quale costruire la INSERT/UPDATE
     *
     * @return la stringa Sql
     */
    public String stringaSql(Query q) {
        /* variabili e costanti locali di lavoro */
        String stringa = "";
        boolean continua = false;
        QueryModifica qm = null;

        try {    // prova ad eseguire il codice

            /*
             * controlla che la Query non sia nulla e
             * sia di tipo QueryModifica
             */
            if (q != null) {
                if (q instanceof QueryModifica) {
                    qm = (QueryModifica)q;
                    continua = true;
                } else {
                    throw new Exception("La Query non e' di tipo QueryModifica");
                }// fine del blocco if-else
            }// fine del blocco if

            /* procede alla costruzione della stringa */
            if (continua) {

                /* regola il riferimento alla query per la classe */
                this.setQuery(qm);

                /* costruzione delle varie sezioni del comando */
                stringa += sezioneComando();
                stringa += sezioneTavola();
                stringa += sezioneCampi();
                stringa += sezioneFiltro();

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
    protected String sezioneComando() {
        /* variabili e costanti locali di lavoro */
        String stringa = "";
        QueryModifica query;

        try { // prova ad eseguire il codice
            /* aggiunge il comando */
            query = this.getQueryModifica();
            switch (query.getTipoQuery()) {
                case Query.TIPO_DELETE:
                    stringa += getDatabaseSql().getDelete();
                    stringa += getDatabaseSql().getFrom();
                    break;
                case Query.TIPO_INSERT:
                    stringa += getDatabaseSql().getInsert();
                    break;
                case Query.TIPO_UPDATE:
                    stringa += getDatabaseSql().getUpdate();
                    break;
                default: // caso non definito
                    break;
            } // fine del blocco switch
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return stringa;
    }


    /**
     * Ritorna la stringa corrispondente alla sezione Tavola.
     * <p/>
     * MySql supporta le query di modifica in relazione con tavole
     * esterne tramite il comando JOIN
     *
     * @return la stringa con il comando e la tavola
     */
    protected String sezioneTavola() {
        /* variabili e costanti locali di lavoro */
        String stringa = "";
        String stringaJoin = "";

        try { // prova ad eseguire il codice

            stringa += this.getTavolaBase();

            /* se si riferisce a tavole esterne in relazione, usa la
            * apposita sintassi (E101-04 Searched DELETE statement)*/
            stringaJoin = this.stringaJoin();
            if (Lib.Testo.isValida(stringaJoin)) {
                stringa += getDatabaseSql().getUsing();
                stringa += this.getTavolaBase();
                stringa += stringaJoin;
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return stringa;
    }


    /**
     * Ritorna la stringa corrispondente alla sezione Campi.
     * <p/>
     *
     * @return la stringa con comando, campi e valori
     */
    private String sezioneCampi() {
        /* variabili e costanti locali di lavoro */
        String stringa = "";

        try { // prova ad eseguire il codice

            /* invoca il metodo delegato in funzione del tipo di query */
            switch (this.getQueryModifica().getTipoQuery()) {
                case Query.TIPO_DELETE:
                    // la DELETE non usa la sezione campi
                    break;
                case Query.TIPO_INSERT:
                    stringa += stringaCampiInsert();
                    break;
                case Query.TIPO_UPDATE:
                    stringa += stringaCampiUpdate();
                    break;
                default: // caso non definito
                    break;
            } // fine del blocco switch

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
     * Ritorna la stringa corrispondente alla sezione Filtro.
     * <p/>
     *
     * @return la stringa con comando, campi e valori
     */
    private String sezioneFiltro() {
        /* variabili e costanti locali di lavoro */
        String stringa = "";
        Filtro filtro = null;

        try { // prova ad eseguire il codice

            /* recupera il filtro */
            filtro = this.getQueryModifica().getFiltro();

            /* invoca il metodo delegato in funzione del tipo di query */
            switch (this.getQueryModifica().getTipoQuery()) {
                case Query.TIPO_DELETE:
                    /* delega alla superclasse la costruzione della stringa */
                    stringa += this.stringaFiltro(filtro);
                    break;
                case Query.TIPO_INSERT:
                    // la INSERT non usa la sezione filtro
                    break;
                case Query.TIPO_UPDATE:
                    /* delega alla superclasse la costruzione della stringa */
                    stringa += this.stringaFiltro(filtro);
                    break;
                default: // caso non definito
                    break;
            } // fine del blocco switch

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
     * Costruisce la stringa Sql relativa
     * alla sezione campi di una QueryModifica
     * di tipo INSERT
     * <p/>
     *
     * @return la stringa Sql
     */
    private String stringaCampiInsert() {

        /* variabili e costanti locali di lavoro */
        String stringa = "";
        ArrayList elementi = null;
        CampoQuery cq = null;
        Campo campo = null;
        Object valore = null;
        String stringaVal = null;

        try { // prova ad eseguire il codice

            /* recupera la lista degli elementi */
            elementi = this.getQuery().getListaElementi();

            /*
             * Costruisce nella superclasse la parte relativa
             * ai campi della Query e la racchiude tra parentesi
             * I nomi dei campi non sono qualificati
             */
            stringa += this.getDatabaseSql().getParentesiAperta();
            stringa += this.stringaCampi(elementi, false);
            stringa += this.getDatabaseSql().getParentesiChiusa();

            /*
             * Costruisce in questa classe la parte relativa
             * ai valori della Query
             */
            stringa += "\n";
            stringa += this.getDatabaseSql().getValues();
            stringa += this.getDatabaseSql().getParentesiAperta();

            for (int k = 0; k < elementi.size(); k++) {

                /* recupera l'elemento */
                cq = (CampoQuery)elementi.get(k);

                /* recupera il campo */
                campo = cq.getCampo();

                /* recupera il valore di database per il campo */
                valore = cq.getValoreDb();

                /* recupera la stringa Sql corrispondente al valore */
                stringaVal = this.stringaValoreCampo(campo, valore);

                /* aggiunge eventualmente la virgola */
                if (k > 0) {
                    stringa += this.getDatabaseSql().getSeparatoreValori();
                }// fine del blocco if

                /* aggiunge il valore alla stringa finale */
                stringa += stringaVal;
            } // fine del ciclo for

            stringa += this.getDatabaseSql().getParentesiChiusa();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return stringa;
    } /* fine del metodo */


    /**
     * Costruisce la stringa Sql relativa
     * alla sezione campi di una QueryModifica
     * di tipo UPDATE
     * <p/>
     *
     * @return la stringa Sql
     */
    private String stringaCampiUpdate() {

        /* variabili e costanti locali di lavoro */
        String stringa = "";
        ArrayList elementi = null;
        CampoQuery cq = null;
        Campo campo = null;
        Object valore = null;
        String stringaVal = null;

        try { // prova ad eseguire il codice

            /* recupera la lista degli elementi */
            elementi = this.getQuery().getListaElementi();

            /* aggiunge il comando SET */
            stringa += this.getDatabaseSql().getSet();

            /* spazzola gli elementi */
            for (int k = 0; k < elementi.size(); k++) {

                /* recupera l'elemento */
                cq = (CampoQuery)elementi.get(k);

                /* recupera il campo */
                campo = cq.getCampo();

                /* recupera il valore di database per il campo */
                valore = cq.getValoreDb();

                /* recupera la stringa Sql corrispondente al valore */
                stringaVal = this.stringaValoreCampo(campo, valore);

                /* aggiunge eventualmente la virgola */
                if (k > 0) {
                    stringa += this.getDatabaseSql().getSeparatoreValori();
                }// fine del blocco if

                stringa += this.getDatabaseSql().getStringaCampo(campo);
                stringa += this.getDatabaseSql().getUguale();
                stringa += stringaVal;

            } // fine del ciclo for

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return stringa;
    } /* fine del metodo */


    /**
     * Ritorna una stringa Sql corrispondente a un valore per un campo.
     * <p/>
     *
     * @param campo il campo
     * @param valore il valore da convertire
     *
     * @return la stringa Sql relativa al valore
     */
    private String stringaValoreCampo(Campo campo, Object valore) {
        /* variabili e costanti locali di lavoro */
        String stringa = "";
        TipoDati tipoDati = null;
        TipoDatiSql tipoDatiSql = null;


        try {    // prova ad eseguire il codice

            /* recupera il tipo dati che gestisce il campo */
            tipoDati = this.getDatabase().getTipoDati(campo);

            /* effettua il casting a tipo Sql */
            tipoDatiSql = (TipoDatiSql)tipoDati;

            /* chiede al tipo dati Sql la stringa relativa al valore */
            stringa = tipoDatiSql.stringaValore(valore);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return stringa;
    }

//    /**
//     * Ritorna la stringa Sql corrispondente alla Tavola.
//     * <p/>
//     *
//     * @return la stringa per la Tavola
//     */
//    private String getTavolaBase() {
//        return this.getQuery().getModulo().getModello().getTavolaArchivio();
//    }


    /**
     * Ritorna la QueryModifica di riferimento.
     * <p/>
     *
     * @return la QueryModifica
     */
    protected QueryModifica getQueryModifica() {
        /* variabili e costanti locali di lavoro */
        QueryModifica qm = null;

        try {    // prova ad eseguire il codice
            qm = (QueryModifica)this.getQuery();
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return qm;
    }

    //-------------------------------------------------------------------------
}// fine della classe it.algos.base.db.DBSelect.java

//-----------------------------------------------------------------------------

