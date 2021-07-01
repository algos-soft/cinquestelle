/**
 * Title:     SqlStandard
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      21-ott-2004
 */
package it.algos.base.database.sql.implem.mysql;

import it.algos.base.database.connessione.Connessione;
import it.algos.base.database.sql.DbSqlBase;
import it.algos.base.database.tipodati.TipoDati;
import it.algos.base.errore.Errore;

/**
 * Database di tipo Sql generico
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 21-ott-2004 ore 11.18.34
 */
public final class DbSqlMySql extends DbSqlBase {

    /**
     * specifica CHANGE del comando ALTER TABLE
     */
    private String change = " CHANGE ";

    /**
     * specifica MODIFY del comando ALTER TABLE
     */
    private String modify = " MODIFY ";

    /**
     * opzione NOT NULL per una definizione di colonna
     * non ammette nulli sulla colonna
     */
    private String opzioneNotNull = " NOT NULL ";

    /**
     * opzione NULL per una definizione di colonna
     * ammette nulli sulla colonna
     */
    private String opzioneNull = " NULL ";

    /**
     * parola chiave ENGINE per selezionare lo storage engine
     * alla creazione di una tavola
     */
    private String engine = " ENGINE ";

    /**
     * storage engine di tipo MyISAM (non supporta fkeys e transazioni,
     * e' il default di MySql)
     */
    private String engineMyISAM = " MYISAM ";

    /**
     * storage engine di tipo InnoDB (supporta fkeys e transazioni)
     */
    private String engineInnoDb = " INNODB ";

    /**
     * opzione tavola per character set e collation UTF-8
     * <p/>
     * usiamo la collation utf8_general_ci (ci=case-insensitive):
     * se creo un indice unico non permette doppioni dello stesso testo
     * con case diverso e ordina in maniera case-insensitive.
     * <p/>
     * Tratto dal manuale MySql:
     * A difference between the collations is that this is true for utf8_general_ci:
     * ß = s
     * Whereas this is true for utf8_unicode_ci:
     * ß = ss
     * <p/>
     * MySQL implements language-specific collations for the utf8 character set only
     * if the ordering with utf8_unicode_ci does not work well for a language.
     * For example, utf8_unicode_ci works fine for German and French, so there is
     * no need to create special utf8 collations for these two languages.
     * <p/>
     * utf8_general_ci also is satisfactory for both German and French, except
     * that “ß” is equal to “s”, and not to “ss”. If this is acceptable for
     * your application, then you should use utf8_general_ci because it is faster.
     * Otherwise, use utf8_unicode_ci because it is more accurate.
     */
    private String opzioneTableUTF8 = " CHARACTER SET utf8 COLLATE utf8_general_ci ";


    /**
     * Costruttore completo.
     * <p/>
     * Non avendo modificatore, puo' essere invocato solo da
     * una classe interna al proprio package.<br>
     * Viene invocato da CreaIstanza.
     */
    DbSqlMySql() {
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

        /* regola il nome della classe driver */
        this.setNomeClasseDriver("com.mysql.jdbc.Driver");

        /* regola il subprotocollo jdbc */
        this.setSubprotocolloJDBC("mysql");

        /* regola la porta di default */
        this.setPortaDefault(3306);

        /* regola il database di default */
        this.setNomeDbDefault("test");

        /* regola l'utente di default */
        this.setLoginDefault("root");

        /* regola la password di default */
        this.setPasswordDefault("");

        /*
         * Regolazione finale dei dati del database prima della inizializzazione.
         * Deve essere invocato obbligatoriamente da ogni
         * sottoclasse concreta alla fine del metodo Inizia().
         */
        super.regolaDatiDb();


    }// fine del metodo inizia


    /**
     * Regola gli interpreti per il Db.
     * <p/>
     * Crea e assegna al Db le istanze delle classi che dovranno
     * trasformare gli oggetti in comandi Sql.<br>
     * Sovrascrive il metodo della superclasse.
     */
    protected void regolaInterpreti() {

        /* invoca il metodo della superclasse, che assegna interpreti di default */
        super.regolaInterpreti();

        /* sostituisce l'interprete per la struttura */
        this.setInterpreteStruct(new InterpreteStructMySql(this));

    } // fine del metodo


    /**
     * Riempie la collezione dei tipi di dati per il Db.
     * <p/>
     * Sovrascrive il metodo della superclasse.
     * <p/>
     * Per MySql, sostituisce il tipo Booleano con un tipo Booleano specifico.
     * <p/>
     * Riempie la collezione dei tipi archivio con gli oggetti
     * TipoDati supportati dal database.<br>
     * - chiave: classe Java gestita dal tipo archivio
     * - valore: oggetto TipoDati che gestisce la classe
     */
    protected void creaTipiDati() {

        /* variabili e costanti locali di lavoro */
        Integer chiave = null;

        try { // prova ad eseguire il codice

            /* crea i tipi standard nella superclasse */
            super.creaTipiDati();

            /* sostituisce il tipo Booleano nella collezione */
            chiave = new Integer(TipoDati.TIPO_BOOLEANO);
            this.getTipiDati().put(chiave, new TipoDatiMySqlBooleano(this));

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Regolazione dei comandi Sql.
     * <p/>
     * Sovrascrive il metodo della superclasse
     */
    protected void regolaComandi() {
        super.regolaComandi();
        this.keyBooleano = " BOOL ";
    }


    /**
     * Ritorna la stringa rappresentante l'Url per una
     * connessione JDBC al database.
     * <p/>
     * Sovrascrive il metodo della superclasse
     * Forza la connessione ad usare Unicode con character set UTF-8
     *
     * @param conn la connessione per la quale ricavare l'url
     *
     * @return l'Url per la connessione
     */
    public String getStringaUrl(Connessione conn) {
        /* variabili e costanti locali di lavoro */
        String url = "";

        try {    // prova ad eseguire il codice

            url = super.getStringaUrl(conn);
            url += "?useUnicode=true&characterEncoding=UTF8";

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return url;
    }


    public String getChange() {
        return change;
    }


    public String getModify() {
        return modify;
    }


    public String getOpzioneNotNull() {
        return opzioneNotNull;
    }


    public String getOpzioneNull() {
        return opzioneNull;
    }


    public String getEngine() {
        return engine;
    }


    public String getEngineMyISAM() {
        return engineMyISAM;
    }


    public String getEngineInnoDb() {
        return engineInnoDb;
    }


    public String getOpzioneTableUTF8() {
        return opzioneTableUTF8;
    }

}// fine della classe
