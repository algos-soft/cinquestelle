/**
 * Title:     InterpreteStructHsqldb
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      8-nov-2004
 */
package it.algos.base.database.sql.implem.hsqldb;

import it.algos.base.campo.base.Campo;
import it.algos.base.database.Db;
import it.algos.base.database.connessione.Connessione;
import it.algos.base.database.sql.DbSql;
import it.algos.base.database.sql.interprete.InterpreteStructSqlBase;
import it.algos.base.errore.Errore;
import it.algos.base.modulo.Modulo;

/**
 * Interprete per la manipolazione della struttura di un
 * database HSQLDB.
 * <p/>
 * Attualmente HSQLDB ha le seguenti limitazioni:
 * - Supporta l'attribuzione della caratteristica NOT NULL di una colonna
 * solo in fase di creazione della tavola. Per modificare la caratteristica
 * NOT NULL di una colonna bisogna ricreare l'intera tavola.<br>
 * - Supporta l'attribuzione della caratteristica PRIMARY KEY
 * solo in fase di creazione della tavola. Per modificare la caratteristica
 * PRIMARY KEY bisogna ricreare l'intera tavola.<br>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 8-nov-2004 ore 8.45.03
 */
public class InterpreteStructHsqldb extends InterpreteStructSqlBase {


    /**
     * Costruttore completo.<br>
     *
     * @param dbSql il database Sql proprietario
     */
    public InterpreteStructHsqldb(DbSql dbSql) {
        /** rimanda al costruttore della superclasse */
        super(dbSql);
    } /* fine del metodo costruttore completo */


    /**
     * Modifica il tipo di una colonna.
     * <p/>
     * Per ora HSQLDB non supporta la modifica del tipo di una colonna.
     * Alex 19/11/04
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
     * @param flag true per assegnare, false per rimuovere la caratteristica
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
                comando = this.getDatabaseSql().getNot();
                comando += this.getDatabaseSql().getNullo();
            } else {  // rimuove
                comando = this.getDatabaseSql().getNullo();
            }// fine del blocco if-else

            /* costruisce la stringa Sql */
            stringa += this.getDatabaseSql().getAlterTable();
            stringa += this.getDatabaseSql().getStringaTavola(campo);
            stringa += this.getDatabaseSql().getAlterColumn();
            stringa += this.getDatabaseSql().getStringaCampo(campo);
            stringa += this.getDatabaseSql().getSet();
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
     * Attualmente HSQLDB non supporta l'eliminazione di una Primary Key.
     *
     * @param tavola la tavola dalla quale eliminare la caratteristica
     *
     * @return true se riuscito, o se la tavola non aveva Primary Key
     */
    public boolean eliminaPrimaryKey(String tavola) {
        return true;
    }


    /**
     * Elimina una Foreign Key.
     * <p/>
     * HSQLDB registra automaticamente nomi di tavole e campi in maiuscolo
     *
     * @param tavola il nome della tavola proprietaria della fkey
     * @param nomeFkey il nome della fKey da eliminare
     *
     * @return true se la fKey e' stata eliminata correttamente
     */
    public boolean eliminaForeignKey(String tavola, String nomeFkey) {
        return this.eliminaConstraint(tavola, nomeFkey);
    }


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
        /* variabili e costanti locali di lavoro */
        boolean eseguito = true;
        Connessione conn = null;
        String cmd = "";
        int risultato = 0;

        try { // prova ad eseguire il codice

            /* recupera la connessione */
            conn = this.getDatabaseSql().getConnessioneJDBC();

            /* costuisce il comando */
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
    }


    /**
     * Crea una tavola di un modulo sul database.
     * <p/>
     * La tavola viene creata solo se non e' gia' esistente.<br>
     * <p/>
     * Sovrascritto perché HSQLDB esegue ulteriori operazioni rispetto alla
     * superclasse nel caso di tavole di tipo TEXT.
     *
     * @param modulo il modulo per il quale creare la tavola
     * @param conn la connessione da utilizzare
     *
     * @return true se gia' esistente o creata correttamente
     *         false se ha tentato di crearla ma non e' riuscito.
     */
    public boolean creaTavola(Modulo modulo, Connessione conn) {
        /* variabili e costanti locali di lavoro */
        boolean eseguito = true;
        String tavola;


        try {    // prova ad eseguire il codice

            tavola = modulo.getModello().getTavolaArchivio();
            tavola = fixCase(tavola);

            /* controlla se la tavola e' gia' esistente
             * se non esiste, la crea */
            if (!this.isEsisteTavola(tavola, conn)) {

                eseguito = super.creaTavola(modulo, conn);

                /**
                 * Se si tratta di una TEXT table, invia il secondo
                 * comando per la regolazione delle caratteristiche.
                 */
                if (eseguito) {
                    if (this.getDatabase().getTipoTavole() == Db.TAVOLE_TEXT) {
                        eseguito = this.regolaTextTable(tavola);
                    }// fine del blocco if
                }// fine del blocco if

            }// fine del blocco if


        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return eseguito;
    }


    /**
     * Ritorna la stringa di opzioni creazine tavola.
     * <p/>
     *
     * @param modulo proprietario della tavola
     *
     * @return la stringa di opzioni
     */
    protected String getStringaOpzioniTavola(Modulo modulo) {
        /* variabili e costanti locali di lavoro */
        String stringa = "";

        try { // prova ad eseguire il codice

            /* TEMPORARY TABLE contents for each session (connection) are
            emptied by default at each commit or rollback. The optional
            qualifier ON COMMIT PRESERVE ROWS can be used to keep the rows while
            the session is open. The default is ON COMMIT DELETE ROWS.*/
            if (modulo.getModello().isUsaTavolaTemporanea()) {
                stringa = "ON COMMIT PRESERVE ROWS";
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        return stringa;
    }

//    /**
//     * Crea la tavola con tutti i campi del modulo.
//     * <p/>
//     * Crea i campi contenenti gia' le caratteristiche:
//     * TIPO, PRIMARY KEY e NOT NULL
//     * @param modulo il modulo per il quale creare la tavola
//     * @param conn la connessione da utilizzare
//     * @return true se riuscito
//     */
//    private boolean creaTavolaCompleta(Modulo modulo, Connessione conn) {
//        /* variabili e costanti locali di lavoro */
//        boolean eseguito = false;
//        String tavola;
//        String stringa = "";
//        int risultato;
//
//        try {	// prova ad eseguire il codice
//
//            /* recupera la tavola */
//            tavola = this.getDatabaseSql().getStringaTavola(modulo);
//            tavola = fixCase(tavola);
//
//            /* costruisce la stringa di comando */
//            stringa += this.getDatabaseSql().getCreate();
//            stringa += this.getStringaTipoTavola();
//            stringa += this.getDatabaseHsqldb().getTable();
//            stringa += tavola;
//            stringa += this.getDatabaseSql().getParentesiAperta();
//            stringa += this.getColumnDefinitions(modulo);
//            stringa += this.getDatabaseSql().getParentesiChiusa();
//
//            /* invia il comando al database e recupera il risultato */
//            risultato = this.getDatabaseSql().esegueUpdate(stringa, conn);
//
//            /* contolla il risultato */
//            eseguito = false;
//            if (risultato != -1) {
//                /* regola il valore di ritorno */
//                eseguito = true;
//            } /* fine del blocco if */
//
//            /**
//             * Se si tratta di una TEXT table, invia il secondo
//             * comando per la regolazione delle caratteristiche.
//             */
//            if (eseguito) {
//                if (this.getDatabase().getTipoTavole() == Db.TAVOLE_TEXT) {
//                    eseguito = this.regolaTextTable(tavola);
//                }// fine del blocco if
//            }// fine del blocco if
//
//        } catch (Exception unErrore) {	// intercetta l'errore
//            Errore.crea(unErrore);
//        } // fine del blocco try-catch
//
//        /* valore di ritorno */
//        return eseguito;
//    }


    /**
     * Effettua le regolazioni alla creazione di una TEXT table.
     * <p/>
     * Per creare una text table occorre inviare un secondo comando:<br>
     * SET TABLE <tablename> SOURCE <quoted_filename_and_options>
     *
     * @param tavola il nome della tavola
     *
     * @return true se riuscito
     */
    private boolean regolaTextTable(String tavola) {
        /* variabili e costanti locali di lavoro */
        boolean eseguito = false;
        Connessione conn = null;
        String cmd = "";
        int risultato = 0;

        try {    // prova ad eseguire il codice

            /* recupera la connessione */
            conn = this.getDatabaseSql().getConnessioneJDBC();

            cmd += this.getDatabaseSql().getSet();
            cmd += this.getDatabaseHsqldb().getTable();
            cmd += fixCase(tavola);
            cmd += this.getDatabaseHsqldb().getSource();
            cmd += '"';
            cmd += conn.getDatiConnessione().getNomeDatabase();
            cmd += ".text";
            cmd += ";";
            cmd += "fs=";
            cmd += this.getDatabase().getSepText();
            cmd += '"';

            /* invia il comando al database e recupera il risultato */
            risultato = this.getDatabaseSql().esegueUpdate(cmd, conn);

            /* contolla il risultato */
            eseguito = false;
            if (risultato != -1) {
                /* regola il valore di ritorno */
                eseguito = true;
            } /* fine del blocco if */

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return eseguito;
    }

//    /**
//     * Ritorna la stringa relativa alla definizione delle colonne.
//     * <p/>
//     * @param modulo il modulo di riferimento
//     * @return la stringa di definizione di tutte le colonne
//     */
//    private String getColumnDefinitions(Modulo modulo) {
//        /* variabili e costanti locali di lavoro */
//        String stringa = "";
//        Modello modello = null;
//        HashMap campi = null;
//        ArrayList listaCampi = null;
//        Campo campo = null;
//
//        try {	// prova ad eseguire il codice
//            modello = modulo.getModello();
//            campi = modello.getCampiFisici();
//            listaCampi = Libreria.hashMapToArrayList(campi);
//            for (int k = 0; k < listaCampi.size(); k++) {
//                campo = (Campo)listaCampi.get(k);
//                if (k > 0) {
//                    stringa += this.getDatabaseSql().getSeparatoreCampi();
//                }// fine del blocco if
//                stringa += this.getColumnDefinition(campo);
//            } // fine del ciclo for
//
//        } catch (Exception unErrore) {	// intercetta l'errore
//            Errore.crea(unErrore);
//        } // fine del blocco try-catch
//
//        /* valore di ritorno */
//        return stringa;
//    }

//    /**
//     * Ritorna la stringa relativa alla definizione di una colonna.
//     * <p/>
//     * @param campo il campo di riferimento
//     * @return la stringa di definizione della colonna
//     */
//    private String getColumnDefinition(Campo campo) {
//        /* variabili e costanti locali di lavoro */
//        String stringa = "";
//        String colonna = "";
//        String keyword = "";
//        String notNull = "";
//        String primaryKey = "";
//
//        try {	// prova ad eseguire il codice
//
//            /* acquisisce i dati dal campo */
//            colonna = this.getDatabaseSql().getStringaCampo(campo);
//            colonna = fixCase(colonna);
//            keyword = this.getDatabaseSql().getKeywordSql(campo);
//            if (!campo.getCampoDB().isAccettaValoreNullo()) {
//                notNull = this.getDatabaseSql().getNot();
//                notNull += this.getDatabaseSql().getNullo();
//            }// fine del blocco if
//            if (campo.getCampoDB().isChiavePrimaria()) {
//                primaryKey = this.getDatabaseSql().getPrimaryKey();
//            }// fine del blocco if
//
//            /* crea la stringa di definizione della colonna */
//            stringa += colonna;
//            stringa += keyword;
//            stringa += notNull;
//            stringa += primaryKey;
//
//        } catch (Exception unErrore) {	// intercetta l'errore
//            Errore.crea(unErrore);
//        } // fine del blocco try-catch
//
//        /* valore di ritorno */
//        return stringa;
//    }


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
            stringa += this.getDatabaseHsqldb().getDropConstraint();
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
            cmd += "SET REFERENTIAL_INTEGRITY ";
            if (flag) {
                cmd += "TRUE";
            } else {
                cmd += "FALSE";
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
     * Ritorna il database HSQLDB proprietario dell'Interprete.
     * <p/>
     *
     * @return il database HSQLDB proprietario
     */
    private DbSqlHsqldb getDatabaseHsqldb() {
        return (DbSqlHsqldb)this.getDatabaseSql();
    }

    //-------------------------------------------------------------------------
}// fine della classe


