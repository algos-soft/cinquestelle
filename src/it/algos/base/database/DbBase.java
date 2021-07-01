/**
 * Title:     DbBase
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      21-ott-2004
 */
package it.algos.base.database;

import it.algos.base.campo.base.Campo;
import it.algos.base.campo.tipodati.tipomemoria.TipoMemoria;
import it.algos.base.database.connessione.Connessione;
import it.algos.base.database.connessione.ConnessioneDati;
import it.algos.base.database.dati.Dati;
import it.algos.base.database.interprete.Interprete;
import it.algos.base.database.tipodati.TipoDati;
import it.algos.base.database.util.Funzione;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.modulo.Modulo;
import it.algos.base.pref.Pref;
import it.algos.base.query.Query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Implementazione astratta di un database generico.
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 21-ott-2004 ore 13.51.14
 */
public abstract class DbBase implements Db {

    /**
     * Flag - indica se l'oggetto e' gia' stato inizializzato
     */
    private boolean inizializzato = false;

    /**
     * tipologia di database
     */
    private int tipoDb = 0;

    /**
     * flag - indica se il motore di database e' acceso.
     * Usato solo per database interno
     * Acceso dal database specifico (normalmente alla prima connessione)
     * Spento dal database specifico dopo aver eseguito lo shutdown
     */
    private boolean motoreAcceso = false;

    /**
     * nome della classe da utilizzare come driver <br>
     * es. "org.postgresql.Driver"...
     */
    private String nomeClasseDriver = "";

    /**
     * subprotocollo del driver jdbc <br>
     * es. "postgresql" o "mysql" o "hsqldb"...
     */
    private String subprotocolloJDBC = "";

    /**
     * modalita' di funzionamento del database <br>
     * Client-server o Stand-alone<br>
     * vedi costanti in Db
     */
    private int modoFunzionamento = 0;

    /**
     * Tipo di accesso ai dati di un database Stand-alone
     * Dati su file o dati su risorsa (read-only)<br>
     * vedi costanti in Db
     */
    private int tipoAccessoDati = 0;

    /**
     * tipologia delle tavole <br>
     * vedi costanti in Db
     */
    private int tipoTavole = 0;

    /**
     * separatore di campo per le tavole TEXT <br>
     */
    private String sepText = "";

    /**
     * Percorso di accesso ai dati di un database Stand-alone
     * (percorso fino alla directory che contiene i file del database)
     * - Percorso assoluto per dati su file
     * - Percorso nel jar per dati su risorsa
     * Utilizzato per costruire l'URL della connessione.
     */
    private String percorsoDati = null;

    /**
     * Indirizzo dell'host di default
     */
    private String hostDefault = "";

    /**
     * Modo di funzionamento di default del database <br>
     */
    private int modoFunzionamentoDefault = 0;

    /**
     * Tipo di accesso ai dati di default del database <br>
     */
    private int tipoAccessoDatiDefault = 0;

    /**
     * Tipo di tavole di default del database <br>
     */
    private int tipoTavoleDefault = 0;

    /**
     * separatore di campo di default per le tavole TEXT <br>
     */
    private String sepTextDefault = "";

    /**
     * Percorso dati di default del database <br>
     */
    private String percorsoDatiDefault = "";

    /**
     * Porta di default del database <br>
     */
    private int portaDefault = 0;

    /**
     * Nome di default del database <br>
     */
    private String nomeDbDefault = "";

    /**
     * Utente di default del database <br>
     */
    private String loginDefault = "";

    /**
     * Password di default del database <br>
     */
    private String passwordDefault = "";

    /**
     * Oggetto contenente i dati per la connessione
     */
    private ConnessioneDati datiConnessione = null;

    /**
     * Connessione al database.<br>
     * Variabile interna di lavoro.<br>
     * Viene regolata passando il parametro all'ingresso
     * di alcuni metodi.<br>
     * Viene utilizzata dai metodi interni per mantenere
     * un riferimento alla connessione sulla quale stanno
     * operando.<br>
     * Questo rende la classe non thread-safe.<br>
     * Per rendere la classe thread-safe occorre eliminare
     * questa variabile e passare la connessione a cascata
     * ai metodi interni che ne hanno bisogno.<br>
     */
    private Connessione connessione = null;

    /**
     * Collezione dei tipi di dati disponibili
     * chiave: da interfaccia TipoDati
     * valore: oggetto tipoDati
     */
    protected HashMap tipiDati = null;

    /**
     * Collezione degli oggetti Funzione supportati dal database.
     * chiave: il codice chiave della funzione generica (da Funzione)
     * valore: l'oggetto Funzione corrispondente
     */
    private HashMap funzioni = null;

    /**
     * Interprete di una Query di tipo Selezione per il database
     */
    private Interprete interpreteQs = null;

    /**
     * Interprete di un Filtro per il database
     */
    private Interprete interpreteFiltro = null;

    /**
     * Interprete di un Ordine per il database
     */
    private Interprete interpreteOrdine = null;

    /**
     * Interprete di una Query di tipo Modifica per il database
     */
    private Interprete interpreteQm = null;

    /**
     * Interprete per la manipolazione della struttura del database
     */
    private Interprete interpreteStruct = null;

    /**
     * stringa generica per l'unione di tipo AND
     */
    protected String unioneAnd = "";

    /**
     * stringa generica per l'unione di tipo OR
     */
    protected String unioneOr = "";

    /**
     * stringa generica per l'unione di tipo AND NOT
     */
    protected String unioneAndNot = "";

    /**
     * stringa generica per la parentesi aperta
     */
    protected String parentesiAperta = "";

    /**
     * stringa generica per la parentesi chiusa
     */
    protected String parentesiChiusa = "";


    /**
     * Costruttore completo con parametri.
     */
    public DbBase() {
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
     * Regolazioni immediate di riferimenti e variabili. <br>
     * Metodo chiamato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {

        try { // prova ad eseguire il codice

            /* regola i dati di default per il database */
            this.regolaDefaultDatabase();

            /*
             * crea un oggetto contenente i dati da utilizzare
             * per la creazione di nuove connessioni.
             * l'oggetto e' inizialmente riempito con valori di default generici
             */
            this.datiConnessione = new ConnessioneDati(this);

            /**
             * Regola la modalita' di funzionamento
             */
            this.modoFunzionamento = this.getModoFunzionamentoDefault();

            /** Regola il tipo di accesso ai dati */
            this.tipoAccessoDati = this.tipoAccessoDatiDefault;

            /**
             * Regola il tipo di tavole
             */
            this.tipoTavole = this.getTipoTavoleDefault();

            /**
             * Regola il separatore di campo per le tavole TEXT
             */
            this.sepText = this.getSepTextDefault();

            /**
             * Regola il percorso di registrazione dati del database
             */
            this.percorsoDati = this.getPercorsoDatiDefault();

            /* crea la collezione per i tipi archivio */
            this.tipiDati = new HashMap();

            /* crea la collezione delle funzioni */
            this.funzioni = new HashMap();

            /* regola le variabili per i comandi */
            this.regolaComandi();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }// fine del metodo inizia


    /**
     * Regolazione dei comandi.
     * <p/>
     */
    private void regolaComandi() {
        this.unioneAnd = Db.AND;
        this.unioneOr = Db.OR;
        this.unioneAndNot = Db.AND_NOT;
        this.parentesiAperta = Db.PARENTESI_APERTA;
        this.parentesiChiusa = Db.PARENTESI_CHIUSA;
    }


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
     * *
     *
     * @return true se l'inizializzazione ha avuto successo <br>
     */
    public boolean inizializza() {

        if (this.isInizializzato() == false) {

            /*
             * Crea la collezione dei tipi dati per il database
             */
            this.creaTipiDati();

            /*
             * Regola gli interpreti dei comandi
             * implementato nelle sottoclassi
             */
            this.regolaInterpreti();

            /*
             * Regola il flag inizializzato
             */
            this.setInizializzato(true);

        }// fine del blocco if

        /* valore di ritorno */
        return true;
    }


    /**
     * Riempie la collezione dei tipi archivio per il Db.
     * <p/>
     * Sovrascritto dalle sottoclassi.
     * Riempie la collezione dei tipi archivio con gli oggetti
     * TipoArchivio supportati dal database.<br>
     * chiave: classe Java gestita dal tipo archivio
     * valore: oggetto TipoDati
     */
    protected void creaTipiDati() {
    }


    /**
     * Regola gli interpreti per il Db.
     * <p/>
     * Sovrascritto dalle sottoclassi.
     * Crea e assegna al Db le istanze delle classi che dovranno
     * trasformare gli oggetti in comandi per il Db.<br>
     * Chiamato da Inizializza.
     */
    protected void regolaInterpreti() {
    }


    /**
     * Regola i dati di default per il database.
     * <p/>
     * Invocato dal ciclo Inizia.
     */
    private void regolaDefaultDatabase() {

        try {    // prova ad eseguire il codice

            /* dati di default per la connessione */
            this.setHostDefault("localhost");
            this.setNomeDbDefault("algos");
            this.setPortaDefault(0);
            this.setLoginDefault("");
            this.setPasswordDefault("");

            /* dati di default generali */
            this.setModoFunzionamentoDefault(Db.MODO_STAND_ALONE);
            this.setPercorsoDatiDefault(Lib.Sist.getDirDati());
            this.setSepTextDefault("");
            this.setTipoAccessoDatiDefault(Db.ACCESSO_DATI_FILE);
            this.setTipoTavoleDefault(Db.TAVOLE_CACHED);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

    }


    /**
     * Regola i dati del db prima della inizializzazione.
     * <p/>
     * Deve essere invocato obbligatoriamente da ogni
     * sottoclasse concreta alla fine del metodo Inizia().
     * I dati per la connessione
     * vengono regolati usando i dati di default e i dati di Preferenze.
     * Se esiste il dato in Preferenze, ha la precedenza sul dato di default.
     */
    protected void regolaDatiDb() {
        /* variabili e costanti locali di lavoro */
        String host;
        int porta;
        String database;
        String login;
        String password;
        int modo;
        int tipoTavole;
        String sep;
        String percorso;

        try {    // prova ad eseguire il codice

            /*
             * regolazione iniziale dei dati della connessione
             * - prima usa i dati di default del database
             * - poi, se le preferenze sono disponibili,
             *   usa eventuali dati esistenti nelle preferenze
             */
            this.setHost(this.getHostDefault());
            this.setPorta(this.getPortaDefault());
            this.setNomeDatabase(this.getNomeDbDefault());
            this.setLogin(this.getLoginDefault());
            this.setPassword(this.getPasswordDefault());
            this.setModoFunzionamento(this.getModoFunzionamentoDefault());
            this.setTipoTavole(this.getTipoTavoleDefault());
            this.setSepText(this.getSepTextDefault());
            this.setPercorsoDati(this.getPercorsoDatiDefault());

            /* regola l'host*/
            host = Pref.DB.indirizzo.str();
            if (Lib.Testo.isValida(host)) {
                this.setHost(host);
            }// fine del blocco if

            /* regola la porta */
            porta = Pref.DB.porta.intero();
            if (porta > 0) {
                this.setPorta(porta);
            }// fine del blocco if

            /* regola il nome del database */
            database = Pref.DB.archivio.str();
            if (Lib.Testo.isValida(database)) {
                this.setNomeDatabase(database);
            }// fine del blocco if

            /* regola il login */
            login = Pref.DB.utente.str();
            if (Lib.Testo.isValida(login)) {
                this.setLogin(login);
            }// fine del blocco if

            /* regola la password */
            password = Pref.DB.password.str();
            if (Lib.Testo.isValida(password)) {
                this.setPassword(password);
            }// fine del blocco if

            /* regola la modalita' di funzionamento stand-alone o client-server */
            modo = (Integer)Pref.DB.modo.comboOgg();
            if (modo != 0) {
                this.setModoFunzionamento(modo);
            }// fine del blocco if

            /* regola la tipologia di tavole del database */
            tipoTavole = (Integer)Pref.DB.tavole.comboOgg();
            if (tipoTavole != 0) {
                this.setTipoTavole(tipoTavole);
            }// fine del blocco if

            /* regola il separatore da utilizzare per le tavole TEXT */
            sep = Pref.DB.separatore.str();
            if (Lib.Testo.isValida(sep)) {
                this.setSepText(sep);
            }// fine del blocco if

            /* regola il percorso del file di dati */
            percorso = Pref.DB.directory.str();
            if (Lib.Testo.isValida(sep)) {
                this.setPercorsoDati(percorso);
            }// fine del blocco if


        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Carica una selezione di record.
     * <p/>
     * Sovrascritto nelle sottoclassi.<br>
     * Per qualsiasi db, prima di eseguire la Query
     * occorre risolverla.<br>
     * I metodi nelle sottoclassi invocheranno il metodo
     * della superclasse prima di effettuare le elaborazioni
     * specifiche.<br>
     *
     * @param query informazioni per effettuare la selezione
     * @param conn non usato
     *
     * @return un oggetto dati
     */
    public Dati querySelezione(Query query, Connessione conn) {
        this.regolaQuery(query);
        return null;
    }


    /**
     * Esegue la modifica di uno o piu' record.
     * <p/>
     * La modifica puo' consistere in inserimento di nuovi record
     * o modifica o cancellazione di record esistenti.
     * <p/>
     * Per qualsiasi db, prima di eseguire la Query
     * occorre risolverla.<br>
     * I metodi nelle sottoclassi invocheranno il metodo
     * della superclasse prima di effettuare le elaborazioni
     * specifiche.<br>
     *
     * @param query informazioni per effettuare la modifica
     * @param conn non usato
     *
     * @return il numero di record interessati
     */
    public int queryModifica(Query query, Connessione conn) {
        this.regolaQuery(query);
        /* qui gli eventi */
        return 0;
    }


    /**
     * Risolve la query e converte i valori da Memoria ad Archivio.
     * <p/>
     */
    private void regolaQuery(Query query) {

        try { // prova ad eseguire il codice

            /*
             * risolve la Query (converte i nomi in Campi)
             * risolve i nomi a fronte del modulo della query
             */
            query.risolvi();

            /*
             * Trasporta i valori dei campi e dei filtri della query
             * da livello Memoria a livello Database
             */
            query.memoriaDb(this);

//            /*
//             * converte i valori della Query da livello
//             * Business Logic a livello Database
//             * converte i valori a fronte di questo database
//             */
//            query.bl2db(this);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Ritorna l'oggetto Funzione corrispondente a una data chiave.
     * <p/>
     *
     * @param codice il codice chiave della funzione generica (da interfaccia Funzione)
     *
     * @return l'oggetto Funzione
     */
    public Funzione getFunzione(String codice) {
        /* valore di ritorno */
        return (Funzione)funzioni.get(codice);
    }


    /**
     * Convertitore di valori da livello Memoria a livello Archivio.
     * <p/>
     * Sovrascritto dalle sottoclassi.
     *
     * @param tipoMemoria
     * @param valoreIn il valore da convertire
     *
     * @return il valore convertito
     */
    public Object memoriaArchivio(TipoMemoria tipoMemoria, Object valoreIn) {
        /* variabili e costanti locali di lavoro */
        Object valoreOut = null;

        valoreOut = valoreIn;

        /* valore di ritorno */
        return valoreOut;
    }


    /**
     * Ritorna una stringa che identifica un campo per il database.
     * <p/>
     * Sovrascritto dalle sottoclassi.
     *
     * @param campo il campo
     *
     * @return una stringa che identifica il campo per il Db
     */
    public String getStringaCampo(Campo campo) {
        /* valore di ritorno */
        return campo.getNomeInterno();
    }


    /**
     * Ritorna una stringa che identifica un campo per il database.
     * <p/>
     * La stringa e' qualificata con il nome della tavola (tavola.campo)
     * Sovrascritto dalle sottoclassi.
     * Nella classe base, rimanda a getStringaCampo()
     *
     * @param campo il campo
     *
     * @return una stringa qualificata che identifica il campo per il Db
     */
    public String getStringaCampoQualificata(Campo campo) {
        /* valore di ritorno */
        return this.getStringaCampo(campo);
    }


    /**
     * Ritorna il tipo dati del Db in grado di gestire i valori
     * di un dato campo.
     * <p/>
     *
     * @param campo il campo per il quale cercare il corrispondente tipo dati
     *
     * @return il TipoDati del db che gestisce i valori del campo.
     */
    public TipoDati getTipoDati(Campo campo) {
        /* variabili e costanti locali di lavoro */
        TipoDati tipo = null;
        int chiave = 0;
        String t = "";

        try {    // prova ad eseguire il codice

            /* recupera la chiave del tipo DB dal campo */
            chiave = campo.getCampoDati().getChiaveTipoDatiDb();

            /* recupera il tipo dati Sql che gestisce la classe */
            tipo = this.getTipoDati(chiave);

            /* verifica se e' stato trovato un tipo dati
             * che gestisce la classe */
            if (tipo == null) {
                t += "Il database non ha un tipo dati in grado di ";
                t += "gestire il campo \n" + campo.getNomeInterno();
                throw new Exception(t);
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return tipo;

    }


    /**
     * Ritorna il tipo dati del Db in grado di gestire una
     * data classe di business logic.
     * <p/>
     *
     * @param classe la classe di business logic da gestire
     *
     * @return il TipoDati del db che gestisce la classe.
     */
    public TipoDati getTipoDati(Class classe) {
        /* variabili e costanti locali di lavoro */
        TipoDati tipo = null;
        Iterator i = null;
        TipoDati unTipo = null;
        Class unaClasse = null;

        try {    // prova ad eseguire il codice

            /* spazzola i tipi dati del Db e ritorna
             * il primo che gestisce la classe data */
            /* recupera la chiave del tipo DB dal campo */
            i = this.getTipiDati().values().iterator();
            while (i.hasNext()) {
                unTipo = (TipoDati)i.next();
                unaClasse = unTipo.getClasseBl();
                if (unaClasse == classe) {
                    tipo = unTipo;
                    break;
                }// fine del blocco if
            } /* fine del blocco while */

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return tipo;
    }


    /**
     * Ritorna il tipo dati del Db corrispondente a una data chiave.
     * <p/>
     *
     * @param chiave la chiave da cercare
     *
     * @return l'oggetto TipoDati del Db corrispondente
     */
    private TipoDati getTipoDati(Integer chiave) {
        /* variabili e costanti locali di lavoro */
        TipoDati tipo = null;
        Object oggetto = null;

        try { // prova ad eseguire il codice
            oggetto = this.getTipiDati().get(chiave);
            if (oggetto != null) {
                tipo = (TipoDati)oggetto;
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        return tipo;
    }


    /**
     * Ritorna il tipo dati del Db corrispondente a una data chiave.
     * <p/>
     *
     * @param chiave la chiave da cercare
     *
     * @return l'oggetto TipoDati del Db corrispondente
     */
    public TipoDati getTipoDati(int chiave) {
        return this.getTipoDati(new Integer(chiave));
    }


    /**
     * Ritorna true se un tipo dati e' di tipo testo.
     * <p/>
     * E' di tipo Testo se la classe di business logic e' String.
     *
     * @param chiave la chiave del tipo dati da cercare
     *
     * @return true se e' di tipo testo
     */
    public boolean isTipoTesto(int chiave) {
        /* variabili e costanti locali di lavoro */
        boolean isTesto = false;
        TipoDati td = null;

        try {    // prova ad eseguire il codice
            td = this.getTipoDati(chiave);
            if (td != null) {
                isTesto = td.isTipoTesto();
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return isTesto;
    }


    public int getTipoDb() {
        return tipoDb;
    }


    public void setTipoDb(int tipoDb) {
        this.tipoDb = tipoDb;
    }


    /**
     * Ritorna lo stato di accensione del motore di database
     * <p/>
     * Significativo solo per database stand-alone
     *
     * @return true se il motore di database e' acceso
     */
    public boolean isMotoreAcceso() {
        return motoreAcceso;
    }


    /**
     * Regola lo stato di accensione del motore di database
     * <p/>
     *
     * @param motoreAcceso lo stato di accensione del motore
     */
    protected void setMotoreAcceso(boolean motoreAcceso) {
        this.motoreAcceso = motoreAcceso;
    }


    /**
     * Ritorna il nome della classe del driver
     * <p/>
     *
     * @return il nome della classe Driver
     */
    public String getNomeClasseDriver() {
        return nomeClasseDriver;
    }


    protected void setNomeClasseDriver(String nomeClasseDriver) {
        this.nomeClasseDriver = nomeClasseDriver;
    }


    public String getSubprotocolloJDBC() {
        return subprotocolloJDBC;
    }


    protected void setSubprotocolloJDBC(String subprotocolloJDBC) {
        this.subprotocolloJDBC = subprotocolloJDBC;
    }


    /**
     * Ritorna il modo di funzionamento del database
     * <p/>
     * Opzioni possibili: MODO_SERVER o MODO_STAND_ALONE
     *
     * @return il modo di funzionamento
     *
     * @see Db
     */
    public int getModoFunzionamento() {
        return modoFunzionamento;
    }


    /**
     * Regola il modo di funzionamento del database
     * <p/>
     * Opzioni possibili: MODO_SERVER o MODO_STAND_ALONE
     * Vedi costanti in Db
     */
    public void setModoFunzionamento(int modoFunzionamento) {
        this.modoFunzionamento = modoFunzionamento;
    }


    protected int getTipoAccessoDati() {
        return tipoAccessoDati;
    }


    /**
     * Regola il tipo di accesso ai dati
     * <p/>
     * Opzioni possibili: ACCESSO_DATI_FILE o ACCESSO_DATI_RISORSA<br>
     * Vedi costanti in Db<br>
     * Significativo solo per database stand-alone.<br>
     */
    public void setTipoAccessoDati(int tipoAccessoDati) {
        this.tipoAccessoDati = tipoAccessoDati;
    }


    /**
     * Ritorna il tipo di tavole del database
     */
    public int getTipoTavole() {
        return tipoTavole;
    }


    /**
     * Regola il tipo di tavole
     * <p/>
     * Opzioni possibili: TAVOLE_MEMORY, TAVOLE_CACHED o TAVOLE_TEXT
     * Vedi costanti in Db
     */
    public void setTipoTavole(int tipoTavole) {
        this.tipoTavole = tipoTavole;
    }


    /**
     * Ritorna il separatore di campo per le tavole TEXT
     * <p/>
     *
     * @return la stringa del separatore
     */
    public String getSepText() {
        return sepText;
    }


    /**
     * Regola il separatore di campo per le tavole TEXT
     * <p/>
     *
     * @param sepText la stringa del separatore
     */
    public void setSepText(String sepText) {
        this.sepText = sepText;
    }


    protected String getPercorsoDati() {
        return percorsoDati;
    }


    /**
     * Regola il percorso dove il database registra i dati.
     * <p/>
     */
    public void setPercorsoDati(String percorsoDati) {
        this.percorsoDati = percorsoDati;
    }


    private String getHostDefault() {
        return hostDefault;
    }


    protected void setHostDefault(String hostDefault) {
        this.hostDefault = hostDefault;
    }


    private int getModoFunzionamentoDefault() {
        return modoFunzionamentoDefault;
    }


    protected void setModoFunzionamentoDefault(int modoFunzionamentoDefault) {
        this.modoFunzionamentoDefault = modoFunzionamentoDefault;
    }


    private int getTipoAccessoDatiDefault() {
        return tipoAccessoDatiDefault;
    }


    protected void setTipoAccessoDatiDefault(int tipoAccessoDatiDefault) {
        this.tipoAccessoDatiDefault = tipoAccessoDatiDefault;
    }


    private int getTipoTavoleDefault() {
        return tipoTavoleDefault;
    }


    protected void setTipoTavoleDefault(int tipoTavoleDefault) {
        this.tipoTavoleDefault = tipoTavoleDefault;
    }


    private String getSepTextDefault() {
        return sepTextDefault;
    }


    protected void setSepTextDefault(String sepTextDefault) {
        this.sepTextDefault = sepTextDefault;
    }


    /**
     * Ritorna il percorso dati di default del database.
     * <p/>
     *
     * @return il percorso dati di default del database
     */
    public String getPercorsoDatiDefault() {
        return percorsoDatiDefault;
    }


    protected void setPercorsoDatiDefault(String percorsoDatiDefault) {
        this.percorsoDatiDefault = percorsoDatiDefault;
    }


    /**
     * Ritorna la porta di default del database.
     * <p/>
     *
     * @return la porta di default del database
     */
    public int getPortaDefault() {
        return portaDefault;
    }


    protected void setPortaDefault(int portaDefault) {
        this.portaDefault = portaDefault;
    }


    /**
     * Ritorna il nome di default del database.
     * <p/>
     *
     * @return il nome di default del database
     */
    public String getNomeDbDefault() {
        return nomeDbDefault;
    }


    protected void setNomeDbDefault(String nomeDbDefault) {
        this.nomeDbDefault = nomeDbDefault;
    }


    /**
     * Ritorna il login di default del database.
     * <p/>
     *
     * @return il login di default del database
     */
    public String getLoginDefault() {
        return loginDefault;
    }


    protected void setLoginDefault(String loginDefault) {
        this.loginDefault = loginDefault;
    }


    /**
     * Ritorna la password di default del database.
     * <p/>
     *
     * @return la password di default del database
     */
    public String getPasswordDefault() {
        return passwordDefault;
    }


    protected void setPasswordDefault(String passwordDefault) {
        this.passwordDefault = passwordDefault;
    }


    protected ConnessioneDati getDatiConnessione() {
        return datiConnessione;
    }


    public void setHost(String host) {
        this.getDatiConnessione().setHost(host);
    }


    public void setPorta(int porta) {
        this.getDatiConnessione().setPorta(porta);
    }


    /**
     * Assegna il nome del database.
     * <p/>
     *
     * @param nome il nome del database
     */
    public void setNomeDatabase(String nome) {
        this.getDatiConnessione().setNomeDatabase(nome);
    }


    /**
     * Recupera il nome del database.
     * <p/>
     *
     * @return il nome del database
     */
    public String getNomeDatabase() {
        return this.getDatiConnessione().getNomeDatabase();
    }


    public void setLogin(String login) {
        this.getDatiConnessione().setLogin(login);
    }


    public void setPassword(String password) {
        this.getDatiConnessione().setPassword(password);
    }


    public Connessione getConnessione() {
        return connessione;
    }


    public void setConnessione(Connessione connessione) {
        this.connessione = connessione;
    }


    /**
     * Ritorna la collezione dei tipi dati gestiti da questo db
     * <p/>
     *
     * @return la collezione dei tipi dati
     *         chiave: da interfaccia TipoDati
     *         valore: oggetto TipoDati
     */
    public HashMap getTipiDati() {
        return tipiDati;
    }


    private void setTipiDati(HashMap tipiDati) {
        this.tipiDati = tipiDati;
    }


    public HashMap getFunzioni() {
        return funzioni;
    }


    private void setFunzioni(HashMap funzioni) {
        this.funzioni = funzioni;
    }


    public Interprete getInterpreteQs() {
        return interpreteQs;
    }


    public void setInterpreteQs(Interprete interpreteQs) {
        this.interpreteQs = interpreteQs;
    }


    public Interprete getInterpreteFiltro() {
        return interpreteFiltro;
    }


    public void setInterpreteFiltro(Interprete interpreteFiltro) {
        this.interpreteFiltro = interpreteFiltro;
    }


    public Interprete getInterpreteOrdine() {
        return interpreteOrdine;
    }


    public void setInterpreteOrdine(Interprete interpreteOrdine) {
        this.interpreteOrdine = interpreteOrdine;
    }


    public Interprete getInterpreteQm() {
        return interpreteQm;
    }


    public void setInterpreteQm(Interprete interpreteQm) {
        this.interpreteQm = interpreteQm;
    }


    public Interprete getInterpreteStruct() {
        return interpreteStruct;
    }


    public void setInterpreteStruct(Interprete interpreteStruct) {
        this.interpreteStruct = interpreteStruct;
    }


    public String getUnioneAnd() {
        return unioneAnd;
    }


    public String getUnioneOr() {
        return unioneOr;
    }


    public String getUnioneAndNot() {
        return unioneAndNot;
    }


    public String getParentesiAperta() {
        return parentesiAperta;
    }


    public String getParentesiChiusa() {
        return parentesiChiusa;
    }


    /**
     * Crea una tavola sul database.
     * <p/>
     * La tavola viene creata solo se non gia' esistente.
     * Sovrascritto nelle sottoclassi.
     * Nella classe base, non fa  nulla.
     * La tavola viene creata solo se non gia' esistente.
     *
     * @param modulo il modulo per il quale creare la tavola
     * @param conn la connessione da utilizzare
     *
     * @return true se esistente o creata correttamente
     *         false se non creata
     */
    public boolean creaTavola(Modulo modulo, Connessione conn) {
        return true;
    }


    /**
     * Allinea una colonna del Db in base al corrispondente
     * campo del Modello.
     * <p/>
     * Sovrascritto nelle sottoclassi.
     * Nella classe base, non fa  nulla.
     *
     * @param campo il campo per il quale allineare la colonna
     * @param conn la connessione da utilizzare
     *
     * @return true se ha eseguito correttamente
     */
    public boolean allineaCampo(Campo campo, Connessione conn) {
        return true;
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
        return null;
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
        return null;
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
    public abstract boolean eliminaColonna(Campo campo, Connessione conn);


    /**
     * Attiva o disattiva l'integrità referenziale.
     * <p/>
     *
     * @param flag di attivazione
     *
     * @return true se riuscito
     */
    public boolean setReferentialIntegrity(boolean flag, Connessione conn) {
        return false;
    }


    /**
     * Controlla se l'oggetto e' inizializzato.
     * <p/>
     *
     * @return true se inizializzato
     */
    public boolean isInizializzato() {
        return inizializzato;
    }


    private void setInizializzato(boolean inizializzato) {
        this.inizializzato = inizializzato;
    }


    /**
     * Controlla il database e' disponibile all'apertura di connessioni.
     * <p/>
     * Sovrascritto dalle sottoclassi. <br>
     * Prova ad aprire e chiudere una connessione. <br>
     *
     * @return true se il database e' disponibile all'apertura di connessioni.
     */
    public boolean isDisponibileConnessioni() {
        return true;
    }


    /**
     * Chiude il database.
     * <p/>
     * Sovrascritto dalle sottoclassi.
     * Consolida i dati su disco, libera i lock sui file, esegue
     * altre eventuali finalizzazioni.
     *
     * @return true se riuscito
     */
    public boolean shutdown() {
        this.setMotoreAcceso(false);
        return true;
    }

}// fine della classe
