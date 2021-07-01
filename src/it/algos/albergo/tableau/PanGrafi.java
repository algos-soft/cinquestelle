/**
 * Title:     TbGraph
 * Copyright: Copyright (c) 2009
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      24-feb-2009
 */
package it.algos.albergo.tableau;

import com.wildcrest.j2printerworks.J2ComponentPrinter;
import it.algos.albergo.AlbergoLib;
import it.algos.base.errore.Errore;
import it.algos.base.layout.Layout;
import it.algos.base.libreria.Lib;
import it.algos.base.pannello.PannelloFlusso;
import it.algos.base.stampa.Printer;
import it.algos.base.wrapper.DueDate;
import it.algos.base.wrapper.DueInt;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.util.Date;

/**
 * Componente di visualizzazione delle prenotazioni.
 * </p>
 * Contiene uno scroll pane con:
 * - la barra dei giorni come ColumnHeder,
 * - la barra delle Risorse come RowHeder,
 * - il grafo nel viewport
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 24-feb-2009 ore 9.11.17
 */
class PanGrafi extends JPanel {

    /* Tableau di riferimento */
    private Tableau tableau;

    /* scroll pane */
    private JScrollPane scrollPane;

    /* barra con le risorse */
    private TbBarRisorse barRisorse;

    /* barra del tempo */
    private BarraTempo barraTempo;

    /* grafo delle prenotazioni */
    private GrafoPrenotazioni grafoPrenotazioni;

    /* fattore di scala corrente */
    private double currScale;

    /* estremi del periodo correntemente visualizzato */
    private Date data1, data2;


    /**
     * Costruttore completo.
     * <p/>
     *
     * @param tableau tableau di riferimento
     * @param data1 inizio periodo
     * @param data2 fine periodo
     */
    public PanGrafi(Tableau tableau, Date data1, Date data2) {
        /* rimanda al costruttore della superclasse */
        super();

        this.setTableau(tableau);
        this.setData1(data1);
        this.setData2(data2);

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            new Errore(unErrore);
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
        /* variabili e costanti locali di lavoro */
        JScrollPane scrollPane;
        GrafoPrenotazioni graph;
        BarraTempo barTempo;
        TbBarRisorse barRisorse;

        try { // prova ad eseguire il codice

            /**
             * controlla che le date siano specificate correttamente
             * se non lo sono usa il default
             */
            DueDate periodo = new DueDate(this.getData1(), this.getData2());
            if (!periodo.isSequenza()) {
                Date d1 = AlbergoLib.getDataProgramma();
                Date d2 = Lib.Data.add(d1, 30);
                this.setData1(d1);
                this.setData1(d2);
            }// fine del blocco if

            this.setCurrScale(1);

            /* uso un BorderLayout specializzato per considerare la scroll bar verticale */
            this.setLayout(new LayoutPanGrafi());

            /* crea le barre ed il grafo */
            barTempo = this.creaBarTempo();   // barra tempo
            barRisorse = this.creaBarRisorse();   // barra risorse
            graph = this.creaTbGraph();         // grafo

            /* pone la dimensione del grafo in base alle barre */
            int w = barTempo.getPreferredSize().width;
            int h = barRisorse.getPreferredSize().height;
            graph.setPreferredSize(new Dimension(w, h));

            /* crea uno scroll pane */
            scrollPane = new JScrollPane();
            this.setScrollPane(scrollPane);

            /* le barre ed il grafo vengono inserite in uno scroll pane */
            this.impagina();

            /* lo scroll pane viene aggiunto a questo pannello */
            this.add(scrollPane);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }// fine del metodo inizia


    /**
     * Crea e registra la barra risorse.
     * <p/>
     *
     * @return la barra risorse
     */
    private TbBarRisorse creaBarRisorse() {
    	TbBarRisorse bar = new TbBarRisorse(this);
        this.setBarRisorse(bar);
        return bar;
    }


    /**
     * Crea e registra la barra del tempo.
     * <p/>
     *
     * @return la barra del tempo
     */
    private BarraTempo creaBarTempo() {
        BarraTempo bar = new BarraTempo(this);
        this.setBarTempo(bar);
        return bar;
    }


    /**
     * Crea e registra il componente grafico.
     * <p/>
     *
     * @return il componente grafico
     */
    private GrafoPrenotazioni creaTbGraph() {
        GrafoPrenotazioni comp = new GrafoPrenotazioni(this);
        this.setGrafoPrenotazioni(comp);
        return comp;
    }


    /**
     * Inserisce i componenti nello scroll pane.
     * <p/>
     */
    private void impagina() {
        /* variabili e costanti locali di lavoro */
        JScrollPane scrollPane;
        GrafoPrenotazioni graph;
        BarraTempo barTempo;
        TbBarRisorse barRisorse;
        PanGradientRounded pgr;

        try {    // prova ad eseguire il codice

            /* recupera i componenti */
            scrollPane = this.getScrollPane();
            graph = this.getGrafoPrenotazioni();
            barTempo = this.getBarTempo();
            barRisorse = this.getBarRisorse();


            /* pannello sfumato grigio per l'angolo superiore sinistro */
            pgr = new PanGradientRounded();
            pgr.setRoundRadius(0);

            /* Inserisce i componenti nello scroll pane */
            scrollPane.setViewportView(graph);
            scrollPane.setColumnHeaderView(barTempo);
            scrollPane.setRowHeaderView(barRisorse);
            scrollPane.setCorner(JScrollPane.UPPER_LEFT_CORNER, pgr);


        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Ricarica il grafo del tempo e il grafo dei periodi
     * in base alle date correnti
     * <p/>
     * Quello delle risorse no perché è sempre uguale e viene caricato
     * una sola volta in costruzione. (da rivedere)
     */
    public void reload() {
        this.getBarRisorse().reload();
        this.getBarTempo().reload();
        this.getGrafoPrenotazioni().reload();
    }


    /**
     * Stampa il tableau.
     * <p/>
     */
    public void stampa() {

        /* variabili e costanti locali di lavoro */
        PanGrafi pg;
        PannelloFlusso panSopra;
        PannelloFlusso panSotto;


        try { // prova ad eseguire il codice

            /* crea un nuovo pan grafi */
            pg = new PanGrafi(this.getTableau(),this.getData1(),this.getData2());

            /* crea la barra dei giorni sopra */
            panSopra = new PannelloFlusso(Layout.ORIENTAMENTO_ORIZZONTALE);
            panSopra.setUsaGapFisso(true);
            panSopra.setGapPreferito(0);
            panSopra.add(this.creaPanAngolo(pg));
            panSopra.add(new BarraTempo(this));
            panSopra.add(this.creaPanAngolo(pg));
            
            /* crea la barra dei giorni sotto */
            panSotto = new PannelloFlusso(Layout.ORIENTAMENTO_ORIZZONTALE);
            panSotto.setUsaGapFisso(true);
            panSotto.setGapPreferito(0);
            panSotto.add(this.creaPanAngolo(pg));
            panSotto.add(new BarraTempo(this,true));
            panSotto.add(this.creaPanAngolo(pg));

            /* crea il pannello da stampare */
            JPanel panel = new JPanel();
            panel.setLayout(new BorderLayout());
            panel.add(new TbBarRisorse(this), BorderLayout.LINE_START);
            panel.add(panSopra, BorderLayout.PAGE_START);
            panel.add(pg.getGrafoPrenotazioni(), BorderLayout.CENTER);
            panel.add(panSotto, BorderLayout.PAGE_END);
            panel.add(new TbBarRisorse(this), BorderLayout.LINE_END);

            /* crea un printer */
            Printer printer = new Printer();
//            printer.setOrientation(J2Printer.LANDSCAPE);
            printer.setCrossPlatformDialogs(true);
            printer.removeHeader();
            printer.removeFooter();

            /* crea un ComponentPrinter e lo aggiunge */
            J2ComponentPrinter componentPrinter = new J2ComponentPrinter(panel);

             //in orizzontale, (giorni), crea le pagine che occorrono
            componentPrinter.setHorizontalPageRule(J2ComponentPrinter.TILE);

            // in verticale (risorse), se adatta a pagina è acceso fa stare le risorse
            // nell'altezza di 1 pagina
            // se no scala tutta la stampa a una misura leggibile, che stampata mi da' 
            // quadratini da 5 mm di lato circa
            if (this.getTableau().isAdatta()) {
                componentPrinter.setVerticalPageRule(J2ComponentPrinter.SHRINK_TO_FIT);
            } else {
                componentPrinter.setVerticalPageRule(J2ComponentPrinter.TILE);
                printer.setScale(0.642d);
            }// fine del blocco if-else


            printer.addPageable(componentPrinter);

            /* presenta il print preview (dal quale si regola e si stampa)*/
            printer.showPrintPreviewDialog();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }

    /**
     * Crea un pannello d'angolo per il layout di stampa.
     * <p/>
     * @return il pannello d'angolo
     */
    private JPanel creaPanAngolo(PanGrafi pg) {

        /* variabili e costanti locali di lavoro */
        JPanel pan=new JPanel();

        try {    // prova ad eseguire il codice
            int wPan = pg.getBarRisorse().getPreferredSize().width;
            int hPan = pg.getBarTempo().getPreferredSize().height;
            pan.setPreferredSize(new Dimension(wPan, hPan));
            Lib.Comp.bloccaDim(pan);

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return pan;
    }



    /**
     * Regola la scala.
     * <p/>
     *
     * @param scale il fattore di scala, 1=100%
     */
    void setScale(double scale) {
        /* variabili e costanti locali di lavoro */

        try { // prova ad eseguire il codice
            this.setCurrScale(scale);
            this.getGrafoPrenotazioni().setScale(scale);
            this.getBarRisorse().setScale(scale);
            this.getBarTempo().setScale(scale);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Verifica se il contenuto (ciò che sta dentro lo scroll pane)
     * è più piccolo del contenitore (lo scroll pane stesso).
     * <p/>
     *
     * @return true se il contenuto è più piccolo del contenitore
     */
    public boolean isUnderSize() {
        /* variabili e costanti locali di lavoro */
        boolean under = false;

        try {    // prova ad eseguire il codice
            Dimension dimRealScroll = this.getScrollPane().getSize();
            Dimension dimScroll = this.getScrollPane().getPreferredSize();
            under = !Lib.Mat.isContenuta(dimScroll, dimRealScroll);
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return under;
    }


    /**
     * Ritorna il fattore di scala ottimale in modo da far stare
     * tutta la barra tempo nella finestra.
     * <p/>
     *
     * @return il fattore di scala ottimale
     */
    public double getBestScale() {
        /* variabili e costanti locali di lavoro */
        double newScale = 0;
        double scale;

        try {    // prova ad eseguire il codice

            int larBarTempo = getBarTempo().getPreferredSize().width;
            int larView = this.getScrollPane().getViewport().getSize().width;
            scale = (double)larView / (double)larBarTempo;

            double currScale = this.getCurrScale();
            newScale = scale * currScale;

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return newScale;
    }


    /**
     * Ritorna le coordinate di partenza (riga/colonna) di un periodo nel grafo.
     * <p/>
     *
     * @param idRisorsa codice della risorsa
     * @param d1 data di inizio periodo
     *
     * @return DueInt
     *         - il primo è il numero della riga (0 per la prima),
     *         - il secondo è il numero della colonna (0 per la prima)
     */
    public DueInt getPosPeriodo(int idRisorsa, Date d1) {
        /* variabili e costanti locali di lavoro */
        DueInt result = null;
        try {    // prova ad eseguire il codice
            int posRisorsa = this.getBarRisorse().getPos(idRisorsa);
            int posGiorno = this.getBarTempo().getGrafoGiorni().getPos(d1);
            result = new DueInt(posRisorsa, posGiorno);
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return result;
    }


    /**
     * Recupera il nome di una risorsa dal codice.
     * <p/>
     *
     * @param idRisorsa id della risorsa
     *
     * @return il nome della risorsa
     */
    public String getNomeRisorsa(int idRisorsa) {
        return this.getBarRisorse().getNomeRisorsa(idRisorsa);
    }


    /**
     * Ritorna il numero di risorse.
     * <p/>
     *
     * @return il numero di risorse
     */
    public int getNumRisorse() {
        return this.getBarRisorse().getNumRisorse();
    }


    /**
     * Ritorna il numero di giorni.
     * <p/>
     *
     * @return il numero di giorni
     */
    public int getNumGiorni() {
        return this.getBarTempo().getGrafoGiorni().getNumGiorni();
    }


    /**
     * Entra in modalità full-screen.
     * <p/>
     */
    private void enterFullScreen() {
        this.getTableau().enterFullScreen();
    }


    /**
     * Esce dalla modalità full-screen.
     * <p/>
     */
    private void exitFullScreen() {
        this.getTableau().exitFullScreen();
    }


    public Tableau getTableau() {
        return tableau;
    }


    private void setTableau(Tableau tableau) {
        this.tableau = tableau;
    }


    public JScrollPane getScrollPane() {
        return scrollPane;
    }


    private void setScrollPane(JScrollPane scrollPane) {
        this.scrollPane = scrollPane;
    }


    public TbBarRisorse getBarRisorse() {
        return barRisorse;
    }


    private void setBarRisorse(TbBarRisorse barRisorse) {
        this.barRisorse = barRisorse;
    }

    BarraTempo getBarTempo() {
        return barraTempo;
    }

    private void setBarTempo(BarraTempo barraTempo) {
        this.barraTempo = barraTempo;
    }

    GrafoPrenotazioni getGrafoPrenotazioni() {
        return grafoPrenotazioni;
    }


    private void setGrafoPrenotazioni(GrafoPrenotazioni grafoPrenotazioni) {
        this.grafoPrenotazioni = grafoPrenotazioni;
    }


    private double getCurrScale() {
        return currScale;
    }


    private void setCurrScale(double currScale) {
        this.currScale = currScale;
    }


    public Date getData1() {
        return data1;
    }


    public void setData1(Date data1) {
        this.data1 = data1;
    }


    public Date getData2() {
        return data2;
    }


    public void setData2(Date data2) {
        this.data2 = data2;
    }


    /**
     * Layout specializzato.
     * </p>
     * Aggionge alla larghezza del componente la largheza della scroll bar
     * per evitare che la presenza della scroll bar verticale, che porta via larghezza,
     * faccia comparire la scroll bar orizzontale.
     */
    private class LayoutPanGrafi extends BorderLayout {

        @Override
        public Dimension preferredLayoutSize(Container container) {
            /* variabili e costanti locali di lavoro */
            Dimension dim;
            int barW;

            /* recupero la larghezza delle scroll bar sul S.O. corrente */
            UIDefaults uidef = UIManager.getDefaults();
            barW = Integer.parseInt(uidef.get("ScrollBar.width").toString());

            dim= super.preferredLayoutSize(container);
            double w = dim.getWidth();
            double h = dim.getHeight();

            /* valore di ritorno */
            return new Dimension((int)w+barW, (int)(h));
        }


    } // fine della classe 'interna'

}// fine della classe