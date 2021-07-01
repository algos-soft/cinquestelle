package it.algos.albergo.stampeobbligatorie.istat;

import com.wildcrest.j2printerworks.J2PanelPrinter;
import it.algos.albergo.stampeobbligatorie.testastampe.TestaStampe;
import it.algos.albergo.stampeobbligatorie.testastampe.TestaStampeModulo;
import it.algos.albergo.tabelle.azienda.AziendaModulo;
import it.algos.base.campo.base.Campo;
import it.algos.base.campo.base.CampoFactory;
import it.algos.base.database.dati.Dati;
import it.algos.base.database.memoria.dati.DatiMemoria;
import it.algos.base.errore.Errore;
import it.algos.base.font.FontFactory;
import it.algos.base.layout.Layout;
import it.algos.base.libreria.Lib;
import it.algos.base.libreria.Libreria;
import it.algos.base.messaggio.MessaggioAvviso;
import it.algos.base.modulo.Modulo;
import it.algos.base.pannello.Pannello;
import it.algos.base.pannello.PannelloFactory;
import it.algos.base.query.Query;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.query.filtro.FiltroFactory;
import it.algos.base.query.selezione.QuerySelezione;
import it.algos.gestione.indirizzo.tabelle.nazione.Nazione;
import it.algos.gestione.indirizzo.tabelle.nazione.NazioneModulo;
import it.algos.gestione.indirizzo.tabelle.provincia.Provincia;
import it.algos.gestione.indirizzo.tabelle.provincia.ProvinciaModulo;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import javax.swing.text.TabSet;
import javax.swing.text.TabStop;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Date;

/**
 * Modello dati di una Stampa Modulo ISTAT C/59.
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 9-ott-2008 ore 16.21.46
 */
public class ModuloIstatC59 extends J2PanelPrinter {

    /**
     * codice del record ISTAT di testa dal quale recuperare i dati
     */
    private int codTesta;

    /**
     * Dati letti dal database e tenuti in memoria
     */
    private int numDoc;

    private Date dataDoc;

    private String comune;

    private String denominazione;

    private int numStelle;

    private int numGiornoPrec;

    private int numArrivati;

    private int numPartiti;


    /**
     * Costruttore completo senza parametri.
     * <p/>
     *
     * @param codNotifica codice del record ISTAT di testa
     */
    public ModuloIstatC59(int codNotifica) {
        /* rimanda al costruttore della superclasse */
        super();

        try { // prova ad eseguire il codice

            this.setCodTesta(codNotifica);

            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo costruttore completo


    /**
     * Regolazioni iniziali di riferimenti e variabili.
     * </p>
     * Metodo invocato direttamente dal
     * costruttore (init) <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        /* variabili e costanti locali di lavoro */
        JPanel pannello;

        try {    // prova ad eseguire il codice

            this.setHorizontalAlignment(CENTER);
            this.setPageRule(SHRINK_TO_WIDTH);

            this.leggiDatabase();

            pannello = this.creaPan();

            this.setPanel(pannello);


        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }// fine del metodo inizia


    /**
     * Legge il database e memorizza i dati di testa nelle variabili di istanza.
     * <p/>
     */
    private void leggiDatabase() {
        /* variabili e costanti locali di lavoro */
        int codTesta;
        Modulo modTesta;
        Query query;
        Filtro filtro;
        Dati dati;

        int progressivo;
        Date data;
        int presPrec;
        int numArrivati;
        int numPartiti;

        String comune;
        String denominazione;
        int numStelle;

        try {    // prova ad eseguire il codice

            /* legge i dati di testa dal database */
            codTesta = this.getCodTesta();
            modTesta = TestaStampeModulo.get();

            query = new QuerySelezione(modTesta);
            query.addCampo(TestaStampe.Cam.progressivo.get());
            query.addCampo(TestaStampe.Cam.data.get());
            query.addCampo(TestaStampe.Cam.presPrec.get());
            query.addCampo(TestaStampe.Cam.numArrivati.get());
            query.addCampo(TestaStampe.Cam.numPartiti.get());
            filtro = FiltroFactory.codice(modTesta, codTesta);
            query.setFiltro(filtro);

            dati = modTesta.query().querySelezione(query);
            progressivo = dati.getIntAt(TestaStampe.Cam.progressivo.get());
            data = dati.getDataAt(TestaStampe.Cam.data.get());
            presPrec = dati.getIntAt(TestaStampe.Cam.presPrec.get());
            numArrivati = dati.getIntAt(TestaStampe.Cam.numArrivati.get());
            numPartiti = dati.getIntAt(TestaStampe.Cam.numPartiti.get());
            dati.close();

            /* scrive i dati di testa nelle variabili di istanza */
            this.setNumDoc(progressivo);
            this.setDataDoc(data);
            this.setNumGiornoPrec(presPrec);
            this.setNumArrivati(numArrivati);
            this.setNumPartiti(numPartiti);

            /* legge i dati dell'azienda dal database */
            comune = AziendaModulo.getLocalita(this.getCodAzienda());
            if (Lib.Testo.isVuota(comune)) {
                new MessaggioAvviso(
                        "Attenzione! Inserire la località dell'azienda in Tabella Aziende!");
            }// fine del blocco if

            denominazione = AziendaModulo.getRagioneSociale(this.getCodAzienda());
            if (Lib.Testo.isVuota(denominazione)) {
                new MessaggioAvviso(
                        "Attenzione! Inserire la denominazione dell'azienda in Tabella Aziende!");
            }// fine del blocco if

            numStelle = AziendaModulo.getNumeroStelle(this.getCodAzienda());
            if (numStelle <= 0) {
                new MessaggioAvviso(
                        "Attenzione! Inserire il numero di stelle dell'azienda in Tabella Aziende!");
            }// fine del blocco if

            /* scrive i dati dell'azienda nelle variabili di istanza */
            this.setComune(comune);
            this.setDenominazione(denominazione);
            this.setNumStelle(numStelle);

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Pannello principale.
     *
     * @return pannello grafico da inserire nello stampabile
     */
    private JPanel creaPan() {
        /* variabili e costanti locali di lavoro */
        JPanel jPanel = null;
        Pannello pan;
        JPanel unPan;

        try { // prova ad eseguire il codice

            pan = PannelloFactory.verticale(null);
            pan.setUsaGapFisso(true);
            pan.setGapPreferito(10);
            pan.setOpaque(true);
            pan.setBackground(Color.green);

            unPan = this.creaPanTesta();
            pan.add(unPan);
            unPan = this.creaPanCorpo();
            pan.add(unPan);
            unPan = this.creaPanTables();
            pan.add(unPan);

            jPanel = pan.getPanFisso();
            Lib.Comp.setPreferredWidth(jPanel, 550);


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return jPanel;
    }


    /**
     * Crea il pannello per l'area di testa.
     * <p/>
     *
     * @return il pannello
     */
    private JPanel creaPanTesta() {
        /* variabili e costanti locali di lavoro */
        JPanel jpan = null;
        Pannello pan;
        Pannello panTesti;
        JLabel label;
        String data;
        String numero;

        try {    // prova ad eseguire il codice

            panTesti = PannelloFactory.verticale(null);
            panTesti.setAllineamento(Layout.ALLINEA_CENTRO);
            panTesti.setUsaGapFisso(true);
            panTesti.setGapPreferito(2);

            label = this.creaLabelTesta("ISTITUTO NAZIONALE DI STATISTICA");
            panTesti.add(label);

            label = this.creaLabelTesta("ENTE NAZIONALE ITALIANO PER IL TURISMO");
            label.setFont(FontFactory.creaPrinterFont(Font.PLAIN, 8));
            panTesti.add(label);

            panTesti.add(creaGap(6));

            label = this.creaLabelTesta(
                    "RILEVAZIONE DEL MOVIMENTO DEI CLIENTI NEGLI ESERCIZI RICETTIVI");
            panTesti.add(label);

            label = this.creaLabelTesta("MOD. ISTAT C/59");
            panTesti.add(label);

            panTesti.add(creaGap(6));

            data = this.getStringaDataDoc();
            numero = this.getStringaNumeroDoc();
            label = this.creaLabelTesta("DATA: " + data + "  -  N. PROGR.: " + numero);
            panTesti.add(label);

            /* pannello orizzontale filler-testi-filler */
            pan = PannelloFactory.orizzontale(null);
            pan.setAllineamento(Layout.ALLINEA_ALTO);
            pan.setUsaGapFisso(true);
            pan.setGapPreferito(0);
            pan.creaBordo();

            pan.add(Lib.Comp.createHorizontalFiller(1000));
            pan.add(panTesti);
            pan.add(Lib.Comp.createHorizontalFiller(1000));

            jpan = pan.getPanFisso();


        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return jpan;
    }


    /**
     * Crea una JLabel per il pannello di testa.
     * <p/>
     *
     * @param testo per la JLabel
     *
     * @return la JLabel creata
     */
    private JLabel creaLabelTesta(String testo) {
        /* variabili e costanti locali di lavoro */
        JLabel label = null;

        try {    // prova ad eseguire il codice
            label = new JLabel(testo);
            label.setFont(FontFactory.creaPrinterFont(Font.BOLD, 10));
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return label;
    }


    /**
     * Crea il pannello per l'area di corpo.
     * <p/>
     *
     * @return il pannello
     */
    private JPanel creaPanCorpo() {
        /* variabili e costanti locali di lavoro */
        JPanel jpan = null;
        Pannello pan;

        try {    // prova ad eseguire il codice

            pan = PannelloFactory.orizzontale(null);
            pan.setAllineamento(Layout.ALLINEA_ALTO);
            pan.setUsaGapFisso(true);
            pan.setGapPreferito(0);
            pan.creaBordo();

            pan.add(this.creaPanDati());
            pan.add(Lib.Comp.createHorizontalFiller(0, 5, 1000));
            pan.add(this.creaPanTotali());

            jpan = pan.getPanFisso();

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return jpan;
    }


    /**
     * Crea il pannello con i dati (comune, tipo esercizio, denominazione...).
     * <p/>
     *
     * @return il pannello creato
     */
    private JPanel creaPanDati() {
        /* variabili e costanti locali di lavoro */
        JPanel jpan = null;
        Pannello pan;
        JTextPane textPane;

        try {    // prova ad eseguire il codice

            pan = PannelloFactory.verticale(null);
            pan.setAllineamento(Layout.ALLINEA_SX);
            pan.setUsaGapFisso(true);
            pan.setGapPreferito(0);

            textPane = this.creaRigaDati("Comune", this.getComune());
            pan.add(textPane);

            textPane = this.creaRigaDati("Tipo di esercizio", this.getTipoEsercizio());
            pan.add(textPane);

            textPane = this.creaRigaDati("Denominazione", this.getDenominazione());
            pan.add(textPane);

            textPane = this.creaRigaDati("Numero stelle", this.getStringaNumStelle());
            pan.add(textPane);

            jpan = pan.getPanFisso();

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return jpan;
    }


    /**
     * Crea una riga della tabella Dati.
     * <p/>
     *
     * @param label etichetta della riga
     * @param testo testo della riga
     *
     * @return la riga creata
     */
    private JTextPane creaRigaDati(String label, String testo) {
        /* variabili e costanti locali di lavoro */
        JTextPane textPane = null;
        StyledDocument doc;
        Style styleLabel;
        Style styleTesto;

        try {
            // Get the text pane's document
            textPane = new JTextPane();
            doc = (StyledDocument)textPane.getDocument();

            // Crea lo stile per la label
            styleLabel = doc.addStyle("StyleLabel", null);
            StyleConstants.setBold(styleLabel, true);
            StyleConstants.setFontFamily(styleLabel, "SansSerif");
            StyleConstants.setFontSize(styleLabel, 8);

            // Crea lo stile per il testo
            styleTesto = doc.addStyle("StyleTesto", null);
            StyleConstants.setBold(styleTesto, false);
            StyleConstants.setFontFamily(styleTesto, "SansSerif");
            StyleConstants.setFontSize(styleTesto, 10);

            // Add the tab set to the logical style;
            // the logical style is inherited by all paragraphs
            ArrayList<TabStop> list = new ArrayList<TabStop>();
            float pos = 100;
            int align = TabStop.ALIGN_LEFT;
            int leader = TabStop.LEAD_NONE;
            TabStop tstop = new TabStop(pos, align, leader);
            list.add(tstop);

            TabStop[] tstops = list.toArray(new TabStop[0]);
            TabSet tabs = new TabSet(tstops);

            Style style = textPane.getLogicalStyle();
            StyleConstants.setTabSet(style, tabs);
            textPane.setLogicalStyle(style);

            // Append to document
            doc.insertString(doc.getLength(), label, styleLabel);
            doc.insertString(doc.getLength(), "\t" + testo, styleTesto);


        } catch (BadLocationException e) {
            int a = 87;
        }

        /* valore di ritorno */
        return textPane;
    }


    /**
     * Crea il pannello con i totali
     * <p/>
     *
     * @return il pannello creato
     */
    private JPanel creaPanTotali() {
        /* variabili e costanti locali di lavoro */
        JPanel jpan = null;
        Pannello pan;
        JPanel panRiga;

        int numGiornoPrec;
        int numArrivati;
        int numTotale;
        int numPartiti;
        int numPresNotte;

        try {    // prova ad eseguire il codice

            numGiornoPrec = this.getNumGiornoPrec();
            numArrivati = this.getNumArrivati();
            numTotale = numGiornoPrec + numArrivati;
            numPartiti = this.getNumPartiti();
            numPresNotte = numTotale - numPartiti;

            pan = PannelloFactory.verticale(null);
            pan.setAllineamento(Layout.ALLINEA_SX);
            pan.setUsaGapFisso(true);
            pan.setGapPreferito(5);

            panRiga = this.creaRigaTotale("Clienti del giorno precedente", numGiornoPrec, true);
            pan.add(panRiga);

            panRiga = this.creaRigaTotale("Arrivati", numArrivati, false);
            pan.add(panRiga);

            panRiga = this.creaRigaTotale("Totale", numTotale, true);
            pan.add(panRiga);

            panRiga = this.creaRigaTotale("Partiti", numPartiti, false);
            pan.add(panRiga);

            panRiga = this.creaRigaTotale("Presenti nella notte", numPresNotte, true);
            pan.add(panRiga);

            jpan = pan.getPanFisso();

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return jpan;
    }


    /**
     * Crea un pannello - riga per un singolo totale.
     * <p/>
     *
     * @param titolo testo dell'etichetta
     * @param numero numero nel riquadro
     * @param bold true per testi e numero in grassetto
     *
     * @return il pannello-riga
     */
    private JPanel creaRigaTotale(String titolo, int numero, boolean bold) {
        /* variabili e costanti locali di lavoro */
        JPanel panOut = null;
        Pannello panTot;
        JLabel label;
        JLabel campo;
        Font font;
        int wLabel = 150;
        int wCampo = 50;

        try {    // prova ad eseguire il codice

            panTot = PannelloFactory.orizzontale(null);
            panTot.setUsaGapFisso(true);
            panTot.setGapPreferito(0);

            label = new JLabel(titolo);
            campo = new JLabel("" + numero);

            campo.setHorizontalAlignment(SwingConstants.CENTER);

            if (bold) {
                font = FontFactory.creaPrinterFont(Font.BOLD, 9f);
            } else {
                font = FontFactory.creaPrinterFont(9f);
            }// fine del blocco if-else

            label.setFont(font);
            campo.setFont(font);

            campo.setBorder(BorderFactory.createLineBorder(Color.BLACK));

            Lib.Comp.setPreferredWidth(label, wLabel);
            Lib.Comp.bloccaDim(label);

            Lib.Comp.setPreferredWidth(campo, wCampo);
            Lib.Comp.bloccaDim(campo);

            panTot.add(label);
            panTot.add(campo);

            panOut = panTot.getPanFisso();

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return panOut;
    }


    /**
     * Crea il pannello per l'area di tabelle.
     * <p/>
     *
     * @return il pannello
     */
    private JPanel creaPanTables() {
        /* variabili e costanti locali di lavoro */
        JPanel jpan = null;
        Pannello panTot;
        Pannello panTitolo;
        Pannello panTabelle;
        JLabel labelTitolo;

        try {    // prova ad eseguire il codice

            /* pannello del titolo */
            /* crea il pannello con titolo */
            panTitolo = PannelloFactory.orizzontale(null);
            panTitolo.setUsaGapFisso(true);
            panTitolo.setGapPreferito(0);
            labelTitolo = new JLabel("CLIENTI DEL GIORNO");
            labelTitolo.setFont(FontFactory.creaPrinterFont(Font.BOLD, 9));
            panTitolo.add(Lib.Comp.createHorizontalFiller(1000));
            panTitolo.add(labelTitolo);
            panTitolo.add(Lib.Comp.createHorizontalFiller(1000));

            /* pannello con le due tabelle affiancate */
            panTabelle = PannelloFactory.orizzontale(null);
            panTabelle.setGapMinimo(20);
            panTabelle.setGapPreferito(20);
            panTabelle.setGapMassimo(1000);
            panTabelle.add(this.creaPanTable(false)); // tabella Italiani
            panTabelle.add(this.creaPanTable(true));   // tabella Stranieri

            /* pannello completo */
            panTot = PannelloFactory.verticale(null);
            panTot.setUsaGapFisso(true);
            panTot.setGapPreferito(0);
            panTot.creaBordo();

            panTot.add(panTitolo);
            panTot.add(this.creaGap(6));
            panTot.add(panTabelle);

            jpan = panTot.getPanFisso();

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return jpan;
    }


    /**
     * Crea un pannello con la tabella Italiani o Stranieri.
     * <p/>
     *
     * @param stranieri false per gli italiani true per gli stranieri
     *
     * @return il pannello con la tabella
     */
    private JPanel creaPanTable(boolean stranieri) {
        /* variabili e costanti locali di lavoro */
        JPanel jpan = null;
        JLabel labelTitolo;
        Pannello panTitolo;
        Pannello panOut;
        String titolo;
        JTable table;
        JTableHeader header;
        JTable tableTot = null;
        TableModel tableModel;
        DatiMemoria dm;
        ArrayList<Campo> campi;
        DatiMemoria datiTot;
        TableColumnModel cmod;
        int quantiArrivati;
        int quantiPartiti;

        try {    // prova ad eseguire il codice

            /* crea il pannello con titolo */
            panTitolo = PannelloFactory.orizzontale(null);
            panTitolo.setUsaGapFisso(true);
            panTitolo.setGapPreferito(0);
            if (stranieri) {
                titolo = "CLIENTI STRANIERI";
            } else {
                titolo = "CLIENTI ITALIANI";
            }// fine del blocco if-else
            labelTitolo = new JLabel(titolo);
            labelTitolo.setFont(FontFactory.creaPrinterFont(Font.BOLD, 9));
            panTitolo.add(Lib.Comp.createHorizontalFiller(1000));
            panTitolo.add(labelTitolo);
            panTitolo.add(Lib.Comp.createHorizontalFiller(1000));

            /* crea la tabella e recupera l'header */
            table = this.creaTable(stranieri);
            header = table.getTableHeader();

            /* crea la jTable con i totali */
            tableModel = table.getModel();
            quantiArrivati = this.getNumArrPar(stranieri, true);
            quantiPartiti = this.getNumArrPar(stranieri, false);
            if (tableModel instanceof DatiMemoria) {
                dm = (DatiMemoria)tableModel;
                campi = dm.getCampi();
                datiTot = new DatiMemoria(campi, 1);
                datiTot.setValueAt("Totale", 0, 1);
                datiTot.setValueAt("" + quantiArrivati, 0, 2);
                datiTot.setValueAt("" + quantiPartiti, 0, 3);
                tableTot = new TableClienti(datiTot);
                tableTot.setFont(FontFactory.creaPrinterFont(Font.BOLD, 9f));
                cmod = table.getColumnModel();
                tableTot.setColumnModel(cmod);
            }// fine del blocco if

            /* crea il pannello con titolo e tabella */
            panOut = PannelloFactory.verticale(null);
            panOut.setUsaGapFisso(true);
            panOut.setGapPreferito(0);

            panOut.add(panTitolo);
            panOut.add(this.creaGap(3));
            panOut.add(header);
            panOut.add(table);
            if (tableTot != null) {
                panOut.add(tableTot);
            }// fine del blocco if

            jpan = panOut.getPanFisso();

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return jpan;
    }


    /**
     * Crea un JTable con la tabella Italiani o Stranieri.
     * <p/>
     *
     * @param stranieri false per gli italiani true per gli stranieri
     *
     * @return la tabella
     */
    private JTable creaTable(boolean stranieri) {
        /* variabili e costanti locali di lavoro */
        JTable table = null;
        Dati dati;
        JTableHeader header;
        TableColumn col;
        TableColumnModel cmod;

        try {    // prova ad eseguire il codice

            dati = this.getDati(stranieri);

            table = new TableClienti(dati);

            /* regola la larghezza delle colonne numeriche */
            cmod = table.getColumnModel();

            col = cmod.getColumn(0);   // numerini
            col.setPreferredWidth(20);
            col.setMaxWidth(20);
            col.setResizable(false);

            col = cmod.getColumn(2);    // arrivati
            col.setPreferredWidth(35);
            col.setMaxWidth(35);
            col.setResizable(false);

            col = cmod.getColumn(3);     // partiti
            col.setPreferredWidth(35);
            col.setMaxWidth(35);
            col.setResizable(false);

            /* regola le caratteristiche dell'header */
            header = table.getTableHeader();
            header.setFont(FontFactory.creaPrinterFont(9f));
            header.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return table;
    }


    /**
     * Recupera i dati per la tabella Italiani o Stranieri.
     * <p/>
     *
     * @param stranieri false per gli italiani true per gli stranieri
     *
     * @return i dati per la tabella
     */
    private Dati getDati(boolean stranieri) {
        /* variabili e costanti locali di lavoro */
        Dati datiMem = null;
        Dati datiDB;
        Query query;
        Filtro filtro;
        int codResidenza;
        String residenza;
        String siglaResidenza;
        int numArrivati;
        int numPartiti;

        String titoloCol;

        ArrayList<Campo> campi;
        Campo campoProgressivo;
        Campo campoResidenza;
        Campo campoArrivati;
        Campo campoPartiti;

        String stringa;

        try {    // prova ad eseguire il codice

            Modulo modIstat = ISTATModulo.get();
            query = new QuerySelezione(modIstat);
            query.addCampo(ISTAT.Cam.codResidenza.get());
            query.addCampo(ISTAT.Cam.numArrivati.get());
            query.addCampo(ISTAT.Cam.numPartiti.get());

            /* costruisce il filtro */
            filtro = this.getFiltroRighe(stranieri);
            query.setFiltro(filtro);

            /* esegue la query */
            datiDB = modIstat.query().querySelezione(query);

            /* crea un oggetto Dati in memoria */
            if (stranieri) {
                titoloCol = "nazionalità di residenza";
            } else {
                titoloCol = "provincia di residenza";
            }// fine del blocco if-else
            campoProgressivo = CampoFactory.intero("#");
            campoResidenza = CampoFactory.testo(titoloCol);
            campoArrivati = CampoFactory.testo("arrivati");
            campoPartiti = CampoFactory.testo("partiti");
            campi = new ArrayList<Campo>();
            campi.add(campoProgressivo);
            campi.add(campoResidenza);
            campi.add(campoArrivati);
            campi.add(campoPartiti);

            datiMem = new DatiMemoria(campi, this.getRigheTab());

            /* riempie la colonna Progressivi */
            for (int k = 0; k < datiMem.getRowCount(); k++) {
                datiMem.setValueAt(k, campoProgressivo, k + 1);
            } // fine del ciclo for

            for (int k = 0; k < datiDB.getRowCount(); k++) {
                codResidenza = datiDB.getIntAt(k, ISTAT.Cam.codResidenza.get());
                numArrivati = datiDB.getIntAt(k, ISTAT.Cam.numArrivati.get());
                numPartiti = datiDB.getIntAt(k, ISTAT.Cam.numPartiti.get());
                if (stranieri) {
                    Modulo mod = NazioneModulo.get();
                    residenza = mod.query().valoreStringa(Nazione.Cam.nazione.get(), codResidenza);
                    siglaResidenza = mod.query()
                            .valoreStringa(Nazione.Cam.sigla2.get(), codResidenza);
                } else {
                    Modulo mod = ProvinciaModulo.get();
                    residenza = mod.query()
                            .valoreStringa(Provincia.Cam.nomeCorrente.get(), codResidenza);
                    siglaResidenza = mod.query()
                            .valoreStringa(Provincia.Cam.sigla.get(), codResidenza);
                }// fine del blocco if-else

                /* scrive nei dati memoria */

                stringa = residenza;
                if (Lib.Testo.isValida(siglaResidenza)) {
                    stringa += " (" + siglaResidenza + ")";
                }// fine del blocco if
                datiMem.setValueAt(k, campoResidenza, stringa);

                stringa = "";
                if (numArrivati > 0) {
                    stringa = "" + numArrivati;
                }// fine del blocco if
                datiMem.setValueAt(k, campoArrivati, "" + stringa);

                stringa = "";
                if (numPartiti > 0) {
                    stringa = "" + numPartiti;
                }// fine del blocco if
                datiMem.setValueAt(k, campoPartiti, "" + stringa);

            } // fine del ciclo for

            /* chiude i dati sul DB */
            datiDB.close();


        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return datiMem;
    }


    /**
     * Ritorna il numero di righe che deve avere ognuna delle due tabelle di dettaglio.
     * <p/>
     * E' il massimo tra le due tabelle.
     * Se questo numero è imferiore a un minimo stabilito, ritorna comunque
     * il minimo stabilito.
     *
     * @return il numero di righe di ognuna delle due tabelle
     */
    private int getRigheTab() {
        /* variabili e costanti locali di lavoro */
        int quanteRighe = 0;
        int minRighe = 23;

        try {    // prova ad eseguire il codice
            quanteRighe = this.getRigheMax();
            if (quanteRighe < minRighe) {
                quanteRighe = minRighe;
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return quanteRighe;
    }


    /**
     * Ritorna il numero massimo di righe tra la tabella Italiani e Stranieri.
     * <p/>
     *
     * @return il numero massimo di righe
     */
    private int getRigheMax() {
        /* variabili e costanti locali di lavoro */
        int righeMax = 0;
        int righeIta;
        int righeStra;

        try {    // prova ad eseguire il codice
            righeIta = this.contaRecords(false);
            righeStra = this.contaRecords(true);
            righeMax = Lib.Mat.getMax(righeIta, righeStra);
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return righeMax;
    }


    /**
     * Conta i records di dettaglio di questa scheda.
     * <p/>
     *
     * @param stranieri false per gli italiani true per gli stranieri
     *
     * @return il numero di records richiesti
     */
    private int contaRecords(boolean stranieri) {
        /* variabili e costanti locali di lavoro */
        int quanti = 0;
        Filtro filtro;
        Modulo modIstat;

        try {    // prova ad eseguire il codice

            /* costruisce il filtro */
            modIstat = ISTATModulo.get();
            filtro = this.getFiltroRighe(stranieri);
            quanti = modIstat.query().contaRecords(filtro);

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return quanti;
    }


    /**
     * Ritorna un filtro che isola le righe ISTAT italiane o straniere di questa scheda.
     * <p/>
     *
     * @param stranieri false per gli italiani true per gli stranieri
     *
     * @return il filtro
     */
    private Filtro getFiltroRighe(boolean stranieri) {
        /* variabili e costanti locali di lavoro */
        Filtro filtro = null;
        Filtro unFiltro;
        int codTipoRiga;

        try {    // prova ad eseguire il codice
            filtro = new Filtro();
            unFiltro = FiltroFactory.crea(ISTAT.Cam.linkTesta.get(), this.getCodTesta());
            filtro.add(unFiltro);
            if (stranieri) {
                codTipoRiga = ISTAT.TipoRiga.straniero.getCodice();
            } else {
                codTipoRiga = ISTAT.TipoRiga.italiano.getCodice();
            }// fine del blocco if-else
            unFiltro = FiltroFactory.crea(ISTAT.Cam.tipoRiga.get(), codTipoRiga);
            filtro.add(unFiltro);
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return filtro;
    }


    /**
     * Ritorna la data del documento sotto forma di stringa
     * <p/>
     *
     * @return la data come stringa
     */
    private String getStringaDataDoc() {
        /* variabili e costanti locali di lavoro */
        String stringaData = "";
        Date data;

        try { // prova ad eseguire il codice
            data = this.getDataDoc();
            stringaData = Lib.Data.getDataBreve(data);
            stringaData = stringaData.toUpperCase();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return stringaData;
    }


    /**
     * Ritorna il numero del documento sotto forma di stringa
     * <p/>
     *
     * @return il numero come stringa
     */
    private String getStringaNumeroDoc() {
        return "" + this.getNumDoc();
    }


    /**
     * Ritorna il tipo di esercizio
     * <p/>
     *
     * @return il nome del comune
     */
    private String getTipoEsercizio() {
        return "ALBERGO";
    }


    /**
     * Ritorna il numero di stelle dell'esercizio come stringa
     * <p/>
     *
     * @return il numero di stelle
     */
    private String getStringaNumStelle() {
        /* variabili e costanti locali di lavoro */
        String stringa = "";
        int numero;

        try {    // prova ad eseguire il codice
            numero = this.getNumStelle();
            stringa = "" + numero;
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return stringa;
    }


    /**
     * Ritorna il codice dell'azienda di riferimento.
     * <p/>
     *
     * @return il codice dell'azienda
     */
    private int getCodAzienda() {
        /* variabili e costanti locali di lavoro */
        int codAzienda = 0;
        Modulo modTesta;
        int codTesta;

        try {    // prova ad eseguire il codice
            modTesta = TestaStampeModulo.get();
            codTesta = this.getCodTesta();
            codAzienda = modTesta.query().valoreInt(TestaStampe.Cam.azienda.get(), codTesta);
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return codAzienda;
    }


    /**
     * Crea un gap fisso verticale.
     * <p/>
     *
     * @param gap in pixel
     *
     * @return il componente creato
     */
    private Component creaGap(int gap) {
        return Lib.Comp.createVerticalFiller(gap, gap, gap);
    }


    /**
     * JTable per i dati di dettaglio
     * </p>
     */
    private final class TableClienti extends JTable {

        /**
         * Costruttore completo con parametri. <br>
         *
         * @param tableModel il modello dati
         */
        public TableClienti(TableModel tableModel) {
            /* rimanda al costruttore della superclasse */
            super(tableModel);

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
            this.setFont(FontFactory.creaPrinterFont(9f));
            this.setGridColor(Color.BLACK);
            this.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            this.setShowGrid(true);
        }


        /**
         * Sovrascritto per avere tutte le colonne centrate tranne la
         * descrizione allineata a sinistra
         */
        public TableCellRenderer getCellRenderer(int row, int col) {
            /* variabili e costanti locali di lavoro */
            TableCellRenderer renderer = null;
            JLabel label;

            try { // prova ad eseguire il codice
                renderer = super.getCellRenderer(row, col);
                if ((renderer != null) && (renderer instanceof JLabel)) {
                    label = (JLabel)renderer;
                    label.setHorizontalAlignment(SwingConstants.CENTER);
                    if (col == 1) {
                        label.setHorizontalAlignment(SwingConstants.LEFT);
                    }// fine del blocco if
                }// fine del blocco if


            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

            /* valore di ritorno */
            return renderer;
        }

    } // fine della classe 'interna'


    /**
     * Ritorna il numero totale di arrivati o partiti italiani o stranieri per questa scheda.
     * <p/>
     *
     * @param stranieri false per italiani true per stranieri
     * @param arrivati false per partiti true per arrivati
     *
     * @return il totale richiesto
     */
    private int getNumArrPar(boolean stranieri, boolean arrivati) {
        /* variabili e costanti locali di lavoro */
        int quanti = 0;
        Number numero;
        Filtro filtro;
        Modulo modIstat;
        String nomeCampo;

        try {    // prova ad eseguire il codice
            filtro = getFiltroRighe(stranieri);
            modIstat = ISTATModulo.get();
            if (arrivati) {
                nomeCampo = ISTAT.Cam.numArrivati.get();
            } else {
                nomeCampo = ISTAT.Cam.numPartiti.get();
            }// fine del blocco if-else
            numero = modIstat.query().somma(nomeCampo, filtro);
            quanti = Libreria.getInt(numero);
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return quanti;
    }


    private int getCodTesta() {
        return codTesta;
    }


    private void setCodTesta(int codTesta) {
        this.codTesta = codTesta;
    }


    private int getNumDoc() {
        return numDoc;
    }


    private void setNumDoc(int numDoc) {
        this.numDoc = numDoc;
    }


    private Date getDataDoc() {
        return dataDoc;
    }


    private void setDataDoc(Date dataDoc) {
        this.dataDoc = dataDoc;
    }


    private String getComune() {
        return comune;
    }


    private void setComune(String comune) {
        this.comune = comune;
    }


    private String getDenominazione() {
        return denominazione;
    }


    private void setDenominazione(String denominazione) {
        this.denominazione = denominazione;
    }


    private int getNumStelle() {
        return numStelle;
    }


    private void setNumStelle(int numStelle) {
        this.numStelle = numStelle;
    }


    private int getNumGiornoPrec() {
        return numGiornoPrec;
    }


    private void setNumGiornoPrec(int numGiornoPrec) {
        this.numGiornoPrec = numGiornoPrec;
    }


    private int getNumArrivati() {
        return numArrivati;
    }


    private void setNumArrivati(int numArrivati) {
        this.numArrivati = numArrivati;
    }


    private int getNumPartiti() {
        return numPartiti;
    }


    private void setNumPartiti(int numPartiti) {
        this.numPartiti = numPartiti;
    }


}// fine della classe