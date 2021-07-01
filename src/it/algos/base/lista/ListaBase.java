/**
 * Title:     ListaBase
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      7-set-2004
 */
package it.algos.base.lista;

import com.wildcrest.j2printerworks.J2FlowPrinter;
import com.wildcrest.j2printerworks.J2Printer;
import com.wildcrest.j2printerworks.J2TablePrinter;
import it.algos.base.campo.base.Campo;
import it.algos.base.campo.dati.CDElencoInterno;
import it.algos.base.campo.dati.CDElencoLink;
import it.algos.base.campo.dati.CampoDati;
import it.algos.base.costante.CostanteColore;
import it.algos.base.database.connessione.Connessione;
import it.algos.base.database.dati.Dati;
import it.algos.base.database.memoria.dati.DatiMemoria;
import it.algos.base.database.util.Funzione;
import it.algos.base.database.util.Operatore;
import it.algos.base.errore.Errore;
import it.algos.base.evento.BaseListener;
import it.algos.base.evento.Eventi;
import it.algos.base.evento.campo.CampoMemoriaAz;
import it.algos.base.evento.campo.CampoMemoriaEve;
import it.algos.base.evento.lista.ListaClicAz;
import it.algos.base.evento.lista.ListaClicEve;
import it.algos.base.evento.lista.ListaClicLis;
import it.algos.base.evento.lista.ListaCursoreAz;
import it.algos.base.evento.lista.ListaCursoreEve;
import it.algos.base.evento.lista.ListaCursoreLis;
import it.algos.base.evento.lista.ListaDoppioClicAz;
import it.algos.base.evento.lista.ListaDoppioClicEve;
import it.algos.base.evento.lista.ListaDoppioClicLis;
import it.algos.base.evento.lista.ListaEnterAz;
import it.algos.base.evento.lista.ListaEnterEve;
import it.algos.base.evento.lista.ListaEnterLis;
import it.algos.base.evento.lista.ListaGenAz;
import it.algos.base.evento.lista.ListaGenEve;
import it.algos.base.evento.lista.ListaGenLis;
import it.algos.base.evento.lista.ListaModCellaAz;
import it.algos.base.evento.lista.ListaModCellaEve;
import it.algos.base.evento.lista.ListaModCellaLis;
import it.algos.base.evento.lista.ListaReturnAz;
import it.algos.base.evento.lista.ListaReturnEve;
import it.algos.base.evento.lista.ListaReturnLis;
import it.algos.base.evento.lista.ListaSelAz;
import it.algos.base.evento.lista.ListaSelEve;
import it.algos.base.evento.lista.ListaSelLis;
import it.algos.base.evento.portale.PortaleListaPopAz;
import it.algos.base.evento.portale.PortaleListaPopEve;
import it.algos.base.evento.tavola.TavolaColResizeAz;
import it.algos.base.evento.tavola.TavolaColResizeEve;
import it.algos.base.evento.tavola.TavolaSelAz;
import it.algos.base.evento.tavola.TavolaSelEve;
import it.algos.base.font.FontFactory;
import it.algos.base.interfaccia.Generale;
import it.algos.base.layout.Layout;
import it.algos.base.libreria.Lib;
import it.algos.base.libreria.Libreria;
import it.algos.base.libreria.Parametro;
import it.algos.base.modulo.Modulo;
import it.algos.base.navigatore.Navigatore;
import it.algos.base.pannello.Pannello;
import it.algos.base.pannello.PannelloBase;
import it.algos.base.pannello.PannelloFlusso;
import it.algos.base.portale.Portale;
import it.algos.base.portale.PortaleLista;
import it.algos.base.query.Query;
import it.algos.base.query.campi.CampoQuery;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.query.filtro.FiltroFactory;
import it.algos.base.query.ordine.Ordine;
import it.algos.base.query.selezione.QuerySelezione;
import it.algos.base.tavola.Tavola;
import it.algos.base.tavola.renderer.RendererBase;
import it.algos.base.tavola.renderer.RendererNumero;
import it.algos.base.toolbar.ToolBar;
import it.algos.base.vista.Vista;
import it.algos.base.vista.VistaElemento;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.event.EventListenerList;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Enumeration;

/**
 * Presentazione di dati sotto forma di elenco.
 * </p>
 * Questa classe astratta: <ul>
 * <li> Gestisce un'istanza di <code>Vista</code> </li>
 * <li> Gestisce un'istanza di <code>Tabella</code> </li>
 * <li> Gestisce un'istanza di <code>Tavola</code> </li>
 * <li> Gestisce un'istanza di <code>StatusBar</code> </li>
 * <li> </li>
 * </ul>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 7-set-2004 ore 7.30.22
 */
public abstract class ListaBase extends PannelloBase implements Lista {

    private static final boolean DEBUG = false;

    /**
     * Numero di righe da visualizzare di default verticale
     */
    private static final int RIGHE_VERT = 20;

    /**
     * Numero di righe da visualizzare di default orizzantalel
     */
    private static final int RIGHE_ORIZ = 3;

    /**
     * colori della GUI
     */
    private static final Color COLORE_SFONDO_TITOLI = CostanteColore.VERDE_CHIARO_DUE;

    /**
     * Colore di sfondo per la tavola totali
     */
    private static final Color SFONDO_TOTALI = Color.LIGHT_GRAY;

    /**
     * verso di ordinamento del campo nella lista - ascendente
     */
    public static final int VERSO_ASCENDENTE = 1;

    /**
     * verso di ordinamento del campo nella lista - discendente
     */
    public static final int VERSO_DISCENDENTE = 2;

    /**
     * verso di ordinamento del campo nella lista - verso del campo
     */
    public static final int VERSO_DEL_CAMPO = 0;

    /**
     * flag - abilita l'uso dei caratteri per filtrare la lista - default
     */
    private static final boolean IS_USA_CARATTERE_FILTRO = true;

    /**
     * Riferimento al portale proprietario di questa lista
     */
    private PortaleLista portale = null;

    /**
     * Nome della vista da utilizzare per le colonne
     * (se non specificata usa la Vista di default del Modulo)
     */
    private String nomeVista = "";

    /**
     * Vista da utilizzare per le colonne
     * (se non specificata usa la Vista di default del Modulo)
     */
    private Vista vista = null;

    /**
     * Modello dati per la Tavola
     */
    private TavolaModello modello = null;

    /**
     * Modello dati per la Tavola Totali
     */
    private TavolaModello modelloTotali = null;

    /**
     * flag - mantiene lo stato di aggiornamento
     * dei totali visualizzati.
     * <p/>
     * Viene acceso quando i totali vengono aggiornati
     * Viene spento quando cambia il filtro base o corrente
     */
    private boolean totaliAggiornati;

    /**
     * flag - aggiornamento continuo dei totali
     * <p/>
     * Se attivato, i totali vengono aggiornati ad ogni caricamento dei
     * dati nella lista.
     * Di default non è attivo e i totali vengono aggiornati solo quando
     * cambiano i filtri.
     */
    private boolean aggiornamentoTotaliContinuo;

    /**
     * tavola per visualizzare i dati (JTable)
     */
    private Tavola tavola = null;

    /**
     * tavola per visualizzare i totali (JTable)
     */
    private Tavola tavolaTotali = null;

    /**
     * Altezza preferita della lista.
     * <p/>
     * Valore espresso in numero di righe da visualizzare
     */
    private int altezzaPreferita = 0;

    /**
     * Filtro base della lista.
     */
    private Filtro filtroBase = null;

    /**
     * Filtro corrente della lista.
     * <p/>
     * regolato dai comandi di selezione righe nella tavola
     * regolato dal dialogo di ricerca
     */
    private Filtro filtroCorrente = null;

    /**
     * Ordine corrente della lista
     */
    private Ordine ordine = null;

    /**
     * Campo di ordinamento corrente della Lista
     */
    private Campo campoOrdinamento = null;

    /**
     * Flag di ordinamento corrente della Lista
     * true=ascendente, false=discendente
     */
    private boolean isOrdineAscendente = true;

    /**
     * Scorrevole per contenere la tavola
     */
    private JScrollPane scorrevole = null;

    /**
     * flag per abilitare la creazione di nuovi record.
     * <p/>
     * Di default e' true
     */
    private boolean nuovoRecordAbilitato = false;

    /**
     * flag per abilitare la modifica dei record.
     * <p/>
     * Di default e' true
     */
    private boolean modificaRecordAbilitata = false;

    /**
     * flag per abilitare la cancellazione dei record
     * di default e' true
     */
    private boolean deleteAbilitato = false;

    /**
     * flag per abilitare l'ordinamento cliccando sulle colonne
     * di default e' true
     */
    private boolean ordinamentoAbilitato = false;

    /**
     * flag - abilita l'uso della status bar
     */
    private boolean isUsaStatusBar = false;

    /**
     * Status bar della lista
     */
    private ListaStatusBar statusBar = null;

    /**
     * Pannello al piede della lista (contiene totali e status bar)
     */
    private Pannello panPiede = null;

    /**
     * flag - abilita l'uso della barra dei totali
     */
    private boolean usaTotali = false;

    /**
     * Pannello totali (contiene Tavola Totali e placeholder per scroll bar)
     */
    private Pannello panTotali = null;

    /**
     * flag - abilita l'uso dei caratteri per filtrare la lista
     */
    private boolean usaCarattereFiltro = false;

    /**
     * Numero totale di records selezionati dal filtro base.
     * <p/>
     * Corrisponde al numero di records virtualmente disponibili
     * per questa lista.<br>
     * Aggiornato ad ogni caricamento dei record.
     */
    int numRecordsDisponibili = 0;

    /**
     * Numero totale di records visualizzati nella lista.
     * <p/>
     * Corrisponde al numero di records selezionati dal filtro
     * base piu' il filtro corrente.<br>
     * Aggiornato ad ogni caricamento dei record.
     */
    int numRecordsVisualizzati = 0;

    /**
     * A list of event listeners for this component.
     */
    private EventListenerList listaListener;

    /**
     * flag - abilita l'uso dei filtri pop nella lista - di default è abilitato
     */
    private boolean usaFiltriPop;


    /**
     * Costruttore base senza parametri. <br>
     * Indispensabile anche se non viene utilizzato
     * (anche solo per compilazione in sviluppo) <br>
     * Rimanda al costruttore completo <br>
     * Utilizza eventuali valori di default <br>
     */
    public ListaBase() {
        /* rimanda al costruttore di questa classe */
        this(null);
    }// fine del metodo costruttore base


    /**
     * Costruttore completo con parametri. <br>
     *
     * @param unPortale portale che contiene questa lista
     */
    public ListaBase(PortaleLista unPortale) {
        /* rimanda al costruttore della superclasse */
        super();

        /* regola le variabili di istanza coi parametri */
        this.setPortale(unPortale);

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
        PannelloFlusso pan;
        JScrollPane scorrevole;
        Border bordo;

        try { // prova ad eseguire il codice

            /* regolazione di default dei flag iniziali */
            this.setNuovoRecordAbilitato(true);
            this.setModificaRecordAbilitata(true);
            this.setDeleteAbilitato(true);
            this.setOrdinabile(true);
            this.setUsaCarattereFiltro(IS_USA_CARATTERE_FILTRO);
            this.setUsaStatusBar(true);
            this.setUsaTotali(true);
            this.setUsaFiltriPop(true);

            /* assegna il layout manager alla lista */
            this.setLayout(new LayoutLista(this));

            /* crea il filtro base */
            this.setFiltroBase(new Filtro());

            /* crea il filtro corrente */
            this.setFiltroCorrente(new Filtro());

            /* crea l'istanza della tavola (JTable)
             * e vi registra i listener ai i quali la lista e' interessata */
            Tavola tavola = this.creaTavola();
            tavola.setLista(this);
            this.setTavola(tavola);
            this.setTavolaTotali(new TavolaTotali(this));
            this.getTavola().addListener(new AzioneModificaSelezioneRighe());
            this.getTavola().addListener(new AzioneResizeColonna());

            /* crea uno scorrevole con bordo e con dentro la Tavola */
            scorrevole = new JScrollPane(this.getTavola());
            bordo = BorderFactory.createLineBorder(Color.white);
            scorrevole.setBorder(bordo);
            scorrevole.getViewport().setBackground(COLORE_SFONDO_TITOLI);
            this.setScorrevole(scorrevole);

            /* crea la status bar */
            this.setStatusBar(new ListaStatusBar(this));

            /* Aggiunge lo scorrevole alla lista */
            this.add(this.getScorrevole());

            /* Crea il pannello a piede lista, lo registra e
             * lo aggiunge alla lista */
            pan = new PannelloFlusso(Layout.ORIENTAMENTO_VERTICALE);
            this.setPanPiede(pan);
            pan.setUsaGapFisso(true);
            pan.setGapPreferito(0);
            this.add(pan, BorderLayout.SOUTH);

            /* Crea il pannello totali con la tavola totali e lo registra */
            pan = new PannelloFlusso(Layout.ORIENTAMENTO_ORIZZONTALE);
            this.setPanTotali(pan);
            pan.setUsaGapFisso(true);
            pan.setGapPreferito(0);

            /* aggiunge un bordo sinistro pari allo spessore
             * dello scorrevole per allineare dati e totali */
            int x = (int)scorrevole.getViewportBorderBounds().getX();
            bordo = BorderFactory.createEmptyBorder(0, x, 0, 0);
            pan.getPanFisso().setBorder(bordo);
            pan.add(this.getTavolaTotali());

            if (DEBUG) {
                this.getScorrevole().setOpaque(true);
                this.getScorrevole().setBackground(Color.GREEN);
                this.setOpaque(true);
                this.setBackground(Color.GREEN);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            new Errore(unErrore);
        }// fine del blocco try-catch


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
        boolean continua;
        String nomeVista;
        Vista vista;
        ListaStatusBar statusBar;
        VistaElemento elem;

        try {    // prova ad eseguire il codice

            /* recupera la Vista associata */
            vista = this.getVista();

            if (vista == null) {

                /* assegna la Vista recuperandola dal Navigatore
                 * usa quella di default se non e' specificata */
                nomeVista = this.getNavigatore().getNomeVista();

                if (Lib.Testo.isValida(nomeVista)) {
                    vista = this.getModulo().getVista(nomeVista);
                } else {
                    nomeVista = Generale.VISTA_COMPLETA;
                    vista = this.getModulo().getVista(nomeVista);
                }// fine del blocco if-else
            }// fine del blocco if

            continua = (vista != null);

            if (continua) {

                /**
                 * Se il Navigatore usa le frecce di spostamento,
                 * si accerta che la Vista contenga il campo Ordine.
                 * Se non lo contiene lo aggiunge ora, come invisibile.
                 */
                if (this.getNavigatore().isUsaFrecceSpostaOrdineLista()) {
                    Campo campoOrd = this.getModulo().getCampoOrdine();
                    if (vista.getCampo(campoOrd) == null) {
                        elem = vista.addCampo(campoOrd, 0); // prima colonna
                        elem.setVisibile(false);
                        vista.setCampoOrdineDefault(campoOrd); // ordine di default
                    }// fine del blocco if
                }// fine del blocco if

                /**
                 * se il Navigatore usa la gestione del campo Preferito,
                 * si accerta che la Vista contenga il campo Preferito
                 */
                if (this.getNavigatore().isUsaPreferito()) {
                    Campo campoPref = this.getModulo().getCampoPreferito();
                    if (vista.getCampo(campoPref) == null) {
                        vista.addCampo(campoPref); // ultima colonna
                    }// fine del blocco if
                }// fine del blocco if

                this.setVista(vista);

                /* inizializza la vista */
                this.getVista().inizializza();

                /* aggiunge un campo chiave alla Vista se assente */
                this.regolaColonnaCodice();

                /* regolazione della tavola dei dati */
                this.regolaTavola();

                /* assegna alla lista il campo di ordinamento corrente
                 * se non e' stato specificato, usa quello di default della Vista */
                if (this.getCampoOrdinamento() == null) {
                    this.setCampoOrdinamento(this.getVista().getCampoOrdineDefault());
                }// fine del blocco if

                /* regola l'oggetto Ordine della lista in base
                 * al campo di ordinamento corrente */
                this.regolaOrdineLista();


                /* Regola il numero di righe da visualizzare */
                this.regolaAltezzaPreferita();

                /* Aggiunge il pannello totali al pannello piede */
                if (this.isUsaTotali()) {
                    this.regolaTavolaTotali();
                    this.getPanPiede().add(this.getPanTotali());
                }// fine del blocco if

                /* aggiunge la eventuale status bar al pannello piede */
                if (this.isUsaStatusBar()) {
                    statusBar = this.getStatusBar();
                    this.getPanPiede().add(statusBar);
                    statusBar.inizializza();
                    statusBar.addListener(
                            ListaStatusBar.Evento.popModificato,
                            new AzionePopStatuBar());
                }// fine del blocco if

                /* crea la lista dei propri eventi */
                this.setListaListener(new EventListenerList());

                /* regola il filtro corrente in base agli eventuali popup */
                this.regolaFiltroPop();

            }// fine del blocco if


        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
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
     * Sovrascritto nelle sottoclassi
     */
    public void avvia() {
        try { // prova ad eseguire il codice

            /* deseleziona le righe eventualmente selezionate */
            this.eliminaSelezione();

            /* carica i dati in base a filtro e ordine correnti*/
            this.caricaSelezione();

        } catch (Exception unErrore) { // intercetta l'errore
            new Errore(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Crea la Tavola per la lista.
     * <p/>
     * Sovrascritto dalle sottoclassi se si vuole creare una tavola specifica
     * @return la Tavola creata
     */
    protected Tavola creaTavola() {
        return new Tavola();
    }



    /**
     * Regolazioni base della tavola dei dati.
     * <p/>
     * Metodo invocato dal ciclo inizializza <br>
     */
    private void regolaTavola() {
        /* variabili e costanti locali di lavoro */
        Tavola tavola;

        try { // prova ad eseguire il codice

            tavola = this.getTavola();

            /* crea il modello dei dati */
            this.setModello(new TavolaModello(tavola, this.getVista()));

            /* assegna il Modello dati alla Tavola */
            tavola.setModello(this.getModello());

            /* inizializza la tavola */
            tavola.inizializza();

            /* Forza le dimensioni dello scorrevole pari
             * alle dimensioni della tavola piu' la larghezza
             * della scroll bar verticale */
            Dimension dt = this.getTavola().getPreferredSize();
            int wScrollBar = this.getScorrevole().getVerticalScrollBar().getPreferredSize().width;
            int lar = dt.width + wScrollBar;
            int alt = dt.height;
            Dimension ds = new Dimension(lar, alt);
            tavola.setPreferredScrollableViewportSize(ds);

        } catch (Exception unErrore) { // intercetta l'errore
            new Errore(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Regolazioni base della tavola dei totali.
     * <p/>
     * Metodo invocato dal ciclo inizializza <br>
     */
    private void regolaTavolaTotali() {
        /* variabili e costanti locali di lavoro */
        Tavola tavola;
        TavolaModello modTavola;
        Vista vista;
        ArrayList<VistaElemento> elementi;

        try { // prova ad eseguire il codice

            /* crea una vista con tutti e soli gli elementi visibili della vista principale */
            elementi = this.getVista().getElementiVisibili();
            vista = new Vista(this.getModulo());
            vista.setElementi(elementi);
            vista.inizializza();

            /* recupera la tavola totali */
            tavola = this.getTavolaTotali();

            /* crea il modello dati dei totali */
            modTavola = new TavolaModello(tavola, vista);
            this.setModelloTotali(modTavola);

            /* assegna il Modello dati alla Tavola */
            tavola.setModello(this.getModelloTotali());

            /* regolazioni grafiche e funzionali */
            tavola.setBackground(SFONDO_TOTALI);
            tavola.setFocusable(false);
            tavola.setRowSelectionAllowed(false);
            tavola.setColumnSelectionAllowed(false);
            tavola.setAutoResizeMode(this.getTavola().getAutoResizeMode());

            /* inizializza la tavola */
            tavola.inizializza();

        } catch (Exception unErrore) { // intercetta l'errore
            new Errore(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Sincronizzazione della lista.
     * <p/>
     * Chiamato dalla sincronizzazione del portale
     * che contiene la lista.<br>
     * Sincronizza la status bar<br>
     */
    public void sincronizza() {
        /* variabili e costanti locali di lavoro */
        ListaStatusBar sb;

        try { // prova ad eseguire il codice
            if (this.isUsaStatusBar()) {
                sb = this.getStatusBar();
                if (sb != null) {
                    sb.sincronizza();
                }// fine del blocco if
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Campo a cui associare il combobox.
     * <p/>
     * Se il campo è linkato, usa il campo linkato <br>
     * Se il campo non è linkato, usa il campo originale <br>
     */
    private Campo selezionaCampoCombo(Campo unCampo) {
        /* variabili e costanti locali di lavoro */
        Campo campoBox = null;
        Campo campo;
        CampoDati campoDati;

        try { // prova ad eseguire il codice
            campoDati = unCampo.getCampoDati();

            if (campoDati instanceof CDElencoLink) {
                campo = unCampo.getCampoDB().getCampoValoriLinkato();
                campoBox = this.getVista().getCampo(campo);
            }// fine del blocco if

            if (campoDati instanceof CDElencoInterno) {
                campoBox = unCampo;
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            new Errore(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return campoBox;
    }


    /**
     * Regola il numero di righe da visualizzare.
     * <p/>
     * Metodo invocato dal ciclo inizializza <br>
     * Se non e' stata specificata una altezza
     * preferita, usa quella di default.
     */
    private void regolaAltezzaPreferita() {
        /* variabili e costanti locali di lavoro */
        if (this.getAltezzaPreferita() == 0) {
            if (this.getPortale().getToolBar().isVerticale()) {
                this.setAltezzaPreferita(RIGHE_VERT);
            } else {
                this.setAltezzaPreferita(RIGHE_ORIZ);
            }// fine del blocco if-else
        }// fine del blocco if
    }


    /**
     * Aggiunge una colonna con il campo chiave del Modulo alla Vista.
     * <p/>
     * La Vista deve essere gia' inizializzata, altrimenti
     * non si e' ancora sicuri che contenga tutti i campi.<br>
     * La colonna viene aggiunta solo se non gia' presente.<br>
     * La colonna viene aggiunta in testa.
     */
    private void regolaColonnaCodice() {
        /* variabili e costanti locali di lavoro */
        Vista vista;
        VistaElemento elemento;
        boolean continua = true;
        Campo unCampo;
        int posizione;

        try {    // prova ad eseguire il codice

            /* recupera la Vista */
            vista = this.getVista();

            /* controlla che la Vista sia inizializzata
             * se no non puo' procedere */
            if (continua) {
                if (!vista.isInizializzato()) {
                    continua = false;
                    throw new Exception("Vista non inizializzata");
                }// fine del blocco if
            }// fine del blocco if

            if (continua) {

                /* recupera la posizione del campo chiave se esistente */
                posizione = this.getPosizioneCampoChiave();

                /*
                 * Se non esiste, crea un clone e lo aggiunge come
                 * invisibile in prima posizione
                 */
                if (posizione == -1) { // non esiste
                    posizione = 0;
                    unCampo = this.getModulo().getCampoChiave().clonaCampo();
                    elemento = vista.addCampo(unCampo, posizione);
                    elemento.setVisibile(false);
                }// fine del blocco if-else

            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

    }


    /**
     * Carica tutti i records.
     * <p/>
     * Azzera il filtro corrente <br>
     * Ricarica i records <br>
     */
    public void caricaTutti() {

        try { // prova ad eseguire il codice

            this.setFiltroCorrente(null);
            this.caricaSelezione();

        } catch (Exception unErrore) { // intercetta l'errore
            new Errore(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Mostra solo i records selezionati.
     * <p/>
     * Crea un filtro basato sui records selezionati <br>
     * Regola la variabile filtro <br>
     * Ricarica i records <br>
     */
    public void soloSelezionati() {
        /* variabili e costanti locali di lavoro */
        Filtro filtro = null;

        try { // prova ad eseguire il codice

            /* crea un filtro per i record selezionati */
            filtro = this.getFiltroSelezionati();

            /* sostituisce il filtro corrente della lista */
            this.setFiltroCorrente(filtro);

        } catch (Exception unErrore) { // intercetta l'errore
            new Errore(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Rimuove i records selezionati.
     * <p/>
     * Recupera il filtro corrente della lista <br>
     * Crea un filtro basato sui records selezionati <br>
     * Aggiunge il filtro (in negativo) a quello gia esistente <br>
     * Regola la variabile filtro <br>
     * Ricarica i records <br>
     */
    public void nascondeSelezionati() {
        /* variabili e costanti locali di lavoro */
        Filtro filtroSelezione;
        Filtro filtroCorrente;
        Filtro filtroNuovo;

        try { // prova ad eseguire il codice

            /* recupera il filtro corrente */
            filtroCorrente = this.getFiltroCorrente();

            /* crea un filtro che esclude i record selezionati */
            filtroSelezione = this.getFiltroSelezionati();
            filtroSelezione.setInverso(true);

            /* crea un nuovo filtro vuoto */
            filtroNuovo = new Filtro();

            /*
             * aggiunge al nuovo filtro il precedente filtro corrente
             * piu' il filtro che esclude i selezionati
             */
            filtroNuovo.add(filtroCorrente);
            filtroNuovo.add(filtroSelezione);

            /* sostituisce il filtro corrente della lista */
            this.setFiltroCorrente(filtroNuovo);


        } catch (Exception unErrore) { // intercetta l'errore
            new Errore(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * carica i i records specificati.
     * <p/>
     * Crea un filtro basato sui records specificati <br>
     * Regola la variabile filtro <br>
     * Ricarica i records <br>
     */
    public void caricaSelezione(int[] codici) {
        /* variabili e costanti locali di lavoro */
        Filtro filtro;

        try { // prova ad eseguire il codice

            /* crea un filtro per i record selezionati */
            filtro = this.getFiltroDaIntArray(codici);

            /* sostituisce il filtro corrente della lista */
            this.setFiltroCorrente(filtro);

        } catch (Exception unErrore) { // intercetta l'errore
            new Errore(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Porta una riga in area visibile.
     * <p/>
     *
     * @param indice l'indice della riga (0 per la prima)
     */
    public void setRigaVisibile(int indice) {
        try { // prova ad eseguire il codice
            this.getTavola().rendiRigaVisibile(indice);
        } catch (Exception unErrore) { // intercetta l'errore
            new Errore(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Seleziona una riga nella lista.
     * <p/>
     *
     * @param indice l'indice della riga (0 per la prima)
     */
    public void setRigaSelezionata(int indice) {
        try { // prova ad eseguire il codice
            this.getTavola().selezionaRiga(indice);
        } catch (Exception unErrore) { // intercetta l'errore
            new Errore(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Seleziona un gruppo di righe nella lista.
     * <p/>
     *
     * @param indici l'elenco delle righe da selezionare
     */
    public void setRigheSelezionate(int[] indici) {
        try { // prova ad eseguire il codice
            this.getTavola().selezionaRighe(indici);
        } catch (Exception unErrore) { // intercetta l'errore
            new Errore(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Porta una riga in area visibile e la seleziona.
     * <p/>
     *
     * @param indice l'indice della riga (0 per la prima)
     */
    public void setRigaVisibileSelezionata(int indice) {
        try { // prova ad eseguire il codice
            this.setRigaVisibile(indice);
            this.setRigaSelezionata(indice);
        } catch (Exception unErrore) { // intercetta l'errore
            new Errore(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Porta un record in area visibile.
     * <p/>
     *
     * @param codice il codice chiave del record
     */
    public void setRecordVisibile(int codice) {
        /* variabili e costanti locali di lavoro */
        int posizione;

        try { // prova ad eseguire il codice

            /* recupera la posizione dal modello */
            posizione = this.getModello().getPosizione(codice);

            /* delega l'operazione alla tavola */
            if (posizione != -1) {
                this.getTavola().rendiRigaVisibile(posizione);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            new Errore(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Seleziona un record.
     * <p/>
     * Se il record non e' in area visibile, rimane non visibile.
     *
     * @param codice il codice chiave del record
     */
    public void setRecordSelezionato(int codice) {
        /* variabili e costanti locali di lavoro */
        int posizione;

        try { // prova ad eseguire il codice

            /* determina la posizione del record richiesto nella tavola */
            posizione = this.getModello().getPosizione(codice);

            /* controlla che il record esista
             * seleziona la riga nella tavola */
            if (posizione != -1) {
                this.setRigaSelezionata(posizione);
            } /* fine del blocco if/else */

        } catch (Exception unErrore) { // intercetta l'errore
            new Errore(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Seleziona uno o piu' records nella lista.
     * <p/>
     * Trasforma l'elenco di codici nelle relative posizioni
     * Delega alla tavola la selezione
     *
     * @param codici l'elenco dei codici dei record
     */
    public void setRecordsSelezionati(int[] codici) {
        /* variabili e costanti locali di lavoro */
        int posizione;
        ArrayList<Integer> posizioni;
        int[] righe;

        try {    // prova ad eseguire il codice

            /* determina le posizioni nel modello */
            posizioni = new ArrayList<Integer>();
            for (int chiave : codici) {
                posizione = this.getModello().getPosizione(chiave);
                if (posizione != -1) {
                    posizioni.add(posizione);
                }// fine del blocco if
            }

            /* delega alla tavola la selezione */
            righe = new int[posizioni.size()];
            for (int k = 0; k < posizioni.size(); k++) {
                posizione = posizioni.get(k);
                righe[k] = posizione;
            } // fine del ciclo for
            this.setRigheSelezionate(righe);

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

    }


    /**
     * Seleziona un record e lo porta in area visibile.
     * <p/>
     *
     * @param codice il codice chiave del record
     */
    public void setRecordVisibileSelezionato(int codice) {
        this.setRecordSelezionato(codice);
        this.setRecordVisibile(codice);
    }


    /**
     * Porta l'ultima riga in area visibile.
     * <p/>
     */
    public void setUltimaRigaVisibile() {
        /* variabili e costanti locali di lavoro */
        int recordsVisibili;

        try { // prova ad eseguire il codice
            recordsVisibili = this.getNumRecordsVisualizzati();
            this.setRigaVisibile(recordsVisibili - 1);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Seleziona tutti i records della lista.
     * <p/>
     */
    public void selezionaTutti() {
        try { // prova ad eseguire il codice
            /* invoca il metodo nella classe delegata */
            this.getTavola().selezionaTutti();
        } catch (Exception unErrore) { // intercetta l'errore
            new Errore(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Elimina la selezione dei records della <code>Tavola</code>.
     */
    public void eliminaSelezione() {
        try { // prova ad eseguire il codice
            this.getTavola().eliminaSelezione();
        } catch (Exception unErrore) { // intercetta l'errore
            new Errore(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Carica la selezione di records.
     * <p/>
     * Opera col filtro base piu quello corrente <br>
     * Recupera l'ordinamento dalla tavola <br>
     * Recupera i campi dalla Vista <br>
     * Costruisce la Query <br>
     * Delega al Db il caricamento dei dati con la query preparata <br>
     * Assegna il risultato ai modello dati della lista <br>
     */
    public void caricaSelezione() {
        boolean continua;
        ArrayList campi;
        Ordine ordine;
        QuerySelezione query;
        Dati dati;
        Campo campoOrdinamento;
        int colonna = 0;
        int[] righe;
        Navigatore nav;
        Connessione conn;
        TavolaModello modelloDati;

        try { // prova ad eseguire il codice

            /* recupera il navigatore e controlla se è inizializzato */
            nav = this.getNavigatore();
            continua = nav.isInizializzato();

            /* controlla se la connessione è aperta */
            if (continua) {
                continua = false;
                conn = nav.getConnessione();
                if (conn != null) {
                    if (conn.isOpen()) {
                        continua = true;
                    }// fine del blocco if
                }// fine del blocco if
            }// fine del blocco if

            /* procede a ricaricare la lista */
            if (continua) {

                /* Memorizza la lista dei records attualmente selezionati.
                 * Uso i codici di record e non gli indici di riga
                 * perche' se un record cade in posizione diversa
                 * da prima (e' cambiato il valore di un campo coinvolto
                 * nell'ordinamento) mi troverei un record selezionato
                 * diverso dal precedente */
                righe = this.getChiaviSelezionate();

                /* recupera i campi */
                campi = this.getVista().getCampiFisici();

                /* recupera l'ordine */
                ordine = this.getOrdine();

                /* costruisce la query */
                query = new QuerySelezione(this.getModulo());
                query.setCampi(campi);
                query.setFiltro(this.getFiltro());
                query.setOrdine(ordine);

                /* recupera la connessione e carica i dati */
                dati = nav.query().querySelezione(query);

                /* cancella la eventuale sessione di editing in corso nelle celle */
                this.cancellaEditingLista();

                /* assegna i nuovi dati al modello */
                modelloDati = this.getModello();
                if (modelloDati != null) {
                    modelloDati.setDati(dati);
                }// fine del blocco if

                /* accende la colonna ordinata sulla tavola */
                campoOrdinamento = this.getCampoOrdinamento();
                if (campoOrdinamento != null) {
                    colonna = this.getColonna(campoOrdinamento);
                    if (colonna >= 0) {
                        this.getTavola().accendiColonna(colonna);
                    }// fine del blocco if
                }// fine del blocco if

                /* regola la colonna corrente della tavola */
                this.getTavola().setColonnaSelezionata(colonna);

                /* ripristina la selezione dei records nella lista */
                // todo - questa chiamata è estremamente inefficiente
                // todo - meccanismo da ripensare! alex set-09
                this.setRecordsSelezionati(righe);

                /* regola il numero di records disponibili/visualizzati*/
                this.regolaNumeriRecords();

                /* aggiorna i totali se necessario */
                if (this.isUsaTotali()) {
                    if (!this.isTotaliAggiornati()) {
                        this.aggiornaTotali();
                    }// fine del blocco if
                }// fine del blocco if

            }// fine del blocco if

//            if (dati != null) {
//                dati.close();
//            }// fine del blocco if

            this.sincronizza();

        } catch (Exception unErrore) { // intercetta l'errore
            new Errore(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Aggiorna i valori dei totali nella tavola totali.
     * <p/>
     */
    public void aggiornaTotali() {
        ArrayList<Campo> campiTotali;
        ArrayList<Campo> campiTotalizzabili;
        Object valore;
        Dati dati = null;
        DatiMemoria datiTotali = null;
        boolean continua;
        Query query;
        Filtro filtro;

        try { // prova ad eseguire il codice

            /* recupera l'elenco dei campi totalizzabili */
            campiTotalizzabili = this.getVista().getCampiTotalizzabili();
            continua = campiTotalizzabili.size() > 0;

            /* Crea un oggetto dati memoria con le colonne corrispondenti
             * a tutti i campi nella tavola totali */
            if (continua) {
                campiTotali = this.getModelloTotali().getVista().getCampi();
                datiTotali = new DatiMemoria(campiTotali, 1);
            }// fine del blocco if

            /* carica i valori dei campi totalizzabili */
            if (continua) {
                query = new QuerySelezione(this.getModulo());
                filtro = this.getFiltro();
                query.setFiltro(filtro);
                for (Campo campo : campiTotalizzabili) {
                    CampoQuery cq = query.addCampo(campo);
                    cq.addFunzione(Funzione.SUM);
                } // fine del ciclo for-each
                dati = this.getNavigatore().query().querySelezione(query);
            }// fine del blocco if

            /* spazzola i campi totalizzabili
             * e scrive i valori nei corrispondenti campi dei totali */
            if (continua) {
                for (Campo campo : campiTotalizzabili) {
                    valore = dati.getValueAt(0, campo);
                    datiTotali.setValueAt(0, campo, valore);
                }
            }// fine del blocco if

            /* assegna i dati al modello totali */
            if (continua) {
                this.getModelloTotali().setDati(datiTotali);
                this.setTotaliAggiornati(true);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            new Errore(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Assegna un valore a una cella dei totali.
     * <p/>
     *
     * @param campo totalizzato
     * @param valore da assegnare
     */
    public void setTotale(Campo campo, Number valore) {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        TavolaModello modelloTotali;
        Dati dati = null;

        try {    // prova ad eseguire il codice
            modelloTotali = this.getModelloTotali();
            continua = (modelloTotali != null);

            if (continua) {
                dati = modelloTotali.getDati();
                continua = (dati != null);
            }// fine del blocco if

            if (continua) {
                dati.setValueAt(0, campo, valore);
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

    }


    /**
     * Sincronizza la larghezza colonne della tavola totali
     * con la larghezza colonne della tavola dati.
     * <p/>
     */
    private void syncColonneTotali() {
        /* variabili e costanti locali di lavoro */
        Tavola tabDati;
        Tavola tabTotali;
        TableColumnModel modColDati;
        TableColumnModel modColTot;
        Enumeration enumerazione;
        ArrayList<TableColumn> colonneDati;
        TableColumn colonna;
        TableColumn colonnaTot;
        TableColumn colonnaDati;
        int lar;
        int i;

        try { // prova ad eseguire il codice

            /* recupera tavole e modelli */
            tabDati = this.getTavola();
            tabTotali = this.getTavolaTotali();
            modColDati = tabDati.getColumnModel();
            modColTot = tabTotali.getColumnModel();

            /* crea un elenco ordinato delle colonne dati */
            enumerazione = modColDati.getColumns();
            colonneDati = new ArrayList<TableColumn>();
            while (enumerazione.hasMoreElements()) {
                colonna = (TableColumn)enumerazione.nextElement();
                colonneDati.add(colonna);
            }

            /* spazzola le colonne totali e copia le larghezze
             * dalle corrispondenti colonne dati */
            i = 0;
            enumerazione = modColTot.getColumns();
            while (enumerazione.hasMoreElements()) {
                colonnaTot = (TableColumn)enumerazione.nextElement();
                colonnaDati = colonneDati.get(i);
                lar = colonnaDati.getWidth();
                colonnaTot.setMinWidth(lar);
                colonnaTot.setMaxWidth(lar);
                i++;
            }

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Cancella la eventuale sessione di editing della lista.
     * <p/>
     * Chiamato da ogni caricamento di dati in lista<br>
     * Se la lista e' in editing, cancella l'editing.
     */
    private void cancellaEditingLista() {
        /* variabili e costanti locali di lavoro */
        Tavola tavola;
        TableCellEditor editor;

        try {    // prova ad eseguire il codice
            tavola = this.getTavola();
            if (tavola != null) {
                editor = tavola.getCellEditor();
                if (editor != null) {
                    editor.cancelCellEditing();
                }// fine del blocco if
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Regola il numero di records disponibili e visualizzati.
     * <p/>
     */
    private void regolaNumeriRecords() {
        /* variabili e costanti locali di lavoro */
        int quantiDisponibili;
        int quantiVisualizzati = 0;
        Modulo modulo;
        Campo campoChiave;
        Filtro fb;
        Navigatore nav;
        TavolaModello modelloDati;

        try {    // prova ad eseguire il codice

            /* regola il numero di records selezionati dal filtro base */
            modelloDati = this.getModello();
            if (modelloDati != null) {
                quantiVisualizzati = modelloDati.getRowCount();
            }// fine del blocco if

            this.setNumRecordsVisualizzati(quantiVisualizzati);

            /* regola il numero di records disponibili */
            modulo = this.getModulo();
            campoChiave = modulo.getCampoChiave();
            fb = this.getFiltroBase();
            nav = this.getNavigatore();
            quantiDisponibili = nav.query().contaRecords(campoChiave, fb);
            this.setNumRecordsDisponibili(quantiDisponibili);

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Ritorna un filtro corrispondente alle chiaviSelezionate.
     * <p/>
     *
     * @param chiavi array dei codici chiave
     *
     * @return un filtro corrispondente alle chiaviSelezionate
     */
    private Filtro getFiltroDaIntArray(int[] chiavi) {
        /* variabili e costanti locali di lavoro */
        Filtro filtro = null;
        Campo campo;
        try { // prova ad eseguire il codice
            campo = this.getCampoChiave();
            filtro = FiltroFactory.elenco(campo, chiavi);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return filtro;
    } /* fine del metodo */


    /**
     * Ritorna un filtro che isola i record correntemente selezionati.
     *
     * @return un filtro corrispondente ai record selezionati
     */
    public Filtro getFiltroSelezionati() {
        /* variabili e costanti locali di lavoro */
        Filtro unFiltro = null;
        int[] chiaviSelezionate;

        try {    // prova ad eseguire il codice

            /* recupera i codici-chiave delle righe selezionate nella lista */
            chiaviSelezionate = this.getChiaviSelezionate();

            unFiltro = this.getFiltroDaIntArray(chiaviSelezionate);

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } /* fine del blocco try-catch */

        /* valore di ritorno */
        return unFiltro;
    } /* fine del metodo */


    /**
     * Restituisce un Campo del Modulo di questa Lista.
     * <p/>
     *
     * @param nome del campo
     *
     * @return il campo, nullo se non lo trova
     */
    private Campo getCampoModulo(String nome) {
        /* variabili e costanti locali di lavoro */
        Modulo modulo;
        String chiave;
        Campo campo = null;

        try {    // prova ad eseguire il codice
            modulo = this.getModulo();
            chiave = Lib.Camp.chiaveCampo(nome, modulo);
            campo = this.getVista().getCampoAlex(chiave);
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return campo;
    }


    /**
     * Restituisce il Campo chiave di questa Lista.
     * <p/>
     *
     * @return il campo chiave, nullo se non lo trova
     */
    private Campo getCampoChiave() {
        /* variabili e costanti locali di lavoro */
        Campo campoChiaveVista = null;
        Campo campoChiaveModulo;
        Vista vista;

        try { // prova ad eseguire il codice
            campoChiaveModulo = this.getModulo().getCampoChiave();
            vista = this.getVista();
            campoChiaveVista = vista.getCampo(campoChiaveModulo);
        } catch (Exception unErrore) { // intercetta l'errore
            new Errore(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return campoChiaveVista;
    }


    /**
     * Restituisce la posizione del Campo chiave di questa Lista.
     * <p/>
     *
     * @return la posizione del campo chiave, 0 per la prima,
     *         -1 se il campo chiave non esiste nella Vista
     */
    private int getPosizioneCampoChiave() {
        /* variabili e costanti locali di lavoro */
        int posizione = 0;
        Campo campoChiaveModulo;
        Vista vista;

        try { // prova ad eseguire il codice
            campoChiaveModulo = this.getModulo().getCampoChiave();
            vista = this.getVista();
            posizione = vista.getIndiceCampo(campoChiaveModulo);
        } catch (Exception unErrore) { // intercetta l'errore
            new Errore(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return posizione;
    }


    /**
     * Restituisce il Campo visibile del Modulo di questa Lista.
     * <p/>
     *
     * @return il campo visibile, nullo se non lo trova
     */
    public Campo getCampoVisibile() {
        /* valore di ritorno */
        return this.getModulo().getModello().getCampoVisibile();
//        return getCampoModulo(Modello.NOME_CAMPO_VISIBILE);
    }


    /**
     * Restituisce il Campo ordine del Modulo di questa Lista.
     * <p/>
     *
     * @return il campo visibile, nullo se non lo trova
     */
    public Campo getCampoOrdine() {
        /* valore di ritorno */
        return this.getModulo().getCampoOrdine();
//        return getCampoModulo(Modello.NOME_CAMPO_ORDINE);
    }


    /**
     * Restituisce il Modulo al quale la Lista si riferisce.<p>
     *
     * @return il Modulo della Lista
     */
    public Modulo getModulo() {
        /* variabili e costanti locali di lavoro */
        Modulo modulo = null;

        try { // prova ad eseguire il codice
            modulo = this.getPortale().getModulo();
        } catch (Exception unErrore) { // intercetta l'errore
            new Errore(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return modulo;
    }


    /**
     * Restituisce il Modello dati della JTable.<p>
     *
     * @return il Modello del Modulo
     */
    public TavolaModello getModello() {
        return this.modello;
    }


    public void setModello(TavolaModello modello) {
        this.modello = modello;
    }


    private TavolaModello getModelloTotali() {
        return modelloTotali;
    }


    private void setModelloTotali(TavolaModello modelloTotali) {
        this.modelloTotali = modelloTotali;
    }


    /**
     * Determina se i totali sono aggiornati
     * <p/>
     * Se l'aggiornamento continuo dei totali è attivo, ritorna sempre false.
     * In caso contrario, ritorna il valore del flag locale totaliAggiornati
     *
     * @return true se sono aggiornati, false se non sono aggiornati
     */
    private boolean isTotaliAggiornati() {
        /* variabili e costanti locali di lavoro */
        boolean aggiornati = false;

        try { // prova ad eseguire il codice
            if (this.isAggiornamentoTotaliContinuo()) {
                aggiornati = false;
            } else {
                aggiornati = this.totaliAggiornati;
            }// fine del blocco if-else
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return aggiornati;
    }


    private void setTotaliAggiornati(boolean flag) {
        this.totaliAggiornati = flag;
    }


    private boolean isAggiornamentoTotaliContinuo() {
        return aggiornamentoTotaliContinuo;
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
        this.aggiornamentoTotaliContinuo = flag;
    }


    /**
     * Restituisce il codice chiave della riga selezionata nella lista.
     * <p/>
     *
     * @return codice chiave
     */
    public int getRecordSelezionato() {
        /* variabili e costanti locali di lavoro */
        int riga;
        int chiave = 0;

        try { // prova ad eseguire il codice
            riga = this.getRigaSelezionata();
            chiave = getChiave(riga);
        } catch (Exception unErrore) { // intercetta l'errore
            new Errore(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return chiave;
    }


    /**
     * Restituisce i codici chiave delle righe selezionate nella lista.
     * <p/>
     * Recupera dal modello i numeri delle righe selezionate.<br>
     * Costruisce il valore della chiave per ogni riga selezionata.<br>
     *
     * @return int[] un array delle chiavi dei record selezionati
     */
    public int[] getChiaviSelezionate() {
        /* variabili e costanti locali di lavoro */
        int[] arrayRighe;
        boolean continua;
        int[] arrayChiavi = null;
        Integer valoreChiave;
        int posColCod;
        Object valore;
        TavolaModello modello = null;

        try { // prova ad eseguire il codice

            /* recupera le posizioni delle righe selezionate */
            arrayRighe = this.getRigheSelezionate();
            continua = (arrayRighe != null && arrayRighe.length > 0);

            /* modello dati della tavola */
            if (continua) {
                modello = this.getModello();
                continua = (modello != null);
            }// fine del blocco if

            /* costruisce l'array vuoto di chiavi */
            if (continua) {
                arrayChiavi = new int[arrayRighe.length];

                /* posizione della colonna codice */
                posColCod = this.getPosColonnaCodice();

                /* ciclo per costruire il valore della chiave per ogni riga selezionata */
                for (int k = 0; k < arrayChiavi.length; k++) {
                    valore = modello.getValueAt(arrayRighe[k], posColCod);
                    if (valore != null) {
                        valoreChiave = (Integer)valore;
                        arrayChiavi[k] = valoreChiave;
                    }// fine del blocco if
                } /* fine del blocco for */
            }// fine del blocco if

            if (arrayChiavi == null) {
                arrayChiavi = new int[0];
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            new Errore(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return arrayChiavi;
    }


    /**
     * recupera dal modello i numeri delle righe visualizzate.
     * costruisce il valore della chiave per ogni riga visualizzata
     *
     * @return int[] un array delle chiavi visualizzate
     */
    public int[] getChiaviVisualizzate() {
        /* variabili e costanti locali di lavoro */
        int[] arrayChiavi = null;
        int quanteRighe;
        Integer valoreChiave;

        try { // prova ad eseguire il codice

            quanteRighe = this.getRowCount();

            /* costruisce l'array vuoto di chiavi */
            arrayChiavi = new int[quanteRighe];

            /* ciclo per costruire il valore della chiave per ogni riga */
            for (int k = 0; k < quanteRighe; k++) {
                valoreChiave = (Integer)this.getModello().getValueAt(k, this.getPosColonnaCodice());
                arrayChiavi[k] = valoreChiave.intValue();
            } /* fine del blocco for */
        } catch (Exception unErrore) { // intercetta l'errore
            new Errore(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return arrayChiavi;
    }


    /**
     * Restituisce il codice chiave di una riga della lista alla posizione data.
     * <p/>
     *
     * @param posizione la posizione nella lista (0 per la prima)
     *
     * @return il codice chiave della riga alla posizione data (-1 se non valido)
     */
    public int getChiave(int posizione) {
        /* variabili e costanti locali di lavoro */
        int chiave = -1;
        TavolaModello modello;

        try { // prova ad eseguire il codice
            modello = this.getModello();
            if (modello != null) {
                if (posizione >= 0) {
                    chiave = modello.getChiave(posizione);
                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            new Errore(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return chiave;
    }


    /**
     * Restituisce il codice chiave della riga selezionata.
     * <p/>
     * Controlla che ci sia una ed una sola riga selezionata <br>
     *
     * @return il codice chiave della riga alla posizione data (-1 se non valido)
     */
    public int getChiaveSelezionata() {
        /* variabili e costanti locali di lavoro */
        int chiave;
        int riga;

        /* valore di defualt */
        chiave = -1;

        try { // prova ad eseguire il codice

            if (this.isRigaSelezionata()) {
                riga = this.getRigaSelezionata();
                chiave = this.getChiave(riga);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            new Errore(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return chiave;
    }


    /**
     * Restituisce il numero di righe selezionate nella lista
     * <p/>
     *
     * @return il numero di righe selezionate
     */
    public int getQuanteRigheSelezionate() {
        /* variabili e costanti locali di lavoro */
        int quante = 0;
        Tavola tavola;

        try { // prova ad eseguire il codice
            tavola = this.getTavola();
            if (tavola != null) {
                quante = tavola.getSelectedRowCount();
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            new Errore(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return quante;
    }


    /**
     * Comando del mouse cliccato nei titoli.
     * <p/>
     * Invoca il metodo delegato <br>
     *
     * @param unEvento evento generato dall'interfaccia utente
     */
    public void colonnaCliccata(MouseEvent unEvento) {
        /* variabili e costanti locali di lavoro */
        int colonna;
        Campo campo;
        int verso;

        try { // prova ad eseguire il codice

            /* recupera il numero della colonna cliccata */
            colonna = this.getTavola().getColonnaCliccata(unEvento);

            /* recupera il campo corrispondente */
            campo = getCampo(colonna);
            
            if (campo != null) {
            	
                if (campo.getCampoDB().isCampoFisico()) {

                    /*
                     * determina se e' il campo di ordinamento corrente.
                     * - se e' il campo corrente, inverte il flag
                     * - se e' un altro campo, pone il flag ad Ascendente
                     */
                    if (campo == this.getCampoOrdinamento()) {
                        this.setOrdineAscendente(!this.isOrdineAscendente());
                    } else {
                        this.setOrdineAscendente(true);
                    }// fine del blocco if-else

                    /* ordina sul campo */
                    if (this.isOrdineAscendente()) {
                        verso = VERSO_ASCENDENTE;
                    } else {
                        verso = VERSO_DISCENDENTE;
                    }// fine del blocco if-else

                    this.ordina(campo, verso);

                }// fine del blocco if

			}



        } catch (Exception unErrore) { // intercetta l'errore
            new Errore(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Ordina sulla colonna.
     * <p/>
     *
     * @param colonna colonna su cui effettuare l'ordinamento
     * (1 per la prima colonna)
     */
    private void colonna(int colonna) {
        /* variabili e costanti locali di lavoro */
        Campo unCampo;

        try { // prova ad eseguire il codice

            /* recupera il campo corrispondente */
            unCampo = getCampo(colonna - 1);

            /* esegue l'ordinamento sul campo
             * (sempre ascendente perché ho appena cambiato colonna) */
            this.ordina(unCampo);

            /* registra come attributo la colonna selezionata (1 per la prima) */
            this.getTavola().setColonnaSelezionata(colonna);

        } catch (Exception unErrore) { // intercetta l'errore
            new Errore(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Ordina sulla colonna a sinistra.
     * <p/>
     * Sposta l'ordinamento sulla colonna a sinistra di quella attuale <br>
     * Se l'ordinamento era sulla prima colonna, non esegue nulla <br>
     * Aggiorna il colore del voce <br>
     */
    public void colonnaSinistra() {
        /* variabili e costanti locali di lavoro */
        int colonna;

        try { // prova ad eseguire il codice

            /* recupera la colonna attuale */
            colonna = this.getTavola().getColonnaSelezionata();

            /* esegue solo dalla prima colonna in su */
            if (colonna > 1) {
                /* invoca il metodo delegato della classe */
                this.colonna(colonna - 1);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            new Errore(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Ordina sulla colonna a destra.
     * <p/>
     * Sposta l'ordinamento sulla colonna a destra di quella attuale <br>
     * Se l'ordinamento era sull'ultima colonna, non esegue nulla <br>
     * Aggiorna il colore del voce <br>
     */
    public void colonnaDestra() {
        /* variabili e costanti locali di lavoro */
        int colonna;
        int max;

        try { // prova ad eseguire il codice

            /* recupera la colonna attuale */
            colonna = this.getTavola().getColonnaSelezionata();

            /* recupera il numero totale di colonne da non superare */
            max = this.getTavola().getColumnCount();

            /* esegue solo dalla prima colonna in su */
            if (colonna < max) {
                /* invoca il metodo delegato della classe */
                this.colonna(colonna + 1);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            new Errore(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Ordina la lista su un campo.
     * <p/>
     * Invoca il metodo sovrascritto della classe <br>
     * Ricarica la selezione <br>
     *
     * @param campo il campo sul quale ordinare la lista
     */
    public void ordina(Campo campo) {
        this.ordina(campo, VERSO_ASCENDENTE);
    }


    /**
     * Ordina la lista su un campo.
     * <p/>
     * Ricarica la selezione
     *
     * @param campo il campo sul quale ordinare la lista
     * @param verso VERSO_ASCENDENTE, VERSO_DISCENDENTE, VERSO_DEL_CAMPO
     */
    public void ordina(Campo campo, int verso) {
        /* variabili e costanti locali di lavoro */
        Ordine ordine;
        String operatore;

        try {    // prova ad eseguire il codice

            /* registra il nuovo campo di ordinamento corrente */
            this.setCampoOrdinamento(campo);

            /* regola l'ordine della lista in base al campo di ordinamento corrente */
            this.regolaOrdineLista();

            /* recupera l'ordine della lista e regola il flag del verso di ordinamento */
            ordine = this.getOrdine();
            if (ordine != null) {

                /* determina l'operatore da utilizzare */
                switch (verso) {
                    case VERSO_ASCENDENTE:
                        operatore = Operatore.ASCENDENTE;
                        break;
                    case VERSO_DISCENDENTE:
                        operatore = Operatore.DISCENDENTE;
                        break;
                    case VERSO_DEL_CAMPO:
                        operatore = null;
                        break;
                    default: // caso non definito
                        operatore = Operatore.ASCENDENTE;
                        break;
                } // fine del blocco switch

                /*
                 * modifica l'operatore del primo ordine, se non e'
                 * stato richiesto il verso del campo.
                 * In tal caso, lascia l'ordine invariato
                 */
                if (operatore != null) {
                    ordine.getElemento(0).setOperatore(operatore);
                }// fine del blocco if

                /* ricarica la lista */
                this.caricaSelezione();

            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

    }


    /**
     * Regola l'ordine della lista in base al campo di ordinamento corrente.
     * <p/>
     * Recupera il campo di ordinamento corrente <br>
     * Recupera l'ordine dal campo <br>
     * - Se il campo e' di questo modulo, usa l'ordine privato <br>
     * - Se e' di un altro modulo, usa l'ordine pubblico <br>
     */
    private void regolaOrdineLista() {
        /* variabili e costanti locali di lavoro */
        Campo campo;
        Ordine ordine = null;

        try {    // prova ad eseguire il codice

            /* recupera il campo di ordinamento */
            campo = this.getCampoOrdinamento();

            if (campo != null) {

                if (campo.getModulo() == this.getModulo()) {
                    ordine = campo.getCampoLista().getOrdinePrivato();
                } else {    // il campo e' di un altro modulo
                    ordine = campo.getCampoLista().getOrdinePubblico();
                } /* fine del blocco if-else */
            }// fine del blocco if

            if (ordine != null) {
                this.setOrdine(ordine);
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Ritorna il campo corrispondente a una colonna visibile
     * nella lista.
     * <p/>
     *
     * @param colonna l'indice della colonna visibile (0 per la prima)
     *
     * @return il campo corrispondente
     */
    public Campo getCampo(int colonna) {
        return this.getVista().getCampoVisibile(colonna);
    }


    /**
     * Recupera un campo dal modello completo di questa Lista.
     * <p/>
     * L'indice fa riferimento all'elenco completo dei campi,
     * compresi quelli invisibili.
     *
     * @param indice l'indice del campo da recuperare (0 per il primo)
     *
     * @return il campo corrispondente
     */
    public Campo getCampoModello(int indice) {
        return this.getVista().getCampo(indice);
    }


    /**
     * Ritorna l'indice della colonna della Tavola corrispondente a un campo della Lista.
     * <p/>
     *
     * @param campo il campo per il quale ricavare l'indice di colonna
     *
     * @return l'indice della colonna corrispondente (0 per la prima)
     */
    public int getColonna(Campo campo) {
        return this.getVista().getIndiceCampoVisibile(campo);
    }


    /**
     * Recupera il campo della colonna corrente.
     * <p/>
     *
     * @return il campo della colonna corrente (selezionata)
     */
    public Campo getCampoCorrente() {
        /* variabili e costanti locali di lavoro */
        Campo campo = null;

        try {    // prova ad eseguire il codice
            campo = this.getCampo(this.getTavola().getColonnaSelezionata());
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return campo;
    }


    /**
     * Restituisce l'oggetto concreto dell'interfaccia.
     * <p/>
     *
     * @return componente concreto
     */
    public JComponent getLista() {
        return this;
    } /* fine del metodo getter */


    /**
     * Restituisce l'oggetto concreto dell'interfaccia.
     * <p/>
     *
     * @return oggetto base concreto
     */
    public ListaBase getListaBase() {
        return this;
    }


    /**
     * Restituisce la posizione della riga selezionata nella lista.
     * <p/>
     *
     * @return la posizione della riga selezionata (0 per la prima)
     *         (-1 se non ci sono righe selezionate)
     */
    public int getRigaSelezionata() {
        /* variabili e costanti locali di lavoro */
        int riga = 0;
        Tavola unaTavola;

        try { // prova ad eseguire il codice
            /* recupera il riferimento alla tavola */
            unaTavola = this.getTavola();

            if (unaTavola != null) {
                riga = unaTavola.getSelectedRow();
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            new Errore(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return riga;
    }


    /**
     * Restituisce la posizione delle righe selezionate nella lista.
     * <p/>
     *
     * @return elenco delle posizioni delle righe selezionate (la prima è zero)
     */
    public int[] getRigheSelezionate() {
        /* variabili e costanti locali di lavoro */
        int righe[] = null;
        Tavola unaTavola;

        try { // prova ad eseguire il codice
            unaTavola = this.getTavola();
            if (unaTavola != null) {
                righe = unaTavola.getSelectedRows();
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            new Errore(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return righe;
    }


    /**
     * Restituisce la posizione nella lista di un dato codice chiave.
     * <p/>
     *
     * @param chiave il codice chiave da cercare
     *
     * @return la posizione della riga (0 per la prima, -1 se non trovata)
     */
    public int getPosizione(int chiave) {
        return this.getModello().getPosizione(chiave);
    }


    /**
     * Restituisce le righe della Tavola.
     * <p/>
     *
     * @return il numero totale di righe della JTable
     */
    protected int getRowCount() {
        /* variabili e costanti locali di lavoro */
        int righe = 0;
        Tavola unaTavola;

        try { // prova ad eseguire il codice
            /* recupera il riferimento alla tavola */
            unaTavola = this.getTavola();

            if (unaTavola != null) {
                righe = this.getTavola().getRowCount();
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            new Errore(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return righe;
    }


    /**
     * Restituisce il valore di una cella.
     * <p/>
     *
     * @param riga della tavola (0 per la prima)
     * @param campo del quale recuperare il valore
     *
     * @return il valore della cella richiesta
     */
    public Object getValueAt(int riga, Campo campo) {
        /* variabili e costanti locali di lavoro */
        Object valore = null;
        int colonna;

        try { // prova ad eseguire il codice
            colonna = this.getColonna(campo);
            if (colonna >= 0) {
                valore = this.getTavola().getValueAt(riga, colonna);
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            new Errore(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return valore;
    }


    /**
     * Restituisce il valore di una cella della riga selezionata.
     * <p/>
     *
     * @param campo del quale recuperare il valore
     *
     * @return il valore della cella richiesta
     */
    public Object getSelectedValueAt(Campo campo) {
        /* variabili e costanti locali di lavoro */
        Object valore = null;
        boolean continua = true;
        int riga = -1;

        try { // prova ad eseguire il codice

            /* controllo che una e una sola riga sia selezionata */
            if (continua) {
                continua = (this.getQuanteRigheSelezionate() == 1);
            }// fine del blocco if

            /* recupero il numero della riga selezionata */
            if (continua) {
                riga = this.getRigaSelezionata();
            }// fine del blocco if

            /* recupero il valore */
            if (riga >= 0) {
                valore = this.getValueAt(riga, campo);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            new Errore(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return valore;
    }


    /**
     * Restituisce il valore di una cella visibile.
     * <p/>
     *
     * @param riga la riga della tavola (0 per la prima)
     * @param colonna la colonna della tavola (0 per la prima)
     *
     * @return il valore della cella
     */
    protected Object getValueAt(int riga, int colonna) {
        return this.getTavola().getValueAt(riga, colonna);
    }


    /**
     * Restituisce il valore intero di una cella visibile.
     * <p/>
     *
     * @param riga la riga della tavola (0 per la prima)
     * @param colonna la colonna della tavola (0 per la prima)
     *
     * @return il valore intero della cella
     */
    protected int getIntAt(int riga, int colonna) {
        return Libreria.getInt(this.getValueAt(riga, colonna));
    }


    /**
     * Restituisce il valore stringa di una cella visibile.
     * <p/>
     *
     * @param riga la riga della tavola (0 per la prima)
     * @param colonna la colonna della tavola (0 per la prima)
     *
     * @return il valore stringa della cella
     */
    protected String getStringAt(int riga, int colonna) {
        return Lib.Testo.getStringa(this.getValueAt(riga, colonna));
    }


    /**
     * Restituisce il valore booleano di una cella visibile.
     * <p/>
     *
     * @param riga la riga della tavola (0 per la prima)
     * @param colonna la colonna della tavola (0 per la prima)
     *
     * @return il valore booleano della cella
     */
    protected boolean getBoolAt(int riga, int colonna) {
        return Libreria.getBool(this.getValueAt(riga, colonna));
    }


    /**
     * Assegna il titolo a una colonna.
     * <p/>
     *
     * @param campo della colonna
     * @param titolo da assegnare
     */
    public void setTitoloColonna(Campo campo, String titolo) {
        /* variabili e costanti locali di lavoro */
        int colonna;
        Tavola tavola;

        try {    // prova ad eseguire il codice
            colonna = getColonna(campo);
            if (colonna >= 0) {
                tavola = this.getTavola();
                if (tavola != null) {
                    tavola.setVisibleColumnName(colonna, titolo);
                }// fine del blocco if
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Aggiorna il valore video di un campo.
     * <p/>
     * Metodo invocato quando il campo prende il fuoco <br>
     * Metodo  invocato solo se il campo (colonna) è modificabile <br>
     *
     * @return l'oggetto Campo per ulteriori usi
     */
    public Campo aggiornaCampoInfuocato() {
        /* variabili e costanti locali di lavoro */
        Campo campo = null;
        int colonnaLink = 0;
        Object valoreLink = null;
        int riga = 0;
        int colonna = 0;

        try { // prova ad eseguire il codice
            riga = this.getTavola().getEditingRow();
            colonna = this.getTavola().getEditingColumn();
            //colonna++;    // todo levato alex 18-03-05, puntava alla colonna sbagliata e andava in errore

            if (this.isColonnaLinkata(colonna)) { //todo questo non funge/gac
                campo = recuperaCampoValori(colonna);

                colonnaLink = this.getColonna(campo);
                valoreLink = this.getModello().getValueAt(riga, colonnaLink);

                //todo provvisorio?
                valoreLink = this.getModello().getValueAt(riga, 2);
                //todo provvisorio - pero funziona

                campo.getCampoDati().setArchivio(valoreLink);
                campo.getCampoLogica().archivioGui();
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            new Errore(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return campo;
    }


    /**
     * Intercetta prepareRenderer di JTable.
     * <p/>
     * Prepara il renderer per una cella prima che venga disegnato.
     * Sovrascritto dalle sottoclassi. <br>
     * Permette alla sottoclasse di modificare il componente
     * da visualizzare nella cella. <br>
     *
     * @param comp il componente da visualizzare della cella
     * @param riga l'indice della riga (0 per la prima)
     * @param colonna l'indice della colonna (0 per la prima)
     *
     * @return il componente da visualizzare nella cella
     */
    public Component prepareRenderer(Component comp, int riga, int colonna) {
        return comp;
    }


    /**
     * Ritorna la Vista della Lista.
     * <p/>
     *
     * @return vista la Vista da assegnata alla lista
     */
    public Vista getVista() {
        return this.vista;
    }


    /**
     * Regola la Vista per la Lista.
     * <p/>
     *
     * @param vista la Vista da assegnare alla lista
     */
    public void setVista(Vista vista) {
        this.vista = vista;
    }


    /**
     * Ritorna il nome della Vista della Lista.
     * <p/>
     *
     * @return il nome della Vista
     */
    public String getNomeVista() {
        return this.nomeVista;
    }


    /**
     * Regola la Vista per la Lista.
     * <p/>
     *
     * @param nomeVista nome della Vista
     */
    public void setNomeVista(String nomeVista) {
        this.nomeVista = nomeVista;
    }


    public JScrollPane getScorrevole() {
        return scorrevole;
    }


    private void setScorrevole(JScrollPane scorrevole) {
        this.scorrevole = scorrevole;
    }


    public PortaleLista getPortale() {
        return portale;
    }


    public void setPortale(PortaleLista portale) {
        this.portale = portale;
    }


    public Tavola getTavola() {
        return tavola;
    }


    public void setTavola(Tavola tavola) {
        this.tavola = tavola;
    }


    public Tavola getTavolaTotali() {
        return tavolaTotali;
    }


    private void setTavolaTotali(Tavola tavolaTotali) {
        this.tavolaTotali = tavolaTotali;
    }


    /**
     * Ritorna l'altezza preferita della lista.
     * <p/>
     *
     * @return il numero di righe visualizzate nella lista
     */
    public int getAltezzaPreferita() {
        return altezzaPreferita;
    }


    /**
     * Regola l'altezza preferita della lista.
     * <p/>
     *
     * @param altezzaPreferita il numero di righe da visualizzare
     */
    public void setAltezzaPreferita(int altezzaPreferita) {
        this.altezzaPreferita = altezzaPreferita;
    }


    public Filtro getFiltroBase() {
        return filtroBase;
    }


    public void setFiltroBase(Filtro filtroBase) {
        this.filtroBase = filtroBase;
        this.setTotaliAggiornati(false);
    }


    public Filtro getFiltroCorrente() {
        return filtroCorrente;
    }


    public void setFiltroCorrente(Filtro filtroCorrente) {
        this.filtroCorrente = filtroCorrente;
        this.setTotaliAggiornati(false);
    }


    /**
     * Ritorna il filtro completo della lista.
     * <p/>
     * E' composto da filtro base + filtro corrente
     *
     * @return il filtro completo
     */
    public Filtro getFiltro() {
        /* variabili e costanti locali di lavoro */
        Filtro filtro = null;

        try {    // prova ad eseguire il codice
            filtro = new Filtro();
            filtro.add(this.getFiltroBase());
            filtro.add(this.getFiltroCorrente());
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return filtro;
    }


    /**
     * Aggiunge un filtro al filtro corrente della lista.
     * <p/>
     * Se il filtro corrente non esiste, imposta il filtro
     * appena passato come filtro corrente
     *
     * @param unione la clausola di unione del filtro nuovo al corrente
     * @param filtro il filtro da aggiungere
     */
    public void addFiltroCorrente(String unione, Filtro filtro) {
        /* variabili e costanti locali di lavoro */
        Filtro filtroAttuale;

        try { // prova ad eseguire il codice
            filtroAttuale = this.getFiltroCorrente();
            if (filtroAttuale != null) {
                filtroAttuale.add(unione, filtro);
            } else {
                this.setFiltroCorrente(filtro);
            }// fine del blocco if-else

        } catch (Exception unErrore) { // intercetta l'errore
            new Errore(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Aggiunge un filtro al filtro corrente della lista.
     * <p/>
     * Se il filtro corrente non esiste, imposta il filtro
     * appena passato come filtro corrente
     * Il nuovo filtro viene aggiunto con la clausola AND
     *
     * @param filtro il filtro da aggiungere
     */
    public void addFiltroCorrente(Filtro filtro) {
        this.addFiltroCorrente(Operatore.AND, filtro);
    }


    private Campo getCampoOrdinamento() {
        return campoOrdinamento;
    }


    public void setCampoOrdinamento(Campo campoOrdinamento) {
        this.campoOrdinamento = campoOrdinamento;
    }


    private boolean isOrdineAscendente() {
        return isOrdineAscendente;
    }


    private void setOrdineAscendente(boolean ordineAscendente) {
        this.isOrdineAscendente = ordineAscendente;
    }


    /**
     * Recupera l'ordine corrente della lista.
     *
     * @return l'ordine
     */
    public Ordine getOrdine() {
        return ordine;
    }


    /**
     * Regola l'ordine della lista.
     * <p/>
     *
     * @param ordine l'oggetto Ordine
     */
    public void setOrdine(Ordine ordine) {
        this.ordine = ordine;
    }


    /**
     * Controlla se la lista e' ordinata sul campo ordine.
     * <p/>
     * Perche' sia ordinata sul campo ordine, la lista deve avere un ordine
     * il cui primo elemento sia il campo ordine del modulo della lista stessa.
     *
     * @return true se la lista e' ordinata
     */
    public boolean isOrdinataCampoOrdine() {
        /* variabili e costanti locali di lavoro */
        boolean ordinata = false;
        boolean continua;
        Modulo modulo;
        Campo campoOrdine = null;
        Ordine ordine = null;
        Ordine.Elemento elemento = null;
        Campo campo = null;
        String chiaveCampoOrdineModulo = "";
        String chiaveCampoOrdineLista;

        try { // prova ad eseguire il codice

            modulo = this.getModulo();
            continua = (modulo != null);

            if (continua) {
                campoOrdine = modulo.getCampoOrdine();
                continua = (campoOrdine != null);
            }// fine del blocco if

            if (continua) {
                chiaveCampoOrdineModulo = campoOrdine.getChiaveCampo();
                ordine = this.getOrdine();
                continua = (ordine != null);
            }// fine del blocco if

            if (continua) {
                continua = (ordine.getSize() > 0);
            }// fine del blocco if

            if (continua) {
                elemento = ordine.getElemento(0);
                continua = (elemento != null);
            }// fine del blocco if

            if (continua) {
                campo = elemento.getCampo();
                continua = (campo != null);
            }// fine del blocco if

            if (continua) {
                chiaveCampoOrdineLista = campo.getChiaveCampo();
                if (chiaveCampoOrdineModulo.equals(chiaveCampoOrdineLista)) {
                    ordinata = true;
                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            new Errore(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return ordinata;
    }


    /**
     * Ritorna lo stato di modificabilità di una colonna.
     * <p/>
     *
     * @param colonna (0 per la prima)
     */
    public boolean isColonnaModificabile(int colonna) {
        /* variabili e costanti locali di lavoro */
        boolean modificabile = false;

        try {    // prova ad eseguire il codice
            modificabile = this.getCampoModello(colonna).isModificabileLista();
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return modificabile;
    }


    /**
     * Invocato quando si modifica il valore di una cella nella lista.
     * <p/>
     *
     * @param valore il valore memoria proveniente dall'editing
     * @param codice codice chiave del record editato
     * @param campo campo della colonna editata
     */
    public void setValueAt(Object valore, int codice, Campo campo) {
        /* variabili e costanti locali di lavoro */
        Campo unCampo;

        try {    // prova ad eseguire il codice

            unCampo = this.selezionaCampo(campo);
            this.caricaSelezione();

            this.fireCellaModifcata(unCampo, codice);

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

    }


    /**
     * Seleziona il campo su cui operare.
     * <p/>
     * Seleziona il campo su cui operare; nel caso di ComboBox, potrebbe non
     * coincidere con la colonna <br>
     *
     * @param campo della colonna editata
     *
     * @return campo selezionato
     */
    private Campo selezionaCampo(Campo campo) {
        /* variabili e costanti locali di lavoro */
        Campo unCampo = null;
        Campo campoLinkato;

        try { // prova ad eseguire il codice

            campoLinkato = campo.getCampoLista().getCampoValori();
            if (campoLinkato != null) {
                unCampo = campoLinkato;
            } else {
                unCampo = campo;
            }// fine del blocco if-else

        } catch (Exception unErrore) { // intercetta l'errore
            new Errore(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return unCampo;
    }


    /**
     * Recupera il campo valori di una colonna.
     * <p/>
     * Ritorna il campo che gestisce i valori elenco per la colonna  <br>
     * Se il riferimento � nullo, ritorna il campo corrispondente alla colonna <br>
     *
     * @param colonna indice della colonna editata (0 per la prima)
     *
     * @return campo valori
     */
    private Campo recuperaCampoValori(int colonna) {
        /* variabili e costanti locali di lavoro */
        Campo unCampo = null;

        try { // prova ad eseguire il codice
            /* recupera il campo corrispondente alla colonna */
            unCampo = this.getCampoModello(colonna);

            if (this.isColonnaLinkata(colonna)) {
                unCampo = unCampo.getCampoLista().getCampoValori();
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            new Errore(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return unCampo;
    }


    /**
     * Controlla se è selezionata una riga.
     * <p/>
     *
     * @return vero se &egrave; selezionata una ed una sola riga
     *         falso se non ci sono righe selezionate, oppure se ce n'� pi� di una
     */
    public boolean isRigaSelezionata() {
        /* variabili e costanti locali di lavoro */
        boolean selezionata = false;
        int numRighe = 0;

        try { // prova ad eseguire il codice
            numRighe = this.getTavola().getSelectedRowCount();

            if (numRighe == 1) {
                selezionata = true;
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            new Errore(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return selezionata;
    }


    /**
     * Regola la modalità di selezione.
     * <p/>
     *
     * @param modo di selezione
     * valori ammessi;
     * ListSelectionModel.SINGLE_SELECTION
     * ListSelectionModel.SINGLE_INTERVAL_SELECTION
     * ListSelectionModel.MULTIPLE_INTERVAL_SELECTION
     */
    public void setSelectionMode(int modo) {
        /* variabili e costanti locali di lavoro */
        Tavola tavola;

        try {    // prova ad eseguire il codice
            tavola = this.getTavola();
            if (tavola != null) {
                tavola.setSelectionMode(modo);
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
        return this.getTavola().isCarattere(codiceCarattere);
    }


    public boolean isNuovoRecordAbilitato() {
        return this.nuovoRecordAbilitato;
    }


    /**
     * Controlla la possibilita' di creare nuovi record per questa Lista.
     * <p/>
     *
     * @param flag true per abilitare la creazione, false per disabilitarla
     */
    public void setNuovoRecordAbilitato(boolean flag) {
        this.nuovoRecordAbilitato = flag;
    }


    public boolean isModificaRecordAbilitata() {
        return modificaRecordAbilitata;
    }


    public void setModificaRecordAbilitata(boolean modificaRecordAbilitata) {
        this.modificaRecordAbilitata = modificaRecordAbilitata;
    }


    public boolean isDeleteAbilitato() {
        return deleteAbilitato;
    }


    /**
     * Controlla la possibilita' di eliminare records per questa Lista.
     * <p/>
     *
     * @param flag true per abilitare la eliminazione, false per disabilitarla
     */
    public void setDeleteAbilitato(boolean flag) {
        this.deleteAbilitato = flag;
    }


    /**
     * Ritorna true se la lista e' ordinabile cliccando sulle colonne.
     * <p/>
     *
     * @return true se ordinabile
     */
    public boolean isOrdinabile() {
        return ordinamentoAbilitato;
    }


    /**
     * Abilita l'ordinamento cliccando sulle colonne.
     * <p/>
     *
     * @param flag true per abilitare l'ordinamento, false per disabilitarlo
     */
    public void setOrdinabile(boolean flag) {
        this.ordinamentoAbilitato = flag;
    }


    /**
     * Ritorna true se la lista e' ordinabile manualmente.
     * <p/>
     * L'ordinamento manuale si effettua con gli appositi pulsanti
     * nella toolbar della lista ed e' basato sul campo Ordine.
     *
     * @return true se ordinabile manualmente
     */
    public boolean isOrdinamentoManuale() {
        /* variabili e costanti locali di lavoro */
        boolean ordinabile = false;
        Portale portale;
        ToolBar tb;

        try { // prova ad eseguire il codice
            portale = this.getPortale();
            if (portale != null) {
                tb = portale.getToolBar();
                if (tb != null) {
                    ordinabile = tb.isUsaFrecce();
                }// fine del blocco if
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            new Errore(unErrore);
        }// fine del blocco try-catch

        return ordinabile;
    }


    /**
     * Ritorna la posizione della colonna chiave
     * nella Vista di questa Lista.
     *
     * @return la posizione della colonna chiave (0 per la prima)
     */
    public int getPosColonnaCodice() {
        /* variabili e costanti locali di lavoro */
        int colonna = 0;
        TavolaModello modello;

        try { // prova ad eseguire il codice
            modello = this.getModello();
            if (modello != null) {
                colonna = modello.getColonnaChiavi();
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            new Errore(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return colonna;
    }


    private Pannello getPanPiede() {
        return panPiede;
    }


    private void setPanPiede(Pannello panPiede) {
        this.panPiede = panPiede;
    }


    private Pannello getPanTotali() {
        return panTotali;
    }


    private void setPanTotali(Pannello panTotali) {
        this.panTotali = panTotali;
    }


    /**
     * Ritorna il flag di uso della status bar.
     * <p/>
     *
     * @return true se la lista usa la status bar
     */
    public boolean isUsaStatusBar() {
        return isUsaStatusBar;
    }


    /**
     * Abilita o disabilita  l'uso della status bar nella Lista.
     * <p/>
     *
     * @param usaStatusBar true per usare la status bar, false per non usarla
     */
    public void setUsaStatusBar(boolean usaStatusBar) {
        isUsaStatusBar = usaStatusBar;
    }


    /**
     * Ritorna la Status Bar della lista.
     * <p/>
     *
     * @return la Status Bar della lista
     */
    public ListaStatusBar getStatusBar() {
        return statusBar;
    }


    private void setStatusBar(ListaStatusBar statusBar) {
        this.statusBar = statusBar;
    }


    /**
     * Visualizza un testo nel componente custom (centrale).
     * <p/>
     *
     * @param testo da visualizzare nel componente custom (centrale)
     */
    public void setCustom(String testo) {
        /* variabili e costanti locali di lavoro */
        ListaStatusBar statusBar;

        try { // prova ad eseguire il codice
            statusBar = this.getStatusBar();

            if (statusBar != null) {
                statusBar.setCustom(testo);
            } // fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    private boolean isUsaTotali() {
        return usaTotali;
    }


    /**
     * Abilita o disabilita  l'uso della barra dei totali nella Lista.
     * <p/>
     *
     * @param flag true per usare la barra dei totali, false per non usarla
     */
    public void setUsaTotali(boolean flag) {
        this.usaTotali = flag;
    }


    /**
     * Ritorna il numero totale di records selezionati dal filtro base.
     * <p/>
     * Corrisponde al numero di records virtualmente disponibili
     * per questa lista.<br>
     * Aggiornato ad ogni caricamento dei record.<br>
     *
     * @return il numero di records selezionati dal filtro base
     */
    public int getNumRecordsDisponibili() {
        return numRecordsDisponibili;
    }


    private void setNumRecordsDisponibili(int numRecordsBase) {
        this.numRecordsDisponibili = numRecordsBase;
    }


    /**
     * Ritorna il numero totale di records visualizzati nella lista.
     * <p/>
     * Corrisponde al numero di records selezionati dal filtro
     * base piu' il filtro corrente.<br>
     * Aggiornato ad ogni caricamento dei record.
     *
     * @return il numero di records attualmente visualizzati.
     */
    public int getNumRecordsVisualizzati() {
        return numRecordsVisualizzati;
    }


    private void setNumRecordsVisualizzati(int numRecordsVisualizzati) {
        this.numRecordsVisualizzati = numRecordsVisualizzati;
    }


    public Navigatore getNavigatore() {
        return this.getPortale().getNavigatore();
    }


    /**
     * Ritorna la connessione utilizzata per l'accesso al database.
     * <p/>
     *
     * @return la connessione
     */
    private Connessione getConnessione() {
        return this.getNavigatore().getConnessione();
    }


    public boolean isUsaCarattereFiltro() {
        return this.usaCarattereFiltro;
    }


    /**
     * Abilita l'uso dei caratteri per filtrare la lista.
     */
    public void setUsaCarattereFiltro(boolean usaCarattereFiltro) {
        this.usaCarattereFiltro = usaCarattereFiltro;
    }


    /**
     * Ritorna l'uso dei filtri pop
     * <p/>
     *
     * @return true se la lista usa i filtri pop
     */
    public boolean isUsaFiltriPop() {
        return usaFiltriPop;
    }


    /**
     * Regola l'uso dei filtri pop
     * <p/>
     *
     * @param flag per attivare l'uso dei filtri pop
     */
    public void setUsaFiltriPop(boolean flag) {
        this.usaFiltriPop = flag;
    }


    /**
     * Determina se i valori elenco di una colonna sono controllati da un'altra colonna.
     *
     * @param colonna interessata
     *
     * @return true se esiste una colonna con i valori elenco
     */
    private boolean isColonnaLinkata(int colonna) {
        /* variabili e costanti locali di lavoro */
        Campo campoColonna = null;
        Campo campoValori = null;
        boolean linkata = false;

        try { // prova ad eseguire il codice
            if (colonna > -1) {
                /* recupera il campo corrispondente alla colonna */
                campoColonna = this.getCampoModello(colonna);

                if (campoColonna != null) {
                    campoValori = campoColonna.getCampoLista().getCampoValori();
                }// fine del blocco if

                linkata = (campoValori != null ? true : false);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            new Errore(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return linkata;
    }


    /**
     * Prende il fuoco sulla tavola.
     * <p/>
     */
    @Override
    public void grabFocus() {
        /* variabili e costanti locali di lavoro */
        Tavola tavola;

        try {    // prova ad eseguire il codice
            tavola = this.getTavola();

            if (tavola != null) {
                tavola.grabFocus();
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Stampa la lista.
     * <p/>
     *
     * @param titolo eventuale della stampa
     */
    public void stampa(String titolo) {
        /* variabili e costanti locali di lavoro */
        J2Printer printer;
        J2FlowPrinter fp;

        try {    // prova ad eseguire il codice

            /* crea e regola il Printer */
            printer = Lib.Stampa.getDefaultPrinter();
            printer.setCenterHeader(titolo);
            printer.setRightHeader(this.getRowCount() + " records ");

//            /* crea un flow printer */
//            fp= new J2FlowPrinter();
//
//            /* aggiunge la tavola dati */
//            tablePrinter = this.getPrinterTavolaDati();
//            fp.addFlowable(tablePrinter);
//
//            /* aggiunge la tavola totali, se usata */
//            if (this.isUsaTotali()) {
//                tablePrinter = this.getPrinterTavolaTotali();
//                fp.addFlowable(tablePrinter);
//            }// fine del blocco if

            /* aggiunge il flow printer completo */
            fp = this.getFlowPrinterLista();
            printer.addPageable(fp);

            /* stampa */
            printer.print();

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

    }


    /**
     * Ritorna un flow printer per la lista completa (dati e totali).
     * <p/>
     *
     * @return il flow Printer creato
     */
    public J2FlowPrinter getFlowPrinterLista() {
        /* variabili e costanti locali di lavoro */
        J2FlowPrinter fp = new J2FlowPrinter();
        J2TablePrinter tp;


        try { // prova ad eseguire il codice

            /* aggiunge la tavola dati */
            tp = this.getPrinterTavolaDati();
            fp.addFlowable(tp);

            /* aggiunge la tavola totali, se usata */
            if (this.isUsaTotali()) {
                tp = this.getPrinterTavolaTotali();
                fp.addFlowable(tp);
            }// fine del blocco if
            
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return fp;
    }


    /**
     * Ritorna un printer per la stampa della tavola dati.
     * <p/>
     *
     * @return il Printer creato
     */
    public J2TablePrinter getPrinterTavolaDati() {
        /* variabili e costanti locali di lavoro */
        J2TablePrinter tablePrinter = null;
        boolean continua;
        Tavola tavola;

        try {    // prova ad eseguire il codice

            tavola = this.getTavola();
            continua = (tavola != null);

            if (continua) {
                tablePrinter = tavola.getTablePrinter();
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return tablePrinter;
    }


    /**
     * Ritorna un printer per la stampa della tavola totali.
     * <p/>
     *
     * @return il Printer creato
     */
    public J2TablePrinter getPrinterTavolaTotali() {
        /* variabili e costanti locali di lavoro */
        J2TablePrinter tablePrinter = null;
        boolean continua;
        Tavola tavola;

        try {    // prova ad eseguire il codice

            tavola = this.getTavolaTotali();
            continua = (tavola != null);

            if (continua) {
                tablePrinter = tavola.getTablePrinter();

                /* rende invisibile l'header */
                JTableHeader header;
                header = tablePrinter.getTable().getTableHeader();
                header.setPreferredSize(new Dimension(0,0));
                Lib.Comp.bloccaDim(header);

                /* regola il colore di sfondo della tavola */
                JTable table;
                table = tablePrinter.getTable();
                table.setFont(FontFactory.creaPrinterFont(Font.BOLD));
                table.setBackground(Color.lightGray);
                table.setOpaque(true);

            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return tablePrinter;
    }


    /**
     * .
     * <p/>
     */
    private void regolaFiltroPop() {
        /* variabili e costanti locali di lavoro */
        Filtro filtro;

        try {    // prova ad eseguire il codice
            filtro = this.getStatusBar().getFiltroPops();
            this.setFiltroCorrente(filtro);
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

    }


    /**
     * Ritorna il totale di un campo totalizzato nella lista.
     * <p/>
     *
     * @param campo totalizzato
     *
     * @return il valore del totale
     */
    public double getTotale(Campo campo) {
        /* variabili e costanti locali di lavoro */
        double totale = 0.0;
        boolean continua;
        TavolaModello modTotali = null;
        Vista vista = null;
        int indice = 0;
        Object oggetto;

        try {    // prova ad eseguire il codice
            continua = campo != null;

            if (continua) {
                modTotali = this.getModelloTotali();
                continua = modTotali != null;
            }// fine del blocco if

            if (continua) {
                vista = modTotali.getVista();
                continua = vista != null;
            }// fine del blocco if

            if (continua) {
                indice = vista.getIndiceCampo(campo);
                continua = indice >= 0;
            }// fine del blocco if

            if (continua) {
                continua = modTotali.getRowCount() == 1;
            }// fine del blocco if

            if (continua) {
                oggetto = modTotali.getValueAt(0, indice);
                totale = Libreria.getDouble(oggetto);
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return totale;
    }


    /**
     * Ritorna il totale di un campo totalizzato nella lista.
     * <p/>
     *
     * @param nome del campo totalizzato (campo del modulo della lista)
     *
     * @return il valore del totale
     */
    public double getTotale(String nome) {
        /* variabili e costanti locali di lavoro */
        double totale = 0.0;
        Campo campo;

        try {    // prova ad eseguire il codice
            campo = this.getCampoModulo(nome);
            if (campo != null) {
                totale = this.getTotale(campo);
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return totale;
    }


    /**
     * Azione di cambio valore memoria del campo.
     */
    private class AzioneMemoriaCampo extends CampoMemoriaAz {

        /**
         * CampoMemoriaAz, da CampoListener.
         * </p>
         * Esegue l'azione <br>
         * Rimanda al metodo delegato, nel gestore specifico associato
         * all' oggetto che genera questo evento <br>
         * Sovrascritto nelle sottoclassi <br>
         *
         * @param unEvento evento che causa l'azione da eseguire <br>
         */
        public void campoMemoriaAz(CampoMemoriaEve unEvento) {
            try { // prova ad eseguire il codice

                Campo campo = unEvento.getCampo();
                int a = 87;


            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }
    } // fine della classe interna


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
     * È responsabilità della classe invocare questo metodo quando
     * si creano le condizioni per generare l'evento <br>
     *
     * @see javax.swing.event.EventListenerList
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
     * @param evento generato dall'interfaccia utente
     *
     * @see javax.swing.event.EventListenerList
     */
    public void fire(Eventi evento) {
        /* variabili e costanti locali di lavoro */
        EventListenerList listaListener;

        try {    // prova ad eseguire il codice
            listaListener = this.getListaListener();
            Lib.Eventi.fire(listaListener, evento, Lista.class, this);
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
     * È responsabilità della classe invocare questo metodo quando
     * si creano le condizioni per generare l'evento <br>
     *
     * @see javax.swing.event.EventListenerList
     */
    private void fireCellaModifcata(Campo campo, int codice) {
        /* variabili e costanti locali di lavoro */
        EventListenerList listaListener;
        Parametro eve;
        Parametro cam;
        Parametro cod;

        try {    // prova ad eseguire il codice
            listaListener = this.getListaListener();
            eve = new Parametro(Lista.class, this);
            cam = new Parametro(Campo.class, campo);
            cod = new Parametro(Integer.class, codice);
            Lib.Eventi.fire(listaListener, Evento.cellaModificata, eve, cam, cod);
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Inner class per gestire l'azione.
     */
    private class AzioneModificaSelezioneRighe extends TavolaSelAz {

        /**
         * Esegue l'azione <br>
         *
         * @param unEvento evento che causa l'azione da eseguire <br>
         */
        public void tavolaSelAz(TavolaSelEve unEvento) {
            try { // prova ad eseguire il codice
                fire(Evento.selezione);
            } catch (Exception unErrore) { // intercetta l'errore
                new Errore(unErrore);
            }// fine del blocco try-catch
        }
    } // fine della classe interna

    /**
     * Invocato quando cambia la larghezza di una colonna
     * della tavola dati.
     */
    private class AzioneResizeColonna extends TavolaColResizeAz {

        /**
         * Esegue l'azione <br>
         *
         * @param unEvento evento che causa l'azione da eseguire <br>
         */
        public void tavolaColResizeAz(TavolaColResizeEve unEvento) {
            try { // prova ad eseguire il codice

                /* sincronizza le larghezze delle colonne nella tavola totali */
                syncColonneTotali();

            } catch (Exception unErrore) { // intercetta l'errore
                new Errore(unErrore);
            }// fine del blocco try-catch
        }
    } // fine della classe interna

    /**
     * Invocato quando si seleziona un elemento dai popup filtro
     * <p/>
     */
    private class AzionePopStatuBar extends PortaleListaPopAz {

        /**
         * portaleListaPopAz, da PortaleListaPopLis.
         * </p>
         * Esegue l'azione <br>
         * Rimanda al metodo sovrascritto, nell'oggetto specifico
         * della classe che genera questo evento <br>
         * Sovrascritto nelle sottoclassi <br>
         *
         * @param unEvento evento che causa l'azione da eseguire <br>
         */
        public void portaleListaPopAz(PortaleListaPopEve unEvento) {
            try { // prova ad eseguire il codice
                regolaFiltroPop();
                caricaSelezione();
                getNavigatore().sincronizza();
            } catch (Exception unErrore) { // intercetta l'errore
                new Errore(unErrore);
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

        click(ListaClicLis.class, ListaClicEve.class, ListaClicAz.class, "listaClicAz"),
        doppioClick(
                ListaDoppioClicLis.class,
                ListaDoppioClicEve.class,
                ListaDoppioClicAz.class,
                "listaDoppioClicAz"),
        enter(ListaEnterLis.class, ListaEnterEve.class, ListaEnterAz.class, "listaEnterAz"),
        ritorno(ListaReturnLis.class, ListaReturnEve.class, ListaReturnAz.class, "listaReturnAz"),
        selezione(ListaSelLis.class, ListaSelEve.class, ListaSelAz.class, "listaSelAz"),
        frecciaAlto(
                ListaCursoreLis.class,
                ListaCursoreEve.class,
                ListaCursoreAz.class,
                "listaFrecciaAltoAz"),
        frecciaBasso(
                ListaCursoreLis.class,
                ListaCursoreEve.class,
                ListaCursoreAz.class,
                "listaFrecciaBassoAz"),
        paginaSu(
                ListaCursoreLis.class,
                ListaCursoreEve.class,
                ListaCursoreAz.class,
                "listaPaginaSuAz"),
        paginaGiu(
                ListaCursoreLis.class,
                ListaCursoreEve.class,
                ListaCursoreAz.class,
                "listaPaginaGiuAz"),
        home(ListaCursoreLis.class, ListaCursoreEve.class, ListaCursoreAz.class, "listaHomeAz"),
        end(ListaCursoreLis.class, ListaCursoreEve.class, ListaCursoreAz.class, "listaEndAz"),
        cellaModificata(
                ListaModCellaLis.class,
                ListaModCellaEve.class,
                ListaModCellaAz.class,
                "listaModCellaAz"),
        generico(ListaGenLis.class, ListaGenEve.class, ListaGenAz.class, "listaGenAz");

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

    /**
     * Layout manager della Lista.
     * </p>
     *
     * @author Guido Andrea Ceresa, Alessandro Valbonesi
     * @author alex
     * @version 1.0    / 7-feb-2005 ore 15.05.12
     */
    private final class LayoutLista extends BorderLayout {

        /**
         * Lista di riferimento
         */
        private Lista lista = null;


        /**
         * Costruttore completo con parametri.
         * <p/>
         *
         * @param lista la lista di riferimento
         */
        public LayoutLista(Lista lista) {
            /* rimanda al costruttore della superclasse */
            super();

            /* regola le variabili di istanza coi parametri */
            this.setLista(lista);

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
        }// fine del metodo inizia


        /**
         * Ritorna la dimensione preferita della Lista.
         * <p/>
         * La larghezza viene lasciata calcolare alla superclasse.<br>
         * L'altezza viene calcolata in funzione del numero di righe
         * da visualizzare e degli altri componenti della lista.<br>
         * <p/>
         * 24-12-05 BUG FIX JAVA (o forse e' previsto che
         * si comporti cosi', ma non va bene):
         * Problema: getPreferredSize della Lista
         * ritorna una dimensione che non comprende l'altezza
         * dell'header della tavola contenuta nella Lista.
         * Percio' aggiungo qui l'altezza dell'header.
         * Alex
         *
         * @return la dimensione preferita della Lista
         */
        public Dimension preferredLayoutSize(Container cont) {
            /* variabili e costanti locali di lavoro */
            Lista lista;
            Tavola tavola;
            Dimension dim = null;
            JTableHeader header;
            Dimension dimLista = null;
            int wLista;
            int hLista;
            int quanteRighe;

            Insets iLista;
            int iListaTop = 0;
            int iListaBot = 0;

            Insets iScroll;
            int iScrollTop = 0;
            int iScrollBot = 0;

            int hInsets = 0;

            int hHeader = 0;
            int hRiga = 0;
            int hPanPiede = 0;
            int wTot = 0;
            int hTot = 0;

            try {    // prova ad eseguire il codice

                /* recupera la lista */
                lista = this.getLista();
                tavola = lista.getTavola();

                /* recupera la larghezza della lista
                 * (e' pari a quella dello scorrevole) */
                dimLista = super.preferredLayoutSize(cont);
                int w0 = tavola.getPreferredScrollableViewportSize().width;
                wLista = w0;

                /* recupera gli insets verticali della lista */
                iLista = lista.getListaBase().getInsets();
                iListaTop = iLista.top;
                iListaBot = iLista.bottom;

                /* recupera gli insets dello scorrevole */
                iScroll = lista.getListaBase().getScorrevole().getInsets();
                iScrollTop = iScroll.top;
                iScrollBot = iScroll.bottom;

                /* determina l'altezza della lista in funzione
                 * delle righe da visualizzare */
                quanteRighe = lista.getAltezzaPreferita();
                hRiga = lista.getTavola().getRowHeight();
                hLista = quanteRighe * hRiga;

                /* recupera l'altezza dell'header */
                header = lista.getTavola().getTableHeader();
                if (header != null) {
                    hHeader = header.getPreferredSize().height;
                }// fine del blocco if

                /* somma gli insets verticali della lista e dello scorrevole */
                hInsets = iListaTop + iListaBot + iScrollTop + iScrollBot;

                /* somma l'altezza del pannello al piede */
                hPanPiede = getPanPiede().getPreferredSize().height;

                /* calcola le dimensioni finali */
                wTot = wLista;
                hTot = hLista + hHeader + hInsets + hPanPiede;
                dim = new Dimension(wTot, hTot);

            } catch (Exception unErrore) {    // intercetta l'errore
                new Errore(unErrore);
            } // fine del blocco try-catch

            /* valore di ritorno */
            return dim;
        }


        public Dimension maximumLayoutSize(Container container) {
            /* variabili e costanti locali di lavoro */
            Dimension dim;
            dim = super.maximumLayoutSize(container);
            return dim;
        }


        private Lista getLista() {
            return lista;
        }


        private void setLista(Lista lista) {
            this.lista = lista;
        }
    }// fine della classe

    /**
     * Tavola dei totali.
     * </p>
     *
     * @author Guido Andrea Ceresa, Alessandro Valbonesi
     * @author alex
     * @version 1.0    / 7-feb-2005 ore 15.05.12
     */
    private final class TavolaTotali extends Tavola {

        /**
         * Costruttore completo con parametri. <br>
         *
         * @param unaLista lista proprietaria di questa tavola
         */
        public TavolaTotali(Lista unaLista) {
            super(unaLista);
        }// fine del metodo costruttore completo


        public void inizializza() {

            try { // prova ad eseguire il codice
                super.inizializza();
                this.regolaRenderers();
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }


        /**
         * Regola i renderer delle colonne.
         * <p/>
         * Assegna un renderer vuoto a tutte le colonne non totalizzabili
         */
        private void regolaRenderers() {
            /* variabili e costanti locali di lavoro */
            Enumeration colonne;
            Vista vista;
            ArrayList<Campo> listaCampi;
            ArrayList<TableColumn> listaColonne = new ArrayList<TableColumn>();
            TableColumn col;
            Campo campo;
            RendererBase rendererOld;
            RendererBase rendererNew;
            int hAlig;

            try {    // prova ad eseguire il codice

                /* recupera i campi visibili della vista totali */
                vista = this.getLista().getVista();
                listaCampi = vista.getCampiVisibili();

                /**
                 * recupera le colonne della tavola totali
                 * (dovrebbero essere sempre parallele ai campi)
                 */
                colonne = this.getColumnModel().getColumns();
                while (colonne.hasMoreElements()) {
                    Object o = colonne.nextElement();
                    if (o != null && o instanceof TableColumn) {
                        col = (TableColumn)o;
                        listaColonne.add(col);
                    }// fine del blocco if
                }

                /**
                 * Se la colonna corrisponde a un campo non totalizzabile,
                 * assegna un renderer vuoto.
                 * Se la colonna corrisponde a un campo totalizzabile,
                 * assegna un nuovo renderer numerico con lo stesso allineamento
                 * del renderer originale.
                 *
                 */
                for (int k = 0; k < listaColonne.size(); k++) {
                    col = listaColonne.get(k);
                    if (listaCampi.size() > k) {
                        campo = listaCampi.get(k);
                        if (col != null && campo != null) {
                            if (!campo.isTotalizzabile()) {
                                col.setCellRenderer(new EmptyRenderer());
                            } else {
                                rendererOld = campo.getCampoDati().getRenderer();
                                hAlig = rendererOld.getHorizontalAlignment();
                                rendererNew = new RendererNumero(campo);
                                rendererNew.setHorizontalAlignment(hAlig);
                                col.setCellRenderer(rendererNew);
                            }// fine del blocco if-else
                        }// fine del blocco if
                    }// fine del blocco if
                } // fine del ciclo for


            } catch (Exception unErrore) {    // intercetta l'errore
                new Errore(unErrore);
            } // fine del blocco try-catch

        }


        /**
         * Classe 'interna'.
         * <p/>
         * Renderer vuoto
         */
        private final class EmptyRenderer extends DefaultTableCellRenderer {

            private JLabel label = new JLabel();


            public Component getTableCellRendererComponent(
                    JTable jTable,
                    Object o,
                    boolean b,
                    boolean b1,
                    int i,
                    int i1) {
                return label;
            }
        } // fine della classe 'interna'


    }// fine della classe


}// fine della classe
