/**
 * Title:     ConnessioneDB
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      23-set-2004
 */
package it.algos.base.database.connessione;

import it.algos.base.campo.base.Campo;
import it.algos.base.database.dati.Dati;
import it.algos.base.database.sql.DbSql;
import it.algos.base.database.util.Funzione;
import it.algos.base.database.util.Operatore;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.modello.Modello;
import it.algos.base.modulo.Modulo;
import it.algos.base.query.Query;
import it.algos.base.query.QueryFactory;
import it.algos.base.query.campi.CampoQuery;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.query.filtro.FiltroFactory;
import it.algos.base.query.modifica.QueryDelete;
import it.algos.base.query.modifica.QueryInsert;
import it.algos.base.query.modifica.QueryUpdate;
import it.algos.base.query.ordine.Ordine;
import it.algos.base.query.selezione.QuerySelezione;
import it.algos.base.wrapper.CampoValore;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * </p>
 * Questa classe concreta: <ul>
 * <li>Implementa la gestione di una connessione con un database JDBC
 * </ul>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 23-set-2004 ore 12.12.36
 */
public final class ConnessioneJDBC extends ConnessioneBase {

    /**
     * costante per identificare il protocollo JDBC
     */
    private static final String JDBC = "jdbc";

    /**
     * connessione al database JDBC
     */
    private Connection connection = null;


    /**
     * Costruttore completo.
     * <p/>
     *
     * @param dbSql il database Sql proprietario di questa connessione
     */
    public ConnessioneJDBC(DbSql dbSql) {
        /* rimanda al costruttore della superclasse */
        super(dbSql);

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }// fine del metodo costruttore base


    /**
     * Regolazioni immediate di riferimenti e variabili. <br>
     * Metodo chiamato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
    }// fine del metodo inizia


    /**
     * Tenta di aprire la connessione con il database.
     *
     * @return true se riuscito
     */
    public boolean open() throws Exception {
        /* variabili e costanti locali di lavoro */
        boolean riuscito = false;
        Connection c = null;
        String url = null;
        String login = null;
        String pass = null;
        boolean continua = true;

        try { // prova ad eseguire il codice

            /* controlla che non sia già aperta */
            if (this.isOpen()) {
                continua = false;
            }// fine del blocco if

            /* recupera i dati per la connessione */
            if (continua) {
                url = this.getStringaUrl();
                login = this.getDatiConnessione().getLogin();
                pass = this.getDatiConnessione().getPassword();
            }// fine del blocco if

            /* carica il driver */
            if (continua) {
                if (!this.caricaDriver()) {
                    throw new Exception("Impossibile caricare il driver del database.");
                }// fine del blocco if
            }// fine del blocco if

            /* controlla la validità dell'URL */
            if (continua) {
                if (Lib.Testo.isVuota(url)) {
                    throw new Exception("Indirizzo del database non specificato.");
                }// fine del blocco if
            }// fine del blocco if

            /* apre la connessione */
            if (continua) {
                try { // prova ad eseguire il codice
                    c = DriverManager.getConnection(url, login, pass);
                } catch (Exception unErrore) { // intercetta l'errore
                }// fine del blocco try-catch
            }// fine del blocco if

            /* assegna il riferimento alla connessione aperta */
            if (continua) {
                if (c != null) {
                    this.setConnection(c);
                    this.setOpen(true);
                    riuscito = true;
                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return riuscito;
    }


    /**
     * Tenta di chiudere la connessione.<br>
     * Se la connessione non e' aperta, non fa nulla.<br>
     */
    public void close() {
        /* variabili e costanti locali di lavoro */
        Connection c = null;

        try {    // prova ad eseguire il codice
            if (this.isOpen()) {
                c = this.getConnection();
                if (c != null) {
                    c.close();
                    this.setOpen(false);
                }// fine del blocco if
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Controlla se la connessione � attiva e funzionante.
     * <p/>
     * Tenta di aprire la connessione e poi la richiude<br>
     *
     * @return true sel la connessione e' attiva e funzionante.
     */
    public boolean test() {
        /* variabili e costanti locali di lavoro */
        boolean ok = false;
        String url = null;
        String login = null;
        String pass = null;
        Connection c = null;

        try {    // prova ad eseguire il codice
            if (this.isOpen()) {
                ok = true;
            } else {
                try { // prova ad eseguire il codice
                    url = this.getStringaUrl();
                    login = this.getDatiConnessione().getLogin();
                    pass = this.getDatiConnessione().getPassword();
                    this.caricaDriver();
                    c = DriverManager.getConnection(url, login, pass);
                    c.close();
                    ok = true;
                } catch (Exception unErrore) { // intercetta l'errore
                    // non mostra messaggio di errore
                }// fine del blocco try-catch
            }// fine del blocco if-else
        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return ok;
    }


    /**
     * Carica il driver del database.
     *
     * @return true se riuscito.
     */
    private boolean caricaDriver() {
        /* variabili e costanti locali di lavoro */
        boolean riuscito = false;
        String nomeClasseDriver = null;

        try {    // prova ad eseguire il codice

            /* recupera il nome classe del driver */
            nomeClasseDriver = this.getDb().getNomeClasseDriver();
            if (nomeClasseDriver == null) {
                throw new Exception("Nome classe driver non specificato.");
            }// fine del blocco if

            /* istanzia la classe del driver e la registra nel DriverManager */
            try {    // prova ad eseguire il codice
                Class.forName(nomeClasseDriver);
            } catch (ClassNotFoundException unErrore) { // intercetta l'errore
                /* mostra il messaggio di errore */
                Errore.crea(unErrore);
            } // fine del blocco try-catch

            /* se riuscito, accende il flag */
            riuscito = true;

        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return riuscito;
    } // fine del metodo


    /**
     * Recupera l'oggetto DatabaseMetaData di questa connessione.
     * <p/>
     *
     * @return l'oggetto DatabaseMetaData della connessione
     */
    public DatabaseMetaData getMetaData() {
        /* variabili e costanti locali di lavoro */
        DatabaseMetaData md = null;
        Connection cn = null;
        boolean continua = true;
        boolean openByMe = false;

        try {    // prova ad eseguire il codice

            /* controlla che la connessione sia aperta, se non è
             * aperta la apre ora e accende un flag per poi richiuderla */
            if (continua) {
                if (!this.isOpen()) {
                    continua = this.open();
                    if (continua) {
                        openByMe = true;
                    }// fine del blocco if
                }// fine del blocco if
            }// fine del blocco if

            /* recupero l'oggetto Connection */
            if (continua) {
                cn = this.getConnection();
                continua = cn != null;
            }// fine del blocco if

            /* recupero l'oggetto DatabaseMetaData */
            if (continua) {
                md = cn.getMetaData();
            }// fine del blocco if

            /* se ho aperto qui la connessione, la richiudo */
            if (openByMe) {
                this.close();
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return md;
    }


    /**
     * Carica una selezione di record.
     * <p/>
     *
     * @param query informazioni per effettuare la selezione
     *
     * @return un oggetto dati
     */
    public Dati querySelezione(Query query) {
        return this.getDb().querySelezione(query, this);
    }


    /**
     * Esegue la modifica di uno o piu' record.
     * <p/>
     * La modifica puo' consistere in inserimento di nuovi record
     * o modifica o cancellazione di record esistenti.
     *
     * @param query informazioni per effettuare la modifica
     *
     * @return il numero di record interessati, -1 se fallito
     */
    public int queryModifica(Query query) {
        return this.getDb().queryModifica(query, this);
    }


    /**
     * Esegue una Query di selezione sul database e ottiene un ResultSet.
     * <p/>
     *
     * @param stringaSql la stringa di comandi Sql per la Query
     *
     * @return il ResultSet ottenuto
     */
    public ResultSet esegueSelect(String stringaSql) {
        return this.getDbSql().esegueSelect(stringaSql, this);
    }


    /**
     * Esegue una Query di modifica sul database e ottiene un risultato.
     * <p/>
     *
     * @param stringaSql la stringa di comandi Sql per la Query
     *
     * @return il risultato ottenuto
     */
    public int esegueUpdate(String stringaSql) {
        return this.getDbSql().esegueUpdate(stringaSql, this);
    }


    /**
     * Controlla l'esistenza di una tavola.
     * <p/>
     *
     * @param tavola il nome della tavola
     *
     * @return true se esiste
     */
    public boolean isEsisteTavola(String tavola) {
        return this.getDbSql().isEsisteTavola(tavola, this);
    }


    /**
     * Determina se una data colonna esiste sul database.
     * <p/>
     *
     * @param nomeTavola nome della tavola
     * @param nomeColonna nome della colonna
     *
     * @return true se la colonna esiste
     */
    public boolean isEsisteColonna(String nomeTavola, String nomeColonna) {
        return this.getDbSql().isEsisteColonna(nomeTavola, nomeColonna, this);
    }


    /**
     * Determina se un dato campo esiste sul database.
     * <p/>
     *
     * @param campo il campo da controllare
     *
     * @return true se il campo esiste
     */
    public boolean isEsisteCampo(Campo campo) {
        return this.getDbSql().isEsisteCampo(campo, this);
    }


    /**
     * Crea la tavola del modulo sul database.
     * <p/>
     * La tavola viene creata solo se non gia' esistente.
     *
     * @param modulo di riferimento
     *
     * @return true se esistente o creata correttamente
     *         false se non creata
     */
    public boolean creaTavola(Modulo modulo) {
        return this.getDb().creaTavola(modulo, this);
    }


    /**
     * Elimina una tavola dal database.
     * <p/>
     *
     * @param tavola il nome della tavola
     *
     * @return true se riuscito
     */
    public boolean eliminaTavola(String tavola) {
        return this.getDbSql().eliminaTavola(tavola, this);
    }


    /**
     * Crea una colonna del database in base a un campo.
     * <p/>
     * Crea la colonna del tipo appropriato al campo.
     * Non effettua altre regolazioni (caratteristiche, indici ecc...)
     *
     * @param campo il campo per il quale creare la colonna
     *
     * @return true se la colonna e' stata creata correttamente
     */
    public boolean creaColonna(Campo campo) {
        return this.getDbSql().creaColonna(campo, this);
    }


    /**
     * Allinea una colonna del Db in base al corrispondente
     * campo del Modello.
     * <p/>
     * Se la colonna non esiste, la crea e la allinea
     * Se la colonna esiste, la allinea soltanto
     *
     * @param campo il campo per il quale allineare la colonna
     *
     * @return true se ha eseguito correttamente
     */
    public boolean allineaCampo(Campo campo) {
        return this.getDb().allineaCampo(campo, this);
    }


    /**
     * Elimina una colonna dal database.
     * <p/>
     *
     * @param campo il campo corrispondente alla colonna da eliminare
     *
     * @return true se riuscito.
     */
    public boolean eliminaColonna(Campo campo) {
        return this.getDb().eliminaColonna(campo, this);
    }


    /**
     * Attiva o disattiva l'integrità referenziale.
     * <p/>
     *
     * @param flag di attivazione
     *
     * @return true se riuscito
     */
    public boolean setReferentialIntegrity(boolean flag) {
        return this.getDb().setReferentialIntegrity(flag, this);
    }


    /**
     * Carica un singolo record del Modulo.
     * <p/>
     * Crea la query per identificare il record <br>
     * Invia la query al db, che esegue e ritorna i Dati <br>
     *
     * @param modulo di riferimento
     * @param codice codice chiave del record
     *
     * @return un oggetto dati con tutti i campi del modello
     */
    public Dati caricaRecord(Modulo modulo, int codice) {
        /* variabili e costanti locali di lavoro */
        Dati dati = null;
        Query query = null;

        try { // prova ad eseguire il codice
            query = QueryFactory.codice(modulo, codice);
            dati = this.querySelezione(query);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return dati;
    }


    /**
     * Carica una selezione di records del Modulo.
     * <p/>
     *
     * @param modulo di riferimento
     * @param filtro il filtro per selezionare i records (null = tutti)
     * @param ordine l'ordine del risultato (null = nessun ordine)
     *
     * @return un oggetto dati con tutti i campi del modello
     */
    public Dati caricaRecords(Modulo modulo, Filtro filtro, Ordine ordine) {
        /* variabili e costanti locali di lavoro */
        Dati dati = null;
        Query q = null;
        ArrayList<Campo> campi = null;

        try { // prova ad eseguire il codice
            campi = modulo.getCampiFisici();
            q = new QuerySelezione(modulo);
            q.setCampi(campi);
            q.addFiltro(filtro);
            q.setOrdine(ordine);
            dati = this.querySelezione(q);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return dati;
    }


    /**
     * Carica una selezione di records del Modulo.
     * <p/>
     * I records vengono ordinati sul campo Ordine
     *
     * @param modulo di riferimento
     * @param filtro il filtro per selezionare i records (null = tutti)
     *
     * @return un oggetto dati con tutti i campi del modello
     */
    public Dati caricaRecords(Modulo modulo, Filtro filtro) {
        /* variabili e costanti locali di lavoro */
        Campo campoOrdine = null;
        Ordine ordine = null;

        try { // prova ad eseguire il codice
            campoOrdine = modulo.getCampoOrdine();
            ordine = new Ordine();
            ordine.add(campoOrdine);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return this.caricaRecords(modulo, filtro, ordine);
    }


    /**
     * Carica una selezione di records del Modulo.
     * <p/>
     *
     * @param modulo di riferimento
     * @param ordine l'ordine del risultato
     *
     * @return un oggetto dati con tutti i campi del modello
     */
    public Dati caricaRecords(Modulo modulo, Ordine ordine) {
        return this.caricaRecords(modulo, (Filtro)null, ordine);
    }


    /**
     * Carica tutti i records del Modulo.
     * <p/>
     *
     * @param modulo di riferimento
     *
     * @return un oggetto dati con tutti i record e tutti i campi del modello
     */
    public Dati caricaRecords(Modulo modulo) {
        return this.caricaRecords(modulo, (Filtro)null, (Ordine)null);
    }


    /**
     * Crea un nuovo record con valori.
     * <p/>
     * Usa il valore specificato per tutti i campi forniti.
     * I campi non forniti vengono riempiti con i valori di default.
     *
     * @param modulo di riferimento
     * @param valori da registrare, null per nuovo record standard
     *
     * @return il codice del record creato, -1 se non riuscito
     */
    public int nuovoRecord(Modulo modulo, ArrayList<CampoValore> valori) {
        /* variabili e costanti locali di lavoro */
        int codice = 0;
        boolean continua;
        ArrayList<CampoValore> listaValori;
        Query query;
        Campo campo;
        Object valore;
        int quantiRecord = 0;
        Campo campoChiave;
        Campo campoVisibile;
        boolean trovato = false;
        Modello modello;

        try { // prova ad eseguire il codice

            /* se la lista è nulla, crea una lista vuota */
            if (valori == null) {
                listaValori = new ArrayList<CampoValore>();
            } else {
                listaValori = valori;
            }// fine del blocco if-else

            /* recupera il modello */
            modello = modulo.getModello();
            campoChiave = modulo.getCampoChiave();
            campoVisibile = modulo.getCampoVisibile();

            /* risolve i CampiValore */
            this.risolviLista(listaValori, modulo);

            /*
             * metodo trigger ante registrazione
             * permette al modello specifico di intercettare la chiamata
             * per modificare i valori o annullare la creazione del record
             */
            continua = modello.trigger(Modello.Trigger.nuovoAnte, 0, listaValori, null, this);

            if (continua) {

                /* risolve nuovamente i CampiValore perche' qualcuno potrebbe
                 * essere stato aggiunto dal trigger in forma non risolta */
                this.risolviLista(listaValori, modulo);

                /* costruisce la query */
                query = new QueryInsert(modulo);

                /* controlla che la lista dei campi contenga il campo Visibile
                 * se c'e'  pone il valore a true
                 * se non c'e' lo aggiunge ora con valore true */
                for (CampoValore cv : listaValori) {
                    campo = cv.getCampo();
                    if (campo.equals(campoVisibile)) {
                        cv.setValore(true);
                        trovato = true;
                        break;
                    }// fine del blocco if
                }
                if (!trovato) {
                    CampoValore cv = new CampoValore(campoVisibile, true);
                    listaValori.add(cv);
                }// fine del blocco if

                /* aggiunge tutti i campi alla query */
                for (CampoValore cv : listaValori) {
                    campo = cv.getCampo();
                    valore = cv.getValore();
                    query.addCampo(campo, valore);
                    if (campo == campoChiave) {
                        codice = ((Integer)valore);
                    }// fine del blocco if
                }

                /* esegue la query */
                quantiRecord = this.queryModifica(query);

                /* metodo trigger post registrazione */
                modello.trigger(Modello.Trigger.nuovoPost, codice, listaValori, null, this);

            }// fine del blocco if

            if (quantiRecord != 1) {
                codice = -1;
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return codice;
    }


    /**
     * Crea un nuovo record.
     * <p/>
     * Tutti i campi vengono riempiti con i valori di default.
     *
     * @param modulo di riferimento
     *
     * @return il codice del record creato, -1 se non riuscito
     */
    public int nuovoRecord(Modulo modulo) {
        return this.nuovoRecord(modulo, null);
    }


    /**
     * Registra un record esistente.
     * <p/>
     * Usa il valore Archivio dei campi
     *
     * @param modulo di riferimento
     * @param codice il codice chiave del record
     * @param campi la lista dei campi da registrare
     *
     * @return true se riuscito
     */
    public boolean registraRecord(Modulo modulo, int codice, ArrayList<Campo> campi) {
        /* variabili e costanti locali di lavoro */
        boolean riuscito = false;
        ArrayList<CampoValore> campiValore = null;
        Campo campo = null;
        Object valore = null;
        CampoValore campoValore = null;

        /* invoca il metodo sovrascritto nella superclasse */
        super.registraRecord(codice, campi);

        try { // prova ad eseguire il codice
            campiValore = new ArrayList<CampoValore>();
            if (campi != null) {
                for (int k = 0; k < campi.size(); k++) {
                    campo = (Campo)campi.get(k);
                    valore = campo.getCampoDati().getMemoria();
                    campoValore = new CampoValore(campo, valore);
                    campiValore.add(campoValore);
                } // fine del ciclo for
            }// fine del blocco if

            riuscito = this.registraRecordValori(modulo, codice, campiValore);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return riuscito;
    }


    /**
     * Registra un record esistente.
     * <p/>
     * Usa i campi e i valori forniti
     *
     * @param modulo di riferimento
     * @param codice il codice chiave del record
     * @param campiValore la lista dei campi con valori (oggetti CampoValore)
     *
     * @return true se riuscito
     */
    public boolean registraRecordValori(Modulo modulo,
                                        int codice,
                                        ArrayList<CampoValore> campiValore) {
        /* variabili e costanti locali di lavoro */
        boolean riuscito = false;
        boolean continua;
        Query query;
        Filtro filtro;
        Campo campo;
        Object valore;
        Campo campoChiave;
        int quanti;
        Modello modello;
        ArrayList<CampoValore> listaPre = null;

        try { // prova ad eseguire il codice

            /* recupera il modello */
            modello = modulo.getModello();

            /* se la funzione è attiva, crea una lista per memorizzare
             * i valori precedenti l'operazione */
            if (modello.isTriggerModificaUsaPrecedenti()) {
                listaPre = modulo.query().valoriRecord(codice, this);
            }// fine del blocco if

            /* costruisce la query */
            query = new QueryUpdate(modulo);

            /* risolve i CampiValore */
            this.risolviLista(campiValore, modulo);

            /* invoca il metodo trigger ante registrazione */
            continua = modello.trigger(Modello.Trigger.modificaAnte,
                    codice,
                    campiValore,
                    null,
                    this);

            if (continua) {

                /* risolve nuovamente i CampiValore perche' qualcuno potrebbe
                 * essere stato aggiunto dal trigger specifico in forma non risolta */
                this.risolviLista(campiValore, modulo);

                /* aggiunge i campi alla query */
                for (CampoValore cv : campiValore) {
                    campo = cv.getCampo();
                    valore = cv.getValore();
                    query.addCampo(campo, valore);
                } // fine del ciclo for-each

                /* crea il filtro per la query */
                campoChiave = modulo.getCampoChiave();
                filtro = FiltroFactory.crea(campoChiave, codice);
                query.addFiltro(filtro);

                /* esegue la query e recupera il risultato */
                quanti = this.queryModifica(query);

                /* invoca il metodo trigger post registrazione */
                modello.trigger(Modello.Trigger.modificaPost, codice, campiValore, listaPre, this);

                /* regola il valore di ritorno */
                if (quanti == 1) {
                    riuscito = true;
                }// fine del blocco if

            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return riuscito;
    }


    /**
     * Elimina un record esistente.
     * <p/>
     *
     * @param modulo di riferimento
     * @param codice il codice chiave del record
     *
     * @return true se riuscito
     */
    public boolean eliminaRecord(Modulo modulo, int codice) {
        /* variabili e costanti locali di lavoro */
        boolean riuscito = false;
        boolean continua;
        Filtro filtro;
        ArrayList<CampoValore> listaPre = null;
        Modello modello;
        Query query;
        int risultato;


        try { // prova ad eseguire il codice

            /* recupera il modello */
            modello = modulo.getModello();

            /* se la funzione è attiva, crea una lista per memorizzare
             * i valori precedenti l'operazione */
            if (modello.isTriggerEliminaUsaPrecedenti()) {
                listaPre = modulo.query().valoriRecord(codice, this);
            }// fine del blocco if

            /* invoca il metodo trigger ante */
            continua = modello.trigger(Modello.Trigger.eliminaAnte, codice, null, null, this);

            if (continua) {

                /* crea una query di eliminazione */
                filtro = FiltroFactory.codice(modulo, codice);
                query = new QueryDelete(modulo);
                query.setFiltro(filtro);

                /* esegue la query */
                risultato = this.queryModifica(query);
                if (risultato != -1) {
                    riuscito = true;
                }// fine del blocco if

                /* invoca il metodo trigger post */
                if (riuscito) {
                    modello.trigger(Modello.Trigger.eliminaPost, codice, null, listaPre, this);
                }// fine del blocco if

            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return riuscito;
    }


    /**
     * Elimina tutti i records corrispondenti a un dato filtro.
     * <p/>
     * ATTENZIONE! QUESTO METODO NON INVOCA I TRIGGER NEL MODELLO!
     *
     * @param modulo di riferimento
     * @param filtro il filtro da applicare (null per nessun filtro)
     *
     * @return true se riuscito
     */
    public boolean eliminaRecords(Modulo modulo, Filtro filtro) {
        /* variabili e costanti locali di lavoro */
        boolean riuscito = false;
        Query query;
        int risultato;

        try { // prova ad eseguire il codice

            query = new QueryDelete(modulo);

            if (filtro != null) {
                query.setFiltro(filtro);
            }// fine del blocco if

            risultato = this.queryModifica(query);
            if (risultato != -1) {
                riuscito = true;
            }// fine del blocco if


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return riuscito;

    }


    /**
     * Elimina tutti i records della tavola.
     * <p/>
     * ATTENZIONE! QUESTO METODO NON INVOCA I TRIGGER NEL MODELLO!
     *
     * @param modulo di riferimento
     *
     * @return true se riuscito
     */
    public boolean eliminaRecords(Modulo modulo) {
        return this.eliminaRecords(modulo, null);
    }


    /**
     * Restituisce il numero di records corrispondenti
     * a un Filtro dato
     * <p/>
     *
     * @param modulo di riferimento
     * @param filtro il filtro da applicare (null per tutti i record)
     *
     * @return il numero di record selezionati dal filtro
     */
    public int contaRecords(Modulo modulo, Filtro filtro) {
        /* variabili e costanti locali di lavoro */
        Query q = null;
        Campo campoChiave = null;
        Dati dati = null;
        int quantiRecords = 0;

        try {                                   // prova ad eseguire il codice

            /* recupera il campo chiave del modulo */
            campoChiave = modulo.getCampoChiave();

            /* crea una QuerySelezione */
            q = new QuerySelezione(modulo);

            /* regola la QuerySelezione con campo e filtro */
            q.addCampo(campoChiave);
            if (filtro != null) {
                q.setFiltro(filtro);
            } /* fine del blocco if */

            /* esegue la Query */
            dati = modulo.query().querySelezione(q, this);

            /* recupera il numero di records risultanti */
            quantiRecords = dati.getRowCount();

            /* chiude i dati */
            dati.close();

        } catch (Exception unErrore) {           // intercetta l'errore
            /** messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /* valore di ritorno */
        return quantiRecords;
    }


    /**
     * Recupera il massimo valore presente in un dato campo per un dato filtro.
     *
     * @param campo il campo per il quale recuperare il massimo valore
     * @param filtro il filtro da applicare, null per non specificato
     *
     * @return il massimo valore per il campo,
     *         (zero se non ci sono records corrispondenti al filtro)
     */
    public int valoreMassimo(Campo campo, Filtro filtro) {
        /* variabili e costanti locali di lavoro */
        int max = 0;
        Modulo modulo;
        QuerySelezione unaQuery = null;
        CampoQuery cq = null;
        Dati dati = null;

        try { // prova ad eseguire il codice

            modulo = campo.getModulo();

            /* crea una QuerySelezione per il Modulo */
            unaQuery = new QuerySelezione(modulo);

            /* aggiunge il campo con la funzione MAX */
            cq = unaQuery.addCampo(campo);
            cq.addFunzione(Funzione.MAX);

            /* aggiunge l'eventuale filtro */
            unaQuery.setFiltro(filtro);

            /* esegue la query */
            dati = this.querySelezione(unaQuery);

            /* recupera il risultato */
            max = dati.getIntAt(0, 0);

            /* chiude l'oggetto dati */
            dati.close();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return max;
    }


    /**
     * Determina se un campo e' vuoto in tutti i records della sua tavola.
     * <p/>
     * Un campo e' considerato vuoto quando contiene solo valori
     * nulli o valori considerati vuoti per lo specifico tipo di campo.
     *
     * @param campo il campo da controllare
     *
     * @return true se tutti i records sono vuoti
     */
    public boolean isCampoVuoto(Campo campo) {
        /** variabili e costanti locali di lavoro */
        boolean isVuoto = false;
//        TipoDati td = null;
        Object valoreVuoto = null;
        Filtro filtroVuoti = null;
        int quantiRecords = 0;

        try {    // prova ad eseguire il codice

            /* recupera un oggetto rappresentante il valore di
             * business logic vuoto per questo tipo di campo */
            valoreVuoto = campo.getCampoDati().getValoreArchivioVuoto();

            /* crea un filtro per escludere i valori vuoti */
            filtroVuoti = new Filtro(campo, Operatore.DIVERSO, valoreVuoto);

            /* conta i records corrispondenti */
            quantiRecords = this.contaRecords(campo.getModulo(), filtroVuoti);

            /* se il numero di records e' zero, il campo e' vuoto */
            if (quantiRecords == 0) {
                isVuoto = true;
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return isVuoto;
    }


    /**
     * Inverte i valori di un campo di due record.
     * <p/>
     *
     * @param campo il campo del quale invertire i valori
     * @param codA codice chiave del primo record
     * @param codB codice chiave del secondo record
     *
     * @return true se riuscito
     */
    public boolean swapCampo(Campo campo, int codA, int codB) {
        /* variabili e costanti locali di lavoro */
        boolean riuscito = true;
        Query query = null;
        Dati dati = null;
        Object valore = null;
        Integer intero = null;
        Modulo modulo;
        int ordineA = 0;
        int ordineB = 0;

        try { // prova ad eseguire il codice

            modulo = campo.getModulo();

            query = new QuerySelezione(modulo);
            query.addCampo(campo);
            query.addFiltro(FiltroFactory.codice(modulo, codA));
            dati = this.querySelezione(query);
            valore = dati.getValueAt(0, 0);
            intero = (Integer)valore;
            ordineA = intero.intValue();
            dati.close();

            query = new QuerySelezione(modulo);
            query.addCampo(campo);
            query.addFiltro(FiltroFactory.codice(modulo, codB));
            dati = this.querySelezione(query);
            valore = dati.getValueAt(0, 0);
            intero = (Integer)valore;
            ordineB = intero.intValue();
            dati.close();

            query = new QueryUpdate(modulo);
            query.addCampo(campo, new Integer(ordineB));
            query.addFiltro(FiltroFactory.codice(modulo, codA));
            this.queryModifica(query);

            query = new QueryUpdate(modulo);
            query.addCampo(campo, new Integer(ordineA));
            query.addFiltro(FiltroFactory.codice(modulo, codB));
            this.queryModifica(query);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return riuscito;
    }


    /**
     * Inverte i valori del campo ordine di due record.
     * <p/>
     *
     * @param modulo di riferimento
     * @param codA codice chiave del primo record
     * @param codB codice chiave del secondo record
     *
     * @return true se riuscito
     */
    public boolean swapOrdine(Modulo modulo, int codA, int codB) {
        /* variabili e costanti locali di lavoro */
        boolean riuscito = false;
        Campo campoOrdine = null;

        try { // prova ad eseguire il codice
            campoOrdine = modulo.getCampoOrdine();
            riuscito = this.swapCampo(campoOrdine, codA, codB);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return riuscito;
    }


    /**
     * Risolve tutti gli oggetti CampoValore in una lista.
     * <p/>
     * Risolve a fronte di un dato modulo.
     *
     * @param modulo di riferimento
     */
    private void risolviLista(ArrayList<CampoValore> lista, Modulo modulo) {
        try {    // prova ad eseguire il codice
            if (lista != null) {
                for (CampoValore cv : lista) {
                    cv.risolvi(modulo);
                }
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Restituisce la stringa rappresentante l'url
     * verso il quale effettuare la connessione. <br>
     */
    private String getStringaUrl() {
        return this.getDbSql().getStringaUrl(this).toString();
    } // fine del metodo


    /* Ritorna la stringa relativa al protocollo Jdbc */
    public String getJdbc() {
        return JDBC;
    }


    private DbSql getDbSql() {
        return (DbSql)this.getDb();
    }


    /**
     * Ritorna la connessione Java.
     * <p/>
     *
     * @return la connessione
     */
    public Connection getConnection() {
        return connection;
    }


    private void setConnection(Connection connection) {
        this.connection = connection;
    }

}// fine della classe
