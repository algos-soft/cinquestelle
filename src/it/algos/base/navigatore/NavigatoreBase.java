/**
 * Title:     NavigatoreBase
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      28-gen-2004
 */
package it.algos.base.navigatore;

import com.wildcrest.j2printerworks.J2TablePrinter;

import it.algos.base.azione.Azione;
import it.algos.base.campo.base.Campo;
import it.algos.base.campo.dati.CDElenco;
import it.algos.base.campo.inizializzatore.Init;
import it.algos.base.campo.video.CVComboLista;
import it.algos.base.combo.ComboModello;
import it.algos.base.database.Db;
import it.algos.base.database.connessione.Connessione;
import it.algos.base.database.connessione.Connettore;
import it.algos.base.database.dati.Dati;
import it.algos.base.database.util.MetodiQuery;
import it.algos.base.database.util.Operatore;
import it.algos.base.dialogo.Dialogo;
import it.algos.base.dialogo.DialogoFactory;
import it.algos.base.errore.Errore;
import it.algos.base.evento.BaseListener;
import it.algos.base.evento.Eventi;
import it.algos.base.evento.form.FormModificatoAz;
import it.algos.base.evento.form.FormModificatoEve;
import it.algos.base.evento.lista.ListaModCellaAz;
import it.algos.base.evento.lista.ListaModCellaEve;
import it.algos.base.evento.navigatore.NavStatoAz;
import it.algos.base.evento.navigatore.NavStatoEve;
import it.algos.base.evento.navigatore.NavStatoLis;
import it.algos.base.evento.portale.PortaleAddRecAz;
import it.algos.base.evento.portale.PortaleAddRecEve;
import it.algos.base.evento.portale.PortaleCursoreAz;
import it.algos.base.evento.portale.PortaleCursoreEve;
import it.algos.base.evento.portale.PortaleDuplicaRecAz;
import it.algos.base.evento.portale.PortaleDuplicaRecEve;
import it.algos.base.evento.portale.PortaleEliminaRecAz;
import it.algos.base.evento.portale.PortaleEliminaRecEve;
import it.algos.base.evento.portale.PortaleLevaRecAz;
import it.algos.base.evento.portale.PortaleLevaRecEve;
import it.algos.base.evento.portale.PortaleListaSelAz;
import it.algos.base.evento.portale.PortaleListaSelEve;
import it.algos.base.evento.portale.PortaleModificaRecAz;
import it.algos.base.evento.portale.PortaleModificaRecEve;
import it.algos.base.evento.portale.PortaleNuovoRecAz;
import it.algos.base.evento.portale.PortaleNuovoRecEve;
import it.algos.base.finestra.Finestra;
import it.algos.base.help.Help;
import it.algos.base.help.HelpFin;
import it.algos.base.importExport.DialogoExport;
import it.algos.base.importExport.Importa;
import it.algos.base.libreria.Lib;
import it.algos.base.lista.Lista;
import it.algos.base.lista.ListaDefault;
import it.algos.base.lista.TavolaModello;
import it.algos.base.messaggio.MessaggioAvviso;
import it.algos.base.messaggio.MessaggioDialogo;
import it.algos.base.modulo.Modulo;
import it.algos.base.modulo.ModuloBase;
import it.algos.base.navigatore.info.InfoLista;
import it.algos.base.navigatore.info.InfoNavigatore;
import it.algos.base.navigatore.info.InfoScheda;
import it.algos.base.portale.Portale;
import it.algos.base.portale.PortaleBase;
import it.algos.base.portale.PortaleDialogo;
import it.algos.base.portale.PortaleLista;
import it.algos.base.portale.PortaleNavigatore;
import it.algos.base.portale.PortaleScheda;
import it.algos.base.pref.Pref;
import it.algos.base.progetto.Progetto;
import it.algos.base.progressbar.ProgressBar;
import it.algos.base.query.Query;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.query.filtro.FiltroFactory;
import it.algos.base.query.selezione.QuerySelezione;
import it.algos.base.relazione.RelazioneManager;
import it.algos.base.ricerca.Ricerca;
import it.algos.base.ricerca.RicercaBase;
import it.algos.base.ricerca.RicercaDefault;
import it.algos.base.scheda.Scheda;
import it.algos.base.statusbar.StatusBar;
import it.algos.base.tavola.Tavola;
import it.algos.base.toolbar.ToolBar;
import it.algos.base.vista.Vista;
import it.algos.base.wrapper.CampoValore;
import it.algos.base.wrapper.SetValori;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.event.EventListenerList;

import java.awt.Dimension;
import java.awt.Window;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Controllo degli Eventi, della Business Logic e della GUI.
 * </p>
 * Questa classe: <ul>
 * <li> Contiene logicamente Liste, Schede e Dialoghi, che sono graficamente
 * inseriti nei Portali</li>
 * <li> Raccoglie i comandi che provengono dalla gestione degli eventi
 * mantenuta nel package Gestore </li>
 * <li> Gestisce direttamente parte della Business Logic e parte la delega a
 * LogicaNavigatore </li>
 * <li> Delega la rappresentazione grafica GUI alle classi del package Portale </li>
 * <li> Coordina i vari elementi; dialoga col DB; dialoga con altri moduli </li>
 * </ul>
 *
 * @author Alberto Colombo, Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 28-gen-2004 ore 15.04.05
 */
public abstract class NavigatoreBase implements Navigatore, Connettore {

    /**
     * nome della vista di default della Lista
     */
    public static final String NOME_VISTA_DEFAULT = Modulo.VISTA_BASE_DEFAULT;

    /**
     * nome del set di default per la Scheda
     */
    public static final String NOME_SET_DEFAULT = Modulo.SET_BASE_DEFAULT;

    /**
     * flag - controlla se il navigatore è inizializzato
     */
    private boolean inizializzato = false;

    /**
     * Titolo della finestra che contiene il Navigatore
     */
    private String titoloFinestra = "";

    /**
     * flag - orientamento generale del navigatore
     */
    protected boolean isOrizzontale = false;

    /**
     * portale di questo navigatore
     */
    protected PortaleNavigatore portaleNavigatore = null;

    /**
     * portale della lista
     */
    protected PortaleLista portaleLista = null;

    /**
     * portale della scheda
     */
    protected PortaleScheda portaleScheda = null;

    /**
     * portale del dialogo
     */
    protected PortaleDialogo portaleDialogo = null;

    /**
     * nome del navigatore (usato per il titolo delle finestre)
     */
    private String nomeNavigatore = "";

    /**
     * riferimento al modulo a cui appartiene questo navigatore
     */
    private Modulo modulo = null;

    /**
     * flag - indica se il Navigatore effettua le operazioni
     * standard (nuovo, modifica, elimina) sotto transazione
     */
    private boolean usaTransazioni = false;

    /**
     * connessione da utilizzare per l'accesso al database<br>
     * se non specificato usa quella del modulo
     */
    private Connessione connessione = null;

    /**
     * nome chiave interno del navigatore, per recuperarlo nella collezione
     */
    private String nomeChiave = "";

    /**
     * modalità di funzionamento
     */
    private int modo = 0;

    /**
     * nome della vista da utilizzare per costruire la Lista
     */
    private String nomeVista = "";

    /**
     * nomi del set da utilizzare per costruire la Scheda standard
     * eventuali schede non standard, verranno sostituite come oggetti
     * dal modulo specifico e non come nomi di set
     */
    private String nomeSet = "";

    /**
     * gestore della business logic del navigatore
     */
    protected LogicaNavigatore logica = null;

    /**
     * Istanza di classe per raccogliere tutti i metodi delle query
     */
    private MetodiQuery query = null;


    /**
     * flag per i nuovi record - indica che il naviogatore e' in fase di editing di
     * un novo record
     */
    private boolean isNuovoRecord = false;

    /**
     * flag - comportamento dopo la chiusura della scheda
     * con il pulsante di registrazione
     * se true, dismette la scheda dopo la chiusura
     */
    private boolean isDismettiSchedaDopoRegistrazione = true;

    /**
     * flag - chiede conferma per la registrazione quando si sposta
     * il record in scheda in presenza di modifiche.
     * false - registra automaticamente
     * true - chiede conferma per la registrazione
     */
    private boolean confermaRegistrazioneSpostamento = false;

    /**
     * Campo del modulo di questo Navigatore che e' linkato
     * al campo chiave dell'eventuale oggetto che pilota la
     * lista di questo Navigatore.
     * <p/>
     * Un Navigatore puo' essere:
     * - indipendente: in questo caso non ha pilota
     * - contenuto in un campo: in questo caso e' pilotato dal campo
     * - contenuto in un Portale di un altro Navigatore: in questo caso
     * e' pilotato dalla Lista del Navigatore che lo contiene.<br>
     * Questo riferimento e' regolato una solo volta in fase di inizializzazione.
     */
    Campo campoLink = null;

    /**
     * Elenco di valori int che pilotano la lista di questo navigatore.
     * <p/>
     * Significativo solo se questo Navigatore e' pilotato.
     * La lista di questo navigatore mostrera' tutti i record
     * che corrispondono ai valori di link specificati.<br>
     * Se e' pilotato da un campo, e' sempre un solo valore.<br>
     * Se e' pilotato da un Navigatore, possono essere piu' valori.<br>
     * (se seleziono piu' righe nella lista del navigatore pilota,
     * vedo tutte le corrispondenti righe nella lista del Navigatore pilotato).<br>
     * Questo elenco e' regolato in qualsiasi momento dall'oggetto pilota
     * e viene utilizzato ad ogni avvio della lista di questo navigatore.
     */
    int[] valoriPilota = null;

    /**
     * Eventuale Navigatore che pilota questo Navigatore.
     */
    Navigatore navPilota = null;

    /**
     * Eventuale Navigatore pilotato da questo Navigatore.
     */
    private Navigatore navPilotato = null;

    /**
     * flag - permette di pilotare questo navigatore con piu' di una riga.
     * <p/>
     * Controlla il comportamento della lista pilotata quando
     * esiste piu' di un valore pilota.<br>
     * Se attivato, vengono caricate tutte le corrispondenti righe
     * nella lista pilotata.<br>
     * Se disattivato, non viene caricata nessuna riga.<br>
     */
    private boolean isPilotatoDaRigheMultiple = false;

    /**
     * riferimento al campo che contiene e pilota questo navigatore
     */
    private Campo campoPilota = null;

//    /**
//     * Flag - indica se il Navigatore e' stato modificato.
//     * <p/>
//     * Disattivato ad ogni avvio.<br>
//     * Attivato non appena si aggiunge, modifica o elimina un record.<br>
//     */
//    private boolean isModificato = false;

    /**
     * Flag - indica se il Navigatore e' aperto.
     * <p/>
     * Un Navigatore e' aperto se la sua finestra e' visibile sullo schermo
     * Attivato ad ogni avvio.<br>
     * Disattivato alla chiusura del navigatore.<br>
     */
    private boolean aperto = false;

    /**
     * Dialogo di ricerca di default
     */
    private RicercaBase ricercaDefault = null;

    /**
     * Dialogo di ricerca corrente
     */
    private RicercaBase ricerca = null;

    /**
     * A list of event listeners for this component.
     */
    private EventListenerList listaListener;

    /**
     * riferimento all'azione nuovo record
     */
    private AzPortaleNuovoRec azionePortaleNuovoRec = null;

    /**
     * riferimento all'azione aggiungi record
     */
    private AzPortaleAddRec azionePortaleAddRec = null;

    /**
     * riferimento all'azione modifica record
     */
    private AzPortaleModificaRec azionePortaleModificaRec = null;

    /**
     * riferimento all'azione elimina record
     */
    private AzPortaleEliminaRec azionePortaleEliminaRec = null;

    /**
     * riferimento all'azione rimuovi record
     */
    private AzPortaleLevaRec azionePortaleLevaRec = null;


    /**
     * riferimento all'azione per la duplicazione di record
     */
    private AzPortaleDuplicaRec azionePortaleDuplicaRec = null;

    /**
     * riferimento all'azione per la modifica di selezione nella lista
     */
    private AzPortaleListaSel azionePortaleListaSel = null;

    /**
     * riferimento all'azione per la gestione del cursore nella lista
     */
    private AzPortaleListaCursore azionePortaleCursore = null;

    /**
     * flag di modificabilità del Navigatore
     */
    private boolean modificabile = false;


    /**
     * Costruttore base senza parametri. <br>
     * Indispensabile anche se non viene utilizzato
     * (anche solo per compilazione in sviluppo) <br>
     * Rimanda al costruttore completo <br>
     * Utilizza eventuali valori di default <br>
     * Non uso il modificatore <i>public<i/> per <i>nascondere</i> la
     * classe all'esterno del package <br>
     */
    NavigatoreBase() {
        /* rimanda al costruttore di questa classe */
        super();
    }// fine del metodo costruttore base


    /**
     * Costruttore completo con parametri. <br>
     *
     * @param unModulo modulo di riferimento
     */
    public NavigatoreBase(Modulo unModulo) {
        /* rimanda al costruttore della superclasse */
        super();

        this.setModulo(unModulo);

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo costruttore completo


    /**
     * Regolazioni immediate di riferimenti e variabili.
     * </p>
     * Metodo chiamato direttamente dal costruttore <br>
     * Regola alcuni parametri di default <br>
     * Crea un PortaleNavigatore che esiste sempre <br>
     * Nelle sottoclassi deciderà quali portali inserire nel
     * PortaleNavigatore e se dotarlo di Finestra o meno <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        /* variabili e costanti locali di lavoro */
        Modulo modulo;

        try { // prova ad eseguire il codice

            /* recupera il modulo se esistente */
            modulo = this.getModulo();

            /* regola il navigatore come modificabile*/
            this.setModificabile(true);

            /* crea una logica per questo Navigatore */
            this.setLogica(new LogicaNavigatore(this));

            /* crea un'istanza dell'oggetto che raccoglie i metodi delle query */
            this.setQuery(new MetodiQuery(this));

            /* regola la modalità di funzionamento standard */
            this.setModo(MODO_DEFAULT);

            /* nome di default preso dal Modulo */
            if (modulo != null) {
                this.setNomeChiave(modulo.getNomeChiave());
            }// fine del blocco if

            /* la Vista per la lista e' quella di default del modulo */
            this.setNomeVista(NOME_VISTA_DEFAULT);

            /* il set per la scheda e' quello di default del modulo */
            this.setNomeSet(NOME_SET_DEFAULT);

            /* l'orientamento di default e' orizzontale */
            this.setOrizzontale(true);

            /* regola il flag di uso pannello unico in base alle preferenze */
            this.setUsaPannelloUnico(this.isPrefPannelloUnico());

            /* crea sempre un PortaleNavigatore */
            this.creaPortaleNavigatore();

            /* di default usa una finestra di tipo normale */
            this.setUsaFinestra(true, false);

            /* crea l'array dei valori pilota */
            this.setValoriPilota(new int[0]);

            /* crea e registra la lista dei listeners */
            this.setListaListener(new EventListenerList());

            /* regola l'utilizzo delle transazioni dal default del Modulo */
            if (modulo != null) {
                this.setUsaTransazioni(modulo.isUsaTransazioni());
            }// fine del blocco if

            this.setAggiornamentoTotaliContinuo(true);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }/* fine del metodo inizia */


    /**
     * Inizializzazione dell'oggetto.
     * <p/>
     * Regola le caratteristiche dinamiche in base alla impostazione
     * corrente delle caratteristiche statiche.<br>
     * Viene chiamato dall'inizializzazione del Modulo.<br>
     */
    public void inizializza() {
        /* variabili e costanti locali di lavoro */
        boolean flag;

        try { // prova ad eseguire il codice

            /* regola il titolo della eventuale finestra */
            this.regolaTitolo();

            /* se l'uso delle transazioni è attivato, controlla
             * che l'ambiente possa gestire le transazioni
             * Se non è possibile, da' un messaggio e disattiva
             * l'utilizzo delle transazioni */
            this.checkTransazioni();

            /* regola la connessione da utilizzare per l'accesso al database */
            this.regolaConnessione();

            /*
             * Recupera la preferenza di comportamento di registrazione
             * spostando il record in scheda in presenza di modifiche.
             */
            flag = Pref.GUI.conferma.is();
            this.setConfermaRegistrazioneSpostamento(flag);

            /* inizializzazione dei portali */
            this.inizializzaPortali();

            /* aggiunge i listener a tutte le schede */
            this.aggiungeListenerSchede();

            /* inizializzazione della logica del navigatore */
            if (this.getLogica() != null) {
                this.getLogica().inizializza();
            }// fine del blocco if


            this.regolaAzione();

            /* inizialmente la progress bar non e' visibile */
            this.setProgressBarVisibile(false);

            /* contrassegna come inizializzato */
            this.setInizializzato(true);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }// fine del metodo inizializza


    /**
     * Regola il titolo della finestra.
     * <p/>
     * Se non è stato assegnato un titolo usa quello del Modulo
     */
    protected void regolaTitolo() {
        /* variabili e costanti locali di lavoro */
        String titolo;
        Modulo modulo;

        try {    // prova ad eseguire il codice
            titolo = this.getTitoloFinestra();
            if (Lib.Testo.isVuota(titolo)) {
                modulo = this.getModulo();
                if (modulo != null) {
                    this.setTitoloFinestra(modulo.getTitoloFinestra());
                }// fine del blocco if
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Controlla l'attivabiità delle transazioni.
     * <p/>
     * Se l'uso delle transazioni è attivato, controlla che
     * l'ambiente le supporti.
     * Se non le supporta, da' un messaggio e disattiva l'uso
     * delle transazioni.
     */
    protected void checkTransazioni() {
        /* variabili e costanti locali di lavoro */
        boolean riuscito = true;
        Modulo modulo;
        Campo campo;
        String testo;
        String motivo = "";
        Init init;

        try {    // prova ad eseguire il codice
            if (this.isUsaTransazioni()) {

                /* nel Progetto deve esistere il modulo Contatori */
                modulo = Progetto.getModuloContatore();
                if (modulo == null) {
                    riuscito = false;
                    motivo += "- non esiste il modulo Contatori in questo progetto";
                }// fine del blocco if

                /* il campo chiave del modulo deve avere un inizializzatore
                 * e questo deve supportare le transazioni */
                campo = this.getModulo().getCampoChiave();
                init = campo.getInit();
                if (init == null) {
                    riuscito = false;
                    motivo += "- il campo chiave non ha un Inizializzatore";
                } else {
                    if (!init.isSupportaTransazioni()) {
                        riuscito = false;
                        motivo +=
                                "\n- l'inizializzatore del campo chiave non  " +
                                        "supporta le transazioni";
                    }// fine del blocco if
                }// fine del blocco if-else

                if (!riuscito) {
                    testo = "Modulo: " + this.getModulo().getNomeChiave() + "\n";
                    testo += "Navigatore: " + this.getNomeChiave() + "\n";
                    testo += "Impossibile attivare l'uso delle transazioni\n";
                    testo += motivo;
                    testo += "\n";
                    testo += "Le transazioni verranno disattivate per questo Navigatore";
                    new MessaggioAvviso(testo);
                    this.setUsaTransazioni(false);
                }// fine del blocco if

            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * regola la connessione da utilizzare per l'accesso al database
     * <p/>
     * Invocato da Inizializza.<br>
     * Se il Navigatore usa le transazioni, deve creare una
     * connessione propria.<br>
     * Se non usa le transazioni, non crea alcuna connessione; in
     * tal modo usa la connessione del Modulo.
     */
    protected void regolaConnessione() {
        /* variabili e costanti locali di lavoro */
        Connessione conn = null;
        Db db;
        boolean continua;


        try {    // prova ad eseguire il codice
            if (this.isUsaTransazioni()) {

                /* recupera il database del modulo */
                db = this.getModulo().getDb();
                continua = (db != null);

                /* crea una nuova connessione */
                if (continua) {
                    conn = db.creaConnessione();
                    continua = (conn != null);
                }// fine del blocco if

                /* registra la connessione */
                if (continua) {
                    this.setConnessione(conn);
                }// fine del blocco if

            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Inizializza i portali del Navigatore.
     * <p/>
     */
    protected void inizializzaPortali() {
        try { // prova ad eseguire il codice

            if (this.getPortaleLista() != null) {
                this.getPortaleLista().inizializza();
            }// fine del blocco if

            if (this.getPortaleScheda() != null) {
                this.getPortaleScheda().inizializza();
            }// fine del blocco if

            if (this.getPortaleNavigatore() != null) {
                this.getPortaleNavigatore().inizializza();
            }// fine del bl occo if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Aggiunge i listener a tutte le schede.
     * <p/>
     * Il Navigatore si registra presso ogni scheda come interessato a certi eventi.
     */
    protected void aggiungeListenerSchede() {
        /* variabili e costanti locali di lavoro */
        PortaleScheda ps;
        HashMap<String, Scheda> schede;

        try {    // prova ad eseguire il codice
            ps = this.getPortaleScheda();
            if (ps != null) {
                schede = ps.getSchede();
                if (schede != null) {
                    for (Scheda scheda : schede.values()) {
                        scheda.addListener(new AzFormModificato());
                    }
                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Regolazioni di ri-avvio.
     * <p/>
     * Metodo chiamato da altre classi ogni volta che questo oggetto deve
     * <i>ripartire</i>, per essere sicuri che sia <i>pulito</i> <br>
     * Viene eseguito tutte le volte che necessita <br>
     * <p/>
     * Avvia ogni componente del navigatore, se presente <br>
     * Avvia il Gestore <br>
     */
    public void avvia() {
        /* variabili e costanti locali di lavoro */
        Lista lista;
        Connessione conn;

        try { // prova ad eseguire il codice

            /* se non è inizializzato lo inizializza ora */
            if (!this.isInizializzato()) {
                this.inizializza();
            }// fine del blocco if

            /* apre la connessione se non già aperta */
            conn = this.getConnessione();
            if (conn != null) {
                if (!conn.isOpen()) {
                    conn.open();
                }// fine del blocco if
            }// fine del blocco if

            /* avvia il portale lista */
            if (this.getPortaleLista() != null) {
                this.getPortaleLista().avvia();
            }// fine del blocco if

            /* avvia il portale scheda */
            if (this.getPortaleScheda() != null) {
                this.getPortaleScheda().avvia();
            }// fine del blocco if

            /* avvia il portale navigatore */
            if (this.getPortaleNavigatore() != null) {
                this.getPortaleNavigatore().avvia();
            }// fine del blocco if

            /* toglie l'eventuale filtro corrente alla lista */
            lista = this.getLista();
            if (lista != null) {
                lista.setFiltroCorrente(null);
            }// fine del blocco if

            /* all'avvio forza un evento di selezione modificata
             * per aggiornare la lista */
            this.selezioneModificata();

            /* se usa pannello unico, all'avvio visualizza sempre
             * il componente A */
            if (this.isUsaPannelloUnico()) {
                this.visualizzaComponenteA();
            }// fine del blocco if

            /* sincronizza il navigatore */
            this.sincronizza();

            /* genera un evento del modulo */
            this.getModulo().fire(ModuloBase.Evento.mostra);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Sincronizza il Navigatore.
     * <p/>
     * Controlla lo stato di tutti i suoi componenti <br>
     * Elabora la sua business logic <br>
     * Regola la GUI di conseguenza <br>
     */
    public void sincronizza() {
        /* variabili e costanti locali di lavoro */
        Object obj;
        InfoLista infoLista;
        InfoScheda infoScheda;
        InfoNavigatore infoNav;

        try {    // prova ad eseguire il codice

            /* sincronizza il portale lista */
            if (this.getPortaleLista() != null) {
                obj = this.getPortaleLista().getInfoStato();
                if ((obj != null) && (obj instanceof InfoLista)) {
                    infoLista = (InfoLista)obj;
                    infoLista.avvia();
                    infoLista = this.getInfoLista(infoLista);
                    this.getPortaleLista().setInfoStato(infoLista);
                    this.getPortaleLista().sincronizza();
                }// fine del blocco if
            }// fine del blocco if

            /* sincronizza il portale scheda */
            if (this.getPortaleScheda() != null) {
                obj = this.getPortaleScheda().getInfoStato();
                if ((obj != null) && (obj instanceof InfoScheda)) {
                    infoScheda = (InfoScheda)obj;
                    infoScheda.avvia();
                    infoScheda = this.getInfoScheda(infoScheda);
                    this.getPortaleScheda().setInfoStato(infoScheda);
                    this.getPortaleScheda().sincronizza();
                }// fine del blocco if
            }// fine del blocco if

            /* sincronizza il portale navigatore */
            if (this.getPortaleNavigatore() != null) {
                obj = this.getPortaleNavigatore().getInfoStato();
                if ((obj != null) && (obj instanceof InfoNavigatore)) {
                    infoNav = (InfoNavigatore)obj;
                    infoNav.avvia();
                    infoNav = this.getInfoNavigatore(infoNav);
                    this.getPortaleNavigatore().setInfoStato(infoNav);
                    this.getPortaleNavigatore().sincronizza();
                }// fine del blocco if
            }// fine del blocco if


        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

    }


    /**
     * Crea il portale Navigatore di questo navigatore.<p>
     * <p/>
     * Crea l'istanza <br>
     * Un portale navigatore esiste sempre; di solito con finestra <br>
     * Il flag per l'esistenza o meno della finestra, viene regolato
     * dalla sottoclasse <br>
     * Regola la variabile d'istanza <br>
     *
     * @return true se il portale è stato creato correttamente
     */
    protected boolean creaPortaleNavigatore() {
        /* variabili e costanti locali di lavoro */
        boolean riuscito = true;
        PortaleNavigatore unPortale = null;

        try { // prova ad eseguire il codice
            /* crea l'istanza */
            if (riuscito) {
                unPortale = new PortaleNavigatore(this);
            }// fine del blocco if

            /* regola la variabile di istanza */
            this.setPortaleNavigatore(unPortale);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return riuscito;
    }


    /**
     * Crea un PortaleLista per questo navigatore. <p/>
     * <p/>
     * Chiamato dal ciclo Inizia.
     * Delega alla classe Factory la creazione standard <br>
     * La Lista non viene inizializzata <br>
     * Aggiunge il Portale come componente al Navigatore
     * <p/>
     * Associa alla lista la vista mantenuta da questo navigatore <br>
     * Regola la variabile d'istanza Portale del Navigatore <br>
     * Regola il filtro iniziale <br>
     *
     * @return il portale appena creato
     */
    protected PortaleLista creaPortaleLista() {
        /* variabili e costanti locali di lavoro */
        PortaleLista unPortale = null;
        Lista lista;

        try { // prova ad eseguire il codice

            /* crea un'istanza della classe da ritornare */
            unPortale = new PortaleLista(this);

            /* crea un'istanza della Lista */
            lista = this.creaLista();

            /* assegna alla lista il riferimento a questo portale */
            lista.setPortale(unPortale);

            /* regola la variabile d'istanza del portale */
            unPortale.setLista(lista);

            /* regola la variabile d'istanza del navigatore */
            this.setPortaleLista(unPortale);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return unPortale;
    }


    /**
     * Crea il portale scheda di questo navigatore.
     * <p/>
     * Crea un portale scheda <br>
     * Associa alla scheda il set mantenuto da questo navigatore <br>
     * Inizializza il portale <br> Regola le dimensioni <br>
     *
     * @return il portale appena creato
     */
    protected PortaleScheda creaPortaleScheda() {
        /* variabili e costanti locali di lavoro */
        PortaleScheda portale = null;

        try { // prova ad eseguire il codice

            portale = new PortaleScheda(this);
            this.setPortaleScheda(portale);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return portale;
    } // fine del metodo


    /**
     * Crea la lista.
     * <p/>
     * Sovrascritto dalle sottoclassi se vogliono usare una lista diversa
     *
     * @return la lista creata
     */
    protected Lista creaLista() {
        int c = 87;
        return new ListaDefault();
    }





    /**
     * Aggiunge i portali interni al PortaleNavigatore.
     * <p/>
     */
    protected void addPortali(Portale sopra, Portale sotto) {
        /* variabili e costanti locali di lavoro */
        PortaleNavigatore portaleNavigatore;

        try {    // prova ad eseguire il codice
            portaleNavigatore = this.getPortaleNavigatore();
            portaleNavigatore.addPortali(sopra, sotto);
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Regola le azioni.
     * <p/>
     * Metodo invocato dal ciclo inizializza <br>
     * Associa l'azione al navigatore <br>
     */
    protected void regolaAzione() {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        Portale portale;
        BaseListener listener;

        try {    // prova ad eseguire il codice

            /* crea le azioni da associare alla lista */
            this.setAzionePortaleNuovoRec(new AzPortaleNuovoRec());
            this.setAzionePortaleAddRec(new AzPortaleAddRec());
            this.setAzionePortaleModificaRec(new AzPortaleModificaRec());
            this.setAzionePortaleEliminaRec(new AzPortaleEliminaRec());
            this.setAzionePortaleLevaRec(new AzPortaleLevaRec());
            this.setAzionePortaleDuplicaRec(new AzPortaleDuplicaRec());
            this.setAzionePortaleListaSel(new AzPortaleListaSel());
            this.setAzionePortaleCursore(new AzPortaleListaCursore());

            /* recupera il portale */
            portale = this.getPortaleLista();
            continua = (portale != null);

            if (continua) {

                /* recupera ed aggiunge l'azione nuovo record */
                listener = this.getAzionePortaleNuovoRec();
                portale.addListener(listener);

                /* recupera ed aggiunge l'azione aggiungi record */
                listener = this.getAzionePortaleAddRec();
                portale.addListener(listener);

                /* recupera ed aggiunge l'azione modifica record */
                listener = this.getAzionePortaleModificaRec();
                portale.addListener(listener);

                /* recupera ed aggiunge l'azione elimina record */
                listener = this.getAzionePortaleEliminaRec();
                portale.addListener(listener);

                /* recupera ed aggiunge l'azione rimuovi record */
                listener = this.getAzionePortaleLevaRec();
                portale.addListener(listener);

                /* recupera ed aggiunge l'azione duplica record */
                listener = this.getAzionePortaleDuplicaRec();
                portale.addListener(listener);

                /* recupera ed aggiunge l'azione modifica selezione lista */
                listener = this.getAzionePortaleListaSel();
                portale.addListener(listener);

                /* recupera ed aggiunge l'azione modifica selezione lista */
                listener = this.getAzionePortaleCursore();
                portale.addListener(listener);

                getLista().addListener(new AzCellaModificata());
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */
    }


    /**
     * Rende questo navigatore pilotato da un dato Modulo.
     * <p/>
     *
     * @param modulo il modulo che deve pilotare questo navigatore.
     */
    public void pilotaNavigatore(Modulo modulo) {
        /* variabili e costanti locali di lavoro */
        Modulo moduloFiglio;
        Modulo moduloPadre;
        Campo campoLink;

        try {    // prova ad eseguire il codice
            moduloFiglio = this.getModulo();
            moduloPadre = modulo;
            campoLink = RelazioneManager.getCampoLinkDiretto(moduloFiglio, moduloPadre);
            if (campoLink != null) {
                this.setCampoLink(campoLink);
            } else {
                throw new Exception("Non trovata relazione tra i moduli " +
                        moduloFiglio.getNomeChiave() +
                        " e " +
                        moduloPadre.getNomeChiave());
            }// fine del blocco if-else

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Ritorna la lista dei valori da assegnare automaticamente
     * a un un nuovo record creato da un navigatore pilotato.
     * <p/>
     *
     * @return il set di valori da assegnare automaticamente
     */
    private SetValori getValoriNuovoRecordAuto() {
        /* variabili e costanti locali di lavoro */
        SetValori sv = new SetValori(this.getModulo());
        Campo campoLink = null;
        Campo campoOrdine = null;
        Modulo modulo = null;
        int valoreLink = 0;
        int valoreOrdine = 0;
        CampoValore cv;

        try {    // prova ad eseguire il codice

            /* se pilotato, aggiunge il campo link
             * e il campo ordine */
            if (this.isPilotato()) {
                modulo = this.getModulo();
                campoLink = this.getCampoLink();
                campoOrdine = modulo.getCampoOrdine();
                valoreLink = this.getValorePilota();
                valoreOrdine = this.getValoreOrdine();

                /* aggiunge il campo link */
                cv = new CampoValore(campoLink, valoreLink);
                sv.add(cv);

                /* aggiunge il campo ordine */
                cv = new CampoValore(campoOrdine, valoreOrdine);
                sv.add(cv);

            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return sv;
    }


    /**
     * Invocato prima di creare un nuovo record
     * <p/>
     * Metodo sovrascritto dalle sottoclassi.
     * La sottoclasse può modificare il set di valori.
     *
     * @param set di valori per il record da creare
     *
     * @return true se si può procedere
     */
    protected boolean nuovoRecordAnte(SetValori set) {
        return true;
    }


    /**
     * Invocato prima di registrare un record esistente
     * <p/>
     * Metodo sovrascritto dalle sottoclassi.
     * La sottoclasse può modificare il set di valori.
     *
     * @param codice del record che sta per essere registrato
     * @param set di valori per il record da registrare
     *
     * @return true se si può procedere
     */
    protected boolean registraRecordAnte(int codice, SetValori set) {
        return true;
    }


    /**
     * Invocato prima di eliminare un record esistente
     * <p/>
     * Metodo sovrascritto dalle sottoclassi.
     *
     * @param codice del record che sta per essere eliminato
     *
     * @return true se si può procedere
     */
    protected boolean eliminaRecordAnte(int codice) {
        return true;
    }


    /**
     * Ritorna il valore da assegnare al campo ordine di un nuovo record
     * creato da un Navigatore pilotato.
     * <p/>
     * Quando un navigatore pilotato crea un nuovo record, gli assegna
     * il valore del campo Ordine in modo che il record creato
     * cada per ultimo tra quelli filtrati dal Navigatore.
     *
     * @return il valore del campo ordine per il nuovo record
     */
    private int getValoreOrdine() {
        /* variabili e costanti locali di lavoro */
        Modulo modulo = null;
        Campo campoOrdine = null;
        Filtro filtro = null;
        int valSuccessivo = 0;

        try {    // prova ad eseguire il codice

            /* recupera il modulo del navigatore */
            modulo = this.getModulo();

            /* recupera il campo ordine */
            campoOrdine = modulo.getCampoOrdine();

            /* crea un filtro che include tutti i record pilotati
             * escluso il record da regolare */
            filtro = this.creaFiltroPilota();

            /* recupera il valore massimo del campo ordine */
            valSuccessivo = this.query().valoreMassimo(campoOrdine, filtro);

            /* incrementa di 1 il valore ottenuto */
            valSuccessivo++;

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return valSuccessivo;
    }


    /**
     * Azione help.
     * <p/>
     * Apre una finestra di help <br>
     * Metodo invocato dal Gestore Eventi <br>
     * Invoca la classe delegata per la funzionalità specifica <br>
     */
    public void apreHelp() {

        try { // prova ad eseguire il codice
            new HelpFin(Help.Tipo.utente);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Azione help programmatore.
     * <p/>
     * Apre una finestra di help per il programmatore <br>
     * Metodo invocato dal Gestore Eventi <br>
     * Invoca la classe delegata per la funzionalità specifica <br>
     */
    public void apreHelpProgrammatore() {
        try { // prova ad eseguire il codice
            new HelpFin(Help.Tipo.programmatore);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

//        /* invoca il metodo delegato della classe */
//        this.getModulo().getHelp().abilitaHelpProgrammatore(null, "pippoz");
    }


    /**
     * Apre il Navigatore.
     * <p/>
     * Se il Navigatore non è aperto:
     * - esegue il pack della finestra
     * - centra la finestra sullo schermo
     * - attiva il flag "aperto"
     * Sempre:
     * - rende visibile la finestra del Navigatore
     */
    public void apriNavigatore() {
        /* variabili e costanti locali di lavoro */
        boolean continua = true;
        Portale portale = null;
        Window window = null;

        try {    // prova ad eseguire il codice

        	//Window w = getPortaleNavigatore().getWindow();
        	//w.pack();
        	//w.setAlwaysOnTop(true);
        	//w.setComponentZOrder(w, 0);
        	//w.setVisible(true);
        	//w.toBack();
        	
//        	JFrame f = new JFrame();
//        	f.setPreferredSize(new Dimension(300,200));
//        	Lib.Gui.centraFinestra(f);
//        	f.pack();
//        	f.setVisible(true);
        	
        	
            /* recupera il Portale Navigatore */
            if (continua) {
                portale = this.getPortaleNavigatore();
                continua = (portale != null);
            }// fine del blocco if

            /* recupera la eventuale finestra */
            if (continua) {
                window = portale.getWindow();
                continua = (window != null);
            }// fine del blocco if

            /* se è la prima volta, la impacchetta e la centra */
            if (continua) {
                if (!this.isAperto()) {
                    window.pack();
                    Lib.Gui.centraWindow(window, null);
                    this.setAperto(true);
                }// fine del blocco if
            }// fine del blocco if

            /* aggiorna la lista e rende la finestra/dialogo visibile */
            if (continua) {
                this.aggiornaLista();
                window.setVisible(true);
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Azione chiude il Navigatore.
     * <p/>
     * Chiude la finestra del navigatore <br>
     * Metodo invocato dal Gestore Eventi <br>
     * Esegue solo se il Modulo di questo Navigatore non è il modulo iniziale <br>
     */
    public void chiudiNavigatore() {
        /* variabili e costanti locali di lavoro */
        Portale portale;
        Connessione conn;

        try {    // prova ad eseguire il codice
            if (!this.isNavigatoreMain()) {

                /* se è un Navigatore a pannello unico,
           * alla chiusura passa alla visualizzazione
           * del componente A (lista) */
                if (this.isUsaPannelloUnico()) {
                    this.visualizzaComponenteA();
                }// fine del blocco if

                /* rende la finestra invisibile */
                portale = this.getPortaleNavigatore();
                if (portale != null) {
                    portale.setFinestraVisibile(false);
                }// fine del blocco if

                /* alla chiusura contrassegna il navigatore
                 * come non aperto */
                this.setAperto(false);

                /* riabilita il menu di avvio del modulo */
                if (!Pref.GUI.abilitati.is()) {
                    this.getModulo().getAzioneModulo().setEnabled(true);
                }// fine del blocco if

                /* chiude la connessione (se non è quella del modulo) */
                conn = this.getConnessione();
                if (!conn.equals(this.getModulo().getConnessione())) {
                    if (conn.isOpen()) {
                        conn.close();
                    }// fine del blocco if
                }// fine del blocco if

                /* genera un evento del modulo */
                this.getModulo().fire(ModuloBase.Evento.nascondi);

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
        int codUscita = 0;

        try {    // prova ad eseguire il codice

            try { // prova ad eseguire il codice

                /**
                 * - richiede la chiusura della scheda con dialogo se modificata
                 * - acquisisce il codice di uscita dalla scheda
                 */
                codUscita = this.richiediChiusuraSchedaDialogo(Scheda.BOTTONE_ABBANDONA, true);

                /* esegue le azioni in base al codice di uscita */
                switch (codUscita) {
                    case Scheda.ANNULLA: //chiusura scheda non avvenuta
                        break;

                    case Scheda.ABBANDONA:  //chiusura scheda avvenuta con abbandono delle eventuali modifiche

                        /* operazioni post chiusura scheda */
                        this.postChiusuraScheda();
                        break;

                    case Scheda.REGISTRA:  //chiusura scheda avvenuta con registrazione delle eventuali modifiche

                        /* operazioni post chiusura scheda */
                        this.postChiusuraScheda();

                        /* lancia un evento di Navigatore Modificato */
                        this.fireModificato();
                        break;

                    default: // caso non definito
                        break;
                } // fine del blocco switch


            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Operazioni effettuate dopo la chiusura della scheda.
     * <p/>
     */
    private void postChiusuraScheda() {

        try {    // prova ad eseguire il codice

            /* se il Navigatore usa pannello unico dopo la chiusura
             * visualizza il componente A */
            if (this.isUsaPannelloUnico()) {
                this.visualizzaComponenteA();
            }// fine del blocco if

            /* aggiorna la lista */
            this.aggiornaLista();

            /* sincronizza il Navigatore */
            this.sincronizza();

            /* rida' il fuoco alla lista */
            this.fuocoAllaLista();

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Chiude la scheda con un dialogo di conferma per la registrazione.
     * <p/>
     * Il dialogo viene presentato solo se la scheda è modificata
     *
     * @param bDefault codice del bottone preselezionato nel dialogo
     * @param dismetti Se true, dopo la chiusura dismette la scheda
     * (carica tutti i campi con valori vuoti)
     *
     * @return codice di chiusura della scheda:
     *         - Scheda.ANNULLA = chiusura non avvenuta
     *         - Scheda.ABBANDONA = chiusura avvenuta con abbandono delle eventuali modifiche
     *         - Scheda.REGISTRA = chiusura avvenuta con registrazione delle eventuali modifiche
     */
    public int richiediChiusuraSchedaDialogo(int bDefault, boolean dismetti) {
        /* variabili e costanti locali di lavoro */
        int codUscita = 0;
        Scheda scheda = null;
        boolean confermato = false;
        boolean continua = true;
        ArrayList<Campo> campi;
        Navigatore nav;

        try { // prova ad eseguire il codice

//            /* per prima cosa chiede alla scheda di provare (ricorsivamente)
//             * a chiudere le schede di tutti i suoi campi navigatore */
//            if (continua) {
//                campi = this.getScheda().getCampiNavigatore();
//                for(Campo campo : campi){
//                    nav = campo.getNavigatore();
//                    codUscita = nav.richiediChiusuraSchedaDialogo(bDefault, dismetti);
//                    switch (codUscita) {
//                        case Scheda.ANNULLA:
//                            continua = false;
//                            break;
//                        case Scheda.ABBANDONA:
//                            continua = true;
//                            break;
//                        case Scheda.REGISTRA:
//                            continua = true;
//                            break;
//                        default : // caso non definito
//                            break;
//                    } // fine del blocco switch
//
//                    if (!continua) {
//                        break;
//                    }// fine del blocco if
//                } // fine del ciclo for
//            }// fine del blocco if


            if (continua) {
                codUscita = Scheda.ABBANDONA;
                scheda = this.getScheda();
                if (scheda != null) {
                    codUscita = scheda.richiediChiusuraConDialogo(bDefault, dismetti);
                }// fine del blocco if

                /* chiude effettivamente la scheda */
                if (codUscita != Scheda.ANNULLA) {
                    switch (codUscita) {
                        case Scheda.ABBANDONA:
                            confermato = false;
                            break;
                        case Scheda.REGISTRA:
                            confermato = true;
                            break;
                        default: // caso non definito
                            break;
                    } // fine del blocco switch

                    this.chiusuraEffettivaScheda(confermato);

                }// fine del blocco if
            }// fine del blocco if


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return codUscita;
    }


    /**
     * Chiude la scheda senza dialogo di conferma per la registrazione.
     * <p/>
     *
     * @param registra true per registrazione automatica
     * @param dismetti Se true, dopo la chiusura dismette la scheda
     * (carica tutti i campi con valori vuoti)
     *
     * @return true se la scheda e' stata chiusa
     */
    public boolean richiediChiusuraSchedaNoDialogo(boolean registra, boolean dismetti) {
        /* variabili e costanti locali di lavoro */
        boolean eseguito = false;
        Scheda scheda;

        try { // prova ad eseguire il codice
            scheda = this.getScheda();
            if (scheda != null) {
                eseguito = scheda.richiediChiusuraNoDialogo(registra, dismetti);
            }// fine del blocco if

            if (eseguito) {
                this.chiusuraEffettivaScheda(registra);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return eseguito;
    }


    /**
     * Chiude effettivamente la scheda.
     * <p/>
     *
     * @param confermato true se confermato false se annullato
     */
    protected void chiusuraEffettivaScheda(boolean confermato) {
        /* variabili e costanti locali di lavoro */
        Connessione conn;
        Scheda scheda;

        try { // prova ad eseguire il codice

            /* se il Navigatore usa la transazione ed è in transazione,
             * conclude la transazione */
            if (this.isUsaTransazioni()) {
                if (this.isInTransazione()) {
                    conn = this.getConnessione();
                    if (confermato) {
                        conn.commit();
                    } else {
                        conn.rollback();
                    }// fine del blocco if-else
                }// fine del blocco if
            }// fine del blocco if

            /* se la scheda non e' stata confermata e il navigatore era
             * in fase di creazione di un nuovo record, lo elimina */
            if (!confermato) {
                if (this.isNuovoRecord()) {
                    this.eliminaNuovoRecord();
                }// fine del blocco if
            }// fine del blocco if

            /* sincronizza il navigatore */
            this.sincronizza();

            /* spegne il flag nuovoRecord del navigatore */
            this.setNuovoRecord(false);

            /* avvia la scheda con codice zero */
            scheda = this.getScheda();
            if (scheda != null) {
                scheda.avvia(0);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Passa il fuoco alla lista.
     * <p/>
     */
    public void fuocoAllaLista() {
        /* variabili e costanti locali di lavoro */
        Lista lista = null;

        try { // prova ad eseguire il codice
            lista = this.getLista();

            if (lista != null) {
                this.getLista().grabFocus();
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Azione preferenze.
     * <p/>
     * Apre una finestra di preferenze <br>
     * Metodo invocato dal Gestore Eventi <br>
     * Invoca il metodo delegato <br>
     */
    public void aprePreferenze() {
        /* variabili e costanti locali di lavoro */
        Modulo modPref = null;

        try { // prova ad eseguire il codice
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Azione registra dati default.
     * <p/>
     * Registra i dati del modulo <br>
     * Metodo invocato dal Gestore Eventi <br>
     * Invoca il metodo delegato <br>
     */
    public void registraDatiDefault() {
        /* invoca il metodo delegato della classe */
        this.getModulo().getModello().registraDatiDefault();
    }


    /**
     * Azione ricerca.
     * <p/>
     * Apre una finestra di ricerca <br>
     * Metodo invocato dal Gestore Eventi <br>
     * Invoca il metodo delegato <br>
     */
    public void apreRicerca() {
        /* variabili e costanti locali di lavoro */
        Filtro filtro = null;
        Filtro filtroCorrente = null;
        Filtro filtroRicerca = null;
        Lista lista = null;
        RicercaBase ricerca = null;
        Ricerca.Opzioni opzioneRicerca;
        boolean continua = true;

        try { // prova ad eseguire il codice

            /* recupera il filtro corrente */
            if (continua) {
                lista = this.getLista();
                filtroCorrente = lista.getFiltroCorrente();
            }// fine del blocco if

            /* recupera la ricerca del Navigatore
             * se non c'e' crea ora una ricerca di default */
            if (continua) {
                ricerca = this.getRicerca();
                if (ricerca == null) {
                    ricerca = new RicercaDefault(this.getModulo());
                }// fine del blocco if
                continua = ricerca != null;
            }// fine del blocco if

            if (continua) {

                /* presenta il dialogo (avvia il dialogo)*/
                ricerca.avvia();

                if (ricerca.isConfermato()) {

                    opzioneRicerca = ricerca.getOpzioneRicerca();
                    filtroRicerca = ricerca.getFiltro();

                    if (opzioneRicerca != null) {
                        /* crea il filtro in base all'opzione di ricerca */
                        switch (opzioneRicerca) {
                            case standard:
                                filtro = filtroRicerca;
                                break;
                            case aggiungiAllaLista:
                                filtro = new Filtro();
                                filtro.add(filtroCorrente);
                                if (filtroRicerca != null) {
                                    filtro.add(Filtro.Op.OR, filtroRicerca);
                                }// fine del blocco if
                                break;
                            case rimuoviDallaLista:
                                filtro = new Filtro();
                                filtro.add(filtroCorrente);
                                if (filtroRicerca != null) {
                                    filtroRicerca.setInverso(true);
                                    filtro.add(filtroRicerca);
                                }// fine del blocco if
                                break;
                            case cercaNellaLista:
                                filtro = new Filtro();
                                filtro.add(filtroCorrente);
                                if (filtroRicerca != null) {
                                    filtro.add(Filtro.Op.AND, filtroRicerca);
                                }// fine del blocco if
                                break;
                            default: // caso non definito
                                break;
                        } // fine del blocco switch
                    } else {  // opzioni non definite
                        filtro = filtroRicerca;
                    }// fine del blocco if-else

                    lista.setFiltroCorrente(filtro);
                    this.aggiornaLista();

                }// fine del blocco if

            }// fine del blocco if


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Azione proietta.
     * <p/>
     * Apre un dialogo di proiezione dati <br>
     * Metodo invocato dal Gestore Eventi <br>
     * Invoca il metodo delegato <br>
     */
    public void proietta() {
        this.getLogica().proietta();
    }


    /**
     * Testo da vsualizzare per conferma preferito.
     * <p/>
     *
     * @return il testo del messaggio visualizzato per confermare
     *         l'impostazione del Record Preferito
     */
    protected String getMessaggioRecordPreferito() {
        return "Vuoi impostare il record selezionato come Preferito?";
    }


    /**
     * Azione Imposta come Preferito.
     * <p/>
     * Imposta il record correntemente selezionato nella lista
     * come Preferito.
     * Chiede prima conferma.
     */
    public void setPreferito() {
        /* variabili e costanti locali di lavoro */
        Dialogo dialogo;
        int codice;

        try { // prova ad eseguire il codice
            Lib.Sist.beep();
            dialogo = DialogoFactory.annullaConferma();
            dialogo.setMessaggio(this.getMessaggioRecordPreferito());
            dialogo.avvia();
            if (dialogo.isConfermato()) {
                codice = this.getLista().getChiaveSelezionata();
                this.setPreferito(codice);
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Imposta un record come Preferito.
     * <p/>
     *
     * @param codice del record da rendere Preferito
     */
    protected void setPreferito(int codice) {
        /* variabili e costanti locali di lavoro */
        Campo campoPref;
        Filtro filtroBase;
        Filtro filtroPref;
        Filtro filtroTot;
        int[] codici;

        try {    // prova ad eseguire il codice

            /* recupera il campo Preferito */
            campoPref = this.getModulo().getCampoPreferito();

            /* rimuove tutti gli eventuali Preferiti (dovrebbe essere 1 solo) */
            filtroBase = this.getLista().getFiltroBase();
            filtroPref = FiltroFactory.crea(campoPref, true);
            filtroTot = new Filtro();
            filtroTot.add(filtroBase);
            filtroTot.add(filtroPref);
            codici = this.query().valoriChiave(filtroTot);
            for (int cod : codici) {
                this.query().registraRecordValore(cod, campoPref, false);
            }

            /* assegna il flag Preferito al record selezionato */
            codice = this.getLista().getChiaveSelezionata();
            this.query().registraRecordValore(codice, campoPref, true);

            /* lancia l'evento Navigatore modificato */
            this.fireModificato();

            /* rinfresca la lista */
            this.aggiornaLista();

            /* evento di modifica del record preferito */
            this.preferitoModificato(codice);

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Evento di modifica del record preferito.
     * <p/>
     * Metodo sovrascritto nelle sottoclassi <br>
     */
    protected void preferitoModificato(int codice) {
    }


    /**
     * Azione esporta.
     * <p/>
     * Apre una finestra di esportazione <br>
     * Metodo invocato dal Gestore Eventi <br>
     * Invoca la classe delegata <br>
     */
    public void apreEsporta() {
        /* variabili e costanti locali di lavoro */
        DialogoExport dialogo;

        try { // prova ad eseguire il codice

            /* crea e presenta il dialogo  */
            dialogo = new DialogoExport(this.getModulo());
            dialogo.avvia();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Azione importa.
     * <p/>
     * Apre una finestra di importazione <br>
     * Metodo invocato dal Gestore Eventi <br>
     * Invoca il metodo delegato <br>
     */
    public void apreImporta() {
        /* invoca il metodo delegato della classe */
        new Importa(this);
    }


    /**
     * Azione stampa in una <code>Lista</code>.
     * <p/>
     * Metodo invocato dal Gestore Eventi <br>
     */
    public void stampaLista() {
        /* variabili e costanti locali di lavoro */
        Lista lista;
        Finestra finestra;
        String titolo = "";

        try { // prova ad eseguire il codice

            finestra = this.getFinestra();
            if (finestra != null) {
                titolo = finestra.getFinestraBase().getTitle();
            }// fine del blocco if

            lista = this.getLista();
            if (lista != null) {
                lista.stampa(titolo);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Ritorna un TablePrinter per stampare il contenuto corrente della lista.
     * <p/>
     *
     * @return il TablePrinter
     */
    public J2TablePrinter getTablePrinter() {
        /* variabili e costanti locali di lavoro */
        J2TablePrinter printer = null;
        boolean continua;
        Lista lista;
        Tavola tavola = null;

        try {    // prova ad eseguire il codice
            lista = this.getLista();
            continua = lista != null;

            if (continua) {
                tavola = lista.getTavola();
                continua = tavola != null;
            }// fine del blocco if

            if (continua) {
                printer = tavola.getTablePrinter();
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return printer;
    }


    /**
     * Attiva una Finestra (la porta in primo piano).
     * <p/>
     * Metodo invocato dal Gestore Eventi <br>
     * Invoca il metodo delegato <br>
     */
    public void attivaFinestra() {
        /* invoca il metodo delegato della classe */
    }


    /**
     * Chiude una Finestra.
     * <p/>
     * Metodo invocato dal Gestore Eventi <br>
     * Invoca il metodo delegato <br>
     */
    public void chiudeFinestra() {
        /* invoca il metodo delegato della classe */
        this.chiudiNavigatore();
    }


    /**
     * Ritorna la finestra del Navigatore
     * <p/>
     *
     * @return la finestra del Navigatore, null se
     *         non ha finestra
     */
    public Finestra getFinestra() {
        /* variabili e costanti locali di lavoro */
        Finestra fin = null;
        PortaleNavigatore pn;

        try { // prova ad eseguire il codice
            pn = this.getPortaleNavigatore();
            if (pn != null) {
                fin = pn.getFinestra();
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return fin;
    }


    /**
     * Ritorna la Status Bar della finestra del Navigatore.
     * <p/>
     */
    public StatusBar getStatusBar() {
        /* variabili e costanti locali di lavoro */
        StatusBar statBar = null;
        Finestra fin = null;

        try {    // prova ad eseguire il codice
            fin = this.getFinestra();
            if (fin != null) {
                statBar = fin.getStatusBar();
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return statBar;
    }


    /**
     * Ritorna la Progress Bar della finestra del Navigatore.
     * <p/>
     */
    public ProgressBar getProgressBar() {
        /* variabili e costanti locali di lavoro */
        ProgressBar progBar = null;
        StatusBar statBar = null;

        try {    // prova ad eseguire il codice
            statBar = this.getStatusBar();
            if (statBar != null) {
                progBar = statBar.getProgressBar();
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return progBar;
    }


    /**
     * Azione nuovo record in una <code>Lista</code>.
     * <p/>
     * Metodo invocato dal Gestore Eventi <br>
     * Richiede la chiusura della scheda eventualmente aperta<br>
     * Crea un nuovo record<br>
     * Presenta il record in scheda<br>
     *
     * @return il codice del record creato, -1 se non creato.
     */
    protected int nuovoRecord() {
        int codice = 0;
        boolean continua = true;
        Scheda scheda;
        Navigatore nav;
        int codUscita = 0;
        boolean flag;

        try { // prova ad eseguire il codice
            nav = this;

            /* richiede la chiusura della scheda corrente */
            scheda = this.getScheda();
            if (scheda != null) {
                codUscita = scheda.richiediChiusuraConDialogo(Scheda.BOTTONE_REGISTRA, true);
                if (codUscita == Scheda.ANNULLA) {
                    continua = false;
                }// fine del blocco if
            }// fine del blocco if

            /* elimina un eventuale nuovo record precedente */
            if (continua) {
                this.eliminaNuovoRecord();
            }// fine del blocco if

            /* chiude effettivamente la scheda confermando o annullando le modifiche
             * il codice di uscita adesso può essere solo ABBANDONA o REGISTRA
             * perché il caso ANNULLA è stato intercettato in precedenza */
            if (continua) {
                flag = false; // default
                switch (codUscita) {
                    case Scheda.ABBANDONA:
                        flag = false;
                        break;
                    case Scheda.REGISTRA:
                        flag = true;
                        break;
                } // fine del blocco switch
                this.chiusuraEffettivaScheda(flag);
            }// fine del blocco if

            /* richiede al navigatore la creazione di un nuovo record */
            if (continua) {
                codice = this.creaRecord();
                if (codice == -1) {
                    continua = false;
                }// fine del blocco if
            }// fine del blocco if

            /* regola il flag nuovo record del navigatore */
            if (continua) {
                nav.setNuovoRecord(true);
            }// fine del blocco if

            /* presenta il nuovo record in scheda */
            if (continua) {
                scheda.vaiPagina(0);
                this.apriScheda(codice, true);
            }// fine del blocco if

            /* se il navigatore usa pannello unico, visualizza
             * il componente B */
            if (continua) {
                if (nav.isUsaPannelloUnico()) {
                    nav.visualizzaComponenteB();
                }// fine del blocco if
            }// fine del blocco if

            /* sincronizza il navigatore */
            if (continua) {
                nav.sincronizza();
            }// fine del blocco if

//            /* lancia un evento di stato navigatore modificato */
//            if (continua) {
//                this.fireModificato();
//            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return codice;
    }


    /**
     * Azione aggiungi record in una <code>Lista</code>.
     * <p/>
     * Metodo invocato dal Gestore Eventi <br>
     *
     * @return il codice del record aggiunto, -1 se non aggiunto.
     */
    protected int aggiungiRecord() {
        return nuovoRecord();
    }


    private void eliminaNuovoRecord() {
        /* variabili e costanti locali di lavoro */
        Navigatore nav;
        int codice;
        Scheda scheda;

        try { // prova ad eseguire il codice
            nav = this;
            scheda = nav.getScheda();
            if (scheda != null) {
                if (nav.isNuovoRecord()) {
                    codice = scheda.getCodice();
                    this.query().eliminaRecord(codice);
                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Azione modifica record in una <code>Lista</code>.
     * <p/>
     * Metodo invocato dal Gestore Eventi <br>
     * Sincronizza lo stato della GUI <br>
     * (la sincronizzazione avviene DOPO, ma viene lanciata da qui) <br>
     * Controlla e chiude una eventuale scheda aperta <br>
     * Chiede la chiave alla lista master <br>
     * Avvia la scheda, passandogli il codice-chiave <br>
     *
     * @return true se eseguito correttamente
     */
    protected boolean modificaRecord() {
        /* variabili e costanti locali di lavoro */
        boolean eseguito = false;
        int codice = 0;
        Navigatore nav;
        Scheda scheda;
        int codUscita = 0;
        boolean flag;

        try { // prova ad eseguire il codice

            eseguito = true;

            /* richiede la chiusura della scheda eventualmente aperta
            * se la chiusura viene rifiutata non procede */
            scheda = this.getScheda();
            if (scheda != null) {
                codUscita = scheda.richiediChiusuraConDialogo(Scheda.BOTTONE_REGISTRA, true);
                if (codUscita == Scheda.ANNULLA) {
                    eseguito = false;
                }// fine del blocco if
            }// fine del blocco if

            /* elimina un eventuale nuovo record precedente */
            if (eseguito) {
                this.eliminaNuovoRecord();
            }// fine del blocco if

            /* chiude effettivamente la scheda confermando o annullando le modifiche
             * il codice di uscita adesso può essere solo ABBANDONA o REGISTRA
             * perché il caso ANNULLA è stato intercettato in precedenza */
            if (eseguito) {
                flag = false; // default
                switch (codUscita) {
                    case Scheda.ABBANDONA:
                        flag = false;
                        break;
                    case Scheda.REGISTRA:
                        flag = true;
                        break;
                } // fine del blocco switch
                this.chiusuraEffettivaScheda(flag);
            }// fine del blocco if


            if (eseguito) {

                nav = this;

                /* recupera la chiave dalla lista e controlla che sia valida */
                if (eseguito) {
                    codice = this.getLogica().getChiaveMaster();
                    if (codice < 1) {
                        eseguito = false;
                    }// fine del blocco if
                }// fine del blocco if

                /* carica i dati del record nella scheda */
                if (eseguito) {

                    /* visualizza la scheda con il record */
                    if (scheda != null) {
                        scheda.vaiPagina(0);
                    }// fine del blocco if

                    this.apriScheda(codice, false);

                    /* se il navigatore usa pannello unico, visualizza
                     * il componente B */
                    if (nav.isUsaPannelloUnico()) {
                        nav.visualizzaComponenteB();
                    }// fine del blocco if

                    /* sincronizza il navigatore */
                    nav.sincronizza();

                }// fine del blocco if

            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return eseguito;
    }


    /**
     * Azione duplica record in una <code>Lista</code>.
     * <p/>
     * Metodo invocato dal Gestore Eventi <br>
     *
     * @return true se eseguito correttamente
     */
    protected boolean duplicaRecord() {
        /* invoca il metodo delegato della classe */
        return this.getLogica().duplicaRecord();
    }


    /**
     * Azione elimina record in una <code>Lista</code>.
     * <p/>
     * Metodo invocato dal Gestore Eventi <br>
     * Recupera dalla lista il/i record selezionato/i <br>
     * Chiede conferma all'utente <br>
     * Elimina i record <br>
     */
    protected void eliminaRecord() {
        /* variabili e costanti locali di lavoro */
        PortaleLista pLista;
        int[] chiavi;
        int chiave;
        Scheda scheda;
        int codScheda;
        boolean riuscito;

        try { // prova ad eseguire il codice

            /* recupera il portale lista*/
            pLista = this.getPortaleLista();

            /* recupera i codici-chiave delle righe selezionate nella lista */
            chiavi = pLista.getChiaviSelezionate();

            /* dialogo utente */
            if (confermaEliminazione(chiavi)) {

                for (int k = 0; k < chiavi.length; k++) {
                    chiave = chiavi[k];

                    /* opportunità di intercettazione per le sottoclassi */
                    riuscito = this.eliminaRecordAnte(chiave);

                    /* eliminazione fisica */
                    if (riuscito) {
                        this.query().eliminaRecord(chiave);

                        /**
                         * Si assicura che dopo l'eliminazione esista
                         * ancora un record Preferito, se usato.
                         */
                        this.assicuraPreferito();

                    }// fine del blocco if

                } // fine del ciclo for

                /* accende il flag modificato */
                this.fireModificato();

                /* se il record attualmente visualizzato in scheda
                 * e' uno di quelli eliminati, chiude la scheda */
                scheda = this.getScheda();
                if (scheda != null) {
                    codScheda = scheda.getCodice();
                    if (codScheda != 0) {
                        if (Lib.Array.isEsiste(chiavi, codScheda)) {
                            scheda.richiediChiusuraNoDialogo(false, true);
                        }// fine del blocco if
                    }// fine del blocco if
                }// fine del blocco if

                /* aggiorna la lista dopo la eliminazione */
                this.aggiornaLista();

                /* sincronizza il navigatore */
                this.sincronizza();

            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Azione rimuovi record in una <code>Lista</code>.
     * <p/>
     * Metodo invocato dal Gestore Eventi <br>
     */
    protected void rimuoviRecord() {
        this.eliminaRecord();
    }


    /**
     * Si assicura che nella selezione del Navigatore
     * esista un record Preferito. Se non esiste,
     * imposta il primo record come Preferito.
     * <p/>
     * Invocato dopo l'eliminazione di un record.
     */
    private void assicuraPreferito() {
        /* variabili e costanti locali di lavoro */
        Campo campoPref;
        Campo campoChiave;
        Campo campoOrdine;
        Campo campoPreferito;
        Query query;
        Filtro filtroBase;
        Dati dati;
        int codice;

        try {    // prova ad eseguire il codice

            campoPref = this.getModulo().getCampoPreferito();
            if (campoPref != null) {
                if (!this.esistePreferito()) {
                    campoChiave = this.getModulo().getCampoChiave();
                    campoOrdine = this.getModulo().getCampoOrdine();
                    campoPreferito = this.getModulo().getCampoPreferito();
                    filtroBase = this.getLista().getFiltroBase();
                    codice = 0;
                    query = new QuerySelezione(this.getModulo());
                    query.addCampo(campoChiave);
                    query.setFiltro(filtroBase);
                    query.addOrdine(campoOrdine);
                    dati = this.query().querySelezione(query);
                    if (dati.getRowCount() > 0) {
                        codice = dati.getIntAt(0, campoChiave);
                    }// fine del blocco if
                    dati.close();
                    if (codice > 0) {
                        this.query().registraRecordValore(codice, campoPreferito, true);
                    }// fine del blocco if
                }// fine del blocco if
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Selezione della scheda da visualizzare.
     * <p/>
     * Metodo invocato modificaRecord prima di visualizzare la scheda <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     *
     * @param codice del record che sta per essere visualizzato
     *
     * @return il nome chiave della scheda da visualizzare
     */
    public String selezionaScheda(int codice) {
        /* variabili e costanti locali di lavoro */
        String nomeChiave = "";
        PortaleScheda portale = null;

        try { // prova ad eseguire il codice
            portale = this.getPortaleScheda();
            if (portale != null) {
                nomeChiave = portale.getNomeSchedaCorrente();
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return nomeChiave;
    }

//    /**
//     * Apertura di una scheda su un record.
//     * <p/>
//     * - Seleziona la scheda da visualizzare <br>
//     * - Avvia il portale scheda con il record <br>
//     *
//     * @param codice il codice del record da visualizzare
//     */
//    private void apriScheda(int codice) {
//        /* variabili e costanti locali di lavoro */
//        boolean continua;
//        String nomeScheda;
//        Navigatore nav;
//
//        try { // prova ad eseguire il codice
//
//            /* recupera il Navigatore */
//            nav = this;
//
//            /* seleziona la scheda da visualizzare
//             * eventualmente sovrascritto dalle sottoclassi */
//            nomeScheda = nav.selezionaScheda(codice);
//
//            continua = Lib.Testo.isValida(nomeScheda);
//
//            if (continua) {
//                /* seleziona la scheda corrente nel Portale */
//                nav.getPortaleScheda().setNomeSchedaCorrente(nomeScheda);
//
//                /* avvia il Portale con il codice del record */
//                nav.getPortaleScheda().avvia(codice);
//            }// fine del blocco if
//
//        } catch (Exception unErrore) { // intercetta l'errore
//            Errore.crea(unErrore);
//        }// fine del blocco try-catch
//    }
//


    /**
     * Tasto carattere generico in una <code>Lista</code>.
     * <p/>
     * Metodo invocato dal Gestore Eventi <br>
     *
     * @param unEvento evento che ha generato il comando
     */
    public void listaCarattere(KeyEvent unEvento) {
        /* invoca il metodo delegato della classe */
        this.getLogica().listaCarattere(unEvento);
    }


    /**
     * Mouse cliccato in una <code>Lista</code>.
     * <p/>
     * Metodo invocato dal Gestore Eventi <br>
     * Sincronizza lo stato della GUI <br>
     */
    public void listaClick() {
        try { // prova ad eseguire il codice
            /* sincronizza il navigatore */
            this.sincronizza();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Mouse cliccato due volte in una <code>Lista</code>.
     * <p/>
     * Metodo invocato dal Gestore Eventi <br>
     * Modifica il record in scheda <br>
     */
    public void listaDoppioClick() {
        /* invoca il metodo delegato della classe */
        this.modificaRecord();
    }


    /**
     * Tasto Enter in una <code>Lista</code>.
     * <p/>
     * Metodo invocato dal Gestore Eventi <br>
     */
    public void listaEnter() {
        /* invoca il metodo delegato della classe */
        this.modificaRecord();
    }


//    /**
//     * Comando del mouse cliccato nei titoli.
//     * <p/>
//     * Metodo invocato dal Gestore Eventi <br>
//     * Invoca il metodo delegato <br>
//     *
//     * @param unEvento evento generato dall'interfaccia utente
//     */
//    public void colonnaCliccata(MouseEvent unEvento) {
//        /* invoca il metodo delegato della classe */
//        this.getLogica().colonnaCliccata(unEvento);
//    }


    /**
     * Entrata in una cella della lista (che riceve il fuoco).
     * <p/>
     * Metodo invocato dal Gestore Eventi <br>
     * Invoca il metodo delegato <br>
     */
    public void entrataCella() {
        /* invoca il metodo delegato della classe */
        this.getLogica().entrataCella();
    }


    /**
     * Uscita da una cella della lista (che perde il fuoco).
     * <p/>
     * Metodo invocato dal Gestore Eventi <br>
     * Invoca il metodo delegato <br>
     */
    public void uscitaCella() {
        /* invoca il metodo delegato della classe */
        this.getLogica().uscitaCella();
    }


    /**
     * Entrata in una cella della lista (che riceve il fuoco).
     * <p/>
     * Metodo invocato dal Logicanavigatore <br>
     * Sovrascritto nelle sottoclassi.
     *
     * @param codice chiave del record
     * @param unCampo campo editato
     */
    public void entrataCella(int codice, Campo unCampo) {
    }


    /**
     * Uscita da una cella della lista (che perde il fuoco).
     * <p/>
     * Metodo invocato dal Logicanavigatore <br>
     * Sovrascritto nelle sottoclassi.
     *
     * @param codice chiave del record
     * @param unCampo campo editato
     */
    public void uscitaCella(int codice, Campo unCampo) {
    }


    /**
     * Bottone carica tutti in una <code>Lista</code>.
     * <p/>
     * Metodo invocato dal Gestore Eventi <br>
     * Sovrascritto nelle sottoclassi <br>
     * Sincronizza lo stato della GUI <br>
     * Invoca il metodo delegato <br>
     */
    public void caricaTutti() {
        /* variabili e costanti locali di lavoro */
        Lista lista;

        try { // prova ad eseguire il codice
            /* recupera la lista */
            lista = this.getLista();

            /* invoca il metodo nella classe delegata */
            lista.caricaTutti();

            /* sincronizza il navigatore */
            this.sincronizza();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Bottone mostra solo selezionati in una <code>Lista</code>.
     * <p/>
     * Metodo invocato dal Gestore Eventi <br>
     * Sovrascritto nelle sottoclassi <br>
     * Invoca il metodo delegato <br>
     * Sincronizza lo stato della GUI <br>
     */
    public void soloSelezionati() {
        /* variabili e costanti locali di lavoro */
        Lista lista;

        try { // prova ad eseguire il codice

            /* recupera la lista */
            lista = this.getLista();

            /* invoca il metodo nella classe delegata */
            lista.soloSelezionati();

            this.aggiornaLista();


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Bottone nasconde selezionati in una <code>Lista</code>.
     * <p/>
     * Metodo invocato dal Gestore Eventi <br>
     * Sovrascritto nelle sottoclassi <br>
     * Invoca il metodo delegato <br>
     * Sincronizza lo stato della GUI <br>
     */
    public void nascondeSelezionati() {
        /* variabili e costanti locali di lavoro */
        Lista lista = null;

        try { // prova ad eseguire il codice
            /* recupera la lista */
            lista = this.getLista();

            /* invoca il metodo nella classe delegata */
            lista.nascondeSelezionati();

            this.aggiornaLista();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Ricarica la selezione della Lista.
     * <p/>
     * Usa il filtro base piu' quello corrente<br>
     * Invoca il metodo delegato <br>
     */
    public void aggiornaLista() {
        /* variabili e costanti locali di lavoro */
        PortaleLista pl;

        try { // prova ad eseguire il codice
            pl = this.getPortaleLista();
            if (pl != null) {
                pl.aggiornaLista();
                this.sincronizza();
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Assegna un filtro base alla Lista
     * <p/>
     *
     * @param filtro base da assegnare
     */
    public void setFiltroBase(Filtro filtro) {
        /* variabili e costanti locali di lavoro */
        Lista lista;

        try { // prova ad eseguire il codice
            lista = this.getLista();
            if (lista != null) {
                lista.setFiltroBase(filtro);
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Assegna un filtro corrente alla Lista.
     * <p/>
     *
     * @param filtro corrente da assegnare
     */
    public void setFiltroCorrente(Filtro filtro) {
        /* variabili e costanti locali di lavoro */
        Lista lista;

        try { // prova ad eseguire il codice
            lista = this.getLista();
            if (lista != null) {
                lista.setFiltroCorrente(filtro);
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Attiva l'aggiornamento continuo dei totali.
     * <p/>
     * Se attivato, i totali vengono aggiornati ad ogni caricamento dei
     * dati nella lista.
     * Di default i totali vengono aggiornati solo quando
     * cambiano i filtri.
     *
     * @param flag per attivare l'aggiornamento continuo dei totali
     */
    public void setAggiornamentoTotaliContinuo(boolean flag) {
        /* variabili e costanti locali di lavoro */
        Lista lista;

        try { // prova ad eseguire il codice
            lista = this.getLista();
            if (lista != null) {
                lista.setAggiornamentoTotaliContinuo(flag);
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Bottone salva la selezione della <code>Lista</code>.
     * <p/>
     * Metodo invocato dal Gestore Eventi <br>
     * Sovrascritto nelle sottoclassi <br>
     * Invoca il metodo delegato <br>
     * Sincronizza lo stato della GUI <br>
     */
    public void salvaSelezioneEsterna() {
        /* invoca il metodo delegato della classe */
        this.getLogica().salvaSelezioneEsterna();
    }


    /**
     * Bottone carica la selezione nella <code>Lista</code>.
     * <p/>
     * Metodo invocato dal Gestore Eventi <br>
     * Sovrascritto nelle sottoclassi <br>
     * Invoca il metodo delegato <br>
     * Sincronizza lo stato della GUI <br>
     */
    public void caricaSelezioneEsterna() {
        /* invoca il metodo delegato della classe */
        this.getLogica().caricaSelezioneEsterna();
    }


    /**
     * Bottone riga su in una <code>Lista</code>.
     * <p/>
     * Metodo invocato dal Gestore Eventi <br>
     * Regola il tipo di spostamento <br>
     * Invoca il metodo delegato per invertire i valori <br>
     */
    public void rigaSu() {
        /* invoca il metodo delegato della classe */
        this.spostaRiga(Dati.Spostamento.precedente);
    }


    /**
     * Bottone riga giu in una <code>Lista</code>.
     * <p/>
     * Metodo invocato dal Gestore Eventi <br>
     * Regola il tipo di spostamento <br>
     * Invoca il metodo delegato per invertire i valori <br>
     */
    public void rigaGiu() {
        /* invoca il metodo delegato della classe */
        this.spostaRiga(Dati.Spostamento.successivo);
    }


    /**
     * Freccia in alto.
     * <p/>
     * Metodo invocato dal Gestore Eventi <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     */
    public void frecciaAlto() {
    }


    /**
     * Freccia in basso.
     * <p/>
     * Metodo invocato dal Gestore Eventi <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     */
    public void frecciaBasso() {
    }


    /**
     * Frecce pagina in una <code>Lista</code>.
     * <p/>
     * Metodo invocato dal Gestore Eventi <br>
     * Sincronizza lo stato della GUI <br>
     * Invoca il metodo delegato <br>
     * <p/>
     * Le righe nella tavola partono da zero <br>
     *
     * @param unEvento evento generato dall'interfaccia utente
     */
    public void pagine(KeyEvent unEvento) {
//        /* invoca il metodo delegato della classe */
//        this.getLogica().pagine(unEvento);
    }


    /**
     * Freccia Home in una <code>Lista</code>.
     * <p/>
     * Metodo invocato dal Gestore Eventi <br>
     * Sincronizza lo stato della GUI <br>
     * Invoca il metodo delegato <br>
     * <p/>
     * Le righe nella tavola partono da zero <br>
     */
    public void home() {
//        /* invoca il metodo delegato della classe */
//        this.getLogica().home();
    }


    /**
     * Freccia End in una <code>Lista</code>.
     * <p/>
     * Metodo invocato dal Gestore Eventi <br>
     * Sincronizza lo stato della GUI <br>
     * Invoca il metodo delegato <br>
     * <p/>
     * Le righe nella tavola partono da zero <br>
     */
    public void end() {
//        /* invoca il metodo delegato della classe */
//        this.getLogica().end();
    }


    /**
     * Ordina sulla colonna a sinistra in una <code>Lista</code>.
     * <p/>
     * Metodo invocato dal Gestore Eventi <br>
     * Sincronizza lo stato della GUI <br>
     * Invoca il metodo delegato <br>
     * Le colonne partono da 1 <br>
     */
    public void colonnaSinistra() {
        /* invoca il metodo delegato della classe */
        this.getLogica().colonnaSinistra();
    }


    /**
     * Ordina sulla colonna a destra in una <code>Lista</code>.
     * <p/>
     * Metodo invocato dal Gestore Eventi <br>
     * Sincronizza lo stato della GUI <br>
     * Invoca il metodo delegato <br>
     * Le colonne partono da 1 <br>
     */
    public void colonnaDestra() {
        /* invoca il metodo delegato della classe */
        this.getLogica().colonnaDestra();
    }


    /**
     * Ripristina la scheda con i valori dal database.
     * <p/>
     * Metodo invocato dal Gestore Eventi
     */
    public void annullaModifiche() {
        /* variabili e costanti locali di lavoro */
        Scheda scheda = null;

        try { // prova ad eseguire il codice
            /* recupera la scheda */
            scheda = this.getScheda();

            /* invoca il metodo nella classe delegata */
            scheda.ricaricaScheda();

            /* sincronizza il navigatore */
            this.sincronizza();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
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
        /* variabili e costanti locali di lavoro */
        boolean eseguito = false;
        boolean continua = true;
        Scheda scheda;
        int paginaCorrente = 0;
        int recordCorrente = 0;
        boolean dismetti;
        Navigatore nav;
        Lista lista;
        String messaggio;

        try { // prova ad eseguire il codice

            nav = this;

            lista = nav.getLista();

            /* chiude la scheda registrando */
            scheda = this.getScheda();

            if (continua) {
                paginaCorrente = scheda.getNumeroPagina();
                recordCorrente = scheda.getCodice();
                dismetti = this.isDismettiSchedaDopoRegistrazione();
                continua = nav.richiediChiusuraSchedaNoDialogo(true, dismetti);
            }// fine del blocco if

            if (continua) {
                /* ripristina la pagina corrente */
                scheda.vaiPagina(paginaCorrente);

                /* se usa pannello unico, dopo la registrazione della scheda
                 * visualizza il componente A */
                if (nav.isUsaPannelloUnico()) {
                    nav.visualizzaComponenteA();
                    if (lista != null) {
                        lista.setRecordVisibileSelezionato(recordCorrente);
                    }// fine del blocco if

                }// fine del blocco if

                /* sincronizza il navigatore */
                nav.sincronizza();

                /* rida' il fuoco alla lista */
                if (lista != null) {
                    nav.fuocoAllaLista();
                }// fine del blocco if

                eseguito = continua;
            }// fine del blocco if

            /* ricarica la lista e visualizza l'ultimo record aggiunto*/
            if (continua) {
                if (lista != null) {
                    nav.mostraRecord(recordCorrente);
                }// fine del blocco if
            }// fine del blocco if

            /* crea una nuova scheda */
            if (continua) {
                if (nav.isNuovoRecord()) {
                    if (Pref.GUI.continua.is()) {
                        this.nuovoRecord();
                    }// fine del blocco if
                }// fine del blocco if
            }// fine del blocco if


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return eseguito;
    }


    /**
     * Bottone primo record in una <code>Scheda</code>.
     * <p/>
     * Metodo invocato dal Gestore Eventi <br>
     * Invoca il metodo delegato <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     */
    public void primoRecord() {
        /* invoca il metodo delegato della classe */
        this.spostaRecord(Dati.Spostamento.primo);
    }


    /**
     * Bottone record precedente in una <code>Scheda</code>.
     * <p/>
     * Metodo invocato dal Gestore Eventi <br>
     * Invoca il metodo delegato <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     */
    public void recordPrecedente() {
        /* invoca il metodo delegato della classe */
        this.spostaRecord(Dati.Spostamento.precedente);
    }


    /**
     * Bottone record successivo in una <code>Scheda</code>.
     * <p/>
     * Metodo invocato dal Gestore Eventi <br>
     * Invoca il metodo delegato <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     */
    public void recordSuccessivo() {
        /* invoca il metodo delegato della classe */
        this.spostaRecord(Dati.Spostamento.successivo);
    }


    /**
     * Bottone ultimo record in una <code>Scheda</code>.
     * <p/>
     * Metodo invocato dal Gestore Eventi <br>
     * Invoca il metodo delegato <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     */
    public void ultimoRecord() {
        /* invoca il metodo delegato della classe */
        this.spostaRecord(Dati.Spostamento.ultimo);
    }


    /**
     * Spostamento di record in una scheda.
     * <p/>
     * Recupera il codice della scheda <br>
     * Recupera dai dati il codice del record relativo<br>
     * Riavvia la scheda con il nuovo record <br>
     */
    private void spostaRecord(Dati.Spostamento spostamento) {
        /* variabili e costanti locali di lavoro */
        int codiceRelativo = 0;
        TavolaModello modello = null;
        Lista lista = null;
        Scheda scheda = null;
        boolean continua = true;
        int codUscita = 0;
        int codiceScheda = 0;
        Navigatore nav = null;

        try { // prova ad eseguire il codice

            nav = this;
            lista = this.getLista();
            scheda = this.getScheda();
            codiceScheda = scheda.getCodice();

            /* tenta di chiudere la scheda corrente */
            if (continua) {
                if (nav.isConfermaRegistrazioneSpostamento()) {
                    codUscita = nav.richiediChiusuraSchedaDialogo(Scheda.BOTTONE_REGISTRA, false);
                    if (codUscita == Scheda.ANNULLA) {
                        continua = false;
                    }// fine del blocco if

                } else {
                    continua = nav.richiediChiusuraSchedaNoDialogo(true, false);
                }// fine del blocco if-else

            }// fine del blocco if

            /* apre il record relativo in scheda */
            if (continua) {

                /* recupera dalla lista il codice del record
                 * al quale si vuole andare */
                modello = nav.getLista().getModello();
                codiceRelativo = modello.getCodiceRelativo(codiceScheda, spostamento);

                /* visualizza la scheda con il record */
                this.apriScheda(codiceRelativo, false);

                /* seleziona il record nella lista */
                lista.setRecordVisibileSelezionato(codiceRelativo);

                /* sincronizza il navigatore */
                nav.sincronizza();

            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Apertura di una scheda su un record.
     * <p/>
     * - Seleziona la scheda da visualizzare <br>
     * - Avvia il portale scheda con il record <br>
     *
     * @param codice il codice del record da visualizzare
     * @param nuovoRecord true se si sta presentando un nuovo record
     */
    private void apriScheda(int codice, boolean nuovoRecord) {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        String nomeScheda;
        Navigatore nav;
        Connessione conn;
        PortaleScheda ps;

        try { // prova ad eseguire il codice

            /* recupera il Navigatore */
            nav = this;

            /* seleziona la scheda da visualizzare
             * eventualmente sovrascritto dalle sottoclassi */
            nomeScheda = nav.selezionaScheda(codice);

            continua = Lib.Testo.isValida(nomeScheda);

            if (continua) {

                /* seleziona la scheda corrente nel Portale */
                nav.getPortaleScheda().setNomeSchedaCorrente(nomeScheda);

                /* se il navigatore usa la transazione, avvia la transazione */
                if (this.isUsaTransazioni()) {
                    conn = this.getConnessione();
                    conn.startTransaction();
                }// fine del blocco if

                /* avvia il Portale con il codice del record */
                ps = nav.getPortaleScheda();
                if (ps != null) {
                    ps.avvia(codice, nuovoRecord);
                }// fine del blocco if


            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Mostra un record nella lisa.
     * <p/>
     * Aggiunge il record al filtro corrente della lista <br>
     * Ricarica la lista <br>
     * Porta la riga in area visibile <br>
     * Seleziona la riga <br>
     */
    public void mostraRecord(int cod) {
        /* variabili e costanti locali di lavoro */
        Filtro filtroCorr;
        Filtro filtroRec;
        Filtro filtroNuovo;
        Lista lista;

        try { // prova ad eseguire il codice

            lista = this.getLista();
            filtroCorr = lista.getFiltroCorrente();

            if ((filtroCorr != null) && (filtroCorr.isValido())) {
                filtroRec = FiltroFactory.codice(this.getModulo(), cod);
                filtroNuovo = new Filtro();
                filtroNuovo.add(filtroCorr);
                filtroNuovo.add(Filtro.Op.OR, filtroRec);
                lista.setFiltroCorrente(filtroNuovo);
            }// fine del blocco if

            this.aggiornaLista();
            lista.setRecordVisibileSelezionato(cod);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch


    }


    /**
     * Mouse cliccato in un <code>Campo</code> della <code>Scheda</code>.
     * <p/>
     * Metodo invocato dal Gestore Eventi <br>
     *
     * @param unCampo campo che ha generato l'evento
     */
    public void mouseClick(Campo unCampo) {
        int posA = 0;
        int posB = 0;
        int posC = 0;
        int pos = 0;
        Object[] valori = null;

        posA = ((Integer)unCampo.getCampoDati().getMemoria()).intValue();
        posB = ((Integer)unCampo.getCampoDati().getVideo()).intValue();

        JComboBox combo = ((CVComboLista)unCampo.getCampoVideoNonDecorato()).getComboBox();
        posC = combo.getSelectedIndex();
        ComboModello mod = null;

        /* recupera la lista valori dal campo dati */
        valori = ((CDElenco)unCampo.getCampoDati()).getArrayValori();

        String[] lis = {"primo", "sec", "terzo"};
        mod = new ComboModello(lis);
        mod = (ComboModello)combo.getModel();
//        combo.setModel(mod);
//        ((ComboBase)combo).setValori(valori);

//        unCampo.getCampoVideo().aggiornaGUI(new Integer(posB));
//        if (posA!=posB) {
//            int a=87;
//        }// fine del blocco if
//        if (posA!=posC) {
//            int a=87;
//        }// fine del blocco if
        pos = 3;
//        combo.setSelectedIndex(-1);
        unCampo.getCampoVideo().aggiornaGUI(new Integer(pos));
        mod.fireContentsChanged(mod, pos, pos);
    }


    /**
     * Mouse cliccato due volte in un <code>Campo</code> della <code>Scheda</code>.
     * <p/>
     * Metodo invocato dal Gestore Eventi <br>
     *
     * @param unCampo campo che ha generato l'evento
     */
    public void mouseDoppioClick(Campo unCampo) {
    }


    /**
     * Sincronizzazione del testo di un campo calcolato.
     * <p/>
     * Metodo invocato dal Gestore Eventi <br>
     * Evento lanciato da un componente utilizzato per il calcolo del campo <br>
     * L'evento viene lanciato quando il componente subisce delle modifiche <br>
     * Invoca il metodo delegato <br>
     *
     * @param unCampo campo calcolato da sincronizzare
     */
    public void calcolaCampo(Campo unCampo) {
        try { // prova ad eseguire il codice
            /* invoca il metodo delegato della classe */
            unCampo.getCampoLogica().archivioGui();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Sincronizzazione della label di un campo calcolato.
     * <p/>
     * Metodo invocato dal Gestore Eventi <br>
     * Evento lanciato da un componente utilizzato per il calcolo del campo <br>
     * L'evento viene lanciato quando il componente subisce delle modifiche <br>
     * Invoca il metodo delegato <br>
     *
     * @param unCampo campo calcolato da sincronizzare
     */
    public void calcolaLabel(Campo unCampo) {
        try { // prova ad eseguire il codice
            /* invoca il metodo delegato della classe */
            unCampo.getCampoLogica().memoriaGui();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Inserimento di un carattere generico in un campo testo.
     * <p/>
     * Metodo invocato dal Gestore Eventi <br>
     * Invoca il metodo delegato <br>
     *
     * @param unCampo campo che ha generato l'evento
     */
    public void carattere(Campo unCampo) {
        try { // prova ad eseguire il codice
            /* invoca il metodo delegato della classe */
            this.modificaCampo(unCampo);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Inserimento di un carattere a video in un campo testo.
     * <p/>
     * Metodo invocato dal Gestore Eventi <br>
     * Invoca il metodo delegato <br>
     *
     * @param unCampo campo che ha generato l'evento
     */
    public void carattereTesto(Campo unCampo) {
        try { // prova ad eseguire il codice
            /* invoca il metodo delegato della classe */
            this.modificaCampo(unCampo);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Inserimento di un carattere a video in un campo area di testo.
     * <p/>
     * Metodo invocato dal Gestore Eventi <br>
     * Invoca il metodo delegato <br>
     *
     * @param unCampo campo che ha generato l'evento
     */
    public void carattereTestoArea(Campo unCampo) {
        try { // prova ad eseguire il codice
            /* invoca il metodo delegato della classe */
            this.modificaCampo(unCampo);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Modifica di un campo.
     * </p>
     * Controlla la validit� del Campo <br>
     * Aggiorna il campo (da GUI a memoria e poi di nuovo a GUI) <br>
     * Sincronizza il navigatore <br>
     *
     * @param unCampo campo che ha generato l'evento
     */
    private void modificaCampo(Campo unCampo) {
        try { // prova ad eseguire il codice
            /* invoca il metodo delegato della classe */
            this.getLogica().modificaCampo(unCampo);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Inserimento del carattere Enter in un campo testo.
     * <p/>
     * Metodo invocato dal Gestore Eventi <br>
     */
    public void enter() {
        try { // prova ad eseguire il codice
            /* invoca il metodo delegato della classe */
            this.registraScheda();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Inserimento del carattere Esc in un campo testo.
     * <p/>
     * Metodo invocato dal Gestore Eventi <br>
     */
    public void escape() {
        try { // prova ad eseguire il codice
            /* invoca il metodo delegato della classe */
            this.chiudeScheda();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Entrata nel campo (che riceve il fuoco).
     * </p>
     * Metodo invocato dal Gestore Eventi <br>
     *
     * @param unCampo campo che ha generato l'evento
     */
    public void entrataCampo(Campo unCampo) {
    }


    /**
     * Uscita dal campo (che perde il fuoco).
     * </p>
     * Metodo invocato dal Gestore Eventi <br>
     *
     * @param unCampo campo che ha generato l'evento
     */
    public void uscitaCampo(Campo unCampo) {
    }


    /**
     * Modifica di un item in un componente.
     * <p/>
     * Metodo invocato dal Gestore Eventi <br>
     * Aggiorna il campo (da GUI a memoria e poi di nuovo a GUI) <br>
     * Sincronizza il navigatore <br>
     * Invoca il metodo delegato della classe <br>
     *
     * @param unCampo campo che ha generato l'evento
     */
    public void itemModificato(Campo unCampo) {
        try { // prova ad eseguire il codice
            /* invoca il metodo delegato della classe */
            this.modificaCampo(unCampo);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Entrata dal popup di un ComboBox (che diventa visibile).
     * <p/>
     * Metodo invocato dal Gestore Eventi <br>
     * Invoca il metodo delegato della classe <br>
     * Sincronizza il navigatore <br>
     *
     * @param unCampo campo che ha generato l'evento
     */
    public void entrataPopup(Campo unCampo) {
    }


    /**
     * Uscita dal popup di un ComboBox (che diventa invisibile).
     * <p/>
     * Metodo invocato dal Gestore Eventi <br>
     * Aggiorna il campo (da GUI a memoria e poi di nuovo a GUI) <br>
     * Invoca il metodo delegato della classe <br>
     * Sincronizza il navigatore <br>
     *
     * @param unCampo campo che ha generato l'evento
     */
    public void uscitaPopup(Campo unCampo) {
        try { // prova ad eseguire il codice
            /* invoca il metodo delegato della classe */
            this.getLogica().uscitaPopup(unCampo);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Modifica della selezione di una lista </li>
     * <p/>
     * Metodo invocato dal Gestore Eventi <br>
     * Invoca il metodo delegato della classe <br>
     * Sovrascritto nelle sottoclassi <br>
     */
    public void selezioneModificata() {
        try { // prova ad eseguire il codice
            /* invoca il metodo delegato della classe */
            this.getLogica().selezioneModificata();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Verifica se la scheda e' registrabile.
     * <p/>
     * Sovrascritto nelle sottoclassi.
     * Invoca il metodo delagato della scheda.
     *
     * @param scheda la scheda da controllare
     *
     * @return true se e' registrabile
     */
    public boolean isSchedaRegistrabile(Scheda scheda) {
        /* variabili e costanti locali di lavoro */
        boolean registrabile = true;

        try {    // prova ad eseguire il codice
            return scheda.isRegistrabile();
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return registrabile;
    }


    /**
     * Recupera i valori pilota di questo navigatore.
     * <p/>
     * Sovrascritto nelle sottoclassi.<br>
     *
     * @return i valori pilota
     */
    public int[] recuperaValoriPilota() {
        /* variabili e costanti locali di lavoro */
        int[] valori = null;
        Lista listaPilota = null;

        try {    // prova ad eseguire il codice
            listaPilota = this.getListaPilota();
            valori = listaPilota.getChiaviSelezionate();
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return valori;
    }


    /**
     * Regola i valori pilota di questo Navigatore.
     * <p/>
     * Recupera i valori dal navigatore pilota<br>
     * Registra i valori in questo navigatore, solo se sono cambiati<br>
     * Se il flag isPilotaDaRigheMultiple e' attivato, puo' usare
     * piu' di una riga pilota, altrimenti la lista pilotata e' vuota.<br>
     * Se non ci sono righe selezionate il valore pilota viene impostato
     * a zero in modo da non caricare righe.
     *
     * @return true se i valori pilota sono stati modificati
     *         rispetto ai precedenti
     */
    public boolean regolaValoriPilota() {
        /* variabili e costanti locali di lavoro */
        int[] valoriPilota = null;
        Navigatore navPilota = null;
        boolean cambiati = false;

        try {    // prova ad eseguire il codice

            navPilota = this.getNavPilota();
            valoriPilota = navPilota.recuperaValoriPilota();

            if (valoriPilota.length > 0) { // almeno una riga selezionata

                if (valoriPilota.length > 1) { // piu' di una riga selezionata
                    if (this.isPilotatoDaRigheMultiple()) {
                        cambiati = this.setValoriPilota(valoriPilota);
                    } else {
                        cambiati = this.setValorePilota(0);
                    }// fine del blocco if-else

                } else {    // una sola riga selezionata
                    cambiati = this.setValorePilota(valoriPilota[0]);
                }// fine del blocco if-else

            } else {    // nessuna riga selezionata
                cambiati = this.setValorePilota(0);
            }// fine del blocco if-else

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return cambiati;
    }


    /**
     * Recupera la lista pilota di questo Navigatore.
     * <p/>
     * Metodo sovrascritto nelle sottoclassi.
     *
     * @return la lista pilota di questo navigatore
     */
    protected Lista getListaPilota() {
        return null;
    }


    /**
     * Ritorna il pacchetto di informazioni del portale Lista.
     * <p/>
     * Puo' essere sovrascritto dalle sottoclassi per leggere
     * e/o modificare le informazioni di stato prima che vengano
     * utilizzate per sincronizzare la GUI.
     *
     * @param info il pacchetto di informazioni per la sincronizzazione
     */
    public InfoLista getInfoLista(InfoLista info) {
        return info;
    }


    /**
     * Ritorna il pacchetto di informazioni del portale Scheda.
     * <p/>
     * Puo' essere sovrascritto dalle sottoclassi per leggere
     * e/o modificare le informazioni di stato prima che vengano
     * utilizzate per sincronizzare la GUI.
     *
     * @param info il pacchetto di informazioni per la sincronizzazione
     */
    protected InfoScheda getInfoScheda(InfoScheda info) {
        return info;
    }


    /**
     * Ritorna il pacchetto di informazioni del portale Navigatore.
     * <p/>
     * Puo' essere sovrascritto dalle sottoclassi per leggere
     * e/o modificare le informazioni di stato prima che vengano
     * utilizzate per sincronizzare la GUI.
     *
     * @param info il pacchetto di informazioni per la sincronizzazione
     */
    protected InfoNavigatore getInfoNavigatore(InfoNavigatore info) {
        return info;
    }


    /**
     * Visualizza il solo componente A del portale Navigatore.
     * <p/>
     * Visualizza il componente alto/sinistra dello split pane del portale navigatore.
     */
    public void visualizzaComponenteA() {
        try { // prova ad eseguire il codice
            this.getPortaleNavigatore().visualizzaComponenteA();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Visualizza il solo componente B del portale Navigatore.
     * <p/>
     * Visualizza il componente basso/destra dello split pane del portale navigatore.
     */
    public void visualizzaComponenteB() {
        try { // prova ad eseguire il codice
            this.getPortaleNavigatore().visualizzaComponenteB();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }

//    /**
//     * Regola il filtro base della lista.
//     * Se non esiste il campo Visibile, il filtro e nullo <br>
//     * //todo controllare esistenza campo visibile
//     */
//    protected Filtro getFiltroDefault() {
//        /* variabili e costanti locali di lavoro */
//        Filtro filtro = null;
//
//        try { // prova ad eseguire il codice
//            if (Pref.getBool(Pref.Gen.PROGRAMMATORE)) {
//                filtro = null;
//            } else {
//                filtro
//                        = new Filtro(
//                        Modello.NOME_CAMPO_VISIBILE,
//                        Operatore.UGUALE,
//                        CostanteDB.VERO);
//            }// fine del blocco if-else
//
//        } catch (Exception unErrore) { // intercetta l'errore
//            Errore.crea(unErrore);
//        }// fine del blocco try-catch
//
//        /* valore di ritorno */
//        return filtro;
//    } /* fine del metodo */


    /**
     * Creazione fisica di un nuovo record.
     * <p/>
     *
     * @return il codice del record creato, -1 se non creato.
     */
    public int creaRecord() {
        /* variabili e costanti locali di lavoro */
        int codice = 0;
        ArrayList<CampoValore> valori = null;
        SetValori sv = null;
        boolean continua = true;
        Campo campoPreferito;

        try {    // prova ad eseguire il codice

            /* se il navigatore e' pilotato, recupera
             * i valori da assegnare automaticamente,
             * altrimenti crea un nuovo set vuoto */
            if (this.isPilotato()) {
                sv = this.getValoriNuovoRecordAuto();
            } else {
                sv = new SetValori(this.getModulo());
            }// fine del blocco if-else

            /**
             * Regola il valore del campo Preferito, se usato.
             * Controlla se tra i records isolati dal filtro base
             * esiste già un record Preferito.
             * Se non esiste, aggiunge il campo Preferito con valore true
             * al set che deve essere registrato.
             */
            campoPreferito = this.getModulo().getCampoPreferito();
            if (campoPreferito != null) {
                if (!this.esistePreferito()) {
                    sv.setValore(campoPreferito, true);
                }// fine del blocco if
            }// fine del blocco if

            /* opportunità di intercettazione per le sottoclassi */
            continua = this.nuovoRecordAnte(sv);

            /* crea il nuovo record */
            if (continua) {
                valori = sv.getListaValori();
                codice = this.query().nuovoRecord(valori, this.getConnessione());
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return codice;
    }


    /**
     * Registrazione fisica di un record.
     * <p/>
     *
     * @param codice il codice chiave del record
     * @param campi la lista dei campi da registrare
     *
     * @return true se riuscito
     */
    public boolean registraRecord(int codice, ArrayList<Campo> campi) {
        /* variabili e costanti locali di lavoro */
        boolean riuscito = false;
        SetValori set;
        ArrayList<CampoValore> lista;
        boolean continua;

        try { // prova ad eseguire il codice

            set = new SetValori(this.getModulo());
            for (Campo campo : campi) {
                set.add(new CampoValore(campo));
            }

            /* opportunità di intercettazione per la sottoclasse */
            continua = this.registraRecordAnte(codice, set);

            /* registrazione fisica */
            if (continua) {
                lista = set.getListaValori();
                riuscito = this.query().registraRecordValori(codice, lista);
            }// fine del blocco if


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return riuscito;

    }


    /**
     * Registra il record attualmente visualizzato nella scheda.
     * <p/>
     * Delega alla scheda un controllo di possibile registrazione <br>
     * Recupera dalla scheda il codice <br>
     * Recupera dalla scheda i campi fisici <br>
     * Registra il record<br>
     *
     * @return true se riuscito
     */
    public boolean registraRecord() {
        boolean riuscito = true;
        boolean continua = true;
        Scheda scheda;
        int codice = 0;
        ArrayList<Campo> campi;
        Navigatore nav;

        try { // prova ad eseguire il codice

            scheda = this.getScheda();
            continua = (scheda != null);

            /* recupero il codice della scheda */
            if (continua) {
                codice = scheda.getCodice();
                if (codice <= 0) {
                    continua = false;
                }// fine del blocco if
            }// fine del blocco if

            /* se era nuovo record, accendo il campo Visibile */
            if (continua) {
                if (this.isNuovoRecord()) {
                    scheda.setRecordVisibile();
                }// fine del blocco if
            }// fine del blocco if

            /* registro i campi fisici modificati della scheda */
            if (continua) {
                campi = scheda.getCampiFisiciModificati();
                continua = this.registraRecord(codice, campi);
                riuscito = continua;
            }// fine del blocco if

            /* se ha registrato spengo il flag nuovo record */
            if (riuscito) {
                this.setNuovoRecord(false);
            }// fine del blocco if

            // giro ricorsivamente questo comando a tutti gli eventuali campi
            // Navigatore contenuti nella scheda
            if (continua) {
                campi = scheda.getCampiNavigatore();
                for (Campo campo : campi) {
                    nav = campo.getNavigatore();
                    continua = nav.registraRecord();
                    if (!continua) {
                        riuscito = false;
                        break;
                    }// fine del blocco if
                }// fine del blocco for
            }// fine del blocco if

            /* lancia l'evento Navigatore modificato */
            if (continua) {
                this.fireModificato();
            }// fine del blocco if


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return riuscito;
    }


    /**
     * Controlla se tra i records isolati dal filtro base
     * esiste già un record identificato come Preferito.
     * <p/>
     *
     * @return true se esiste un record Preferito
     */
    private boolean esistePreferito() {
        /* variabili e costanti locali di lavoro */
        boolean esiste = false;
        Campo campoPreferito;
        Filtro filtroBase;
        Filtro filtroPref;
        Filtro filtroTot;
        int quanti;

        try {    // prova ad eseguire il codice

            campoPreferito = this.getModulo().getCampoPreferito();
            if (campoPreferito != null) {
                filtroBase = this.getLista().getFiltroBase();
                filtroPref = FiltroFactory.crea(campoPreferito, true);
                filtroTot = new Filtro();
                filtroTot.add(filtroBase);
                filtroTot.add(filtroPref);
                quanti = this.query().contaRecords(filtroTot);
                if (quanti > 0) {
                    esiste = true;
                }// fine del blocco if
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return esiste;
    }


    /**
     * Dialogo di conferma utente prima dell'eliminazione.
     * <p/>
     * Presenta un messaggio di conferma.
     * Se confermato, ritorna true.
     *
     * @param chiavi array di chiavi dei record da eliminare
     *
     * @return true se confermato
     */
    protected boolean confermaEliminazione(int[] chiavi) {
        /* variabili e costanti locali di lavoro */
        boolean conferma = false;
        int quanti = 0;
        MessaggioDialogo dialogo = null;
        String uno = "";
        String moltiPrima = "";
        String moltiDopo = "";

        uno = "Sei sicuro di voler cancellare il record selezionato?";
        moltiPrima = "Sei sicuro di voler cancellare i ";
        moltiDopo = " record selezionati?";

        try { // prova ad eseguire il codice

            quanti = chiavi.length;

            if (quanti == 1) {
                dialogo = new MessaggioDialogo(uno);
            } else if (quanti > 1) {
                dialogo = new MessaggioDialogo(moltiPrima + quanti + moltiDopo);
            } else if (quanti == 0) {
                dialogo = new MessaggioDialogo("Ciccato");
            } /* fine del blocco if-else-if-else */

            /* procede solo dopo conferma esplicita */
            if (dialogo.getRisposta() == JOptionPane.YES_OPTION) {
                conferma = true;
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return conferma;
    } /* fine del metodo-azione */


    /**
     * Spostamento di una riga in una <code>Lista</code>.
     * <p/>
     * Recupera il codice del record selezionato <br>
     * Recupera il codice del record contiguo nell'ordinamento <br>
     * Invoca il metodo delegato per invertire i valori <br>
     * Ricarica la Lista <br>
     * Mantiene selezionata la riga <br>
     * Sincronizza lo stato della GUI <br>
     * <p/>
     * (non usa il modificatore public, cos� il metodo
     * viene visto solo all'interno del package) <br>
     *
     * @param spostamento precedente o successivo
     */
    private void spostaRiga(Dati.Spostamento spostamento) {
        /* variabili e costanti locali di lavoro */
        Lista lista = null;
        TavolaModello modello = null;
        int corrente = 0;
        int contiguo = 0;
        int riga = 0;
        int tot = 0;

        try { // prova ad eseguire il codice
            /* recupera lista e modello dati */
            lista = this.getLista();
            modello = lista.getModello();

            /* Recupera il codice del record selezionato */
            corrente = lista.getRecordSelezionato();

            /* Recupera il codice del record contiguo nell'ordinamento */
            contiguo = modello.getCodiceRelativo(corrente, spostamento);

            /* inverte i valori del campo ordine dei due record */
            this.query().swapOrdine(corrente, contiguo);

            /* memorizza la riga selezionata */
            riga = lista.getTavola().getSelectedRow();
            tot = lista.getTavola().getRowCount();

            /* ricarica la Lista */
            this.getPortaleLista().avvia();

            /* riseleziona la riga */
            switch (spostamento) {
                case precedente:
                    if (riga > 0) {
                        riga--;
                    }// fine del blocco if
                    break;
                case successivo:
                    if (riga < tot) {
                        riga++;
                    }// fine del blocco if
                    break;
                default: // caso non definito
                    break;
            } // fine del blocco switch
            lista.getTavola().setRowSelectionInterval(riga, riga);

            this.fireModificato();

            /* sincronizza il navigatore */
            this.sincronizza();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Ritorna lo stato del flag di uso pannello unico
     * dalle preferenze.
     * <p/>
     *
     * @return lo stato del flag
     */
    protected boolean isPrefPannelloUnico() {
        /* variabili e costanti locali di lavoro */
        boolean flag = false;

        try {    // prova ad eseguire il codice
            flag = Pref.GUI.espansione.is();
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return flag;
    }


    /**
     * Ritorna lo stato del flag usa pannello unico
     *
     * @return true se il Navigatore usa un pannello unico
     */
    public boolean isUsaPannelloUnico() {
        /* variabili e costanti locali di lavoro */
        boolean usa = false;
        PortaleNavigatore pn = null;

        try { // prova ad eseguire il codice
            pn = this.getPortaleNavigatore();
            if (pn != null) {
                usa = pn.isUsaPannelloUnico();
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return usa;
    }


    /**
     * Regola il flag di uso di pannello unico.
     * <p/>
     * Se disattivato, il Navigatore usa uno split pane
     * per mostrare contemporaneamente i suoi due componenti.
     * Se attivato, il Navigatore usa un pannello unico
     * per mostrare alternativamente i suoi due componenti.
     *
     * @param flag true per usare un pannello unico
     */
    public void setUsaPannelloUnico(boolean flag) {
        /* variabili e costanti locali di lavoro */
        PortaleNavigatore pn = null;

        try { // prova ad eseguire il codice
            pn = this.getPortaleNavigatore();
            if (pn != null) {
                pn.setUsaPannelloUnico(flag);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Visualizza il componente B in finestra separata.<p>
     * Significativo solo se il navigatore usa un pannello unico
     *
     * @param flag true per usare finestra separata
     */
    public void setUsaFinestraPop(boolean flag) {
        this.getPortaleNavigatore().setUsaFinestraPop(flag);
    }



    /**
     * Controlla se è usato il bottone nuovo record nella lista.
     * <p/>
     *
     * @return true se è usato
     */
    public boolean isUsaNuovo() {
        /* variabili e costanti locali di lavoro */
        boolean usa = false;
        Portale portaleLista;

        try { // prova ad eseguire il codice
            portaleLista = this.getPortaleLista();
            if (portaleLista != null) {
                usa = portaleLista.isUsaNuovo();
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return usa;
    }


    /**
     * Abilita l'uso del bottone nuovo record in lista
     * <p/>
     * Visualizza il bottone nella toolbar della lista.<br>
     *
     * @param usa per usare il bottone
     */
    public void setUsaNuovo(boolean usa) {
        /* variabili e costanti locali di lavoro */
        Portale portaleLista;

        try { // prova ad eseguire il codice
            portaleLista = this.getPortaleLista();
            if (portaleLista != null) {
                portaleLista.setUsaNuovo(usa);
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Abilita l'uso del bottone modifica record in lista
     * <p/>
     * Visualizza il bottone nella toolbar della lista.<br>
     *
     * @param usa per usare il bottone
     */
    public void setUsaModifica(boolean usa) {
        /* variabili e costanti locali di lavoro */
        Portale portaleLista;

        try { // prova ad eseguire il codice
            portaleLista = this.getPortaleLista();
            if (portaleLista != null) {
                portaleLista.setUsaModifica(usa);
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Determina il tipo di bottoni nuovo/elimina della lista
     * <p/>
     * Usa la coppia nuovo/elimina oppure la coppia aggiungi/rimuovi <br>
     *
     * @param tipoBottoni nuovo/elimina oppure aggiungi/rimuovi
     */
    public void setTipoBottoni(Lista.Bottoni tipoBottoni) {
        /* variabili e costanti locali di lavoro */
        Portale portaleLista;

        try { // prova ad eseguire il codice
            portaleLista = this.getPortaleLista();
            if (portaleLista != null) {
                portaleLista.setTipoBottoni(tipoBottoni);
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Controlla se sono usate le frecce di spostamento ordine nella lista.
     * <p/>
     *
     * @return true se sono usate
     */
    public boolean isUsaFrecceSpostaOrdineLista() {
        /* variabili e costanti locali di lavoro */
        boolean usa = false;
        Portale portaleLista;

        try { // prova ad eseguire il codice
            portaleLista = this.getPortaleLista();
            if (portaleLista != null) {
                usa = portaleLista.isUsaFrecceSpostaOrdineLista();
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return usa;
    }


    /**
     * Abilita l'uso delle dei bottoni di spostamento di ordine
     * del record su e giu nella lista.
     * <p/>
     * Visualizza i bottoni nella toolbar della lista.<br>
     * La lista e' spostabile solo se ordinata sul campo ordine.<br>
     *
     * @param usaFrecce per usare i bottoni di spostamento
     */
    public void setUsaFrecceSpostaOrdineLista(boolean usaFrecce) {
        /* variabili e costanti locali di lavoro */
        Portale portaleLista = null;

        try { // prova ad eseguire il codice
            portaleLista = this.getPortaleLista();
            if (portaleLista != null) {
                portaleLista.setUsaFrecceSpostaOrdineLista(usaFrecce);
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Abilita l'uso delle del bottone Duplica Record nella lista.
     * <p/>
     *
     * @param flag per usare il bottone Duplica Record
     */
    public void setUsaDuplicaRecord(boolean flag) {
        /* variabili e costanti locali di lavoro */
        Portale portaleLista = null;

        try { // prova ad eseguire il codice
            portaleLista = this.getPortaleLista();
            if (portaleLista != null) {
                portaleLista.setUsaDuplicaRecord(flag);
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Abilita l'uso del pulsante ricerca nella lista.
     * <p/>
     *
     * @param flag per usare il pulsante
     */
    public void setUsaRicerca(boolean flag) {
        /* variabili e costanti locali di lavoro */
        Portale portaleLista;

        try { // prova ad eseguire il codice
            portaleLista = this.getPortaleLista();
            if (portaleLista != null) {
                portaleLista.setUsaRicerca(flag);
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Abilita l'uso del pulsante Proietta nella lista.
     * <p/>
     *
     * @param flag per usare il pulsante
     */
    public void setUsaProietta(boolean flag) {
        /* variabili e costanti locali di lavoro */
        Portale portaleLista;

        try { // prova ad eseguire il codice
            portaleLista = this.getPortaleLista();
            if (portaleLista != null) {
                portaleLista.setUsaProietta(flag);
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Abilita l'uso della toolbar nella lista.
     * <p/>
     *
     * @param flag per usare la toolbar
     */
    public void setUsaToolBarLista(boolean flag) {
        /* variabili e costanti locali di lavoro */
        Portale portale;

        try { // prova ad eseguire il codice
            portale = this.getPortaleLista();
            if (portale != null) {
                portale.setUsaToolbar(flag);
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Abilita l'uso della status bar nella lista.
     * <p/>
     *
     * @param flag per usare la status bar
     */
    public void setUsaStatusBarLista(boolean flag) {
        /* variabili e costanti locali di lavoro */
        Lista lista;

        try { // prova ad eseguire il codice
            lista = this.getLista();
            if (lista != null) {
                lista.setUsaStatusBar(flag);
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Abilita l'uso della dei filtri pop nella lista.
     * <p/>
     *
     * @param flag per usare i filtri pop
     */
    public void setUsaFiltriPopLista(boolean flag) {
        /* variabili e costanti locali di lavoro */
        Lista lista;

        try { // prova ad eseguire il codice
            lista = this.getLista();
            if (lista != null) {
                lista.setUsaFiltriPop(flag);
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Abilita l'uso della status bar nella scheda.
     * <p/>
     *
     * @param flag per usare la status bar
     */
    public void setUsaStatusBarScheda(boolean flag) {
        /* variabili e costanti locali di lavoro */
        Scheda scheda;

        try { // prova ad eseguire il codice
            scheda = this.getScheda();
            if (scheda != null) {
                scheda.setUsaStatusBar(flag);
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Controlla l'uso del pulsante Preferito nella lista
     * <p/>
     *
     * @return true se usa il pulsante Preferito
     */
    public boolean isUsaPreferito() {
        /* variabili e costanti locali di lavoro */
        boolean usa = false;
        Portale portale;

        try { // prova ad eseguire il codice
            portale = this.getPortaleLista();
            if (portale != null) {
                usa = portale.isUsaPreferito();
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine de l blocco try-catch

        /* valore di ritorno */
        return usa;
    }


    /**
     * Abilita l'uso della funzione e del pulsante Preferito nella lista.
     * <p/>
     * <p/>
     * Il flag della funzione è nel portale <br>
     * Il flag del bottone è nella toolbar <br>
     *
     * @param flag per usare il pulsante
     */
    public void setUsaPreferito(boolean flag) {
        /* variabili e costanti locali di lavoro */
        PortaleLista portaleLista;

        try { // prova ad eseguire il codice
            portaleLista = this.getPortaleLista();
            if (portaleLista != null) {
                portaleLista.setUsaPreferito(flag);
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Regola la visibilita' della progress bar nella finestra.
     * <p/>
     * La Progress Bar normalmente non e' visibile
     * (Viene comunque resa visibile durante l'esecuzione una Operazione)
     *
     * @param flag per regolare la visibilita'
     */
    public void setProgressBarVisibile(boolean flag) {
        StatusBar sb;

        try {    // prova ad eseguire il codice
            sb = this.getStatusBar();
            if (sb != null) {
                sb.setProgressBarVisibile(flag);
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Chiede al navigatore il permesso di chiuderlo.
     * <p/>
     * Presenta eventuali messaggi all'utente per risolvere situazioni aperte <br>
     * Metodo sovrascritto dalle sottoclassi <br>
     *
     * @return true se riuscito.
     */
    public boolean richiediChiusura() {
        /* variabili e costanti locali di lavoro */
        boolean riuscito = true;

        try {    // prova ad eseguire il codice

            riuscito = true;

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return riuscito;
    }


    public PortaleLista getPortaleLista() {
        return portaleLista;
    }


    /**
     * Assegna il portale Lista al navigatore.
     * <p/>
     *
     * @param portaleLista il portale Lista da assegnare
     */
    protected void setPortaleLista(PortaleLista portaleLista) {
        this.portaleLista = portaleLista;
    }


    public PortaleScheda getPortaleScheda() {
        return portaleScheda;
    }


    /**
     * Assegna il portale Scheda al navigatore.
     * <p/>
     *
     * @param portaleScheda il portale Scheda da assegnare
     */
    protected void setPortaleScheda(PortaleScheda portaleScheda) {
        this.portaleScheda = portaleScheda;
    }


    /**
     * riferimento al modulo a cui appartiene questo navigatore
     */
    public Modulo getModulo() {
        return modulo;
    }


    /**
     * riferimento al modulo a cui appartiene questo navigatore
     */
    protected void setModulo(Modulo modulo) {
        this.modulo = modulo;
    }


    /**
     * Indica se il Navigatore effettua le operazioni standard
     * sotto transazione
     * <p/>
     *
     * @return true se opera sotto transazione
     */
    private boolean isUsaTransazioni() {
        return usaTransazioni;
    }


    /**
     * Indica se il Navigatore deve effettuare le operazioni standard
     * sotto transazione
     * <p/>
     *
     * @param flag di controllo
     */
    public void setUsaTransazioni(boolean flag) {
        this.usaTransazioni = flag;
    }


    /**
     * Indica se il Navigatore è correntemente in transazione.
     * <p/>
     *
     * @return true se è in transazione
     */
    private boolean isInTransazione() {
        /* variabili e costanti locali di lavoro */
        boolean inTrans = false;
        Connessione conn;

        try {    // prova ad eseguire il codice
            conn = this.getConnessione();
            inTrans = conn.isInTransaction();
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return inTrans;
    }


    /**
     * Ritorna la connessione da utilizzare per l'accesso al database
     * <p/>
     * Se non è stata registrata una connessione specifica,
     * usa la connessione del Modulo.<br>
     *
     * @return la connessione da utilizzare
     */
    public Connessione getConnessione() {
        /* variabili e costanti locali di lavoro */
        Connessione conn = null;

        try { // prova ad eseguire il codice
            if (this.connessione != null) {
                conn = this.connessione;
            } else {
                conn = this.getModulo().getConnessione();
            }// fine del blocco if-else
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return conn;
    }


    /**
     * Registra una connessione specifica da utilizzare
     * per l'accesso al database.
     * <p/>
     *
     * @param connessione da utilizzare
     */
    public void setConnessione(Connessione connessione) {
        this.connessione = connessione;
    }


    /**
     * Aggiunge una scheda alla collezione del Modulo.
     * <p/>
     * La scheda aggiunta viene resa corrente.
     *
     * @param unaScheda da aggiungere
     *
     * @return il nome chiave della scheda aggiunta
     */
    public String addScheda(Scheda unaScheda) {
        /* variabili e costanti locali di lavoro */
        String nome = "";

        try { // prova ad eseguire il codice

            nome = this.getPortaleScheda().addScheda(unaScheda);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return nome;
    }


    /**
     * Aggiunge una scheda alla collezione.
     * <p/>
     * Regola la scheda appena aggiunta come corrente <br>
     * Se il nomeChiave è nullo o vuoto, usa come chiave un numero progressivo <br>
     *
     * @param nomeChiave della scheda
     * @param scheda da aggiungere
     *
     * @return il nome definitivo assegnato alla scheda
     */
    public String addScheda(String nomeChiave, Scheda scheda) {
        /* variabili e costanti locali di lavoro */
        String chiave = "";

        try { // prova ad eseguire il codice

            chiave = this.getPortaleScheda().addScheda(nomeChiave, scheda);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return chiave;
    }


    /**
     * Aggiunge una scheda e la rende corrente.
     * <p/>
     * Usa il nome chiave della scheda <br>
     */
    public void addSchedaCorrente(Scheda unaScheda) {
        /* variabili e costanti locali di lavoro */
        Portale port;

        try { // prova ad eseguire il codice

            port = this.getPortaleScheda();
            if (port != null) {
                port.addScheda(unaScheda);
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }

//    /**
//     * Aggiunge una scheda alla collezione.
//     * <p/>
//     *
//     * @param nomeChiave della scheda
//     * @param unaScheda  da aggiungere
//     *
//     * @return il nome definitivo assegnato alla scheda  �
//     */
//    public String addScheda(String nomeChiave, Scheda unaScheda) {
//        /* variabili e costanti locali di lavoro */
//        String nomeScheda = "";
//        Portale portale = null;
//
//        try { // prova ad eseguire il codice
//            portale = this.getPortaleScheda();
//            if (portale != null) {
//                nomeScheda = portale.addScheda(nomeChiave, unaScheda);
//            }// fine del blocco if
//
//        } catch (Exception unErrore) { // intercetta l'errore
//            Errore.crea(unErrore);
//        }// fine del blocco try-catch
//
//        /* valore di ritorno */
//        return nomeScheda;
//    }


    /**
     * Aggiunge un set alla collezione interna.
     * <p/>
     *
     * @param nomeSet da recuperare dal modello
     *
     * @return lo stesso nome del set passato come parametro
     */
    public String addSet(String nomeSet) {
        /* variabili e costanti locali di lavoro */
        String nome = "";
        Portale portale = null;

        try { // prova ad eseguire il codice
            portale = this.getPortaleScheda();
            if (portale != null) {
                nome = portale.addSet(nomeSet);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return nome;
    }


    /**
     * Recupera la scheda del navigatore
     *
     * @return la scheda gestita dal Navigatore
     */
    public Scheda getScheda() {
        /* variabili e costanti locali di lavoro */
        Portale portale;
        Scheda scheda = null;

        try { // prova ad eseguire il codice
            portale = this.getPortaleScheda();
            if (portale != null) {
                scheda = portale.getSchedaCorrente();
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return scheda;
    }


    /**
     * Recupera una scheda dalla collezione del navigatore
     * <p/>
     *
     * @param chiave per la collezione
     *
     * @return la scheda corrispondente
     */
    public Scheda getScheda(String chiave) {
        /* variabili e costanti locali di lavoro */
        PortaleScheda portale;
        Scheda scheda = null;

        try { // prova ad eseguire il codice
            portale = this.getPortaleScheda();
            if (portale != null) {
                scheda = portale.getScheda(chiave);
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return scheda;
    }


    /**
     * Crea un'azione specifica e la aggiunge al navigatore.
     * <p/>
     * L'azione può essere aggiunta: <ul>
     * <li> Alla toolbar della lista (in coda) </li>
     * <li> Alla toolbar della scheda (in coda) </li>
     * <li> Ad un menu (posizione specificata nell'azione stessa) </li>
     * <ul/>
     * Metodo invocato dal ciclo inizializza <br>
     *
     * @param azione completa di icona, titolo ed help
     * @param tool switch di selezione lista/scheda/menu
     *
     * @deprecated usa addAzione(Azione azione)
     */
    public void addAzione(Azione azione, Navigatore.Tool tool) {
        /* invoca il metodo delegato della libreria */
        Lib.Risorse.addAzione(azione, this, tool);
    }


    /**
     * Crea un'azione specifica e la aggiunge al navigatore.
     * <p/>
     * L'azione può essere aggiunta: <ul>
     * <li> Alla toolbar della lista (in coda) </li>
     * <li> Alla toolbar della scheda (in coda) </li>
     * <li> Ad un menu (posizione specificata nell'azione stessa) </li>
     * <ul/>
     * Metodo invocato dal ciclo inizializza <br>
     *
     * @param azione completa di icona, titolo ed help
     */
    public void addAzione(Azione azione) {
        /* invoca il metodo delegato della libreria */
        Lib.Risorse.addAzione(azione, this);
    }


    /**
     * Abilita l'uso dei caratteri per filtrare la lista.
     *
     * @param usaCarattereFiltro
     */
    public void setUsaCarattereFiltro(boolean usaCarattereFiltro) {
        /* variabili e costanti locali di lavoro */
        Lista lista;

        try { // prova ad eseguire il codice
            lista = this.getLista();
            if (lista != null) {
                lista.setUsaCarattereFiltro(usaCarattereFiltro);
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Ritorna l'oggetto contenente i metodi Query.
     * <p/>
     *
     * @return l'oggetto contenente i metodi Query
     */
    public MetodiQuery query() {
        return this.getQuery();
    }


    /**
     * nome chiave interno del navigatore
     */
    public String getNomeChiave() {
        return nomeChiave;
    }


    /**
     * nome chiave interno del navigatore
     */
    public void setNomeChiave(String nomeChiave) {
        this.nomeChiave = nomeChiave;
    }


    private int getModo() {
        return modo;
    }


    public void setModo(int modo) {
        this.modo = modo;
    }


    public String getNomeVista() {
        return nomeVista;
    }


    public void setNomeVista(String nomeVista) {
        this.nomeVista = nomeVista;
    }


    /**
     * Ritorna la lista gestita dal navigatore.<br>
     *
     * @return la lista gestita dal Navigatore
     */
    public Lista getLista() {
        /* variabili e costanti locali di lavoro */
        Portale portale;
        Lista lista = null;

        try { // prova ad eseguire il codice
            portale = this.getPortaleLista();
            if (portale != null) {
                lista = portale.getLista();
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return lista;
    }


    /**
     * Assegna una Lista specifica al navigatore<p>
     *
     * @param lista la lista da assegnare
     */
    public void setLista(Lista lista) {
        /* variabili e costanti locali di lavoro */
        PortaleLista pLista = null;

        try { // prova ad eseguire il codice
            pLista = this.getPortaleLista();
            if (pLista != null) {
                pLista.setLista(lista);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Assegna una Vista specifica alla lista del navigatore
     * <p/>
     *
     * @param vista da assegnare
     */
    public void setVista(Vista vista) {
        /* variabili e costanti locali di lavoro */
        Lista lista;

        try { // prova ad eseguire il codice
            lista = this.getLista();
            if (lista != null) {
                lista.setVista(vista);
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    public String getNomeSet() {
        return nomeSet;
    }


    public void setNomeSet(String nomeSet) {
        this.nomeSet = nomeSet;
    }


    /**
     * Determina se il navigatore deve creare una propria finestra.
     * <p/>
     *
     * @param usa true se il navigatore deve creare una propria finestra
     */
    public void setUsaFinestra(boolean usa) {
        /* variabili e costanti locali di lavoro */
        PortaleNavigatore pn;

        try { // prova ad eseguire il codice
            pn = this.getPortaleNavigatore();
            if (pn != null) {
                pn.setUsaFinestra(usa);
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Determina se il navigatore deve creare una propria finestra.
     * <p/>
     *
     * @param usa true se il navigatore deve creare una propria finestra
     * @param dialogo true se la finestra deve essere di tipo dialogo modale
     */
    public void setUsaFinestra(boolean usa, boolean dialogo) {
        /* variabili e costanti locali di lavoro */
        PortaleNavigatore pn;

        try { // prova ad eseguire il codice
            pn = this.getPortaleNavigatore();
            if (pn != null) {
                pn.setUsaFinestra(usa);
                pn.setUsaDialogo(dialogo);
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Determina se il navigatore usa una propria finestra.
     * <p/>
     *
     * @return true se il Navigatore usa la finestra
     */
    private boolean isUsaFinestra() {
        /* variabili e costanti locali di lavoro */
        boolean usa = false;
        Portale pn;

        try { // prova ad eseguire il codice
            pn = this.getPortaleNavigatore();
            if (pn != null) {
                usa = pn.isUsaFinestra();
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return usa;
    }


    /**
     * Determina se il navigatore usa una propria finestra di tipo dialogo.
     * <p/>
     *
     * @return true se il Navigatore usa la finestra di tipo dialogo
     */
    private boolean isUsaFinestraDialogo() {
        /* variabili e costanti locali di lavoro */
        boolean usa = false;
        PortaleNavigatore pn;

        try { // prova ad eseguire il codice
            if (this.isUsaFinestra()) {
                pn = this.getPortaleNavigatore();
                if (pn != null) {
                    usa = pn.isUsaFinestraDialogo();
                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return usa;
    }


    /**
     * Recupera lo stato corrente di modificabilità del Navigatore.
     * <p/>
     *
     * @return lo stato di modificabilità del Navigatore
     */
    public boolean isModificabile() {
        return this.modificabile;
    }


    /**
     * Rende il Navigatore modificabile o meno.
     * <p/>
     * Un Navigatore modificabile consente di modificare i
     * record visualizzati in lista o in scheda.<br>
     * Un Navigatore non modificabile permette
     * la sola visualizzazione dei record.<br>
     *
     * @param flag true se il Navigatore deve essere modificabile
     */
    public void setModificabile(boolean flag) {
        try {    // prova ad eseguire il codice
            this.modificabile = flag;
            this.regolaModificabile();
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Regola il navigatore dopo aver impostato
     * l'attributo Modificabile.
     * <p/>
     */
    private void regolaModificabile() {
        /* variabili e costanti locali di lavoro */
        Scheda scheda;

        try {    // prova ad eseguire il codice

            /* regola la scheda e sincronizza il navigatore
             * (solo se già inizializzato) */
            if (this.isInizializzato()) {
                scheda = this.getScheda();
                if (scheda != null) {
                    scheda.setModificabile(this.isModificabile());
                    scheda.sincronizza();
                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Ritorna il titolo della finestra del Navigatore.
     * <p/>
     *
     * @return il titolo della finestra
     */
    private String getTitoloFinestra() {
        return titoloFinestra;
    }


    /**
     * Assegna il titolo della finestra del Navigatore.
     * <p/>
     *
     * @param titolo il titolo della finestra
     */
    public void setTitoloFinestra(String titolo) {
        /* variabili e costanti locali di lavoro */
        Portale pn;

        try { // prova ad eseguire il codice
            this.titoloFinestra = titolo;
            pn = this.getPortaleNavigatore();
            if (pn != null) {
                pn.setTitoloFinestra(titolo);
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    public LogicaNavigatore getLogica() {
        return logica;
    }


    public void setLogica(LogicaNavigatore logica) {
        this.logica = logica;
    }


    private MetodiQuery getQuery() {
        return query;
    }


    protected void setQuery(MetodiQuery query) {
        this.query = query;
    }


    public boolean isNavigatoreMain() {
        return this.getModulo().isPrimoModulo();
    }


    public PortaleNavigatore getPortaleNavigatore() {
        return portaleNavigatore;
    }


    /**
     * Assegna il portale Navigatore al navigatore.
     * <p/>
     *
     * @param portaleNav il portale Navigatore da assegnare
     */
    protected void setPortaleNavigatore(PortaleNavigatore portaleNav) {
        this.portaleNavigatore = portaleNav;
    }


    /**
     * Regola l'altezza della lista.
     * <p/>
     * Il valore e' espresso in righe <br>
     * Se non regolato, usa l'altezza di default della Lista <br>
     *
     * @param righe l'altezza della lista espressa in righe
     */
    public void setRigheLista(int righe) {
        /* variabili e costanti locali di lavoro */
        Lista lista = null;

        try { // prova ad eseguire il codice
            lista = this.getLista();
            if (lista != null) {
                lista.setAltezzaPreferita(righe);
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Regola la larghezza del Portale Lista.
     * <p/>
     * Il valore e' espresso in pixel <br>
     * Se non regolato, usa la dimensione interna <br>
     *
     * @param larghezza la larghezza della lista in pixel
     */
    public void setLarghezzaLista(int larghezza) {
        /* variabili e costanti locali di lavoro */
        PortaleLista portaleLista = null;

        try { // prova ad eseguire il codice
            portaleLista = this.getPortaleLista();
            if (portaleLista != null) {
                Lib.Comp.setPreferredWidth(portaleLista, larghezza);
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Registra sul database il valore di un campo per un dato record.
     *
     * @param campo il campo da registrare
     * @param codice codice chiave del record da registrare
     *
     * @return true se riuscito
     */
    protected boolean cellaModificata(Campo campo, int codice) {
        /* variabili e costanti locali di lavoro */
        boolean riuscito = true;

        try { // prova ad eseguire il codice

            /* accende il flag modificato */
            this.fireModificato();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return riuscito;
    }


    public String getNomeNavigatore() {
        return nomeNavigatore;
    }


    public void setNomeNavigatore(String nomeNavigatore) {
        this.nomeNavigatore = nomeNavigatore;
    }


    public boolean isOrizzontale() {
        return isOrizzontale;
    }


    /**
     * Regola l'orientamento del navigatore.
     * <p/>
     *
     * @param orizzontale true per orizzontale, false per verticale.
     */
    public void setOrizzontale(boolean orizzontale) {
        isOrizzontale = orizzontale;
    }


    public boolean isNuovoRecord() {
        return isNuovoRecord;
    }


    public void setNuovoRecord(boolean nuovoRecord) {
        isNuovoRecord = nuovoRecord;
    }


    /**
     * Ritorna il flag di comportamento dopo la chiusura della scheda
     * con il pulsante di registrazione.
     * <p/>
     *
     * @return true se la scheda va dismessa dopo la chiusura con registrazione
     */
    public boolean isDismettiSchedaDopoRegistrazione() {
        return isDismettiSchedaDopoRegistrazione;
    }


    /**
     * Regola il comportamento dopo la chiusura della scheda
     * con il pulsante di registrazione.
     * <p/>
     * Se true, dismette la scheda dopo la chiusura
     *
     * @param flag true per dismettere dopo registrazione
     */
    protected void setDismettiSchedaDopoRegistrazione(boolean flag) {
        isDismettiSchedaDopoRegistrazione = flag;
    }


    /**
     * Ritorna il flag di comportamento di registrazione
     * allo spostamento in scheda in presenza di modifiche.
     * <p/>
     *
     * @return true se registra automaticamente, false se chiede conferma
     */
    public boolean isConfermaRegistrazioneSpostamento() {
        return confermaRegistrazioneSpostamento;
    }


    /**
     * Regola il flag di comportamento di registrazione
     * allo spostamento in scheda in presenza di modifiche.
     * <p/>
     * Se true, dismette la scheda dopo la chiusura
     *
     * @param flag true per registrare automaticamente, false per chiedere conferma
     */
    protected void setConfermaRegistrazioneSpostamento(boolean flag) {
        this.confermaRegistrazioneSpostamento = flag;
    }


    /**
     * Ritorna il campo di link verso il modulo pilota.
     * <p/>
     *
     * @return il campo di link verso il modulo pilota
     */
    public Campo getCampoLink() {
        return campoLink;
    }


    /**
     * Regola il campo di link verso il modulo pilota.
     * <p/>
     *
     * @param campoLink il campo di link verso il modulo pilota
     */
    public void setCampoLink(Campo campoLink) {
        this.campoLink = campoLink;
    }


    /**
     * Ritorna l'elenco dei valori pilota.
     * <p/>
     *
     * @return l'elenco dei valori pilota
     */
    public int[] getValoriPilota() {
        return valoriPilota;
    }


    /**
     * Ritorna il primo valore dall'elenco dei valori pilota.
     * <p/>
     *
     * @return il primo valore pilota
     */
    public int getValorePilota() {
        /* variabili e costanti locali di lavoro */
        int valorePilota = 0;
        int[] valoriPilota = null;
        int primoValore = 0;

        try {    // prova ad eseguire il codice
            valoriPilota = this.getValoriPilota();
            if (valoriPilota != null) {
                if (valoriPilota.length > 0) {
                    primoValore = valoriPilota[0];
                    valorePilota = primoValore;
                }// fine del blocco if
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return valorePilota;
    }


    /**
     * Regola l'elenco di valori pilota.
     * <p/>
     * I valori pilota vengono modificati solo se i valori
     * ricevuti sono diversi da quelli attuali.<br>
     * Se i valori sono stati modificati, ritorna true
     * per segnalare il fatto al chiamante.
     *
     * @param valoriPilota l'elenco dei valori pilota
     *
     * @return true se i valori pilota sono stati modificati.
     */
    public boolean setValoriPilota(int[] valoriPilota) {
        /* variabili e costanti locali di lavoro */
        int[] valoriPrecedenti = null;
        boolean cambiato = false;

        try { // prova ad eseguire il codice

            /* recupera gli attuali valori pilota */
            valoriPrecedenti = this.getValoriPilota();

            if ((valoriPilota == null) && (valoriPrecedenti == null)) {   // entrambi nulli
                cambiato = false;
            } else {   // almeno uno non nullo

                if ((valoriPilota != null) && (valoriPrecedenti != null)) {  // entrambi non nulli
                    if (Lib.Array.isUguali(valoriPilota, valoriPrecedenti)) {
                        cambiato = false;
                    } else {
                        cambiato = true;
                    }// fine del blocco if-else
                } else {   // uno e' nullo e l'altro no
                    cambiato = true;
                }// fine del blocco if-else

            }// fine del blocco if-else

            if (cambiato) {
                this.valoriPilota = valoriPilota;
                this.valoriPilotaCambiati(valoriPilota);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return cambiato;

    }


    /**
     * Metodo chiamato ogni volta che i valori pilota sono cambiati.
     * <p/>
     * Da sovrascrivere nelle sottoclassi per intercettare
     * il momento del cambio dei valori pilota.
     *
     * @param nuoviValori i nuovi valori pilota
     */
    public void valoriPilotaCambiati(int[] nuoviValori) {
        /* variabili e costanti locali di lavoro */
        Lista lista = null;

        try { // prova ad eseguire il codice
            lista = this.getLista();
            if (lista != null) {
                lista.eliminaSelezione();
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Regola l'elenco di valori pilota con un singolo valore.
     * <p/>
     *
     * @param valorePilota il valore pilota
     *
     * @return true se i valori pilota sono stati modificati.
     */
    public boolean setValorePilota(int valorePilota) {
        /* variabili e costanti locali di lavoro */
        int[] valoriPilota = null;
        boolean cambiati = false;

        try { // prova ad eseguire il codice
            valoriPilota = new int[1];
            valoriPilota[0] = valorePilota;
            cambiati = this.setValoriPilota(valoriPilota);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return cambiati;
    }


    /**
     * Crea un filtro corrispondente ai valori pilota.
     * <p/>
     *
     * @return il filtro corrispondente ai valori pilota
     */
    public Filtro creaFiltroPilota() {
        /* variabili e costanti locali di lavoro */
        Navigatore nav = null;
        Campo campoLink = null;
        int[] valoriPilota = null;
        int valorePilota = 0;
        Filtro filtroPilota = null;
        Filtro filtro = null;

        try { // prova ad eseguire il codice
            nav = this;
            campoLink = nav.getCampoLink();
            valoriPilota = nav.getValoriPilota();
            if (valoriPilota != null) {
                if (valoriPilota.length > 0) {
                    filtroPilota = new Filtro();
                    for (int k = 0; k < valoriPilota.length; k++) {
                        valorePilota = valoriPilota[k];
                        filtro = FiltroFactory.crea(campoLink, valorePilota);
                        filtroPilota.add(Operatore.OR, filtro);
                    } // fine del ciclo for
                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return filtroPilota;
    }


    /**
     * Ritorna true se questo Navigatore e' pilotato.
     * <p/>
     *
     * @return true se questo Navigatore e' pilotato
     */
    public boolean isPilotato() {
        /* variabili e costanti locali di lavoro */
        boolean isPilotato = false;

        if (this.getCampoLink() != null) {
            isPilotato = true;
        }// fine del blocco if

        /* valore di ritorno */
        return isPilotato;
    }


    /**
     * Flag - seleziona l'icona piccola, media o grande.
     * <p/>
     *
     * @param unTipoIcona codice tipo icona (Codifica in ToolBar)
     *
     * @see it.algos.base.toolbar.ToolBar
     */
    public void setTipoIcona(int unTipoIcona) {
    }


    /**
     * Regola la dimensione delle icone.
     * <p/>
     * Dimensione piccola <br>
     * Regola tutte le icone di tutte le ToolBar del navigatore <br>
     */
    public void setIconePiccole() {
        this.setTipoIcona(ToolBar.ICONA_PICCOLA);
    }


    /**
     * Regola la dimensione delle icone.
     * <p/>
     * Dimensione media <br>
     * Regola tutte le icone di tutte le ToolBar del navigatore <br>
     */
    public void setIconeMedie() {
        this.setTipoIcona(ToolBar.ICONA_MEDIA);
    }


    /**
     * Regola la dimensione delle icone.
     * <p/>
     * Dimensione grande <br>
     * Regola tutte le icone di tutte le ToolBar del navigatore <br>
     */
    public void setIconeGrandi() {
        this.setTipoIcona(ToolBar.ICONA_GRANDE);
    }


    /**
     * flag - usa il bottone elimina.
     *
     * @param flag true per usare il bottone elimina
     */
    public void setUsaElimina(boolean flag) {
        this.getPortaleLista().setUsaElimina(flag);
    }


    /**
     * flag - usa i bottoni di selezione.
     *
     * @param usaSelezione true per usare i tre bottoni di selezione dei records <br>
     */
    public void setUsaSelezione(boolean usaSelezione) {
    }


    /**
     * flag - usa il bottone di stampa lista.
     *
     * @param flag true per usare il bottone di stampa lista <br>
     */
    public void setUsaStampaLista(boolean flag) {
    }


    /**
     * Abilita o disabilita  l'uso della barra dei totali nella Lista.
     * <p/>
     *
     * @param flag true per usare la barra dei totali, false per non usarla
     */
    public void setUsaTotaliLista(boolean flag) {
        /* variabili e costanti locali di lavoro */
        Lista lista;

        try { // prova ad eseguire il codice
            lista = this.getLista();
            if (lista != null) {
                lista.setUsaTotali(flag);
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch


    }


    /**
     * Ritorna il navigatore Master di questo navigatore.
     * <p/>
     * Sovrascritto nelle sottoclassi.
     *
     * @return il navigatore Master
     */
    public Navigatore getNavMaster() {
        return null;
    }


    /**
     * Ritorna il navigatore Slave di questo navigatore.
     * <p/>
     * Sovrascritto nelle sottoclassi.
     *
     * @return il navigatore Slave
     */
    public Navigatore getNavSlave() {
        return null;
    }


    /**
     * Ritorna il navigatore pilota di questo navigatore.
     * <p/>
     *
     * @return il navigatore pilota
     */
    public Navigatore getNavPilota() {
        return navPilota;
    }


    /**
     * Assegna il navigatore pilota di questo navigatore.
     * <p/>
     *
     * @param navPilota il navigatore pilota
     */
    public void setNavPilota(Navigatore navPilota) {
        this.navPilota = navPilota;
    }


    /**
     * Ritorna il Navigatore pilotato da questo navigatore.
     * <p/>
     *
     * @return il Navigatore pilotato
     */
    public Navigatore getNavPilotato() {
        return navPilotato;
    }


    /**
     * Assegna il Navigatore pilotato.
     * <p/>
     *
     * @param navPilotato il Navigatore pilotato
     */
    public void setNavPilotato(Navigatore navPilotato) {
        this.navPilotato = navPilotato;
    }


    /**
     * Ritorna il flag che indica se questo navigatore puo'
     * essere pilotato da piu' di un valore.
     * <p/>
     *
     * @return true se puo' essere pilotato da piu' di un valore
     */
    public boolean isPilotatoDaRigheMultiple() {
        return isPilotatoDaRigheMultiple;
    }


    /**
     * Permette di pilotare questo navigatore con piu' di un valore.
     * <p/>
     * Regolazione significativa solo su un navigatore pilotato.<br>
     * Questo flag controlla il comportamento della lista pilotata
     * quando esistono piu' valori pilota
     * (es. quando si seleziona piu' di una riga nella lista pilota)<br>
     * Se attivato, vengono caricate tutte le corrispondenti righe.<br>
     * Se disattivato, non viene caricata nessuna riga.<br>
     *
     * @param flag true per permettere piu' di una riga pilota.
     */
    public void setPilotatoDaRigheMultiple(boolean flag) {
        isPilotatoDaRigheMultiple = flag;
    }


    /**
     * Ritorna il riferimento al campo che contiene e pilota questo navigatore.
     * <p/>
     *
     * @return il campo che contiene e pilota il navigatore.
     */
    public Campo getCampoPilota() {
        return campoPilota;
    }


    /**
     * Regola il riferimento al campo che contiene e pilota questo navigatore.
     * <p/>
     *
     * @param campoPilota il campo che contiene e pilota il navigatore.
     */
    public void setCampoPilota(Campo campoPilota) {
        this.campoPilota = campoPilota;
    }


    /**
     * Restituisce un campo della Scheda.
     * <p/>
     * Metodo sovrascritto nelle sottoclassi <br>
     *
     * @param nomeCampo nome del campo
     *
     * @return il campo richiesto - null se non esiste
     */
    protected Campo getCampoScheda(String nomeCampo) {
        /* variabili e costanti locali di lavoro */
        Campo unCampo = null;
        Scheda scheda = null;

        try {    // prova ad eseguire il codice
            scheda = this.getScheda();
            if (scheda != null) {
                unCampo = scheda.getCampo(nomeCampo);
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return unCampo;
    }


    /**
     * Costruisce il menu tabelle specifico.
     * </p>
     */
    public void setMenuTabelle(JMenuItem menu) {
        this.getPortaleNavigatore().setMenuTabelle(menu);
    }

//    /**
//     * Ritorna true se il navigatore e' stato modificato.
//     * <p/>
//     * Il navigatore viene contrassegnato come modificato non
//     * appena si aggiunge, modifica o cancella un record <br>
//     *
//     * @return true se il navigatore e' stato modificato
//     */
//    public boolean isModificato() {
//        return isModificato;
//    }
//


    /**
     * Lancia un evento per la modifica del navigatore.
     * <p/>
     */
    protected void fireModificato() {

        try { // prova ad eseguire il codice
            this.fire(NavigatoreBase.Evento.statoModificato);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Ritorna true se il navigatore e' aperto.
     * <p/>
     * Il Navigatore e' aperto se la sua finestra e' visibile sullo schermo.
     *
     * @return true se il navigatore e' aperto
     */
    public boolean isAperto() {
        return aperto;
    }


    private void setAperto(boolean aperto) {
        this.aperto = aperto;
    }


    /**
     * Verifica se il navigatore e' stato gia' inizializzato.
     * <p/>
     *
     * @return true se il navigatore e' stato gia' inizializzato
     */
    public boolean isInizializzato() {
        return inizializzato;
    }


    protected void setInizializzato(boolean inizializzato) {
        this.inizializzato = inizializzato;
    }


    /**
     * Recupera il dialogo di ricerca del Navigatore
     * <p/>
     * Ritorna il dialogo di ricerca specifico o in assenza il dialogo
     * di ricerca di default
     *
     * @return il dialogo di ricerca
     */
    public RicercaBase getRicerca() {
        /* variabili e costanti locali di lavoro */
        RicercaBase ricercaOut;

        if (this.ricerca != null) {
            ricercaOut = this.ricerca;
        } else {
            ricercaOut = this.ricercaDefault;
        }// fine del blocco if-else

        /* valore di ritorno */
        return ricercaOut;
    }


    /**
     * Assegna un dialogo di ricerca a questo navigatore
     * <p/>
     *
     * @param ricerca il dialogo di ricerca
     */
    public void setRicerca(RicercaBase ricerca) {
        this.ricerca = ricerca;
    }


    public EventListenerList getListaListener() {
        return listaListener;
    }


    public void setListaListener(EventListenerList listaListener) {
        this.listaListener = listaListener;
    }


    private AzPortaleNuovoRec getAzionePortaleNuovoRec() {
        return azionePortaleNuovoRec;
    }


    private void setAzionePortaleNuovoRec(AzPortaleNuovoRec azionePortaleNuovoRec) {
        this.azionePortaleNuovoRec = azionePortaleNuovoRec;
    }


    private AzPortaleAddRec getAzionePortaleAddRec() {
        return azionePortaleAddRec;
    }


    private void setAzionePortaleAddRec(AzPortaleAddRec azionePortaleAddRec) {
        this.azionePortaleAddRec = azionePortaleAddRec;
    }


    private AzPortaleModificaRec getAzionePortaleModificaRec() {
        return azionePortaleModificaRec;
    }


    private void setAzionePortaleModificaRec(AzPortaleModificaRec azionePortaleModificaRec) {
        this.azionePortaleModificaRec = azionePortaleModificaRec;
    }


    private AzPortaleEliminaRec getAzionePortaleEliminaRec() {
        return azionePortaleEliminaRec;
    }


    private void setAzionePortaleEliminaRec(AzPortaleEliminaRec azionePortaleEliminaRec) {
        this.azionePortaleEliminaRec = azionePortaleEliminaRec;
    }


    private AzPortaleLevaRec getAzionePortaleLevaRec() {
        return azionePortaleLevaRec;
    }


    private void setAzionePortaleLevaRec(AzPortaleLevaRec azionePortaleLevaRec) {
        this.azionePortaleLevaRec = azionePortaleLevaRec;
    }


    private AzPortaleDuplicaRec getAzionePortaleDuplicaRec() {
        return azionePortaleDuplicaRec;
    }


    private void setAzionePortaleDuplicaRec(AzPortaleDuplicaRec azionePortaleDuplicaRec) {
        this.azionePortaleDuplicaRec = azionePortaleDuplicaRec;
    }


    private AzPortaleListaSel getAzionePortaleListaSel() {
        return azionePortaleListaSel;
    }


    private void setAzionePortaleListaSel(AzPortaleListaSel azionePortaleListaSel) {
        this.azionePortaleListaSel = azionePortaleListaSel;
    }


    private AzPortaleListaCursore getAzionePortaleCursore() {
        return azionePortaleCursore;
    }


    private void setAzionePortaleCursore(AzPortaleListaCursore azionePortaleCursore) {
        this.azionePortaleCursore = azionePortaleCursore;
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
            Lib.Eventi.fire(listaListener, unEvento, Navigatore.class, this);
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Esegue l'azione generata dall'evento.
     * <p/>
     * Metodo invocato dalla classe interna <br>
     */
    public void esegui(PortaleBase.Evento evento) {

        try { // prova ad eseguire il codice

            switch (evento) {
                case nuovoRecord:
                    this.nuovoRecord();
                    break;
                case aggiungiRecord:
                    this.aggiungiRecord();
                    break;
                case modificaRecord:
                    this.modificaRecord();
                    break;
                case duplicaRecord:
                    this.duplicaRecord();
                    break;
                case eliminaRecord:
                    this.eliminaRecord();
                    break;
                case rimuoviRecord:
                    this.rimuoviRecord();
                    break;
                case listaSelezione:
                    this.selezioneModificata();
                    break;
                case stato:
                    break;
                default: // caso non definito
                    break;
            } // fine del blocco switch

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Lanciata da ogni scheda del Navigatore ogni volta che
     * un campo viene modificato
     */
    protected class AzFormModificato extends FormModificatoAz {

        /**
         * Esegue l'azione
         * <p/>
         *
         * @param unEvento evento che causa l'azione da eseguire
         */
        public void formModificatoAz(FormModificatoEve unEvento) {
            try { // prova ad eseguire il codice
                int a = 87;
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }
    } // fine della classe interna


    /**
     * Inner class per gestire l'azione.
     */
    private class AzPortaleNuovoRec extends PortaleNuovoRecAz {

        /**
         * portaleNuovoRecAz, da PortaleNuovoRecLis.
         * </p>
         * Esegue l'azione <br>
         * Rimanda al metodo sovrascritto, nell'oggetto specifico
         * della classe che genera questo evento <br>
         * Sovrascritto nelle sottoclassi <br>
         *
         * @param unEvento evento che causa l'azione da eseguire <br>
         */
        public void portaleNuovoRecAz(PortaleNuovoRecEve unEvento) {
            try { // prova ad eseguire il codice
                /* invoca il metodo delegato della classe gestione eventi */
                esegui(PortaleBase.Evento.nuovoRecord);
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }
    } // fine della classe interna


    /**
     * Inner class per gestire l'azione.
     */
    private class AzPortaleAddRec extends PortaleAddRecAz {

        /**
         * portaleAddRecAz, da PortaleAddRecLis.
         * </p>
         * Esegue l'azione <br>
         * Rimanda al metodo sovrascritto, nell'oggetto specifico
         * della classe che genera questo evento <br>
         * Sovrascritto nelle sottoclassi <br>
         *
         * @param unEvento evento che causa l'azione da eseguire <br>
         */
        public void portaleAddRecAz(PortaleAddRecEve unEvento) {
            try { // prova ad eseguire il codice
                /* invoca il metodo delegato della classe gestione eventi */
                esegui(PortaleBase.Evento.aggiungiRecord);
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }

    } // fine della classe interna


    /**
     * Inner class per gestire l'azione.
     */
    private class AzPortaleModificaRec extends PortaleModificaRecAz {

        /**
         * portaleModificaRecAz, da PortaleModificaRecLis.
         * </p>
         * Esegue l'azione <br>
         * Rimanda al metodo sovrascritto, nell'oggetto specifico
         * della classe che genera questo evento <br>
         * Sovrascritto nelle sottoclassi <br>
         *
         * @param unEvento evento che causa l'azione da eseguire <br>
         */
        public void portaleModificaRecAz(PortaleModificaRecEve unEvento) {
            try { // prova ad eseguire il codice
                /* invoca il metodo delegato della classe gestione eventi */
                esegui(PortaleBase.Evento.modificaRecord);
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }
    } // fine della classe interna


    /**
     * Inner class per gestire l'azione.
     */
    private class AzPortaleDuplicaRec extends PortaleDuplicaRecAz {

        /**
         * portaleModificaRecAz, da PortaleModificaRecLis.
         * </p>
         * Esegue l'azione <br>
         * Rimanda al metodo sovrascritto, nell'oggetto specifico
         * della classe che genera questo evento <br>
         * Sovrascritto nelle sottoclassi <br>
         *
         * @param unEvento evento che causa l'azione da eseguire <br>
         */
        public void portaleDuplicaRecAz(PortaleDuplicaRecEve unEvento) {
            try { // prova ad eseguire il codice
                /* invoca il metodo delegato della classe gestione eventi */
                esegui(PortaleBase.Evento.duplicaRecord);
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }
    } // fine della classe interna


    /**
     * Inner class per gestire l'azione.
     */
    private class AzPortaleEliminaRec extends PortaleEliminaRecAz {

        /**
         * portaleEliminaRecAz, da PortaleEliminaRecLis.
         * </p>
         * Esegue l'azione <br>
         * Rimanda al metodo sovrascritto, nell'oggetto specifico
         * della classe che genera questo evento <br>
         * Sovrascritto nelle sottoclassi <br>
         *
         * @param unEvento evento che causa l'azione da eseguire <br>
         */
        public void portaleEliminaRecAz(PortaleEliminaRecEve unEvento) {
            try { // prova ad eseguire il codice
                /* invoca il metodo delegato della classe gestione eventi */
                esegui(PortaleBase.Evento.eliminaRecord);
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }
    } // fine della classe interna


    /**
     * Inner class per gestire l'azione.
     */
    private class AzPortaleLevaRec extends PortaleLevaRecAz {

        /**
         * portaleLevaRecAz, da PortaleLevaRecLis.
         * </p>
         * Esegue l'azione <br>
         * Rimanda al metodo sovrascritto, nell'oggetto specifico
         * della classe che genera questo evento <br>
         * Sovrascritto nelle sottoclassi <br>
         *
         * @param unEvento evento che causa l'azione da eseguire <br>
         */
        public void portaleLevaRecAz(PortaleLevaRecEve unEvento) {
            try { // prova ad eseguire il codice
                /* invoca il metodo delegato della classe gestione eventi */
                esegui(PortaleBase.Evento.rimuoviRecord);
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }

    } // fine della classe interna


    /**
     * Inner class per gestire l'azione.
     */
    private class AzPortaleListaSel extends PortaleListaSelAz {

        /**
         * portaleListaSelAz, da PortaleListaSelLis.
         * </p>
         * Esegue l'azione <br>
         * Rimanda al metodo sovrascritto, nell'oggetto specifico
         * della classe che genera questo evento <br>
         * Sovrascritto nelle sottoclassi <br>
         *
         * @param unEvento evento che causa l'azione da eseguire <br>
         */
        public void portaleListaSelAz(PortaleListaSelEve unEvento) {
            try { // prova ad eseguire il codice
                /* invoca il metodo delegato della classe gestione eventi */
                esegui(PortaleBase.Evento.listaSelezione);
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }
    } // fine della classe interna


    /**
     * Inner class per gestire l'azione.
     */
    private class AzPortaleListaCursore extends PortaleCursoreAz {

        /**
         * portaleFrecciaAltoAz, da PortaleCursoreLis.
         * </p>
         * Esegue l'azione <br>
         * Rimanda al metodo sovrascritto, nell'oggetto specifico
         * della classe che genera questo evento <br>
         * Sovrascritto nelle sottoclassi <br>
         *
         * @param unEvento evento che causa l'azione da eseguire <br>
         */
        public void portaleFrecciaAltoAz(PortaleCursoreEve unEvento) {
            frecciaAlto();
        }


        /**
         * portaleFrecciaBassoAz, da PortaleCursoreLis.
         * </p>
         * Esegue l'azione <br>
         * Rimanda al metodo sovrascritto, nell'oggetto specifico
         * della classe che genera questo evento <br>
         * Sovrascritto nelle sottoclassi <br>
         *
         * @param unEvento evento che causa l'azione da eseguire <br>
         */
        public void portaleFrecciaBassoAz(PortaleCursoreEve unEvento) {
            frecciaBasso();
        }


        /**
         * portalePagSuAz, da PortaleCursoreLis.
         * </p>
         * Esegue l'azione <br>
         * Rimanda al metodo sovrascritto, nell'oggetto specifico
         * della classe che genera questo evento <br>
         * Sovrascritto nelle sottoclassi <br>
         *
         * @param unEvento evento che causa l'azione da eseguire <br>
         */
        public void portalePagSuAz(PortaleCursoreEve unEvento) {
        }


        /**
         * portalePagGiuAz, da PortaleCursoreLis.
         * </p>
         * Esegue l'azione <br>
         * Rimanda al metodo sovrascritto, nell'oggetto specifico
         * della classe che genera questo evento <br>
         * Sovrascritto nelle sottoclassi <br>
         *
         * @param unEvento evento che causa l'azione da eseguire <br>
         */
        public void portalePagGiuAz(PortaleCursoreEve unEvento) {
        }


        /**
         * portaleHomeAz, da PortaleCursoreLis.
         * </p>
         * Esegue l'azione <br>
         * Rimanda al metodo sovrascritto, nell'oggetto specifico
         * della classe che genera questo evento <br>
         * Sovrascritto nelle sottoclassi <br>
         *
         * @param unEvento evento che causa l'azione da eseguire <br>
         */
        public void portaleHomeAz(PortaleCursoreEve unEvento) {
            home();
        }


        /**
         * portaleEndAz, da PortaleCursoreLis.
         * </p>
         * Esegue l'azione <br>
         * Rimanda al metodo sovrascritto, nell'oggetto specifico
         * della classe che genera questo evento <br>
         * Sovrascritto nelle sottoclassi <br>
         *
         * @param unEvento evento che causa l'azione da eseguire <br>
         */
        public void portaleEndAz(PortaleCursoreEve unEvento) {
            end();
        }
    } // fine della classe interna


    /**
     * Inner class per gestire l'azione.
     */
    private class AzCellaModificata extends ListaModCellaAz {

        /**
         * listaModCellaAz, da ListaModCellaLis.
         * </p>
         * Esegue l'azione <br>
         * Rimanda al metodo sovrascritto, nell'oggetto specifico
         * della classe che genera questo evento <br>
         * Sovrascritto nelle sottoclassi <br>
         *
         * @param unEvento evento che causa l'azione da eseguire <br>
         */
        public void listaModCellaAz(ListaModCellaEve unEvento) {
            /* variabili e costanti locali di lavoro */
            Campo campo;
            int codice;

            try { // prova ad eseguire il codice
                campo = unEvento.getCampo();
                codice = unEvento.getCodice();

                cellaModificata(campo, codice);
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }

    } // fine della classe interna


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

        /* quando il navigatore si modifica (aggiunta, modifica od eliminazione di un record) */
        statoModificato(NavStatoLis.class, NavStatoEve.class, NavStatoAz.class, "navStatoAz");

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


}// fine della classe
