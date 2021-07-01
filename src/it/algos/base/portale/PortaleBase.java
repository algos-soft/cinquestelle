/**
 * Title:     PortaleBase
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      19-mar-2004
 */
package it.algos.base.portale;

import it.algos.base.azione.Azione;
import it.algos.base.database.connessione.Connessione;
import it.algos.base.errore.Errore;
import it.algos.base.evento.BaseListener;
import it.algos.base.evento.Eventi;
import it.algos.base.evento.modulo.ModNascostoAz;
import it.algos.base.evento.modulo.ModNascostoEve;
import it.algos.base.evento.modulo.ModVisibileAz;
import it.algos.base.evento.modulo.ModVisibileEve;
import it.algos.base.evento.portale.PortaleAddRecAz;
import it.algos.base.evento.portale.PortaleAddRecEve;
import it.algos.base.evento.portale.PortaleAddRecLis;
import it.algos.base.evento.portale.PortaleCursoreAz;
import it.algos.base.evento.portale.PortaleCursoreEve;
import it.algos.base.evento.portale.PortaleCursoreLis;
import it.algos.base.evento.portale.PortaleDuplicaRecAz;
import it.algos.base.evento.portale.PortaleDuplicaRecEve;
import it.algos.base.evento.portale.PortaleDuplicaRecLis;
import it.algos.base.evento.portale.PortaleEliminaRecAz;
import it.algos.base.evento.portale.PortaleEliminaRecEve;
import it.algos.base.evento.portale.PortaleEliminaRecLis;
import it.algos.base.evento.portale.PortaleLevaRecAz;
import it.algos.base.evento.portale.PortaleLevaRecEve;
import it.algos.base.evento.portale.PortaleLevaRecLis;
import it.algos.base.evento.portale.PortaleListaSelAz;
import it.algos.base.evento.portale.PortaleListaSelEve;
import it.algos.base.evento.portale.PortaleListaSelLis;
import it.algos.base.evento.portale.PortaleModificaRecAz;
import it.algos.base.evento.portale.PortaleModificaRecEve;
import it.algos.base.evento.portale.PortaleModificaRecLis;
import it.algos.base.evento.portale.PortaleNuovoRecAz;
import it.algos.base.evento.portale.PortaleNuovoRecEve;
import it.algos.base.evento.portale.PortaleNuovoRecLis;
import it.algos.base.evento.portale.PortaleStatoAz;
import it.algos.base.evento.portale.PortaleStatoEve;
import it.algos.base.evento.portale.PortaleStatoLis;
import it.algos.base.finestra.Finestra;
import it.algos.base.finestra.FinestraPortale;
import it.algos.base.form.Form;
import it.algos.base.gestore.Gestore;
import it.algos.base.layout.Layout;
import it.algos.base.layout.LayoutFlusso;
import it.algos.base.libreria.Lib;
import it.algos.base.lista.Lista;
import it.algos.base.modulo.Modulo;
import it.algos.base.navigatore.Navigatore;
import it.algos.base.navigatore.info.Info;
import it.algos.base.navigatore.stato.StatoPortale;
import it.algos.base.pannello.PannelloBase;
import it.algos.base.progetto.Progetto;
import it.algos.base.scheda.Scheda;
import it.algos.base.toolbar.ToolBar;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JMenu;
import javax.swing.JPanel;
import javax.swing.event.EventListenerList;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.PopupMenuListener;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Window;
import java.awt.event.ActionListener;
import java.awt.event.FocusListener;
import java.awt.event.ItemListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.WindowListener;
import java.util.Iterator;
import java.util.LinkedHashMap;

/**
 * Implementazione di un contenitore che puo' contenere due oggetti:
 * - una toolBar
 * - un componente principale
 * <p/>
 * La toolbar può essere posizionata a nord, sud, est, ovest rispetto
 * al componente principale, e viene orientata orizzontalmente o
 * verticalmente in base alla sua posizione.
 * La toolbar può anche non essere utilizzata.
 * Vedi Portale.setPosToolbar().
 * <p/>
 * Un Portale può essere automaticamente inserito in una finestra
 * tramite il comando setUsaFinestra().
 * La finestra è di tipo dialogo modale se si attiva il flag setUsaDialogo().
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 19-mar-2004 ore 14.02.18
 */
public abstract class PortaleBase extends PannelloBase implements Portale {

    /**
     * usata in fase di sviluppo per vedere l'oggetto facilmente
     */
    protected static final boolean DEBUG = false;

    /**
     * barra dei bottoni
     */
    private ToolBar toolBar;

    /**
     * componente principale da visualizzare nel Portale
     */
    private JComponent compMain;

    /**
     * contenitore della toolbar in uso
     */
    private JPanel compTool;

    /**
     * Flag per usare la toolbar
     */
    private boolean usaToolbar = true;

    /**
     * Flag per usare i pulsanti standard nella toolbar
     */
    private boolean usaPulsantiStandard = true;


    /**
     * Posizione della toolbar all'interno del Portale.
     *
     * @see ToolBar.Pos
     */
    private ToolBar.Pos posToolbar;

    /**
     * collezione chiave-valore per le azioni del portale.
     * le eventuali finestre fanno riferimento a queste azioni per
     * costruire la propria barra menu
     */
    private LinkedHashMap<String, Azione> azioni;

    /**
     * gestore dello stato del Portale
     */
    protected StatoPortale stato;

    /**
     * Riferimento al Navigatore proprietario di questo portale <br>
     */
    private Navigatore navigatore;

    /**
     * Pacchetto di informazioni sullo stato del Portale
     * Creato alla creazione del portale
     * Regolato in base allo stato corrente tel Portale
     * ogni volta che viene letto con il metodo getInfoStato
     */
    private Info pacchettoInfo;

    /**
     * A list of event listeners for this component.
     */
    private EventListenerList listaListener;

    /**
     * eventuale finestra del portale
     * tipicamente contiene il portale Navigatore
     */
    private Finestra finestra;

    /**
     * eventuale finestra di dialogo del portale
     * usata in alternativa alla finestra
     */
    private JDialog finestraDialogo;

    /**
     * flag - determina se il portale deve creare una finestra propria
     * (di tipo finestra o di tipo dialogo)
     */
    private boolean usaFinestra = false;

    /**
     * Flag per usare una finestra di tipo Dialogo anziché di tipo Finestra
     */
    private boolean usaDialogo = false;

    /**
     * classe interna per azione/evento/listener
     */
    private ModuloVisibile modVisibile;

    /**
     * classe interna per azione/evento/listener
     */
    private ModuloNascosto modNascosto;

    /**
     * flag di controllo avvenuta inizializzazione
     */
    private boolean inizializzato;


    /**
     * Costruttore base senza parametri. <br>
     * Indispensabile anche se non viene utilizzato
     * (anche solo per compilazione in sviluppo) <br>
     * Rimanda al costruttore completo <br>
     * Utilizza eventuali valori di default <br>
     */
    public PortaleBase() {
        /* rimanda al costruttore di questa classe */
        this(null);
    }// fine del metodo costruttore base


    /**
     * Costruttore completo con parametri. <br>
     *
     * @param unNavigatore navigatore che gestisce questo pannello
     */
    public PortaleBase(Navigatore unNavigatore) {
        /* rimanda al costruttore della superclasse */
        super();

        /* regola le variabili di istanza coi parametri */
        this.setNavigatore(unNavigatore);

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
        try {    // prova ad eseguire il codice

            /* crea e aggiunge le azioni */
            this.setAzioni(new LinkedHashMap<String, Azione>());
            this.addAzioni();
            this.setModVisibile(new ModuloVisibile());
            this.setModNascosto(new ModuloNascosto());

            /* posizione di default della ToolBar */
            this.setPosToolbar(ToolBar.Pos.ovest);

            /* crea il componente principale */
            this.creaCompMain();

            /* componenti grafiche */
            /* crea la toolbar */
            this.creaCompTool();
            this.creaToolbar();

            /* crea una finestra per il Portale */
            this.creaFinestra();

            /* Crea una finestra di dialogo a uso di questo portale */
            this.creaDialogo();

            /* se e' attivo il debug usa sfondo e colori */
            if (DEBUG) {
                this.setOpaque(true);
                this.setBackground(Color.gray);
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }// fine del metodo inizia


    /**
     * Regolazioni dinamiche dell'oggetto.
     * <p/>
     * Regola le caratteristiche dinamiche in base alla impostazione
     * corrente delle caratteristiche statiche <br>
     * Metodo invocato dalla classe che crea questo oggetto (di norma) <br>
     * Viene eseguito una sola volta <br>
     * Sovrascritto nelle sottoclassi <br>
     */
    public void inizializza() {
        try { // prova ad eseguire il codice

//            super.inizializza();

            /* lista dei propri eventi */
            this.setListaListener(new EventListenerList());

            /* inizializzazione della eventuale finestra */
            if (this.getFinestra() != null) {
                this.getFinestra().inizializza();
            }// fine del blocco if

            /* inizializza la eventuale toolbar */
            this.inizializzaToolBar();

            /* aggiorna la visibilità della toolbar in base al flag */
            this.setUsaToolbar(this.isUsaToolbar());

            /* regola le azioni */
            this.regolaAzioni();

            /* Costruisce graficamente il Portale */
            this.impagina();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Regolazioni di ri-avvio.
     * <p/>
     * Metodo chiamato da altre classi ogni volta che questo oggetto deve
     * <i>ripartire</i>, per essere sicuri che sia <i>pulito</i> <br>
     * Viene eseguito tutte le volte che necessita <br>
     * <p/>
     * Sovrascritto nelle sottoclassi
     *
     * @param codice del record da caricare
     */
    public void avvia(int codice) {

        /* inizializza se non ancora inizializzato */
        if (!this.isInizializzato()) {
            this.inizializza();
        }// fine del blocco if

    }// fine del metodo avvia


    /**
     * Regolazioni di ri-avvio.
     * <p/>
     * Metodo chiamato da altre classi ogni volta che questo oggetto deve
     * <i>ripartire</i>, per essere sicuri che sia <i>pulito</i> <br>
     * Viene eseguito tutte le volte che necessita <br>
     * <p/>
     * Sovrascritto nelle sottoclassi <br>
     */
    public void avvia() {

        /* inizializza se non ancora inizializzato */
        if (!this.isInizializzato()) {
            this.inizializza();
        }// fine del blocco if

    }// fine del metodo avvia


    /**
     * Sincronizza gli elementi.
     * <p/>
     * Metodo invocato dal metodo fire() <br>
     * Può essere invocato anche da altri metodi interni <br>
     * (in salita) <b>
     */
    public void sincroInterno() {
        Info info;
        try { // prova ad eseguire il codice
            info = this.getInfoStato();
            if (info != null) {
                info.avvia();
                this.setInfoStato(info);
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Sincronizza gli elementi.
     * <p/>
     * Metodo invocato da sincroEsterno del contenitore superiore <br>
     * (in discesa) <b>
     */
    public void sincroEsterno() {
    }


    /**
     * Sincronizza la GUI del portale.
     * <p/>
     * Recupera le informazioni sullo stato del portale <br>
     * Regola lo stato del portale <br>
     * Invoca i metodi delegati nelle sottoclassi <br>
     */
    public void sincronizza() {
    }


    /**
     * Costruisce graficamente il Portale.
     * <p/>
     * - svuota il Portale
     * - assegna il Layout
     * - aggiunge i componenti
     */
    protected void impagina() {
        /* variabili e costanti locali di lavoro */
        Component compMain;
        int orientamento;

        try {    // prova ad eseguire il codice

//            /* svuota il portale */
//            this.removeAll();
//
//            /* aggiunge i componenti */
//            compMain = this.getCompMain();
//            if (compMain != null) {
//
//                switch (this.getPosToolbar()) {
//                    case nord:
//                        orientamento = Layout.ORIENTAMENTO_VERTICALE;
//                        this.assegnaLayout(orientamento);
//                        this.aggiungiToolBar(false);
//                        this.add(compMain);
//                        break;
//                    case est:
//                        orientamento = Layout.ORIENTAMENTO_ORIZZONTALE;
//                        this.assegnaLayout(orientamento);
//                        this.add(compMain);
//                        this.aggiungiToolBar(true);
//                        break;
//                    case sud:
//                        orientamento = Layout.ORIENTAMENTO_VERTICALE;
//                        this.assegnaLayout(orientamento);
//                        this.add(compMain);
//                        this.aggiungiToolBar(false);
//                        break;
//                    case ovest:
//                        orientamento = Layout.ORIENTAMENTO_ORIZZONTALE;
//                        this.assegnaLayout(orientamento);
//                        this.aggiungiToolBar(true);
//                        this.add(compMain);
//                        break;
//                    case nessuna:
//                        orientamento = Layout.ORIENTAMENTO_VERTICALE;
//                        this.assegnaLayout(orientamento);
//                        this.add(compMain);
//                        break;
//                    default: // caso non definito
//                        orientamento = Layout.ORIENTAMENTO_VERTICALE;
//                        this.assegnaLayout(orientamento);
//                        this.add(compMain);
//                        this.aggiungiToolBar(false);
//                        break;
//                } // fine del blocco switch
//
//            }// fine del blocco if

            /* svuota il portale */
            this.removeAll();

            /* aggiunge i componenti */
            compMain = this.getCompMain();

            switch (this.getPosToolbar()) {
                case nord:
                    orientamento = Layout.ORIENTAMENTO_VERTICALE;
                    this.assegnaLayout(orientamento);
                    this.aggiungiToolBar(false);
                    if (compMain != null) {
                        this.add(compMain);
                    }// fine del blocco if
                    break;
                case est:
                    orientamento = Layout.ORIENTAMENTO_ORIZZONTALE;
                    this.assegnaLayout(orientamento);
                    if (compMain != null) {
                        this.add(compMain);
                    }// fine del blocco if
                    this.aggiungiToolBar(true);
                    break;
                case sud:
                    orientamento = Layout.ORIENTAMENTO_VERTICALE;
                    this.assegnaLayout(orientamento);
                    if (compMain != null) {
                        this.add(compMain);
                    }// fine del blocco if
                    this.aggiungiToolBar(false);
                    break;
                case ovest:
                    orientamento = Layout.ORIENTAMENTO_ORIZZONTALE;
                    this.assegnaLayout(orientamento);
                    this.aggiungiToolBar(true);
                    if (compMain != null) {
                        this.add(compMain);
                    }// fine del blocco if
                    break;
                case nessuna:
                    orientamento = Layout.ORIENTAMENTO_VERTICALE;
                    this.assegnaLayout(orientamento);
                    if (compMain != null) {
                        this.add(compMain);
                    }// fine del blocco if
                    break;
                default: // caso non definito
                    orientamento = Layout.ORIENTAMENTO_VERTICALE;
                    this.assegnaLayout(orientamento);
                    if (compMain != null) {
                        this.add(compMain);
                    }// fine del blocco if
                    this.aggiungiToolBar(false);
                    break;
            } // fine del blocco switch


        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Crea la finestra per questo Portale.
     * <p/>
     * Crea una finestra Portale <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     * Invocato dal ciclo inizializza() <br>
     */
    protected void creaFinestra() {

        /* variabili e costanti locali di lavoro */
        Finestra unaFinestra;

        try {    // prova ad eseguire il codice

            /* crea e registra l'istanza della finestra */
            unaFinestra = new FinestraPortale(this);
            this.setFinestra(unaFinestra);

            /* di default rende la finestra ridimensionabile */
            unaFinestra.getFinestraBase().setResizable(true);

        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } // fine del blocco try-catch

    }


    /**
     * Assegna un voce alla finestra del Portale.
     * <p/>
     * Il voce viene assegnato alla finestre e al dialogo
     *
     * @param titolo da assegnare
     */
    public void setTitoloFinestra(String titolo) {
        /* variabili e costanti locali di lavoro */
        Finestra finestra;
        JDialog dialogo;

        try {    // prova ad eseguire il codice
            finestra = this.getFinestra();
            if (finestra != null) {
                finestra.setTitolo(titolo);
            }// fine del blocco if
            dialogo = this.getFinestraDialogo();
            if (dialogo != null) {
                dialogo.setTitle(titolo);
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Regola e assegna il layout al Portale.
     * <p/>
     * Crea un layout orientato in senso perpendicolare
     * a quello della toolbar (se presente).<br>
     * Se la toolbar non e' usata, crea un layout verticale.<br>
     *
     * @param orientamento del layout
     */
    private void assegnaLayout(int orientamento) {
        /* variabili e costanti locali di lavoro */
        Layout layout;

        try {    // prova ad eseguire il codice
            layout = new LayoutPortale(this, orientamento);
            this.setLayout(layout);
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Aggiunge una scheda alla collezione.
     * <p/>
     * Regola la scheda appena aggiunta come corrente <br>
     *
     * @param unaScheda da aggiungere
     *
     * @return il nome definitivo assegnato alla scheda
     */
    public String addScheda(Scheda unaScheda) {
        return "";
    }


    /**
     * Aggiunge una scheda alla collezione.
     * <p/>
     * Regola la scheda appena aggiunta come corrente <br>
     * Se il nomeChiave è nullo o vuoto, usa come chiave un numero progressivo <br>
     *
     * @param nomeChiave della scheda
     * @param unaScheda da aggiungere
     *
     * @return il nome definitivo assegnato alla scheda
     */
    public String addScheda(String nomeChiave, Scheda unaScheda) {
        return "";
    }


    /**
     * Aggiunge un set alla collezione interna.
     * <p/>
     *
     * @param nomeSet da recuperare dal modello
     *
     * @return lo stesso nome del set passato come parametro
     */
    public String addSet(String nomeSet) {
        return nomeSet;
    }


    /**
     * Ritorna il pacchetto di informazioni sullo stato del portale.
     * <p/>
     * Il pacchetto puo' non essere aggiornato.<br>
     * Per aggiornarlo chiamare il metodo avvia() del pacchetto.<br>
     *
     * @return un pacchetto con le informazioni
     */
    public Info getInfoStato() {
        /* valore di ritorno */
        return this.pacchettoInfo;
    }


    /**
     * Regola lo stato del portale.
     * <p/>
     * Sincronizza la GUI del Portale <br>
     * Regola lo stato della Lista (azioni ed altro) <br>
     * Sovrascritto nelle sottoclassi <br>
     *
     * @param info il pacchetto di informazioni sullo stato della lista
     */
    public void setInfoStato(Info info) {
    }


    /**
     * Crea e registra una finestra di dialogo a uso di questo portale.
     * <p/>
     */
    private void creaDialogo() {
        /* variabili e costanti locali di lavoro */
        JDialog dialogo;

        try {    // prova ad eseguire il codice

            dialogo = new JDialog();
            dialogo.setModal(true);
            dialogo.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
            this.setFinestraDialogo(dialogo);

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Crea e registra il componente principale.
     * <p/>
     */
    private void creaCompMain() {
        /* variabili e costanti locali di lavoro */
        JPanel pan;

        try { // prova ad eseguire il codice
            pan = new JPanel();
            pan.setOpaque(false);
            pan.setLayout(new BorderLayout());
            this.setCompMain(pan);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    } // fine del metodo


    /**
     * Crea e registra il contenitore della tool bar.
     * <p/>
     */
    private void creaCompTool() {
        /* variabili e costanti locali di lavoro */
        JPanel pan;

        try { // prova ad eseguire il codice
            pan = new JPanel();
            pan.setOpaque(false);
            pan.setLayout(new BorderLayout());
            this.setCompTool(pan);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    } // fine del metodo


    /**
     * Crea la eventuale toolbar.
     * <p/>
     * Sovrascritto dalle sottoclassi.<br>
     */
    protected void creaToolbar() {
    } // fine del metodo


    /**
     * Aggiunge la ToolBar al Portale.
     * <p/>
     * - Aggiunge la toolbar solo se previsto e se questa esiste.<br>
     *
     * @param verticale true per toolbar verticale,
     * false per toolbar orizzontale
     */
    protected void aggiungiToolBar(boolean verticale) {
        /* variabili e costanti locali di lavoro */
        ToolBar tb;
        JComponent comp;
        JPanel compTool;

        try {    // prova ad eseguire il codice

            /* controlla se la toolbar e' prevista */
            tb = this.getToolBar();
            if (tb != null) {
                tb.setVerticale(verticale);
                comp = tb.getToolBar();
                comp.setAlignmentX(0);

                compTool = this.getCompTool();
                compTool.add(comp);
                this.add(compTool);

                if (verticale) {
                    Lib.Comp.bloccaLarMax(compTool);
                } else {
                    Lib.Comp.bloccaAltMax(compTool);
                }// fine del blocco if-else

            }// fine del blocco if


        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    } // fine del metodo

//    /**
//     * Uso della toolbar.
//     *
//     * @param tipo della toolbar da utilizzare
//     */
//    public void usaToolBar(TipoToolbar tipo) {
//    } // fine del metodo


    /**
     * Inizializza la toolbar (se presente).<br>
     */
    protected void inizializzaToolBar() {
        /* variabili e costanti locali di lavoro */
        ToolBar toolbar;

        try {    // prova ad eseguire il codice
            toolbar = this.getToolBar();
            if (toolbar != null) {
                toolbar.inizializza();
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } // fine del blocco try-catch

    } // fine del metodo


    /**
     * Aggiunge le azioni del portale.
     * <p/>
     * Metodo invocato dal ciclo inizia del costruttore <br>
     * Crea ogni singola azione per questo portale (anche se non viene usata) <br>
     * La singola azione viene creata dal metodo delegato della superclasse, che
     * la clona dal Progetto <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     */
    protected void addAzioni() {
        try { // prova ad eseguire il codice

            /* gruppo di azioni sul testo dei campi */
            this.addAzione(Azione.CARATTERE);
            this.addAzione(Azione.ENTER);
            this.addAzione(Azione.ESCAPE);
            this.addAzione(Azione.ENTRATA_CAMPO);
            this.addAzione(Azione.USCITA_CAMPO);

            /* gruppo di azioni sui campi */
            this.addAzione(Azione.MOUSE_CLICK);
            this.addAzione(Azione.MOUSE_DOPPIO_CLICK);
            this.addAzione(Azione.ENTRATA_POPUP);
            this.addAzione(Azione.USCITA_POPUP);
            this.addAzione(Azione.ITEM_MODIFICATO);
            this.addAzione(Azione.RADIO_MODIFICATO);
            this.addAzione(Azione.POPUP_MODIFICATO);
            this.addAzione(Azione.SELEZIONE_MODIFICATA);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Regola le azioni del portale.
     * <p/>
     * Metodo invocato dal ciclo Inizializza() <br>
     * Spazzola tutta la collezione di azioni <br>
     * Invoca il metodo avvia di ogni azione, passandogli il parametro <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     */
    protected void regolaAzioni() {
        /* variabili e costanti locali di lavoro */
        Iterator unGruppo;
        Azione unAzione;
        Modulo modulo;
        Gestore unGestore;

        try {    // prova ad eseguire il codice
            modulo = this.getModulo();
            if (modulo != null) {
                unGestore = modulo.getGestore();
                if (unGestore != null) {
                    unGruppo = this.getAzioni().values().iterator();
                    while (unGruppo.hasNext()) {
                        unAzione = (Azione)unGruppo.next();
                        unAzione.setPortale(this);
                        unAzione.avvia(unGestore);
                    } /* fine del blocco while */
                } else {
                    throw new Exception("Gestore nullo.");
                }// fine del blocco if-else
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Aggiunge un bottone alla Toolbar.
     * <p/>
     * Aggiunge l'azione alla collezione del Portale <br>
     * Usa la chiave interna all'azione.
     *
     * @param azione specifica
     */
    public void addBottoneToolbar(Azione azione) {
        /* variabili e costanti locali di lavoro */
        String chiave;
        ToolBar tb;

        try { // prova ad eseguire il codice

            /* recupera la chiave e controlla che sia valida */
            chiave = azione.getChiave();
            if (!Lib.Testo.isValida(chiave)) {
                throw new Exception("Manca la chiave dell'azione.");
            }// fine del blocco if

            /* aggiunge l'azione alla collezione azioni del portale */
            this.addAzione(chiave, azione);

            /* aggiunge il bottone alla toolbar */
            tb = this.getToolBar();
            if (tb != null) {
                tb.addBottone(azione);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Aggiunge un bottone alla toolbar.
     * <p/>
     *
     * @param icona per il bottone
     * @param tooltip per il bottone
     * @param azione da invocare quando il bottone viene premuto
     *
     * @return il bottone aggiunto
     */
    public JButton addBottone(Icon icona, String tooltip, Action azione) {
        /* variabili e costanti locali di lavoro */
        JButton bot = null;
        ToolBar toolbar;

        try { // prova ad eseguire il codice

            /* aggiunge il bottone stampa fatture alla toolbar */
            bot = new JButton(azione);
            bot.setIcon(icona);
            bot.setBorderPainted(false);
            bot.setOpaque(false);
            bot.setToolTipText(tooltip);
            bot.setFocusable(false);
            bot.setMargin(new Insets(0, 0, 0, 0));
            toolbar = this.getToolBar();
            if (toolbar != null) {
                toolbar.getToolBar().add(bot);
            }// fine del blocco if

//            AzioneBase unAzione=null;
//            unAzione = new AzioneBase();
//            unAzione.setIconaPiccola(icona);
//            unAzione.setIconaMedia(icona);
//            unAzione.getAzione().setIcona
//            unAzione.setTooltip(tooltip);
//            toolbar = this.getToolBar();
//            if (toolbar!=null) {
//                toolbar.addBottone(unAzione);
//            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return bot;

    }


    /**
     * Aggiunge una singola azione al portale.
     * <p/>
     * Aggiunge l'azione alla collezione (chiave-valore) di Azioni
     * del Portale <br>
     *
     * @param unAzione specifica
     */
    protected void addAzione(Azione unAzione) {
        /* variabili e costanti locali di lavoro */
        String nomeAzione;

        try { // prova ad eseguire il codice
            if (unAzione != null) {
                nomeAzione = unAzione.getChiave();
                this.getAzioni().put(nomeAzione, unAzione);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Aggiunge una singola azione al portale.
     * <p/>
     * Aggiunge l'azione alla collezione (chiave-valore) di Azioni
     * del Portale <br>
     *
     * @param unAzione specifica
     * @param chiave per la collezione
     */
    public void addAzione(String chiave, Azione unAzione) {
        try { // prova ad eseguire il codice
            this.getAzioni().put(chiave, unAzione);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Aggiunge la singola azione al portale.
     * <p/>
     * Usa il nome chiave dell'azione per recuperarla dal Progetto <br>
     * Se la trova, clona l'azione <br>
     * Se creata, aggiunge l'azione alla collezione (chiave-valore) di Azioni
     * del Portale <br>
     *
     * @param nomeAzione chiave per recuperare l'azione dal Progetto
     */
    protected void addAzione(String nomeAzione) {
        /* variabili e costanti locali di lavoro */
        Azione unAzione;

        try { // prova ad eseguire il codice
            unAzione = Progetto.getAzioneClonata(nomeAzione);

            if (unAzione != null) {
                this.addAzione(unAzione);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Aggiunge una azione specifica al portale.
     * <p/>
     * Usa il nome chiave dell'azione per recuperarla dal Progetto <br>
     * Se la trova, clona l'azione <br>
     * Se creata, aggiunge l'azione alla collezione (chiave-valore) di Azioni
     * del Portale <br>
     *
     * @param nomeAzione chiave per recuperare l'azione dal Progetto
     */
    public void addAzioneProgetto(String nomeAzione) {
        /* variabili e costanti locali di lavoro */
        Azione unAzione;
        Gestore gestore;

        try { // prova ad eseguire il codice
            unAzione = Progetto.getAzioneClonata(nomeAzione);

            if (unAzione != null) {

                this.getAzioni().put(nomeAzione, unAzione);

                this.getToolBar().addBottone(unAzione);
                unAzione.setPortale(this);
                gestore = this.getModulo().getGestore();
                if (gestore != null) {
                    unAzione.avvia(gestore);
                }// fine del blocco if

            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Ritorna la toolbar contenuta nel portale.
     * <p/>
     *
     * @return la toolbar
     */
    public ToolBar getToolBar() {
        return toolBar;
    }


    public void setToolBar(ToolBar toolBar) {
        this.toolBar = toolBar;
    }


    public JComponent getCompMain() {
        return compMain;
    }


    protected JPanel getCompTool() {
        return compTool;
    }


    private void setCompTool(JPanel compTool) {
        this.compTool = compTool;
    }


    /**
     * Assegna il compnente principale al portale.
     *
     * @param compMain componente grafico
     */
    public void setCompMain(JComponent compMain) {
        this.compMain = compMain;
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
        ToolBar tb;

        try { // prova ad eseguire il codice
            tb = this.getToolBar();
            if (tb != null) {
                usa = tb.isUsaFrecce();
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return usa;
    }


    public void setUsaFrecceSpostaOrdineLista(boolean usaFrecce) {
        /* variabili e costanti locali di lavoro */
        ToolBar tb;

        try { // prova ad eseguire il codice
            tb = this.getToolBar();
            if (tb != null) {
                tb.setUsaFrecce(usaFrecce);
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
        ToolBar tb;

        try { // prova ad eseguire il codice
            tb = this.getToolBar();
            if (tb != null) {
                tb.setUsaDuplica(flag);
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
        ToolBar tb;

        try { // prova ad eseguire il codice
            tb = this.getToolBar();
            if (tb != null) {
                tb.setUsaRicerca(flag);
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
        ToolBar tb;

        try { // prova ad eseguire il codice
            tb = this.getToolBar();
            if (tb != null) {
                tb.setUsaProietta(flag);
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
        return false;
    }


    /**
     * Abilita l'uso del pulsante Preferito nella lista.
     * <p/>
     *
     * @param flag per usare il pulsante
     */
    public void setUsaPreferito(boolean flag) {
    }


    public Azione getAzione(String nomeChiave) {
        /* variabili e costanti locali di lavoro */
        LinkedHashMap<String, Azione> azioni;
        Azione azione = null;

        try { // prova ad eseguire il codice
            azioni = this.getAzioni();
            azione = azioni.get(nomeChiave);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return azione;
    }


    public LinkedHashMap<String, Azione> getAzioni() {
        return azioni;
    }


    private void setAzioni(LinkedHashMap<String, Azione> azioni) {
        this.azioni = azioni;
    }


    /**
     * Restituisce un'azione che implementa l'interfaccia ActionListener.
     * <p/>
     * Recupera l'azione generica <br>
     * Esegue il casting specifico <br>
     *
     * @param unaChiave nome interno dell'azione
     *
     * @return oggetto di tipo specifico per il Listener
     */
    public ActionListener getAzAction(String unaChiave) {
        /* variabili e costanti locali di lavoro */
        Azione azione;
        ActionListener listener = null;

        try { // prova ad eseguire il codice

            azione = this.getAzione(unaChiave);
            listener = (ActionListener)azione;

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return listener;
    }


    /**
     * Restituisce un'azione che implementa l'interfaccia FocusListener.
     * <p/>
     * Recupera l'azione generica <br>
     * Esegue il casting specifico <br>
     *
     * @param unaChiave nome interno dell'azione
     *
     * @return oggetto di tipo specifico per il Listener
     */
    public FocusListener getAzFocus(String unaChiave) {
        /* variabili e costanti locali di lavoro */
        Azione azione;
        FocusListener listener = null;

        try { // prova ad eseguire il codice

            azione = this.getAzione(unaChiave);
            listener = (FocusListener)azione;

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return listener;
    }


    /**
     * Restituisce un'azione che implementa l'interfaccia ItemListener.
     * <p/>
     * Recupera l'azione generica <br>
     * Esegue il casting specifico <br>
     *
     * @param unaChiave nome interno dell'azione
     *
     * @return oggetto di tipo specifico per il Listener
     */
    public ItemListener getAzItem(String unaChiave) {
        /* variabili e costanti locali di lavoro */
        Azione azione;
        ItemListener listener = null;

        try { // prova ad eseguire il codice

            azione = this.getAzione(unaChiave);
            listener = (ItemListener)azione;

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return listener;
    }


    /**
     * Restituisce un'azione che implementa l'interfaccia KeyListener.
     * <p/>
     * Recupera l'azione generica <br>
     * Esegue il casting specifico <br>
     *
     * @param unaChiave nome interno dell'azione
     *
     * @return oggetto di tipo specifico per il Listener
     */
    public KeyListener getAzKey(String unaChiave) {
        /* variabili e costanti locali di lavoro */
        Azione azione;
        KeyListener listener = null;

        try { // prova ad eseguire il codice

            azione = this.getAzione(unaChiave);
            listener = (KeyListener)azione;

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return listener;
    }


    /**
     * Restituisce un'azione che implementa l'interfaccia ListSelectionListener.
     * <p/>
     * Recupera l'azione generica <br>
     * Esegue il casting specifico <br>
     *
     * @param unaChiave nome interno dell'azione
     *
     * @return oggetto di tipo specifico per il Listener
     */
    public ListSelectionListener getAzListSelection(String unaChiave) {
        /* variabili e costanti locali di lavoro */
        Azione azione;
        ListSelectionListener listener = null;

        try { // prova ad eseguire il codice

            azione = this.getAzione(unaChiave);
            listener = (ListSelectionListener)azione;

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return listener;
    }


    /**
     * Restituisce un'azione che implementa l'interfaccia MouseListener.
     * <p/>
     * Recupera l'azione generica <br>
     * Esegue il casting specifico <br>
     *
     * @param unaChiave nome interno dell'azione
     *
     * @return oggetto di tipo specifico per il Listener
     */
    public MouseListener getAzMouse(String unaChiave) {
        /* variabili e costanti locali di lavoro */
        Azione azione;
        MouseListener listener = null;

        try { // prova ad eseguire il codice

            azione = this.getAzione(unaChiave);
            listener = (MouseListener)azione;

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return listener;
    }


    /**
     * Restituisce un'azione che implementa l'interfaccia PopupMenuListener.
     * <p/>
     * Recupera l'azione generica <br>
     * Esegue il casting specifico <br>
     *
     * @param unaChiave nome interno dell'azione
     *
     * @return oggetto di tipo specifico per il Listener
     */
    public PopupMenuListener getAzPopupMenu(String unaChiave) {
        /* variabili e costanti locali di lavoro */
        Azione azione;
        PopupMenuListener listener = null;

        try { // prova ad eseguire il codice

            azione = this.getAzione(unaChiave);
            listener = (PopupMenuListener)azione;

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return listener;
    }


    /**
     * Restituisce un'azione che implementa l'interfaccia WindowListener.
     * <p/>
     * Recupera l'azione generica <br>
     * Esegue il casting specifico <br>
     *
     * @param unaChiave nome interno dell'azione
     *
     * @return oggetto di tipo specifico per il Listener
     */
    public WindowListener getAzWindow(String unaChiave) {
        /* variabili e costanti locali di lavoro */
        Azione azione;
        WindowListener listener = null;

        try { // prova ad eseguire il codice

            azione = this.getAzione(unaChiave);
            listener = (WindowListener)azione;

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return listener;
    }


    /**
     * restituisce una istanza concreta.
     *
     * @return istanza di <code>PortaleBase</code>
     */
    public PortaleBase getPortale() {
        return this;
    }


    public Lista getLista() {
        return null;
    }


    /**
     * Assegna una Lista al portale <p>
     * Regola il riferimento al Portale nella Lista
     *
     * @param lista la lista da assegnare
     */
    public void setLista(Lista lista) {
    }


    public void setNomeVista(String nomeVista) {
    }


    /**
     * Ritorna la scheda corrente.
     * <p/>
     * E' la scheda attualmente visualizzata da questo Portale.
     *
     * @return la scheda corrente
     */
    public Scheda getSchedaCorrente() {
        return null;
    }


    /**
     * Posizione della toolbar all'interno del Portale.
     * <p/>
     *
     * @return la posizione
     *
     * @see ToolBar.Pos
     */
    protected ToolBar.Pos getPosToolbar() {
        return posToolbar;
    }


    public boolean isUsaToolbar() {
        return usaToolbar;
    }


    /**
     * Abilita l'uso della toolbar.
     * <p/>
     *
     * @param flag per usare la toolbar
     */
    public void setUsaToolbar(boolean flag) {
        /* variabili e costanti locali di lavoro */
        ToolBar tb;

        try { // prova ad eseguire il codice

            this.usaToolbar = flag;

            tb = this.getToolBar();
            if (tb != null) {
                tb.getToolBar().setVisible(flag);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Posizione della toolbar all'interno del Portale.
     * <p/>
     *
     * @param pos la posizione
     *
     * @see ToolBar.Pos
     */
    public void setPosToolbar(ToolBar.Pos pos) {
        this.posToolbar = pos;
    }


    public Navigatore getNavigatore() {
        return navigatore;
    }


    public void setNavigatore(Navigatore navigatore) {
        this.navigatore = navigatore;
    }


    /**
     * Ritorna il Modulo del Portale.
     * <p/>
     *
     * @return il modulo del Portale
     */
    public Modulo getModulo() {
        /* variabili e costanti locali di lavoro */
        Modulo modulo = null;
        Navigatore navigatore;

        try {    // prova ad eseguire il codice
            navigatore = this.getNavigatore();
            if (navigatore != null) {
                modulo = navigatore.getModulo();
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return modulo;
    }


    /**
     * Ritorna il form contenuto nel Portale.
     * <p/>
     *
     * @return il form contenuto nel Portale, null se il portale
     *         non contiene un form
     */
    public Form getForm() {
        return null;
    }


    /**
     * Ritorna la connessione di questo portale.
     * <p/>
     *
     * @return la connessione utilizzata dal Portale
     */
    public Connessione getConnessione() {
        /* variabili e costanti locali di lavoro */
        Connessione conn = null;
        Navigatore nav;

        try {    // prova ad eseguire il codice
            nav = this.getNavigatore();
            if (nav != null) {
                conn = nav.getConnessione();
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return conn;
    }


    public StatoPortale getStato() {
        return stato;
    }


    public void setStato(StatoPortale stato) {
        this.stato = stato;
    }


    /**
     * ritorna il pacchetto di informazioni del Portale
     * <p/>
     *
     * @return il pacchetto di informazioni
     */
    protected Info getPacchettoInfo() {
        return pacchettoInfo;
    }


    protected void setPacchettoInfo(Info pacchettoInfo) {
        this.pacchettoInfo = pacchettoInfo;
    }


    /**
     * Abilita o disabilita  l'uso della status bar nella Lista.
     * <p/>
     * Sovrascritto dalle sottoclassi.
     *
     * @param usaStatusBar true per usare la status bar, false per non usarla
     */
    public void setUsaStatusBar(boolean usaStatusBar) {
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
        try { // prova ad eseguire il codice
            if (this.getToolBar() != null) {
                this.getToolBar().setTipoIcona(unTipoIcona);
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }



    /**
     * Abilita l'uso dei pulsanti standard nella toolbar
     * <p/>
     * Il dettaglio su quali pulsanti usare si regola con i metodi specifici per ogni pulsante.
     *
     * @return flag di uso dei pulsanti standard
     */
    public boolean isUsaPulsantiStandard() {
        return usaPulsantiStandard;
    }

    /**
     * Abilita l'uso dei pulsanti standard nella toolbar
     * <p/>
     * Se false, i pulsanti standard non vengono aggiunti alla toolbar
     * Il dettaglio su quali pulsanti usare si regola con i metodi specifici per ogni pulsante.
     *
     * @param usa per usare i pulsanti standard
     */
    public void setUsaPulsantiStandard(boolean usa) {
        this.usaPulsantiStandard = usa;
    }



    /**
     * Determina se è usato il bottone Nuovo Record nella lista
     * <p/>
     *
     * @return true se è usato
     */
    public boolean isUsaNuovo() {
        /* variabili e costanti locali di lavoro */
        boolean usa = false;
        ToolBar tb;

        try { // prova ad eseguire il codice
            tb = this.getToolBar();
            if (tb != null) {
                usa = tb.isUsaNuovo();
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return usa;
    }


    /**
     * flag - usa il bottone Nuovo Record.
     *
     * @param flag true per usare il bottone Nuovo Record
     */
    public void setUsaNuovo(boolean flag) {
        try { // prova ad eseguire il codice
            if (this.getToolBar() != null) {
                this.getToolBar().setUsaNuovo(flag);
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * flag - usa il bottone Modifica Record.
     *
     * @param flag true per usare il bottone Modifica Record
     */
    public void setUsaModifica(boolean flag) {
        try { // prova ad eseguire il codice
            if (this.getToolBar() != null) {
                this.getToolBar().setUsaModifica(flag);
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
        try { // prova ad eseguire il codice
            if (this.getToolBar() != null) {
                this.getToolBar().setTipoBottoni(tipoBottoni);
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * flag - usa il bottone elimina.
     *
     * @param flag true per usare il bottone elimina
     */
    public void setUsaElimina(boolean flag) {
        try { // prova ad eseguire il codice
            if (this.getToolBar() != null) {
                this.getToolBar().setUsaElimina(flag);
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * flag - usa i bottoni di selezione nella toolbar.
     *
     * @param usaSelezione true per usare i tre bottoni di selezione dei records
     * (solo selezionati, rimuovi selezionati, mostra tutti)
     */
    public void setUsaSelezione(boolean usaSelezione) {
        try { // prova ad eseguire il codice
            if (this.getToolBar() != null) {
                this.getToolBar().setUsaSelezione(usaSelezione);
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * flag - usa il bottone di stampa lista.
     *
     * @param flag true per usare il bottone di stampa lista <br>
     */
    public void setUsaStampa(boolean flag) {
        try { // prova ad eseguire il codice
            if (this.getToolBar() != null) {
                this.getToolBar().setUsaStampa(flag);
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Rende visibile o invisibile la finestra / dialogo del Portale.
     * <p/>
     * Opera sulla finestra o sul dialogo a seconda del flag usaDialogo del portale
     *
     * @param flag per regolare la visibilità
     */
    public void setFinestraVisibile(boolean flag) {
        /* variabili e costanti locali di lavoro */
        Window window;

        try {    // prova ad eseguire il codice

            /* rende la finestra/dialogo visibile */
            if (this.isUsaFinestra()) {
                window = this.getWindow();
                if (window != null) {

                    /* se viene resa visibile, esegue prima una sincronizzazione */
                    if (flag) {
                        this.sincronizza();
                    }// fine del blocco if

                    window.setVisible(flag);

                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Inserisce graficamente il portale nella propria finestra o dialogo.
     */
    public void entraInFinestra() {
        /* variabili e costanti locali di lavoro */
        Window window;

        try { // prova ad eseguire il codice
            if (this.isUsaFinestra()) {
                window = this.getWindow();
                if (window != null) {
                    window.add(this);
                    window.pack();
                    Lib.Gui.centraWindow(window, null);
                }// fine del blocco if
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    public EventListenerList getListaListener() {
        return listaListener;
    }


    public void setListaListener(EventListenerList listaListener) {
        this.listaListener = listaListener;
    }


    /**
     * Ritorna la finestra di questo portale.
     *
     * @return la finestra del portale
     */
    public Finestra getFinestra() {
        return finestra;
    }


    protected void setFinestra(Finestra finestra) {
        this.finestra = finestra;
    }


    /**
     * Ritorna la finestra dialogo di questo portale.
     *
     * @return la finestra dialogo del portale
     */
    public JDialog getFinestraDialogo() {
        return finestraDialogo;
    }


    protected void setFinestraDialogo(JDialog dialogo) {
        this.finestraDialogo = dialogo;
    }


    /**
     * Determina se il Portale usa una finestra propria
     * <p/>
     *
     * @return true se usa una finestra propria
     */
    public boolean isUsaFinestra() {
        return usaFinestra;
    }


    /**
     * Determina se il Portale deve usare una finestra propria
     * <p/>
     *
     * @param usa true se deve usare una finestra propria
     */
    public void setUsaFinestra(boolean usa) {
        this.usaFinestra = usa;
    }


    /**
     * Determina se il Portale usa una finestra di tipo dialogo
     * <p/>
     *
     * @return true se usa una finestra propria di tipo dialogo
     */
    public boolean isUsaFinestraDialogo() {
        /* variabili e costanti locali di lavoro */
        boolean usa = false;

        try { // prova ad eseguire il codice
            if (this.isUsaFinestra()) {
                usa = this.usaDialogo;
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return usa;
    }


    /**
     * Determina se la finestra deve essere di tipo dialogo modale
     * anziché di tipo Finestra
     * <p/>
     * Significativo solo se usaFinestra è true
     *
     * @param usaDialogo true per usare una finestra di tipo dialogo modale
     */
    public void setUsaDialogo(boolean usaDialogo) {
        this.usaDialogo = usaDialogo;
    }


    /**
     * Ritorna la eventuale finestra usata dal portale.
     * <p/>
     * La finestra può essere un JFrame o un JDialog
     */
    public Window getWindow() {
        /* variabili e costanti locali di lavoro */
        Window window = null;
        Finestra finestra;

        try {    // prova ad eseguire il codice
            if (this.isUsaFinestra()) {
                if (this.isUsaFinestraDialogo()) {
                    window = this.getFinestraDialogo();
                } else {
                    finestra = this.getFinestra();
                    if (finestra != null) {
                        window = finestra.getFinestraBase();
                    }// fine del blocco if
                }// fine del blocco if-else
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return window;
    }


    protected ModuloVisibile getModVisibile() {
        return modVisibile;
    }


    private void setModVisibile(ModuloVisibile modVisibile) {
        this.modVisibile = modVisibile;
    }


    protected ModuloNascosto getModNascosto() {
        return modNascosto;
    }


    private void setModNascosto(ModuloNascosto modNascosto) {
        this.modNascosto = modNascosto;
    }


    protected boolean isInizializzato() {
        return inizializzato;
    }


    protected void setInizializzato(boolean inizializzato) {
        this.inizializzato = inizializzato;
    }


    /**
     * Regola la visibilità del modulo.
     * </p>
     */
    protected void regolaModulo(Modulo modulo, Operazione operazione) {
    }


    /**
     * Costruisce il menu tabelle specifico.
     * </p>
     */
    public void setMenuTabelle(JMenu menu) {
    }


    /**
     * Azione invocata quando il modulo viene reso visibile.
     * <p/>
     */
    public final class ModuloVisibile extends ModVisibileAz {

        /**
         * visibileAz, da ModVisibileLis.
         * </p>
         * Esegue l'azione <br>
         * Rimanda al metodo sovrascritto, nell'oggetto specifico
         * della classe che genera questo evento <br>
         * Sovrascritto nelle sottoclassi <br>
         *
         * @param unEvento evento che causa l'azione da eseguire <br>
         */
        public void visibileAz(ModVisibileEve unEvento) {
            /* variabili e costanti locali di lavoro */
            Object oggetto;
            Modulo modulo;

            try { // prova ad eseguire il codice
                oggetto = unEvento.getSource();

                if (oggetto instanceof Modulo) {
                    modulo = (Modulo)oggetto;
                    regolaModulo(modulo, Operazione.visibile);
                }// fine del blocco if

            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }
    } // fine della classe 'interna'


    /**
     * Azione invocata quando il modulo viene reso nascosto.
     * <p/>
     */
    public final class ModuloNascosto extends ModNascostoAz {

        /**
         * nascostoAz, da ModNascostoLis.
         * </p>
         * Esegue l'azione <br>
         * Rimanda al metodo sovrascritto, nell'oggetto specifico
         * della classe che genera questo evento <br>
         * Sovrascritto nelle sottoclassi <br>
         *
         * @param unEvento evento che causa l'azione da eseguire <br>
         */
        public void nascostoAz(ModNascostoEve unEvento) {
            /* variabili e costanti locali di lavoro */
            Object oggetto;
            Modulo modulo;

            try { // prova ad eseguire il codice
                oggetto = unEvento.getSource();

                if (oggetto instanceof Modulo) {
                    modulo = (Modulo)oggetto;
                    regolaModulo(modulo, Operazione.nascosto);
                }// fine del blocco if

            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

        }
    } // fine della classe 'interna'


    /**
     * Classe interna Enumerazione.
     */
    public enum Operazione {

        visibile(true),
        nascosto(false);

        /**
         * visibile
         */
        private boolean azioneVisibile;


        /**
         * Costruttore completo con parametri.
         *
         * @param azioneVisibile utilizzato nei popup
         */
        Operazione(boolean azioneVisibile) {
            try { // prova ad eseguire il codice
                /* regola le variabili di istanza coi parametri */
                this.setAzioneVisibile(azioneVisibile);
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }


        private boolean isAzioneVisibile() {
            return azioneVisibile;
        }


        private void setAzioneVisibile(boolean azioneVisibile) {
            this.azioneVisibile = azioneVisibile;
        }
    }// fine della classe


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
            Lib.Eventi.fire(listaListener, unEvento, Portale.class, this);
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

        nuovoRecord(PortaleNuovoRecLis.class,
                PortaleNuovoRecEve.class,
                PortaleNuovoRecAz.class,
                "portaleNuovoRecAz"),
        aggiungiRecord(PortaleAddRecLis.class,
                PortaleAddRecEve.class,
                PortaleAddRecAz.class,
                "portaleAddRecAz"),
        modificaRecord(PortaleModificaRecLis.class,
                PortaleModificaRecEve.class,
                PortaleModificaRecAz.class,
                "portaleModificaRecAz"),
        duplicaRecord(PortaleDuplicaRecLis.class,
                PortaleDuplicaRecEve.class,
                PortaleDuplicaRecAz.class,
                "portaleDuplicaRecAz"),
        eliminaRecord(PortaleEliminaRecLis.class,
                PortaleEliminaRecEve.class,
                PortaleEliminaRecAz.class,
                "portaleEliminaRecAz"),
        rimuoviRecord(PortaleLevaRecLis.class,
                PortaleLevaRecEve.class,
                PortaleLevaRecAz.class,
                "portaleLevaRecAz"),
        listaSelezione(PortaleListaSelLis.class,
                PortaleListaSelEve.class,
                PortaleListaSelAz.class,
                "portaleListaSelAz"),
        frecciaAlto(PortaleCursoreLis.class,
                PortaleCursoreEve.class,
                PortaleCursoreAz.class,
                "portaleFrecciaAltoAz"),
        frecciaBasso(PortaleCursoreLis.class,
                PortaleCursoreEve.class,
                PortaleCursoreAz.class,
                "portaleFrecciaBassoAz"),
        paginaSu(PortaleCursoreLis.class,
                PortaleCursoreEve.class,
                PortaleCursoreAz.class,
                "portalePaginaSuAz"),
        paginaGiu(PortaleCursoreLis.class,
                PortaleCursoreEve.class,
                PortaleCursoreAz.class,
                "portalePaginaGiuAz"),
        home(PortaleCursoreLis.class,
                PortaleCursoreEve.class,
                PortaleCursoreAz.class,
                "portaleHomeAz"),
        end(PortaleCursoreLis.class,
                PortaleCursoreEve.class,
                PortaleCursoreAz.class,
                "portaleEndAz"),
        stato(PortaleStatoLis.class, PortaleStatoEve.class, PortaleStatoAz.class, "portaleStatoAz");

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
     * Layout del Portale
     * <p/>
     */
    private final class LayoutPortale extends LayoutFlusso {

        /**
         * Costruttore completo con parametri.
         * <p/>
         *
         * @param portale di riferimento
         * @param orientamento del layout
         * (Layout.ORIENTAMENTO_VERTICALE o Layout.ORIENTAMENTO_ORIZZONTALE)
         */
        public LayoutPortale(Portale portale, int orientamento) {
            /* rimanda al costruttore della superclasse */
            super(portale.getPortale().getPanFisso(), orientamento);

            try { // prova ad eseguire il codice
                /* regolazioni iniziali di riferimenti e variabili */
                this.inizia();
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }/* fine del blocco try-catch */

        }/* fine del metodo costruttore completo */


        /**
         * Regolazioni immediate di riferimenti e variabili. <br>
         * Metodo chiamato direttamente dal costruttore <br>
         *
         * @throws Exception unaEccezione
         */
        private void inizia() throws Exception {
            try { // prova ad eseguire il codice
                this.setUsaGapFisso(true);
                this.setGapPreferito(0);
                this.setUsaScorrevole(false);
                this.setAllineamento(Layout.ALLINEA_SX);
                this.setRidimensionaComponenti(true);
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }


        public Dimension preferredLayoutSize(Container parent) {
            Dimension dim = super.preferredLayoutSize(parent);
            return dim;
        }


        public Dimension minimumLayoutSize(Container parent) {
            Dimension dim = super.minimumLayoutSize(parent);
            return dim;
        }


        public Dimension maximumLayoutSize(Container parent) {
            Dimension dim = super.maximumLayoutSize(parent);
            return dim;
        }


    } // fine della classe 'interna'


}// fine della classe
