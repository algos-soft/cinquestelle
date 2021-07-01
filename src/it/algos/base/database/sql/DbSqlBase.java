/**
 * Title:     DbSqlBase
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      8-ott-2004
 */
package it.algos.base.database.sql;

import it.algos.base.campo.base.Campo;
import it.algos.base.database.DbBase;
import it.algos.base.database.connessione.Connessione;
import it.algos.base.database.connessione.ConnessioneDati;
import it.algos.base.database.connessione.ConnessioneJDBC;
import it.algos.base.database.dati.Dati;
import it.algos.base.database.sql.dati.DatiSql;
import it.algos.base.database.sql.implem.standard.InterpreteFiltroStandard;
import it.algos.base.database.sql.implem.standard.InterpreteOrdineStandard;
import it.algos.base.database.sql.implem.standard.InterpreteQmStandard;
import it.algos.base.database.sql.implem.standard.InterpreteQsStandard;
import it.algos.base.database.sql.implem.standard.InterpreteStructStandard;
import it.algos.base.database.sql.interprete.InterpreteFiltroSql;
import it.algos.base.database.sql.interprete.InterpreteOrdineSql;
import it.algos.base.database.sql.interprete.InterpreteQmSql;
import it.algos.base.database.sql.interprete.InterpreteQsSql;
import it.algos.base.database.sql.interprete.InterpreteStructSqlBase;
import it.algos.base.database.sql.tipodati.TipoDatiSql;
import it.algos.base.database.sql.tipodati.TipoDatiSqlBooleano;
import it.algos.base.database.sql.tipodati.TipoDatiSqlData;
import it.algos.base.database.sql.tipodati.TipoDatiSqlDecimal;
import it.algos.base.database.sql.tipodati.TipoDatiSqlDouble;
import it.algos.base.database.sql.tipodati.TipoDatiSqlIntero;
import it.algos.base.database.sql.tipodati.TipoDatiSqlLink;
import it.algos.base.database.sql.tipodati.TipoDatiSqlLong;
import it.algos.base.database.sql.tipodati.TipoDatiSqlTesto;
import it.algos.base.database.sql.tipodati.TipoDatiSqlTime;
import it.algos.base.database.sql.tipodati.TipoDatiSqlTimestamp;
import it.algos.base.database.sql.util.FunzioneSql;
import it.algos.base.database.sql.util.OperatoreSql;
import it.algos.base.database.tipodati.TipoDati;
import it.algos.base.database.util.Funzione;
import it.algos.base.database.util.Operatore;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.modulo.Modulo;
import it.algos.base.query.Query;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Database SQL generico
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 8-ott-2004 ore 17.16.47
 */
public abstract class DbSqlBase extends DbBase implements DbSql {

    /**
     * Oggetto DatabaseMetaData relativo a questo database
     */
    private DatabaseMetaData metaData;

    /**
     * Mappatura tra le clausole di unione generiche e
     * le corrispondenti clausole di unione Sql
     */
    private HashMap unioni = null;

    /**
     * Mappatura tra gli operatori di filtro generici e i
     * corrispondenti operatori Sql
     * chiave: il codice dell'operatore di filtro generico (da Operatore)
     * valore: l'oggetto operatore Sql corrispondente
     */
    private HashMap operatoriFiltro = null;

    /**
     * Mappatura tra gli operatori di ordine generici e i
     * corrispondenti operatori Sql
     * chiave: il codice dell'operatore di ordine generico (da Operatore)
     * valore: la stringa SQL corrispondente
     */
    private HashMap operatoriOrdine = null;

    /**
     * mappatura tra i tipi Jdbc e il corrispondente
     * tipo di dati del database.
     * chiave: tipoJdbc (int)
     * valore: chiave del tipo dati (int)
     * E' utilizzata per individuare un tipo dati del database
     * in grado di gestire un determinato tipo jdbc
     */
    private HashMap mappaJdbc2Tipi = null;

    /**
     * stringa sql per eseguire il comando SELECT
     */
    protected String select = "";

    /**
     * stringa sql per eseguire il comando DELETE
     */
    protected String delete = "";

    /**
     * stringa sql per eseguire il comando INSERT
     */
    protected String insert = "";

    /**
     * stringa sql per eseguire il comando UPDATE
     */
    protected String update = "";

    /**
     * stringa sql per la parola chiave SET
     */
    protected String set = "";

    /**
     * stringa sql per la parola chiave VALUES
     */
    protected String values = "";

    /**
     * separatore dei campi in un elenco campi sql
     */
    protected String separatoreCampi = "";

    /**
     * separatore dei valori in un elenco valori sql
     */
    protected String separatoreValori = "";

    /**
     * stringa sql per la clausola FROM
     */
    protected String from = "";

    /**
     * stringa sql per la clausola USING
     */
    protected String using = "";


    /**
     * stringa sql per l'asterisco (es. SELECT * FROM...)
     */
    protected String asterisco = "";

    /**
     * stringa sql per la clausola LEFT JOIN
     */
    protected String leftJoin = "";

    /**
     * stringa sql per la clausola ON
     */
    protected String on = "";

    /**
     * stringa sql per la clausola WHERE
     */
    protected String where = "";

    /**
     * stringa sql per la clausola ORDER BY
     */
    protected String orderBy = "";

    /**
     * stringa sql per l'operatore uguale (=)
     */
    protected String uguale = "";

    /**
     * stringa sql per l'operatore diverso (!=)
     */
    protected String diverso = "";

    /**
     * stringa Sql per l'operatore MINORE
     */
    protected String minore = "";

    /**
     * stringa Sql per l'operatore MINORE UGUALE
     */
    protected String minoreUguale = "";

    /**
     * stringa Sql per l'operatore MAGGIORE
     */
    protected String maggiore = "";

    /**
     * stringa Sql per l'operatore MAGGIORE UGUALE
     */
    protected String maggioreUguale = "";

    /**
     * stringa Sql per l'operatore LIKE
     */
    protected String like = "";

    /**
     * stringa Sql per l'operatore IN
     */
    protected String in = "";

    /**
     * stringa Sql per l'operatore di ordinamento Ascendente
     */
    protected String ascendente = "";

    /**
     * stringa Sql per l'operatore di ordinamento Discendente
     */
    protected String discendente = "";

    /**
     * stringa Sql per la funzione COUNT
     */
    protected String count = "";

    /**
     * stringa Sql per la funzione MIN
     */
    protected String min = "";

    /**
     * stringa Sql per la funzione MAX
     */
    protected String max = "";

    /**
     * stringa Sql per la funzione SUM
     */
    protected String sum = "";

    /**
     * stringa Sql per la funzione AVG
     */
    protected String avg = "";

    /**
     * stringa Sql per la funzione LENGTH
     */
    protected String length = "";

    /**
     * stringa Sql per la funzione UPPER
     */
    protected String upper = "";

    /**
     * stringa Sql per la funzione LOWER
     */
    protected String lower = "";

    /**
     * stringa Sql per la funzione DISTINCT
     */
    protected String distinct = "";

    /**
     * stringa Sql per la wildcard nelle ricerche di testo
     */
    protected String wildcard = "";

    /**
     * stringa Sql per la funzione NOT
     */
    protected String not = "";

    /**
     * stringa Sql per la clausola IS
     */
    protected String is = "";

    /**
     * stringa Sql per la clausola NULL
     */
    protected String nullo = "";

    /**
     * keyword Sql per il tipo DATA
     */
    protected String keyData = "";

    /**
     * keyword Sql per il tipo TIMESTAMP
     */
    protected String keyTimestamp = "";

    /**
     * keyword Sql per il tipo TIME
     */
    protected String keyTime = "";

    /**
     * keyword Sql per il tipo INTERO
     */
    protected String keyIntero = "";

    /**
     * keyword Sql per il tipo DECIMAL
     */
    protected String keyDecimal = "";

    /**
     * keyword Sql per il tipo DOUBLE
     */
    protected String keyDouble = "";

    /**
     * keyword Sql per il tipo BIGINT
     */
    protected String keyBigint = "";

    /**
     * keyword Sql per il tipo TESTO
     */
    protected String keyTesto = "";

    /**
     * keyword Sql per il tipo BOOLEANO
     */
    protected String keyBooleano = "";

    /**
     * delimitatore del testo (apice singolo)
     */
    protected String delimTesto = "";

    /**
     * stringa sql per eseguire il comando CREATE TABLE
     */
    protected String createTable = "";

    /**
     * stringa sql per eseguire il comando CREATE TEMPORARY TABLE
     */
    protected String createTempTable = "";

    /**
     * stringa sql per eseguire il comando ALTER TABLE
     */
    protected String alterTable = "";

    /**
     * stringa sql per eseguire il comando DROP TABLE
     */
    protected String dropTable = "";

    /**
     * stringa sql per eseguire il comando ALTER COLUMN
     */
    protected String alterColumn = "";

    /**
     * parola chiave per INDEX
     */
    protected String index = "";

    /**
     * stringa sql per eseguire il comando generico CREATE
     */
    protected String create = "";

    /**
     * stringa sql per eseguire il comando generico ADD
     */
    protected String add = "";

    /**
     * stringa sql per eseguire il comando generico DROP
     */
    protected String drop = "";

    /**
     * stringa sql per la clausola ADD COLUMN del comando ALTER TABLE
     */
    private String addColumn = "";

    /**
     * stringa sql per l'azione referenziale RESTRICT
     */
    protected String restrict = "";

    /**
     * stringa sql per il comportamento drop di tipo CASCADE
     */
    protected String dropCascade = "";

    /**
     * parola chiave per identificare una CONSTRAINT
     */
    protected String constraint = "";

    /**
     * parola chiave per la constraint PRIMARY KEY
     */
    protected String primaryKey = "";

    /**
     * parola chiave per la constraint FOREIGN KEY
     */
    protected String foreignKey = "";

    /**
     * parola chiave per la clausola UNIQUE
     */
    protected String unique = "";

    /**
     * parola chiave per la clausola referenziale REFERENCES
     */
    protected String references = "";

    /**
     * parola chiave per la clausola referenziale ON DELETE
     */
    protected String onDelete = "";

    /**
     * parola chiave per la clausola referenziale ON UPDATE
     */
    protected String onUpdate = "";

    /**
     * parola chiave per l'azione referenziale NO ACTION
     */
    protected String noAction = "";

    /**
     * parola chiave per l'azione referenziale CASCADE
     */
    protected String cascade = "";

    /**
     * parola chiave per l'azione referenziale SET NULL
     */
    protected String setNull = "";

    /**
     * parola chiave per l'azione referenziale SET DEFAULT
     */
    protected String setDefault = "";


    /**
     * Costruttore completo.
     */
    public DbSqlBase() {
        /* rimanda al costruttore della superclasse */
        super();

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo costruttore completo


    /**
     * Regolazioni immediate di riferimenti e variabili.
     * <p/>
     * Metodo chiamato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {

        this.mappaJdbc2Tipi = new HashMap();

        /* prepara la mappatura tipo jdbc - tipo dati del db */
        this.caricaMappaJdbc();

        /* regola le variabili per i comandi */
        this.regolaComandi();


    }// fine del metodo inizia


    /**
     * Regolazioni dinamiche dell'oggetto.
     * <p/>
     * Regola le caratteristiche dinamiche in base alla impostazione
     * corrente delle caratteristiche statiche. <br>
     * Puo' essere chiamato piu' volte.<br>
     * Se l'inizializzazione ha successo imposta il flag inizializzato a true.<br>
     * Il flag puo' essere successivamente modificato dalle sottoclassi se
     * non riescono a portare a termine la propria inizializzazione specifica.<br>
     * <p/>
     * Sovrascritto nelle sottoclassi <br>
     *
     * @return true se l'inizializzazione ha avuto successo <br>
     */
    public boolean inizializza() {

        if (!this.isInizializzato()) {

            /* invoca il metodo sovrascritto della superclasse */
            super.inizializza();

            /* recupera l'oggettoMetaData del database */
            this.recuperaMetaData();

            /* crea la mappa delle clausole di unione Sql */
            this.creaMappaUnioni();

            /* carica le mappe degli operatori Sql */
            this.caricaMappeOperatori();

            /* carica le mappe delle funzioni Sql */
            this.caricaMappaFunzioni();

        }// fine del blocco if

        /* valore di ritorno */
        return true;
    }


    /**
     * Regola i parametri del database.
     * <p/>
     * Acquisisce le informazioni DatabaseMetaData del database <br>
     * Analizza i dati ottenuti <br>
     * Registra le variabili significative nell'oggetto Db <br>
     *
     * @return true se riuscito
     */
    private boolean recuperaMetaData() {
        /* variabili e costanti locali di lavoro */
        boolean continua = true;
        Connessione conn = null;
        DatabaseMetaData md;

        try {    // prova ad eseguire il codice

            /* crea una connessione */
            if (continua) {
                conn = this.creaConnessione();
                continua = conn != null;
            }// fine del blocco if

            /* recupera e registra i metadati */
            if (continua) {
                md = conn.getMetaData();
                this.setMetaData(md);
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return continua;
    }


    /**
     * Crea una nuova connessione a questo database.
     * <p/>
     * Sovrascritto dalle sottoclassi.<br>
     * La connessione viene creata con un oggetto dati che e' una
     * copia dell'oggetto dati connessione del database.<br>
     * Prima di aprire la connessione, il programmatore puo'
     * effettuare ulteriori regolazioni.<br>
     *
     * @return la connessione appena creata
     */
    public Connessione creaConnessione() {
        /* variabili e costanti locali di lavoro */
        Connessione conn = null;
        ConnessioneDati datiClone = null;

        try { // prova ad eseguire il codice

            /* crea una nuova connessione JDBC */
            conn = new ConnessioneJDBC(this);

            /* clona l'oggetto dati connessione del database */
            datiClone = this.getDatiConnessione().clona();

            /* assegna l'oggetto dati clonato alla connessione */
            conn.setDatiConnessione(datiClone);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
        /* valore di ritorno */
        return conn;
    }


    /**
     * Controlla se il database è disponibile all'apertura di connessioni.
     * <p/>
     * Prova ad aprire e chiudere una connessione. <br>
     *
     * @return true se il database è disponibile all'apertura di connessioni.
     */
    public boolean isDisponibileConnessioni() {
        /* variabili e costanti locali di lavoro */
        boolean disponibile = false;
        boolean riuscito;
        Connessione conn;

        conn = this.creaConnessione();
        if (conn != null) {
            try { // prova ad eseguire il codice
                riuscito = conn.open();
                if (riuscito) {
                    conn.close();
                    disponibile = true;
                }// fine del blocco if
            } catch (Exception unErrore) { // intercetta l'errore
                // qui non fa nulla
            }// fine del blocco try-catch

        }// fine del blocco if

        /* valore di ritorno */
        return disponibile;
    }


    /**
     * Riempie la collezione dei tipi di dati per il Db.
     * <p/>
     * Sovrascrive il metodo della superclasse.
     * Riempie la collezione dei tipi archivio con gli oggetti
     * TipoDati supportati dal database.<br>
     * - chiave: classe Java gestita dal tipo archivio
     * - valore: oggetto TipoDati che gestisce la classe
     */
    protected void creaTipiDati() {

        /* variabili e costanti locali di lavoro */
        Integer chiave;
        HashMap tipi;

        try { // prova ad eseguire il codice

            /* recupera la collezione dei tipi gestiti dal database */
            tipi = this.getTipiDati();

            /* aggiunge il tipo Booleano alla collezione */
            chiave = new Integer(TipoDati.TIPO_BOOLEANO);
            tipi.put(chiave, new TipoDatiSqlBooleano(this));

            /* aggiunge il tipo Data alla collezione */
            chiave = new Integer(TipoDati.TIPO_DATA);
            tipi.put(chiave, new TipoDatiSqlData(this));

            /* aggiunge il tipo Decimal alla collezione */
            chiave = new Integer(TipoDati.TIPO_DECIMAL);
            tipi.put(chiave, new TipoDatiSqlDecimal(this));

            /* aggiunge il tipo Double alla collezione */
            chiave = new Integer(TipoDati.TIPO_DOUBLE);
            tipi.put(chiave, new TipoDatiSqlDouble(this));

            /* aggiunge il tipo Intero alla collezione */
            chiave = new Integer(TipoDati.TIPO_INTERO);
            tipi.put(chiave, new TipoDatiSqlIntero(this));

            /* aggiunge il tipo Link alla collezione */
            chiave = new Integer(TipoDati.TIPO_LINK);
            tipi.put(chiave, new TipoDatiSqlLink(this));

            /* aggiunge il tipo Long alla collezione */
            chiave = new Integer(TipoDati.TIPO_LONG);
            tipi.put(chiave, new TipoDatiSqlLong(this));

            /* aggiunge il tipo Testo alla collezione */
            chiave = new Integer(TipoDati.TIPO_TESTO);
            tipi.put(chiave, new TipoDatiSqlTesto(this));

            /* aggiunge il tipo Time alla collezione */
            chiave = new Integer(TipoDati.TIPO_TIME);
            tipi.put(chiave, new TipoDatiSqlTime(this));

            /* aggiunge il tipo Timestamp alla collezione */
            chiave = new Integer(TipoDati.TIPO_TIMESTAMP);
            tipi.put(chiave, new TipoDatiSqlTimestamp(this));


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Carica la mappatura tra i tipi Jdbc e i tipi dati del database.
     * <p/>
     * A ogni tipo JDBC supportato corrisponde un tipo dati del database<br>
     * Chiave: tipo jdbc (integer, da Types)
     * Valore: chiave del tipo del database (Integer, da TipoDati)
     */
    private void caricaMappaJdbc() {

        try {    // prova ad eseguire il codice

            // i tipi JDBC commentati non sono per ora supportati
            // (non hanno un corrispondente tipo Db)

//            this.putTipo(Types.ARRAY, 0);
            this.putTipo(Types.BIGINT, TipoDati.TIPO_LONG);
//            this.putTipo(Types.BINARY, 0);
            this.putTipo(Types.BIT, TipoDati.TIPO_BOOLEANO);
//            this.putTipo(Types.BLOB, 0);
            this.putTipo(Types.BOOLEAN, TipoDati.TIPO_BOOLEANO);
            this.putTipo(Types.CHAR, TipoDati.TIPO_TESTO);
//            this.putTipo(Types.CLOB, 0);
//            this.putTipo(Types.DATALINK, 0);
            this.putTipo(Types.DATE, TipoDati.TIPO_DATA);
            this.putTipo(Types.DECIMAL, TipoDati.TIPO_DECIMAL);
//            this.putTipo(Types.DISTINCT, 0);
            this.putTipo(Types.DOUBLE, TipoDati.TIPO_DOUBLE);
            this.putTipo(Types.FLOAT, TipoDati.TIPO_DECIMAL);
            this.putTipo(Types.INTEGER, TipoDati.TIPO_INTERO);
//            this.putTipo(Types.JAVA_OBJECT, 0);
//            this.putTipo(Types.LONGVARBINARY, 0);
            this.putTipo(Types.LONGVARCHAR, TipoDati.TIPO_TESTO);
//            this.putTipo(Types.NULL, 0);
            this.putTipo(Types.NUMERIC, TipoDati.TIPO_DOUBLE);
//            this.putTipo(Types.OTHER, 0);
            this.putTipo(Types.REAL, TipoDati.TIPO_DOUBLE);
//            this.putTipo(Types.REF, 0);
//            this.putTipo(Types.SMALLINT, 0);
//            this.putTipo(Types.STRUCT, 0);
            this.putTipo(Types.TIME, TipoDati.TIPO_TIME);
            this.putTipo(Types.TIMESTAMP, TipoDati.TIPO_TIMESTAMP);
            this.putTipo(Types.TINYINT, TipoDati.TIPO_INTERO);
//            this.putTipo(Types.VARBINARY, 0);
            this.putTipo(Types.VARCHAR, TipoDati.TIPO_TESTO);
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Aggiunge una riga alla mappa tipo jdbc.
     * <p/>
     *
     * @param tipoJdbc il codice del tipo jdbc (da Types)
     * @param tipoDb il codice del tipo Db (da TipoDati)
     */
    private void putTipo(int tipoJdbc, int tipoDb) {
        /* variabili e costanti locali di lavoro */
        HashMap mappa = mappaJdbc2Tipi;
        Integer tjInteger = null;
        Integer tdInteger = null;

        tjInteger = new Integer(tipoJdbc);
        tdInteger = new Integer(tipoDb);

        mappa.put(tjInteger, tdInteger);
    }


    /**
     * Regolazione dei comandi Sql.
     * <p/>
     * Sovrascritto dalle sottoclassi
     */
    protected void regolaComandi() {

        /* crea la mappa per le clausole di unione */
        unioni = new HashMap();

        /* crea le mappe per gli operatori e le funzioni */
        operatoriFiltro = new HashMap();
        operatoriOrdine = new HashMap();

        /* regola i comandi specifici Sql*/

        /* sovrascrive alcuni comandi definiti nella superclasse */
        this.unioneAnd = DbSql.UNIONE_AND;
        this.unioneOr = DbSql.UNIONE_OR;
        this.unioneAndNot = DbSql.UNIONE_AND_NOT;
        this.parentesiAperta = DbSql.PARENTESI_APERTA;
        this.parentesiChiusa = DbSql.PARENTESI_CHIUSA;

        /* regola i comandi specifici */
        this.select = DbSql.SELECT;
        this.delete = DbSql.DELETE;
        this.insert = DbSql.INSERT;
        this.update = DbSql.UPDATE;
        this.set = DbSql.SET;
        this.values = DbSql.VALUES;
        this.separatoreCampi = DbSql.SEP_CAMPI;
        this.separatoreValori = DbSql.SEP_VALORI;
        this.from = DbSql.FROM;
        this.using = DbSql.USING;
        this.asterisco = DbSql.ASTERISCO;
        this.leftJoin = DbSql.LEFT_JOIN;
        this.on = DbSql.ON;
        this.where = DbSql.WHERE;
        this.orderBy = DbSql.ORDER_BY;
        this.uguale = DbSql.OP_UGUALE;
        this.diverso = DbSql.OP_DIVERSO;
        this.minore = DbSql.OP_MINORE;
        this.minoreUguale = DbSql.OP_MINORE_UGUALE;
        this.maggiore = DbSql.OP_MAGGIORE;
        this.maggioreUguale = DbSql.OP_MAGGIORE_UGUALE;
        this.like = DbSql.OP_LIKE;
        this.in = DbSql.OP_IN;
        this.ascendente = DbSql.OP_ASCENDENTE;
        this.discendente = DbSql.OP_DISCENDENTE;
        this.count = DbSql.FN_COUNT;
        this.min = DbSql.FN_MIN;
        this.max = DbSql.FN_MAX;
        this.sum = DbSql.FN_SUM;
        this.avg = DbSql.FN_AVG;
        this.length = DbSql.FN_LENGTH;
        this.upper = DbSql.FN_UPPER;
        this.lower = DbSql.FN_LOWER;
        this.distinct = DbSql.FN_DISTINCT;
        this.wildcard = DbSql.WILDCARD;
        this.not = DbSql.NOT;
        this.is = DbSql.IS;
        this.nullo = DbSql.NULLO;
        this.keyData = DbSql.KEY_DATA;
        this.keyTimestamp = DbSql.KEY_TIMESTAMP;
        this.keyTime = DbSql.KEY_TIME;
        this.keyIntero = DbSql.KEY_INTERO;
        this.keyDecimal = DbSql.KEY_DECIMAL;
        this.keyDouble = DbSql.KEY_DOUBLE;
        this.keyBigint = DbSql.KEY_BIGINT;
        this.keyTesto = DbSql.KEY_TESTO;
        this.keyBooleano = DbSql.KEY_BOOLEANO;
        this.delimTesto = DbSql.DELIM_TESTO;
        this.createTable = DbSql.CREATE_TABLE;
        this.createTempTable = DbSql.CREATE_TEMP_TABLE;
        this.alterTable = DbSql.ALTER_TABLE;
        this.dropTable = DbSql.DROP_TABLE;
        this.alterColumn = DbSql.ALTER_COLUMN;
        this.index = DbSql.INDEX;
        this.create = DbSql.CREATE;
        this.add = DbSql.ADD;
        this.drop = DbSql.DROP;
        this.addColumn = DbSql.ADD_COLUMN;
        this.restrict = DbSql.RESTRICT;
        this.dropCascade = DbSql.DROP_CASCADE;
        this.constraint = DbSql.CONSTRAINT;
        this.primaryKey = DbSql.PRIMARY_KEY;
        this.foreignKey = DbSql.FOREIGN_KEY;
        this.unique = DbSql.UNIQUE;
        this.references = DbSql.REFERENCES;
        this.onDelete = DbSql.ON_DELETE;
        this.onUpdate = DbSql.ON_UPDATE;
        this.noAction = DbSql.NO_ACTION;
        this.cascade = DbSql.CASCADE;
        this.setNull = DbSql.SET_NULL;
        this.setDefault = DbSql.SET_DEFAULT;

    }


    /**
     * Regola gli interpreti per il Db.
     * <p/>
     * Crea e assegna al Db le istanze delle classi che dovranno
     * trasformare gli oggetti in comandi Sql.<br>
     * Sovrascritto dalle sottoclassi.
     */
    protected void regolaInterpreti() {

        /* assegno gli interpreti standard al Db */
        this.setInterpreteQs(new InterpreteQsStandard(this));
        this.setInterpreteFiltro(new InterpreteFiltroStandard(this));
        this.setInterpreteOrdine(new InterpreteOrdineStandard(this));
        this.setInterpreteQm(new InterpreteQmStandard(this));
        this.setInterpreteStruct(new InterpreteStructStandard(this));
    }


    /**
     * Carica la hash map unioni con le clausole di unione Sql.
     * <p/>
     * La mappa contiene delle coppie chiave-valore costituite da:
     * codice unione generica - stringa unione Sql
     */
    private void creaMappaUnioni() {
        unioni.put(Operatore.AND, this.getUnioneAnd());
        unioni.put(Operatore.OR, this.getUnioneOr());
        unioni.put(Operatore.AND_NOT, this.getUnioneAndNot());
    }


    /**
     * Carica la mappa degli operatori Sql di filtro e ordine.
     * <p/>
     * Crea un operatore Sql corrispondente a ogni operatore generico
     * e carica la hash map operatori.<br>
     * La mappa contiene delle coppie chiave-valore costituite da:
     * codice operatore generico - oggetto operatore Sql.<br>
     * Questa mappa puo' essere ulteriormente regolata nelle sottoclassi.
     */
    private void caricaMappeOperatori() {
        /* variabili e costanti locali di lavoro */
        Operatore op = null;

        /* OPERATORI DI FILTRO */

        /* Operatore Sql UGUALE */
        op = new OperatoreSql();
        op.setSimbolo(this.getUguale());
        operatoriFiltro.put(Operatore.UGUALE, op);

        /* Operatore Sql DIVERSO */
        op = new OperatoreSql();
        op.setSimbolo(this.getDiverso());
        operatoriFiltro.put(Operatore.DIVERSO, op);

        /* Operatore Sql MINORE */
        op = new OperatoreSql();
        op.setSimbolo(this.getMinore());
        operatoriFiltro.put(Operatore.MINORE, op);

        /* Operatore Sql MINORE_UGUALE */
        op = new OperatoreSql();
        op.setSimbolo(this.getMinoreUguale());
        operatoriFiltro.put(Operatore.MINORE_UGUALE, op);

        /* Operatore Sql MAGGIORE */
        op = new OperatoreSql();
        op.setSimbolo(this.getMaggiore());
        operatoriFiltro.put(Operatore.MAGGIORE, op);

        /* Operatore Sql MAGGIORE_UGUALE */
        op = new OperatoreSql();
        op.setSimbolo(this.getMaggioreUguale());
        operatoriFiltro.put(Operatore.MAGGIORE_UGUALE, op);

        /* Operatore Sql COMINCIA (solo testo) */
        op = new OperatoreSql();
        op.setSimbolo(this.getLike());
        op.setWildcard(this.getWildcard());
        op.setPosizioneWildcard(OperatoreSql.DESTRA);
        operatoriFiltro.put(Operatore.COMINCIA, op);

        /* Operatore Sql CONTIENE (solo testo) */
        op = new OperatoreSql();
        op.setSimbolo(this.getLike());
        op.setWildcard(this.getWildcard());
        op.setPosizioneWildcard(OperatoreSql.ENTRAMBE);
        operatoriFiltro.put(Operatore.CONTIENE, op);

        /* Operatore Sql FINISCE (solo testo) */
        op = new OperatoreSql();
        op.setSimbolo(this.getLike());
        op.setWildcard(this.getWildcard());
        op.setPosizioneWildcard(OperatoreSql.SINISTRA);
        operatoriFiltro.put(Operatore.FINISCE, op);

        /* Operatore Sql MASCHERA (solo testo)
         * e' uguale all'operatore COMINCIA
         * differenziato solo per chiarezza di lettura
         * Usare stringhe con il carattere _ (underscore)
         * per la definizione della maschera */
        op = new OperatoreSql();
        op.setSimbolo(this.getLike());
        op.setWildcard(this.getWildcard());
        op.setPosizioneWildcard(OperatoreSql.DESTRA);
        operatoriFiltro.put(Operatore.MASCHERA, op);

        /* Operatore Sql IN */
        op = new OperatoreSql();
        op.setSimbolo(this.getIn());
        operatoriFiltro.put(Operatore.IN, op);

        /* OPERATORI DI ORDINAMENTO */

        /* Operatore Sql Ascendente */
        operatoriOrdine.put(Operatore.ASCENDENTE, this.getAscendente());
        /* Operatore Sql Discendente */
        operatoriOrdine.put(Operatore.DISCENDENTE, this.getDiscendente());

    }


    /**
     * Carica la mappa delle funzioni Sql.
     * <p/>
     * Crea una funzione Sql corrispondente a ogni funzione generico
     * e carica la hash map funzioni.<br>
     * La mappa contiene delle coppie chiave-valore costituite da:
     * codice funzione generica - oggetto funzione Sql.<br>
     * Questa mappa puo' essere ulteriormente regolata nelle sottoclassi.
     */
    private void caricaMappaFunzioni() {
        /* variabili e costanti locali di lavoro */
        FunzioneSql fn;
        HashMap funzioni = this.getFunzioni();

        /* Funzione Sql COUNT */
        fn = new FunzioneSql();
        fn.setSimbolo(this.getCount());
        funzioni.put(Funzione.COUNT, fn);

        /* Funzione Sql MIN */
        fn = new FunzioneSql();
        fn.setSimbolo(this.getMin());
        funzioni.put(Funzione.MIN, fn);

        /* Funzione Sql MAX */
        fn = new FunzioneSql();
        fn.setSimbolo(this.getMax());
        funzioni.put(Funzione.MAX, fn);

        /* Funzione Sql SUM */
        fn = new FunzioneSql();
        fn.setSimbolo(this.getSum());
        funzioni.put(Funzione.SUM, fn);

        /* Funzione Sql AVG */
        fn = new FunzioneSql();
        fn.setSimbolo(this.getAvg());
        funzioni.put(Funzione.AVG, fn);

        /* Funzione Sql LENGTH */
        fn = new FunzioneSql();
        fn.setSimbolo(this.getLength());
        funzioni.put(Funzione.LENGTH, fn);

        /* Funzione Sql UPPER */
        fn = new FunzioneSql();
        fn.setSimbolo(this.getUpper());
        funzioni.put(Funzione.UPPER, fn);

        /* Funzione Sql LOWER */
        fn = new FunzioneSql();
        fn.setSimbolo(this.getLower());
        funzioni.put(Funzione.LOWER, fn);

        /* Funzione Sql DISTINCT */
        fn = new FunzioneSql();
        fn.setSimbolo(this.getDistinct());
        funzioni.put(Funzione.DISTINCT, fn);

    }


    /**
     * Carica una selezione di record.
     * <p/>
     *
     * @param query informazioni per effettuare la selezione
     * @param conn la connessione sulla quale effettuare la query
     *
     * @return un oggetto dati
     */
    public Dati querySelezione(Query query, Connessione conn) {
        /* variabili e costanti locali di lavoro */
        DatiSql dati = null;
        InterpreteQsSql i;
        String stringaSql;
        ResultSet rs;

        /* invoca il metodo sovrascritto */
        super.querySelezione(query, conn);

        try { // prova ad eseguire il codice

            /* costruisce la stringa Sql per la query */
            i = this.getInterpreteQsSql();
            stringaSql = i.stringaSql(query);

            /* esegue la Query e ottiene un ResultSet */
            rs = this.esegueSelect(stringaSql, conn);

            /* costruisce un oggetto Dati con il ResultSet */
            dati = new DatiSql(this, query);
            dati.setResultSet(rs);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return dati;
    }


    /**
     * Esegue la modifica di uno o piu' record.
     * <p/>
     * La modifica puo' consistere in inserimento di nuovi record
     * o modifica o cancellazione di record esistenti.
     *
     * @param query informazioni per effettuare la modifica
     * @param conn la connessione sulla quale effettuare la query
     *
     * @return il numero di record interessati, -1 se fallito
     */
    public int queryModifica(Query query, Connessione conn) {
        /* variabili e costanti locali di lavoro */
        int risultato = 0;
        InterpreteQmSql i;
        String stringaSql;

        /* invoca il metodo sovrascritto */
        super.queryModifica(query, conn);

        try { // prova ad eseguire il codice

            /* costruisce la stringa Sql per la query */
            i = this.getInterpreteQmSql();
            stringaSql = i.stringaSql(query);

            /* esegue la Query e ottiene un risultato */
            risultato = this.esegueUpdate(stringaSql, conn);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return risultato;
    }


    /**
     * Controlla se una tavola esiste sul database.
     * <p/>
     * La ricerca della tavola e' case-sensitive.
     *
     * @param nomeTavola il nome della tavola da cercare
     * @param conn la connessione da utilizzare
     *
     * @return true se la tavola esiste, altrimenti false
     */
    public boolean isEsisteTavola(String nomeTavola, Connessione conn) {
        /* valore di ritorno */
        return this.getInterpreteStructSql().isEsisteTavola(nomeTavola, conn);
    } /* fine del metodo */


    /**
     * Determina se una data colonna esiste sul database.
     * <p/>
     *
     * @param nomeTavola nome della tavola
     * @param nomeColonna nome della colonna
     * @param conn la connessione da utilizzare
     *
     * @return true se la colonna esiste
     */
    public boolean isEsisteColonna(String nomeTavola, String nomeColonna, Connessione conn) {
        return this.getInterpreteStructSql().isEsisteColonna(nomeTavola, nomeColonna, conn);
    }


    /**
     * Determina se un dato campo eisste sul database.
     * <p/>
     *
     * @param campo il campo da controllare
     * @param conn la connessione da utilizzare per il controllo
     *
     * @return true se il campo esiste
     */
    public boolean isEsisteCampo(Campo campo, Connessione conn) {
        return this.getInterpreteStructSql().isEsisteCampo(campo, conn);
    }


    /**
     * Esegue una Query di selezione sul database e ottiene un ResultSet.
     * <p/>
     *
     * @param stringaSql la stringa di comandi Sql per la Query
     * @param conn la connessione da utilizzare per la Query
     *
     * @return il ResultSet ottenuto
     */
    public ResultSet esegueSelect(String stringaSql, Connessione conn) {
        /* variabili e costanti locali di lavoro */
        ResultSet rs = null;
        Statement stat;
        ConnessioneJDBC connJDBC;
        Connection connection;

        /**
         * Sensibilità del ResultSet.
         * Questo tipo di cursore non è sensibile alle modifiche
         * apportate al database mentre è aperto
         */
        final int tipoRs = ResultSet.TYPE_SCROLL_INSENSITIVE;
//        final int tipoRs = ResultSet.TYPE_SCROLL_SENSITIVE;

        /**
         * Concurrency del ResultSet.
         * E' possibile utilizzare il ResultSet solo per la
         * lettura dei dati
         */
        final int concRs = ResultSet.CONCUR_READ_ONLY;

        try {    // prova ad eseguire il codice

            /* esegue la query */
            try {

                /* verifica se la connessione e' aperta */
                if (!conn.isOpen()) {
                    throw new Exception("La connessione al database non e' aperta.");
                }// fine del blocco if

                /* recupera la connessione JDBC */
                connJDBC = (ConnessioneJDBC)conn;

                /* recupera l'oggetto Connection */
                connection = connJDBC.getConnection();

                /* crea uno Statement */
                stat = connection.createStatement(tipoRs, concRs);

                /* esegue lo statement e recupera il ResultSet */
                rs = stat.executeQuery(stringaSql);

            } catch (SQLException unEccezione) {
                Errore.crea(unEccezione);
            } /* fine del blocco try-catch */

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return rs;
    }

//    /**
//     * Esegue una Query di selezione sul database e ottiene un ResulSet.
//     * <p/>
//     *
//     * @param stringaSql la stringa di comandi Sql per la Query
//     *
//     * @return il ResulSet ottenuto
//     */
//    public ResultSet esegueQuery(String stringaSql, Connessione conn){
//        /* variabili e costanti locali di lavoro */
//        ResultSet risultato=null;
//        Statement stat;
//        ConnessioneJDBC connJDBC;
//        Connection connection;
//
//        try {    // prova ad eseguire il codice
//
//            /* esegue la query */
//            try {
//
//                /* verifica se la connessione e' aperta */
//                if (!conn.isOpen()) {
//                    throw new Exception("La connessione al database non e' aperta.");
//                }// fine del blocco if
//
//                /* recupera la connessione JDBC */
//                connJDBC = (ConnessioneJDBC)conn;
//
//                /* recupera l'oggetto Connection */
//                connection = connJDBC.getConnection();
//
//                /* crea uno Statement */
//                stat = connection.createStatement();
//
//                /* esegue lo statement e recupera valore di ritorno */
//                risultato = stat.executeQuery(stringaSql);
//
//            } catch (SQLException unEccezione) {
//                Errore.crea(unEccezione);
//            } /* fine del blocco try-catch */
//
//        } catch (Exception unErrore) {    // intercetta l'errore
//            Errore.crea(unErrore);
//        } // fine del blocco try-catch
//
//        /* valore di ritorno */
//        return risultato;
//    }


    /**
     * Esegue una Query di modifica sul database e ottiene un risultato.
     * <p/>
     *
     * @param stringaSql la stringa di comandi Sql per la Query
     * @param conn la connessione da utilizzare per la Query
     *
     * @return il risultato ottenuto
     *         - il numero di record interessati alla modifica (INSERT/UPDATE/DELETE)
     *         - 0 per i comandi che non ritornano risultato (DDL)
     *         - -1 se fallito
     */
    public int esegueUpdate(String stringaSql, Connessione conn) {
        /* variabili e costanti locali di lavoro */
        int risultato;
        int fallito = -1;
        Statement stat;
        ConnessioneJDBC connJDBC;
        Connection connection;

        try {    // prova ad eseguire il codice

            /* esegue la query */
            try {

                /* verifica se la connessione e' aperta */
                if (!conn.isOpen()) {
                    throw new Exception("La connessione al database non e' aperta.");
                }// fine del blocco if

                /* recupera la connessione JDBC */
                connJDBC = (ConnessioneJDBC)conn;

                /* recupera l'oggetto Connection */
                connection = connJDBC.getConnection();

                /* crea uno Statement */
                stat = connection.createStatement();

                /* esegue lo statement e recupera valore di ritorno */
                risultato = stat.executeUpdate(stringaSql);

                /* chiude lo statement */
                stat.close();

            } catch (SQLException unEccezione) {
                risultato = fallito;
                Errore.crea(unEccezione);
            } /* fine del blocco try-catch */

        } catch (Exception unErrore) {    // intercetta l'errore
            risultato = fallito;
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return risultato;
    }


    /**
     * Ritorna una stringa che identifica un campo per il database Sql.
     * <p/>
     * La stringa non e' qualificata con il nome della tavola.
     * Sovrascrive il metodo della superclasse.<br>
     *
     * @param campo il campo
     *
     * @return la stringa Sql che identifica il campo
     */
    public String getStringaCampo(Campo campo) {
        /* variabili e costanti locali di lavoro */
        String stringa = null;

        try { // prova ad eseguire il codice
            stringa = super.getStringaCampo(campo);
            stringa = fixCase(stringa);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return stringa;
    }


    /**
     * Ritorna una stringa che identifica un campo per il database.
     * <p/>
     * La stringa e' qualificata con il nome della tavola (tavola.campo)
     * Sovrascrive il metodo della superclasse.<br>
     *
     * @param campo il campo
     *
     * @return una stringa qualificata che identifica il campo
     */
    public String getStringaCampoQualificata(Campo campo) {
        /* variabili e costanti locali di lavoro */
        String stringa = null;
        String stringaTavola;
        String stringaCampo;

        try { // prova ad eseguire il codice
            stringaTavola = campo.getModulo().getModello().getTavolaArchivio();
            stringaCampo = this.getStringaCampo(campo);
            stringa = stringaTavola + "." + stringaCampo;
            stringa = stringa.toLowerCase();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return stringa;
    }


    /**
     * Ritorna una stringa che identifica una tavola per il database.
     * <p/>
     * Sovrascrive il metodo della superclasse.<br>
     *
     * @param campo il campo del quale interessa la tavola
     *
     * @return una stringa che identifica la tavola
     */
    public String getStringaTavola(Campo campo) {
        /* variabili e costanti locali di lavoro */
        String nomeTavola = null;

        try { // prova ad eseguire il codice
            nomeTavola = campo.getModulo().getModello().getTavolaArchivio();
            nomeTavola = fixCase(nomeTavola);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        /* valore di ritorno */
        return nomeTavola;
    }


    /**
     * Ritorna una stringa che identifica una tavola per il database.
     * <p/>
     * La tavola viene recuperata dal fornito.
     * Sovrascrive il metodo della superclasse.<br>
     *
     * @param modulo il modulo dal quale recuperare la tavola
     *
     * @return una stringa che identifica la tavola
     */
    public String getStringaTavola(Modulo modulo) {
        /* valore di ritorno */
        return modulo.getModello().getTavolaArchivio().toLowerCase();
    }


    /**
     * Ritorna la keyword Sql corrispondente al tipo dati di un campo.
     * <p/>
     * La keyword e' usata per costruire il campo sul Db.
     *
     * @param campo il campo
     *
     * @return la keyword Sql per il campo
     */
    public String getKeywordSql(Campo campo) {
        /* variabili e costanti locali di lavoro */
        String keyword = "";
        TipoDatiSql t = null;

        try {    // prova ad eseguire il codice
            t = this.getTipoDatiSql(campo);
            keyword = t.getKeyword();
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return keyword;
    }


    /**
     * Ritorna il tipo dati Jdbc per un dato campo.
     * <p/>
     *
     * @param campo il campo
     *
     * @return il tipo Jdbc Sql per il campo (da java.Sql.Types)
     */
    public int getTipoJdbc(Campo campo) {
        /* variabili e costanti locali di lavoro */
        int tipo = 0;

        try { // prova ad eseguire il codice
            tipo = this.getTipoDatiSql(campo).getTipoJdbc();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return tipo;
    }


    /**
     * Dato un tipo JDBC, ritorna la chiave del corrispondente tipo dati sql
     * nella collezione.
     * <p/>
     *
     * @param tipoJdbc il tipo JDBC da cercare
     *
     * @return la chiave del tipo dati (0 se non trovato)
     */
    public int getChiaveTipoDatiSql(int tipoJdbc) {
        /* variabili e costanti locali di lavoro */
        int chiave = 0;
        HashMap tipiDati = null;
        Set entries = null;
        Iterator i = null;
        Object o = null;
        Map.Entry entry = null;
        TipoDatiSql tdSql = null;
        int tipo = 0;
        Integer chiaveInteger = null;

        try {    // prova ad eseguire il codice
            tipiDati = this.getTipiDati();
            entries = tipiDati.entrySet();
            i = entries.iterator();
            while (i.hasNext()) {
                o = i.next();
                entry = (Map.Entry)o;
                tdSql = (TipoDatiSql)(entry.getValue());
                tipo = tdSql.getTipoJdbc();
                if (tipo == tipoJdbc) {
                    chiaveInteger = (Integer)(entry.getKey());
                    chiave = chiaveInteger.intValue();
                    break;
                }// fine del blocco if
            }

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return chiave;
    }


    /**
     * Ritorna la stringa rappresentante l'Url per una
     * connessione JDBC al database.
     * <p/>
     * Sovrascritto dalle sottoclassi
     *
     * @param conn la connessione per la quale ricavare l'url
     *
     * @return l'Url per la connessione
     */
    public String getStringaUrl(Connessione conn) {
        /* variabili e costanti locali di lavoro */
        String url = "";
        ConnessioneDati dati;
        String proto = "";
        String host = "";
        String porta = "";
        String database = "";
        String s;
        int i;

        try {    // prova ad eseguire il codice

            /* regola la connessione del Db */
            this.setConnessione(conn);

            /* recupera i dati per la connessione */
            dati = this.getConnessioneJDBC().getDatiConnessione();

            /* == regola la sezione Protocollo == */

            /* identificativo del protocollo Jdbc */
            proto += this.getConnessioneJDBC().getJdbc() + ":";

            /* identificativo del subprotocollo */
            s = this.getSubprotocolloJDBC();
            if (Lib.Testo.isValida(s)) {
                proto += s + ":";
            }// fine del blocco if

            /* == regola la sezione Host == */

            /* indirizzo dell'host */
            s = dati.getHost();
            if (Lib.Testo.isValida(s)) {
                host += "//" + s;
            }// fine del blocco if

            /* == regola la sezione Porta == */

            /* numero della porta */
            i = dati.getPorta();
            if (i != 0) {
                porta += ":" + i;
            }// fine del blocco if

            /* == regola la sezione Database == */

            /* nome del database */
            s = dati.getNomeDatabase();
            if (Lib.Testo.isValida(s)) {
                database += "/" + s;
            }// fine del blocco if

            /* == costruisce la stringa completa per l'URL */
            url += proto + host + porta + database;

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return url;
    }


    /**
     * Ritorna il tipo dati Sql del Db in grado di gestire
     * i valori di un dato campo.
     * <p/>
     *
     * @param campo il campo per il quale cercare il corrispondente tipo dati
     *
     * @return il TipoDatiSql del db che gestisce i valori del campo.
     */
    public TipoDatiSql getTipoDatiSql(Campo campo) {
        return (TipoDatiSql)this.getTipoDati(campo);
    }


    /**
     * Ritorna il tipo dati Sql del Db in grado di gestire un dato tipo JDBC.
     * <p/>
     *
     * @param tipo il tipo jdbc da gestire
     *
     * @return il TipoDatiSql del db che gestisce il tipo.
     */
    public TipoDatiSql getTipoDatiDaJdbc(int tipo) {
        /* variabili e costanti locali di lavoro */
        TipoDatiSql tipoOut = null;
        HashMap mappaJdbc = null;
        Integer tipoJdbc = null;
        Integer chiaveTipo = null;
        int chiaveInt = 0;
        Object obj = null;

        try { // prova ad eseguire il codice

            mappaJdbc = this.getMappaJdbc2Tipi();
            tipoJdbc = new Integer(tipo);
            obj = mappaJdbc.get(tipoJdbc);
            if (obj != null) {
                chiaveTipo = (Integer)obj;
                chiaveInt = chiaveTipo.intValue();
                obj = this.getTipoDati(chiaveInt);
                if (obj != null) {
                    tipoOut = (TipoDatiSql)obj;
                }// fine del blocco if
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return tipoOut;
    }


    public InterpreteQsSql getInterpreteQsSql() {
        return (InterpreteQsSql)this.getInterpreteQs();
    }


    public InterpreteFiltroSql getInterpreteFiltroSql() {
        return (InterpreteFiltroSql)this.getInterpreteFiltro();
    }


    public InterpreteOrdineSql getInterpreteOrdineSql() {
        return (InterpreteOrdineSql)this.getInterpreteOrdine();
    }


    public InterpreteQmSql getInterpreteQmSql() {
        return (InterpreteQmSql)this.getInterpreteQm();
    }


    public InterpreteStructSqlBase getInterpreteStructSql() {
        return (InterpreteStructSqlBase)this.getInterpreteStruct();
    }


    /**
     * Ritorna la connessione JDBC al database.
     * <p/>
     *
     * @return un oggetto ConnessioneJDBC
     */
    public ConnessioneJDBC getConnessioneJDBC() {
        /* variabili e costanti locali di lavoro */
        ConnessioneJDBC connJDBC = null;

        try {    // prova ad eseguire il codice
            connJDBC = (ConnessioneJDBC)this.getConnessione();
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return connJDBC;
    }


    /**
     * Ritorna il codice del case con il quale il database
     * tratta e memorizza gli unquoted identifiers (nomi delle
     * tavole, dei campi ecc...)
     * <p/>
     *
     * @return il codice del case
     */
    private identifiersCase getUnquotedIdentifiersCase() {
        /* variabili e costanti locali di lavoro */
        identifiersCase caso = null;
        boolean continua = true;
        DatabaseMetaData md = null;

        try {    // prova ad eseguire il codice

            /* recupera i metadati */
            if (continua) {
                md = this.getMetaData();
                continua = (md != null);
            }// fine del blocco if

            if (continua) {

                if (md.storesLowerCaseIdentifiers()) {
                    caso = identifiersCase.lowercase;
                }// fine del blocco if

                if (md.storesUpperCaseIdentifiers()) {
                    caso = identifiersCase.uppercase;
                }// fine del blocco if

                if (md.storesMixedCaseIdentifiers()) {
                    caso = identifiersCase.mixedcase;
                }// fine del blocco if

            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return caso;
    }


    /**
     * Converte un identificatore nel case supportato dal database.
     * <p/>
     *
     * @param identIn l'identificatore da convertire
     *
     * @return l'identificatore convertito nel case appropriato per il database
     */
    public String fixCase(String identIn) {
        /* variabili e costanti locali di lavoro */
        String identOut = null;
        identifiersCase caso = null;
        try { // prova ad eseguire il codice

            if (identIn != null) {

                /* recupera il codice del case */
                caso = this.getUnquotedIdentifiersCase();

                if (caso != null) {
                    /* converte la stringa */
                    switch (caso) {
                        case lowercase:
                            identOut = identIn.toLowerCase();
                            break;
                        case uppercase:
                            identOut = identIn.toUpperCase();
                            break;
                        case mixedcase:
                            identOut = identIn;
                            break;
                        default: // caso non definito
                            identOut = identIn;
                            break;
                    } // fine del blocco switch
                } else {
                    identOut = identIn;
                }// fine del blocco if-else

            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return identOut;
    }


    /**
     * Ritorna l'oggetto DatabaseMetaData relativo a questo database.
     * <p/>
     *
     * @return l'oggetto DatabaseMetaData del database
     */
    public DatabaseMetaData getMetaData() {
        return metaData;
    }


    private void setMetaData(DatabaseMetaData metaData) {
        this.metaData = metaData;
    }


    /**
     * Ritorna la stringa Sql corrispondente a una data
     * clausola di unione generica.
     * <p/>
     *
     * @param codice il codice della clausola di unione generica (v. Operatore)
     *
     * @return la stringa Sql per la clausola di unione
     */
    public String getUnione(String codice) {
        /* valore di ritorno */
        return (String)unioni.get(codice);
    }


    /**
     * Ritorna l'operatore Sql corrispondente a un dato operatore filtro generico.
     * <p/>
     *
     * @param codice il codice dell'operatore filtro generico (v. Operatore)
     *
     * @return l'oggetto OperatoreSql
     */
    public OperatoreSql getOperatoreFiltro(String codice) {
        /* valore di ritorno */
        return (OperatoreSql)operatoriFiltro.get(codice);
    }


    /**
     * Ritorna la stringa Sql corrispondente a un dato operatore ordine generico.
     * <p/>
     *
     * @param codice il codice dell'operatore ordine generico (v. Operatore)
     *
     * @return la stringa Sql
     */
    public String getOperatoreOrdine(String codice) {
        /* valore di ritorno */
        return (String)operatoriOrdine.get(codice);
    }


    /**
     * Ritorna l'oggetto FunzioneSql corrispondente a una data chiave.
     * <p/>
     *
     * @param codice il codice chiave della funzione generica
     * (da interfaccia Funzione)
     *
     * @return l'oggetto FunzioneSql
     */
    public FunzioneSql getFunzioneSql(String codice) {
        /* valore di ritorno */
        return (FunzioneSql)this.getFunzione(codice);
    }

//    /**
//     * Crea una tavola sul database.
//     * <p/>
//     * La tavola viene creata solo se non gia' esistente.
//     *
//     * @param modulo il modulo per il quale creare la tavola
//     *
//     * @return true se esistente o creata correttamente
//     *         false se non creata
//     */
//    public boolean creaTavola(Modulo modulo) {
//        return this.getInterpreteStructSql().creaTavola(modulo);
//    }


    /**
     * Crea una tavola sul database.
     * <p/>
     *
     * @param modulo di riferimento
     * @param conn la connessione da utilizzare
     *
     * @return true se riuscito
     */
    public boolean creaTavola(Modulo modulo, Connessione conn) {
        return this.getInterpreteStructSql().creaTavola(modulo, conn);
    }


    /**
     * Elimina una tavola dal database.
     * <p/>
     *
     * @param tavola il nome della tavola
     * @param conn la connessione da utilizzare
     *
     * @return true se riuscito
     */
    public boolean eliminaTavola(String tavola, Connessione conn) {
        return this.getInterpreteStructSql().eliminaTavola(tavola, conn);
    }


    /**
     * Allinea una colonna del Db in base al corrispondente
     * campo del Modello.
     * <p/>
     * - se la colonna non esiste, la crea e la regola
     * - se esiste, la regola soltanto
     *
     * @param campo il campo per il quale allineare la colonna
     * @param conn la connessione da utilizzare
     *
     * @return true se ha eseguito correttamente
     */
    public boolean allineaCampo(Campo campo, Connessione conn) {
        return this.getInterpreteStructSql().allineaCampo(campo, conn);
    }


    /**
     * Ritorna l'elenco dei nomi di tutti i campi di una tavola del database.
     * <p/>
     *
     * @param tavola il nome della tavola
     * @param conn la connessione da utilizzare per l'interrogazione
     *
     * @return una lista di oggetti String contenente i nomi non qualificati
     *         di tutti i campi della tavola.
     */
    public ArrayList getCampiTavola(String tavola, Connessione conn) {
        return this.getInterpreteStructSql().getCampiTavola(tavola, conn);
    }


    /**
     * Crea un oggetto Campo da una colonna di una tavola.
     * <p/>
     * Il nome interno del campo e' uguale al nome della colonna
     * Il tipo Memoria del campo corrisponde al tipo Archivio della colonna
     *
     * @param modulo del campo da creare
     * @param tavola il nome della tavola
     * @param colonna il nome della colonna
     * @param conn la connessione da utilizzare per l'interrogazione
     *
     * @return il campo creato.
     */
    public Campo creaCampoColonna(Modulo modulo, String tavola, String colonna, Connessione conn) {
        return this.getInterpreteStructSql().creaCampoColonna(modulo, tavola, colonna, conn);
    }


    /**
     * Crea una colonna del database in base a un campo.
     * <p/>
     * Crea la colonna del tipo appropriato al campo.
     * Non effettua altre regolazioni (caratteristiche, indici ecc...)
     *
     * @param campo il campo per il quale creare la colonna
     * @param conn la connessione da utilizzare
     *
     * @return true se la colonna e' stata creata correttamente
     */
    public boolean creaColonna(Campo campo, Connessione conn) {
        return this.getInterpreteStructSql().creaColonna(campo, conn);
    }


    /**
     * Elimina una colonna dal database.
     * <p/>
     *
     * @param campo il campo corrispondente alla colonna da eliminare
     * @param conn la connessione da utilizzare per l'operazione
     *
     * @return true se riuscito.
     */
    public boolean eliminaColonna(Campo campo, Connessione conn) {
        return this.getInterpreteStructSql().eliminaColonna(campo, conn);
    }


    /**
     * Attiva o disattiva l'integrità referenziale.
     * <p/>
     *
     * @param flag di attivazione
     *
     * @return true se riuscito
     */
    public boolean setReferentialIntegrity(boolean flag, Connessione conn) {
        return this.getInterpreteStructSql().setReferentialIntegrity(flag, conn);
    }


    private HashMap getMappaJdbc2Tipi() {
        return mappaJdbc2Tipi;
    }


    public String getSelect() {
        return this.select;
    }


    public String getDelete() {
        return this.delete;
    }


    public String getInsert() {
        return this.insert;
    }


    public String getUpdate() {
        return this.update;
    }


    public String getSet() {
        return this.set;
    }


    public String getValues() {
        return this.values;
    }


    public String getSeparatoreCampi() {
        return this.separatoreCampi;
    }


    public String getSeparatoreValori() {
        return this.separatoreValori;
    }


    public String getFrom() {
        return this.from;
    }


    public String getUsing() {
        return this.using;
    }


    public String getAsterisco() {
        return this.asterisco;
    }


    public String getLeftJoin() {
        return this.leftJoin;
    }


    public String getOn() {
        return this.on;
    }


    public String getWhere() {
        return this.where;
    }


    public String getOrderBy() {
        return this.orderBy;
    }


    public String getUguale() {
        return this.uguale;
    }


    public String getDiverso() {
        return this.diverso;
    }


    public String getMinore() {
        return this.minore;
    }


    public String getMinoreUguale() {
        return this.minoreUguale;
    }


    public String getMaggiore() {
        return this.maggiore;
    }


    public String getMaggioreUguale() {
        return this.maggioreUguale;
    }


    public String getLike() {
        return this.like;
    }

    public String getIn() {
        return this.in;
    }

    public String getAscendente() {
        return ascendente;
    }


    public String getDiscendente() {
        return discendente;
    }


    public String getCount() {
        return count;
    }


    public String getMin() {
        return min;
    }


    public String getMax() {
        return max;
    }


    public String getSum() {
        return sum;
    }


    public String getAvg() {
        return avg;
    }


    public String getLength() {
        return length;
    }


    public String getUpper() {
        return this.upper;
    }


    public String getLower() {
        return lower;
    }


    public String getDistinct() {
        return distinct;
    }


    public String getWildcard() {
        return this.wildcard;
    }


    public String getNot() {
        return this.not;
    }


    public String getIs() {
        return this.is;
    }


    public String getNullo() {
        return this.nullo;
    }


    public String getKeyData() {
        return this.keyData;
    }


    public String getKeyTimestamp() {
        return keyTimestamp;
    }


    public String getKeyTime() {
        return keyTime;
    }


    public String getKeyIntero() {
        return keyIntero;
    }


    public String getKeyDecimal() {
        return keyDecimal;
    }


    public String getKeyDouble() {
        return keyDouble;
    }


    public String getKeyBigint() {
        return keyBigint;
    }


    public String getKeyTesto() {
        return keyTesto;
    }


    public String getKeyBooleano() {
        return keyBooleano;
    }


    public String getDelimTesto() {
        return delimTesto;
    }


    public String getCreateTable() {
        return createTable;
    }


    public String getCreateTempTable() {
        return createTempTable;
    }


    public String getAlterTable() {
        return alterTable;
    }


    public String getDropTable() {
        return dropTable;
    }


    public String getAlterColumn() {
        return alterColumn;
    }


    public String getIndex() {
        return index;
    }


    public String getCreate() {
        return create;
    }


    public String getAdd() {
        return add;
    }


    public String getDrop() {
        return drop;
    }


    public String getAddColumn() {
        return addColumn;
    }


    public String getRestrict() {
        return restrict;
    }


    public String getDropCascade() {
        return dropCascade;
    }


    public String getConstraint() {
        return constraint;
    }


    public String getPrimaryKey() {
        return primaryKey;
    }


    public String getForeignKey() {
        return foreignKey;
    }


    public String getUnique() {
        return unique;
    }


    public String getReferences() {
        return references;
    }


    public String getOnDelete() {
        return onDelete;
    }


    public String getOnUpdate() {
        return onUpdate;
    }


    public String getNoAction() {
        return noAction;
    }


    public String getCascade() {
        return cascade;
    }


    public String getSetNull() {
        return setNull;
    }


    public String getSetDefault() {
        return setDefault;
    }

}// fine della classe
