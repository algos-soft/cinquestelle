/**
 * Title:     PortaleNavigatore
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      21-apr-2004
 */
package it.algos.base.portale;

import it.algos.base.azione.Azione;
import it.algos.base.errore.Errore;
import it.algos.base.finestra.Finestra;
import it.algos.base.finestra.FinestraBase;
import it.algos.base.finestra.FinestraPortale;
import it.algos.base.libreria.Lib;
import it.algos.base.libreria.LibMat;
import it.algos.base.menu.barra.MenuBarra;
import it.algos.base.menu.barra.MenuBarraBase;
import it.algos.base.menu.menu.Menu;
import it.algos.base.modulo.Modulo;
import it.algos.base.navigatore.Navigatore;
import it.algos.base.navigatore.info.Info;
import it.algos.base.navigatore.info.InfoNavigatore;
import it.algos.base.navigatore.stato.StatoNavigatore;
import it.algos.base.pannello.Pannello;
import it.algos.base.pannello.PannelloBase;
import it.algos.base.progetto.Progetto;
import it.algos.base.toolbar.ToolBar;

import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JSplitPane;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Window;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

/**
 * </p>
 * Questa classe: <ul>
 * Implementa un PortaleNavigatore.
 * E' costituito da un PortaleBase contenente un JComponent, che puo'
 * essere, in alternativa:
 * - un JSplitPane.<br>
 * - un JPanel (PannelloBase)<br>
 * Il PortaleNavigatore contiene anche due Portali.<br>
 * Nel caso del JSplitPane, li visualizza contemporaneamente.<br>
 * Nel caso del JPanel, li visualizza alternativamente,
 * su richiesta del Navigatore.<br>
 *
 * @author Alberto Colombo, Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 21-apr-2004 ore 17.32.12
 */
public class PortaleNavigatore extends PortaleBase {

    /**
     * Primo portale contenuto in questo Portale
     */
    private Portale portaleA = null;

    /**
     * Secondo portale contenuto in questo Portale
     */
    private Portale portaleB = null;

    /**
     * Split Pane eventualmente contenuto in questo Portale <br>
     * Usato in alternativa al pannello singolo.<br>
     * Visualizza entrambi i contenuti (A e B) del portale.<br>
     */
    private JSplitPane splitPane = null;

    /**
     * Pannello singolo eventualmente contenuto in questo Portale
     * <p/>
     * Usato in alternativa allo split pane.<br>
     * Il pannello singolo visualizza uno solo dei due contenuti
     * per volta (A o B).
     */
    private Pannello pannelloSingolo = null;


    /**
     * flag - se il portale usa un unico pannello
     * per la visualizzazione alternativa dei componenti A e B
     * <p/>
     */
    boolean isUsaPannelloUnico = false;

    /**
     * flag - apre una finestra separata per il secondo componente.
     * <p/>
     * Significativo solo se il portale usa il pannello unico.
     */
    boolean isFinestraPop = false;


    private JComponent comLista;


    /**
     * Costruttore base senza parametri. <br>
     * Indispensabile anche se non viene utilizzato
     * (anche solo per compilazione in sviluppo) <br>
     * Rimanda al costruttore completo <br>
     * Utilizza eventuali valori di default <br>
     */
    public PortaleNavigatore() {
        /* rimanda al costruttore di questa classe */
        this(null);
    }// fine del metodo costruttore base


    /**
     * Costruttore completo con parametri. <br>
     *
     * @param unNavigatore navigatore che gestisce questo pannello
     */
    public PortaleNavigatore(Navigatore unNavigatore) {
        /* rimanda al costruttore della superclasse */
        super(unNavigatore);

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
        /* variabili e costanti locali di lavoro */
        Pannello pannello = null;

        try { // prova ad eseguire il codice

            /* posizione di default della ToolBar */
            this.setPosToolbar(ToolBar.Pos.nessuna);

            /* Crea il componente video di tipo singolo */
            /* Assegna il layout manager al pannello singolo (classe interna)*/
            pannello = new PannelloBase();
            pannello.getPanFisso().setLayout(new LayoutPannelloSingolo());
            this.setPannelloSingolo(pannello);

            /* crea il componente video di tipo split pane */
            this.setSplitPane(this.creaSplitPane());

            /* crea il pacchetto informazioni */
            super.setPacchettoInfo(new InfoNavigatore(this.getNavigatore()));

            /* Assegna il gestore dello stato */
            super.setStato(new StatoNavigatore(this));

            /* regola il flag di utilizzo della finestra popup (secondo comp) */
            this.setUsaFinestraPop(false);


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }// fine del metodo inizia


    /**
     * Effettua l'inizializzazione del portale
     * <p/>
     * sovrascrive il metodo della superclasse <br>
     */
    public void inizializza() {
        /* variabili e costanti locali di lavoro */
        Portale portaleB;

        try {    // prova ad eseguire il codice

            /* invoca il metodo sovrascritto della superclasse */
            super.inizializza();

            /* Inserisce il portale nella propria finestra */
            if (this.isUsaFinestra()) {
                this.entraInFinestra();
            }// fine del blocco if

            /* regola i listener ai moduli */
            this.regolaListener();

//            /* inizializza il pannello singolo */
//            this.getPannelloSingolo().inizializza();

            /* regolazioni dello split pane */
            this.regolaSplitPane();

            /* crea il pacchetto informazioni */
            this.getPacchettoInfo().inizializza();

            /* aggiunge il componente video al componente principale */
            this.getCompMain().add(this.getComponenteVideo());

            /* se usa pannello unico, e usa la finestra pop per
             * il componente B, chiede al portale B di inserirsi
             * nella propria finestra di dialogo. */
            if (this.isUsaPannelloUnico()) {
                if (this.isUsaFinestraPop()) {
                    portaleB = this.getPortaleB();
                    if (portaleB != null) {
                        portaleB.setUsaFinestra(true);
                        portaleB.entraInFinestra();
                    }// fine del blocco if
                }// fine del blocco if
            }// fine del blocco if

            /* marca come inizializzato */
            this.setInizializzato(true);


        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } // fine del blocco try-catch

    }// fine del metodo inizializza


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

        /* invoca il metodo sovrascritto nella superclasse */
        super.avvia();

        /* inserisce i contenuti nel componente video */
        this.regolaComponenteVideo();

    }// fine del metodo avvia


    /**
     * Regolazioni dello split pane.
     * <p/>
     * Invocato dal ciclo Inizializza
     */
    private void regolaSplitPane() {
        /* variabili e costanti locali di lavoro */
        JSplitPane splitPane;

        try {    // prova ad eseguire il codice

            /* regola l'orientamento dello SplitPane in funzione
             * dell'orientamento del Navigatore */
            splitPane = this.getSplitPane();
            if (this.getNavigatore().isOrizzontale()) {    // navigatore orizzontale
                splitPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
            } else {    // navigatore verticale
                splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
            }// fine del blocco if-else

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Crea la finestra per questo Portale.
     * <p/>
     * Crea una finestra Portale <br>
     * Invocato dal ciclo inizia() <br>
     * Regola le tre funzionalit&agrave della Finestra: <ul>
     * <li> Aggiunge il Portale Navigatore </li>
     * <li> Regola il menu della Finestra </li>
     * <li> Regola la StatusBar della Finestra </li>
     * </ul>
     */
    protected void creaFinestra() {

        try {    // prova ad eseguire il codice
            if (this.isUsaFinestraDialogo()) {
                this.creaFinestraDialogo();
            } else {
                this.creaFinestraStandard();
            }// fine del blocco if-else
        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Crea una finestra dialogo per questo Portale.
     * <p/>
     */
    private void creaFinestraDialogo() {
        /* variabili e costanti locali di lavoro */
        JDialog dialogo;

        try {    // prova ad eseguire il codice

            /* crea l'istanza del dialogo */
            dialogo = new JDialog((Frame)null, true);
            this.setFinestraDialogo(dialogo);

            dialogo.getContentPane().add(this);
        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Crea la finestra per questo Portale.
     * <p/>
     * Crea una finestra Portale <br>
     * Invocato dal ciclo inizia() <br>
     * Regola le tre funzionalit&agrave della Finestra: <ul>
     * <li> Aggiunge il Portale Navigatore </li>
     * <li> Regola il menu della Finestra </li>
     * <li> Regola la StatusBar della Finestra </li>
     * </ul>
     */
    private void creaFinestraStandard() {
        /* variabili e costanti locali di lavoro */
        Finestra unaFinestra;

        try {    // prova ad eseguire il codice
            /* crea l'istanza della finestra */
            unaFinestra = new FinestraPortale(this);
            this.setFinestra(unaFinestra);

            /* regola il contenuto della status bar della finestra */
            unaFinestra.getFinestraBase()
                    .setTestoStatusBar(Progetto.getIstanza().getNomeProgramma());

            /* rende la finestra ridimensionabile */
            unaFinestra.getFinestraBase().setResizable(true);

        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Regola i listener.
     * <p/>
     * Metodo invocato dal ciclo inizializza <br>
     * Registra questo portale come listener in tuttti i moduli <br>
     */
    public void regolaListener() {
        String nomeModulo;
        Modulo moduloNav;
        Modulo modulo;
        ArrayList moduli;
        Azione azione;
        it.algos.base.portale.PortaleBase.ModuloVisibile modVisibile;
        it.algos.base.portale.PortaleBase.ModuloNascosto modNascosto;

        try {    // prova ad eseguire il codice
            /* recupera le classi interne */
            modVisibile = this.getModVisibile();
            modNascosto = this.getModNascosto();

            moduloNav = this.getModulo();
            moduli = moduloNav.getModuli();

            for (int k = 0; k < moduli.size(); k++) {
                nomeModulo = (String)moduli.get(k);
                modulo = Progetto.getModulo(nomeModulo);
                if (modulo != null) {
                    azione = modulo.getAzioneModulo();
                    this.addAzione(azione);
                    modulo.addListener(modVisibile);
                    modulo.addListener(modNascosto);
                }// fine del blocco if
            } // fine del ciclo for-each

        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Aggiunge le azioni al portale.
     * <p/>
     * Metodo invocato dal ciclo inizia del costruttore <br>
     * Invoca il metodo sovrascritto della superclasse <br>
     * Aggiunge ogni singola azione per questo portale (anche se non viene usata) <br>
     * La singola azione viene creata dal metodo delegato della superclasse, che
     * la clona dal Progetto <br>
     */
    protected void addAzioni() {
        try {    // prova ad eseguire il codice
            /* invoca il metodo sovrascritto della superclasse */
            super.addAzioni();

            /* gruppo della finestra */
            this.addAzione(Azione.ATTIVA_FINESTRA);
            this.addAzione(Azione.CHIUDE_FINESTRA);

            /* gruppo di azioni per la gestione del singolo record */
            this.addAzione(Azione.NUOVO_RECORD);
            this.addAzione(Azione.MODIFICA_RECORD);
            this.addAzione(Azione.ELIMINA_RECORD);

            /* gruppo del menu archivio */
            this.addAzione(Azione.CHIUDE_NAVIGATORE);
            this.addAzione(Azione.CHIUDE_PROGRAMMA);
            this.addAzione(Azione.PREFERENZE);
            this.addAzione(Azione.UTENTI);
            this.addAzione(Azione.CONTATORI);
            this.addAzione(Azione.SEMAFORI);
            this.addAzione(Azione.REGISTRA_DATI_DEFAULT);
            this.addAzione(Azione.SALVA_SELEZIONE);
            this.addAzione(Azione.CARICA_SELEZIONE);
            this.addAzione(Azione.EXPORT);
            this.addAzione(Azione.IMPORT);
            this.addAzione(Azione.BACKUP);
            this.addAzione(Azione.RESTORE);
            this.addAzione(Azione.STAMPA);

            /* gruppo del menu composizione */
            this.addAzione(Azione.ANNULLA_OPERAZIONE);
            this.addAzione(Azione.COPIA_TESTO);
            this.addAzione(Azione.INCOLLA_TESTO);
            this.addAzione(Azione.MOSTRA_APPUNTI);
            this.addAzione(Azione.SELEZIONA_TUTTO);
            this.addAzione(Azione.TAGLIA_TESTO);

            /* gruppo di azioni di selezione della lista */
            this.addAzione(Azione.RICERCA);
            this.addAzione(Azione.PROIETTA);
            this.addAzione(Azione.CARICA_TUTTI);
            this.addAzione(Azione.SOLO_SELEZIONATI);
            this.addAzione(Azione.NASCONDE_SELEZIONATI);

            /* gruppo del menu aiuto */
            this.addAzione(Azione.ABOUT);
            this.addAzione(Azione.UPDATE);
            this.addAzione(Azione.HELP);
            this.addAzione(Azione.HELP_PROGRAMMATORE);

        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Crea uno split pane vuoto regolato di default.
     * <p/>
     *
     * @return uno split pane vuoto gia' regolato
     */
    protected JSplitPane creaSplitPane() {
        /* variabili e costanti locali di lavoro */
        JSplitPane splitPane = null;

        try {    // prova ad eseguire il codice

            /* crea un nuovo Split Pane */
            splitPane = new JSplitPane();

            /* abilita l'espansione/contrazione del divisore */
            splitPane.setOneTouchExpandable(true);

            /**
             * forza il componente a destra o in basso a mantenere
             * fissa la propria dimensione quando ridimensiono l'intero
             * split pane (non influisce quando agisco sul divisore)
             * 0 = il componente sinistra/alto e' fisso
             * 1 = il componente destra/basso e' fisso
             */
            splitPane.setResizeWeight(1);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return splitPane;
    }


    /**
     * Registra i due componenti Portale nel Portale Navigatore.
     *
     * @param pA il primo componente Portale (sinistra o alto)
     * @param pB il secondo componente Portale (destra o basso)
     */
    public void addPortali(Portale pA, Portale pB) {

        try {    // prova ad eseguire il codice

            this.setPortaleA(pA);
            this.setPortaleB(pB);

        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } // fine del blocco try-catch

    } // fine del metodo


    /**
     * Inserisce i contenuti nel componente video del
     * Portale Navigatore.
     * <p/>
     * Invocato dal ciclo avvia.
     * - Se e' un portale a visualizzazione singola,
     * inserisce il primo componente nel pannello singolo.
     * - Se e' un portale a visualizzazione doppia,
     * inserisce i due portali nei rispettivi componenti
     * dello split pane.
     */
    private void regolaComponenteVideo() {
        /* variabili e costanti locali di lavoro */
        JSplitPane splitPane;
        JComponent compA;
        JComponent compB;

        try {    // prova ad eseguire il codice

            if (this.isSingolo()) { // portale a visualizzazione singola

                this.setContenutoSingolo(Portale.COMP_A);

            } else { // portale a visualizzazione doppia

                /* recupera lo split pane */
                splitPane = this.getSplitPane();

                /* recupera i componenti */
                compA = (JComponent)this.getPortaleA();
                compB = (JComponent)this.getPortaleB();

                /* assegna i due componenti Portale allo Split Pane */
                splitPane.setLeftComponent(compA);
                splitPane.setRightComponent(compB);

            }// fine del blocco if-else

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

    }


    /**
     * Regola il contenuto del pannello singolo del Portale.
     * <p/>
     * E' possibile scegliere se deve contenere il componente A
     * o il componente B
     *
     * @param codComponente COMP_A/COMP_B per il componente A o B
     * Costanti nella interfaccia Portale.
     */
    private void setContenutoSingolo(int codComponente) {
        /* variabili e costanti locali di lavoro */
        JComponent componente;
        Pannello panSingolo;

        try { // prova ad eseguire il codice

            /* individua il componente */
            switch (codComponente) {
                case Portale.COMP_A:
                    componente = (JComponent)this.getPortaleA();
                    break;
                case Portale.COMP_B:
                    componente = (JComponent)this.getPortaleB();
                    break;
                default: // caso non definito
                    throw new Exception("Codice componente sconosciuto.");
            } // fine del blocco switch

            /* recupera il Pannello singolo */
            panSingolo = this.getPannelloSingolo();

            /* svuota il pannello singolo dei contenuti */
            panSingolo.getPanFisso().removeAll();

            /* inserisce il componente richiesto nel Pannello singolo */
            panSingolo.add(componente);

            /* ridisegna il componente */
            this.getComponenteVideo().revalidate();
            this.getComponenteVideo().repaint();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Ritorna il componente video di questo Portale.
     * <p/>
     *
     * @return il componente video del portale
     */
    private JComponent getComponenteVideo() {
        /* variabili e costanti locali di lavoro */
        JComponent componenteVideo = null;

        try {    // prova ad eseguire il codice
            if (this.isSingolo()) {
                componenteVideo = this.getPannelloSingolo().getPanFisso();
            } else {
                componenteVideo = this.getSplitPane();
            }// fine del blocco if-else
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return componenteVideo;
    }


    /**
     * Regola lo stato del portale Navigatore.
     * <p/>
     * Sincronizza la GUI del Portale <br>
     * Regola lo stato della Navigatore (azioni ed altro) <br>
     *
     * @param info il pacchetto di informazioni sullo stato della Navigatore
     */
    public void setInfoStato(Info info) {
        try { // prova ad eseguire il codice
            this.getStato().regola(info);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Mostra nel pannello singolo solo il componente A.
     * <p/>
     * E' il componente in alto o a sinistra
     * Significativo solo se il Portale usa il pannello unico
     * per i due componenti
     */
    public void visualizzaComponenteA() {
        /* variabili e costanti locali di lavoro */
        Portale portaleB;

        try { // prova ad eseguire il codice

            if (this.isUsaPannelloUnico()) {
                if (this.isUsaFinestraPop()) {
                    portaleB = this.getPortaleB();
                    if (portaleB != null) {

                        /* recupera il dialogo e lo rende invisibile */
                        Component top = Lib.Comp.risali(portaleB.getPortale(), JDialog.class);
                        if (top!=null) {
                            top.setVisible(false);
                        }// fine del blocco if

//                        portaleB.setFinestraVisibile(false);

                    }// fine del blocco if
                } else {
                    this.setContenutoSingolo(Portale.COMP_A);
                }// fine del blocco if-else
            }// fine del blocco if-else

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Mostra nel pannello singolo solo il componente B.
     * <p/>
     * E' il componente in basso o a destra
     */
    public void visualizzaComponenteB() {
        /* variabili e costanti locali di lavoro */
        Portale portaleB;

        try { // prova ad eseguire il codice

            if (this.isUsaPannelloUnico()) {

                if (this.isUsaFinestraPop()) {

                    /**
                     * crea un dialogo e ci mette dentro il portale B
                     * L'owner del dialogo è la finestra più esterna
                     * Il close box del dialogo è rinviato all'azione Annulla
                     * tramite un apposito listener
                     */
                    portaleB = this.getPortaleB();
                    portaleB.setUsaDialogo(true);   // usa una finestra di tipo dialogo

                    /* crea il dialogo posseduto dal top level ancestor */
                    JDialog dial=null;
                    Window ownerWin=null;
                    Container cont = this.getTopLevelAncestor();
                    if (cont instanceof JDialog) {
                        JDialog ownerDialog = (JDialog)cont;
                        ownerWin = ownerDialog;
                        dial = new JDialog(ownerDialog);
                    }// fine del blocco if
                    if (cont instanceof JFrame) {
                        JFrame ownerFrame = (JFrame)cont;
                        ownerWin = ownerFrame;
                        dial = new JDialog(ownerFrame);
                    }// fine del blocco if

                    if (dial!=null) {
                        dial.setTitle(this.getNavigatore().getModulo().getTitoloFinestra());
                        dial.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
                        dial.addWindowListener(new CloseListener());
                        dial.add(portaleB.getPortale());
                        dial.pack();
                        Lib.Gui.centraWindow(dial, ownerWin);
                        this.sincronizza();
                        dial.setVisible(true);
                    }// fine del blocco if


                } else {
                    this.setContenutoSingolo(Portale.COMP_B);
                }// fine del blocco if-else
            } else {
                this.setContenutoSingolo(Portale.COMP_B);
            }// fine del blocco if-else

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    public Portale getPortaleA() {
        return portaleA;
    }


    /**
     * Regola il primo portale (alto/sinistra) contenuto
     * in questo Portale Navigatore.
     * <p/>
     *
     * @param portale il Portale da assegnare
     */
    public void setPortaleA(Portale portale) {
        this.portaleA = portale;
    }


    public Portale getPortaleB() {
        return portaleB;
    }


    /**
     * Regola il primo portale (alto/sinistra) contenuto
     * in questo Portale Navigatore.
     * <p/>
     *
     * @param portale il Portale da assegnare
     */
    public void setPortaleB(Portale portale) {
        this.portaleB = portale;
    }


    public JSplitPane getSplitPane() {
        return splitPane;
    }


    protected void setSplitPane(JSplitPane splitPane) {
        this.splitPane = splitPane;
    }


    private Pannello getPannelloSingolo() {
        return pannelloSingolo;
    }


    protected void setPannelloSingolo(Pannello pannelloSingolo) {
        this.pannelloSingolo = pannelloSingolo;
    }


    private boolean isSingolo() {
//        return this.getNavigatore().isUsaPannelloUnico();
        return this.isUsaPannelloUnico;

    }


    /**
     * Ritorna lo stato del flag pannello unico
     *
     * @return true se il Portale Navigatore usa un pannello unico
     */
    public boolean isUsaPannelloUnico() {
        return this.isUsaPannelloUnico;
    }


    /**
     * Regola il flag di uso di pannello unico.
     * <p/>
     * Se attivato, il Portale Navigatore usa un pannello unico
     * per mostrare alternativamente i suoi due componenti.
     *
     * @param flag true per usare un pannello unico
     */
    public void setUsaPannelloUnico(boolean flag) {
        this.isUsaPannelloUnico = flag;
    }


    public boolean isUsaFinestraPop() {
        return isFinestraPop;
    }


    /**
     * Mostra il componente B in finestra modale separata.
     * <p/>
     * Stabilisce se il portale deve usare una finestra modale separata
     * per la visualizzazione del componente B.<br>
     * (Significativo solo se il navigatore usa un pannello unico)
     *
     * @param flag true per usare finestra separata per il componnte B
     */
    public void setUsaFinestraPop(boolean flag) {
        this.isFinestraPop = flag;
    }


    /**
     * Classe interna - layout manager del pannello singolo del portale Navigatore.
     * </p>
     * Il Pannello Singolo del portale Navigatore contiene alternativamente
     * il componente A o il componente B del Portale Navigatore.
     * La sua dimensione deve essere sempre pari al massimo tra le due.
     *
     * @author Guido Andrea Ceresa, Alessandro Valbonesi
     * @author alex
     * @version 1.0    / 04-02-2005 ore 17.05.26
     */
    protected class LayoutPannelloSingolo extends BorderLayout {


        /**
         * Costruttore completo.
         * <p/>
         * Indispensabile anche se non viene utilizzato
         * (anche solo per compilazione in sviluppo) <br>
         */
        public LayoutPannelloSingolo() {
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
         * Regolazioni statiche di riferimenti e variabili.
         * </p>
         * Metodo chiamato direttamente dal costruttore <br>
         *
         * @throws Exception unaEccezione
         */
        private void inizia() throws Exception {
        }// fine del metodo inizia


        /**
         * Ritorna la dimensione preferita del Portale.
         * <p/>
         * Se usa finestra pop e' la dimensione preferita del componente A.<br>
         * Se usa la stessa finestra, e' il bounding box che contiene
         * il componente A e il componente B <br>
         *
         * @param contenitore il contenitore
         */
        public Dimension preferredLayoutSize(Container contenitore) {
            /* variabili e costanti locali di lavoro */
            Dimension dimTot = null;
            Portale portaleA;
            Portale portaleB;
            Dimension dimA = null;
            Dimension dimB = null;

            try { // prova ad eseguire il codice

                portaleA = getPortaleA();
                portaleB = getPortaleB();

                if (portaleA != null) {
                    dimA = portaleA.getPortale().getPreferredSize();
                }// fine del blocco if

                if (portaleB != null) {
                    dimB = portaleB.getPortale().getPreferredSize();
                }// fine del blocco if

                if (isUsaFinestraPop()) {
                    dimTot = dimA;
                } else {
                    dimTot = LibMat.massimaDimensione(dimA, dimB);
                }// fine del blocco if-else

            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

            /* valore di ritorno */
            return dimTot;

        }


        /**
         * Ritorna la dimensione minima del Portale.
         * <p/>
         * Se usa finestra pop e' la dimensione minima del componente A.<br>
         * Se usa la stessa finestra, e' la massima tra le due minime
         * del componente A e B <br>
         *
         * @param contenitore il contenitore
         */
        public Dimension minimumLayoutSize(Container contenitore) {
            /* variabili e costanti locali di lavoro */
            Dimension dimTot = null;
            Portale portaleA;
            Portale portaleB;
            Dimension dimA = null;
            Dimension dimB = null;

            try { // prova ad eseguire il codice
                portaleA = getPortaleA();
                portaleB = getPortaleB();

                if (portaleA != null) {
                    dimA = portaleA.getPortale().getMinimumSize();
                }// fine del blocco if

                if (portaleB != null) {
                    dimB = portaleB.getPortale().getMinimumSize();
                }// fine del blocco if

                if (isUsaFinestraPop()) {
                    dimTot = dimA;
                } else {
                    dimTot = LibMat.massimaDimensione(dimA, dimB);
                }// fine del blocco if-else

            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

            /* valore di ritorno */
            return dimTot;

        }


        /**
         * Ritorna la dimensione massima del Portale.
         * <p/>
         * Se usa finestra pop e' la dimensione massima del componente A.<br>
         * Se usa la stessa finestra, e' la minima tra le due massime
         * del componente A e B <br>
         * <br>
         *
         * @param contenitore il contenitore
         */
        public Dimension maximumLayoutSize(Container contenitore) {
            /* variabili e costanti locali di lavoro */
            Dimension dimTot = null;
            Portale portaleA;
            Portale portaleB;
            Dimension dimA = null;
            Dimension dimB = null;

            try { // prova ad eseguire il codice

                portaleA = getPortaleA();
                portaleB = getPortaleB();

                if (portaleA != null) {
                    dimA = portaleA.getPortale().getMaximumSize();
                }// fine del blocco if

                if (portaleB != null) {
                    dimB = portaleB.getPortale().getMaximumSize();
                }// fine del blocco if

                if (isUsaFinestraPop()) {
                    dimTot = dimA;
                } else {
                    dimTot = LibMat.minimaDimensione(dimA, dimB);
                }// fine del blocco if-else

            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

            /* valore di ritorno */
            return dimTot;

        }

    }// fine della classe interna


    /**
     * Regola la visibilità del modulo.
     * </p>
     * Metodo invocato da un evento lanciato da un'altro modulo <br>
     * Regola la visibilità della riga di menu corrispondente al modulo <br>
     * Invoca il metodo sovrascritto nella classe MenuBarraBase <br>
     */
    protected void regolaModulo(Modulo modulo, Operazione operazione) {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        MenuBarraBase menuBarraBase;
        Finestra fin;
        FinestraBase finbase;
        MenuBarra menuBarra = null;
        try { // prova ad eseguire il codice

            fin = this.getFinestra();
            continua = fin != null;

            if (continua) {
                finbase = fin.getFinestraBase();
                menuBarra = finbase.getMenuBarra();
                continua = menuBarra != null;
            }// fine del blocco if

            if (continua) {
                menuBarraBase = menuBarra.getMenuBarra();
                menuBarraBase.regolaModulo(modulo, operazione);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Costruisce il menu tabelle specifico.
     * </p>
     */
    public void setMenuTabelle(JMenuItem menu) {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        Finestra fin;
        MenuBarra barra = null;
        Menu tabelle;
        Component[] componenti;

        try { // prova ad eseguire il codice
            fin = this.getFinestra();
            continua = (fin != null);

            if (continua) {
                barra = fin.getFinestraBase().getMenuBarra();
                continua = (barra != null);
            }// fine del blocco if

            if (continua) {
                tabelle = barra.getMenuTabelle();
                tabelle.getMenu().removeAll();

                componenti = menu.getComponents();
                menu.getContainerListeners();

                /* traverso tutta la collezione */
                for (Component comp : componenti) {
                    tabelle.getMenu().add(comp);
                } // fine del ciclo for-each

            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Listener per il tentativo di chiusura tramite closebox del dialogo
     * contenitore del portale B .
     * </p>
     */
    private final class CloseListener extends WindowAdapter {

        @Override
        public void windowClosing(WindowEvent windowEvent) {
            getNavigatore().chiudeScheda();
        }


    } // fine della classe 'interna'


}// fine della classe
