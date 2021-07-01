/**
 * Copyright: Copyright (c) 2006
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      15-feb-2007
 */
package it.algos.base.pref;

import it.algos.base.config.Config;
import it.algos.base.database.Db;
import it.algos.base.database.DbFactory;
import it.algos.base.database.connessione.ConnessioneJDBC;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.libreria.LibResultSet;
import it.algos.base.libreria.Libreria;
import it.algos.base.progetto.Progetto;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Map;

/**
 * Gestore delle preferenze su database SQL
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 15-feb-2007 ore 15.46.15
 */
public final class PrefStoreSql extends PrefStore {

    private static final String NOME_TAVOLA = "PREF";

    private static final String NOME_CAMPO_CHIAVE = "CHIAVE";

    private static final String NOME_CAMPO_VALORE = "VALSTRINGA";

    /* database di riferimento */
    private Db db;


    /**
     * Costruttore completo
     * <p/>
     *
     * @param comune true se è uno store comune false se è locale
     */
    public PrefStoreSql(boolean comune) {
        /* rimanda al costruttore della superclasse */
        super(comune);

        try { // prova ad eseguire il codice

            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo costruttore completo


    /**
     * Regolazioni statiche di riferimenti e variabili.
     * </p>
     * Metodo invocato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        /* variabili e costanti locali di lavoro */
        Db db;

        try { // prova ad eseguire il codice
            db = this.creaDb();
            db.inizializza();
            this.setDb(db);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo inizia


    /**
     * Inizializza l'oggetto.
     * <p/>
     * Crea un database ed una connessione <br>
     * Controlla che esistano sul server tutte le preferenze comuni <br>
     * Se no, le crea <br>
     */
    public void inizializza() {
        /* variabili e costanti locali di lavoro */
        ConnessioneJDBC conn;
        boolean esiste;
        String stringa;

        try { // prova ad eseguire il codice

            conn = this.getConnessione();
            conn.open();

            esiste = conn.isEsisteTavola(NOME_TAVOLA);
            if (!esiste) {
                stringa =
                        "create table " +
                                NOME_TAVOLA +
                                " (" +
                                NOME_CAMPO_CHIAVE +
                                " VARCHAR(20), " +
                                NOME_CAMPO_VALORE +
                                " VARCHAR(20))";
                conn.esegueUpdate(stringa);
            }// fine del blocco if

            conn.close();

        } catch (Exception unErrore) { // intercetta l'errore
            new Errore(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Carica le preferenze comuni (da un server).
     * <p/>
     * Metodo sovrascritto nelle sottoclassi <br>
     *
     * @return vero se il caricamento è riuscito
     */
    @Override
    public boolean carica() {
        /* variabili e costanti locali di lavoro */
        ConnessioneJDBC conn;
        PrefGruppi pref;
        PrefWrap wrap;
        int tot;
        String chiave;
        String stringa;
        ResultSet rs;
        Object valore;
        String valStringa;
        ArrayList<String> lista;
        Pref.TipoDati tipo;

        try { // prova ad eseguire il codice

            conn = this.getConnessione();
            conn.open();

            /* traverso tutta la collezione */
            for (Map.Entry mappa : Pref.getPref().entrySet()) {
                pref = (PrefGruppi)mappa.getValue();
                chiave = (String)mappa.getKey();
                wrap = pref.getWrap();
                tipo = wrap.getTipoDati();

                stringa = "select ";
                stringa += NOME_CAMPO_VALORE;
                stringa += " from ";
                stringa += NOME_TAVOLA;
                stringa += this.getFiltroChiave(chiave);
                rs = conn.esegueSelect(stringa);
                tot = LibResultSet.quanteRighe(rs);

                if (tot == 1) {
                    valStringa = (String)LibResultSet.getValoreCella(rs, 1, 1);
                    valore = this.getValorePref(wrap.getTipoDati(), valStringa);

                    if (tipo == Pref.TipoDati.stringa) {
                        lista = Lib.Array.creaLista(valStringa);
                        valore = lista.get(0);
                        wrap.setValoriUsati(lista);
                    }// fine del blocco if

                    wrap.setValore(valore);
                }// fine del blocco if

            } // fine del ciclo for-each

            conn.close();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch


        return true;
    }


    /**
     * Registra le preferenze comuni
     * <p/>
     * - spazzola tutte le preferenze
     * - registra quelle che sono da registrare
     * - cancella quelle che non sono da registrare
     *
     * @return vero se la registrazione è stata effettuata
     */
    public boolean registra() {
        /* variabili e costanti locali di lavoro */
        ConnessioneJDBC conn;
        PrefGruppi pref;
        PrefWrap wrap;
        String chiave;
        String stringa;
        String val;
        boolean registro;
        int ritorno = 0;

        try { // prova ad eseguire il codice

            conn = this.getConnessione();
            conn.open();

            /* traverso tutta la collezione  e registro solo le preferenze
             * con valore diverso dal default */
            for (Map.Entry mappa : Pref.getPref().entrySet()) {

                pref = (PrefGruppi)mappa.getValue();
                chiave = (String)mappa.getKey();
                wrap = pref.getWrap();

                registro = this.isDaRegistrare(chiave, pref);

                if (registro) {       // è da registrare, se non esiste va creata

                    /* si accerta che la preferenza esista
                     * se non esiste la crea ora */
                    if (!this.isEsisteRecord(chiave, conn)) {
                        stringa =
                                "insert into " +
                                        NOME_TAVOLA +
                                        " (" +
                                        NOME_CAMPO_CHIAVE +
                                        ") values ('" +
                                        chiave +
                                        "')";
                        ritorno = conn.esegueUpdate(stringa);
                    }// fine del blocco if

                    /* scrive il valore stringa nel record */
                    val = this.getValoreDaRegistrare(wrap);
                    stringa = "update " + NOME_TAVOLA + " set " + NOME_CAMPO_VALORE + "=";
                    stringa += "'";
                    stringa += val;
                    stringa += "'";
                    stringa += this.getFiltroChiave(chiave);
                    ritorno = conn.esegueUpdate(stringa);

                } else {      // non è da registrare, se esiste va cancellata

                    if (this.isEsisteRecord(chiave, conn)) {
                        stringa = "delete from " + NOME_TAVOLA + this.getFiltroChiave(chiave);
                        ritorno = conn.esegueUpdate(stringa);
                    }// fine del blocco if


                }// fine del blocco if-else


            } // fine del ciclo for-each

            conn.close();


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch


        return true;
    }


    /**
     * Controlla se esiste un record con la chiave data.
     * <p/>
     *
     * @param chiave da controllare
     * @param conn la connessione da utilizzare
     *
     * @return true se esiste
     */
    private boolean isEsisteRecord(String chiave, ConnessioneJDBC conn) {
        /* variabili e costanti locali di lavoro */
        boolean esiste = false;
        String stringa;
        ResultSet rs;
        int quante;
        boolean opened = false;

        try {    // prova ad eseguire il codice

            conn = this.getConnessione();

            /* se la connessione è chiusa la apre */
            if (!conn.isOpen()) {
                conn.open();
                opened = true;
            }// fine del blocco if

            /* crea un filtro per isolare la preferenza in oggetto */
            stringa =
                    "select " +
                            NOME_CAMPO_CHIAVE +
                            " from " +
                            NOME_TAVOLA +
                            " where " +
                            NOME_CAMPO_CHIAVE +
                            "='" +
                            chiave +
                            "'";
            rs = conn.esegueSelect(stringa);
            quante = LibResultSet.quanteRighe(rs);
            if (quante > 0) {
                esiste = true;
            }// fine del blocco if

            /* se ha aperto la connessione la richiude */
            if (opened) {
                conn.close();
            }// fine del blocco if


        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return esiste;
    }


    /**
     * Crea e il db delle preferenze.
     * <p/>
     *
     * @return il database creato
     */
    private Db creaDb() {
        /* variabili e costanti locali di lavoro */
        Db db = null;
        String indirizzo;
        String nomePorta;
        String login;
        String nomeTipo;
        String nome;
        String pass;
        int tipo;
        int porta;
        String nomeDb;
        String percorso;
        String host;
        String nomeBase;

        try {    // prova ad eseguire il codice

            if (this.isComune()) {
                indirizzo = Config.getValore(Config.INDIRIZZO_DB_PREF_SHARED);
                nomePorta = Config.getValore(Config.PORTA_DB_PREF_SHARED);
                login = Config.getValore(Config.LOGIN_DB_PREF_SHARED);
                nomeTipo = Config.getValore(Config.TIPO_DB_PREF_SHARED);
                nome = Config.getValore(Config.NOME_DB_PREF_SHARED);
                pass = Config.getValore(Config.PASS_DB_PREF_SHARED);
            } else {
                indirizzo = Config.getValore(Config.INDIRIZZO_DB_PREF_LOCAL);
                nomePorta = Config.getValore(Config.PORTA_DB_PREF_LOCAL);
                login = Config.getValore(Config.LOGIN_DB_PREF_LOCAL);
                nomeTipo = Config.getValore(Config.TIPO_DB_PREF_LOCAL);
                nome = Config.getValore(Config.NOME_DB_PREF_LOCAL);
                pass = Config.getValore(Config.PASS_DB_PREF_LOCAL);
            }// fine del blocco if-else


            porta = Libreria.getInt(nomePorta);
            tipo = Libreria.getInt(nomeTipo);

            /* tipo di database - di default HSQLDB */
            if (tipo == 0) {
                db = DbFactory.crea(Db.SQL_HSQLDB);
            }// fine del blocco if
            db = DbFactory.crea(tipo);

            /* login del database
             * di default il login di default del database */
            if (!Lib.Testo.isValida(login)) {
                login = db.getLoginDefault();
            }// fine del blocco if
            db.setLogin(login);

            /* indirizzo di rete o directory del file dati del database
             * di default database monoutente con file dati nella dir Preferenze */
            if (Lib.Testo.isValida(indirizzo)) {
                if (this.isFileSystemAddress(indirizzo)) {  // indirizzo locale
                    host = "";
                    percorso = indirizzo;
                } else {    // indirizzo di rete
                    host = indirizzo;
                    percorso = "";
                }// fine del blocco if-else
            } else {
                host = "";
                percorso = Lib.Sist.getDirPreferenze();
            }// fine del blocco if-else
            db.setHost(host);
            db.setPercorsoDati(percorso);

            /* nome del database
    * di default è il nome del programma */
            if (!Lib.Testo.isValida(nome)) {
                nomeBase = Progetto.getNomePrimoModulo().toLowerCase();
                if (this.isComune()) {
                    nome = nomeBase + "_shared";
                } else {
                    nome = nomeBase + "_local";
                }// fine del blocco if-else
            }// fine del blocco if
            db.setNomeDatabase(nome);

            /* password del database
             * di default la password di default del database */
            if (!Lib.Testo.isValida(pass)) {
                pass = db.getPasswordDefault();
            }// fine del blocco if
            db.setPassword(pass);

            /* porta del database
             * di default la porta di default del database */
            if (porta == 0) {
                porta = db.getPortaDefault();
            }// fine del blocco if
            db.setPorta(porta);

            /* modo di funzionamento del database
             * se l'indirizzo è locale, è stand alone
             * se l'indirizzo è di rete, è client-server */
            if (this.isFileSystemAddress(indirizzo)) {
                db.setModoFunzionamento(Db.MODO_STAND_ALONE);
            } else {
                db.setModoFunzionamento(Db.MODO_CLIENT_SERVER);
            }// fine del blocco if-else


        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return db;
    }


    /**
     * Determina se un indirizzo è di file system o di rete.
     * <p/>
     * E' di file system se contiene slash o backslash
     *
     * @param address da controllare
     *
     * @return true se di file system false se di rete
     */
    private boolean isFileSystemAddress(String address) {
        /* variabili e costanti locali di lavoro */
        boolean isLocale = false;
        int idxSlash = 0;
        int idxBackSlash = 0;

        try {    // prova ad eseguire il codice
            idxSlash = address.indexOf("/");
            idxBackSlash = address.indexOf("\\");
            if ((idxSlash >= 0) || (idxBackSlash >= 0) || Lib.Testo.isVuota(address)) {
                isLocale = true;
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return isLocale;
    }


    /**
     * Ritorna la connessione al DB delle preferenze.
     * <p/>
     */
    private ConnessioneJDBC getConnessione() {
        /* variabili e costanti locali di lavoro */
        ConnessioneJDBC conn = null;
        Db db;
        boolean continua;


        try {    // prova ad eseguire il codice
            db = this.getDb();
            continua = (db != null);
            if (continua) {
                conn = (ConnessioneJDBC)db.getConnessione();
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return conn;
    }


    /**
     * Ritorna il valore corrispondente a una preferenza.
     * <p/>
     *
     * @param chiave chiave della preferenza
     *
     * @return valore convertito
     */
    public Object get(String chiave) {
        /* variabili e costanti locali di lavoro */
        Object valore = null;
        ConnessioneJDBC conn;
        String stringaSql;
        ResultSet rs = null;
        Object ogg;
        String valStr;
        PrefWrap wrap;
        Pref.TipoDati tipo;

        boolean continua;

        try { // prova ad eseguire il codice

            conn = this.getConnessione();
            continua = (conn != null);

            if (continua) {

                conn.open();

                /* crea la stringa per la query */
                stringaSql =
                        "select " +
                                NOME_CAMPO_VALORE +
                                " from " +
                                NOME_TAVOLA +
                                this.getFiltroChiave(chiave);
                rs = conn.esegueSelect(stringaSql);

                if (LibResultSet.quanteRighe(rs) > 0) {
                    ogg = LibResultSet.getValoreCella(rs, 1, 1);
                    valStr = Lib.Testo.getStringa(ogg);

                    wrap = this.getWrapper(chiave);
                    tipo = wrap.getTipoDati();
                    valore = this.getValorePref(tipo, valStr);
                }// fine del blocco if

                conn.close();

            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return valore;
    }


    /**
     * Ritorna la sezione filtro SQL per isolare il record con la chiave data.
     * <p/>
     *
     * @param chiave della preferenza
     *
     * @return la sezione filtro della stringa Sql
     */
    private String getFiltroChiave(String chiave) {
        return " where " + NOME_CAMPO_CHIAVE + "='" + chiave + "' ";
    }


    /**
     * Chiude lo store.
     * <p/>
     * Spegne il database
     */
    public void close() {
        try { // prova ad eseguire il codice
            this.getDb().shutdown();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    private Db getDb() {
        return db;
    }


    private void setDb(Db db) {
        this.db = db;
    }


}// fine della classe
