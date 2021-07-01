/**
 * Title:     DbSqlHsqldb
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      15-nov-2004
 */
package it.algos.base.database.sql.implem.hsqldb;

import it.algos.base.database.Db;
import it.algos.base.database.connessione.Connessione;
import it.algos.base.database.connessione.ConnessioneDati;
import it.algos.base.database.sql.DbSqlBase;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;

/**
 * Database di tipo Hsqldb
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 15-nov-2004 ore 12.08
 */
public final class DbSqlHsqldb extends DbSqlBase {


    /**
     * Protocollo di tipo file
     * per modalita' Stand alone su file
     */
    private static final String PROTO_FILE = "file:";

    /**
     * Protocollo di tipo res
     * per modalita' Stand alone su risorsa (read-only)
     */
    private static final String PROTO_RES = "res:";

    /**
     * Protocollo di tipo mem
     * per modalita' Stand alone in memoria
     */
    private static final String PROTO_MEM = "mem:";


    /**
     * Protocollo di tipo hsql
     * per modalita' client-server
     */
    private static final String PROTO_HSQL = "hsql";

    /**
     * clausola DROP CONSTRAINT per il comando ALTER TABLE
     */
    private String dropConstraint = " DROP CONSTRAINT ";

    /**
     * parola chiave TABLE
     */
    private String table = " TABLE ";

    /**
     * opzione MEMORY per la creazione della tavola
     */
    private String memory = " MEMORY ";

    /**
     * opzione CACHED per la creazione della tavola
     */
    private String cached = " CACHED ";

    /**
     * opzione TEMP per la creazione della tavola
     */
    private String temp = " TEMP ";

    /**
     * opzione TEXT per la creazione della tavola
     */
    private String text = " TEXT ";

    /**
     * parola chiave SOURCE per SET TABLE
     */
    private String source = " SOURCE ";

    /**
     * comando SHUTDOWN per chiudere il database
     */
    private String shutdown = " SHUTDOWN ";

    /**
     * opzione COMPACT per il comando SHUTDOWN
     */
    private String compact = " COMPACT ";


    /**
     * Costruttore completo.
     * <p/>
     * Non avendo modificatore, puo' essere invocato solo da
     * una classe interna al proprio package.<br>
     * Viene invocato da CreaIstanza.
     */
    DbSqlHsqldb() {
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
        this.setNomeClasseDriver("org.hsqldb.jdbcDriver");

        /* regola il subprotocollo jdbc */
        this.setSubprotocolloJDBC("hsqldb");

        /* regola la modalita' di funzionamento di default */
        this.setModoFunzionamentoDefault(MODO_STAND_ALONE);

        /* regola il tipo di accesso dati di default */
        this.setTipoAccessoDatiDefault(ACCESSO_DATI_FILE);

        /* regola il tipo di tavole di default */
        this.setTipoTavoleDefault(TAVOLE_CACHED);

        /* regola il separatore di default per le tavole TEXT */
        this.setSepTextDefault("\\t");

        /* regola il percorso dati di default */
        this.setPercorsoDatiDefault(Lib.Sist.getDirDati());

        /* regola la porta di default */
        this.setPortaDefault(9001);

        /* regola l'utente di default */
        this.setLoginDefault("sa");

        /*
         * Regolazione finale dei dati del database prima della inizializzazione.
         * Deve essere invocato obbligatoriamente da ogni
         * sottoclasse concreta alla fine del metodo Inizia().
         */
        super.regolaDatiDb();

    }// fine del metodo inizia


    /**
     * Regolazione dei comandi Sql.
     * <p/>
     * Sovrascrive il metodo della superclasse
     */
    protected void regolaComandi() {
        super.regolaComandi();
        this.keyTesto = " LONGVARCHAR ";
    }


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
        this.setInterpreteStruct(new InterpreteStructHsqldb(this));

        /* sostituisce l'interprete per le query di modifica */
        this.setInterpreteQm(new InterpreteQmHsqldb(this));

    } // fine del metodo


    /**
     * Crea una nuova connessione a questo database.
     * <p/>
     * Sovrascrive il metodo della superclasse.<br>
     * In HSQLDB in modalita' stand-alone, l'apertura della prima connessione
     * implica l'accensione del motore di database.<br>
     * Quindi, ad ogni nuova connessione regoliamo il flag
     * motoreAcceso a true.<br>
     *
     * @return la connessione appena creata
     */
    public Connessione creaConnessione() {
        /* variabili e costanti locali di lavoro */
        Connessione conn = null;

        try { // prova ad eseguire il codice

            /* crea una nuova connessione JDBC per il modulo */
            conn = super.creaConnessione();

            /* se e' in modo stand-alone, regola il flag motoreAccesso a true */
            if (this.getModoFunzionamento() == Db.MODO_STAND_ALONE) {
                this.setMotoreAcceso(true);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
        /* valore di ritorno */
        return conn;
    }


    /**
     * Ritorna la stringa rappresentante l'Url per una
     * connessione JDBC al database.
     * <p/>
     * Sovrascrive il metodo della superclasse
     *
     * @param conn la connessione per la quale ricavare l'url
     *
     * @return l'Url per la connessione
     */
    public String getStringaUrl(Connessione conn) {
        /* variabili e costanti locali di lavoro */
        String url = "";

        try {    // prova ad eseguire il codice

            /*
             * invoca il metodo sovrascritto che regola la connessione
             * (solo per eseguire procedure comuni, qui non uso la
             * stringa restituita da metodo)
             */
            super.getStringaUrl(conn);
            url = this.getStringaBaseProtocollo();

            /* determina la stringa in funzione del modo di funzionamento */
            switch (this.getModoFunzionamento()) {
                case MODO_CLIENT_SERVER:
                    url += this.getUrlServer();
                    break;
                case MODO_STAND_ALONE:
                    url += this.getUrlStandAlone();
                    break;
                default: // caso non definito
                    throw new Exception("Modo di funzionamento non supportato.");
            } // fine del blocco switch

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return url;
    }


    /**
     * Ritorna la stringa rappresentante l'Url per la
     * connessione JDBC al database in modalita' Client-Server.
     * <p/>
     * Si basa sui dati impostati nella Connessione
     *
     * @return l'Url per la connessione
     */
    private String getUrlServer() {
        /* variabili e costanti locali di lavoro */
        String url = "";
        ConnessioneDati dati = null;
        String proto = "";
        String host = "";
        String porta = "";
        String database = "";
        String s = "";
        int i = 0;

        try {    // prova ad eseguire il codice

            /* recupera i dati per la connessione */
            dati = this.getConnessione().getDatiConnessione();

            /* stringa del protocollo */
            proto += PROTO_HSQL + ":";

            /* indirizzo dell'host */
            s = dati.getHost();
            if (Lib.Testo.isValida(s)) {
                host += "//" + s;
            }// fine del blocco if

            /* numero della porta */
            i = dati.getPorta();
            if (i != 0) {
                porta += ":" + i;
            }// fine del blocco if

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
     * Ritorna la stringa rappresentante l'Url per la
     * connessione JDBC al database in modalita' Stand-alone.
     * <p/>
     * Si basa sui dati impostati nella Connessione
     *
     * @return l'Url per la connessione
     */
    private String getUrlStandAlone() {
        /* variabili e costanti locali di lavoro */
        String url = "";
        String filePath;
        String proto = "";
        String t = null;

        try {    // prova ad eseguire il codice

            /* stringa del protocollo */
            switch (this.getTipoAccessoDati()) {
                case Db.ACCESSO_DATI_FILE:
                    proto += PROTO_FILE;
                    filePath = this.getDbFilePath();
                    url += proto + filePath;
                    break;
                case Db.ACCESSO_DATI_MEMORIA:
                    proto += PROTO_MEM;
                    url += proto + this.getNomeDatabase();
                    break;
                case Db.ACCESSO_DATI_RISORSA:
                    proto += PROTO_RES;
                    filePath = this.getDbFilePath();
                    url += proto + filePath;
                    break;
                default: // caso non definito
                    t = "Tipo di accesso ai dati non supportato: ";
                    t += this.getTipoAccessoDati();
                    throw new Exception(t);
            } // fine del blocco switch

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return url;
    }


    /**
     * Ritorna il percorso completo per il file del database.
     * <p/>
     *
     * @return il percorso per il file
     */
    private String getDbFilePath() {
        /* variabili e costanti locali di lavoro */
        String path = "";
        ConnessioneDati dati = null;
        String s = "";
        String percorso = "";
        String database = "";

        try {    // prova ad eseguire il codice

            /* recupera i dati per la connessione */
            dati = this.getConnessione().getDatiConnessione();

            /* percorso della cartella dati */
            s = this.getPercorsoDati();
            if (Lib.Testo.isValida(s)) {
                percorso += s;
            }// fine del blocco if

            /* nome del database */
            s = dati.getNomeDatabase();
            if (Lib.Testo.isValida(s)) {
                database += "/" + s;
            }// fine del blocco if

            path = percorso + database;

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return path;
    }


    /**
     * Ritorna la sezione di base del protocollo per l' Url.
     * <p/>
     * La stringa comprende:
     * - il protocollo Jdbc
     * - il subprotocollo Jdbc di HSQLDB
     *
     * @return la stringa di base del protocollo
     */
    private String getStringaBaseProtocollo() {
        /* variabili e costanti locali di lavoro */
        String stringa = "";
        String s = "";

        /* identificativo del protocollo Jdbc */
        stringa += this.getConnessioneJDBC().getJdbc() + ":";

        /* identificativo del subprotocollo */
        s = this.getSubprotocolloJDBC();
        if (Lib.Testo.isValida(s)) {
            stringa += s + ":";
        }// fine del blocco if

        /* valore di ritorno */
        return stringa;
    }


    /**
     * Ritorna la stringa rappresentante il comando CREATE TABLE
     * <p/>
     * In HSQLDB le tavole possono essere di tipo diverso
     * (MEMORY, CACHED, TEXT)
     * La stringa di creazione resa dipende dal tipo di tavole
     * previsto per questo database.
     *
     * @return la stringa di comando per la creazione di una tavola
     */
    public String getCreateTable() {
        /* variabili e costanti locali di lavoro */
        String stringa = "";

        try { // prova ad eseguire il codice
            stringa = this.getCreate();
            stringa += this.getStringaTipoTavola();
            stringa += this.getTable();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return stringa;
    }


    /**
     * Ritorna la stringa per l'opzione tipo tavola del
     * comando CREATE TABLE.
     * <p/>
     *
     * @return la stringa per il tipo di tavola
     */
    private String getStringaTipoTavola() {
        /* variabili e costanti locali di lavoro */
        String stringa = "";

        try {    // prova ad eseguire il codice
            switch (this.getTipoTavole()) {
                case Db.TAVOLE_MEMORY:
                    stringa = this.getMemory();
                    break;
                case Db.TAVOLE_CACHED:
                    stringa = this.getCached();
                    break;
                case Db.TAVOLE_TEXT:
                    stringa = this.getText();
                    break;
                case Db.TAVOLE_TEMP:
                    stringa = this.getTemp();
                    break;
                default: // caso non definito
                    throw new Exception("Tipo di tavola non supportato.");
            } // fine del blocco switch
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return stringa;
    }


    /**
     * Chiude il database.
     * <p/>
     * Solo se il Db sta funzionando in modalita' stand-alone<br>
     * Apre una connessione, esegue il comando Sql SHUTDOWN [COMPACT]<br>
     * Spegne il flag motoreAcceso nella superclasse<br>
     *
     * @return true se riuscito
     */
    public boolean shutdown() {
        /* variabili e costanti locali di lavoro */
        boolean riuscito = true;
        Connessione c = null;
        String cmd = "";

        try { // prova ad eseguire il codice

            if (this.getModoFunzionamento() == Db.MODO_STAND_ALONE) {

                if (this.isMotoreAcceso()) {
                    /* costruisce il comando */
                    cmd += this.getShutdown();
                    cmd += this.getCompact();

                    /* apre una nuova connessione e invia il comando al db */
                    c = this.creaConnessione();
                    c.open();
                    this.esegueUpdate(cmd, c);
                    c.close();

                    /* spegne il flag motore acceso nella superclasse */
                    super.shutdown();

                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return riuscito;
    }


    public String getDropConstraint() {
        return dropConstraint;
    }


    public String getTable() {
        return table;
    }


    public String getMemory() {
        return memory;
    }


    public String getCached() {
        return cached;
    }


    public String getTemp() {
        return temp;
    }


    public String getText() {
        return text;
    }


    public String getSource() {
        return source;
    }


    private String getShutdown() {
        return shutdown;
    }


    private String getCompact() {
        return compact;
    }

}// fine della classe
