/**
 * Title:     DbSql
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      8-ott-2004
 */
package it.algos.base.database.sql;

import it.algos.base.campo.base.Campo;
import it.algos.base.database.Db;
import it.algos.base.database.connessione.Connessione;
import it.algos.base.database.connessione.ConnessioneJDBC;
import it.algos.base.database.sql.interprete.InterpreteFiltroSql;
import it.algos.base.database.sql.interprete.InterpreteOrdineSql;
import it.algos.base.database.sql.interprete.InterpreteQmSql;
import it.algos.base.database.sql.interprete.InterpreteQsSql;
import it.algos.base.database.sql.interprete.InterpreteStructSqlBase;
import it.algos.base.database.sql.tipodati.TipoDatiSql;
import it.algos.base.database.sql.util.FunzioneSql;
import it.algos.base.database.sql.util.OperatoreSql;
import it.algos.base.errore.Errore;
import it.algos.base.modulo.Modulo;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;

/**
 * Database generico SQL
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 8-ott-2004 ore 17.13.33
 */
public interface DbSql extends Db {

    /**
     * stringa sql per eseguire il comando SELECT
     */
    public static final String SELECT = " SELECT ";

    /**
     * stringa sql per eseguire il comando DELETE
     */
    public static final String DELETE = " DELETE ";

    /**
     * stringa sql per eseguire il comando INSERT
     */
    public static final String INSERT = " INSERT INTO ";

    /**
     * stringa sql per eseguire il comando UPDATE
     */
    public static final String UPDATE = " UPDATE ";

    /**
     * stringa sql per la parola chiave SET
     */
    public static final String SET = " SET ";

    /**
     * stringa sql per la parola chiave VALUES
     */
    public static final String VALUES = " VALUES ";

    /**
     * separatore dei campi in un elenco campi sql
     */
    public static final String SEP_CAMPI = ", ";

    /**
     * separatore dei valori in un elenco valori sql
     */
    public static final String SEP_VALORI = ", ";

    /**
     * stringa sql per la clausola FROM
     */
    public static final String FROM = " FROM ";

    /**
     * stringa sql per la clausola USING
     */
    public static final String USING = " USING ";


    /**
     * stringa sql per l'asterisco (es. SELECT * FROM...)
     */
    public static final String ASTERISCO = " * ";

    /**
     * stringa sql per la clausola LEFT JOIN
     */
    public static final String LEFT_JOIN = " LEFT JOIN ";

    /**
     * stringa sql per la clausola ON
     */
    public static final String ON = " ON ";

    /**
     * stringa sql per la clausola WHERE
     */
    public static final String WHERE = " WHERE ";

    /**
     * stringa sql per la clausola ORDER BY
     */
    public static final String ORDER_BY = " ORDER BY ";

    /**
     * stringa Sql per l'unione di tipo AND
     */
    public static final String UNIONE_AND = " AND ";

    /**
     * stringa Sql per l'unione di tipo OR
     */
    public static final String UNIONE_OR = " OR ";

    /**
     * stringa Sql per l'unione di tipo AND NOT
     */
    public static final String UNIONE_AND_NOT = " AND NOT ";

    /**
     * stringa Sql per la parentesi aperta
     */
    public static final String PARENTESI_APERTA = "(";

    /**
     * stringa Sql per la parentesi chiusa
     */
    public static final String PARENTESI_CHIUSA = ")";

    /**
     * stringa Sql per l'operatore uguale (=)
     */
    public static final String OP_UGUALE = " = ";

    /**
     * stringa Sql per l'operatore diverso (!=)
     */
    public static final String OP_DIVERSO = " != ";

    /**
     * stringa Sql per l'operatore MINORE
     */
    public static final String OP_MINORE = " < ";

    /**
     * stringa Sql per l'operatore MINORE UGUALE
     */
    public static final String OP_MINORE_UGUALE = " <= ";

    /**
     * stringa Sql per l'operatore MAGGIORE
     */
    public static final String OP_MAGGIORE = " > ";

    /**
     * stringa Sql per l'operatore MAGGIORE UGUALE
     */
    public static final String OP_MAGGIORE_UGUALE = " >= ";

    /**
     * stringa Sql per l'operatore LIKE
     */
    public static final String OP_LIKE = " LIKE ";

    /**
     * stringa Sql per l'operatore IN
     */
    public static final String OP_IN = " IN ";

    /**
     * stringa Sql per l'operatore di ordinamento ASC
     */
    public static final String OP_ASCENDENTE = " ASC ";

    /**
     * stringa Sql per l'operatore di ordinamento DESC
     */
    public static final String OP_DISCENDENTE = " DESC ";

    /*
     * stringa Sql per la funzione COUNT
     */
    public static final String FN_COUNT = " COUNT"; // no spazio dopo funzioni (mysql!)

    /*
     * stringa Sql per la funzione MIN
     */
    public static final String FN_MIN = " MIN"; // no spazio dopo funzioni (mysql!)

    /*
     * stringa Sql per la funzione MAX
     */
    public static final String FN_MAX = " MAX"; // no spazio dopo funzioni (mysql!)

    /*
     * stringa Sql per la funzione SUM
     */
    public static final String FN_SUM = " SUM";  // no spazio dopo funzioni (mysql!)

    /*
     * stringa Sql per la funzione AVG
     */
    public static final String FN_AVG = " AVG";   // no spazio dopo funzioni (mysql!)

    /*
     * stringa Sql per la funzione LENGTH
     */
    public static final String FN_LENGTH = " LENGTH";    // no spazio dopo funzioni (mysql!)

    /**
     * stringa Sql per la funzione UPPER
     */
    public static final String FN_UPPER = " UPPER";  // no spazio dopo funzioni (mysql!)

    /**
     * stringa Sql per la funzione UPPER
     */
    public static final String FN_LOWER = " LOWER";  // no spazio dopo funzioni (mysql!)

    /**
     * stringa Sql per la funzione DISTINCT
     */
    public static final String FN_DISTINCT = " DISTINCT"; // no spazio dopo funzioni (mysql!)

    /**
     * stringa Sql per la funzione NOT
     */
    public static final String NOT = " NOT";  // no spazio dopo funzioni (mysql!)

    /**
     * stringa Sql per la wildcard nelle ricerche di testo
     */
    public static final String WILDCARD = "%";


    /**
     * stringa Sql per la clausola IS
     */
    public static final String IS = " IS ";

    /**
     * stringa Sql per la clausola NULL
     */
    public static final String NULLO = " NULL ";

    /**
     * keyword Sql per il tipo DATA
     */
    public static final String KEY_DATA = " DATE ";

    /**
     * keyword Sql per il tipo TIMESTAMP
     */
    public static final String KEY_TIMESTAMP = " TIMESTAMP ";

    /**
     * keyword Sql per il tipo TIME
     */
    public static final String KEY_TIME = " TIME ";

    /**
     * keyword Sql per il tipo INTERO
     */
    public static final String KEY_INTERO = " INTEGER ";

    /**
     * keyword Sql per il tipo DECIMAL
     */
    public static final String KEY_DECIMAL = " DECIMAL ";

    /**
     * keyword Sql per il tipo DOUBLE
     */
    public static final String KEY_DOUBLE = " DOUBLE ";

    /**
     * keyword Sql per il tipo BIGINT
     */
    public static final String KEY_BIGINT = " BIGINT ";

    /**
     * keyword Sql per il tipo TESTO
     */
    public static final String KEY_TESTO = " TEXT ";

    /**
     * keyword Sql per il tipo BOOLEANO
     */
    public static final String KEY_BOOLEANO = " BOOLEAN ";

    /**
     * delimitatore del testo (apice singolo)
     */
    public static final String DELIM_TESTO = "'";

    /**
     * stringa sql per eseguire il comando CREATE TABLE
     */
    public static final String CREATE_TABLE = " CREATE TABLE ";

    /**
     * stringa sql per eseguire il comando CREATE TEMPORARY TABLE
     */
    public static final String CREATE_TEMP_TABLE = " CREATE TEMPORARY TABLE ";

    /**
     * stringa sql per eseguire il comando ALTER TABLE
     */
    public static final String ALTER_TABLE = " ALTER TABLE ";

    /**
     * stringa sql per eseguire il comando DROP TABLE
     */
    public static final String DROP_TABLE = " DROP TABLE ";

    /**
     * stringa sql per eseguire il comando ALTER COLUMN
     */
    public static final String ALTER_COLUMN = " ALTER COLUMN ";

    /**
     * parola chiave per INDEX
     */
    public static final String INDEX = " INDEX ";

    /**
     * stringa sql per eseguire il comando generico CREATE
     */
    public static final String CREATE = " CREATE ";

    /**
     * stringa sql per eseguire il comando generico ADD
     */
    public static final String ADD = " ADD ";

    /**
     * stringa sql per eseguire il comando generico DROP
     */
    public static final String DROP = " DROP ";

    /**
     * stringa sql per la clausola ADD COLUMN del comando ALTER TABLE
     */
    public static final String ADD_COLUMN = " ADD COLUMN ";

    /**
     * stringa sql per l'azione referenziale RESTRICT
     */
    public static final String RESTRICT = " RESTRICT ";

    /**
     * stringa sql per il comportamento drop di tipo CASCADE
     */
    public static final String DROP_CASCADE = " CASCADE ";

    /**
     * parola chiave per identificare una CONSTRAINT
     */
    public static final String CONSTRAINT = " CONSTRAINT ";

    /**
     * parola chiave per la constraint PRIMARY KEY
     */
    public static final String PRIMARY_KEY = " PRIMARY KEY ";

    /**
     * parola chiave per la constraint FOREIGN KEY
     */
    public static final String FOREIGN_KEY = " FOREIGN KEY ";

    /**
     * parola chiave per la clausola UNIQUE
     */
    public static final String UNIQUE = " UNIQUE ";

    /**
     * parola chiave per la clausola referenziale REFERENCES
     */
    public static final String REFERENCES = " REFERENCES ";

    /**
     * parola chiave per la clausola referenziale ON DELETE
     */
    public static final String ON_DELETE = " ON DELETE ";

    /**
     * parola chiave per la clausola referenziale ON UPDATE
     */
    public static final String ON_UPDATE = " ON UPDATE ";

    /**
     * parola chiave per l'azione referenziale NO ACTION
     */
    public static final String NO_ACTION = " NO ACTION ";

    /**
     * parola chiave per l'azione referenziale CASCADE
     */
    public static final String CASCADE = " CASCADE ";

    /**
     * parola chiave per l'azione referenziale SET NULL
     */
    public static final String SET_NULL = " SET NULL ";

    /**
     * parola chiave per l'azione referenziale SET DEFAULT
     */
    public static final String SET_DEFAULT = " SET DEFAULT ";

//-----------------------------------------------------------------------------
// Codifica della posizione delle informazioni ottenute dal driver JDBC
//-----------------------------------------------------------------------------

    /**
     * nome della tavola (getTables)
     */
    public static final int INFO_NOME_TAVOLA = 3;

    /**
     * nome della colonna (getColumns)
     */
    public static final int INFO_NOME_CAMPO = 4;

    /**
     * tipo JDBC della colonna (getColumns)
     */
    public static final int INFO_TIPO_JDBC = 5;

    /**
     * attributo se la colonna accetta valori nulli (getColumns)
     */
    public static final int INFO_NULLABLE = 18;

    /**
     * nome dell'indice (getIndexInfo)
     */
    public static final int INFO_IDX_NOME_INDICE = 6;

    /**
     * nome della colonna indicizzata (getIndexInfo)
     */
    public static final int INFO_IDX_NOME_CAMPO = 9;

    /**
     * attributo NON UNICO dell'indice (getIndexInfo)
     */
    public static final int INFO_IDX_NON_UNICO = 4;

    /**
     * nome della colonna Primary Key (getPrimaryKeys)
     */
    public static final int INFO_PKEY_CAMPO = 4;

    /**
     * nome della Primary Key (getPrimaryKeys)
     */
    public static final int INFO_PKEY_NOME = 6;

    /**
     * nome della Foreign Key (getImportedKeys)
     */
    public static final int INFO_FKEY_NOME_FKEY = 12;

    /**
     * nome della tavola della Foreign Key (getImportedKeys)
     */
    public static final int INFO_FKEY_TAVOLA_FKEY = 7;

    /**
     * nome della colonna della Foreign Key (getImportedKeys)
     */
    public static final int INFO_FKEY_CAMPO_FKEY = 8;

    /**
     * primary key table name being imported (getImportedKeys)
     */
    public static final int INFO_FKEY_TAVOLA_PKEY = 3;

    /**
     * primary key column name  being imported (getImportedKeys)
     */
    public static final int INFO_FKEY_CAMPO_PKEY = 4;

    /**
     * What happens to the foreign key when primary is deleted (getImportedKeys)
     */
    public static final int INFO_FKEY_AZIONE_DELETE = 11;

    /**
     * What happens to a  foreign key when the primary key is updated (getImportedKeys)
     */
    public static final int INFO_FKEY_AZIONE_UPDATE = 10;

//-----------------------------------------------------------------------------
// Varie
//-----------------------------------------------------------------------------

    /**
     * suffisso di default per il nome delle constraint Primary Key
     */
    public static final String SUFFISSO_DEFAULT_PKEY = "_pkey";

    /**
     * suffisso di default per il nome delle constraint Foreign Key
     */
    public static final String SUFFISSO_DEFAULT_FKEY = "_fkey";

    /**
     * carattere di Underscore
     */
    public static final String UNDERSCORE = "_";


    public abstract String getSelect();


    public abstract String getDelete();


    public abstract String getInsert();


    public abstract String getUpdate();


    public abstract String getSet();


    public abstract String getValues();


    public abstract String getSeparatoreCampi();


    public abstract String getSeparatoreValori();


    public abstract String getFrom();


    public abstract String getUsing();


    public abstract String getAsterisco();


    public abstract String getLeftJoin();


    public abstract String getOn();


    public abstract String getWhere();


    public abstract String getOrderBy();


    public abstract String getUguale();


    public abstract String getDiverso();


    public abstract String getMinore();


    public abstract String getMinoreUguale();


    public abstract String getMaggiore();


    public abstract String getMaggioreUguale();


    public abstract String getLike();


    public abstract String getAscendente();


    public abstract String getDiscendente();


    public abstract String getCount();


    public abstract String getMin();


    public abstract String getMax();


    public abstract String getSum();


    public abstract String getAvg();


    public abstract String getLength();


    public abstract String getUpper();


    public abstract String getLower();


    public abstract String getDistinct();


    public abstract String getWildcard();


    public abstract String getNot();


    public abstract String getIs();


    public abstract String getNullo();


    public abstract String getKeyData();


    public abstract String getKeyTimestamp();


    public abstract String getKeyTime();


    public abstract String getKeyIntero();


    public abstract String getKeyDecimal();


    public abstract String getKeyDouble();


    public abstract String getKeyBigint();


    public abstract String getKeyTesto();


    public abstract String getKeyBooleano();


    public abstract String getDelimTesto();


    public abstract String getCreateTable();


    public abstract String getCreateTempTable();


    public abstract String getAlterTable();


    public abstract String getDropTable();


    public abstract String getAlterColumn();


    public abstract String getIndex();


    public abstract String getCreate();


    public abstract String getAdd();


    public abstract String getDrop();


    public abstract String getAddColumn();


    public abstract String getRestrict();


    public abstract String getDropCascade();


    public abstract String getConstraint();


    public abstract String getPrimaryKey();


    public abstract String getForeignKey();


    public abstract String getUnique();


    public abstract String getReferences();


    public abstract String getOnDelete();


    public abstract String getOnUpdate();


    public abstract String getNoAction();


    public abstract String getCascade();


    public abstract String getSetNull();


    public abstract String getSetDefault();


    /**
     * Ritorna l'interprete Sql delle Query di Selezione
     */
    public abstract InterpreteQsSql getInterpreteQsSql();


    /**
     * Ritorna l'interprete Sql del filtro
     */
    public abstract InterpreteFiltroSql getInterpreteFiltroSql();


    /**
     * Ritorna l'interprete Sql dell'Ordine
     */
    public abstract InterpreteOrdineSql getInterpreteOrdineSql();


    /**
     * Ritorna l'interprete Sql delle Query di Modifica
     */
    public abstract InterpreteQmSql getInterpreteQmSql();


    /**
     * Ritorna l'interprete Sql della Struttura del database
     */
    public abstract InterpreteStructSqlBase getInterpreteStructSql();


    /**
     * Ritorna la connessione JDBC al database.
     * <p/>
     *
     * @return un oggetto ConnessioneJDBC
     */
    public abstract ConnessioneJDBC getConnessioneJDBC();


    /**
     * Ritorna l'oggetto DatabaseMetaData relativo a questo database.
     * <p/>
     *
     * @return l'oggetto DatabaseMetaData del database
     */
    public abstract DatabaseMetaData getMetaData();


    /**
     * Converte un identificatore nel case supportato dal database.
     * <p/>
     *
     * @param identIn l'identificatore da convertire
     *
     * @return l'identificatore convertito nel case appropriato per il database
     */
    public abstract String fixCase(String identIn);


    /**
     * Ritorna la stringa Sql corrispondente a una data
     * clausola di unione generica.
     * <p/>
     *
     * @param codice il codice della clausola di unione generica (v. Operatore)
     *
     * @return la stringa Sql per la clausola di unione
     */
    public abstract String getUnione(String codice);


    /**
     * Ritorna l'operatore Sql corrispondente a un dato operatore filtro generico.
     * <p/>
     *
     * @param codice il codice dell'operatore filtro generico
     *
     * @return l'oggetto OperatoreSql
     *
     * @see it.algos.base.query.filtro.Filtro.Op
     */
    public abstract OperatoreSql getOperatoreFiltro(String codice);


    /**
     * Ritorna la stringa Sql corrispondente a un dato operatore ordine generico.
     * <p/>
     *
     * @param codice il codice dell'operatore ordine generico (v. Operatore)
     *
     * @return la stringa Sql
     */
    public abstract String getOperatoreOrdine(String codice);


    /**
     * Ritorna l'oggetto FunzioneSql corrispondente a una data chiave.
     * <p/>
     *
     * @param codice il codice chiave della funzione generica
     * (da interfaccia Funzione)
     *
     * @return l'oggetto FunzioneSql
     */
    public abstract FunzioneSql getFunzioneSql(String codice);


    /**
     * Esegue una Query di selezione sul database e ottiene un ResultSet.
     * <p/>
     *
     * @param stringaSql la stringa di comandi Sql per la Query
     * @param conn la connessione da utilizzare per la Query
     *
     * @return il ResultSet ottenuto
     */
    public ResultSet esegueSelect(String stringaSql, Connessione conn);


    /**
     * Esegue una Query di modifica sul database e ottiene un risultato.
     * <p/>
     *
     * @param stringaSql la stringa di comandi Sql per la Query
     * @param conn la connessione da utilizzare per la Query
     *
     * @return il risultato ottenuto
     */
    public abstract int esegueUpdate(String stringaSql, Connessione conn);


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
    public abstract boolean isEsisteTavola(String nomeTavola, Connessione conn);


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
    public abstract boolean isEsisteColonna(String nomeTavola,
                                            String nomeColonna,
                                            Connessione conn);

//    /**
//     * Crea una tavola sul database.
//     * <p/>
//     * La tavola viene creata solo se non gia' esistente.
//     * @param modulo il modulo proprietario della tavola
//     *
//     * @return true se esistente o creata correttamente
//     * false se non creata
//     */
//    public abstract boolean creaTavola(Modulo modulo);


    /**
     * Crea una tavola sul database.
     * <p/>
     *
     * @param modulo di riferimento
     * @param conn la connessione da utilizzare
     *
     * @return true se riuscito
     */
    public abstract boolean creaTavola(Modulo modulo, Connessione conn);


    /**
     * Elimina una tavola dal database.
     * <p/>
     *
     * @param tavola il nome della tavola
     * @param conn la connessione da utilizzare
     *
     * @return true se riuscito
     */
    public abstract boolean eliminaTavola(String tavola, Connessione conn);


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
    public abstract boolean creaColonna(Campo campo, Connessione conn);


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
    public abstract boolean allineaCampo(Campo campo, Connessione conn);


    /**
     * Ritorna una stringa che identifica una tavola per il database.
     * <p/>
     *
     * @param campo il campo del quale interessa la tavola
     *
     * @return una stringa che identifica la tavola
     */
    public abstract String getStringaTavola(Campo campo);


    /**
     * Ritorna una stringa che identifica una tavola per il database.
     * <p/>
     * La tavola viene recuperata dal modulo fornito.
     * Sovrascrive il metodo della superclasse.<br>
     *
     * @param modulo il modulo dal quale recuperare la tavola
     *
     * @return una stringa che identifica la tavola
     */
    public abstract String getStringaTavola(Modulo modulo);


    /**
     * Ritorna la keyword Sql corrispondente al tipo dati di un campo.
     * <p/>
     * La keyword e' usata per costruire il campo sul Db.
     *
     * @param campo il campo
     *
     * @return la keyword Sql per il campo
     */
    public abstract String getKeywordSql(Campo campo);


    /**
     * Ritorna il tipo dati Jdbc per un dato campo.
     * <p/>
     *
     * @param campo il campo
     *
     * @return il tipo Jdbc Sql per il campo (da java.Sql.Types)
     */
    public abstract int getTipoJdbc(Campo campo);


    /**
     * Dato un tipo JDBC, ritorna la chiave del corrispondente tipo dati sql
     * nella collezione.
     * <p/>
     *
     * @param tipoJdbc il tipo JDBC da cercare
     *
     * @return la chiave del tipo dati (0 se non trovato nella collezione)
     */
    public int getChiaveTipoDatiSql(int tipoJdbc);


    /**
     * Ritorna la stringa rappresentante l'Url per una
     * connessione JDBC al database.
     * <p/>
     *
     * @param conn la connessione per la quale ricavare l'url
     *
     * @return l'Url per la connessione
     */
    public abstract String getStringaUrl(Connessione conn);


    /**
     * Ritorna il tipo dati Sql del Db in grado di gestire
     * il tipo Memoria di un dato campo.
     * <p/>
     *
     * @param campo il campo per il quale cercare il corrispondente tipo dati
     *
     * @return il TipoDatiSql del db che gestisce il tipo memoria del campo.
     */
    public abstract TipoDatiSql getTipoDatiSql(Campo campo);


    /**
     * Ritorna il primo tipo dati Sql del Db in grado di gestire
     * un dato tipo JDBC.
     * <p/>
     *
     * @param tipo il tipo jdbc da gestire
     *
     * @return il primo TipoDatiSql del db che gestisce il tipo.
     */
    public abstract TipoDatiSql getTipoDatiDaJdbc(int tipo);


    /**
     * Regola il modo di funzionamento del database
     * <p/>
     * Sovrascritto dalle sottoclassi
     * Opzioni possibili: MODO_SERVER o MODO_STAND_ALONE
     * Vedi costanti in Db
     */
    public abstract void setModoFunzionamento(int modoFunzionamento);


    /**
     * Regola il tipo di tavole
     * <p/>
     * Sovrascritto dalle sottoclassi
     * Opzioni possibili: TAVOLE_MEMORY, TAVOLE_CACHED o TAVOLE_TEXT
     * Vedi costanti in Db
     */
    public abstract void setTipoTavole(int tipoTavole);


    /**
     * Codifica del case per il trattamento degli identificatori.
     * <p/>
     */
    public enum identifiersCase {

        uppercase(),
        lowercase(),
        mixedcase();


        /**
         * Costruttore completo con parametri.
         */
        identifiersCase() {
            try { // prova ad eseguire il codice
                /* regola le variabili di istanza coi parametri */
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }

    }// fine della classe

}// fine della interfaccia
