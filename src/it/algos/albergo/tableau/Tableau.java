/**
 * Title:     Tableau
 * Copyright: Copyright (c) 2009
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      24-feb-2009
 */
package it.algos.albergo.tableau;

import it.algos.albergo.AlbergoLib;
import it.algos.albergo.clientealbergo.ClienteAlbergoModulo;
import it.algos.albergo.prenotazione.PrenotazioneModulo;
import it.algos.base.errore.Errore;
import it.algos.base.font.FontFactory;
import it.algos.base.layout.Layout;
import it.algos.base.libreria.Lib;
import it.algos.base.libreria.Libreria;
import it.algos.base.modulo.OnEditingFinished;
import it.algos.base.pannello.Pannello;
import it.algos.base.pannello.PannelloBase;
import it.algos.base.pannello.PannelloFlusso;
import it.algos.base.util.jdatepicker.JDatePicker;

import java.awt.AWTEvent;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.Box;
import javax.swing.DefaultComboBoxModel;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Tabellone di visualizzazione e gestione grafica delle prenotazioni.
 * </p>
 * Contiene una toolbar con i comandi e il tabellone grafico
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 24-feb-2009 ore 9.11.17
 */
public class Tableau extends JFrame {

    
    /* datamodel per il tableau */
    private TableauDatamodel datamodel;

    /* toolbar */
    private Pannello toolbar;

    /* contenitore dei grafi */
    private PanGrafi panGrafi;

    /* slider di zooming */
    private JSlider zoomSlider;

    /* checkbox per adattare automaticamente l'altezza della pagina */
    private JCheckBox checkAdatta;

    /* slider di ampiezza settimane */
    private JSlider weekSlider;

    /* campo numero di settimane */
    private JTextField fieldSettimane;
    
    /* combo per il tipo di risorse */
    @SuppressWarnings("rawtypes")
	private JComboBox comboTipo;

    /* numero di settimane correntemente visualizzate*/
    private int numSettimane;

    /* selettore della data iniziale */
    private JDatePicker datePicker;

    /* flag - se attivata la modalità full-screen */
    private boolean fullScreenMode;

    /* bottone di attivazione e disattivazione modalità full-screen */
    private JButton fullScreenButton;

//    /* elenco di celle con i dati delle risorse */
//    private ArrayList<CellRisorsaIF> celleRisorsa;

    /* dimensione della griglia di snap */
    static final int GRID_SIZE = 11;

    /* dimensione modulo orizzontale per tutto il tableau */
    static final int H_MODULE = 22;

    /* dimensione modulo verticale per tutto il tableau */
    static final int V_MODULE = 22;

    /**
     * altezza delle celle di tipo Mese
     */
    static final int H_CELLE_MESE = 16;

    /**
     * altezza delle celle di tipo Giorno
     * (la larghezza è quella della griglia)
     */
    static final int H_CELLE_GIORNO = 30;

    /**
     * larghezza delle celle di tipo Camera
     * (l'altezza è quella della griglia)
     */
    static final int W_CELLE_CAMERA = 72;

    /* step di scala per zoom in e out */
    private static final int SCALE_STEP = 5;

    /* Font per nome giorno e nome mese nelle celle di tipo Giorno */
    static final Font FONT_GIORNI = FontFactory.creaScreenFont(10f);

    /* Font per il numero del giorno nelle celle di tipo Giorno */
    static final Font FONT_NUMERI_GIORNO = FontFactory.creaScreenFont(Font.BOLD, 14f);

    /* Font per il nome del mese nelle celle di tipo Mese */
    static final Font FONT_NOMI_MESI = FontFactory.creaScreenFont(Font.BOLD, 12F);

    /* CELLE DI PERIODO */

    /**
     * altezza delle celle di Periodo
     */
    static final int H_CELLE_PERIODO = 14;

    /* Font per il nome del cliente nelle celle di Periodo */
    static final Font FONT_NOMI_CLIENTI = FontFactory.creaScreenFont(Font.BOLD, 11f);

    /* Font per il numero della camera di provenienza e destinazione nelle celle di Periodo */
    static final Font FONT_CAMERE_CAMBIO = FontFactory.creaScreenFont(Font.BOLD, 11f);

    /* Colore di sfondo della cella se la prenotazione non è confermata ma non è ancora scaduta */
    static final Color COLORE_NON_CONFERMATO = new Color(242, 182, 2);  // giallo

    /* Colore di sfondo della cella se la prenotazione è scaduta */
    static final Color COLORE_SCADUTO = new Color(195, 41, 25);  // rosso scuro

    /* Colore di sfondo della cella se la prenotazione non è confermata ed è una opzione */
    static final Color COLORE_OPZIONE = new Color(255, 167, 157);  // rosa salmone

    /* Colore di sfondo della cella se il periodo è confermato e non è ancora arrivato */
    static final Color COLORE_CONFERMATO = new Color(3, 134, 18);  // verde scuro

    /* Colore di sfondo della cella se il periodo è arrivato e partito */
    static final Color COLORE_PARTITO = new Color(180, 180, 180);  // grigio

    /* Colore di sfondo della cella se il periodo è attualmente presente */
    static final Color COLORE_PRESENTE = new Color(30, 76, 89);  // blu scuro

    /* Colore di sfondo della cella per i nuovi periodi da tableau (celle provvisorie) */
    static final Color COLORE_NUOVO = new Color(0, 0, 200);  // blu

    /* Colore per il nome del cliente nelle celle di periodo */
    static final Color COLORE_NOME_CLIENTE = Color.white;

    /* Colore per le camere di entrata e uscita nelle celle di periodo */
    static final Color COLORE_CAMERE_CAMBIO = Color.yellow;

    /* numero di settimane di visibilità di default */
    static final int NUM_SETT_DEFAULT = 6;

    /* inizio scala slider settimane */
    static final int MIN_SLIDER_SETT = 2;

    /* fondo scala slider settimane */
    static final int MAX_SLIDER_SETT = 16;

    /* icona di attivazione modalità full-screen */
    static final Icon ICONA_ENTER_FULL_SCREEN = Lib.Risorse.getIconaBase("FullScreenEnter24");

    /* icona di disattivazione modalità full-screen */
    static final Icon ICONA_EXIT_FULL_SCREEN = Lib.Risorse.getIconaBase("FullScreenExit24");
    
    /* id del tipo di risorsa Camera */
    public static final int ID_TIPO_RISORSE_CAMERA = 0;


    
    /**
     * Costruttore completo.
     * <p/>
     * Costruisce il tableau a partire da una certa data
     * per un dato numero di settimane
     *
     * @param datamodel - datamodel per il tableau
     * @param data1 - data inizio periodo
     * @param numSettimane - numero di settimane di estensione
     */
    public Tableau(TableauDatamodel datamodel, Date data1, int numSettimane) {

        super();
        setDatamodel(datamodel);
        this.inizia(data1, numSettimane);

    }


    /**
     * Regolazioni statiche di riferimenti e variabili.
     * </p>
     * Metodo chiamato direttamente dal costruttore
     * <p/>
     *
     * @param data1        inizio periodo
     * @param numSettimane numero di settimane di estensione
     * @throws Exception unaEccezione
     */
    private void inizia(Date data1, int numSettimane) {
        /* variabili e costanti locali di lavoro */
        Pannello pan;
        Date data;

        /* titolo del dialogo */
        this.setTitle("Prospetto prenotazioni");

        pan = new PannelloFlusso(Layout.ORIENTAMENTO_VERTICALE);
        pan.setUsaGapFisso(true);
        pan.setGapPreferito(0);

        /* se la data di inizio è nulla o non valida
         * usa una data di default */
        if (Lib.Data.isValida(data1)) {
            data = data1;
        } else {
            data = Lib.Data.getCorrente();
            data = Lib.Data.add(data, -7);
        }// fine del blocco if-else

        /* regola il numero di setttimane (ancora manca la GUI) */
        if (numSettimane > 0) {
            this.setNumSettimane(numSettimane);
        } else {
            this.setNumSettimane(NUM_SETT_DEFAULT);
        }// fine del blocco if-else

        /* crea e aggiunge la toolbar */
        Pannello toolbar = this.creaToolbar();
        pan.add(toolbar);

        /**
         * assegna nuovamente il n settimane perché
         * ora esiste la GUI e quindi regola anche i componenti
         */
        this.setNumSettimane(this.getNumSettimane());

        /* regola la data di inizio */
        this.setDataInizio(data);

        /* crea il componente con i grafi */
        pan.add(this.creaPanGrafi());
        
        /* aggiunge il pannelllo principale al JFrame */
        this.add(pan.getPanFisso());

        /**
         * aggiunge un listener di resizing che limita
         * le dimensioni massime della finestra alla misura del tableau
         */
        this.addComponentListener(new AzWindowResized());

//        /* valore iniziale */
//        this.setNumSettimane(NUM_SETT_DEFAULT);

    }// fine del metodo inizia


    /**
     * Ricarica i dati nel tableau e lo rende visibile.
     * <p/>
     */
    public void avvia() {
        this.reload();
        this.setVisible(true);
    }


    /**
     * Crea e registra la toolbar.
     * <p/>
     * In realtà è un pannello orizzontale, che funziona meglio
     *
     * @return la toolbar creata
     */
    private Pannello creaToolbar() {
        /* variabili e costanti locali di lavoro */
        PannelloFlusso tbar = null;

        try {    // prova ad eseguire il codice

            tbar = new PannelloFlusso(Layout.ORIENTAMENTO_ORIZZONTALE);
            tbar.setAllineamento(Layout.ALLINEA_CENTRO);

            tbar.setGapFisso(0);

//            tbar.setGapPreferito(6);
//            tbar.setGapMassimo(8);

            this.setToolbar(tbar);

            /* margine iniziale */
            tbar.add(Box.createHorizontalStrut(10));

            /* selettore tipo risorse */
            this.comboTipo = creaComboTipo();
            tbar.add(this.comboTipo);

            /* glue */
            tbar.add(Box.createHorizontalStrut(10));  // per dist.minima
            tbar.add(Box.createHorizontalGlue());

            /* zoom panel */
            tbar.add(this.creaPanZoom());

            /* glue */
            tbar.add(Box.createHorizontalStrut(10));  // per dist.minima
            tbar.add(Box.createHorizontalGlue());

            /* print panel */
            tbar.add(this.creaPanStampa());

            /* glue */
            tbar.add(Box.createHorizontalStrut(10));  // per dist.minima
            tbar.add(Box.createHorizontalGlue());

            /* button panel */
            tbar.add(this.creaPanBottoni());

            /* glue */
            tbar.add(Box.createHorizontalStrut(10));   // per dist.minima           
            tbar.add(Box.createHorizontalGlue());

            /* range panel */
            tbar.add(this.creaPanRange());

            /* margine finale */
            tbar.add(Box.createHorizontalStrut(20));

            /* assegna l'altezza alla tooolbar */
            tbar.setPreferredHeigth(50);

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return tbar;
    }

    /**
     * Crea il combo box per il tipo di dati.
     * <p/>
     *
     * @return il combobox
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	private JComboBox creaComboTipo() {
    	TipoRisorsaTableau[] tipiRisorsa = datamodel.getTipiRisorsa();
    	
        DefaultComboBoxModel model = new DefaultComboBoxModel();
        for(TipoRisorsaTableau dao : tipiRisorsa){
            model.addElement(dao);
        }
        
        JComboBox combo = new JComboBox(model);
        combo.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange()==ItemEvent.SELECTED) {
					Object obj = e.getItem();
					if ((obj != null) && (obj instanceof TipoRisorsaTableau)) {
						getPanGrafi().reload();
					}
				}
			}
		});
        
        combo.setFocusable(false);
        combo.setPreferredSize(new Dimension(130,combo.getPreferredSize().height));
        combo.setSelectedIndex(0);
        return combo;
    }

    /**
     * Crea il pannello di zoom / full screen.
     * <p/>
     *
     * @return il pannello creato
     */
    private PannelloBase creaPanZoom() {
        /* variabili e costanti locali di lavoro */
        Pannello pan = new PannelloFlusso(Layout.ORIENTAMENTO_ORIZZONTALE);
        Icon icona;
        JButton bot;

        try { // prova ad eseguire il codice

            pan.setGapFisso(0);
            pan.setAllineamento(Layout.ALLINEA_CENTRO);

//            pan.setOpaque(true);
//            pan.setBackground(Color.yellow);


            /* crea e aggiunge il bottone full-screen */
            bot = new JButton(ICONA_ENTER_FULL_SCREEN);
            bot.setPreferredSize(new Dimension(28, 28));
            this.setFullScreenButton(bot);
            bot.addActionListener(new AzFullScreen());
            bot.setOpaque(false);
            bot.setBorderPainted(false);
            bot.setContentAreaFilled(false); // serve per bug su XP
            bot.setToolTipText("Attiva/disattiva la modalità a pieno schermo");
            pan.add(bot);

            /* spazio */
            pan.add(Box.createHorizontalStrut(15));

            /* bottone lente - */
            icona = Lib.Risorse.getIconaBase("lente24-");
            bot = new JButton(icona);
            bot.setPreferredSize(new Dimension(28, 28));
            bot.addActionListener(new AzZoomOut());
            bot.setOpaque(true);
            bot.setBorderPainted(false);
            bot.setContentAreaFilled(false); // serve per bug su XP
            bot.setToolTipText("Zoom indietro");
            pan.add(bot);

            /* crea e aggiunge lo slider */
            JSlider slider = new JSlider(JSlider.HORIZONTAL, 10, 100, 55);
            this.setZoomSlider(slider);
            slider.setPreferredSize(new Dimension(120, 30));
            slider.setFocusable(false);
            slider.addChangeListener(new AzZoomSlider());
            slider.setToolTipText("Fattore di Zoom");
            pan.add(slider);	// disabilitato x fare spazio

            /* bottone lente + */
            icona = Lib.Risorse.getIconaBase("lente24+");
            bot = new JButton(icona);
            bot.setPreferredSize(new Dimension(28, 28));
            bot.addActionListener(new AzZoomIn());
            bot.setOpaque(false);
            bot.setBorderPainted(false);
            bot.setContentAreaFilled(false); // serve per bug su XP
            bot.setToolTipText("Zoom avanti");
            pan.add(bot);

            /* spazio */
            pan.add(Box.createHorizontalStrut(15));

            /* bottone autosize + */
            icona = Lib.Risorse.getIconaBase("AutoSize24");
            bot = new JButton(icona);
            bot.setPreferredSize(new Dimension(28, 28));
            bot.addActionListener(new AzAutoSize());
            bot.setOpaque(false);
            bot.setBorderPainted(false);
            bot.setContentAreaFilled(false); // serve per bug su XP
            bot.setToolTipText("Auto zoom");
            pan.add(bot);


            pan.bloccaDim();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return pan.getPanFisso();
    }


    /**
     * Crea il pannello con i bottoni di comando.
     * <p/>
     *
     * @return il pannello con lo slider
     */
    private PannelloBase creaPanBottoni() {
        /* variabili e costanti locali di lavoro */
        PannelloFlusso pan = new PannelloFlusso(Layout.ORIENTAMENTO_ORIZZONTALE);
        Icon icona;
        JButton bot;

        try { // prova ad eseguire il codice

//            pan.setOpaque(true);
//            pan.setBackground(Color.yellow);
            pan.setGapMinimo(0);
            pan.setGapPreferito(25);
            pan.setGapMassimo(25);
            pan.setAllineamento(Layout.ALLINEA_CENTRO);

//            /* bottone Stampa */
//            icona = Lib.Risorse.getIconaBase("Print24");
//            bot = new JButton(icona);
//            bot.setPreferredSize(new Dimension(28, 28));
//            bot.addActionListener(new AzStampa());
//            bot.setOpaque(false);
//            bot.setBorderPainted(false);
//            bot.setContentAreaFilled(false); // serve per bug su XP
//            bot.setAlignmentY(0);
//            bot.setToolTipText("Stampa il tabellone");
//            pan.add(bot);

            /* bottone Ricerca Periodo */
            icona = Lib.Risorse.getIconaBase("ricerca24");
            bot = new JButton(icona);
            bot.setPreferredSize(new Dimension(28, 28));
            bot.addActionListener(new AzRicerca());
            bot.setOpaque(false);
            bot.setBorderPainted(false);
            bot.setContentAreaFilled(false); // serve per bug su XP
            bot.setAlignmentY(0);
            bot.setToolTipText("Ricerca una prenotazione");
            pan.add(bot);

            /* bottone Storico Cliente */
            bot = AlbergoLib.creaBotStorico();
            bot.setPreferredSize(new Dimension(28, 28));
            bot.addActionListener(new AzStorico());
            bot.setAlignmentY(0);
            pan.add(bot);

//            pan.bloccaDim();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return pan;
    }




    /**
     * Crea un Preventivo / Prenotazione dal Tableau
     * <p>
     * Invocato quando si rilascia il mouse dopo aver trascinato
     * un nuovo periodo nel grafo prenotazioni
     * @param codCamera codice della camera
     * @param dataInizio del periodo
     * @param dataFine del periodo
     * @return il codice del preventivo/prenotazione creato
     */
    int creaPrevePren(int codCamera, Date dataInizio, Date dataFine) {
        /* variabili e costanti locali di lavoro */
        PrenotazioneModulo modulo;
        int codCreato=0;

        try {    // prova ad eseguire il codice

            modulo = PrenotazioneModulo.get();
            if (modulo != null) {
                codCreato = modulo.nuovaPrenotazione(codCamera, dataInizio, dataFine);
                if (codCreato>0) {
                    this.reload();
                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return codCreato;
    }


    /**
     * Crea il pannello di controllo della stampa.
     * <p/>
     *
     * @return il pannello di controllo della stampa
     */
    private Pannello creaPanStampa() {
        /* variabili e costanti locali di lavoro */
        Pannello pan = null;
        Icon icona;
        JButton bot;

        try {    // prova ad eseguire il codice

            /* crea e regola il pannello */
            pan = new PannelloFlusso(Layout.ORIENTAMENTO_ORIZZONTALE);
            pan.setAllineamento(Layout.ALLINEA_CENTRO);
            pan.setGapMassimo(0);

            /* bottone Stampa */
            icona = Lib.Risorse.getIconaBase("Print24");
            bot = new JButton(icona);
            bot.setPreferredSize(new Dimension(28, 28));
            bot.addActionListener(new AzStampa());
            bot.setOpaque(false);
            bot.setBorderPainted(false);
            bot.setContentAreaFilled(false); // serve per bug su XP
            bot.setAlignmentY(0);
            bot.setToolTipText("Stampa il tabellone");
            pan.add(bot);

            /* checkbox Autoscale */
            JCheckBox cb = new JCheckBox("Adatta al foglio");
            this.setCheckAdatta(cb);
            cb.setSelected(true);
            cb.setFocusable(false);
            String tooltip = "Riduce la stampa in modo da \n"
                    + "far stare tutte le camere nell'altezza di una pagina";
            cb.setToolTipText(tooltip);
            pan.add(cb);

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return pan;
    }


    /**
     * Crea il pannello di controllo del range visualizzato.
     * <p/>
     *
     * @return il pannello con lo slider
     */
    private PannelloBase creaPanRange() {
        /* variabili e costanti locali di lavoro */
        Pannello pan = new PannelloFlusso(Layout.ORIENTAMENTO_ORIZZONTALE);
        Icon icona;
        JButton bot;
        JLabel label;

        try { // prova ad eseguire il codice

            pan.setAllineamento(Layout.ALLINEA_CENTRO);
            pan.setGapFisso(5);

            /* label "dal" */
            label = new JLabel("dal");
            pan.add(label);


            /* bottone Sposta verso Sinistra + */
            icona = Lib.Risorse.getIconaBase("Precedente24");
            bot = new JButton(icona);
            bot.setPreferredSize(new Dimension(28, 28));
            bot.addActionListener(new AzMoveLeft());
            bot.setOpaque(false);
            bot.setBorderPainted(false);
            bot.setContentAreaFilled(false); // serve per bug su XP
            bot.setToolTipText("Indietro di 1 settimana");
            pan.add(bot);

            /* date picker */
            JDatePicker picker = new JDatePicker();
            this.setDatePicker(picker);
            picker.addChangeListener(new AzDataModificata());
            Lib.Comp.bloccaDim(picker);
            pan.add(picker);

            /* bottone Sposta verso Destra + */
            icona = Lib.Risorse.getIconaBase("Successivo24");
            bot = new JButton(icona);
            bot.setPreferredSize(new Dimension(28, 28));
            bot.addActionListener(new AzMoveRight());
            bot.setOpaque(false);
            bot.setBorderPainted(false);
            bot.setContentAreaFilled(false); // serve per bug su XP
            bot.setToolTipText("Avanti di 1 settimana");
            pan.add(bot);


            /* slider settimane */
            int numset = this.getNumSettimane();
            JSlider slider = new JSlider(JSlider.HORIZONTAL, MIN_SLIDER_SETT, MAX_SLIDER_SETT, numset);
            this.setWeekSlider(slider);
            slider.setPreferredSize(new Dimension(120, 30));
            slider.setFocusable(false);
            slider.setMajorTickSpacing(1);
            slider.setPaintTicks(true);
            slider.setSnapToTicks(true);
            slider.addChangeListener(new AzWeekSlider());
            slider.setToolTipText("Numero di settimane visualizzate");
            pan.add(slider);

            /* campo editabile numero di settimane */
            JTextField field = new JTextField(2);
            this.setFieldSettimane(field);
            field.addActionListener(new AzEditNumSettimane());   // ascolta la modifica
            field.addFocusListener(new AzEditFocus());           // ascolta la perdita di fuoco
            Lib.Comp.bloccaDim(field);
            pan.add(field);

            pan.add(Box.createHorizontalStrut(5));

            /* label "settimane" */
            label = new JLabel("settimane");
            pan.add(label);

            /* bottone reload */
            icona = Lib.Risorse.getIconaBase("Refresh24");
            bot = new JButton(icona);
            bot.setPreferredSize(new Dimension(28, 28));
            bot.addActionListener(new AzReload());
            bot.setOpaque(false);
            bot.setBorderPainted(false);
            bot.setContentAreaFilled(false); // serve per bug su XP
            bot.setMargin(new Insets(5, 5, 5, 5));
            bot.setToolTipText("Ricarica i dati nel tabellone");
            pan.add(bot);

            pan.bloccaDim();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return pan.getPanFisso();
    }


    /**
     * Crea e registra il componente grafico.
     * <p/>
     *
     * @return il componente grafico creato
     */
    private PanGrafi creaPanGrafi() {
        /* variabili e costanti locali di lavoro */
        PanGrafi comp = null;

        try {    // prova ad eseguire il codice
            comp = new PanGrafi(this, this.getDataInizio(), this.getData2());
            this.setPanGrafi(comp);
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return comp;
    }


    /**
     * Ritorna la data di fine.
     * <p/>
     *
     * @return la data di fine
     */
    private Date getData2() {
        /* variabili e costanti locali di lavoro */
        Date dataFine = null;
        Date dataInizio;
        int quantiGiorni;

        try { // prova ad eseguire il codice
            dataInizio = this.getDataInizio();
            quantiGiorni = this.getNumSettimane() * 7;
            dataFine = Lib.Data.add(dataInizio, quantiGiorni - 1);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return dataFine;
    }


    /**
     * Ricarica il tableau in base al range corrente.
     * <p/>
     */
    private void reload() {
        /* variabili e costanti locali di lavoro */
        PanGrafi panGrafi;
        Date dataInizio, dataFine;

        try {    // prova ad eseguire il codice

            dataInizio = this.getDataInizio();
            dataFine = this.getData2();
            panGrafi = this.getPanGrafi();
            panGrafi.setData1(dataInizio);
            panGrafi.setData2(dataFine);
            panGrafi.reload();

            this.packTableau();

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * E' stato modificato il valore nel campo numero settimane.
     * <p/>
     *
     * @param event l'evento di modifica
     */
    private void numSettimaneEdit(AWTEvent event) {
        try {    // prova ad eseguire il codice
            Object source = event.getSource();
            if (source instanceof JTextField) {
                JTextField field = (JTextField) source;
                String text = field.getText();
                int num = Libreria.getInt(text);

                /* regola lo slider restando dentro al suo range */
                int numSlider = 0;
                if (num < 1) {
                    numSlider = NUM_SETT_DEFAULT;
                }// fine del blocco if
                if (num > MAX_SLIDER_SETT) {
                    numSlider = MAX_SLIDER_SETT;
                }// fine del blocco if
//                getWeekSlider().setValue(numSlider);

                /* assegna il numero di settimane */
                setNumSettimane(num);

            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Ridimensiona il dialogo tale che sia grande quanto il suo contenuto
     * e non sia più grande dell'area utile dello schermo.
     * <p/>
     */
    public void packTableau() {
        /* variabili e costanti locali di lavoro */

        try {    // prova ad eseguire il codice


            /**
             *  adatta la finestra al contenuto
             * (cambia la dimensione della finestra in base al contenuto)
             */
            this.pack();

            /* forza a starci comunque nello schermo */
            if (!this.isFullScreenMode()) {
                Lib.Gui.fitWindowToScreen(this);
            } else {
                Lib.Gui.fitWindowToFullScreen(this);
            }// fine del blocco if-else


        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

    }


    /**
     * Entra in modalità full-screen.
     * <p/>
     */
    public void enterFullScreen() {

        try {    // prova ad eseguire il codice

            if (!this.isFullScreenMode()) {
                GraphicsDevice device = this.getUniqueDevice();
                if (device != null) {

                    this.dispose();  // Releases all of the native screen resources
                    this.setUndecorated(true);
                    this.setVisible(true);
                    device.setFullScreenWindow(this);

                    this.getFullScreenButton().setIcon(ICONA_EXIT_FULL_SCREEN);
                    this.setFullScreenMode(true);

                }// fine del blocco if
            }// fine del blocco if


        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Esce dalla modalità full-screen.
     * <p/>
     */
    public void exitFullScreen() {

        try {    // prova ad eseguire il codice

            if (this.isFullScreenMode()) {
                GraphicsDevice device = this.getUniqueDevice();
                if (device != null) {

                    this.dispose();   // Releases all of the native screen resources
                    this.setUndecorated(false);
                    this.setResizable(true);
                    this.setVisible(true);
                    device.setFullScreenWindow(null);

                    this.getFullScreenButton().setIcon(ICONA_ENTER_FULL_SCREEN);
                    this.setFullScreenMode(false);

                    this.packTableau();

                }// fine del blocco if
            }// fine del blocco if


        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Ritorna l'unico screen device di questo computer.
     * <p/>
     * Se ne ha più di 1 ritorna null
     *
     * @return l'unico screen device di questo computer
     */
    private GraphicsDevice getUniqueDevice() {
        GraphicsDevice device = null;

        try {    // prova ad eseguire il codice
            GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
            GraphicsDevice[] devices = env.getScreenDevices();
            if (devices != null) {
                if (devices.length == 1) {
                    device = devices[0];
                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return device;
    }


    /**
     * Ritorna la lista delle celle di periodo da inserire nel grafo.
     * <p/>
     * Chiede al fornitore registrato.
     *
     * @return la lista di celle fornita
     */
    public ArrayList<CellPeriodoIF> getCellePeriodo() {
        /* variabili e costanti locali di lavoro */
        ArrayList<CellPeriodoIF> lista = null;
        Date d1, d2;

        try { // prova ad eseguire il codice
            d1 = this.getDataInizio();
            d2 = this.getData2();
            lista = datamodel.getCellePeriodo(getTipoRisorseSelezionato().getId(), d1, d2);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return lista;
    }


    /**
     * Ritorna la cella di periodo relativa a un dato periodo.
     * <p/>
     * Chiede al fornitore di celle registrato.
     *
     * @param idTipoRisorsa id del tipo di risorsa
     * @param idRecordSorgente id del record sorgente
     * @return la cella del periodo
     */
    public CellPeriodoIF getCellaPeriodo(int idTipoRisorsa, int idRecordSorgente) {
        return this.datamodel.getCellaPeriodo(idTipoRisorsa, idRecordSorgente);
    }

    
    /**
     * Ritorna il tipo di risorse selezionato nel popup
     * @return il tipo di risorse selezionato
     */
    public TipoRisorsaTableau getTipoRisorseSelezionato(){
    	TipoRisorsaTableau tipo=null;
    	Object obj = this.comboTipo.getSelectedItem();
    	if ((obj != null) && (obj instanceof TipoRisorsaTableau)) {
    		tipo = (TipoRisorsaTableau)obj;
		}
    	
    	return tipo;

    }
    
    
    /**
     * Ritorna l'elenco di tutti gli id di record sorgenti di cella contenuti
     * in una data prenotazione.
     * <p/>
     * Nel caso di prenotazione di camere, ritorna gli id di periodo
     * Nel caso di prenotazione di risorse, ritorna gli id di RisorsaPeriodo di tutti i periodi
     * ecc...
     *
     * @param idPrenotazione l'id della prenotazione
     * @return l'array con tutti gli id di record sorgenti contenuti nella prenotazione
     */
    public int[] getIdRecordSorgenti(int idPrenotazione) {
        return this.datamodel.getIdRecordSorgenti(idPrenotazione, getTipoRisorseSelezionato().getId());
    }


    /**
     * Apre per modifica una prenotazione.
     * <p/>
     *
     * @param codPeriodo il codice della prenotazione
     * @return true se la prenotazione è stata modificata
     */
    public void apriPrenotazione(int codPrenotazione, OnEditingFinished listener) {
        datamodel.apriPrenotazione(codPrenotazione, listener);
    }


    /**
     * Stampa il tableau.
     * <p/>
     */
    private void stampa() {
        if (!this.isFullScreenMode()) {
            this.getPanGrafi().stampa();
        } else {
            Lib.Sist.beep();
        }// fine del blocco if-else
    }


    /**
     * .
     * <p/>
     */
    private void ricerca() {
        /* variabili e costanti locali di lavoro */

        try {    // prova ad eseguire il codice
            JDialog dialogo = new JDialog(this);
            dialogo.setVisible(true);
//            Dialogo dialogo = new DialogoBase();
//            dialogo.getPortale().getFinestra();
//
//            dialogo.avvia();
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

    }


    /**
     * Apertura dello storico del cliente selezionato.
     * <p/>
     */
    private void storico() {
        /* variabili e costanti locali di lavoro */
    	CellPeriodoIF cella;
        int codCliente;

        try {    // prova ad eseguire il codice
            cella = this.getPanGrafi().getGrafoPrenotazioni().getCellaSelezionata();
            if (cella != null) {
                int codPeriodo = cella.getUO().getCodPeriodo();
                if (codPeriodo > 0) {
                    codCliente = datamodel.getCodCliente(codPeriodo);
                    if (codCliente > 0) {
                        ClienteAlbergoModulo.showStorico(codCliente);
                    }// fine del blocco if
                }// fine del blocco if
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

    }

    /**
     * @return true se il checkbox "Adatta a pagina" è spuntato.
     *         <p/>
     */
    boolean isAdatta() {
        return this.getCheckAdatta().isSelected();
    }


	public TableauDatamodel getDatamodel() {
		return datamodel;
	}


	public void setDatamodel(TableauDatamodel datamodel) {
		this.datamodel = datamodel;
	}


	private Pannello getToolbar() {
        return toolbar;
    }


    private void setToolbar(Pannello toolbar) {
        this.toolbar = toolbar;
    }


    private PanGrafi getPanGrafi() {
        return panGrafi;
    }


    private void setPanGrafi(PanGrafi panGrafi) {
        this.panGrafi = panGrafi;
    }


    private JSlider getZoomSlider() {
        return zoomSlider;
    }


    private void setZoomSlider(JSlider zoomSlider) {
        this.zoomSlider = zoomSlider;
    }

    private JCheckBox getCheckAdatta() {
        return checkAdatta;
    }

    private void setCheckAdatta(JCheckBox checkAdatta) {
        this.checkAdatta = checkAdatta;
    }

    private JSlider getWeekSlider() {
        return weekSlider;
    }


    private void setWeekSlider(JSlider weekSlider) {
        this.weekSlider = weekSlider;
    }


    private JTextField getFieldSettimane() {
        return fieldSettimane;
    }


    private void setFieldSettimane(JTextField fieldSettimane) {
        this.fieldSettimane = fieldSettimane;
    }


    private JDatePicker getDatePicker() {
        return datePicker;
    }


    private void setDatePicker(JDatePicker datePicker) {
        this.datePicker = datePicker;
    }


    private int getNumSettimane() {
        return numSettimane;
    }


    private boolean isFullScreenMode() {
        return fullScreenMode;
    }


    private void setFullScreenMode(boolean fullScreenMode) {
        this.fullScreenMode = fullScreenMode;
    }


    private JButton getFullScreenButton() {
        return fullScreenButton;
    }


    private void setFullScreenButton(JButton fullScreenButton) {
        this.fullScreenButton = fullScreenButton;
    }


    public ArrayList<CellRisorsaIF> getCelleRisorsa() {
    	TipoRisorsaTableau tipo = getTipoRisorseSelezionato();
    	return datamodel.getCelleRisorsa(tipo.getId());
    }


//    public void setCelleRisorsa(ArrayList<CellRisorsaIF> celle) {
//        this.celleRisorsa = celle;
//    }


    /**
     * Ritorna la data di inizio.
     * <p/>
     *
     * @return la data di inizio
     */
    private Date getDataInizio() {
        return this.getDatePicker().getDate();
    }


    /**
     * Assegna la data iniziale al tableau.
     * <p/>
     *
     * @param data la data di inizio della visualizzazione
     */
    private void setDataInizio(Date data) {
        /* variabili e costanti locali di lavoro */
        JDatePicker picker;

        try { // prova ad eseguire il codice

            picker = this.getDatePicker();
            if (picker != null) {
                picker.setDate(data);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Assegna il numero di settimane da visualizzare.
     * <p/>
     *
     * @param numSettimane il numero di settimane
     */
    private void setNumSettimane(int numSettimane) {
        /* variabili e costanti locali di lavoro */
        JTextField field;

        try { // prova ad eseguire il codice
            this.numSettimane = numSettimane;

            /* Aggiorna la GUI se esiste già */
            field = this.getFieldSettimane();
            if (field != null) {
                field.setText("" + numSettimane);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Azione bottone reload
     * </p>
     */
    private final class AzReload implements ActionListener {

        public void actionPerformed(ActionEvent actionEvent) {
            reload();
        }
    } // fine della classe 'interna'


    /**
     * Azione bottone Zoom In
     * </p>
     */
    private final class AzZoomIn implements ActionListener {

        public void actionPerformed(ActionEvent actionEvent) {
            int currVal = getZoomSlider().getValue();
            int newVal = currVal + SCALE_STEP;
            getZoomSlider().setValue(newVal);
        }
    } // fine della classe 'interna'


    /**
     * Azione bottone Zoom Out
     * </p>
     */
    private final class AzZoomOut implements ActionListener {

        public void actionPerformed(ActionEvent actionEvent) {
            int currVal = getZoomSlider().getValue();
            int newVal = currVal - SCALE_STEP;
            getZoomSlider().setValue(newVal);
        }
    } // fine della classe 'interna'


    /**
     * Azione bottone Auto Zoom
     * </p>
     */
    private final class AzAutoSize implements ActionListener {
        /* variabili e costanti locali di lavoro */


        public void actionPerformed(ActionEvent actionEvent) {
            double scale = getPanGrafi().getBestScale();
            getPanGrafi().setScale(scale);
//            getZoomSlider().setValue((int)scale);
        }
    } // fine della classe 'interna'


    /**
     * Azione slider di zoom
     * </p>
     */
    private final class AzZoomSlider implements ChangeListener {

        public void stateChanged(ChangeEvent changeEvent) {

            JSlider source = (JSlider) changeEvent.getSource();
            if (!source.getValueIsAdjusting()) {
                int intVal = source.getValue();
                double dVal = intVal / 55d;
                getPanGrafi().setScale(dVal);

                /**
                 * se dopo l'operazione il contenuto è più piccolo
                 * del contenitrore, effetta il pack del dialogo
                 * (non in modalità full-screen)
                 */
                if (!isFullScreenMode()) {
                    if (getPanGrafi().isUnderSize()) {
                        pack();
                    }// fine del blocco if
                }// fine del blocco if

            }

        }
    } // fine della classe 'interna'


    /**
     * Azione Stampa
     * </p>
     */
    private final class AzStampa implements ActionListener {

        public void actionPerformed(ActionEvent actionEvent) {
            stampa();
        }
    } // fine della classe 'interna'


    /**
     * Azione Stampa
     * </p>
     */
    private final class AzRicerca implements ActionListener {

        public void actionPerformed(ActionEvent actionEvent) {
            ricerca();
        }
    } // fine della classe 'interna'


    /**
     * Azione Storico
     * </p>
     */
    private final class AzStorico implements ActionListener {

        public void actionPerformed(ActionEvent actionEvent) {
            storico();
        }
    } // fine della classe 'interna'


    /**
     * Azione slider di ampiezza settimane
     * </p>
     */
    private final class AzWeekSlider implements ChangeListener {

        public void stateChanged(ChangeEvent changeEvent) {

            JSlider source = (JSlider) changeEvent.getSource();
            if (!source.getValueIsAdjusting()) {
                int intVal = source.getValue();
                setNumSettimane(intVal);
            }

        }
    } // fine della classe 'interna'


    /**
     * Azione campo numero settimane modificato
     * </p>
     */
    private final class AzEditNumSettimane implements ActionListener {

        public void actionPerformed(ActionEvent actionEvent) {
            numSettimaneEdit(actionEvent);
        }
    } // fine della classe 'interna'


    /**
     * Azione campo numero settimane perde il fuoco
     * </p>
     */
    private final class AzEditFocus implements FocusListener {

        public void focusGained(FocusEvent focusEvent) {
        }


        public void focusLost(FocusEvent focusEvent) {
            if (!focusEvent.isTemporary()) {
                numSettimaneEdit(focusEvent);
            }// fine del blocco if
        }

    } // fine della classe 'interna'


    /**
     * Azione bottone Muovi verso Sinistra
     * </p>
     */
    private final class AzMoveLeft implements ActionListener {

        public void actionPerformed(ActionEvent actionEvent) {
            Date data = getDataInizio();
            data = Lib.Data.add(data, -7);
            setDataInizio(data);
        }
    } // fine della classe 'interna'


    /**
     * Azione bottone Muovi verso Destra
     * </p>
     */
    private final class AzMoveRight implements ActionListener {

        public void actionPerformed(ActionEvent actionEvent) {
            Date data = getDataInizio();
            data = Lib.Data.add(data, 7);
            setDataInizio(data);
        }
    } // fine della classe 'interna'


    /**
     * Azione picker data iniziale modificato
     * </p>
     */
    private final class AzDataModificata implements ChangeListener {

        public void stateChanged(ChangeEvent changeEvent) {
//            reload();
            int a = 87;
        }
    } // fine della classe 'interna'


    /**
     * Azione finestra ridimensionata
     * </p>
     * Limita la dimensione massima della finestra
     */
    private class AzWindowResized extends ComponentAdapter {

        @Override
        public void componentResized(ComponentEvent componentEvent) {
            /* variabili e costanti locali di lavoro */
            Object source;

            try { // prova ad eseguire il codice

                source = componentEvent.getSource();
                if (source instanceof Component) {

                    Component comp = (Component) source;

                    if (comp instanceof Tableau) {

                        Tableau tb = (Tableau) comp;

//                        tb.packTableau();

                    }// fine del blocco if


                }// fine del blocco if
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }
    } // fine della classe 'interna'


    /**
     * Azione bottone full screen
     * </p>
     */
    private final class AzFullScreen implements ActionListener {

        public void actionPerformed(ActionEvent actionEvent) {
            if (isFullScreenMode()) {
                exitFullScreen();
            } else {
                enterFullScreen();
            }// fine del blocco if-else
        }
    } // fine della classe 'interna'


}// fine della classe
