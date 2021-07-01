/**
 * Title:     InterpreteStructMySql
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      8-nov-2004
 */
package it.algos.base.database.sql.implem.mysql;

import it.algos.base.campo.base.Campo;
import it.algos.base.database.connessione.Connessione;
import it.algos.base.database.sql.DbSql;
import it.algos.base.database.sql.interprete.InterpreteStructSqlBase;
import it.algos.base.database.sql.tipodati.TipoDatiSql;
import it.algos.base.errore.Errore;
import it.algos.base.modulo.Modulo;

import java.sql.Types;
import java.util.ArrayList;

/**
 * Interprete per la manipolazione della struttura di un
 * database MySql.
 * <p/>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 8-nov-2004 ore 8.45.03
 */
public class InterpreteStructMySql extends InterpreteStructSqlBase {


    /**
     * Costruttore completo.<br>
     *
     * @param dbSql il database Sql proprietario
     */
    public InterpreteStructMySql(DbSql dbSql) {
        /** rimanda al costruttore della superclasse */
        super(dbSql);
    } /* fine del metodo costruttore completo */


    /**
     * Ritorna la stringa di opzioni di creazione tavola.
     * <p/>
     *
     * @param modulo proprietario della tavola
     *
     * @return la stringa di opzioni
     */
    protected String getStringaOpzioniTavola(Modulo modulo) {
        /* variabili e costanti locali di lavoro */
        String stringa = "";

        try {    // prova ad eseguire il codice

            /* MySql deve creare tavole con storage engine InnoDb per il
             * supporto delle transazioni e delle foreign keys.
             * Aggiunge il tipo di storage engine InnoDb */
            stringa += this.getDatabaseMySql().getEngine();
            stringa += this.getDatabaseSql().getUguale();
            stringa += this.getDatabaseMySql().getEngineInnoDb();
            stringa += this.getDatabaseMySql().getOpzioneTableUTF8();

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return stringa;
    }


    /**
     * Modifica il tipo di una colonna.
     * <p/>
     *
     * @param tavola il nome della tavola
     * @param colonna il nome della colonna
     * @param keyword la keyword Sql per il tipo della colonna
     *
     * @return true se la colonna e' stata modificata con successo
     */
    public boolean modificaTipoColonna(String tavola, String colonna, String keyword) {
        /* variabili e costanti locali di lavoro */
        boolean eseguito = true;
        Connessione conn = null;
        String stringa = "";
        int risultato = 0;

        try { // prova ad eseguire il codice

            /* recupera la connessione */
            conn = this.getDatabaseSql().getConnessioneJDBC();

            /* costruisce la stringa Sql */
            stringa = this.getDatabaseSql().getAlterTable();
            stringa += fixCase(tavola);
            stringa += this.getDatabaseMySql().getModify();
            stringa += fixCase(colonna);
            stringa += " ";
            stringa += keyword;

            /* invia il comando al database e controlla il risultato */
            risultato = this.getDatabaseSql().esegueUpdate(stringa, conn);
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
     * Assegna o rimuove la caratteristica NOT NULL da una colonna.
     * <p/>
     * Sovrascrive il metodo della superclasse.
     * <p/>
     * Per cambiare la caratteristica NOT NULL, in MySQL occorre
     * inviare un comando di ridefinizione della colonna
     * lasciando il nome e il tipo invariato, e cambiare
     * solo l'opzione NULL/NOT NULL
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
        TipoDatiSql tipoDati = null;
        String stringa = "";
        int risultato = 0;

        try { // prova ad eseguire il codice

            /* recupera la connessione */
            conn = this.getDatabaseSql().getConnessioneJDBC();

            /* determina il comando Sql da inviare */
            if (flag) { // assegna
                comando = this.getDatabaseMySql().getOpzioneNotNull();
            } else {    // rimuovi
                comando = this.getDatabaseMySql().getOpzioneNull();
            } /* fine del blocco if */

            /* recupera il tipo dati Sql del campo */
            tipoDati = (TipoDatiSql)this.getDatabaseSql().getTipoDati(campo);

            /* costruisce la stringa Sql */
            stringa += this.getDatabaseSql().getAlterTable();
            stringa += this.getDatabaseSql().getStringaTavola(campo);
            stringa += this.getDatabaseMySql().getChange();
            stringa += this.getDatabaseSql().getStringaCampo(campo);
            stringa += " ";
            stringa += this.getDatabaseSql().getStringaCampo(campo);
            stringa += tipoDati.stringaSqlTipo();
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
     * Elimina la eventuale caratteristica Primary Key dalla tavola.
     * <p/>
     *
     * @param tavola la tavola dalla quale eliminare la caratteristica
     *
     * @return true se riuscito, o se la tavola non aveva Primary Key
     */
    public boolean eliminaPrimaryKey(String tavola) {
        /* variabili e costanti locali di lavoro */
        boolean eseguito = true;
        Connessione conn = null;
        ArrayList risposta = null;
        String cmd = "";
        int risultato = 0;

        try { // prova ad eseguire il codice

            /* recupera la connessione */
            conn = this.getDatabaseSql().getConnessioneJDBC();

            /* recupera la caratteristica dal DB */
            risposta = this.getInfoPrimaryKey(tavola);

            /* controllo se esiste una pkey per la tavola sul DB */
            if (risposta.size() > 0) {

                /* costruisce il comando */
                cmd += this.getDatabaseSql().getAlterTable();
                cmd += fixCase(tavola);
                cmd += this.getDatabaseSql().getDrop();
                cmd += this.getDatabaseSql().getPrimaryKey();

                /* invia il comando e controlla il risultato */
                risultato = this.getDatabaseSql().esegueUpdate(cmd, conn);
                if (risultato == -1) {
                    eseguito = false;
                } /* fine del blocco if */

            } /* fine del blocco if */

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
        /* valore di ritorno */
        return eseguito;
    }


    /**
     * Crea un indice su un campo.
     * <p/>
     * Sovrascrive il metodo della superclasse.
     * MySql DEVE fornire la lunghezza per gli indici di tipo TEXT/BLOB
     *
     * @param campo il campo sul quale creare l'indice
     * @param unico true se l'indice deve accettare solo valori unici
     *
     * @return true se l'indice e' stato creato correttamente
     */
    protected boolean creaIndice(Campo campo, boolean unico) {

        /* variabili locali di lavoro*/
        boolean eseguito = true;
        Connessione conn = null;
        String tavola = null;
        String nomeCampo = null;
        int lunghezzaIndice = 0;
        TipoDatiSql tipoDati = null;
        int tipoJdbc = 0;
        String nomeIndice = null;
        String cmd = "";
        int risultato = 0;

        try {    // prova ad eseguire il codice

            /* recupera la connessione */
            conn = this.getDatabaseSql().getConnessioneJDBC();

            /* estrae i dati dal campo */
            tavola = this.getDatabaseSql().getStringaTavola(campo);
            nomeCampo = this.getDatabaseSql().getStringaCampo(campo);
            tipoDati = (TipoDatiSql)this.getDatabaseSql().getTipoDati(campo);
            tipoJdbc = tipoDati.getTipoJdbc();

            /* costruisce il nome per l'indice da creare */
            nomeIndice = tavola + DbSql.UNDERSCORE + nomeCampo;

            /* costruisce il comando */
            cmd = this.getDatabaseSql().getCreate();

            /* aggiunge eventualmente la clausola Unique */
            if (unico) {
                cmd += this.getDatabaseSql().getUnique();
            } /* fine del blocco if */

            cmd += this.getDatabaseSql().getIndex();
            cmd += nomeIndice;
            cmd += this.getDatabaseSql().getOn();
            cmd += tavola;
            cmd += " ";
            cmd += this.getDatabaseSql().getParentesiAperta();
            cmd += nomeCampo;

            /* se si tratta di TEXT o BLOB, fornisce anche la lunghezza */
            if ((tipoJdbc == Types.LONGVARCHAR) || (tipoJdbc == Types.BLOB)) {
                lunghezzaIndice = campo.getCampoDB().getLunghezzaIndice();
                cmd += this.getDatabaseSql().getParentesiAperta();
                cmd += lunghezzaIndice;
                cmd += this.getDatabaseSql().getParentesiChiusa();
            }// fine del blocco if

            cmd += this.getDatabaseSql().getParentesiChiusa();

            /* invia il comando al Db e controlla il risultato */
            risultato = this.getDatabaseSql().esegueUpdate(cmd, conn);
            if (risultato == -1) {
                eseguito = false;
            } /* fine del blocco if */

        } catch (Exception unErrore) {    // intercetta l'errore
            /** mostra il messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        return eseguito;
    } /* fine del metodo */


    /**
     * Elimina un indice.
     * <p/>
     *
     * @param tavola la tavola dalla quale eliminare l'indice
     * @param nomeIndice il nome dell'indice da eliminare
     *
     * @return true se l'indice e' stato eliminato correttamente
     */
    public boolean eliminaIndice(String tavola, String nomeIndice) {

        /* variabili locali di lavoro*/
        boolean eseguito = true;
        Connessione conn = null;
        String cmd = "";
        int risultato = 0;

        try { // prova ad eseguire il codice

            /* recupera la connessione */
            conn = this.getDatabaseSql().getConnessioneJDBC();

            /* costruisce il comando */
            cmd += this.getDatabaseSql().getAlterTable();
            cmd += fixCase(tavola);
            cmd += this.getDatabaseSql().getDrop();
            cmd += this.getDatabaseSql().getIndex();
            cmd += fixCase(nomeIndice);

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
    } /* fine del metodo */


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

        /* variabili locali di lavoro*/
        boolean eseguito = true;
        Connessione conn = null;
        String cmd = "";
        int risultato = 0;

        try { // prova ad eseguire il codice

            /* recupera la connessione */
            conn = this.getDatabaseSql().getConnessioneJDBC();

            /* costruisce il comando */
            cmd += this.getDatabaseSql().getAlterTable();
            cmd += fixCase(tavola);
            cmd += this.getDatabaseSql().getDrop();
            cmd += this.getDatabaseSql().getForeignKey();
            cmd += fixCase(nomeFkey);

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

    } /* fine del metodo */


    /**
     * Attiva o disattiva l'integrità referenziale.
     * <p/>
     *
     * @param flag di attivazione
     *
     * @return true se riuscito
     */
    public boolean setReferentialIntegrity(boolean flag, Connessione conn) {
        /* variabili locali di lavoro*/
        boolean eseguito = true;
        String cmd = "";
        int risultato = 0;

        try { // prova ad eseguire il codice

            /* costruisce il comando */
            cmd += "SET FOREIGN_KEY_CHECKS = ";
            if (flag) {
                cmd += "1";
            } else {
                cmd += "0";
            }// fine del blocco if-else

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
     * Ritorna il database MySQL proprietario dell'Interprete.
     * <p/>
     *
     * @return il database MySQL proprietario
     */
    private DbSqlMySql getDatabaseMySql() {
        return (DbSqlMySql)this.getDatabaseSql();
    }

    //-------------------------------------------------------------------------
}// fine della classe

//-----------------------------------------------------------------------------

