/**
 * Title:     DialogoChart
 * Copyright: Copyright (c) 2009
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      22-feb-2009
 */
package it.algos.albergo.sviluppopresenze;

import com.wildcrest.j2printerworks.J2ComponentPrinter;
import com.wildcrest.j2printerworks.J2Printer;
import it.algos.base.componente.bottone.BottoneDialogo;
import it.algos.base.dialogo.DialogoBase;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.pagina.Pagina;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;

import javax.swing.JComponent;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Dialogo per la visualizzazione di un grafico JFreeChart
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 22-feb-2009 ore 18.27.13
 */
class DialogoChart extends DialogoBase {

    /* chard viualizzato */
    private JFreeChart chart;

    /* componente contenente il grafico */
    private ChartPanel chartPanel;


    /**
     * Costruttore completo con parametri. <br>
     *
     * @param chart grafico da visualizzare
     */
    public DialogoChart(JFreeChart chart) {
        /* rimanda al costruttore della superclasse */
        super();

        /* regola le variabili di istanza coi parametri */
        this.setChart(chart);

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
        JFreeChart chart;
        ChartPanel chartPanel;

        try { // prova ad eseguire il codice

            this.getDialogo().setModal(false);
            this.setTitolo("Grafico");

            Pagina pagina = this.getLibro().getPagina(0);
            pagina.setBorder(null);

            chart = this.getChart();
            chartPanel = new ChartPanel(chart);
            chartPanel.setPreferredSize(new Dimension(800, 600));
            
            this.setChartPanel(chartPanel);
            this.addComponente(chartPanel);

            BottoneDialogo bot = new BottoneDialogo();
            bot.setText("Ruota");
            bot.setFocusable(false);
            bot.setIcon(Lib.Risorse.getIconaBase("Redo24"));
            bot.addActionListener(new AzRuota());
            this.addBottone(bot);
            
            this.addBottoneStampa();
            this.addBottoneChiudi();


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }// fine del metodo inizia


    /**
     * Ruota il grafico.
     * <p/>
     */
    private void ruota() {
        /* variabili e costanti locali di lavoro */
        JFreeChart chart;
        CategoryPlot plot;
        PlotOrientation currOrient;
        PlotOrientation newOrient;


        try {    // prova ad eseguire il codice
            chart = this.getChart();
            plot = (CategoryPlot)chart.getPlot();
            currOrient = plot.getOrientation();
            if (currOrient == PlotOrientation.VERTICAL) {
                newOrient = PlotOrientation.HORIZONTAL;
            } else {
                newOrient = PlotOrientation.VERTICAL;
            }// fine del blocco if-else

            plot.setOrientation(newOrient);

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch
}





    @Override
    /* invocato dal bottone stampa */
    protected void stampaDialogo() {
        /* variabili e costanti locali di lavoro */
        JComponent comp;
        J2ComponentPrinter cpr;
        J2Printer printer;

        try { // prova ad eseguire il codice

            comp = this.getChartPanel();
            cpr = new J2ComponentPrinter(comp);
            cpr.setHorizontalPageRule(J2ComponentPrinter.SHRINK_TO_FIT);
            cpr.setVerticalPageRule(J2ComponentPrinter.SHRINK_TO_FIT);


            /* crea e regola il Printer */
            printer = Lib.Stampa.getDefaultPrinter();
            printer.setOrientation(J2Printer.LANDSCAPE);
            printer.addPageable(cpr);

            /* stampa */
            printer.print();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Azione Ruota.
     * </p>
     */
    private final class AzRuota implements ActionListener {

        public void actionPerformed(ActionEvent actionEvent) {
            ruota();
        }
    } // fine della classe 'interna'



    private JFreeChart getChart() {
        return chart;
    }


    private void setChart(JFreeChart chart) {
        this.chart = chart;
    }


    private ChartPanel getChartPanel() {
        return chartPanel;
    }


    private void setChartPanel(ChartPanel chartPanel) {
        this.chartPanel = chartPanel;
    }
}// fine della classe
