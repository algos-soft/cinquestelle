/**
 * Title:     Tavola
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      23-set-2004
 */
package it.algos.base.tavola;

import com.wildcrest.j2printerworks.J2Printer;
import com.wildcrest.j2printerworks.J2TablePrinter;
import it.algos.base.azione.Azione;
import it.algos.base.azione.adapter.AzAdapterKey;
import it.algos.base.campo.base.Campo;
import it.algos.base.campo.dati.CampoDati;
import it.algos.base.componente.WrapTextArea;
import it.algos.base.costante.CostanteColore;
import it.algos.base.costante.CostanteFont;
import it.algos.base.costante.CostanteLook;
import it.algos.base.database.dati.Dati;
import it.algos.base.errore.Errore;
import it.algos.base.evento.BaseListener;
import it.algos.base.evento.Eventi;
import it.algos.base.evento.GestioneEventi;
import it.algos.base.evento.tavola.TavolaColResizeAz;
import it.algos.base.evento.tavola.TavolaColResizeEve;
import it.algos.base.evento.tavola.TavolaColResizeLis;
import it.algos.base.evento.tavola.TavolaGenAz;
import it.algos.base.evento.tavola.TavolaGenEve;
import it.algos.base.evento.tavola.TavolaGenLis;
import it.algos.base.evento.tavola.TavolaSelAz;
import it.algos.base.evento.tavola.TavolaSelEve;
import it.algos.base.evento.tavola.TavolaSelLis;
import it.algos.base.font.FontFactory;
import it.algos.base.gestore.Gestore;
import it.algos.base.libreria.Lib;
import it.algos.base.libreria.Libreria;
import it.algos.base.lista.Lista;
import it.algos.base.lista.TavolaModello;
import it.algos.base.portale.Portale;
import it.algos.base.pref.Pref;
import it.algos.base.tavola.renderer.RendererBase;
import it.algos.base.tavola.renderer.RendererTitolo;
import it.algos.base.tavola.scroll.JTableScrolling;
import it.algos.base.vista.Vista;
import it.algos.base.vista.VistaElemento;

import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.EventListenerList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableColumnModelEvent;
import javax.swing.event.TableColumnModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Date;

/**
 * Wrapper alla classe JTable.
 * </p>
 * Questa classe: <ul>
 * <li> Mantiene tutte le informazioni grafiche: font, colore, dimensioni ...</li>
 * <li> I titoli delle colonne vengono gestiti direttamente dalla Vista </li>
 * <li> Gestisce la colonna di ordinamento </li>
 * </ul>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 23-set-2004 ore 8.11.35
 */
public class Tavola extends JTable implements GestioneEventi {

    /**
     * font della GUI
     */
    private static final Font FONT_TITOLI = CostanteFont.FONT_TITOLI_LISTA;

    private static final Font FONT_CELLE = CostanteFont.FONT_CAMPI_LISTA;

    /**
     * colori della GUI
     */
    private static final Color COLORE_GRIGLIA = CostanteColore.VERDE_CHIARO_DUE;

    private static final Color COLORE_TESTO_CELLE = Color.black;

    private static final Color COLORE_SFONDO_CELLE = CostanteColore.SFONDO_CAMPO_LISTA;

    private static final Color COLORE_TESTO_TITOLI = CostanteColore.TESTO_ETICHETTA;

    private static final Color COLORE_SFONDO_TITOLI = CostanteColore.VERDE_CHIARO_DUE;

    private static final Color COLORE_TITOLO_SELEZIONATO = new Color(250, 50, 50);

    /**
     * caratteristiche della lista
     */
    private static final boolean DISEGNA_GRIGLIA = true;

    private static final boolean DISEGNA_LINEE_ORIZZONTALI = true;

    private static final boolean DISEGNA_LINEE_VERTICALI = true;

    /**
     * altezza di una riga (in funzione del look-and-feel)
     */
    private static final int ALTEZZA_RIGA_LOOK_METAL = 16;

    private static final int ALTEZZA_RIGA_LOOK_MAC = 18;

    /**
     * moltiplicatore tra il font ed i pixel della riga
     */
    private static final double DELTA = 1.75;

    /**
     * indentificatori del look in uso
     */
    private static final String LOOK_METAL = CostanteLook.LOOK_METAL;

    private static final String LOOK_MAC = CostanteLook.LOOK_MAC;

    /**
     * lista, proprietaria di questa tavola
     */
    private Lista lista = null;

    /**
     * Modello dati della Tavola
     */
    private TavolaModello modello = null;

    /**
     * caratteri (uno o piu) filtrati dalle varie azioni
     * arrayList di interi
     */
    private ArrayList caratteri = null;

    /**
     * colonna attualmente selezionata (1 per la prima)
     */
    private int colonnaSelezionata = 0;

    /**
     * flag - controlla se la tavola è stata inizializzata
     */
    private boolean inizializzata;

    /**
     * A list of event listeners for this component.
     */
    private EventListenerList listaListener;


    /**
     * Costruttore base senza parametri. <br>
     * Indispensabile anche se non viene utilizzato
     * (anche solo per compilazione in sviluppo) <br>
     * Rimanda al costruttore completo <br>
     * Utilizza eventuali valori di default <br>
     */
    public Tavola() {
        /* rimanda al costruttore di questa classe */
        this(null, null);
    }// fine del metodo costruttore base


    /**
     * Costruttore completo con parametri. <br>
     *
     * @param modello modello dati per la tavola
     */
    public Tavola(TavolaModello modello) {
        this(null, modello);
    }// fine del metodo costruttore base


    /**
     * Costruttore completo con parametri. <br>
     *
     * @param lista lista proprietaria di questa tavola
     */
    public Tavola(Lista lista) {
        this(lista, null);
    }// fine del metodo costruttore base


    /**
     * Costruttore completo con parametri. <br>
     *
     * @param unaLista lista proprietaria di questa tavola
     * @param modello modello dati per la tavola
     */
    public Tavola(Lista unaLista, TavolaModello modello) {
        /* rimanda al costruttore della superclasse */
        super();

        /* regola le variabili di istanza coi parametri */
        this.setLista(unaLista);
        this.setModello(modello);

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            new Errore(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo costruttore completo


    /**
     * Regolazioni immediate di riferimenti e variabili. <br>
     * Metodo chiamato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {

        /* crea lista degli ascoltatori dei propri eventi */
        this.setListaListener(new EventListenerList());

        /* aggiunge un listener per la modifica delle colonne */
        this.getColumnModel().addColumnModelListener(new ColumnModelListener(this));

        /* creazione della variabile */
        this.caratteri = new ArrayList();

        /* selezione della lista: singola, continua, discontinua */
        this.regolaModoSelezione();

        /* auto resize mode di default */
        this.setAutoResizeMode(AUTO_RESIZE_SUBSEQUENT_COLUMNS);

        /* regola i font, colori, bordi e sfondi di tutti gli oggetti */
        this.regolaFontColori();

        /* regola l'altezza delle righe in funzione del look-and-feel e del font utilizzato */
        this.regolaAltezzaRighe();

        /* colonna inizialmente selezionata */
        this.setColonnaSelezionata(1);

        AlgosTextField campoTesto;
        campoTesto = new AlgosTextField();
        AlgosCellEditor editore;
        editore = new AlgosCellEditor(campoTesto);
        this.setDefaultEditor(String.class, editore);

    }// fine del metodo inizia


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
     */
    public void inizializza() {
        /* variabili e costanti locali di lavoro */
        Vista vista;

        try { // prova ad eseguire il codice

            /* inizializza la vista */
            vista = this.getVista();
            if (vista != null) {
                if (!vista.isInizializzato()) {
                    vista.inizializza();
                }// fine del blocco if
            }// fine del blocco if

            /* regola i renderer di ogni colonna */
            this.regolaRendererColonne();

            /* regola gli editor di ogni colonna */
            this.regolaEditorColonne();

            /* Aggiunge i Listener */
            this.aggiungeListener();

            /* Elimina le colonne non visibili. */
            this.regolaVisibilitaColonne();

            /* regola la larghezza delle colonne fisse e variabili */
            this.regolaLarghezzaColonne();

            /* regola i font ed i colori dei titoli delle colonne */
            this.regolaFontColoriTitoli();

            /* accende il flag di avvenuta inizializzazione */
            this.setInizializzata(true);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch


    }


    /**
     * Accende una colonna nei titoli.
     * <p/>
     * Spegne tutte le altre colonne.
     *
     * @param indice l'indice della colonna da accendere (0 per la prima)
     */
    public void accendiColonna(int indice) {
        /* variabili e costanti locali di lavoro */
        TableColumnModel modello;
        TableColumn colonna;
        RendererTitolo renderer;
        Color colore;
        JTableHeader header;

        try {    // prova ad eseguire il codice

            header = this.getTableHeader();

            if (header != null) {

                modello = this.getColumnModel();

                /*  ciclo for */
                for (int k = 0; k < modello.getColumnCount(); k++) {

                    colonna = modello.getColumn(k);
                    renderer = (RendererTitolo)colonna.getHeaderRenderer();
                    
                    if (renderer!=null) {
                    	
                        if (indice == k) {
                            colore = COLORE_TITOLO_SELEZIONATO;
                        } else {
                            colore = COLORE_TESTO_TITOLI;
                        }// fine del blocco if-else

                        renderer.setColoreSelezione(colore);
					}


                } /* fine del blocco for */

                /* ridisegna l'header */
                header.resizeAndRepaint();

            }// fine del blocco if


        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

    }


    /**
     * Regola la selezione della lista.
     * Pu� essere: singola, continua, discontinua <br>
     */
    private void regolaModoSelezione() {
        /* variabili e costanti locali di lavoro */
        int tipoSelezioneLista;

        /* recupera dalle preferenze */
        if (Pref.GUI.discontinua.is()) {
            tipoSelezioneLista = ListSelectionModel.MULTIPLE_INTERVAL_SELECTION;
        } else {
            tipoSelezioneLista = ListSelectionModel.SINGLE_INTERVAL_SELECTION;
        } /* fine del blocco if/else */

        this.setRowSelectionAllowed(true);
        this.setColumnSelectionAllowed(false);
        this.setSelectionMode(tipoSelezioneLista);

    } /* fine del metodo */


    /**
     * Regola font, colori, griglia e sfondi.
     */
    private void regolaFontColori() {
        /* variabili e costanti locali di lavoro */
        JTableHeader unaTestata;

        try { // prova ad eseguire il codice
            unaTestata = this.getTableHeader();
            unaTestata.setFont(FONT_TITOLI);
            unaTestata.setForeground(COLORE_TESTO_TITOLI);
            unaTestata.setBackground(COLORE_SFONDO_TITOLI);

            /* font, colori e sfondo delle celle */
            this.setFont(FONT_CELLE);
            this.setForeground(COLORE_TESTO_CELLE);
            this.setBackground(COLORE_SFONDO_CELLE);

            /* colori ed utilizzo della griglia */
            this.setShowGrid(DISEGNA_GRIGLIA);
            this.setShowHorizontalLines(DISEGNA_LINEE_ORIZZONTALI);
            this.setShowVerticalLines(DISEGNA_LINEE_VERTICALI);
            this.setGridColor(COLORE_GRIGLIA);

        } catch (Exception unErrore) { // intercetta l'errore
            new Errore(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Regola i renderer di ogni colonna.
     * <p/>
     * Metodo invocato dal ciclo inizializza <br>
     */
    private void regolaRendererColonne() {
        /* variabili e costanti locali di lavoro */
        TableColumnModel unModelloColonna;
        TableColumn unaColonna;
        ArrayList<Campo> campi;
        Campo unCampo;
        CampoDati unCampoDati;
        RendererBase renderer;

        try {    // prova ad eseguire il codice

            unModelloColonna = this.getColumnModel();

            campi = this.getVista().getCampi();

            for (int k = 0; k < campi.size(); k++) {

                unCampo = campi.get(k); // passa dal CampoVista
                unCampoDati = unCampo.getCampoDati();
                renderer = unCampoDati.getRenderer();

                unaColonna = unModelloColonna.getColumn(k);

                /* usa un renderer specifico del campo, altrimenti
                 * usa quello di sistema (Java) */
                if (renderer != null) {
                    unaColonna.setCellRenderer(renderer);
                }// fine del blocco if

            } /* fine del blocco for */

        } catch (Exception unErrore) { // intercetta l'errore
            new Errore(unErrore);
        } /* fine del blocco try-catch */
    }


    /**
     * Regola i renderer e gli editor di ogni colonna.
     * <p/>
     * Metodo invocato dal ciclo inizializza <br>
     */
    private void regolaEditorColonne() {
        /* variabili e costanti locali di lavoro */
        Vista vista;
        Campo campo;
        Campo campoOriginale;
        Campo campoCheck = null;
        Campo campoEditor = null;
        int indice;
        boolean continua;
        TableColumnModel modelloColonne;
        TableColumn colonna = null;
        ArrayList<VistaElemento> elementi;
        CampoDati campoDati;
        TableCellEditor editor = null;

        try {    // prova ad eseguire il codice

            vista = this.getVista();
            elementi = vista.getElementi();
            modelloColonne = this.getColumnModel();
            indice = 0;
            for (VistaElemento elem : elementi) {
                continua = true;

                /* recupera il campo dell'elemento e il campo originale */
                campo = elem.getCampo();
                campoOriginale = elem.getCampoOriginale();
                if (campo == null) {
                    continua = false;
                }// fine del blocco if

                /* recupera il campo del quale controllare la modificabilità in lista */
                if (continua) {
                    campoCheck = campo;
                    if (campoOriginale != null) {
                        if (!campoOriginale.equals(campo)) {
                            campoCheck = campoOriginale;
                        }// fine del blocco if
                    }// fine del blocco if-else
                }// fine del blocco if

                /* controlla se il campo di riferimento è modificabile in lista */
                if (continua) {
                    if (!campoCheck.isModificabileLista()) {
                        continua = false;
                    }// fine del blocco if
                }// fine del blocco if

                /* determina da quale campo deve essere recuperato
                 * l'editor, normalmente è il campo stesso, se il campo
                 * originale è diverso, e' il campo originale */
                if (continua) {
                    campoEditor = campo;
                    if (campoOriginale != null) {
                        if (!campoOriginale.equals(campo)) {
                            campoEditor = campoOriginale;
                        }// fine del blocco if
                    }// fine del blocco if-else
                }// fine del blocco if

                /* recupera l'editor da assegnare al campo */
                if (continua) {
                    campoDati = campoEditor.getCampoDati();
                    editor = campoDati.getEditor();
                    if (editor == null) {
                        continua = false;
                    }// fine del blocco if
                }// fine del blocco if

                /* recupera la colonna della tavola alla quale va assegnato l'editor */
                if (continua) {
                    colonna = modelloColonne.getColumn(indice);
                    if (colonna == null) {
                        continua = false;
                    }// fine del blocco if
                }// fine del blocco if

                /* se il campo non è modificabile lista, lo rende modificabile ora */
                if (continua) {
                    if (!campo.isModificabileLista()) {
                        campo.setModificabileLista(true);
                    }// fine del blocco if
                }// fine del blocco if

//                /* aggiunge i listener al campo */
//                if (continua) {
//                    Portale portale = this.getPortale();
//                    campo.getCampoVideo().aggiungeListener(portale);
//                }// fine del blocco if

                /* assegna l'editor alla colonna */
                if (continua) {
                    colonna.setCellEditor(editor);
                }// fine del blocco if

                indice++;
            }


        } catch (Exception unErrore) { // intercetta l'errore
            new Errore(unErrore);
        } /* fine del blocco try-catch */
    }

//    /**
//     * .
//     * <p/>
//     */
//    public TableCellRenderer getCellRenderer (int riga, int colonna) {
//        /* variabili e costanti locali di lavoro */
//        TableCellRenderer cr  = null;
//
//        try {	// prova ad eseguire il codice
//            cr = super.getCellRenderer(riga, colonna);
//        } catch (Exception unErrore) {	// intercetta l'errore
//            new Errore(unErrore);
//        } // fine del blocco try-catch
//
//        /* valore di ritorno */
//        return cr;
//    }


    /**
     * Regola l'altezza delle righe.
     * Regola in funzione del look-and-feel e del font utilizzato <br>
     * <p/>
     * todo la selezione dal look non funziona 23-9-04/gac
     */
    private void regolaAltezzaRighe() {
        /* variabili e costanti locali di lavoro */
        Font unFont;
        int unaDimensione;
        int altezzaRiga;

        /* recupera la dimensione del font utilizzato per le celle delle righe */
        unFont = this.getFont();
        unaDimensione = unFont.getSize();

        /* seleziona l'altezza della riga in funzione del look-and-feel */
        if (Lib.Gui.usaLook(LOOK_METAL)) {
            altezzaRiga = ALTEZZA_RIGA_LOOK_METAL;
        } else if (Lib.Gui.usaLook(LOOK_MAC)) {
            altezzaRiga = ALTEZZA_RIGA_LOOK_MAC;
        } else {
            altezzaRiga = ALTEZZA_RIGA_LOOK_METAL;
        } /* fine del blocco if/else */

        /* patch, perch� la selezione dal look non mi funziona */
        altezzaRiga = (int)(unaDimensione * DELTA);

        /* */
        this.setRowHeight(altezzaRiga);
    } /* fine del metodo */


    /**
     * Start editing when a key is typed.
     * <p/>
     * Sovrascrivo il metodo della classe JTable, per stoppare alcuni caratteri,
     * da gestire localmente per la sincronizzazione <br>
     * I caratteri filtrati sono la sommatoria di tutti i listener associati alla
     * tavola <br>
     */
    protected boolean processKeyBinding(
            KeyStroke ks,
            KeyEvent unEvento,
            int condition,
            boolean pressed) {

        /* variabili e costanti locali di lavoro */
        int codice;
        boolean valore = false;
        Lista lista;
        Gestore gestore;

        try { // prova ad eseguire il codice
            codice = unEvento.getKeyCode();

            if (this.isCarattere(codice)) {
                valore = false;
            } else {
                valore = super.processKeyBinding(ks, unEvento, condition, pressed);
            }// fine del blocco if-else

            lista = this.getLista();
            if (lista != null) {
                gestore = lista.getModulo().getGestore();
                if ((condition == JComponent.WHEN_FOCUSED) && (!pressed)) {
                    switch (codice) {
                        case KeyEvent.VK_UP:
                            /* invoca il metodo delegato della classe gestione eventi */
                            gestore.frecciaAlto(unEvento, null);
                            break;
                        case KeyEvent.VK_DOWN:
                            /* invoca il metodo delegato della classe gestione eventi */
                            gestore.frecciaBasso(unEvento, null);
                            break;
                        case KeyEvent.VK_PAGE_UP:
                            /* invoca il metodo delegato della classe gestione eventi */
//                        gestore.pagine(unEvento, null);
                            break;
                        case KeyEvent.VK_PAGE_DOWN:
                            /* invoca il metodo delegato della classe gestione eventi */
//                        gestore.pagine(unEvento, null);
                            break;
                        case KeyEvent.VK_HOME:
                            /* invoca il metodo delegato della classe gestione eventi */
                            gestore.home(unEvento, null);
                            break;
                        case KeyEvent.VK_END:
                            /* invoca il metodo delegato della classe gestione eventi */
                            gestore.end(unEvento, null);
                            break;
                        default: // caso non definito
                            break;
                    } // fine del blocco switch
                }// fine del blocco if
            }// fine del blocco if


        } catch (Exception unErrore) { // intercetta l'errore
            new Errore(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return valore;

    } /* fine del metodo */


    /**
     * Aggiunge i Listener.
     * <p/>
     * Metodo invocato dal ciclo inizializza <br>
     * Aggiunge alla JTable gli ascoltatori dei comandi (eventi) <br>
     * La tavola e' suddivisa in titoli e celle <br>
     */
    protected void aggiungeListener() {
        /* variabili e costanti locali di lavoro */
//        TableColumnModel unModelloColonna;
//        TableColumn unaColonna;
//        TableCellEditor editor;
        Lista lista;

        try {    // prova ad eseguire il codice

            /* aggiunge l'azione doppio clic nella tabella */
            this.addTopoListener(Azione.LISTA_DOPPIO_CLICK);

            /* aggiunge l'azione specifica dei titoli (testa delle colonne) */
            this.addTopoListener(Azione.TITOLO, this.getTableHeader());

            lista = this.getLista();
            if (lista != null) {
                if (lista.isUsaCarattereFiltro()) {
                    this.addCarattereListener(Azione.LISTA_CARATTERE);
                }// fine del blocco if
            }// fine del blocco if

//            /* aggiunge le azioni di controllo della JTable */
//            this.addSelezioneListener(Azione.SELEZIONE_MODIFICATA);
//
//            /* aggiunge le azioni delle celle (corpo della JTable) */
//            this.addTopoListener(Azione.LISTA_CLICK);

//            this.addCarattereListener(Azione.COLONNA);

//            this.addCarattereListener(Azione.FRECCE);
//            this.addCarattereListener(Azione.HOME);
//            this.addCarattereListener(Azione.PAGINE);
            this.addCarattereListener(Azione.LISTA_ENTER);
            this.addCarattereListener(Azione.LISTA_RETURN);
//            this.addCarattereListener(Azione.END);

//
//            /* aggiunge le azioni ad ogni colonna */
//            unModelloColonna = this.getColumnModel();
//            for (int k = 0; k < unModelloColonna.getColumnCount(); k++) {
//                unaColonna = unModelloColonna.getColumn(k);
//                editor = unaColonna.getCellEditor();
//            } /* fine del blocco for */

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } /* fine del blocco try-catch */
    }


    /**
     * Aggiunge i Listener del mouse.
     * <p/>
     * Aggiunge al componente i Listener specializzati sul mouse <br>
     * Recupera l'azione di tipo MouseListener <br>
     *
     * @param nomeAzione nome dell'azione da recuperare
     * @param componente componente a cui aggiungere l'azione
     */
    private void addTopoListener(String nomeAzione, Component componente) {
        /* variabili e costanti locali di lavoro */
        MouseListener azione;

        try {    // prova ad eseguire il codice
            azione = this.getAzMouse(nomeAzione);
            if (componente != null) {
                componente.addMouseListener(azione);
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } /* fine del blocco try-catch */
    }


    /**
     * Aggiunge i Listener del mouse.
     * <p/>
     * Aggiunge al componente i Listener specializzati sul mouse <br>
     * Recupera l'azione di tipo MouseListener <br>
     * Invoca il metodo sovrascritto della classe <br>
     * Di default usa il componente JTable <br>
     *
     * @param nomeAzione nome dell'azione da recuperare
     */
    private void addTopoListener(String nomeAzione) {
        /* invoca il metodo sovrascritto della classe */
        this.addTopoListener(nomeAzione, this);
    }


    /**
     * Aggiunge i Listener sui caratteri.
     * <p/>
     * Aggiunge al componente i Listener specializzati sui caratteri <br>
     * Recupera l'azione di tipo KeyListener <br>
     * Estrae dall'azione i caratteri filtrati <br>
     * Aggiunge i caratteri filtrati da questa azione, alla collezione della Tavola <br>
     *
     * @param nomeAzione nome dell'azione da recuperare
     * @param componente componente a cui aggiungere l'azione
     */
    private void addCarattereListener(String nomeAzione, Component componente) {
        /* variabili e costanti locali di lavoro */
        KeyListener azione;
        AzAdapterKey azioneCarattere;
        ArrayList carFiltrati;

        try {    // prova ad eseguire il codice
            azione = this.getAzKey(nomeAzione);
            if (azione != null) {

                componente.addKeyListener(azione);

                if (azione instanceof AzAdapterKey) {
                    azioneCarattere = (AzAdapterKey)azione;
                    carFiltrati = azioneCarattere.getCaratteri();

                    for (int k = 0; k < carFiltrati.size(); k++) {
                        this.caratteri.add(carFiltrati.get(k));
                    } // fine del ciclo for
                }// fine del blocco if

            }// fine del blocco if


        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } /* fine del blocco try-catch */
    }


    /**
     * Aggiunge i Listener sui caratteri.
     * <p/>
     * Aggiunge al componente i Listener specializzati sui caratteri <br>
     * Recupera l'azione di tipo KeyListener <br>
     * Estrae dall'azione i caratteri filtrati <br>
     * Aggiunge i caratteri filtrati da questa azione, alla collezione della Tavola <br>
     * Invoca il metodo sovrascritto della classe <br>
     * Di default usa il componente il modello di selezione della lista della JTable <br>
     *
     * @param nomeAzione nome dell'azione da recuperare
     */
    private void addCarattereListener(String nomeAzione) {
        /* invoca il metodo sovrascritto della classe */
        this.addCarattereListener(nomeAzione, this);
    }

//    /**
//     * Aggiunge i Listener sulla selezione delle righe.
//     * <p/>
//     * Aggiunge al componente i Listener specializzati sulla selezione delle righe <br>
//     * Recupera l'azione di tipo ListSelectionListener <br>
//     *
//     * @param nomeAzione nome dell'azione da recuperare
//     * @param lista      modello di selezione della lista a cui aggiungere l'azione
//     */
//    private void addSelezioneListener(String nomeAzione, ListSelectionModel lista) {
//        /* variabili e costanti locali di lavoro */
//        ListSelectionListener azione = null;
//
//        try {	// prova ad eseguire il codice
//            azione = this.getAzSelection(nomeAzione);
//            lista.addListSelectionListener(azione);
//        } catch (Exception unErrore) {	// intercetta l'errore
//            new Errore(unErrore);
//        } /* fine del blocco try-catch */
//    }

//    /**
//     * Aggiunge i Listener sulla selezione delle righe.
//     * <p/>
//     * Aggiunge al componente i Listener specializzati sulla selezione delle righe <br>
//     * Recupera l'azione di tipo ListSelectionListener <br>
//     * Invoca il metodo sovrascritto della classe <br>
//     * Di default usa il componente il modello di selezione della lista della JTable <br>
//     *
//     * @param nomeAzione nome dell'azione da recuperare
//     */
//    private void addSelezioneListener(String nomeAzione) {
//        /* variabili e costanti locali di lavoro */
//        ListSelectionModel lista = null;
//
//        try { // prova ad eseguire il codice
//
//            lista = this.getSelectionModel();
//
//            /* invoca il metodo sovrascritto della classe */
//            this.addSelezioneListener(nomeAzione, lista);
//        } catch (Exception unErrore) { // intercetta l'errore
//            new Errore(unErrore);
//        }// fine del blocco try-catch
//    }

//    /**
//     * Aggiunge i Listener del fuoco.
//     * <p/>
//     * Aggiunge al componente i Listener specializzati sul fuoco <br>
//     * Recupera l'azione di tipo FocusListener <br>
//     * Invoca il metodo sovrascritto della classe <br>
//     *
//     * @param nomeAzione nome dell'azione da recuperare
//     * @param editor     componente a cui aggiungere l'azione
//     */
//    private void addFuocoListener(String nomeAzione, TableCellEditor editor) {
//        /* variabili e costanti locali di lavoro */
//        FocusListener azione = null;
//
//        try {	// prova ad eseguire il codice
//            azione = this.getAzFocus(nomeAzione);
//        } catch (Exception unErrore) {	// intercetta l'errore
//            new Errore(unErrore);
//        } /* fine del blocco try-catch */
//    }


    /**
     * Intercetta prepareRenderer di JTable.
     * <p/>
     * Prepara il renderer per una cella prima che venga disegnato.
     *
     * @param renderer il renderer della cella
     * @param riga l'indice della riga (0 per la prima)
     * @param colonna l'indice della colonna (0 per la prima)
     *
     * @return il componente da visualizzare nella cella
     */
    public Component prepareRenderer(TableCellRenderer renderer, int riga, int colonna) {
        /* variabili e costanti locali di lavoro */
        Component comp = null;
        Lista lista;

        try { // prova ad eseguire il codice
        	if (renderer!=null) {
                comp = super.prepareRenderer(renderer, riga, colonna);
                lista = this.getLista();
                if (lista != null) {
                    if (comp!=null) {
                        comp = lista.prepareRenderer(comp, riga, colonna);
        			}
                }// fine del blocco if
			}

        } catch (Exception unErrore) { // intercetta l'errore
            new Errore(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return comp;
    }


    /**
     * Metodo chiamato dalla JTable quando comincia o finisce
     * una sessione di editing di una cella.
     * <p/>
     * Metodo invocato dal ciclo System <br>
     */
    public void setCellEditor(TableCellEditor anEditor) {

        super.setCellEditor(anEditor);

        if (anEditor == null) { // editing finished or canceled
            this.editEnd();
        } else { // editing about to start
            this.editStart();
        }
    }


    /**
     * Metodo chiamato all'inizio dell'editing di una cella.
     * <p/>
     */
    private void editStart() {
        /* variabili e costanti locali di lavoro */
        try { // prova ad eseguire il codice
        } catch (Exception unErrore) { // intercetta l'errore
            new Errore(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Metodo chiamato al termine dell'editing di una cella.
     * <p/>
     */
    private void editEnd() {
        /* variabili e costanti locali di lavoro */
        try { // prova ad eseguire il codice
            int a = 87;
        } catch (Exception unErrore) { // intercetta l'errore
            new Errore(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Elimina le colonne non visibili.
     * <p/>
     * Elimina nella GUI e NON nel modello le colonne non visibili <br>
     * Si basa sulla visibilit� indicata nella Vista <br>
     * Presuppone che l'ordinamento delle colonne nella Vista e nella
     * tavola (JTable), sia identico <br>
     * <p/>
     * Il ciclo for e' discendente, perche' la posizione delle colonne
     * eliminate verrebbe influenzata dalle precedenti <br>
     * Spazzola tutte le colonne e le convalida SOLO se la Vista gli
     * conferma che deve essere mostrata - le altre le rimuove <br>
     * <p/>
     * Di solito elimina solo il Campo codice <br>
     */
    public void regolaVisibilitaColonne() {
        /* variabili e costanti locali di lavoro */
        TableColumn unaColonna;
        int colonne;
        TableColumnModel tavMod;

        try { // prova ad eseguire il codice
            colonne = this.getVista().getColumnCount();

            for (int k = colonne - 1; k >= 0; k--) {
                /* recupera la singola colonna */
                tavMod = this.getColumnModel();

                if (tavMod.getColumnCount() == 0) {
                    return;
                }// fine del blocco if

                unaColonna = tavMod.getColumn(k);

                /* solo quelli confermati visibili dalla Vista */
                if (!this.getVista().isColonnaVisibile(k)) {
                    this.removeColumn(unaColonna);
                } /* fine del blocco if/else */
            } /* fine del blocco for */
        } catch (Exception unErrore) { // intercetta l'errore
            new Errore(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Regola la larghezza delle colonne.
     * <p/>
     * Invocato dal metodo Inizializza
     * Distingue tra colonne fisse e variabili <br>
     * Recupera dalla Vista le colonne visibili nell'ordine corretto <br>
     * Calcola la larghezza preferita della Lista, come somma di tutte le
     * larghezze preferite delle colonne <br>
     * La differenza di larghezza rispetto alla finestra viene ripartita in
     * maniera ponderale tra le colonne variabili <br>
     * Regola ogni oggetto della TableColumnModel con la larghezza calcolata <br>
     */
    private void regolaLarghezzaColonne() {
        /* variabili e costanti locali di lavoro */
        ArrayList elementi;
        Vista vista;
        VistaElemento elemento;
        TableColumnModel modelloColonne;
        TableColumn unaColonna;
        Campo unCampo;
        int lar;
        int min;

        try { // prova ad eseguire il codice

            vista = this.getVista();
            elementi = vista.getElementiVisibili();
            modelloColonne = this.getColumnModel();

            int tot = 0;

            for (int k = 0; k < elementi.size(); k++) {
                elemento = (VistaElemento)elementi.get(k);
                unCampo = (Campo)elemento.getContenuto();

                /** determina la larghezza preferita della colonna.
                 * se specificato, usa quella dell'elemento, altrimenti
                 * usa quella del campo. */
                lar = elemento.getLarghezzaColonna();
                if (lar == 0) {
                    lar = unCampo.getCampoLista().getLarghezzaColonna();
                }// fine del blocco if

                /** determina la larghezza minima della colonna.
                 * per ora usa sempre quella del campo. */
                min = unCampo.getCampoLista().getLarghezzaMinima();

                unaColonna = modelloColonne.getColumn(k);
                if (elemento.isRidimensionabile()) {
                    unaColonna.setPreferredWidth(lar);
                    unaColonna.setMinWidth(min);
                    unaColonna.setResizable(true);
                } else {
                    unaColonna.setMinWidth(lar);
                    unaColonna.setMaxWidth(lar);
                    unaColonna.setResizable(false);
                }// fine del blocco if-else

            } // fine del ciclo for

            // qui la largezza della tavola è corretta...
            // la larghezza dello scroller no......
//            Dimension c = this.getPreferredSize();
//            Dimension d = new Dimension(tot, (int)c.getHeight());
//            Dimension e = this.getPreferredScrollableViewportSize();
//            this.setPreferredScrollableViewportSize(this.getPreferredSize());
            int a = 87;

        } catch (Exception unErrore) { // intercetta l'errore
            new Errore(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Regola i font ed i colori dei titoli delle colonne.
     * <p/>
     * La colonna di ordinamento parte da 1, mentre le colonne del
     * modello partono da zero <br>
     */
    private void regolaFontColoriTitoli() {
        /* variabili e costanti locali di lavoro */
        ArrayList colonne;
        RendererTitolo unRendererTitolo; // renderer dei titoli delle colonne
        TableColumnModel modelloColonne;
        TableColumn unaColonna;
        Campo unCampo;
        String unToolTipText;

        try { // prova ad eseguire il codice
            colonne = this.getVista().getCampiVisibili();
            modelloColonne = this.getColumnModel();

            /*  ciclo for */
            for (int k = 0; k < colonne.size(); k++) {
                unCampo = (Campo)colonne.get(k);
                unaColonna = modelloColonne.getColumn(k);
                unRendererTitolo = new RendererTitolo();

                /* recupera il toolTipText */
                unToolTipText = unCampo.getCampoLista().getToolTipText();

                unRendererTitolo.setToolTipText(unToolTipText);
                unaColonna.setHeaderRenderer(unRendererTitolo);
            } /* fine del blocco for */
        } catch (Exception unErrore) { // intercetta l'errore
            new Errore(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Ritorna l'indice della colonna cliccata.
     * <p/>
     *
     * @param evento l'evento generato dal clic
     *
     * @return l'indice della colonna cliccata (0 per la prima,
     *         -1 se il clic e' fuori dalle colonne)
     */
    public int getColonnaCliccata(MouseEvent evento) {
        /* variabili e costanti locali di lavoro */
        int colonna = 0;
        TableColumnModel modello;
        int x;

        try {    // prova ad eseguire il codice
            modello = this.getColumnModel();
            x = evento.getX();
            colonna = modello.getColumnIndexAtX(x);

            /* registra come attributo la colonna selezionata (1 per la prima) */
            this.setColonnaSelezionata(colonna + 1);

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return colonna;
    }


    /**
     * Seleziona una riga.
     * <p/>
     *
     * @param posizione la posizione assoluta della riga da selezionare
     * (0 per la prima riga)
     */
    public void selezionaRiga(int posizione) {
        /* variabili e costanti locali di lavoro */
        int quanteRighe = 0;
        int[] selezione = null;

        try { // prova ad eseguire il codice

            /* numero di righe esistenti */
            quanteRighe = this.getRowCount();

            /* controlla che la posizione richiesta esista
             * seleziona la riga */
            if (posizione < quanteRighe) {
                selezione = new int[1];
                selezione[0] = posizione;
                this.selezionaRighe(selezione);
            } /* fine del blocco if */

        } catch (Exception unErrore) { // intercetta l'errore
            new Errore(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Seleziona un gruppo di righe.
     * <p/>
     *
     * @param righe l'elenco delle righe da selezionare
     */
    public void selezionaRighe(int[] righe) {
        /* variabili e costanti locali di lavoro */
        ListSelectionModel sm = null;
        int posizione = 0;

        try {    // prova ad eseguire il codice

            sm = this.getSelectionModel();
            sm.clearSelection();
            for (int k = 0; k < righe.length; k++) {
                posizione = righe[k];
                sm.addSelectionInterval(posizione, posizione);
            } // fine del ciclo for

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Porta una riga in area visibile.
     * <p/>
     * Se la riga richiesta è superiore al numero di righe visualizzate,
     * si posiziona sull'ultima riga visibile <br>
     *
     * @param riga la riga da rendere visibile
     */
    public void rendiRigaVisibile(int riga) {
        try { // prova ad eseguire il codice
            if (riga >= 0) {
                JTableScrolling.makeRowVisible(this, riga);
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Seleziona tutti i records.
     * Controlla che esistano dei records <br>
     * Seleziona tutte le righe <br>
     */
    public void selezionaTutti() {
        /* variabili e costanti locali di lavoro */
        int righe;
        try { // prova ad eseguire il codice
            /* numero di righe esistenti */
            righe = this.getRowCount();

            /* controlla che esistano dei records */
            if (righe > 0) {
                /* seleziona tutte le righe */
                this.setRowSelectionInterval(0, righe - 1);
            } /* fine del blocco if/else */
        } catch (Exception unErrore) { // intercetta l'errore
            new Errore(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Elimina la selezione corrente.
     * <p/>
     */
    public void eliminaSelezione() {
        this.selezionaRighe(new int[0]);
    }


    /**
     * Ritorna il numero di righe della Tavola.
     * <p/>
     * Se la Tavola ha un modello dati, rimanda alla superclasse
     * Se la Tavola non ha un modello dati, ritorna zero.
     *
     * @return il numero di righe
     */
    public int getRowCount() {
        /* variabili e costanti locali di lavoro */
        int numero = 0;

        try {    // prova ad eseguire il codice
            if (this.getModel() != null) {
                numero = super.getRowCount();
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return numero;
    }


    /**
     * Ritorna il numero di colonne della Tavola.
     * <p/>
     * Se la Tavola ha un modello dati, rimanda alla superclasse
     * Se la Tavola non ha un modello dati, ritorna zero.
     *
     * @return il numero di colonne
     */
    public int getColumnCount() {
        /* variabili e costanti locali di lavoro */
        int numero = 0;

        try {    // prova ad eseguire il codice
            if (this.getModel() != null) {
                numero = super.getColumnCount();
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return numero;
    }


    public TavolaModello getModello() {
        return modello;
    }


    public void setModello(TavolaModello modello) {
        this.modello = modello;
        if (modello != null) {
            this.setModel(modello);
        }// fine del blocco if
    }


    public Lista getLista() {
        return lista;
    }


    public void setLista(Lista lista) {
        this.lista = lista;
    }


    private Portale getPortale() {
        /* variabili e costanti locali di lavoro */
        Portale portale = null;
        Lista lista;

        try { // prova ad eseguire il codice
            lista = this.getLista();
            if (lista != null) {
                portale = lista.getPortale();
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return portale;
    }


    private Vista getVista() {
//        return getLista().getVista();
        return getModello().getVista();
    }


    /**
     * Ritorna il nome di una colonna visibile.
     * <p/>
     *
     * @param colonna indice della colonna visibile
     *
     * @return nome della colonna specificata
     */
    public String getVisibleColumnName(int colonna) {
        /* variabili e costanti locali di lavoro */
        String nome = "";
        TavolaModello modello;

        try {    // prova ad eseguire il codice
            modello = this.getModello();
            if (modello != null) {
                nome = modello.getVisibleColumnName(colonna);
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return nome;
    } /* fine del metodo */


    /**
     * Assegna il titolo a una colonna visibile
     *
     * @param colonna indice della colonna visibile (0 per la prima)
     * @param nome nome della colonna
     */
    public void setVisibleColumnName(int colonna, String nome) {
        /* variabili e costanti locali di lavoro */
        TavolaModello modello;

        try {    // prova ad eseguire il codice
            modello = this.getModello();
            if (modello != null) {
                modello.setVisibleColumnName(colonna, nome);
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Controlla se il carattere &egrave; compreso tra quelli della collezione.
     * <p/>
     *
     * @param codiceCarattere codifica del carattere
     *
     * @return vero se &egrave; nella collezione
     */
    public boolean isCarattere(int codiceCarattere) {
        /* variabili e costanti locali di lavoro */
        boolean compreso = false;

        try { // prova ad eseguire il codice
            compreso = Lib.Mat.isEsiste(this.caratteri, codiceCarattere);
        } catch (Exception unErrore) { // intercetta l'errore
            new Errore(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return compreso;
    }


    /**
     * Stampa questa tavola.
     * <p/>
     *
     * @return true se stampata
     */
    public boolean stampa() {
        return this.stampa("");
    }


    /**
     * Stampa questa tavola.
     * <p/>
     *
     * @param titolo della stampa
     *
     * @return true se stampata
     */
    public boolean stampa(String titolo) {
        /* variabili e costanti locali di lavoro */
        boolean continua = false;
        J2Printer printer;

        try { // prova ad eseguire il codice
            printer = this.getPrinter(titolo);
            printer.print();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return continua;
    }


    /**
     * Crea un Printer per questa Tavola.
     * <p/>
     *
     * @return il Printer creato
     */
    public J2Printer getPrinter() {
        return this.getPrinter("");
    }


    /**
     * Crea un Printer per questa Tavola.
     * <p/>
     *
     * @param titolo della stampa
     *
     * @return il Printer creato
     */
    public J2Printer getPrinter(String titolo) {
        /* variabili e costanti locali di lavoro */
        J2Printer printer = null;
        J2TablePrinter tablePrinter;

        try {    // prova ad eseguire il codice

            tablePrinter = this.getTablePrinter();

            /* crea e regola il Printer */
            printer = Lib.Stampa.getDefaultPrinter();
            printer.setCenterHeader(titolo);
            printer.setRightHeader(this.getRowCount() + " records ");
            printer.addPageable(tablePrinter);

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return printer;
    }


    /**
     * Ritorna un oggetto stampabile per questa Tavola.
     * <p/>
     *
     * @return l'oggetto stampabile
     */
    public J2TablePrinter getTablePrinter() {
        return this.getTablePrinter(true);
    }


    /**
     * Ritorna un oggetto stampabile per questa Tavola.
     * <p/>
     *
     * @param printHeader true per stampare anche l'header
     *
     * @return l'oggetto stampabile
     */
    public J2TablePrinter getTablePrinter(boolean printHeader) {
        /* variabili e costanti locali di lavoro */
        J2TablePrinter tablePrinter = null;
        JTable table;
        int colHdrPrint;

        try {    // prova ad eseguire il codice

            /* se non è stata ancora inizializzata la inizializza ora */
            if (!this.isInizializzata()) {
                this.inizializza();
            }// fine del blocco if

            if (printHeader) {
                colHdrPrint = J2TablePrinter.ALL_PAGES;
            } else {
                colHdrPrint = J2TablePrinter.NO_PAGES;
            }// fine del blocco if-else

            /* crea e regola un TablePrinter */
            table = this.creaJTableStampa();
            tablePrinter = new J2TablePrinter(table);
            tablePrinter.setColumnHeaderPrinting(colHdrPrint);
            tablePrinter.setHorizontalAlignment(J2TablePrinter.CENTER);
            tablePrinter.setHorizontalPageRule(J2TablePrinter.SHRINK_TO_FIT);
            tablePrinter.setOverlap(true);
            tablePrinter.setMaximumPaginationGap(1.0);// non spezza mai le righe su due pagine

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return tablePrinter;
    }


    /**
     * Crea la JTable da stampare completa di dati.
     * <p/>
     *
     * @return la JTable creata
     */
    private JTable creaJTableStampa() {
        /* variabili e costanti locali di lavoro */
        Tavola newTable = null;
        Dati dati;
        JTableHeader header;
        int quante;
        TableColumnModel cmod1;
        TableColumnModel cmod2;
        TableColumn col;
        TableCellRenderer renderer;
        int lar;

        try {    // prova ad eseguire il codice

            /**
             * crea, registra e regola una nuova JTable
             * con lo stesso modello dati
             */
            newTable = new Tavola(this.getModello());
            newTable.setBackground(Color.white);
            newTable.setFont(FontFactory.creaPrinterFont(9f));
            newTable.setIntercellSpacing(new Dimension(5, 5));
            newTable.setRowHeight(25);
            newTable.setGridColor(Color.BLACK);
            newTable.getTableHeader().setOpaque(false);

            newTable.getTableHeader().setFont(FontFactory.creaPrinterFont(Font.BOLD, 9f));
            newTable.getTableHeader().setForeground(Color.black);
            newTable.getTableHeader().setBackground(Color.white);


            /* tiene solo le colonne confermate visibili dalla Vista */
            cmod1 = newTable.getColumnModel();
            quante = this.getVista().getColumnCount();
            for (int k = quante - 1; k >= 0; k--) {
                if (k < cmod1.getColumnCount()) {
                    col = cmod1.getColumn(k);
                    if (!this.getVista().isColonnaVisibile(k)) {
                        newTable.removeColumn(col);
                    } /* fine del blocco if/else */
                }// fine del blocco if
            } /* fine del blocco for */


            /* trasporta i renderers */
            cmod1 = this.getColumnModel();
            cmod2 = newTable.getColumnModel();
            quante = cmod2.getColumnCount();
            for (int k = 0; k < quante; k++) {
                TableColumn col1 = cmod1.getColumn(k);
                TableColumn col2 = cmod2.getColumn(k);
                renderer = col1.getCellRenderer();
                col2.setCellRenderer(renderer);

                //vecchio sistema, ora copiamo i renderers dalla tavola di origine
                //col.setCellRenderer(new RendererCella(newTable));

            } /* fine del blocco for */


            /* assegna le larghezze alle colonne */
            cmod2 = newTable.getColumnModel();
            quante = cmod2.getColumnCount();
            for (int k = 0; k < quante; k++) {
                col = cmod2.getColumn(k);
                lar = this.getLarColonnaVisibile(k);
                col.setMinWidth(lar);
                col.setPreferredWidth(lar);
                col.setMaxWidth(lar);


            } // fine del ciclo for

            /* regola l'header dopo aver regolato le larghezze colonna */
            header = newTable.getTableHeader();
            header.setFont(FontFactory.creaPrinterFont(Font.BOLD, 8f));
            int w = header.getPreferredSize().width;
            int h = header.getPreferredSize().height;
            header.setPreferredSize(new Dimension(w, h + 10));
            header.setBackground(Color.lightGray);

//            todo - invece di mettere il background color all'header (vedi sopra)
//            todo - posso sostituire il renderer delle celle dell'header
//            todo - può essere una strada interessante - alex 06-2008
//            Enumeration en = table.getColumnModel().getColumns();
//            while (en.hasMoreElements()) {
//                Object o = en.nextElement();
//                if (o instanceof TableColumn) {
//                    TableColumn tc = (TableColumn)o;
//                    tc.setHeaderRenderer(new RendererCellaHeader());
//                }// fine del blocco if
//            }

            newTable.doLayout();

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return newTable;
    }


    /**
     * Renderer per la JTable ad altezza riga variabile
     */
    private class RendererCella extends DefaultTableCellRenderer {

        /* JTable di riferimento */
        private JTable table;

        /* componenti previsti in funzione della classe
        * li mantengo qui per prestazioni */
        private JCheckBox checkBox;

        private WrapTextArea wArea;

        private JLabel label;


        /**
         * Costruttore completo.
         * <p/>
         *
         * @param table la JTable di riferimento
         */
        public RendererCella(JTable table) {
            /* rimanda al costruttore della superclasse */
            super();

            this.table = table;

            try { // prova ad eseguire il codice
                /* regolazioni iniziali di riferimenti e variabili */
                this.inizia();
            } catch (Exception unErrore) { // intercetta l'errore
                new Errore(unErrore);
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
            try { // prova ad eseguire il codice

                checkBox = new JCheckBox();
                checkBox.setOpaque(false);
                checkBox.setVerticalAlignment(SwingConstants.TOP);
                checkBox.setHorizontalAlignment(SwingConstants.CENTER);
                checkBox.setBorder(null);

                wArea = new WrapTextArea();
                wArea.setOpaque(false);
                wArea.setFont(table.getFont());

                label = new JLabel();
                label.setOpaque(false);
                label.setFont(table.getFont());
                label.setHorizontalAlignment(SwingConstants.LEFT);
                label.setVerticalAlignment(SwingConstants.CENTER);


            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch


        }


        public Component getTableCellRendererComponent(
                JTable table,
                Object object,
                boolean b,
                boolean b1,
                int row,
                int col) {

            /* variabili e costanti locali di lavoro */
            Component comp = null;
            String stringa;
            Date data;
            boolean bool;
            int minHeigth;
            int actualHeigth;
            Campo campo;
            RendererBase renderer;
            Object objOut;

            try { // prova ad eseguire il codice

                /* delega al campo dati il rendering del valore */
                campo = getVista().getCampoVisibile(col);
                renderer = campo.getCampoDati().getRenderer();
                objOut = object;
                if (renderer != null) {
                    objOut = renderer.rendValue(object);
                }// fine del blocco if-else

                /* caso stringa */
                if (campo.isTesto()) {
                    stringa = Lib.Testo.getStringa(objOut);
                    label.setText(stringa);
                    label.setHorizontalAlignment(SwingConstants.LEFT);
                    comp = label;
                }// fine del blocco if

                /* caso booleano */
                if (campo.isBooleano()) {
                    bool = Libreria.getBool(objOut);
                    checkBox.setSelected(bool);
                    comp = checkBox;
                }// fine del blocco if

                /* caso numero */
                if (campo.isNumero()) {
                    stringa = Lib.Testo.getStringa(objOut);
                    label.setText(stringa);
                    label.setHorizontalAlignment(SwingConstants.RIGHT);
                    comp = label;
                }// fine del blocco if

                /* caso data */
                if (campo.isData()) {
                    data = Libreria.getDate(objOut);
                    stringa = Lib.Data.getStringa(data);
                    label.setText(stringa);
                    label.setHorizontalAlignment(SwingConstants.CENTER);
                    comp = label;
                }// fine del blocco if

//                /* caso stringa */
//                if (objOut instanceof String) {
//                    stringa = (String)objOut;
//
////                    wArea.setText(stringa);
////                    wArea.setWidth(getLarColonnaVisibile(col)); // regola automaticamente l'altezza
////                    comp = wArea;
//
//                    label.setText(stringa);
//                    comp = label;
//
//                }// fine del blocco if
//
//                /* caso booleano */
//                if (objOut instanceof Boolean) {
//                    bool = (Boolean)objOut;
//                    checkBox.setSelected(bool);
//                    comp = checkBox;
//                }// fine del blocco if
//
//                /* caso numero */
//                if (objOut instanceof Number) {
//                    Number numero = (Number)objOut;
//                    stringa = Lib.Testo.getStringa(numero);
//                    label.setText(stringa);
//                    label.setHorizontalAlignment(SwingConstants.RIGHT);
//                    comp = label;
//                }// fine del blocco if
//
//                /* caso data */
//                if (objOut instanceof Date) {
//                    data = (Date)objOut;
//                    stringa = Lib.Data.getStringa(data);
//                    label.setText(stringa);
//                    label.setHorizontalAlignment(SwingConstants.CENTER);
//                    comp = label;
//                }// fine del blocco if

                /* se non ha trovato il componente, lo richiede alla superclasse*/
                if (comp == null) {
                    comp = super.getTableCellRendererComponent(table, object, b, b1, row, col);
                }// fine del blocco if

                /* se l'altezza della riga della JTable è inferiore a quella
                 * del componente, aumenta l'altezza della riga della JTable
                 * Attenzione! il comando setRowHeight va chiamato per tutte le
                 * righe prima di iniziare la stampa della JTable se no non ha effetto */
                minHeigth = comp.getPreferredSize().height + table.getIntercellSpacing().height;
                actualHeigth = table.getRowHeight(row);
                if (actualHeigth < minHeigth) {
//                    table.setRowHeight(row, minHeigth);
                }// fine del blocco if
//                table.setRowHeight(row, 30);

            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

            /* valore di ritorno */
            return comp;

        }

    }

    /**
     * Renderer per le celle dell'header della JTable di stampa
     */
    private class RendererCellaHeader extends DefaultTableCellRenderer {

        JLabel label;


        /**
         * Costruttore completo.
         * <p/>
         */
        public RendererCellaHeader() {
            /* rimanda al costruttore della superclasse */
            super();

            try { // prova ad eseguire il codice
                /* regolazioni iniziali di riferimenti e variabili */
                this.inizia();
            } catch (Exception unErrore) { // intercetta l'errore
                new Errore(unErrore);
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
            try { // prova ad eseguire il codice

                label = new JLabel();
                label.setFont(FontFactory.creaPrinterFont(Font.BOLD, 9f));
                label.setOpaque(true);
                label.setBackground(Color.lightGray);
//                label.setBorder(BorderFactory.createLineBorder(Color.black));

            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch


        }


        public Component getTableCellRendererComponent(
                JTable table,
                Object object,
                boolean b,
                boolean b1,
                int row,
                int col) {

            /* variabili e costanti locali di lavoro */
            Component comp = null;

            try { // prova ad eseguire il codice

//                comp = super.getTableCellRendererComponent(table,  object, b, b1, row, col);
//                comp.setBackground(Color.lightGray);

                String stringa = Lib.Testo.getStringa(object);
                label.setText(stringa);
                comp = label;

            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

            /* valore di ritorno */
            return comp;

        }

    }


    /**
     * Ritorna la larghezza desiderata per una colonna visibile dalla Vista.
     * <p/>
     *
     * @param colonna indice di colonna visibile (0 la prima)
     *
     * @return la larghezza desiderata
     */
    private int getLarColonnaVisibile(int colonna) {
        /* variabili e costanti locali di lavoro */
        int lar = 0;
        ArrayList<VistaElemento> listaElementi;
        VistaElemento elem;

        try {    // prova ad eseguire il codice
            listaElementi = this.getVista().getElementiVisibili();
            elem = listaElementi.get(colonna);
            lar = elem.getLarghezzaColonna();
            if (lar == 0) {
                lar = elem.getCampo().getCampoLista().getLarghezzaColonna();
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return lar;
    }


    /**
     * Invocato da Java quando cambia la selezione delle righe nella JTable.
     * <p/>
     * Lancia un evento di Selezione Modificata per la tavola.
     * <p/>
     */
    public void valueChanged(ListSelectionEvent e) {
        /* variabili e costanti locali di lavoro */
        boolean adjusting;

        super.valueChanged(e);

        try {    // prova ad eseguire il codice

            // aspetta che la regolazione sia definitiva
            adjusting = e.getValueIsAdjusting();
            if (!adjusting) {
                this.fire(Tavola.Evento.selModificata);
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Restituisce un'azione che implementa l'interfaccia MouseListener.
     * <p/>
     *
     * @param unaChiave nome interno dell'azione
     *
     * @return oggetto di tipo specifico per il Listener
     */
    private MouseListener getAzMouse(String unaChiave) {
        /* variabili e costanti locali di lavoro */
        MouseListener listener = null;
        Portale portale;

        try { // prova ad eseguire il codice

            /* invoca il metodo delegato della classe */
            portale = this.getPortale();
            if (portale != null) {
                listener = portale.getAzMouse(unaChiave);
            }// fine del blocco if


        } catch (Exception unErrore) { // intercetta l'errore
            new Errore(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return listener;
    }


    /**
     * Restituisce un'azione che implementa l'interfaccia KeyListener.
     * <p/>
     *
     * @param unaChiave nome interno dell'azione
     *
     * @return oggetto di tipo specifico per il Listener
     */
    private KeyListener getAzKey(String unaChiave) {
        /* variabili e costanti locali di lavoro */
        KeyListener listener = null;
        Portale portale;

        try { // prova ad eseguire il codice

            /* invoca il metodo delegato della classe */
            portale = this.getPortale();
            if (portale != null) {
                listener = portale.getAzKey(unaChiave);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            new Errore(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return listener;
    }


    /**
     * Restituisce un'azione che implementa l'interfaccia ListSelectionListener.
     * <p/>
     *
     * @param unaChiave nome interno dell'azione
     *
     * @return oggetto di tipo specifico per il Listener
     */
    private ListSelectionListener getAzSelection(String unaChiave) {
        /* variabili e costanti locali di lavoro */
        ListSelectionListener listener = null;
        Portale portale;

        try { // prova ad eseguire il codice

            /* invoca il metodo delegato della classe */
            portale = this.getPortale();
            if (portale != null) {
                listener = portale.getAzListSelection(unaChiave);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            new Errore(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return listener;
    }


    /**
     * Restituisce un'azione che implementa l'interfaccia FocusListener.
     * <p/>
     *
     * @param unaChiave nome interno dell'azione
     *
     * @return oggetto di tipo specifico per il Listener
     */
    private FocusListener getAzFocus(String unaChiave) {
        /* variabili e costanti locali di lavoro */
        FocusListener listener = null;
        Portale portale;

        try { // prova ad eseguire il codice

            /* invoca il metodo delegato della classe */
            portale = this.getPortale();
            if (portale != null) {
                listener = portale.getAzFocus(unaChiave);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            new Errore(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return listener;
    }


    public int getColonnaSelezionata() {
        return colonnaSelezionata;
    }


    public void setColonnaSelezionata(int colonnaSelezionata) {
        this.colonnaSelezionata = colonnaSelezionata;
    }


    private boolean isInizializzata() {
        return inizializzata;
    }


    private void setInizializzata(boolean inizializzata) {
        this.inizializzata = inizializzata;
    }


    /**
     * Controlla la modificabilità globale della tavola (editing in lista)
     * <p/>
     *
     * @param modificabile true per rendere la tavola modificabile a livello globale
     * (la modificabilità di ogni singolo campo è controllata dal campo)
     */
    public void setModificabile(boolean modificabile) {
        TavolaModello modello;

        try { // prova ad eseguire il codice
            modello = this.getModello();
            if (modello != null) {
                modello.setModificabile(modificabile);
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
            new Errore(unErrore);
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
            new Errore(unErrore);
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
            new Errore(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Avvisa tutti i listener.
     * <p/>
     * Avvisa tutti i listener che si sono registrati
     * per questo tipo di evento <br>
     * L'evento viene creato al momento <br>
     * � responsabilit� della classe invocare questo metodo quando
     * si creano le condizioni per generare l'evento <br>
     *
     * @see javax.swing.event.EventListenerList
     */
    public void fire(Eventi unEvento) {
        /* variabili e costanti locali di lavoro */
        EventListenerList listaListener;

        try {    // prova ad eseguire il codice
            listaListener = this.getListaListener();
            Lib.Eventi.fire(listaListener, unEvento, Tavola.class, this);
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Classe interna Enumerazione.
     * <p/>
     * Eventi che possono essere lanciati dall'oggetto <br>
     * Per ogni evento: <ul>
     * <li> classe interfaccia </li>
     * <li> classe evento </li>
     * <li> classe azione </li>
     * <li> metodo azione </li>
     * </ul>
     */
    public enum Evento implements Eventi {

        generico(TavolaGenLis.class, TavolaGenEve.class, TavolaGenAz.class, "tavolaGenAz"),
        selModificata(TavolaSelLis.class, TavolaSelEve.class, TavolaSelAz.class, "tavolaSelAz"),
        resizeColonna(
                TavolaColResizeLis.class,
                TavolaColResizeEve.class,
                TavolaColResizeAz.class,
                "tavolaColResizeAz");

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
                new Errore(unErrore);
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

    class AlgosTextField extends JTextField {

        /**
         * Constructs a new TextField.  A default model is created, the initial
         * string is null, and the number of columns is set to 0.
         */
        public AlgosTextField() {
            super();
        }
    }

    class AlgosCellEditor extends DefaultCellEditor {

        AlgosTextField campoTesto = null;


        public AlgosCellEditor(AlgosTextField campoTesto) {
            super(campoTesto);
            this.campoTesto = campoTesto;
        }


        public Component getTableCellEditorComponent(
                JTable table,
                Object value,
                boolean isSelected,
                int row,
                int column) {
            if (isSelected) {
                campoTesto.selectAll();
            }// fine del blocco if
            return super.getTableCellEditorComponent(table, value, isSelected, row, column);
        }

    }

    /**
     * Column Model Listener.
     * <p/>
     * Intercetta gli eventi di modifica delle colonne
     */
    private final class ColumnModelListener implements TableColumnModelListener {

        private JTable table;


        // It is necessary to keep the table since it is not possible
        // to determine the table from the event's source
        public ColumnModelListener(JTable table) {
            this.table = table;
        }


        public void columnAdded(TableColumnModelEvent e) {
            int fromIndex = e.getFromIndex();
            int toIndex = e.getToIndex();
            // fromIndex and toIndex identify the range of added columns
        }


        public void columnRemoved(TableColumnModelEvent e) {
            int fromIndex = e.getFromIndex();
            int toIndex = e.getToIndex();
            // fromIndex and toIndex identify the range of removed columns
        }


        public void columnMoved(TableColumnModelEvent e) {
            int fromIndex = e.getFromIndex();
            int toIndex = e.getToIndex();
            // fromIndex and toIndex identify the range of columns being moved.
            // In the case of a user dragging a column, this event is fired as
            // the column is being dragged to its new position. Also, if the
            // column displaces another during dragging, the fromIndex and
            // toIndex show its new position; this new position is only
            // temporary until the user stops dragging the column.
        }


        public void columnMarginChanged(ChangeEvent e) {
            // The width of some column has changed.
            // The event does not identify which column.
            fire(Tavola.Evento.resizeColonna);
        }


        public void columnSelectionChanged(ListSelectionEvent e) {
            int a = 87;
            // See e964 Listening for Selection Events in a JTable Component

        } // fine della classe 'interna'


    }// fine della classe

}
