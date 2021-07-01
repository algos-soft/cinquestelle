/**
 * Title:     PanComandi
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      9-feb-2009
 */
package it.algos.albergo.sviluppopresenze;

import it.algos.base.errore.Errore;
import it.algos.base.layout.Layout;
import it.algos.base.libreria.Lib;

import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Pannello per i comandi del dialogo di analisi sviluppo presenze
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 9-feb-2009 ore 12.38.23
 */
class PanComandi extends PanDialogo {

    JButton botStampa, botEsporta, botGraficoPres, botGraficoVal, botMostra;


    /**
     * Costruttore completo con parametri.
     * <p/>
     *
     * @param dialogo dialogo di riferimento
     */
    public PanComandi(SviluppoDialogo dialogo) {
        /* rimanda al costruttore della superclasse */
        super(dialogo, Layout.ORIENTAMENTO_ORIZZONTALE);

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
        JButton bot;

        try { // prova ad eseguire il codice

            this.setGapMinimo(5);
            this.setGapPreferito(10);
            this.setGapMassimo(100);

            this.creaBordo();

            bot = new JButton("Stampa");
            bot.setToolTipText("Stampa il risultato dell'analisi");
            this.setBotStampa(bot);
            bot.addActionListener(new AzStampa());
            this.regolaBottone(bot);
            this.add(bot);

            bot = new JButton("Esporta");
            bot.setToolTipText("Esporta su file il risultato dell'analisi");
            this.setBotEsporta(bot);
            bot.addActionListener(new AzEsporta());
            this.regolaBottone(bot);
            this.add(bot);

            bot = new JButton("Grafico presenze");
            bot.setToolTipText("Costruisce una rappresentazione grafica delle presenze");
            this.setBotGraficoPres(bot);
            bot.addActionListener(new AzGraficoPresenze());
            this.regolaBottone(bot);
            Lib.Comp.setPreferredWidth(bot, 140);
            this.add(bot);

            bot = new JButton("Grafico valori");
            bot.setToolTipText("Costruisce una rappresentazione grafica dei valori");
            this.setBotGraficoVal(bot);
            bot.addActionListener(new AzGraficoValori());
            this.regolaBottone(bot);
            Lib.Comp.setPreferredWidth(bot, 140);
            this.add(bot);


            bot = new JButton("Mostra");
            bot.setToolTipText(
                    "Mostra la lista delle prenotazioni che concorrono a formare le righe selezionata");
            this.setBotMostra(bot);
            bot.addActionListener(new AzMostra());
            this.regolaBottone(bot);
            this.add(bot);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch


    }// fine del metodo inizia


    /**
     * Regolazioni standard dei bottoni di comando.
     * <p/>
     * @param bot bottone da regolare
     */
    private void regolaBottone (JButton bot) {
        bot.setOpaque(false);
        Lib.Comp.setPreferredWidth(bot, 80);
    }





    /**
     * .
     * <p/>
     */
    public void sincronizza() {
        /* variabili e costanti locali di lavoro */
        boolean esistonoDati;
        boolean isRigheSelezionate;

        try {    // prova ad eseguire il codice

            esistonoDati = this.getDialogoMain().isEsistonoDati();
            isRigheSelezionate = (this.getDialogoMain().getQuanteRigheSelezionate()) > 0;

            this.getBotStampa().setEnabled(esistonoDati);
            this.getBotEsporta().setEnabled(esistonoDati);
            this.getBotGraficoPres().setEnabled(esistonoDati);
            this.getBotGraficoVal().setEnabled(esistonoDati);
            this.getBotMostra().setEnabled(isRigheSelezionate);

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

    }


    /**
     * Stampa.
     * <p/>
     * Invocato dal bottone
     */
    private void stampa() {
        this.getDialogoMain().stampa();
    }


    /**
     * Esporta.
     * <p/>
     * Invocato dal bottone
     */
    private void esporta() {
        this.getDialogoMain().esporta();
    }


    /**
     * Grafico Presenze.
     * <p/>
     * Invocato dal bottone
     */
    private void graficoPresenze() {
        this.getDialogoMain().graficoPresenze();
    }

    /**
     * Grafico Valori.
     * <p/>
     * Invocato dal bottone
     */
    private void graficoValori() {
        this.getDialogoMain().graficoValori();
    }



    /**
     * Mostra.
     * <p/>
     * Invocato dal bottone
     */
    private void mostra() {
        this.getDialogoMain().mostra();
    }


    /**
     * Azione Stampa Risultati
     */
    private final class AzStampa implements ActionListener {

        public void actionPerformed(ActionEvent event) {
            stampa();
        }
    } // fine della classe interna

    /**
     * Azione Esporta Risultati
     */
    private final class AzEsporta implements ActionListener {

        public void actionPerformed(ActionEvent event) {
            esporta();
        }
    } // fine della classe interna

    /**
     * Azione Grafico Presenze
     */
    private final class AzGraficoPresenze implements ActionListener {

        public void actionPerformed(ActionEvent event) {
            graficoPresenze();
        }
    } // fine della classe interna

    /**
     * Azione Grafico Valori
     */
    private final class AzGraficoValori implements ActionListener {

        public void actionPerformed(ActionEvent event) {
            graficoValori();
        }
    } // fine della classe interna


    /**
     * Azione Mostra Sorgenti
     */
    private final class AzMostra implements ActionListener {

        public void actionPerformed(ActionEvent event) {
            mostra();
        }
    } // fine della classe interna


    private JButton getBotStampa() {
        return botStampa;
    }


    private void setBotStampa(JButton botStampa) {
        this.botStampa = botStampa;
    }


    private JButton getBotEsporta() {
        return botEsporta;
    }


    private void setBotEsporta(JButton botEsporta) {
        this.botEsporta = botEsporta;
    }


    private JButton getBotGraficoPres() {
        return botGraficoPres;
    }


    private void setBotGraficoPres(JButton botGraficoPres) {
        this.botGraficoPres = botGraficoPres;
    }


    private JButton getBotGraficoVal() {
        return botGraficoVal;
    }


    private void setBotGraficoVal(JButton botGraficoVal) {
        this.botGraficoVal = botGraficoVal;
    }


    private JButton getBotMostra() {
        return botMostra;
    }


    private void setBotMostra(JButton botMostra) {
        this.botMostra = botMostra;
    }
}// fine della classe