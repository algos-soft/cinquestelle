/**
 * Title:     StatElemento
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      24-giu-2009
 */
package it.algos.albergo.statistiche;

import it.algos.base.componente.WrapTextArea;
import it.algos.base.costante.CostanteColore;
import it.algos.base.errore.Errore;
import it.algos.base.font.FontFactory;
import it.algos.base.layout.Layout;
import it.algos.base.libreria.Lib;
import it.algos.base.pannello.PannelloFlusso;

import javax.swing.JButton;
import javax.swing.JTextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Singolo elemento del pannello di lancio Statistiche.
 * </p>
 * Contiene una breve spiegazione del tipo di statistica e il bottone di lancio.
 * Quando il bottone di lancio viene premuto chiude il dialogo di riferimento
 * e lancia la funzionalifà associata.
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 24-giu-2009 ore 22.12.47
 */
public final class StatElemento extends PannelloFlusso {

    StatDialogo dialogo;
    String testo;
    String spiega;
    ActionListener listener;

    /**
     * Costruttore completo con parametri.
     * <p>
     *
     * @param dialogo di riferimento
     * @param testo testo per il bottone
     * @param spiega spiegazione della statistica
     * @param listener associato al bottone
     */
    public StatElemento(StatDialogo dialogo, String testo, String spiega, ActionListener listener) {
        /* rimanda al costruttore della superclasse */
        super(Layout.ORIENTAMENTO_VERTICALE);

        this.setDialogo(dialogo);
        this.setTesto(testo);
        this.setSpiega(spiega);
        this.setListener(listener);

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

        try { // prova ad eseguire il codice
            this.setAllineamento(Layout.ALLINEA_CENTRO);
            this.setUsaGapFisso(true);
            this.setGapPreferito(5);

            /* crea il bottone e l'area spiega e le aggiunge */
            this.add(this.creaBottone());
            this.add(this.creaAreaSpiega());

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }// fine del metodo inizia


    /**
     * Crea il bottone con l'azione.
     * <p/>
     * @return il bottone creato
     */
    private JButton creaBottone () {
        /* variabili e costanti locali di lavoro */
        JButton bot=null;

        try {    // prova ad eseguire il codice
            bot = new JButton(this.getTesto());
            Lib.Comp.setPreferredWidth(bot, 200);
            Lib.Comp.bloccaLarghezza(bot);
            bot.addActionListener(this.getListener());
            bot.setOpaque(false);

            /**
             * aggoinge al bottone un altro listener per
             * chiudere il dialogo quando viene premuto
             */
            bot.addActionListener(new AzChiudiDialogo());

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return bot;
    }

    /**
     * Crea l'area di testo con la spiegazione.
     * <p/>
     * @return l'area di testo
     */
    private JTextArea creaAreaSpiega () {
        /* variabili e costanti locali di lavoro */
        WrapTextArea area=null;

        try {    // prova ad eseguire il codice
            area = new WrapTextArea(this.getSpiega());
//            area = new JTextArea(this.getSpiega(),0,20);
            area.setEditable(false);
            area.setOpaque(false);
            area.setWidth(200);
            area.setFont(FontFactory.creaScreenFont(11.5f));
            area.setForeground(CostanteColore.BLU);
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return area;
    }


    /**
     * E'stato premuto uno dei bottoni del dialogo.
     * <p/>
     * Chiude il dialogo
     */
    private void bottonePremuto () {
        try {    // prova ad eseguire il codice
            this.getDialogo().getDialogo().setVisible(false);
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

    }





    private StatDialogo getDialogo() {
        return dialogo;
    }


    private void setDialogo(StatDialogo dialogo) {
        this.dialogo = dialogo;
    }


    private String getTesto() {
        return testo;
    }


    private void setTesto(String testo) {
        this.testo = testo;
    }


    private String getSpiega() {
        return spiega;
    }


    private void setSpiega(String spiega) {
        this.spiega = spiega;
    }


    private ActionListener getListener() {
        return listener;
    }


    private void setListener(ActionListener listener) {
        this.listener = listener;
    }

    /**
     * Azione del bottone</p>
     */
    private final class AzChiudiDialogo implements ActionListener {

        public void actionPerformed(ActionEvent actionEvent) {
            bottonePremuto();
        }
    } // fine della classe 'interna'

}// fine della classe
