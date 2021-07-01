/**
 * Title:     ContoDialogoRiepilogo
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      16-lug-2006
 */
package it.algos.albergo.conto;

import com.wildcrest.j2printerworks.HorizontalLine;
import com.wildcrest.j2printerworks.J2ComponentPrinter;
import com.wildcrest.j2printerworks.J2FlowPrinter;
import com.wildcrest.j2printerworks.J2Printer;
import com.wildcrest.j2printerworks.J2TablePrinter;
import com.wildcrest.j2printerworks.J2TreePrinter;
import com.wildcrest.j2printerworks.VerticalGap;
import it.algos.albergo.Albergo;
import it.algos.albergo.AlbergoLib;
import it.algos.albergo.conto.addebito.Addebito;
import it.algos.albergo.conto.addebito.AddebitoModulo;
import it.algos.albergo.conto.movimento.Movimento;
import it.algos.albergo.conto.movimento.MovimentoModulo;
import it.algos.albergo.conto.pagamento.Pagamento;
import it.algos.albergo.conto.pagamento.PagamentoModulo;
import it.algos.albergo.conto.sconto.ScontoModulo;
import it.algos.albergo.conto.sospeso.SospesoModulo;
import it.algos.albergo.pianodeicontialbergo.AlberoPC;
import it.algos.albergo.pianodeicontialbergo.OggettoNodoPC;
import it.algos.base.albero.Albero;
import it.algos.base.albero.AlberoNodo;
import it.algos.base.campo.base.Campo;
import it.algos.base.campo.base.CampoFactory;
import it.algos.base.costante.CostanteColore;
import it.algos.base.dialogo.DialogoBase;
import it.algos.base.errore.Errore;
import it.algos.base.font.FontFactory;
import it.algos.base.layout.Layout;
import it.algos.base.libreria.Lib;
import it.algos.base.libreria.Libreria;
import it.algos.base.lista.Lista;
import it.algos.base.modulo.Modulo;
import it.algos.base.navigatore.Navigatore;
import it.algos.base.pannello.Pannello;
import it.algos.base.pannello.PannelloFactory;
import it.algos.base.pannello.PannelloFlusso;
import it.algos.base.progetto.Progetto;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.query.filtro.FiltroFactory;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Dialogo di esecuzione riepilogo di gestione.
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 22-mar-2006 ore 15.00.06
 */
public final class ContoDialogoRiepilogo extends DialogoBase {

    /* nomi dei campi del dialogo */
    private static final String nomeDataIni = "dal";

    private static final String nomeDataFine = "al";

    private static final String nomeOpzioni = "opzioni";

    private static DecimalFormat formatoNumero = new DecimalFormat("#,###,##0.00");

    /* pannello di dettaglio vendite e incassi */
    private Pannello panDettaglio = null;

    /* pannello contenitore dell'albero di riepilogo vendite */
    private Pannello panAlberoVendite = null;

    /* pannello contenitore del totale ricavi */
    private Pannello panTotRicavi = null;

    /* pannello contenitore del riepilogo */
    private Pannello panRiepilogo = null;


    /* albero di riepilogo vendite */
    private Albero albero = null;

    /* tavola di riepilogo incassi */
    private JTable tavola = null;

    /* modello della tavola incassi */
    private TavolaIncassiModello modTavolaIncassi;

    /* bottone esegui calcolo */
    private JButton bottoneEsegui;

    /* bottone mostra addebiti */
    private JButton bottoneAddebiti;

    /* bottone mostra pagamenti */
    private JButton bottonePagamenti;

    private JFormattedTextField campoNum;

    private JPanel panRenderer;

    /* flag per distinguere la prima esecuzione dalle successive */
    private boolean primaEsecuzione = true;

    /* pannello-bottone ricavi lordi */
    private PanBottone pbRicaviLordi;

    /* pannello-bottone sconti */
    private PanBottone pbSconti;

    /* pannello-bottone ricavi netti */
    private PanBottone pbRicaviNetti;

    /* pannello-bottone totale incassi */
    private PanBottone pbTotIncassi;

    /* pannello-bottone riporto totale ricavi netti nel riepilogo */
    private PanBottone pbRipRicaviNetti;

    /* pannello-bottone riporto totale incassi nel riepilogo */
    private PanBottone pbRipTotIncassi;

    /* pannello-bottone credito clienti nel riepilogo */
    private PanBottone pbCreditoClienti;

    /* pannello-bottone sospesi nel riepilogo */
    private PanBottone pbSospesi;

    /* pannello-bottone pagamenti a titolo sconosciuto riepilogo */
    private PanBottone pbSconosciuti;


    /**
     * Costruttore completo con parametri
     * <p/>
     */
    public ContoDialogoRiepilogo() {
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
     * Regolazioni immediate di riferimenti e variabili. <br>
     * Metodo chiamato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {

        /* variabili e costanti locali di lavoro */
        String titolo = "Riepilogo di gestione";

        try { // prova ad eseguire il codice

            /* questo dialogo non e' modale */
            this.getDialogo().setModal(false);

            this.preparaRenderer();

            this.setTitolo(titolo);
            this.addBottoneStampa();
            this.addBottoneChiudi();

            this.creaPannelli();


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Crea, registra i pannelli principali e li aggiunge al dialogo.
     * <p/>
     */
    private void creaPannelli() {
        /* variabili e costanti locali di lavoro */
        Pannello pan;

        try {    // prova ad eseguire il codice

            /* pannello date */
            pan = this.creaPanDate();
            this.addPannello(pan);

            /* pannello dettaglio */
            pan = this.creaPanDettaglio();
            this.setPanDettaglio(pan);
            this.addPannello(pan);


        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

    }


    /**
     * Pannello di impostazione delle date.
     * <p/>
     *
     * @return il pannello creato
     */
    private Pannello creaPanDate() {
        /* variabili e costanti locali di lavoro */
        Pannello panDate = null;
        Campo campoDataInizio;
        Campo campoDataFine;
        Date dataProposta;
        JButton bottone;

        try {    // prova ad eseguire il codice

            /* pannello date */
            dataProposta = AlbergoLib.getDataProgramma();
            campoDataInizio = CampoFactory.data(ContoDialogoRiepilogo.nomeDataIni);
            campoDataInizio.decora().obbligatorio();
            campoDataInizio.setValore(dataProposta);
            campoDataFine = CampoFactory.data(ContoDialogoRiepilogo.nomeDataFine);
            campoDataFine.decora().obbligatorio();
            campoDataFine.setValore(dataProposta);

            /* bottone esegui */
            bottone = new JButton("Esegui");
            this.setBottoneEsegui(bottone);
            bottone.addActionListener(new AzioneEsegui());
            bottone.setFocusPainted(false);
            bottone.setOpaque(false);

            panDate = PannelloFactory.orizzontale(this.getModulo());
            panDate.creaBordo("Periodo da riepilogare");
            panDate.setAllineamento(Layout.ALLINEA_BASSO);
            panDate.add(campoDataInizio);
            panDate.add(campoDataFine);
            panDate.add(bottone);
            panDate.add(Box.createHorizontalGlue());

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return panDate;
    }


    /**
     * Crea il pannello di visualizzazione dei dettagli vendite e incassi.
     * <p/>
     *
     * @return il pannello creato
     */
    private Pannello creaPanDettaglio() {
        /* variabili e costanti locali di lavoro */
        Pannello panTot = null;
        Pannello pan;

        try {    // prova ad eseguire il codice

            panTot = new PannelloFlusso(Layout.ORIENTAMENTO_ORIZZONTALE);
            this.setPanDettaglio(panTot);

            pan = this.creaPanRicavi();
            panTot.add(pan);
            pan = this.creaPanDestro();
            panTot.add(pan);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return panTot;
    }


    /**
     * Crea il pannello destro con incassi e riepilogo.
     * <p/>
     *
     * @return il pannello creato
     */
    private Pannello creaPanDestro() {
        /* variabili e costanti locali di lavoro */
        Pannello panTot = null;
        Pannello pan;

        try {    // prova ad eseguire il codice

            panTot = new PannelloFlusso(Layout.ORIENTAMENTO_VERTICALE);

            pan = this.creaPanIncassi();
            panTot.add(pan);

            panTot.add(Box.createVerticalGlue());

            pan = this.creaPanRiepilogo();
            panTot.add(pan);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return panTot;
    }


    /**
     * Crea il pannello di riepilogo dei ricavi.
     * <p/>
     *
     * @return il pannello creato
     */
    private Pannello creaPanRicavi() {
        /* variabili e costanti locali di lavoro */
        Pannello panVendite = null;
        Pannello panAlbero;
        Pannello panOpzioni;
        Pannello panTotali;
        Pannello panBasso;

        try {    // prova ad eseguire il codice

            /* pannello riepilogo vendite */
            panVendite = new PannelloFlusso(Layout.ORIENTAMENTO_VERTICALE);
            panVendite.creaBordo("Ricavi");

            /* pannello albero vendite */
            panAlbero = new PannelloFlusso(Layout.ORIENTAMENTO_VERTICALE);
            this.setPanAlberoVendite(panAlbero);

            /* pannello opzioni */
            panOpzioni = this.creaPanOpzioni();

            /* pannello totali */
            panTotali = this.creaPanTotRicavi();

            /* pannello opzioni e totali */
            panBasso = new PannelloFlusso(Layout.ORIENTAMENTO_ORIZZONTALE);
            panBasso.add(panOpzioni);
            panBasso.add(Box.createHorizontalGlue());
            panBasso.add(panTotali);

            /* aggiunge i componenti */
            panVendite.add(panAlbero);
            panVendite.add(panBasso);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return panVendite;
    }


    /**
     * Crea il pannello Opzioni dell'albero Ricavi.
     * <p/>
     *
     * @return il pannello creato
     */
    private Pannello creaPanOpzioni() {
        /* variabili e costanti locali di lavoro */
        Pannello pan = null;
        Campo campoOpzioni;
        JButton bottone;

        try {    // prova ad eseguire il codice

            campoOpzioni = CampoFactory.radioInterno(nomeOpzioni);
            campoOpzioni.setOrientamentoComponenti(Layout.ORIENTAMENTO_ORIZZONTALE);
            campoOpzioni.setUsaNonSpecificato(false);
            campoOpzioni.decora().eliminaEtichetta();
            campoOpzioni.setValoriInterni("mastri, conti, sottoconti");

            /* bottone mostra addebiti */
            bottone = new JButton("Mostra movimenti");
            this.setBottoneAddebiti(bottone);
            bottone.addActionListener(new AzioneAddebiti());
            bottone.setFocusPainted(false);
            bottone.setOpaque(false);

            pan = new PannelloFlusso(Layout.ORIENTAMENTO_VERTICALE);
            pan.setUsaGapFisso(true);
            pan.setGapPreferito(10);
            pan.add(campoOpzioni);
            pan.add(bottone);

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return pan;
    }


    /**
     * Crea il pannello di totalizzazione dei ricavi.
     * <p/>
     *
     * @return il pannello creato
     */
    private Pannello creaPanTotRicavi() {
        /* variabili e costanti locali di lavoro */
        Pannello pan = null;
        PanBottone pb;

        try {    // prova ad eseguire il codice

            pan = new PannelloFlusso(Layout.ORIENTAMENTO_VERTICALE);
            this.setPanTotRicavi(pan);
            pan.setGapPreferito(4);
            pan.setAllineamento(Layout.ALLINEA_DX);

            pb = new PanBottone("Totale ricavi", new AzRicaviLordi());
            this.setPbRicaviLordi(pb);
            pan.add(pb);

            pb = new PanBottone("- Sconti", new AzSconti());
            this.setPbSconti(pb);
            pan.add(pb);

            pb = new PanBottone("Ricavi netti", null);
            this.setPbRicaviNetti(pb);
            pb.setBold(true);
            pb.setFeelClickable(false);
            pan.add(pb);

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return pan;
    }


    /**
     * Crea il pannello di visualizzazione incassi.
     * <p/>
     *
     * @return il pannello creato
     */
    private Pannello creaPanIncassi() {
        /* variabili e costanti locali di lavoro */
        Pannello panIncassi = null;
        Pannello panTavola;
        Pannello panTotale;
        Pannello panBottone;
        JTable table;
        TavolaIncassiModello modello;
        TableColumn column;
        JButton bottone;
        ListSelectionModel selModel;
        PanBottone pb;

        try {    // prova ad eseguire il codice

            panIncassi = new PannelloFlusso(Layout.ORIENTAMENTO_VERTICALE);
            panIncassi.setGapMassimo(20);
            panIncassi.setAllineamento(Layout.ALLINEA_DA_COMPONENTI);
            panIncassi.creaBordo("Incassi");

            modello = new TavolaIncassiModello();
            this.setModTavolaIncassi(modello);
            table = new JTable(modello);
            this.setTavola(table);

            table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            table.getTableHeader().setFont(FontFactory.creaScreenFont(Font.BOLD));
            table.setFont(FontFactory.creaScreenFont());

            /* aggiunge un listener per la modifica della selezione */
            selModel = table.getSelectionModel();
            selModel.addListSelectionListener(new AzioneSelezioneLista());

            /* regola i renderer per le colonne */
            table.setDefaultRenderer(Object.class, new RendererTabIncassi());
            table.setDefaultRenderer(Double.class, new RendererTabIncassi());

            /* regola la larghezza delle colonne */
            for (int i = 0; i <= 1; i++) {
                column = table.getColumnModel().getColumn(i);
                if (i == 1) {
                    column.setPreferredWidth(100);  // colonna importi
                } else {
                    column.setPreferredWidth(70);
                }
            }

            panTavola = new PannelloFlusso(Layout.ORIENTAMENTO_VERTICALE);
            panTavola.setUsaGapFisso(true);
            panTavola.setGapPreferito(0);
            panTavola.add(table.getTableHeader());
            panTavola.add(table);

            /* Pannello-bottone totale incassi */
            pb = new PanBottone("Totale incassi", new AzTotIncassi());
            this.setPbTotIncassi(pb);
            panTotale = new PannelloFlusso(Layout.ORIENTAMENTO_ORIZZONTALE);
            panTotale.add(Box.createHorizontalGlue());
            panTotale.add(pb);

            /* bottone mostra movimenti */
            bottone = new JButton("Mostra movimenti");
            this.setBottonePagamenti(bottone);
            bottone.addActionListener(new AzionePagamenti());
            bottone.setFocusPainted(false);
            bottone.setOpaque(false);
            panBottone = new PannelloFlusso(Layout.ORIENTAMENTO_ORIZZONTALE);
            panBottone.add(Box.createHorizontalGlue());
            panBottone.add(bottone);
            panBottone.add(Box.createHorizontalGlue());

            panIncassi.add(panTavola);
            panIncassi.add(panTotale);
            panIncassi.add(panBottone);


        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return panIncassi;
    }


    /**
     * Crea il pannello di riepilogo finale.
     * <p/>
     *
     * @return il pannello creato
     */
    private Pannello creaPanRiepilogo() {
        /* variabili e costanti locali di lavoro */
        Pannello panRiepilogo = null;
        PanBottone pb;

        try {    // prova ad eseguire il codice

            panRiepilogo = new PannelloFlusso(Layout.ORIENTAMENTO_VERTICALE);
            this.setPanRiepilogo(panRiepilogo);
            panRiepilogo.setAllineamento(Layout.ALLINEA_DX);
            panRiepilogo.setGapMassimo(8);
            panRiepilogo.creaBordo("Riepilogo");

            /* Pannello-bottone riporto ricavi netti */
            pb = new PanBottone("Ricavi netti", null);
            this.setPbRipRicaviNetti(pb);
            pb.setFeelClickable(false);
            panRiepilogo.add(pb);

            /* Pannello-bottone riporto totale incassi */
            pb = new PanBottone("- Incassi", null);
            this.setPbRipTotIncassi(pb);
            pb.setFeelClickable(false);
            panRiepilogo.add(pb);

            /* Pannello-bottone credito clienti */
            pb = new PanBottone("Credito Clienti", null);
            this.setPbCreditoClienti(pb);
            pb.setFeelClickable(false);
            pb.setBold(true);
            panRiepilogo.add(pb);

            /* aggiunge un separatore */
            JSeparator js = new JSeparator();
            js.setMaximumSize(new Dimension(js.getMaximumSize().width, 10));
            panRiepilogo.add(js);

            /* Pannello-bottone di cui sospesi */
            pb = new PanBottone("di cui sospesi", new AzSospesi());
            this.setPbSospesi(pb);
            pb.setColor(CostanteColore.ROSSO);
            panRiepilogo.add(pb);

            /* Pannello-bottone pagamenti a titolo sconosciuto */
            pb = new PanBottone("a titolo sconosciuto", new AzSconosciuti());
            this.setPbSconosciuti(pb);
            pb.setColor(CostanteColore.ROSSO);
            panRiepilogo.add(pb);


        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return panRiepilogo;
    }


    /**
     * Listener del pannello-bottone totale ricavi lordi
     */
    private final class AzRicaviLordi implements ActionListener {

        public void actionPerformed(ActionEvent actionEvent) {
            try { // prova ad eseguire il codice
                mostraAddebiti(getFiltroAddebitiPeriodo());
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

        }
    } // fine della classe 'interna'


    /**
     * Listener del pannello-bottone totale sconti
     */
    private final class AzSconti implements ActionListener {

        public void actionPerformed(ActionEvent actionEvent) {
            /* variabili e costanti locali di lavoro */
            Filtro filtro;

            try { // prova ad eseguire il codice
                filtro = getFiltroMovimentiPeriodo();
                mostraSconti(filtro);
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }
    } // fine della classe 'interna'


    /**
     * Listener del pannello-bottone totale incassi
     */
    private final class AzTotIncassi implements ActionListener {

        public void actionPerformed(ActionEvent actionEvent) {
            /* variabili e costanti locali di lavoro */
            Filtro filtro;

            try { // prova ad eseguire il codice
                filtro = getFiltroMovimentiPeriodo();
                mostraPagamenti(filtro);
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

        }
    } // fine della classe 'interna'


    /**
     * Listener del pannello-bottone sospesi
     */
    private final class AzSospesi implements ActionListener {

        public void actionPerformed(ActionEvent actionEvent) {
            /* variabili e costanti locali di lavoro */
            Filtro filtro;

            try { // prova ad eseguire il codice
                filtro = getFiltroMovimentiPeriodo();
                mostraSospesi(filtro);
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }
    } // fine della classe 'interna'


    /**
     * Listener del pannello-bottone sconosciuti
     */
    private final class AzSconosciuti implements ActionListener {

        public void actionPerformed(ActionEvent actionEvent) {
            /* variabili e costanti locali di lavoro */
            Filtro filtroData;
            Filtro filtroTitolo;
            Filtro filtroTot;

            try { // prova ad eseguire il codice
                filtroData = getFiltroMovimentiPeriodo();
                filtroTitolo = getFiltroPagamentiSconosciuti();
                filtroTot = new Filtro();
                filtroTot.add(filtroData);
                filtroTot.add(filtroTitolo);
                mostraPagamenti(filtroTot);
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }
    } // fine della classe 'interna'


    /**
     * Ritorna un filtro per selezionare tutti gli addebiti nel periodo di analisi.
     * <p/>
     *
     * @return il filtro
     */
    private Filtro getFiltroAddebitiPeriodo() {
        /* variabili e costanti locali di lavoro */
        Filtro fdata1;
        Filtro fdata2;
        Filtro filtroData = null;

        try {    // prova ad eseguire il codice

            fdata1 = FiltroFactory.crea(Addebito.Cam.data.get(),
                    Filtro.Op.MAGGIORE_UGUALE,
                    getDataInizio());

            fdata2 = FiltroFactory.crea(Addebito.Cam.data.get(),
                    Filtro.Op.MINORE_UGUALE,
                    getDataFine());
            filtroData = new Filtro();
            filtroData.add(fdata1);
            filtroData.add(fdata2);

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return filtroData;
    }


    /**
     * Ritorna un filtro per selezionare tutti i movimenti nel periodo di analisi.
     * <p/>
     *
     * @return il filtro
     */
    private Filtro getFiltroMovimentiPeriodo() {
        /* variabili e costanti locali di lavoro */
        Filtro fdata1;
        Filtro fdata2;
        Filtro filtroData = null;

        try {    // prova ad eseguire il codice

            fdata1 = FiltroFactory.crea(Movimento.Cam.data.get(),
                    Filtro.Op.MAGGIORE_UGUALE,
                    getDataInizio());

            fdata2 = FiltroFactory.crea(Movimento.Cam.data.get(),
                    Filtro.Op.MINORE_UGUALE,
                    getDataFine());
            filtroData = new Filtro();
            filtroData.add(fdata1);
            filtroData.add(fdata2);

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return filtroData;
    }


    /**
     * Ritorna un filtro che seleziona tutti i pagamenti a titolo sconosciuto.
     * <p/>
     * (tutti quelli che hanno un qualsiasi titolo non previsto nella enum)
     *
     * @return il filtro per selezionare i pagamenti a titolo sconosciuto
     */
    private Filtro getFiltroPagamentiSconosciuti() {
        /* variabili e costanti locali di lavoro */
        Filtro filtroSconosciuti = null;
        Filtro filtro;
        Pagamento.TitoloPagamento[] titoli;
        Pagamento.TitoloPagamento unTitolo;
        int codTitolo;

        try {    // prova ad eseguire il codice
            /* filtro per i pagamenti con titolo diverso da quanto previsto nella enum */
            titoli = Pagamento.TitoloPagamento.values();
            filtroSconosciuti = new Filtro();
            for (int k = 0; k < titoli.length; k++) {
                unTitolo = titoli[k];
                codTitolo = unTitolo.getCodice();
                filtro = FiltroFactory.crea(Pagamento.Cam.titolo.get(),
                        Filtro.Op.DIVERSO,
                        codTitolo);
                filtroSconosciuti.add(filtro);
            } // fine del ciclo for
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return filtroSconosciuti;
    }


    /**
     * Rende il dialogo visibile.
     * <p/>
     * Può essere sovrascritto per effettuare operazioni
     * subito prima che il dialogo diventi visibile
     */
    public void rendiVisibile() {

        /* nasconde il dettaglio finché non si preme esegui */
        this.setContenutoVisibile(false);

        this.getDialogo().pack();
        Lib.Gui.centraFinestra(this.getDialogo());

        this.vaiCampoPrimo();

        super.rendiVisibile();

    }


    /**
     * Rende visibile o invisibile l'intero contenuto del riepilogo.
     * <p/>
     * (Tranne il pannello di impostazione date)
     *
     * @param flag per la visibilità
     */
    private void setContenutoVisibile(boolean flag) {
        /* variabili e costanti locali di lavoro */
        Pannello pan;

        try {    // prova ad eseguire il codice

            pan = this.getPanDettaglio();
            pan.getPanFisso().setVisible(flag);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Metodo invocato dal pulsante Esegui.
     * <p/>
     */
    private void esegui() {
        /* variabili e costanti locali di lavoro */
        PanBottone pb;
        boolean visibile;

        try {    // prova ad eseguire il codice

            this.aggiornaAlberoVendite();
            this.aggiornaTavolaIncassi();
            this.aggiornaSospesi();
            this.aggiornaSconosciuti();

            this.aggiornaValoriCalcolati();

            pb = this.getPbSconosciuti();
            visibile = (pb.getImporto() != 0);
            pb.setVisible(visibile);


            this.setContenutoVisibile(true);

            /* se e' il primo aggiornamento
             * centra la finestra */
            if (primaEsecuzione) {
                this.getDialogo().pack();
                Lib.Gui.centraFinestra(this.getDialogo());
                primaEsecuzione = false;
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Prepara alcune variabili comuni per il renderer dei nodi.
     * <p/>
     */
    private void preparaRenderer() {
        try {    // prova ad eseguire il codice
            campoNum = new JFormattedTextField(formatoNumero);
            campoNum.setBorder(null);
            campoNum.setHorizontalAlignment(JFormattedTextField.RIGHT);
            panRenderer = new JPanel();
            panRenderer.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));
            panRenderer.setLayout(new BorderLayout());
            panRenderer.setPreferredSize(new Dimension(350, 20));
            panRenderer.setOpaque(true);
            panRenderer.setBackground(Color.WHITE);
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Carica i dati nell'albero e lo inserisce nel pannello.
     * <p/>
     */
    private void aggiornaAlberoVendite() {
        /* variabili e costanti locali di lavoro */
        Albero albero = null;
        Pannello pannello = null;

        try {    // prova ad eseguire il codice

            /* crea l'albero e lo aggiunge al pannello */
            albero = new AlberoPC();
            this.setAlbero(albero);
            this.regolaAlbero();
            this.caricaDatiAlbero();
            pannello = this.getPanAlberoVendite();
            pannello.removeAll();
            pannello.add(albero);

            pannello.setPreferredSize(470, 350);
            pannello.getPanFisso().validate();

            /* seleziona la voce visualizza sottoconti */
            this.getCampoOpzioni().setValore(3);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

    }


    /**
     * Regola l'albero.
     * <p/>
     * Sostituisce lo UserObject dei nodi
     * Assegna un renderer specifico
     */
    private void regolaAlbero() {
        /* variabili e costanti locali di lavoro */
        Albero albero;
        RendererNodo renderer;
        AlberoNodo nodoRoot;
        ArrayList<AlberoNodo> nodi;
        OggettoNodoPC oggettoNodoOld;
        OggettoNodoRiepilogo oggettoNodoNew;
        Object oggetto;

        try {    // prova ad eseguire il codice

            albero = this.getAlbero();

            /* aggiunge un listener per la modifica della selezione */
            albero.getTree().addTreeSelectionListener(new AzioneSelezioneAlbero());

            /* sostituisce lo UserObject con uno piu' specifico */
            nodoRoot = albero.getModelloAlbero().getNodoRoot();
            nodi = nodoRoot.getNodi();
            for (AlberoNodo nodo : nodi) {
                oggetto = nodo.getUserObject();
                if (oggetto != null) {
                    if (oggetto instanceof OggettoNodoPC) {
                        oggettoNodoOld = (OggettoNodoPC)oggetto;
                        oggettoNodoNew = new OggettoNodoRiepilogo();
                        oggettoNodoNew.setTipo(oggettoNodoOld.getTipo());
                        oggettoNodoNew.setCodice(oggettoNodoOld.getCodice());
                        oggettoNodoNew.setDescrizione(oggettoNodoOld.getDescrizione());
                        nodo.setUserObject(oggettoNodoNew);
                    }// fine del blocco if
                }// fine del blocco if
            }

            /* Assegna un renderer personalizzato per
             * disegnare le celle dell'albero. */
            renderer = new RendererNodo();
            albero.setCellRenderer(renderer);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

    }


    /**
     * Carica i dati nell'albero.
     * <p/>
     */
    private void caricaDatiAlbero() {
        /* variabili e costanti locali di lavoro */
        Albero albero;
        AlberoNodo nodoRoot;
        ArrayList<AlberoNodo> nodi;
        Object oggetto;
        OggettoNodoRiepilogo oggettoNodo;
        AlberoPC.TipoNodo tipoNodo;
        int codice;
        Modulo modAddebito;
        Modulo modSottoconto;
        Campo campoTot;
        Campo campoSottoconto;
        Campo campoDataAddebito;
        Filtro filtroAzienda;
        Filtro filtroSottoconto;
        Filtro filtroData1;
        Filtro filtroData2;
        Filtro filtroDate;
        Filtro filtro;
        double totale;
        double totGenerale = 0;

        try {    // prova ad eseguire il codice
            albero = this.getAlbero();
            modAddebito = Albergo.Moduli.Addebito();
            modSottoconto = Albergo.Moduli.AlbSottoconto();

            /* Spazzola tutti i nodi di tipo Sottoconto */
            nodoRoot = albero.getModelloAlbero().getNodoRoot();
            nodi = nodoRoot.getNodi();
            for (AlberoNodo nodo : nodi) {
                oggetto = nodo.getUserObject();
                if (oggetto != null) {
                    if (oggetto instanceof OggettoNodoRiepilogo) {
                        oggettoNodo = (OggettoNodoRiepilogo)oggetto;
                        tipoNodo = oggettoNodo.getTipo();
                        if (tipoNodo.equals(AlberoPC.TipoNodo.sottoconto)) {
                            codice = oggettoNodo.getCodice();

                            campoTot = modAddebito.getCampo(Addebito.Cam.importo.get());
                            campoSottoconto = modSottoconto.getCampoChiave();
                            campoDataAddebito = modAddebito.getCampo(Addebito.Cam.data.get());

                            filtroAzienda = Albergo.Moduli.Conto().getFiltroAzienda();
                            filtroSottoconto = FiltroFactory.crea(campoSottoconto, codice);
                            filtroData1 = FiltroFactory.crea(campoDataAddebito,
                                    Filtro.Op.MAGGIORE_UGUALE,
                                    this.getDataInizio());
                            filtroData2 = FiltroFactory.crea(campoDataAddebito,
                                    Filtro.Op.MINORE_UGUALE,
                                    this.getDataFine());
                            filtroDate = new Filtro();
                            filtroDate.add(filtroData1);
                            filtroDate.add(filtroData2);
                            filtro = new Filtro();
                            filtro.add(filtroAzienda);
                            filtro.add(filtroSottoconto);
                            filtro.add(filtroDate);

                            totale = (Double)modAddebito.query().somma(campoTot, filtro);
                            oggettoNodo.setImporto(totale);

                            /* incrementa il totale generale */
                            totGenerale += totale;

                            /* incrementa i totali Conto e Mastro */
                            this.regolaNodiParente(nodo);

                        }// fine del blocco if

                    }// fine del blocco if
                }// fine del blocco if
            }// fine del blocco for

            /* regola il valore del campo Totale */
            this.getPbRicaviLordi().setImporto(totGenerale);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Regola i totali del conto e del mastro relativi
     * al nodo di sottoconto passato.
     * <p/>
     *
     * @param nodoSottoconto per il quale regolare conto e mastro
     */
    private void regolaNodiParente(AlberoNodo nodoSottoconto) {
        /* variabili e costanti locali di lavoro */
        OggettoNodoRiepilogo objSottoconto;
        OggettoNodoRiepilogo objNodo;
        double importo;
        AlberoNodo nodoConto;
        AlberoNodo nodoMastro;

        try {    // prova ad eseguire il codice
            objSottoconto = (OggettoNodoRiepilogo)nodoSottoconto.getUserObject();
            importo = objSottoconto.getImporto();
            nodoConto = (AlberoNodo)nodoSottoconto.getParent();
            objNodo = (OggettoNodoRiepilogo)nodoConto.getUserObject();
            objNodo.setImporto(objNodo.getImporto() + importo);
            nodoMastro = (AlberoNodo)nodoConto.getParent();
            objNodo = (OggettoNodoRiepilogo)nodoMastro.getUserObject();
            objNodo.setImporto(objNodo.getImporto() + importo);
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

    }


    /**
     * Mostra gli addebiti del mastro/sottoconto/conto selezionato nell'albero.
     * <p/>
     * Apre il modulo Addebito con la lista addebiti relativa
     * al nodo correntemente selezionato nell'albero
     */
    private void mostraAddebiti() {
        /* variabili e costanti locali di lavoro */
        boolean continua = true;
        Albero albero;
        ArrayList<AlberoNodo> nodiSelezionati = null;
        AlberoNodo nodo;
        Object oggetto = null;
        OggettoNodoRiepilogo oggettoNodo;
        AlberoPC.TipoNodo tipo = null;
        int codice = 0;
        Modulo modFiltro = null;
        Modulo modAddebiti;
        Filtro filtroCodice;
        Campo campoDataAddebito;


        try {    // prova ad eseguire il codice

            albero = this.getAlbero();
            continua = albero != null;

            if (continua) {
                nodiSelezionati = albero.getNodiSelezionati();
                continua = (nodiSelezionati.size() == 1);
            }// fine del blocco if

            if (continua) {
                nodo = nodiSelezionati.get(0);
                oggetto = nodo.getUserObject();
                continua = ((oggetto != null) && (oggetto instanceof OggettoNodoRiepilogo));
            }// fine del blocco if

            if (continua) {
                oggettoNodo = (OggettoNodoRiepilogo)oggetto;
                tipo = oggettoNodo.getTipo();
                codice = oggettoNodo.getCodice();
                continua = ((tipo != null) && (codice > 0));
            }// fine del blocco if

            /* a questo punto ho un tipo di record e un codice validi */
            if (continua) {
                switch (tipo) {
                    case mastro:
                        modFiltro = Albergo.Moduli.AlbMastro();
                        break;
                    case conto:
                        modFiltro = Albergo.Moduli.AlbConto();
                        break;
                    case sottoconto:
                        modFiltro = Albergo.Moduli.AlbSottoconto();
                        break;
                    default: // caso non definito
                        continua = false;
                        break;
                } // fine del blocco switch
            }// fine del blocco if

            if (continua) {
                modAddebiti = Albergo.Moduli.Addebito();
                campoDataAddebito = modAddebiti.getCampo(Addebito.Cam.data.get());

                filtroCodice = FiltroFactory.crea(modFiltro.getCampoChiave(), codice);

                Filtro fdata1 = FiltroFactory.crea(campoDataAddebito,
                        Filtro.Op.MAGGIORE_UGUALE,
                        this.getDataInizio());

                Filtro fdata2 = FiltroFactory.crea(campoDataAddebito,
                        Filtro.Op.MINORE_UGUALE,
                        this.getDataFine());
                Filtro filtroData = new Filtro();
                filtroData.add(fdata1);
                filtroData.add(fdata2);

                Filtro filtro = new Filtro();
                filtro.add(filtroCodice);
                filtro.add(filtroData);

                /* mostra il navigatore */
                this.mostraAddebiti(filtro);

            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

    }


    /**
     * Mostra i pagamenti relativi alla riga correntemente selezionata.
     * <p/>
     * Apre il modulo Pagamento con la lista pagamenti relativa
     * alla riga correntemente selezionata nella tavola Incassi
     */
    private void mostraPagamenti() {
        /* variabili e costanti locali di lavoro */
        JTable tavola;
        int riga;
        Pagamento.TitoloPagamento titolo;
        TavolaIncassiModello modello;
        int codTitolo;
        Modulo modPagamenti;
        Campo campoDataPagamento;
        Filtro filtroTitolo = null;
        Filtro filtro;
        Pagamento.TitoloPagamento[] titoli;


        try {    // prova ad eseguire il codice
            tavola = this.getTavola();
            modello = (TavolaIncassiModello)tavola.getModel();
            riga = tavola.getSelectedRow();
            titolo = modello.getTitolo(riga);

            /* Crea un filtro per il titolo pagamento.
             * se il titolo è null (Altro), filtra solo i pagamenti diversi da quanto
             * previsto nella enum */
            if (titolo != null) {
                codTitolo = titolo.getCodice();
                filtroTitolo = FiltroFactory.crea(Pagamento.Cam.titolo.get(), codTitolo);
            } else {
                /* filtro per i pagamenti con titolo diverso da quanto previsto nella enum */
                titoli = Pagamento.TitoloPagamento.values();
                filtroTitolo = new Filtro();
                for (int k = 0; k < titoli.length; k++) {
                    titolo = titoli[k];
                    codTitolo = titolo.getCodice();
                    filtro = FiltroFactory.crea(Pagamento.Cam.titolo.get(),
                            Filtro.Op.DIVERSO,
                            codTitolo);
                    filtroTitolo.add(filtro);
                } // fine del ciclo for
            }// fine del blocco if-else

            modPagamenti = PagamentoModulo.get();
            campoDataPagamento = modPagamenti.getCampo(Pagamento.Cam.data.get());

            Filtro fdata1 = FiltroFactory.crea(campoDataPagamento,
                    Filtro.Op.MAGGIORE_UGUALE,
                    this.getDataInizio());

            Filtro fdata2 = FiltroFactory.crea(campoDataPagamento,
                    Filtro.Op.MINORE_UGUALE,
                    this.getDataFine());

            Filtro filtroData = new Filtro();
            filtroData.add(fdata1);
            filtroData.add(fdata2);

            Filtro filtroTot = new Filtro();
            filtroTot.add(filtroTitolo);
            filtroTot.add(filtroData);

            /* regola e mostra il navigatore */
            this.mostraPagamenti(filtroTot);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

    }


    /**
     * Mostra gli addebiti corrispondenti a un dato filtro
     * nel Navigatore Addebiti.
     * <p/>
     *
     * @param filtro di selezione dei record
     */
    private void mostraAddebiti(Filtro filtro) {
        this.mostraMovimenti(AddebitoModulo.get(), filtro);
    }


    /**
     * Mostra i pagamenti corrispondenti a un dato filtro
     * nel Navigatore Pagamenti.
     * <p/>
     *
     * @param filtro di selezione dei record
     */
    private void mostraPagamenti(Filtro filtro) {
        this.mostraMovimenti(PagamentoModulo.get(), filtro);
    }


    /**
     * Mostra gli sconti corrispondenti a un dato filtro
     * nel Navigatore Sconti.
     * <p/>
     *
     * @param filtro di selezione dei record
     */
    private void mostraSconti(Filtro filtro) {
        this.mostraMovimenti(ScontoModulo.get(), filtro);
    }


    /**
     * Mostra i sospesi corrispondenti a un dato filtro
     * nel Navigatore Sospesi.
     * <p/>
     *
     * @param filtro di selezione dei record
     */
    private void mostraSospesi(Filtro filtro) {
        this.mostraMovimenti(SospesoModulo.get(), filtro);
    }


    /**
     * Mostra i pagamenti corrispondenti a un dato filtro
     * nel Navigatore Pagamenti.
     * <p/>
     *
     * @param modMovi modulo movimenti da visualizzare
     * @param filtro di selezione dei record
     */
    private void mostraMovimenti(MovimentoModulo modMovi, Filtro filtro) {
        /* variabili e costanti locali di lavoro */
        Navigatore nav;
        Lista lista;

        try {    // prova ad eseguire il codice
            /* regola e mostra il navigatore
             * (il filtro base della lista e' già regolato per l'azienda corrente) */
            nav = modMovi.getNavigatoreCorrente();
            lista = nav.getLista();
            nav.avvia();
            lista.setFiltroCorrente(filtro);
            nav.aggiornaLista();
            nav.apriNavigatore();
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

    }


    /**
     * Carica i dati nella tavola incassi.
     * <p/>
     */
    private void aggiornaTavolaIncassi() {
        /* variabili e costanti locali di lavoro */
        double totale;
        double totaleSconti;
        double totGenerale = 0;

        try {    // prova ad eseguire il codice

            /* totalizza i titoli previsti nella enum */
            for (Pagamento.TitoloPagamento titolo : Pagamento.TitoloPagamento.values()) {
                totale = this.getTotPagamenti(titolo);
                this.setImportoTitolo(titolo, totale);
                totGenerale += totale;
            }

            /* regola il valore del pannello-bottone Totale Incassi*/
            this.getPbTotIncassi().setImporto(totGenerale);

            /* regola il pannello-bottone totale sconti */
            totaleSconti = this.getTotSconti();
            this.getPbSconti().setImporto(totaleSconti);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

    }


    /**
     * Aggiorna il valore dei sospesi.
     * <p/>
     */
    private void aggiornaSospesi() {
        /* variabili e costanti locali di lavoro */
        double importo;

        try {    // prova ad eseguire il codice
            importo = this.getTotSospesi();
            this.getPbSospesi().setImporto(importo);
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Aggiorna il valore dei pagamenti a titolo sconosciuto.
     * <p/>
     */
    private void aggiornaSconosciuti() {
        /* variabili e costanti locali di lavoro */
        double importo;

        try {    // prova ad eseguire il codice
            importo = this.getTotPagamenti(null);
            this.getPbSconosciuti().setImporto(importo);
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Aggiorna i valori calcolati del dialogo.
     * <p/>
     */
    private void aggiornaValoriCalcolati() {
        /* variabili e costanti locali di lavoro */
        double ricaviLordi;
        double sconti;
        double ricaviNetti;
        double totIncassi;

        try {    // prova ad eseguire il codice

            /* ricavi netti = ricavi lordi - sconti */
            ricaviLordi = this.getPbRicaviLordi().getImporto();
            sconti = this.getPbSconti().getImporto();
            this.getPbRicaviNetti().setImporto(ricaviLordi - sconti);

            /* riporto ricavi netti nel riepilogo */
            ricaviNetti = this.getPbRicaviNetti().getImporto();
            this.getPbRipRicaviNetti().setImporto(ricaviNetti);

            /* riporto totale incassi nel riepilogo */
            totIncassi = this.getPbTotIncassi().getImporto();
            this.getPbRipTotIncassi().setImporto(totIncassi);

            /* calcolo credito clienti */
            this.getPbCreditoClienti().setImporto(ricaviNetti - totIncassi);


        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Assegna un valore a un titolo di pagamento.
     * <p/>
     *
     * @param titolo di pagamento (dalla enum)
     * null per assegnare l'importo alla voce "Altro"
     * @param importo da assegnare
     */
    public void setImportoTitolo(Pagamento.TitoloPagamento titolo, Double importo) {
        /* variabili e costanti locali di lavoro */
        TavolaIncassiModello modello;

        try { // prova ad eseguire il codice
            modello = getModTavolaIncassi();
            if (modello != null) {
                modello.setImportoTitolo(titolo, importo);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Ritorna l'importo totale dei pagamenti del periodo corrente
     * per il titolo di pagamento specificato.
     * <p/>
     *
     * @param titolo di pagamento
     * null per il totale degli eventuali pagamenti a titolo non previsto nella enum
     *
     * @return il totale pagamenti
     */
    private double getTotPagamenti(Pagamento.TitoloPagamento titolo) {
        /* variabili e costanti locali di lavoro */
        double totale = 0;
        ContoModulo modConto;
        Modulo modPagamento;
        Filtro filtroAzienda;
        Filtro filtroTitolo;
        Filtro filtroDate;
        Filtro filtro;
        Campo campoImporto;
        int codTitolo;
        Number numero;


        try {    // prova ad eseguire il codice

            modPagamento = PagamentoModulo.get();
            modConto = ContoModulo.get();

            /* filtro per l'azienda attiva */
            filtroAzienda = modConto.getFiltroAzienda();

            /* filtro per il periodo */
            filtroDate = this.getFiltroMovimentiPeriodo();

            /* filtro titolo */
            if (titolo != null) {
                /* filtro per i pagamenti con titolo specificato */
                codTitolo = titolo.getCodice();
                filtroTitolo = FiltroFactory.crea(Pagamento.Cam.titolo.get(), codTitolo);
            } else {
                filtroTitolo = this.getFiltroPagamentiSconosciuti();
            }// fine del blocco if-else

            /* filtro complessivo */
            filtro = new Filtro();
            filtro.add(filtroAzienda);
            filtro.add(filtroTitolo);
            filtro.add(filtroDate);

            /* somma i pagamenti */
            campoImporto = modPagamento.getCampo(Pagamento.Cam.importo.get());
            numero = modPagamento.query().somma(campoImporto, filtro);
            totale = Libreria.getDouble(numero);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return totale;
    }


    /**
     * Ritorna l'importo totale dei movimenti di un dato tipo per il periodo corrente
     * <p/>
     *
     * @param modMovi modulo movimenti da totalizzare
     *
     * @return il totale movimenti
     */
    private double getTotMovimenti(Modulo modMovi) {
        /* variabili e costanti locali di lavoro */
        double totale = 0;
        ContoModulo modConto;
        Filtro filtroAzienda;
        Filtro filtroDate;
        Filtro filtro;
        Campo campoImporto;
        Number numero;


        try {    // prova ad eseguire il codice

            modConto = ContoModulo.get();

            /* filtro per l'azienda attiva */
            filtroAzienda = modConto.getFiltroAzienda();

            /* filtro per il periodo */
            filtroDate = this.getFiltroMovimentiPeriodo();

            /* filtro complessivo */
            filtro = new Filtro();
            filtro.add(filtroAzienda);
            filtro.add(filtroDate);

            /* somma i movimenti */
            campoImporto = modMovi.getCampo(Movimento.Cam.importo.get());
            numero = modMovi.query().somma(campoImporto, filtro);
            totale = Libreria.getDouble(numero);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return totale;
    }


    /**
     * Ritorna l'importo totale degli sconti per il periodo corrente
     * <p/>
     *
     * @return il totale sconti
     */
    private double getTotSconti() {
        return this.getTotMovimenti(ScontoModulo.get());
    }


    /**
     * Ritorna l'importo totale degli sconti per il periodo corrente
     * <p/>
     *
     * @return il totale sconti
     */
    private double getTotSospesi() {
        return this.getTotMovimenti(SospesoModulo.get());
    }


    protected void eventoMemoriaModificata(Campo campo) {
        /* variabili e costanti locali di lavoro */
        int sel;
        Albero albero;

        if (campo.equals(this.getCampoOpzioni())) {
            sel = (Integer)campo.getValore();
            albero = this.getAlbero();
            if (albero != null) {
                albero.setExpansionLevel(sel);
            }// fine del blocco if
        }// fine del blocco if
    }


    public void sincronizza() {
        boolean abilita;
        Albero albero;
        ArrayList<AlberoNodo> nodi;
        JTable tavola;
        int[] righe;

        super.sincronizza();    //To change body of overridden methods use File | Settings | File Templates.

        try { // prova ad eseguire il codice

            /* abilita il campo opzioni */
            abilita = (this.getAlbero() != null);
            this.getCampoOpzioni().setModificabile(abilita);

            /* abilita il pulsante stampa */
            abilita = (this.getAlbero() != null);
            this.getBottoneStampa().setEnabled(abilita);

            /* abiitazione bottone Esegui */
            abilita = (Lib.Data.isSequenza(this.getDataInizio(), this.getDataFine()));
            this.getBottoneEsegui().setEnabled(abilita);

            /* abiitazione bottone Mostra Addebiti
             * ci deve essere uno e un solo nodo selezionato */
            abilita = false;
            albero = this.getAlbero();
            if (albero != null) {
                nodi = albero.getNodiSelezionati();
                if (nodi.size() == 1) {
                    abilita = true;
                }// fine del blocco if
            }// fine del blocco if
            this.getBottoneAddebiti().setEnabled(abilita);

            /* abiitazione bottone Mostra Pagamenti
             * ci deve essere una e una sola riga selezionata */
            abilita = false;
            tavola = this.getTavola();
            if (tavola != null) {
                righe = tavola.getSelectedRows();
                abilita = (righe.length > 0);
            }// fine del blocco if
            this.getBottonePagamenti().setEnabled(abilita);


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    protected void stampaDialogo() {
        Albero albero;
        J2TreePrinter treePrinter;
        J2TablePrinter tablePrinter;
        J2ComponentPrinter compPrinter;
        J2ComponentPrinter titoloRicavi;
        J2ComponentPrinter titoloIncassi;
        JLabel label;
        J2Printer printer;
        J2FlowPrinter flowPrinter;
        String sData1, sData2;

        try { // prova ad eseguire il codice
            albero = this.getAlbero();
            if (albero != null) {

                sData1 = Lib.Data.getStringa(this.getDataInizio());
                sData2 = Lib.Data.getStringa(this.getDataFine());

                /* costruisce il Printer il titolo dell'albero Ricavi */
                label = new JLabel("Ricavi");
                label.setFont(FontFactory.creaPrinterFont(Font.BOLD, 18f));
                titoloRicavi = new J2ComponentPrinter(label);
                titoloRicavi.setHorizontalAlignment(J2Printer.LEFT);

                /* costruisce il Printer per l'Albero Ricavi */
                treePrinter = new J2TreePrinter(albero.getTree());
                treePrinter.setHorizontalAlignment(J2Printer.LEFT);

                /* costruisce il Printer il titolo della tavola Incassi */
                label = new JLabel("Incassi");
                label.setFont(FontFactory.creaPrinterFont(Font.BOLD, 18f));
                titoloIncassi = new J2ComponentPrinter(label);
                titoloIncassi.setHorizontalAlignment(J2Printer.LEFT);

                /* costruisce il Printer per la tavola Incassi */
                tablePrinter = new J2TablePrinter(this.getTavola());
                tablePrinter.setHorizontalAlignment(J2Printer.LEFT);

                /* crea un FlowPrinter e aggiunge i vari printers */
                flowPrinter = new J2FlowPrinter();

                /* l'albero ricavi */
                flowPrinter.addFlowable(titoloRicavi);
                flowPrinter.addFlowable(new VerticalGap(0.3));
                flowPrinter.addFlowable(treePrinter);

                /* i totali ricavi */
                flowPrinter.addFlowable(new VerticalGap(0.3));
                compPrinter = new J2ComponentPrinter(this.getPanTotRicavi().getPanFisso());
                flowPrinter.addFlowable(compPrinter);
                flowPrinter.addFlowable(new HorizontalLine(16, 2, 0));

                /* la tavola incassi */
                flowPrinter.addFlowable(titoloIncassi);
                flowPrinter.addFlowable(new VerticalGap(0.3));
                flowPrinter.addFlowable(tablePrinter);

                /* il totale incassi */
                flowPrinter.addFlowable(new VerticalGap(0.3));
                compPrinter = new J2ComponentPrinter(this.getPbTotIncassi().getPanFisso());
                flowPrinter.addFlowable(compPrinter);
                flowPrinter.addFlowable(new HorizontalLine(16, 2, 0));

                /* il riepilogo */
                compPrinter = new J2ComponentPrinter(this.getPanRiepilogo().getPanFisso());
                flowPrinter.addFlowable(compPrinter);

                /* crea e stampa il printer finale */
                printer = new J2Printer(Progetto.getPrintLicense());
                printer.setSeparatePrintThread(false);
                printer.setLeftMargin(.5);
                printer.setRightMargin(.5);
                printer.setScale(0.75);
                printer.setPageable(flowPrinter);
                printer.setPageImagesMonochrome(true);
                printer.setLeftHeader("");
                printer.setCenterHeader("Riepilogo di gestione dal " + sData1 + " al " + sData2);
                printer.setRightHeader("");
                printer.setCenterFooter("");
                printer.setRightFooter("Pagina ### di @@@");
                printer.setHeaderStyle(J2Printer.LINE);
                printer.setFooterStyle(J2Printer.LINE);
                printer.print();

            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Ritorna il campo Opzioni.
     * <p/>
     *
     * @return il campo opzioni
     */
    private Campo getCampoOpzioni() {
        return this.getCampo(nomeOpzioni);
    }


    /**
     * Ritorna la data di inizio impostata nel dialogo.
     * <p/>
     *
     * @return la data di inizio analisi
     */
    private Date getDataInizio() {
        return (Date)this.getCampo(ContoDialogoRiepilogo.nomeDataIni).getValore();
    }


    /**
     * Ritorna la data di fine impostata nel dialogo.
     * <p/>
     *
     * @return la data di fine analisi
     */
    private Date getDataFine() {
        return (Date)this.getCampo(ContoDialogoRiepilogo.nomeDataFine).getValore();
    }


    private Pannello getPanDettaglio() {
        return panDettaglio;
    }


    private void setPanDettaglio(Pannello panDettaglio) {
        this.panDettaglio = panDettaglio;
    }


    private Pannello getPanAlberoVendite() {
        return panAlberoVendite;
    }


    private void setPanAlberoVendite(Pannello panAlberoVendite) {
        this.panAlberoVendite = panAlberoVendite;
    }


    private Pannello getPanRiepilogo() {
        return panRiepilogo;
    }


    private void setPanRiepilogo(Pannello panRiepilogo) {
        this.panRiepilogo = panRiepilogo;
    }


    private Pannello getPanTotRicavi() {
        return panTotRicavi;
    }


    private void setPanTotRicavi(Pannello panTotRicavi) {
        this.panTotRicavi = panTotRicavi;
    }


    private Albero getAlbero() {
        return albero;
    }


    private void setAlbero(Albero albero) {
        this.albero = albero;
    }


    private JTable getTavola() {
        return tavola;
    }


    private void setTavola(JTable tavola) {
        this.tavola = tavola;
    }


    private TavolaIncassiModello getModTavolaIncassi() {
        return modTavolaIncassi;
    }


    private void setModTavolaIncassi(TavolaIncassiModello modTavolaIncassi) {
        this.modTavolaIncassi = modTavolaIncassi;
    }


    private JButton getBottoneEsegui() {
        return bottoneEsegui;
    }


    private void setBottoneEsegui(JButton bottoneEsegui) {
        this.bottoneEsegui = bottoneEsegui;
    }


    private JButton getBottoneAddebiti() {
        return bottoneAddebiti;
    }


    private void setBottoneAddebiti(JButton bottoneAddebiti) {
        this.bottoneAddebiti = bottoneAddebiti;
    }


    private JButton getBottonePagamenti() {
        return bottonePagamenti;
    }


    private void setBottonePagamenti(JButton bottonePagamenti) {
        this.bottonePagamenti = bottonePagamenti;
    }


    private PanBottone getPbRicaviLordi() {
        return pbRicaviLordi;
    }


    private void setPbRicaviLordi(PanBottone pbRicaviLordi) {
        this.pbRicaviLordi = pbRicaviLordi;
    }


    private PanBottone getPbSconti() {
        return pbSconti;
    }


    private void setPbSconti(PanBottone pbSconti) {
        this.pbSconti = pbSconti;
    }


    private PanBottone getPbRicaviNetti() {
        return pbRicaviNetti;
    }


    private void setPbRicaviNetti(PanBottone pbRicaviNetti) {
        this.pbRicaviNetti = pbRicaviNetti;
    }


    private PanBottone getPbTotIncassi() {
        return pbTotIncassi;
    }


    private void setPbTotIncassi(PanBottone pbTotIncassi) {
        this.pbTotIncassi = pbTotIncassi;
    }


    private PanBottone getPbRipRicaviNetti() {
        return pbRipRicaviNetti;
    }


    private void setPbRipRicaviNetti(PanBottone pbRipRicaviNetti) {
        this.pbRipRicaviNetti = pbRipRicaviNetti;
    }


    private PanBottone getPbRipTotIncassi() {
        return pbRipTotIncassi;
    }


    private void setPbRipTotIncassi(PanBottone pbRipTotIncassi) {
        this.pbRipTotIncassi = pbRipTotIncassi;
    }


    private PanBottone getPbCreditoClienti() {
        return pbCreditoClienti;
    }


    private void setPbCreditoClienti(PanBottone pbCreditoClienti) {
        this.pbCreditoClienti = pbCreditoClienti;
    }


    private PanBottone getPbSospesi() {
        return pbSospesi;
    }


    private void setPbSospesi(PanBottone pbSospesi) {
        this.pbSospesi = pbSospesi;
    }


    private PanBottone getPbSconosciuti() {
        return pbSconosciuti;
    }


    private void setPbSconosciuti(PanBottone pbSconosciuti) {
        this.pbSconosciuti = pbSconosciuti;
    }


    /**
     * Azione per eseguire il ricalcolo del riepilogo
     * </p>
     */
    private final class AzioneEsegui implements ActionListener {

        public void actionPerformed(ActionEvent actionEvent) {
            esegui();
        }
    } // fine della classe 'interna'


    /**
     * Azione per mostrare gli addebiti relativi al nodo
     * correntemente selezionato nell'albero
     */
    private final class AzioneAddebiti implements ActionListener {

        public void actionPerformed(ActionEvent actionEvent) {
            mostraAddebiti();
        }
    } // fine della classe 'interna'


    /**
     * Azione per mostrare i pagamenti relativi alla riga incassi
     * correntemente selezionata nella tavola
     */
    private final class AzionePagamenti implements ActionListener {

        public void actionPerformed(ActionEvent actionEvent) {
            mostraPagamenti();
        }
    } // fine della classe 'interna'


    /**
     * Azione eseguita quando si modifica la selezione nell'albero
     */
    private final class AzioneSelezioneAlbero implements TreeSelectionListener {

        public void valueChanged(TreeSelectionEvent evento) {
            sincronizza();
        }
    } // fine della classe 'interna'


    /**
     * Azione eseguita quando si modifica la selezione nella lista
     */
    private final class AzioneSelezioneLista implements ListSelectionListener {

        public void valueChanged(ListSelectionEvent evento) {
            sincronizza();
        }
    } // fine della classe 'interna'


    /**
     * Nodo per l'albero del piano dei conti.
     */
    private final class OggettoNodoRiepilogo extends OggettoNodoPC {

        /* importo del record */
        private double importo;


        /**
         * Costruttore completo con parametri. <br>
         */
        public OggettoNodoRiepilogo() {
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


        private double getImporto() {
            return importo;
        }


        public void setImporto(double importo) {
            this.importo = importo;
        }
    }  // fine della classe interna


    /**
     * Renderer specifico per i nodi dell'albero
     */
    private class RendererNodo extends DefaultTreeCellRenderer {

        /**
         * Intercetto il metodo per ottenere informazioni
         * sul nodo e regolare il testo del renderer
         */
        public Component getTreeCellRendererComponent(JTree tree,
                                                      Object value,
                                                      boolean sel,
                                                      boolean expanded,
                                                      boolean leaf,
                                                      int row,
                                                      boolean hasFocus) {

            /* variabili e costanti locali di lavoro */
            Component comp;
            AlberoNodo nodo;
            Object oggetto;
            OggettoNodoRiepilogo oggettoNodo;
            AlberoPC.TipoNodo tipoNodo = null;
            String testo = "";
            double importo = 0;
            Font font = AlberoPC.getFontSottoconto();  //default

            comp = super.getTreeCellRendererComponent(tree,
                    value,
                    sel,
                    expanded,
                    leaf,
                    row,
                    hasFocus);

            try { // prova ad eseguire il codice

                /* recupera i dati del nodo */
                nodo = (AlberoNodo)value;
                oggetto = nodo.getUserObject();
                if (oggetto != null) {
                    if (oggetto instanceof OggettoNodoRiepilogo) {
                        oggettoNodo = (OggettoNodoRiepilogo)oggetto;
                        tipoNodo = oggettoNodo.getTipo();
                        testo = oggettoNodo.getDescrizione();
                        importo = oggettoNodo.getImporto();
                    }// fine del blocco if
                }// fine del blocco if

                /* regolazioni in funzione del tipo di nodo */
                if (tipoNodo != null) {
                    switch (tipoNodo) {
                        case mastro:
                            font = AlberoPC.getFontMastro();
                            break;
                        case conto:
                            font = AlberoPC.getFontConto();
                            break;
                        case sottoconto:
                            font = AlberoPC.getFontSottoconto();
                            break;
                        default: // caso non definito
                            break;
                    } // fine del blocco switch
                }// fine del blocco if

                /* regolo il testo per il renderer */
                this.setText(testo);
                this.setFont(font);

                /* regolo il componente col numero */
                campoNum.setValue(importo);
                campoNum.setFont(font);

                /* aggiungo il componente originale e il numero
                 * al pannello da ritornare */
                panRenderer.removeAll();
                panRenderer.add(comp, BorderLayout.WEST);
                panRenderer.add(campoNum, BorderLayout.EAST);

            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

            /* valore di ritorno */
            return panRenderer;

        }

    }   // fine della classe interna


    /**
     * Modello dati per la tavola Incassi
     */
    private class TavolaIncassiModello extends AbstractTableModel {

        String[] columnNames = {"Titolo", "Importo"};

        ArrayList<Pagamento.TitoloPagamento> titoli;

        ArrayList<String> nomi;

        ArrayList<Double> importi;


        /**
         * Costruttore base
         */
        public TavolaIncassiModello() {

            try { // prova ad eseguire il codice

                titoli = new ArrayList<Pagamento.TitoloPagamento>();
                nomi = new ArrayList<String>();
                importi = new ArrayList<Double>();

                for (Pagamento.TitoloPagamento titolo : Pagamento.TitoloPagamento.values()) {
                    titoli.add(titolo);
                    nomi.add(titolo.getDescrizione());
                    importi.add(0.0);
                }

            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

        }// fine del metodo costruttore base


        public int getColumnCount() {
            return 2;
        }


        public int getRowCount() {
            return nomi.size();
        }


        public String getColumnName(int col) {
            return columnNames[col];
        }


        public Object getValueAt(int row, int col) {
            /* variabili e costanti locali di lavoro */
            Object valore = null;
            ArrayList lista = null;

            try { // prova ad eseguire il codice

                if (col == 0) {
                    lista = nomi;
                }// fine del blocco if

                if (col == 1) {
                    lista = importi;
                }// fine del blocco if

                valore = lista.get(row);

            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

            /* valore di ritorno */
            return valore;
        }


        /**
         * Assegna un valore a un titolo di pagamento.
         * <p/>
         *
         * @param titolo di pagamento (dalla enum)
         * null per assegnare l'importo alla voce "Altro"
         * @param importo da assegnare
         */
        public void setImportoTitolo(Pagamento.TitoloPagamento titolo, Double importo) {
            /* variabili e costanti locali di lavoro */
            int pos;

            try {    // prova ad eseguire il codice
                if (titolo != null) {
                    pos = titolo.ordinal();
                } else {
                    pos = nomi.size() - 1;
                }// fine del blocco if-else

                importi.set(pos, importo);
                this.fireTableDataChanged();

            } catch (Exception unErrore) {    // intercetta l'errore
                Errore.crea(unErrore);
            } // fine del blocco try-catch
        }


        /**
         * Ritorna il titolo di pagamento corrispondente a una riga.
         * <p/>
         *
         * @param riga la riga della tavola
         *
         * @return il titolo di pagamento corrispondente (null per la voce Altro)
         */
        public Pagamento.TitoloPagamento getTitolo(int riga) {
            /* variabili e costanti locali di lavoro */
            Pagamento.TitoloPagamento titolo = null;

            try {    // prova ad eseguire il codice
                titolo = titoli.get(riga);
            } catch (Exception unErrore) {    // intercetta l'errore
                Errore.crea(unErrore);
            } // fine del blocco try-catch

            /* valore di ritorno */
            return titolo;
        }


    }


    /**
     * Renderer per la colonna importi della JTable incassi
     */
    private static class RendererTabIncassi extends DefaultTableCellRenderer {


        protected void setValue(Object object) {
            /* variabili e costanti locali di lavoro */
            double doppio;
            String testo;

            if (object instanceof Double) {
                this.setHorizontalAlignment(JLabel.RIGHT);
                doppio = (Double)object;
                testo = formatoNumero.format(doppio);
                super.setValue(testo);
            } else {
                this.setHorizontalAlignment(JLabel.LEFT);
                super.setValue(object);
            }// fine del blocco if-else

        }


    }


    /**
     * Bottone con etichetta e importo cliccabile
     * </p>
     */
    public final class PanBottone extends PannelloFlusso {

        private String etichetta;

        private ActionListener listener;

        private JLabel label;

        private JButton bottone;

        private double importo;

        private Font fontPlain = FontFactory.creaScreenFont();

        private Font fontBold = FontFactory.creaScreenFont(Font.BOLD);


        /**
         * Costruttore completo con parametri. <br>
         *
         * @param etichetta dell'oggetto
         * @param listener da invocare quando il bottone viene premuto
         */
        public PanBottone(String etichetta, ActionListener listener) {
            /* rimanda al costruttore della superclasse */
            super(Layout.ORIENTAMENTO_ORIZZONTALE);

            /* regola le variabili di istanza coi parametri */
            this.setEtichetta(etichetta);
            this.setListener(listener);

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
            JLabel label;
            JButton bottone;
            Dimension dim;

            try { // prova ad eseguire il codice

                this.setAllineamento(Layout.ALLINEA_CENTRO);
                this.setUsaGapFisso(true);
                this.setGapPreferito(5);

                label = new JLabel(this.getEtichetta());
                this.setLabel(label);
                this.add(label);

                bottone = new JButton();
                this.setBottone(bottone);
                bottone.setFocusable(false);
                bottone.setHorizontalAlignment(SwingConstants.RIGHT);
                bottone.setMargin(new Insets(1, 1, 1, 1));
                bottone.setFont(fontPlain);
                bottone.setRolloverEnabled(true);
                dim = new Dimension(100, 22);
                bottone.setPreferredSize(dim);
                Lib.Comp.bloccaDim(bottone);

                /* regola gli attributi di default */
                this.setBold(false);
                this.setFeelClickable(true);
                this.setColor(Color.BLACK);

                bottone.addActionListener(this.getListener());
                this.add(bottone);

                this.setImporto(0);

            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

        }


        public double getImporto() {
            return importo;
        }


        /**
         * Assegna il valore da visualizzare nel bottone.
         * <p/>
         *
         * @param importo da visualizzare
         */
        public void setImporto(double importo) {
            /* variabili e costanti locali di lavoro */
            JButton bottone;
            NumberFormat nf;
            String stringa;

            try {    // prova ad eseguire il codice
                this.importo = importo;
                bottone = this.getBottone();
                nf = NumberFormat.getInstance();
                nf.setMinimumFractionDigits(2);
                stringa = nf.format(importo);
                bottone.setText(stringa);
            } catch (Exception unErrore) {    // intercetta l'errore
                new Errore(unErrore);
            } // fine del blocco try-catch
        }


        /**
         * Regola l'attributo Bold del carattere per etichetta e bottone.
         * <p/>
         *
         * @param bold true per il carattere bold, false per plain
         */
        public void setBold(boolean bold) {
            /* variabili e costanti locali di lavoro */
            JButton bottone;
            JLabel label;
            Font font;

            try {    // prova ad eseguire il codice

                bottone = this.getBottone();
                label = this.getLabel();
                if (bold) {
                    font = fontBold;
                } else {
                    font = fontPlain;
                }// fine del blocco if-else
                bottone.setFont(font);
                label.setFont(font);

            } catch (Exception unErrore) {    // intercetta l'errore
                new Errore(unErrore);
            } // fine del blocco try-catch
        }


        /**
         * Regola il colore di foreground per etichetta e bottone.
         * <p/>
         *
         * @param colore da assegnare
         */
        public void setColor(Color colore) {
            /* variabili e costanti locali di lavoro */
            JButton bottone;
            JLabel label;

            try {    // prova ad eseguire il codice

                bottone = this.getBottone();
                label = this.getLabel();
                bottone.setForeground(colore);
                label.setForeground(colore);

            } catch (Exception unErrore) {    // intercetta l'errore
                new Errore(unErrore);
            } // fine del blocco try-catch
        }


        /**
         * Regola l'apparenza cliccabile del bottone.
         * <p/>
         * (E' solo l'apparenza, se c'è una azione associata la esegue comunque)
         *
         * @param clickable true per apparire clickable, false per apparire non clickable
         */
        public void setFeelClickable(boolean clickable) {
            /* variabili e costanti locali di lavoro */
            JButton bottone;
            String tooltip;

            try {    // prova ad eseguire il codice
                bottone = this.getBottone();
                bottone.setRolloverEnabled(clickable);
                bottone.setContentAreaFilled(clickable);
                bottone.setOpaque(!clickable);
                if (clickable) {
                    tooltip = "Premere per mostrare i movimenti che formano questo importo";
                } else {
                    tooltip = "";
                }// fine del blocco if-else
                bottone.setToolTipText(tooltip);
            } catch (Exception unErrore) {    // intercetta l'errore
                new Errore(unErrore);
            } // fine del blocco try-catch
        }


        private String getEtichetta() {
            return etichetta;
        }


        private void setEtichetta(String etichetta) {
            this.etichetta = etichetta;
        }


        private ActionListener getListener() {
            return listener;
        }


        private void setListener(ActionListener listener) {
            this.listener = listener;
        }


        private JLabel getLabel() {
            return label;
        }


        private void setLabel(JLabel label) {
            this.label = label;
        }


        private JButton getBottone() {
            return bottone;
        }


        private void setBottone(JButton bottone) {
            this.bottone = bottone;
        }
    } // fine della classe 'interna'


}// fine della classe
