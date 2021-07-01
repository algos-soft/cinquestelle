/**
 * Title:        ModuloBase.java
 * Package:      it.algos.base.modulo
 * Description:  Abstract Data Types per la struttura dei dati di un modulo
 * Copyright:    Copyright (c) 2002 Company:      Algos s.r.l.
 */

package it.algos.base.modulo;

import it.algos.base.albero.AlberoModello;
import it.algos.base.albero.AlberoNodo;
import it.algos.base.azione.Azione;
import it.algos.base.azione.modulo.AzAvviaModulo;
import it.algos.base.bottone.BottoneAzione;
import it.algos.base.calcresolver.AlberoCalcolo;
import it.algos.base.campo.base.Campo;
import it.algos.base.campo.base.CampoFactory;
import it.algos.base.contatore.ContatoreModulo;
import it.algos.base.database.Db;
import it.algos.base.database.DbFactory;
import it.algos.base.database.connessione.Connessione;
import it.algos.base.database.connessione.Connettore;
import it.algos.base.database.dati.Dati;
import it.algos.base.database.util.MetodiQuery;
import it.algos.base.dialogo.Dialogo;
import it.algos.base.dialogo.DialogoAnnullaConferma;
import it.algos.base.errore.Errore;
import it.algos.base.evento.BaseListener;
import it.algos.base.evento.Eventi;
import it.algos.base.evento.modulo.ModNascostoAz;
import it.algos.base.evento.modulo.ModNascostoEve;
import it.algos.base.evento.modulo.ModNascostoLis;
import it.algos.base.evento.modulo.ModVisibileAz;
import it.algos.base.evento.modulo.ModVisibileEve;
import it.algos.base.evento.modulo.ModVisibileLis;
import it.algos.base.finestra.AboutDialogo;
import it.algos.base.gestore.Gestore;
import it.algos.base.gestore.GestoreBase;
import it.algos.base.gestore.gestorestato.GestoreStatoScheda;
import it.algos.base.gestore.gestorestato.GestoreStatoSchedaDefault;
import it.algos.base.help.Help;
import it.algos.base.libreria.Lib;
import it.algos.base.libreria.Libreria;
import it.algos.base.lista.Lista;
import it.algos.base.lista.ListaStatusBar;
import it.algos.base.modello.Modello;
import it.algos.base.modello.ModelloDefault;
import it.algos.base.modulo.logica.LogicaModulo;
import it.algos.base.navigatore.Navigatore;
import it.algos.base.navigatore.NavigatoreFactory;
import it.algos.base.palette.Palette;
import it.algos.base.portale.Portale;
import it.algos.base.portale.PortaleModulo;
import it.algos.base.progetto.Progetto;
import it.algos.base.query.Query;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.query.filtro.FiltroFactory;
import it.algos.base.query.ordine.Ordine;
import it.algos.base.query.selezione.PassaggiSelezione;
import it.algos.base.query.selezione.QuerySelezione;
import it.algos.base.scheda.Scheda;
import it.algos.base.scheda.SchedaDefault;
import it.algos.base.stampa.StampaLista;
import it.algos.base.toolbar.ToolBar;
import it.algos.base.vista.Vista;
import it.algos.base.wrapper.Campi;
import it.algos.base.wrapper.CampoValore;
import it.algos.base.wrapper.Estratti;
import it.algos.base.wrapper.EstrattoBase;
import it.algos.base.wrapper.Popup;
import it.algos.base.wrapper.TreStringhe;
import it.algos.base.wrapper.WrapFiltri;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.event.EventListenerList;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;

/**
 * Questa classe astratta e' responsabile di: <br>
 * A - Costruire un nuovo tipo di dato (record in pascal), per ogni modulo <br>
 * B - Serve come contenitore di tutte le informazioni necessarie e sufficenti
 * per avviare ed utilizzare un modulo <br>
 * C - Una applicazione e' un modulo che parte per primo <br>
 * D - Tipicamente si costruisce un oggetto di questa classe, si regolano qui
 * tutte le variabili ed i riferimenti (compreso quello al gestore
 * principale), poi si invoca il metodo <code>lancia</code> di questa
 * classe (col parametro obbligatorio di inizio codificato) ed a questo punto
 * ci sono tutte le informazioni che servono e viene invocato il metodo
 * lancia della classe gestore <br>
 *
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /  15 dicembre 2002 ore 9.32
 */
public class ModuloBase implements Modulo, Connettore {


    /**
     * flag - indica se il modulo e' stato gia' preparato.
     */
    private boolean preparato = false;

    /**
     * flag - indica se il modulo e' stato gia' relazionato.
     */
    private boolean relazionato = false;

    /**
     * flag - indica se il modulo e' stato gia' inizializzato.
     */
    private boolean inizializzato = false;

    /**
     * flag - indica se il modulo e' stato avviato una prima volta.
     */
    private boolean avviato = false;

    /**
     * lista di moduli dipendenti
     */
    private ArrayList<Modulo> moduliDipendenti;

    /**
     * collezione dei navigatori
     */
    private LinkedHashMap<String, Navigatore> navigatori = null;

    /**
     * collezione delle liste - il modulo puo' avere n liste, ma il gestore ne
     * mantiene attiva solo una alla volta (per adesso) ogni lista ha
     * associata una particolare Vista e' obbligatorio avere almeno una lista
     * che di default usa la vistadefault ogni lista crea la propria
     * FinestraListaOld e ListaModello
     */
    protected LinkedHashMap unaCollezioneListe = null;

    /**
     * collezione di oggetti scheda
     */
    private LinkedHashMap<String, Scheda> schede = null;

    /**
     * DOCUMENT ME!
     */
    private AzAvviaModulo azioneModulo = null;

    /**
     * database di riferimento del Modulo
     */
    private Db db = null;

    /**
     * connessione al database di riferimento
     */
    private Connessione connessione = null;


    /**
     * gestore principale degli eventi di questo modulo
     */
    private Gestore gestore = null;

    /**
     * regolazione dello stato della scheda
     */
    private GestoreStatoScheda unGestoreStatoScheda = null;

    /**
     * gestione dell'aiuto con mappatura dei files HTML
     */
    private Help unHelp = null;

    /**
     * collezione dei nomi dei moduli (o tabelle) attivi (e visibili) in questo modulo
     */
    private ArrayList<String> nomiModuliMenu = null;

    /**
     * modello normale dei dati (Abstract Data Types)
     */
    private Modello unModelloDati = null;

    /**
     * modulo chiamante che ha invocato questo modulo (es. sublista)
     */
    private Modulo unModuloChiamante = null;

    /**
     * modulo che ha creato questo modulo (alla partenza del programma)
     */
    private Modulo unModuloParente = null;

    /**
     * progetto in esecuzione  (il progetto e' unico, il riferimento serve solo
     * a recuperare l'istanza)
     */
    private Progetto unProgetto = null;

    /**
     * nome chiave del navigatore di default di questo modulo
     */
    private String nomeNavigatoreDefault;

    /**
     * riferimento al Navigatore in uso (corrente)
     */
    private String nomeNavigatoreCorrente = "";

    /**
     * riferimento alla lista corrente questo puntatore serve solo
     * temporaneamente da quando viene creato nella sottoclasse xxxModulo a
     * quando viene inizializzato il gestore che mantiene il puntatore
     * effettivo
     */
    private String nomeListaCorrente = "";

    /**
     * riferimento alla cheda corrente questo puntatore serve solo
     * temporaneamente da quando viene creato nella sottoclasse xxxModulo a
     * quando viene inizializzato il gestore che mantiene il puntatore
     * effettivo
     */
    private String nomeSchedaCorrente = "";

    /**
     * nome chiave del modulo (viene usato per recuperare il modulo dalla
     * collezione e come default per il nome della tavola archivio e del menu)
     */
    private String nomeChiave = "";

    /**
     * voce del modulo come appare nel menu Moduli
     */
    private String titoloMenu = "";

    /**
     * flag per usare il gestore come tabella (finestre piu' piccole)
     */
    private boolean isTabella = false;

    /**
     * nodo dell'albero moduli in cui viene registrato questo modulo
     * serve per lanciare i moduli dipendenti e posizionare correttamente
     * i loro nodi nell'albero (se nell'albero vengono costruiti piu' nodi
     * dello stesso modulo, questo valore si riferisce all'ultimo nodo
     * costruito)
     */
    private AlberoNodo nodoCorrente = null;

    /**
     * Elenco di passaggi obbligati di questo modulo.
     * Serve per risolvere automaticamente le relazioni ambigue
     * per tutte le query del modulo.
     * Contiene valori statici (oggetti di tipo TreStringhe),
     * viene creato nel Modello e risolto in fase di inizializzazione
     * del Progetto, dopo l'inizializzazione di tutti i Moduli,
     * per costruire l'oggetto passaggiObbligati del Modulo
     * Struttura:
     * - nome modulo destinazione,
     * - nome modulo del campo passaggio,
     * - nome campo passaggio
     */
    private ArrayList passaggiObbligati = null;

    /**
     * elenco di passaggi obbligati di questo modulo
     */
    private PassaggiSelezione passaggiSelezione = null;

    /**
     * voce della finestra per il Navigatore standard
     */
    private String titoloFinestra = "";

    /**
     * nome del programma (usato solo per il modulo iniziale del programma)
     */
    private String nomeProgramma = "";

    /**
     * Istanza di classe interna per raccogliere tutti i messaggi tra moduli
     * (servono per lo scambio di informazioni)
     */
    private MetodiMessaggioModulo messaggio = null;

    /**
     * Istanza di classe interna per raccogliere tutti i metodi delle query
     */
    private MetodiQuery query = null;

    /**
     * riferimento alla logica specifica del modulo.
     * <p/>
     * Il modulo base non ha una logica specifica, le sottoclassi possono averla.
     */
    private LogicaModulo logicaSpecifica = null;

    /**
     * Flag - indica se il modulo fa parte del sistema
     * di moduli fissi del programma
     */
    private boolean moduloFisso = false;

    /**
     * A list of event listeners for this component.
     */
    protected EventListenerList listaListener;

    /**
     * Modalità di avvio del modulo
     */
    private ModoAvvio modoAvvio = null;

    /**
     * scheda di default usata per presentare il record all'esterno
     * (usata esternamente dai campi combo)
     */
    private Scheda schedaPop;


    /**
     * utilizzo delle transazioni nel ciclo di editing
     * del record dei navigatori
     */
    private boolean usaTransazioni;


    /**
     * nome utilizzato nella lista dai campi combo di altri moduli linkati
     */
    private String nomePubblico;

    /**
     * palette del modulo
     */
    private Palette palette;


    /**
     * Costruttore base senza parametri <br>
     * Indispensabile anche se non viene utilizzato (anche solo per
     * compilazione in sviluppo) <br>
     */
    public ModuloBase() {
    }/* fine del metodo costruttore base */


    /**
     * Costruttore completo <br>
     *
     * @param unNomeModulo nome interno del modulo
     * @param unNodo nodo dell'albero moduli
     */
    public ModuloBase(String unNomeModulo, AlberoNodo unNodo) {
        this(unNomeModulo, unNodo, false);
    }/* fine del metodo costruttore completo */


    /**
     * Costruttore completo <br>
     *
     * @param unNomeModulo nome interno del modulo
     * @param unNodo nodo dell'albero moduli
     * @param fisso true se e' un modulo fisso del programma
     */
    public ModuloBase(String unNomeModulo, AlberoNodo unNodo, boolean fisso) {
        /* rimanda al costruttore della superclasse */
        super();

        /* regola le variabili di istanza coi parametri */
        this.setModuloFisso(fisso);
        this.setNomeModulo(unNomeModulo);
        this.nodoCorrente = unNodo;

        /**
         * regolazioni iniziali di riferimenti e variabili
         */
        try { // prova ad eseguire il codice
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }/* fine del blocco try-catch */
    }/* fine del metodo costruttore completo */


    /**
     * Regolazioni iniziali di riferimenti e variabili
     * Metodo chiamato direttamente dal costruttore
     * Vengono costruite le classi di default e vengono memorizzati negli
     * attributi i relativi riferimenti
     * Se si usano classi specifiche verranno sovrascritti gli attributi
     * (variabili)
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        /* variabili e costanti locali di lavoro */
        Navigatore nav;

        try { // prova ad eseguire il codice

            this.setModuliDipendenti(new ArrayList<Modulo>());

            /*
             * crea la collezione di moduli (vuota)
             * verra' riempita nella sottoclasse specifica
             */
            this.setModuli(new ArrayList<String>());

            /*
             * crea i sotto-moduli di questo modulo e dopo crea se stesso
             * (prima i moduli semplici e poi quello di alto livello)
             */
            this.creaModuli();

            /**
             * Regola di default il nome pubblico uguale al nome modulo
             */
            this.setNomePubblico(this.getNomeModulo());

            /* crea un'istanza dell'oggetto che raccoglie tutti i  messaggi tra moduli */
            this.messaggio = new MetodiMessaggioModulo(this);

            /* crea un'istanza dell'oggetto che raccoglie i metodi delle query */
            this.query = new MetodiQuery(this);

            /* regola la modalita di avvio del modulo */
            this.setModoAvvio(ModoAvvio.normale);

            /* regola la variabile col progetto in esecuzione */
            this.unProgetto = Progetto.getIstanza();

            /* crea un'azione standard per avviare il modulo
             * (passandogli il riferimento a questo oggetto-modulo) */
            this.setAzioneModulo(new AzAvviaModulo(this));

            /* crea un gestore */
            this.setGestore(new GestoreBase());

            /* crea l'elenco dei passaggi obbligati statici */
            this.passaggiObbligati = new ArrayList();

            /* crea l'oggetto passaggiSelezione del Modulo */
            this.passaggiSelezione = new PassaggiSelezione();

            /**
             * crea un modello normale dei dati (Abstract Data Types)
             */
            this.setModello(new ModelloDefault());

            /*
            * Crea la collezione vuota di schede.
            * Aggiunge una scheda di default visibile all'esterno del modulo <br>
            */
            this.setSchede(new LinkedHashMap<String, Scheda>());
            this.addScheda(new SchedaDefault(this));

            /*
            * Crea la collezione vuota di navigatori.
            */
            this.setNavigatori(new LinkedHashMap<String, Navigatore>());

            /* Chiave del navigatore di default */
            this.setNomeNavigatoreDefault(Navigatore.NOME_CHIAVE_DEFAULT);

            /* Crea il navigatore di default e lo aggiunge alla collezione. */
            nav = NavigatoreFactory.normale(this);
            this.addNavigatoreCorrente(nav);

            /**
             * Creazione e regolazione dei Navigatori.
             * Nel modulo base non fa nulla.
             * Viene sovrascritto nelle sottoclassi per creare
             * nuovi Navigatori o regolare i Navigatori esistenti
             */
            this.creaNavigatori();

            /**
             * crea un gestore dello stato scheda
             */
            this.setGestoreStatoScheda(new GestoreStatoSchedaDefault());

//            spostato dal alex come primo comando del metodo
//            i moduli da cui questo dipende vanno creati subito
//            se no i comandi in <inizia> di questo modulo non li
//            hanno disponibili!
//            alex 21-05-07
//            /*
//             * crea i sotto-moduli di questo modulo e dopo crea se stesso
//             * (prima i moduli semplici e poi quello di alto livello)
//             */
//            this.creaModuli();

            /* lista dei propri eventi */
            this.setListaListener(new EventListenerList());

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch


    }/* fine del metodo inizia */


    /**
     * Preparazione del Modulo.
     * <p/>
     * Prepara gli oggetti immediatamente necessari
     * al funzionamento del Modulo <br>
     * Invocato dal Progetto una sola volta.
     * Invocato dopo il ciclo di creazione e prima
     * del ciclo relaziona <br>
     *
     * @return true se la preparazione e' riuscita
     */
    public boolean prepara() {
        /* variabili e costanti locali di lavoro */
        boolean riuscito = false;
        boolean continua;
        Db database;

        continua = true;

        try { // prova ad eseguire il codice

            if (!this.isPreparato()) {

                /*
                 * Crea le eventuali preferenze specifiche del modulo
                 * Metodo sovrascritto nelle sottoclassi
                 */
                if (continua) {
//                    this.regolaPreferenze();
                }// fine del blocco if

                /*
                 * Assegna al Modulo il riferimento al database di Progetto.
                 * Sovrascrivibile dalle sottoclassi.
                 */
                if (continua) {
                    this.creaDatabase();
                }// fine del blocco if

                /* Inizializza il database del modulo.
                 * Se e' quello di progetto, e' gia' stato
                 * inizializzato, comunque il controllo e' interno
                 * al metodo inizializza() del database */
                if (continua) {
                    database = this.getDb();
                    if (database != null) {
                        continua = database.inizializza();
                    }// fine del blocco if
                }// fine del blocco if

                /*
                 * Crea una connessione al database e la assegna al Modulo
                 * La connessione viene anche aperta.
                 */
                if (continua) {
                    continua = this.creaConnessione();
                }// fine del blocco if

                /*
                 * Creazioni varie del modello
                 */
                if (continua) {
                    this.getModello().prepara(this);
                }// fine del blocco if

                /**
                 * Regola il flag preparato.
                 */
                this.setPreparato(true);

            }// fine del blocco if

            riuscito = continua;

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return riuscito;
    }


    /**
     * Relaziona i campi del Modello.
     */
    public boolean relaziona() {
        /* variabili e costanti locali di lavoro */
        boolean riuscito = true;

        try { // prova ad eseguire il codice
            if (!this.isRelazionato()) {

                /* se non è ancora preparato lo prepara ora */
                if (!this.isPreparato()) {
                    riuscito = this.prepara();
                    if (!riuscito) {
                        throw new Exception("Impossibile preparare il modulo " +
                                this.getNomeChiave());
                    }// fine del blocco if
                }// fine del blocco if

                riuscito = this.getModello().relaziona();
                this.setRelazionato(riuscito);

            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return riuscito;
    }


    /**
     * Regolazioni iniziali <i>una tantum</i>.
     * </p>
     * Metodo chiamato dalla classe Progetto <br>
     * Viene eseguito una sola volta <br>
     * <p/>
     * Inizializza il gestore , prima di tutto (servono i Comandi per
     * inzializzare i Campi) <br>
     * Tenta di inizializzare il modulo <br>
     * Prima inizializza il modello, se e' riuscito
     * inizializza anche gli altri oggetti del modulo <br>
     *
     * @return true se il modulo e' stato inizializzato
     */
    public boolean inizializza() {
        /* variabili e costanti locali di lavoro */
        boolean riuscito;

        try {    // prova ad eseguire il codice

            if (!this.isInizializzato()) {

                /* se non è ancora relazionato lo relaziona ora */
                if (!this.isRelazionato()) {
                    riuscito = this.relaziona();
                    if (!riuscito) {
                        throw new Exception("Impossibile relazionare il modulo " +
                                this.getNomeChiave());
                    }// fine del blocco if
                }// fine del blocco if

                /* inizializza l'azione di avvio */
                if (this.getAzioneModulo() != null) {
                    this.getAzioneModulo().inizializza();
                }// fine del blocco if

                /* inizializza il gestore dello stato della scheda */
                this.getGestoreStatoScheda().inizializzaGestoreStato(this);

                /* inizializza il modello */
                this.getModello().inizializza(this);

                /* aggiunge al menu gli eventuali moduli */
                this.addModuliVisibili();

                /* assegna una scheda pop se non già assegnata una più specifica */
                if (this.getSchedaPop() == null) {
                    this.setSchedaPop(new SchedaDefault(this));
                }// fine del blocco if

                /* inizializza tutte le schede */
                this.inizializzaSchede();

                /* inizializza i navigatori */
                this.inizializzaNavigatori();

                /* eventuale creazione dei dati iniziali */
                this.getModello().recuperaDatiStandard();

                /* aggiunta di azioni specifiche */
                this.creaAzioni();

                /* regola il flag */
                this.setInizializzato(true);
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return true;
    } // fine del metodo


    /**
     * Regolazioni di avvio, ogni volta che questo oggetto deve 'ripartire',
     * per essere sicuri che sia 'pulito'
     * <p/>
     * Metodo chiamato da altre classi <br>
     * Viene eseguito tutte le volte che necessita  <br>
     */
    public void avvia() {
        /* variabili e costanti locali di lavoro */
        Navigatore nav;
        Palette palette;

        try { // prova ad eseguire il codice

            /* inizializza se non ancora fatto */
            if (!this.isInizializzato()) {
                this.inizializza();
            }// fine del blocco if

            /* avvia tutti i Navigatori registrati */
            this.avviaNavigatori();

            switch (this.getModoAvvio()) {
                /* avvia il Navigatore corrente del Modulo */
                case normale:
                    nav = this.getNavigatoreCorrente();
                    if (nav != null) {
                        nav.apriNavigatore();
                    }// fine del blocco if
                    break;
                case paletta:
                    palette = this.creaPalette();
                    palette.avvia();
                    break;
                case splash:

                    break;
                case dialogo:

                    break;
                case senzaGui:

                    break;
                default: // caso non definito
                    break;
            } // fine del blocco switch

            /* il modulo è stato avviato la prima volta */
            this.setAvviato(true);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    } /* fine del metodo */



    /**
     * Ritorna un filtro Popup da usare nelle liste di moduli linkati.
     * <p/>
     *
     * @param nomeCampo il nome del campo per il filtro
     * @param titolo per il filtro
     *
     * @return il filtro popup
     */
    protected WrapFiltri getFiltroPop(String nomeCampo, String titolo) {
        /* variabili e costanti locali di lavoro */
        WrapFiltri filtroPop = null;
        Ordine ordine;
        int[] codici;
        ArrayList nomi;
        int cod;
        String nome;
        Filtro filtro;
        Modulo modulo;

        try {    // prova ad eseguire il codice

            modulo = this;

            filtroPop = new WrapFiltri();
            ordine = new Ordine();
            ordine.add(nomeCampo);
            codici = modulo.query().valoriChiave(ordine);
            nomi = modulo.query().valoriCampo(nomeCampo);

            for (int k = 0; k < codici.length; k++) {
                cod = codici[k];
                nome = Lib.Testo.getStringa(nomi.get(k));
                filtro = FiltroFactory.crea(modulo.getCampoChiave(), cod);
                filtroPop.add(filtro, nome);
            } // fine del ciclo for

            filtroPop.setTuttiFinale(false);
            filtroPop.setTitolo(titolo);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return filtroPop;
    }


    /**
     * Ritorna un filtro Popup da usare nelle liste di moduli linkati.
     * <p/>
     *
     * @param nomeCampo il nome del campo per il filtro
     *
     * @return il filtro popup
     */
    public WrapFiltri getFiltroPop(String nomeCampo) {
        /* invoca il metodo sovrascritto della superclasse */
        return this.getFiltroPop(nomeCampo, "Filtro");
    }


    /**
     * Ritorna un filtro Popup ss da usare nelle liste di moduli linkati.
     * <p/>
     *
     * @return il filtro popup
     */
    public WrapFiltri getFiltroPop() {
        /* invoca il metodo sovrascritto della superclasse */
        WrapFiltri wrapFiltri = null;
        Campo campo;
        boolean nonTrovato;

        try { // prova ad eseguire il codice
            campo = this.getCampo(Modello.NOME_CAMPO_SIGLA);
            nonTrovato = (campo == null);


            if (nonTrovato) {
                campo = this.getCampo(Modello.NOME_CAMPO_DESCRIZIONE);
                nonTrovato = (campo == null);
            }// fine del blocco if

            if (campo != null) {
                wrapFiltri = this.getFiltroPop(campo.getNomeInterno(), "Filtro");
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return wrapFiltri;
    }


    /**
     * Avvia tutti i Navigatori registrati per questo Modulo.
     * <p/>
     */
    private void avviaNavigatori() {
        /* variabili e costanti locali di lavoro */
        LinkedHashMap<String, Navigatore> navigatori;

        try {    // prova ad eseguire il codice
            navigatori = this.getNavigatori();
            for (Navigatore nav : navigatori.values()) {
                nav.avvia();
            }
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Crea ed inizializza la palette.
     * <p/>
     *
     * @return la palettte creata
     */
    private Palette creaPalette() {
        /* variabili e costanti locali di lavoro */
        Palette palette = null;
        ImageIcon iconaModulo;
        ArrayList<String> nomiModuli;
        Modulo mod;
        AzAvviaModulo azAvvia;

        try { // prova ad eseguire il codice

            /* crea una nuova palette */
            palette = new Palette();
            this.setPalette(palette);


//            palette.setTitolo(this.getNomeModulo());

            /* assegna l'icona del modulo alla finestra */
            azAvvia = this.getAzioneModulo();
            iconaModulo = azAvvia.getIconaPiccola();

            /* aggiunge tutti i sottomoduli alla palette */
            nomiModuli = this.getModuli();
            for (String nome : nomiModuli) {
                mod = Progetto.getModulo(nome);
                azAvvia = mod.getAzioneModulo();
                palette.addAzione(azAvvia);
            } // fine del ciclo for-each

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return palette;
    }


    /**
     * Creazione e regolazione dei Navigatori.
     * <p/>
     * Anche eventuale creazione <br>
     * Metodo invocato dal ciclo inizia del costruttore <br>
     * Regola il Navigatore di default <br>
     * Crea altri Navigatori (oltre a quello standard) <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     */
    protected void creaNavigatori() {
    }


    /**
     * Aggiunge un Navigatore con nome chiave propria alla collezione del Modulo.
     * <p/>
     * Se il nome chiave e' mancante solleva una eccezione.
     *
     * @param nav il navigatore da aggiungere
     */
    public void addNavigatore(Navigatore nav) {
        /* variabili e costanti locali di lavoro */
        String chiave = null;
        try { // prova ad eseguire il codice
            chiave = nav.getNomeChiave();
            if (Lib.Testo.isValida(chiave)) {
                this.putNavigatore(nav);
            } else {
                throw new Exception("Nome chiave navigatore mancante.");
            }// fine del blocco if-else
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    } // fine del metodo


    /**
     * Aggiunge un Navigatore con un dato nome chiave alla collezione del Modulo.
     * <p/>
     *
     * @param nav il navigatore da aggiungere
     * @param chiave per la collezione
     */
    public void addNavigatore(Navigatore nav, String chiave) {
        /* variabili e costanti locali di lavoro */
        try { // prova ad eseguire il codice
            if (Lib.Testo.isValida(chiave)) {
                if (this.getNavigatore(chiave) == null) {
                    nav.setNomeChiave(chiave);
                    this.putNavigatore(nav);
                } else {
                    throw new Exception("Nome chiave navigatore già esistente.");
                }// fine del blocco if-else
            } else {
                throw new Exception("Nome chiave navigatore non valido.");
            }// fine del blocco if-else
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    } // fine del metodo


    /**
     * Aggiunge un Navigatore con nome chiave alla collezione del Modulo.
     * <p/>
     * Imposta il navigatore aggiunto come navigatore corrente.
     * Se il nome chiave e' mancante solleva una eccezione.
     *
     * @param nav il navigatore da aggiungere
     */
    public void addNavigatoreCorrente(Navigatore nav) {
        try { // prova ad eseguire il codice
            this.addNavigatore(nav);
            this.setNomeNavigatoreCorrente(nav.getNomeChiave());
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    } // fine del metodo


    /**
     * Assegna il navigatore di default al modulo.
     * <p/>
     * Imposta il navigatore aggiunto come navigatore corrente.
     * Se il nome chiave e' mancante solleva una eccezione.
     *
     * @param nav il navigatore da aggiungere
     */
    public void setNavigatoreDefault(Navigatore nav) {
        try { // prova ad eseguire il codice
            nav.setNomeChiave(Navigatore.NOME_CHIAVE_DEFAULT);
            this.getNavigatori().remove(Navigatore.NOME_CHIAVE_DEFAULT);
            this.addNavigatore(nav);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    } // fine del metodo


    /**
     * azione che lancia questo modulo
     *
     * @param unaAzioneModulo DOCUMENT ME!
     */
    public void setAzioneModulo(AzAvviaModulo unaAzioneModulo) {
        this.azioneModulo = unaAzioneModulo;
    }/* fine del metodo setter */


    /**
     * azione che ha lanciato questo modulo
     *
     * @return DOCUMENT ME!
     */
    public AzAvviaModulo getAzioneModulo() {
        return this.azioneModulo;
    }/* fine del metodo getter */


    /**
     * Azione about.
     * <p/>
     * Apre una finestra di informazioni <br>
     * Metodo invocato dal Gestore Eventi <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     */
    public void apreAbout() {
        new AboutDialogo().avvia();
    }


    /**
     * Recupera la connessione del Modulo
     * <p/>
     *
     * @return la connessione del Modulo
     */
    public Connessione getConnessione() {
        return connessione;
    }


    /**
     * Assegna una connessione al Modulo
     * <p/>
     *
     * @param connessione la connessione da assegnare
     */
    public void setConnessione(Connessione connessione) {
        this.connessione = connessione;
    }


    public Gestore getGestore() {
        return gestore;
//        return Progetto.getGestore();
    }


    public void setGestore(Gestore gestore) {
        this.gestore = gestore;
    }


    /**
     * gestore stato lista
     *
     * @param unGestoreStatoScheda DOCUMENT ME!
     */
    public void setGestoreStatoScheda(GestoreStatoScheda unGestoreStatoScheda) {
        this.unGestoreStatoScheda = unGestoreStatoScheda;
    }/* fine del metodo setter */


    /**
     * gestore dello stato della scheda
     *
     * @return DOCUMENT ME!
     */
    public GestoreStatoScheda getGestoreStatoScheda() {
        return this.unGestoreStatoScheda;
    }/* fine del metodo getter */


    /**
     * gestione dell'aiuto con mappatura dei files HTML
     *
     * @param unHelp DOCUMENT ME!
     */
    public void setHelp(Help unHelp) {
        this.unHelp = unHelp;
    }/* fine del metodo setter */


    /**
     * utilizzo di un solo pannello nel navigatore
     */
    public void setUsaPannelloUnico(boolean unico) {
        /* variabili e costanti locali di lavoro */
        Navigatore nav;

        try { // prova ad eseguire il codice
            nav = this.getNavigatoreCorrente();

            if (nav != null) {
                nav.setUsaPannelloUnico(unico);
            } // fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * gestione dell'aiuto con mappatura dei files HTML
     *
     * @return DOCUMENT ME!
     */
    public Help getHelp() {
        return this.unHelp;
    }/* fine del metodo getter */


    /**
     * modello normale dei dati (Abstract Data Types)
     *
     * @param unModelloDati DOCUMENT ME!
     */
    public void setModello(Modello unModelloDati) {
        this.unModelloDati = unModelloDati;
    }/* fine del metodo setter */


    /**
     * modello dei dati (Abstract Data Types)
     *
     * @return DOCUMENT ME!
     */
    public Modello getModello() {
        return this.unModelloDati;
    }/* fine del metodo getter */


    /**
     * Aggiunge una scheda.
     * <p/>
     * Usa il nome chiave della scheda <br>
     * Se esiste già una scheda con la stessa chiave, modifica la chiave
     * interna dell'istanza scheda aggiungendo un numero progressivo <br>
     */
    protected String addScheda(Scheda unaScheda) {
        /* variabili e costanti locali di lavoro */
        String chiaveBase;
        String chiave = "";
        LinkedHashMap<String, Scheda> schede;
        boolean riuscito = false;
        int contatore = 0;

        try { // prova ad eseguire il codice

            chiaveBase = unaScheda.getNomeChiave();
            schede = this.getSchede();

            chiave = chiaveBase;
            while (!riuscito) {
                if (schede.containsKey(chiave)) {
                    contatore++;
                    chiave = chiaveBase + contatore;
                } else {
                    unaScheda.setNomeChiave(chiave);
                    schede.put(chiave, unaScheda);
                    riuscito = true;
                }// fine del blocco if-else
            }// fine del blocco while

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return chiave;
    }


    /**
     * Aggiunge una scheda al navigatore corrente e la rende corrente.
     * <p/>
     */
    protected void addSchedaNavCorrente(Scheda unaScheda) {
        /* variabili e costanti locali di lavoro */
        Navigatore nav;

        try { // prova ad eseguire il codice

            nav = this.getNavigatoreCorrente();
            if (nav != null) {
                nav.addSchedaCorrente(unaScheda);
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Stampa un elenco di campi in forma di lista.
     * <p/>
     *
     * @param vista contenente i campi da stampare
     * @param filtro per selezionare i record da stampare
     * @param ordine di stampa dei record
     *
     * @return l'oggetto StampaListaCampi creato, pronto da eseguire con run()
     */
    public StampaLista getStampaLista(Vista vista, Filtro filtro, Ordine ordine) {
        return new StampaLista(this, vista, filtro, ordine);
    }


    /**
     * Ritorna la lista dei moduli dai quali questo modulo dipende.
     * <p/>
     *
     * @return la lista dei moduli dai quali questo modulo dipende
     */
    public ArrayList<String> getModuli() {
        return this.nomiModuliMenu;
    }/* fine del metodo getter */


    /**
     * Regola la lista dei moduli dai quali questo modulo dipende.
     * <p/>
     *
     * @param unaCollezioneModuli la lista dei moduli dai quali questo modulo dipende
     */
    private void setModuli(ArrayList<String> unaCollezioneModuli) {
        this.nomiModuliMenu = unaCollezioneModuli;
    }


    /**
     * modulo della scheda che ha creato questo modulo
     *
     * @param unModuloChiamante DOCUMENT ME!
     */
    public void setModuloChiamante(Modulo unModuloChiamante) {
        this.unModuloChiamante = unModuloChiamante;
    }/* fine del metodo setter */


    /**
     * modulo della scheda che ha creato questo modulo
     *
     * @return DOCUMENT ME!
     */
    public Modulo getModuloChiamante() {
        return this.unModuloChiamante;
    }/* fine del metodo setter */


    /**
     * ritorna un'istanza di ModuloBase
     * per evitare l'uso della interfaccia
     *
     * @return un'istanza di ModuloBase
     */
    public ModuloBase getModuloBase() {
        return this;
    }/* fine del metodo setter */


    /**
     * Recupera il modulo di riferimento di questo oggetto (se stesso).
     * <br>
     *
     * @return il modulo di riferimento
     */
    public Modulo getModulo() {
        return this;
    }


    /**
     * modulo che ha creato questo modulo (alla partenza del programma)
     *
     * @param unModuloParente DOCUMENT ME!
     */
    public void setModuloParente(Modulo unModuloParente) {
        this.unModuloParente = unModuloParente;
    }/* fine del metodo setter */


    /**
     * modulo che ha creato questo modulo (alla partenza del programma)
     *
     * @return DOCUMENT ME!
     */
    public Modulo getModuloParente() {
        return this.unModuloParente;
    }/* fine del metodo */


    /**
     * restituisce il puntatore lista corrente
     *
     * @return DOCUMENT ME!
     */
    public String getNomeLista() {
        return this.nomeListaCorrente;
    }/* fine del metodo getter */


    /**
     * regola la lista corrente
     *
     * @param nomeListaCorrente DOCUMENT ME!
     */
    public void setNomeListaCorrente(String nomeListaCorrente) {
        this.nomeListaCorrente = nomeListaCorrente;
    }/* fine del metodo setter */


    /**
     * nome chiave del modulo (viene usato per recuperare il modulo dalla
     * collezione e come default per il nome della tavola, del menu)
     *
     * @param unNomeModulo il nome del modulo
     */
    public void setNomeModulo(String unNomeModulo) {
        this.nomeChiave = unNomeModulo;
    }/* fine del metodo setter */


    /**
     * nome chiave del modulo (viene usato per recuperare il modulo dalla
     * collezione e come default per il nome della tavola, del menu)
     *
     * @return DOCUMENT ME!
     */
    public String getNomeChiave() {
        return this.nomeChiave;
    }/* fine del metodo */


    /**
     * nome visibile pubblico del modulo.
     */
    public String getNomeModulo() {
        return this.getNomeChiave();
    }/* fine del metodo */


    /**
     * Ritorna il nome della tavola associata a questo modulo
     * <p/>
     *
     * @return il nome della tavola
     */
    public String getTavola() {
        Modello modello = null;
        String nomeTavola = "";

        try { // prova ad eseguire il codice
            modello = this.getModello();
            if (modello != null) {
                nomeTavola = modello.getTavolaArchivio();
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return nomeTavola;

    }/* fine del metodo */


    public String getTitoloMenu() {
        return titoloMenu;
    }


    protected void setTitoloMenu(String nomeMenu) {
        this.titoloMenu = nomeMenu;
    }


    /**
     * restituisce il puntatore scheda corrente
     *
     * @return DOCUMENT ME!
     */
    public String getNomeScheda() {
        return this.nomeSchedaCorrente;
    }/* fine del metodo getter */


    /**
     * regola la scheda corrente
     *
     * @param nomeSchedaCorrente DOCUMENT ME!
     */
    public void setNomeSchedaCorrente(String nomeSchedaCorrente) {
        this.nomeSchedaCorrente = nomeSchedaCorrente;
    }/* fine del metodo setter */


    /**
     * progetto in esecuzione  (il progetto e' unico, il riferimento serve solo
     * a recuperare l'istanza)
     *
     * @return DOCUMENT ME!
     */
    public Progetto getProgetto() {
        return this.unProgetto;
    }/* fine del metodo */


    /**
     * Ritorna una vista del Modello.
     * <p/>
     *
     * @param nome il nome della Vista
     *
     * @return la Vista richiesta
     */
    public Vista getVista(String nome) {
        return this.getModello().getVista(nome);
    } /* fine del metodo getter */


    /**
     * Ritorna la vista di default del Modulo
     */
    public Vista getVistaDefault() {
        return this.getModello().getVistaDefault();
    } /* fine del metodo getter */


    /**
     * Ritorna il nome della la vista di default del Modulo <p>
     *
     * @return il nome della la vista di default del Modulo
     */
    public String getNomeVistaDefault() {
        return VISTA_BASE_DEFAULT;
    } /* fine del metodo getter */


    /**
     * flag per usare il gestore come tabella (finestre piu' piccole)
     *
     * @param isTabella DOCUMENT ME!
     */
    public void setTabella(boolean isTabella) {
        this.isTabella = isTabella;
    }/* fine del metodo setter */


    /**
     * flag per usare il gestore come tabella (finestre piu' piccole)
     *
     * @return DOCUMENT ME!
     */
    public boolean isTabella() {
        return this.isTabella;
    }/* fine del metodo getter */


    public AlberoNodo getNodoCorrente() {
        return nodoCorrente;
    }


    public void setNodoCorrente(AlberoNodo nodoCorrente) {
        this.nodoCorrente = nodoCorrente;
    }


    public boolean isPreparato() {
        return preparato;
    }


    private void setPreparato(boolean preparato) {
        this.preparato = preparato;
    }


    public boolean isRelazionato() {
        return relazionato;
    }


    protected void setRelazionato(boolean relazionato) {
        this.relazionato = relazionato;
    }


    public Db getDb() {
        return db;
    }


    public void setDb(Db db) {
        this.db = db;
    }


    /**
     * metodo principale di passaggio di informazioni tra i moduli
     *
     * @param unaQueryModulo DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public boolean messaggioModulo(InfoModulo unaQueryModulo) {
        return false;
    }/* fine del metodo */


    protected String getNomeNavigatoreCorrente() {
        return nomeNavigatoreCorrente;
    }


    protected void setNomeNavigatoreCorrente(String nomeNavigatoreCorrente) {
        this.nomeNavigatoreCorrente = nomeNavigatoreCorrente;
    }


    /**
     * aggiunge un navigatore alla collezione
     *
     * @param unNavigatore navigatore da aggiungere
     * @param unaChiave chiave per recuperare il navigatore nella collezione
     */
    protected void putNavigatore(Navigatore unNavigatore, String unaChiave) {
        try { // prova ad eseguire il codice
            this.getNavigatori().put(unaChiave, unNavigatore);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }/* fine del blocco try-catch */
    }/* fine del metodo setter */


    /**
     * aggiunge un navigatore alla collezione.
     *
     * @param unNavigatore navigatore da aggiungere
     */
    private void putNavigatore(Navigatore unNavigatore) {
        /* variabili e costanti locali di lavoro */
        String unaChiave = "";

        try { // prova ad eseguire il codice

            /* recupera il nome chiave del navigatore */
            unaChiave = unNavigatore.getNomeChiave();

            /* invoca il metodo sovraccaricato di questa classe */
            this.putNavigatore(unNavigatore, unaChiave);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }/* fine del blocco try-catch */
    }/* fine del metodo setter */


    /**
     * Ritorna un navigatore dalla collezione, data la chiave.
     * <p/>
     *
     * @return il Navigatore richiesto
     */
    public Navigatore getNavigatore(String unaChiave) {
        /* variabili e costanti locali di lavoro */
        Navigatore unNavigatore = null;

        try {    // prova ad eseguire il codice
            unNavigatore = this.getNavigatori().get(unaChiave);
        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return unNavigatore;
    } // fine del metodo


    /**
     * Ritorna il Navigatore corrente.
     * <p/>
     */
    public Navigatore getNavigatoreCorrente() {
        /* valore di ritorno */
        return this.getNavigatore(this.getNomeNavigatoreCorrente());
    } // fine del metodo


    /**
     * Ritorna la lista gestita dal navigatore corrente.<br>
     *
     * @return la lista gestita dal Navigatore
     */
    public Lista getLista() {
        /* variabili e costanti locali di lavoro */
        Lista lista = null;
        Navigatore nav;

        try { // prova ad eseguire il codice
            nav = this.getNavigatoreCorrente();

            if (nav != null) {
                lista = nav.getLista();
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return lista;
    }


    /**
     * Ritorna il Navigatore di default del Modulo.
     * <p/>
     *
     * @return il navivagore di default
     */
    public Navigatore getNavigatoreDefault() {
        /* valore di ritorno */
        return this.getNavigatore(getNomeNavigatoreDefault());
    } // fine del metodo


    private String getNomeNavigatoreDefault() {
        return nomeNavigatoreDefault;
    }


    private void setNomeNavigatoreDefault(String chiave) {
        this.nomeNavigatoreDefault = chiave;
    }


    /**
     * Crea tutti i Moduli indispensabili per il funzionamento di questo modulo.
     * </p>
     * Metodo invocato dal ciclo inizia del costruttore <br>
     * Metodo sovrascritto nelle classi specifiche <br>
     */
    protected void creaModuli() {
    }


    /**
     * Crea un nuovo modulo, dipendente da questo.
     * </p>
     * Metodo invocato dal ciclo inizia del costruttore <br>
     * Chiamato dal metodo creaModulo del modulo specifico <br>
     * Rimanda al metodo delegato di Progetto, passandogli questo
     * modulo, parente di quello che dovra' essere creato <br>
     *
     * @param unModulo modulo provvisorio, usato solo
     * per portarsi il percorso della classe ed il nome chiave <br>
     */
    protected Modulo creaModulo(Modulo unModulo) {
        /* variabili e costanti locali di lavoro */
        Modulo moduloNuovo = null;
        AlberoNodo unNodo = null;

        try { // prova ad eseguire il codice

            /* aggiunge alla lista dei moduli dipendenti */
            this.getModuliDipendenti().add(unModulo);

            /* recupera il nodo corrente dell'albero */
            unNodo = this.getNodoCorrente();

            /* invoca il metodo delegato */
            moduloNuovo = Progetto.getIstanza().creaModulo(unModulo, unNodo);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return moduloNuovo;
    }/* fine del metodo */


    /**
     * Aggiunge i moduli (e le tabelle) al menu.
     * </p>
     * Metodo invocato dal ciclo inizializza <br>
     * Metodo sovrascritto nelle classi specifiche <br>
     */
    protected void addModuliVisibili() {
    }


    /**
     * Costruisce il menu tabelle specifico.
     * </p>
     * Metodo invocato dal ciclo inizializza <br>
     * Di default usa il navigatore corrente <br>
     */
    protected void setMenuTabelle(JMenuItem menu) {
        /* invoca il metodo sovrascritto della classe */
        this.setMenuTabelle(menu, this.getNavigatoreCorrente());
    }


    /**
     * Costruisce il menu tabelle specifico.
     * </p>
     * Metodo invocato dal ciclo inizializza <br>
     */
    protected void setMenuTabelle(JMenuItem menu, Navigatore nav) {
        /* invoca il metodo delegato della classe */
        nav.setMenuTabelle(menu);
    }


    /**
     * Aggiunge un modulo (o una tabella) al menu.
     * </p>
     * Metodo invocato dal ciclo inizializza <br>
     * Chiamato dal metodo addModuloVisibile del modulo specifico <br>
     * Aggiunge alla collezione moduli (di questo modulo), gli eventuali moduli
     * (o tabelle), che verranno poi inserite nel menu moduli e tabelle, dalla
     * classe Navigatore <br>
     * I moduli e le tabelle appaiono nei rispettivi menu, nell'ordine in cui
     * sono elencati nella sottoclasse specifica <br>
     * La collezione moduli accetta oggetti di tipo Modulo recuperati dalla
     * collezione della classe Singleton Progetto
     *
     * @param nomeModulo nome-chiave interno del modulo da mostrare nel menu
     */
    protected void addModuloVisibile(String nomeModulo) {
        try {    // prova ad eseguire il codice

            /* aggiunge alla collezione specifica di questo modulo */
            this.getModuli().add(nomeModulo);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    public boolean isPrimoModulo() {
        /* variabili e costanti locali di lavoro */
        boolean primo = false;
        AlberoNodo nodo;

        try { // prova ad eseguire il codice
            nodo = this.getNodoCorrente();
            if (nodo != null) {
                primo = nodo.isRoot();
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* variabili e costanti locali di lavoro */
        return primo;
    }


    /**
     * ritorna i passaggi obbligati del modulo.<br>
     *
     * @return l'oggetto contenente i passaggi obbligati del modulo
     */
    public PassaggiSelezione getPassaggiSelezione() {
        return this.passaggiSelezione;
    }/* fine del metodo getter */


    /**
     * Ritorna l'oggetto contenente i metodi Query del modulo.
     * <p/>
     *
     * @return l'oggetto contenente i metodi Query
     */
    public MetodiQuery query() {
        return this.query;
    }


    /**
     * Ritorna l'oggetto contenente i metodi Messaggio del modulo.
     * <p/>
     *
     * @return l'oggetto contenente i metodi Messaggio
     */
    public MetodiMessaggioModulo messaggio() {
        return this.messaggio;
    }


    /**
     * Inizializza tutte le schede.
     * <p/>
     * Metodo invocato dal ciclo inizializza <br>
     */
    private void inizializzaSchede() {
        /* variabili e costanti locali di lavoro */
        LinkedHashMap<String, Scheda> schede;

        try { // prova ad eseguire il codice

            schede = this.getSchede();
            for (Scheda scheda : schede.values()) {
                scheda.inizializza();
            }

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }/* fine del blocco try-catch */

    }/* fine del metodo */


    /**
     * inizializza i navigatori
     */
    protected void inizializzaNavigatori() {
        /* variabili e costanti locali di lavoro */
        Navigatore unNavigatore;

        try { // prova ad eseguire il codice

            /* todo disabilitato alex 25-10-07, se lo mettiamo in Inizializza, non possiamo
 * todo fare ricerche e selezioni su tabelle grandi (es. città) */
//            if (this.isTabella()) {
//                unNavigatore = this.getNavigatoreDefault();
//                unNavigatore.setUsaRicerca(false);
//                unNavigatore.setUsaSelezione(false);
//            }// fine del blocco if

            /* se il modello usa il campo Preferito,
             * attiva il flag sul Navigatore di default */
            if (this.getModello().isUsaCampoPreferito()) {
                unNavigatore = this.getNavigatoreDefault();
                if (unNavigatore != null) {
                    unNavigatore.setUsaPreferito(true);
                }// fine del blocco if
            }// fine del blocco if

            /* opportunità per la sottoclasse di regolare i
             * navigatori prima della inizializzazione */
            this.regolaNavigatori();

            /* inizializzazione */
            for (Navigatore nav : this.getNavigatori().values()) {
                nav.inizializza();
            }

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }/* fine del blocco try-catch */

    }/* fine del metodo */


    /**
     * Invocato prima della inizializzazione dei Navigatori.
     * <p/>
     * Opportunità per la sottoclasse di regolare i Navigatori
     * prima della inizializzazione
     */
    protected void regolaNavigatori() {
        try {    // prova ad eseguire il codice
            ;
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }

//    /**
//     * Controlla l'utilizzo delle transazioni.
//     *
//     * @return vero se può utilizzarla
//     */
//    private boolean usaTransazioni() {
//        /* variabili e costanti locali di lavoro */
//        boolean transa = false;
//
//        try { // prova ad eseguire il codice
//            if (this.isUsaTransazioni()) {
//                transa = (Progetto.ModFissi.contatori.isUso() && Progetto.ModFissi.semafori.isUso());
//            }// fine del blocco if
//
//        } catch (Exception unErrore) { // intercetta l'errore
//            Errore.crea(unErrore);
//        }/* fine del blocco try-catch */
//
//        /* valore di ritorno */
//        return transa;
//    }/* fine del metodo */


    /**
     * Esegue dei task specifici del modulo.<br>
     * Chiamato da Progetto dopo l'inizializzazione di tutti i moduli.<br>
     * I moduli vengono visitati una sola volta per ogni modulo,<br>
     * nell'ordine Discendente dell'albero (dall'alto al basso).<br>
     * Puo' essere sovrascritto dalla sottoclasse.<br>
     */
    public void postDiscendente() {
        try {    // prova ad eseguire il codice

        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    } // fine del metodo


    /**
     * Esegue dei task specifici del modulo.<br>
     * Chiamato da Progetto dopo l'inizializzazione di tutti i moduli.<br>
     * I moduli vengono visitati una sola volta per ogni modulo,<br>
     * nell'ordine Ascendente dell'albero (dal basso verso l'alto).<br>
     * Puo' essere sovrascritto dalla sottoclasse.<br>
     */
    public void postAscendente() {
        try {    // prova ad eseguire il codice

            /* recupero dei dati iniziali */
//            this.getModello().popolaTavolaArchivio();

        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    } // fine del metodo


    public String getTitoloFinestra() {
        return titoloFinestra;
    }


    protected void setTitoloFinestra(String titoloFinestra) {
        this.titoloFinestra = titoloFinestra;
        this.getNavigatoreCorrente().setTitoloFinestra(titoloFinestra);
    }


    /**
     * Ritorna l'eventuale icona del modulo, visualizzata
     * nel titolo della finestra, nella palette o nei menu
     * <p/>
     * L'icona viene recuperata dall'azione Avvia Modulo
     * L'icona recuperata è quella media
     *
     * @return l'eventuale icona media del modulo
     */
    private ImageIcon getIconaMedia() {
        /* variabili e costanti locali di lavoro */
        ImageIcon icona = null;
        boolean continua;
        AzAvviaModulo azModulo;

        try { // prova ad eseguire il codice

            /* recupera l'azione modulo */
            azModulo = this.getAzioneModulo();
            continua = (azModulo != null);

            /* recupera la chiave della icona media dall'azione */
            if (continua) {
                icona = azModulo.getIconaMedia();
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return icona;
    }


    public String getNomeProgramma() {
        return nomeProgramma;
    }


    protected void setNomeProgramma(String nomeProgramma) {
        this.nomeProgramma = nomeProgramma;
    }


    /**
     * Ritorna un campo dalla collezione campi del Modello.
     * <p/>
     *
     * @param nomeCampo il nome interno del campo
     *
     * @return il campo
     */
    public Campo getCampo(String nomeCampo) {
        /* variabili e costanti locali di lavoro */
        Campo campo = null;
        Modello modello;
        try { // prova ad eseguire il codice
            modello = this.getModello();
            if (modello != null) {
                campo = modello.getCampo(nomeCampo);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return campo;
    }


    /**
     * Ritorna un campo dalla collezione campi del Modello.
     * <p/>
     *
     * @param campo l'elemento della Enum Campi dall'interfaccia
     *
     * @return il campo
     */
    public Campo getCampo(Campi campo) {
        /* variabili e costanti locali di lavoro */
        Campo unCampo = null;
        String nome = "";

        try { // prova ad eseguire il codice
            nome = campo.get();
            unCampo = this.getCampo(nome);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return unCampo;
    }


    /**
     * Ritorna il clone di un campo dalla collezione campi del Modello.
     * <p/>
     *
     * @param nomeCampo il nome interno del campo
     *
     * @return il campo clonato
     */
    public Campo getCloneCampo(String nomeCampo) {
        Campo campoClonato = null;
        Campo campoOriginale;

        try { // prova ad eseguire il codice
            campoOriginale = this.getCampo(nomeCampo);
            if (campoOriginale != null) {
                campoClonato = campoOriginale.clonaCampo();
                campoClonato.reset();
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return campoClonato;
    }


    /**
     * Ritorna il campo chiave del modulo.
     * <p/>
     *
     * @return il campo chiave
     */
    public Campo getCampoChiave() {
        return this.getModello().getCampoChiave();
    }


    /**
     * Ritorna il campo ordine del modulo.
     * <p/>
     *
     * @return il campo ordine
     */
    public Campo getCampoOrdine() {
        return this.getModello().getCampoOrdine();
    }


    /**
     * Ritorna il campo visibile del modulo.
     * <p/>
     *
     * @return il campo visibile
     */
    public Campo getCampoVisibile() {
        return this.getModello().getCampoVisibile();
    }


    /**
     * Restituisce il campo Note del Modello.
     *
     * @return il campo Note
     */
    public Campo getCampoNote() {
        return this.getModello().getCampoNote();
    } /* fine del metodo getter */


    /**
     * Restituisce il campo "preferito" del Modello.
     *
     * @return il campo "preferito"
     */
    public Campo getCampoPreferito() {
        return this.getModello().getCampoPreferito();
    } /* fine del metodo getter */


    /**
     * Ritorna tutti i campi del modulo.
     * <p/>
     *
     * @return il la lista dei campi fisici del modulo
     */
    public ArrayList getCampi() {
        return Libreria.hashMapToArrayList(this.getModello().getCampiModello());
    }


    /**
     * Ritorna tutti i campi fisici del modulo.
     * <p/>
     *
     * @return il la lista dei campi fisici del modulo
     */
    public ArrayList<Campo> getCampiFisici() {
        return Libreria.hashMapToArrayList(this.getModello().getCampiFisici());
    }


    /**
     * Ritorna i campi fisici non fissi (non algos) del modulo.
     * <p/>
     *
     * @return il la lista dei campi fisici non fissi
     */
    public ArrayList<Campo> getCampiFisiciNonFissi() {
        /* variabili e costanti locali di lavoro */
        ArrayList<Campo> campi = null;
        LinkedHashMap oggetti;

        try { // prova ad eseguire il codice

            oggetti = this.getModello().getCampiFisiciNonFissi();

            campi = new ArrayList<Campo>();

            for (Object oggetto : oggetti.values()) {
                if (oggetto instanceof Campo) {
                    campi.add((Campo)oggetto);
                }// fine del blocco if
            } // fine del ciclo for-each

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return campi;
    }


    /**
     * Restituisce i soli campi fissi
     * <p/>
     *
     * @return la lista dei campi fissi
     */
    public ArrayList<Campo> getCampiFissi() {
        return this.getModello().getCampiFissi();
    }


    /**
     * Ritorna l'elenco dei campi definiti come ricercabili.
     * <p/>
     *
     * @return l'elenco dei campi ricercabili
     */
    public ArrayList<Campo> getCampiRicercabili() {
        return this.getModello().getCampiRicercabili();
    }


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
        return this.getModello().getAlberoCampi();
    }


    /**
     * Controlla l'esistenza di un Campo.
     * <br>
     *
     * @param unaChiave nome chiave per recuperare il campo dalla collezione
     *
     * @return vero se il campo esiste nella collezione campi del Modello
     */
    public boolean isEsisteCampo(String unaChiave) {
        return this.getModello().isEsisteCampo(unaChiave);
    } // fine del metodo


    /**
     * Controlla l'esistenza di una Vista.
     * <br>
     *
     * @param unaChiave nome chiave per recuperare la vista dalla collezione
     *
     * @return vero se la Vista esiste nella collezione viste del Modello
     */
    public boolean isEsisteVista(String unaChiave) {
        return this.getModello().isEsisteVista(unaChiave);
    } // fine del metodo


    /**
     * Ritorna un filtro per identificare i dati dei quali eseguire
     * il backup per questo modulo.
     * <p/>
     * Normalmente il filtro è nullo, copia tutti i dati
     *
     * @return il filtro da applicare in fase di backup
     */
    public Filtro getFiltroBackup() {
        return null;
    }


    /**
     * Ritorna un filtro per identificare i dati da eliminare prima di
     * effettuare il restore per questo modulo.
     * <p/>
     * Normalmente il filtro è nullo, elimina su tutti i dati
     *
     * @return il filtro di eliminazione da applicare prima del restore
     */
    public Filtro getFiltroRestore() {
        return null;
    }


    /**
     * aggiunge un passaggio obbligato all'elenco statico di passaggi obbligati
     * (statico) di questo modulo.
     *
     * @param unPassaggio un oggetto di tipo TreStringhe che descrive il passaggio
     */
    public void addPassaggioObbligato(TreStringhe unPassaggio) {
        try {    // prova ad eseguire il codice
            this.passaggiObbligati.add(unPassaggio);
        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Riempie l'oggetto passaggiSelezione (dinamico) del modulo
     * in base all'elenco statico dei passaggi obbligati (creato nel modello).<br>
     * Chiamato da Progetto dopo l'inizializzazione di tutti i moduli
     * tutte le query che includono campi del modulo
     * destinazione useranno il percorso che passa dal campo specificato.
     */
    public void regolaPassaggiSelezione() {
        /* variabili e costanti locali di lavoro */
        TreStringhe unPassaggio = null;
        String nomeModuloDestinazione = null;
        Modulo moduloDestinazione = null;
        String nomeModuloPassaggio = null;
        Modulo moduloPassaggio = null;
        String nomeCampoPassaggio = null;
        String tavolaDestinazione = null;
        Campo campoPassaggio = null;

        try {    // prova ad eseguire il codice

            for (int k = 0; k < passaggiObbligati.size(); k++) {
                unPassaggio = (TreStringhe)passaggiObbligati.get(k);
                nomeModuloDestinazione = unPassaggio.getPrima();
                nomeModuloPassaggio = unPassaggio.getSeconda();
                nomeCampoPassaggio = unPassaggio.getTerza();
                /* aggiunge il passaggio solo se esistono i moduli richiesti */
                if (Progetto.isEsisteModulo(nomeModuloDestinazione)) {
                    if (Progetto.isEsisteModulo(nomeModuloPassaggio)) {
                        moduloDestinazione = Progetto.getModulo(nomeModuloDestinazione);
                        moduloPassaggio = Progetto.getModulo(nomeModuloPassaggio);
                        tavolaDestinazione = moduloDestinazione.getModello()
                                .getTavolaArchivio();
                        campoPassaggio = moduloPassaggio.getModello().getCampo(nomeCampoPassaggio);
                        this.passaggiSelezione.addPassaggio(tavolaDestinazione, campoPassaggio);
                    }// fine del blocco if
                }// fine del blocco if
            } // fine del ciclo for

        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Crea e presenta un nuovo record vuoto.
     * <p/>
     * Viene presentato in una scheda ad hoc, modale <br>
     *
     * @return codice del record appena creato
     *         -1 se non riuscito
     */
    public int nuovoRecord() {
        return this.nuovoRecord(null);
    }


    /**
     * Crea e presenta un nuovo record con valori preimpostati.
     * <p/>
     * Viene presentato in una scheda ad hoc, modale <br>
     *
     * @param valori preimpostati, null per record vuoto
     *
     * @return codice del record appena creato
     *         -1 se non riuscito
     */
    public int nuovoRecord(ArrayList<CampoValore> valori) {
        /* variabili e costanti locali di lavoro */
        int codice = -1;
        boolean continua;
        Connessione conn;
        Scheda scheda;
        boolean riuscito = false;

        try { // prova ad eseguire il codice

            codice = this.query().nuovoRecord(valori);
            continua = codice > 0;

            if (continua) {
                scheda = this.getSchedaPop();
                conn = this.getConnessione();
                riuscito = this.presentaRecord(codice, scheda, true, conn);
            }// fine del blocco if

            if (continua) {
                if (!riuscito) {
                    this.query().eliminaRecord(codice);
                    codice = -1;
                }// fine del blocco if-else
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return codice;
    }


    /**
     * Presenta un record esistente in una scheda modale.
     * <p/>
     * Legge i dati dal database
     * Carica i dati nella scheda
     * Presenta la scheda in modifica all'utente
     *
     * @param codice del record
     * @param nomeScheda nome chiave della scheda nella collezione schede del modulo
     * @param conn connessione da utilizzare per il recupero dei dati
     *
     * @return true se confermato, con o senza modifiche
     */
    public boolean presentaRecord(int codice, String nomeScheda, Connessione conn) {
        /* variabili e costanti locali di lavoro */
        Scheda scheda;
        boolean confermato = false;

        try { // prova ad eseguire il codice
            scheda = this.getScheda(nomeScheda);
            if (scheda != null) {
                confermato = this.presentaRecord(codice, scheda, conn);
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return confermato;
    }


    /**
     * Presenta un record esistente in una scheda modale.
     * Usa la scheda pop del Modulo.
     * <p/>
     *
     * @param codice del record
     * @param conn connessione da utilizzare per il recupero dei dati
     *
     * @return true se confermato, con o senza modifiche
     */
    public boolean presentaRecord(int codice, Connessione conn) {
        /* variabili e costanti locali di lavoro */
        Scheda scheda;
        boolean confermato = false;

        try { // prova ad eseguire il codice
            scheda = this.getSchedaPop();
            confermato = this.presentaRecord(codice, scheda, conn);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return confermato;
    }


    /**
     * Presenta un record esistente in una scheda modale.
     * Usa la connessione di default del Modulo.
     * <p/>
     *
     * @param codice del record
     * @param nomeScheda nome chiave della scheda nella collezione schede del modulo
     *
     * @return true se confermato, con o senza modifiche
     */
    public boolean presentaRecord(int codice, String nomeScheda) {
        /* variabili e costanti locali di lavoro */
        Scheda scheda;
        boolean confermato = false;

        try { // prova ad eseguire il codice
            scheda = this.getScheda(nomeScheda);
            if (scheda != null) {
                confermato = this.presentaRecord(codice, scheda, null);
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return confermato;
    }


    /**
     * Presenta un record esistente in una scheda modale.
     * <p/>
     * Usa la scheda di default per presentazione esterna (schedaPop) del modulo
     * Usa la connessione del modulo
     *
     * @param codice del record
     *
     * @return true se confermato, con o senza modifiche
     */
    public boolean presentaRecord(int codice) {
        /* variabili e costanti locali di lavoro */
        boolean confermato = false;
        Connessione conn;

        try { // prova ad eseguire il codice

            conn = this.getConnessione();
            confermato = this.presentaRecord(codice, conn);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return confermato;
    }


    /**
     * Presenta un record esistente in una scheda modale.
     * <p/>
     * Consente l'editing all'utente e permette di confermare o abbandonare.
     *
     * @param codice del record
     * @param scheda da utilizzare
     * @param conn connessione da utilizzare per il recupero dei dati
     *
     * @return vero se confermato, con o senza modifiche
     */
    public boolean presentaRecord(int codice, Scheda scheda, Connessione conn) {
        return this.presentaRecord(codice, scheda, false, conn);
    }


    /**
     * Presenta un record in una scheda modale.
     * <p/>
     * Consente l'editing all'utente e permette di confermare o abbandonare.
     *
     * @param codice del record
     * @param scheda da utilizzare
     * @param nuovoRecord true se il record presentato è un nuovo record
     * @param conn connessione da utilizzare per il recupero dei dati
     *
     * @return vero se confermato, con o senza modifiche
     */
    public boolean presentaRecord(int codice,
                                  Scheda scheda,
                                  boolean nuovoRecord,
                                  Connessione conn) {
        /* variabili e costanti locali di lavoro */
        boolean confermato = false;
        PortaleModulo portale;

        try { // prova ad eseguire il codice

            portale = new PortaleModulo(scheda, conn);
            portale.inizializza();
            portale.avvia(codice, nuovoRecord);
            confermato = portale.isConfermato();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return confermato;
    }

    /**
     * Presenta un record in una scheda non modale.
     * <p/>
     * Quando l'editing termina notifica un listener.
     *
     * @param codice del record
     * @param nuovoRecord true se il record presentato è un nuovo record
     * @param editListener listener notificato quando termina l'editing
     */
    public void presentaRecord(int codice, boolean nuovo, OnEditingFinished editListener){
        Scheda scheda = this.getSchedaPop();
        Connessione conn = this.getConnessione();
        PortaleModulo portale = new PortaleModulo(scheda, conn, true);
        portale.addEditFinishedListener(editListener);
        portale.inizializza();
        portale.avvia(codice, nuovo);
    }


    /**
     * Restituisce un estratto.
     * </p>
     * Restituisce un estratto conforme al tipo ed al record richiesto <br>
     *
     * @param estratto desiderato
     * @param chiave con cui effettuare la ricerca
     *
     * @return l'estratto costruito dal modello
     */
    public EstrattoBase getEstratto(Estratti estratto, Object chiave) {
        /* variabili e costanti locali di lavoro */
        EstrattoBase est = null;
        Modello modello;

        try { // prova ad eseguire il codice
            modello = this.getModello();
            if (modello != null) {
                est = modello.getEstratto(estratto, chiave);
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return est;
    }


    /**
     * Restituisce un estratto sotto forma di stringa.
     * </p>
     *
     * @param estratto stringa dell'estratto desiderato
     * @param chiave con cui effettuare la ricerca
     * @param conn connessione da utilizzare
     *
     * @return la stringa costruita dal modello
     */
    public String getEstStr(Estratti estratto, int chiave, Connessione conn) {
        /* variabili e costanti locali di lavoro */
        String stringa = "";
        boolean continua;
        EstrattoBase est = null;

        try { // prova ad eseguire il codice
            /* controllo di congruità */
            continua = (estratto != null && chiave > 0);

            if (continua) {
                est = this.getEstratto(estratto, chiave, conn);
                continua = (est != null);
            } // fine del blocco if

            if (continua) {
                if (est.getTipo() == EstrattoBase.Tipo.stringa) {
                    stringa = est.getStringa();
                } // fine del blocco if
            } // fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return stringa;
    }


    /**
     * Restituisce un estratto.
     * </p>
     * Restituisce un estratto conforme al tipo ed al record richiesto <br>
     * Utilizza la connessione fornita per effettuare le query <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     *
     * @param estratto tipo di estratto desiderato
     * @param chiave con cui effettuare la ricerca
     * @param conn la connessione da utilizzare per le query
     *
     * @return l'estratto costruito
     */
    public EstrattoBase getEstratto(Estratti estratto, Object chiave, Connessione conn) {
        /* variabili e costanti locali di lavoro */
        EstrattoBase est = null;
        Modello modello;

        try { // prova ad eseguire il codice
            modello = this.getModello();
            if (modello != null) {
                est = modello.getEstratto(estratto, chiave, conn);
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return est;
    }


    /**
     * Restituisce il codice dell'eventuale Record Preferito.
     * <p/>
     * Se esiste il campo Preferito, ritorna il record con Preferito = true
     * Se non esiste il campo Preferito o se non è indicato un
     * Record Preferito, ritorna il primo record del Modulo
     * in ordine di campo Ordine.
     *
     * @return il codice del Record Preferito, 0 se non trovato
     */
    public int getRecordPreferito() {
        /* variabili e costanti locali di lavoro */
        int codPref = 0;
        Campo campoPref;
        boolean fallback = false;
        Query query;
        Dati dati;

        try {    // prova ad eseguire il codice

            campoPref = this.getCampoPreferito();

            if (campoPref != null) {
                codPref = this.query().valoreChiave(campoPref.getNomeInterno(), true);
                if (codPref == 0) {
                    fallback = true;   // non c'è record Preferito, attiva fallback
                }// fine del blocco if
            } else { // non c'è il campo Preferito, attiva fallback
                fallback = true;
            }// fine del blocco if-else

            /* procedura di fallback
             * ritorna il primo record in ordine di Ordine */
            if (fallback) {
                query = new QuerySelezione(this);
                query.addCampo(this.getCampoChiave());
                query.addOrdine(this.getCampoOrdine());
                dati = this.query().querySelezione(query);
                if (dati.getRowCount() > 0) {
                    codPref = dati.getIntAt(0, this.getCampoChiave());
                }// fine del blocco if
                dati.close();
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return codPref;
    }


    /**
     * Recupera una icona specifica del modulo.
     * <p/>
     *
     * @param chiave dell'icona da recuperare
     *
     * @return l'icona richiesta, null se non trovata
     */
    public Icon getIcona(String chiave) {
        /* variabili e costanti locali di lavoro */
        Icon icona = null;

        try {    // prova ad eseguire il codice
            icona = Lib.Risorse.getIcona(this, chiave);
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return icona;
    }


    /**
     * Assegna al Modulo il riferimento a un database.
     * <p/>
     * Invocato dal ciclo Prepara.<br>
     * Sovrascritto dalle sottoclassi.<br>
     * - Il database viene assegnato solo se non gia' esistente.<br>
     * - Se esiste un database di Progetto, assegna al modulo il database
     * di Progetto.<br>
     * - Se non esiste il database di Progetto, crea un nuovo database
     * e lo assegna al Progetto.<br>
     * - Va sovrascritto quando si vuole usare un database
     * diverso da quello di Progetto.<br>
     * - Il metodo che sovrascrive dovra' creare un nuovo database
     * e assegnarlo al modulo.<br>
     */
    protected void creaDatabase() {
        /* variabili e costanti locali di lavoro */
        Db db = null;
        boolean continua = true;

        try {    // prova ad eseguire il codice

            /* controlla se esiste gia' un database */
            if (continua) {
                if (this.getDb() != null) {
                    continua = false;
                }// fine del blocco if
            }// fine del blocco if

//            /* controlla se e' previsto l'uso del database */
//            if (continua) {
//                if (!PrefOld.getBool(PrefOld.DB.USA_DATABASE)) {
//                    continua = false;
//                }// fine del blocco if
//            }// fine del blocco if

            /* controlla se esiste un database di progetto
             * se non esiste ancora, lo crea e lo assegna al progetto */
            if (continua) {
                if (Progetto.getDb() == null) {

                    db = DbFactory.crea();
                    db.inizializza();
                    Progetto.setDb(db);

                    /* controlla se il database di progetto e' disponibile alle connessioni
                     * se non lo e' mostra le preferenze e poi esce */
                    if (!db.isDisponibileConnessioni()) {
                        Progetto.emergenzaDatiMancanti();
                    }// fine del blocco if

                }// fine del blocco if
            }// fine del blocco if

            /* assegna al modulo il database di Progetto */
            if (continua) {
                this.setDb(Progetto.getDb());
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Recupera o crea una connessione al database.
     * <p/>
     * La connessione viene aperta
     *
     * @return true se riuscito.
     */
    protected boolean creaConnessione() {

        /* variabili e costanti locali di lavoro */
        boolean riuscito = false;
        Connessione conn = null;
        Db db = null;
        boolean continua;

        try {    // prova ad eseguire il codice

            /* recupera il database del modulo */
            db = this.getDb();
            continua = (db != null);

            /**
             * recupera o crea la connessione
             * - se e' il db di progetto, usa la connessione di progetto
             * (il progetto crea la connessione alla prima chiamata di getConnessione)
             * - se e' in altro db, crea la connessione
             * usa i dati di connessione del database
             */
            if (continua) {
                if (db.equals(Progetto.getDb())) {
                    conn = Progetto.getConnessione();
                } else {
                    conn = db.creaConnessione();
                }// fine del blocco if-else
                continua = (conn != null);
            }// fine del blocco if

            /* registra la connessione */
            if (continua) {
                this.setConnessione(conn);
            }// fine del blocco if

            /* apre la connessione se non gia' aperta */
            if (continua) {
                if (!conn.isOpen()) {
                    riuscito = conn.open();
                } else {
                    riuscito = true;
                }// fine del blocco if-else
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return riuscito;
    }


    /**
     * Chiede al modulo il permesso di chiuderlo.
     * <p/>
     * Presenta eventuali messaggi all'utente per risolvere situazioni aperte <br>
     *
     * @return true se riuscito.
     */
    public boolean richiedeChiusura() {
        /* variabili e costanti locali di lavoro */
        boolean riuscito = true;

        try {    // prova ad eseguire il codice
            riuscito = this.richiediChiusuraNavigatori();
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return riuscito;
    }


    /**
     * Richiede la chiusura a tutti i navigatori non pilotati del modulo.
     * <p/>
     * I navigatori pilotati vengono chiusi dal navigatore pilota.
     *
     * @return true se tutti i navigatori non pilotati sono disponibili alla chiusura.
     */
    private boolean richiediChiusuraNavigatori() {
        /* variabili e costanti locali di lavoro */
        boolean riuscito = true;
        Navigatore navigatore;
        Iterator unGruppo;

        try {    // prova ad eseguire il codice
            unGruppo = this.getNavigatori().values().iterator();

            while (unGruppo.hasNext()) {
                navigatore = (Navigatore)unGruppo.next();
                if (!navigatore.isPilotato()) {
                    riuscito = navigatore.richiediChiusura();
                    if (!riuscito) {
                        break;
                    }// fine del blocco if
                }// fine del blocco if
            } /* fine del blocco while */

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return riuscito;

    }


    /**
     * Chiude effettivamente il modulo.
     * <p/>
     */
    public void chiude() {
        Connessione conn;
        Db database;

        try {    // prova ad eseguire il codice

            /* chiude i navigatori */
            this.chiudeNavigatori();

            /* chiude la connessione (se non e' quella di Progetto)*/
            conn = this.getConnessione();
            if (conn != null) {
                if (!conn.equals(Progetto.getConnessione())) {
                    conn.close();
                }// fine del blocco if
            }// fine del blocco if

            /* spegne il database (se non e' quello di Progetto) */
            database = this.getDb();
            if (database != null) {
                if (!database.equals(Progetto.getDb())) {
                    database.shutdown();
                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Chiusura della scheda tramite il bottone Annulla.
     * <p/>
     * Metodo invocato dal gestore eventi <br>
     */
    public void chiudeScheda() {
        /* variabili e costanti locali di lavoro */
        Navigatore nav;
        boolean continua;

        try { // prova ad eseguire il codice
            nav = this.getNavigatoreDefault();
            continua = (nav != null);

            if (continua) {
                nav.chiudeScheda();
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Chiude tutti i navigatori del modulo.
     * <p/>
     */
    private void chiudeNavigatori() {
        /* variabili e costanti locali di lavoro */
        Navigatore navigatore;
        Iterator unGruppo;
        try {    // prova ad eseguire il codice
            unGruppo = this.getNavigatori().values().iterator();

            while (unGruppo.hasNext()) {
                navigatore = (Navigatore)unGruppo.next();
                navigatore.chiudiNavigatore();
            } /* fine del blocco while */

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Rilascia un numero di ID per il prossimo record di questo modulo.
     * <p/>
     * Aggiorna il contatore prima del rilascio.
     *
     * @return il prossimo ID per il nuovo record del Modulo, 0 se non riuscito
     */
    public int releaseID() {
        /* variabili e costanti locali di lavoro */
        ContatoreModulo modCont;
        int codCont;
        int nextID = 0;

        try {    // prova ad eseguire il codice
            modCont = Progetto.getModuloContatore();
            if (modCont != null) {
                codCont = this.getModello().getIdContatore();
                nextID = modCont.releaseID(codCont);
            } else {
                throw new Exception("Impossibile rilasciare un ID per il nuovo record.\n" +
                        "Il Progetto non contiene il modulo Contatori.");
            }// fine del blocco if-else
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return nextID;
    }

//    /**
//     * Regola le preferenze specifiche di questo modulo.
//     * <p/>
//     * Invoca il metodo sovrascritto creaPreferenze()<br>
//     */
//    private void regolaPreferenze() {
//        try {    // prova ad eseguire il codice
//
//            /* Invoca il metodo sovrascritto creaPreferenze()
//             * Il modulo specifico aggiunge le proprie preferenze
//             * alla collezione di progetto */
//            this.creaPreferenze();
//
//            /* Scrive i temi delle preferenze sul database (valori hard-coded) */
//            PrefFactory.scriveTemi(this);
//
//            /* Scrive le preferenze sul database (valori hard-coded) */
//            PrefFactory.scrivePreferenze(this);
//
//            /* Legge le preferenze dal database (valori correnti) */
//            PrefFactory.leggePreferenze(this);
//
//        } catch (Exception unErrore) {    // intercetta l'errore
//            Errore.crea(unErrore);
//        } // fine del blocco try-catch
//    }


    /**
     * Crea le preferenze specifiche di questo modulo.
     * <p/>
     * Invocato dal metodo prepara() del modulo.<br>
     * Sovrascritto dai moduli specifici quando devono creare preferenze proprie.<br>
     */
    protected void creaPreferenze() {

//        /* esempio di aggiunta di un tema specifico
//         * da effettuarsi nel modulo specifico */
//        TemaValore tema = new TemaValore();
//        tema.setSigla("temaSpecifico");
//        tema.setDescrizione("descrizione del tema specifico");
//        tema.setLivello(1);
//        this.addTema(tema);
//
//        /* esempio di aggiunta di una preferenza specifica
//         * da effettuarsi nel modulo specifico */
//        PrefValore pref = new PrefValore();
//        pref.setTema(Pref.Gen.TEMA);
//        pref.setSigla("pippo");
//        pref.setDescrizione("Preferenza specifica");
//        pref.setNote("Prova di una preferenza specifica del modulo");
//        pref.setNoteProg("qui le note del programmatore");
//        pref.setAttiva(false);
//        pref.setTipo(PrefPac.TIPO_INT);
//        pref.setStandard(97);
//        this.addPreferenza(pref);

    }

//    /**
//     * Aggiunge una preferenza specifica di questo modulo.
//     * <p/>
//     * Da invocare nel modulo specifico dall'interno del metodo creaPreferenze.<br>
//     * Regola automaticamente il modulo proprietario della preferenza passata.<br>
//     *
//     * @param pref l'oggetto di tipo PrefValore da aggiungere
//     */
//    protected void addPreferenza(PrefValore pref) {
//        try { // prova ad eseguire il codice
//            if (pref != null) {
//                pref.setChiaveModulo(this.getNomeChiave());
//                PrefFactory.addPref(pref);
//            }// fine del blocco if
//        } catch (Exception unErrore) { // intercetta l'errore
//            Errore.crea(unErrore);
//        }// fine del blocco try-catch
//
//    }
//
//
//    /**
//     * Aggiunge un tema di preferenza specifica di questo modulo.
//     * <p/>
//     * Da invocare nel modulo specifico dall'interno del metodo creaPreferenze.<br>
//     * Regola automaticamente il modulo proprietario del tema passato.<br>
//     *
//     * @param tema l'oggetto di tipo PrefValore da aggiungere
//     */
//    protected void addTema(TemaValore tema) {
//        try { // prova ad eseguire il codice
//            if (tema != null) {
//                tema.setChiaveModulo(this.getNomeChiave());
//                PrefFactory.addTema(tema);
//            }// fine del blocco if
//        } catch (Exception unErrore) { // intercetta l'errore
//            Errore.crea(unErrore);
//        }// fine del blocco try-catch
//
//    }

//
//    /**
//     * Ritorna la lista delle preferenze specifiche del modulo.
//     * <p/>
//     * Spazzola la collezione di progetto ed estrae solo quelle
//     * relative al modulo.
//     *
//     * @return la lista delle preferenze specifiche del modulo (oggetti PrefValore)
//     */
//    public ArrayList getPreferenze() {
//        /* variabili e costanti locali di lavoro */
//        ArrayList lista = null;
//        Iterator unGruppo = null;
//        PrefValore pref = null;
//        String chiaveModulo = null;
//        String chiave = null;
//
//        try {    // prova ad eseguire il codice
//
//            lista = new ArrayList();
//
//            chiaveModulo = this.getNomeChiave();
//
//            /* spazzola tutte le preferenze */
//            unGruppo = PrefOld.getPreferenze().values().iterator();
//            while (unGruppo.hasNext()) {
//                pref = (PrefValore)unGruppo.next();
//                chiave = pref.getChiaveModulo();
//                if (chiave.equals(chiaveModulo)) {
//                    lista.add(pref);
//                }// fine del blocco if
//            } /* fine del blocco while */
//
//        } catch (Exception unErrore) {    // intercetta l'errore
//            Errore.crea(unErrore);
//        } // fine del blocco try-catch
//
//        /* valore di ritorno */
//        return lista;
//    }

//    /**
//     * Ritorna la lista dei temi di preferenza preferenze specifici del modulo.
//     * <p/>
//     * Spazzola la collezione di progetto ed estrae solo i temi
//     * relativi al modulo.
//     *
//     * @return la lista dei temi specifici del modulo (oggetti TemaValore)
//     */
//    public ArrayList getTemiPreferenze() {
//        /* variabili e costanti locali di lavoro */
//        ArrayList lista = null;
//        Iterator unGruppo = null;
//        TemaValore tema = null;
//        String chiaveModulo = null;
//        String chiave = null;
//
//        try {    // prova ad eseguire il codice
//
//            lista = new ArrayList();
//
//            chiaveModulo = this.getNomeChiave();
//
//            /* spazzola tutte le preferenze */
//            unGruppo = PrefOld.getTemiPreferenze().values().iterator();
//            while (unGruppo.hasNext()) {
//                tema = (TemaValore)unGruppo.next();
//                chiave = tema.getChiaveModulo();
//                if (chiave.equals(chiaveModulo)) {
//                    lista.add(tema);
//                }// fine del blocco if
//            } /* fine del blocco while */
//
//        } catch (Exception unErrore) {    // intercetta l'errore
//            Errore.crea(unErrore);
//        } // fine del blocco try-catch
//
//        /* valore di ritorno */
//        return lista;
//    }


    /**
     * Ritorna la rappresentazione del modulo come Stringa.
     * <p/>
     */
    public String toString() {
        /* variabili e costanti locali di lavoro */
        String stringa = null;

        try {    // prova ad eseguire il codice
            stringa = this.getNomeChiave();
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return stringa;
    }


    /**
     * Ritorna il percorso completo del file.
     * <p/>
     * Necessario perché ho oscurato il metodo toString <br>
     */
    public String getPath() {
        /* valore di ritorno */
        return super.toString();
    }


    /**
     * Presenta un dialogo per selezionare un record tramite un combo.
     * <p/>
     *
     * @param nomeCampo da esaminare
     * @param filtro dei records da selezionare
     * @param messaggio del dialogo
     * @param valIniziale del selettore
     * @param usaNuovo permette di creare nuovi record (default=true)
     * @param est Eventuale estratto posizionato sotto
     * @param altriCampi eventuali da visualizzare nel combo
     *
     * @return codice del record selezionato
     *         zero se non selezionato
     */
    public int selezionaRecord(String nomeCampo,
                               Filtro filtro,
                               String messaggio,
                               String valIniziale,
                               boolean usaNuovo,
                               Estratti est,
                               String... altriCampi) {
        /* variabili e costanti locali di lavoro */
        int codice = 0;
        boolean continua;
        Campo unCampo = null;
        int numRec;
        int cambioSelettore = 20;
        Dialogo dialogo = null;
        Object ogg;

        try { // prova ad eseguire il codice
            /* recupero il numero di records interessati */
            numRec = query.contaRecords(filtro);
            continua = (numRec > 0);

            /* crea un combo a seconda del numero di records */
            if (continua) {
                if (numRec < cambioSelettore) {
                    unCampo = CampoFactory.comboLinkPop(nomeCampo);
                } else {
                    unCampo = CampoFactory.comboLinkSel(nomeCampo);
                }// fine del blocco if-else
                continua = (unCampo != null);
            }// fine del blocco if

            if (continua) {
                unCampo.setNomeModuloLinkato(this.getNomeModulo());
                unCampo.setNomeCampoValoriLinkato(nomeCampo);
                unCampo.setLarScheda(150);
                unCampo.decora().eliminaEtichetta();
                unCampo.setUsaNuovo(usaNuovo);
                unCampo.setFiltroBase(filtro);
            }// fine del blocco if

            /* aggiunge eventuali colonne aggiuntive al combo */
            if (continua) {
                if (altriCampi != null) {
                    for (int k = 0; k < altriCampi.length; k++) {
                        unCampo.addColonnaCombo(altriCampi[k]);
                    } // fine del ciclo for
                }// fine del blocco if
            }// fine del blocco if


            if (continua) {
                if (Lib.Testo.isValida(valIniziale)) {
                    unCampo.setValoreIniziale(valIniziale);
                }// fine del blocco if
            }// fine del blocco if

            if (continua) {
                if (est != null) {
                    unCampo.decora().estrattoSotto(est);
                }// fine del blocco if
            }// fine del blocco if

            if (continua) {
                unCampo.inizializza();
            }// fine del blocco if

            if (continua) {
                if (!Lib.Testo.isValida(messaggio)) {
                    messaggio = "Seleziona " + nomeCampo;
                }// fine del blocco if
            }// fine del blocco if

            if (continua) {
                dialogo = new DialogoSelezionaRecord(this, messaggio, unCampo);
                dialogo.avvia();
            }// fine del blocco if

            if (continua) {
                if (dialogo.isConfermato()) {
                    ogg = unCampo.getValore();
                    codice = Libreria.getInt(ogg);
                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return codice;
    }


    /**
     * Presenta un dialogo per selezionare un record.
     * <p/>
     *
     * @param nomeCampo da esaminare
     * @param filtro dei records da selezionare
     * @param messaggio del dialogo
     * @param valIniziale del selettore
     * @param usaNuovo permette di creare nuovi record (default=true)
     * @param est Eventuale estratto posizionato sotto
     *
     * @return stringa trovata
     */
    public String selezionaTesto(String nomeCampo,
                                 Filtro filtro,
                                 String messaggio,
                                 String valIniziale,
                                 boolean usaNuovo,
                                 Estratti est) {
        /* variabili e costanti locali di lavoro */
        String valore = "";
        int codice;

        try { // prova ad eseguire il codice
            codice = selezionaRecord(nomeCampo, filtro, messaggio, valIniziale, usaNuovo, est);

            if (codice > 0) {
                valore = this.query().valoreStringa(nomeCampo, codice);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return valore;
    }


    /**
     * Presenta un dialogo per selezionare un record tramite un combo.
     * <p/>
     *
     * @param nomeCampo da esaminare
     *
     * @return codice del record selezionato
     *         zero se non selezionato
     */
    public int selezionaRecord(String nomeCampo) {
        /* invoca il metodo delegato della classe */
        return this.selezionaRecord(nomeCampo, null, "", "", true, null, (String[])null);
    }


    /**
     * Presenta un dialogo per selezionare un record tramite un combo.
     * <p/>
     *
     * @param nomi dei campi da visualizzare nella scelta da combo
     *
     * @return codice del record selezionato
     *         zero se non selezionato
     */
    public int selezionaRecord(String... nomi) {
        /* variabili e costanti locali di lavoro */
        int cod = 0;
        boolean continua;
        String[] aggiuntivi;
        String nomeCampo;
        int quanti;

        try { // prova ad eseguire il codice

            continua = (nomi != null) && (nomi.length > 0);

            if (continua) {

                quanti = nomi.length;

                nomeCampo = nomi[0];

                if (quanti > 1) {
                    aggiuntivi = new String[quanti - 1];
                    for (int k = 0; k < quanti - 1; k++) {
                        aggiuntivi[k] = nomi[k + 1];
                    } // fine del ciclo for
                    cod = this.selezionaRecord(nomeCampo, null, "", "", true, null, aggiuntivi);
                } else {
                    cod = this.selezionaRecord(nomeCampo, null, "", "", true, null);
                }// fine del blocco if-else

            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return cod;
    }


    /**
     * Seleziona, con un dialogo, un valore da un campo testo con un selettore.
     * <p/>
     *
     * @param nomeCampo da esaminare
     *
     * @return valore del testo selezionato
     */
    public String selezionaTesto(String nomeCampo) {
        /* invoca il metodo delegato della classe */
        return this.selezionaTesto(nomeCampo, null, "", "", true, null);
    }


    /**
     * Bottone carica tutti in una <code>Lista</code>.
     * <p/>
     * Sovrascritto nelle sottoclassi <br>
     * Sincronizza lo stato della GUI <br>
     * Invoca il metodo delegato <br>
     */
    public void caricaTutti() {
        this.getNavigatoreCorrente().caricaTutti();
    }


    /**
     * Chiude la scheda corrente registrando il record.
     * <p/>
     * Metodo invocato dal Gestore Eventi <br>
     * Invoca il metodo delegato <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     *
     * @return true se riuscito
     */
    public boolean registraScheda() {
        return this.getNavigatoreDefault().registraScheda();
    }


    /**
     * Ritorna l'oggetto che gestisce la logica specifica del modulo.
     * <p/>
     *
     * @return la logica specifica del modulo
     */
    public LogicaModulo getLogicaSpecifica() {
        return logicaSpecifica;
    }


    /**
     * Assegna l'oggetto che gestisce la logica specifica del modulo.
     * <p/>
     *
     * @param logica la logica specifica del modulo
     */
    protected void setLogicaSpecifica(LogicaModulo logica) {
        this.logicaSpecifica = logica;
    }


    public boolean isInizializzato() {
        return inizializzato;
    }


    private void setInizializzato(boolean inizializzato) {
        this.inizializzato = inizializzato;
    }


    /**
     * Ritorna true se il modulo è stato avviato una prima volta
     * <p/>
     *
     * @return true se il modulo è stato avviato una prima volta
     */
    public boolean isAvviato() {
        return avviato;
    }


    protected void setAvviato(boolean avviato) {
        this.avviato = avviato;
    }


    /**
     * Ritorna l'elenco dei moduli dipendenti da questo modulo
     * <p/>
     *
     * @return l'elenco dei moduli dipendenti
     */
    public ArrayList<Modulo> getModuliDipendenti() {
        return moduliDipendenti;
    }


    private void setModuliDipendenti(ArrayList<Modulo> moduliDipendenti) {
        this.moduliDipendenti = moduliDipendenti;
    }


    private LinkedHashMap<String, Scheda> getSchede() {
        return schede;
    }


    private void setSchede(LinkedHashMap<String, Scheda> schede) {
        this.schede = schede;
    }


    /**
     * Recupera la scheda di default.
     * <p/>
     *
     * @return la scheda richiesta
     */
    public Scheda getSchedaDefault() {
        return this.getSchede().get(Modulo.NOME_SCHEDA_DEFAULT);
    }


    /**
     * Recupera una scheda dalla collezione
     * <p/>
     *
     * @param chiave della scheda
     *
     * @return la scheda richiesta
     */
    public Scheda getScheda(String chiave) {
        return this.getSchede().get(chiave);
    }


    /**
     * Recupera la collezione di navigatori del modulo
     * <p/>
     *
     * @return la collezione di navigatori
     */
    public LinkedHashMap<String, Navigatore> getNavigatori() {
        return navigatori;
    }


    private void setNavigatori(LinkedHashMap<String, Navigatore> navigatori) {
        this.navigatori = navigatori;
    }


    /**
     * Controlla se questo modulo fa parte del sistema di moduli fissi.
     * <p/>
     *
     * @return true se e' un modulo fisso
     */
    public boolean isModuloFisso() {
        return moduloFisso;
    }


    /**
     * Regola il flag  che indica se questo modulo fa parte
     * del sistema di moduli fissi.
     * <p/>
     *
     * @param moduloFisso true se e' un modulo fisso
     */
    public void setModuloFisso(boolean moduloFisso) {
        this.moduloFisso = moduloFisso;
    }


    /**
     * Sincronizza gli elementi.
     * <p/>
     * Metodo invocato dal metodo fire() <br>
     * Può essere invocato anche da altri metodi interni <br>
     * (in salita) <b>
     */
    public void sincroInterno() {
    }


    /**
     * Sincronizza gli elementi.
     * <p/>
     * Metodo invocato da sincroEsterno del contenitore superiore <br>
     * (in discesa) <b>
     */
    public void sincroEsterno() {
    }


    public EventListenerList getListaListener() {
        return listaListener;
    }


    public void setListaListener(EventListenerList listaListener) {
        this.listaListener = listaListener;
    }


    public ModoAvvio getModoAvvio() {
        return modoAvvio;
    }


    protected void setModoAvvio(ModoAvvio modoAvvio) {
        this.modoAvvio = modoAvvio;
    }


    /**
     * Metodo invocato quando viene premuta una HotKey.
     * <p/>
     * Metodo sovrascritto nelle sottoclassi <br>
     *
     * @param chiave della hotkey premuta
     */
    public void hotkey(int chiave) {
    }


    /**
     * Ritorna la scheda da utilizzare per l'editing dall'esterno
     * <p/>
     *
     * @return la scheda per l'editing esterno
     */
    public Scheda getSchedaPop() {
        return schedaPop;
    }


    /**
     * Assegna la scheda da usare per la presentazione
     * dei record richiesta dall'esterno (campi combo)
     *
     * @param schedaPop da utilizzare
     */
    protected void setSchedaPop(Scheda schedaPop) {
        this.schedaPop = schedaPop;
    }


    /**
     * Controlla se l'uso delle transazioni è attivato per il Modulo
     * <p/>
     *
     * @return true se l'uso delle transazioni è attivato
     */
    public boolean isUsaTransazioni() {
        return usaTransazioni;
    }


    protected void setUsaTransazioni(boolean usaTransazioni) {
        this.usaTransazioni = usaTransazioni;
    }


    /**
     * Ritorna il nome pubblico del modulo, utilizzato nella lista dai
     * campi combo di altri moduli linkati.
     * <p/>
     *
     * @return il nome pubblico
     */
    public String getNomePubblico() {
        return nomePubblico;
    }


    protected void setNomePubblico(String nomePubblico) {
        this.nomePubblico = nomePubblico;
    }


    protected Palette getPalette() {
        return palette;
    }


    private void setPalette(Palette palette) {
        this.palette = palette;
    }


    /**
     * Ritorna l'albero dei campi calcolati
     * <p/>
     *
     * @return l'albero dei campi calcolati
     */
    public AlberoCalcolo getAlberoCalcolo() {
        return this.getModello().getAlberoCalcolo();
    }


    /**
     * Crea eventuali azioni specifiche del modulo.
     * <p/>
     * Le azioni vengono aggiunte al navigatore corrente <br>
     * Le azioni vengono aggiunte alla toolbar della lista
     * oppure al menu Archivio <br>
     * Metodo invocato dal ciclo inizializza <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     * (metodo chiamato dalla superclasse) <br>
     */
    protected void creaAzioni() {
    }


    /**
     * Crea una riga di menu e l'aggiunge al menu passatogli.
     * <p/>
     *
     * @param mod modulo da lanciare
     * @param menu a cui aggiungere la riga di menu
     */
    protected void creaRigaMenu(Modulo mod, JMenu menu) {
        /* variabili e costanti locali di lavoro */
        Azione azione;
        JMenuItem rigamenu;

        try { // prova ad eseguire il codice
            /* costruisce la riga di menu partendo dall'azione */
            azione = mod.getAzioneModulo();
            rigamenu = new JMenuItem(azione.getAzione());
            menu.add(rigamenu);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Crea un'azione specifica.
     * <p/>
     * L'azione viene aggiunta al navigatore corrente <br>
     * L'azione viene aggiunta alla toolbar della lista <br>
     * Metodo invocato dal ciclo inizializza <br>
     * <p/>
     * Crea un'istanza di BottoneAzione <br>
     * Recupera l'icona <br>
     * Recupera la toolbar del portale Lista del navigatore corrente <br>
     * Aggiunge botttone e icona alla toolbar <br>
     *
     * @param mod modulo
     * @param nomeIcona path relativo dell'immagine da usare
     * @param metodo nome del metodo nella classe modulo
     * @param help string di help (tooltiptext)
     *
     * @deprecated
     */
    protected void creaAzione(Modulo mod, String nomeIcona, String metodo, String help) {
        /* variabili e costanti locali di lavoro */
        BottoneAzione bottone;
        Navigatore nav;
        ToolBar toolbar;
        String pathModulo;
        String pathIcona;
        String cartella = "icone";
        String car = ".";
        int pos;
        ImageIcon icona;
        Portale portale;

        try { // prova ad eseguire il codice

            /* crea l'istanza */
            bottone = new BottoneAzione(this, metodo);

            /* aggiunge il testo di help */
            bottone.setToolTipText(help);

            /* recupera l'icona */
            /* si suppone che esista una cartella icone */
            /* e che sia allo stesso livello del modulo chiamante */
            pathModulo = mod.getPath();
            pos = pathModulo.lastIndexOf(car);
            pathIcona = pathModulo.substring(0, pos);
            pathIcona += car;
            pathIcona += cartella;
            pathIcona += car;
            pathIcona += nomeIcona;
            icona = Lib.Risorse.getIconaBase(pathIcona);
            bottone.setIcon(icona);

            nav = this.getNavigatoreCorrente();
            portale = nav.getPortaleLista();

            /* aggiunge l'azione alla collezione del portale
             * usa il nome del metodo come chiave */
            portale.addAzione(metodo, bottone.getAzione());

            /* aggiunge il bottone alla toolbar */
            toolbar = portale.getToolBar();
            toolbar.getToolBar().add(bottone);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Crea un'azione specifica.
     * <p/>
     * L'azione viene aggiunta al navigatore corrente <br>
     * L'azione viene aggiunta alla toolbar della lista <br>
     * Metodo invocato dal ciclo inizializza <br>
     * <p/>
     * Crea un'istanza di BottoneAzione <br>
     * Recupera l'icona <br>
     * Recupera la toolbar del portale Lista del navigatore corrente <br>
     * Aggiunge botttone e icona alla toolbar <br>
     *
     * @param nomeIcona path relativo dell'immagine da usare
     * @param metodo nome del metodo nella classe modulo
     * @param help string di help (tooltiptext)
     *
     * @deprecated
     */
    protected void creaAzione(String nomeIcona, String metodo, String help) {
        /* invoca il metodo sovrascritto della classe */
        this.creaAzione(this, nomeIcona, metodo, help);
    }


    /**
     * Crea un'azione specifica e la aggiunge al navigatore specificato.
     * <p/>
     * L'azione può essere aggiunta: <ul>
     * <li> Alla toolbar della lista (in coda) </li>
     * <li> Alla toolbar della scheda (in coda) </li>
     * <li> Ad un menu (posizione specificata nell'azione stessa) </li>
     * <ul/>
     * Metodo invocato dal ciclo inizializza <br>
     *
     * @param azione completa di icona, voce ed help
     * @param nav navigatore specifico
     * @param tool switch di selezione lista/scheda/menu
     *
     * @deprecated
     */
    protected void addAzione(Azione azione, Navigatore nav, Navigatore.Tool tool) {
        /* invoca il metodo delegato della libreria */
        Lib.Risorse.addAzione(azione, nav, tool);
    }


    /**
     * Crea un'azione specifica e la aggiunge al navigatore specificato.
     * <p/>
     * L'azione può essere aggiunta: <ul>
     * <li> Alla toolbar della lista (in coda) </li>
     * <li> Alla toolbar della scheda (in coda) </li>
     * <li> Ad un menu (posizione specificata nell'azione stessa) </li>
     * <ul/>
     * Metodo invocato dal ciclo inizializza <br>
     *
     * @param azione completa di icona, voce ed help
     * @param nav navigatore specifico
     */
    protected void addAzione(Azione azione, Navigatore nav) {
        /* invoca il metodo delegato della libreria */
        Lib.Risorse.addAzione(azione, nav);
    }


    /**
     * Crea un'azione specifica e la aggiunge al navigatore corrente.
     * <p/>
     * L'azione può essere aggiunta: <ul>
     * <li> Alla toolbar della lista (in coda) </li>
     * <li> Alla toolbar della scheda (in coda) </li>
     * <li> Ad un menu (posizione specificata nell'azione stessa) </li>
     * <ul/>
     * Metodo invocato dal ciclo inizializza <br>
     * Di default seleziona il navigatore corrente <br>
     *
     * @param azione completa di icona, voce ed help
     *
     * @deprecated
     */
    protected void addAzione(Azione azione, Navigatore.Tool tool) {
        /* invoca il metodo sovrascritto (quasi) della classe */
        this.addAzione(azione, this.getNavigatoreCorrente(), tool);
    }


    /**
     * Crea un'azione specifica e la aggiunge al navigatore corrente.
     * <p/>
     * L'azione può essere aggiunta: <ul>
     * <li> Alla toolbar della lista (in coda) </li>
     * <li> Alla toolbar della scheda (in coda) </li>
     * <li> Ad un menu (posizione specificata nell'azione stessa) </li>
     * <ul/>
     * Metodo invocato dal ciclo inizializza <br>
     * Di default seleziona il navigatore corrente e la lista <br>
     *
     * @param azione completa di icona, voce ed help
     */
    protected void addAzione(Azione azione) {
        /* invoca il metodo sovrascritto (quasi) della classe */
        this.addAzione(azione, this.getNavigatoreCorrente());
    }


    /**
     * Crea un'azione specifica e la aggiunge alla toolbar.
     * <p/>
     * L'azione viene aggiunta al navigatore corrente <br>
     * L'azione viene aggiunta alla toolbar della lista <br>
     * Metodo invocato dal ciclo inizializza <br>
     * <p/>
     * Crea un'istanza di BottoneAzione <br>
     * Recupera l'icona <br>
     * Recupera la toolbar del portale Lista del navigatore corrente <br>
     * Aggiunge bottone e icona alla toolbar <br>
     *
     * @param path completo dell'immagine da usare
     * @param metodo nome del metodo nella classe modulo specifica
     * @param help string di help (tooltiptext)
     */
    protected void addAzioneBar(String path, String metodo, String help) {
        /* invoca il metodo sovrascritto della classe */
        Lib.Risorse.addAzione(this, path, metodo, help, true);
    }


    /**
     * Crea un'azione specifica e la aggiunge al menu archivio.
     * <p/>
     * L'azione viene aggiunta al navigatore corrente <br>
     * L'azione viene aggiunta al menu Archivio del portale <br>
     * Metodo invocato dal ciclo inizializza <br>
     * <p/>
     * Crea un'istanza di BottoneAzione <br>
     * Recupera l'icona <br>
     * Recupera il menu Archivio del navigatore corrente <br>
     * Aggiunge bottone e icona al menu <br>
     *
     * @param path completo dell'immagine da usare
     * @param metodo nome del metodo nella classe modulo specifica
     * @param help string di help (tooltiptext)
     */
    protected void addAzioneMenu(String path, String metodo, String help) {
        /* invoca il metodo sovrascritto della classe */
        Lib.Risorse.addAzione(this, path, metodo, help, false);
    }


    /**
     * Crea un icona da associare al modulo.
     * <p/>
     * Regola l'icona della'azione AzAvviaModulo associata <br>
     *
     * @param icona nome dell'immagine da usare
     * @param tipo costante per il tipo di icona
     * (Azione.ICONA_PICCOLA, Azione.ICONA_MEDIA, Azione.ICONA_GRANDE)
     * @param specifica true se va presa dalle icone specifiche del modulo,
     * false se va presa dalle icone di base
     */
    protected void setIcona(String icona, String tipo, boolean specifica) {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        AzAvviaModulo azione;

        try { // prova ad eseguire il codice
            azione = this.getAzioneModulo();
            continua = azione != null;

            if (continua) {
                if (specifica) {
                    azione.putValue(tipo, Lib.Risorse.getIcona(this, icona));
                } else {
                    azione.putValue(tipo, Lib.Risorse.getIconaBase(icona));
                }// fine del blocco if-else

            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Crea un icona da associare al modulo.
     * <p/>
     * Regola l'icona della'azione AzAvviaModulo associata <br>
     * Di defaul usa le icone nella cartella specifica del modulo/progetto <br>
     *
     * @param icona nome dell'immagine da usare
     */
    protected void setIcona(String icona, String tipo) {
        /* invoca il metodo sovrascritto della superclasse */
        this.setIcona(icona, Azione.ICONA_MEDIA, true);
    }


    /**
     * Crea un icona da associare al modulo.
     * <p/>
     * Regola l'icona della'azione AzAvviaModulo associata <br>
     * Di default usa la dimensione media dell'icona <br>
     * Di default usa le icone nella cartella specifica del modulo/progetto <br>
     *
     * @param icona nome dell'immagine da usare
     */
    protected void setIcona(String icona) {
        /* invoca il metodo sovrascritto della superclasse */
        this.setIcona(icona, Azione.ICONA_MEDIA);
    }


    /**
     * Regola la selezione iniziale di un popup.
     * <p/>
     * Esegue anche la selezione del filtro <br>
     * <p/>
     *
     * @param pop popup (Enumeration in interfaccia) da selezionare
     * @param pos posizione da selezionare (inizia da 1)
     */
    public void setPopup(Popup pop, int pos) {
        /* test per simulare click sul popup */
        boolean continua;
        Lista lista;
        ListaStatusBar status = null;
        ArrayList<JComboBox> filtriCombo = null;
        String txtPop = "";
        String txtCombo;
        int oldPos;

        try { // prova ad eseguire il codice
            lista = this.getLista();
            continua = (lista != null);

            if (continua) {
                status = lista.getStatusBar();
                continua = (status != null);
            }// fine del blocco if

            if (continua) {
                filtriCombo = status.getComboFiltri();
                continua = (filtriCombo != null);
            }// fine del blocco if

            if (continua) {
                if (pos > 0) {
                    pos--;
                }// fine del blocco if
                txtPop = pop.get();
                continua = (Lib.Testo.isValida(txtPop));
            }// fine del blocco if

            if (continua) {
                for (JComboBox combo : filtriCombo) {
                    txtCombo = combo.getToolTipText();
                    if (Lib.Testo.isVuota(txtCombo)) {
                        continue;
                    }// fine del blocco if

                    if (txtCombo.equals(txtPop)) {
                        oldPos = combo.getSelectedIndex();

                        /* se il popup è già posizionato, non si genera un evento */
                        if (oldPos == pos) {
                            if (oldPos == 0) {
                                if (combo.getItemCount() > 1) {
                                    combo.setSelectedIndex(1);
                                }// fine del blocco if
                            } else {
                                combo.setSelectedIndex(0);
                            }// fine del blocco if-else
                        }// fine del blocco if

                        combo.setSelectedIndex(pos);
                        break;
                    }// fine del blocco if
                } // fine del ciclo for-each
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Regola la selezione iniziale di un popup per il valore nullo del filtro.
     * <p/>
     * Esegue anche la selezione del filtro <br>
     * <p/>
     *
     * @param pop popup (Enumeration in interfaccia) da selezionare
     */
    public void setPopup(Popup pop) {
        /* test per simulare click sul popup */
        boolean continua;
        Lista lista;
        ListaStatusBar status = null;
        ArrayList<JComboBox> filtriCombo = null;
        String txtPop = "";
        String txtCombo;
        int pos;
        int oldPos;

        try { // prova ad eseguire il codice
            lista = this.getLista();
            continua = (lista != null);

            if (continua) {
                status = lista.getStatusBar();
                continua = (status != null);
            }// fine del blocco if

            if (continua) {
                filtriCombo = status.getComboFiltri();
                continua = (filtriCombo != null);
            }// fine del blocco if

            if (continua) {
                txtPop = pop.get();
                continua = (Lib.Testo.isValida(txtPop));
            }// fine del blocco if

            if (continua) {
                for (JComboBox combo : filtriCombo) {
                    txtCombo = combo.getToolTipText();
                    if (Lib.Testo.isVuota(txtCombo)) {
                        continue;
                    }// fine del blocco if

                    if (txtCombo.equals(txtPop)) {
                        pos = combo.getItemCount() - 1;
                        oldPos = combo.getSelectedIndex();

                        /* se il popup è già posizionato, non si genera un evento */
                        if (oldPos == pos) {
                            if (oldPos == 0) {
                                if (combo.getItemCount() > 1) {
                                    combo.setSelectedIndex(1);
                                }// fine del blocco if
                            } else {
                                combo.setSelectedIndex(0);
                            }// fine del blocco if-else
                        }// fine del blocco if

                        combo.setSelectedIndex(pos);
                        break;
                    }// fine del blocco if
                } // fine del ciclo for-each
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Prima istallazione.
     * <p/>
     * Metodo invocato da Progetto.lancia() <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     * <p/>
     * Esegue il metodo iniziale di istallazione per ogni modulo <br>
     */
    public void setup() {
    }


    /**
     * Restituisce in runtime l'istanza presente in progetto.
     * <p/>
     *
     * @return modulo presente in Progetto
     */
    protected static Modulo get(String nome) {
        /* variabili e costanti locali di lavoro */
        Modulo mod = null;
        boolean continua;

        try { // prova ad eseguire il codice
            continua = Lib.Testo.isValida(nome);

            if (continua) {
                mod = Progetto.getModulo(nome);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return mod;
    }


    /**
     * Aggiunge un listener.
     * <p/>
     * Aggiunge uno specifico listener <br>
     * L'oggetto mantiene una propria collezione di listener <br>
     *
     * @see javax.swing.event.EventListenerList
     */
    public void addListener(BaseListener listener) {
        /* variabili e costanti locali di lavoro */
        EventListenerList listaListener;

        try {    // prova ad eseguire il codice
            listaListener = this.getListaListener();
            Evento.addLocale(listaListener, listener);
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
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
     * @deprecated
     */
    public void fire() {
    }


    /**
     * Avvisa tutti i listener.
     * <p/>
     * Avvisa tutti i listener che si sono registrati
     * per questo tipo di evento <br>
     * L'evento viene creato al momento <br>
     * È responsabilità della classe invocare questo metodo quando
     * si creano le condizioni per generare l'evento <br>
     *
     * @see javax.swing.event.EventListenerList
     */
    public void fire(Eventi unEvento) {
        /* variabili e costanti locali di lavoro */
        EventListenerList listaListener;

        try {    // prova ad eseguire il codice
            listaListener = this.getListaListener();
            Lib.Eventi.fire(listaListener, unEvento, Modulo.class, this);
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Classe interna Enumerazione.
     * <p/>
     * Eventi che vengono lanciati dal modulo <br>
     * Per ogni evento: <ul>
     * <li> classe interfaccia </li>
     * <li> classe evento </li>
     * <li> classe azione </li>
     * <li> metodo azione </li>
     * </ul>
     */
    public enum Evento implements Eventi {

        mostra(ModVisibileLis.class, ModVisibileEve.class, ModVisibileAz.class, "visibileAz"),
        nascondi(ModNascostoLis.class, ModNascostoEve.class, ModNascostoAz.class, "nascostoAz");

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
         * @param evento classe
         * @param azione classe
         * @param metodo nome metodo azione
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
         * @param lista degli eventi a cui aggiungersi
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
         * @param lista degli eventi da cui rimuoverlo
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
         * @param lista degli eventi a cui aggiungersi
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
         * @param lista degli eventi da cui rimuoverlo
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
     * Dialogo di selezione di un record.
     * </p>
     */
    private final class DialogoSelezionaRecord extends DialogoAnnullaConferma {

        private Campo campo;


        /**
         * Costruttore base con parametri.
         * <p/>
         *
         * @param modulo di riferimento
         * @param messaggio da visualizzare
         * @param campo combo di selezione
         */
        public DialogoSelezionaRecord(Modulo modulo, String messaggio, Campo campo) {
            /* rimanda al costruttore della superclasse */
            super(modulo);

            /* regolazioni iniziali di riferimenti e variabili */
            try {

                /* regola le variabili di istanza coi parametri */
                this.setCampo(campo);
                this.setMessaggio(messaggio);

                this.inizia();
            } catch (Exception unErrore) {
                Errore.crea(unErrore);
            } /* fine del blocco try-catch */
        } /* fine del metodo costruttore completo */


        /**
         * Regolazioni immediate di riferimenti e variabili. <br>
         * Metodo chiamato direttamente dal costruttore <br>
         *
         * @throws Exception unaEccezione
         */
        private void inizia() throws Exception {
            try { // prova ad eseguire il codice
                this.setTitolo("Selettore");
                this.addCampo(this.getCampo());
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

        }


        /**
         * Determina se il dialogo e' confermabile.
         * <p/>
         * E' confermabile se il campo non è vuoto
         *
         * @return true se confermabile / registrabile
         */
        public boolean isConfermabile() {
            /* variabili e costanti locali di lavoro */
            boolean confermabile = false;
            Campo campo;

            try { // prova ad eseguire il codice
                confermabile = super.isConfermabile();
                if (confermabile) {
                    campo = this.getCampo();
                    confermabile = !campo.isVuoto();
                }// fine del blocco if

            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

            /* valore di ritorno */
            return confermabile;
        }


        private Campo getCampo() {
            return campo;
        }


        private void setCampo(Campo campo) {
            this.campo = campo;
        }


    } // fine della classe 'interna'


} // fine della classe