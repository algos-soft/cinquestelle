/**
 * Title:        Modello.java
 * Package:      it.algos.base.modello
 * Description:  Abstract Data Types per il tracciato record
 * Copyright:    Copyright (c) 2002
 * Company:      Algos s.r.l.
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /
 * Creato:       il 3 ottobre 2002 alle 20.02
 */
package it.algos.base.modello;

import it.algos.base.albero.AlberoModello;
import it.algos.base.albero.AlberoNodo;
import it.algos.base.calcresolver.AlberoCalcolo;
import it.algos.base.calcresolver.CalcResolver;
import it.algos.base.campo.base.Campo;
import it.algos.base.campo.db.CampoDB;
import it.algos.base.campo.inizializzatore.Init;
import it.algos.base.campo.lista.CampoLista;
import it.algos.base.contatore.Contatore;
import it.algos.base.contatore.ContatoreModulo;
import it.algos.base.costante.CostanteCarattere;
import it.algos.base.costante.CostanteModello;
import it.algos.base.costante.CostanteModulo;
import it.algos.base.database.Db;
import it.algos.base.database.DbFactory;
import it.algos.base.database.connessione.Connessione;
import it.algos.base.database.dati.Dati;
import it.algos.base.database.sql.implem.hsqldb.DbSqlHsqldb;
import it.algos.base.database.util.MetodiQuery;
import it.algos.base.errore.Errore;
import it.algos.base.evento.BaseListener;
import it.algos.base.evento.Eventi;
import it.algos.base.evento.db.DbTriggerAz;
import it.algos.base.evento.db.DbTriggerEve;
import it.algos.base.evento.db.DbTriggerLis;
import it.algos.base.interfaccia.ContenitoreCampi;
import it.algos.base.libreria.Lib;
import it.algos.base.libreria.Libreria;
import it.algos.base.libreria.Parametro;
import it.algos.base.modulo.Modulo;
import it.algos.base.pref.Pref;
import it.algos.base.progetto.Progetto;
import it.algos.base.query.Query;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.query.selezione.QuerySelezione;
import it.algos.base.vista.Vista;
import it.algos.base.vista.VistaElemento;
import it.algos.base.wrapper.Campi;
import it.algos.base.wrapper.CampoValore;
import it.algos.base.wrapper.Estratti;
import it.algos.base.wrapper.EstrattoBase;
import it.algos.base.wrapper.SetCampi;
import it.algos.base.wrapper.SetValori;
import it.algos.base.wrapper.TreStringhe;
import it.algos.base.wrapper.Viste;
import it.algos.base.wrapper.WrapFiltri;

import javax.swing.event.EventListenerList;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Abstract Data Types per il tracciato record di ogni archivio<br>
 * <br>
 * Questa classe astratta e' responsabile di:<br>
 * A - Creare e mantenere al proprio interno una collezione campiRecord,
 * di oggetti di tipo CampoBase che racchiudono tutte le
 * informazioni<br>
 * B - Restituire elenchi di campi della lista, della scheda ecc. <br>
 * C - Mantenere un elenco di eventuali tabelle associate al modello per
 * costruire il menu della lista<br>
 * <br>
 * Ogni sottoclasse concreta di questa classe, aggiungera' i propri campi<br>
 * <br>
 * Ogni campo viene creato con un costruttore semplice con solo le
 * informazioni piu' comuni; le altre variabili vengono regolate con chiamate
 * successive<br>
 *
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /  3 ottobre 2002 ore 20.02
 */
public abstract class Modello implements ContenitoreCampi, CostanteModello {

    /**
     * nome del campo chiave sempre presente
     */
    public static final String NOME_CAMPO_CHIAVE = "codice";

    /**
     * nome del campo ordine sempre presente
     */
    public static final String NOME_CAMPO_ORDINE = "ordine";

    /**
     * nome del campo data di creazione sempre presente
     */
    public static final String NOME_CAMPO_DATA_CREAZIONE = "datacreazione";

    /**
     * nome del campo data di modifica sempre presente
     */
    public static final String NOME_CAMPO_DATA_MODIFICA = "datamodifica";

    /**
     * nome del campo id utente creazione sempre presente
     */
    public static final String NOME_CAMPO_ID_CREAZIONE = "idutentecreazione";

    /**
     * nome del campo id utente modifica sempre presente
     */
    public static final String NOME_CAMPO_ID_MODIFICA = "idutentemodifica";

    /**
     * nome del campo visibile sempre presente
     */
    public static final String NOME_CAMPO_VISIBILE = "visibile";

    /**
     * nome del campo "note"
     */
    public static final String NOME_CAMPO_NOTE = "note";

    /**
     * nome del campo "preferito"
     */
    public static final String NOME_CAMPO_PREFERITO = "pref";

    /**
     * nome del campo sigla sempre presente nelle tabelle
     */
    public static final String NOME_CAMPO_SIGLA = "sigla";

    /**
     * nome del campo descrizione sempre presente nelle tabelle
     */
    public static final String NOME_CAMPO_DESCRIZIONE = "descrizione";

    /**
     * nome del campo legame sempre presente nelle tabelle
     */
    public static final String NOME_CAMPO_LINK = "link";

    /**
     * nome del campo valore sempre presente nelle tabelle
     */
    public static final String NOME_CAMPO_VALORE = "valore";

    /**
     * nome del campo logico sempre presente nelle tabelle
     */
    public static final String NOME_CAMPO_LOGICO = "logico";

    /**
     * nome del campo logico sempre presente nelle tabelle
     */
    public static final String NOME_CAMPO_TESTO = "testo";

    /**
     * codifica interna per diverse tipologie di spazzolamento della
     * collezione di campi
     */
    private static final int VISIBILI_LISTA = 1;

    private static final int VISIBILI_SCHEDA = 2;

    private static final int FISSI_ALGOS = 3;

    private static final int NON_ALGOS = 4;

    /**
     * costante per unire due testi di estratti
     */
    protected static final String UNIONE_ESTRATTI = " - ";

    /**
     * Abstract Data Types per le informazioni del modulo
     */
    public Modulo unModulo = null;

    /**
     * nome generale della finestra (puo' essere diverso dal nome della tavola)
     */
    private String unNomeFinestra = "";

    /**
     * titolo della finestra lista che visualizza questo modello di dati
     */
    private String unTitoloLista = "";

    /**
     * titolo della finestra scheda che visualizza questo modello di dati
     */
    private String unTitoloScheda = "";

    /**
     * collezione di tutte le viste (oggetti di tipo Vista) - campi della lista
     */
    protected HashMap<String, Vista> collezioneViste = null;

    /**
     * collezione di tutti i set (oggetti di tipo Campo) - campi della scheda
     */
    protected HashMap<String, ArrayList> collezioneSet = null;

    /**
     * campo che viene 'letto' da un campo link di un'altra tavola
     */
    private String unCampoPubblico = "";    // da levare

    /**
     * nome della tavola del database associata a questo modello di dati
     */
    protected String unaTavolaArchivio = "";

    /* flag per usare una tavola di tipo temporaneo (default=false) */
    private boolean usaTavolaTemporanea;

    /* flag per usare l'integrità referenziale sul database */
    private boolean usaIntegritaReferenziale;

    /**
     * flag per aggiornare la data e l'utente di modifica del record ad ogni scrittura
     */
    private boolean aggiornaDataUtenteModifica;


    /**
     * collezione base di tutti gli oggetti campo del record <br>
     * la chiave della LinkedHashMap per recuperare l'oggetto e'
     * unNomeInternoCampo del campo (vedi CampoBase) <br>
     * oggetti di tipo Campo
     */
    private LinkedHashMap<String, Campo> campiModello = null;

    /**
     * collezione dei nomi di tutti i campi secondo l'ordine di creazione
     * iniziale <br>
     * questa collezione si rende necessaria, perche' la HashMap non mantiene un
     * ordinamento dei propri elementi <br>
     * i nomi dei campi sono sempre i nomeInterno di ogni campo <br>
     * l'ordine di creazione dei campi nella tavola del database e' questo, ma
     * i nomi usati nel database potrebbero essere diversi dal nomeInterno di
     * ogni campo (usa il metodo get per avere i valori sicuri) <br>
     * oggetti di tipo String
     */
    protected ArrayList<String> nomeCampiModello = null;

    /**
     * collezione dei nomi dei campi visibili nella lista, secondo l'ordine di
     * presentazione - i nomi dei campi sono sempre i nomeInterno di ogni campo <br>
     * oggetti di tipo String
     */
    protected ArrayList nomeCampiLista = null;  // DA  LEVARE

    /**
     * collezione dei campi visibili nella scheda, secondo l'ordine di
     * presentazione - i nomi dei campi sono sempre i nomeInterno di ogni campo <br>
     * oggetti di tipo CampoBase
     */
    protected LinkedHashMap campiScheda = null;  // DA  LEVARE

    /**
     * stringa dei nomi interni dei campi visibili nella scheda
     */
    protected String nomeCampiScheda = "";  // DA  LEVARE

    /**
     * collezione dei nomi interni dei campi visibili nella scheda
     */
    protected ArrayList listaCampiScheda = null;  // DA  LEVARE

    /**
     * flag per utilizzare il campo note
     */
    private boolean usaCampoNote = false;

    /**
     * flag per utilizzare il campo "preferito"
     */
    private boolean usaCampoPreferito = false;

    /**
     * flag - controlla se evitare l'eliminazione dei campi
     * superflui, indipendentemente da ogni altra impostazione.
     * Va attivato sui modelli che vengono derivati, perche'
     * altrimenti cercherebbero di eliminare i campi aggiunti
     * dal modello che li deriva.
     */
    protected boolean isEvitaEliminaCampiSuperflui = false;

    /* id del contatore per questa tavola nella tavola Contatori */
    private int idContatore;

    /**
     * primo campo editabile
     */
    private String primoCampo = "";

    /**
     * Filtri specifici visualizzati nel popup delle lista.
     */
    private ArrayList<WrapFiltri> filtriPop;

    /**
     * albero che mantiene la gerarchia dei campi calcolati
     */
    private AlberoCalcolo alberoCalcolo;

    /**
     * flag - attivazione del trigger per nuovo record
     */
    private boolean triggerNuovoAttivo;

    /**
     * flag - attivazione del trigger per modifica record
     */
    private boolean triggerModificaAttivo;

    /**
     * flag - attiva la gestione dei valori precedenti nel
     * trigger per modifica record
     */
    private boolean triggerModificaUsaPrecedenti;

    /**
     * flag - attivazione del trigger per eliminazione record
     */
    private boolean triggerEliminaAttivo;

    /**
     * flag - attiva la gestione dei valori precedenti nel
     * trigger per elimina record
     */
    private boolean triggerEliminaUsaPrecedenti;

    /**
     * A list of event listeners for this component.
     */
    private EventListenerList listaListener;

    /**
     * Integrita' referenziale di default del modello
     * in caso di cancellazione del record di testa
     * Possibili valori:
     * - AZIONE_NO_ACTION non fa nulla ma non permette di cancellare il record di testa
     * - AZIONE_CASCADE cancella automaticamente i record linkati
     * - AZIONE_SET_NULL attribuisce il valore null al campo link dei record linkati
     * - AZIONE_SET_DEFAULT attribuisce il valore di default al campo link dei record linkati
     */
    private Db.Azione azioneDelete = null;

    /**
     * Integrita' referenziale di default del modello
     * in caso di modifica del valore di link sul record di testa
     * Possibili valori:
     * - AZIONE_NO_ACTION non fa nulla ma non permette di modificare
     * il valore del campo chiave sul record di testa
     * - AZIONE_CASCADE modifica automaticamente i record linkati
     * - AZIONE_SET_NULL attribuisce il valore null al campo link dei record linkati
     * - AZIONE_SET_DEFAULT attribuisce il valore di default al campo link dei record linkati
     * (AZIONE_NO_ACTION, AZIONE_CASCADE, AZIONE_SET_NULL, AZIONE_SET_DEFAULT)
     */
    private Db.Azione azioneUpdate = null;


    /**
     * filtro del modello applicato a tutte le query relative a questo modello
     */
    private Filtro filtroModello;


    /**
     * Costruttore base senza parametri <br>
     * Indispensabile anche se non viene utilizzato
     * (anche solo per compilazione in sviluppo) <br>
     * Rimanda al costruttore completo utilizzando eventuali valori di default
     */
    public Modello() {
        /** rimanda al costruttore di questa classe */
        this(null);
    } /* fine del metodo costruttore base */


    /**
     * Costruttore completo  <br>
     *
     * @param unModulo Abstract Data Types per le informazioni del modulo
     */
    public Modello(Modulo unModulo) {
        /** rimanda al costruttore della superclasse */
        super();

        /** regola le variabili di istanza coi parametri */
        this.unModulo = unModulo;

        /** regolazioni iniziali di riferimenti e variabili */
        try {                                   // prova ad eseguire il codice
            this.inizia();
        } catch (Exception unErrore) {           // intercetta l'errore
            /** messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */
    } /* fine del metodo costruttore completo */


    /**
     * Regolazioni iniziali di riferimenti e variabili
     * Metodo chiamato direttamente dal costruttore
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {

        /* creazione delle collezioni (vuote) */
        this.campiModello = new LinkedHashMap<String, Campo>();
        this.campiScheda = new LinkedHashMap();
        this.nomeCampiModello = new ArrayList<String>();
        this.nomeCampiLista = new ArrayList();
        this.collezioneViste = new HashMap<String, Vista>();
        this.collezioneSet = new HashMap<String, ArrayList>();

        /* collezione di liste di filtri */
        this.setFiltriPop(new ArrayList<WrapFiltri>());

        /* albero dei campi calcolati di questo modello dati */
        this.setAlberoCalcolo(new AlberoCalcolo(this));

        /* crea lista degli ascoltatori dei propri eventi */
        this.setListaListener(new EventListenerList());

        /* regolazione di default dell'uso dell'integrità referenziale
         * sul database */
        this.setUsaIntegritaReferenziale(true);

        /*
         * Di default non tocca il record linkato
         * quando si cancella il record di testa.
         */
        this.setAzioneDelete(Db.Azione.setNull);

        /**
         * di default modifica automaticamente il valore del campo
         * linkato quando si modifica il valore del campo chiave
         */
        this.setAzioneUpdate(Db.Azione.cascade);

        /* attivazioni di default dei trigger:
         * per ottimizzare le prestazioni, i trigger sono
         * disattivati di default */
        //@todo non sono d'accordo gac/8-2-07
        //@todo se serve lo teniamo sempre attivo
        //@todo le probabilità che mi ricordi di regolarlo nel modulo specifico sono vicine allo zero

        this.setTriggerNuovoAttivo(true);
        this.setTriggerModificaAttivo(true, false);
        this.setTriggerEliminaAttivo(true, false);

        /* aggiornamento della data e utente di modifica del record ad ogni scrittura */
        this.setAggiornaDataUtenteModifica(true);

        /* crea il filtro del modello (vuoto) */
        this.setFiltroModello(new Filtro());

    } /* fine del metodo inizia */


    /**
     * Relaziona tutti i campi.
     * <p/>
     * Crea, allinea o elimina i campi.<br>
     * Metodo invocato dal ciclo inizia del Progetto <br>
     *
     * @return true se riuscito
     */
    public boolean relaziona() {
        /* variabili e costanti locali di lavoro */
        LinkedHashMap campi;
        boolean riuscito;
        Iterator unGruppo;
        Campo unCampo;
        CampoDB unCampoDB;

        riuscito = true;

        try { // prova ad eseguire il codice

            campi = getCampiFisici();

            unGruppo = campi.values().iterator();

            while (unGruppo.hasNext()) {
                unCampo = (Campo)unGruppo.next();
                unCampoDB = unCampo.getCampoDB();

                riuscito = (unCampoDB.relaziona() ? riuscito : false);

            } /* fine del blocco while */

            /* elimina eventuali campi presenti sul database ma non nel modello
             * (solo se la preferenza ALLINEA_STRUTTURA e' true)
             * (solo se le preferenze sono disponibili) */
            if (riuscito) {
                if (Pref.DB.allinea.is()) {
                    riuscito = this.eliminaCampiSuperflui();
                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return riuscito;
    }


    /**
     * Inizializza il Modello.<br>
     * Crea i componenti del Modello, li inizializza ed effettua le
     * regolazioni ulteriori.
     *
     * @param unModulo
     *
     * @return true se il modello e' stato inizializzato correttamente
     */
    public boolean inizializza(Modulo unModulo) {
        /* variabili e costanti locali di lavoro */

        try {    // prova ad eseguire il codice

            /* se il modello usa una tavola temporanea disattiva
             * l'uso dell'integrità referenziale che non si può
             * usare con le tavole temporanee */
            if (this.isUsaTavolaTemporanea()) {
                this.setUsaIntegritaReferenziale(false);
            }// fine del blocco if

            /* inizializza i campi */
            this.inizializzaCampi();

            /* inizializza l'albero dei campi calcolati */
            this.getAlberoCalcolo().inizializza();

            /* inizializza le Viste */
            this.inizializzaViste();

//            /*
//             * elimina dal db eventuali campi superflui
//             * (campi presenti nel DB ma non nel Modello)
//             */
//            if (isEvitaEliminaCampiSuperflui == false) {
//                this.eliminaCampiSuperflui();
//            }// fine del blocco if

            /* regola il contatore */
            this.regolaContatore();

            /* regola il modello dopo la inizializzazione */
            this.regola();

        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return true;

    } // fine del metodo


    /**
     * Crea il modello.
     * <p/>
     * Crea tutti gli elementi del Modello.
     *
     * @param unModulo
     */
    public void prepara(Modulo unModulo) {
        /* variabili e costanti locali di lavoro */
        Vista vista;
        ArrayList<Campo> unSet;

        try {    // prova ad eseguire il codice

            /* regola il riferimento al modulo */
            this.setModulo(unModulo);

            /* creazione dei campi (sovrascritto dalla sottoclasse) */
            this.creaCampi();

            /* Aggiunge eventualmente un campo note al modello */
            if (this.isUsaCampoNote()) {
                this.creaCampoNote();
            }

            /* Aggiunge eventualmente un campo "preferito" al modello */
            if (this.isUsaCampoPreferito()) {
                this.creaCampoPreferito();
            }

            /* se e' vuoto il nome della tavola, forza quello del modulo */
            this.regolaTavola();

            /* crea la tavola se non esiste
             * va fatto dopo i campi perche' la tavola viene
             * creata con il campo chiave */
            this.creaTavola();

            /** creazione della Vista di default
             * con tutti i campi visibili nella lista */
            vista = this.creaVistaDefault();
            vista.setNomeVista(CostanteModulo.VISTA_BASE_DEFAULT);
            this.addVista(vista);

            /* creazione delle Viste (sovrascritto dalla sottoclasse) */
            this.creaViste();

            /* creazione del set di default per i campi visibili in scheda */
            unSet = this.creaSetDefault();
            this.addSet(CostanteModulo.SET_BASE_DEFAULT, unSet);

            /* creazione del set dei campi visibili nella
             * pagina Programmatore */
            this.creaSetProgrammatore(); // metodo obbligatorio di questa classe

            this.creaSet();    // metodo vuoto sovrascrivibile nella classe specifica

            /* regolazione di default (puo essere modificata) */
            this.setCampoPubblico(CostanteModello.NOME_CAMPO_DESCRIZIONE);

            /* creazione passaggi obbligati (sovrascritto nella sottoclasse)*/
            this.creaPassaggiObbligati();


        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } // fine del blocco try-catch

    } // fine del metodo


    /**
     * Regola il Modello al termine della inizializzazione.
     * <p/>
     * Regola tutti gli elementi del Modello dopo che sono stati inizializzati
     */
    private void regola() {

        try {    // prova ad eseguire il codice

            /* regolazioni (eventuali) delle viste inizializzate.
             *  metodo vuoto sovrascrivibile nella classe specifica */
            this.regolaViste();

            /* clonazione dei campi dei set */
            this.clonaSet(); // metodo obbligatorio di questa classe

            /* regolazione dei set */
            this.regolaSet();    // metodo vuoto sovrascrivibile nella classe specifica

            /* regolazione dei filtri */
            this.regolaFiltriPop();    // metodo vuoto sovrascrivibile nella classe specifica

        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Regolazioni di avvio, ogni volta che questo oggetto deve 'ripartire', per
     * essere sicuri che sia 'pulito'
     * Metodo chiamato da altre classi
     * Viene eseguito tutte le volte che necessita
     */
    public void avviaModello() {
    } /* fine del metodo */


    /**
     * Aggiunge un filtro al filtro del modello.
     * <p/>
     *
     * @param filtroIn da aggiungere
     * @return il filtro modello dopo l'aggiunta
     */
    public Filtro addFiltroModello(Filtro filtroIn) {
        /* variabili e costanti locali di lavoro */
        Filtro filtroModello = null;

        try {    // prova ad eseguire il codice
            filtroModello = this.getFiltroModello();
            filtroModello.add(filtroIn);
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return filtroModello;
    }


    /**
     * se e' vuoto il nome della tavola, forza quello del modulo
     */
    private void regolaTavola() {
        /** variabili e costanti locali di lavoro */
        String unaTavola;

        if (this.getTavolaArchivio().equals("")) {
            unaTavola = this.getModulo().getNomeChiave();
            this.setTavolaArchivio(unaTavola);
        } /* fine del blocco if */
    } /* fine del metodo */


    /**
     * Controlla se questo modello necessita del modulo Contatori.
     * <p/>
     * Basta che un campo usi un inizializzatore di tipo InitContatore.
     *
     * @return true se usa i conattori
     */
    public boolean isUsaContatori() {
        /* variabili e costanti locali di lavoro */
        boolean usa = false;
        LinkedHashMap<String, Campo> campi;
        Init init;

        try {    // prova ad eseguire il codice
            campi = this.getCampiFisici();
            for (Campo campo : campi.values()) {
                init = campo.getInit();
                if (init != null) {
                    if (init.isNecessitaContatori()) {
                        usa = true;
                        break;
                    }// fine del blocco if
                }// fine del blocco if
            }
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return usa;
    }


    /**
     * Regola il contatore per questo Modello.
     * <p/>
     * Controlla che esista il record nella tavola Contatori;
     * se non esiste lo crea adesso.<br>
     * Se il prossimo ID è zero, regola il prossimo ID
     * analizzando i dati esistenti.
     *
     * @return true se riuscito
     */
    public boolean regolaContatore() {
        /* variabili e costanti locali di lavoro */
        boolean riuscito = false;
        ContatoreModulo modCont;
        String nomeTavola;
        int chiave;
        ArrayList<CampoValore> valori;
        CampoValore cv;
        Campo campo;
        int nextID;
        int maxval;
        int nextval;
        boolean continua;

        try {    // prova ad eseguire il codice

            /* recupera il modulo Contatore */
            modCont = Progetto.getModuloContatore();
            continua = (modCont != null);

            if (continua) {
                continua = modCont.isInizializzato();
            }// fine del blocco if

            if (continua) {

                /* controlla se esiste l'entry per la tavola di questo modello */
                nomeTavola = this.getTavolaArchivio();
                chiave = modCont.query().valoreChiave(Contatore.Cam.tavola.get(), nomeTavola);

                /* se non esiste lo crea e recupera la chiave */
                if (chiave == 0) {
                    valori = new ArrayList<CampoValore>();
                    campo = modCont.getCampoTavola();
                    cv = new CampoValore(campo, nomeTavola);
                    valori.add(cv);
                    chiave = modCont.query().nuovoRecord(valori);
                }// fine del blocco if-else

                /* registra la chiave nella variabie di istanza */
                this.setIdContatore(chiave);

                /* se la tavola dei dati è vuota, pone il prossimo id a zero */
                if (this.isTavolaVuota()) {
                    modCont.query().registraRecordValore(chiave, modCont.getCampoNextID(), 0);
                }// fine del blocco if

                /* recupera il prossimo ID */
                nextID = modCont.query().valoreInt(modCont.getCampoNextID(), chiave);

                /* se il prossimo ID è zero, lo aggiorna in base ai dati attuali */
                if (nextID == 0) {

                    /* recupera il massimo valore del campo chiave di questo modello */
                    maxval = this.query().valoreMassimo(this.getCampoChiave());

                    /* registra il prossimo valore
                     * (il valore massimo incrementato di 1) */
                    nextval = maxval + 1;
                    modCont.query().registraRecordValore(chiave, modCont.getCampoNextID(), nextval);

                }// fine del blocco if

                riuscito = true;

            }// fine del blocco if


        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return riuscito;
    }


    /**
     * Inizializza tutti i campi del modello non ancora inizializzati.<br>
     *
     * @return true se ha inizializzato tutti i campi
     */
    private boolean inizializzaCampi() {

        try {    // prova ad eseguire il codice

            /** traversa tutta la collezione dei campi */
            for (Campo unCampo : this.getCampiModello().values()) {

                /* inizializza il campo */
                unCampo.inizializza();

                /* se il campo è fisico e calcolato, lo aggiunge all'albero
                 * dei campi calcolati */
                if (unCampo.getCampoDB().isCampoFisico()) {
                    if (unCampo.getCampoLogica().isCalcolato()) {
                        this.getAlberoCalcolo().addCampoCalcolato(unCampo);
                    }// fine del blocco if
                }// fine del blocco if
            }

        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return true;
    }


    /**
     * Inizializza tutte le Viste del modulo non ancora inizializzate.<br>
     *
     * @return true se tutte le Viste sono state inizializzate
     */
    protected boolean inizializzaViste() {
        /* variabili e costanti locali di lavoro */
        boolean tuttoInizializzato = true;
        Iterator unGruppo;
        Vista unaVista;

        try {    // prova ad eseguire il codice

            /* traversa tutta la collezione delle Viste */
            unGruppo = this.collezioneViste.values().iterator();
            while (unGruppo.hasNext()) {

                /* recupera la singola Vista */
                unaVista = (Vista)unGruppo.next();

                /* inizializza la Vista (se non gia' inizializzata) */
                if (!unaVista.inizializza()) {
                    tuttoInizializzato = false;
                    break;
                }// fine del blocco if

            } /* fine del blocco while */

        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return tuttoInizializzato;

    } // fine del metodo


    /**
     * Costruisce una collezione con i titoli delle colonne della lista
     * ordinati secondo la lista nomeCampiLista
     *
     * @return lista ordinata dei titoli delle colonne
     */
    private ArrayList creaArrayTitoliLista() {
        /** variabili di lavoro */
        Campo unCampo;
        String unNome;
        ArrayList<String> unaLista = new ArrayList<String>();

        /** controllo errori e gestione delle eccezioni */
        try {
            /** traverso tutta la collezione */
            for (int k = 0; k < nomeCampiLista.size(); k++) {
                unNome = (String)nomeCampiLista.get(k);
                unCampo = (Campo)campiModello.get(unNome);
                if ((unCampo != null) && (unCampo.getCampoLista().isPresenteVistaDefault())) {
                    unaLista.add(unCampo.getCampoLista().getTitoloColonna());
                } /* fine del blocco if */
            } /* fine del blocco for */
        } // fine di try
        catch (Exception unEccezione) {
            new Errore(unEccezione, "creaArrayTitoliLista");
        } // fine del blocco try/catch

        /** valore di ritorno */
        return unaLista;
    } /* fine del metodo  */


    /**
     * Crea la tavola di questo modello sul database.
     * <p/>
     * La tavola viene creata solo se non e' gia' esistente
     *
     * @return true se la tavola esiste gia' o se e' stata
     *         creata correttamente
     */
    public boolean creaTavola() {
        /* variabili e costanti locali di lavoro */
        boolean eseguito = false;
        Modulo modulo;

        try {    // prova ad eseguire il codice

            modulo = this.getModulo();
            eseguito = modulo.getConnessione().creaTavola(modulo);

        } catch (Exception unErrore) {    // intercetta l'errore
            /** mostra il messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /* valore di ritorno */
        return eseguito;

    } /* fine del metodo */


    /**
     * Cancella dal db gli eventuali campi non contenuti nel Modello.
     * <p/>
     *
     * @return true se ha eliminato correttamente i campi superflui
     *         o se non ci sono campi superflui da eliminare
     */
    protected boolean eliminaCampiSuperflui() {
        /* invoca il metodo delegato della classe */
        return Lib.Mod.eliminaCampiSuperflui(this);
    } // fine del metodo


    /**
     * Costruisce un ArrayList di riferimenti ordinati (oggetti Campo) per
     * individuare i campi che voglio vedere nella scheda di default.
     * <p/>
     * Viene chiamato DOPO che nella sottoclasse sono stati costruiti tutti i campi
     */
    protected ArrayList<Campo> creaSetDefault() {
        /* variabili e costanti locali di lavoro */
        ArrayList<Campo> unaLista;
        Campo unCampo;

        /* crea l'istanza della lista */
        unaLista = new ArrayList<Campo>();

        try {    // prova ad eseguire il codice

            /* traversa tutta la collezione dei campi di questo modello */
            Iterator unGruppo = this.getIteratorValoreCampi();
            while (unGruppo.hasNext()) {
                /* recupera il campo */
                unCampo = (Campo)unGruppo.next();

                /* aggiunge il campo se e' visibile nella scheda */
                if (unCampo.getCampoScheda().isPresenteInScheda()) {
                    unaLista.add(unCampo);
                } /* fine del blocco if */
            } /* fine del blocco while */

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /* valore di ritorno */
        return unaLista;

    } /* fine del metodo */


    /**
     * Costruisce un ArrayList di riferimenti ordinati (oggetti Campo) per
     * individuare i campi da vedere nella pagina Programmatore della scheda.<br>
     * Aggiunge l'array alla collezione
     */
    private void creaSetProgrammatore() {
        /** variabili e costanti locali di lavoro */
        ArrayList unaLista;
        Campo unCampo;

        /** crea l'istanza della lista */
        unaLista = new ArrayList();

        try {    // prova ad eseguire il codice
            /** traversa tutta la collezione dei campi di questo modello */
            Iterator unGruppo = this.getIteratorValoreCampi();
            while (unGruppo.hasNext()) {
                /** recupera il campo */
                unCampo = (Campo)unGruppo.next();

                /** aggiunge il campo se e' un campo fisso */
                /*  */
                if (unCampo.getCampoDB().isFissoAlgos()) {
                    unaLista.add(unCampo);
                }// fine del blocco if

            } /* fine del blocco while */

            /** aggiunge questo array alla collezione collezioneSet */
            this.collezioneSet.put(CostanteModulo.SET_PROGRAMMATORE, unaLista);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */
    } /* fine del metodo */


    /**
     * Clonazione dei campi dei sets nella collezione
     * Spazzola tutti i sets
     * Spazzola tutti i campi
     * Effettua delle COPIE (parziali) di ogni campo e sostituisce nel
     * set gli originali con le copie
     */
    private void clonaSet() {
        /* variabili e costanti locali di lavoro */
        ArrayList unSet;
        Campo unCampoOriginale;
        Campo unCampoCopiato;

        try {    // prova ad eseguire il codice

            /** traversa tutta la collezione */
            Iterator unGruppo = this.collezioneSet.values().iterator();
            while (unGruppo.hasNext()) {
                /* recupera il set */
                unSet = (ArrayList)unGruppo.next();

                /* spazzola tutto il set */
                for (int k = 0; k < unSet.size(); k++) {

                    /** recupera il campo originale */
                    unCampoOriginale = (Campo)unSet.get(k);

                    if (unCampoOriginale != null) {
                        /* esegue la copia */
                        unCampoCopiato = unCampoOriginale.clonaCampo();

                        /** sostituisce nella lista */
                        unSet.remove(k);
                        unSet.add(k, unCampoCopiato);
                    }// fine del blocco if
                } /* fine del blocco for */

            } /* fine del blocco while */
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */
    } /* fine del metodo */


    /**
     * regola la stringa dei nomi dei campi visibili nella scheda
     */
    public void creaStringaNomeCampiScheda() {
        /** pulisce la variabile */
        this.nomeCampiScheda = "";

        /** traversa tutta la collezione */
        String unaChiave = "";
        Iterator unGruppo = campiScheda.keySet().iterator();
        while (unGruppo.hasNext()) {
            unaChiave = (String)unGruppo.next();
            this.nomeCampiScheda += ",";
            this.nomeCampiScheda += unaChiave;
        } /* fine del blocco while */

        /** pulisce la virgola iniziale in eccesso */
        this.nomeCampiScheda = this.nomeCampiScheda.substring(1);
    } /* fine del metodo */


    /**
     * todo solo per compatibilita', da eliminare!
     * todo non sono d'accordo (gac)
     * Crea una Vista di questo modulo.<br>
     * Crea l'istanza di Vista
     * Riempie la vista con l'elenco di oggetti passato come parametro
     * Aggiunge la Vista alla collezione interna di viste
     * Gli oggetti contenuti nell'array passato possono essere:
     * - Stringhe con nome interno del campo di questo modulo
     * - Campi di questo o di altri Moduli
     *
     * @param unNomeVista     nome da assegnare come chiave alla Vista
     * @param unaListaOggetti elenco dei campi da inserire nella vista
     *
     * @return unaVista la nuova vista costruita ed aggiunta alla collezione
     */
    protected Vista addVista(String unNomeVista, ArrayList unaListaOggetti) {

        /** variabili e costanti locali di lavoro */
        Campo unCampo;
        String unNomeCampo;
        Vista unaVista = null;
        Object unOggetto;

        try {    // prova ad eseguire il codice

            /* crea la vista vuota */
            unaVista = this.creaVista(unNomeVista);

            /* spazzola la lista oggetti e li aggiunge alla Vista */
            for (int k = 0; k < unaListaOggetti.size(); k++) {

                unOggetto = unaListaOggetti.get(k);

                /* caso Campo */
                if (unOggetto instanceof Campo) {
                    unCampo = (Campo)unOggetto;
                    unaVista.addCampo(unCampo);
                }// fine del blocco if

                /* caso Nome Campo (del Modulo) */
                if (unOggetto instanceof String) {
                    unNomeCampo = (String)unOggetto;
                    unaVista.addCampo(unNomeCampo);
                }// fine del blocco if

            } // fine del ciclo for

            /* aggiunge la Vista alla collezione */
            this.addVista(unaVista);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /** valore di ritorno */
        return unaVista;
    } /* fine del metodo */


    /**
     * todo solo per compatibilita', da eliminare!
     * Crea una Vista di questo modulo.<br>
     * Crea l'istanza di Vista
     * Riempie la vista con l'elenco di oggetti passato come parametro
     * Aggiunge la Vista alla collezione interna di viste
     * Gli oggetti contenuti nell'array passato possono essere:
     * - Stringhe con nome interno del campo di questo modulo
     * - Campi di questo o di altri Moduli
     *
     * @param viste           nome da assegnare come chiave alla Vista
     * @param unaListaOggetti elenco dei campi da inserire nella vista
     *
     * @return unaVista la nuova vista costruita ed aggiunta alla collezione
     */
    protected Vista addVista(Viste viste, ArrayList unaListaOggetti) {
        return addVista(viste.toString(), unaListaOggetti);
    }


    /**
     * Crea una vista di campi
     * Crea l'istanza di Vista e riempie la collezione interna coi
     * campi recuperati dalla stringa di nomi separato da virgola
     * passata come parametro
     * Aggiunge la Vista alla collezione interna di viste
     *
     * @param unNomeVista       nome da assegnare come chiave alla Vista
     * @param unaSerieNomiCampi elenco separato da virgola dei nomi dei campi
     *                          da inserire nella vista
     *
     * @return unaVista la nuova vista costruita ed aggiunta alla collezione
     */
    protected Vista addVista(String unNomeVista, String unaSerieNomiCampi) {
        /* variabili e costanti locali di lavoro */
        ArrayList unaLista = null;

        try {    // prova ad eseguire il codice
            /* trasforma la lista di nomi in un array */
            unaLista = Lib.Array.creaLista(unaSerieNomiCampi);
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /* rimanda al metodo sovrascritto */
        return addVista(unNomeVista, unaLista);
    }


    /**
     * Crea una vista di campi
     * Crea l'istanza di Vista e riempie la collezione interna coi
     * campi recuperati dalla stringa di nomi separato da virgola
     * passata come parametro
     * Aggiunge la Vista alla collezione interna di viste
     *
     * @param vista della Enumeration dell'interfaccia
     * @param campo della Enumeration dell'interfaccia
     *
     * @return unaVista la nuova vista costruita ed aggiunta alla collezione
     */
    protected Vista addVista(Viste vista, Campi campo) {
        /* invoca il metodo delegato della classe */
        return this.addVista(vista.toString(), campo.get());

    }


    /**
     * Aggiunge un oggetto Vista alla collezione.<br>
     * Regola il riferimento al Modulo nella vista.
     *
     * @param unaVista riferimento all'oggetto Vista
     */
    public void addVista(Vista unaVista) {
        /* variabili e costanti locali di lavoro */
        String unaChiave;
        boolean aggiungi = true;

        try {    // prova ad eseguire il codice

            /* recupera il nome della Vista */
            unaChiave = unaVista.getNomeVista();

            /* aggiunge la Vista alla collezione Viste del modello */
            if (aggiungi) {

                this.getVisteModello().put(unaChiave, unaVista);

            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } // fine del blocco try-catch

    } /* fine del metodo */


    /**
     * Aggiunge un set al modello
     * <p/>
     *
     * @param chiave nome chiave per la collezione
     * @param set    elenco dei nomi di campo
     */
    protected void addSet(String chiave, ArrayList set) {
        try {    // prova ad eseguire il codice
            this.collezioneSet.put(chiave, set);
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

    } /* fine del metodo */


    /**
     * Crea una Vista per questo modulo. <br>
     *
     * @param nomeVista il nome della vista da creare
     *
     * @return la vista creata
     */
    protected Vista creaVista(String nomeVista) {
        return new Vista(nomeVista, this.getModulo());
    } // fine del metodo


    /**
     * Crea la vista di default.
     * <p/>
     * Metodo invocato dal ciclo di preparazione dei moduli <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     *
     * @return la vista creata
     */
    protected Vista creaVistaDefault() {
        /** variabili e costanti locali di lavoro */
        Vista unaVista = null;
        Campo unCampo;
        CampoDB unCampoDB;
        CampoLista unCampoLista;
        boolean visibile;
        VistaElemento unElemento;

        try {    // prova ad eseguire il codice

            /* crea la vista di default vuota */
            unaVista = this.creaVista(CostanteModulo.VISTA_BASE_DEFAULT);

            /** traversa tutta la collezione dei campi di questo modello */
            Iterator unGruppo = this.getIteratorValoreCampi();
            while (unGruppo.hasNext()) {

                /* Se presente nella lista di default,
                 * recupera il campo e lo aggiunge alla Vista di default
                 * Regola la caratteristica di visibilita' in base
                 * al flag del campo */
                unCampo = (Campo)unGruppo.next();
                unCampoDB = unCampo.getCampoDB();
                unCampoLista = unCampo.getCampoLista();
                if (unCampoLista.isPresenteVistaDefault()) {

                    visibile = unCampoLista.isVisibileVistaDefault();
                    unElemento = unaVista.addCampo(unCampo);
                    unElemento.setVisibile(visibile);

                    /*
                     * Controllo flags espansione campo linkato.
                     * Se si tratta di un campo linkato, ed e' visibile
                     * nella vista di default, deve necessariamente avere
                     * visibile la parte originale o la parte espansa.
                     * Se cosi' non e', rende automaticamente visibile
                     * la parte originale
                     */
                    if (unElemento.isVisibile()) {
                        if (unCampoDB.isLinkato()) {
                            if (!unElemento.isVisibileOriginale()) {
                                if (!unElemento.isVisibileEspansione()) {
                                    unElemento.setVisibileOriginale(true);
                                }// fine del blocco if
                            }// fine del blocco if
                        }// fine del blocco if
                    }// fine del blocco if

                }// fine del blocco if

            } /* fine del blocco while */

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /* valore di ritorno */
        return unaVista;
    } /* fine del metodo */


    /**
     * Crea un set di campi della scheda.
     * <p/>
     * Crea un array e lo riempie coi campi recuperati
     * dall'array di nomi passato come parametro <br>
     * Aggiunge il set alla collezione interna di set <br>
     *
     * @param nomeSet        da assegnare come chiave al set
     * @param listaNomiCampi elenco dei campi da inserire nel set
     *
     * @return lista il nuovo set costruito ed aggiunto alla collezione
     */
    protected ArrayList creaSet(String nomeSet, ArrayList listaNomiCampi) {
        /* variabili e costanti locali di lavoro */
        ArrayList lista = null;
        Campo unCampo;
        String unNomeCampo;

        try {    // prova ad eseguire il codice
            /* costruisce una nuova istanza di array */
            lista = new ArrayList();

            /* spazzola tutta la lista */
            for (int k = 0; k < listaNomiCampi.size(); k++) {

                /* recupera il nome del campo */
                unNomeCampo = (String)listaNomiCampi.get(k);

                /* recupera il campo */
                unCampo = this.getCampo(unNomeCampo);

                /* aggiunge il campo alla vista */
                lista.add(unCampo);

            } /* fine del blocco for */

            /* aggiunge questo array alla collezione collezioneSet */
            this.collezioneSet.put(nomeSet, lista);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /* valore di ritorno */
        return lista;
    }


    /**
     * Crea un set di campi della scheda.
     * <p/>
     * Crea un array e lo riempie coi campi recuperati
     * dall'array di nomi passato come parametro <br>
     * Aggiunge il set alla collezione interna di set <br>
     *
     * @param set            istanza della enumeration
     * @param listaNomiCampi elenco dei campi da inserire nel set
     *
     * @return lista il nuovo set costruito ed aggiunto alla collezione
     */
    protected ArrayList creaSet(SetCampi set, ArrayList listaNomiCampi) {
        /* invoca il metodo delegato della classe */
        return this.creaSet(set.toString(), listaNomiCampi);
    }


    /**
     * Crea un set di campi della scheda.
     * <p/>
     * Crea un array e lo riempie coi campi recuperati
     * dall'array di nomi passato come parametro <br>
     * Aggiunge il set alla collezione interna di set <br>
     *
     * @param nomeSet   da assegnare come chiave al set
     * @param nomiCampi elenco dei campi da inserire nel set
     *
     * @return lista il nuovo set costruito ed aggiunto alla collezione
     */
    protected ArrayList creaSet(String nomeSet, String nomiCampi) {
        /* variabili e costanti locali di lavoro */
        ArrayList lista = null;
        ArrayList listaNomiCampi;

        try {    // prova ad eseguire il codice
            /* trasforma la lista di nomi in un array */
            listaNomiCampi = Lib.Array.creaLista(nomiCampi);

            if (listaNomiCampi != null) {
                lista = this.creaSet(nomeSet, listaNomiCampi);
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /* valore di ritorno */
        return lista;
    }


    /**
     * Crea un set di campi della scheda.
     * <p/>
     * Crea un array e lo riempie coi campi recuperati
     * dall'array di nomi passato come parametro <br>
     * Aggiunge il set alla collezione interna di set <br>
     *
     * @param nomeSet   da assegnare come chiave al set
     * @param nomeCampo elenco dei campi da inserire nel set
     *
     * @return lista il nuovo set costruito ed aggiunto alla collezione
     */
    protected ArrayList creaSet(String nomeSet, String... nomeCampo) {
        /* variabili e costanti locali di lavoro */
        ArrayList lista = null;
        ArrayList listaNomiCampi;

        try {    // prova ad eseguire il codice
            /* trasforma la lista di nomi in un array */
            listaNomiCampi = Lib.Array.creaLista(nomeCampo);

            if (listaNomiCampi != null) {
                lista = this.creaSet(nomeSet, listaNomiCampi);
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /* valore di ritorno */
        return lista;
    }


    /**
     * Crea un set di campi della scheda.
     * <p/>
     * Crea un array e lo riempie coi campi recuperati
     * dall'array di nomi passato come parametro <br>
     * Aggiunge il set alla collezione interna di set <br>
     *
     * @param set       istanza della enumeration
     * @param nomeCampo elenco dei campi da inserire nel set
     *
     * @return lista il nuovo set costruito ed aggiunto alla collezione
     */
    protected ArrayList creaSet(SetCampi set, Campi... nomeCampo) {
        /* variabili e costanti locali di lavoro */
        ArrayList lista = null;
        ArrayList listaNomiCampi;

        try {    // prova ad eseguire il codice
            listaNomiCampi = new ArrayList();

            /* traverso tutta la collezione */
            for (Campi campo : nomeCampo) {
                listaNomiCampi.add(campo.get());
            } // fine del ciclo for-each

            if (listaNomiCampi != null) {
                lista = this.creaSet(set, listaNomiCampi);
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /* valore di ritorno */
        return lista;
    }


    /**
     * Recupera il campo di una vista
     */
    protected Campo recuperaCampoVista(String unNomeVista, String unNomeCampo) {
        /** variabili e costanti locali di lavoro */
        Vista unaVista = null;
        Campo unCampo = null;

        try {    // prova ad eseguire il codice
            /** recupera la vista */
            unaVista = (Vista)this.collezioneViste.get(unNomeVista);

            /** recupera il campo */
            if (unaVista != null) {
                unCampo = unaVista.getCampoAlex(unNomeCampo);
            } /* fine del blocco if */
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /** valore di ritorno */
        return unCampo;
    } /* fine del metodo */


    /**
     * Regola il titolo di una colonna di una vista
     */
    protected void regolaTitolo(String unNomeVista, String unNomeCampo, String unTitolo) {
        /* variabili e costanti locali di lavoro */
        Campo unCampo;

        try {    // prova ad eseguire il codice
            /** recupera il campo */
            unCampo = recuperaCampoVista(unNomeVista, unNomeCampo);

            /** regola il titolo */
            if (unCampo != null) {
                unCampo.setTitoloColonna(unTitolo);
            } /* fine del blocco if */
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */
    } /* fine del metodo */


    /**
     * Regola la larghezza di una colonna di una vista
     */
    protected void regolaLarghezza(String unNomeVista, String unNomeCampo, int unaLarghezza) {
        /* variabili e costanti locali di lavoro */
        Campo unCampo;

        try {    // prova ad eseguire il codice
            /** recupera il campo */
            unCampo = recuperaCampoVista(unNomeVista, unNomeCampo);

            /** regola la larghezza */
            if (unCampo != null) {
                unCampo.setLarLista(unaLarghezza);
            } /* fine del blocco if */
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */
    } /* fine del metodo */


    /**
     * Rende visibile il campo nella lista
     * Aggiunge il nome del campo alla lista dei campi visibili in lista <br>
     *
     * @param unNomeCampo riferimento al nome dell'oggetto campo
     */
    public void setVisibileLista(String unNomeCampo) {
        /** invoca il metodo sovrascritto */
        this.setVisibileLista(unNomeCampo, CostanteCarattere.MAX);
    } /* fine del metodo */


    /**
     * Rende visibile il campo nella lista
     * Aggiunge il nome del campo alla lista dei campi visibili in lista <br>
     *
     * @param unNomeCampo riferimento al nome dell'oggetto campo
     * @param posizione   all'interno della lista di campi
     */
    public void setVisibileLista(String unNomeCampo, int posizione) {
        /* variabili e costanti locali di lavoro */
        Campo unCampo;

        /** recupera il campo */
        unCampo = getCampo(unNomeCampo);

        /** regola la proprieta' del campo */
        unCampo.getCampoLista().setPresenteVistaDefault(true);

        /** aggiunge il nome del campo alla lista */
        /** */
        if (posizione == CostanteCarattere.MAX) {
            this.nomeCampiLista.add(unNomeCampo);
        } else {
            this.nomeCampiLista.add(posizione, unNomeCampo);
        } /* fine del blocco if/else */
    } /* fine del metodo */


    /**
     * Rende invisibile il campo nella lista
     * Rimuove il nome del campo dalla lista dei campi visibili in lista <br>
     *
     * @param unNomeCampo riferimento al nome dell'oggetto campo
     */
    public void setNonVisibileLista(String unNomeCampo) {
        /* variabili e costanti locali di lavoro */
        Campo unCampo;

        /** recupera il campo */
        unCampo = getCampo(unNomeCampo);

        /** regola la proprieta' del campo */
        unCampo.getCampoLista().setPresenteVistaDefault(false);

        /** Rimuove il nome del campo dalla lista */
        this.nomeCampiLista.remove(unNomeCampo);
    } /* fine del metodo */


    /**
     * Rende visibile il campo nella scheda
     * Aggiunge il nome del campo alla lista dei campi visibili in scheda <br>
     *
     * @param unNomeCampo riferimento al nome dell'oggetto campo
     */
    public void setVisibileScheda(String unNomeCampo) {
        /** invoca il metodo sovrascritto */
        this.setVisibileScheda(unNomeCampo, CostanteCarattere.MAX);
    } /* fine del metodo */


    /**
     * Rende visibile il campo nella scheda
     * Aggiunge il nome del campo alla lista dei campi visibili in scheda <br>
     *
     * @param unNomeCampo riferimento al nome dell'oggetto campo
     * @param posizione   all'interno della lista di campi
     */
    public void setVisibileScheda(String unNomeCampo, int posizione) {
        /* variabili e costanti locali di lavoro */
        Campo unCampo;

        /** recupera il campo */
        unCampo = getCampo(unNomeCampo);

        /** regola la proprieta' del campo */
        unCampo.getCampoScheda().setPresenteScheda(true);

        /** aggiunge il nome del campo alla lista */
        /** */
        if (posizione == CostanteCarattere.MAX) {
//            this.nomeCampiScheda.add(unNomeCampo);
        } else {
//            this.nomeCampiScheda.add(posizione, unNomeCampo);
        } /* fine del blocco if/else */
    } // fine del metodo


    /**
     * Spazzola tutta la collezione di campiOldModello
     * restituisce un sotto-insieme della collezione, filtrata
     * secondo il parametro di selezione
     */
    private LinkedHashMap spazzolaCampi(int tipoCampo) {
        /* variabili e costanti locali di lavoro */
        LinkedHashMap unaCollezione;
        Iterator unGruppo;
        Campo unCampo;
        Map.Entry unaCoppia;
        String unaChiave;

        /** costruisce la nuova sotto-collezione vuota */
        unaCollezione = new LinkedHashMap();

        /** traversa tutta la collezione */
        try {    // prova ad eseguire il codice
            unGruppo = campiModello.entrySet().iterator();
            while (unGruppo.hasNext()) {
                unaCoppia = (Map.Entry)unGruppo.next();
                unaChiave = (String)unaCoppia.getKey();
                unCampo = (Campo)unaCoppia.getValue();

                /** selezione */
                switch (tipoCampo) {
                    case VISIBILI_LISTA:
                        if (unCampo.getCampoLista().isPresenteVistaDefault()) {
                            unaCollezione.put(unaChiave, unCampo);
                        } /* fine del blocco if */
                        break;
                    case VISIBILI_SCHEDA:
                        if (unCampo.getCampoScheda().isPresenteInScheda()) {
                            unaCollezione.put(unaChiave, unCampo);
                        } /* fine del blocco if */
                        break;
                    case FISSI_ALGOS:
                        if (unCampo.getCampoDB().isFissoAlgos()) {
                            unaCollezione.put(unaChiave, unCampo);
                        } /* fine del blocco if */
                        break;
                    case NON_ALGOS:
                        if (unCampo.getCampoDB().isFissoAlgos() == false) {
                            unaCollezione.put(unaChiave, unCampo);
                        } /* fine del blocco if */
                        break;
                    default:
                        break;
                } /* fine del blocco switch */

            } /* fine del blocco while */
        } catch (Exception unErrore) {    // intercetta l'errore
            /** mostra il messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /** valore di ritorno */
        return unaCollezione;
    } /* fine del metodo */


    /**
     * Recupera l'albero di campi per questo Modulo.
     * <p/>
     * L'albero e' cosi' strutturato:
     * al primo livello tutti i campi fisici locali
     * sotto a ogni campo linkato da un altro modulo,
     * gli alberi dei moduli linkati (ricorsivo).
     * Ogni nodo
     *
     * @return il modello dati dell'albero
     */
    public AlberoModello getAlberoCampi() {
        /* variabili e costanti locali di lavoro */
        AlberoModello datiAlbero = null;
        AlberoNodo nodoRoot;
        AlberoNodo nodo;
        HashMap mappaCampiModello;
        Campo campo;
        AlberoModello sottoAlbero;
        Modulo moduloLinkato;

        try {    // prova ad eseguire il codice

            /* crea il modello dell'albero */
            datiAlbero = new AlberoModello();

            /* crea il nodo root con la tavola */
            nodoRoot = new AlberoNodo();
            nodoRoot.setUserObject(this.getModulo());

            /* aggiunge il nodo root */
            datiAlbero.addNodo(nodoRoot, null);

            mappaCampiModello = this.getCampiFisici();
            for (Object oggetto : mappaCampiModello.values()) {
                if (oggetto != null) {
                    if (oggetto instanceof Campo) {

                        /* recupera il campo */
                        campo = (Campo)oggetto;

                        /* crea il nuovo nodo col campo*/
                        nodo = new AlberoNodo(campo);

                        /* aggiunge il nodo all'albero */
                        datiAlbero.addNodo(nodo, nodoRoot);

                        /* Se e' un campo linkato, recupera ricorsivamente
                         * l'albero campi del modulo linkato e lo aggiunge al nodo
                         * del campo */
                        if (campo.getCampoDB().isLinkato()) {
                            moduloLinkato = campo.getCampoDB().getModuloLinkato();
                            sottoAlbero = moduloLinkato.getAlberoCampi();
                            nodo.add(sottoAlbero.getNodoRoot());
                        }// fine del blocco if

                    }// fine del blocco if
                }// fine del blocco if

            } // fine del ciclo for-each

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return datiAlbero;
    }


    /**
     * Rende visibile il campo ordine nella lista
     * <p/>
     *
     * @param flag di visibilità
     */
    public void setCampoOrdineVisibileLista(boolean flag) {
        /* variabili e costanti locali di lavoro */
        Campo unCampo;

        try {    // prova ad eseguire il codice
            unCampo = this.getCampo(NOME_CAMPO_ORDINE);
            if (unCampo != null) {
                unCampo.setVisibileVistaDefault(flag);
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */
    }


    /**
     * Rende visibile il campo ordine nella lista
     */
    protected void setCampoOrdineVisibileLista() {
        this.setCampoOrdineVisibileLista(true);
    }


    /**
     * Rende visibile il campo note nella scheda
     */
    protected void setCampoNoteVisibileScheda() {
        /* variabili e costanti locali di lavoro */
        Campo unCampo = null;

        try {    // prova ad eseguire il codice
            unCampo = this.getCampo(NOME_CAMPO_NOTE);
            unCampo.getCampoScheda().setPresenteScheda(true);
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */
    } /* fine del metodo */


    /**
     * Regola i titoli delle finestre lista e scheda
     * Puo' essere sovrascritto dalle sottoclassi
     */
    protected void regolaTitoli() {
        /* di default suppongo che il nome della finestra
         * sia lo stesso della tavola */
        this.unNomeFinestra = this.unaTavolaArchivio;

        /** regola i titoli delle finestre per questo modello */
        this.unTitoloLista = unNomeFinestra + " - lista";
        this.unTitoloScheda = unNomeFinestra + " - scheda";
    } /* fine del metodo */


    /**
     * Restituisce il campo valore richiesto.
     * <p/>
     * Recupera il campo da questo modello <br>
     * Invoca il metodo statico in libreria <br>
     *
     * @param lista     array coppia campo-valore
     * @param nomeCampo da ricercare
     *
     * @return campo-valore richiesto
     */
    protected CampoValore getCampoValore(ArrayList<CampoValore> lista, String nomeCampo) {
        /* variabili e costanti locali di lavoro */
        CampoValore campoValore = null;
        Campo campo;

        try { // prova ad eseguire il codice
            /* Recupera il campo da questo modello */
            campo = this.getCampo(nomeCampo);

            /* Invoca il metodo statico in libreria */
            campoValore = Lib.Mod.getCampoValore(lista, campo);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return campoValore;
    }


    /**
     * Intercetta le chiamate al database.
     * <p/>
     * Risolve i campi calcolati <br>
     * Smista le chiamate ai metodi specializzati, sovrascrivibili <br>
     * <p/>
     * Tabella delle informazioni passate ai metodi sovrascrivibili:
     * +---------------+--------+--------------+-----------+
     * |tipo di        | codice | lista da     |lista dei  |
     * |operazione     | record | registrare   |valori     |
     * |               |        | o registrata |precedenti |
     * |---------------+--------+--------------+-----------|
     * |Nuovo Ante     |        |      X       |           |
     * |Nuovo Post     |   X    |      X       |           |
     * |Modifica Ante  |   X    |      X       |           |
     * |Modifica Post  |   X    |      X       |     X     |
     * |Elimina Ante   |   X    |              |           |
     * |Elimina Post   |   X    |              |     X     |
     * +---------------+--------+--------------+-----------+
     * Nota: per ottimizzare le prestazioni, la lista dei valori
     * precedenti viene recuperata e passata avanti solo se il
     * relativo flag è attivo nel Modello.
     *
     * @param operazione codice del tipo di operazione
     * @param codice     codice del record interessato
     * @param lista      di oggetti CampoValore da registrare o registrata
     * @param listaPre   lista di oggetti CampoValore precedenti l'operazione
     * @param conn       la connessione utilizzata per effettuare la query
     *
     * @return true se riuscito
     */
    public boolean trigger(Modello.Trigger operazione,
                           int codice,
                           ArrayList<CampoValore> lista,
                           ArrayList<CampoValore> listaPre,
                           Connessione conn) {

        /* variabili e costanti locali di lavoro */
        boolean riuscito = true;
        SetValori sv;

        try { // prova ad eseguire il codice

            switch (operazione) {

                case nuovoAnte:

                    /* regola i campi calcolati */
                    this.calcLista(lista, true, 0, conn);

                    /* se il trigger è attivo invoca il metodo sovrascrivibile */
                    if (this.isTriggerNuovoAttivo()) {

                        /**
                         * invoca il metodo sovrascrivibile passando la lista di oggetti CampoValore
                         * (vecchio sistema)
                         */
                        riuscito = this.nuovoRecordAnte(lista, conn);

                        /**
                         * invoca il metodo sovrascrivibile passando l'oggetto SetValori
                         * (nuovo sistema)
                         */
                        if (riuscito) {
                            sv = new SetValori(this.getModulo(), lista);
                            riuscito = this.nuovoRecordAnte(sv, conn);
                            lista = sv.getListaValori();
                        }// fine del blocco if

                        /* regola nuovamente i campi calcolati
                         * (la lista può essere stata modificata) */
                        if (riuscito) {
                            this.calcLista(lista, true, 0, conn);
                        }// fine del blocco if
                    }// fine del blocco if
                    break;

                case nuovoPost:
                    /* se il trigger è attivo invoca il metodo sovrascrivibile */
                    if (this.isTriggerNuovoAttivo()) {

                        /**
                         * invoca il metodo sovrascrivibile passando la lista di oggetti CampoValore
                         * (vecchio sistema)
                         */
                        riuscito = this.nuovoRecordPost(codice, lista, conn);

                        /**
                         * invoca il metodo sovrascrivibile passando l'oggetto SetValori
                         * (nuovo sistema)
                         */
                        if (riuscito) {
                            sv = new SetValori(this.getModulo(), lista);
                            riuscito = this.nuovoRecordPost(codice, sv, conn);
                            lista = sv.getListaValori();
                        }// fine del blocco if


                    }// fine del blocco if

                    /* dopo il post, lancia l'evento */
                    this.fireTrigger(Db.TipoOp.nuovo, conn, codice, lista, null);

                    break;

                case modificaAnte:

                    /* regola i campi calcolati */
                    this.calcLista(lista, false, codice, conn);

                    /* se il trigger è attivo invoca il metodo sovrascrivibile */
                    if (this.isTriggerModificaAttivo()) {
                        riuscito = this.registraRecordAnte(codice, lista, conn);
                        /* regola nuovamente i campi calcolati
                         * (la lista può essere stata modificata) */
                        if (riuscito) {
                            this.calcLista(lista, false, codice, conn);
                        }// fine del blocco if
                    }// fine del blocco if
                    break;

                case modificaPost:

                    /* se il trigger è attivo invoca il metodo sovrascrivibile */
                    if (this.isTriggerModificaAttivo()) {
                        riuscito = this.registraRecordPost(codice, lista, listaPre, conn);

                        /**
                         * invoca il metodo sovrascrivibile passando l'oggetto SetValori
                         * (nuovo sistema)
                         */
                        if (riuscito) {
                            sv = new SetValori(this.getModulo(), lista);
                            SetValori svPre = new SetValori(this.getModulo(), listaPre);
                            riuscito = this.registraRecordPost(codice, sv, svPre, conn);
                            lista = sv.getListaValori();
                        }// fine del blocco if


                    }// fine del blocco if

                    /* dopo il post, lancia l'evento */
                    this.fireTrigger(Db.TipoOp.modifica, conn, codice, lista, listaPre);
                    break;

                case eliminaAnte:

                    /* se il trigger è attivo invoca il metodo sovrascrivibile */
                    if (this.isTriggerEliminaAttivo()) {
                        riuscito = this.eliminaRecordAnte(codice, conn);
                    }// fine del blocco if
                    break;

                case eliminaPost:

                    /* se il trigger è attivo invoca il metodo sovrascrivibile */
                    if (this.isTriggerEliminaAttivo()) {
                        riuscito = this.eliminaRecordPost(codice, listaPre, conn);
                    }// fine del blocco if

                    /* dopo il post, lancia l'evento */
                    this.fireTrigger(Db.TipoOp.elimina, conn, codice, null, listaPre);
                    break;

                default: // caso non definito
                    break;
            } // fine del blocco switch

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return riuscito;
    } // fine del metodo


    /**
     * Elabora una lista di oggetti CampoValore.
     * <p/>
     * Elabora una lista di oggetti CampoValore e restituisce la
     * stessa lista integrata con i tutti campi calcolati necessari
     * per mantenere il database sincronizzato.<br>
     * Se si tratta di nuovo record, garantisce che la lista contenga
     * anche tutti i campi fisici non calcolati con i valori forniti
     * o in mancanza con i valori di default per nuovo record.
     *
     * @param listaIn di oggetti CampoValore da elaborare
     * @param nuovo   tipo di operazione, true per nuovo false per modifica
     * @param codice  del record solo in caso di modifica
     * @param conn    la connessione da utilizzare per accedere al database
     */
    private ArrayList<CampoValore> calcLista(ArrayList<CampoValore> listaIn,
                                             boolean nuovo,
                                             int codice,
                                             Connessione conn) {
        /* variabili e costanti locali di lavoro */
        ArrayList<CampoValore> listaOut = null;
        ArrayList<CampoValore> listaCV = null;
        CalcResolver resolver;

        try {    // prova ad eseguire il codice

            /* se nuovo, si assicura che ci siano tutti i
             * campi fisici non calcolati */
            if (nuovo) {
                listaCV = calcListaNuovo(listaIn, conn);
            } else {
                listaCV = listaIn;
            }// fine del blocco if-else

            /* elabora la lista per i campi calcolati */
            resolver = new CalcResolver(this, listaCV, nuovo, codice, conn);
            resolver.avvia();

            /* lista finale in uscita */
            listaOut = resolver.getListaElaborata();

            /* sostituisce la lista in ingresso con quella elaborata */
            listaIn.clear();
            listaIn.addAll(listaOut);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return listaOut;
    }


    /**
     * Regola la lista dei CampiValore per la creazione di un nuovo record.
     * <p/>
     * Si assicura che nella lista in uscita ci siano tutti i
     * campi non calcolati del modello.
     * Per i campi mancanti usa i valori di default.
     *
     * @param listaIn la lista valori in ingresso
     * @param conn    la connessione da utilizzare
     *
     * @return la lista completa per nuovo record (esclusi i campi calcolati)
     */
    private ArrayList<CampoValore> calcListaNuovo(ArrayList<CampoValore> listaIn,
                                                  Connessione conn) {
        /* variabili e costanti locali di lavoro */
        ArrayList<CampoValore> listaOut = null;
        LinkedHashMap<String, Campo> campiModello;
        CampoValore cvChiave;
        CampoValore cv;
        Object valore;
        Campo campoChiaveMod;
        Campo campoChiaveLista;

        try {    // prova ad eseguire il codice

            listaOut = new ArrayList<CampoValore>();
            campoChiaveMod = this.getCampoChiave();

            /* controlla se il campo chiave è nella lista.
             * se non c'è, lo aggiunge ora */
            cvChiave = Lib.Camp.getCampoValore(listaIn, campoChiaveMod);
            if (cvChiave == null) {

                cvChiave = new CampoValore(campoChiaveMod, null);


            }// fine del blocco if-else

            /* aggiunge il campo chiave alla lista in uscita */
            listaOut.add(cvChiave);

            /* se manca il codice, assegna il valore al campo chiave
         * recuperandolo dall'inizializzatore */
            if ((cvChiave.getValore() == null) || ((Integer)cvChiave.getValore() == 0)) {
                campoChiaveLista = cvChiave.getCampo();
                valore = campoChiaveLista.getCampoDati().getValoreNuovoRecord(conn);
                cvChiave.setValore(valore);
            }// fine del blocco if

            /* spazzola i campi fisici non calcolati del modello
             * (escluso il campo chiave, già regolato)
             * e controlla se sono nella lista.
             * Se sono nella lista in ingresso li usa,
             * altrimenti li crea con valore di default per nuovo record */
            campiModello = this.getCampiFisici();
            for (Campo campo : campiModello.values()) {
                if (!campo.isCalcolato()) {
                    if (!campo.equals(this.getCampoChiave())) {
                        cv = Lib.Camp.getCampoValore(listaIn, campo);
                        if (cv == null) {
                            valore = campo.getCampoDati().getValoreNuovoRecord(conn);
                            cv = new CampoValore(campo, valore);
                        }// fine del blocco if-else
                        listaOut.add(cv);
                    }// fine del blocco if
                }// fine del blocco if
            }

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return listaOut;
    }


    /**
     * Metodo invocato prima della creazione di un nuovo record.
     * <p/>
     * Puo' modificare i valori che stanno per essere registrati<br>
     * Viene sovrascritto dalle classi specifiche <br>
     * Le eventuali modifiche vanno fatte sulla lista che viene
     * passata come parametro.
     *
     * @param lista array coppia campo-valore contenente i
     *              dati che stanno per essere registrati
     * @param conn  la connessione utilizzata per effettuare la query
     *
     * @return true per continuare il processo di registrazione,
     *         false per non effettuare la registrazione
     */
    protected boolean nuovoRecordAnte(ArrayList<CampoValore> lista, Connessione conn) {
        return true;
    } // fine del metodo


    /**
     * Metodo invocato prima della creazione di un nuovo record.
     * <p/>
     * Puo' modificare i valori che stanno per essere registrati<br>
     * Viene sovrascritto dalle classi specifiche <br>
     * Le eventuali modifiche vanno fatte sulla lista che viene
     * passata come parametro.
     *
     * @param set  oggetto set di valori contenente i
     *             dati che stanno per essere registrati
     * @param conn la connessione utilizzata per effettuare la query
     *
     * @return true per continuare il processo di registrazione,
     *         false per non effettuare la registrazione
     */
    protected boolean nuovoRecordAnte(SetValori set, Connessione conn) {
        return true;
    } // fine del metodo


    /**
     * Metodo invocato dopo la creazione di un nuovo record.
     * <p/>
     * Viene sovrascritto dalle classi specifiche <br>
     *
     * @param codice del record creato
     * @param lista  array coppia campo-valore contenente
     *               i dati appena registrati
     * @param conn   la connessione utilizzata per effettuare la query
     *
     * @return true se riuscito
     */
    protected boolean nuovoRecordPost(int codice, ArrayList<CampoValore> lista, Connessione conn) {
        return true;
    } // fine del metodo


    /**
     * Metodo invocato dopo la creazione di un nuovo record.
     * <p/>
     * Viene sovrascritto dalle classi specifiche <br>
     *
     * @param codice del record creato
     * @param set    oggetto set di valori contenente
     *               i dati appena registrati
     * @param conn   la connessione utilizzata per effettuare la query
     *
     * @return true se riuscito
     */
    protected boolean nuovoRecordPost(int codice, SetValori set, Connessione conn) {
        return true;
    } // fine del metodo


    /**
     * Metodo invocato prima della registrazione di un record esistente.
     * <p/>
     * Puo' modificare i valori che stanno per essere registrati<br>
     * Viene sovrascritto dalle classi specifiche <br>
     * Le eventuali modifiche vanno fatte sulla lista che viene
     * passata come parametro.
     *
     * @param codice del record
     * @param lista  array coppia campo-valore contenente i
     *               dati che stanno per essere registrati
     * @param conn   connessione utilizzata
     *
     * @return true per continuare il processo di registrazione,
     *         false per non effettuare la registrazione
     */
    protected boolean registraRecordAnte(int codice,
                                         ArrayList<CampoValore> lista,
                                         Connessione conn) {
        return true;
    } // fine del metodo


    /**
     * Metodo invocato dopo la registrazione di un record esistente.
     * <p/>
     * Viene sovrascritto dalle classi specifiche <br>
     *
     * @param codice   del record
     * @param lista    array coppia campo-valore contenente
     *                 i dati appena registrati
     * @param listaPre array coppia campo-valore contenente
     *                 i dati precedenti la registrazione
     * @param conn connessione utilizzata per la registrazione
     *
     * @return true se riuscito
     */
    protected boolean registraRecordPost(int codice,
                                         ArrayList<CampoValore> lista,
                                         ArrayList<CampoValore> listaPre,
                                         Connessione conn) {
        return true;
    } // fine del metodo


    /**
     * Metodo invocato dopo la registrazione di un record esistente.
     * <p/>
     * Viene sovrascritto dalle classi specifiche <br>
     *
     * @param codice   del record
     * @param set set dei valori registrati
     * @param setPre set dei valori precedenti la registrazione
     * @param conn connessione utilizzata per la registrazione
     * @return true se riuscito
     */
    protected boolean registraRecordPost(int codice,
                                         SetValori set,
                                         SetValori setPre,
                                         Connessione conn) {
        return true;
    } // fine del metodo



    /**
     * Metodo invocato prima della eliminazione di un record esistente.
     * <p/>
     * Viene sovrascritto dalle classi specifiche <br>
     *
     * @param codice del record da eliminare
     * @param conn   la connessione utilizzata per effettuare la query
     *
     * @return true per continuare il processo di eliminazione,
     *         false per non effettuare la eliminazione
     */
    protected boolean eliminaRecordAnte(int codice, Connessione conn) {
        return true;
    } // fine del metodo


    /**
     * Metodo invocato dopo l'eliminazione di un record esistente.
     * <p/>
     * Viene sovrascritto dalle classi specifiche <br>
     *
     * @param codice   del record eliminato
     * @param listaPre lista dei valori contenuti nel record al momento della eliminazione
     * @param conn     la connessione utilizzata per effettuare la query
     *
     * @return true se riuscito
     */
    protected boolean eliminaRecordPost(int codice,
                                        ArrayList<CampoValore> listaPre,
                                        Connessione conn) {
        return true;
    } // fine del metodo


    /**
     * Regola i titoli delle finestre lista e scheda
     * Puo' essere sovrascritto dalle sottoclassi
     * controlla che i titoli non siano vuoti
     */
    protected void regolaTitoli(String titoloLista, String titoloScheda) {
        if (titoloLista == "") {
            this.setTitoloLista(unaTavolaArchivio + " - lista");
        } else {
            this.setTitoloLista(titoloLista);
        } /* fine del blocco if/else */

        if (titoloScheda == "") {
            this.setTitoloScheda(unaTavolaArchivio + " - scheda");
        } else {
            this.setTitoloScheda(titoloScheda);
        } /* fine del blocco if/else */
    } /* fine del metodo */


    /**
     * Creazione dei campi.
     * <p/>
     * Metodo invocato dal ciclo prepara del modulo <br>
     * Creazione dei campi record di questo modello <br>
     * I campi verranno visualizzati nell'ordine di inserimento <br>
     * Ogni campo viene creato con un costruttore semplice con solo le piu'
     * comuni informazioni; le altre vengono regolate con chiamate successive <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     * (metodo chiamato dalla superclasse) <br>
     */
    protected void creaCampi() {
    }


    /**
     * Aggiunge un campo "note" standard al modello.
     * <p/>
     *
     * @return il campo Note aggiunto
     */
    protected Campo creaCampoNote() {
        return null;
    } /* fine del metodo */


    /**
     * Aggiunge un campo "preferito" al modello.
     * <p/>
     */
    protected void creaCampoPreferito() {
    } /* fine del metodo */


    /**
     * Ritorna il codice del record preferito.
     * <p/>
     *
     * @return il codice del record impostato come preferito
     */
    public int getRecordPreferito() {
        return 0;
    } /* fine del metodo */


    /**
     * Creazione delle viste aggiuntive.
     * <p/>
     * Metodo invocato dal ciclo statico del progetto <br>
     * Eventuale creazione di viste aggiuntive, oltre alla vista base di default <br>
     * Costruisce degli ArrayList di riferimenti ordinati (oggetti <code>Vista</code>) per
     * individuare i campi che voglio vedere nelle liste alternative ed
     * aggiuntive a quella standard (costruita in automatico nella superclasse) <br>
     * Gli array vengono creati coi campi di questo modello, oppure con
     * viste di altri moduli, oppure con campi di altri modelli <br>
     * Viene chiamato <strong>dopo</strong> che nella sottoclasse sono stati costruiti tutti i campi <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     * (metodo chiamato dalla superclasse) <br>
     *
     * @see it.algos.base.costante.CostanteModulo#VISTA_BASE_DEFAULT
     * @see it.algos.base.costante.CostanteModello#NOME_CAMPO_SIGLA
     */
    protected void creaViste() {
    }


    /**
     * Regolazione delle viste aggiuntive.
     * <p/>
     * Metodo invocato dal ciclo inizializza <br>
     * Eventuale regolazione delle caratteristiche specifiche di ogni copia
     * dei campi delle viste; le variazioni modificano <strong>solo</strong> le copie <br>
     * Viene chiamato <strong>dopo</strong> che nella superclasse sono state
     * <strong>clonate</strong> tutte le viste <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     * (metodo chiamato dalla superclasse) <br>
     *
     * @see it.algos.base.costante.CostanteModulo#VISTA_BASE_DEFAULT
     * @see it.algos.base.costante.CostanteModello#NOME_CAMPO_SIGLA
     */
    protected void regolaViste() {
    }


    /**
     * eventuale regolazione dell'ordinamento dei campi visibili nella scheda
     * sovrascritto dalla sottoclasse
     */
    protected void regolaCampiScheda() {
    } /* fine del metodo */


    /**
     * Creazione dei set aggiuntivi.
     * <p/>
     * Metodo invocato dal ciclo statico del progetto <br>
     * Eventuale creazione di set aggiuntivi, oltre al set base di default <br>
     * Costruisce degli ArrayList di riferimenti ordinati (oggetti <code>Campo</code>) per
     * individuare i campi che voglio vedere in un set di campi scheda <br>
     * Gli array vengono creati coi campi di questo modello, oppure con
     * campi di altri moduli <br>
     * Viene chiamato <strong>dopo</strong> che nella sottoclasse specifica sono stati
     * costruiti tutti i campi <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     * (metodo chiamato dalla superclasse) <br>
     */
    protected void creaSet() {
    } /* fine del metodo */

//    /**
//     * Creazione del gruppo di campi per la ricerca di default.
//     * <p/>
//     * Metodo invocato dal ciclo statico del progetto <br>
//     * Spazzola tutti i campi del modello <br>
//     * Aggiunge alla lista quelli col flag (ricercabile) attivo <br>
//     */
//    private void creaSetRicerca() {
//        List lista = null;
//        Campo unCampo = null;
//
//        try { // prova ad eseguire il codice
//            /* recupera la variabile d'istanza */
//            lista = this.getCampiRicercabili();
//
//            Iterator unGruppo = getCampiModello().values().iterator();
//
//            while (unGruppo.hasNext()) {
//                unCampo = (Campo)unGruppo.next();
//                if (unCampo.getCampoLogica().isRicercabile()) {
//                    lista.add(unCampo);
//                }// fine del blocco if
//            } /* fine del blocco while */
//
//        } catch (Exception unErrore) { // intercetta l'errore
//            Errore.crea(unErrore);
//        }// fine del blocco try-catch
//    } /* fine del metodo */


    /**
     * Regolazione dei set aggiuntivi.
     * <p/>
     * Metodo invocato dal ciclo inizializza <br>
     * Eventuale regolazione delle caratteristiche specifiche di ogni copia
     * dei campi dei set; le variazioni modificano <strong>solo</strong> le copie <br>
     * Viene chiamato <strong>dopo</strong> che nella sottoclasse specifica sono stati
     * costruiti tutti i campi <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     * (metodo chiamato dalla superclasse) <br>
     */
    protected void regolaSet() {
    } /* fine del metodo */


    /**
     * Crea un lista di filtri vuota e la aggiunge alla collezione.
     * <p/>
     * Metodo invocato nelle sottoclassi <br>
     *
     * @return lista di filtri (una per ogni popup)
     */
    protected WrapFiltri addPopFiltro() {
        /* variabili e costanti locali di lavoro */
        WrapFiltri listaFiltri = null;
        ArrayList<WrapFiltri> filtriPop;

        try { // prova ad eseguire il codice

            /* recupera la collezione di liste di filtri */
            filtriPop = this.getFiltriPop();

            /* crea la nuova lista */
            listaFiltri = new WrapFiltri();

            /* aggiunge la nuova lista alla collezione */
            filtriPop.add(listaFiltri);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return listaFiltri;
    }


    /**
     * Aggiunge un nuovo filtroPop all'elenco dei filtri Pop.
     * <p/>
     *
     * @param filtroPop da aggiungere
     */
    protected void addPopFiltro(WrapFiltri filtroPop) {
        /* variabili e costanti locali di lavoro */
        ArrayList<WrapFiltri> filtriPop;

        try { // prova ad eseguire il codice

            /* recupera la collezione di filtriPop */
            filtriPop = this.getFiltriPop();

            /* aggiunge il nuovo filtroPop */
            if (filtriPop != null) {
                filtriPop.add(filtroPop);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Aggiunge un filtro alla prima lista di filtri.
     * <p/>
     * Metodo invocato nelle sottoclassi <br>
     * Se la prima lista di filtri non esiste, la crea al volo <br>
     *
     * @param filtro da aggiungere
     * @param nome   visibile nei popup
     */
    protected void add(Filtro filtro, String nome) {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        WrapFiltri listaFiltri;
        ArrayList<WrapFiltri> filtriPop;

        try { // prova ad eseguire il codice
            /* recupera la collezione di liste di filtri */
            filtriPop = this.getFiltriPop();
            continua = (filtriPop != null) && (filtro != null);

            /* regola il nome */
            if (continua) {
                if (Lib.Testo.isValida(nome)) {
                    filtro.setNome(nome);
                }// fine del blocco if
            }// fine del blocco if

            /* crea la nuova lista */
            if (continua) {
                if (filtriPop.size() < 1) {
                    filtriPop.add(new WrapFiltri());
                }// fine del blocco if
            }// fine del blocco if

            /* aggiunge la nuova lista alla collezione */
            if (continua) {
                listaFiltri = filtriPop.get(0);
                listaFiltri.add(filtro);
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Aggiunge un filtro alla prima lista di filtri.
     * <p/>
     * Metodo invocato nelle sottoclassi <br>
     * Se la prima lista di filtri non esiste, la crea al volo <br>
     *
     * @param filtro da aggiungere
     */
    protected void add(Filtro filtro) {
        this.add(filtro, "");
    }


    /**
     * Regolazione dei filtri per i popup.
     * <p/>
     * Metodo invocato dal ciclo inizializza <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     * (metodo chiamato dalla superclasse) <br>
     * <p/>
     * Crea uno o più filtri alla lista, tramite un popup posizionato in basso a destra <br>
     * I popup si posizionano bandierati a destra,
     * ma iniziando da sinistra (secondo l'ordine di creazione) <br>
     */
    protected void regolaFiltriPop() {
    } /* fine del metodo */


    /**
     * Creazione dei passaggi obbligati di questo modello
     * sovrascritto dalla sottoclasse
     */
    protected void creaPassaggiObbligati() {
    } /* fine del metodo */


    /**
     * Aggiunge un passaggio obbligato (statico) all'elenco del Modello.<br>
     * Costruisce un oggetto TreStringhe contenente i nomi e lo
     * aggiunge all'elenco
     *
     * @param nomeModuloDestinazione il nome del modulo della tavola da raggiungere
     * @param nomeModuloPassaggio    il nome del modulo dal quale passare
     * @param nomeCampoPassaggio     il nome del campo dal quale passare
     */
    protected void addPassaggioObbligato(String nomeModuloDestinazione,
                                         String nomeModuloPassaggio,
                                         String nomeCampoPassaggio) {
        try {    // prova ad eseguire il codice
            TreStringhe unPassaggio = new TreStringhe(nomeModuloDestinazione,
                    nomeModuloPassaggio,
                    nomeCampoPassaggio);

            this.getModulo().getModuloBase().addPassaggioObbligato(unPassaggio);

        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } // fine del blocco try-catch

    } /* fine del metodo */


    /**
     * Aggiunge un passaggio obbligato (statico) all'elenco del Modello.<br>
     *
     * @param nomeModuloDestinazione il nome del modulo della tavola da raggiungere
     * @param nomeCampoPassaggio     il nome del campo di questo modulo dal quale passare
     */
    protected void addPassaggioObbligato(String nomeModuloDestinazione, String nomeCampoPassaggio) {
        /* variabili e costanti locali di lavoro */
        String nomeModuloPassaggio = null;

        nomeModuloPassaggio = this.getModulo().getNomeChiave();
        this.addPassaggioObbligato(nomeModuloDestinazione, nomeModuloPassaggio, nomeCampoPassaggio);

    } /* fine del metodo */


    /**
     * Crea un database in memoria con la struttura dati di questo modello.
     * <p/>
     *
     * @param nomeDb nome del database da creare
     */
    public Db creaDbMemoria(String nomeDb) {
        /* variabili e costanti locali di lavoro */
        Db db = null;
        Connessione conn;
        LinkedHashMap<String, Campo> campi;

        try {    // prova ad eseguire il codice

            /* crea e inizializza un database HSQLDB in memoria */
            db = (DbSqlHsqldb)DbFactory.crea(Db.SQL_HSQLDB);
            db.setModoFunzionamento(Db.MODO_STAND_ALONE);
            db.setTipoAccessoDati(Db.ACCESSO_DATI_MEMORIA);
            db.setNomeDatabase(nomeDb);
            db.inizializza();

            /* crea e apre una connessione */
            conn = db.creaConnessione();
            conn.open();

            /* crea la tavola e i campi sul database */
            conn.creaTavola(this.getModulo());
            campi = this.getCampiFisici();
            for (Campo campo : campi.values()) {
                conn.allineaCampo(campo);
            }

            /* chiude la connesione */
            conn.close();

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return db;
    }


    /**
     * regolazioni particolari sui singoli campi
     */
    public void regolaNuovoRecord() {
    } /* fine del metodo */


    /**
     * Recupero dei dati standard.
     * <p/>
     * Se la tavola e' vuota, e se esistono dei dati di default,
     * riempie la tavola con i dati di default.
     */
    public void recuperaDatiStandard() {
        /* variabili e costanti locali di lavoro */
        Db dbDefault;

        try { // prova ad eseguire il codice

            if (this.isTavolaVuota()) {
                Progetto.creaDbDefault();

                if (this.isEsistonoDatiStandard()) {
                    this.caricaDatiStandard();
                }// fine del blocco if

                /* se il database e' in modo monoutenza, dopo
                 * l'eventuale recupero effettua uno SHUTDOWN */
                dbDefault = Progetto.getDbDefault();
                if (dbDefault != null) {
                    if (dbDefault.getModoFunzionamento() == Db.MODO_STAND_ALONE) {
                        if (dbDefault.isMotoreAcceso()) {
                            dbDefault.shutdown();
                        }// fine del blocco if
                    }// fine del blocco if
                }// fine del blocco if

            }// fine del blocco if


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    } // fine del metodo


    /**
     * Registra i dati di default.
     * <p/>
     * Registra sul database dei dati di default i dati
     * contenuti nella tavola di un modello, prendendoli
     * dal database in uso al Modulo <br>
     * I dati di default vengono completamente sostituiti <br>
     *
     * @return true se eseguito correttamente
     */
    public boolean registraDatiDefault() {
        /* invoca il metodo delegato della classe */
        return Lib.Mod.registraDatiDefault(this);
    }


    /**
     * Controlla se la tavola del Modello e' vuota.
     * <p/>
     *
     * @return true se la tavola e' vuota.
     */
    private boolean isTavolaVuota() {
        /* variabili e costanti locali di lavoro */
        boolean isVuota = false;
        Query q = null;
        Dati d = null;

        try {    // prova ad eseguire il codice
            q = new QuerySelezione(this.getModulo());
            q.addCampo(this.getCampoChiave());
            d = this.getModulo().query().querySelezione(q);
            if (d.getRowCount() == 0) {
                isVuota = true;
            }// fine del blocco if
            d.close();
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return isVuota;
    }


    /**
     * Controlla se esistono dei dati di default per questo Modulo/Modello.
     * <p/>
     * Cerca nella cartella dei dati di default i file
     * che si chiamano <modulo>.properties e <modulo>.script.
     * Se li trova entrambi, assume che esistano dei dati di default
     * per il modulo.
     *
     * @return true se esistono dei dati di default
     */
    private boolean isEsistonoDatiStandard() {
        /* invoca il metodo delegato della classe */
        return Lib.Mod.isEsistonoDatiStandard(this);
    }


    /**
     * Carica i dati di default per questo Modulo/Modello.
     * <p/>
     */
    private void caricaDatiStandard() {
        /* invoca il metodo delegato della classe */
        Lib.Mod.caricaDatiDefault(this);
    }


    /**
     * Restituisce un estratto da una catena di due campi.
     * </p>
     * Questo metodo viene chiamato da un metodo specifico di una sottoclasse
     * a cui si chiede un Estratto di un'altra classe linkata <br>
     * Il primo campo deve essere un Campo di questo Modulo, con un CampoDB
     * di tipo CDBLinkato <br>
     * Da questo campo si recupera il modulo linkato ed il codice del legame
     * di relazione con l'altra tavola <br>
     * Con questi dati ed il nome del campo linkato della seconda tavola, si
     * recupera l'estratto <br>
     *
     * @param nomeCampoLink  campo di tipo link della prima tavola
     * @param nomeCampoTesto campo di tipo testo della seconda tavola
     *
     * @return l'estratto costruito con la stringa ricavata
     */
    protected EstrattoBase getEstrattoLink(String nomeCampoLink,
                                           String nomeCampoTesto,
                                           int codicerecord) {
        /* variabili e costanti locali di lavoro */
        EstrattoBase estratto = null;
        Campo unCampo = null;
        Object unOggetto = null;
        int codice = 0;
        Integer numero = null;

        try {    // prova ad eseguire il codice

            /* recupera il campo link ed il modulo linkato */
            unCampo = this.getCampo(nomeCampoLink);

            /* recupera il codice del campo */
            // todo eventualmente da reimplementare alex 28-01-05
//            unOggetto = QueryFactoryOld.valoreCampo(unCampo, codicerecord);

            if (unOggetto != null) {
                numero = (Integer)unOggetto;
                codice = numero.intValue();
            }// fine del blocco if

            if (codice > 0) {
                /* recupera l'estratto */
                // todo eventualmente da reimplementare alex 28-01-05

//                estratto
//                        = QueryFactoryOld.estrattoModulo(modulo, nomeCampoTesto, codice);
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return estratto;
    } // fine del metodo


    /**
     * Restituisce un estratto di tipo stringa dal singolo campo.
     * </p>
     * Controlla che esista il campo <br>
     * Recupera il valore del campo, che deve essere di tipo testo <br>
     * Crea un oggetto Estratto col valore recuperato <br>
     *
     * @param nomeCampo campo con valore valido di testo
     * @param codRec    record sul quale effettuare la ricerca
     *
     * @return l'estratto costruito con la stringa ricavata
     */
    protected EstrattoBase getEstrattoTesto(String nomeCampo, int codRec) {
        /* variabili e costanti locali di lavoro */
        EstrattoBase estratto = null;
        Campo unCampo;
        Modulo modulo;
        String unTesto;

        try {    // prova ad eseguire il codice
            /* controlla che il parametro corrisponda ad un campo */
            if (this.isEsisteCampo(nomeCampo)) {
                /* recupera il campo */
                unCampo = this.getCampo(nomeCampo);

                /* recupera il valore corrente del campo */
                modulo = unCampo.getModulo();
                unTesto = modulo.query().valoreStringa(nomeCampo, codRec);

                /* il campo deve restituire un valore non nullo */
                if (Lib.Testo.isValida(unTesto)) {
                    estratto = new EstrattoBase(unTesto, EstrattoBase.Tipo.stringa);
                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return estratto;
    }


    /**
     * Restituisce un estratto di tipo stringa dal singolo campo.
     * </p>
     * Controlla che esista il campo <br>
     * Recupera il valore del campo, che deve essere di tipo testo <br>
     * Crea un oggetto Estratto col valore recuperato <br>
     *
     * @param nomeCampo campo con valore valido di testo
     * @param oggCodRec record sul quale effettuare la ricerca
     *
     * @return l'estratto costruito con la stringa ricavata
     */
    protected EstrattoBase getEstrattoTesto(String nomeCampo, Object oggCodRec) {
        /* variabili e costanti locali di lavoro */
        EstrattoBase estratto = null;
        boolean continua = false;
        int codRec = 0;

        try {    // prova ad eseguire il codice
            try { // prova ad eseguire il codice
                codRec = Integer.decode(oggCodRec.toString());
                continua = true;
            } catch (Exception unErrore) { // intercetta l'errore
            }// fine del blocco try-catch

            if (continua) {
                estratto = this.getEstrattoTesto(nomeCampo, codRec);
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return estratto;
    }


    /**
     * Restituisce un estratto.
     * </p>
     * Restituisce un estratto conforme al nome ed al record richiesto <br>
     * Se il nome selezionato coincide con un campo esistente, invoca il
     * metodo delegato di questa classe <br>
     * Altrimenti occcorre sovrascrivere questo metodo nelle classi specifiche
     * e nelle stesse scrivere i metodi per ogni richiesta <br>
     *
     * @param unNomeEstratto nome dell'estratto codificato nell'interfaccia
     * @param unCodiceRecord record sul quale effettuare la ricerca
     *
     * @return l'estratto costruito con la stringa ricavata
     *
     * @deprecated
     */
    public EstrattoBase getEstrattoOld(String unNomeEstratto, int unCodiceRecord) {
        /* variabili e costanti locali di lavoro */
        EstrattoBase unEstratto = null;

        try {    // prova ad eseguire il codice

            /* invoca il metodo delegato a recuperare l'estratto da un singolo campo */
            unEstratto = this.getEstrattoTesto(unNomeEstratto, unCodiceRecord);

            /* controlla che ci sia un valore valido */
            if (unEstratto != null) {
                // non deve fare niente altro
            } else {
                // occorre sovrascrivere questo metodo nelle classi specifiche
                // richiamare questo metodo della superclasse
                // scrivere i metodi specifici per ogni richiesta di pannelli
                // o di campi multipli
            }// fine del blocco if-else

        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return unEstratto;
    } // fine del metodo


    /**
     * Restituisce un estratto del campo specificato.
     * </p>
     * Controlla che il tipo di estratto richiesto sia una stringa <br>
     *
     * @param estratto codifica dell'estratto desiderato
     * @param codRec   codice del record sul quale effettuare la ricerca
     * @param campo    nome del campo di questa tavola da cui estarre il valore
     *
     * @return l'estratto costruito con la stringa ricavata
     */
    protected EstrattoBase getEstratto(Estratti estratto, Object codRec, String campo) {
        /* variabili e costanti locali di lavoro */
        EstrattoBase unEstratto = null;
        EstrattoBase.Tipo tipoEst;
        String valore;

        try { // prova ad eseguire il codice
            /* tipo di estratto codificato */
            tipoEst = estratto.getTipo();

            if (tipoEst == EstrattoBase.Tipo.stringa) {
                /* recupera il valore dal database */
                valore = this.query().valoreStringa(campo, (Integer)codRec);

                /* crea l'estratto */
                unEstratto = new EstrattoBase(valore, tipoEst);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return unEstratto;
    }


    /**
     * Restituisce un estratto del campo specificato.
     * </p>
     * Controlla che il tipo di estratto richiesto sia una stringa <br>
     *
     * @param estratto codifica dell'estratto desiderato
     * @param codRec   codice del record sul quale effettuare la ricerca
     * @param campo    nome del campo di questa tavola da cui estarre il valore
     *
     * @return l'estratto costruito con la stringa ricavata
     */
    protected EstrattoBase getEst(Estratti estratto, Object codRec, Campi campo) {
        /* valore di ritorno */
        return this.getEstratto(estratto, codRec, campo.get());
    }


    /**
     * Restituisce un estratto del campo specificato.
     * </p>
     * Controlla che il tipo di estratto richiesto sia una stringa <br>
     *
     * @param tipo        codifica dell'estratto desiderato
     * @param chiave      per effettuare la ricerca
     * @param campoChiave su cui effettuare la ricerca
     * @param campoValore da cui estrarre il valore
     *
     * @return l'estratto costruito con la stringa ricavata
     */
    protected EstrattoBase getEstratto(Estratti tipo,
                                       Object chiave,
                                       String campoChiave,
                                       String campoValore) {
        /* variabili e costanti locali di lavoro */
        EstrattoBase estratto = null;
        EstrattoBase.Tipo tipoEst;
        Object oggetto;

        try { // prova ad eseguire il codice
            /* tipo di estratto codificato */
            tipoEst = tipo.getTipo();

//            if (tipoEst == EstrattoBase.Tipo.COPPIA) {
            /* recupera il valore dal database */
            oggetto = this.query().valore(campoChiave, chiave, campoValore);

            /* crea l'estratto */
            estratto = new EstrattoBase(oggetto, EstrattoBase.Tipo.stringa);
//            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return estratto;
    }


    /**
     * Restituisce un estratto con due campi concatenati con trattino.
     * </p>
     *
     * @param estratto desiderato
     * @param chiave   per effettuare la ricerca
     * @param campoA   primo campo da recuperare
     * @param campoB   secondo campo da recuperare
     *
     * @return l'estratto costruito con la stringa ricavata
     */
    protected EstrattoBase getEstrattoDoppio(Estratti estratto,
                                             Object chiave,
                                             String campoA,
                                             String campoB) {
        /* variabili e costanti locali di lavoro */
        EstrattoBase unEstratto = null;
        boolean continua;
        EstrattoBase.Tipo tipoEst;
        int cod;
        String valoreA;
        String valoreB;
        String testo;

        try { // prova ad eseguire il codice
            /* tipo di estratto codificato */
            tipoEst = estratto.getTipo();
            continua = (tipoEst == EstrattoBase.Tipo.stringa);

            if (continua) {

                /* recupera i valori dal database */
                cod = (Integer)chiave;
                valoreA = this.query().valoreStringa(campoA, cod);
                valoreB = this.query().valoreStringa(campoB, cod);

                /* costruisce la stringa */
                testo = valoreA;
                if (Lib.Testo.isValida(valoreB)) {
                    testo += UNIONE_ESTRATTI + valoreB;
                }// fine del blocco if

                /* crea l'estratto */
                unEstratto = new EstrattoBase(testo, tipoEst);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return unEstratto;
    }


    /**
     * Restituisce un estratto di un'altro modulo.
     * </p>
     * Estrae dal modulo l'estratto richiesto <br>
     *
     * @param estratto  desiderato
     * @param campoLink locale di legame al modulo di estratto
     * @param chiave    con cui effettuare la ricerca
     *
     * @return l'estratto richiesto
     */
    protected EstrattoBase getEstrattoPassante(Estratti estratto, String campoLink, Object chiave) {
        /* variabili e costanti locali di lavoro */
        EstrattoBase unEstratto = null;
        Modulo modulo;
        String nomeModulo;
        int cod;

        try { // prova ad eseguire il codice

            /* recupera il valore nel campo di link */
            cod = this.query().valoreInt(campoLink, (Integer)chiave);

            /* recupera il modulo interessato */
            nomeModulo = estratto.getNomeModulo();
            modulo = Progetto.getModulo(nomeModulo);

            /* invoca il metodo delegato della superclasse */
            if (modulo != null) {
                unEstratto = modulo.getEstratto(estratto, cod);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return unEstratto;
    }


    /**
     * Restituisce un estratto con un valore interno più un'altro estratto.
     * </p>
     * Estrae dal modulo l'estratto richiesto <br>
     *
     * @param estratto     desiderato
     * @param campoInterno per il valore da aggiungere all'estratto
     * @param campoLink    locale di legame al modulo di estratto
     * @param chiave       con cui effettuare la ricerca
     *
     * @return l'estratto richiesto
     */
    protected EstrattoBase getEstrattoComposto(Estratti estratto,
                                               String campoInterno,
                                               String campoLink,
                                               Object chiave) {
        /* variabili e costanti locali di lavoro */
        EstrattoBase unEstratto = null;
        String valore;
        String testo;
        String testoComposto;

        try { // prova ad eseguire il codice

            /* recupera il valore dal database */
            valore = this.query().valoreStringa(campoInterno, (Integer)chiave);

            unEstratto = this.getEstrattoPassante(estratto, campoLink, chiave);
            testo = unEstratto.getStringa();

            testoComposto = valore;
            if (Lib.Testo.isValida(testo)) {
                testoComposto += UNIONE_ESTRATTI + testo;
            }// fine del blocco if
            testo = testoComposto;

//            testo = valore + UNIONE_ESTRATTI + testo;

            unEstratto = new EstrattoBase(testo, unEstratto.getTipo());

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return unEstratto;
    }


    /**
     * Restituisce un estratto.
     * </p>
     * Restituisce un estratto conforme al tipo ed al record richiesto <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     *
     * @param tipo codifica dell'estratto desiderato
     *
     * @return l'estratto costruito
     */
    public EstrattoBase getEstratto(Estratti tipo) {
        return null;
    }


    /**
     * Restituisce un estratto.
     * </p>
     * Restituisce un estratto conforme al tipo ed al record richiesto <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     *
     * @param tipo   tipo di estratto desiderato
     * @param chiave con cui effettuare la ricerca
     *
     * @return l'estratto costruito
     */
    public EstrattoBase getEstratto(Estratti tipo, Object chiave) {
        return null;
    }


    /**
     * Restituisce un estratto.
     * </p>
     * Restituisce un estratto conforme al tipo ed al record richiesto <br>
     * Utilizza la connessione fornita per effettuare le query <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     *
     * @param tipo   tipo di estratto desiderato
     * @param chiave con cui effettuare la ricerca
     * @param conn   la connessione da utilizzare per le query
     *
     * @return l'estratto costruito
     */
    public EstrattoBase getEstratto(Estratti tipo, Object chiave, Connessione conn) {
        return getEstratto(tipo, chiave);
    }


    /**
     * Aggiunge un oggetto campo alla collezione.<br>
     *
     * @param unCampo riferimento all'oggetto campo
     */
    public void addCampo(Campo unCampo) {

        /* variabili e costanti locali di lavoro */
        String unaChiaveCampo;
        String unaChiaveModuloLinkato;
        boolean aggiungi = true;
        CampoDB unCampoDB;

        try {    // prova ad eseguire il codice

            /* recupera il nome del campo */
            unaChiaveCampo = unCampo.getNomeInterno();

            /* assegna il riferimento al modulo */
            unCampo.setModulo(this.getModulo());

            /* Controlla se l'eventuale modulo linkato esiste.
             * Se si tratta di un campo linkato, e il modulo al quale
             * il campo e' linkato non esiste nel progetto, non aggiunge
             * il campo alla collezione.
             * Questo caso si verifica quando un modulo figlio viene
             * lanciato da solo, e non dal proprio modulo padre.*/
            unCampoDB = unCampo.getCampoDB();
            if (unCampoDB.isLinkato()) {
                unaChiaveModuloLinkato = unCampoDB.getNomeModuloLinkato();
                aggiungi = Progetto.isEsisteModulo(unaChiaveModuloLinkato);
            }// fine del blocco if

            /* aggiunge il campo alla collezione dei campi del modello */
            if (aggiungi) {

                /* regola la congruita' del campo prima di aggiungerlo*/
                this.regolaCongruitaCampo(unCampo);

                /* aggiunge il campo alla collezione */
                this.getCampiModello().put(unaChiaveCampo.toLowerCase(), unCampo);
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } // fine del blocco try-catch

    } /* fine del metodo */


    /**
     * Rimuove un campo dalla collezione.
     * <p/>
     *
     * @param chiave del campo da rimuovere
     *
     * @return true se esisteva ed è stato rimosso
     */
    protected boolean removeCampo(String chiave) {
        /* variabili e costanti locali di lavoro */
        boolean rimosso = false;
        LinkedHashMap<String, Campo> campi;

        try {    // prova ad eseguire il codice
            campi = this.getCampiModello();
            if (campi.get(chiave) != null) {
                campi.remove(chiave);
                rimosso = true;
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return rimosso;

    } /* fine del metodo */


    /**
     * Rimuove un campo dalla collezione.
     * <p/>
     *
     * @param campo della Enum da rimuovere
     *
     * @return true se esisteva ed è stato rimosso
     */
    protected boolean removeCampo(Campi campo) {
        /* variabili e costanti locali di lavoro */
        boolean rimosso = false;
        String chiave;

        try {    // prova ad eseguire il codice
            chiave = campo.get();
            rimosso = this.removeCampo(chiave);
        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return rimosso;

    } /* fine del metodo */


    /**
     * Rimuove un campo dalla collezione.
     * <p/>
     *
     * @param campo da rimuovere
     *
     * @return true se esisteva ed è stato rimosso
     */
    protected boolean removeCampo(Campo campo) {
        /* variabili e costanti locali di lavoro */
        boolean rimosso = false;
        String chiave;

        try {    // prova ad eseguire il codice
            chiave = campo.getNomeInterno();
            rimosso = this.removeCampo(chiave);
        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return rimosso;

    } /* fine del metodo */


    /**
     * Regola la congruita' del campo prima di aggiungerlo al Modello.<br>
     * Se i parametri impostati per il campo sono incongrui,
     * esegue delle regolazioni automatiche per rispettare la
     * congruita' o, se impossibile, visualizza un errore.
     *
     * @param unCampo il campo da regolare
     */
    private void regolaCongruitaCampo(Campo unCampo) {
        /* variabili e costanti locali di lavoro */
        CampoDB unCampoDB = null;
        CampoLista unCampoLista = null;

        try {    // prova ad eseguire il codice

            unCampoDB = unCampo.getCampoDB();
            unCampoLista = unCampo.getCampoLista();

        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } // fine del blocco try-catch

    } // fine del metodo


    /**
     * Controlla se nel modello esiste un campo chiave primario
     *
     * @return true se esiste almeno un campo chiave primario
     *         (dovrebbe esistere un solo campo chiave primaria in un modello)
     */
    public boolean esisteChiavePrimaria() {
        /** variabili e costanti locali di lavoro */
        boolean esiste = false;
        Iterator unGruppo = null;
        Campo unCampo = null;

        /** traversa tutta la collezione
         *  e controlla se esistono chiavi primarie */
        unGruppo = this.getCampiModello().values().iterator();
        while (unGruppo.hasNext()) {
            unCampo = (Campo)unGruppo.next();
            if (unCampo.getCampoDB().isChiavePrimaria()) {
                esiste = true;
                break;
            } /* fine del blocco if */
        } /* fine del blocco while */

        return esiste;
    } /* fine del metodo */


    /**
     * metodo setter per modificare il valore della variabile protetta
     */
    public void setModulo(Modulo unModulo) {
        this.unModulo = unModulo;
    } /* fine del metodo setter */


    /**
     * metodo setter per modificare il valore della variabile protetta
     */
    public void setTavolaArchivio(String unaTavolaArchivio) {
        try {    // prova ad eseguire il codice
            if (Lib.Testo.isValida(unaTavolaArchivio)) {
                this.unaTavolaArchivio = unaTavolaArchivio;
            } else {
                if (this.getModulo() != null) {
                    this.unaTavolaArchivio = this.getModulo().getNomeChiave();
                }// fine del blocco if

            } /* fine del blocco if/else */
        } catch (Exception unErrore) {    // intercetta l'errore
            /** mostra il messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */
    } /* fine del metodo setter */


    public boolean isUsaTavolaTemporanea() {
        return usaTavolaTemporanea;
    }


    public void setUsaTavolaTemporanea(boolean usaTavolaTemporanea) {
        this.usaTavolaTemporanea = usaTavolaTemporanea;
    }


    /**
     * Ritorna l'uso dell'integrità referenziale sul database.
     * <p/>
     *
     * @return true se il modello usa l'integrità referenziale
     */
    public boolean isUsaIntegritaReferenziale() {
        return usaIntegritaReferenziale;
    }


    /**
     * Regola l'uso dell'integrità referenziale sul database.
     * <p/>
     *
     * @param flag true per usare l'integrità referenziale
     */
    protected void setUsaIntegritaReferenziale(boolean flag) {
        this.usaIntegritaReferenziale = flag;
    }


    protected boolean isAggiornaDataUtenteModifica() {
        return aggiornaDataUtenteModifica;
    }


    /**
     * flag per aggiornare la data e l'utente di modifica del record ad ogni scrittura
     * <p/>
     *
     * @param flag per aggiornare la data l'utente di modifica ad ogni scrittura
     */
    public void setAggiornaDataUtenteModifica(boolean flag) {
        this.aggiornaDataUtenteModifica = flag;
    }


    /**
     * metodo setter per modificare il valore della variabile privata
     */
    public void setTitoloLista(String unTitoloLista) {
        this.unTitoloLista = unTitoloLista;
    } /* fine del metodo setter */


    /**
     * metodo setter per modificare il valore della variabile privata
     */
    public void setTitoloScheda(String unTitoloScheda) {
        this.unTitoloScheda = unTitoloScheda;
    } /* fine del metodo setter */


    /**
     * metodo setter per modificare il valore della variabile privata
     */
    public void setUsaCampoNote(boolean usaCampoNote) {
        this.usaCampoNote = usaCampoNote;
    } /* fine del metodo setter */


    /**
     * metodo getter per ottenere il valore della variabile privata
     */
    protected boolean isUsaCampoNote() {
        return this.usaCampoNote;
    } /* fine del metodo getter */


    /**
     * Ritorna l'utilizzo del campo "preferito"
     * <p/>
     *
     * @return true se usa il campo "preferito"
     */
    public boolean isUsaCampoPreferito() {
        return usaCampoPreferito;
    }


    /**
     * Regola l'utilizzo del campo "preferito"
     * <p/>
     *
     * @param flag per usare il campo "preferito"
     */
    public void setUsaCampoPreferito(boolean flag) {
        this.usaCampoPreferito = flag;
    }


    /**
     * Ritorna l'id del contatore di questo modello nella tavola Contatori
     * <p/>
     *
     * @return l'id del contatore
     */
    public int getIdContatore() {
        return idContatore;
    }


    private void setIdContatore(int idContatore) {
        this.idContatore = idContatore;
    }


    /**
     * metodo setter per modificare il valore della variabile privata
     */
    public void setCampoPubblico(String unCampoPubblico) {
        this.unCampoPubblico = unCampoPubblico;
    } /* fine del metodo setter */


    /**
     * Restituisce il modulo al quale questo modello appartiene
     */
    public Modulo getModulo() {
        return this.unModulo;
    } /* fine del metodo getter */


    /**
     * Ritorna l'oggetto contenente i metodi Query del modulo.
     * <p/>
     *
     * @return l'oggetto contenente i metodi Query
     */
    public MetodiQuery query() {
        /* variabili e costanti locali di lavoro */
        MetodiQuery query = null;
        Modulo modulo;

        try { // prova ad eseguire il codice
            modulo = this.getModulo();

            if (modulo != null) {
                query = modulo.query();
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return query;
    }


    /**
     * Restituisce la intera collezione degli oggetti Campo che
     * costituiscono il modello del record
     */
    public LinkedHashMap<String, Campo> getCampiModello() {
        return this.campiModello;
    } /* fine del metodo getter */


    /**
     * Restituisce la intera collezione degli oggetti Vista del Modello
     *
     * @return la collezione delle Viste del modello
     */
    public HashMap<String, Vista> getVisteModello() {
        return this.collezioneViste;
    } /* fine del metodo getter */


    /**
     * Restituisce i soli campi fisici dalla collezione del modello
     */
    public LinkedHashMap<String, Campo> getCampiFisici() {
        return Lib.Camp.filtraCampiFisici(this.getCampiModello());
    } /* fine del metodo getter */


    /**
     * Restituisce i soli campi visibili nella Vista di default
     *
     * @return una mappa chiave - campo
     */
    public LinkedHashMap getCampiVisibili() {
        return Lib.Camp.filtraCampiVisibili(this.getCampiModello());
    } /* fine del metodo getter */


    /**
     * Restituisce i soli campi fisici nion fissi (non algos)
     *
     * @return una mappa chiave - campo
     */
    public LinkedHashMap getCampiFisiciNonFissi() {
        return Lib.Camp.filtraCampiFisiciNoAlgos(this.getCampiModello());
    } /* fine del metodo getter */


    /**
     * Restituisce i soli campi fissi
     * <p/>
     *
     * @return la lista dei campi fissi
     */
    public ArrayList<Campo> getCampiFissi() {
        /* variabili e costanti locali di lavoro */
        ArrayList<Campo> campiFissi = null;
        HashMap mappa;
        ArrayList<Campo> campiModello;

        try { // prova ad eseguire il codice
            campiFissi = new ArrayList<Campo>();
            mappa = this.getCampiModello();
            campiModello = Libreria.hashMapToArrayList(mappa);
            for (Campo campo : campiModello) {
                if (campo.getCampoDB().isFissoAlgos()) {
                    campiFissi.add(campo);
                }// fine del blocco if
            }
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return campiFissi;
    }


    /**
     * Ritorna l'elenco dei campi definiti come ricercabili.
     * <p/>
     *
     * @return l'elenco dei campi ricercabili
     */
    public ArrayList<Campo> getCampiRicercabili() {
        /* variabili e costanti locali di lavoro */
        ArrayList<Campo> lista = null;
        Iterator unGruppo = null;
        Campo unCampo = null;

        try {    // prova ad eseguire il codice

            lista = new ArrayList<Campo>();
            unGruppo = this.getCampiModello().values().iterator();
            while (unGruppo.hasNext()) {
                unCampo = (Campo)unGruppo.next();
                if (unCampo.getCampoDati().isRicercabile()) {
                    lista.add(unCampo);
                } /* fine del blocco if */
            } /* fine del blocco while */
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return lista;
    }


    /**
     * Controlla l'esistenza di un campo.
     * <br>
     *
     * @param unaChiave nomeInterno chiave per recuperare il campo dalla collezione
     *
     * @return vero se il campo esiste nella collezione campi del Modello
     */
    public boolean isEsisteCampo(String unaChiave) {
        /* variabili e costanti locali di lavoro */
        boolean esiste = false;
        String unaChiaveMinuscola = null;

        try {    // prova ad eseguire il codice

            /* recupera la chiave */
            unaChiaveMinuscola = unaChiave.toLowerCase();

            /* controlla il campo */
            esiste = this.campiModello.containsKey(unaChiaveMinuscola);

        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return esiste;
    } // fine del metodo


    /**
     * Controlla l'esistenza di una vista. <br>
     *
     * @param unaChiave nomeInterno chiave per recuperare la vista dalla collezione
     */
    public boolean isEsisteVista(String unaChiave) {
        /* variabili e costanti locali di lavoro */
        boolean esiste = false;

        try {    // prova ad eseguire il codice

            /* controlla se esiste nella collezione */
            esiste = this.collezioneViste.containsKey(unaChiave);

        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return esiste;
    } // fine del metodo


    /**
     * Restituisce il singolo campo della collezione
     *
     * @param unaChiave nomeInterno chiave per recuperare il campo dalla collezione
     *
     * @return oggetto campo richiesto (di tipo Campo), null se non trovato
     */
    public Campo getCampo(String unaChiave) {
        /* variabili e costanti locali di lavoro */
        Campo unCampo = null;
        String unaChiaveMinuscola;

        try { // prova ad eseguire il codice

            /* recupera la chiave */
            unaChiaveMinuscola = unaChiave.toLowerCase();

            /* recupera il campo */
            unCampo = (Campo)this.campiModello.get(unaChiaveMinuscola);

            /* verifica se il campo e' stato trovato
             *  se non e' stato trovato, avvisa */
            if (unCampo == null) {
                String unMessaggio = "";
                unMessaggio += "Non ho trovato il campo di nome " + unaChiaveMinuscola;
                if (this.getModulo() != null) {
                    unMessaggio += "\n" + "nel modulo " + this.getModulo().getNomeChiave();
                } /* fine del blocco if */

//                throw new Exception(unMessaggio);

            } /* fine del blocco if */
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return unCampo;
    }


    /**
     * Restituisce il singolo campo della collezione
     *
     * @param campo elemento della Enum
     *
     * @return oggetto campo richiesto (di tipo Campo), null se non trovato
     */
    public Campo getCampo(Campi campo) {
        return this.getCampo(campo.get());
    }


    /**
     * Restituisce il nome del campo chiave del modello
     *
     * @return il nome del campo chiave
     */
    public String getNomeCampoChiave() {
        return NOME_CAMPO_CHIAVE;
    }// fine del metodo getter


    /**
     * Restituisce il campo chiave del Modello.
     *
     * @return il campo chiave
     */
    public Campo getCampoChiave() {
        return this.getCampo(getNomeCampoChiave());
    } /* fine del metodo getter */


    /**
     * Restituisce il campo ordine del Modello.
     *
     * @return il campo ordine
     */
    public Campo getCampoOrdine() {
        return this.getCampo(Modello.NOME_CAMPO_ORDINE);
    } /* fine del metodo getter */


    /**
     * Restituisce il campo Visibile del Modello.
     *
     * @return il campo Visibile
     */
    public Campo getCampoVisibile() {
        return this.getCampo(Modello.NOME_CAMPO_VISIBILE);
    } /* fine del metodo getter */


    /**
     * Restituisce il campo Data Creazione del Modello.
     *
     * @return il campo Data Creazione
     */
    public Campo getCampoDataCreazione() {
        return this.getCampo(Modello.NOME_CAMPO_DATA_CREAZIONE);
    } /* fine del metodo getter */


    /**
     * Restituisce il campo Utente Creazione del Modello.
     *
     * @return il campo Utente Creazione
     */
    public Campo getCampoUtenteCreazione() {
        return this.getCampo(Modello.NOME_CAMPO_ID_CREAZIONE);
    } /* fine del metodo getter */


    /**
     * Restituisce il campo Data Modifica del Modello.
     *
     * @return il campo Data Modifica
     */
    public Campo getCampoDataModifica() {
        return this.getCampo(Modello.NOME_CAMPO_DATA_MODIFICA);
    } /* fine del metodo getter */


    /**
     * Restituisce il campo Utente Modifica del Modello.
     *
     * @return il campo Utente Modifica
     */
    public Campo getCampoUtenteModifica() {
        return this.getCampo(Modello.NOME_CAMPO_ID_MODIFICA);
    } /* fine del metodo getter */


    /**
     * Restituisce il campo Note del Modello.
     *
     * @return il campo Note
     */
    public Campo getCampoNote() {
        return this.getCampo(Modello.NOME_CAMPO_NOTE);
    } /* fine del metodo getter */


    /**
     * Restituisce il campo "preferito" del Modello.
     *
     * @return il campo "preferito"
     */
    public Campo getCampoPreferito() {
        return this.getCampo(Modello.NOME_CAMPO_PREFERITO);
    } /* fine del metodo getter */


    /**
     * Numero totale dei campi del modello del record
     */
    public int getQuantiCampiModello() {
        return this.campiModello.size();
    } /* fine del metodo getter */


    /**
     * Restituisce la collezione degli oggetti Campo che
     * sono visibili nella scheda
     */
    public LinkedHashMap getCampiScheda() {
        /** invoca il metodo delegato con il parametro opportuno */
        return this.spazzolaCampi(VISIBILI_SCHEDA);
    } /* fine del metodo getter */


    /**
     * Restituisce la collezione di tutti gli oggetti Campo che
     * <bold>non</bold> siano quelli fissi Algos <br>
     *
     * @return una collezione dei soli campi del modello specifico
     *
     * @see #spazzolaCampi
     */
    public LinkedHashMap getCampiModelloSpecifico() {
        /** invoca il metodo delegato con il parametro opportuno */
        return this.spazzolaCampi(NON_ALGOS);
    } /* fine del metodo getter */


    /**
     * Restituisce la collezione degli oggetti CampoBase che
     * sono visibili nella scheda
     */
    public LinkedHashMap getCampiSchedaOld() {
        return this.campiScheda;
    } /* fine del metodo getter */


    /**
     * Numero dei campi visibili nella lista
     */
    public int getQuantiCampiLista() {
        return this.nomeCampiLista.size();
    } /* fine del metodo getter */


    /**
     * Numero dei campi visibili nella scheda
     */
    public int getQuantiCampiScheda() {
        return this.campiScheda.size();
    } /* fine del metodo getter */


    /**
     * Restituisce i nomi (nomeInterno) di tutti i campi del modello,
     * sotto forma di collezione (set)
     */
    public ArrayList getArrayNomeCampiModello() {
        return this.nomeCampiModello;
    } /* fine del metodo getter */


    /**
     * Restituisce i nomi (nomeInterno) di tutti i campi della lista,
     * sotto forma di collezione (set)
     */
    public ArrayList getArrayNomeCampiLista() {
        return this.nomeCampiLista;
    } /* fine del metodo getter */


    /**
     * Restituisce i nomi (nomeInterno) di tutti i campi della scheda,
     * sotto forma di collezione (set)
     */
    public ArrayList getArrayNomeCampiScheda() {
        return Lib.Array.creaLista(nomeCampiScheda);
    } /* fine del metodo getter */


    /**
     * Restituisce i titoli delle colonne (titoloLista) di tutti i campi
     * della lista, sotto forma di collezione (set)
     */
    public ArrayList getArrayTitoliLista() {
        return this.creaArrayTitoliLista();
    } /* fine del metodo getter */


    /**
     * Restituisce i testi (valori di default per una nuova scheda) di tutti
     * i campi della scheda, sotto forma di collezione (set)
     */
    public ArrayList getArrayDefaultCampiScheda() {
        return Lib.Array.creaLista(this.getStringaDefaultCampiScheda());
    } /* fine del metodo getter */


    /**
     * Restituisce i nomi (nomeInterno) di tutti i campi del modello,
     * sotto forma di stringa separati da una virgola
     */
    public String getStringaNomeCampiModello() {
        return Lib.Testo.getStringaVirgola(nomeCampiModello);
    } /* fine del metodo getter */


    /**
     * Restituisce i nomi (nomeInterno) di tutti i campi della lista,
     * sotto forma di stringa separati da una virgola
     */
    public String getStringaNomeCampiLista() {
        return Lib.Testo.getStringaVirgola(nomeCampiLista);
    } /* fine del metodo getter */


    /**
     * Restituisce i nomi (nomeInterno) di tutti i campi della scheda,
     * sotto forma di stringa separati da una virgola
     */
    public String getStringaNomeCampiScheda() {
        return nomeCampiScheda;
    } /* fine del metodo getter */


    /**
     * Restituisce i titoli delle colonne (titoloLista) di tutti i campi
     * della lista, sotto forma di stringa separati da una virgola
     */
    public String getStringaTitoliLista() {
        return Lib.Testo.getStringaVirgola(getArrayTitoliLista());
    } /* fine del metodo getter */


    /**
     * Restituisce i testi (valori di default per una nuova scheda) di tutti
     * i campi della scheda, sotto forma di stringa separati da una virgola
     */
    public String getStringaDefaultCampiScheda() {
//	return this.stringaTestiCampiScheda();
        return "";
    } /* fine del metodo getter */


    /**
     * Restituisce la sotto-collezione di campi fissi Algos
     */
    public LinkedHashMap getCollezioneCampiFissi() {
        /** invoca il metodo delegato con il parametro opportuno */
        return this.spazzolaCampi(FISSI_ALGOS);
    } /* fine del metodo getter */


    /**
     * Restituisce un array coi nomi dei campi fissi Algos
     */
    public ArrayList getArrayCampiFissi() {
        /** invoca il metodo delegato */
        return Lib.Testo.getListaChiavi(this.getCollezioneCampiFissi());
    } /* fine del metodo getter */


    /**
     * Restituisce una stringa coi nomi dei campi fissi Algos
     */
    public String getNomeCampiFissi() {
        /** invoca il metodo delegato */
        return Lib.Testo.getStringaVirgola(this.getArrayCampiFissi());
    } /* fine del metodo getter */


    /**
     * metodo getter per ottenere il valore della variabile privata
     */
    public String getTavolaArchivio() {
        return this.unaTavolaArchivio.toLowerCase();
    } /* fine del metodo getter */


    /**
     * metodo getter per ottenere il valore della variabile privata
     */
    public String getTitoloLista() {
        return this.unTitoloLista;
    } /* fine del metodo getter */


    /**
     * metodo getter per ottenere il valore della variabile privata
     */
    public String getTitoloScheda() {
        return this.unTitoloScheda;
    } /* fine del metodo getter */


    /**
     * metodo getter per ottenere il valore della variabile privata
     */
    public String getCampoPubblico() {
        return this.unCampoPubblico;
    } /* fine del metodo getter */


    /**
     * Ritorna il database del Modulo.
     * <p/>
     *
     * @return un oggetto Db
     */
    public Db getDb() {
        return this.getModulo().getDb();
    } /* fine del metodo getter */


    /**
     * Ritorna la singola Vista dalla collezione.
     *
     * @param nomeVista nome chiave identificativo della Vista
     *
     * @return la singola Vista
     */
    public Vista getVista(String nomeVista) {
        return (Vista)this.collezioneViste.get(nomeVista);
    } /* fine del metodo getter */


    /**
     * Ritorna la singola Vista dalla collezione.
     *
     * @param vista della Enumeration dell'interfaccia
     *
     * @return la singola Vista
     */
    public Vista getVista(Viste vista) {
        return this.getVista(vista.toString());
    } /* fine del metodo getter */


    /**
     * Ritorna la vista di default del Modello
     */
    public Vista getVista() {
        return this.getVista(CostanteModulo.VISTA_BASE_DEFAULT);
    } /* fine del metodo getter */


    /**
     * Ritorna la vista di default del Modello
     */
    public Vista getVistaDefault() {
        return this.getVista(CostanteModulo.VISTA_BASE_DEFAULT);
    } /* fine del metodo getter */


    /**
     * metodo getter per ottenere il valore della variabile privata
     */
    public ArrayList getSetCampiScheda(String nomeSet) {
        return (ArrayList)this.collezioneSet.get(nomeSet);
    } /* fine del metodo getter */


    /**
     * metodo getter per ottenere il valore della variabile privata
     */
    public ArrayList getVistaPubblica(String nomeVista) {
        return null;
        //	return (ArrayList)this.vistePubbliche.get(nomeVista);
    } /* fine del metodo getter */


    /**
     * Restituisce un oggetto (monouso) che percorre tutta la collezione
     * delle chiavi dei campi, per 'spazzolarla' ad usi varii
     */
    public Iterator getIteratorChiaveCampi() {
        return campiModello.keySet().iterator();
    } /* fine del metodo */


    /**
     * Restituisce un oggetto (monouso) che percorre tutta la collezione
     * dei valori dei campi, per 'spazzolarla' ad usi varii
     */
    public Iterator getIteratorValoreCampi() {
        return campiModello.values().iterator();
    } /* fine del metodo */


    public String getPrimoCampo() {
        return primoCampo;
    }


    protected void setPrimoCampo(String primoCampo) {
        this.primoCampo = primoCampo;
    }


    public ArrayList<WrapFiltri> getFiltriPop() {
        return filtriPop;
    }


    protected void setFiltriPop(ArrayList<WrapFiltri> filtriPop) {
        this.filtriPop = filtriPop;
    }


    /**
     * Ritorna l'albero dei campi calcolati
     * <p/>
     *
     * @return l'albero dei campi calcolati
     */
    public AlberoCalcolo getAlberoCalcolo() {
        return alberoCalcolo;
    }


    private void setAlberoCalcolo(AlberoCalcolo alberoCalcolo) {
        this.alberoCalcolo = alberoCalcolo;
    }


    public Db.Azione getAzioneDelete() {
        return azioneDelete;
    }


    protected void setAzioneDelete(Db.Azione azioneDelete) {
        this.azioneDelete = azioneDelete;
    }


    public Db.Azione getAzioneUpdate() {
        return azioneUpdate;
    }


    protected void setAzioneUpdate(Db.Azione azioneUpdate) {
        this.azioneUpdate = azioneUpdate;
    }


    /**
     * @return il filtro del modello
     */
    public Filtro getFiltroModello() {
        return filtroModello;
    }


    public void setFiltroModello(Filtro filtroModello) {
        this.filtroModello = filtroModello;
    }


    /**
     * Determina se il trigger per Nuovo Record è attivo.
     * <p/>
     *
     * @return true se è attivo
     */
    public boolean isTriggerNuovoAttivo() {
        return triggerNuovoAttivo;
    }


    /**
     * Attivazione del trigger per nuovo record.
     * <p/>
     *
     * @param flag di attivazione
     */
    public void setTriggerNuovoAttivo(boolean flag) {
        this.triggerNuovoAttivo = flag;
    }


    /**
     * Determina se il trigger per Modifica Record è attivo.
     * <p/>
     *
     * @return true se è attivo
     */
    public boolean isTriggerModificaAttivo() {
        return triggerModificaAttivo;
    }


    /**
     * Ritorna lo stato di attivazione della gestione dei
     * valori precedenti nel trigger Modifica Record.
     * <p/>
     *
     * @return true se la gestione è attiva
     */
    public boolean isTriggerModificaUsaPrecedenti() {
        return triggerModificaUsaPrecedenti;
    }


    /**
     * Attivazione del trigger per modifica record.
     * <p/>
     *
     * @param flag di attivazione
     * @param prec true per attivare la gestione dei valori precedenti
     */
    public void setTriggerModificaAttivo(boolean flag, boolean prec) {
        this.triggerModificaAttivo = flag;
        this.triggerModificaUsaPrecedenti = prec;
    }


    /**
     * Determina se il trigger per Elimina Record è attivo.
     * <p/>
     *
     * @return true se è attivo
     */
    public boolean isTriggerEliminaAttivo() {
        return triggerEliminaAttivo;
    }


    /**
     * Ritorna lo stato di attivazione della gestione dei
     * valori precedenti nel trigger Elimina Record.
     * <p/>
     *
     * @return true se la gestione è attiva
     */
    public boolean isTriggerEliminaUsaPrecedenti() {
        return triggerEliminaUsaPrecedenti;
    }


    /**
     * Attivazione del trigger per modifica record.
     * <p/>
     *
     * @param flag di attivazione
     * @param prec true per attivare la gestione dei valori precedenti
     */
    public void setTriggerEliminaAttivo(boolean flag, boolean prec) {
        this.triggerEliminaAttivo = flag;
        this.triggerEliminaUsaPrecedenti = prec;
    }


    public EventListenerList getListaListener() {
        return listaListener;
    }


    public void setListaListener(EventListenerList listaListener) {
        this.listaListener = listaListener;
    }


    /**
     * Aggiunge un listener.
     * <p/>
     * Aggiunge uno specifico listener <br>
     * L'oggetto mantiene una propria collezione di listener <br>
     *
     * @see javax.swing.event.EventListenerList
     */
    public void addListener(Eventi evento, BaseListener listener) {
        /* variabili e costanti locali di lavoro */
        EventListenerList listaListener;

        try {    // prova ad eseguire il codice
            listaListener = this.getListaListener();
            evento.add(listaListener, listener);
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Rimuove un listener.
     * <p/>
     * Rimuove lo specifico listener <br>
     * L'oggetto mantiene una propria collezione di listener <br>
     *
     * @see javax.swing.event.EventListenerList
     */
    public void removeListener(BaseListener listener) {
        /* variabili e costanti locali di lavoro */
        EventListenerList listaListener;

        try {    // prova ad eseguire il codice
            listaListener = this.getListaListener();
            Evento.removeLocale(listaListener, listener);
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Avvisa tutti i listener.
     * <p/>
     * Avvisa tutti i listener che si sono registrati
     * per questo tipo di evento <br>
     * L'evento viene creato al momento <br>
     * è responsabilità della classe invocare questo metodo quando
     * si creano le condizioni per generare l'evento <br>
     *
     * @param tipo      di operazione sul db (nuovo, modifica, elimina)
     * @param conn      connessione utilizzata per effettuare l'operazione
     * @param codice    del record interessato
     * @param valoriNew dei campi del record interessato prima dell'operazione
     * @param valoriOld dei campi del record interessato dopo l'operazione
     *
     * @see javax.swing.event.EventListenerList
     */
    private void fireTrigger(Db.TipoOp tipo,
                             Connessione conn,
                             int codice,
                             ArrayList<CampoValore> valoriNew,
                             ArrayList<CampoValore> valoriOld) {
        /* variabili e costanti locali di lavoro */
        EventListenerList listaListener;
        Parametro[] par;

        try {    // prova ad eseguire il codice
            listaListener = this.getListaListener();

            par = new Parametro[5];
            par[0] = new Parametro(Db.TipoOp.class, tipo);
            par[1] = new Parametro(Connessione.class, conn);
            par[2] = new Parametro(Integer.class, codice);
            par[3] = new Parametro(ArrayList.class, valoriNew);
            par[4] = new Parametro(ArrayList.class, valoriOld);

            Lib.Eventi.fire(listaListener, Evento.trigger, par);
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Classe interna Enumerazione.
     * <p/>
     * Eventi che vengono lanciati dal modello <br>
     * Per ogni evento: <ul>
     * <li> classe interfaccia </li>
     * <li> classe evento </li>
     * <li> classe azione </li>
     * <li> metodo azione </li>
     * </ul>
     */
    public enum Evento implements Eventi {

        trigger(DbTriggerLis.class, DbTriggerEve.class, DbTriggerAz.class, "dbTriggerAz");

        /**
         * interfaccia listener per l'evento
         */
        private Class listener;

        /**
         * classe evento
         */
        private Class evento;

        /**
         * classe azione
         */
        private Class azione;

        /**
         * metodo
         */
        private String metodo;


        /**
         * Costruttore completo con parametri.
         *
         * @param listener interfaccia
         * @param evento   classe
         * @param azione   classe
         * @param metodo   nome metodo azione
         */
        Evento(Class listener, Class evento, Class azione, String metodo) {
            try { // prova ad eseguire il codice
                /* regola le variabili di istanza coi parametri */
                this.setListener(listener);
                this.setEvento(evento);
                this.setAzione(azione);
                this.setMetodo(metodo);
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }


        /**
         * Aggiunge un listener alla lista.
         * <p/>
         * Metodo statico <br>
         * Serve per utilizzare questa Enumeration <br>
         * Controlla che il listener appartenga all'enumerazione <br>
         *
         * @param lista    degli eventi a cui aggiungersi
         * @param listener dell'evento da lanciare
         */
        public static void addLocale(EventListenerList lista, BaseListener listener) {
            Lib.Eventi.add(Evento.values(), lista, listener);
        }


        /**
         * Rimuove un listener dalla lista.
         * <p/>
         * Metodo statico <br>
         * Serve per utilizzare questa Enumeration <br>
         * Controlla che il listener appartenga all'enumerazione <br>
         *
         * @param lista    degli eventi da cui rimuoverlo
         * @param listener dell'evento da non lanciare
         */
        public static void removeLocale(EventListenerList lista, BaseListener listener) {
            Lib.Eventi.remove(Evento.values(), lista, listener);
        }


        /**
         * Aggiunge un listener alla lista.
         * <p/>
         * Serve per utilizzare la Enumeration della sottoclasse <br>
         * Metodo (sovra)scritto nelle Enumeration specifiche
         * (le Enumeration delle sottoclassi della classe dove
         * e' questa Enumeration) <br>
         * Controlla che il listener appartenga all'enumerazione <br>
         *
         * @param lista    degli eventi a cui aggiungersi
         * @param listener dell'evento da lanciare
         */
        public void add(EventListenerList lista, BaseListener listener) {
            Lib.Eventi.add(Evento.values(), lista, listener);
        }


        /**
         * Rimuove un listener dalla lista.
         * <p/>
         * Serve per utilizzare la Enumeration della sottoclasse <br>
         * Metodo (sovra)scritto nelle Enumeration specifiche
         * (le Enumeration delle sottoclassi della classe dove
         * e' questa Enumeration) <br>
         * Controlla che il listener appartenga all'enumerazione <br>
         *
         * @param lista    degli eventi da cui rimuoverlo
         * @param listener dell'evento da non lanciare
         */
        public void remove(EventListenerList lista, BaseListener listener) {
            Lib.Eventi.remove(Evento.values(), lista, listener);
        }


        public Class getListener() {
            return listener;
        }


        private void setListener(Class listener) {
            this.listener = listener;
        }


        public Class getEvento() {
            return evento;
        }


        private void setEvento(Class evento) {
            this.evento = evento;
        }


        public Class getAzione() {
            return azione;
        }


        private void setAzione(Class azione) {
            this.azione = azione;
        }


        public String getMetodo() {
            return metodo;
        }


        private void setMetodo(String metodo) {
            this.metodo = metodo;
        }


    }// fine della classe


    /**
     * Classe interna Enumerazione.
     */
    public enum Trigger {

        nuovoAnte(),
        nuovoPost(),
        modificaAnte(),
        modificaPost(),
        eliminaAnte(),
        eliminaPost()

    }// fine della classe

}// fine della classe