/**
 * Title:     InterpreteStructSql
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      8-nov-2004
 */
package it.algos.base.database.sql.implem.postgres;

import it.algos.base.campo.base.Campo;
import it.algos.base.database.connessione.Connessione;
import it.algos.base.database.sql.DbSql;
import it.algos.base.database.sql.interprete.InterpreteStructSqlBase;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;

import java.util.ArrayList;

/**
 * Interprete per la manipolazione della struttura di un
 * database Postrgesql.
 * <p/>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 8-nov-2004 ore 8.45.03
 */
public class InterpreteStructPostgres extends InterpreteStructSqlBase {


    /**
     * Costruttore completo.<br>
     *
     * @param dbSql il database Sql proprietario
     */
    public InterpreteStructPostgres(DbSql dbSql) {
        /** rimanda al costruttore della superclasse */
        super(dbSql);
    } /* fine del metodo costruttore completo */


    /**
     * Modifica il tipo di una colonna.
     * <p/>
     * Per ora postgresql non fa nulla.<br>
     * dalla versione 8.0 sara' in grado di modificare il tipo
     * di una colonna esistente.<br>
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
     *
     * @param campo il campo del quale regolare la colonna
     * @param flag true per assegnare, false per rimuovere
     * la caratteristica NOT NULL
     *
     * @return true se riuscito
     */
    public boolean regolaNotNull(Campo campo, boolean flag) {
        /* variabili e costanti locali di lavoro */
        boolean riuscito = true;
        Connessione conn = null;
        String comando = null;
        String stringa = "";
        int risultato = 0;

        try { // prova ad eseguire il codice

            /* recupera la connessione */
            conn = this.getDatabaseSql().getConnessioneJDBC();

            /* determina il comando Sql da inviare */
            if (flag) { // assegna
                comando = this.getDatabasePostgres().getSetNotNull();
            } else {    // rimuovi
                comando = this.getDatabasePostgres().getDropNotNull();
            } /* fine del blocco if */

            /* costruisce la stringa Sql */
            stringa += this.getDatabaseSql().getAlterTable();
            stringa += this.getDatabaseSql().getStringaTavola(campo);
            stringa += this.getDatabaseSql().getAlterColumn();
            stringa += this.getDatabaseSql().getStringaCampo(campo);
            stringa += comando;

            /* invia il comando al Db */
            risultato = this.getDatabaseSql().esegueUpdate(stringa, conn);

            /* controlla il risultato */
            if (risultato == -1) {
                riuscito = false;
            } /* fine del blocco if */

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return riuscito;

    }


    /**
     * Restituisce il comando Sql per eliminare una colonna.
     * <p/>
     *
     * @param tavola il nome della tavola
     * @param colonna il nome della colonna da eliminare
     *
     * @return il comando Sql per l'eliminazione della colonna
     */
    protected String stringaEliminaColonna(String tavola, String colonna) {
        /* variabili e costanti locali di lavoro */
        String cmd = "";

        try {    // prova ad eseguire il codice

            cmd = this.getDatabaseSql().getAlterTable();
            cmd += fixCase(tavola);
            cmd += this.getDatabaseSql().getDrop();
            cmd += fixCase(colonna);
            cmd += this.getDatabaseSql().getCascade();

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return cmd;
    }


    /**
     * Elimina la eventuale caratteristica Primary Key dalla tavola.
     * <p/>
     *
     * @param tavola la tavola dalla quale eliminare la caratteristica
     *
     * @return true se riuscito, o se la tavola non aveva Primary Key
     */
    public boolean eliminaPrimaryKey(String tavola) {
        /* variabili e costanti locali di lavoro */
        boolean riuscito = true;
        ArrayList risposta = null;
        ArrayList infoPkey = null;
        String nomePkey = null;

        try { // prova ad eseguire il codice

            /* recupera la caratteristica dal DB */
            risposta = this.getInfoPrimaryKey(tavola);

            /* controllo se esiste una pkey per la tavola sul DB */
            if (risposta.size() > 0) {

                /* recupera il nome della constraint Primary Key */
                infoPkey = (ArrayList)risposta.get(0);
                nomePkey = this.getCaratteristica(infoPkey, DbSql.INFO_PKEY_NOME).toString();

                /* elimina la eventuale pkey della tavola */
                if (Lib.Testo.isValida(nomePkey)) {
                    riuscito = this.eliminaConstraint(tavola, nomePkey);
                }// fine del blocco if

            } /* fine del blocco if */

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
        /* valore di ritorno */
        return riuscito;
    }


    /**
     * Elimina una constraint da una tavola.
     * <p/>
     *
     * @param tavola il nome della tavola
     * @param constraint il nome della constraint da eliminare
     *
     * @return true se la constraint e' stata eliminata correttamente
     */
    private boolean eliminaConstraint(String tavola, String constraint) {

        /* variabili locali di lavoro*/
        boolean eseguito = true;
        Connessione conn = null;
        String stringa = "";
        int risultato = 0;

        try { // prova ad eseguire il codice

            /* recupera la connessione */
            conn = this.getDatabaseSql().getConnessioneJDBC();

            /* costruisce la stringa Sql e la invia al DB */
            stringa += this.getDatabaseSql().getAlterTable();
            stringa += fixCase(tavola);
            stringa += this.getDatabasePostgres().getDropConstraint();
            stringa += fixCase(constraint);

            /* invia il comando e controlla il risultato */
            risultato = this.getDatabaseSql().esegueUpdate(stringa, conn);
            if (risultato == -1) {
                eseguito = false;
            } /* fine del blocco if */
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return eseguito;
    } /* fine del metodo */


    /**
     * Elimina un indice.
     * <p/>
     *
     * @param tavola la tavola dalla quale eliminare l'indice
     * (postgres non la usa)
     * @param nomeIndice il nome dell'indice da eliminare
     *
     * @return true se l'indice e' stato eliminato correttamente
     */
    public boolean eliminaIndice(String tavola, String nomeIndice) {

        /* variabili locali di lavoro*/
        boolean eseguito = true;
        Connessione conn = null;
        String cmd = "";
        int unRisultato = 0;

        try { // prova ad eseguire il codice

            /* recupera la connessione */
            conn = this.getDatabaseSql().getConnessioneJDBC();

            /* costruisce il comando */
            cmd += this.getDatabaseSql().getDrop();
            cmd += this.getDatabaseSql().getIndex();
            cmd += fixCase(nomeIndice);

            /* invia il comando al Db e controlla il risultato */
            unRisultato = this.getDatabaseSql().esegueUpdate(cmd, conn);
            if (unRisultato == -1) {
                eseguito = false;
            } /* fine del blocco if */

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch


        return eseguito;
    } /* fine del metodo */


    /**
     * Crea una Foreign Key.
     * <p/>
     * Sovrascrive il metodo della superclasse.
     *
     * @param tavola il nome della tavola
     * @param colonna il nome della colonna
     * @param riferimento il comando per la definizione del riferimento
     * (cioe' il contenuto della clausola REFERENCES, clausola esclusa)
     */
    protected boolean creaForeignKey(String tavola, String colonna, String riferimento) {

        /* variabili locali di lavoro*/
        boolean eseguito = true;
        Connessione conn = null;
        String nomeConstraint = "";
        String cmd = "";
        int risultato = 0;

        try { // prova ad eseguire il codice

            tavola = fixCase(tavola);
            colonna = fixCase(colonna);
            riferimento = fixCase(riferimento);

            /* recupera la connessione */
            conn = this.getDatabaseSql().getConnessioneJDBC();

            /* determina il nome della constraint */
            nomeConstraint = colonna + this.getDatabaseSql().SUFFISSO_DEFAULT_FKEY;

            /* costruisce il comando */
            cmd += this.getDatabaseSql().getAlterTable();
            cmd += fixCase(tavola);
            cmd += this.getDatabaseSql().getAdd();
            cmd += this.getDatabaseSql().getConstraint();
            cmd += nomeConstraint;
            cmd += this.getDatabaseSql().getForeignKey();
            cmd += this.getDatabaseSql().getParentesiAperta();
            cmd += fixCase(colonna);
            cmd += this.getDatabaseSql().getParentesiChiusa();
            cmd += this.getDatabaseSql().getReferences();
            cmd += riferimento;

            /* invia il comando al Db e controlla il risultato */
            risultato = this.getDatabaseSql().esegueUpdate(cmd, conn);
            if (risultato == -1) {
                eseguito = false;
            } /* fine del blocco if */

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return eseguito;
    }


    /**
     * Elimina una Foreign Key.
     * <p/>
     *
     * @param tavola il nome della tavola proprietaria della fkey
     * @param nomeFkey il nome della fKey da eliminare
     *
     * @return true se la fKey e' stata eliminata correttamente
     */
    public boolean eliminaForeignKey(String tavola, String nomeFkey) {
        return this.eliminaConstraint(tavola, nomeFkey);
    } /* fine del metodo */


    /**
     * Attiva o disattiva l'integrità referenziale.
     * <p/>
     * PostgreSQL does not yet have SQL-syntax commands to change
     * actions, turn on/off referential integrity, etc.
     * Alex 05-02-2007
     *
     * @param flag di attivazione
     *
     * @return true se riuscito
     */
    public boolean setReferentialIntegrity(boolean flag, Connessione conn) {
        return false;
    }


    /**
     * Ritorna il database Postgres proprietario dell'Interprete.
     * <p/>
     *
     * @return il database Postgres proprietario
     */
    private DbSqlPostgres getDatabasePostgres() {
        return (DbSqlPostgres)this.getDatabaseSql();
    }

    //-------------------------------------------------------------------------
}// fine della classe

//-----------------------------------------------------------------------------

