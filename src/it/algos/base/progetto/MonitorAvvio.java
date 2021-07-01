/**
 * Copyright:    Copyright (c) 2003
 * Company:      Algos s.r.l.
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  alex
 * @version 1.0  /
 * Creato:       il 22-04-2008 alle 9.18
 */

package it.algos.base.progetto;

import it.algos.base.errore.Errore;
import it.algos.base.finestra.Finestra;
import it.algos.base.finestra.FinestraBase;

import javax.swing.*;
import java.awt.*;

/**
 * Monitor di avvio del programma
 * <p/>
 * Visualizza una finestra con la progress bar di avvio
 */
public final class MonitorAvvio {

    /* numero delle fasi di avvio
    * (per ogni fase si ha uno spazzolamento dei moduli
    * il fondo scala della progressbar è uguale
    * al numero di fasi per il numero di moduli)*/
    private static final int QUANTE_FASI = 3;

    /* finestra di monitor avvio del programma */
    private FinestraBase finestra;

    /* label superiore di monitor avvio del programma */
    private JLabel labelSopra;

    /* label inferiore di monitor avvio del programma */
    private JLabel labelSotto;

    /* progressbar di monitor avvio del programma */
    private JProgressBar progressbar;

    /* numero totale di moduli del programma */
    private int totModuli;


    /**
     * Costruttore completo senza parametri
     */
    public MonitorAvvio() {
        try { // prova ad eseguire il codice
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    } /* fine del metodo costruttore completo */


    /**
     * Regolazioni iniziali di riferimenti e variabili
     * Metodo chiamato direttamente dal costruttore
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        /* variabili e costanti locali di lavoro */
        String nome;
        JLabel label;
        JProgressBar bar;
        FinestraBase finestra;
        JPanel pan;

        try { // prova ad eseguire il codice

            nome = Progetto.getIstanza().getNomeProgramma();

            /* crea e registra la JLabel superiore */
            label = new JLabel("Avvio " + nome + " in corso...");
            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setOpaque(false);
            label.setBackground(Color.red);
            label.setPreferredSize(new Dimension(200, 25));
            this.setLabelSopra(label);

            /* crea e registra la JLabel inferiore */
            label = new JLabel();
            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setOpaque(false);
            label.setBackground(Color.red);
            label.setPreferredSize(new Dimension(200, 25));
            this.setLabelSotto(label);

            /* crea e registra la ProgressBar */
            bar = new JProgressBar();
            bar.setStringPainted(false);
            bar.setIndeterminate(true);
            this.setProgressbar(bar);

            /* crea il pannello con i contenuti */
            pan = new JPanel();
            pan.setLayout(new BorderLayout());
            pan.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            pan.setOpaque(false);
            pan.setBackground(Color.YELLOW);
            pan.add(this.getLabelSopra(), BorderLayout.NORTH);
            pan.add(bar);
            pan.add(this.getLabelSotto(), BorderLayout.SOUTH);

            /* crea e registra la finestra */
            finestra = new FinestraBase();
            finestra.setUndecorated(true);
            finestra.setSize(new Dimension(300, 100));
            this.setFinestra(finestra);

            /* aggiunge il contenuto alla finestra */
            finestra.add(pan);

            /* visualizza il monitor */
            this.show();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch


    } /* fine del metodo inizia */


    /**
     * Visualizza il monitor.
     * <p/>
     */
    private void show() {
        /* variabili e costanti locali di lavoro */
        FinestraBase finestra;

        try {    // prova ad eseguire il codice

            finestra = this.getFinestra();
            finestra.centra();
            finestra.setVisible(true);

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Aggiorna la descrizione della fase di avvio.
     * <p/>
     *
     * @param fase stringa che descrive la fase di lavoro
     */
    public void setFase(String fase) {
        /* variabili e costanti locali di lavoro */
        JLabel label;

        try {    // prova ad eseguire il codice
            label = this.getLabelSotto();
            label.setText(fase);
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Aggiorna il monitor.
     * <p/>
     * Incrementa il valore corrente della progressbar di 1
     */
    public void update() {
        /* variabili e costanti locali di lavoro */
        JProgressBar bar;

        try {    // prova ad eseguire il codice

            /* incrementa il valore corrente di 1*/
            bar = this.getProgressbar();
            bar.setValue(bar.getValue() + 1);

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Chiude la finestra di monitor.
     * <p/>
     */
    public void close() {
        /* variabili e costanti locali di lavoro */
        Finestra finestra;

        try {    // prova ad eseguire il codice
            finestra = this.getFinestra();
            if (finestra != null) {
                finestra.setVisible(false);
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

    }


    private FinestraBase getFinestra() {
        return finestra;
    }


    private void setFinestra(FinestraBase finestra) {
        this.finestra = finestra;
    }


    private JLabel getLabelSopra() {
        return labelSopra;
    }


    private void setLabelSopra(JLabel labelSopra) {
        this.labelSopra = labelSopra;
    }


    private JLabel getLabelSotto() {
        return labelSotto;
    }


    private void setLabelSotto(JLabel labelSotto) {
        this.labelSotto = labelSotto;
    }


    private JProgressBar getProgressbar() {
        return progressbar;
    }


    private void setProgressbar(JProgressBar progressbar) {
        this.progressbar = progressbar;
    }


    private int getTotModuli() {
        return totModuli;
    }


    /**
     * Assegna il numero totale di moduli che verranno elaborati
     * <p/>
     * Regola di conseguenza la ProgressBar
     *
     * @param totModuli numero di moduli elaborati
     */
    public void setTotModuli(int totModuli) {
        /* variabili e costanti locali di lavoro */
        JProgressBar bar;
        int barMax;

        try { // prova ad eseguire il codice

            this.totModuli = totModuli;
            bar = this.getProgressbar();
            if (bar != null) {
                barMax = totModuli * QUANTE_FASI;
                bar.setMaximum(barMax);
                bar.setIndeterminate(false);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


}// fine della classe