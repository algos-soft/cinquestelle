/**
 * Title:        CostanteDB.java
 * Package:      it.algos.base.costante
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:      Algos s.r.l.
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /
 * Creato:       il 29 settembre 2002 alle 22.06
 */
//-----------------------------------------------------------------------------
// Copyright (c) 2002  Algos s.r.l. All Rights Reserved.
//-----------------------------------------------------------------------------
//-----------------------------------------------------------------------------
//  Package di questa interfaccia e classi importate
//-----------------------------------------------------------------------------
package it.algos.base.costante;

//-----------------------------------------------------------------------------
// Classe principale


//-----------------------------------------------------------------------------
/**
 * Interfaccia per le costanti dei database<br>
 *
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /  29 settembre 2002 ore 22.06
 */
public interface CostanteDB {

    //-------------------------------------------------------------------------
    // Costanti statiche della classe    (private,pubbliche)  (class variables)
    //-------------------------------------------------------------------------
    /**
     * lunghezza piu' comune (di default) del campo di tipo CHAR
     */
    public static final int DEFAULT_CARATTERI_STRINGA = 30;

    /**
     * driver di default, se manca la preferenza
     */
    public static final String DB_DRIVER = "org.postgresql.Driver";

    /**
     * percorso di default, se manca la preferenza
     */
    public static final String DB_PERCORSO = "jdbc:postgresql:";

    /**
     * indirizzo di default, se manca la preferenza
     */
    public static final String DB_INDIRIZZO = "127.0.0.1";   // default a localhost

    /**
     * porta di default, se manca la preferenza
     */
    public static final String DB_PORTA = "5432";   // porta di default postgresql

    /**
     * nome archivio di default, se manca la preferenza
     */
    public static final String DB_ARCHIVIO = "algos";

    /**
     * utente di default, se manca la preferenza
     */
    public static final String DB_UTENTE = "postgres";

    /**
     * password di default, se manca la preferenza
     */
    public static final String DB_PASSWORD = "";

    /**
     * parole chiave sql
     */
    public static final String CREATE_TABLE = " CREATE TABLE ";

    public static final String DROP_TABLE = " DROP TABLE ";

    public static final String ALTER_TABLE = " ALTER TABLE ";

    public static final String ALTER_COLUMN = " ALTER COLUMN ";

    public static final String ADD_COLUMN = " ADD COLUMN ";

    public static final String SELECT = " SELECT ";

    public static final String SELECT_DISTINCT = " SELECT DISTINCT ";

    public static final String SELECT_COUNT = " SELECT COUNT(*) FROM ";

    public static final String FROM = " FROM ";

    public static final String WHERE = " WHERE ";

    public static final String ORDER_BY = " ORDER BY ";

    public static final String ASC = " ASC ";

    public static final String DESC = " DESC ";

    public static final String UPPER = " UPPER ";

    public static final String LEFT_JOIN = " LEFT JOIN ";

    public static final String ON = " ON ";

    public static final String LIKE = " LIKE ";

    public static final String INSERT_INTO = " INSERT INTO ";

    public static final String VALUES = " VALUES ";

    public static final String UPDATE = " UPDATE ";

    public static final String SET = " SET ";

    public static final String DELETE_FROM = " DELETE FROM ";

    public static final String AS = " AS ";

    public static final String ADD = " ADD ";

    public static final String CREATE_VIEW = " CREATE VIEW ";

    public static final String DROP_VIEW = " DROP VIEW ";

    public static final String CONSTRAINT = " CONSTRAINT ";

    public static final String UNIQUE = " UNIQUE ";

    public static final String PRIMARY_KEY = " PRIMARY KEY ";

    public static final String CHECK = " CHECK ";

    public static final String FOREIGN_KEY = " FOREIGN KEY ";

    public static final String REFERENCES = " REFERENCES ";

    public static final String ON_DELETE = " ON DELETE ";

    public static final String ON_UPDATE = " ON UPDATE ";

    public static final String NO_ACTION = " NO ACTION ";

    public static final String CASCADE = " CASCADE ";

    public static final String SET_NULL = " SET NULL ";

    public static final String SET_DEFAULT = " SET DEFAULT ";

    public static final String SET_NOT_NULL = " SET NOT NULL ";

    public static final String DROP_NOT_NULL = " DROP NOT NULL ";

    public static final String CREATE = " CREATE ";

    public static final String INDEX = " INDEX ";

    public static final String DROP_INDEX = " DROP INDEX ";

    public static final String ADD_CONSTRAINT = " ADD CONSTRAINT ";

    public static final String DROP_CONSTRAINT = " DROP CONSTRAINT ";

    public static final String DROP_COLUMN = " DROP COLUMN ";

    //    /** clausole di unione dei filtri semplici */
    public static final String AND = " AND ";

    public static final String OR = " OR ";

    /**
     * clausole di unione per i filtri composti
     */
    public static final String UNION = " UNION ";

    public static final String INTERSECT = " INTERSECT ";

    public static final String EXCEPT = " EXCEPT ";

    /**
     * separatore dei campi sql
     */
    public static final String SEPARATORE_CAMPI = ",\n";

    /**
     * separatore delle tavole sql
     */
    public static final String SEPARATORE_TAVOLE = " \n";

    /**
     * separatore delle condizioni sql
     */
    public static final String SEPARATORE_CONDIZIONI = " \n";

    /**
     * separatore degli ordinamenti
     */
    public static final String SEPARATORE_ORDINE = ",\n";

    /**
     * suffisso di default per il nome della constraint Primary Key
     */
    public static final String SUFFISSO_DEFAULT_PKEY = "_pkey";

    /**
     * suffisso di default per il nome della constraint Foreign Key
     */
    public static final String SUFFISSO_DEFAULT_FKEY = "_fkey";

    /**
     * costanti carattere
     */
    public static final String VUOTO = "";

    public static final String CIRCONFLESSO = "^";

    public static final String APICE = "'";

    public static final String ASTERISCO = "*";

    public static final String VIRGOLA = ",";

    public static final String SPAZIO = " ";

    public static final String PARENTESI_APERTA = "(";

    public static final String PARENTESI_CHIUSA = ")";

    /**
     * valori logici in forma di stringa
     */
    public static final String STRINGA_VERO = "true";

    public static final String STRINGA_FALSO = "false";

    /**
     * valori logici come oggetti Boolean
     */
    public static final Boolean VERO = new Boolean(true);

    public static final Boolean FALSO = new Boolean(false);

    /**
     * valore nullo Sql
     */
    public static final String NULL = "null";

    /**
     * funzioni semplici
     */
    public static final String UPPERCASE = " UPPER ";

    public static final String LOWERCASE = " LOWER ";

//    /** funzioni complesse */
//    public static final DBFunzione FN_MINUSCOLO = DBFunzione.minuscolo();
//    public static final DBFunzione FN_MAIUSCOLO = DBFunzione.maiuscolo();

    /**
     * tipi di Query Modifica
     */
    public static final int TIPO_QUERY_INSERT = 1;  // creazione nuovo record

    public static final int TIPO_QUERY_UPDATE = 2;  // aggiornamento record esistente

    public static final int TIPO_QUERY_DELETE = 3;  // eliminazione record

    /**
     * keywords SQL per identificare i tipi di dati
     */
    public static final String KEY_SQL_INTERO = " INTEGER ";

    public static final String KEY_SQL_TESTO = " VARCHAR ";

    public static final String KEY_SQL_BOOLEANO = " BOOLEAN ";

    public static final String KEY_SQL_DATA = " DATE ";

    public static final String KEY_SQL_TIMESTAMP = " TIMESTAMP ";

    public static final String KEY_SQL_VALUTA = " DECIMAL ";

//    /** inizializzatori di nuovo record per i campi */
//    public static final Init INIT_SEQUENZIALE = new InitSequenziale();
//    public static final Init INIT_DATA_ATTUALE = new InitDataAttuale();
//    public static final Init INIT_TIMESTAMP_ATTUALE = new InitTimestampAttuale();
//    public static final Init INIT_UTENTE_CORRENTE = new InitUtenteCorrente();

    /**
     * codici per recuperare le informazioni su un campo dal driver JDBC
     */
    public static final int INFO_NOME_TAVOLA = 3;

    public static final int INFO_NOME_CAMPO = 4;

    public static final int INFO_TIPO_JDBC = 5;

    public static final int INFO_NULLABLE = 18;

    /**
     * codici per recuperare le informazioni su un indice dal driver JDBC
     */
    public static final int INFO_IDX_NOME_INDICE = 6;

    public static final int INFO_IDX_NOME_CAMPO = 9;

    public static final int INFO_IDX_NON_UNICO = 4;

    /**
     * codici per recuperare le informazioni su una Primary Key dal driver JDBC
     */
    public static final int INFO_PKEY_CAMPO = 4;

    public static final int INFO_PKEY_NOME = 6;

    /**
     * codici per recuperare le informazioni su una Foreign Key dal driver JDBC
     */
    public static final int INFO_FKEY_NOME_FKEY = 12;

    public static final int INFO_FKEY_TAVOLA_FKEY = 7;

    public static final int INFO_FKEY_CAMPO_FKEY = 8;

    public static final int INFO_FKEY_TAVOLA_PKEY = 3;

    public static final int INFO_FKEY_CAMPO_PKEY = 4;

    public static final int INFO_FKEY_AZIONE_DELETE = 11;

    public static final int INFO_FKEY_AZIONE_UPDATE = 10;

//-------------------------------------------------------------------------
}// fine della interfaccia
//-----------------------------------------------------------------------------

